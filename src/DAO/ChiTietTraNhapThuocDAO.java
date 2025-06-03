package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class ChiTietTraNhapThuocDAO {
	public List<Object[]> getChiTietPhieuTraNhapThuoc(String maPhieuDatHang) {
		List<Object[]> chiTietList = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaNhap "
				+ "FROM ChiTietPhieuTraNhapThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maTNT = ?";

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maPhieuDatHang);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String tenThuoc = rs.getString("tenThuoc");
				int soLuong = rs.getInt("soLuong");
				float donGiaBan = rs.getFloat("donGiaNhap");

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
	
	public Object[] getPhieuTraNhapThuocInfo(String maHD) {
		String sql = "SELECT maTNT, maNV, maNCC, ngayTra, lyDoTra "
				+ "FROM PhieuTraNhapThuoc WHERE maTNT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maHD);

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (rs.next()) {
				return new Object[] { rs.getString("maTNT"), rs.getString("maNV"), rs.getString("maNCC"),
						sdf.format(rs.getDate("ngayTra")), rs.getString("lyDoTra") };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
