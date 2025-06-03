package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;

public class HoaDonTraNhapThuocDAO {
	public List<Object[]> loadDataToTable() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT maTNT, maNV, maNCC, ngayTra, lyDoTra FROM PhieuTraNhapThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {

				Object[] row = { rs.getString("maTNT"), rs.getString("maNV"), rs.getString("maNCC"),
						sdf.format(rs.getDate("ngayTra")), rs.getString("lyDoTra") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> searchHoaDon(String maHoaDon, String maNV, String maNCC, String ngayTra) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM PhieuTraNhapThuoc WHERE 1=1");
		List<Object> params = new ArrayList<>();

		if (!maHoaDon.isEmpty()) {
			sql.append(" AND maTNT LIKE ?");
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
		if (ngayTra != null && !ngayTra.trim().isEmpty()) {
			sql.append(" AND ngayTra = ?");
			params.add(ngayTra);
		}

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String ngay = sdf.format(rs.getDate("ngayTra"));

				Object[] row = { rs.getString("maTNT"), rs.getString("maNV"), rs.getString("maNCC"), ngay, rs.getString("lyDoTra") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
		}
		return data;
	}
}