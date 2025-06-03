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

public class TaiChinhDAO {
	public List<Object[]> getGiaoDich(String date) {
		List<Object[]> data = new ArrayList<Object[]>();

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM PhieuThuChi WHERE ngayGiaoDich = ?")) {

			stmt.setString(1, date);
			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String ngayGD = sdf.format(rs.getDate("ngayGiaoDich"));

				Object[] row = { rs.getString("maPTC"), rs.getString("maNV"), ngayGD, rs.getString("hinhThuc"),
						rs.getString("loaiGiaoDich"), rs.getString("maDon") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public boolean luuPhieuThu(String maNV, String ngayLap, String phuongThucThanhToanStr, String loaiGiaoDich,
			String maHoaDon) {
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc")) {
			// Generate unique maPhieu
			String maPhieu = generateMaPhieu(con);

			// Prepare SQL insert statement
			String sql = "INSERT INTO PhieuThuChi (maPTC, maNV, ngayGiaoDich, hinhThuc, loaiGiaoDich, maDon) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);

			// Convert ngayLap (dd/MM/yyyy) to java.sql.Date
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsedDate = sdf.parse(ngayLap);
			java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

			// Set parameters
			stmt.setString(1, maPhieu);
			stmt.setString(2, maNV);
			stmt.setDate(3, sqlDate);
			stmt.setString(4, phuongThucThanhToanStr);
			stmt.setString(5, loaiGiaoDich);
			stmt.setString(6, maHoaDon);

			// Execute insert
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lưu phiếu thu: " + e.getMessage());
			return false;
		}
	}

	public String generateMaPhieu(Connection con) throws SQLException {
		String maPhieu = "PTC";
		String sql = "SELECT COUNT(*) FROM PhieuThuChi";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int count = rs.getInt(1) + 1;
			maPhieu += String.format("%03d", count); // e.g., PTC001
		}
		return maPhieu;
	}

	// Tìm kiếm hóa đơn theo khách hàng
	public List<Object[]> timKiemPhieuTuKH(String maKH) {
	    List<Object[]> data = new ArrayList<>();
	    StringBuilder sql = new StringBuilder(
	        "SELECT ptc.* FROM PhieuThuChi ptc " +
	        "WHERE ptc.maDon IN (" +
	        "    SELECT maPBT FROM PhieuBanThuoc WHERE maKH LIKE ?" +
	        "    UNION " +
	        "    SELECT maPDT FROM PhieuDatThuoc WHERE maKH LIKE ?" +
	        "    UNION " +
	        "    SELECT maPTT FROM PhieuTraThuoc WHERE maKH LIKE ?" +
	        ")"
	    );
	    List<Object> params = new ArrayList<>();

	    if (maKH != null && !maKH.trim().isEmpty()) {
	        params.add("%" + maKH.trim() + "%");
	        params.add("%" + maKH.trim() + "%");
	        params.add("%" + maKH.trim() + "%");
	    } else {
	        return data;
	    }

	    try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = con.prepareStatement(sql.toString())) {

	        for (int i = 0; i < params.size(); i++) {
	            stmt.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = stmt.executeQuery();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	        while (rs.next()) {
	            String ngayGD = sdf.format(rs.getDate("ngayGiaoDich"));

	            Object[] row = {
	                rs.getString("maPTC"),
	                rs.getString("maNV"),
	                ngayGD,
	                rs.getString("hinhThuc"),
	                rs.getString("loaiGiaoDich"),
	                rs.getString("maDon")
	            };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu: " + e.getMessage());
	    }

	    return data;
	}
}
