package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


import java.awt.*;
//import java.awt.print.PageFormat;
//import java.awt.print.Printable;
//import java.awt.print.PrinterException;
//import java.awt.print.PrinterJob;

public class Dialog_XemPhieuTraNhapThuoc extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableChiTietHoaDon;
	private DefaultTableModel modelChiTiet;
	private static boolean isPrinting = false;

	public Dialog_XemPhieuTraNhapThuoc(JFrame parent, String maHoaDon, String maNV, String maNCC, String ngayTra,
			String lyDoTra, java.util.List<Object[]> chiTietList) {
		super(parent, "Phiếu Trả Nhập Thuốc - " + maHoaDon, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 781, 800);
		setLocationRelativeTo(parent);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Tiêu đề hóa đơn
		JLabel lblTieuDe = new JLabel("HÓA ĐƠN TRẢ NHẬP THUỐC");
		lblTieuDe.setBounds(10, 131, 745, 40);
		lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblTieuDe.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTieuDe);

		// Thông tin hóa đơn
		JPanel panelThongTin = new JPanel();
		panelThongTin.setBounds(10, 173, 745, 171);
		panelThongTin.setBackground(new Color(255, 245, 228));
		panelThongTin.setBorder(BorderFactory.createTitledBorder("Thông Tin Hóa Đơn"));
		panelThongTin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		contentPane.add(panelThongTin);
		panelThongTin.setLayout(new GridLayout(5, 2, 10, 10));

		JLabel lblMaHD = new JLabel("Mã Hóa Đơn:");
		lblMaHD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(lblMaHD);

		JLabel txtMaHD = new JLabel(maHoaDon);
		txtMaHD.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(txtMaHD);

		JLabel lblMaNV = new JLabel("Mã Nhân Viên:");
		lblMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(lblMaNV);

		JLabel txtMaNV = new JLabel(maNV);
		txtMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(txtMaNV);

		JLabel lblMaNCC = new JLabel("Mã NCC:");
		lblMaNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(lblMaNCC);

		JLabel txtMaNCC = new JLabel(maNCC);
		txtMaNCC.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(txtMaNCC);

		JLabel lblNgayTra = new JLabel("Ngày Trả:");
		lblNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(lblNgayTra);

		JLabel txtNgayTra = new JLabel(ngayTra);
		txtNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(txtNgayTra);
		
		JLabel lblLyDoTra = new JLabel("Lý Do Trả:");
		lblLyDoTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(lblLyDoTra);

		JLabel txtLyDoTra = new JLabel(lyDoTra);
		txtLyDoTra.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelThongTin.add(txtLyDoTra);
		

		// Bảng chi tiết hóa đơn
		JPanel panelChiTiet = new JPanel();
		panelChiTiet.setBounds(10, 354, 745, 300);
		panelChiTiet.setBackground(new Color(255, 245, 228));
		panelChiTiet.setBorder(BorderFactory.createTitledBorder("Chi Tiết Hóa Đơn"));

		contentPane.add(panelChiTiet);

		tableChiTietHoaDon = new JTable();
		tableChiTietHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tableChiTietHoaDon.setRowHeight(25);
		modelChiTiet = new DefaultTableModel(new Object[][] {},
				new String[] { "Tên Thuốc", "Số Lượng", "Đơn Giá", "Thành Tiền" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tableChiTietHoaDon.setModel(modelChiTiet);
		JTableHeader header = tableChiTietHoaDon.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 13));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableChiTietHoaDon.getColumnCount(); i++) {
			tableChiTietHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		panelChiTiet.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(tableChiTietHoaDon);
		scrollPane.setBounds(12, 21, 723, 287);
		panelChiTiet.add(scrollPane);

		// Tổng tiền
		JLabel lblTongTien = new JLabel("Tổng Tiền:");
		lblTongTien.setBounds(20, 654, 100, 30);
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		contentPane.add(lblTongTien);

		JLabel txtTongTien = new JLabel(String.format("%,.0fđ", tongTien(chiTietList)));
		txtTongTien.setBounds(130, 654, 200, 30);
		txtTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		contentPane.add(txtTongTien);

		// Nút đóng
		JButton btnDong = new JButton("Hủy");
		btnDong.setBounds(655, 713, 100, 40);
		btnDong.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnDong.setBackground(new Color(255, 0, 0));
		btnDong.setForeground(Color.WHITE);
		btnDong.setOpaque(true);
		btnDong.setBorderPainted(false);
		contentPane.add(btnDong);

		// Đọc ảnh gốc từ file
		ImageIcon originalIcon = new ImageIcon("image\\logoMTP.png");
		// Lấy hình ảnh từ ImageIcon
		Image originalImage = originalIcon.getImage();
		// Tính toán kích thước mới (50% kích thước gốc)
		int newWidth = originalIcon.getIconWidth() / 2;
		int newHeight = originalIcon.getIconHeight() / 2;
		// Scale ảnh về kích thước mới
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		// Tạo ImageIcon mới từ ảnh đã scale
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		// Tạo JLabel và thiết lập icon đã scale
		JLabel lblLogo = new JLabel(scaledIcon);
		lblLogo.setBounds(10, 10, 238, 135);
		contentPane.add(lblLogo);

		JLabel lblTenCongTy = new JLabel("CÔNG TY CỔ PHẦN DƯỢC PHẨM MTP PHARMACY");
		lblTenCongTy.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblTenCongTy.setBounds(258, 0, 400, 30);
		contentPane.add(lblTenCongTy);

		JPanel panelCongTy = new JPanel();
		panelCongTy.setBounds(258, 30, 497, 95);
		contentPane.add(panelCongTy);
		panelCongTy.setLayout(null);

		JLabel lblMaSoThue = new JLabel("Mã Số Thuế:");
		lblMaSoThue.setBounds(0, 0, 287, 16);
		lblMaSoThue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(lblMaSoThue);

		JLabel txtMaSoThue = new JLabel("0101234567");
		txtMaSoThue.setBounds(141, 0, 374, 16);
		txtMaSoThue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(txtMaSoThue);

		JLabel lblDiaChi = new JLabel("Địa Chỉ:");
		lblDiaChi.setBounds(0, 26, 287, 16);
		lblDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(lblDiaChi);

		JLabel txtDiaChi = new JLabel("12 Nguyễn Văn Bảo, Phường 1, Quận Gò Vấp, TP.HCM");
		txtDiaChi.setBounds(141, 26, 374, 16);
		txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(txtDiaChi);

		JLabel lblSoDienThoai = new JLabel("Số Điện Thoại:");
		lblSoDienThoai.setBounds(0, 52, 287, 16);
		lblSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(lblSoDienThoai);

		JLabel txtSoDienThoai = new JLabel("0988889999");
		txtSoDienThoai.setBounds(141, 52, 374, 16);
		txtSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(txtSoDienThoai);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(0, 78, 287, 16);
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(lblEmail);

		JLabel txtEmail = new JLabel("PharmacyManagement.MTP@gmail.com");
		txtEmail.setBounds(141, 78, 374, 16);
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panelCongTy.add(txtEmail);
		
		
		
		// Load dữ liệu chi tiết hóa đơn
		loadChiTietHoaDon(chiTietList);

//		if (phuongThucThanhToanStr.equals("Chuyển khoản")) {
//			btnDong.setVisible(false);
//		}
		// Sự kiện nút Đóng
		btnDong.addActionListener(e -> dispose());
		
		
	}

	private void loadChiTietHoaDon(java.util.List<Object[]> chiTietList) {
		for (Object[] row : chiTietList) {
			modelChiTiet.addRow(row);
		}
	}

	public static boolean isPrinting() {
		return isPrinting;
	}
	
	private double tongTien(java.util.List<Object[]> chiTietList) {
		double tongTien = 0;
		for (Object[] row : chiTietList) {
			int soLuong = Integer.parseInt(row[1].toString());
			double donGia = Double.parseDouble(row[2].toString().replace("đ", "").replace(",", ""));
			tongTien += soLuong * donGia;
		}
		return tongTien;
	}
}