package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DAO.ThuocDAO;

public class Frame_Thuoc_ThietLapGia extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTable tableThuoc;
	private DefaultTableModel tableModel;
	private ThuocDAO thuocDAO = new ThuocDAO();

	private JButton btnLuu, btnTaiLai, btnTim;
	private JTextField txtMaThuocTim, txtTenThuocTim;
	private JTextField txtDonGiaBan;
	private JTextField txtDonGiaNhap;

	public Frame_Thuoc_ThietLapGia() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1545, 854);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1514, 516);
		pnlBackGround.add(scrollPane);

		tableThuoc = new JTable();
		tableThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableThuoc.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã thuốc", "Tên thuốc", "Đơn giá nhập", "Đơn giá bán" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		JTableHeader header = tableThuoc.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tableThuoc.setRowHeight(30);
		scrollPane.setViewportView(tableThuoc);

		// Căn giữa dữ liệu trong table
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tableThuoc.getColumnCount(); i++) {
			tableThuoc.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JPanel panelTimKiem = new JPanel();
		panelTimKiem.setBackground(new Color(242, 132, 123));
		panelTimKiem.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelTimKiem.setBounds(985, 536, 538, 205);
		pnlBackGround.add(panelTimKiem);
		panelTimKiem.setLayout(null);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tác Vụ Tìm Kiếm");
		titledBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelTimKiem.setBorder(titledBorder);

		JLabel lblMaThuocTim = new JLabel("Mã Thuốc:");
		lblMaThuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaThuocTim.setBounds(25, 16, 206, 50);
		panelTimKiem.add(lblMaThuocTim);

		txtMaThuocTim = new JTextField();
		txtMaThuocTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaThuocTim.setBounds(227, 21, 286, 40);
		panelTimKiem.add(txtMaThuocTim);
		txtMaThuocTim.setColumns(10);

		// Auto uppercase
		txtMaThuocTim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtMaThuocTim.getText();
				txtMaThuocTim.setText(text.toUpperCase());
			}
		});

		JLabel lblTenThuocTim = new JLabel("Tên Thuốc:");
		lblTenThuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTenThuocTim.setBounds(25, 70, 206, 50);
		panelTimKiem.add(lblTenThuocTim);

		btnTaiLai = new JButton("Tải Lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBackground(new Color(167, 62, 20));
		btnTaiLai.setBounds(326, 130, 167, 50);
		panelTimKiem.add(btnTaiLai);

		txtTenThuocTim = new JTextField();
		txtTenThuocTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenThuocTim.setColumns(10);
		txtTenThuocTim.setBounds(227, 76, 286, 40);
		panelTimKiem.add(txtTenThuocTim);

		addEnterKeyListener(txtMaThuocTim);
		addEnterKeyListener(txtTenThuocTim);

		btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBackground(new Color(167, 62, 20));
		btnTim.setBounds(72, 130, 167, 50);
		panelTimKiem.add(btnTim);

		JPanel panelXuLy = new JPanel();
		panelXuLy.setLayout(null);
		panelXuLy.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelXuLy.setBackground(new Color(242, 132, 123));
		panelXuLy.setBounds(10, 536, 424, 205);
		TitledBorder xuLyBorder = BorderFactory.createTitledBorder("Tác Vụ Xử Lý");
		xuLyBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelXuLy.setBorder(xuLyBorder);
		pnlBackGround.add(panelXuLy);

		btnLuu = new JButton("Lưu");
		btnLuu.setIcon(new ImageIcon("icon\\save.png"));
		btnLuu.setForeground(Color.BLACK);
		btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnLuu.setBackground(new Color(167, 62, 20));
		btnLuu.setBounds(129, 137, 167, 50);
		panelXuLy.add(btnLuu);

		txtDonGiaBan = new JTextField();
		txtDonGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDonGiaBan.setBounds(195, 85, 202, 42);
		panelXuLy.add(txtDonGiaBan);
		txtDonGiaBan.setColumns(10);

		JLabel lblDonGiaBan = new JLabel("Đơn Giá Bán");
		lblDonGiaBan.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblDonGiaBan.setBounds(26, 83, 140, 34);
		panelXuLy.add(lblDonGiaBan);

		JLabel lblDonGiaNhap = new JLabel("Đơn Giá Nhập");
		lblDonGiaNhap.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblDonGiaNhap.setBounds(26, 28, 140, 34);
		panelXuLy.add(lblDonGiaNhap);

		txtDonGiaNhap = new JTextField();
		txtDonGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtDonGiaNhap.setColumns(10);
		txtDonGiaNhap.setBounds(195, 28, 202, 42);
		panelXuLy.add(txtDonGiaNhap);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("image\\logoMTP.png"));
		lblLogo.setBounds(503, 536, 407, 211);
		pnlBackGround.add(lblLogo);

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

		tableThuoc.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tableThuoc.getSelectedRow();
				if (row != -1) {
					txtDonGiaNhap.setText(tableThuoc.getValueAt(row, 2).toString());
					txtDonGiaBan.setText(tableThuoc.getValueAt(row, 3).toString());
				}
			}
		});

		btnLuu.addActionListener(e -> btnLuuActionPerformed());
		btnTaiLai.addActionListener(e -> btnTaiLaiActionPerformed());
		btnTim.addActionListener(e -> btnTimActionPerformed());
		loadDataToTable();
	}

	private void loadDataToTable() {
		tableModel = (DefaultTableModel) tableThuoc.getModel();
		tableModel.setRowCount(0);
		List<Object[]> list = thuocDAO.getDanhSachGia();
		for (Object[] objects : list) {
			tableModel.addRow(objects);
		}
	}

	private void btnLuuActionPerformed() {
		if (tableThuoc.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc để cập nhật giá!", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String maThuoc = tableThuoc.getValueAt(tableThuoc.getSelectedRow(), 0).toString();
		String giaNhap = txtDonGiaNhap.getText().replaceAll("[^0-9]", "");
		String giaBan = txtDonGiaBan.getText().replaceAll("[^0-9]", "");
		thuocDAO.updatePrice(maThuoc, giaNhap, giaBan);
		btnTaiLaiActionPerformed();
	}

	private void btnTimActionPerformed() {
		String maThuoc = txtMaThuocTim.getText();
		String tenThuoc = txtTenThuocTim.getText();

		tableModel.setRowCount(0);
		List<Object[]> list = thuocDAO.timKiemThuoc(maThuoc, tenThuoc);
		for (Object[] objects : list) {
			tableModel.addRow(objects);
		}
	}

	private void btnTaiLaiActionPerformed() {
		txtDonGiaNhap.setText("");
		txtDonGiaBan.setText("");
		txtMaThuocTim.setText("");
		txtTenThuocTim.setText("");
		loadDataToTable();
		tableThuoc.clearSelection();
	}

	// Sự kiện phím tắt enter cho tìm kiếm
	@SuppressWarnings("serial")
	private void addEnterKeyListener(JComponent component) {
		component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"searchAction");
		component.getActionMap().put("searchAction", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnTimActionPerformed(); // Thực hiện tìm kiếm ngay khi nhấn Enter
			}
		});
	}
}