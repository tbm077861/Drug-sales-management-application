package DAO;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import connectDB.ConnectDB;
public class TraNhapThuocDAO {
	//Tìm kiếm phiếu nhâp theo mã phiếu nhập
		public List<Object[]> searchPhieuNhap(String maPhieuNhap){
			String sql = "SELECT * FROM PhieuNhapThuoc WHERE maPNT = ?";
			List<Object[]> result = new ArrayList<>();
	
			try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
		             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
				pstmt.setString(1, maPhieuNhap);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Object[] row = new Object[5];
					row[0] = rs.getString("maPNT");
					row[1] = rs.getString("maNV");
					row[2] = rs.getString("maNCC");
					// Định dạng ngày tháng
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					row[3] = sdf.format(rs.getDate("ngayNhap"));
					row[4] = rs.getString("phuongThucThanhToan");
					
					
					result.add(row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
	//Lấy chi tiết thuốc trong phiếu nhập
	public List<Object[]> getChiTietPhieuNhap(String maPhieuNhap) {
		String sql = "SELECT ct.maPNT, ct.maThuoc, ct.donGiaNhap, ct.soLuong, t.maThuoc,t.tenThuoc " +
				"FROM ChiTietPhieuNhapThuoc ct " +
				"JOIN Thuoc t ON ct.maThuoc = t.maThuoc " +
				"WHERE ct.maPNT = ?";
		List<Object[]> result = new ArrayList<>();
		
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, maPhieuNhap);
			ResultSet rs = pstmt.executeQuery();
			// Format tiền tệ Việt Nam
			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN")); // Định dạng tiền tệ Việt Nam

			while (rs.next()) {
				Object[] row = new Object[5];
				row[0] = rs.getString("maThuoc");
				row[1] = rs.getString("tenThuoc");
				row[2] = rs.getInt("soLuong");
				row[3] = currencyFormat.format(rs.getDouble("donGiaNhap")); // Định dạng đơn giá nhập
	            row[4] = currencyFormat.format(rs.getDouble("donGiaNhap") * rs.getInt("soLuong")); // Định dạng tổng tiền
				
				result.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	// Lấy mã trả nhập thuốc tiếp theo
	public String getNextMaTraNhap() {
		String sql = "SELECT MAX(maTNT) AS maxMaPTNT FROM PhieuTraNhapThuoc";
		String nextMaTraNhap = null;
		
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
		     PreparedStatement pstmt = conn.prepareStatement(sql);
		     ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String maxMaPTNT = rs.getString("maxMaPTNT");
				if (maxMaPTNT != null) {
					int nextId = Integer.parseInt(maxMaPTNT.substring(3)) + 1; // Tăng mã lên 1
					nextMaTraNhap = "TNT" + String.format("%03d", nextId); // Định dạng mã mới
				} else {
					nextMaTraNhap = "TNT001"; // Nếu không có mã nào, bắt đầu từ PN001
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextMaTraNhap;
	}
	// Lấy mã thu chi tiếp theo
	public String getNextMaThuChi() {
		String sql = "SELECT MAX(maPTC) AS maxMaPTC FROM PhieuThuChi";
		String nextMaThuChi = null;
		
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
		     PreparedStatement pstmt = conn.prepareStatement(sql);
		     ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String maxMaPTC = rs.getString("maxMaPTC");
				if (maxMaPTC != null) {
					int nextId = Integer.parseInt(maxMaPTC.substring(3)) + 1; // Tăng mã lên 1
					nextMaThuChi = "PTC" + String.format("%03d", nextId); // Định dạng mã mới
				} else {
					nextMaThuChi = "PTC001"; // Nếu không có mã nào, bắt đầu từ TC001
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextMaThuChi;
	}
	// Lưu phiếu trả nhập thuốc
	 public boolean luuPhieuTraNhapThuoc(String maTNT, String maPNT, String maNV, String maNCC,java.util.Date ngayTra, String lyDoTra) {
		 String sql = "INSERT INTO PhieuTraNhapThuoc (maTNT, maPNT, maNV, maNCC, ngayTra, lyDoTra) VALUES (?, ?, ?, ?, ?, ?)";
	        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, maTNT);
	            pstmt.setString(2, maPNT);
	            pstmt.setString(3, maNV);
	            pstmt.setString(4, maNCC);
	            pstmt.setDate(5, new java.sql.Date(ngayTra.getTime()));
	            pstmt.setString(6, lyDoTra);
	            int rowsAffected = pstmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	 }
	 
	 // Lưu chi tiết phiếu trả nhập thuốc
	 public boolean luuChiTietPhieuTraNhapThuoc(String maTNT, String maThuoc, int soLuong, String lyDoTra, double donGiaTra) {
		 	String sql = "INSERT INTO ChiTietPhieuTraNhapThuoc (maTNT, maThuoc, soLuong, donGiaNhap) VALUES (?, ?, ?, ?)";
		    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {
		        pstmt.setString(1, maTNT);
		        pstmt.setString(2, maThuoc);
		        pstmt.setInt(3, soLuong);
		        pstmt.setDouble(4, donGiaTra);
		        int rowsAffected = pstmt.executeUpdate();
		        return rowsAffected > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
	 // Lưu phiếu thu chi
	 public boolean luuPhieuThuChi(String maPTC, String maNV,Date ngayLap, String hinhThuc, String loaiGiaoDich, String maDon) {
		 	String sql = "INSERT INTO PhieuThuChi (maPTC, maNV, ngayGiaoDich, hinhThuc, loaiGiaoDich, maDon) VALUES (?, ?, ?, ?, ?, ?)";
	        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, maPTC);
	            pstmt.setString(2, maNV);
	            pstmt.setDate(3, new java.sql.Date(ngayLap.getTime()));
	            pstmt.setString(4, hinhThuc);
	            pstmt.setString(5, loaiGiaoDich);
	            pstmt.setString(6, maDon);
	            int rowsAffected = pstmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		 
	 }

		
}