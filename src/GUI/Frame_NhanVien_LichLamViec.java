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

import DAO.LichLamViecDAO;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import com.toedter.calendar.JDateChooser;

public class Frame_NhanVien_LichLamViec extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel pnlBackGround;
	private JTable tableLich;
	private DefaultTableModel tableModel;
	private LichLamViecDAO  lichDAO= new LichLamViecDAO();

	private Dialog_ChiTietLich dialog_ChiTietLich;
	private JButton btnThem, btnSua, btnXoa, btnXuat, btnTaiLai, btnTim;
	private JTextField txtTimMaLich, txtTimMaNV;
	private JDateChooser dateChooserTimNgay;

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
	public Frame_NhanVien_LichLamViec() {
		setLayout(null);
		setPreferredSize(new Dimension(1550, 755));

		pnlBackGround = new JPanel();
		pnlBackGround.setBounds(0, 0, 1545, 854);
		pnlBackGround.setBackground(new Color(254, 222, 192));
		pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(pnlBackGround);
		pnlBackGround.setLayout(null);

		JScrollPane scrollPaneLich = new JScrollPane();
		scrollPaneLich.setBounds(10, 10, 1504, 471);
		pnlBackGround.add(scrollPaneLich);

		tableLich = new JTable();
		tableLich.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableLich.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"M\u00E3 L\u1ECBch", "M\u00E3 NV", "Ng\u00E0y", "M\u00E3 Ca", "Ghi Ch\u00FA"
			}
		));
		tableLich.getColumnModel().getColumn(2).setPreferredWidth(55);

		JTableHeader header = tableLich.getTableHeader();
		header.setFont(new Font("Segoe UI", Font.BOLD, 18));
		tableLich.setRowHeight(30);
		scrollPaneLich.setViewportView(tableLich);

		// Căn giữa dữ liệu trong table
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tableLich.getColumnCount(); i++) {
			tableLich.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
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

		JLabel lblTimMaLich = new JLabel("Mã Lịch:");
		lblTimMaLich.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTimMaLich.setBounds(25, 16, 206, 50);
		panelTimKiem.add(lblTimMaLich);

		txtTimMaLich = new JTextField();
		txtTimMaLich.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimMaLich.setBounds(227, 21, 286, 40);
		panelTimKiem.add(txtTimMaLich);
		txtTimMaLich.setColumns(10);

		// Auto uppercase

		txtTimMaLich.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = txtTimMaLich.getText();
				txtTimMaLich.setText(text.toUpperCase());
			}
		});

		btnTim = new JButton("Tìm");
		btnTim.setIcon(new ImageIcon("icon\\find.png"));
		btnTim.setBackground(new Color(167, 62, 20));
		btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTim.setBounds(89, 190, 167, 50);
		panelTimKiem.add(btnTim);

		JLabel lblTimMaNV = new JLabel("Mã NV:");
		lblTimMaNV.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTimMaNV.setBounds(25, 70, 206, 50);
		panelTimKiem.add(lblTimMaNV);

		btnTaiLai = new JButton("Tải Lại");
		btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
		btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnTaiLai.setBackground(new Color(167, 62, 20));
		btnTaiLai.setBounds(331, 190, 167, 50);
		panelTimKiem.add(btnTaiLai);

		JLabel lblTimNgay = new JLabel("Ngày:");
		lblTimNgay.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTimNgay.setBounds(25, 122, 206, 50);
		panelTimKiem.add(lblTimNgay);

		txtTimMaNV = new JTextField();
		txtTimMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtTimMaNV.setColumns(10);
		txtTimMaNV.setBounds(227, 76, 286, 40);
		panelTimKiem.add(txtTimMaNV);

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
//
//		// Sự kiện phím tắt enter cho tìm kiếm
//		addEnterKeyListener(txtTimMaLich);
//		addEnterKeyListener(txtTimMaNV);
		
		dateChooserTimNgay = new JDateChooser();
		dateChooserTimNgay.setDateFormatString("dd/MM/yyyy");
		dateChooserTimNgay.getDateEditor().getUiComponent().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		dateChooserTimNgay.setBounds(227, 137, 286, 43);
		panelTimKiem.add(dateChooserTimNgay);

		loadDataToTable();
	}
	// Hiển thị dữ liệu lên table
	public void loadDataToTable() {
		tableModel = (DefaultTableModel) tableLich.getModel();
		tableModel.setRowCount(0);
		List<Object[]> listLich = lichDAO.loadDataToTable();
		for (Object[] row : listLich) {
			tableModel.addRow(row);
		}
	}
	// Xử lý nút thêm
		private void btnThemActionPerformed() throws ParseException {
			
			tableLich.clearSelection();
			dialog_ChiTietLich = new Dialog_ChiTietLich(null, false);
			dialog_ChiTietLich.hienThiMaLich(generateMaLich());
			dialog_ChiTietLich.setVisible(true);
			
			btnTaiLaiActionPerformed();
		}
		// Generate mã lịch
		private String generateMaLich() {
		    String lastMaLich = lichDAO.getLastMaLich();
		    if (lastMaLich == null || lastMaLich.isEmpty() || lastMaLich.equals("LLV000")) {
		        return "LLV001";
		    }
		    String numberStr = lastMaLich.substring(3);
		    int number = Integer.parseInt(numberStr);
		    number++;

		    return String.format("LLV%03d", number);
		}
		
		
		// Xử lý nút sửa
		private void btnSuaActionPerformed() throws ParseException {
			int selectedRow = tableLich.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa");
				return;
			}
			String maLich = (String) tableLich.getValueAt(selectedRow, 0);
			String maNV = (String) tableLich.getValueAt(selectedRow, 1);
			String ngay = (String) tableLich.getValueAt(selectedRow, 2);
			String maCa = (String) tableLich.getValueAt(selectedRow, 3);
			String ghiChu = (String) tableLich.getValueAt(selectedRow, 4);
			

			dialog_ChiTietLich = new Dialog_ChiTietLich(null, true);
			dialog_ChiTietLich.hienThiLich(maLich, maNV, maCa, ngay, ghiChu);
			dialog_ChiTietLich.setVisible(true);

			btnTaiLaiActionPerformed();
		}
		// Xử lý nút xóa
		private void btnXoaActionPerformed() {
			int selectedRow = tableLich.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
				return;
			}
			String maLich = (String) tableLich.getValueAt(selectedRow, 0);
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa lịch làm việc này không?",
					"Xác nhận", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				lichDAO.deleteLichLamViec(maLich);
				btnTaiLaiActionPerformed();
			}
		}
		// Xử lý nút xuất
		private void btnXuatActionPerformed() {
			try {
				Workbook workbook = new XSSFWorkbook();
				Sheet sheet = workbook.createSheet("LichLamViec");
				Row headerRow = sheet.createRow(0);
				String[] headers = { "Mã Lịch", "Mã NV", "Ngày", "Mã Ca", "Ghi Chú" };
				for (int i = 0; i < headers.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(headers[i]);
				}
				for (int i = 0; i < tableLich.getRowCount(); i++) {
					Row row = sheet.createRow(i + 1);
					for (int j = 0; j < tableLich.getColumnCount(); j++) {
						Cell cell = row.createCell(j);
						if (tableLich.getValueAt(i, j) != null) {
							cell.setCellValue(tableLich.getValueAt(i, j).toString());
						}
					}
				}
				try (FileOutputStream fileOut = new FileOutputStream("LichLamViec.xlsx")) {
					workbook.write(fileOut);
					JOptionPane.showMessageDialog(this, "Xuất file thành công!");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + e.getMessage());
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Lỗi khi xuất file: " + e.getMessage());
			}
		}
		// Xử lý nút tìm
		private void btnTimActionPerformed() {
			String maLich = txtTimMaLich.getText().trim();
			String maNV = txtTimMaNV.getText().trim();
			String ngay = "";
			if (dateChooserTimNgay.getDate() != null) {
				ngay = new java.text.SimpleDateFormat("dd/MM/yyyy").format(dateChooserTimNgay.getDate());
			}
			tableModel.setRowCount(0);
			List<Object[]> listLich = lichDAO.searchLichLamViec(maLich, maNV, ngay);
			for (Object[] row : listLich) {
				tableModel.addRow(row);
			}
	
		}
		// Xử lí nút tải lại
		private void btnTaiLaiActionPerformed() {
			txtTimMaLich.setText("");
			txtTimMaNV.setText("");
			dateChooserTimNgay.setDate(null);
			loadDataToTable();
			tableLich.clearSelection();
		}
}
