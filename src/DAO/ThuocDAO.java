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

import connectDB.ConnectDB;

public class ThuocDAO {

	public List<Object[]> loadDataToTable() {
		List<Object[]> data = new ArrayList<Object[]>();

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement("SELECT * FROM Thuoc")) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String hsd = sdf.format(rs.getDate("hanSuDung"));
				String formatGiaNhap = String.format("%,.0fđ", rs.getFloat("donGiaNhap"));
				String formatGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						formatGiaNhap, formatGiaBan, hsd, rs.getString("hamLuong"), rs.getInt("soLuongTon"),
						rs.getInt("soLuongThucTe") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public boolean isDuplicateMaThuoc(String maThuoc) {
		String sql = "SELECT COUNT(*) FROM Thuoc WHERE maThuoc = ?";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, maThuoc);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean themThuoc(String maThuoc, String tenThuoc, String donViTinh, String donGiaNhap, String donGiaBan,
			String hanSuDung, String hamLuong, String soLuongTon, String soLuongThucTe) throws ParseException {
		String sql = "INSERT INTO Thuoc VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = sdf.parse(hanSuDung);
		java.sql.Date hsdsql = new java.sql.Date(parsed.getTime());

		float Nhap = Float.parseFloat(donGiaNhap.replaceAll("[^\\d.]", ""));
		float Ban = Float.parseFloat(donGiaBan.replaceAll("[^\\d.]", ""));

		int SLTon = Integer.parseInt(soLuongTon);
		int SLThucTe = Integer.parseInt(soLuongThucTe);

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, maThuoc);
			stmt.setString(2, tenThuoc);
			stmt.setString(3, donViTinh);
			stmt.setDouble(4, Nhap);
			stmt.setDouble(5, Ban);
			stmt.setDate(6, hsdsql);
			stmt.setString(7, hamLuong);
			stmt.setInt(8, SLTon);
			stmt.setInt(9, SLThucTe);
			stmt.executeUpdate();

//			JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Lỗi khi thêm dữ liệu: " + e.getMessage());
			return false;
		}
	}

	public boolean capNhatThuoc(String maThuoc, String tenThuoc, String donViTinh, String donGiaNhap, String donGiaBan,
			String hanSuDung, String hamLuong, String soLuongTon, String soLuongThucTe) throws ParseException {

		String sql = "UPDATE Thuoc SET tenThuoc=?, donViTinh=?, donGiaNhap=?, donGiaBan=?, hanSuDung=?, hamLuong=?, soLuongTon=?, soLuongThucTe=? WHERE maThuoc=?";

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = sdf.parse(hanSuDung);
		java.sql.Date hsdsql = new java.sql.Date(parsed.getTime());

		float Nhap = Float.parseFloat(donGiaNhap.replaceAll("[^\\d.]", ""));
		float Ban = Float.parseFloat(donGiaBan.replaceAll("[^\\d.]", ""));

		int SLTon = Integer.parseInt(soLuongTon);
		int SLThucTe = Integer.parseInt(soLuongThucTe);

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, tenThuoc);
			stmt.setString(2, donViTinh);
			stmt.setDouble(3, Nhap);
			stmt.setDouble(4, Ban);
			stmt.setDate(5, hsdsql);
			stmt.setString(6, hamLuong);
			stmt.setInt(7, SLTon);
			stmt.setInt(8, SLThucTe);
			stmt.setString(9, maThuoc);
			stmt.executeUpdate();

