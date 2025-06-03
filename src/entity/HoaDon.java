package entity;

import java.util.Date;

public class HoaDon {
    private String maHoaDon;
    private Date ngayLap;
    private NhanVien nhanVien; // Liên kết với nhân viên lập hóa đơn
    private double tongTien;

    // Constructor mặc định
    public HoaDon() {
    }

    // Constructor đầy đủ tham số
    public HoaDon(String maHoaDon, Date ngayLap, NhanVien nhanVien, double tongTien) {
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.nhanVien = nhanVien;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    // Phương thức toString
    @Override
    public String toString() {
        return "HoaDon{" +
                "maHoaDon='" + maHoaDon + '\'' +
                ", ngayLap=" + ngayLap +
                ", nhanVien=" + nhanVien +
                ", tongTien=" + tongTien +
                '}';
    }
}