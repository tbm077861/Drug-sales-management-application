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

public class NhanVienDAO {

    public List<Object[]> loadDataToTable(Boolean trangThai) {
        List<Object[]> data = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(
            		 "SELECT nv.maNV, nv.hoTen, nv.ngaySinh, nv.gioiTinh, nv.CCCD, nv.soDienThoai, " +
                             "nv.email, nv.diaChi, nv.chucVu, nv.trinhDo, nv.luong, tk.matKhau " +
                             "FROM NhanVien nv LEFT JOIN TaiKhoan tk ON nv.maNV = tk.maNV " +
                             "WHERE nv.trangThai = ?")) {
        	
        	stmt.setBoolean(1, trangThai);
            ResultSet rs = stmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String ngaySinh = sdf.format(rs.getDate("ngaySinh"));
                float luong = rs.getFloat("Luong");
                String formattedLuong = String.format("%,.0fđ", luong); // Format
                Object[] row = { rs.getString("maNV"), rs.getString("hoTen"), ngaySinh, 
                		rs.getString("gioiTinh"), rs.getString("CCCD"), rs.getString("soDienThoai"),
                        rs.getString("email"), rs.getString("diaChi"), rs.getString("chucVu"),
                        rs.getString("trinhDo"), formattedLuong , rs.getString("matKhau")};
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
        return data;
    }


    public Map<String, String> getNhanVienData() {
        Map<String, String> nhanVienData = new HashMap<>();
        String sql = "SELECT maNV, hoTen FROM NhanVien";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                nhanVienData.put(rs.getString("maNV"), rs.getString("hoTen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nhanVienData;
    }
	public boolean isDuplicateCCCD(String cccd, String maNV) {
	    String sql = "SELECT COUNT(*) FROM NhanVien WHERE CCCD = ?";
	    if (maNV != null) {
	        sql += " AND maNV != ?";
	    }
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, cccd);
	        if (maNV != null) {
	            pstmt.setString(2, maNV);
	        }
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra CCCD: " + e.getMessage());
	    }
	    return false;
	}
	

