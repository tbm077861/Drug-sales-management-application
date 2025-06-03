package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import connectDB.ConnectDB;

public class ChiTietBanThuocDAO {

	public List<Object[]> getChiTietPhieuBanThuoc(String maPhieuBanThuoc) {
		List<Object[]> chiTietList = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaBan "
				+ "FROM ChiTietPhieuBanThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maPBT = ?";

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maPhieuBanThuoc);
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

	public boolean luuChiTietPhieuBanThuoc(String maPhieuBanThuoc, String maThuoc, int soLuong, double donGiaBan) {
        String selectSql = "SELECT soLuong FROM ChiTietPhieuBanThuoc WHERE maPBT = ? AND maThuoc = ?";
        String deleteSql = "DELETE FROM ChiTietPhieuBanThuoc WHERE maPBT = ? AND maThuoc = ?";
        String insertSql = "INSERT INTO ChiTietPhieuBanThuoc (maPBT, maThuoc, soLuong, donGiaBan) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Kiểm tra xem thuốc đã tồn tại trong hóa đơn chưa
            selectStmt.setString(1, maPhieuBanThuoc);
            selectStmt.setString(2, maThuoc);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Thuốc đã tồn tại, kiểm tra số lượng
                int soLuongHienTai = rs.getInt("soLuong");
                if (soLuongHienTai != soLuong) {
					// Nếu số lượng khác, xóa bản ghi cũ
                	try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                        deleteStmt.setString(1, maPhieuBanThuoc);
                        deleteStmt.setString(2, maThuoc);
                        deleteStmt.executeUpdate();
                    }
                } else return true; // Không cần cập nhật nếu số lượng không thay đổi
            }

            // Thêm bản ghi mới
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, maPhieuBanThuoc);
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
	
	
	public List<Object[]> getChiTietPhieuBanThuoc_ChuyenFrame(String maPhieuBanThuoc) {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaBan "
				+ "FROM ChiTietPhieuBanThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maPBT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maPhieuBanThuoc);

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

	public Object[] getPhieuBanThuocInfo(String maPhieuBanThuoc) {
		String sql = "SELECT maPBT, ngayLap, maNV, maKH, trangThai, phuongThucThanhToan "
				+ "FROM PhieuBanThuoc WHERE maPBT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maPhieuBanThuoc);

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (rs.next()) {
				return new Object[] { rs.getString("maPBT"), sdf.format(rs.getDate("ngayLap")), rs.getString("maNV"),
						rs.getString("maKH"), rs.getString("trangThai"), rs.getString("phuongThucThanhToan") };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean xoaChiTietPhieuBanThuoc(String maPhieuBanThuoc, String maThuoc) {
        String selectSql = "SELECT COUNT(*) FROM ChiTietPhieuBanThuoc WHERE maPBT = ? AND maThuoc = ?";
        String deleteSql = "DELETE FROM ChiTietPhieuBanThuoc WHERE maPBT = ? AND maThuoc = ?";

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Kiểm tra xem bản ghi có tồn tại không
            selectStmt.setString(1, maPhieuBanThuoc);
            selectStmt.setString(2, maThuoc);
            ResultSet rs = selectStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) != 0) {
            	try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setString(1, maPhieuBanThuoc);
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
