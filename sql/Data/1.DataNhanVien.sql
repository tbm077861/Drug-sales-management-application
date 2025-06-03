--==============Data Real================================
INSERT INTO NhanVien (maNV, hoTen, ngaySinh, gioiTinh, CCCD, soDienThoai, email, diaChi, chucVu, trinhDo, luong, trangThai)
VALUES
-- === Quản lý ===
('NV001', N'Nguyễn Văn An', '1985-03-15', N'Nam', '012345678901', '0909111222', 'an.nguyen@nhathuocabc.vn', N'12 Lê Lợi, Q1, TP.HCM', N'Quản lý', N'Dược sĩ đại học', 16000000, 1),
('NV002', N'Lê Thị Mai', '1987-07-22', N'Nữ', '012345678902', '0909222333', 'mai.le@nhathuocabc.vn', N'45 Trần Hưng Đạo, Q5, TP.HCM', N'Quản lý', N'Dược sĩ đại học', 15500000, 1),
('NV003', N'Phạm Văn Cường', '1982-11-10', N'Nam', '012345678903', '0909333444', 'cuong.pham@nhathuocabc.vn', N'87 Phan Xích Long, Q.Phú Nhuận, TP.HCM', N'Quản lý', N'Dược sĩ đại học', 17000000, 1),
('NV004', N'Trần Thị Hoa', '1989-01-18', N'Nữ', '012345678904', '0909444555', 'hoa.tran@nhathuocabc.vn', N'22 Nguyễn Thái Bình, Q.1, TP.HCM', N'Quản lý', N'Dược sĩ đại học', 15200000, 1),
('NV005', N'Võ Minh Tâm', '1986-06-30', N'Nam', '012345678905', '0909555666', 'tam.vo@nhathuocabc.vn', N'5 Nguyễn Tri Phương, Q.10, TP.HCM', N'Quản lý', N'Dược sĩ đại học', 15800000, 1),

-- === Nhân viên ===
('NV006', N'Nguyễn Thị Lan', '1992-02-14', N'Nữ', '012345678906', '0911000001', 'lan.nguyen@nhathuocabc.vn', N'101 Điện Biên Phủ, Q.Bình Thạnh', N'Nhân viên', N'Dược sĩ trung cấp', 9200000, 1),
('NV007', N'Huỳnh Văn Minh', '1993-04-25', N'Nam', '012345678907', '0911000002', 'minh.huynh@nhathuocabc.vn', N'202 Cách Mạng Tháng 8, Q.3', N'Nhân viên', N'Dược sĩ trung cấp', 9100000, 1),
('NV008', N'Đặng Thị Hồng', '1994-12-03', N'Nữ', '012345678908', '0911000003', 'hong.dang@nhathuocabc.vn', N'33 Nguyễn Đình Chiểu, Q.1', N'Nhân viên', N'Dược sĩ trung cấp', 9000000, 1),
('NV009', N'Trương Văn Lộc', '1995-08-19', N'Nam', '012345678909', '0911000004', 'loc.truong@nhathuocabc.vn', N'19 Trường Sa, Q.Bình Thạnh', N'Nhân viên', N'Dược sĩ trung cấp', 9300000, 1),
('NV010', N'Ngô Thị Bích', '1991-10-07', N'Nữ', '012345678910', '0911000005', 'bich.ngo@nhathuocabc.vn', N'8 Phan Đăng Lưu, Q.Phú Nhuận', N'Nhân viên', N'Dược sĩ trung cấp', 9200000, 1),
('NV011', N'Phan Văn Dũng', '1990-09-17', N'Nam', '012345678911', '0911000006', 'dung.phan@nhathuocabc.vn', N'72 Cộng Hòa, Q.Tân Bình', N'Nhân viên', N'Dược sĩ trung cấp', 9150000, 1),
('NV012', N'Thái Thị Xuân', '1992-03-28', N'Nữ', '012345678912', '0911000007', 'xuan.thai@nhathuocabc.vn', N'66 Lý Thái Tổ, Q.10', N'Nhân viên', N'Dược sĩ trung cấp', 9100000, 1),
('NV013', N'Tô Quang Huy', '1996-05-05', N'Nam', '012345678913', '0911000008', 'huy.to@nhathuocabc.vn', N'98 Nguyễn Văn Cừ, Q.5', N'Nhân viên', N'Dược sĩ trung cấp', 9000000, 1),
('NV014', N'Lê Thị Thu', '1993-11-12', N'Nữ', '012345678914', '0911000009', 'thu.le@nhathuocabc.vn', N'144 Tôn Đức Thắng, Q.1', N'Nhân viên', N'Dược sĩ trung cấp', 9050000, 1),
('NV015', N'Vũ Quốc Khánh', '1994-07-21', N'Nam', '012345678915', '0911000010', 'khanh.vu@nhathuocabc.vn', N'13 Nguyễn Văn Trỗi, Q.Phú Nhuận', N'Nhân viên', N'Dược sĩ trung cấp', 9200000, 1),
('NV016', N'Cao Thị Minh', '1991-06-02', N'Nữ', '012345678916', '0911000011', 'minh.cao@nhathuocabc.vn', N'88 Hoàng Văn Thụ, Q.Phú Nhuận', N'Nhân viên', N'Dược sĩ trung cấp', 9100000, 1),
('NV017', N'Hồ Anh Tuấn', '1993-09-09', N'Nam', '012345678917', '0911000012', 'tuan.ho@nhathuocabc.vn', N'43 Nguyễn Trãi, Q1', N'Nhân viên', N'Dược sĩ trung cấp', 9050000, 1),
('NV018', N'Trần Thị Hạnh', '1995-12-22', N'Nữ', '012345678918', '0911000013', 'hanh.tran@nhathuocabc.vn', N'11 Hai Bà Trưng, Q.1', N'Nhân viên', N'Dược sĩ trung cấp', 9000000, 1),
('NV019', N'Nguyễn Quốc Bảo', '1996-01-30', N'Nam', '012345678919', '0911000014', 'bao.nguyen@nhathuocabc.vn', N'70 Pasteur, Q.3', N'Nhân viên', N'Dược sĩ trung cấp', 9150000, 1),
('NV020', N'Bùi Thị Diễm', '1992-08-08', N'Nữ', '012345678920', '0911000015', 'diem.bui@nhathuocabc.vn', N'99 Nguyễn Thông, Q.3', N'Nhân viên', N'Dược sĩ trung cấp', 9050000, 1);