package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;

import DAO.NhanVienDAO;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;

public class Frame_NhanVien_DanhSachNhanVien extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTextField txtMaNhanVien;
	private JTextField txtTenNhanVien;
	private JTextField txtCanCuoc;
	private JTextField txtEmail;
	private JTable tableNhanVien;
	private JTextField txtMaNhanVienTim;
	private JTextField txtTenNhanVienTim;
	private JTextField txtCanCuocTim;
	private JDateChooser txtNgaySinh;
	private JComboBox<String> txtGioiTinhTim,txtGioiTinh, txtChucVu, txtTrinhDo;
	private DefaultTableModel tableModel;
	private JTextField txtMatKhau;
	private NhanVienDAO nhanVienDAO = new NhanVienDAO();
	private JTextField txtLuong;
	private JTextField txtDiaChi;
	private JTextField txtSoDienThoai;
	private JRadioButton btnRadioNghiViec;
	private JButton btnNghi;
	private boolean trangThai = true;
	private JButton btnThem, btnLuu, btnLamViec;

	// Cập nhật dữ liệu vào table
	private void loadDataToTable() {
		trangThai = btnRadioNghiViec.isSelected() ? false : true;
		List<Object[]> data = nhanVienDAO.loadDataToTable(trangThai);
		tableModel = (DefaultTableModel) tableNhanVien.getModel();
		tableModel.setRowCount(0);
		for (Object[] row : data) {
			tableModel.addRow(row);
		}
	}

	public class KiemTraNhap {
		public static String validateID(String id) {
			if (id == null || id.isEmpty()) {
				return "Mã nhân viên không được để trống!";
			}
			String idRegex = "^NV\\d+$";
			if (!Pattern.matches(idRegex, id)) {
				return "Mã nhân viên không hợp lệ! Mã nhân viên phải bắt đầu bằng 'NV' và theo sau là các chữ số.";
			}
			return null;
		}

		public static String validateCCCD(String cccd) {
			if (cccd == null || cccd.isEmpty()) {
				return "Căn cước không được để trống!";
			}
			String cccdRegex = "^\\d{12}$";
			if (!Pattern.matches(cccdRegex, cccd)) {
				return "Căn cước không hợp lệ! Căn cước phải là chuỗi gồm 12 chữ số.";
			}
			return null;
		}

		public static String validatePhone(String phone) {
			if (phone == null || phone.isEmpty()) {
				return "Số điện thoại không được để trống!";
			}
			String phoneRegex = "^\\d{10}$";
			if (!Pattern.matches(phoneRegex, phone)) {
				return "Số điện thoại không hợp lệ! Số điện thoại phải là chuỗi gồm 10 chữ số.";
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

		public static String validateName(String name) {
			if (name == null || name.isEmpty()) {
				return "Họ và tên không được để trống!";
			}
			String nameRegex = "^[\\p{L} ]+$";
			if (!Pattern.matches(nameRegex, name)) {
				return "Họ và tên không hợp lệ! Họ và tên chỉ được chứa các ký tự chữ cái và khoảng trắng.";
			}
			return null;
		}

		public static String validatePassword(String password) {
			if (password == null || password.isEmpty()) {
				return "Mật khẩu không được để trống!";
			}
			return null;
		}

		public static String validateEmail(String email) {
			if (email == null || email.isEmpty()) {
				return "Email không được để trống!";
			}
			String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
			if (!Pattern.matches(emailRegex, email)) {
				return "Email không hợp lệ! Vui lòng nhập đúng định dạng email.";
			}
			return null;
		}
		
		public static String validateLuong(String luong) {
			if (luong == null || luong.isEmpty()) {
				return "Lương không được để trống!";
			}
			String luongRegex = "^\\d+$";
			if (!Pattern.matches(luongRegex, luong)) {
				return "Lương không hợp lệ! Lương phải là chuỗi gồm các chữ số.";
			}
			return null;
		}
		
		public static String validateDiaChi(String diaChi) {
			if (diaChi == null || diaChi.isEmpty()) {
				return "Địa chỉ không được để trống!";
			}
			return null;
		}
		
		public static String validateSoDienThoai(String soDienThoai) {
			if (soDienThoai == null || soDienThoai.isEmpty()) {
				return "Số điện thoại không được để trống!";
			}
			String soDienThoaiRegex = "^\\d{10}$";
			if (!Pattern.matches(soDienThoaiRegex, soDienThoai)) {
				return "Số điện thoại không hợp lệ! Số điện thoại phải là chuỗi gồm 10 chữ số.";
			}
			return null;
		}
		
	}

	private boolean isDuplicateCCCD(String cccd, String maNV) {
		return nhanVienDAO.isDuplicateCCCD(cccd, maNV);
	}

	private boolean isDuplicateEmail(String email, String maNV) {
		return nhanVienDAO.isDuplicateEmail(email, maNV);
	}

	// Sự kiện nút Thêm
	private void btnThemActionPerformed() throws ParseException {
		// Set the txtMaNhanVien text field to be editable
		txtMaNhanVien.setEditable(true);

		// Existing code for adding a new employee
		String maNV = txtMaNhanVien.getText().trim();
		String hoTen = txtTenNhanVien.getText().trim();
		String email = txtEmail.getText().trim();
		String cccd = txtCanCuoc.getText().trim();
		String gioiTinh = txtGioiTinh.getSelectedItem().toString();
		String matKhau = txtMatKhau.getText().trim();
		String chucVu = txtChucVu.getSelectedItem().toString();
		String trinhDo = txtTrinhDo.getSelectedItem().toString();
		String luong = txtLuong.getText().replaceAll("[^0-9]", "");
		String soDienThoai = txtSoDienThoai.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		

		String error;

		error = KiemTraNhap.validateID(maNV);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtMaNhanVien.requestFocus();
			return;
		}

		error = KiemTraNhap.validateName(hoTen);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtTenNhanVien.requestFocus();
			return;
		}

		error = KiemTraNhap.validateEmail(email);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtEmail.requestFocus();
			return;
		}

		error = KiemTraNhap.validateCCCD(cccd);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtCanCuoc.requestFocus();
			return;
		}

		error = KiemTraNhap.validatePassword(matKhau);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtMatKhau.requestFocus();
			return;
		}

		error = KiemTraNhap.validateLuong(luong);
		if (error != null) {
	        JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
	        txtLuong.requestFocus();
	        return;
	    }
		
		error = KiemTraNhap.validateDiaChi(diaChi);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtDiaChi.requestFocus();
			return;
		}
		
		error = KiemTraNhap.validateSoDienThoai(soDienThoai);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtSoDienThoai.requestFocus();
			return;
		}
		
		
		if (isDuplicateCCCD(cccd, maNV)) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this,
					"Error: Căn cước đã tồn tại! Vui lòng kiểm tra lại thông tin.");
			txtCanCuoc.requestFocus();
			return;
		}

		if (isDuplicateEmail(email, maNV)) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this,
					"Error: Email đã tồn tại! Vui lòng kiểm tra lại thông tin.");
			txtEmail.requestFocus();
			return;
		}

		Date ngaySinh = txtNgaySinh.getDate();
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this,
					"Error: Ngày không hợp lệ! Ngày phải theo định dạng dd/MM/yyyy.");
			txtNgaySinh.requestFocus();
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngaySinhStr = sdf.format(ngaySinh);

		nhanVienDAO.saveNhanVien(false, maNV, hoTen, ngaySinhStr, gioiTinh, cccd, soDienThoai, email, diaChi, chucVu, trinhDo, luong, matKhau);
		btnThem.setEnabled(false);
		loadDataToTable();
		clearFields();
		lockFields();
		generateNextMaNVAgain();
	}
	
	// Sự kiện nút Sửa
	private void btnSuaActionPerformed() {
		unlockFields();
		
		int selectedRow = tableNhanVien.getSelectedRow();
		if (selectedRow == -1) {
			btnThem.setEnabled(true);
		} else {
			btnLuu.setEnabled(true);
		}
	}

	// Sự kiện nút Lưu
	private void btnLuuActionPerformed() throws ParseException {
		int selectedRow = tableNhanVien.getSelectedRow();
		if (selectedRow < 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa để lưu!");
			return;
		}

		// Set the txtMaNhanVien text field to be non-editable
		txtMaNhanVien.setEditable(false);

		String maNV = txtMaNhanVien.getText().trim();
		String hoTen = txtTenNhanVien.getText().trim();
		String email = txtEmail.getText().trim();
		String cccd = txtCanCuoc.getText().trim();
		String gioiTinh = txtGioiTinh.getSelectedItem().toString();
		String matKhau = txtMatKhau.getText().trim();
		String chucVu = txtChucVu.getSelectedItem().toString();
		String trinhDo = txtTrinhDo.getSelectedItem().toString();
		String luong = txtLuong.getText().replaceAll("[^0-9]", "");
		String soDienThoai = txtSoDienThoai.getText().trim();
		String diaChi = txtDiaChi.getText().trim();

		String error;

		error = KiemTraNhap.validateID(maNV);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtMaNhanVien.requestFocus();
			return;
		}

		error = KiemTraNhap.validateName(hoTen);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtTenNhanVien.requestFocus();
			return;
		}

		error = KiemTraNhap.validateEmail(email);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtEmail.requestFocus();
			return;
		}

		error = KiemTraNhap.validateCCCD(cccd);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtCanCuoc.requestFocus();
			return;
		}

		error = KiemTraNhap.validatePassword(matKhau);
		if (error != null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
			txtMatKhau.requestFocus();
			return;
		}
		
		error = KiemTraNhap.validateLuong(luong);
		if (error != null) {
	        JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this, "Error: " + error);
	        txtLuong.requestFocus();
	        return;
	    }

		if (nhanVienDAO.isDuplicateCCCD(cccd, maNV)) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this,
					"Error: Căn cước đã tồn tại! Vui lòng kiểm tra lại thông tin.");
			txtCanCuoc.requestFocus();
			return;
		}

		if (isDuplicateEmail(email, maNV)) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this,
					"Error: Email đã tồn tại! Vui lòng kiểm tra lại thông tin.");
			txtEmail.requestFocus();
			return;
		}

		Date ngaySinh = txtNgaySinh.getDate();
		if (ngaySinh == null) {
			JOptionPane.showMessageDialog(Frame_NhanVien_DanhSachNhanVien.this,
					"Error: Ngày sinh không hợp lệ! Vui lòng chọn ngày sinh.");
			txtNgaySinh.requestFocus();
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String ngaySinhStr = sdf.format(ngaySinh);

		nhanVienDAO.saveNhanVien(true, maNV, hoTen, ngaySinhStr, gioiTinh, cccd, soDienThoai, email, diaChi, chucVu, trinhDo, luong, matKhau);
		btnLuu.setEnabled(false);
		loadDataToTable();
		clearFields();
		lockFields();
		generateNextMaNVAgain();
	}

	// Sự kiện nút Nghỉ
	private void btnNghiActionPerformed() {
		int selectedRow = tableNhanVien.getSelectedRow();
		if (selectedRow < 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần thôi việc!");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn cho nhân viên thôi việc?", "Xác nhận thôi việc",
				JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			String maNV = tableModel.getValueAt(selectedRow, 0).toString();
			nhanVienDAO.thoiViecNhanVien(maNV);
			loadDataToTable();
			clearFields();
			lockFields();
			generateNextMaNVAgain();
		}
	}
	
	// Sự kiện nút Quay lại
	private void btnLamViecActionPerformed() {
		int selectedRow = tableNhanVien.getSelectedRow();
		if (selectedRow < 0) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần làm việc lại!");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn cho nhân viên làm việc lại?",
				"Xác nhận làm việc lại", JOptionPane.YES_NO_OPTION);

		if (confirm == JOptionPane.YES_OPTION) {
			String maNV = tableModel.getValueAt(selectedRow, 0).toString();
			nhanVienDAO.quayLaiLamViec(maNV);
			loadDataToTable();
			clearFields();
			lockFields();
			generateNextMaNVAgain();
		}
	}

	// Sự kiện nút Tìm kiếm
	private void btnTimActionPerformed() {
	    String maNV = txtMaNhanVienTim.getText().trim();
	    String hoTen = txtTenNhanVienTim.getText().trim();
	    String cccd = txtCanCuocTim.getText().trim();
	    Object selectedItem = txtGioiTinhTim.getSelectedItem();
	    String gioiTinh = (selectedItem != null) ? selectedItem.toString().trim() : "";
	    trangThai = btnRadioNghiViec.isSelected() ? false : true;
	    
		if (btnRadioNghiViec.isSelected()) {
			btnNghi.setVisible(false);
			btnLamViec.setVisible(true);
		} else {
			btnNghi.setVisible(true);
			btnLamViec.setVisible(false);
		}
	    
	    List<Object[]> data = nhanVienDAO.searchNhanVien(maNV, hoTen, cccd, trangThai, gioiTinh.equals("") ? null : gioiTinh);
	    tableModel.setRowCount(0);
	    for (Object[] row : data) {
	        tableModel.addRow(row);
	    }
	}

