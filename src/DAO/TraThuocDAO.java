package DAO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;
import connectDB.ConnectDB;

public class TraThuocDAO {

	
	// Tải danh sách phiếu bán thuốc đã thanh toán
    public List<Object[]> loadDataToDSHoaDon() {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT pbt.maPBT, pbt.ngayLap, pbt.maKH, kh.hoTen, kh.soDienThoai, SUM(ct.soLuong * ct.donGiaBan) as tongTien " +
                      "FROM PhieuBanThuoc pbt " +
                      "JOIN KhachHang kh ON pbt.maKH = kh.maKH " +
                      "JOIN ChiTietPhieuBanThuoc ct ON pbt.maPBT = ct.maPBT " +
                      "WHERE pbt.trangThai = N'Đã thanh toán' " +
                      "GROUP BY pbt.maPBT, pbt.ngayLap, pbt.maKH, kh.hoTen, kh.soDienThoai";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                data.add(new Object[] {
                    rs.getString("maPBT"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ngayLap")),
                    rs.getString("maKH"),
                    rs.getString("hoTen"),
                    rs.getString("soDienThoai"),
                    String.format("%,.0fđ", rs.getDouble("tongTien"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    // Tải danh sách phiếu đặt thuốc đã thanh toán
    public List<Object[]> loadDataToDSPhieuDat() {
        List<Object[]> data = new ArrayList<>();
        String query = "SELECT pdt.maPDT, pdt.ngayDat, pdt.maKH, kh.hoTen, kh.soDienThoai, SUM(ct.soLuong * ct.donGiaBan) as tongTien " +
                      "FROM PhieuDatThuoc pdt " +
                      "JOIN KhachHang kh ON pdt.maKH = kh.maKH " +
                      "JOIN ChiTietPhieuDatThuoc ct ON pdt.maPDT = ct.maPDT " +
                      "WHERE pdt.trangThai = N'Đã thanh toán' " +
                      "GROUP BY pdt.maPDT, pdt.ngayDat, pdt.maKH, kh.hoTen, kh.soDienThoai";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                data.add(new Object[] {
                    rs.getString("maPDT"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ngayDat")),
                    rs.getString("maKH"),
                    rs.getString("hoTen"),
                    rs.getString("soDienThoai"),
                    String.format("%,.0fđ", rs.getDouble("tongTien"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    
    // Tìm kiếm hóa đơn theo các tiêu chí
    public List<Object[]> timKiemHoaDon(String maHD, String maKH, String tenKH, String soDienThoai) {
        List<Object[]> data = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT pbt.maPBT, pbt.ngayLap, pbt.maKH, kh.hoTen, kh.soDienThoai, SUM(ct.soLuong * ct.donGiaBan) as tongTien " +
            "FROM PhieuBanThuoc pbt " +
            "JOIN KhachHang kh ON pbt.maKH = kh.maKH " +
            "JOIN ChiTietPhieuBanThuoc ct ON pbt.maPBT = ct.maPBT " +
            "WHERE pbt.trangThai = N'Đã thanh toán'"
        );
        List<String> params = new ArrayList<>();

        if (!maHD.isEmpty()) {
            query.append(" AND pbt.maPBT LIKE ?");
            params.add("%" + maHD + "%");
        }
        if (!maKH.isEmpty()) {
            query.append(" AND pbt.maKH LIKE ?");
            params.add("%" + maKH + "%");
        }
        if (!tenKH.isEmpty()) {
            query.append(" AND kh.hoTen LIKE ?");
            params.add("%" + tenKH + "%");
        }
        if (!soDienThoai.isEmpty()) {
            query.append(" AND kh.soDienThoai LIKE ?");
            params.add("%" + soDienThoai + "%");
        }
        query.append(" GROUP BY pbt.maPBT, pbt.ngayLap, pbt.maKH, kh.hoTen, kh.soDienThoai");

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setString(i + 1, params.get(i));
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(new Object[] {
                    rs.getString("maPBT"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ngayLap")),
                    rs.getString("maKH"),
                    rs.getString("hoTen"),
                    rs.getString("soDienThoai"),
                    String.format("%,.0fđ", rs.getDouble("tongTien"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Tìm kiếm phiếu đặt thuốc theo các tiêu chí
    public List<Object[]> timKiemPhieuDat(String maPDT, String maKH, String tenKH, String soDienThoai) {
        List<Object[]> data = new ArrayList<>();
        StringBuilder query = new StringBuilder(
            "SELECT pdt.maPDT, pdt.ngayDat, pdt.maKH, kh.hoTen, kh.soDienThoai, SUM(ct.soLuong * ct.donGiaBan) as tongTien " +
            "FROM PhieuDatThuoc pdt " +
            "JOIN KhachHang kh ON pdt.maKH = kh.maKH " +
            "JOIN ChiTietPhieuDatThuoc ct ON pdt.maPDT = ct.maPDT " +
            "WHERE pdt.trangThai = N'Đã thanh toán'"
        );
        List<String> params = new ArrayList<>();

        if (!maPDT.isEmpty()) {
            query.append(" AND pdt.maPDT LIKE ?");
            params.add("%" + maPDT + "%");
        }
        if (!maKH.isEmpty()) {
            query.append(" AND pdt.maKH LIKE ?");
            params.add("%" + maKH + "%");
        }
        if (!tenKH.isEmpty()) {
            query.append(" AND kh.hoTen LIKE ?");
            params.add("%" + tenKH + "%");
        }
        if (!soDienThoai.isEmpty()) {
            query.append(" AND kh.soDienThoai LIKE ?");
            params.add("%" + soDienThoai + "%");
        }
        query.append(" GROUP BY pdt.maPDT, pdt.ngayDat, pdt.maKH, kh.hoTen, kh.soDienThoai");

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setString(i + 1, params.get(i));
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(new Object[] {
                    rs.getString("maPDT"),
                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ngayDat")),
                    rs.getString("maKH"),
                    rs.getString("hoTen"),
                    rs.getString("soDienThoai"),
                    String.format("%,.0fđ", rs.getDouble("tongTien"))
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    // Lấy mã phiếu trả thuốc mới nhất
    public String getLastMaPhieuTra() {
        String lastMaPTT = "PTT000";
        String query = "SELECT TOP 1 maPTT FROM PhieuTraThuoc ORDER BY maPTT DESC";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                lastMaPTT = rs.getString("maPTT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastMaPTT;
    }

    // Lấy danh sách mã hóa đơn đã thanh toán
    public List<String> getAllMaHoaDon() {
        List<String> maHDList = new ArrayList<>();
        String query = "SELECT maPBT FROM PhieuBanThuoc WHERE trangThai = N'Đã thanh toán'";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                maHDList.add(rs.getString("maPBT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maHDList;
    }

    // Lấy thông tin hóa đơn từ mã hóa đơn
    public Map<String, String> getThongTinHoaDon(String maHD) {
        Map<String, String> info = new HashMap<>();
        String query = "SELECT pbt.maKH, kh.hoTen as tenKH " +
                      "FROM PhieuBanThuoc pbt " +
                      "JOIN KhachHang kh ON pbt.maKH = kh.maKH " +
                      "WHERE pbt.maPBT = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maHD);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                info.put("maKH", rs.getString("maKH"));
                info.put("tenKH", rs.getString("tenKH"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }
    
    // Lấy danh sách mã phiếu đặt thuốc đã thanh toán
    public List<String> getAllMaPhieuDat() {
        List<String> maPDTList = new ArrayList<>();
        String query = "SELECT maPDT FROM PhieuDatThuoc WHERE trangThai = N'Đã thanh toán'";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                maPDTList.add(rs.getString("maPDT"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maPDTList;
    }

    // Lấy thông tin phiếu đặt thuốc từ mã phiếu đặt thuốc
    public Map<String, String> getThongTinPhieuDat(String maPDT) {
        Map<String, String> info = new HashMap<>();
        String query = "SELECT pdt.maKH, kh.hoTen as tenKH " +
                      "FROM PhieuDatThuoc pdt " +
                      "JOIN KhachHang kh ON pdt.maKH = kh.maKH " +
                      "WHERE pdt.maPDT = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maPDT);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                info.put("maKH", rs.getString("maKH"));
                info.put("tenKH", rs.getString("tenKH"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    // Lưu phiếu trả thuốc
    public boolean luuPhieuTra(String maPhieuTra, String maHoaDon, String maNV, String maKH, String ngayTra, String lyDoTra, boolean isBanThuoc) {
        String query = "INSERT INTO PhieuTraThuoc (maPTT, maHD, maNV, maKH, ngayTra, lyDoTra) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, maPhieuTra);
            pstmt.setString(2, maHoaDon); // maHD có thể là maPBT hoặc maPDT
            pstmt.setString(3, maNV);
            pstmt.setString(4, maKH);
            pstmt.setDate(5, new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(ngayTra).getTime()));
            pstmt.setString(6, lyDoTra);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean capNhatSoLuong(String maThuoc, int soLuong) {
        String query = "UPDATE Thuoc SET soLuongTon = soLuongTon + ?, soLuongThucTe = soLuongThucTe + ? WHERE maThuoc = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, soLuong); // cho soLuongTon
            pstmt.setInt(2, soLuong); // cho soLuongThucTe
            pstmt.setString(3, maThuoc);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Object[]> loadDataToDSPhieuTra() {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT maPTT, maHD, ngayTra, maNV, maKH, lyDoTra FROM PhieuTraThuoc";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                String ngayTra = sdf.format(rs.getDate("ngayTra"));
                Object[] row = {
                    rs.getString("maPTT"),
                    rs.getString("maHD"),
                    ngayTra,
                    rs.getString("maNV"),
                    rs.getString("maKH"),
                    rs.getString("lyDoTra")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public List<Object[]> timKiemPhieuTra(String maPhieuTra, String maHoaDon, String ngayTra, String maNV, String maKH, String lyDoTra) {
        List<Object[]> data = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT maPTT, maHD, ngayTra, maNV, maKH, lyDoTra FROM PhieuTraThuoc WHERE 1=1");
        if (!maPhieuTra.isEmpty()) {
            sql.append(" AND maPTT LIKE ?");
        }
        if (!maHoaDon.isEmpty()) {
            sql.append(" AND maHD LIKE ?");
        }
        if (ngayTra != null) {
            sql.append(" AND ngayTra = ?");
        }
        if (!maNV.isEmpty()) {
            sql.append(" AND maNV LIKE ?");
        }
        if (!maKH.isEmpty()) {
            sql.append(" AND maKH LIKE ?");
        }
        if (!lyDoTra.isEmpty()) {
            sql.append(" AND lyDoTra LIKE ?");
        }

        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (!maPhieuTra.isEmpty()) {
                stmt.setString(paramIndex++, "%" + maPhieuTra + "%");
            }
            if (!maHoaDon.isEmpty()) {
                stmt.setString(paramIndex++, "%" + maHoaDon + "%");
            }
            if (ngayTra != null) {
                stmt.setString(paramIndex++, ngayTra);
            }
            if (!maNV.isEmpty()) {
                stmt.setString(paramIndex++, "%" + maNV + "%");
            }
            if (!maKH.isEmpty()) {
                stmt.setString(paramIndex++, "%" + maKH + "%");
            }
            if (!lyDoTra.isEmpty()) {
                stmt.setString(paramIndex++, "%" + lyDoTra + "%");
            }

            ResultSet rs = stmt.executeQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            while (rs.next()) {
                String ngayTraFormatted = sdf.format(rs.getDate("ngayTra"));
                Object[] row = {
                    rs.getString("maPTT"),
                    rs.getString("maHD"),
                    ngayTraFormatted,
                    rs.getString("maNV"),
                    rs.getString("maKH"),
                    rs.getString("lyDoTra")
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}