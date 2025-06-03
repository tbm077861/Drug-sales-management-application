package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import connectDB.*;

public class KhachHangDAO {

	public List<Object[]> loadDataToTable() {
		List<Object[]> data = new ArrayList<>();
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT maKH, hoTen, ngaySinh, diaChi, soDienThoai, email FROM KhachHang WHERE maKH NOT LIKE 'KHVL%'")) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String ngaySinh = sdf.format(rs.getDate("ngaySinh"));
				Object[] row = { rs.getString("maKH"), rs.getString("hoTen"), ngaySinh, rs.getString("diaChi"),
						rs.getString("soDienThoai"), rs.getString("email") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public Map<String, String> getKhachHangData() {
		Map<String, String> khachHangData = new HashMap<>();
		String sql = "SELECT maKH, hoTen FROM KhachHang";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				khachHangData.put(rs.getString("maKH"), rs.getString("hoTen"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return khachHangData;
	}

	public boolean isDuplicateDienThoai(String dienThoai, String maKH) {
		String sql = "SELECT COUNT(*) FROM KhachHang WHERE soDienThoai = ?";
		if (maKH != null) {
			sql += " AND maKH != ?";
		}
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, dienThoai);
			if (maKH != null) {
				pstmt.setString(2, maKH);
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra điện thoại: " + e.getMessage());
		}
		return false;
	}

	public boolean isDuplicateEmail(String email, String maKH) {
		String sql = "SELECT COUNT(*) FROM KhachHang WHERE email = ?";
		if (maKH != null) {
			sql += " AND maKH != ?";
		}
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, email);
			if (maKH != null) {
				pstmt.setString(2, maKH);
			}
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra Email: " + e.getMessage());
		}
		return false;
	}

	public void saveKhachHang(boolean isEditing, String maKH, String hoTen, String ngaySinh, String diaChi,
			String soDienThoai, String email) throws ParseException {
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc")) {
			String sql;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse(ngaySinh);
			java.sql.Date datesql = new java.sql.Date(parsed.getTime());

			if (isEditing) {
				sql = "UPDATE KhachHang SET hoTen=?, ngaySinh=?, diaChi=?, soDienThoai=?, email=? WHERE maKH=?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, hoTen);
					pstmt.setDate(2, datesql);
					pstmt.setString(3, diaChi);
					pstmt.setString(4, soDienThoai);
					pstmt.setString(5, email);
					pstmt.setString(6, maKH);
					pstmt.executeUpdate();
				}
			} else {
				sql = "INSERT INTO KhachHang (maKH, hoTen, ngaySinh, diaChi, soDienThoai, email) VALUES (?, ?, ?, ?, ?, ?)";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, maKH);
					pstmt.setString(2, hoTen);
					pstmt.setDate(3, datesql);
					pstmt.setString(4, diaChi);
					pstmt.setString(5, soDienThoai);
					pstmt.setString(6, email);
					pstmt.executeUpdate();
				}
			}

			JOptionPane.showMessageDialog(null, isEditing ? "Cập nhật thành công!" : "Thêm mới thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
		}
	}

	public void deleteKhachHang(String maKH) {
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement("DELETE FROM KhachHang WHERE maKH = ?")) {

			pstmt.setString(1, maKH);
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(null, "Xóa thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi xóa: " + e.getMessage());
		}
	}

	public List<Object[]> searchKhachHang(String maKH, String tenKH, String dienThoai, String diaChi) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"SELECT maKH, hoTen, ngaySinh, diaChi, soDienThoai, email FROM KhachHang WHERE 1=1");
		List<Object> params = new ArrayList<>();

		if (!maKH.isEmpty()) {
			sql.append(" AND maKH LIKE ?");
			params.add("%" + maKH + "%");
		}
		if (!tenKH.isEmpty()) {
			sql.append(" AND hoTen LIKE ?");
			params.add("%" + tenKH + "%");
		}
		if (!dienThoai.isEmpty()) {
			sql.append(" AND soDienThoai LIKE ?");
			params.add("%" + dienThoai + "%");
		}
		if (!diaChi.isEmpty()) {
			sql.append(" AND diaChi LIKE ?");
			params.add("%" + diaChi + "%");
		}

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String ngaySinh = sdf.format(rs.getDate("ngaySinh"));
				Object[] row = { rs.getString("maKH"), rs.getString("hoTen"), ngaySinh, rs.getString("diaChi"),
						rs.getString("soDienThoai"), rs.getString("email") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> getAllKhachHang() {
		List<Object[]> List = new ArrayList<>();
		String sql = "SELECT * FROM KhachHang";
		try (Connection connection = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String maKH = resultSet.getString("maKH");
				String tenKH = resultSet.getString("hoTen");

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				String ngaySinh = sdf.format(resultSet.getDate("ngaySinh"));

				String diaChi = resultSet.getString("diaChi");
				String soDienThoai = resultSet.getString("soDienThoai");
				String email = resultSet.getString("email");

				Object[] nv = { maKH, tenKH, ngaySinh, diaChi, soDienThoai, email };
				List.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return List;
	}

	public String getLastMaKH() {
		String sql = "SELECT MAX(maKH) FROM KhachHang WHERE maKH NOT LIKE 'KHVL%'";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String lastMaKH = rs.getString(1);
				if (lastMaKH != null && !lastMaKH.isEmpty()) {
					return lastMaKH;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã khách hàng cuối cùng: " + e.getMessage());
		}
		return "KH000";
	}

	public Map<String, String> getThongTinKhachHang(String soDienThoai) {

		Map<String, String> khachHangData = new HashMap<>();
		String sql = "SELECT maKH, hoTen FROM KhachHang WHERE soDienThoai = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, soDienThoai);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				khachHangData.put("maKH", rs.getString("maKH"));
				khachHangData.put("hoTen", rs.getString("hoTen"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return khachHangData;
	}

	public List<String> getAllSoDienThoai() {
		List<String> dsSDT = new ArrayList<>();
		String sql = "SELECT soDienThoai FROM KhachHang WHERE soDienThoai IS NOT NULL";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				dsSDT.add(rs.getString("soDienThoai"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsSDT;
	}

	public Map<String, String> getKhachHangByMaKH(String maKH) {
		Map<String, String> khachHangData = new HashMap<>();
		String sql = "SELECT * FROM KhachHang WHERE maKH = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, maKH);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				khachHangData.put("soDienThoai", rs.getString("soDienThoai"));
				khachHangData.put("hoTen", rs.getString("hoTen"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return khachHangData;
	}

	// Thống kê khách hàng theo ngày
	public List<Object[]> thongKeTheoNgay(String ngay) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT k.maKH, k.hoTen, COUNT(DISTINCT h.maPBT) AS soGiaoDich, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu " +
	                 "FROM KhachHang k " +
	                 "LEFT JOIN PhieuBanThuoc h ON k.maKH = h.maKH " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE CAST(h.ngayLap AS DATE) = ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY k.maKH, k.hoTen";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, ngay);
	        ResultSet rs = pstmt.executeQuery();

	        int stt = 1;
	        while (rs.next()) {
	            String maKH = rs.getString("maKH");
	            String hoTen = rs.getString("hoTen");
	            int soGiaoDich = rs.getInt("soGiaoDich");
	            double doanhThu = rs.getDouble("doanhThu");
	            double trungBinh = soGiaoDich > 0 ? doanhThu / soGiaoDich : 0;

	            Object[] row = { stt++, maKH, hoTen, soGiaoDich, doanhThu, trungBinh };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo ngày: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê khách hàng theo tháng
	public List<Object[]> thongKeTheoThang(int nam, int thang) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT k.maKH, k.hoTen, COUNT(DISTINCT h.maPBT) AS soGiaoDich, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu " +
	                 "FROM KhachHang k " +
	                 "LEFT JOIN PhieuBanThuoc h ON k.maKH = h.maKH " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE YEAR(h.ngayLap) = ? AND MONTH(h.ngayLap) = ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY k.maKH, k.hoTen";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, nam);
	        pstmt.setInt(2, thang);
	        ResultSet rs = pstmt.executeQuery();

	        int stt = 1;
	        while (rs.next()) {
	            String maKH = rs.getString("maKH");
	            String hoTen = rs.getString("hoTen");
	            int soGiaoDich = rs.getInt("soGiaoDich");
	            double doanhThu = rs.getDouble("doanhThu");
	            double trungBinh = soGiaoDich > 0 ? doanhThu / soGiaoDich : 0;

	            Object[] row = { stt++, maKH, hoTen, soGiaoDich, doanhThu, trungBinh };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo tháng: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê khách hàng theo năm
	public List<Object[]> thongKeTheoNam(int nam) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT k.maKH, k.hoTen, COUNT(DISTINCT h.maPBT) AS soGiaoDich, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu " +
	                 "FROM KhachHang k " +
	                 "LEFT JOIN PhieuBanThuoc h ON k.maKH = h.maKH " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE YEAR(h.ngayLap) = ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY k.maKH, k.hoTen";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, nam);
	        ResultSet rs = pstmt.executeQuery();

	        int stt = 1;
	        while (rs.next()) {
	            String maKH = rs.getString("maKH");
	            String hoTen = rs.getString("hoTen");
	            int soGiaoDich = rs.getInt("soGiaoDich");
	            double doanhThu = rs.getDouble("doanhThu");
	            double trungBinh = soGiaoDich > 0 ? doanhThu / soGiaoDich : 0;

	            Object[] row = { stt++, maKH, hoTen, soGiaoDich, doanhThu, trungBinh };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo năm: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê khách hàng theo khoảng thời gian
	public List<Object[]> thongKeTheoKhoangThoiGian(String tuNgay, String denNgay) {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT k.maKH, k.hoTen, COUNT(DISTINCT h.maPBT) AS soGiaoDich, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu " +
	                 "FROM KhachHang k " +
	                 "LEFT JOIN PhieuBanThuoc h ON k.maKH = h.maKH " +
	                 "LEFT JOIN ChiTietPhieuBanThuoc ct ON h.maPBT = ct.maPBT " +
	                 "WHERE CAST(h.ngayLap AS DATE) BETWEEN ? AND ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY k.maKH, k.hoTen";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, tuNgay);
	        pstmt.setString(2, denNgay);
	        ResultSet rs = pstmt.executeQuery();

	        int stt = 1;
	        while (rs.next()) {
	            String maKH = rs.getString("maKH");
	            String hoTen = rs.getString("hoTen");
	            int soGiaoDich = rs.getInt("soGiaoDich");
	            double doanhThu = rs.getDouble("doanhThu");
	            double trungBinh = soGiaoDich > 0 ? doanhThu / soGiaoDich : 0;

	            Object[] row = { stt++, maKH, hoTen, soGiaoDich, doanhThu, trungBinh };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo khoảng thời gian: " + e.getMessage());
	    }
	    return data;
	}

	// Thống kê phân bố khách hàng theo độ tuổi
	public Map<String, Double> thongKeTheoDoTuoi() {
		Map<String, Double> result = new HashMap<>();
		String sql = "SELECT DATEDIFF(YEAR, ngaySinh, GETDATE()) AS tuoi, COUNT(*) AS soLuong "
				+ "FROM KhachHang GROUP BY DATEDIFF(YEAR, ngaySinh, GETDATE())";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			int total = 0;
			Map<String, Integer> counts = new HashMap<>();
			while (rs.next()) {
				int tuoi = rs.getInt("tuoi");
				int soLuong = rs.getInt("soLuong");
				String nhomTuoi;
				if (tuoi < 20) {
					nhomTuoi = "<20";
				} else if (tuoi <= 30) {
					nhomTuoi = "20-30";
				} else if (tuoi <= 40) {
					nhomTuoi = "30-40";
				} else {
					nhomTuoi = ">40";
				}

				counts.put(nhomTuoi, counts.getOrDefault(nhomTuoi, 0) + soLuong);
				total += soLuong;
			}

			for (Map.Entry<String, Integer> entry : counts.entrySet()) {
				double percentage = (entry.getValue() * 100.0) / total;
				result.put(entry.getKey(), percentage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo độ tuổi: " + e.getMessage());
		}
		return result;
	}

	// Thống kê phân bố giao dịch theo phương thức thanh toán
	public Map<String, Double> thongKePhuongThucThanhToanKhachHang(String fromDate, String toDate) {
	    Map<String, Double> result = new HashMap<>();
	    String sql = "SELECT h.phuongThucThanhToan, COUNT(*) AS soLuong " +
	                 "FROM KhachHang k JOIN PhieuBanThuoc h ON k.maKH = h.maKH " +
	                 "WHERE CAST(h.ngayLap AS DATE) BETWEEN ? AND ? AND h.trangThai = N'Đã thanh toán' " +
	                 "GROUP BY h.phuongThucThanhToan";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, fromDate);
	        pstmt.setString(2, toDate);
	        ResultSet rs = pstmt.executeQuery();

	        int total = 0;
	        Map<String, Integer> counts = new HashMap<>();
	        while (rs.next()) {
	            String phuongThuc = rs.getString("phuongThucThanhToan");
	            int soLuong = rs.getInt("soLuong");
	            counts.put(phuongThuc, soLuong);
	            total += soLuong;
	        }

	        System.out.println("Tổng số giao dịch (phương thức, " + fromDate + " đến " + toDate + "): " + total);
	        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
	            double percentage = (entry.getValue() * 100.0) / total;
	            result.put(entry.getKey(), percentage);
	            System.out.println("Phương thức: " + entry.getKey() + ", Số lượng: " + entry.getValue() + ", Tỷ lệ: " + percentage + "%");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê phương thức thanh toán: " + e.getMessage());
	    }
	    return result;
	}

	// Get tên bằng mã khách hàng
	public String getTenKHByMaKH(String maKH) {
		String sql = "SELECT hoTen FROM KhachHang WHERE maKH = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, maKH);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("hoTen");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy tên khách hàng: " + e.getMessage());
		}
		return "Không xác định";
	}
}