// Sự kiện phím tắt enter cho tìm kiếm
	@SuppressWarnings("serial")
	private void addEnterKeyListener(JComponent component) {
	    component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "searchAction");
	    component.getActionMap().put("searchAction", new AbstractAction() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	btnTimActionPerformed(); // Thực hiện tìm kiếm ngay khi nhấn Enter
	        }
	    });
	}

// Sự kiện nút Tải lại
	private void btnTaiLaiActionPerformed() {
		btnNghi.setVisible(true); // Hiện nút nghỉ
		btnLamViec.setVisible(false); // Ẩn nút làm việc
		btnRadioNghiViec.setSelected(false); // Bỏ chọn radio button nghỉ việc
		clearSearchFields();
		loadDataToTable();
		btnHuyActionPerformed(); // Xóa trắng form
	}

	
	// Sự kiện nút Hủy
	private void btnHuyActionPerformed() {
		clearFields();
		generateNextMaNVAgain();
		
		tableNhanVien.clearSelection();
		btnThem.setEnabled(false);
		btnLuu.setEnabled(false);
	}
	
// Phương thức hỗ trợ xóa trắng form
	private void clearFields() {
		txtMaNhanVien.setText("");
		txtTenNhanVien.setText("");
		txtEmail.setText("");
		txtCanCuoc.setText("");
		txtMatKhau.setText("");
		txtNgaySinh.setDate(null);
		txtGioiTinh.setSelectedIndex(0);
		txtChucVu.setSelectedIndex(0);
		txtLuong.setText("");
		txtTrinhDo.setSelectedIndex(0);
		txtDiaChi.setText("");
		txtSoDienThoai.setText("");
		lockFields();
	}

