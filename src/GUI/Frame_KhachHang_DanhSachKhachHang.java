package GUI;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import DAO.KhachHangDAO;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Frame_KhachHang_DanhSachKhachHang extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTextField txtMaKhachHang;
	private JTextField txtTenKhachHang;
	private JTextField txtDiaChi;
	private JTextField txtDienThoai;
	private JTextField txtEmail;
	private JDateChooser txtNgaySinh;
	private JTable tableKhachHang;
	private JTextField txtMaKhachHangTim;
	private JTextField btnTenKhachHangTim;
	private JTextField txtDienThoaiTim;
	private JTextField txtDiaChiTim;
	private DefaultTableModel tableModel;
//	private boolean isEditting = false;
	private KhachHangDAO khachHangDAO = new KhachHangDAO();
	
	private JButton btnThem, btnLuu;

	// Cập nhật dữ liệu vào table
	private void loadDataToTable() {
		List<Object[]> data = khachHangDAO.loadDataToTable();
		tableModel = (DefaultTableModel) tableKhachHang.getModel();
		tableModel.setRowCount(0);
		for (Object[] rowData : data) {
			String maKH = (String) rowData[0];
			if (!maKH.equals("KHVL")) {
				tableModel.addRow(rowData);
			}
		}
	}

	// Kiểm tra dữ liệu nhập
	public class kiemTraNhap {
		public static String validateID(String id) {
			if (id == null || id.isEmpty()) {
				return "Mã khách hàng không được để trống!";
			}
			String idRegex = "^KH\\d+$";
			if (!Pattern.matches(idRegex, id)) {
				return "Mã khách hàng không hợp lệ! Mã khách hàng phải bắt đầu bằng 'KH' và theo sau là các chữ số.";
			}
			return null;
		}

		public static String validateName(String name) {
			if (name == null || name.isEmpty()) {
				return "Tên khách hàng không được để trống!";
			}
			return null;
		}

		public static String validatePhone(String phone) {
			if (phone == null || phone.isEmpty()) {
				return "Số điện thoại không được để trống!";
			}
			String phoneRegex = "^0\\d{9,10}$";
			if (!Pattern.matches(phoneRegex, phone)) {
				return "Số điện thoại không hợp lệ! Số điện thoại phải bắt đầu bằng số 0 và theo sau là 9 hoặc 10 chữ số.";
			}
			return null;
		}

		public static String validateEmail(String email) {
			if (email == null || email.isEmpty()) {
				return "Email không được để trống!";
			}
			String emailRegex = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
			if (!Pattern.matches(emailRegex, email)) {
				return "Email không hợp lệ! Email phải theo định dạng x@x.x";
			}
			return null;
		}
		
		public static String validateDate(String date) {
			if (date == null || date.isEmpty()) {
				return "Ngày không được để trống!";
			}
			String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
			if (!Pattern.matches(dateRegex, date)) {
				return "Ngày không hợp lệ! Ngày phải theo định dạng dd/MM/yyyy.";
			}
			return null;
		}

		public static String validateAddress(String address) {
			if (address == null || address.isEmpty()) {
				return "Địa chỉ không được để trống!";
			}
			return null;
		}
	}

	// Thêm khách hàng
	private void btnThemAction() throws ParseException {
		txtMaKhachHang.setEditable(true);

		String maKH = txtMaKhachHang.getText().trim();
		String tenKH = txtTenKhachHang.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String dienThoai = txtDienThoai.getText().trim();
		String email = txtEmail.getText().trim();

		String error;

		error = kiemTraNhap.validateID(maKH);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtMaKhachHang.requestFocus();
			return;
		}

		error = kiemTraNhap.validateName(tenKH);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtTenKhachHang.requestFocus();
			return;
		}

		error = kiemTraNhap.validateAddress(diaChi);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtDiaChi.requestFocus();
			return;
		}

		error = kiemTraNhap.validatePhone(dienThoai);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtDienThoai.requestFocus();
			return;
		}

		error = kiemTraNhap.validateEmail(email);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtEmail.requestFocus();
			return;
		}

		if (khachHangDAO.isDuplicateDienThoai(dienThoai, maKH)) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Số điện thoại đã tồn tại!");
			txtDienThoai.requestFocus();
			return;
		}

		if (khachHangDAO.isDuplicateEmail(email, maKH)) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Email đã tồn tại!");
			txtEmail.requestFocus();
			return;
		}
		
		Date ngaySinh = txtNgaySinh.getDate();
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this,
					"Error: Ngày sinh không hợp lệ! Vui lòng chọn ngày sinh.");
			txtNgaySinh.requestFocus();
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngaySinhStr = sdf.format(ngaySinh);

		khachHangDAO.saveKhachHang(false, maKH, tenKH, ngaySinhStr, diaChi, dienThoai, email);
		btnThem.setEnabled(false);
		loadDataToTable();
		clearInput();
		lockFields();
		gen_ma();
	}

	// Su kien nut sua
	private void btnSuaAction() {
		unlockFields();
		
		int selectedRow = tableKhachHang.getSelectedRow();
		if (selectedRow == -1) {
			btnThem.setEnabled(true);
		} else {
			btnLuu.setEnabled(true);
		}
	}
	
	
	// Lưu thông tin khách hàng sau khi sửa
	private void btnLuuAction() throws ParseException {
		int selectedRow = tableKhachHang.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Vui lòng chọn khách hàng cần sửa để lưu!");
			return;
		}

		txtMaKhachHang.setEditable(false);

		String maKH = txtMaKhachHang.getText().trim();
		String tenKH = txtTenKhachHang.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String dienThoai = txtDienThoai.getText().trim();
		String email = txtEmail.getText().trim();
		Date ngaySinh = txtNgaySinh.getDate();

		String error;

		error = kiemTraNhap.validateID(maKH);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtMaKhachHang.requestFocus();
			return;
		}

		error = kiemTraNhap.validateName(tenKH);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtTenKhachHang.requestFocus();
			return;
		}

		error = kiemTraNhap.validateAddress(diaChi);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtDiaChi.requestFocus();
			return;
		}

		error = kiemTraNhap.validatePhone(dienThoai);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtDienThoai.requestFocus();
			return;
		}

		error = kiemTraNhap.validateEmail(email);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi! " + error);
			txtEmail.requestFocus();
			return;
		}

		if (khachHangDAO.isDuplicateDienThoai(dienThoai, maKH)) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Số điện thoại đã tồn tại!");
			txtDienThoai.requestFocus();
			return;
		}

		if (khachHangDAO.isDuplicateEmail(email, maKH)) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Email đã tồn tại!");
			txtEmail.requestFocus();
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngaySinhStr = sdf.format(ngaySinh);

		khachHangDAO.saveKhachHang(true, maKH, tenKH, ngaySinhStr, diaChi, dienThoai, email);
		btnLuu.setEnabled(false);
		loadDataToTable();
		clearInput();
		lockFields();
		gen_ma();
	}

	// Xóa khách hàng
	private void btnXoaAction() {
		int selectedRow = tableKhachHang.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Vui lòng chọn khách hàng để xóa!");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(Frame_KhachHang_DanhSachKhachHang.this,
				"Bạn có chắc chắn muốn xóa khách hàng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			String maKH = (String) tableKhachHang.getValueAt(selectedRow, 0);
			khachHangDAO.deleteKhachHang(maKH);
			loadDataToTable();
			clearInput();
			lockFields();
			gen_ma();
		}
	}

	private void btnHuyAction() {
		clearInput();
		gen_ma();
		
		tableKhachHang.clearSelection();
		btnThem.setEnabled(false);
		btnLuu.setEnabled(false);
	}

	// Tìm kiếm khách hàng
	private void btnTimAction() {
		String maKH = txtMaKhachHangTim.getText().trim();
		String tenKH = btnTenKhachHangTim.getText().trim();
		String dienThoai = txtDienThoaiTim.getText().trim();
		String diaChi = txtDiaChiTim.getText().trim();

		List<Object[]> data = khachHangDAO.searchKhachHang(maKH, tenKH, dienThoai, diaChi);
		tableModel = (DefaultTableModel) tableKhachHang.getModel();
		tableModel.setRowCount(0);
		for (Object[] rowData : data) {
			String maKHValue = (String) rowData[0];
			if (!maKHValue.equals("KHVL")) {
				tableModel.addRow(rowData);
			}
		}
	}
	
	// Sự kiện phím tắt enter cho tìm kiếm
		@SuppressWarnings("serial")
		private void addEnterKeyListener(JComponent component) {
		    component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "searchAction");
		    component.getActionMap().put("searchAction", new AbstractAction() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	btnTimAction(); // Thực hiện tìm kiếm ngay khi nhấn Enter
		        }
		    });
		}

	// Tải lại dữ liệu vào table
	private void btnTaiLaiAction() {
		clearSearch();
		loadDataToTable();
	}

	// Xóa dữ liệu trong các ô nhập
	private void clearInput() {
		txtMaKhachHang.setText("");
		txtTenKhachHang.setText("");
		txtDiaChi.setText("");
		txtDienThoai.setText("");
		txtEmail.setText("");
		txtNgaySinh.setDate(null);
		lockFields();
//		isEditting = false;
	}
	
	// Thêm phương thức khóa textfield
		private void lockFields() {
		    txtMaKhachHang.setEditable(false);
		    txtTenKhachHang.setEditable(false);
		    txtDiaChi.setEditable(false);
		    txtDienThoai.setEditable(false);
		    txtEmail.setEditable(false);
		    txtNgaySinh.setEnabled(false);
		}
		
		// Thêm phương thức mở khóa textfield
		private void unlockFields() {
			txtTenKhachHang.setEditable(true);
			txtDiaChi.setEditable(true);
			txtDienThoai.setEditable(true);
			txtEmail.setEditable(true);
			txtNgaySinh.setEnabled(true);
		    // Giữ txtMaKhachHang không chỉnh sửa được
		    txtMaKhachHang.setEditable(false);
		    
		}

	// Xóa dữ liệu trong các ô tìm kiếm
	private void clearSearch() {
		txtMaKhachHangTim.setText("");
		btnTenKhachHangTim.setText("");
		txtDienThoaiTim.setText("");
		txtDiaChiTim.setText("");
	}
	
	// Set Default MaKhachHang
	private String generateNextMaKhachHang() {
		String lastMaKH = khachHangDAO.getLastMaKH();
	    if (lastMaKH == null || lastMaKH.isEmpty() || lastMaKH.equals("KHVL") || lastMaKH.equals("KH000")) {
	        return "KH001";
	    }

	    String numericPart = lastMaKH.substring(2); 
	    int number = Integer.parseInt(numericPart);
	    number++;

	    return String.format("KH%03d", number);
	}
	// set Generate MaKhachHang again when deleted
	private void gen_ma() {
		// Gen MaKH
		String gen_MaKH = generateNextMaKhachHang();
		txtMaKhachHang.setText(gen_MaKH);
	}
	
	// Sự kiện nút Xuất file
		private void btnXuatFileAction() {
		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("DanhSachKhachHang");

		    // Create header row
		    Row headerRow = sheet.createRow(0);
		    String[] headers = {"Mã khách hàng", "Tên khách hàng", "Ngày sinh", "Địa chỉ", "Số điện thoại", "Email"};
		    for (int i = 0; i < headers.length; i++) {
		        Cell cell = headerRow.createCell(i);
		        cell.setCellValue(headers[i]);
		    }

		    // Populate data rows
		    List<Object[]> List = khachHangDAO.getAllKhachHang();
		    int rowNum = 1;
		    for (Object[] kh : List) {
				if (!((String) kh[0]).equals("KHVL")) {
					Row row = sheet.createRow(rowNum++);
			        row.createCell(0).setCellValue((String) kh[0]);
			        row.createCell(1).setCellValue((String) kh[1]);
			        row.createCell(2).setCellValue((String) kh[2]);
			        row.createCell(3).setCellValue((String) kh[3]);
			        row.createCell(4).setCellValue((String) kh[4]);
			        row.createCell(5).setCellValue((String) kh[5]);
				}
		    }

		    // Write the output to a file
		    try (FileOutputStream fileOut = new FileOutputStream("DanhSachKhachHang.xlsx")) {
		        workbook.write(fileOut);
		        JOptionPane.showMessageDialog(this, "Xuất file Excel thành công");
		    } catch (IOException e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(this, "Xuất file Excel thất bại");
		    } finally {
		        try {
		            workbook.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameQuanLyKhachHang frame = new FrameQuanLyKhachHang();
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
	public Frame_KhachHang_DanhSachKhachHang() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1543, 755);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 252, 1523, 66);
		panel.setBackground(new Color(255, 128, 64));
		pnlBackGround.add(panel);
		panel.setLayout(null);

		btnThem = new JButton("Thêm");
		btnThem.setBackground(new Color(167, 62, 20));
		btnThem.setIcon(new ImageIcon("icon\\add.png"));
		btnThem.setForeground(Color.BLACK);
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBounds(58, 10, 177, 50);
		panel.add(btnThem);
		btnThem.addActionListener(e -> {
			try {
				btnThemAction();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});

		btnLuu = new JButton("Lưu");
		btnLuu.setIcon(new ImageIcon("icon\\save.png"));
		btnLuu.setForeground(Color.BLACK);
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setBackground(new Color(167, 62, 20));
		btnLuu.setBounds(577, 10, 177, 50);
		panel.add(btnLuu);
		btnLuu.addActionListener(e -> {
			try {
				btnLuuAction();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});

		JButton btnXoa = new JButton("Xóa");
		btnXoa.setIcon(new ImageIcon("icon\\delete.png"));
		btnXoa.setForeground(Color.BLACK);
		btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXoa.setBackground(new Color(167, 62, 20));
		btnXoa.setBounds(829, 10, 177, 50);
		panel.add(btnXoa);
		btnXoa.addActionListener(e -> btnXoaAction());

		JButton btnHuy = new JButton("Hủy");
		btnHuy.setIcon(new ImageIcon("icon\\cancel.png"));
		btnHuy.setForeground(Color.BLACK);
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHuy.setBackground(new Color(167, 62, 20));
		btnHuy.setBounds(1070, 10, 177, 50);
		panel.add(btnHuy);
		btnHuy.addActionListener(e -> btnHuyAction());

		JButton btnXuat = new JButton("Xuất");
		btnXuat.setIcon(new ImageIcon("icon\\print.png"));
		btnXuat.setForeground(Color.BLACK);
		btnXuat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXuat.setBackground(new Color(167, 62, 20));
		btnXuat.setBounds(1317, 13, 177, 45);
		panel.add(btnXuat);
		btnXuat.addActionListener(e -> btnXuatFileAction());
		
		JButton btnSua = new JButton("Sửa");
		btnSua.addActionListener(e -> btnSuaAction());
		btnSua.setIcon(new ImageIcon("icon\\edit.png"));
		btnSua.setForeground(Color.BLACK);
		btnSua.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSua.setBackground(new Color(167, 62, 20));
		btnSua.setBounds(312, 10, 177, 50);
		panel.add(btnSua);

		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setBounds(27, 188, 160, 40);
		lblDiaChi.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlBackGround.add(lblDiaChi);

		JLabel lblMaKhachHang = new JLabel("Mã khách hàng:");
		lblMaKhachHang.setBounds(27, 27, 160, 40);
		lblMaKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlBackGround.add(lblMaKhachHang);

		JLabel lblTenKhachHang = new JLabel("Tên khách hàng:");
		lblTenKhachHang.setBounds(27, 109, 160, 40);
		lblTenKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlBackGround.add(lblTenKhachHang);

		JLabel lblSoDienThoai = new JLabel("Điện thoại:");
		lblSoDienThoai.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblSoDienThoai.setBounds(588, 27, 160, 40);
		pnlBackGround.add(lblSoDienThoai);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblEmail.setBounds(588, 109, 160, 40);
		pnlBackGround.add(lblEmail);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNgaySinh.setBounds(588, 188, 160, 40);
		pnlBackGround.add(lblNgaySinh);

		txtMaKhachHang = new JTextField();
		txtMaKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaKhachHang.setBounds(219, 24, 317, 43);
		pnlBackGround.add(txtMaKhachHang);
		txtMaKhachHang.setColumns(10);

		// Auto uppercase

		txtMaKhachHang.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtMaKhachHang.getText();
				txtMaKhachHang.setText(text.toUpperCase());
			}
		});
		
		// Gen MaKH
		gen_ma();
		

		txtTenKhachHang = new JTextField();
		txtTenKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenKhachHang.setColumns(10);
		txtTenKhachHang.setBounds(219, 106, 317, 43);
		pnlBackGround.add(txtTenKhachHang);

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(219, 185, 317, 43);
		pnlBackGround.add(txtDiaChi);

		txtDienThoai = new JTextField();
		txtDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDienThoai.setColumns(10);
		txtDienThoai.setBounds(740, 31, 317, 43);
		pnlBackGround.add(txtDienThoai);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtEmail.setColumns(10);
		txtEmail.setBounds(740, 111, 317, 43);
		pnlBackGround.add(txtEmail);

		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("dd/MM/yyyy");
		txtNgaySinh.setBounds(740, 190, 317, 43);
		txtNgaySinh.getDateEditor().getUiComponent().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		pnlBackGround.add(txtNgaySinh);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("image\\logoMTP.png"));
		lblNewLabel.setBounds(1109, 27, 407, 211);
		pnlBackGround.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 328, 1100, 417);
		pnlBackGround.add(scrollPane);

		tableKhachHang = new JTable();
		tableKhachHang.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableKhachHang.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã KH", "Tên Khách Hàng", "Ngày Sinh", "Địa Chỉ", "Số Điện Thoại", "Email" }) {
			private static final long serialVersionUID = -5324374389820629878L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableKhachHang.getColumnModel().getColumn(2).setPreferredWidth(55);

		JTableHeader header = tableKhachHang.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tableKhachHang.setRowHeight(30);
		scrollPane.setViewportView(tableKhachHang);

		JPanel pnlTacVu = new JPanel();
		pnlTacVu.setBackground(new Color(255, 128, 64));
		pnlTacVu.setBounds(1120, 328, 413, 417);
		pnlBackGround.add(pnlTacVu);
		pnlTacVu.setLayout(null);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tác vụ");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		pnlTacVu.setBorder(titledBorder);

		JLabel lblNewLabel_1 = new JLabel("Mã KH:");
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1.setBounds(10, 44, 165, 45);
		pnlTacVu.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Tên KH:");
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(10, 119, 165, 45);
		pnlTacVu.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_3 = new JLabel("Số điện thoại:");
		lblNewLabel_1_3.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1_3.setBounds(10, 189, 165, 45);
		pnlTacVu.add(lblNewLabel_1_3);

		JLabel lblNewLabel_1_4 = new JLabel("Địa chỉ:");
		lblNewLabel_1_4.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNewLabel_1_4.setBounds(10, 263, 165, 45);
		pnlTacVu.add(lblNewLabel_1_4);

		txtMaKhachHangTim = new JTextField();
		txtMaKhachHangTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaKhachHangTim.setBounds(167, 44, 225, 45);
		pnlTacVu.add(txtMaKhachHangTim);
		txtMaKhachHangTim.setColumns(10);

		btnTenKhachHangTim = new JTextField();
		btnTenKhachHangTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		btnTenKhachHangTim.setColumns(10);
		btnTenKhachHangTim.setBounds(167, 119, 225, 45);
		pnlTacVu.add(btnTenKhachHangTim);

		txtDienThoaiTim = new JTextField();
		txtDienThoaiTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDienThoaiTim.setColumns(10);
		txtDienThoaiTim.setBounds(167, 191, 225, 45);
		pnlTacVu.add(txtDienThoaiTim);

		txtDiaChiTim = new JTextField();
		txtDiaChiTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDiaChiTim.setColumns(10);
		txtDiaChiTim.setBounds(167, 268, 225, 45);
		pnlTacVu.add(txtDiaChiTim);

		JButton btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setBackground(new Color(255, 128, 128));
		btnTim.setForeground(new Color(0, 0, 0));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBounds(35, 345, 141, 50);
		pnlTacVu.add(btnTim);
		btnTim.addActionListener(e -> btnTimAction());
		
		addEnterKeyListener(txtMaKhachHangTim);
		addEnterKeyListener(btnTenKhachHangTim);
		addEnterKeyListener(txtDienThoaiTim);
		addEnterKeyListener(txtDiaChiTim);

		JButton btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setBackground(new Color(255, 128, 128));
		btnTaiLai.setForeground(new Color(0, 0, 0));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBounds(243, 345, 141, 50);
		pnlTacVu.add(btnTaiLai);
		btnTaiLai.addActionListener(e -> btnTaiLaiAction());
		
		btnThem.setEnabled(false);
		btnLuu.setEnabled(false);
		lockFields();
		
		// set unvisible for "NV"
