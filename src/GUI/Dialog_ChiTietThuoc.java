package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
import DAO.ThuocDAO;

@SuppressWarnings("serial")
public class Dialog_ChiTietThuoc extends JDialog {
	private JTextField txtMaThuoc;
	private JTextField txtTenThuoc;
	private JTextField txtDonViTinh;
	private JTextField txtHamLuong;
	private JDateChooser txtHSD;
	private JLabel lblHSD;
	private JLabel lblHamLuong;
	private JLabel lblSLTon;
	private JLabel lblSLTT;
	private JButton btnSave;
	private JButton btnCancel;
	private JTextField txtDonGiaNhap;
	private JTextField txtDonGiaBan;
	private JTextField txtSLTon;
	private JTextField txtSLTT;
	private boolean isUpdate = false;
	private ThuocDAO thuocDAO = new ThuocDAO();

	public Dialog_ChiTietThuoc(Frame owner, boolean isUpdate) {
		super(owner, !isUpdate ? "Thêm Sản Phẩm Mới" : "Chỉnh Sửa Sản Phẩm", true);
		setFont(new Font("Segoe UI", Font.BOLD, 13));
		setIconImage(Toolkit.getDefaultToolkit().getImage("icon\\medicine.png"));
		setSize(633, 682);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		formPanel.setLayout(null);

		// Add form fields
		JLabel lblMaThuoc = new JLabel("Mã thuốc:");
		lblMaThuoc.setBounds(21, 25, 220, 40);
		lblMaThuoc.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblMaThuoc);
		txtMaThuoc = new JTextField();
		txtMaThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaThuoc.setBounds(264, 29, 326, 40);
		formPanel.add(txtMaThuoc);

