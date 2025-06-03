-- ===================== TẠO DATABASE =====================
CREATE DATABASE DB_QuanLyNhaThuoc;
GO
USE DB_QuanLyNhaThuoc;
GO

-- ===================== BẢNG NHÂN VIÊN =====================
CREATE TABLE dbo.NhanVien (
    maNV VARCHAR(10) PRIMARY KEY,
    hoTen NVARCHAR(100),
    ngaySinh DATE,
    gioiTinh NVARCHAR(10),
    CCCD CHAR(12),
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    diaChi NVARCHAR(200),
    chucVu NVARCHAR(50),
    trinhDo NVARCHAR(100),
    luong FLOAT,
    trangThai BIT 
);

-- ===================== BẢNG TÀI KHOẢN =====================
CREATE TABLE dbo.TaiKhoan (
    maNV VARCHAR(10) PRIMARY KEY,
    matKhau VARCHAR(100),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV)
);

-- ===================== BẢNG KHÁCH HÀNG =====================
CREATE TABLE dbo.KhachHang (
    maKH VARCHAR(10) PRIMARY KEY,
    hoTen NVARCHAR(100),
    ngaySinh DATE,
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15),
    email VARCHAR(100)
);

-- ===================== BẢNG NHÀ CUNG CẤP =====================
CREATE TABLE dbo.NhaCungCap (
    maNCC VARCHAR(10) PRIMARY KEY,
    tenNCC NVARCHAR(100),
    diaChi NVARCHAR(200),
    soDienThoai VARCHAR(15),
    email VARCHAR(100),
    trangThai BIT 
);

-- ===================== BẢNG THUỐC =====================
CREATE TABLE dbo.Thuoc (
    maThuoc VARCHAR(10) PRIMARY KEY,
    tenThuoc NVARCHAR(100),
    donViTinh NVARCHAR(50),
    donGiaNhap FLOAT,
    donGiaBan FLOAT,
    hanSuDung DATE,
    hamLuong NVARCHAR(100),
    soLuongTon INT,
    soLuongThucTe INT
);

-- ===================== PHIẾU BÁN THUỐC =====================
CREATE TABLE dbo.PhieuBanThuoc (
    maPBT VARCHAR(10) PRIMARY KEY,
    ngayLap DATE,
    maNV VARCHAR(10),
    maKH VARCHAR(10),
    trangThai NVARCHAR(50),
    phuongThucThanhToan NVARCHAR(50),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maKH) REFERENCES dbo.KhachHang(maKH),
    CONSTRAINT CHK_PhieuBanThuoc_trangThai CHECK (trangThai IN (N'Đã thanh toán', N'Chờ xử lý', N'Đã hủy')),
	CONSTRAINT CHK_PhieuBanThuoc_phuongThucThanhToan CHECK (phuongThucThanhToan IN (N'Tiền mặt', N'Chuyển khoản'))
);

-- ===================== CHI TIẾT PHIẾU BÁN THUỐC =====================
CREATE TABLE dbo.ChiTietPhieuBanThuoc (
    maPBT VARCHAR(10),
    maThuoc VARCHAR(10),
    soLuong INT,
    donGiaBan FLOAT,
    PRIMARY KEY (maPBT, maThuoc),
    FOREIGN KEY (maPBT) REFERENCES dbo.PhieuBanThuoc(maPBT),
    FOREIGN KEY (maThuoc) REFERENCES dbo.Thuoc(maThuoc)
);

-- ===================== PHIẾU ĐẶT THUỐC =====================
CREATE TABLE dbo.PhieuDatThuoc (
    maPDT VARCHAR(10) PRIMARY KEY,
    maNV VARCHAR(10),
    maKH VARCHAR(10) NULL,
    ngayDat DATE,
    NgayGiao DATE,
    trangThai NVARCHAR(50),
    phuongThucThanhToan NVARCHAR(50),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maKH) REFERENCES dbo.KhachHang(maKH),
	CONSTRAINT CHK_PhieuDatThuoc_phuongThucThanhToan CHECK (phuongThucThanhToan IN (N'Tiền mặt', N'Chuyển khoản')),
    CONSTRAINT CHK_PhieuDatHang_trangThai CHECK (trangThai IN (N'Đã thanh toán', N'Chờ xử lý', N'Đã hủy'))
);

-- ===================== CHI TIẾT PHIẾU ĐẶT THUỐC =====================
CREATE TABLE dbo.ChiTietPhieuDatThuoc (
    maPDT VARCHAR(10),
    maThuoc VARCHAR(10),
    soLuong INT,
    donGiaBan FLOAT,
    PRIMARY KEY (maPDT, maThuoc),
    FOREIGN KEY (maPDT) REFERENCES dbo.PhieuDatThuoc(maPDT),
    FOREIGN KEY (maThuoc) REFERENCES dbo.Thuoc(maThuoc)
);

-- ===================== PHIẾU TRẢ THUỐC (bán thuốc + đặt thuốc)=====================
CREATE TABLE dbo.PhieuTraThuoc (
    maPTT VARCHAR(10) PRIMARY KEY,
    maHD VARCHAR(10), -- maPDT + maPBT
    maNV VARCHAR(10),
    maKH VARCHAR(10),
    ngayTra DATE,
    lyDoTra NVARCHAR(200),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maKH) REFERENCES dbo.KhachHang(maKH)
);

