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

public class BanThuocDAO {

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

	public String getLastMaPhieuBanThuoc() {
		String sql = "SELECT MAX(maPBT) FROM PhieuBanThuoc";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String lastMaHD = rs.getString(1);
				if (lastMaHD != null && !lastMaHD.isEmpty()) {
					return lastMaHD;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã hóa đơn cuối cùng: " + e.getMessage());
		}
		return "PBT000";
	}

	public boolean luuPhieuBanThuoc(String maHD, String ngayLap, String maNV, String maKH, String trangThai,
	        String phuongThucThanhToan) throws ParseException {
	    String selectSql = "SELECT COUNT(*) FROM PhieuBanThuoc WHERE maPBT = ?";
	    String updateSql = "UPDATE PhieuBanThuoc SET ngayLap = ?, maNV = ?, maKH = ?, trangThai = ?, phuongThucThanhToan = ? WHERE maPBT = ?";
	    String insertSql = "INSERT INTO PhieuBanThuoc (maPBT, ngayLap, maNV, maKH, trangThai, phuongThucThanhToan) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

	        // Kiểm tra xem mã hóa đơn đã tồn tại hay chưa
	        selectStmt.setString(1, maHD);
	        ResultSet rs = selectStmt.executeQuery();
	        rs.next();
	        boolean exists = rs.getInt(1) > 0;

	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date parsed = sdf.parse(ngayLap);
	        java.sql.Date datesql = new java.sql.Date(parsed.getTime());

	        if (exists) {
	            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
	                updateStmt.setDate(1, datesql);
	                updateStmt.setString(2, maNV);
	                updateStmt.setString(3, maKH);
	                updateStmt.setString(4, trangThai);
	                updateStmt.setString(5, phuongThucThanhToan);
	                updateStmt.setString(6, maHD);
	                updateStmt.executeUpdate();
	                return true;
	            }
	        } else {
	            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
	                insertStmt.setString(1, maHD);
	                insertStmt.setDate(2, datesql);
	                insertStmt.setString(3, maNV);
	                insertStmt.setString(4, maKH);
	                insertStmt.setString(5, trangThai);
	                insertStmt.setString(6, phuongThucThanhToan);
	                insertStmt.executeUpdate();
	                return true;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi lưu hóa đơn: " + e.getMessage());
	    }
	    return false;
	}

	public boolean capNhatSoLuongTon(String maThuoc, int soLuong) {
		String sql = "UPDATE Thuoc SET soLuongTon = soLuongTon - ?, soLuongThucTe = soLuongThucTe - ? WHERE maThuoc = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, soLuong);
			stmt.setInt(2, soLuong);
			stmt.setString(3, maThuoc);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
