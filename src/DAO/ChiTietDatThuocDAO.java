package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;

public class ChiTietDatThuocDAO {

	public List<Object[]> getChiTietPhieuDatThuoc(String maPhieuDatThuoc) {
		List<Object[]> chiTietList = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaBan "
				+ "FROM ChiTietPhieuDatThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maPDT = ?";

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maPhieuDatThuoc);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String tenThuoc = rs.getString("tenThuoc");
				int soLuong = rs.getInt("soLuong");
				float donGiaBan = rs.getFloat("donGiaBan");

				String formatGiaBan = String.format("%,.0fđ", donGiaBan);
				String formatThanhTien = String.format("%,.0fđ", donGiaBan * soLuong);

				Object[] row = { tenThuoc, soLuong, formatGiaBan, formatThanhTien };
				chiTietList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chiTietList;
	}

	public boolean luuChiTietPhieuDatThuoc(String maPDH, String maThuoc, int soLuong, double donGiaBan) {
		String selectSql = "SELECT soLuong FROM ChiTietPhieuDatThuoc WHERE maPDT = ? AND maThuoc = ?";
		String deleteSql = "DELETE FROM ChiTietPhieuDatThuoc WHERE maPDT = ? AND maThuoc = ?";
		String insertSql = "INSERT INTO ChiTietPhieuDatThuoc (maPDT, maThuoc, soLuong, donGiaBan) VALUES (?, ?, ?, ?)";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

			// Kiểm tra xem thuốc đã tồn tại trong hóa đơn chưa
			selectStmt.setString(1, maPDH);
			selectStmt.setString(2, maThuoc);
			ResultSet rs = selectStmt.executeQuery();

			if (rs.next()) {
				// Thuốc đã tồn tại, kiểm tra số lượng
				int soLuongHienTai = rs.getInt("soLuong");
				if (soLuongHienTai != soLuong) {
					// Nếu số lượng khác, xóa bản ghi cũ
					try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
						deleteStmt.setString(1, maPDH);
						deleteStmt.setString(2, maThuoc);
						deleteStmt.executeUpdate();
					}
				} else return true;
			}

			// Thêm bản ghi mới
			try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
				insertStmt.setString(1, maPDH);
				insertStmt.setString(2, maThuoc);
				insertStmt.setInt(3, soLuong);
				insertStmt.setDouble(4, donGiaBan);
				insertStmt.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Object[]> getChiTietPhieuDatThuoc_ChuyenFrame(String maPhieuDatThuoc) {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT ct.maThuoc, t.tenThuoc, ct.soLuong, ct.donGiaBan "
				+ "FROM ChiTietPhieuDatThuoc ct JOIN Thuoc t ON ct.maThuoc = t.maThuoc " + "WHERE ct.maPDT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maPhieuDatThuoc);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String formatGiaBan = String.format("%,.0fđ", rs.getDouble("donGiaBan"));
				String formatThanhTien = String.format("%,.0fđ", rs.getDouble("donGiaBan") * rs.getInt("soLuong"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getInt("soLuong"), formatGiaBan,
						formatThanhTien };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public Object[] getPhieuDatThuocInfo(String maPhieuDatThuoc) {
		String sql = "SELECT maPDT, maNV, maKH, ngayDat, ngayGiao, trangThai, phuongThucThanhToan "
				+ "FROM PhieuDatThuoc WHERE maPDT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maPhieuDatThuoc);

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (rs.next()) {
				String ngaydat = sdf.format(rs.getDate("ngayDat"));
				String ngaygiao = sdf.format(rs.getDate("ngayGiao"));

				return new Object[] { rs.getString("maPDT"), rs.getString("maNV"), rs.getString("maKH"), ngaydat,
						ngaygiao, rs.getString("trangThai"), rs.getString("phuongThucThanhToan") };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean xoaChiTietPhieuDatThuoc(String maPDT, String maThuoc) {
		String selectSql = "SELECT COUNT(*) FROM ChiTietPhieuDatThuoc WHERE maPDT = ? AND maThuoc = ?";
		String deleteSql = "DELETE FROM ChiTietPhieuDatThuoc WHERE maPDT = ? AND maThuoc = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

			// Kiểm tra xem bản ghi có tồn tại không
			selectStmt.setString(1, maPDT);
			selectStmt.setString(2, maThuoc);
			ResultSet rs = selectStmt.executeQuery();
			rs.next();
			if (rs.getInt(1) != 0) {
				try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
					deleteStmt.setString(1, maPDT);
					deleteStmt.setString(2, maThuoc);
					int rowsAffected = deleteStmt.executeUpdate();
					return rowsAffected > 0;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
