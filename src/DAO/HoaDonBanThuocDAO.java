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

public class HoaDonBanThuocDAO {
	public List<Object[]> loadDataToTable() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT maPBT, ngayLap, maNV, maKH, trangThai, phuongThucThanhToan FROM PhieuBanThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {

				Object[] row = { rs.getString("maPBT"), sdf.format(rs.getDate("ngayLap")),
						rs.getString("maNV"), rs.getString("maKH"), rs.getString("trangThai"),
						rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> searchHoaDon(String maHoaDon, String ngayLap, String maNV, String maKH, String trangThai,
			String phuongThucTT) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT * FROM PhieuBanThuoc WHERE 1=1");
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
		if (ngayLap != null && !ngayLap.trim().isEmpty()) {
			sql.append(" AND ngayLap = ?");
			params.add(ngayLap);
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
				String ngay = sdf.format(rs.getDate("ngayLap"));

				Object[] row = { rs.getString("maPBT"), ngay, rs.getString("maNV"), rs.getString("maKH"),
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
		String sql = "SELECT * FROM PhieuBanThuoc";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {

				Object[] row = { rs.getString("maPBT"), sdf.format(rs.getDate("ngayLap")),
						rs.getString("maNV"), rs.getString("maKH"), rs.getString("trangThai"),
						rs.getString("phuongThucThanhToan") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu hóa đơn: " + e.getMessage());
		}
		return data;
	}
	
	public boolean huyHoaDon(String maHD) {
		String sql = "UPDATE PhieuBanThuoc SET trangThai = N'Đã hủy' WHERE maPBT = ?";
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
	
	// Thống kê hóa đơn theo tuần
	public List<Object[]> thongKeTheoTuan(int nam, int thang, int tuan) {
	    List<Object[]> data = new ArrayList<>();
	    String sql;
	    int ngayBatDau, ngayKetThuc;
	    switch (tuan) {
	        case 1:
	            ngayBatDau = 1;
	            ngayKetThuc = 7;
	            break;
	        case 2:
	            ngayBatDau = 8;
	            ngayKetThuc = 14;
	            break;
	        case 3:
	            ngayBatDau = 15;
	            ngayKetThuc = 21;
	            break;
	        case 4:
	            ngayBatDau = 22;
	            ngayKetThuc = 28;
	            break;
	        default:
	            return data; // Bỏ tuần 5
	    }
	    sql = "SELECT h.maPBT, h.maNV, h.maKH, h.ngayLap, SUM(ct.soLuong * ct.donGiaBan) AS tongTien " +
	          "FROM PhieuBanThuoc h " +
	          "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	          "WHERE YEAR(h.ngayLap) = ? AND MONTH(h.ngayLap) = ? " +
	          "AND DAY(h.ngayLap) BETWEEN ? AND ? AND h.trangThai = N'Đã thanh toán' " +
	          "GROUP BY h.maPBT, h.maNV, h.maKH, h.ngayLap";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, nam);
	        stmt.setInt(2, thang);
	        stmt.setInt(3, ngayBatDau);
	        stmt.setInt(4, ngayKetThuc);
	        ResultSet rs = stmt.executeQuery();
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	        while (rs.next()) {
	            String ngayLap = sdf.format(rs.getDate("ngayLap"));
	            double tongTienValue = rs.getDouble("tongTien");
	            String tongTien = String.format("%,.0f VNĐ", tongTienValue);
	            Object[] row = { rs.getString("maPBT"), ngayLap, rs.getString("maKH"), tongTien };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo tuần: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê hóa đơn theo tháng trong năm
	public List<Object[]> thongKeTheoThang(int nam) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT MONTH(h.ngayLap) AS thang, COUNT(DISTINCT h.maPBT) AS soHoaDon, SUM(ct.soLuong * ct.donGiaBan) AS tongDoanhThu " +
	                 "FROM PhieuBanThuoc h " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE YEAR(h.ngayLap) = ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY MONTH(h.ngayLap) ORDER BY thang";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, nam);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String thang = String.format("Tháng %d", rs.getInt("thang"));
	            String soHoaDon = String.valueOf(rs.getInt("soHoaDon"));
	            double tongDoanhThuValue = rs.getDouble("tongDoanhThu");
	            String tongDoanhThu = String.format("%,.0f VNĐ", tongDoanhThuValue);
	            Object[] row = { thang, soHoaDon, tongDoanhThu };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo tháng: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê hóa đơn theo quý trong năm
	public List<Object[]> thongKeTheoQuy(int nam) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT CASE " +
	                 "WHEN MONTH(h.ngayLap) IN (1,2,3) THEN 'Quý 1' " +
	                 "WHEN MONTH(h.ngayLap) IN (4,5,6) THEN 'Quý 2' " +
	                 "WHEN MONTH(h.ngayLap) IN (7,8,9) THEN 'Quý 3' " +
	                 "ELSE 'Quý 4' END AS quy, " +
	                 "COUNT(DISTINCT h.maPBT) AS soHoaDon, SUM(ct.soLuong * ct.donGiaBan) AS tongDoanhThu " +
	                 "FROM PhieuBanThuoc h " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE YEAR(h.ngayLap) = ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY CASE " +
	                 "WHEN MONTH(h.ngayLap) IN (1,2,3) THEN 'Quý 1' " +
	                 "WHEN MONTH(h.ngayLap) IN (4,5,6) THEN 'Quý 2' " +
	                 "WHEN MONTH(h.ngayLap) IN (7,8,9) THEN 'Quý 3' " +
	                 "ELSE 'Quý 4' END ORDER BY quy";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, nam);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String quy = rs.getString("quy");
	            String soHoaDon = String.valueOf(rs.getInt("soHoaDon"));
	            double tongDoanhThuValue = rs.getDouble("tongDoanhThu");
	            String tongDoanhThu = String.format("%,.0f VNĐ", tongDoanhThuValue);
	            Object[] row = { quy, soHoaDon, tongDoanhThu };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo quý: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê hóa đơn theo năm (chọn khoảng năm)
	public List<Object[]> thongKeTheoNam(int tuNam, int denNam) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT YEAR(h.ngayLap) AS nam, COUNT(DISTINCT h.maPBT) AS soHoaDon, SUM(ct.soLuong * ct.donGiaBan) AS tongDoanhThu " +
	                 "FROM PhieuBanThuoc h " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE YEAR(h.ngayLap) BETWEEN ? AND ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY YEAR(h.ngayLap) ORDER BY nam";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, tuNam);
	        stmt.setInt(2, denNam);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            String nam = String.valueOf(rs.getInt("nam"));
	            String soHoaDon = String.valueOf(rs.getInt("soHoaDon"));
	            double tongDoanhThuValue = rs.getDouble("tongDoanhThu");
	            String tongDoanhThu = String.format("%,.0f VNĐ", tongDoanhThuValue);
	            Object[] row = { nam, soHoaDon, tongDoanhThu };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo năm: " + e.getMessage());
	    }
	    return data;
	}

    // Thống kê tỷ lệ trạng thái hóa đơn
    public Map<String, Double> thongKeTrangThai(String fromDate, String toDate) {
        Map<String, Double> result = new HashMap<>();
        String sql = "SELECT trangThai, COUNT(*) AS soLuong " +
                     "FROM PhieuBanThuoc WHERE CAST(ngayLap AS DATE) BETWEEN ? AND ? " +
                     "GROUP BY trangThai";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fromDate);
            stmt.setString(2, toDate);
            ResultSet rs = stmt.executeQuery();

            int total = 0;
            Map<String, Integer> counts = new HashMap<>();
            while (rs.next()) {
                String trangThai = rs.getString("trangThai");
                // Handle null or empty trangThai
                if (trangThai == null || trangThai.trim().isEmpty()) {
                    trangThai = "Không xác định";
                }
                int soLuong = rs.getInt("soLuong");
                counts.put(trangThai, counts.getOrDefault(trangThai, 0) + soLuong);
                total += soLuong;
            }

            if (total == 0) {
                return result; // Avoid division by zero
            }

            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                double percentage = (entry.getValue() * 100.0) / total;
                result.put(entry.getKey(), percentage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thống kê trạng thái: " + e.getMessage());
        }
        return result;
    }

    // Thống kê tỷ lệ phương thức thanh toán
    public Map<String, Double> thongKePhuongThucThanhToan(String fromDate, String toDate) {
        Map<String, Double> result = new HashMap<>();
        String sql = "SELECT phuongThucThanhToan, COUNT(*) AS soLuong " +
                     "FROM PhieuBanThuoc WHERE CAST(ngayLap AS DATE) BETWEEN ? AND ? " +
                     "GROUP BY phuongThucThanhToan";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fromDate);
            stmt.setString(2, toDate);
            ResultSet rs = stmt.executeQuery();

            int total = 0;
            Map<String, Integer> counts = new HashMap<>();
            while (rs.next()) {
                String phuongThuc = rs.getString("phuongThucThanhToan");
                // Handle null or empty phuongThucThanhToan
                if (phuongThuc == null || phuongThuc.trim().isEmpty()) {
                    phuongThuc = "Không xác định";
                }
                int soLuong = rs.getInt("soLuong");
                counts.put(phuongThuc, counts.getOrDefault(phuongThuc, 0) + soLuong);
                total += soLuong;
            }

            if (total == 0) {
                return result; // Avoid division by zero
            }

            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                double percentage = (entry.getValue() * 100.0) / total;
                result.put(entry.getKey(), percentage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thống kê phương thức thanh toán: " + e.getMessage());
        }
        return result;
    }
    
    // search trong ThongKe
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