//			JOptionPane.showMessageDialog(null, "Cập nhật dữ liệu thành công!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu: " + e.getMessage());
			return false;
		}
	}

	public void xoaThuoc(String maThuoc) {
		String sql = "DELETE FROM Thuoc WHERE maThuoc=?";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setString(1, maThuoc);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Xóa dữ liệu thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi xóa dữ liệu: " + e.getMessage());
		}
	}

	// Tìm kiếm thuốc theo mã thuốc, tên thuốc và số lượng tồn
	public List<Object[]> timKiemThuoc(String maThuoc, String tenThuoc, String soLuongTon) {
		List<Object[]> data = new ArrayList<Object[]>();
		StringBuilder sql = new StringBuilder("SELECT * FROM Thuoc WHERE 1=1");
		List<Object> params = new ArrayList<Object>();

		if (!maThuoc.isEmpty()) {
			sql.append(" AND maThuoc LIKE ?");
			params.add("%" + maThuoc + "%");
		}
		if (!tenThuoc.isEmpty()) {
			sql.append(" AND tenThuoc LIKE ?");
			params.add("%" + tenThuoc + "%");
		}
		if (!soLuongTon.isEmpty()) {
			sql.append(" AND soLuongTon = ?");
			params.add(Integer.parseInt(soLuongTon));
		}

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String hsd = sdf.format(rs.getDate("hanSuDung"));
				String formatGiaNhap = String.format("%,.0fđ", rs.getFloat("donGiaNhap"));
				String formatGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						formatGiaNhap, formatGiaBan, hsd, rs.getString("hamLuong"), rs.getInt("soLuongTon"),
						rs.getInt("soLuongThucTe") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu: " + e.getMessage());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Số lượng tồn phải là số nguyên!");
		}
		return data;
	}

	public List<Object[]> getAllThuoc() {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "SELECT * FROM Thuoc";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				String hsd = sdf.format(rs.getDate("hanSuDung"));
				String formatGiaNhap = String.format("%,.0fđ", rs.getFloat("donGiaNhap"));
				String formatGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						formatGiaNhap, formatGiaBan, hsd, rs.getString("hamLuong"), rs.getInt("soLuongTon"),
						rs.getInt("soLuongThucTe") };
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return list;
	}

	public String getLastMaThuoc() {
		String sql = "SELECT MAX(maThuoc) FROM Thuoc";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				String lastMaThuoc = rs.getString(1);
				if (lastMaThuoc != null && !lastMaThuoc.isEmpty()) {
					return lastMaThuoc;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã thuốc cuối cùng: " + e.getMessage());
		}
		return "T000";
	}

	public List<Object[]> getDanhSachGia() {
		List<Object[]> data = new ArrayList<Object[]>();

		try {
			String sql = "SELECT maThuoc, tenThuoc, donGiaNhap, donGiaBan FROM Thuoc";
			Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String donGiaNhap = String.format("%,.0fđ", rs.getFloat("donGiaNhap"));
				String donGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), donGiaNhap, donGiaBan };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public boolean updatePrice(String maThuoc, String donGiaNhap, String donGiaBan) {
		String sql = "UPDATE Thuoc SET donGiaNhap=?, donGiaBan=? WHERE maThuoc=?";
		float Nhap = Float.parseFloat(donGiaNhap.replaceAll("[^\\d.]", ""));
		float Ban = Float.parseFloat(donGiaBan.replaceAll("[^\\d.]", ""));

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setDouble(1, Nhap);
			stmt.setDouble(2, Ban);
			stmt.setString(3, maThuoc);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cập nhật giá thành công!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật giá: " + e.getMessage(), "Lỗi",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	// Tìm kiếm thuốc theo mã thuốc hoặc tên thuốc
	public List<Object[]> timKiemThuoc(String maThuoc, String tenThuoc) {
		List<Object[]> data = new ArrayList<Object[]>();
		StringBuilder sql = new StringBuilder("SELECT * FROM Thuoc WHERE 1=1");
		List<Object> params = new ArrayList<Object>();

		if (!maThuoc.isEmpty()) {
			sql.append(" AND maThuoc LIKE ?");
			params.add("%" + maThuoc + "%");
		}
		if (!tenThuoc.isEmpty()) {
			sql.append(" AND tenThuoc LIKE ?");
			params.add("%" + tenThuoc + "%");
		}

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String formatGiaNhap = String.format("%,.0fđ", rs.getFloat("donGiaNhap"));
				String formatGiaBan = String.format("%,.0fđ", rs.getFloat("donGiaBan"));

				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), formatGiaNhap, formatGiaBan };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public List<Object[]> getDanhSachKiemKho() {
		List<Object[]> list = new ArrayList<Object[]>();
		String sql = "SELECT * FROM Thuoc";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						rs.getInt("soLuongTon"), rs.getInt("soLuongThucTe"),
						rs.getInt("soLuongThucTe") - rs.getInt("soLuongTon") };
				list.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return list;
	}

	public boolean capNhatSoLuong(String maThuoc, int soLuongTon, int soLuongThucTe) {
		String sql = "UPDATE Thuoc SET soLuongTon=?, soLuongThucTe=? WHERE maThuoc=?";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {

			stmt.setInt(1, soLuongTon);
			stmt.setInt(2, soLuongThucTe);
			stmt.setString(3, maThuoc);
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cập nhật số lượng thực tế thành công!", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng thực tế: " + e.getMessage());
			return false;
		}
	}

	public List<Object[]> timKiemChenhLechThuoc(String maThuoc, String tenThuoc, String chechLech) {
		List<Object[]> data = new ArrayList<Object[]>();
		StringBuilder sql = new StringBuilder("SELECT * FROM Thuoc WHERE 1=1");
		List<Object> params = new ArrayList<Object>();

		if (!maThuoc.isEmpty()) {
			sql.append(" AND maThuoc LIKE ?");
			params.add("%" + maThuoc + "%");
		}
		if (!tenThuoc.isEmpty()) {
			sql.append(" AND tenThuoc LIKE ?");
			params.add("%" + tenThuoc + "%");
		}

		if (!chechLech.isEmpty()) {
			sql.append(" AND soLuongThucTe - soLuongTon = ?");
			params.add(Integer.parseInt(chechLech));
		}
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] row = { rs.getString("maThuoc"), rs.getString("tenThuoc"), rs.getString("donViTinh"),
						rs.getInt("soLuongTon"), rs.getInt("soLuongThucTe"),
						rs.getInt("soLuongThucTe") - rs.getInt("soLuongTon") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm dữ liệu: " + e.getMessage());
		}
		return data;
	}

	// Thống kê thuốc theo tháng
	public List<Object[]> thongKeTheoThang(int nam, int thang) {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT t.maThuoc, t.tenThuoc, SUM(ct.soLuong) AS soLuongBan, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu, t.soLuongTon "
				+ "FROM Thuoc t LEFT JOIN ChiTietPhieuBanThuoc ct ON t.maThuoc = ct.maThuoc "
				+ "JOIN PhieuBanThuoc h ON ct.maPBT = h.maPBT " + "WHERE YEAR(h.ngayLap) = ? AND MONTH(h.ngayLap) = ? "
				+ "GROUP BY t.maThuoc, t.tenThuoc, t.soLuongTon";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, nam);
			stmt.setInt(2, thang);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maThuoc = rs.getString("maThuoc");
				String tenThuoc = rs.getString("tenThuoc");
				int soLuongBan = rs.getInt("soLuongBan");
				double doanhThu = rs.getDouble("doanhThu");
				int tonKho = rs.getInt("soLuongTon");

				Object[] row = { maThuoc, tenThuoc, soLuongBan, doanhThu, tonKho };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo tháng: " + e.getMessage());
		}
		return data;
	}

	// Thống kê thuốc theo năm
	public List<Object[]> thongKeTheoNam(int nam) {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT t.maThuoc, t.tenThuoc, SUM(ct.soLuong) AS soLuongBan, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu, t.soLuongTon "
				+ "FROM Thuoc t LEFT JOIN ChiTietPhieuBanThuoc ct ON t.maThuoc = ct.maThuoc "
				+ "JOIN PhieuBanThuoc h ON ct.maPBT = h.maPBT " + "WHERE YEAR(h.ngayLap) = ? "
				+ "GROUP BY t.maThuoc, t.tenThuoc, t.soLuongTon";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, nam);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maThuoc = rs.getString("maThuoc");
				String tenThuoc = rs.getString("tenThuoc");
				int soLuongBan = rs.getInt("soLuongBan");
				double doanhThu = rs.getDouble("doanhThu");
				int tonKho = rs.getInt("soLuongTon");

				Object[] row = { maThuoc, tenThuoc, soLuongBan, doanhThu, tonKho };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo năm: " + e.getMessage());
		}
		return data;
	}

	// Thống kê thuốc theo khoảng thời gian
	public List<Object[]> thongKeTheoKhoangThoiGian(String tuNgay, String denNgay) {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT t.maThuoc, t.tenThuoc, SUM(ct.soLuong) AS soLuongBan, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu, t.soLuongTon "
				+ "FROM Thuoc t LEFT JOIN ChiTietPhieuBanThuoc ct ON t.maThuoc = ct.maThuoc "
				+ "JOIN PhieuBanThuoc h ON ct.maPBT = h.maPBT " + "WHERE CAST(h.ngayLap AS DATE) BETWEEN ? AND ? "
				+ "GROUP BY t.maThuoc, t.tenThuoc, t.soLuongTon";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, tuNgay);
			stmt.setString(2, denNgay);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String maThuoc = rs.getString("maThuoc");
				String tenThuoc = rs.getString("tenThuoc");
				int soLuongBan = rs.getInt("soLuongBan");
				double doanhThu = rs.getDouble("doanhThu");
				int tonKho = rs.getInt("soLuongTon");

				Object[] row = { maThuoc, tenThuoc, soLuongBan, doanhThu, tonKho };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê theo khoảng thời gian: " + e.getMessage());
		}
		return data;
	}

	// Thống kê doanh thu theo loại thuốc
	public Map<String, Double> thongKeDoanhThuTheoLoaiThuoc(String fromDate, String toDate) {
		Map<String, Double> result = new HashMap<>();
		String sql = "SELECT t.tenThuoc, SUM(ct.soLuong * ct.donGiaBan) AS doanhThu "
				+ "FROM Thuoc t JOIN ChiTietPhieuBanThuoc ct ON t.maThuoc = ct.maThuoc " + "JOIN PhieuBanThuoc h ON ct.maPBT = h.maPBT "
				+ "WHERE CAST(h.ngayLap AS DATE) BETWEEN ? AND ? " + "GROUP BY t.tenThuoc";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, fromDate);
			stmt.setString(2, toDate);
			ResultSet rs = stmt.executeQuery();

			double totalDoanhThu = 0;
			Map<String, Double> doanhThuMap = new HashMap<>();
			while (rs.next()) {
				String tenThuoc = rs.getString("tenThuoc");
				double doanhThu = rs.getDouble("doanhThu");
				doanhThuMap.put(tenThuoc, doanhThu);
				totalDoanhThu += doanhThu;
			}

			for (Map.Entry<String, Double> entry : doanhThuMap.entrySet()) {
				double percentage = (entry.getValue() * 100.0) / totalDoanhThu;
				result.put(entry.getKey(), percentage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê doanh thu theo loại thuốc: " + e.getMessage());
		}
		return result;
	}

	// Thống kê số lượng bán theo loại thuốc
	public Map<String, Double> thongKeSoLuongBanTheoLoaiThuoc(String fromDate, String toDate) {
		Map<String, Double> result = new HashMap<>();
		String sql = "SELECT t.tenThuoc, SUM(ct.soLuong) AS soLuongBan "
				+ "FROM Thuoc t JOIN ChiTietPhieuBanThuoc ct ON t.maThuoc = ct.maThuoc " + "JOIN PhieuBanThuoc h ON ct.maPBT = h.maPBT "
				+ "WHERE CAST(h.ngayLap AS DATE) BETWEEN ? AND ? " + "GROUP BY t.tenThuoc";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, fromDate);
			stmt.setString(2, toDate);
			ResultSet rs = stmt.executeQuery();

			double totalSoLuong = 0;
			Map<String, Double> soLuongMap = new HashMap<>();
			while (rs.next()) {
				String tenThuoc = rs.getString("tenThuoc");
				double soLuongBan = rs.getDouble("soLuongBan");
				soLuongMap.put(tenThuoc, soLuongBan);
				totalSoLuong += soLuongBan;
			}

			for (Map.Entry<String, Double> entry : soLuongMap.entrySet()) {
				double percentage = (entry.getValue() * 100.0) / totalSoLuong;
				result.put(entry.getKey(), percentage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê số lượng bán theo loại thuốc: " + e.getMessage());
		}
		return result;
	}

	// Lưu giao dịch nhập thuốc
	public boolean saveTransaction(String maPN, String ngayNhap, String maNCC, String hinhThucThanhToan,
            double tongTien, Object[][] chiTiet) {
        Connection con = null;
        PreparedStatement stmtPhieuNhap = null;
        PreparedStatement stmtChiTiet = null;
        PreparedStatement stmtUpdateThuoc = null;
        try {
            con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
            con.setAutoCommit(false);

            // Sửa câu lệnh SQL để khớp với cấu trúc bảng PhieuNhap
            // Sử dụng hinhThucThanhToan làm giá trị cho ghiChu, maNV để NULL
            String sqlPhieuNhap = "INSERT INTO PhieuNhap (maPN, ngayNhap, maNV, maNCC, ghiChu) VALUES (?, ?, NULL, ?, ?)";
            stmtPhieuNhap = con.prepareStatement(sqlPhieuNhap);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date parsedDate = sdf.parse(ngayNhap);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            stmtPhieuNhap.setString(1, maPN);
            stmtPhieuNhap.setDate(2, sqlDate);
            stmtPhieuNhap.setString(3, maNCC);
            stmtPhieuNhap.setString(4, hinhThucThanhToan); // Ánh xạ hinhThucThanhToan vào ghiChu
            stmtPhieuNhap.executeUpdate();

            // Lưu chi tiết phiếu nhập
            String sqlChiTiet = "INSERT INTO ChiTietPhieuNhap (maPN, maThuoc, soLuong, donGiaNhap) VALUES (?, ?, ?, ?)";
            stmtChiTiet = con.prepareStatement(sqlChiTiet);

            // Cập nhật số lượng tồn kho
            String sqlUpdateThuoc = "UPDATE Thuoc SET soLuongTon = soLuongTon + ?, soLuongThucTe = soLuongThucTe + ? WHERE maThuoc = ?";
            stmtUpdateThuoc = con.prepareStatement(sqlUpdateThuoc);

            for (Object[] ct : chiTiet) {
                String maThuoc = (String) ct[0];
                int soLuong = (int) ct[1];
                double donGia = (double) ct[2];

                // Lưu chi tiết
                stmtChiTiet.setString(1, maPN);
                stmtChiTiet.setString(2, maThuoc);
                stmtChiTiet.setInt(3, soLuong);
                stmtChiTiet.setDouble(4, donGia);
                stmtChiTiet.executeUpdate();

                // Cập nhật tồn kho
                stmtUpdateThuoc.setInt(1, soLuong);
                stmtUpdateThuoc.setInt(2, soLuong);
                stmtUpdateThuoc.setString(3, maThuoc);
                stmtUpdateThuoc.executeUpdate();
            }

            con.commit();
            return true;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu giao dịch: " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmtPhieuNhap != null) stmtPhieuNhap.close();
                if (stmtChiTiet != null) stmtChiTiet.close();
                if (stmtUpdateThuoc != null) stmtUpdateThuoc.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	// Lấy mã phiếu nhập cuối cùng
	public String getLastMaPhieuNhap() {
		String sql = "SELECT MAX(maPN) FROM PhieuNhap";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			if (rs.next()) {
				String lastMaPN = rs.getString(1);
				if (lastMaPN != null && !lastMaPN.isEmpty()) {
					return lastMaPN;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã phiếu nhập cuối cùng: " + e.getMessage());
		}
		return "PN000";
	}
	
	public double getDonGiaNhap(String maThuoc) {
        String sql = "SELECT donGiaNhap FROM Thuoc WHERE maThuoc = ?";
        try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maThuoc);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("donGiaNhap");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy đơn giá nhập: " + e.getMessage());
        }
        return 0.0; // Return 0 if not found or error occurs
    }
}