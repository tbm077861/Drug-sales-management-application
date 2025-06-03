package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JButton;
import javax.swing.JComponent;

import DAO.ThuocDAO;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

public class Frame_Thuoc_DanhSachThuoc extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTable tableSanPham;
	private DefaultTableModel tableModel;
	private ThuocDAO ThuocDAO = new ThuocDAO();

	private Dialog_ChiTietThuoc dialog_ChiTietThuoc;
	private JButton btnThem, btnSua, btnXoa, btnXuat, btnTaiLai, btnTim;
	private JTextField txtMaThuocTim, txtTenThuocTim, txtSLTonTim;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FrameQuanLySanPham frame = new FrameQuanLySanPham();
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
	public Frame_Thuoc_DanhSachThuoc() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1545, 854);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1504, 471);
		pnlBackGround.add(scrollPane);

		tableSanPham = new JTable();
		tableSanPham.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableSanPham.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Mã thuốc", "Tên thuốc", "Đơn vị tính", "Đơn giá nhập", "Đơn giá bán", "Hạn sử dụng",
						"Hàm lượng", "Số lượng tồn", "Số lượng thực tế" }) {
			private static final long serialVersionUID = 1L;
			boolean [] columnEditables = new boolean[] { false, false, false, false, false, false, false, false, false };
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});

		tableSanPham.getColumnModel().getColumn(2).setPreferredWidth(55);

		JTableHeader header = tableSanPham.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tableSanPham.setRowHeight(30);
		scrollPane.setViewportView(tableSanPham);

		// Căn giữa dữ liệu trong table
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tableSanPham.getColumnCount(); i++) {
			tableSanPham.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JPanel panelTimKiem = new JPanel();
		panelTimKiem.setBackground(new Color(242, 132, 123));
		panelTimKiem.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelTimKiem.setBounds(964, 491, 550, 250);
		pnlBackGround.add(panelTimKiem);
		panelTimKiem.setLayout(null);
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Tác vụ tìm kiếm");
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

		btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setBackground(new Color(167, 62, 20));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBounds(89, 190, 167, 50);
		panelTimKiem.add(btnTim);

		JLabel lblTenThuocTim = new JLabel("Tên Thuốc:");
		lblTenThuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTenThuocTim.setBounds(25, 70, 206, 50);
		panelTimKiem.add(lblTenThuocTim);

		btnTaiLai = new JButton("Tải Lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBackground(new Color(167, 62, 20));
		btnTaiLai.setBounds(331, 190, 167, 50);
		panelTimKiem.add(btnTaiLai);

		JLabel lblSoLuongTonTim = new JLabel("Số Lượng Tồn:");
		lblSoLuongTonTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblSoLuongTonTim.setBounds(25, 122, 206, 50);
		panelTimKiem.add(lblSoLuongTonTim);

		txtTenThuocTim = new JTextField();
		txtTenThuocTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTenThuocTim.setColumns(10);
		txtTenThuocTim.setBounds(227, 76, 286, 40);
		panelTimKiem.add(txtTenThuocTim);

		txtSLTonTim = new JTextField();
		txtSLTonTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtSLTonTim.setColumns(10);
		txtSLTonTim.setBounds(227, 130, 286, 40);
		txtSLTonTim.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        String text = txtSLTonTim.getText().replaceAll("[^0-9]", ""); // Loại bỏ mọi ký tự không phải số
		        txtSLTonTim.setText(text); // Cập nhật lại giá trị chỉ chứa số
		    }
		});
		panelTimKiem.add(txtSLTonTim);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon("image\\logoMTP.png"));
		lblLogo.setBounds(521, 510, 407, 211);
		pnlBackGround.add(lblLogo);

		JPanel panelXuLy = new JPanel();
		panelXuLy.setLayout(null);
		panelXuLy.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelXuLy.setBackground(new Color(242, 132, 123));
		panelXuLy.setBounds(10, 491, 424, 250);
		TitledBorder xuLyBorder = BorderFactory.createTitledBorder("Tác vụ xử Lý");
		xuLyBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
		panelXuLy.setBorder(xuLyBorder);
		pnlBackGround.add(panelXuLy);

		btnThem = new JButton("Thêm");
		btnThem.setIcon(new ImageIcon("icon\\add.png"));
		btnThem.setForeground(Color.BLACK);
		btnThem.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnThem.setBackground(new Color(167, 62, 20));
		btnThem.setBounds(35, 46, 140, 50);
		panelXuLy.add(btnThem);

		btnSua = new JButton("Sửa");
		btnSua.setIcon(new ImageIcon("icon\\edit.png"));
		btnSua.setForeground(Color.BLACK);
		btnSua.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSua.setBackground(new Color(167, 62, 20));
		btnSua.setBounds(35, 157, 140, 50);
		panelXuLy.add(btnSua);

		btnXoa = new JButton("Xóa");
		btnXoa.setIcon(new ImageIcon("icon\\delete.png"));
		btnXoa.setForeground(Color.BLACK);
		btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXoa.setBackground(new Color(167, 62, 20));
		btnXoa.setBounds(244, 46, 140, 50);
		panelXuLy.add(btnXoa);

		btnXuat = new JButton("Xuất");
		btnXuat.setIcon(new ImageIcon("icon\\print.png"));
		btnXuat.setForeground(Color.BLACK);
		btnXuat.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnXuat.setBackground(new Color(167, 62, 20));
		btnXuat.setBounds(244, 157, 140, 50);
		panelXuLy.add(btnXuat);

		btnThem.addActionListener(e -> {
			try {
				btnThemActionPerformed();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnSua.addActionListener(e -> {
			try {
				btnSuaActionPerformed();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnXoa.addActionListener(e -> btnXoaActionPerformed());
		btnXuat.addActionListener(e -> btnXuatActionPerformed());
		btnTim.addActionListener(e -> btnTimActionPerformed());
		btnTaiLai.addActionListener(e -> btnTaiLaiActionPerformed());

		// Sự kiện phím tắt enter cho tìm kiếm
		addEnterKeyListener(txtMaThuocTim);
		addEnterKeyListener(txtTenThuocTim);
		addEnterKeyListener(txtSLTonTim);

		loadDataToTable();
	}

	// Hiển thị dữ liệu
	private void loadDataToTable() {
		tableModel = (DefaultTableModel) tableSanPham.getModel();
		tableModel.setRowCount(0);
		List<Object[]> list = ThuocDAO.loadDataToTable();
		for (Object[] objects : list) {
			tableModel.addRow(objects);
		}
	}

	// Xử lý nút thêm
	private void btnThemActionPerformed() throws ParseException {
		
		tableSanPham.clearSelection();
		dialog_ChiTietThuoc = new Dialog_ChiTietThuoc(null, false);
		dialog_ChiTietThuoc.hienThiMaThuoc(generateMaThuoc());
		dialog_ChiTietThuoc.setVisible(true);
		
		btnTaiLaiActionPerformed();
	}

	// Xử lý nút sửa
	private void btnSuaActionPerformed() throws ParseException {
		int row = tableSanPham.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần sửa!");
			return;
		}
		
		String maThuoc = tableSanPham.getValueAt(row, 0).toString();
		String tenThuoc = tableSanPham.getValueAt(row, 1).toString();
		String donViTinh = tableSanPham.getValueAt(row, 2).toString();
		String donGiaNhap = tableSanPham.getValueAt(row, 3).toString();
		donGiaNhap = donGiaNhap.replaceAll("[^\\d]", "");
		String donGiaBan = tableSanPham.getValueAt(row, 4).toString();
		donGiaBan = donGiaBan.replaceAll("[^\\d]", "");
		String hanSuDung = tableSanPham.getValueAt(row, 5).toString();
		String hamLuong = tableSanPham.getValueAt(row, 6).toString();
        String soLuongTon = tableSanPham.getValueAt(row, 7).toString();
        String soLuongThucTe = tableSanPham.getValueAt(row, 8).toString();
		
		dialog_ChiTietThuoc = new Dialog_ChiTietThuoc(null, true);
		dialog_ChiTietThuoc.hienThiThuoc(maThuoc, tenThuoc, donViTinh, donGiaNhap, donGiaBan, hanSuDung, hamLuong, soLuongTon, soLuongThucTe);
		dialog_ChiTietThuoc.setVisible(true);
		
		btnTaiLaiActionPerformed();
	}

	// Xử lý nút xóa
	private void btnXoaActionPerformed() {
		int row = tableSanPham.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn sản phẩm cần xóa!");
			return;
		}
		String maThuoc = (String) tableSanPham.getValueAt(row, 0);
		int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sản phẩm này không?",
				"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			ThuocDAO.xoaThuoc(maThuoc);
			JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công!", "Xóa sản phẩm",
					JOptionPane.INFORMATION_MESSAGE);
			loadDataToTable();
		}
	}

	// Xử lý nút xuất
	private void btnXuatActionPerformed() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachThuoc");

		// Create header row
		Row headerRow = sheet.createRow(0);
		String[] headers = { "Mã Thuốc", "Tên Thuốc", "Đơn Vị Tính", "Đơn Giá Nhập", "Đơn Giá Bán", "Hạn Sử Dụng",
				"Hàm Lượng", "Số Lượng Tồn", "Số Lượng Thực Tế" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Populate data rows
		List<Object[]> List = ThuocDAO.getAllThuoc();
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
			row.createCell(7).setCellValue((int) nv[7]);
			row.createCell(8).setCellValue((int) nv[8]);
		}

		// Write the output to a file
		try (FileOutputStream fileOut = new FileOutputStream("DanhSachThuoc.xlsx")) {
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

	// Xử lý nút tìm
	private void btnTimActionPerformed() {
		String maThuoc = txtMaThuocTim.getText();
		String tenThuoc = txtTenThuocTim.getText();
		String soLuongTon = txtSLTonTim.getText();

		List<Object[]> list = ThuocDAO.timKiemThuoc(maThuoc, tenThuoc, soLuongTon);
		tableModel.setRowCount(0);
		for (Object[] row : list) {
			tableModel.addRow(row);
		}
	}

	// Xử lý nút tải lại
	private void btnTaiLaiActionPerformed() {
		txtMaThuocTim.setText("");
		txtTenThuocTim.setText("");
		txtSLTonTim.setText("");
		loadDataToTable();
		tableSanPham.clearSelection();
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
	
	// Generate maThuoc
	private String generateMaThuoc() {
	    String lastMaThuoc = ThuocDAO.getLastMaThuoc();
	    if (lastMaThuoc == null || lastMaThuoc.isEmpty() || lastMaThuoc.equals("T000")) {
	        return "T001";
	    }
	    String numberStr = lastMaThuoc.substring(1);
	    int number = Integer.parseInt(numberStr);
	    number++;

	    return String.format("T%03d", number);
	}
}
