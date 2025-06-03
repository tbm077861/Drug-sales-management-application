package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DAO.BangLuongDAO;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.awt.event.ActionEvent;

public class Frame_NhanVien_BangLuong extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTable tableDanhSachNhanVien;
	private JTable tableBangLuong;
	private JPanel pnlTacVu;
	private JLabel lblMaNhanVienTim;
	private JLabel lblTenNhanVienTim;
	private JLabel lblCanCuocTim;
	private JLabel lblGioiTinhTim;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JTextField txtCCCD;
	private JComboBox cboGioiTinh;
	private JButton btnTim;
	private JButton btnTaiLai;
	private JPanel pnlThaoTac;
	private JButton btnTaiLuong;
	private JLabel lblGioiTinhTim_1;
	private JLabel lblGioiTinhTim_2;
	private JLabel lblThcLnh;
	private JTextField txtLuongThuong;
	private JTextField txtLuongTangCa;
	private JTextField txtThucLinh;
	private BangLuongDAO bangLuongDAO = new BangLuongDAO(); // Giả sử bạn đã có DAO để lấy dữ liệu bảng lương

	/**
	 * Create the frame.
	 */
	public Frame_NhanVien_BangLuong() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 778));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1545, 854);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);
		
		JScrollPane scrollPaneDanhSachNhanVien = new JScrollPane();
		scrollPaneDanhSachNhanVien.setBounds(517, 27, 951, 350);
		pnlBackGround.add(scrollPaneDanhSachNhanVien);
		
		tableDanhSachNhanVien = new JTable();
		tableDanhSachNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableDanhSachNhanVien.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã NV", "Họ tên",
				 "Giới Tính", "CCCD", "Chức vụ", "Trình độ"}) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false};
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDanhSachNhanVien.getColumnModel().getColumn(2).setPreferredWidth(55);
		JTableHeader header = tableDanhSachNhanVien.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 15));
		tableDanhSachNhanVien.setRowHeight(30);
		scrollPaneDanhSachNhanVien.setViewportView(tableDanhSachNhanVien);
		
		// Create a custom cell renderer that centers the text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Apply the renderer to each column of the table
		for (int i = 0; i < tableDanhSachNhanVien.getColumnCount(); i++) {
			tableDanhSachNhanVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		JScrollPane scrollPaneBangLuong = new JScrollPane();
		scrollPaneBangLuong.setBounds(517, 415, 951, 326);
		pnlBackGround.add(scrollPaneBangLuong);
		
		tableBangLuong = new JTable();
		tableBangLuong.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableBangLuong.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ng\u00E0y", "Ca", "V\u00E0o", "Ra", "Gi\u1EDD C\u00F4ng", "L\u01B0\u01A1ng Th\u01B0\u1EDDng", "L\u01B0\u01A1ng OT"
			}) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableBangLuong.getColumnModel().getColumn(1).setPreferredWidth(50);
		
		JTableHeader headerBangLuong = tableBangLuong.getTableHeader();
		headerBangLuong.setFont(new Font("Segoe UI", Font.BOLD, 15));
		tableBangLuong.setRowHeight(30);
		scrollPaneBangLuong.setViewportView(tableBangLuong);
		
		// Create a custom cell renderer that centers the text
		DefaultTableCellRenderer centerRendererBangLuong = new DefaultTableCellRenderer();
		centerRendererBangLuong.setHorizontalAlignment(SwingConstants.CENTER);
		// Apply the renderer to each column of the table
		for (int i = 0; i < tableBangLuong.getColumnCount(); i++) {
			tableBangLuong.getColumnModel().getColumn(i).setCellRenderer(centerRendererBangLuong);
		}
		
		pnlTacVu = new JPanel();
		pnlTacVu.setLayout(null);
		pnlTacVu.setBackground(new Color(255, 153, 51));
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tác vụ");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlTacVu.setBounds(39, 29, 421, 348);
		pnlTacVu.setBorder(titledBorder);
		pnlBackGround.add(pnlTacVu);
		
		lblMaNhanVienTim = new JLabel("Mã nhân viên:");
		lblMaNhanVienTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaNhanVienTim.setBounds(21, 37, 180, 34);
		pnlTacVu.add(lblMaNhanVienTim);
		
		lblTenNhanVienTim = new JLabel("Tên nhân viên:");
		lblTenNhanVienTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTenNhanVienTim.setBounds(21, 100, 180, 34);
		pnlTacVu.add(lblTenNhanVienTim);
		
		lblCanCuocTim = new JLabel("Căn cước:");
		lblCanCuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCanCuocTim.setBounds(21, 159, 180, 34);
		pnlTacVu.add(lblCanCuocTim);
		
		lblGioiTinhTim = new JLabel("Giới tính:");
		lblGioiTinhTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioiTinhTim.setBounds(21, 219, 180, 34);
		pnlTacVu.add(lblGioiTinhTim);
		
		txtMaNV = new JTextField();
		txtMaNV.setText("");
		txtMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaNV.setColumns(10);
		txtMaNV.setBounds(175, 37, 166, 38);
		pnlTacVu.add(txtMaNV);
		
		txtTenNV = new JTextField();
		txtTenNV.setText("");
		txtTenNV.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenNV.setColumns(10);
		txtTenNV.setBounds(175, 100, 166, 38);
		pnlTacVu.add(txtTenNV);
		
		txtCCCD = new JTextField();
		txtCCCD.setText("");
		txtCCCD.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtCCCD.setColumns(10);
		txtCCCD.setBounds(175, 159, 166, 38);
		pnlTacVu.add(txtCCCD);
		
		cboGioiTinh = new JComboBox();
		cboGioiTinh.setModel(new DefaultComboBoxModel(new String[] {"", "Nam", "Nữ" }));
		cboGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cboGioiTinh.setBounds(175, 221, 166, 38);
		pnlTacVu.add(cboGioiTinh);
		
		btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBackground(new Color(255, 128, 128));
		btnTim.setBounds(21, 283, 141, 50);
		pnlTacVu.add(btnTim);
		
		btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBackground(new Color(255, 128, 128));
		btnTaiLai.setBounds(216, 283, 141, 50);
		pnlTacVu.add(btnTaiLai);
		
		pnlThaoTac = new JPanel();
		pnlThaoTac.setBackground(new Color(255, 153, 51));
		pnlThaoTac.setBounds(39, 415, 421, 326);
		TitledBorder titledBorderThaoTac = BorderFactory.createTitledBorder("Thao tác");
		titledBorderThaoTac.setTitleFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlThaoTac.setBorder(titledBorderThaoTac);
		pnlBackGround.add(pnlThaoTac);
		pnlThaoTac.setLayout(null);
		
		JLabel lblThangTim = new JLabel("Tháng/Năm:");
		lblThangTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblThangTim.setBounds(27, 32, 180, 34);
		pnlThaoTac.add(lblThangTim);
		
		JComboBox cboThang = new JComboBox();
		cboThang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cboThang.setModel(new DefaultComboBoxModel(new String[] {"2025-1", "2025-2", "2025-3", "2025-4", "2025-5", "2025-6", "2025-7", "2025-8", "2025-9", "2025-10", "2025-11", "2025-12"}));
		cboThang.setBounds(162, 32, 158, 33);
		pnlThaoTac.add(cboThang);
		
		btnTaiLuong = new JButton("Tải Lương");
		btnTaiLuong.setIcon(new ImageIcon("icon\\history_transaction.png"));
		
		btnTaiLuong.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLuong.setBackground(new Color(255, 128, 128));
		btnTaiLuong.setBounds(10, 95, 180, 50);
		pnlThaoTac.add(btnTaiLuong);
		
		lblGioiTinhTim_1 = new JLabel("Thường:");
		lblGioiTinhTim_1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioiTinhTim_1.setBounds(10, 168, 180, 34);
		pnlThaoTac.add(lblGioiTinhTim_1);
		
		lblGioiTinhTim_2 = new JLabel("Tăng Ca:");
		lblGioiTinhTim_2.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioiTinhTim_2.setBounds(10, 212, 180, 34);
		pnlThaoTac.add(lblGioiTinhTim_2);
		
		lblThcLnh = new JLabel("Thực Lĩnh:");
		lblThcLnh.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblThcLnh.setBounds(10, 256, 180, 34);
		pnlThaoTac.add(lblThcLnh);
		
		txtLuongThuong = new JTextField();
		txtLuongThuong.setEnabled(false);
		txtLuongThuong.setEditable(false);
		txtLuongThuong.setForeground(new Color(0, 0, 0));
		txtLuongThuong.setBackground(new Color(253, 153, 51));
		txtLuongThuong.setText("");
		txtLuongThuong.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtLuongThuong.setColumns(10);
		txtLuongThuong.setBounds(121, 168, 180, 38);
		pnlThaoTac.add(txtLuongThuong);
		
		txtLuongTangCa = new JTextField();
		txtLuongTangCa.setEditable(false);
		txtLuongTangCa.setBackground(new Color(253, 153, 51));
		txtLuongTangCa.setEnabled(false);
		txtLuongTangCa.setText("");
		txtLuongTangCa.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtLuongTangCa.setColumns(10);
		txtLuongTangCa.setBounds(121, 212, 180, 38);
		pnlThaoTac.add(txtLuongTangCa);
		
		txtThucLinh = new JTextField();
		txtThucLinh.setEditable(false);
		txtThucLinh.setBackground(new Color(253, 153, 51));
		txtThucLinh.setEnabled(false);
		txtThucLinh.setText("");
		txtThucLinh.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtThucLinh.setColumns(10);
		txtThucLinh.setBounds(121, 256, 180, 38);
		pnlThaoTac.add(txtThucLinh);
		
		JButton btnXuatExcel = new JButton("Xuất Excel");
		btnXuatExcel.setIcon(new ImageIcon("icon\\print.png"));
		btnXuatExcel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXuatExcel.setBackground(new Color(255, 128, 128));
		btnXuatExcel.setBounds(200, 95, 180, 50);
		pnlThaoTac.add(btnXuatExcel);
		
		// Nút tim kiếm nhân viên
		btnTim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String maNV = txtMaNV.getText().trim();
				String hoTen = txtTenNV.getText().trim();
				String cccd = txtCCCD.getText().trim();
				String gioiTinh = (String) cboGioiTinh.getSelectedItem();
				
				List<Object[]> searchResults = bangLuongDAO.searchEmployees(maNV, hoTen, cccd, gioiTinh);
				DefaultTableModel model = (DefaultTableModel) tableDanhSachNhanVien.getModel();
				model.setRowCount(0); // Xóa dữ liệu cũ
				for (Object[] row : searchResults) {
					model.addRow(row);
				}
			}
		});
		// Nút tải lại dữ liệu
		btnTaiLai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtMaNV.setText("");
				txtTenNV.setText("");
				txtCCCD.setText("");
				cboGioiTinh.setSelectedIndex(0);
				showEmployeeData(); // Hiển thị lại dữ liệu nhân viên
			}
		});
		// Nút tải bảng lương
		btnTaiLuong.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String maNV = txtMaNV.getText().trim();
		        String thangNam = (String) cboThang.getSelectedItem();
		        double tongLuongThuong = 0.0;
		        double tongLuongTangCa = 0.0;
		        if (!maNV.isEmpty() && thangNam != null) {
		            List<Object[]> payrollData = bangLuongDAO.getPayroll(maNV, thangNam);
		            DefaultTableModel model = (DefaultTableModel) tableBangLuong.getModel();
		            model.setRowCount(0); // Clear old data
		            for (Object[] row : payrollData) {
		                model.addRow(row);
		             
		                String luongThuongStr = row[5].toString();
		                String luongTangCaStr = row[6].toString();
		                
		                double luongThuong = parseCurrency(luongThuongStr);
		                double luongTangCa = parseCurrency(luongTangCaStr);
		                
		                tongLuongThuong += luongThuong;
		                tongLuongTangCa += luongTangCa;
		                
		            }
		            // Update salary information
		            if (!payrollData.isEmpty()) {
		                // Calculate total salary
		                double thucLinh = tongLuongThuong + tongLuongTangCa;
		                
		                // Định dạng số tiền theo định dạng Việt Nam
		                Locale localeVN = new Locale("vi", "VN");
		                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeVN);
		                // Chuyển đổi số tiền sang định dạng chuỗi
		                currencyFormatter.setMinimumFractionDigits(3);
		                currencyFormatter.setMaximumFractionDigits(3);
		                
		                
		                String luongThuongStr = currencyFormatter.format(tongLuongThuong);
		                String luongTangCaStr = currencyFormatter.format(tongLuongTangCa);
		                String formattedThucLinh = currencyFormatter.format(thucLinh);
		                
		                txtLuongThuong.setText(luongThuongStr);
		                txtLuongTangCa.setText(luongTangCaStr);
		                txtThucLinh.setText(formattedThucLinh);
//		                System.out.println(thucLinh);
		                
		            } else {
		                txtLuongThuong.setText("");
		                txtLuongTangCa.setText("");
		                txtThucLinh.setText("");
		            }
		        } else {
		            // Display a message if the employee ID or month/year is missing
		            System.out.println("Vui lòng nhập mã nhân viên và tháng/năm.");
		        }
		    }
		});



		
		// Hiển thị dữ liệu vào bảng nhân viên
		showEmployeeData();
	}
	
	//Hiển thị dữ liệu vào bảng nhân viên
	public void showEmployeeData() {
		List<Object[]> employeeData = bangLuongDAO.getAllEmployees();
		DefaultTableModel model = (DefaultTableModel) tableDanhSachNhanVien.getModel();
		model.setRowCount(0); // Xóa dữ liệu cũ
		for (Object[] row : employeeData) {
			model.addRow(row);
		}
	}
	
	// Helper method to parse formatted currency strings
			private double parseCurrency(String formattedCurrency) {
			    try {
			        // Remove currency symbols and grouping separators
			        String cleanString = formattedCurrency.replaceAll("[^\\d.,]", "").replace(",", "");
			        return Double.parseDouble(cleanString);
			    } catch (NumberFormatException e) {
			        e.printStackTrace();
			        return 0.0;
			    }
			}
	
}