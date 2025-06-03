package GUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class FrameTrangChu extends JFrame {

	private static final long serialVersionUID = 1L; 
	
	// Frame Thuốc
	private Frame_Thuoc_DanhSachThuoc frame_Thuoc_DanhSachThuoc;
	private Frame_Thuoc_ThietLapGia frame_Thuoc_ThietLapGia;
	private Frame_Thuoc_KiemKho frame_Thuoc_KiemKho;
	private Frame_Thuoc_ThongKe frame_Thuoc_ThongKe;
	// Giao Dịch Thuốc
	private Frame_Thuoc_GiaoDich_BanThuoc frame_Thuoc_GiaoDich_BanThuoc;
	private Frame_Thuoc_GiaoDich_NhapThuoc frame_Thuoc_GiaoDich_NhapThuoc;
	private Frame_Thuoc_GiaoDich_TraNhapThuoc frame_Thuoc_GiaoDich_TraNhapThuoc;
	private Frame_Thuoc_GiaoDich_TraThuoc frame_Thuoc_GiaoDich_TraThuoc;
	private Frame_Thuoc_GiaoDich_DatThuoc frame_Thuoc_GiaoDich_DatThuoc;
	
	// Frame Nhân Viên
	private Frame_NhanVien_DanhSachNhanVien frame_NhanVien_DanhSachNhanVien;
	private Frame_NhanVien_LichLamViec frame_NhanVien_LichLamViec;
	private Frame_NhanVien_ChamCong frame_NhanVien_ChamCong;
	private Frame_NhanVien_BangLuong frame_NhanVien_BangLuong;
	private Frame_NhanVien_ThongKe frame_NhanVien_ThongKe;
	
	// Frame Khách Hàng
	private Frame_KhachHang_DanhSachKhachHang frame_KhachHang_DanhSachKhachHang;
	private Frame_KhachHang_ThongKe frame_KhachHang_ThongKe;
	
	// Frame NCC
	private Frame_NCC_DanhSachNCC frame_NCC_DanhSachNCC;
	private Frame_NCC_ThongKe frame_NCC_ThongKe;
	
	// Frame Hoá Đơn
	private Frame_HoaDon_BanThuoc frame_HoaDon_BanThuoc;
	private Frame_HoaDon_DatThuoc frame_HoaDon_DatThuoc;
	private Frame_HoaDon_NhapThuoc frame_HoaDon_NhapThuoc;
	private Frame_HoaDon_TraThuoc frame_HoaDon_TraThuoc;
	private Frame_HoaDon_TraNhapThuoc frame_HoaDon_TraNhapThuoc;
	
	// Frame Doanh Thu
	private Frame_TaiChinh_DoanhThu frame_TaiChinh_DoanhThu;
	private Frame_TaiChinh_LSGD frame_DoanhThu_NganSach;
	
	
	private Frame_PhanMem_TTPM frame_PhanMem_TTPM;
	
	private Frame_TroGiup_HuongDanSuDung frame_TroGiup_HDSD;
	
	public String maNV, chucVu;
	private JMenuBar menuBar;
	private JMenu btnThuoc, btnHoaDon;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameTrangChu frame = new FrameTrangChu(maNV, chucVu);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public FrameTrangChu(String maNV, String chucVu) {
		setFont(new Font("Segoe UI", Font.BOLD, 13));
		setIconImage(Toolkit.getDefaultToolkit().getImage("icon\\pharmacy.png"));
		setTitle("Hệ Thống Quản Lý Hiệu Thuốc Tây");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1003, 630);
		setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel btnThongKeSanPham = new JLabel("");
		btnThongKeSanPham.setIcon(new ImageIcon("image\\BG_Dashboard.jpg"));
		getContentPane().add(btnThongKeSanPham, BorderLayout.WEST);
		
		menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.setBackground(new Color(192, 192, 192));
		
		JMenu btnHeThong = new JMenu("Hệ Thống");
		btnHeThong.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnHeThong.setIcon(new ImageIcon("icon\\system.png"));
		menuBar.add(btnHeThong);
		
		JMenuItem btnTrangChu = new JMenuItem("Trang Chủ");
		btnTrangChu.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnTrangChu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
		        getContentPane().add(btnThongKeSanPham, BorderLayout.CENTER);
		        revalidate();
		        repaint();
			}
		});
		btnTrangChu.setIcon(new ImageIcon("icon\\homepage.png"));
		btnHeThong.add(btnTrangChu);
		
		JMenuItem btnDangXuat = new JMenuItem("Đăng Xuất");
		btnDangXuat.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(FrameTrangChu.this, "Bạn có muốn đăng xuất ứng dụng không?", "Lựa chọn", JOptionPane.YES_NO_OPTION);
		        if (a == JOptionPane.YES_OPTION) {
		        	dispose();
			        FrameDangNhap Framelogin = new FrameDangNhap();
			        Framelogin.setVisible(true); 
		        }
				
			}
		});
		btnDangXuat.setIcon(new ImageIcon("icon\\signout.png"));
		btnHeThong.add(btnDangXuat);
		
		JMenuItem btnThoat = new JMenuItem("Thoát", KeyEvent.VK_W);
		btnThoat.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnThoat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(FrameTrangChu.this, "Bạn có muốn thoát ứng dụng không?", "Lựa chọn", JOptionPane.YES_NO_OPTION);
		        if (a == JOptionPane.YES_OPTION) {
		        	dispose();
		        }
			}
		});
		btnThoat.setIcon(new ImageIcon("icon\\exit.png"));
		btnHeThong.add(btnThoat);
		
		btnThuoc = new JMenu("Thuốc");
		btnThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnThuoc.setIcon(new ImageIcon("icon\\medicine.png"));
		menuBar.add(btnThuoc);
		
		JMenu btn_Thuoc_GiaoDich = new JMenu("Giao Dịch");
		btn_Thuoc_GiaoDich.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_Thuoc_GiaoDich.setIcon(new ImageIcon("icon\\transaction.png"));
		btnThuoc.add(btn_Thuoc_GiaoDich);
		
		JMenuItem btn_Thuoc_GiaoDich_DatThuoc = new JMenuItem("Đặt Thuốc");
		btn_Thuoc_GiaoDich_DatThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_GiaoDich_DatThuoc = new Frame_Thuoc_GiaoDich_DatThuoc(maNV);
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_GiaoDich_DatThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_GiaoDich.add(btn_Thuoc_GiaoDich_DatThuoc);
		btn_Thuoc_GiaoDich_DatThuoc.setIcon(new ImageIcon("icon\\XL_orders.png"));
		btn_Thuoc_GiaoDich_DatThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		JMenuItem btn_Thuoc_GiaoDich_BanThuoc = new JMenuItem("Bán Thuốc");
		btn_Thuoc_GiaoDich.add(btn_Thuoc_GiaoDich_BanThuoc);
		btn_Thuoc_GiaoDich_BanThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_GiaoDich_BanThuoc = new Frame_Thuoc_GiaoDich_BanThuoc(maNV);
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_GiaoDich_BanThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_GiaoDich_BanThuoc.setIcon(new ImageIcon("icon\\sell.png"));
		btn_Thuoc_GiaoDich_BanThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		JMenuItem btn_Thuoc_GiaoDich_TraThuoc = new JMenuItem("Trả Thuốc");
		btn_Thuoc_GiaoDich.add(btn_Thuoc_GiaoDich_TraThuoc);
		btn_Thuoc_GiaoDich_TraThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_Thuoc_GiaoDich_TraThuoc.setIcon(new ImageIcon("icon\\return.png"));
		
		JMenuItem btn_Thuoc_GiaoDich_NhapThuoc = new JMenuItem("Nhập Thuốc");
		btn_Thuoc_GiaoDich.add(btn_Thuoc_GiaoDich_NhapThuoc);
		btn_Thuoc_GiaoDich_NhapThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_GiaoDich_NhapThuoc = new Frame_Thuoc_GiaoDich_NhapThuoc(maNV);
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_GiaoDich_NhapThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_GiaoDich_NhapThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_Thuoc_GiaoDich_NhapThuoc.setIcon(new ImageIcon("icon\\import_goods.png"));
		
		JMenuItem btn_Thuoc_GiaoDich_TraNhapThuoc = new JMenuItem("Trả Nhập Thuốc");
		btn_Thuoc_GiaoDich.add(btn_Thuoc_GiaoDich_TraNhapThuoc);
		btn_Thuoc_GiaoDich_TraNhapThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_GiaoDich_TraNhapThuoc = new Frame_Thuoc_GiaoDich_TraNhapThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_GiaoDich_TraNhapThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_GiaoDich_TraNhapThuoc.setIcon(new ImageIcon("icon\\return_goods.png"));
		btn_Thuoc_GiaoDich_TraNhapThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_Thuoc_GiaoDich_TraThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_GiaoDich_TraThuoc = new Frame_Thuoc_GiaoDich_TraThuoc(maNV);
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_GiaoDich_TraThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		
		JMenuItem btn_Thuoc_DSThuoc = new JMenuItem("Danh Sách");
		btnThuoc.add(btn_Thuoc_DSThuoc);
		btn_Thuoc_DSThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_Thuoc_DSThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
	            btnThuoc.setForeground(Color.RED);
	            frame_Thuoc_DanhSachThuoc = new Frame_Thuoc_DanhSachThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_DanhSachThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_DSThuoc.setIcon(new ImageIcon("icon\\catalog.png"));
		
		JMenuItem btn_Thuoc_ThietLapGia = new JMenuItem("Thiết Lập Giá");
		btn_Thuoc_ThietLapGia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_ThietLapGia = new Frame_Thuoc_ThietLapGia();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_ThietLapGia, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_ThietLapGia.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_Thuoc_ThietLapGia.setIcon(new ImageIcon("icon\\price.png"));
		btnThuoc.add(btn_Thuoc_ThietLapGia);
		
		JMenuItem btn_Thuoc_KiemKho = new JMenuItem("Kiểm Kho");
		btn_Thuoc_KiemKho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_KiemKho = new Frame_Thuoc_KiemKho();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_KiemKho, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_KiemKho.setIcon(new ImageIcon("icon\\check_list.png"));
		btn_Thuoc_KiemKho.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnThuoc.add(btn_Thuoc_KiemKho);
		
		JMenuItem btn_Thuoc_ThongKe = new JMenuItem("Thống Kê");
		btn_Thuoc_ThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnThuoc.setForeground(Color.RED);
				frame_Thuoc_ThongKe = new Frame_Thuoc_ThongKe();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_Thuoc_ThongKe, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_Thuoc_ThongKe.setIcon(new ImageIcon("icon\\static_product.png"));
		btn_Thuoc_ThongKe.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnThuoc.add(btn_Thuoc_ThongKe);
		
		JMenu btnNhanVien = new JMenu("Nhân Viên");
		btnNhanVien.setIcon(new ImageIcon("icon\\nv.png"));
		btnNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 13));
		menuBar.add(btnNhanVien);
		
		JMenuItem btn_NV_DSNV = new JMenuItem("Danh Sách");
		btnNhanVien.add(btn_NV_DSNV);
		btn_NV_DSNV.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_NV_DSNV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNhanVien.setForeground(Color.RED);
				frame_NhanVien_DanhSachNhanVien = new Frame_NhanVien_DanhSachNhanVien();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NhanVien_DanhSachNhanVien, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NV_DSNV.setIcon(new ImageIcon("icon\\category.png"));
		
		JMenuItem btn_NV_LichLamViec = new JMenuItem("Lịch làm việc");
		btn_NV_LichLamViec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNhanVien.setForeground(Color.RED);
				frame_NhanVien_LichLamViec = new Frame_NhanVien_LichLamViec();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NhanVien_LichLamViec, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NV_LichLamViec.setIcon(new ImageIcon("icon\\schedule.png"));
		btn_NV_LichLamViec.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnNhanVien.add(btn_NV_LichLamViec);
		
		JMenuItem btn_NV_ChamCong = new JMenuItem("Chấm Công");
		btn_NV_ChamCong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNhanVien.setForeground(Color.RED);
				frame_NhanVien_ChamCong = new Frame_NhanVien_ChamCong();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NhanVien_ChamCong, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NV_ChamCong.setIcon(new ImageIcon("icon\\timeskeeping.png"));
		btn_NV_ChamCong.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnNhanVien.add(btn_NV_ChamCong);
		
		JMenuItem btn_NV_BangLuong = new JMenuItem("Bảng Lương");
		btn_NV_BangLuong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNhanVien.setForeground(Color.RED);
				frame_NhanVien_BangLuong = new Frame_NhanVien_BangLuong();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NhanVien_BangLuong, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NV_BangLuong.setIcon(new ImageIcon("icon\\payroll.png"));
		btn_NV_BangLuong.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnNhanVien.add(btn_NV_BangLuong);
		
		JMenuItem btn_NV_ThongKe = new JMenuItem("Thống Kê");
		btn_NV_ThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNhanVien.setForeground(Color.RED);
				frame_NhanVien_ThongKe = new Frame_NhanVien_ThongKe();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NhanVien_ThongKe, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NV_ThongKe.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_NV_ThongKe.setIcon(new ImageIcon("icon\\static_employee.png"));
		btnNhanVien.add(btn_NV_ThongKe);
		
		JMenu btnKhachHang = new JMenu("Khách Hàng");
		btnKhachHang.setIcon(new ImageIcon("icon\\partner.png"));
		btnKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));
		menuBar.add(btnKhachHang);
		
		JMenuItem btn_KhachHang_DanhSachKhachHang = new JMenuItem("Danh Sách");
		btnKhachHang.add(btn_KhachHang_DanhSachKhachHang);
		btn_KhachHang_DanhSachKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_KhachHang_DanhSachKhachHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnKhachHang.setForeground(Color.RED);
				frame_KhachHang_DanhSachKhachHang = new Frame_KhachHang_DanhSachKhachHang();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_KhachHang_DanhSachKhachHang, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		
		btn_KhachHang_DanhSachKhachHang.setIcon(new ImageIcon("icon\\customer.png"));
		
		JMenuItem btn_KhachHang_ThongKe = new JMenuItem("Thống Kê");
		btnKhachHang.add(btn_KhachHang_ThongKe);
		btn_KhachHang_ThongKe.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_KhachHang_ThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnKhachHang.setForeground(Color.RED);
				frame_KhachHang_ThongKe = new Frame_KhachHang_ThongKe();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_KhachHang_ThongKe, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_KhachHang_ThongKe.setIcon(new ImageIcon("icon\\static_customer.png"));
		
		JMenu btnNCC = new JMenu("Nhà Cung Cấp");
		btnNCC.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnNCC.setIcon(new ImageIcon("icon\\factory.png"));
		menuBar.add(btnNCC);
		
		JMenuItem btn_NCC_DanhSachNCC = new JMenuItem("Danh Sách");
		btnNCC.add(btn_NCC_DanhSachNCC);
		btn_NCC_DanhSachNCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNCC.setForeground(Color.RED);
				frame_NCC_DanhSachNCC = new Frame_NCC_DanhSachNCC();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NCC_DanhSachNCC, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NCC_DanhSachNCC.setIcon(new ImageIcon("icon\\supplier.png"));
		btn_NCC_DanhSachNCC.setFont(new Font("Segoe UI", Font.BOLD, 13));
		
		JMenuItem btn_NCC_ThongKe = new JMenuItem("Thống Kê");
		btn_NCC_ThongKe.setIcon(new ImageIcon("icon\\static_supplier.png"));
		btn_NCC_ThongKe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNCC.setForeground(Color.RED);
				frame_NCC_ThongKe = new Frame_NCC_ThongKe();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_NCC_ThongKe, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_NCC_ThongKe.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnNCC.add(btn_NCC_ThongKe);
		
		btnHoaDon = new JMenu("Hóa Đơn");
		btnHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnHoaDon.setIcon(new ImageIcon("icon\\bill.png"));
		menuBar.add(btnHoaDon);
		
		JMenuItem btn_HoaDon_BanThuoc = new JMenuItem("Bán Thuốc");
		btn_HoaDon_BanThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_HoaDon_BanThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnHoaDon.setForeground(Color.RED);
				frame_HoaDon_BanThuoc = new Frame_HoaDon_BanThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_HoaDon_BanThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_HoaDon_BanThuoc.setIcon(new ImageIcon("icon\\bill_stay.png"));
		btnHoaDon.add(btn_HoaDon_BanThuoc);
		
		JMenuItem btn_HoaDon_DatThuoc = new JMenuItem("Đặt Thuốc");
		btn_HoaDon_DatThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_HoaDon_DatThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnHoaDon.setForeground(Color.RED);
				frame_HoaDon_DatThuoc = new Frame_HoaDon_DatThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_HoaDon_DatThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_HoaDon_DatThuoc.setIcon(new ImageIcon("icon\\bill_away.png"));
		btnHoaDon.add(btn_HoaDon_DatThuoc);
		
		JMenuItem btn_HoaDon_NhapThuoc = new JMenuItem("Nhập Thuốc");
		btn_HoaDon_NhapThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_HoaDon_NhapThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnHoaDon.setForeground(Color.RED);
				frame_HoaDon_NhapThuoc = new Frame_HoaDon_NhapThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_HoaDon_NhapThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_HoaDon_NhapThuoc.setIcon(new ImageIcon("icon\\bill_import.png"));
		btnHoaDon.add(btn_HoaDon_NhapThuoc);
		
		JMenuItem btn_HoaDon_TraThuoc = new JMenuItem("Trả Thuốc");
		btn_HoaDon_TraThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_HoaDon_TraThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnHoaDon.setForeground(Color.RED);
				frame_HoaDon_TraThuoc = new Frame_HoaDon_TraThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_HoaDon_TraThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_HoaDon_TraThuoc.setIcon(new ImageIcon("icon\\bill_cancel.png"));
		btnHoaDon.add(btn_HoaDon_TraThuoc);
		
		JMenuItem btn_HoaDon_TraNhapThuoc = new JMenuItem("Trả Nhập Thuốc");
		btn_HoaDon_TraNhapThuoc.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_HoaDon_TraNhapThuoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnHoaDon.setForeground(Color.RED);
				frame_HoaDon_TraNhapThuoc = new Frame_HoaDon_TraNhapThuoc();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_HoaDon_TraNhapThuoc, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_HoaDon_TraNhapThuoc.setIcon(new ImageIcon("icon\\bill_return.png"));
		btnHoaDon.add(btn_HoaDon_TraNhapThuoc);
		
		
		JMenu btnTaiChinh = new JMenu("Tài Chính");
		btnTaiChinh.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnTaiChinh.setIcon(new ImageIcon("icon\\budget.png"));
		menuBar.add(btnTaiChinh);
		
		JMenuItem btn_TaiChinh_DoanhThu = new JMenuItem("Doanh Thu");
		btnTaiChinh.add(btn_TaiChinh_DoanhThu);
		btn_TaiChinh_DoanhThu.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_TaiChinh_DoanhThu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnNCC.setForeground(Color.RED);
				frame_TaiChinh_DoanhThu = new Frame_TaiChinh_DoanhThu();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_TaiChinh_DoanhThu, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_TaiChinh_DoanhThu.setIcon(new ImageIcon("icon\\static_bill.png"));
		
		JMenuItem btn_TaiChinh_LSGD = new JMenuItem("Lịch Sử Giao Dịch");
		btn_TaiChinh_LSGD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnTaiChinh.setForeground(Color.RED);
				frame_DoanhThu_NganSach = new Frame_TaiChinh_LSGD();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_DoanhThu_NganSach, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_TaiChinh_LSGD.setIcon(new ImageIcon("icon\\history_transaction.png"));
		btn_TaiChinh_LSGD.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnTaiChinh.add(btn_TaiChinh_LSGD);
		
		JMenu btnPhanMem = new JMenu("Phần Mềm");
		btnPhanMem.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnPhanMem.setIcon(new ImageIcon("icon\\info.png"));
		menuBar.add(btnPhanMem);
		
		JMenuItem btn_PhanMem_ThongTin = new JMenuItem("Thông Tin");
		btn_PhanMem_ThongTin.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_PhanMem_ThongTin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnPhanMem.setForeground(Color.RED);
				frame_PhanMem_TTPM = new Frame_PhanMem_TTPM();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_PhanMem_TTPM, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btn_PhanMem_ThongTin.setIcon(new ImageIcon("icon\\info_phanmem.png"));
		btnPhanMem.add(btn_PhanMem_ThongTin);
		
		JMenuItem btn_PhanMem_DuongDan = new JMenuItem("Đường Dẫn");
		btn_PhanMem_DuongDan.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btn_PhanMem_DuongDan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(java.net.URI.create("https://github.com/Esonip/Pharmacy-Mangement-Program"));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btn_PhanMem_DuongDan.setIcon(new ImageIcon("icon\\link.png"));
		btnPhanMem.add(btn_PhanMem_DuongDan);
		
		JMenu btnTroGiup = new JMenu("Trợ Giúp");
		btnTroGiup.setIcon(new ImageIcon("icon\\technical_support.png"));
		btnTroGiup.setFont(new Font("Segoe UI", Font.BOLD, 13));
		menuBar.add(btnTroGiup);
		
		JMenuItem btnHDSD = new JMenuItem("Hướng Dẫn Sử Dụng");
		btnHDSD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMenuColors();
				btnTroGiup.setForeground(Color.RED);
				frame_TroGiup_HDSD = new Frame_TroGiup_HuongDanSuDung();
				getContentPane().removeAll();
				getContentPane().add(menuBar, BorderLayout.NORTH);
				getContentPane().add(frame_TroGiup_HDSD, BorderLayout.CENTER);
				revalidate();
				repaint();
			}
		});
		btnHDSD.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnHDSD.setIcon(new ImageIcon("icon\\guidelines.png"));
		btnTroGiup.add(btnHDSD);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		menuBar.add(panel);
		panel.setLayout(null);
		
		JLabel lblUsername = new JLabel(chucVu + " | " + maNV);
		lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 13));
		if ("Nhân Viên".equals(chucVu))
			lblUsername.setIcon(new ImageIcon("icon\\employee.png"));
		else
			lblUsername.setIcon(new ImageIcon("icon\\manager.png"));
		lblUsername.setBounds(356, 0, 262, 39);
		panel.add(lblUsername);
		
		if ("Nhân Viên".equals(chucVu)) {
			// Thuốc
			btn_Thuoc_DSThuoc.setEnabled(false);
			btn_Thuoc_ThietLapGia.setEnabled(false);
			btn_Thuoc_ThongKe.setEnabled(false);
			btn_Thuoc_GiaoDich_TraNhapThuoc.setEnabled(false);
			// Nhân Viên
			btnNhanVien.setEnabled(false);
			// Khách Hàng
			btn_KhachHang_ThongKe.setEnabled(false);
			// NCC
			btnNCC.setEnabled(false);
			// Tài Chính
			btnTaiChinh.setEnabled(false);
			
	    }
		
		
		this.setVisible(true);
	}
	
	
	
	// Hàm reset màu chữ của menu
	private void resetMenuColors() {
	    Component[] components = menuBar.getComponents();
	    for (Component comp : components) {
	        if (comp instanceof JMenu) {
	            ((JMenu) comp).setForeground(Color.BLACK); // Màu mặc định
	        }
	    }
	}
	
	// Hàm gọi màu chữ của menu
	public void setMenuColor(JMenu menu) {
		btnHoaDon.setForeground(Color.BLACK);
		menu.setForeground(Color.RED);
	}
	
	// Trong Frame_TrangChu.java
	public JMenu getBtnThuoc() {
	    return btnThuoc;
	}
}