-- ===================== CHI TIẾT PHIẾU TRẢ THUỐC =====================
CREATE TABLE dbo.ChiTietPhieuTraThuoc (
    maPTT VARCHAR(10),
    maThuoc VARCHAR(10),
    soLuong INT,
    donGiaBan FLOAT,
    PRIMARY KEY (maPTT, maThuoc),
    FOREIGN KEY (maPTT) REFERENCES dbo.PhieuTraThuoc(maPTT),
    FOREIGN KEY (maThuoc) REFERENCES dbo.Thuoc(maThuoc)
);

-- ===================== PHIẾU NHẬP THUỐC =====================
CREATE TABLE dbo.PhieuNhapThuoc (
    maPNT VARCHAR(10) PRIMARY KEY,
    maNV VARCHAR(10),
    maNCC VARCHAR(10),
    ngayNhap DATE,
    trangThai NVARCHAR(50),
	phuongThucThanhToan NVARCHAR(50),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maNCC) REFERENCES dbo.NhaCungCap(maNCC),
	CONSTRAINT CHK_PhieuNhapThuoc_trangThai CHECK (trangThai IN (N'Đã thanh toán', N'Đã hủy')),
	CONSTRAINT CHK_PhieuNhapThuoc_phuongThucThanhToan CHECK (phuongThucThanhToan IN (N'Tiền mặt', N'Chuyển khoản'))
);

-- ===================== CHI TIẾT PHIẾU NHẬP THUỐC =====================
CREATE TABLE dbo.ChiTietPhieuNhapThuoc (
    maPNT VARCHAR(10),
    maThuoc VARCHAR(10),
    soLuong INT,
    donGiaNhap FLOAT,
    PRIMARY KEY (maPNT, maThuoc),
    FOREIGN KEY (maPNT) REFERENCES dbo.PhieuNhapThuoc(maPNT),
    FOREIGN KEY (maThuoc) REFERENCES dbo.Thuoc(maThuoc)
);

-- ===================== PHIẾU TRẢ NHẬP THUỐC =====================
CREATE TABLE dbo.PhieuTraNhapThuoc (
    maTNT VARCHAR(10) PRIMARY KEY,
    maPNT VARCHAR(10),
    maNV VARCHAR(10),
    maNCC VARCHAR(10),
    ngayTra DATE,
    lyDoTra NVARCHAR(200),
    FOREIGN KEY (maPNT) REFERENCES dbo.PhieuNhapThuoc(maPNT),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maNCC) REFERENCES dbo.NhaCungCap(maNCC)
);

-- ===================== CHI TIẾT PHIẾU TRẢ NHẬP THUỐC =====================
CREATE TABLE dbo.ChiTietPhieuTraNhapThuoc (
    maTNT VARCHAR(10),
    maThuoc VARCHAR(10),
    soLuong INT,
    donGiaNhap FLOAT,
    PRIMARY KEY (maTNT, maThuoc),
    FOREIGN KEY (maTNT) REFERENCES dbo.PhieuTraNhapThuoc(maTNT),
    FOREIGN KEY (maThuoc) REFERENCES dbo.Thuoc(maThuoc)
);

-- ===================== SỔ QUỸ THU CHI =====================
CREATE TABLE dbo.PhieuThuChi (
    maPTC VARCHAR(20) PRIMARY KEY,
	maNV VARCHAR(10),
    ngayGiaoDich DATE,
    hinhThuc NVARCHAR(50),
    loaiGiaoDich NVARCHAR(200),
	maDon NVARCHAR(20),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
	CONSTRAINT CHK_PhieuThuChi_hinhThuc CHECK (hinhThuc IN (N'Tiền mặt', N'Chuyển khoản')),
	CONSTRAINT CHK_PhieuThuChi_loaiGiaoDich CHECK (loaiGiaoDich IN (N'Bán thuốc', N'Đặt thuốc', N'Trả thuốc', N'Nhập thuốc', N'Trả nhập thuốc'))
);

-- ===================== QUẢN LÝ LỊCH VÀ CHẤM CÔNG =====================
CREATE TABLE dbo.CaLamViec (
    maCa VARCHAR(10) PRIMARY KEY,
    tenCa NVARCHAR(50),
    gioBatDau TIME,
    gioKetThuc TIME
);

CREATE TABLE dbo.LichLamViec (
    maLLV VARCHAR(10) PRIMARY KEY,
    maNV VARCHAR(10),
    ngay DATE,
    maCa VARCHAR(10),
    ghiChu NVARCHAR(200),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maCa) REFERENCES dbo.CaLamViec(maCa)
);

CREATE TABLE dbo.ChamCong (
    maCC VARCHAR(10) PRIMARY KEY,
    maNV VARCHAR(10),
    ngay DATE,
    maCa VARCHAR(10),
    gioVao TIME,
    gioRa TIME,
    trangThai NVARCHAR(50),
    ghiChu NVARCHAR(200),
    FOREIGN KEY (maNV) REFERENCES dbo.NhanVien(maNV),
    FOREIGN KEY (maCa) REFERENCES dbo.CaLamViec(maCa)
);