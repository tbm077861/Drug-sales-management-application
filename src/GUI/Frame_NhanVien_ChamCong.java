package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import DAO.NhanVienDAO;

public class Frame_NhanVien_ChamCong extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTable tableNhanVien;
	private JTable tableChamCong;
	private DefaultTableModel modelNhanVien, modelChamCong;
	private JTextField txtTimMaNV;
	private JButton btnTimNV, btnTaiLaiNV, btnCheckIn, btnCheckOut, btnTaiLaiChamCong, btnTimChamCong;
	private JDateChooser JDateNgayLapTim;
	private JComboBox<String> cbxCaLam;
	private NhanVienDAO nhanVienDAO = new NhanVienDAO();

	public Frame_NhanVien_ChamCong() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		// Background panel
		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1543, 751);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		// Bảng danh sách nhân viên (bên trái)
		JPanel panelNhanVien = new JPanel();
		TitledBorder titledBorderNhanVien = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2),
				"Danh Sách Nhân Viên");
		titledBorderNhanVien.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelNhanVien.setBorder(titledBorderNhanVien);
		panelNhanVien.setBackground(new Color(220, 128, 78));
		panelNhanVien.setBounds(10, 10, 1015, 281);
		pnlBackGround.add(panelNhanVien);
		panelNhanVien.setLayout(null);

		JScrollPane scrollPaneNhanVien = new JScrollPane();
		scrollPaneNhanVien.setBounds(22, 20, 972, 250);
		panelNhanVien.add(scrollPaneNhanVien);

		tableNhanVien = new JTable();
		tableNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		JTableHeader headerNhanVien = tableNhanVien.getTableHeader();
		headerNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		modelNhanVien = new DefaultTableModel(new Object[][] {},
				new String[] { "Mã NV", "Họ Tên", "Chức Vụ", "Ca Làm" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tableNhanVien.setModel(modelNhanVien);
		tableNhanVien.setRowHeight(30);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableNhanVien.getColumnCount(); i++) {
			tableNhanVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		scrollPaneNhanVien.setViewportView(tableNhanVien);

		// Bảng lịch sử chấm công (bên trái, dưới bảng nhân viên)
		JPanel panelChamCong = new JPanel();
		TitledBorder titledBorderChamCong = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2),
				"Danh Sách Chấm Công");
		titledBorderChamCong.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelChamCong.setBorder(titledBorderChamCong);
		panelChamCong.setBackground(new Color(220, 128, 78));
		panelChamCong.setBounds(10, 368, 1515, 373);
		pnlBackGround.add(panelChamCong);
		panelChamCong.setLayout(null);

		JScrollPane scrollPaneChamCong = new JScrollPane();
		scrollPaneChamCong.setBounds(20, 25, 1472, 338);
		panelChamCong.add(scrollPaneChamCong);

		tableChamCong = new JTable();
		tableChamCong.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		JTableHeader headerChamCong = tableChamCong.getTableHeader();
		headerChamCong.setFont(new Font("Segoe UI", Font.BOLD, 16));
		modelChamCong = new DefaultTableModel(new Object[][] {}, new String[] { "Mã Chấm Công", "Mã NV",
				"Ngày Chấm Công", "Mã Ca", "Giờ Vào", "Giờ Ra", "Trạng Thái", "Ghi Chú" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		tableChamCong.setModel(modelChamCong);
		tableChamCong.setRowHeight(30);

		for (int i = 0; i < tableChamCong.getColumnCount(); i++) {
			tableChamCong.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		scrollPaneChamCong.setViewportView(tableChamCong);

		// Panel tìm kiếm nhân viên (bên phải, trên)
		JPanel panelTimKiem = new JPanel();
		panelTimKiem.setBounds(1065, 39, 460, 153);
		panelTimKiem.setBackground(new Color(220, 128, 78));
		panelTimKiem.setLayout(null);
		TitledBorder titledBorderTimKiem = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2),
				"Tìm Kiếm Nhân Viên");
		titledBorderTimKiem.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelTimKiem.setBorder(titledBorderTimKiem);
		pnlBackGround.add(panelTimKiem);

		JLabel lblMaNV = new JLabel("Mã NV:");
		lblMaNV.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaNV.setBounds(24, 32, 146, 42);
		panelTimKiem.add(lblMaNV);

		txtTimMaNV = new JTextField();
		txtTimMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		txtTimMaNV.setBounds(159, 31, 264, 43);
		panelTimKiem.add(txtTimMaNV);
		txtTimMaNV.setColumns(10);

		btnTimNV = new JButton("Tìm");
		btnTimNV.setIcon(new ImageIcon("icon\\find.png"));
		btnTimNV.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnTimNV.setBounds(56, 95, 139, 43);
		panelTimKiem.add(btnTimNV);

		btnTaiLaiNV = new JButton("Tải Lại");
		btnTaiLaiNV.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLaiNV.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnTaiLaiNV.setBounds(253, 95, 139, 43);
		panelTimKiem.add(btnTaiLaiNV);

		// Panel xử lý chấm công (bên phải, dưới)
		JPanel panelXuLy = new JPanel();
		panelXuLy.setBounds(1065, 237, 460, 94);
		panelXuLy.setBackground(new Color(220, 128, 78));
		panelXuLy.setLayout(null);
		TitledBorder titledBorderXuLy = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2), "Chấm Công");
		titledBorderXuLy.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelXuLy.setBorder(titledBorderXuLy);
		pnlBackGround.add(panelXuLy);

		btnCheckIn = new JButton("Chấm Công Vào");
		btnCheckIn.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnCheckIn.setBackground(new Color(0, 128, 255));
		btnCheckIn.setForeground(Color.WHITE);
		btnCheckIn.setOpaque(true);
		btnCheckIn.setBorderPainted(false);
		btnCheckIn.setBounds(24, 22, 192, 50);
		panelXuLy.add(btnCheckIn);

		btnCheckOut = new JButton("Chấm Công Ra");
		btnCheckOut.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnCheckOut.setBackground(new Color(255, 0, 0));
		btnCheckOut.setForeground(Color.WHITE);
		btnCheckOut.setOpaque(true);
		btnCheckOut.setBorderPainted(false);
		btnCheckOut.setBounds(246, 22, 192, 50);
		panelXuLy.add(btnCheckOut);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(220, 128, 78));
		panel.setBounds(54, 296, 928, 62);
		pnlBackGround.add(panel);
		panel.setLayout(null);

		JLabel lblCaLam = new JLabel("Ca Làm:");
		lblCaLam.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCaLam.setBounds(46, 10, 93, 42);
		panel.add(lblCaLam);

		cbxCaLam = new JComboBox<>(
				new String[] { "Tất cả", "Ca 1 - Sáng", "Ca 2 - Chiều", "Ca 3 - Tối", "Ca 4 - Cả Ngày" });
		cbxCaLam.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbxCaLam.setSelectedIndex(0);
		cbxCaLam.setBounds(149, 12, 184, 40);
		panel.add(cbxCaLam);

		JLabel lblNgayLam = new JLabel("Ngày Làm:");
		lblNgayLam.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNgayLam.setBounds(375, 10, 120, 42);
		panel.add(lblNgayLam);

		JDateNgayLapTim = new JDateChooser();
		JDateNgayLapTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		JDateNgayLapTim.setDateFormatString("dd/MM/yyyy");
		JDateNgayLapTim.setDate(new Date());
		JDateNgayLapTim.setBounds(507, 12, 225, 40);
		panel.add(JDateNgayLapTim);

		btnTaiLaiChamCong = new JButton("");
		btnTaiLaiChamCong.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLaiChamCong.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLaiChamCong.setBackground(new Color(255, 255, 255));
		btnTaiLaiChamCong.setBounds(850, 12, 54, 40);
		panel.add(btnTaiLaiChamCong);

		btnTimChamCong = new JButton("");
		btnTimChamCong.setIcon(new ImageIcon("icon\\find.png"));
		btnTimChamCong.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnTimChamCong.setBounds(770, 12, 54, 40);
		panel.add(btnTimChamCong);
		btnCheckIn.setEnabled(true);
		btnCheckOut.setEnabled(true);

		// Sự kiện
		btnTimNV.addActionListener(e -> timNhanVien());
		btnTaiLaiNV.addActionListener(e -> taiLaiNhanVien());

		btnTaiLaiChamCong.addActionListener(e -> taiLaiChamCong());
		btnTimChamCong.addActionListener(e -> timChamCong());

		btnCheckIn.addActionListener(e -> {
			try {
				chamCongVao();
			} catch (HeadlessException | ParseException e1) {
				e1.printStackTrace();
			}
		});
		btnCheckOut.addActionListener(e -> chamCongRa());
		
		tableNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tableNhanVien.getSelectedRow();
				if (row != -1) {
					btnCheckIn.setEnabled(true);
					btnCheckOut.setEnabled(false);
					tableChamCong.clearSelection();
				}
			}
		});
		
		tableChamCong.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tableChamCong.getSelectedRow();
				if (row != -1) {
					btnCheckIn.setEnabled(false);
					btnCheckOut.setEnabled(true);
					tableNhanVien.clearSelection();
				}
			}
		});

		// phím tắt enter cho tìm kiếm
		addEnterKeyListener(txtTimMaNV);

		// Load dữ liệu ban đầu
		loadDanhSachNhanVien();
		loadDanhSachChamCong();
	}

	// Load danh sách nhân viên của ngày hiện tại
	private void loadDanhSachNhanVien() {
		List<Object[]> data = nhanVienDAO.loadDataToNhanVien();
		modelNhanVien = (DefaultTableModel) tableNhanVien.getModel();
		modelNhanVien.setRowCount(0);
		for (Object[] rowData : data) {
			modelNhanVien.addRow(rowData);
		}
	}

	// Load lịch sử chấm công của ngày hiện tại
	private void loadDanhSachChamCong() {
		List<Object[]> data = nhanVienDAO.loadDataToChamCong();
		modelChamCong = (DefaultTableModel) tableChamCong.getModel();
		modelChamCong.setRowCount(0);
		for (Object[] rowData : data) {
			modelChamCong.addRow(rowData);
		}
	}

	// Tìm kiếm nhân viên
	private void timNhanVien() {
		String maNV = txtTimMaNV.getText().trim();

		List<Object[]> data = nhanVienDAO.searchNhanVienChamCong(maNV);
		modelNhanVien = (DefaultTableModel) tableNhanVien.getModel();
		modelNhanVien.setRowCount(0);
		for (Object[] rowData : data) {
			modelNhanVien.addRow(rowData);
		}
	}

	// Tải lại danh sách nhân viên
	private void taiLaiNhanVien() {
		txtTimMaNV.setText("");
		loadDanhSachNhanVien();
	}

	// Chấm công vào
	private void chamCongVao() throws HeadlessException, ParseException {
		int selectedRow = tableNhanVien.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để chấm công vào!");
			return;
		}

		String maCC = gen_ma();
		String maNV = (String) modelNhanVien.getValueAt(selectedRow, 0);
		String ngayChamCong = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		String maCa = (String) modelNhanVien.getValueAt(selectedRow, 3);
		String gioVao = new SimpleDateFormat("HH:mm:ss").format(new Date());
		String gioRa = null;
		String trangThai = "Đang làm";
		String ghiChu;
		try {
			// Tính ghi chú dựa trên giờ chấm công và giờ bắt đầu ca
			ghiChu = tinhGhiChuTheoGio(maCa, gioVao);
			if (ghiChu == null) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin ca làm " + maCa + "!");
				return;
			}

			// Kiểm tra xem đã chấm công vào chưa
			if (nhanVienDAO.checkChamCongVao(maNV, maCa)) {
				JOptionPane.showMessageDialog(this, "Nhân viên " + maNV + " đã chấm công vào hôm nay!");
				return;
			}

			// Thêm bản ghi chấm công
			if (nhanVienDAO.chamCongVao(maCC, maNV, ngayChamCong, maCa, gioVao, gioRa, trangThai, ghiChu)) {
				JOptionPane.showMessageDialog(this, "Chấm công vào thành công cho nhân viên " + maNV + " !");
				loadDanhSachChamCong();
			} else {
				JOptionPane.showMessageDialog(this, "Chấm công vào thất bại!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi chấm công vào: " + e.getMessage());
			e.printStackTrace();
		}

	}

	// Chấm công ra
	private void chamCongRa() {
		int selectedRow = tableChamCong.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để chấm công ra!");
			return;
		}
		
	    DefaultTableModel model = (DefaultTableModel) tableChamCong.getModel();

	    String maCC = (String) model.getValueAt(selectedRow, 0);
	    String maNV = (String) modelNhanVien.getValueAt(selectedRow, 0);

	    // Kiểm tra xem đã chấm công ra chưa
	    String gioRaCurrent = (String) model.getValueAt(selectedRow, 5);
	    if (!gioRaCurrent.equals("Chưa ra ca")) {
	    	JOptionPane.showMessageDialog(this, "Nhân viên " + maNV + " đã chấm công ra hôm nay!");
	        return;
	    }

	    String gioRa = new SimpleDateFormat("HH:mm:ss").format(new Date());

	    if (nhanVienDAO.chamCongRa(maCC, gioRa)) {
	    	JOptionPane.showMessageDialog(this, "Chấm công vào thành công cho nhân viên " + maNV + " !");
	    	taiLaiChamCong();
	    } else {
	        JOptionPane.showMessageDialog(this, "Cập nhật chấm công ra thất bại!");
	    }
	}

	// Sự kiện phím tắt enter cho tìm kiếm
	@SuppressWarnings("serial")
	private void addEnterKeyListener(JComponent component) {
		component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"searchAction");
		component.getActionMap().put("searchAction", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timNhanVien(); // Thực hiện tìm kiếm ngay khi nhấn Enter
			}
		});
	}

	// Tải lại chấm công
	private void taiLaiChamCong() {
		btnCheckIn.setEnabled(true);
		btnCheckOut.setEnabled(true);
		JDateNgayLapTim.setDate(new Date());
		cbxCaLam.setSelectedIndex(0);
		loadDanhSachChamCong();
	}

	// Tìm kiếm chấm công
	private void timChamCong() {
		String selectedCaLam = (String) cbxCaLam.getSelectedItem();
		String maCa = null;

		switch (selectedCaLam) {
		case "Ca 1 - Sáng":
			maCa = "CA1";
			break;
		case "Ca 2 - Chiều":
			maCa = "CA2";
			break;
		case "Ca 3 - Tối":
			maCa = "CA3";
			break;
		case "Ca 4 - Cả Ngày":
			maCa = "CA4";
			break;
		}

		String ngayChamCong = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (JDateNgayLapTim.getDate() != null) {
			ngayChamCong = sdf.format(JDateNgayLapTim.getDate());
		} else {
			ngayChamCong = sdf.format(new Date());
		}

		List<Object[]> data = nhanVienDAO.searchChamCong(maCa, ngayChamCong);
		modelChamCong = (DefaultTableModel) tableChamCong.getModel();
		modelChamCong.setRowCount(0);
		for (Object[] rowData : data) {
			modelChamCong.addRow(rowData);
		}
	}

	private String generateNextMaChamCong() {
		String lastMaCC = nhanVienDAO.getLastMaChamCong();
		if (lastMaCC == null || lastMaCC.isEmpty() || lastMaCC.equals("CC000")) {
			return "CC001";
		}

		String numericPart = lastMaCC.substring(2);
		int number = Integer.parseInt(numericPart);
		number++;

		return String.format("CC%03d", number);
	}

	private String gen_ma() {
		// Gen MaKH
		return generateNextMaChamCong();
	}

	// Phương thức tính ghi chú dựa trên giờ chấm công và ca làm
	private String tinhGhiChuTheoGio(String maCa, String gioVaoStr) {
		try {
			// Lấy giờ bắt đầu từ database
			String gioBatDauStr = nhanVienDAO.getGioBatDauCaLam(maCa);

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date gioVao = sdf.parse(gioVaoStr);
			Date gioBatDau = sdf.parse(gioBatDauStr);

			// Tính chênh lệch (tính bằng phút)
			long diffInMillis = gioVao.getTime() - gioBatDau.getTime();
			long diffInMinutes = diffInMillis / (60 * 1000); // Chuyển từ mili giây sang phút

			// Khoảng cách "đúng giờ" là ±5 phút
			if (Math.abs(diffInMinutes) <= 5) {
				return "Đúng giờ";
			} else if (diffInMinutes < 0) {
				return "Sớm " + Math.abs(diffInMinutes) + " phút";
			} else {
				return "Trễ " + diffInMinutes + " phút";
			}
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
			return "Không xác định";
		}
	}
}