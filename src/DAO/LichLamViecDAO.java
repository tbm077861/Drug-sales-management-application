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

public class LichLamViecDAO {
	
	// Load data to table
	public List<Object[]> loadDataToTable() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT maLLV, maNV, ngay, maCa, ghiChu FROM LichLamViec";

        try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String formattedDate = sdf.format(rs.getDate("ngay"));
                Object[] row = {
                    rs.getString("maLLV"),
                    rs.getString("maNV"),
                    formattedDate,
                    rs.getString("maCa"),
                    rs.getString("ghiChu")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
	// Lây mã lich làm việc cuối
	public String getLastMaLich() {
		String sql = "SELECT MAX(maLLV) FROM LichLamViec";
	    try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = con.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        
	        if (rs.next()) {
	            String lastMaLich = rs.getString(1);
	            if (lastMaLich != null && !lastMaLich.isEmpty()) {
	                return lastMaLich;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi lấy mã lịch cuối cùng: " + e.getMessage());
	    }
	    return "LLV000";
	}
	// Kiểm tra trùng mã lịch
	public boolean isDuplicateMaLich(String maLich) {
		String sql = "SELECT COUNT(*) FROM LichLamViec WHERE maLLV = ?";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maLich);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi kiểm tra mã lịch: " + e.getMessage());
		}
		return false;
	}
	
	// Thêm lịch làm việc
	public boolean addLichLamViec(String maLLV, String maNV, String ngay, String maCa, String ghiChu) throws ParseException{
		String sql = "INSERT INTO LichLamViec VALUES (?, ?, ?, ?, ?)";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date parsed = sdf.parse(ngay);
		java.sql.Date day = new java.sql.Date(parsed.getTime());
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
            PreparedStatement stmt = con.prepareStatement(sql)) {
        	
            stmt.setString(1, maLLV);
            stmt.setString(2, maNV);
            stmt.setDate(3, day);
            stmt.setString(4, maCa);
            stmt.setString(5, ghiChu);
            return stmt.executeUpdate() > 0;
            
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi thêm lịch làm việc: " + e.getMessage());
        }
        return false;
    }
	// Cập nhật lịch làm việc
	public boolean updateLichLamViec(String maLLV, String maNV, String ngay, String maCa, String ghiChu) {
	    String sql = "UPDATE LichLamViec SET maNV = ?, ngay = ?, maCa = ?, ghiChu = ? WHERE maLLV = ?";
	    try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        // Convert ngay from dd/MM/yyyy to yyyy-MM-dd
	        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
	        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedNgay = outputFormat.format(inputFormat.parse(ngay));

	        stmt.setString(1, maNV);
	        stmt.setDate(2, java.sql.Date.valueOf(formattedNgay));
	        stmt.setString(3, maCa);
	        stmt.setString(4, ghiChu);
	        stmt.setString(5, maLLV);

	        return stmt.executeUpdate() > 0;
	    } catch (ParseException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi định dạng ngày: " + e.getMessage());
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật lịch làm việc: " + e.getMessage());
	    }
	    return false;
	}
	// Xóa lịch làm việc
	public boolean deleteLichLamViec(String maLLV) {
		String sql = "DELETE FROM LichLamViec WHERE maLLV = ?";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, maLLV);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi xóa lịch làm việc: " + e.getMessage());
		}
		return false;
	}
	// Tìm kiếm lịch làm việc theo mã lịch, mã nhân viên, ngày
	public List<Object[]> searchLichLamViec(String maLLV, String maNV, String ngay) {
	    // Danh sách chứa kết quả, mỗi phần tử là một mảng đối tượng với thông tin lịch làm việc
	    List<Object[]> data = new ArrayList<>();

	    // Khởi tạo câu truy vấn cơ bản
	    StringBuilder sql = new StringBuilder("SELECT * FROM LichLamViec WHERE 1=1");
	    // Danh sách chứa các tham số được truyền vào PreparedStatement
	    List<Object> params = new ArrayList<>();

	    // Nếu maLLV không rỗng, thêm điều kiện tìm kiếm theo kiểu LIKE
	    if (!maLLV.isEmpty()) {
	        sql.append(" AND maLLV LIKE ?");
	        params.add("%" + maLLV + "%");
	    }
	    
	    // Nếu maNV không rỗng, thêm điều kiện tìm kiếm theo kiểu LIKE
	    if (!maNV.isEmpty()) {
	        sql.append(" AND maNV LIKE ?");
	        params.add("%" + maNV + "%");
	    }
	    
	    // Nếu ngày không rỗng, thêm điều kiện so sánh chính xác (vì kiểu dữ liệu của cột là date)
	    if (!ngay.isEmpty()) {
	        sql.append(" AND ngay = ?");
	        try {
	            // Chuyển đổi chuỗi ngày sang java.util.Date theo định dạng dd/MM/yyyy
	            java.util.Date parsedDate = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(ngay);
	            // Chuyển java.util.Date thành java.sql.Date để truy vấn
	            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
	            params.add(sqlDate);
	        } catch (ParseException e) {
	            // Nếu có lỗi trong việc chuyển đổi ngày, hiển thị thông báo lỗi
	            e.printStackTrace();
	            javax.swing.JOptionPane.showMessageDialog(null, "Định dạng ngày không hợp lệ!");
	        }
	    }

	    // Kết nối đến cơ sở dữ liệu và thiết lập PreparedStatement
	    try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = con.prepareStatement(sql.toString())) {

	        // Gán giá trị cho các placeholder trong PreparedStatement
	        for (int i = 0; i < params.size(); i++) {
	            stmt.setObject(i + 1, params.get(i));
	        }

	        // Thực thi truy vấn và nhận kết quả trả về
	        ResultSet rs = stmt.executeQuery();
	        // Định dạng ngày theo định dạng dd/MM/yyyy
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

	        // Duyệt qua kết quả trả về và đóng gói dữ liệu vào mảng đối tượng
	        while (rs.next()) {
	            String maLichLamViec = rs.getString("maLLV");
	            String maNhanVien = rs.getString("maNV");
	            // Chuyển đổi giá trị của cột ngay sang định dạng chuỗi
	            String ngayLamViec = sdf.format(rs.getDate("ngay"));
	            String maCa = rs.getString("maCa");
	            String ghiChu = rs.getString("ghiChu");

	            // Tạo mảng đối tượng lưu trữ thông tin của một dòng dữ liệu
	            Object[] row = {maLichLamViec, maNhanVien, ngayLamViec, maCa, ghiChu};
	            data.add(row);
	        }
	    } catch (SQLException ex) {
	        // Nếu có lỗi xảy ra trong quá trình truy vấn dữ liệu, in ra console và hiển thị thông báo cho người dùng
	        ex.printStackTrace();
	        javax.swing.JOptionPane.showMessageDialog(null, "Lỗi khi tìm kiếm lịch làm việc: " + ex.getMessage());
	    }
	    // Trả về danh sách kết quả tìm kiếm
	    return data;
	}



	


}
