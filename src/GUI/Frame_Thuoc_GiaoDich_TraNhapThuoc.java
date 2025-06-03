package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JTextArea;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.awt.event.ActionEvent;

import DAO.TraNhapThuocDAO;


public class Frame_Thuoc_GiaoDich_TraNhapThuoc extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Frame_Thuoc_GiaoDich_TraNhapThuoc frame = new Frame_Thuoc_GiaoDich_TraNhapThuoc();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	private final Color MAIN_COLOR = new Color(254, 222, 192);
	private JTextField txtNhapTimKiem;
	private JTable tableDanhSachPhieuNhap;
	private JTable tableChiTietPhieuNhap;
	private JTextField txtMaPhieuNhap;
	private JTextField txtMaTra;
	private JTextField txtMaNV;
	private JTextField txtMaNCC;
	private JTextField txtNgayNhap;
	private JTextField txtTongTien;
	private JTable tableChiTietTraThuocNhap;
	private JComboBox<String> comboBoxLoaiTimKiem;
	private JButton btnTimKiem;
	private JButton btnXoa;
	private JSpinner spinnerSoLuongTra;
	private JButton btnThem;
	private JButton btnTraThuoc;
	private JButton btnLamMoi;
	private JComboBox<String> comboBoxPhuongThuc;
	private JTextArea textAreaLiDoTra;
	private DefaultTableModel modelDanhSachPhieuNhap;
	private DefaultTableModel modelChiTietPhieuNhap;
	private DefaultTableModel modelChiTietTraThuocNhap;
	private TraNhapThuocDAO traNhapThuocDAO;
	private JTextField txtMaThuChi;
	/**
	 * Create the frame.
	 */
	public Frame_Thuoc_GiaoDich_TraNhapThuoc() {
		traNhapThuocDAO = new TraNhapThuocDAO();
		
		setPreferredSize(new Dimension(1550, 797));
        setBackground(new Color(255, 164, 119));
        setLayout(null);
        
        //Panel tìm kiếm hóa đơn
        TitledBorder titledBorderTimKiem = BorderFactory.createTitledBorder(
        	    new LineBorder(Color.WHITE, 2),
        	    "Tìm kiếm hóa đơn",
        	    TitledBorder.CENTER,
        	    TitledBorder.TOP,
        	    new Font("Arial", Font.BOLD, 18), // Tăng kích cỡ chữ
        	    Color.WHITE
        	);
        JPanel panelTimKiem = new JPanel();
        panelTimKiem.setBackground(new Color(255, 128, 64));
        panelTimKiem.setBounds(21, 10, 661, 176);
        panelTimKiem.setBorder(titledBorderTimKiem);
        add(panelTimKiem);
        panelTimKiem.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Loại tìm kiếm:");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setBounds(33, 45, 145, 34);
        panelTimKiem.add(lblNewLabel);
        
        JLabel lblNhpTmKim = new JLabel("Nhập tìm kiếm:");
        lblNhpTmKim.setForeground(Color.WHITE);
        lblNhpTmKim.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNhpTmKim.setBackground(Color.WHITE);
        lblNhpTmKim.setBounds(33, 104, 145, 34);
        panelTimKiem.add(lblNhpTmKim);
        
        comboBoxLoaiTimKiem = new JComboBox();
        comboBoxLoaiTimKiem.setFont(new Font("Tahoma", Font.BOLD, 18));
        comboBoxLoaiTimKiem.setModel(new DefaultComboBoxModel(new String[] {"Mã phiếu nhập", "Mã nhân viên", "Mã nhà cung cấp"}));
        comboBoxLoaiTimKiem.setBounds(208, 43, 242, 42);
        panelTimKiem.add(comboBoxLoaiTimKiem);
        
        txtNhapTimKiem = new JTextField();
        txtNhapTimKiem.setFont(new Font("Tahoma", Font.BOLD, 18));
        txtNhapTimKiem.setBounds(208, 104, 242, 42);
        panelTimKiem.add(txtNhapTimKiem);
        txtNhapTimKiem.setColumns(10);
        
        btnTimKiem = new JButton("Tìm Kiếm");
        btnTimKiem.setBackground(new Color(255, 255, 255));
        btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnTimKiem.setBounds(505, 43, 125, 103);
        panelTimKiem.add(btnTimKiem);
        btnTimKiem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timKiemPhieuNhap();
            }
        });
        
        //Panel danh sách phiếu nhập
        TitledBorder titledBorderDanhSach = BorderFactory.createTitledBorder(
        	    new LineBorder(Color.WHITE, 2),
        	    "Danh sách phiếu nhập",
        	    TitledBorder.CENTER,
        	    TitledBorder.TOP,
        	    new Font("Arial", Font.BOLD, 18), // Tăng kích cỡ chữ
        	    Color.WHITE
        	);
        JPanel panelDanhSach = new JPanel();
        panelDanhSach.setBackground(new Color(255, 128, 64));
        panelDanhSach.setBounds(21, 207, 661, 306);
        panelDanhSach.setBorder(titledBorderDanhSach);
        add(panelDanhSach);
        panelDanhSach.setLayout(null);
        
        JScrollPane scrollPaneDanhSachPhieuNhap = new JScrollPane();
        scrollPaneDanhSachPhieuNhap.setBounds(10, 31, 641, 265);
        panelDanhSach.add(scrollPaneDanhSachPhieuNhap);
        
        tableDanhSachPhieuNhap = new JTable();
        tableDanhSachPhieuNhap.setFont(new Font("Tahoma", Font.PLAIN, 16));
        modelDanhSachPhieuNhap = new DefaultTableModel(
        		new Object[][] {},
        		new String[] {
					"Mã PNT", "Mã NV", "Mã NCC", "Ngày Nhập", "Phương Thức"
				}
        		);
        tableDanhSachPhieuNhap.setModel(modelDanhSachPhieuNhap);
        tableDanhSachPhieuNhap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tableDanhSachPhieuNhap.getTableHeader().setBackground(new Color(255, 128, 64));
        tableDanhSachPhieuNhap.setRowHeight(30); // Tăng chiều cao hàng
        tableDanhSachPhieuNhap.addMouseListener(new MouseAdapter (){
        	 @Override
             public void mouseClicked(MouseEvent e) {
                 hienThiChiTietPhieuNhap();
             }
        });
        
        scrollPaneDanhSachPhieuNhap.setViewportView(tableDanhSachPhieuNhap);
        
        //Panel chi tiết phiếu nhập
        TitledBorder titledBorderChiTiet = BorderFactory.createTitledBorder(
        	    new LineBorder(Color.WHITE, 2),
        	    "Chi tiết phiếu nhập",
        	    TitledBorder.CENTER,
        	    TitledBorder.TOP,
        	    new Font("Arial", Font.BOLD, 18), // Tăng kích cỡ chữ
        	    Color.WHITE
        	);
        JPanel panelChiTietPhieuNhap = new JPanel();
        panelChiTietPhieuNhap.setBackground(new Color(255, 128, 64));
        panelChiTietPhieuNhap.setBounds(21, 523, 661, 264);
        panelChiTietPhieuNhap.setBorder(titledBorderChiTiet);
        add(panelChiTietPhieuNhap);
        panelChiTietPhieuNhap.setLayout(null);
        
        JScrollPane scrollPaneChiTietPhieuNhap = new JScrollPane();
        scrollPaneChiTietPhieuNhap.setBounds(10, 24, 641, 230);
        panelChiTietPhieuNhap.add(scrollPaneChiTietPhieuNhap);
        
        tableChiTietPhieuNhap = new JTable();
        modelChiTietPhieuNhap = new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"M\u00E3 thu\u1ED1c", "T\u00EAn Thu\u1ED1c", "S\u1ED1 L\u01B0\u1EE3ng", "\u0110\u01A1n Gi\u00E1", "Th\u00E0nh Ti\u1EC1n"
			}
		);
        tableChiTietPhieuNhap.setModel(modelChiTietPhieuNhap);
        tableChiTietPhieuNhap.setFont(new Font("Tahoma", Font.PLAIN, 18));
        tableChiTietPhieuNhap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        tableChiTietPhieuNhap.getTableHeader().setBackground(new Color(255, 128, 64));
        tableChiTietPhieuNhap.setRowHeight(30); // Tăng chiều cao hàng
        tableChiTietPhieuNhap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                chonThuocDeTra();
            }
        });
        scrollPaneChiTietPhieuNhap.setViewportView(tableChiTietPhieuNhap);
        
        //Panel thông tin phiếu nhập
        TitledBorder titledBorderThongTin = BorderFactory.createTitledBorder(
			    new LineBorder(Color.WHITE, 2),
			    "Thông tin hóa đơn",
			    TitledBorder.CENTER,
			    TitledBorder.TOP,
			    new Font("Arial", Font.BOLD, 18), // Tăng kích cỡ chữ
			    Color.WHITE
			);
        JPanel panelThongTinPhieuNhap = new JPanel();
        panelThongTinPhieuNhap.setBackground(new Color(255, 128, 64));
        panelThongTinPhieuNhap.setBounds(731, 10, 778, 361);
        panelThongTinPhieuNhap.setBorder(titledBorderThongTin);
        add(panelThongTinPhieuNhap);
        panelThongTinPhieuNhap.setLayout(null);
        
        JLabel lblMPhiuNhp = new JLabel("Mã phiếu nhập:");
        lblMPhiuNhp.setForeground(Color.WHITE);
        lblMPhiuNhp.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMPhiuNhp.setBackground(Color.WHITE);
        lblMPhiuNhp.setBounds(20, 48, 145, 34);
        panelThongTinPhieuNhap.add(lblMPhiuNhp);
        
        JLabel lblNgyNhp = new JLabel("Ngày nhập:");
        lblNgyNhp.setForeground(Color.WHITE);
        lblNgyNhp.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNgyNhp.setBackground(Color.WHITE);
        lblNgyNhp.setBounds(20, 108, 145, 34);
        panelThongTinPhieuNhap.add(lblNgyNhp);
        
        JLabel lblMNhCung = new JLabel("Mã nhà cung cấp:");
        lblMNhCung.setForeground(Color.WHITE);
        lblMNhCung.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMNhCung.setBackground(Color.WHITE);
        lblMNhCung.setBounds(419, 48, 200, 34);
        panelThongTinPhieuNhap.add(lblMNhCung);
        
        JLabel lblM = new JLabel("Mã nhân viên:");
        lblM.setForeground(Color.WHITE);
        lblM.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblM.setBackground(Color.WHITE);
        lblM.setBounds(419, 170, 145, 34);
        panelThongTinPhieuNhap.add(lblM);
        
        JLabel lblMaTraNhapThuoc = new JLabel("Mã trả:");
        lblMaTraNhapThuoc.setForeground(Color.WHITE);
        lblMaTraNhapThuoc.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblMaTraNhapThuoc.setBackground(Color.WHITE);
        lblMaTraNhapThuoc.setBounds(20, 170, 145, 34);
        panelThongTinPhieuNhap.add(lblMaTraNhapThuoc);
        
        txtMaPhieuNhap = new JTextField();
        txtMaPhieuNhap.setEditable(false);
        txtMaPhieuNhap.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtMaPhieuNhap.setColumns(10);
        txtMaPhieuNhap.setBounds(172, 44, 237, 42);
        panelThongTinPhieuNhap.add(txtMaPhieuNhap);
        
        txtMaTra = new JTextField();
        txtMaTra.setEditable(false);
        txtMaTra.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtMaTra.setColumns(10);
        txtMaTra.setBounds(172, 166, 242, 42);
        panelThongTinPhieuNhap.add(txtMaTra);
        txtMaTra.setText(traNhapThuocDAO.getNextMaTraNhap());
        
        txtMaNV = new JTextField();
        txtMaNV.setEditable(false);
        txtMaNV.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtMaNV.setColumns(10);
        txtMaNV.setBounds(592, 166, 164, 42);
        panelThongTinPhieuNhap.add(txtMaNV);
        
        txtMaNCC = new JTextField();
        txtMaNCC.setEditable(false);
        txtMaNCC.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtMaNCC.setColumns(10);
        txtMaNCC.setBounds(592, 44, 164, 42);
        panelThongTinPhieuNhap.add(txtMaNCC);
        
        textAreaLiDoTra = new JTextArea();
        textAreaLiDoTra.setFont(new Font("Calibri", Font.BOLD, 15));
        textAreaLiDoTra.setBounds(172, 229, 242, 57);
        panelThongTinPhieuNhap.add(textAreaLiDoTra);
        
        JLabel lblLDoTr = new JLabel("Lí do trả:");
        lblLDoTr.setForeground(Color.WHITE);
        lblLDoTr.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblLDoTr.setBackground(Color.WHITE);
        lblLDoTr.setBounds(20, 241, 145, 34);
        panelThongTinPhieuNhap.add(lblLDoTr);
        
        txtNgayNhap = new JTextField();
        txtNgayNhap.setEditable(false);
        txtNgayNhap.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtNgayNhap.setColumns(10);
        txtNgayNhap.setBounds(172, 104, 242, 42);
        // Định dạng ngày tháng năm
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);
        txtNgayNhap.setText(formattedDate);
        panelThongTinPhieuNhap.add(txtNgayNhap);
        
        JLabel lblPhngThc = new JLabel("Phương thức:");
        lblPhngThc.setForeground(Color.WHITE);
        lblPhngThc.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblPhngThc.setBackground(Color.WHITE);
        lblPhngThc.setBounds(20, 298, 145, 34);
        panelThongTinPhieuNhap.add(lblPhngThc);
        
        comboBoxPhuongThuc = new JComboBox();
        comboBoxPhuongThuc.setModel(new DefaultComboBoxModel(new String[] {"Tiền mặt", "Chuyển khoản"}));
        comboBoxPhuongThuc.setFont(new Font("Tahoma", Font.BOLD, 18));
        comboBoxPhuongThuc.setBounds(172, 296, 242, 42);
        panelThongTinPhieuNhap.add(comboBoxPhuongThuc);
        
        JLabel lbMaThuChi = new JLabel("Mã thu chi:");
        lbMaThuChi.setForeground(Color.WHITE);
        lbMaThuChi.setFont(new Font("Tahoma", Font.BOLD, 18));
        lbMaThuChi.setBackground(Color.WHITE);
        lbMaThuChi.setBounds(419, 252, 145, 34);
        panelThongTinPhieuNhap.add(lbMaThuChi);
        
        txtMaThuChi = new JTextField();
        txtMaThuChi.setFont(new Font("Tahoma", Font.PLAIN, 18));
        txtMaThuChi.setEditable(false);
        txtMaThuChi.setColumns(10);
        txtMaThuChi.setBounds(592, 252, 164, 42);
        panelThongTinPhieuNhap.add(txtMaThuChi);
        txtMaThuChi.setText(traNhapThuocDAO.getNextMaThuChi());
        
        //Panel thông tin trả thuốc nhập
        TitledBorder titledBorderTraNhap = BorderFactory.createTitledBorder(
			    new LineBorder(Color.WHITE, 2),
			    "Chi tiết trả thuốc nhập",
			    TitledBorder.CENTER,
			    TitledBorder.TOP,
			    new Font("Arial", Font.BOLD, 18), // Tăng kích cỡ chữ
			    Color.WHITE
			);
        JPanel panelChiTietTra = new JPanel();
        panelChiTietTra.setBackground(new Color(255, 128, 64));
        panelChiTietTra.setBorder(titledBorderTraNhap);
        panelChiTietTra.setBounds(731, 381, 778, 286);
        add(panelChiTietTra);
        panelChiTietTra.setLayout(null);
        
        JLabel lblSLngTr = new JLabel("Số lượng trả:");
        lblSLngTr.setForeground(Color.WHITE);
        lblSLngTr.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblSLngTr.setBackground(Color.WHITE);
        lblSLngTr.setBounds(10, 28, 145, 34);
        panelChiTietTra.add(lblSLngTr);
        
        spinnerSoLuongTra = new JSpinner();
        spinnerSoLuongTra.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));
        spinnerSoLuongTra.setFont(new Font("Tahoma", Font.BOLD, 16));
        spinnerSoLuongTra.setBounds(137, 32, 97, 34);
        panelChiTietTra.add(spinnerSoLuongTra);
        
        JButton btnThem = new JButton("Thêm");
        btnThem.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnThem.setBackground(new Color(255, 255, 255));
        btnThem.setBounds(270, 31, 112, 34);
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themVaoDanhSachTra();
            }
        });
        panelChiTietTra.add(btnThem);
        
        JScrollPane scrollPaneChiTietTraThuocNhap = new JScrollPane();
        scrollPaneChiTietTraThuocNhap.setBounds(20, 72, 737, 185);
        panelChiTietTra.add(scrollPaneChiTietTraThuocNhap);
        
        tableChiTietTraThuocNhap = new JTable();
        modelChiTietTraThuocNhap = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 thu\u1ED1c", "T\u00EAn thu\u1ED1c ", "S\u1ED1 l\u01B0\u1EE3ng", "\u0110\u01A1n gi\u00E1", "Th\u00E0nh ti\u1EC1n"
			}
		);
        tableChiTietTraThuocNhap.setModel(modelChiTietTraThuocNhap);
        tableChiTietTraThuocNhap.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tableChiTietTraThuocNhap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        tableChiTietTraThuocNhap.getTableHeader().setBackground(new Color(255, 128, 64));
        tableChiTietTraThuocNhap.setRowHeight(30); // Tăng chiều cao hàng
       
        scrollPaneChiTietTraThuocNhap.setViewportView(tableChiTietTraThuocNhap);
        
        //Panel thao tác
        TitledBorder titledBorderThaoTac = BorderFactory.createTitledBorder(
			    new LineBorder(Color.WHITE, 2),
			    "Thao tác",
			    TitledBorder.CENTER,
			    TitledBorder.TOP,
			    new Font("Arial", Font.BOLD, 18), // Tăng kích cỡ chữ
			    Color.WHITE
			);
        JPanel panelThaoTac = new JPanel();
        panelThaoTac.setBackground(new Color(255, 128, 64));
        panelThaoTac.setBorder(titledBorderThaoTac);
        panelThaoTac.setBounds(731, 688, 778, 99);
        add(panelThaoTac);
        panelThaoTac.setLayout(null);
        
        JLabel lblTinTrLi = new JLabel("Tổng tiền:");
        lblTinTrLi.setForeground(Color.WHITE);
        lblTinTrLi.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTinTrLi.setBackground(Color.WHITE);
        lblTinTrLi.setBounds(10, 33, 182, 34);
        panelThaoTac.add(lblTinTrLi);
        
        txtTongTien = new JTextField();
        txtTongTien.setEditable(false);
        txtTongTien.setFont(new Font("Tahoma", Font.BOLD, 18));
        txtTongTien.setColumns(10);
        txtTongTien.setBounds(184, 30, 196, 42);
        panelThaoTac.add(txtTongTien);
        
        JButton btnTraThuoc = new JButton("Trả thuốc");
        btnTraThuoc.setBackground(new Color(255, 255, 255));
        btnTraThuoc.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnTraThuoc.setBounds(403, 31, 124, 41);
        btnTraThuoc.addActionListener(e -> {
			try {
				traThuoc();
			} catch (ParseException | IOException e1) {
				e1.printStackTrace();
			}
		});
        panelThaoTac.add(btnTraThuoc);
        
        btnXoa = new JButton("Xóa");
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		xoaThuocTra();
        	}
        });
        btnXoa.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnXoa.setBounds(538, 31, 96, 41);
        panelThaoTac.add(btnXoa);
        
        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBackground(new Color(255, 255, 255));
        btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnLamMoi.setBounds(644, 31, 124, 41);
        panelThaoTac.add(btnLamMoi);
        btnLamMoi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaDuLieuForm();
            }
        });
  
	}
	
	//Hàm tìm kiếm phiếu nhập
	private void timKiemPhieuNhap() {
        String tuKhoa = txtNhapTimKiem.getText().trim();
        String loaiTimKiem = (String) comboBoxLoaiTimKiem.getSelectedItem();
        if (tuKhoa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        while (modelDanhSachPhieuNhap.getRowCount() > 0) {
        	modelDanhSachPhieuNhap.removeRow(0);
        }

        String maPhieuNhap = "";

        if ("Mã phiếu nhập".equals(loaiTimKiem)) {
        	maPhieuNhap = tuKhoa;
        } 

        List<Object[]> result = traNhapThuocDAO.searchPhieuNhap(maPhieuNhap);
        if (result.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy phiếu nhập nào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else {
			for (Object[] row : result) {
				modelDanhSachPhieuNhap.addRow(row);
			}
		}
        
    }
	// Hàm hiển thị chi tiết phiếu nhập
	private void hienThiChiTietPhieuNhap() {
		int selectedRow = tableDanhSachPhieuNhap.getSelectedRow();
		if (selectedRow != -1) {
			
			String maPhieuNhap = (String) tableDanhSachPhieuNhap.getValueAt(selectedRow, 0);
			txtMaPhieuNhap.setText(maPhieuNhap);
			txtMaNCC.setText((String) tableDanhSachPhieuNhap.getValueAt(selectedRow, 2));
			txtMaNV.setText((String) tableDanhSachPhieuNhap.getValueAt(selectedRow, 1));
			txtTongTien.setText("0");
			List<Object[]> chiTietPhieuNhap = traNhapThuocDAO.getChiTietPhieuNhap(maPhieuNhap);
			while (tableChiTietPhieuNhap.getRowCount() > 0) {
				modelChiTietPhieuNhap.removeRow(0);
			}
			for (Object[] row : chiTietPhieuNhap) {
				modelChiTietPhieuNhap.addRow(row);
			}
		}
	}
	// Hàm xóa dữ liệu trong form
	private void xoaDuLieuForm() {
		txtMaPhieuNhap.setText("");
		txtMaNCC.setText("");
		txtMaNV.setText("");
		txtTongTien.setText("0");
		textAreaLiDoTra.setText("");
		spinnerSoLuongTra.setValue(1);
		txtMaTra.setText(traNhapThuocDAO.getNextMaTraNhap());
		txtMaThuChi.setText(traNhapThuocDAO.getNextMaThuChi());
		while (modelChiTietPhieuNhap.getRowCount() > 0) {
			modelChiTietPhieuNhap.removeRow(0);
		}
		
		while (modelChiTietTraThuocNhap.getRowCount() > 0) {
			modelChiTietTraThuocNhap.removeRow(0);
		}
		
		while (modelDanhSachPhieuNhap.getRowCount() > 0) {
			modelDanhSachPhieuNhap.removeRow(0);
		}
		
		txtNhapTimKiem.setText("");
		
	}
	
	// Hàm thêm thuốc vào danh sách trả
	private void themVaoDanhSachTra() {
	    int selectedRow = tableChiTietPhieuNhap.getSelectedRow();
	    if (selectedRow != -1) {
	    	String maThuoc = (String) tableChiTietPhieuNhap.getValueAt(selectedRow, 0);
	        String tenThuoc = (String) tableChiTietPhieuNhap.getValueAt(selectedRow, 1);
	        int soLuongNhap = (int) tableChiTietPhieuNhap.getValueAt(selectedRow, 2); // Get available quantity
	        int soLuongTra = (int) spinnerSoLuongTra.getValue();

	        // Check if the quantity to return exceeds the available quantity
	        if (soLuongTra > soLuongNhap) {
	            JOptionPane.showMessageDialog(this, "Số lượng trả không được lớn hơn số lượng nhập!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        // Get the formatted "đơn giá" from the table
	        String formattedDonGia = (String) tableChiTietPhieuNhap.getValueAt(selectedRow, 3);

	        try {
	            // Parse the formatted "đơn giá" back to a number
	            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	            Number parsedDonGia = currencyFormat.parse(formattedDonGia);
	            double donGia = parsedDonGia.doubleValue();

	            // Calculate "thành tiền"
	            double thanhTien = soLuongTra * donGia;

	            // Format "đơn giá" and "thành tiền" for display
	            String formattedThanhTien = currencyFormat.format(thanhTien);

	            // Add the row to the table
	            modelChiTietTraThuocNhap.addRow(new Object[] {maThuoc,tenThuoc, soLuongTra, formattedDonGia, formattedThanhTien});

	            // Update the total amount
	            double tongTienTra = 0;
	            for (int i = 0; i < modelChiTietTraThuocNhap.getRowCount(); i++) {
	                String formattedThanhTienRow = modelChiTietTraThuocNhap.getValueAt(i, 4).toString();
	                Number parsedThanhTienRow = currencyFormat.parse(formattedThanhTienRow);
	                tongTienTra += parsedThanhTienRow.doubleValue();
	            }

	            txtTongTien.setText(currencyFormat.format(tongTienTra));

	            // Update the quantity in "Chi tiết phiếu nhập"
	            int updatedSoLuongNhap = soLuongNhap - soLuongTra;
	            tableChiTietPhieuNhap.setValueAt(updatedSoLuongNhap, selectedRow, 2); // Update the quantity column
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi xử lý đơn giá: " + e.getMessage(), "Thông báo", JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc trong danh sách chi tiết phiếu nhập", "Thông báo", JOptionPane.WARNING_MESSAGE);
	    }
	}
	// Hàm xóa thuốc trong danh sách trả
	private void xoaThuocTra() {
	    int selectedRow = tableChiTietTraThuocNhap.getSelectedRow();
	    if (selectedRow != -1) {
	        // Get the drug name and quantity from the return list
	        String tenThuoc = (String) modelChiTietTraThuocNhap.getValueAt(selectedRow, 1);
	        int soLuongTra = (int) modelChiTietTraThuocNhap.getValueAt(selectedRow, 2);

	        // Remove the row from the return list
	        modelChiTietTraThuocNhap.removeRow(selectedRow);

	        // Update the total amount
	        double tongTienTra = 0;
	        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
	        for (int i = 0; i < modelChiTietTraThuocNhap.getRowCount(); i++) {
	            String formattedThanhTienRow = modelChiTietTraThuocNhap.getValueAt(i, 4).toString();
	            try {
	                Number parsedThanhTienRow = currencyFormat.parse(formattedThanhTienRow);
	                tongTienTra += parsedThanhTienRow.doubleValue();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        txtTongTien.setText(currencyFormat.format(tongTienTra));

	        // Restore the quantity in ChiTietPhieuNhap
	        for (int i = 0; i < modelChiTietPhieuNhap.getRowCount(); i++) {
	            String tenThuocNhap = (String) modelChiTietPhieuNhap.getValueAt(i, 1);
	            if (tenThuocNhap.equals(tenThuoc)) {
	                int soLuongNhap = (int) modelChiTietPhieuNhap.getValueAt(i, 2);
	                modelChiTietPhieuNhap.setValueAt(soLuongNhap + soLuongTra, i, 2);
	                break;
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc trong danh sách trả thuốc", "Thông báo", JOptionPane.WARNING_MESSAGE);
	    }
	}
	// Hàm thực hiện trả thuốc
	private void traThuoc()throws ParseException, IOException {
		if (tableChiTietTraThuocNhap.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng thêm thuốc vào danh sách trước khi thanh toán trả");
			return;
		}
		
		String maPhieuNhap = txtMaPhieuNhap.getText();
		String maTra = txtMaTra.getText();
		String maNV = txtMaNV.getText();
		String maNCC = txtMaNCC.getText();
		String ngayLap = txtNgayNhap.getText();
		if (ngayLap.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày nhập");
			return;
		}
		// Chuyển đổi định dạng ngày tháng
		SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = inputFormat.parse(ngayLap);

	
		String maThuChi = txtMaThuChi.getText();
		String phuongThucThanhToan = (String) comboBoxPhuongThuc.getSelectedItem();

		String liDoTra = textAreaLiDoTra.getText();
		double tongTien = 0;
		NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		for (int i = 0; i < modelChiTietTraThuocNhap.getRowCount(); i++) {
			String formattedThanhTienRow = modelChiTietTraThuocNhap.getValueAt(i, 4).toString();
			try {
				Number parsedThanhTienRow = currencyFormat.parse(formattedThanhTienRow);
				tongTien += parsedThanhTienRow.doubleValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Chuyển đổi số tiền thành chuỗi định dạng tiền tệ
		String formattedTongTien = currencyFormat.format(tongTien);
		// Chuyển đổi số tiền thành số nguyên
		String formattedTongTienStr = formattedTongTien.replaceAll("[^\\d]", ""); // Loại bỏ ký tự không phải số
		double formattedTongTienDouble = Double.parseDouble(formattedTongTienStr); // Chia cho 1000 để chuyển đổi về số nguyên
		
		
		DefaultTableModel model = (DefaultTableModel) tableChiTietTraThuocNhap.getModel();

		boolean luu = false;
		// Tiền mặt
		if (phuongThucThanhToan.equals("Tiền mặt")) {
			inPhieuTraNhapThuoc(maTra, ngayLap, formattedTongTienDouble, maNV, maNCC, "Đã thanh toán", phuongThucThanhToan);
			if(Dialog_InPhieu_TraNhapThuoc.isPrinting()) {
				luu = traNhapThuocDAO.luuPhieuTraNhapThuoc(maTra, maPhieuNhap, maNV, maNCC, date, liDoTra);
			}else {
				JOptionPane.showMessageDialog(this, "Thanh toán tiền mặt bị hủy");
			}
			
			if (luu) {
				luu = luuChiTietPhieuTraNhapThuoc(maTra, model);
				if (luu) {
					JOptionPane.showMessageDialog(this, "Lưu chi tiết hóa đơn thành công");
					// Luu vào bảng thu chi
					luu = traNhapThuocDAO.luuPhieuThuChi(maThuChi, maNV, date,phuongThucThanhToan, "Trả nhập thuốc", maTra);
					if (luu) {
						JOptionPane.showMessageDialog(this, "Lưu phiếu thu chi thành công");
					} else {
						JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu thu chi");
					}
					xoaDuLieuForm();
				} else {
					JOptionPane.showMessageDialog(this, "Lỗi khi lưu chi tiết hóa đơn");
				}
			} 
			
		}
		// Chuyển khoản
		else {
			hienThiQRCode(maTra, formattedTongTienDouble); 
			if (Dialog_InQRCode.getResult()) {
				inPhieuTraNhapThuoc(maTra, ngayLap, formattedTongTienDouble, maNV, maNCC, "Đã thanh toán", phuongThucThanhToan);
				luu = traNhapThuocDAO.luuPhieuTraNhapThuoc(maTra, maPhieuNhap, maNV, maNCC, date, liDoTra);
				if (luu) {
					luu = luuChiTietPhieuTraNhapThuoc(maTra, model);
					if (luu) {
						JOptionPane.showMessageDialog(this, "Lưu chi tiết hóa đơn thành công");
						// Luu vào bảng thu chi
						luu = traNhapThuocDAO.luuPhieuThuChi(maThuChi, maNV, date, phuongThucThanhToan, "Trả nhập thuốc", maTra);
						if (luu) {
							JOptionPane.showMessageDialog(this, "Lưu phiếu thu chi thành công");
						} else {
							JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu thu chi");
						}
						xoaDuLieuForm();
					} else {
						JOptionPane.showMessageDialog(this, "Lỗi khi lưu chi tiết hóa đơn");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Thanh toán chuyển khoản bị hủy");
			}
		}
	}
	
	//
	private void inPhieuTraNhapThuoc(String maPNT, String ngayLap, double tongTien, String maNV, String maNCC, String trangThaiStr, String phuongThucThanhToanStr) {
		// TODO Auto-generated method stub
		try {
			List<Object[]> data = new ArrayList<>();
			DefaultTableModel model = (DefaultTableModel) tableChiTietTraThuocNhap.getModel();

			for (int i = 0; i < model.getRowCount(); i++) {
				Object[] row = { model.getValueAt(i, 0), // Mã thuốc
						model.getValueAt(i, 1), // Tên thuốc
						model.getValueAt(i, 2), // Số lượng
						model.getValueAt(i, 3), // Đơn giá
						model.getValueAt(i, 4) // Thành tiền
						
				};
				data.add(row);
			}

			Dialog_InPhieu_TraNhapThuoc inPTNT = new Dialog_InPhieu_TraNhapThuoc(null, maPNT, ngayLap, tongTien, maNV, maNCC,
					trangThaiStr, phuongThucThanhToanStr, data);
			inPTNT.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// Hàm lưu chi tiết phiếu trả thuốc
	
	private boolean luuChiTietPhieuTraNhapThuoc(String maTra, DefaultTableModel model) {
		boolean luu = false;
		String lyDoTra = textAreaLiDoTra.getText();
		for (int i = 0; i < model.getRowCount(); i++) {
			String maThuoc = (String) model.getValueAt(i, 0);
			int soLuongTra = (int) model.getValueAt(i, 2);
			// Chuyển đổi định dạng "đơn giá" về số
			String formattedDonGia = (String) model.getValueAt(i, 3);
			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
			Number parsedDonGia = null;
			try {
				parsedDonGia = currencyFormat.parse(formattedDonGia);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			double donGia = parsedDonGia.doubleValue();
			// Tính "thành tiền"
			double thanhTien = soLuongTra * donGia;
			luu = traNhapThuocDAO.luuChiTietPhieuTraNhapThuoc(maTra, maThuoc, soLuongTra,lyDoTra ,donGia);
		}
		return luu;
	}
	// Hàm hiển thị QR Code
	private void hienThiQRCode(String maTNT, double tongTien) {
		try {
			Dialog_InQRCode inQRCode = new Dialog_InQRCode(null, maTNT, tongTien);
			inQRCode.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi hiển thị QR Code: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