	public boolean isDuplicateEmail(String email, String maNV) {
	    String sql = "SELECT COUNT(*) FROM NhanVien WHERE email = ?";
	    if (maNV != null) {
	        sql += " AND maNV != ?";
	    }
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        if (maNV != null) {
	            pstmt.setString(2, maNV);
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



    public void saveNhanVien(boolean isEditing, String maNV, String hoTen, String ngaySinh, String gioiTinh, String CCCD, String soDienThoai, String email, String diaChi, String chucVu, String trinhDo, String luongStr, String matKhau) throws ParseException {
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc")) {
            String sql;
            Boolean trangThai = true;
            // chuyển đổi định dạng tiền
            float luong = 0;
            try {
                // Loại bỏ ký tự không phải số trước khi parse
                luong = Float.parseFloat(luongStr.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Lỗi định dạng lương");
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date parsed = sdf.parse(ngaySinh);
			java.sql.Date datesql = new java.sql.Date(parsed.getTime());
            
            if (isEditing) {
      
            	// update thông tin nhân viên
                sql = "UPDATE NhanVien SET hoTen=?, ngaySinh=?, gioiTinh=?, CCCD=?, soDienThoai=?, email=?, diaChi=?, chucVu=?, trinhDo=?, luong=?, trangThai=? WHERE maNV=?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, hoTen);
					pstmt.setDate(2, datesql);
					pstmt.setString(3, gioiTinh);
					pstmt.setString(4, CCCD);
					pstmt.setString(5, soDienThoai);
					pstmt.setString(6, email);
					pstmt.setString(7, diaChi);
					pstmt.setString(8, chucVu);
					pstmt.setString(9, trinhDo);
					pstmt.setDouble(10, luong);
					pstmt.setBoolean(11, trangThai);
					pstmt.setString(12, maNV);
					pstmt.executeUpdate();
                }
                // update thông tin tài khoản nhân viên
                try (PreparedStatement pstmtTK = conn.prepareStatement("UPDATE TaiKhoan SET matKhau=? WHERE maNV=?")) {
                    pstmtTK.setString(1, matKhau);
                    pstmtTK.setString(2, maNV);
                    pstmtTK.executeUpdate();
                }
            }
            else {
            	
            	// thêm mới nhân viên
                sql = "INSERT INTO NhanVien (maNV, hoTen, ngaySinh, gioiTinh, CCCD, soDienThoai, email, diaChi, chucVu, trinhDo, luong, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					pstmt.setString(1, maNV);
					pstmt.setString(2, hoTen);
					pstmt.setDate(3, datesql);
					pstmt.setString(4, gioiTinh);
					pstmt.setString(5, CCCD);
					pstmt.setString(6, soDienThoai);
					pstmt.setString(7, email);
					pstmt.setString(8, diaChi);
					pstmt.setString(9, chucVu);
					pstmt.setString(10, trinhDo);
					pstmt.setDouble(11, luong);
					pstmt.setBoolean(12, trangThai);
					pstmt.executeUpdate();
				}
				
				// thêm mới tài khoản nhân viên
				try (PreparedStatement pstmtTK = conn.prepareStatement("INSERT INTO TaiKhoan (maNV, matKhau) VALUES (?, ?)")) {
					pstmtTK.setString(1, maNV);
					pstmtTK.setString(2, matKhau);
					pstmtTK.executeUpdate();
				}
            }
            JOptionPane.showMessageDialog(null, isEditing ? "Cập nhật thành công!" : "Thêm mới thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        }
    }

    public void thoiViecNhanVien(String maNV) {
        Connection conn = null;
        try {
            conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
            
            // Cập nhật trạng thái làm việc (false = đã nghỉ việc)
            String sql = "UPDATE NhanVien SET trangThai = 0 WHERE maNV = ?";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, maNV);
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Đã cập nhật trạng thái thôi việc thành công!");
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với mã: " + maNV);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái nhân viên: " + e.getMessage());
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
    public List<Object[]> searchNhanVien(String maNV, String hoTen, String cccd, Boolean trangThai, String gioiTinh) {
        List<Object[]> data = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
        		 "SELECT nv.maNV, nv.hoTen, nv.ngaySinh, nv.gioiTinh, nv.CCCD, nv.soDienThoai, " +
        			        "nv.email, nv.diaChi, nv.chucVu, nv.trinhDo, nv.luong, nv.trangThai, tk.matKhau " +
        			        "FROM NhanVien nv LEFT JOIN TaiKhoan tk ON nv.maNV = tk.maNV WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (!maNV.isEmpty()) {
            sql.append(" AND nv.maNV LIKE ?");
            params.add("%" + maNV + "%");
        }
        
        if (!hoTen.isEmpty()) {
            sql.append(" AND nv.hoTen LIKE ?");
            params.add("%" + hoTen + "%");
        }
        
        if (!cccd.isEmpty()) {
            sql.append(" AND nv.CCCD LIKE ?");
            params.add("%" + cccd + "%");
        }
        
		if (trangThai != null) {
			sql.append(" AND nv.trangThai = ?");
			params.add(trangThai ? 1 : 0);
		}
		else {
	        sql.append(" AND nv.trangThai = 1");
	    }
		
		
        if (gioiTinh != null && !gioiTinh.isEmpty()) {
            sql.append(" AND nv.gioiTinh = ?");
            params.add(gioiTinh);
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
                float luong = rs.getFloat("Luong");
                String formattedLuong = String.format("%,.0fđ", luong); // Format
                
                Object[] row = { rs.getString("maNV"), rs.getString("hoTen"), ngaySinh, 
                		rs.getString("gioiTinh"), rs.getString("CCCD"), rs.getString("soDienThoai"),
                        rs.getString("email"), rs.getString("diaChi"), rs.getString("chucVu"),
                        rs.getString("trinhDo"), formattedLuong , rs.getString("matKhau")};
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm: " + e.getMessage());
        }
        return data;
    }
    
    public List<Object[]> getAllNhanVien() {
        List<Object[]> List = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE trangThai = 1";
        try( Connection connection = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
        	PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String maNV = resultSet.getString("maNV");
                String hoTen = resultSet.getString("hoTen");
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String ngaySinh = sdf.format(resultSet.getDate("ngaySinh"));
                
                String gioiTinh = resultSet.getString("gioiTinh");
                String cccd = resultSet.getString("CCCD");
                String soDienThoai = resultSet.getString("soDienThoai");
                String email = resultSet.getString("email");
                String diaChi = resultSet.getString("diaChi");
                String chucVu = resultSet.getString("chucVu");
                String trinhDo = resultSet.getString("trinhDo");
                String luong = resultSet.getString("luong");

                Object[] nv = {maNV, hoTen, ngaySinh, gioiTinh, cccd, soDienThoai, email, diaChi, chucVu, trinhDo, luong};
                List.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List;
    }
    
    public String getLastMaNV() {
        String sql = "SELECT MAX(maNV) FROM NhanVien";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String lastMaNV = rs.getString(1);
                if (lastMaNV != null && !lastMaNV.isEmpty()) {
                    return lastMaNV;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã nhân viên cuối cùng: " + e.getMessage());
        }
        return "NV000"; // Default value if no records exist or an error occurs
    }
    
    // Nhân viên quay lại làm việc
	public void quayLaiLamViec(String maNV) {
		Connection conn = null;
		try {
			conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");

			// Cập nhật trạng thái làm việc (true = đang làm việc)
			String sql = "UPDATE NhanVien SET trangThai = 1 WHERE maNV = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, maNV);
				int rowsAffected = pstmt.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(null, "Đã cập nhật trạng thái quay lại làm việc thành công!");
				} else {
					JOptionPane.showMessageDialog(null, "Không tìm thấy nhân viên với mã: " + maNV);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật trạng thái nhân viên: " + e.getMessage());
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
	
	// Trạng thái nhân viên
	public boolean getTrangThaiNhanVien(String maNV) {
		String sql = "SELECT trangThai FROM NhanVien WHERE maNV = ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, maNV);
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
	
	// Cham Cong
	public List<Object[]> loadDataToNhanVien() {
	    List<Object[]> data = new ArrayList<>();
	    String sql = "SELECT nv.maNV, nv.hoTen, nv.chucVu, llv.maCa " +
	                "FROM NhanVien nv " +
	                "JOIN LichLamViec llv ON nv.maNV = llv.maNV " +
	                "WHERE llv.ngay = CAST(GETDATE() AS DATE)";
	    
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        
	        while (rs.next()) {
	            String maNV = rs.getString("maNV");
	            String hoTen = rs.getString("hoTen");
	            String chucVu = rs.getString("chucVu");
	            String maCa = rs.getString("maCa");
	            Object[] row = { maNV, hoTen, chucVu, maCa };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu nhân viên: " + e.getMessage());
	    }
	    return data;
	}
	
	public List<Object[]> loadDataToChamCong() {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT * FROM ChamCong WHERE ngay = CAST(GETDATE() AS DATE)";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			ResultSet rs = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
			
			while (rs.next()) {
				String ngay = sdf.format(rs.getDate("ngay"));
				String gioVao = sdfTime.format(rs.getTime("gioVao"));
				String gioRa = rs.getTime("gioRa") != null ? sdfTime.format(rs.getTime("gioRa")) : "Chưa ra ca";
				
				Object[] row = {rs.getString("maCC"), rs.getString("maNV"), ngay, rs.getString("maCa"),
						gioVao, gioRa, rs.getString("trangThai"), rs.getString("ghiChu") };
				
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu chấm công: " + e.getMessage());
		}
		return data;
	}
	
	public List<Object[]> searchNhanVienChamCong(String maNV) {
		List<Object[]> data = new ArrayList<>();
		String sql = "SELECT maNV, hoTen, chucVu FROM NhanVien WHERE maNV LIKE ?";
		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, "%" + maNV + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String maNVCC = rs.getString("maNV");
				String hoTen = rs.getString("hoTen");
				String chucVu = rs.getString("chucVu");
				Object[] row = { maNVCC, hoTen, chucVu };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
		}
		return data;
	}
	
	public List<Object[]> searchChamCong(String maCa, String ngayChamCong) {
	    List<Object[]> data = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM ChamCong WHERE 1=1");
	    List<Object> params = new ArrayList<>();
	    
		if (maCa != null && !maCa.isEmpty() && !maCa.equals("Tất cả")) {
			sql.append(" AND maCa = ?");
			params.add(maCa);
		}
		if (ngayChamCong != null && !ngayChamCong.trim().isEmpty()) {
			sql.append(" AND ngay = ?");
			params.add(ngayChamCong);
		}

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(i + 1, params.get(i));
			}

			ResultSet rs = pstmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

			while (rs.next()) {
				String ngay = sdf.format(rs.getDate("ngay"));
				String gioVao = sdfTime.format(rs.getTime("gioVao"));
				String gioRa = rs.getTime("gioRa") != null ? sdfTime.format(rs.getTime("gioRa")) : "Chưa ra ca";

				Object[] row = { rs.getString("maCC"), rs.getString("maNV"), ngay, rs.getString("maCa"), gioVao, gioRa,
						rs.getString("trangThai"), rs.getString("ghiChu") };
				data.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm chấm công: " + e.getMessage());
		}
		return data;
	}
	
	public String getLastMaChamCong() {
        String sql = "SELECT MAX(maCC) FROM ChamCong";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                String lastMaCC = rs.getString(1);
                if (lastMaCC != null && !lastMaCC.isEmpty()) {
                    return lastMaCC;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã nhân viên cuối cùng: " + e.getMessage());
        }
        return "CC000"; // Default value if no records exist or an error occurs
    }
	
	public String getGioBatDauCaLam(String maCa) throws SQLException {
	    String sql = "SELECT gioBatDau FROM CaLamViec WHERE maCa = ?";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, maCa);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            // Lấy chuỗi giờ (HH:mm:ss) từ cột gioBatDau
	            String gioBatDau = rs.getString("gioBatDau");
	            // Cắt bỏ phần mili giây (VD: 07:00:00.0000000 -> 07:00:00)
	            return gioBatDau.split("\\.")[0];
	        }
	        return null; // Nếu không tìm thấy ca
	    }
	}
	
	public boolean chamCongVao(String maChamCong, String maNV, String ngayChamCong, String maCa, String gioVao, String gioRa, String trangThai, String ghiChu) throws SQLException, ParseException {
	    String sql = "INSERT INTO ChamCong (maCC, maNV, ngay, maCa, gioVao, gioRa, trangThai, ghiChu) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    java.util.Date parsed = sdf.parse(ngayChamCong);
		java.sql.Date datesql = new java.sql.Date(parsed.getTime());
	    
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, maChamCong);
	        stmt.setString(2, maNV);
	        stmt.setDate(3, datesql);
	        stmt.setString(4, maCa);
	        stmt.setString(5, gioVao);
	        stmt.setString(6, gioRa);
	        stmt.setString(7, trangThai);
	        stmt.setString(8, ghiChu);
	        int rowsAffected = stmt.executeUpdate();
	        
	        return rowsAffected > 0;
	    }
	}
	
	public boolean checkChamCongVao(String maNV, String maCa) throws SQLException {
	    String sql = "SELECT * FROM ChamCong WHERE maNV = ? AND ngay = CAST(GETDATE() AS DATE)";
	    
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setString(1, maNV);
	        ResultSet rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            // Đã có bản ghi chấm công hôm nay
	            if (rs.getTime("gioVao") != null) {
	                String maCaDaCham = rs.getString("maCa");
	                if (maCaDaCham.equals(maCa)) {
	                    return true; // Đã chấm công vào với ca này
	                } else {
	                    throw new SQLException("Nhân viên đã chấm công vào với ca " + maCaDaCham + " hôm nay!");
	                }
	            }
	        }
	        return false; // Chưa chấm công vào
	    }
	}
	
	public boolean chamCongRa(String maCC, String gioRa) {
	    String sql = "UPDATE ChamCong SET gioRa = ?, trangThai = ? WHERE maCC = ?";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, gioRa);
	        stmt.setString(2, "Đã chấm công");
	        stmt.setString(3, maCC);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật chấm công ra: " + e.getMessage());
	        return false;
	    }
	}
	
		
	
	
	// Thống kê top K nhân viên theo doanh số
	public List<Object[]> getTopNhanVienByRevenue(int topK, String fromDate, String toDate, String year, String month) {
	    List<Object[]> data = new ArrayList<>();
	    StringBuilder sql = new StringBuilder(
	        "SELECT TOP " + topK + " nv.maNV, nv.hoTen, COUNT(DISTINCT hd.maPBT) as soGiaoDich, " +
	        "SUM(ct.soLuong * ct.donGiaBan) as doanhThu " +
	        "FROM NhanVien nv " +
	        "LEFT JOIN PhieuBanThuoc hd ON nv.maNV = hd.maNV " +
	        "LEFT JOIN ChiTietPhieuBanThuoc ct ON hd.maPBT = ct.maPBT " +
	        "WHERE hd.trangThai = N'Đã thanh toán' "
	    );
	    List<Object> params = new ArrayList<>();

	    // Xử lý điều kiện lọc theo thời gian
	    if (fromDate != null && toDate != null) {
	        sql.append(" AND hd.ngayLap BETWEEN ? AND ?");
	        params.add(fromDate);
	        params.add(toDate);
	    } else if (month != null && year != null) {
	        try {
	            Integer.parseInt(month);
	            Integer.parseInt(year);
	            sql.append(" AND MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ?");
	            params.add(month);
	            params.add(year);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Tháng hoặc năm không hợp lệ: " + e.getMessage());
	        }
	    } else if (year != null) {
	        try {
	            Integer.parseInt(year);
	            sql.append(" AND YEAR(hd.ngayLap) = ?");
	            params.add(year);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Năm không hợp lệ: " + e.getMessage());
	        }
	    }

	    sql.append(" GROUP BY nv.maNV, nv.hoTen " +
	               "ORDER BY doanhThu DESC");

	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
	        
	        for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = pstmt.executeQuery();
	        int stt = 1;
	        while (rs.next()) {
	            double doanhThu = rs.getDouble("doanhThu");
	            double trungBinh = rs.getInt("soGiaoDich") > 0 ? doanhThu / rs.getInt("soGiaoDich") : 0;
	            
	            Object[] row = {
	                stt++,
	                rs.getString("maNV"),
	                rs.getString("hoTen"),
	                rs.getInt("soGiaoDich"),
	                doanhThu,
	                trungBinh
	            };
	            data.add(row);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi thống kê doanh số: " + e.getMessage());
	    }
	    return data;
	}
	
	// Thống kê số ngày làm việc của nhân viên
    public List<Object[]> getWorkingDaysStats(String fromDate, String toDate, String year, String month) {
        List<Object[]> data = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT nv.maNV, nv.hoTen, COUNT(DISTINCT CAST(hd.ngayLap AS DATE)) as soNgayLam " +
            "FROM NhanVien nv " +
            "LEFT JOIN PhieuBanThuoc hd ON nv.maNV = hd.maNV " +
            "WHERE hd.ngayLap IS NOT NULL "
        );
        List<Object> params = new ArrayList<>();

        // Xử lý điều kiện lọc theo thời gian
        if (fromDate != null && toDate != null) {
            sql.append(" AND hd.ngayLap BETWEEN ? AND ?");
            params.add(fromDate);
            params.add(toDate);
        } else if (month != null && year != null) {
            try {
                Integer.parseInt(month);
                Integer.parseInt(year);
                sql.append(" AND MONTH(hd.ngayLap) = ? AND YEAR(hd.ngayLap) = ?");
                params.add(month);
                params.add(year);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Tháng hoặc năm không hợp lệ: " + e.getMessage());
            }
        } else if (year != null) {
            try {
                Integer.parseInt(year);
                sql.append(" AND YEAR(hd.ngayLap) = ?");
                params.add(year);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Năm không hợp lệ: " + e.getMessage());
            }
        }

        sql.append(" GROUP BY nv.maNV, nv.hoTen " +
                   "ORDER BY soNgayLam DESC");

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = pstmt.executeQuery();
            int stt = 1;
            while (rs.next()) {
                Object[] row = {
                    stt++,
                    rs.getString("maNV"),
                    rs.getString("hoTen"),
                    rs.getInt("soNgayLam")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thống kê ngày làm: " + e.getMessage());
        }
        return data;
    }
}
