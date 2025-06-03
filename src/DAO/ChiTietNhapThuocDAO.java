package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;

public class ChiTietNhapThuocDAO {

	public List<Object[]> getChiTietPhieuNhapThuoc(String maHoaDon) {
		List<Object[]> chiTietList = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaNhap "
				+ "FROM ChiTietPhieuNhapThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maPNT = ?";

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maHoaDon);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String tenThuoc = rs.getString("tenThuoc");
				int soLuong = rs.getInt("soLuong");
				float donGiaNhap = rs.getFloat("donGiaNhap");

				String formatGiaBan = String.format("%,.0fđ", donGiaNhap);
				String formatThanhTien = String.format("%,.0fđ", donGiaNhap * soLuong);

				Object[] row = { tenThuoc, soLuong, formatGiaBan, formatThanhTien };
				chiTietList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chiTietList;
	}

	public boolean luuChiTietPhieuNhap(String maPNT, DefaultTableModel model) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
            conn.setAutoCommit(false);
            String query = "INSERT INTO ChiTietPhieuNhapThuoc (maPNT, maThuoc, soLuong, donGiaNhap) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);

            for (int i = 0; i < model.getRowCount(); i++) {
                pstmt.setString(1, maPNT);
                pstmt.setString(2, model.getValueAt(i, 0).toString()); // maThuoc
                pstmt.setInt(3, Integer.parseInt(model.getValueAt(i, 2).toString())); // soLuong
                String donGiaStr = model.getValueAt(i, 3).toString().replace("đ", "").replace(",", "");
                pstmt.setDouble(4, Double.parseDouble(donGiaStr)); // donGiaNhap
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu chi tiết phiếu nhập: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



	public Object[] getPhieuNhapThuocInfo(String maHD) {
		String sql = "SELECT maPNT, maNV, maNCC, ngayNhap, trangThai, phuongThucThanhToan "
				+ "FROM PhieuNhapThuoc WHERE maPNT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maHD);

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (rs.next()) {
				return new Object[] { rs.getString("maPNT"), rs.getString("maNV"), rs.getString("maNCC"),
						sdf.format(rs.getDate("ngayNhap")), rs.getString("trangThai"),
						rs.getString("phuongThucThanhToan") };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
