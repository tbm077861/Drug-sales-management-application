package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;

public class NhaCungCapDAO {
	public List<Object[]> loadDataToTable(Boolean trangThai) {
		List<Object[]> data = new ArrayList<>();
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn
						.prepareStatement("SELECT ncc.maNCC, ncc.tenNCC, ncc.diaChi, ncc.soDienThoai, ncc.email "
								+ "FROM NhaCungCap ncc " + "WHERE ncc.trangThai = ?")) {

			stmt.setBoolean(1, trangThai);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Object[] row = { rs.getString("maNCC"), rs.getString("tenNCC"), rs.getString("diaChi"),
						rs.getString("soDienThoai"), rs.getString("email") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
		}
		return data;
	}

	public void saveNhaCungCap(boolean isEditing, String maNCC, String tenNCC, String diaChi, String soDienThoai,
			String email) throws ParseException {
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc")) {
			String sql;
			Boolean trangThai = true;
			if (isEditing) {
				// update thông tin nhân viên
				sql = "UPDATE NhaCungCap SET tenNCC=?, diaChi=?, soDienThoai=?, email=?, trangThai=? WHERE maNCC=?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, tenNCC);
					pstmt.setString(2, diaChi);
					pstmt.setString(3, soDienThoai);
					pstmt.setString(4, email);
					pstmt.setBoolean(5, trangThai);
					pstmt.setString(6, maNCC);
					pstmt.executeUpdate();
				}
			} else {
				// thêm mới nhà cung cấp
				sql = "INSERT INTO NhaCungCap (maNCC, tenNCC, diaChi, soDienThoai, email, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, maNCC);
					pstmt.setString(2, tenNCC);
					pstmt.setString(3, diaChi);
					pstmt.setString(4, soDienThoai);
					pstmt.setString(5, email);
					pstmt.setBoolean(6, trangThai);
					pstmt.executeUpdate();
				}
			}
			JOptionPane.showMessageDialog(null, isEditing ? "Cập nhật thành công!" : "Thêm mới thành công!");
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
		}
	}

	// Lấy mã NCC cuối cùng
	public String getLastMaNCC() {
		String sql = "SELECT MAX(maNCC) FROM NhaCungCap";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			if (rs.next()) {
				String lastMaNCC = rs.getString(1);
				if (lastMaNCC != null && !lastMaNCC.isEmpty()) {
					return lastMaNCC;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã nhà cung cấp cuối cùng: " + e.getMessage());
		}
		return "NCC000"; // Default value if no records exist or an error occurs
	}

	public boolean isDuplicateEmail(String email, String maNCC) {
		String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE email = ?";
		if (maNCC != null) {
			sql += " AND maNCC != ?";
		}
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, email);
			if (maNCC != null) {
				pstmt.setString(2, maNCC);
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

	public Map<String, String> getNhaCungCapData() {
		Map<String, String> nhaCungCapData = new HashMap<>();
		String sql = "SELECT maNCC, tenNCC FROM NhaCungCap";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				nhaCungCapData.put(rs.getString("maNCC"), rs.getString("tenNCC"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nhaCungCapData;
	}

	public void ngungHopTac(String maNCC) {
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");

			// Cập nhật trạng thái làm việc (false = đã nghỉ việc)
			String sql = "UPDATE NhaCungCap SET trangThai = 0 WHERE maNCC = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, maNCC);
				int rowsAffected = pstmt.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(null, "Đã cập nhật trạng thái ngừng hợp tác thành công!");
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung cấp với mã: " + maNCC);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái nhà cung cấp: " + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Object[]> searchNhanVien(String maNCC, String tenNCC, String diaChi, String email, Boolean trangThai) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder(
				"SELECT ncc.maNCC, ncc.tenNCC, ncc.diaChi, ncc.soDienThoai, ncc.email, ncc.trangThai "
						+ "FROM NhaCungCap ncc WHERE 1=1");
		List<Object> params = new ArrayList<>();

		if (!maNCC.isEmpty()) {
			sql.append(" AND ncc.maNCC LIKE ?");
			params.add("%" + maNCC + "%");
		}

		if (!tenNCC.isEmpty()) {
			sql.append(" AND ncc.tenNCC LIKE ?");
			params.add("%" + tenNCC + "%");
		}

		if (!diaChi.isEmpty()) {
			sql.append(" AND ncc.diaChi LIKE ?");
			params.add("%" + diaChi + "%");
		}

		if (!email.isEmpty()) {
			sql.append(" AND ncc.email LIKE ?");
			params.add("%" + email + "%");
		}

		if (trangThai != null) {
			sql.append(" AND ncc.trangThai = ?");
			params.add(trangThai ? 1 : 0);
		} else {
			sql.append(" AND ncc.trangThai = 1");
		}

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Object[] row = { rs.getString("maNCC"), rs.getString("tenNCC"), rs.getString("diaChi"),
						rs.getString("soDienThoai"), rs.getString("email") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage());
		}
		return data;
	}

	// Lấy danh sách nhà cung cấp
	public List<Object[]> getAllNhaCungCap() {
		List<Object[]> List = new ArrayList<>();
		String sql = "SELECT * FROM NhaCungCap WHERE trangThai = 1";
		try (Connection connection = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String maNCC = resultSet.getString("maNCC");
				String tenNCC = resultSet.getString("tenNCC");

				String diaChi = resultSet.getString("diaChi");
				String soDienThoai = resultSet.getString("soDienThoai");
				String email = resultSet.getString("email");

				Object[] nv = { maNCC, tenNCC, diaChi, soDienThoai, email };
				List.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return List;
	}

	// Nhà cung cấp quay lại hợp tác
	public void quayLaiHopTac(String maNCC) {
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");

			// Cập nhật trạng thái làm việc (true = đang làm việc)
			String sql = "UPDATE NhaCungCap SET trangThai = 1 WHERE maNCC = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, maNCC);
				int rowsAffected = pstmt.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(null, "Đã cập nhật trạng thái quay lại hợp tác thành công!");
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy nhà cung cấp với mã: " + maNCC);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái nhà cung cấp: " + e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Trạng thái nhà cung cấp
	public boolean getTrangThaiNhaCungCap(String maNCC) {
		String sql = "SELECT trangThai FROM NhaCungCap WHERE maNCC = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, maNCC);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getBoolean("trangThai");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi lấy trạng thái nhân viên: " + e.getMessage());
		}
		return false;
	}

	// Thống kê top K nhà cung cấp nhập nhiều hàng nhất
	public List<Object[]> getTopNhaCungCapByImportQuantity(int topK, String year, String month) {
		List<Object[]> data = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT TOP " + topK
				+ " ncc.maNCC, ncc.tenNCC, COUNT(pn.maPNT) as soGiaoDich, SUM(ctpn.soLuong) as tongSoLuong "
				+ "FROM NhaCungCap ncc " + "LEFT JOIN PhieuNhapThuoc pn ON ncc.maNCC = pn.maNCC "
				+ "LEFT JOIN ChiTietPhieuNhapThuoc ctpn ON pn.maPNT = ctpn.maPNT " + "WHERE pn.ngayNhap IS NOT NULL ");
		List<Object> params = new ArrayList<>();

		// Xử lý điều kiện lọc theo thời gian
		if (month != null && year != null) {
			try {
				Integer.parseInt(month);
				Integer.parseInt(year);
				sql.append(" AND MONTH(pn.ngayNhap) = ? AND YEAR(pn.ngayNhap) = ?");
				params.add(month);
				params.add(year);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Tháng hoặc năm không hợp lệ: " + e.getMessage());
			}
		} else if (year != null) {
			try {
				Integer.parseInt(year);
				sql.append(" AND YEAR(pn.ngayNhap) = ?");
				params.add(year);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Năm không hợp lệ: " + e.getMessage());
			}
		}

		sql.append(" GROUP BY ncc.maNCC, ncc.tenNCC " + "ORDER BY tongSoLuong DESC");

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pstmt.executeQuery();
			int stt = 1;
			while (rs.next()) {
				Object[] row = { stt++, rs.getString("maNCC"), rs.getString("tenNCC"), rs.getInt("soGiaoDich"),
						rs.getInt("tongSoLuong") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thống kê số lượng nhập hàng: " + e.getMessage());
		}
		return data;
	}
	
	// Lấy tất cả nhà cung cấp theo mã và tên
    public List<Object[]> getAllNhaCungCapTheoMaVaTen() {
        List<Object[]> suppliers = new ArrayList<>();
        String sql = "SELECT maNCC, tenNCC FROM NhaCungCap";
        try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] supplier = { rs.getString("maNCC"), rs.getString("tenNCC") };
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách nhà cung cấp: " + e.getMessage());
        }
        return suppliers;
    }
}
