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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import DAO.ChiTietDatThuocDAO;
import DAO.HoaDonDatThuocDAO;

import javax.swing.JComboBox;
import javax.swing.JComponent;

public class Frame_HoaDon_DatThuoc extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private DefaultTableModel tableModel;
	private JTextField txtMaPhieuDatTim;
	private JTextField txtMaNhanVienTim;
	private JTextField txtMaKhachHangTim;
	private JTable tablePhieuDatHang;
	private JDateChooser txtNgayDatTim, txtNgayGiaoTim;
	private HoaDonDatThuocDAO hoaDonDao = new HoaDonDatThuocDAO();
	private ChiTietDatThuocDAO chiTietHoaDonDAO = new ChiTietDatThuocDAO();
	private JComboBox<String> cbxTrangThaiTim, cbxPhuongThucTT;
	public static final int NO_SUCH_PAGE = 1;
	public static final int PAGE_EXISTS = 0;

	/**
	 * Create the frame.
	 */
	public Frame_HoaDon_DatThuoc() {
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

		tablePhieuDatHang = new JTable();
		tablePhieuDatHang.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã Phiếu Đặt", "Mã NV", "Mã KH", "Ngày Đặt",
				"Ngày Giao", "Trạng thái", "Phương thức" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tablePhieuDatHang.getColumnModel().getColumn(2).setPreferredWidth(55);
		tablePhieuDatHang.setRowHeight(30);
		// Create a custom cell renderer that centers the text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Apply the renderer to each column of the table
		for (int i = 0; i < tablePhieuDatHang.getColumnCount(); i++) {
			tablePhieuDatHang.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JTableHeader header = tablePhieuDatHang.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		scrollPane.setViewportView(tablePhieuDatHang);

		JPanel PanelTimKiem = new JPanel();
		PanelTimKiem.setBackground(new Color(242, 132, 123));
		PanelTimKiem.setBorder(
				new TitledBorder(null, "Tác vụ tìm kiếm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PanelTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		PanelTimKiem.setBounds(1099, 12, 422, 570);
		pnlBackGround.add(PanelTimKiem);
		PanelTimKiem.setLayout(null);

		JLabel lbMPhieuDat = new JLabel("Mã phiếu đặt");
		lbMPhieuDat.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lbMPhieuDat.setBounds(22, 38, 154, 40);
		PanelTimKiem.add(lbMPhieuDat);

		txtMaPhieuDatTim = new JTextField();
		txtMaPhieuDatTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaPhieuDatTim.setBounds(186, 41, 225, 40);
		PanelTimKiem.add(txtMaPhieuDatTim);
		txtMaPhieuDatTim.setColumns(10);

		txtNgayDatTim = new JDateChooser();
		txtNgayDatTim.setDateFormatString("dd/MM/yyyy");
		txtNgayDatTim.setBounds(186, 231, 225, 40);
		txtNgayDatTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		PanelTimKiem.add(txtNgayDatTim);

		JButton btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setBackground(new Color(0, 0, 0));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBounds(48, 493, 135, 53);
		PanelTimKiem.add(btnTim);

		JButton btnTaiLai = new JButton("Tải lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setBackground(new Color(0, 0, 0));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBounds(242, 493, 135, 53);
		PanelTimKiem.add(btnTaiLai);

		JLabel lblMKhchHng = new JLabel("Mã khách hàng");
		lblMKhchHng.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMKhchHng.setBounds(22, 167, 147, 40);
		PanelTimKiem.add(lblMKhchHng);

		txtMaNhanVienTim = new JTextField();
		txtMaNhanVienTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaNhanVienTim.setColumns(10);
		txtMaNhanVienTim.setBounds(186, 104, 225, 40);
		PanelTimKiem.add(txtMaNhanVienTim);

		JLabel lblMNhnVin = new JLabel("Mã nhân viên");
		lblMNhnVin.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblMNhnVin.setBounds(22, 101, 147, 40);
		PanelTimKiem.add(lblMNhnVin);

		txtMaKhachHangTim = new JTextField();
		txtMaKhachHangTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaKhachHangTim.setColumns(10);
		txtMaKhachHangTim.setBounds(186, 169, 225, 40);
		PanelTimKiem.add(txtMaKhachHangTim);

		JLabel lblNgyLpn = new JLabel("Ngày đặt");
		lblNgyLpn.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNgyLpn.setBounds(22, 231, 147, 40);
		PanelTimKiem.add(lblNgyLpn);

		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTrangThai.setBounds(22, 355, 123, 40);
		PanelTimKiem.add(lblTrangThai);

		cbxTrangThaiTim = new JComboBox<>(new String[] { "Tất cả", "Đã thanh toán", "Chờ xử lý", "Đã hủy" });
		cbxTrangThaiTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbxTrangThaiTim.setBounds(186, 355, 225, 40);
		PanelTimKiem.add(cbxTrangThaiTim);

		JLabel lblPhuongThucThanhToan = new JLabel("Thanh Toán");
		lblPhuongThucThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblPhuongThucThanhToan.setBounds(22, 418, 123, 40);
		PanelTimKiem.add(lblPhuongThucThanhToan);

		cbxPhuongThucTT = new JComboBox<>(new String[] { "Tất cả", "Tiền mặt", "Chuyển khoản" });
		cbxPhuongThucTT.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		cbxPhuongThucTT.setBounds(186, 418, 225, 40);
		PanelTimKiem.add(cbxPhuongThucTT);

		JPanel PanelXuLy = new JPanel();
		PanelXuLy.setLayout(null);
		PanelXuLy.setBorder(new TitledBorder(null, "Tác vụ xử lý", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		PanelXuLy.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		PanelXuLy.setBackground(new Color(242, 132, 123));
		PanelXuLy.setBounds(1099, 592, 422, 149);
		pnlBackGround.add(PanelXuLy);

		JButton btnXuatFile = new JButton("Xuất");
		btnXuatFile.setBounds(124, 23, 179, 48);
		PanelXuLy.add(btnXuatFile);
		btnXuatFile.setIcon(new ImageIcon("icon\\print.png"));
		btnXuatFile.setFont(new Font("Times New Roman", Font.BOLD, 18));

		JButton btnThanhToan = new JButton("THANH TOÁN");
		btnThanhToan.setOpaque(true);
		btnThanhToan.setForeground(Color.WHITE);
		btnThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnThanhToan.setContentAreaFilled(true);
		btnThanhToan.setBorderPainted(false);
		btnThanhToan.setBackground(new Color(0, 128, 255));
		btnThanhToan.setBounds(20, 88, 179, 51);
		PanelXuLy.add(btnThanhToan);

		JButton btnHuy = new JButton("Hủy");
		btnHuy.setOpaque(true);
		btnHuy.setForeground(Color.WHITE);
		btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnHuy.setContentAreaFilled(true);
		btnHuy.setBorderPainted(false);
		btnHuy.setBackground(Color.RED);
		btnHuy.setBounds(222, 88, 179, 51);
		PanelXuLy.add(btnHuy);

		loadDataToTable();

		btnTim.addActionListener(e -> btnTimAction());
		btnTaiLai.addActionListener(e -> btnTaiLaiAction());
		btnXuatFile.addActionListener(e -> btnXuatFileAction());

		addEnterKeyListener(txtMaPhieuDatTim);
		addEnterKeyListener(txtMaNhanVienTim);
		addEnterKeyListener(txtMaKhachHangTim);
		addEnterKeyListener(txtNgayDatTim);
		addEnterKeyListener(cbxTrangThaiTim);
		addEnterKeyListener(cbxPhuongThucTT);
		
		JLabel lblNgyGiao = new JLabel("Ngày Giao");
		lblNgyGiao.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblNgyGiao.setBounds(22, 294, 147, 40);
		PanelTimKiem.add(lblNgyGiao);
		
		txtNgayGiaoTim = new JDateChooser();
		txtNgayGiaoTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtNgayGiaoTim.setDateFormatString("dd/MM/yyyy");
		txtNgayGiaoTim.setBounds(186, 294, 225, 40);
		PanelTimKiem.add(txtNgayGiaoTim);

		tablePhieuDatHang.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int selectedRow = tablePhieuDatHang.getSelectedRow();
				String maPDH = (String) tableModel.getValueAt(selectedRow, 0);
				String maNV = (String) tableModel.getValueAt(selectedRow, 1);
				String maKhachHang = (String) tableModel.getValueAt(selectedRow, 2);
				String ngayDat = (String) tableModel.getValueAt(selectedRow, 3);
				String ngayGiao = (String) tableModel.getValueAt(selectedRow, 4);
				String trangThaiStr = (String) tableModel.getValueAt(selectedRow, 5);
				String phuongThucThanhToanStr = (String) tableModel.getValueAt(selectedRow, 6);

				if (evt.getClickCount() == 2) {
					if (selectedRow != -1) {
						if(trangThaiStr.equals("Đã thanh toán"))
							// In hóa đơn
							inHoaDon(maPDH, maNV, maKhachHang, ngayDat, ngayGiao, trangThaiStr, phuongThucThanhToanStr);
						else if (trangThaiStr.equals("Đã hủy")) {
							// Hiện hoá đơn đã hủy
							huyHoaDon(maPDH, maNV, maKhachHang, ngayDat, ngayGiao, trangThaiStr, phuongThucThanhToanStr);
						}
					}
				}

				if (evt.getClickCount() == 1) {
					if (selectedRow != -1) {
						if (trangThaiStr.equals("Đã thanh toán") || trangThaiStr.equals("Đã hủy")) {
							btnThanhToan.setEnabled(false);
							btnHuy.setEnabled(false);
						} else if (trangThaiStr.equals("Chờ xử lý")) {
							btnThanhToan.setEnabled(true);
							btnHuy.setEnabled(true);
						}
					}
				}
			}
		});

		btnThanhToan.addActionListener(e -> {
			try {
				btnThanhToanAction();
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		});
		btnHuy.addActionListener(e -> btnHuyAction());
	}

	// Cập nhật dữ liệu vào table
	private void loadDataToTable() {
		List<Object[]> data = hoaDonDao.loadDataToTablePDH();
		tableModel = (DefaultTableModel) tablePhieuDatHang.getModel();
		tableModel.setRowCount(0);
		for (Object[] rowData : data) {
			tableModel.addRow(rowData);
		}
	}

	// Xóa dữ liệu trong các ô tìm kiếm
	private void btnTaiLaiAction() {
		txtMaPhieuDatTim.setText("");
		txtMaNhanVienTim.setText("");
		txtMaKhachHangTim.setText("");
		txtNgayDatTim.setDate(null);
		txtNgayGiaoTim.setDate(null);
		cbxTrangThaiTim.setSelectedIndex(0);
		cbxPhuongThucTT.setSelectedIndex(0);
		loadDataToTable();
	}

	// Tìm kiếm hóa đơn
	private void btnTimAction() {
		String maPDH = txtMaPhieuDatTim.getText();
		String maNV = txtMaNhanVienTim.getText();
		String maKH = txtMaKhachHangTim.getText();
		String ngayDat = null;
	    if (txtNgayDatTim.getDate() != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        ngayDat = sdf.format(txtNgayDatTim.getDate());
	    }
	    String ngayGiao = null;
		if (txtNgayGiaoTim.getDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ngayGiao = sdf.format(txtNgayGiaoTim.getDate());
		}
		String trangThai = (String) cbxTrangThaiTim.getSelectedItem();
		String phuongThucTT = (String) cbxPhuongThucTT.getSelectedItem();

		List<Object[]> data = hoaDonDao.searchPhieuDatHang(maPDH, maNV, maKH, ngayDat, ngayGiao, trangThai, phuongThucTT);
		tableModel.setRowCount(0);
		for (Object[] rowData : data) {
			tableModel.addRow(rowData);
		}
	}

	// Xuất hóa đơn ra file Excel
	private void btnXuatFileAction() {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DanhSachPhieuDatHang");

		// Create header row
		Row headerRow = sheet.createRow(0);
		String[] headers = { "Mã phiếu đặt hàng", "Mã nhân viên", "Mã khách hàng", "Ngày đặt", "Ngày Giao", "Tổng tiền", "Trạng thái",
				"Phương thức thanh toán" };
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
		}

		// Populate data rows from table
		DefaultTableModel tableModel = (DefaultTableModel) tablePhieuDatHang.getModel();
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
		try (FileOutputStream fileOut = new FileOutputStream("DanhSachPhieuDatHang.xlsx")) {
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

	private void inHoaDon(String maPDH, String maNV, String maKH, String ngayDat, String ngayGiao,
			String trangThaiStr, String phuongThucThanhToanStr) {
		try {
			List<Object[]> data = chiTietHoaDonDAO.getChiTietPhieuDatThuoc(maPDH);

			Dialog_InPhieuDatThuoc inPhieuDatHang = new Dialog_InPhieuDatThuoc(null, maPDH, maNV, maKH, ngayDat, ngayGiao,
					trangThaiStr, phuongThucThanhToanStr, data);
			inPhieuDatHang.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi in phiếu đặt hàng: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void huyHoaDon(String maPDH, String maNV, String maKH, String ngayDat, String ngayGiao,
			String trangThaiStr, String phuongThucThanhToanStr) {
		try {
			List<Object[]> data = chiTietHoaDonDAO.getChiTietPhieuDatThuoc(maPDH);

			Dialog_XemPhieuDatThuoc huyPhieuDatHang = new Dialog_XemPhieuDatThuoc(null, maPDH, maNV, maKH, ngayDat, ngayGiao,
					trangThaiStr, phuongThucThanhToanStr, data);
			huyPhieuDatHang.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi khi in phiếu đặt hàng: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void btnHuyAction() {
		int selectedRow = tablePhieuDatHang.getSelectedRow();
		if (selectedRow != -1) {
			String maPDH = (String) tableModel.getValueAt(selectedRow, 0);
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy phiếu đặt hàng này?", "Hủy phiếu đặt hàng",
					JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				if (hoaDonDao.huyPhieuDatHang(maPDH)) {
					JOptionPane.showMessageDialog(this, "Hủy phiếu đặt hàng thành công");
					loadDataToTable();
				} else {
					JOptionPane.showMessageDialog(this, "Hủy phiếu đặt hàng thất bại");
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu đặt hàng để hủy");
		}
	}
	
	private void btnThanhToanAction() throws ParseException {
		String maPDH = (String) tableModel.getValueAt(tablePhieuDatHang.getSelectedRow(), 0);
		Object[] data = chiTietHoaDonDAO.getPhieuDatThuocInfo(maPDH);
		Frame_Thuoc_GiaoDich_DatThuoc giaoDich = new Frame_Thuoc_GiaoDich_DatThuoc(data[1].toString());
		giaoDich.setPhieuDatInfo(maPDH, data[2].toString(), data[3].toString(), data[4].toString(), data[6].toString());
		
		FrameTrangChu frameTrangChu = (FrameTrangChu) SwingUtilities.getWindowAncestor(this);
		if (frameTrangChu != null) {
			frameTrangChu.setMenuColor(frameTrangChu.getBtnThuoc());
		}
		this.getParent().add(giaoDich);
		this.setVisible(false);
	}
}
