package entity;

import java.util.Objects;

public class ChiTietPhieuBanThuoc {
    private PhieuBanThuoc phieuBanThuoc;
    private Thuoc thuoc;
    private int soLuong;
    private double donGiaBan;

    // Constructor mặc định
    public ChiTietPhieuBanThuoc() {
    }

    // Constructor đầy đủ tham số
    public ChiTietPhieuBanThuoc(PhieuBanThuoc phieuBanThuoc, Thuoc thuoc, int soLuong, double donGiaBan) {
        this.phieuBanThuoc = phieuBanThuoc;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGiaBan = donGiaBan;
    }

    // Getter và Setter
    public PhieuBanThuoc getPhieuBanThuoc() {
        return phieuBanThuoc;
    }

    public void setPhieuBanThuoc(PhieuBanThuoc phieuBanThuoc) {
        this.phieuBanThuoc = phieuBanThuoc;
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
        ChiTietPhieuBanThuoc that = (ChiTietPhieuBanThuoc) o;
        return Objects.equals(phieuBanThuoc, that.phieuBanThuoc) &&
                Objects.equals(thuoc, that.thuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phieuBanThuoc, thuoc);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuBanThuoc{" +
                "phieuBanThuoc=" + phieuBanThuoc +
                ", thuoc=" + thuoc +
                ", soLuong=" + soLuong +
                ", donGiaBan=" + donGiaBan +
                '}';
    }
}