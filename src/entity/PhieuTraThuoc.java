package entity;

import java.util.Date;
import java.util.Objects;

public class PhieuTraThuoc {
    private String maPTT;
    private PhieuBanThuoc phieuBanThuoc;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private Date ngayTra;
    private String lyDoTra;

    // Constructor mặc định
    public PhieuTraThuoc() {
    }

    // Constructor đầy đủ tham số
    public PhieuTraThuoc(String maPTT, PhieuBanThuoc phieuBanThuoc, NhanVien nhanVien, KhachHang khachHang,
                         Date ngayTra, String lyDoTra) {
        this.maPTT = maPTT;
        this.phieuBanThuoc = phieuBanThuoc;
        this.nhanVien = nhanVien;
        this.khachHang = khachHang;
        this.ngayTra = ngayTra;
        this.lyDoTra = lyDoTra;
    }

    // Getter và Setter
    public String getMaPTT() {
        return maPTT;
    }

    public void setMaPTT(String maPTT) {
        this.maPTT = maPTT;
    }

    public PhieuBanThuoc getPhieuBanThuoc() {
        return phieuBanThuoc;
    }

    public void setPhieuBanThuoc(PhieuBanThuoc phieuBanThuoc) {
        this.phieuBanThuoc = phieuBanThuoc;
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

    public Date getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Date ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getLyDoTra() {
        return lyDoTra;
    }

    public void setLyDoTra(String lyDoTra) {
        this.lyDoTra = lyDoTra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuTraThuoc that = (PhieuTraThuoc) o;
        return Objects.equals(maPTT, that.maPTT);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPTT);
    }

    @Override
    public String toString() {
        return "PhieuTraThuoc{" +
                "maPTT='" + maPTT + '\'' +
                ", phieuBanThuoc=" + phieuBanThuoc +
                ", nhanVien=" + nhanVien +
                ", khachHang=" + khachHang +
                ", ngayTra=" + ngayTra +
                ", lyDoTra='" + lyDoTra + '\'' +
                '}';
    }
}