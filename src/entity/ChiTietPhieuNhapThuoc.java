package entity;

import java.util.Objects;

public class ChiTietPhieuNhapThuoc {
    private PhieuNhapThuoc phieuNhapThuoc;
    private Thuoc thuoc;
    private int soLuong;
    private double donGiaNhap;

    // Constructor mặc định
    public ChiTietPhieuNhapThuoc() {
    }

    // Constructor đầy đủ tham số
    public ChiTietPhieuNhapThuoc(PhieuNhapThuoc phieuNhapThuoc, Thuoc thuoc, int soLuong, double donGiaNhap) {
        this.phieuNhapThuoc = phieuNhapThuoc;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGiaNhap = donGiaNhap;
    }

    // Getter và Setter
    public PhieuNhapThuoc getPhieuNhapThuoc() {
        return phieuNhapThuoc;
    }

    public void setPhieuNhapThuoc(PhieuNhapThuoc phieuNhapThuoc) {
        this.phieuNhapThuoc = phieuNhapThuoc;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuNhapThuoc that = (ChiTietPhieuNhapThuoc) o;
        return Objects.equals(phieuNhapThuoc, that.phieuNhapThuoc) &&
                Objects.equals(thuoc, that.thuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuNhapThuoc, thuoc);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuNhapThuoc{" +
                "phieuNhapThuoc=" + phieuNhapThuoc +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                ", donGiaNhap=" + donGiaNhap +
                '}';
    }
}