package entity;

import java.util.Objects;

public class ChiTietPhieuTraThuoc {
    private PhieuTraThuoc phieuTraThuoc;
    private Thuoc thuoc;
    private int soLuong;
    private double donGiaBan;

    // Constructor mặc định
    public ChiTietPhieuTraThuoc() {
    }

    // Constructor đầy đủ tham số
    public ChiTietPhieuTraThuoc(PhieuTraThuoc phieuTraThuoc, Thuoc thuoc, int soLuong, double donGiaBan) {
        this.phieuTraThuoc = phieuTraThuoc;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGiaBan = donGiaBan;
    }

    // Getter và Setter
    public PhieuTraThuoc getPhieuTraThuoc() {
        return phieuTraThuoc;
    }

    public void setPhieuTraThuoc(PhieuTraThuoc phieuTraThuoc) {
        this.phieuTraThuoc = phieuTraThuoc;
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

    public double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuTraThuoc that = (ChiTietPhieuTraThuoc) o;
        return Objects.equals(phieuTraThuoc, that.phieuTraThuoc) &&
                Objects.equals(thuoc, that.thuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuTraThuoc, thuoc);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuTraThuoc{" +
                "phieuTraThuoc=" + phieuTraThuoc +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                ", donGiaBan=" + donGiaBan +
                '}';
    }
}