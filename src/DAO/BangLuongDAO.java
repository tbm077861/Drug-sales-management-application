package DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import connectDB.ConnectDB;



public class BangLuongDAO {
	
	private static final String Database = null;

	// Lấy danh sách nhân viên từ database
	public List<Object[]> getAllEmployees() {
        List<Object[]> data = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(
            		 "SELECT nv.maNV, nv.hoTen, nv.ngaySinh, nv.gioiTinh, nv.CCCD, nv.soDienThoai, " +
                             "nv.email, nv.diaChi, nv.chucVu, nv.trinhDo, nv.luong, tk.matKhau " +
                             "FROM NhanVien nv LEFT JOIN TaiKhoan tk ON nv.maNV = tk.maNV " +
                             "WHERE nv.trangThai = 1")) {
        	
            ResultSet rs = stmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            while (rs.next()) {
                String ngaySinh = sdf.format(rs.getDate("ngaySinh"));
                float luong = rs.getFloat("Luong");
                String formattedLuong = String.format("%,.0fđ", luong); // Format
                Object[] row = { rs.getString("maNV"), rs.getString("hoTen"), 
                		rs.getString("gioiTinh"), rs.getString("CCCD"), rs.getString("chucVu"),
                        rs.getString("trinhDo")};
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải dữ liệu: " + e.getMessage());
        }
        return data;
    }
	
	//Tìm kiếm nhân viên theo điều kiện
    public List<Object[]> searchEmployees(String maNV, String hoTen, String cccd, String gioiTinh) {
        List<Object[]> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder(
            "SELECT maNV, hoTen, gioiTinh, CCCD, chucVu, trinhDo FROM NhanVien WHERE 1=1"
        );
        if (!maNV.isEmpty()) sb.append(" AND maNV LIKE ?");
        if (!hoTen.isEmpty()) sb.append(" AND hoTen LIKE ?");
        if (!cccd.isEmpty()) sb.append(" AND CCCD LIKE ?");
        if (!gioiTinh.isEmpty()) sb.append(" AND gioiTinh = ?");

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement st = conn.prepareStatement(sb.toString())) {
            int idx = 1;
            if (!maNV.isEmpty()) st.setString(idx++, "%" + maNV + "%");
            if (!hoTen.isEmpty()) st.setString(idx++, "%" + hoTen + "%");
            if (!cccd.isEmpty()) st.setString(idx++, "%" + cccd + "%");
            if (!gioiTinh.isEmpty()) st.setString(idx++, gioiTinh);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("maNV"),
                        rs.getString("hoTen"),
                        rs.getString("gioiTinh"),
                        rs.getString("CCCD"),
                        rs.getString("chucVu"),
                        rs.getString("trinhDo")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Object[]> getPayroll(String maNV, String thangNam) {
        List<Object[]> list = new ArrayList<>();
        // parse year-month
        String[] parts = thangNam.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        YearMonth ym = YearMonth.of(year, month);
        int daysInMonth = ym.lengthOfMonth();

        // lấy lương cố định
        BigDecimal luongCoDinh = getSalary(maNV);
        BigDecimal ratePerHour = luongCoDinh
            .divide(BigDecimal.valueOf(daysInMonth * 8), 2, RoundingMode.HALF_UP);

        String sql = "SELECT cc.ngay, cl.tenCa, cc.gioVao, cc.gioRa, cl.gioBatDau, cl.gioKetThuc"
                   + " FROM ChamCong cc"
                   + " LEFT JOIN CaLamViec cl ON cc.maCa = cl.maCa"
                   + " WHERE cc.maNV = ? AND MONTH(cc.ngay) = ? AND YEAR(cc.ngay) = ?"
                   + " ORDER BY cc.ngay";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, maNV);
            st.setInt(2, month);
            st.setInt(3, year);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Date date = rs.getDate("ngay");
                    String tenCa = rs.getString("tenCa");
                    Time vao = rs.getTime("gioVao");
                    Time ra = rs.getTime("gioRa");
                    Time bd = rs.getTime("gioBatDau");
                    Time kt = rs.getTime("gioKetThuc");
                    
                 // Tính phút đã làm
                    long workedMins = (ra.getTime() - vao.getTime()) / 60000;
                    // Nếu âm, coi như 0
                    if (workedMins < 0) {
                        workedMins = 0;
                    }
                    double workedH = workedMins / 60.0;
                    long stdMins = (kt.getTime() - bd.getTime()) / 60000;
                    double stdH = stdMins / 60.0;
                    double normalH = Math.min(workedH, stdH);
                    double otH = Math.max(workedH - stdH, 0);

                    BigDecimal luongThuong = ratePerHour.multiply(BigDecimal.valueOf(normalH));
                    BigDecimal luongOT = ratePerHour
                        .multiply(BigDecimal.valueOf(otH))
                        .multiply(BigDecimal.valueOf(1.5));
                    
                 // Định dạng tiền tệ Việt Nam
                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                    String formattedLuongThuong = currencyVN.format(luongThuong);
                    String formattedLuongOT = currencyVN.format(luongOT);

 				   
 				   	// Thêm vào danh sách
                    list.add(new Object[]{ date, tenCa, vao, ra, workedH, formattedLuongThuong, formattedLuongOT });
                    
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Lấy lương cố định của nhân viên */
    private BigDecimal getSalary(String maNV) {
        String sql = "SELECT luong FROM NhanVien WHERE maNV = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, maNV);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("luong");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
}
	