package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;

public class DatThuocDAO {

	// Lấy danh sách thuốc để hiển thị
	public List<Object[]> loadDataToDSSP() {
		List<Object[]> data = new ArrayList<Object[]>();

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM Thuoc")) {

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String formatGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						formatGiaBan, rs.getInt("soLuongTon") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return data;
	}

	// Tìm kiếm thuốc theo mã thuốc hoặc tên thuốc
	public List<Object[]> timKiemThuoc(String maThuoc, String tenThuoc) {
		List<Object[]> data = new ArrayList<Object[]>();
		StringBuilder sql = new StringBuilder("SELECT * FROM Thuoc WHERE 1=1");
		List<Object> params = new ArrayList<Object>();

		if (!maThuoc.isEmpty()) {
			sql.append(" AND maThuoc LIKE ?");
			params.add("%" + maThuoc + "%");
		}
		if (!tenThuoc.isEmpty()) {
			sql.append(" AND tenThuoc LIKE ?");
			params.add("%" + tenThuoc + "%");
		}

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String formatGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						formatGiaBan, rs.getInt("soLuongTon") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public boolean capNhatSoLuongTon(String maThuoc, int soLuong) {
		String sql = "UPDATE Thuoc SET soLuongTon = soLuongTon - ? WHERE maThuoc = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, soLuong);
			stmt.setString(2, maThuoc);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Đặt Hàng

	public String getLastMaPhieuDatHang() {
		String sql = "SELECT MAX(maPDT) FROM PhieuDatThuoc";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String lastMaPDH = rs.getString(1);
				if (lastMaPDH != null && !lastMaPDH.isEmpty()) {
					return lastMaPDH;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã phiếu đặt hàng cuối cùng: " + e.getMessage());
		}
		return "PDT000";
	}

	public boolean luuPhieuDatHang(String maPDH, String ngayLap, String ngayGiao, String maNV, String maKH, String trangThai,
	        String phuongThucThanhToan) throws ParseException {
	    String selectSql = "SELECT COUNT(*) FROM PhieuDatThuoc WHERE maPDT = ?";
	    String updateSql = "UPDATE PhieuDatThuoc SET ngayDat = ?, ngayGiao = ?, maNV = ?, maKH = ?, trangThai = ?, phuongThucThanhToan = ? WHERE maPDT = ?";
	    String insertSql = "INSERT INTO PhieuDatThuoc (maPDT, ngayDat, ngayGiao, maNV, maKH, trangThai, phuongThucThanhToan) VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

	    	// Kiểm tra xem mã hóa đơn đã tồn tại hay chưa
	        selectStmt.setString(1, maPDH);
	        ResultSet rs = selectStmt.executeQuery();
	        rs.next();
	        boolean exists = rs.getInt(1) > 0;

	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date parsed1 = sdf.parse(ngayLap);
	        java.sql.Date dat = new java.sql.Date(parsed1.getTime());
	        java.util.Date parsed2 = sdf.parse(ngayGiao);
	        java.sql.Date nhap = new java.sql.Date(parsed2.getTime());

	        if (exists) {
	            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
	                updateStmt.setDate(1, dat);
	                updateStmt.setDate(2, nhap);
	                updateStmt.setString(3, maNV);
	                updateStmt.setString(4, maKH);
	                updateStmt.setString(5, trangThai);
	                updateStmt.setString(6, phuongThucThanhToan);
	                updateStmt.setString(7, maPDH);
	                updateStmt.executeUpdate();
	                return true;
	            }
	        } else {
	            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
	                insertStmt.setString(1, maPDH);
	                insertStmt.setDate(2, dat);
	                insertStmt.setDate(3, nhap);
	                insertStmt.setString(4, maNV);
	                insertStmt.setString(5, maKH);
	                insertStmt.setString(6, trangThai);
	                insertStmt.setString(7, phuongThucThanhToan);
	                insertStmt.executeUpdate();
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi lưu phiếu đặt thuốc: " + e.getMessage());
	    }
	    return false;
	}

}
