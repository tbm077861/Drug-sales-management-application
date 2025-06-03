package entity;

import java.util.Date;
import java.util.Objects;

public class PhieuNhapThuoc {
    private String maPN;
    private NhanVien nhanVien;
    private NhaCungCap nhaCungCap;
    private Date ngayNhap;
    private String hinhThucThanhToan;
    private double tongTien;

    // Constructor mặc định
    public PhieuNhapThuoc() {
    }

    // Constructor đầy đủ tham số
    public PhieuNhapThuoc(String maPN, NhanVien nhanVien, NhaCungCap nhaCungCap, Date ngayNhap,
                          String hinhThucThanhToan, double tongTien) {
        this.maPN = maPN;
        this.nhanVien = nhanVien;
        this.nhaCungCap = nhaCungCap;
        this.ngayNhap = ngayNhap;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tongTien = tongTien;
    }

    // Getter và Setter
    public String getMaPN() {
        return maPN;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuNhapThuoc that = (PhieuNhapThuoc) o;
        return Objects.equals(maPN, that.maPN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPN);
    }

    @Override
    public String toString() {
        return "PhieuNhapThuoc{" +
                "maPN='" + maPN + '\'' +
                ", nhanVien=" + nhanVien +
                ", nhaCungCap=" + nhaCungCap +
                ", ngayNhap=" + ngayNhap +
                ", hinhThucThanhToan='" + hinhThucThanhToan + '\'' +
                ", tongTien=" + tongTien +
                '}';
    }
}