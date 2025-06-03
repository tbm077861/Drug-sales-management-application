package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;

public class HoaDonNhapThuocDAO {
	public List<Object[]> loadDataToTable() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT maPNT, maNV, maNCC, ngayNhap, trangThai, phuongThucThanhToan FROM PhieuNhapThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {

				Object[] row = { rs.getString("maPNT"), rs.getString("maNV"), rs.getString("maNCC"), 
						sdf.format(rs.getDate("ngayNhap")), rs.getString("trangThai"),
						rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> searchHoaDon(String maHoaDon, String ngayNhap, String maNV, String maNCC, String trangThai,
			String phuongThucTT) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM PhieuNhapThuoc WHERE 1=1");
		List<Object> params = new ArrayList<>();

		if (!maHoaDon.isEmpty()) {
			sql.append(" AND maPNT LIKE ?");
			params.add("%" + maHoaDon + "%");
		}
		if (!maNV.isEmpty()) {
			sql.append(" AND maNV LIKE ?");
			params.add("%" + maNV + "%");
		}
		if (!maNCC.isEmpty()) {
			sql.append(" AND maNCC LIKE ?");
			params.add("%" + maNCC + "%");
		}
		if (ngayNhap != null && !ngayNhap.trim().isEmpty()) {
			sql.append(" AND ngayNhap = ?");
			params.add(ngayNhap);
		}
		if (trangThai != null && !trangThai.isEmpty() && !trangThai.equals("Tất cả")) {
			sql.append(" AND trangThai = ?");
			params.add(trangThai);
		}
		if (phuongThucTT != null && !phuongThucTT.isEmpty() && !phuongThucTT.equals("Tất cả")) {
			sql.append(" AND phuongThucThanhToan = ?");
			params.add(phuongThucTT);
		}

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String ngay = sdf.format(rs.getDate("ngayNhap"));

				Object[] row = { rs.getString("maPNT"), rs.getString("maNV"), rs.getString("maNCC"), ngay,
						rs.getString("trangThai"), rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> getAllHoaDon() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT * FROM PhieuNhapThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String tongTien = String.format("%,.0fđ", rs.getFloat("tongTien"));

				Object[] row = { rs.getString("maPNT"), rs.getString("maNV"), rs.getString("maNCC"),
						sdf.format(rs.getDate("ngayNhap")), tongTien, rs.getString("trangThai"),
						rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
		}
		return data;
	}
}