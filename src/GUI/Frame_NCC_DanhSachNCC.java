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

import DAO.NhaCungCapDAO;

import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;



public class Frame_NCC_DanhSachNCC extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton btnTim;
	private JButton btnTaiLai;
	private JButton btnThem;
	private JButton btnXuat;
	private JButton btnHuy;
	private JButton btnLuu;
	private JButton btnSua;
	private JButton btnHopTacLai;
	private DefaultTableModel tableModel;
	private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
	private boolean trangThai = true; // true: đang hợp tác, false: ngừng hợp tác
	private JTable tableNhaCungCap;
	private JTextField txtTimDiaChi;
	private JTextField txtTimTen;
	private JTextField txtTimMa;
	private JTextField txtTimEmail;
	private JTextField txtMaNCC;
	private JTextField txtEmail;
	private JTextField txtTenNCC;
	private JTextField txtDiaChi;
	private JTextField txtSoDienThoai;
	private JRadioButton radioHopTac;
	private JButton btnNgungHopTac;
	
	// Cập nhật dữ liệu vào table
		private void loadDataToTable() {
			trangThai = radioHopTac.isSelected() ? false : true;
			List<Object[]> data = nhaCungCapDAO.loadDataToTable(trangThai);
			tableModel = (DefaultTableModel) tableNhaCungCap.getModel();
			tableModel.setRowCount(0);
			for (Object[] row : data) {
				tableModel.addRow(row);
			}
		}
		// Kiểm tra nhập liệu
		public class KiemTraNhap {
			public static String validateID(String id) {
				if (id == null || id.isEmpty()) {
					return "Mã nhân viên không được để trống!";
				}
				String idRegex = "^NCC\\d+$";
				if (!Pattern.matches(idRegex, id)) {
					return "Mã nhà cung cấp không hợp lệ! Mã nhà cung cấp phải bắt đầu bằng 'NCC' và theo sau là các chữ số.";
				}
				return null;
			}

			public static String validateName(String name) {
				if (name == null || name.isEmpty()) {
					return "Tên không được để trống!";
				}
				String nameRegex = "^[\\p{L} ]+$";
				if (!Pattern.matches(nameRegex, name)) {
					return "Tên không hợp lệ! Họ và tên chỉ được chứa các ký tự chữ cái và khoảng trắng.";
				}
				return null;
			}
			public static String validatePhoneNumber(String phoneNumber) {
                if (phoneNumber == null || phoneNumber.isEmpty()) {
                    return "Số điện thoại không được để trống!";
                }
                String phoneRegex = "^\\d{10,15}$";
                if (!Pattern.matches(phoneRegex, phoneNumber)) {
                    return "Số điện thoại không hợp lệ! Vui lòng nhập đúng định dạng số điện thoại.";
                }
                return null;
            }
			public static String validateAddress(String address) {
				if (address == null || address.isEmpty()) {
                    return "Địa chỉ không được để trống!";
                }
                String addressRegex = "^[\\p{L}0-9., ]+$";
                if (!Pattern.matches(addressRegex, address)) {
                    return "Địa chỉ không hợp lệ! Địa chỉ chỉ được chứa các ký tự chữ cái, số và dấu câu.";
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
		}
		// Kiểm tra trùng mã nhà cung cấp
		private boolean isDuplicateEmail(String email, String maNCC) {
			return nhaCungCapDAO.isDuplicateEmail(email, maNCC);
		}
		
		// Sự kiện nút Thêm
		private void btnThemActionPerformed() throws ParseException {
			// Set the txtMaNCC text field to be editable
			txtMaNCC.setEditable(true);

			// Existing code for adding a new employee
			String mancc = txtMaNCC.getText().trim();
			String hoTen = txtTenNCC.getText().trim();
			String email = txtEmail.getText().trim();	
			String diaChi = txtDiaChi.getText().trim();
			String soDienThoai = txtSoDienThoai.getText().trim();
			
			String error;

			error = KiemTraNhap.validateID(mancc);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtMaNCC.requestFocus();
				return;
			}

			error = KiemTraNhap.validateName(hoTen);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtTenNCC.requestFocus();
				return;
			}

			error = KiemTraNhap.validateEmail(email);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtEmail.requestFocus();
				return;
			}
			error = KiemTraNhap.validateAddress(diaChi);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtDiaChi.requestFocus();
				return;
			}
			
			error = KiemTraNhap.validatePhoneNumber(soDienThoai);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtSoDienThoai.requestFocus();
				return;
			}
			
			
			if (isDuplicateEmail(email, mancc)) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this,
						"Error: Căn cước đã tồn tại! Vui lòng kiểm tra lại thông tin.");
				txtMaNCC.requestFocus();
				return;
			}

			nhaCungCapDAO.saveNhaCungCap(false, mancc, hoTen,diaChi,soDienThoai,email);
			loadDataToTable();
			clearFields();
			lockFields();
			generateNextMaNCCAgain();
		}
		// Xóa trắng
		private void clearFields() {
			txtMaNCC.setText("");
			txtTenNCC.setText("");
			txtDiaChi.setText("");
			txtEmail.setText("");
			txtSoDienThoai.setText("");
//			lockFields();
		}
		// Khóa các trường nhập liệu
		private void lockFields() {
			txtMaNCC.setEditable(false);
			txtTenNCC.setEditable(false);
			txtDiaChi.setEditable(false);
			txtSoDienThoai.setEditable(false);
			txtEmail.setEditable(false);
		}
		// Mở khóa các trường nhập liệu
		private void unlockFields() {
			txtMaNCC.setEditable(true);
			txtTenNCC.setEditable(true);
			txtDiaChi.setEditable(true);
			txtSoDienThoai.setEditable(true);
			txtEmail.setEditable(true);
		}

		// Tạo mã nhà cung cấp tự động
		private String generateNextMaNCC() {
		    String lastMaNCC = nhaCungCapDAO.getLastMaNCC();
		    if (lastMaNCC == null || lastMaNCC.isEmpty() || lastMaNCC.equals("NCC000")) {
		        return "NCC001"; 
		    }

		    String numericPart = lastMaNCC.substring(3); 
		    int number = Integer.parseInt(numericPart);
		    number++;

		    return String.format("NCC%03d", number);
		}
		// generate next again
		private void generateNextMaNCCAgain() {
			String nextMaNCC = generateNextMaNCC();
			txtMaNCC.setText(nextMaNCC);
		}
		// Sự kiện nút Sửa
		private void btnSuaActionPerformed() {
			unlockFields();
			
			int selectedRow = tableNhaCungCap.getSelectedRow();
			if (selectedRow == -1) {
				btnThem.setEnabled(true);
			} else {
				btnLuu.setEnabled(true);
			}
		}
		// Sự kiện nút Lưu
		private void btnLuuActionPerformed() throws ParseException {
			int selectedRow = tableNhaCungCap.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa để lưu!");
				return;
			}

			// Set the txtMaNhanVien text field to be non-editable
			txtMaNCC.setEditable(false);

			String maNCC = txtMaNCC.getText().trim();
			String tenNCC = txtTenNCC.getText().trim();
			String diaChi = txtDiaChi.getText().trim();
			String sdt = txtSoDienThoai.getText().trim();
			String email = txtEmail.getText().trim();

			String error;

			error = KiemTraNhap.validateID(maNCC);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtMaNCC.requestFocus();
				return;
			}

			error = KiemTraNhap.validateName(tenNCC);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtTenNCC.requestFocus();
				return;
			}

			error = KiemTraNhap.validatePhoneNumber(sdt);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtSoDienThoai.requestFocus();
				return;
			}

			error = KiemTraNhap.validateEmail(email);
			if (error != null) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this, "Error: " + error);
				txtEmail.requestFocus();
				return;
			}


			if (nhaCungCapDAO.isDuplicateEmail(email, maNCC)) {
				JOptionPane.showMessageDialog(Frame_NCC_DanhSachNCC.this,
						"Error: Căn cước đã tồn tại! Vui lòng kiểm tra lại thông tin.");
				txtEmail.requestFocus();
				return;
			}

			nhaCungCapDAO.saveNhaCungCap(true,maNCC, tenNCC,diaChi,sdt,email);
			loadDataToTable();
			clearFields();
			lockFields();
			generateNextMaNCCAgain();
		}
		// Sự kiện nút ngừng hợp tác
		private void btnNgungHopTacActionPerformed() {
			int selectedRow = tableNhaCungCap.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần ngưng hợp tác!");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn ngưng hợp tác với nhà cung cấp này?", "Xác nhận ngưng hợp tác",
					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				String maNCC = tableModel.getValueAt(selectedRow, 0).toString();
				nhaCungCapDAO.ngungHopTac(maNCC);
				loadDataToTable();
				clearFields();
				lockFields();
				generateNextMaNCCAgain();
			}
		}
		// Sự kiện nút hợp tác lại
		private void btnHopTacActionPerformed() {
			int selectedRow = tableNhaCungCap.getSelectedRow();
			if (selectedRow < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần hợp tác lại!");
				return;
			}

			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn hợp tác lại?",
					"Xác nhận làm hợp tác lại", JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {
				String maNCC = tableModel.getValueAt(selectedRow, 0).toString();
				nhaCungCapDAO.quayLaiHopTac(maNCC);
				loadDataToTable();
				clearFields();
				lockFields();
				generateNextMaNCCAgain();
			}
		}
		// Sự kiện nút Tìm kiếm
		private void btnTimActionPerformed() {
		    String maNCC = txtTimMa.getText().trim();
		    String tenNCC = txtTimTen.getText().trim();
		    String diaChi = txtTimDiaChi.getText().trim();
		    String email = txtTimEmail.getText().trim();
	
		    trangThai = radioHopTac.isSelected() ? false : true;
		    
			if (radioHopTac.isSelected()) {
				btnHopTacLai.setVisible(true);
				btnNgungHopTac.setVisible(false);
			} else {
				btnHopTacLai.setVisible(false);
				btnNgungHopTac.setVisible(true);
			}
		    
		    List<Object[]> data = nhaCungCapDAO.searchNhanVien(maNCC, tenNCC, diaChi, email, trangThai);
		    tableModel.setRowCount(0);
		    for (Object[] row : data) {
		        tableModel.addRow(row);
		    }
		}
		// Sự kiện phím tắt enter cho tìm kiếm
		@SuppressWarnings({ "unused", "serial" })
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
			btnNgungHopTac.setVisible(true); // Hiện nút nghỉ
			btnHopTacLai.setVisible(false); // Ẩn nút làm việc
			radioHopTac.setSelected(false); // Bỏ chọn radio button nghỉ việc
			clearSearchFields();
			loadDataToTable();
			btnHuyActionPerformed(); // Xóa trắng form
		}
		// Xóa trắng form tim kiếm
		private void clearSearchFields() {
			txtTimMa.setText("");
			txtTimTen.setText("");
			txtTimDiaChi.setText("");
			txtTimEmail.setText("");
			radioHopTac.setSelected(false);
		}
		// Sự kiện nút Hủy
		private void btnHuyActionPerformed() {
			clearFields();
			generateNextMaNCCAgain();
			btnThem.setEnabled(false);
		}
		// Sự kiện nút Xuất file
		private void btnXuatFileAction() {
		    Workbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("DanhSachNhaCungCap");

		    // Create header row
		    Row headerRow = sheet.createRow(0);
		    String[] headers = {"Mã nhà cung cấp", "Họ tên", "Địa chỉ", "Số điện thoại", "Email"};
		    for (int i = 0; i < headers.length; i++) {
		        Cell cell = headerRow.createCell(i);
		        cell.setCellValue(headers[i]);
		    }

		    // Populate data rows
		    List<Object[]> List = nhaCungCapDAO.getAllNhaCungCap();
		    int rowNum = 1;
		    for (Object[] nv : List) {
		        Row row = sheet.createRow(rowNum++);
		        row.createCell(0).setCellValue((String) nv[0]);
		        row.createCell(1).setCellValue((String) nv[1]);
		        row.createCell(2).setCellValue((String) nv[2]);
		        row.createCell(3).setCellValue((String) nv[3]);
		        row.createCell(4).setCellValue((String) nv[4]);
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

		
	/**
	 * Create the frame.
	 */
	public Frame_NCC_DanhSachNCC() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 797));
		
		JPanel pnlBackGround = new JPanel();
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBounds(0, 0, 1540, 797);
		add(pnlBackGround);
		pnlBackGround.setLayout(null);
		
		txtMaNCC = new JTextField();
		txtMaNCC.setText("");
		txtMaNCC.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaNCC.setColumns(10);
		txtMaNCC.setEditable(false);
		txtMaNCC.setBounds(228, 39, 302, 35);
		pnlBackGround.add(txtMaNCC);
		// Auto uppercase MaNCC
		txtMaNCC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtMaNCC.getText();
				txtMaNCC.setText(text.toUpperCase());
			}
		});
		generateNextMaNCCAgain();
		
		txtEmail = new JTextField();
		txtEmail.setText("");
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtEmail.setColumns(10);
		txtEmail.setBounds(721, 106, 234, 35);
		pnlBackGround.add(txtEmail);
		
		txtTenNCC = new JTextField();
		txtTenNCC.setText("");
		txtTenNCC.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenNCC.setColumns(10);
		txtTenNCC.setBounds(228, 106, 302, 35);
		pnlBackGround.add(txtTenNCC);
		
		txtDiaChi = new JTextField();
		txtDiaChi.setText("");
		txtDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDiaChi.setColumns(10);
		txtDiaChi.setBounds(721, 39, 234, 35);
		pnlBackGround.add(txtDiaChi);
		
		JLabel lblMaNhaCungCap = new JLabel("Mã nhà cung cấp:");
		lblMaNhaCungCap.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaNhaCungCap.setBounds(21, 35, 208, 40);
		pnlBackGround.add(lblMaNhaCungCap);
		
		JLabel lblTenNhaCungCap = new JLabel("Tên nhà cung cấp:");
		lblTenNhaCungCap.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTenNhaCungCap.setBounds(21, 102, 200, 40);
		pnlBackGround.add(lblTenNhaCungCap);
		
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblDiaChi.setBounds(621, 35, 160, 40);
		pnlBackGround.add(lblDiaChi);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblEmail.setBounds(621, 102, 160, 40);
		pnlBackGround.add(lblEmail);
		
		JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblSoDienThoai.setBounds(21, 168, 160, 40);
		pnlBackGround.add(lblSoDienThoai);
		
		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setText("");
		txtSoDienThoai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSoDienThoai.setColumns(10);
		txtSoDienThoai.setBounds(228, 172, 302, 35);
		pnlBackGround.add(txtSoDienThoai);
		
		JPanel pnlThaoTac = new JPanel();
		pnlThaoTac.setBackground(new Color(255, 128, 64));
		pnlThaoTac.setBounds(21, 238, 1048, 95);
		pnlBackGround.add(pnlThaoTac);
		pnlThaoTac.setLayout(null);
		
		btnSua = new JButton("Sửa");
		btnSua.setIcon(new ImageIcon("icon\\edit.png"));
		btnSua.setForeground(Color.BLACK);
		btnSua.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSua.setBackground(new Color(167, 62, 20));
		btnSua.setBounds(203, 23, 130, 50);
		pnlThaoTac.add(btnSua);
		btnSua.addActionListener(e -> btnSuaActionPerformed());
		
		btnLuu = new JButton("Lưu");
		btnLuu.setEnabled(false);
		btnLuu.setIcon(new ImageIcon("icon\\save.png"));
		btnLuu.addActionListener(e -> {
			try {
				btnLuuActionPerformed();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		btnLuu.setForeground(Color.BLACK);
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setBackground(new Color(167, 62, 20));
		btnLuu.setBounds(355, 23, 130, 50);
		pnlThaoTac.add(btnLuu);
		
		btnHuy = new JButton("Hủy ");
		btnHuy.addActionListener(e -> btnHuyActionPerformed());
		btnHuy.setIcon(new ImageIcon("icon\\cancel.png"));
		btnHuy.setForeground(Color.BLACK);
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHuy.setBackground(new Color(167, 62, 20));
		btnHuy.setBounds(743, 23, 130, 50);
		pnlThaoTac.add(btnHuy);
		
		btnXuat = new JButton("Xuất");
		btnXuat.setIcon(new ImageIcon("icon\\print.png"));
		btnXuat.setForeground(Color.BLACK);
		btnXuat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXuat.setBackground(new Color(167, 62, 20));
		btnXuat.setBounds(896, 23, 130, 50);
		pnlThaoTac.add(btnXuat);
		btnXuat.addActionListener(e -> btnXuatFileAction());
		
		btnThem = new JButton("Thêm");
		btnThem.setEnabled(false);
		btnThem.setIcon(new ImageIcon("icon\\add.png"));
		btnThem.setBounds(32, 23, 130, 50);
		pnlThaoTac.add(btnThem);
		btnThem.addActionListener(e -> {
			try {
				btnThemActionPerformed();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		btnThem.setForeground(Color.BLACK);
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBackground(new Color(167, 62, 20));
		
		btnNgungHopTac = new JButton("Ngừng hợp tác");
		btnNgungHopTac.setIcon(new ImageIcon("icon\\layoff.png"));
		btnNgungHopTac.setForeground(Color.BLACK);
		btnNgungHopTac.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnNgungHopTac.setBackground(new Color(167, 62, 20));
		btnNgungHopTac.setBounds(522, 23, 201, 50);
		pnlThaoTac.add(btnNgungHopTac);
		btnNgungHopTac.addActionListener(e -> btnNgungHopTacActionPerformed());
		
		btnHopTacLai = new JButton("Hợp tác lại");
		btnHopTacLai.setIcon(new ImageIcon("icon\\back_job.png"));
		btnHopTacLai.setForeground(Color.BLACK);
		btnHopTacLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnHopTacLai.setBackground(new Color(167, 62, 20));
		btnHopTacLai.setBounds(536, 23, 130, 50);
		pnlThaoTac.add(btnHopTacLai);
		btnHopTacLai.addActionListener(e -> btnHopTacActionPerformed());
		
		JPanel pnlTacVu = new JPanel();
		pnlTacVu.setBackground(new Color(255, 128, 64));
		pnlTacVu.setForeground(new Color(0, 0, 0));
		pnlTacVu.setBounds(1095, 22, 424, 311);
		pnlBackGround.add(pnlTacVu);
		pnlTacVu.setLayout(null);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tác vụ");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		pnlTacVu.setBorder(titledBorder);
		
		JLabel lblTimMa = new JLabel("Mã NCC:");
		lblTimMa.setBounds(23, 25, 165, 27);
		lblTimMa.setFont(new Font("Segoe UI", Font.BOLD, 20));
		pnlTacVu.add(lblTimMa);
		
		JLabel lblTimTen = new JLabel("Tên NCC:");
		lblTimTen.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTimTen.setBounds(23, 62, 180, 34);
		pnlTacVu.add(lblTimTen);
		
		JLabel lblTimDiaChi = new JLabel("Địa chỉ:");
		lblTimDiaChi.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTimDiaChi.setBounds(23, 106, 180, 34);
		pnlTacVu.add(lblTimDiaChi);
		
		JLabel lblTimEmail = new JLabel("Email:");
		lblTimEmail.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTimEmail.setBounds(23, 162, 180, 34);
		pnlTacVu.add(lblTimEmail);
		
		txtTimDiaChi = new JTextField();
		txtTimDiaChi.setText("");
		txtTimDiaChi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimDiaChi.setColumns(10);
		txtTimDiaChi.setBounds(132, 106, 212, 35);
		pnlTacVu.add(txtTimDiaChi);
		
		txtTimTen = new JTextField();
		txtTimTen.setText("");
		txtTimTen.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimTen.setColumns(10);
		txtTimTen.setBounds(132, 63, 212, 35);
		pnlTacVu.add(txtTimTen);
		
		txtTimMa = new JTextField();
		txtTimMa.setText("");
		txtTimMa.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimMa.setColumns(10);
		txtTimMa.setBounds(132, 22, 212, 35);
		pnlTacVu.add(txtTimMa);
		
		txtTimEmail = new JTextField();
		txtTimEmail.setText("");
		txtTimEmail.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimEmail.setColumns(10);
		txtTimEmail.setBounds(132, 163, 212, 35);
		pnlTacVu.add(txtTimEmail);
		
		btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBackground(new Color(255, 128, 128));
		btnTim.setBounds(41, 251, 141, 50);
		pnlTacVu.add(btnTim);
		btnTim.addActionListener(e -> btnTimActionPerformed());
		
		btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.addActionListener(e -> btnTaiLaiActionPerformed());
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBackground(new Color(255, 128, 128));
		btnTaiLai.setBounds(221, 251, 141, 50);
		pnlTacVu.add(btnTaiLai);
		btnTaiLai.addActionListener(e -> btnTaiLaiActionPerformed());
		
		
		radioHopTac = new JRadioButton("Ngừng hợp tác");
		radioHopTac.setSelected(false);
		radioHopTac.setFont(new Font("Segoe UI", Font.BOLD, 20));
		radioHopTac.setBackground(new Color(255, 128, 64));
		radioHopTac.setBounds(123, 202, 200, 48);
		pnlTacVu.add(radioHopTac);
		
		JScrollPane scrollPaneNhaCungCap = new JScrollPane();
		scrollPaneNhaCungCap.setBounds(21, 358, 1498, 429);
		pnlBackGround.add(scrollPaneNhaCungCap);
		
		tableNhaCungCap = new JTable();
		tableNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableNhaCungCap.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{"", null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"M\u00E3 ", "T\u00EAn", "\u0110\u1ECBa ch\u1EC9", "S\u1ED1 \u0111i\u1EC7n tho\u1EA1i", "Email"
			}
		));
		tableNhaCungCap.getColumnModel().getColumn(2).setPreferredWidth(55);

		JTableHeader header = tableNhaCungCap.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tableNhaCungCap.setRowHeight(30);
	
		scrollPaneNhaCungCap.setViewportView(tableNhaCungCap);
		// Create a custom cell renderer that centers the text
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

				// Apply the renderer to each column of the table
				for (int i = 0; i < tableNhaCungCap.getColumnCount(); i++) {
					tableNhaCungCap.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
				}
		
		// Auto uppercase MaNCC
		txtMaNCC.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtMaNCC.getText();
				txtMaNCC.setText(text.toUpperCase());
			}
		});
		
		tableNhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int selectedRow = tableNhaCungCap.getSelectedRow();
				if (selectedRow >= 0) {
					txtMaNCC.setText(tableModel.getValueAt(selectedRow, 0).toString());
					txtTenNCC.setText(tableModel.getValueAt(selectedRow, 1).toString());
					txtDiaChi.setText(tableModel.getValueAt(selectedRow, 2).toString());
					txtSoDienThoai.setText(tableModel.getValueAt(selectedRow, 3).toString());
					txtEmail.setText(tableModel.getValueAt(selectedRow, 4).toString());
					
					trangThai = nhaCungCapDAO.getTrangThaiNhaCungCap(txtMaNCC.getText());
					btnNgungHopTac.setVisible(trangThai);
					btnThem.setEnabled(false);
					btnLuu.setEnabled(true);
					
				}
			}
		});
		
		loadDataToTable();
	}
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Frame_DoiTac_NhaCungCap frame = new Frame_DoiTac_NhaCungCap();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}