// Phương thức hỗ trợ xóa trắng form tìm kiếm
	private void clearSearchFields() {
		txtMaNhanVienTim.setText("");
		txtTenNhanVienTim.setText("");
		txtCanCuocTim.setText("");
		txtGioiTinhTim.setSelectedIndex(0);
		btnRadioNghiViec.setSelected(false);
	}

	// Thêm phương thức khóa textfield
	private void lockFields() {
	    txtMaNhanVien.setEditable(false);
	    txtTenNhanVien.setEditable(false);
	    txtCanCuoc.setEditable(false);
	    txtEmail.setEditable(false);
	    txtMatKhau.setEditable(false);
	    txtNgaySinh.setEnabled(false);
	    txtGioiTinh.setEnabled(false);
	    txtChucVu.setEnabled(false);
	    txtLuong.setEditable(false);
	    txtTrinhDo.setEnabled(false);
	    txtDiaChi.setEditable(false);
	    txtSoDienThoai.setEditable(false);
	}
	
	// Thêm phương thức mở khóa textfield
	private void unlockFields() {
	    txtTenNhanVien.setEditable(true);
	    txtCanCuoc.setEditable(true);
	    txtEmail.setEditable(true);
	    txtMatKhau.setEditable(true);
	    txtNgaySinh.setEnabled(true);
	    txtGioiTinh.setEnabled(true);
	    txtChucVu.setEnabled(true);
	    txtLuong.setEditable(true);
	    txtTrinhDo.setEnabled(true);
	    txtDiaChi.setEditable(true);
	    txtSoDienThoai.setEditable(true);
	    // Giữ txtMaNhanVien không chỉnh sửa được
	    txtMaNhanVien.setEditable(false);
	}
	
	// Sự kiện nút Xuất file
	private void btnXuatFileAction() {
	    Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet("DanhSachNhanVien");

	    // Create header row
	    Row headerRow = sheet.createRow(0);
	    String[] headers = {"Mã nhân viên", "Họ tên", "Ngày Sinh", "Giới tính", "CCCD", "Số điện thoại", "Email", "Địa chỉ", "Chức vụ", "Trình độ", "Lương"};
	    for (int i = 0; i < headers.length; i++) {
	        Cell cell = headerRow.createCell(i);
	        cell.setCellValue(headers[i]);
	    }

	    // Populate data rows
	    List<Object[]> List = nhanVienDAO.getAllNhanVien();
	    int rowNum = 1;
	    for (Object[] nv : List) {
	        Row row = sheet.createRow(rowNum++);
	        row.createCell(0).setCellValue((String) nv[0]);
	        row.createCell(1).setCellValue((String) nv[1]);
	        row.createCell(2).setCellValue((String) nv[2]);
	        row.createCell(3).setCellValue((String) nv[3]);
	        row.createCell(4).setCellValue((String) nv[4]);
	        row.createCell(5).setCellValue((String) nv[5]);
	        row.createCell(6).setCellValue((String) nv[6]);
	        row.createCell(7).setCellValue((String) nv[7]);
	        row.createCell(8).setCellValue((String) nv[8]);
	        row.createCell(9).setCellValue((String) nv[9]);
	        row.createCell(10).setCellValue((String) nv[10]);
	    }

	    // Write the output to a file
	    try (FileOutputStream fileOut = new FileOutputStream("DanhSachNhanVien.xlsx")) {
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
	
	// Phương thức tạo mã nhân viên tự động
	private String generateNextMaNV() {
	    String lastMaNV = nhanVienDAO.getLastMaNV();
	    if (lastMaNV == null || lastMaNV.isEmpty() || lastMaNV.equals("NV000")) {
	        return "NV001"; 
	    }

	    String numericPart = lastMaNV.substring(2); 
	    int number = Integer.parseInt(numericPart);
	    number++;

	    return String.format("NV%03d", number);
	}
	
	// generate next again
	private void generateNextMaNVAgain() {
		String nextMaNV = generateNextMaNV();
		txtMaNhanVien.setText(nextMaNV);
	}
	/**
	 * Launch the application.
	 */
//public static void main(String[] args) {
//    EventQueue.invokeLater(new Runnable() {
//        public void run() {
//            try {
//                FrameQuanLyNhanVien frame = new FrameQuanLyNhanVien();
//                frame.setVisible(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    });
//}
	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Frame_NhanVien_DanhSachNhanVien() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1543, 755);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(26, 315, 1117, 74);
		panel.setBackground(new Color(255, 128, 64));
		pnlBackGround.add(panel);
		panel.setLayout(null);

		btnLuu = new JButton("Lưu");
		btnLuu.setIcon(new ImageIcon("icon\\save.png"));
		btnLuu.setForeground(new Color(0, 0, 0));
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setBackground(new Color(167, 62, 20));
		btnLuu.setBounds(390, 13, 130, 50);
		panel.add(btnLuu);

		btnNghi = new JButton("Nghỉ");
		btnNghi.setIcon(new ImageIcon("icon\\layoff.png"));
		btnNghi.setForeground(new Color(0, 0, 0));
		btnNghi.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnNghi.setBackground(new Color(167, 62, 20));
		btnNghi.setBounds(579, 13, 130, 50);
		panel.add(btnNghi);
		
		btnLamViec = new JButton("Trở Lại");
		btnLamViec.setIcon(new ImageIcon("icon\\back_job.png"));
		btnLamViec.setForeground(Color.BLACK);
		btnLamViec.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLamViec.setBackground(new Color(167, 62, 20));
		btnLamViec.setBounds(579, 13, 130, 50);
		panel.add(btnLamViec);

		JButton btnHuy = new JButton("Hủy");
		btnHuy.setIcon(new ImageIcon("icon\\cancel.png"));
		btnHuy.setForeground(new Color(0, 0, 0));
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHuy.setBackground(new Color(167, 62, 20));
		btnHuy.setBounds(769, 13, 130, 50);
		panel.add(btnHuy);

		JButton btnXuat = new JButton("Xuất");
		btnXuat.addActionListener(e -> btnXuatFileAction());
		btnXuat.setIcon(new ImageIcon("icon\\print.png"));
		btnXuat.setForeground(new Color(0, 0, 0));
		btnXuat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXuat.setBackground(new Color(167, 62, 20));
		btnXuat.setBounds(954, 13, 130, 50);
		panel.add(btnXuat);

		btnThem = new JButton("Thêm");
		btnThem.setBounds(25, 13, 130, 50);
		panel.add(btnThem);
		btnThem.setIcon(new ImageIcon("icon\\add.png"));
		btnThem.setForeground(new Color(0, 0, 0));
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBackground(new Color(167, 62, 20));
		
		JButton btnSua = new JButton("Sửa");
		btnSua.setIcon(new ImageIcon("icon\\edit.png"));
		btnSua.setForeground(Color.BLACK);
		btnSua.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSua.setBackground(new Color(167, 62, 20));
		btnSua.setBounds(207, 13, 130, 50);
		panel.add(btnSua);

		JLabel lblCCCD = new JLabel("Căn cước:");
		lblCCCD.setBounds(26, 110, 160, 40);
		lblCCCD.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlBackGround.add(lblCCCD);

		JLabel lblMaNhanVien = new JLabel("Mã nhân viên:");
		lblMaNhanVien.setBounds(26, 10, 160, 40);
		lblMaNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlBackGround.add(lblMaNhanVien);

		JLabel lblHoTenNhanVien = new JLabel("Họ và tên:");
		lblHoTenNhanVien.setBounds(26, 60, 160, 40);
		lblHoTenNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlBackGround.add(lblHoTenNhanVien);

		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioiTinh.setBounds(600, 160, 160, 40);
		pnlBackGround.add(lblGioiTinh);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblEmail.setBounds(600, 10, 160, 40);
		pnlBackGround.add(lblEmail);

		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNgaySinh.setBounds(26, 160, 160, 40);
		pnlBackGround.add(lblNgaySinh);

		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblChucVu.setBounds(600, 260, 160, 40);
		pnlBackGround.add(lblChucVu);

		JLabel lblMatKhau = new JLabel("Mật khẩu:");
		lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMatKhau.setBounds(600, 60, 160, 40);
		pnlBackGround.add(lblMatKhau);

		txtMaNhanVien = new JTextField();
		txtMaNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaNhanVien.setBounds(194, 10, 358, 35);
		pnlBackGround.add(txtMaNhanVien);
		txtMaNhanVien.setColumns(10);
		// Auto uppercase

		txtMaNhanVien.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtMaNhanVien.getText();
				txtMaNhanVien.setText(text.toUpperCase());
			}
		});
		
		generateNextMaNVAgain();

		txtTenNhanVien = new JTextField();
		txtTenNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenNhanVien.setColumns(10);
		txtTenNhanVien.setBounds(194, 60, 358, 35);
		pnlBackGround.add(txtTenNhanVien);

		txtCanCuoc = new JTextField();
		txtCanCuoc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtCanCuoc.setColumns(10);
		txtCanCuoc.setBounds(194, 110, 358, 35);
		pnlBackGround.add(txtCanCuoc);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtEmail.setColumns(10);
		txtEmail.setBounds(785, 14, 358, 35);
		pnlBackGround.add(txtEmail);

		txtChucVu = new JComboBox<>();
		txtChucVu.setModel(new DefaultComboBoxModel<>(new String[] { "Quản lý", "Nhân viên" }));
		txtChucVu.setBounds(785, 264, 193, 35);
		txtChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 18)); //
		pnlBackGround.add(txtChucVu);

		txtMatKhau = new JTextField();
		txtMatKhau.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMatKhau.setColumns(10);
		txtMatKhau.setBounds(785, 64, 358, 35);
		pnlBackGround.add(txtMatKhau);

		txtNgaySinh = new JDateChooser();
		txtNgaySinh.setDateFormatString("dd/MM/yyyy");
		txtNgaySinh.setBounds(194, 156, 358, 35);
		pnlBackGround.add(txtNgaySinh);
		txtNgaySinh.getDateEditor().getUiComponent().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		
		txtGioiTinh = new JComboBox();
		txtGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtGioiTinh.setModel(new DefaultComboBoxModel(new String[] { "Nam", "Nữ" }));
		txtGioiTinh.setBounds(785, 164, 193, 35);
		pnlBackGround.add(txtGioiTinh);

		JScrollPane scrollPaneNhanVien = new JScrollPane();
		scrollPaneNhanVien.setBounds(10, 399, 1523, 346);
		pnlBackGround.add(scrollPaneNhanVien);

		tableNhanVien = new JTable();
		tableNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableNhanVien.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã NV", "Họ tên",
				 "Ngày sinh", "Giới Tính", "CCCD", "SDT", "Email", "Địa chỉ", "Chức vụ", "Trình độ", "Lương", "Mật khẩu"}) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false, false, false, false };
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableNhanVien.getColumnModel().getColumn(2).setPreferredWidth(55);

		JTableHeader header = tableNhanVien.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tableNhanVien.setRowHeight(30);
		scrollPaneNhanVien.setViewportView(tableNhanVien);
		// Create a custom cell renderer that centers the text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Apply the renderer to each column of the table
		for (int i = 0; i < tableNhanVien.getColumnCount(); i++) {
			tableNhanVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JPanel pnlTacVu = new JPanel();
		pnlTacVu.setBackground(new Color(255, 128, 64));
		pnlTacVu.setBounds(1167, 10, 366, 379);
		pnlBackGround.add(pnlTacVu);
		pnlTacVu.setLayout(null);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tác vụ");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		pnlTacVu.setBorder(titledBorder);

		JLabel lblMaNhanVienTim = new JLabel("Mã nhân viên:");
		lblMaNhanVienTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaNhanVienTim.setBounds(21, 37, 180, 34);
		pnlTacVu.add(lblMaNhanVienTim);

		JLabel lblTenNhanVienTim = new JLabel("Tên nhân viên:");
		lblTenNhanVienTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTenNhanVienTim.setBounds(21, 100, 180, 34);
		pnlTacVu.add(lblTenNhanVienTim);

		JLabel lblCanCuocTim = new JLabel("Căn cước:");
		lblCanCuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCanCuocTim.setBounds(21, 159, 180, 34);
		pnlTacVu.add(lblCanCuocTim);

		JLabel lblGioiTinhTim = new JLabel("Giới tính:");
		lblGioiTinhTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblGioiTinhTim.setBounds(21, 219, 180, 34);
		pnlTacVu.add(lblGioiTinhTim);

		txtMaNhanVienTim = new JTextField();
		txtMaNhanVienTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaNhanVienTim.setBounds(175, 37, 166, 38);
		pnlTacVu.add(txtMaNhanVienTim);
		txtMaNhanVienTim.setColumns(10);
		
		// Auto uppercase
		txtMaNhanVienTim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtMaNhanVien.getText();
				txtMaNhanVien.setText(text.toUpperCase());
			}
		});

		txtTenNhanVienTim = new JTextField();
		txtTenNhanVienTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenNhanVienTim.setColumns(10);
		txtTenNhanVienTim.setBounds(175, 100, 166, 38);
		pnlTacVu.add(txtTenNhanVienTim);

		txtCanCuocTim = new JTextField();
		txtCanCuocTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtCanCuocTim.setColumns(10);
		txtCanCuocTim.setBounds(175, 159, 166, 38);
		pnlTacVu.add(txtCanCuocTim);

		txtGioiTinhTim = new JComboBox();
		txtGioiTinhTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtGioiTinhTim.setModel(new DefaultComboBoxModel(new String[] {"", "Nam", "Nữ" }));
		txtGioiTinhTim.setBounds(175, 221, 166, 38);
		pnlTacVu.add(txtGioiTinhTim);

		JButton btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setBackground(new Color(255, 128, 128));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBounds(21, 319, 141, 50);
		pnlTacVu.add(btnTim);
		
		addEnterKeyListener(txtMaNhanVienTim);
		addEnterKeyListener(txtTenNhanVienTim);
		addEnterKeyListener(txtCanCuocTim);
		addEnterKeyListener(txtGioiTinhTim);

		JButton btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setBackground(new Color(255, 128, 128));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBounds(200, 319, 141, 50);
		pnlTacVu.add(btnTaiLai);
		
		btnRadioNghiViec = new JRadioButton("Đã Nghỉ Việc");
		btnRadioNghiViec.setBackground(new Color(255, 128, 64));
		btnRadioNghiViec.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnRadioNghiViec.setBounds(21, 265, 186, 48); 
		pnlTacVu.add(btnRadioNghiViec);
		
		JLabel lblLuong = new JLabel("Lương:");
		lblLuong.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblLuong.setBounds(26, 260, 160, 40);
		pnlBackGround.add(lblLuong);
		
		txtLuong = new JTextField();
		txtLuong.setText("");
		txtLuong.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtLuong.setEditable(false);
		txtLuong.setColumns(10);
		txtLuong.setBounds(194, 260, 358, 35);
		pnlBackGround.add(txtLuong);
		
		JLabel lblTrinhDo = new JLabel("Trình độ:");
		lblTrinhDo.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTrinhDo.setBounds(600, 210, 160, 40);
		pnlBackGround.add(lblTrinhDo);
		
		txtTrinhDo = new JComboBox();
		txtTrinhDo.setModel(new DefaultComboBoxModel<String>(new String[] {"Dược sĩ trung cấp", "Dược sĩ cao đẳng", "Dược sĩ đại học", "Dược sĩ sau đại học"}));
		txtTrinhDo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTrinhDo.setBounds(785, 214, 193, 35);
		pnlBackGround.add(txtTrinhDo);
		
		JLabel lblDiaChi = new JLabel("Địa Chỉ");
		lblDiaChi.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblDiaChi.setBounds(26, 210, 160, 40);
		pnlBackGround.add(lblDiaChi);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setText("");
		txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDiaChi.setEditable(false);
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(194, 207, 358, 35);
		pnlBackGround.add(txtDiaChi);
		
		JLabel lblSoDienThoai = new JLabel("Số Điện Thoại:");
		lblSoDienThoai.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblSoDienThoai.setBounds(600, 110, 160, 40);
		pnlBackGround.add(lblSoDienThoai);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(855, 160, 1, 1);
		pnlBackGround.add(layeredPane);
		
		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setText("");
		txtSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSoDienThoai.setEditable(false);
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBounds(785, 114, 358, 35);
		pnlBackGround.add(txtSoDienThoai);

		btnThem.addActionListener(e -> {
			try {
				btnThemActionPerformed();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnSua.addActionListener(e -> btnSuaActionPerformed());
		btnLuu.addActionListener(e -> {
			try {
				btnLuuActionPerformed();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnNghi.addActionListener(e -> btnNghiActionPerformed());
		btnHuy.addActionListener(e -> btnHuyActionPerformed());
		btnTim.addActionListener(e -> btnTimActionPerformed());
		btnTaiLai.addActionListener(e -> btnTaiLaiActionPerformed());
		btnLamViec.addActionListener(e -> btnLamViecActionPerformed());
		lockFields();
		
		btnThem.setEnabled(false);
		btnLuu.setEnabled(false);
		
		// Sự kiện format tiền lương
		txtLuong.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        String text = txtLuong.getText().replaceAll("[^0-9]", ""); // Loại bỏ mọi ký tự không phải số
		        if (!text.isEmpty()) {
		            try {
		                // Chuyển chuỗi số thành số nguyên
		                long number = Long.parseLong(text);
		                // Định dạng số với dấu phẩy
		                String formattedNumber = String.format("%,d", number);
		                // Thêm chữ "đ" vào cuối
		                txtLuong.setText(formattedNumber + "đ");
		            } catch (NumberFormatException ex) {
		                txtLuong.setText(""); // Xóa nếu không hợp lệ
		            }
		        }
		    }
		});
		
		
		tableNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int selectedRow = tableNhanVien.getSelectedRow();
				if (selectedRow >= 0) {
					txtMaNhanVien.setText(tableModel.getValueAt(selectedRow, 0).toString());
					txtMaNhanVien.setEditable(false);

					txtTenNhanVien.setText(tableModel.getValueAt(selectedRow, 1).toString());
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						Date ngaySinh = sdf.parse(tableModel.getValueAt(selectedRow, 2).toString());
						txtNgaySinh.setDate(ngaySinh);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Lỗi định dạng ngày sinh!");
					}
					txtGioiTinh.setSelectedItem(tableModel.getValueAt(selectedRow, 3).toString());
					txtCanCuoc.setText(tableModel. getValueAt(selectedRow, 4).toString());
					txtSoDienThoai.setText(tableModel.getValueAt(selectedRow, 5).toString());
					txtEmail.setText(tableModel.getValueAt(selectedRow, 6).toString());
					txtDiaChi.setText(tableModel.getValueAt(selectedRow, 7).toString());
					txtChucVu.setSelectedItem(tableModel.getValueAt(selectedRow, 8).toString());
					txtTrinhDo.setSelectedItem(tableModel.getValueAt(selectedRow, 9).toString());
					txtLuong.setText(tableModel.getValueAt(selectedRow, 10).toString());
					txtMatKhau.setText(tableModel.getValueAt(selectedRow, 11).toString());
					
					// Hiển thị nút nghỉ việc hoặc làm việc lại tùy theo trạng thái
					trangThai = nhanVienDAO.getTrangThaiNhanVien(txtMaNhanVien.getText());
					btnNghi.setVisible(trangThai);
					lockFields();
					btnThem.setEnabled(false);
					btnLuu.setEnabled(false);
				}
			}
		});

		// Load dữ liệu ban đầu
		loadDataToTable();
	}
}
