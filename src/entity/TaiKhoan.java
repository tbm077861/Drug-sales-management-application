package entity;

import java.util.Objects;

public class TaiKhoan {
    private String maNV;
    private String matKhau;
    private NhanVien nhanVien; // Liên kết với nhân viên

    // Constructor mặc định
    public TaiKhoan() {
    }

    // Constructor đầy đủ tham số
    public TaiKhoan(String maNV, String matKhau, NhanVien nhanVien) {
        this.maNV = maNV;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
    }

    // Getter và Setter
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaiKhoan taiKhoan = (TaiKhoan) o;
        return Objects.equals(maNV, taiKhoan.maNV);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNV);
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "maNV='" + maNV + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", nhanVien=" + nhanVien +
                '}';
    }
}