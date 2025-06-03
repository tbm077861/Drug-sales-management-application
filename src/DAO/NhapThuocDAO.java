package DAO;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import connectDB.ConnectDB;

public class NhapThuocDAO {

    public String generateMaPhieuNhap() {
        String lastMaPNT = getLastMaPhieuNhap();
        if (lastMaPNT == null || lastMaPNT.isEmpty() || lastMaPNT.equals("PNT000")) {
            return "PNT001";
        }
        String numericPart = lastMaPNT.substring(3); // Remove "PNT" prefix
        try {
            int nextNumber = Integer.parseInt(numericPart) + 1;
            return "PNT" + String.format("%03d", nextNumber);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "PNT001"; // Fallback in case of parsing error
        }
    }

    public String getLastMaPhieuNhap() {
        String sql = "SELECT MAX(maPNT) FROM PhieuNhapThuoc";
        try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String lastMaPNT = rs.getString(1);
                if (lastMaPNT != null && !lastMaPNT.isEmpty()) {
                    return lastMaPNT;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "PNT000";
    }

    public boolean luuPhieuNhap(String maPhieuNhap, String maNV, String maNCC, String ngayNhap, String hinhThucThanhToan, double tongTien) {
        String query = "INSERT INTO PhieuNhapThuoc (maPNT, maNV, maNCC, ngayNhap, trangThai, phuongThucThanhToan) VALUES (?, ?, ?, ?, N'Đã thanh toán', ?)";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maPhieuNhap);
            pstmt.setString(2, maNV);
            pstmt.setString(3, maNCC);
            pstmt.setDate(4, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(ngayNhap).getTime()));
            pstmt.setString(5, hinhThucThanhToan);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi lưu phiếu nhập: " + e.getMessage());
            return false;
        }
    }

    public boolean capNhatSoLuongTon(String maThuoc, int soLuong) {
        String query = "UPDATE Thuoc SET soLuongTon = soLuongTon + ?, soLuongThucTe = soLuongThucTe + ? WHERE maThuoc = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, soLuong);
            pstmt.setInt(2, soLuong);
            pstmt.setString(3, maThuoc);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật số lượng tồn: " + e.getMessage());
            return false;
        }
    }

    public List<Object[]> loadDataToDSPhieuNhap() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT maPNT, maNV, maNCC, ngayNhap, phuongThucThanhToan FROM PhieuNhapThuoc";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                String ngayNhap = sdf.format(rs.getDate("ngayNhap"));
                Object[] row = {
                    rs.getString("maPNT"),
                    rs.getString("maNV"),
                    rs.getString("maNCC"),
                    ngayNhap,
                    rs.getString("phuongThucThanhToan")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi tải danh sách phiếu nhập: " + e.getMessage());
        }
        return data;
    }
}