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

public class HoaDonDatThuocDAO {
	
	public List<Object[]> loadDataToTablePDH() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT maPDT, maNV, maKH, ngayDat, ngayGiao, trangThai, phuongThucThanhToan FROM PhieuDatThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {

				Object[] row = { rs.getString("maPDT"), rs.getString("maNV"), rs.getString("maKH"), 
						sdf.format(rs.getDate("ngayDat")), sdf.format(rs.getDate("ngayGiao")), 
						rs.getString("trangThai"), rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu đặt hàng: " + e.getMessage());
		}
		return data;
	}
	
	public List<Object[]> searchPhieuDatHang(String maPhieuDatHang, String maNV, String maKH, String ngayDat, String ngayGiao, String trangThai,
			String phuongThucTT) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM PhieuDatThuoc WHERE 1=1");
		List<Object> params = new ArrayList<>();

		if (!maPhieuDatHang.isEmpty()) {
			sql.append(" AND maPDT LIKE ?");
			params.add("%" + maPhieuDatHang + "%");
		}
		if (!maNV.isEmpty()) {
			sql.append(" AND maNV LIKE ?");
			params.add("%" + maNV + "%");
		}
		if (!maKH.isEmpty()) {
			sql.append(" AND maKH LIKE ?");
			params.add("%" + maKH + "%");
		}
		if (ngayDat != null && !ngayDat.trim().isEmpty()) {
			sql.append(" AND ngayDat = ?");
			params.add(ngayDat);
		}
		if (ngayGiao != null && !ngayGiao.trim().isEmpty()) {
			sql.append(" AND ngayGiao = ?");
			params.add(ngayGiao);
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
				String ngaydat = sdf.format(rs.getDate("ngayDat"));
				String ngaygiao = sdf.format(rs.getDate("ngayGiao"));

				Object[] row = { rs.getString("maPDT"), rs.getString("maNV"), rs.getString("maKH"), ngaydat, ngaygiao,
						rs.getString("trangThai"), rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm phiếu đặt hàng: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> getAllPhieuDatHang() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT * FROM PhieuDatThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String ngaydat = sdf.format(rs.getDate("ngayDat"));
				String ngaygiao = sdf.format(rs.getDate("ngayGiao"));

				Object[] row = { rs.getString("maPDT"), rs.getString("maNV"), rs.getString("maKH"), ngaydat, ngaygiao,
						rs.getString("trangThai"), rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu phiếu đặt hàng: " + e.getMessage());
		}
		return data;
	}
	
	public boolean huyPhieuDatHang(String maHD) {
		String sql = "UPDATE PhieuDatThuoc SET trangThai = N'Đã hủy' WHERE maPDT = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, maHD);
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi hủy hóa đơn: " + e.getMessage());
			return false;
		}
	}
	
    public List<Object[]> searchHoaDon(String maHoaDon, String maNV, String maKH, String fromDate, String toDate) {
        List<Object[]> data = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT maPBT, maNV, maKH, ngayLap FROM PhieuBanThuoc WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (!maHoaDon.isEmpty()) {
            sql.append(" AND maPBT LIKE ?");
            params.add("%" + maHoaDon + "%");
        }
        if (!maNV.isEmpty()) {
            sql.append(" AND maNV LIKE ?");
            params.add("%" + maNV + "%");
        }
        if (!maKH.isEmpty()) {
            sql.append(" AND maKH LIKE ?");
            params.add("%" + maKH + "%");
        }
        if (!fromDate.isEmpty() && !toDate.isEmpty()) {
            sql.append(" AND CAST(ngayLap AS DATE) BETWEEN ? AND ?");
            params.add(fromDate);
            params.add(toDate);
        }

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String ngayLap = sdf.format(rs.getDate("ngayLap"));
                Object[] row = { rs.getString("maPBT"), rs.getString("maNV"), rs.getString("maKH"), ngayLap };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu: " + e.getMessage());
        }
        return data;
    }
}