package entity;

import java.util.Date;
import java.util.Objects;

public class PhieuBanThuoc {
    private String maPBT;
    private Date ngayLap;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private double tongTien;
    private String trangThai;

    // Constructor mặc định
    public PhieuBanThuoc() {
    }

    // Constructor đầy đủ tham số
    public PhieuBanThuoc(String maPBT, Date ngayLap, NhanVien nhanVien, KhachHang khachHang, double tongTien, String trangThai) {
        this.maPBT = maPBT;
        this.ngayLap = ngayLap;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    // Getter và Setter
    public String getMaPBT() {
        return maPBT;
    }

    public void setMaPBT(String maPBT) {
        this.maPBT = maPBT;
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

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuBanThuoc that = (PhieuBanThuoc) o;
        return Objects.equals(maPBT, that.maPBT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPBT);
    }

    @Override
    public String toString() {
        return "PhieuBanThuoc{" +
                "maPBT='" + maPBT + '\'' +
                ", ngayLap=" + ngayLap +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", tongTien=" + tongTien +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}