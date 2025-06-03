package GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.JButton;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

import DAO.ChiTietTraThuocDAO;
import DAO.HoaDonTraThuocDAO;

import javax.swing.JComponent;

public class Frame_HoaDon_TraThuoc extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private DefaultTableModel tableModel;
	private JTextField txtMaHoaDonTim;
	private JTextField txtMaNhanVienTim;
	private JTextField txtMaKhachHangTim;
	private JTable tableHoaDon;
	private JDateChooser JDateNgayTraTim;
	private HoaDonTraThuocDAO hoaDonDao = new HoaDonTraThuocDAO();
	private ChiTietTraThuocDAO chiTietHoaDonDAO = new ChiTietTraThuocDAO();
	public static final int NO_SUCH_PAGE = 1;
	public static final int PAGE_EXISTS = 0;

	/**
	 * Create the frame.
	 */
	public Frame_HoaDon_TraThuoc() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1543, 751);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 1067, 731);
		pnlBackGround.add(scrollPane);

		tableHoaDon = new JTable();
		tableHoaDon.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã hóa đơn", "Mã nhân viên",
				 "Mã khách hàng", "Ngày trả", "Lý do trả" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(55);
		tableHoaDon.setRowHeight(30);
		// Create a custom cell renderer that centers the text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Apply the renderer to each column of the table
		for (int i = 0; i < tableHoaDon.getColumnCount(); i++) {
			tableHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JTableHeader header = tableHoaDon.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		scrollPane.setViewportView(tableHoaDon);

		JPanel PanelTimKiem = new JPanel();
		PanelTimKiem.setBackground(new Color(242, 132, 123));
		PanelTimKiem.setBorder(
				new TitledBorder(null, "Tác vụ tìm kiếm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PanelTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		PanelTimKiem.setBounds(1099, 42, 422, 476);
		pnlBackGround.add(PanelTimKiem);
		PanelTimKiem.setLayout(null);

		JLabel lbMaHD = new JLabel("Mã Hóa Đơn");
		lbMaHD.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lbMaHD.setBounds(22, 38, 123, 40);
		PanelTimKiem.add(lbMaHD);

		txtMaHoaDonTim = new JTextField();
		txtMaHoaDonTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaHoaDonTim.setBounds(186, 41, 225, 40);
		PanelTimKiem.add(txtMaHoaDonTim);
		txtMaHoaDonTim.setColumns(10);

		JDateNgayTraTim = new JDateChooser();
		JDateNgayTraTim.setDateFormatString("dd/MM/yyyy");
		JDateNgayTraTim.setBounds(186, 310, 225, 40);
		JDateNgayTraTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		PanelTimKiem.add(JDateNgayTraTim);

		JButton btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setBackground(new Color(0, 0, 0));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBounds(58, 400, 135, 53);
		PanelTimKiem.add(btnTim);

		JButton btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setBackground(new Color(0, 0, 0));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBounds(252, 400, 135, 53);
		PanelTimKiem.add(btnTaiLai);

		JLabel lblMaKH = new JLabel("Mã Khách Hàng");
		lblMaKH.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaKH.setBounds(22, 218, 167, 40);
		PanelTimKiem.add(lblMaKH);

		txtMaNhanVienTim = new JTextField();
		txtMaNhanVienTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaNhanVienTim.setColumns(10);
		txtMaNhanVienTim.setBounds(186, 128, 225, 40);
		PanelTimKiem.add(txtMaNhanVienTim);

		JLabel lblMaNhanVien = new JLabel("Mã Nhân Viên");
		lblMaNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMaNhanVien.setBounds(22, 125, 147, 40);
		PanelTimKiem.add(lblMaNhanVien);

		txtMaKhachHangTim = new JTextField();
		txtMaKhachHangTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaKhachHangTim.setColumns(10);
		txtMaKhachHangTim.setBounds(186, 220, 225, 40);
		PanelTimKiem.add(txtMaKhachHangTim);

		JLabel lblNgayTra = new JLabel("Ngày Trả");
		lblNgayTra.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNgayTra.setBounds(22, 310, 147, 40);
		PanelTimKiem.add(lblNgayTra);

		JPanel PanelXuLy = new JPanel();
		PanelXuLy.setLayout(null);
		PanelXuLy.setBorder(new TitledBorder(null, "Tác vụ xử lý", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PanelXuLy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		PanelXuLy.setBackground(new Color(242, 132, 123));
		PanelXuLy.setBounds(1099, 613, 422, 100);
		pnlBackGround.add(PanelXuLy);

		JButton btnXuatFile = new JButton("Xuất");
		btnXuatFile.setBounds(127, 30, 179, 48);
		PanelXuLy.add(btnXuatFile);
		btnXuatFile.setIcon(new ImageIcon("icon\\print.png"));
		btnXuatFile.setFont(new Font("Times New Roman", Font.BOLD, 18));

		loadDataToTable();

		btnTim.addActionListener(e -> btnTimAction());
		btnTaiLai.addActionListener(e -> btnTaiLaiAction());
		btnXuatFile.addActionListener(e -> btnXuatFileAction());

		addEnterKeyListener(txtMaHoaDonTim);
		addEnterKeyListener(txtMaNhanVienTim);
		addEnterKeyListener(txtMaKhachHangTim);
		addEnterKeyListener(JDateNgayTraTim);

		tableHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int selectedRow = tableHoaDon.getSelectedRow();
				String maHoaDon = (String) tableModel.getValueAt(selectedRow, 0);
				String maNV = (String) tableModel.getValueAt(selectedRow, 1);
				String maKH = (String) tableModel.getValueAt(selectedRow, 2);
				String ngayTra = (String) tableModel.getValueAt(selectedRow, 3);
				String lydoTra = (String) tableModel.getValueAt(selectedRow, 4);

				inHoaDon(maHoaDon, maNV, maKH, ngayTra, lydoTra);
			}
		});
	}

	// Cập nhật dữ liệu vào table
	private void loadDataToTable() {
		List<Object[]> data = hoaDonDao.loadDataToTable();
		tableModel = (DefaultTableModel) tableHoaDon.getModel();
		tableModel.setRowCount(0);
		for (Object[] rowData : data) {
			tableModel.addRow(rowData);
		}
	}

	// Xóa dữ liệu trong các ô tìm kiếm
	private void btnTaiLaiAction() {
		txtMaHoaDonTim.setText("");
		txtMaNhanVienTim.setText("");
		txtMaKhachHangTim.setText("");
		JDateNgayTraTim.setDate(null);
		loadDataToTable();
	}

	// Tìm kiếm hóa đơn
	private void btnTimAction() {
		String maHoaDon = txtMaHoaDonTim.getText();
		String maNV = txtMaNhanVienTim.getText();
		String maKH = txtMaKhachHangTim.getText();
		String ngayTra = null;
	    if (JDateNgayTraTim.getDate() != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        ngayTra = sdf.format(JDateNgayTraTim.getDate());
	    }
		List<Object[]> data = hoaDonDao.searchHoaDon(maHoaDon, maNV, maKH, ngayTra);
		tableModel.setRowCount(0);
		for (Object[] rowData : data) {
			tableModel.addRow(rowData);
		}
	}

	// Xuất hóa đơn ra file Excel
	private void btnXuatFileAction() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachPhieuTraThuoc");

		// Create header row
		Row headerRow = sheet.createRow(0);
		String[] headers = { "Mã hóa đơn", "Mã nhân viên", "Mã khách hàng", "Ngày trả", "Lý do trả" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Populate data rows from table
		DefaultTableModel tableModel = (DefaultTableModel) tableHoaDon.getModel();
		int rowCount = tableModel.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.createRow(i + 1); // Bắt đầu từ dòng 1 vì dòng 0 là header
			for (int j = 0; j < tableModel.getColumnCount(); j++) {
				Object value = tableModel.getValueAt(i, j);
				Cell cell = row.createCell(j);
				if (value != null) {
					if (j == 2) { // Cột "Tổng tiền" (cột thứ 3, index 2)
						// Loại bỏ ký tự định dạng tiền tệ (VD: "1.000.000 ₫") và chuyển thành số
						String totalStr = value.toString().replaceAll("[^0-9.]", "");
						try {
							double total = Double.parseDouble(totalStr);
							cell.setCellValue(total);
						} catch (NumberFormatException e) {
							cell.setCellValue(value.toString()); // Nếu lỗi, giữ nguyên giá trị
						}
					} else {
						cell.setCellValue(value.toString());
					}
				} else {
					cell.setCellValue("");
				}
			}
		}

		// Auto-size columns for better readability
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		try (FileOutputStream fileOut = new FileOutputStream("DanhSachPhieuTraThuoc.xlsx")) {
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

	// Sự kiện phím tắt enter cho tìm kiếm
	@SuppressWarnings("serial")
	private void addEnterKeyListener(JComponent component) {
		component.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
				"searchAction");
		component.getActionMap().put("searchAction", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnTimAction(); // Thực hiện tìm kiếm ngay khi nhấn Enter
			}
		});
	}

	private void inHoaDon(String maHoaDon, String maNV, String maKH, String ngayTra, String lyDoTra) {
		try {
			List<Object[]> data = chiTietHoaDonDAO.getChiTietPhieuTraThuoc(maHoaDon);

			Dialog_InPhieuTraThuoc inHoaDon = new Dialog_InPhieuTraThuoc(null, maHoaDon, maNV, maKH, ngayTra, lyDoTra, data);
			inHoaDon.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
