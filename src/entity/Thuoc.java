package entity;

import java.util.Date;
import java.util.Objects;

public class Thuoc {
    private String maThuoc;
    private String tenThuoc;
    private String donViTinh;
    private double donGiaNhap;
    private double donGiaBan;
    private Date hanSuDung;
    private String hamLuong;
    private int soLuongTon;
    private int soLuongThucTe;

    // Constructor mặc định
    public Thuoc() {
    }

    // Constructor đầy đủ tham số
    public Thuoc(String maThuoc, String tenThuoc, String donViTinh, double donGiaNhap, double donGiaBan,
                 Date hanSuDung, String hamLuong, int soLuongTon, int soLuongThucTe) {
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.donViTinh = donViTinh;
        this.donGiaNhap = donGiaNhap;
        this.donGiaBan = donGiaBan;
        this.hanSuDung = hanSuDung;
        this.hamLuong = hamLuong;
        this.soLuongTon = soLuongTon;
        this.soLuongThucTe = soLuongThucTe;
    }

    // Getter và Setter
    public String getMaThuoc() {
        return maThuoc;
    }

    public void setMaThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public Date getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(Date hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public String getHamLuong() {
        return hamLuong;
    }

    public void setHamLuong(String hamLuong) {
        this.hamLuong = hamLuong;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public int getSoLuongThucTe() {
        return soLuongThucTe;
    }

    public void setSoLuongThucTe(int soLuongThucTe) {
        this.soLuongThucTe = soLuongThucTe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thuoc thuoc = (Thuoc) o;
        return Objects.equals(maThuoc, thuoc.maThuoc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maThuoc);
    }

    @Override
    public String toString() {
        return "Thuoc{" +
                "maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", donViTinh='" + donViTinh + '\'' +
                ", donGiaNhap=" + donGiaNhap +
                ", donGiaBan=" + donGiaBan +
                ", hanSuDung=" + hanSuDung +
                ", hamLuong='" + hamLuong + '\'' +
                ", soLuongTon=" + soLuongTon +
                ", soLuongThucTe=" + soLuongThucTe +
                '}';
    }
}