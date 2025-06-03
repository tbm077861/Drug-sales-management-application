package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DAO.ThuocDAO;

public class Frame_Thuoc_KiemKho extends JPanel {

	private static final long serialVersionUID = 1L;
    private JTable tableKiemKho;
    private DefaultTableModel tableModel;
    private ThuocDAO thuocDAO = new ThuocDAO();
    private JTextField txtSLThucTe;
    private JTextField txtSLTon;
    private JTextField txtMaThuocTim;
    private JTextField txtTenThuocTim;
    private JTextField txtChenhLechTim;

	public Frame_Thuoc_KiemKho() {
        setLayout(null);
        setPreferredSize(new Dimension(1550, 755));
        
        
        JPanel pnlBackGround = new JPanel();
        pnlBackGround.setBounds(0, 0, 1545, 854);
        pnlBackGround.setBackground(new Color(254, 222, 192));
        pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(pnlBackGround);
        pnlBackGround.setLayout(null);

        // Bảng kiểm kê
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 10, 1512, 488);
        pnlBackGround.add(scrollPane);

        tableKiemKho = new JTable(tableModel);
        tableKiemKho.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tableKiemKho.setModel(new DefaultTableModel(new Object[][] {}, 
				new String[] { "Mã Thuốc", "Tên Thuốc", "Đơn Vị Tính", "Số Lượng Tồn", "Số Lượng Thực Tế", "Chênh Lệch" }) {
				private static final long serialVersionUID = 1L;
		        boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };
		        
		        public boolean isCellEditable(int row, int column) {
                    return columnEditables[column];
                }
		});
        tableKiemKho.setRowHeight(30);
        
        JTableHeader header = tableKiemKho.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        scrollPane.setViewportView(tableKiemKho);
        
        // Căn giữa dữ liệu trong table
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableKiemKho.getColumnCount(); i++) {
            tableKiemKho.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JPanel panelXuLy = new JPanel();
        panelXuLy.setLayout(null);
        panelXuLy.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelXuLy.setBackground(new Color(242, 132, 123));
        panelXuLy.setBounds(10, 508, 513, 242);
        TitledBorder titledBorder = new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Xử Lý Kiểm Kê");
        titledBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelXuLy.setBorder(titledBorder);
        pnlBackGround.add(panelXuLy);
        
        JButton btnLuu = new JButton("Lưu");
        btnLuu.setIcon(new ImageIcon("icon\\save.png"));
        btnLuu.setForeground(Color.BLACK);
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnLuu.setBackground(new Color(167, 62, 20));
        btnLuu.setBounds(163, 182, 167, 50);
        panelXuLy.add(btnLuu);
        
        txtSLThucTe = new JTextField();
        txtSLThucTe.setText("");
        txtSLThucTe.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtSLThucTe.setColumns(10);
        txtSLThucTe.setBounds(279, 110, 202, 42);
        panelXuLy.add(txtSLThucTe);
        
        JLabel lblSLThucTe = new JLabel("Số Lượng Thực Tế:");
        lblSLThucTe.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblSLThucTe.setBounds(26, 108, 202, 34);
        panelXuLy.add(lblSLThucTe);
        
        JLabel btnSLTon = new JLabel("Số Lượng Tồn:");
        btnSLTon.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnSLTon.setBounds(26, 28, 202, 34);
        panelXuLy.add(btnSLTon);
        
        txtSLTon = new JTextField();
        txtSLTon.setText("");
        txtSLTon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtSLTon.setColumns(10);
        txtSLTon.setBounds(279, 28, 202, 42);
        panelXuLy.add(txtSLTon);
        txtSLTon.setEditable(false);
        
        JLabel lblLogo = new JLabel("");
        lblLogo.setIcon(new ImageIcon("image\\logoMTP.png"));
        lblLogo.setBounds(558, 531, 407, 211);
        pnlBackGround.add(lblLogo);
        
        JPanel panelTimKiem = new JPanel();
        panelTimKiem.setLayout(null);
        panelTimKiem.setBorder(new LineBorder(new Color(0, 0, 0)));
        panelTimKiem.setBackground(new Color(242, 132, 123));
        panelTimKiem.setBounds(984, 508, 538, 242);
        TitledBorder titledBorder2 = new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Tác Vụ Tìm Kiếm");
        titledBorder2.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelTimKiem.setBorder(titledBorder2);
        pnlBackGround.add(panelTimKiem);
        
        JLabel lblMaThuocTim = new JLabel("Mã Thuốc:");
        lblMaThuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMaThuocTim.setBounds(25, 16, 206, 50);
        panelTimKiem.add(lblMaThuocTim);
        
        txtMaThuocTim = new JTextField();
        txtMaThuocTim.setText("");
        txtMaThuocTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtMaThuocTim.setColumns(10);
        txtMaThuocTim.setBounds(227, 21, 286, 40);
        panelTimKiem.add(txtMaThuocTim);
        
        JLabel lblTenThuocTim = new JLabel("Tên Thuốc:");
        lblTenThuocTim.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTenThuocTim.setBounds(25, 70, 206, 50);
        panelTimKiem.add(lblTenThuocTim);
        
        JButton btnTaiLai = new JButton("Tải Lại");
        btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
        btnTaiLai.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnTaiLai.setBackground(new Color(167, 62, 20));
        btnTaiLai.setBounds(325, 182, 167, 50);
        panelTimKiem.add(btnTaiLai);
        
        txtTenThuocTim = new JTextField();
        txtTenThuocTim.setText("");
        txtTenThuocTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtTenThuocTim.setColumns(10);
        txtTenThuocTim.setBounds(227, 76, 286, 40);
        panelTimKiem.add(txtTenThuocTim);
        
        JLabel lblChenhLech = new JLabel("Chênh Lệch:");
        lblChenhLech.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblChenhLech.setBounds(25, 122, 206, 50);
        panelTimKiem.add(lblChenhLech);
        
        txtChenhLechTim = new JTextField();
        txtChenhLechTim.setText("");
        txtChenhLechTim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtChenhLechTim.setColumns(10);
        txtChenhLechTim.setBounds(227, 130, 286, 40);
        panelTimKiem.add(txtChenhLechTim);
        
        addEnterKeyListener(txtTenThuocTim);
        addEnterKeyListener(txtMaThuocTim);
        addEnterKeyListener(txtChenhLechTim);
        
        
        JButton btnTim = new JButton("Tìm");
        btnTim.setIcon(new ImageIcon("icon\\find.png"));
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnTim.setBackground(new Color(167, 62, 20));
        btnTim.setBounds(71, 182, 167, 50);
        panelTimKiem.add(btnTim);
        
        
        
		tableKiemKho.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int selectedRow = tableKiemKho.getSelectedRow();
				if (selectedRow != -1) {
					txtSLTon.setText(tableKiemKho.getValueAt(selectedRow, 3).toString());
					txtSLThucTe.setText(tableKiemKho.getValueAt(selectedRow, 4).toString());
				}
			}
		});
        
		txtSLThucTe.addKeyListener(new KeyAdapter() {
			@Override
		    public void keyReleased(KeyEvent e) {
		        // Chỉ cho phép nhập số
		        String text = txtSLThucTe.getText().replaceAll("[^0-9]", "");
		        txtSLThucTe.setText(text);
		    }
		});
		
		
        btnTim.addActionListener(e -> btnTimActionPerformed());
        btnLuu.addActionListener(e -> btnLuuActionPerformed());
        btnTaiLai.addActionListener(e -> btnTaiLaiActionPerformed()); 
        
        loadDataToTable();
    }


    private void loadDataToTable() {
        tableModel =  (DefaultTableModel) tableKiemKho.getModel();
        tableModel.setRowCount(0);
        List<Object[]> listThuoc = thuocDAO.getDanhSachKiemKho();
		for (Object[] thuoc : listThuoc) {
			tableModel.addRow(thuoc);
		}
    }



    private void btnTimActionPerformed() {
		String maThuoc = txtMaThuocTim.getText();
		String tenThuoc = txtTenThuocTim.getText();
		String chenhLech = txtChenhLechTim.getText();

		tableModel.setRowCount(0);
		List<Object[]> list = thuocDAO.timKiemChenhLechThuoc(maThuoc, tenThuoc, chenhLech);
		for (Object[] objects : list) {
			tableModel.addRow(objects);
		}
	}
    
    private void btnLuuActionPerformed() {
		if (tableKiemKho.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc để cập nhật số lượng thực tế.");
			return;
		}
		int selectedRow = tableKiemKho.getSelectedRow();
		String maThuoc = tableKiemKho.getValueAt(selectedRow, 0).toString();
		int slTon = Integer.parseInt(txtSLThucTe.getText());
		int slThucTe = Integer.parseInt(txtSLThucTe.getText());
		thuocDAO.capNhatSoLuong(maThuoc, slTon, slThucTe);
		btnTaiLaiActionPerformed();
	}
    
	private void btnTaiLaiActionPerformed() {
		txtSLTon.setText("");
		txtSLThucTe.setText("");
		txtMaThuocTim.setText("");
		txtTenThuocTim.setText("");
		loadDataToTable();
		tableKiemKho.clearSelection();
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