		JLabel lblTenThuoc = new JLabel("Tên thuốc:");
		lblTenThuoc.setBounds(21, 88, 220, 40);
		lblTenThuoc.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblTenThuoc);
		txtTenThuoc = new JTextField();
		txtTenThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenThuoc.setBounds(264, 92, 326, 40);
		formPanel.add(txtTenThuoc);

		JLabel lblDonViTinh = new JLabel("Đơn vị tính");
		lblDonViTinh.setBounds(21, 148, 220, 40);
		lblDonViTinh.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblDonViTinh);
		txtDonViTinh = new JTextField();
		txtDonViTinh.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDonViTinh.setBounds(264, 152, 326, 40);
		formPanel.add(txtDonViTinh);

		JLabel lblDonGiaNhap = new JLabel("Đơn giá nhập:");
		lblDonGiaNhap.setBounds(21, 333, 220, 40);
		lblDonGiaNhap.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblDonGiaNhap);
		txtDonGiaNhap = new JTextField();
		txtDonGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDonGiaNhap.setBounds(264, 333, 326, 40);
		formPanel.add(txtDonGiaNhap);

		JLabel lblDonGiaBan = new JLabel("Đơn giá bán:");
		lblDonGiaBan.setBounds(21, 393, 220, 40);
		lblDonGiaBan.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblDonGiaBan);
		txtDonGiaBan = new JTextField();
		txtDonGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDonGiaBan.setBounds(264, 393, 326, 40);
		formPanel.add(txtDonGiaBan);

		lblHSD = new JLabel("Hạn Sử Dụng");
		lblHSD.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblHSD.setBounds(21, 209, 220, 40);
		formPanel.add(lblHSD);
		txtHSD = new JDateChooser();
		txtHSD.setDateFormatString("dd/MM/yyyy");
		txtHSD.setBounds(264, 209, 326, 40);
		txtHSD.getDateEditor().getUiComponent().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		formPanel.add(txtHSD);

		lblHamLuong = new JLabel("Hàm Lượng");
		lblHamLuong.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblHamLuong.setBounds(21, 271, 220, 40);
		formPanel.add(lblHamLuong);
		txtHamLuong = new JTextField();
		txtHamLuong.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtHamLuong.setBounds(264, 271, 326, 40);
		formPanel.add(txtHamLuong);

		lblSLTon = new JLabel("Số Lượng Tồn:");
		lblSLTon.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblSLTon.setBounds(21, 454, 220, 40);
		formPanel.add(lblSLTon);

		lblSLTT = new JLabel("Số Lượng Thực Tế:");
		lblSLTT.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblSLTT.setBounds(21, 517, 220, 40);
		formPanel.add(lblSLTT);

		btnSave = new JButton("Lưu");
		btnSave.setIcon(new ImageIcon("icon\\save.png"));
		btnSave.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSave.setBounds(291, 584, 142, 51);
		formPanel.add(btnSave);

		btnCancel = new JButton("Hủy");
		btnCancel.setIcon(new ImageIcon("icon\\cancel.png"));
		btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCancel.setBounds(467, 584, 142, 51);
		formPanel.add(btnCancel);

		txtSLTon = new JTextField();
		txtSLTon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSLTon.setBounds(264, 458, 326, 40);
		formPanel.add(txtSLTon);

		txtSLTT = new JTextField();
		txtSLTT.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSLTT.setBounds(264, 517, 326, 40);
		formPanel.add(txtSLTT);

		getContentPane().add(formPanel, BorderLayout.CENTER);

		btnSave.addActionListener(e -> {
			try {
				btnSaveActionPerformed();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnCancel.addActionListener(e -> dispose());

		// Sự kiện format tiền tệ

		txtDonGiaNhap.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// Lấy vị trí con trỏ hiện tại trước khi thay đổi văn bản
				int caretPosition = txtDonGiaNhap.getCaretPosition();
				String text = txtDonGiaNhap.getText().replaceAll("[^0-9]", ""); // Loại bỏ mọi ký tự không phải số

				if (!text.isEmpty()) {
					try {
						// Chuyển chuỗi số thành số nguyên
						long number = Long.parseLong(text);
						// Định dạng số với dấu phẩy
						String formattedNumber = String.format("%,d", number);
						// Thêm chữ "đ" vào cuối
						String newText = formattedNumber + "đ";

						// Tắt tạm thời CaretListener để tránh xung đột
						for (CaretListener cl : txtDonGiaNhap.getCaretListeners()) {
							txtDonGiaNhap.removeCaretListener(cl);
						}

						// Cập nhật văn bản
						txtDonGiaNhap.setText(newText);

						// Điều chỉnh vị trí con trỏ: nếu thêm số mới, đặt con trỏ ở cuối phần số
						int newCaretPosition;
						if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') { // Nếu là nhập số
							newCaretPosition = formattedNumber.length(); // Đặt con trỏ ở cuối số
						} else { // Nếu là thao tác khác (xóa, di chuyển, v.v.)
							newCaretPosition = Math.min(caretPosition, formattedNumber.length());
						}
						txtDonGiaNhap.setCaretPosition(newCaretPosition);

						// Kích hoạt lại CaretListener
						txtDonGiaNhap.addCaretListener(new CaretListener() {
							@Override
							public void caretUpdate(CaretEvent e) {
								String currentText = txtDonGiaNhap.getText();
								if (currentText.endsWith("đ")) {
									int numberLength = currentText.length() - 1;
									int currentCaret = txtDonGiaNhap.getCaretPosition();
									if (currentCaret > numberLength) {
										txtDonGiaNhap.setCaretPosition(numberLength);
									}
								}
							}
						});
					} catch (NumberFormatException ex) {
						txtDonGiaNhap.setText(""); // Xóa nếu không hợp lệ
					}
				} else {
					txtDonGiaNhap.setText(""); // Nếu không còn số, xóa toàn bộ
				}
			}
		});

		// Thêm CaretListener để theo dõi và điều chỉnh vị trí con trỏ
		txtDonGiaNhap.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				String text = txtDonGiaNhap.getText();
				if (text.endsWith("đ")) {
					int numberLength = text.length() - 1;
					int caretPosition = txtDonGiaNhap.getCaretPosition();
					if (caretPosition > numberLength) {
						txtDonGiaNhap.setCaretPosition(numberLength);
					}
				}
			}
		});

		txtDonGiaBan.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// Lấy vị trí con trỏ hiện tại trước khi thay đổi văn bản
				int caretPosition = txtDonGiaBan.getCaretPosition();
				String text = txtDonGiaBan.getText().replaceAll("[^0-9]", ""); // Loại bỏ mọi ký tự không phải số

				if (!text.isEmpty()) {
					try {
						// Chuyển chuỗi số thành số nguyên
						long number = Long.parseLong(text);
						// Định dạng số với dấu phẩy
						String formattedNumber = String.format("%,d", number);
						// Thêm chữ "đ" vào cuối
						String newText = formattedNumber + "đ";

						// Tắt tạm thời CaretListener để tránh xung đột
						for (CaretListener cl : txtDonGiaBan.getCaretListeners()) {
							txtDonGiaBan.removeCaretListener(cl);
						}

						// Cập nhật văn bản
						txtDonGiaBan.setText(newText);

						// Điều chỉnh vị trí con trỏ: nếu thêm số mới, đặt con trỏ ở cuối phần số
						int newCaretPosition;
						if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') { // Nếu là nhập số
							newCaretPosition = formattedNumber.length(); // Đặt con trỏ ở cuối số
						} else { // Nếu là thao tác khác (xóa, di chuyển, v.v.)
							newCaretPosition = Math.min(caretPosition, formattedNumber.length());
						}
						txtDonGiaBan.setCaretPosition(newCaretPosition);

						// Kích hoạt lại CaretListener
						txtDonGiaBan.addCaretListener(new CaretListener() {
							@Override
							public void caretUpdate(CaretEvent e) {
								String currentText = txtDonGiaBan.getText();
								if (currentText.endsWith("đ")) {
									int numberLength = currentText.length() - 1;
									int currentCaret = txtDonGiaBan.getCaretPosition();
									if (currentCaret > numberLength) {
										txtDonGiaBan.setCaretPosition(numberLength);
									}
								}
							}
						});
					} catch (NumberFormatException ex) {
						txtDonGiaBan.setText(""); // Xóa nếu không hợp lệ
					}
				} else {
					txtDonGiaBan.setText(""); // Nếu không còn số, xóa toàn bộ
				}
			}
		});

		// Thêm CaretListener để theo dõi và điều chỉnh vị trí con trỏ
		txtDonGiaBan.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				String text = txtDonGiaBan.getText();
				if (text.endsWith("đ")) {
					int numberLength = text.length() - 1;
					int caretPosition = txtDonGiaBan.getCaretPosition();
					if (caretPosition > numberLength) {
						txtDonGiaBan.setCaretPosition(numberLength);
					}
				}
			}
		});
	}

	public void hienThiThuoc(String maThuoc, String tenThuoc, String donViTinh, String donGiaNhap, String donGiaBan,
			String hsd, String hamLuong, String soLuongTon, String soLuongThucTe) {
		isUpdate = true;
		txtMaThuoc.setText(maThuoc);
		txtMaThuoc.setEditable(false);
		txtTenThuoc.setText(tenThuoc);
		txtDonViTinh.setText(donViTinh);

		// Xử lý và định dạng đơn giá nhập
		String cleanedDonGiaNhap = donGiaNhap.replaceAll("[^0-9]", ""); // Loại bỏ mọi ký tự không phải số
		if (!cleanedDonGiaNhap.isEmpty()) {
			try {
				long numberNhap = Long.parseLong(cleanedDonGiaNhap);
				String formattedDonGiaNhap = String.format("%,d", numberNhap) + "đ";
				txtDonGiaNhap.setText(formattedDonGiaNhap);
			} catch (NumberFormatException e) {
				txtDonGiaNhap.setText(""); // Xóa nếu không hợp lệ
			}
		} else {
			txtDonGiaNhap.setText(""); // Nếu chuỗi rỗng thì để trống
		}

		// Xử lý và định dạng đơn giá bán
		String cleanedDonGiaBan = donGiaBan.replaceAll("[^0-9]", ""); // Loại bỏ mọi ký tự không phải số
		if (!cleanedDonGiaBan.isEmpty()) {
			try {
				long numberBan = Long.parseLong(cleanedDonGiaBan);
				String formattedDonGiaBan = String.format("%,d", numberBan) + "đ";
				txtDonGiaBan.setText(formattedDonGiaBan);
			} catch (NumberFormatException e) {
				txtDonGiaBan.setText(""); // Xóa nếu không hợp lệ
			}
		} else {
			txtDonGiaBan.setText(""); // Nếu chuỗi rỗng thì để trống
		}

		// Xử lý và định dạng hạn sử dụng
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(hsd);
			txtHSD.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txtHamLuong.setText(hamLuong);
		txtSLTon.setText(soLuongTon);
		txtSLTT.setText(soLuongThucTe);
	}

	public void hienThiMaThuoc(String maThuoc) {
		isUpdate = false;
		txtMaThuoc.setText(maThuoc);
		txtMaThuoc.setEditable(false);
	}

	public class kiemTraNhap {
		public static String validateMaThuoc(String maThuoc) {
			if (maThuoc == null || maThuoc.isEmpty()) {
				return "Mã thuốc không được để trống!";
			}
			String idRegex = "^T\\d+$";
			if (!Pattern.matches(idRegex, maThuoc)) {
				return "Mã thuốc không hợp lệ! Mã thuốc phải bắt đầu bằng 'T' và theo sau là các chữ số.";
			}
			return null;
		}

		public static String validateTenThuoc(String tenThuoc) {
			if (tenThuoc == null || tenThuoc.isEmpty()) {
				return "Tên thuốc không được để trống!";
			}
			return null;
		}

		public static String validateDonViTinh(String donViTinh) {
			if (donViTinh == null || donViTinh.isEmpty()) {
				return "Đơn vị tính không được để trống!";
			}
			return null;
		}

		public static String validateDonGiaNhap(String donGiaNhap) {
			if (donGiaNhap == null || donGiaNhap.isEmpty()) {
				return "Đơn giá nhập không được để trống!";
			}
			String idRegex = "^\\d+$";
			if (!Pattern.matches(idRegex, donGiaNhap)) {
				return "Đơn giá nhập không hợp lệ! Đơn giá nhập phải là số nguyên.";
			}
			return null;
		}

		public static String validateDonGiaBan(String donGiaBan) {
			if (donGiaBan == null || donGiaBan.isEmpty()) {
				return "Đơn giá bán không được để trống!";
			}
			String idRegex = "^\\d+$";
			if (!Pattern.matches(idRegex, donGiaBan)) {
				return "Đơn giá bán không hợp lệ! Đơn giá bán phải là số nguyên.";
			}
			return null;
		}

		public static String validateHSD(String hsd) {
			if (hsd == null || hsd.isEmpty()) {
				return "Hạn sử dụng không được để trống!";
			}
			return null;
		}

		public static String validateHamLuong(String hamLuong) {
			if (hamLuong == null || hamLuong.isEmpty()) {
				return "Hàm lượng không được để trống!";
			}
			return null;
		}

		public static String validateSLTon(String soLuongTon) {
			if (soLuongTon == null || soLuongTon.isEmpty()) {
				return "Số lượng tồn không được để trống!";
			}
			String idRegex = "^\\d+$";
			if (!Pattern.matches(idRegex, soLuongTon)) {
				return "Số lượng tồn không hợp lệ! Số lượng tồn phải là số nguyên.";
			}
			return null;
		}

		public static String validateSLTT(String soLuongThucTe) {
			if (soLuongThucTe == null || soLuongThucTe.isEmpty()) {
				return "Số lượng thực tế không được để trống!";
			}
			String idRegex = "^\\d+$";
			if (!Pattern.matches(idRegex, soLuongThucTe)) {
				return "Số lượng thực tế không hợp lệ! Số lượng thực tế phải là số nguyên.";
			}
			return null;
		}
	}

	private boolean isDuplicateMaThuoc(String maThuoc) {
		return thuocDAO.isDuplicateMaThuoc(maThuoc);
	}

	private void btnSaveActionPerformed() throws ParseException {
		String maThuoc = txtMaThuoc.getText().trim();
		String tenThuoc = txtTenThuoc.getText().trim();
		String donViTinh = txtDonViTinh.getText().trim();
		String donGiaNhap = txtDonGiaNhap.getText().trim().replaceAll("[^0-9]", "");
		String donGiaBan = txtDonGiaBan.getText().trim().replaceAll("[^0-9]", "");
		String hamLuong = txtHamLuong.getText().trim();
		String soLuongTon = txtSLTon.getText().trim();
		String soLuongThucTe = txtSLTT.getText().trim();

		String error;

		error = kiemTraNhap.validateMaThuoc(maThuoc);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtMaThuoc.requestFocus();
			return;
		}

		error = kiemTraNhap.validateTenThuoc(tenThuoc);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtTenThuoc.requestFocus();
			return;
		}

		error = kiemTraNhap.validateDonViTinh(donViTinh);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtDonViTinh.requestFocus();
			return;
		}

		error = kiemTraNhap.validateDonGiaNhap(donGiaNhap);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtDonGiaNhap.requestFocus();
			return;
		}

		error = kiemTraNhap.validateDonGiaBan(donGiaBan);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtDonGiaBan.requestFocus();
			return;
		}

		error = kiemTraNhap.validateHamLuong(hamLuong);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtHamLuong.requestFocus();
			return;
		}

		error = kiemTraNhap.validateSLTon(soLuongTon);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtSLTon.requestFocus();
			return;
		}

		error = kiemTraNhap.validateSLTT(soLuongThucTe);
		if (error != null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this, "Error: " + error);
			txtSLTT.requestFocus();
			return;
		}

		if (!isUpdate && isDuplicateMaThuoc(maThuoc)) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this,
					"Error: Mã thuốc đã tồn tại! Vui lòng kiểm tra lại thông tin.");
			txtMaThuoc.requestFocus();
			return;
		}

		Date hsd = txtHSD.getDate();
		if (hsd == null) {
			JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this,
					"Error: Ngày không hợp lệ! Ngày phải theo định dạng dd/MM/yyyy.");
			txtHSD.requestFocus();
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String hsdString = sdf.format(hsd);

		if (isUpdate) {
			thuocDAO.capNhatThuoc(maThuoc, tenThuoc, donViTinh, donGiaNhap, donGiaBan, hsdString, hamLuong, soLuongTon,
					soLuongThucTe);
		} else {
			thuocDAO.themThuoc(maThuoc, tenThuoc, donViTinh, donGiaNhap, donGiaBan, hsdString, hamLuong, soLuongTon,
					soLuongThucTe);
		}

		JOptionPane.showMessageDialog(Dialog_ChiTietThuoc.this,
				isUpdate ? "Cập nhật thông tin thuốc thành công!" : "Thêm thuốc mới thành công!");
		dispose();
	}
}
