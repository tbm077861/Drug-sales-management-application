package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;

public class ChiTietTraThuocDAO {
	
	// Lấy danh sách chi tiết phiếu trả thuốc theo mã phiếu trả thuốc
	public List<Object[]> getChiTietPhieuTraThuoc(String maPhieuDatHang) {
		List<Object[]> chiTietList = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaBan "
				+ "FROM ChiTietPhieuTraThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maPTT = ?";

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maPhieuDatHang);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String tenThuoc = rs.getString("tenThuoc");
				int soLuong = rs.getInt("soLuong");
				float donGiaBan = rs.getFloat("donGiaBan");

				String formatGiaBan = String.format("%,.0fđ", donGiaBan);
				String formatThanhTien = String.format("%,.0fđ", donGiaBan * soLuong);

				Object[] row = { tenThuoc, soLuong, formatGiaBan, formatThanhTien };
				chiTietList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chiTietList;
	}
	
	// Lấy chi tiết phiếu bán thuốc theo mã hóa đơn
	public List<Object[]> getChiTietPhieuBanThuoc(String maHoaDon) {
		List<Object[]> chiTietList = new ArrayList<>();
		String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaBan "
				+ "FROM ChiTietPhieuBanThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc " + "WHERE c.maPBT = ?";

		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, maHoaDon);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String maThuoc = rs.getString("maThuoc");
				String tenThuoc = rs.getString("tenThuoc");
				int soLuong = rs.getInt("soLuong");
				float donGiaBan = rs.getFloat("donGiaBan");

				String formatGiaBan = String.format("%,.0fđ", donGiaBan);
				String formatThanhTien = String.format("%,.0fđ", donGiaBan * soLuong);

				Object[] row = {maThuoc, tenThuoc, soLuong, formatGiaBan, formatThanhTien };
				chiTietList.add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chiTietList;
	}
	
	// Lấy chi tiết phiếu đặt thuốc theo mã phiếu đặt hàng
    public List<Object[]> getChiTietPhieuDatHang(String maPhieuDatHang) {
        List<Object[]> chiTietList = new ArrayList<>();
        String sql = "SELECT c.maThuoc, t.tenThuoc, c.soLuong, c.donGiaBan "
                + "FROM ChiTietPhieuDatThuoc c JOIN Thuoc t ON c.maThuoc = t.maThuoc "
                + "WHERE c.maPDT = ?";

        try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, maPhieuDatHang);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                int soLuong = rs.getInt("soLuong");
                float donGiaBan = rs.getFloat("donGiaBan");

                String formatGiaBan = String.format("%,.0fđ", donGiaBan);
                String formatThanhTien = String.format("%,.0fđ", donGiaBan * soLuong);

                Object[] row = { maThuoc, tenThuoc, soLuong, formatGiaBan, formatThanhTien };
                chiTietList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chiTietList;
    }

	// Lưu chi tiết phiếu trả thuốc vào cơ sở dữ liệu
	public boolean luuChiTietPhieuTra(String maPhieuTra, String maThuoc, int soLuongTra, double donGia) {
	    String sql = "INSERT INTO ChiTietPhieuTraThuoc (maPTT, maThuoc, soLuong, donGiaBan) VALUES (?, ?, ?, ?)";
	    try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, maPhieuTra);
	        pstmt.setString(2, maThuoc);
	        pstmt.setInt(3, soLuongTra);
	        pstmt.setDouble(4, donGia);
	        return pstmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    // Lấy số lượng thuốc đã bán theo mã phiếu bán thuốc và mã thuốc
    public int getSoLuongBan(String maPBT, String maThuoc) {
        String sql = "SELECT soLuong FROM ChiTietPhieuBanThuoc WHERE maPBT = ? AND maThuoc = ?";
        try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maPBT);
            pstmt.setString(2, maThuoc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
 // Lấy số lượng mua ban đầu từ ChiTietPhieuBanThuoc hoặc ChiTietPhieuDatThuoc
    public int getSoLuongMuaBanDau(String maHD, String maThuoc, boolean isBanThuoc) {
        String sql = isBanThuoc ?
            "SELECT ct.soLuong " +
            "FROM ChiTietPhieuBanThuoc ct " +
            "JOIN PhieuBanThuoc pbt ON ct.maPBT = pbt.maPBT " +
            "WHERE pbt.maPBT = ? AND ct.maThuoc = ?" :
            "SELECT ct.soLuong " +
            "FROM ChiTietPhieuDatThuoc ct " +
            "JOIN PhieuDatThuoc pdt ON ct.maPDT = pdt.maPDT " +
            "WHERE pdt.maPDT = ? AND ct.maThuoc = ?";
        
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maHD);
            pstmt.setString(2, maThuoc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("soLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy tổng số lượng thuốc đã trả theo maHD và maThuoc
    public int getTongSoLuongDaTra(String maHD, String maThuoc) {
        String sql = "SELECT SUM(ct.soLuong) as tongSoLuong " +
                     "FROM ChiTietPhieuTraThuoc ct " +
                     "JOIN PhieuTraThuoc ptt ON ct.maPTT = ptt.maPTT " +
                     "WHERE ptt.maHD = ? AND ct.maThuoc = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maHD);
            pstmt.setString(2, maThuoc);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("tongSoLuong");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Object[]> getChiTietPhieuTra(String maPTT) {
        List<Object[]> data = new ArrayList<>();
        String sql = "SELECT ct.maThuoc, t.tenThuoc, ct.soLuong, ct.donGiaBan " +
                     "FROM ChiTietPhieuTraThuoc ct " +
                     "JOIN Thuoc t ON ct.maThuoc = t.maThuoc " +
                     "WHERE ct.maPTT = ?";
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maPTT);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String formatGiaBan = String.format("%,.0fđ", rs.getDouble("donGiaBan"));
                String formatThanhTien = String.format("%,.0fđ", rs.getDouble("donGiaBan") * rs.getInt("soLuong"));
                Object[] row = {
                    rs.getString("maThuoc"),
                    rs.getString("tenThuoc"),
                    rs.getInt("soLuong"),
                    formatGiaBan,
                    formatThanhTien
                };
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public Object[] getPhieuTraThuocInfo(String maHD) {
		String sql = "SELECT maPTT, maNV, maKH, ngayTra, lyDoTra "
				+ "FROM PhieuTraThuoc WHERE maPTT = ?";

		try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maHD);

			ResultSet rs = stmt.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			if (rs.next()) {
				return new Object[] { rs.getString("maPTT"), rs.getString("maNV"), rs.getString("maKH"),
						sdf.format(rs.getDate("ngayTra")), rs.getString("lyDoTra") };
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

