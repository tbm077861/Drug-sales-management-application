package entity;

import java.util.Date;
import java.util.Objects;

public class ChamCong {
    private String maChamCong;
    private NhanVien nhanVien;
    private Date ngayChamCong;
    private String maCa;
    private String gioBatDau;
    private String gioKetThuc;
    private String trangThai;
    private String ghiChu;

    // Constructor mặc định
    public ChamCong() {
    }

    // Constructor đầy đủ tham số
    public ChamCong(String maChamCong, NhanVien nhanVien, Date ngayChamCong, String maCa, String gioBatDau,
                    String gioKetThuc, String trangThai, String ghiChu) {
        this.maChamCong = maChamCong;
        this.nhanVien = nhanVien;
        this.ngayChamCong = ngayChamCong;
        this.maCa = maCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    // Getter và Setter
    public String getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(String maChamCong) {
        this.maChamCong = maChamCong;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public Date getNgayChamCong() {
        return ngayChamCong;
    }

    public void setNgayChamCong(Date ngayChamCong) {
        this.ngayChamCong = ngayChamCong;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public String getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(String gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public String getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(String gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChamCong chamCong = (ChamCong) o;
        return Objects.equals(maChamCong, chamCong.maChamCong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maChamCong);
    }

    @Override
    public String toString() {
        return "ChamCong{" +
                "maChamCong='" + maChamCong + '\'' +
                ", nhanVien=" + nhanVien +
                ", ngayChamCong=" + ngayChamCong +
                ", maCa='" + maCa + '\'' +
                ", gioBatDau='" + gioBatDau + '\'' +
                ", gioKetThuc='" + gioKetThuc + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                '}';
    }
}