//		if ("Nhân Viên".equals(chucVu)) {
//	        btnNhanVien.setEnabled(false);
//	    }

		tableKhachHang.getColumnModel().getColumn(0).setPreferredWidth(100);
		// Create a custom cell renderer that centers the text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Apply the renderer to each column of the table
		for (int i = 0; i < tableKhachHang.getColumnCount(); i++) {
			tableKhachHang.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		tableKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = tableKhachHang.getSelectedRow();
				txtMaKhachHang.setText((String) tableKhachHang.getValueAt(selectedRow, 0));
				txtMaKhachHang.setEditable(false);
				txtTenKhachHang.setText((String) tableKhachHang.getValueAt(selectedRow, 1));
				
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date ngaySinh = sdf.parse(tableKhachHang.getValueAt(selectedRow, 2).toString());
					txtNgaySinh.setDate(ngaySinh);
				} catch (ParseException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(Frame_KhachHang_DanhSachKhachHang.this, "Lỗi định dạng ngày sinh: ");
				}
				
				txtDiaChi.setText((String) tableKhachHang.getValueAt(selectedRow, 3));
				txtDienThoai.setText((String) tableKhachHang.getValueAt(selectedRow, 4));
				txtEmail.setText((String) tableKhachHang.getValueAt(selectedRow, 5));
				
				lockFields();
				btnThem.setEnabled(false);
				btnLuu.setEnabled(false);
			}
		});
		loadDataToTable();
	}
}
