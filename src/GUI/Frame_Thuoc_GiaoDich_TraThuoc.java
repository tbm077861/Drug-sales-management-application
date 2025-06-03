package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;
import DAO.*;

public class Frame_Thuoc_GiaoDich_TraThuoc extends JPanel {
    private static final long serialVersionUID = 1L;
    private JPanel pnlBackGround;
    private JTable tableHoaDon;
    private JTable tableChiTiet;
    private JTable tableChiTietTra;
    private JTextField txtMaPhieuTra;
    private JTextField txtMaHoaDon;
    private JTextField txtMaKH;
    private JTextField txtTenKH;
    private JDateChooser txtNgayTra;
    private JComboBox<String> comboBoxMaHD;
    private JTextField txtMaKHSearch;
    private JTextField txtTenKHSearch;
    private JTextField txtSoDienThoaiSearch;
    private JComboBox<String> comboLyDoTra;
    private DefaultTableModel modelHoaDon, modelChiTiet, modelChiTietTra;
    private TraThuocDAO traThuocDAO = new TraThuocDAO();
    private ChiTietBanThuocDAO chiTietBanThuocDAO = new ChiTietBanThuocDAO();
    private ChiTietTraThuocDAO chiTietTraThuocDAO = new ChiTietTraThuocDAO();
    private TaiChinhDAO taiChinhDAO = new TaiChinhDAO();
    private String maNV;
    private JButton btnLuuPhieuTra;
    private JButton btnHuy;

    private JComboBox<String> comboBoxLoaiHoaDon;
    
    // Constructor không tham số
    public Frame_Thuoc_GiaoDich_TraThuoc() {
        this(null);
    }

    // Constructor có tham số
    public Frame_Thuoc_GiaoDich_TraThuoc(String maNV) {
        this.maNV = maNV;

        setLayout(null);
        setPreferredSize(new Dimension(1550, 755));

        // Background Panel
        pnlBackGround = new JPanel();
        pnlBackGround.setBounds(0, 0, 1559, 771);
        pnlBackGround.setBackground(new Color(254, 222, 192));
        pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
        add(pnlBackGround);
        pnlBackGround.setLayout(null);

        // Panel Tìm Hóa Đơn
        JPanel panel_TimKiem = new JPanel();
        panel_TimKiem.setBounds(1065, 10, 460, 305);
        panel_TimKiem.setBackground(new Color(220, 128, 78));
        panel_TimKiem.setLayout(null);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2), "Tìm Hóa Đơn");
        titledBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel_TimKiem.setBorder(titledBorder);
        pnlBackGround.add(panel_TimKiem);

        JLabel lblMaHoaDon = new JLabel("Mã Hóa Đơn:");
        lblMaHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMaHoaDon.setBounds(24, 74, 120, 35);
        panel_TimKiem.add(lblMaHoaDon);

        comboBoxMaHD = new JComboBox<>();
        comboBoxMaHD.setEditable(true);
        comboBoxMaHD.setBounds(150, 74, 280, 35);
        comboBoxMaHD.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel_TimKiem.add(comboBoxMaHD);

        JLabel lblMaKHSearch = new JLabel("Mã KH:");
        lblMaKHSearch.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMaKHSearch.setBounds(24, 119, 120, 35);
        panel_TimKiem.add(lblMaKHSearch);

        txtMaKHSearch = new JTextField();
        txtMaKHSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMaKHSearch.setBounds(150, 119, 280, 35);
        panel_TimKiem.add(txtMaKHSearch);

        JLabel lblTenKHSearch = new JLabel("Tên KH:");
        lblTenKHSearch.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTenKHSearch.setBounds(24, 164, 120, 35);
        panel_TimKiem.add(lblTenKHSearch);

        txtTenKHSearch = new JTextField();
        txtTenKHSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTenKHSearch.setBounds(150, 164, 280, 35);
        panel_TimKiem.add(txtTenKHSearch);

        JLabel lblSoDienThoaiSearch = new JLabel("Số ĐT:");
        lblSoDienThoaiSearch.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSoDienThoaiSearch.setBounds(24, 209, 120, 35);
        panel_TimKiem.add(lblSoDienThoaiSearch);

        txtSoDienThoaiSearch = new JTextField();
        txtSoDienThoaiSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSoDienThoaiSearch.setBounds(150, 209, 280, 35);
        panel_TimKiem.add(txtSoDienThoaiSearch);

        JButton btnTimHoaDon = new JButton("Tìm");
        btnTimHoaDon.setIcon(new ImageIcon("icon\\find.png"));
        btnTimHoaDon.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnTimHoaDon.setBounds(59, 257, 139, 35);
        panel_TimKiem.add(btnTimHoaDon);

        JButton btnTaiLai = new JButton("Tải Lại");
        btnTaiLai.setIcon(new ImageIcon("icon\\refresh.png"));
        btnTaiLai.setFont(new Font("Times New Roman", Font.BOLD, 20));
        btnTaiLai.setBounds(256, 257, 139, 35);
        panel_TimKiem.add(btnTaiLai);
        
        JLabel lblLoaiHoaDon = new JLabel("Loại Hóa Đơn:");
        lblLoaiHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblLoaiHoaDon.setBounds(24, 26, 120, 35);
        panel_TimKiem.add(lblLoaiHoaDon);
        
        String[] loaiHoaDonOptions = {"Hóa đơn bán thuốc", "Hóa đơn đặt thuốc"};
        comboBoxLoaiHoaDon = new JComboBox<>(loaiHoaDonOptions);
        comboBoxLoaiHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBoxLoaiHoaDon.setBounds(150, 26, 280, 35);
        panel_TimKiem.add(comboBoxLoaiHoaDon);

        // Panel Danh Sách Hóa Đơn
        JPanel panel_HoaDon = new JPanel();
        TitledBorder titledBorderLeft = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2), "Danh Sách Hóa Đơn");
        titledBorderLeft.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel_HoaDon.setBorder(titledBorderLeft);
        panel_HoaDon.setBackground(new Color(220, 128, 78));
        panel_HoaDon.setBounds(10, 10, 1045, 260);
        pnlBackGround.add(panel_HoaDon);
        panel_HoaDon.setLayout(null);

        JScrollPane scrollPaneHoaDon = new JScrollPane();
        scrollPaneHoaDon.setBounds(20, 25, 1004, 220);
        panel_HoaDon.add(scrollPaneHoaDon);

        tableHoaDon = new JTable();
        tableHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JTableHeader headerHoaDon = tableHoaDon.getTableHeader();
        headerHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableHoaDon.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Mã Hóa Đơn", "Ngày Lập", "Mã KH", "Tên KH", "Số Điện Thoại", "Tổng Tiền" }
        ) {
            boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableHoaDon.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableHoaDon.getColumnCount(); i++) {
            tableHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        scrollPaneHoaDon.setViewportView(tableHoaDon);

        // Panel Chi Tiết Hóa Đơn
        JPanel panel_ChiTietTra = new JPanel();
        TitledBorder titledBorderRight = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2), "Chi Tiết Hóa Đơn");
        titledBorderRight.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel_ChiTietTra.setBorder(titledBorderRight);
        panel_ChiTietTra.setBackground(new Color(220, 128, 78));
        panel_ChiTietTra.setBounds(10, 283, 1045, 220);
        pnlBackGround.add(panel_ChiTietTra);
        panel_ChiTietTra.setLayout(null);

        JScrollPane scrollPaneChiTiet = new JScrollPane();
        scrollPaneChiTiet.setBounds(20, 25, 1004, 184);
        panel_ChiTietTra.add(scrollPaneChiTiet);

        tableChiTiet = new JTable();
        tableChiTiet.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JTableHeader headerChiTiet = tableChiTiet.getTableHeader();
        headerChiTiet.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableChiTiet.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Mã Thuốc", "Tên Thuốc", "Số Lượng", "Đơn Giá", "Thành Tiền" }
        ) {
            boolean[] columnEditables = new boolean[] { false, false, false, false, false };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableChiTiet.setRowHeight(30);
        DefaultTableCellRenderer centerRendererCT = new DefaultTableCellRenderer();
        centerRendererCT.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableChiTiet.getColumnCount(); i++) {
            tableChiTiet.getColumnModel().getColumn(i).setCellRenderer(centerRendererCT);
        }
        scrollPaneChiTiet.setViewportView(tableChiTiet);

        // Panel Chi Tiết Phiếu Trả
        JPanel panel_ChiTietTraThuoc = new JPanel();
        TitledBorder titledBorderChiTietTra = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2), "Chi Tiết Phiếu Trả");
        titledBorderChiTietTra.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel_ChiTietTraThuoc.setBorder(titledBorderChiTietTra);
        panel_ChiTietTraThuoc.setBackground(new Color(220, 128, 78));
        panel_ChiTietTraThuoc.setBounds(10, 521, 1045, 220);
        pnlBackGround.add(panel_ChiTietTraThuoc);
        panel_ChiTietTraThuoc.setLayout(null);

        JScrollPane scrollPaneChiTietTra = new JScrollPane();
        scrollPaneChiTietTra.setBounds(20, 25, 1004, 184);
        panel_ChiTietTraThuoc.add(scrollPaneChiTietTra);

        tableChiTietTra = new JTable();
        tableChiTietTra.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JTableHeader headerChiTietTra = tableChiTietTra.getTableHeader();
        headerChiTietTra.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableChiTietTra.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "Mã Thuốc", "Tên Thuốc", "Số Lượng Trả", "Đơn Giá", "Thành Tiền" }
        ) {
            boolean[] columnEditables = new boolean[] { false, false, false, false, false };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        tableChiTietTra.setRowHeight(30);
        DefaultTableCellRenderer centerRendererChiTietTra = new DefaultTableCellRenderer();
        centerRendererChiTietTra.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableChiTietTra.getColumnCount(); i++) {
            tableChiTietTra.getColumnModel().getColumn(i).setCellRenderer(centerRendererChiTietTra);
        }
        scrollPaneChiTietTra.setViewportView(tableChiTietTra);

        // Panel Thông Tin Phiếu Trả
        JPanel panel_PhieuTra = new JPanel();
        panel_PhieuTra.setBackground(new Color(220, 128, 78));
        panel_PhieuTra.setBounds(1065, 328, 460, 360);
        TitledBorder titledBorder_PhieuTra = BorderFactory.createTitledBorder(new LineBorder(Color.WHITE, 2), "Thông Tin Phiếu Trả");
        titledBorder_PhieuTra.setTitleFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel_PhieuTra.setBorder(titledBorder_PhieuTra);
        panel_PhieuTra.setLayout(null);
        pnlBackGround.add(panel_PhieuTra);

        JLabel lblMaPhieuTra = new JLabel("Mã Phiếu Trả:");
        lblMaPhieuTra.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMaPhieuTra.setBounds(10, 24, 150, 45);
        panel_PhieuTra.add(lblMaPhieuTra);

        txtMaPhieuTra = new JTextField();
        txtMaPhieuTra.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtMaPhieuTra.setBounds(160, 24, 210, 43);
        txtMaPhieuTra.setEditable(false);
        panel_PhieuTra.add(txtMaPhieuTra);

        JLabel lblMaHoaDonPhieuTra = new JLabel("Mã Hóa Đơn:");
        lblMaHoaDonPhieuTra.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMaHoaDonPhieuTra.setBounds(10, 79, 150, 45);
        panel_PhieuTra.add(lblMaHoaDonPhieuTra);

        txtMaHoaDon = new JTextField();
        txtMaHoaDon.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtMaHoaDon.setBounds(160, 79, 264, 43);
        txtMaHoaDon.setEditable(false);
        panel_PhieuTra.add(txtMaHoaDon);

        JLabel lblMaKH = new JLabel("Mã KH:");
        lblMaKH.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMaKH.setBounds(10, 134, 150, 45);
        panel_PhieuTra.add(lblMaKH);

        txtMaKH = new JTextField();
        txtMaKH.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtMaKH.setBounds(160, 134, 264, 43);
        txtMaKH.setEditable(false);
        panel_PhieuTra.add(txtMaKH);

        JLabel lblTenKH = new JLabel("Tên KH:");
        lblTenKH.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTenKH.setBounds(10, 189, 150, 45);
        panel_PhieuTra.add(lblTenKH);

        txtTenKH = new JTextField();
        txtTenKH.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtTenKH.setBounds(160, 189, 264, 43);
        txtTenKH.setEditable(false);
        panel_PhieuTra.add(txtTenKH);

        JLabel lblNgayTra = new JLabel("Ngày Trả:");
        lblNgayTra.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNgayTra.setBounds(10, 244, 150, 45);
        panel_PhieuTra.add(lblNgayTra);

        txtNgayTra = new JDateChooser();
        txtNgayTra.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtNgayTra.setBounds(160, 244, 264, 43);
        txtNgayTra.setDateFormatString("dd/MM/yyyy");
        txtNgayTra.setDate(new Date());
        txtNgayTra.setEnabled(false);
        panel_PhieuTra.add(txtNgayTra);

        JLabel lblLyDoTra = new JLabel("Lý Do Trả:");
        lblLyDoTra.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLyDoTra.setBounds(10, 299, 150, 45);
        panel_PhieuTra.add(lblLyDoTra);

        String[] lyDoTraOptions = {
            "Sai thuốc",
            "Thuốc hết hạn sử dụng",
            "Khách hàng đổi ý",
            "Hỏng vỏ thuốc",
            "Khác"
        };
        comboLyDoTra = new JComboBox<>(lyDoTraOptions);
        comboLyDoTra.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        comboLyDoTra.setBounds(160, 299, 264, 43);
        panel_PhieuTra.add(comboLyDoTra);

        JButton btnTaiLaiPhieuTra = new JButton("");
        btnTaiLaiPhieuTra.setIcon(new ImageIcon("icon\\refresh.png"));
        btnTaiLaiPhieuTra.setBounds(380, 26, 44, 43);
        panel_PhieuTra.add(btnTaiLaiPhieuTra);

        // Nút Lưu Phiếu Trả
        btnLuuPhieuTra = new JButton("LƯU PHIẾU TRẢ");
        btnLuuPhieuTra.setBounds(1065, 701, 219, 40);
        pnlBackGround.add(btnLuuPhieuTra);
        btnLuuPhieuTra.setIcon(null);
        btnLuuPhieuTra.setForeground(new Color(255, 255, 255));
        btnLuuPhieuTra.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnLuuPhieuTra.setBackground(new Color(0, 128, 255));
        btnLuuPhieuTra.setOpaque(true);
        btnLuuPhieuTra.setContentAreaFilled(true);
        btnLuuPhieuTra.setBorderPainted(false);

        // Nút Hủy
        btnHuy = new JButton("HỦY");
        btnHuy.setBounds(1296, 701, 219, 40);
        pnlBackGround.add(btnHuy);
        btnHuy.setIcon(null);
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnHuy.setBackground(new Color(255, 0, 0));
        btnHuy.setOpaque(true);
        btnHuy.setContentAreaFilled(true);
        btnHuy.setBorderPainted(false);

        modelHoaDon = (DefaultTableModel) tableHoaDon.getModel();
        modelChiTiet = (DefaultTableModel) tableChiTiet.getModel();
        modelChiTietTra = (DefaultTableModel) tableChiTietTra.getModel();

        // Initialize data
        loadDataToTable();
        txtMaPhieuTra.setText(generateMaPhieuTra());
        goiYMaHoaDon();

        // Sự kiện Listeners
        
        // Thêm sự kiện cho comboBoxLoaiHoaDon
        comboBoxLoaiHoaDon.addActionListener(e -> loadDataToTable());
        
        btnTimHoaDon.addActionListener(e -> timHoaDon());
        btnTaiLai.addActionListener(e -> resetForm());
        btnTaiLaiPhieuTra.addActionListener(e -> resetForm());
        btnLuuPhieuTra.addActionListener(e -> {
            try {
                luuPhieuTra();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu phiếu trả: " + ex.getMessage());
            }
        });
        btnHuy.addActionListener(e -> resetForm());

        comboBoxMaHD.addActionListener(e -> {
            if (comboBoxMaHD.getSelectedItem() != null) {
                layThongTinHoaDon(comboBoxMaHD.getSelectedItem().toString());
            }
        });

        tableHoaDon.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("serial")
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int row = tableHoaDon.getSelectedRow();
                    if (row >= 0) {
                        String maHD = tableHoaDon.getValueAt(row, 0).toString();
                        layThongTinHoaDon(maHD);
                    }
                }
            }
        });

        tableChiTiet.addMouseListener(new MouseAdapter() {
            @SuppressWarnings("serial")
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = tableChiTiet.getSelectedRow();
                    if (row >= 0) {
                        nhapSoLuongTra(row);
                    }
                }
            }
        });
    }

    // Hàm tải dữ liệu hóa đơn vào bảng
    private void loadDataToTable() {
        modelHoaDon.setRowCount(0);
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();
        List<Object[]> data;
        if (loaiHoaDon.equals("Hóa đơn bán thuốc")) {
            data = traThuocDAO.loadDataToDSHoaDon();
        } else {
            data = traThuocDAO.loadDataToDSPhieuDat();
        }
        for (Object[] row : data) {
            modelHoaDon.addRow(row);
        }
        goiYMaHoaDon(); // Cập nhật gợi ý mã hóa đơn
    }

    // Hàm tìm kiếm hóa đơn
    private void timHoaDon() {
        String maHD = comboBoxMaHD.getSelectedItem() != null ? comboBoxMaHD.getSelectedItem().toString().trim() : "";
        String maKH = txtMaKHSearch.getText().trim();
        String tenKH = txtTenKHSearch.getText().trim();
        String soDienThoai = txtSoDienThoaiSearch.getText().trim();
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();

        if (maHD.isEmpty() && maKH.isEmpty() && tenKH.isEmpty() && soDienThoai.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một trường để tìm kiếm");
            return;
        }

        modelHoaDon.setRowCount(0);
        List<Object[]> data;
        if (loaiHoaDon.equals("Hóa đơn bán thuốc")) {
            data = traThuocDAO.timKiemHoaDon(maHD, maKH, tenKH, soDienThoai);
        } else {
            data = traThuocDAO.timKiemPhieuDat(maHD, maKH, tenKH, soDienThoai);
        }
        for (Object[] row : data) {
            modelHoaDon.addRow(row);
        }

        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn phù hợp");
        }
    }

    private String generateMaPhieuTra() {
        String lastMaPTT = traThuocDAO.getLastMaPhieuTra();
        if (lastMaPTT == null || lastMaPTT.isEmpty() || lastMaPTT.equals("PTT000")) {
            return "PTT001";
        }
        String numericPart = lastMaPTT.substring(3);
        int nextNumber = Integer.parseInt(numericPart) + 1;
        return "PTT" + String.format("%03d", nextNumber);
    }

    // Hàm gợi ý mã hóa đơn
    private void goiYMaHoaDon() {
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();
        List<String> maHDList;
        if (loaiHoaDon.equals("Hóa đơn bán thuốc")) {
            maHDList = traThuocDAO.getAllMaHoaDon();
        } else {
            maHDList = traThuocDAO.getAllMaPhieuDat();
        }
        comboBoxMaHD.setModel(new DefaultComboBoxModel<>(new Vector<>(maHDList)));
        comboBoxMaHD.setSelectedItem(null);

        JTextField textField = (JTextField) comboBoxMaHD.getEditor().getEditorComponent();
        Timer timer = new Timer(200, null);
        timer.setRepeats(false);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    return;
                }
                timer.restart();
            }
        });

        timer.addActionListener(e -> {
            String input = textField.getText();
            int caretPos = textField.getCaretPosition();
            Vector<String> filteredItems = new Vector<>();
            for (String item : maHDList) {
                if (item.toLowerCase().startsWith(input.toLowerCase())) {
                    filteredItems.add(item);
                }
            }
            comboBoxMaHD.setModel(new DefaultComboBoxModel<>(filteredItems));
            textField.setText(input);
            textField.setCaretPosition(caretPos);
            if (!filteredItems.isEmpty()) {
                comboBoxMaHD.showPopup();
            } else {
                comboBoxMaHD.hidePopup();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String selectedItem = (String) comboBoxMaHD.getSelectedItem();
                    if (selectedItem != null) {
                        comboBoxMaHD.setPopupVisible(false);
                        layThongTinHoaDon(selectedItem);
                    }
                }
            }
        });
    }

    // Hàm lấy thông tin hóa đơn theo mã
    private void layThongTinHoaDon(String maHD) {
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();
        Map<String, String> hoaDonInfo;
        if (loaiHoaDon.equals("Hóa đơn bán thuốc")) {
            hoaDonInfo = traThuocDAO.getThongTinHoaDon(maHD);
        } else {
            hoaDonInfo = traThuocDAO.getThongTinPhieuDat(maHD);
        }
        if (hoaDonInfo != null && !hoaDonInfo.isEmpty()) {
            txtMaHoaDon.setText(maHD);
            txtMaKH.setText(hoaDonInfo.get("maKH"));
            txtTenKH.setText(hoaDonInfo.get("tenKH"));
            loadChiTietHoaDon(maHD);
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn với mã này");
            txtMaHoaDon.setText("");
            txtMaKH.setText("");
            txtTenKH.setText("");
            modelChiTiet.setRowCount(0);
            modelChiTietTra.setRowCount(0);
        }
    }

    // Hàm tải chi tiết hóa đơn
    private void loadChiTietHoaDon(String maHD) {
        modelChiTiet.setRowCount(0);
        modelChiTietTra.setRowCount(0);
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();
        List<Object[]> data;
        if (loaiHoaDon.equals("Hóa đơn bán thuốc")) {
            data = chiTietTraThuocDAO.getChiTietPhieuBanThuoc(maHD);
        } else {
            data = chiTietTraThuocDAO.getChiTietPhieuDatHang(maHD);
        }
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể tải chi tiết hóa đơn. Vui lòng kiểm tra mã hóa đơn.");
            return;
        }
        for (Object[] row : data) {
            Object[] newRow = new Object[5];
            newRow[0] = row[0]; // maThuoc
            newRow[1] = row[1]; // tenThuoc
            newRow[2] = row[2]; // soLuong
            newRow[3] = row[3]; // donGiaBan
            newRow[4] = row[4]; // thanhTien
            modelChiTiet.addRow(newRow);
        }
        btnLuuPhieuTra.setEnabled(false);
    }
    
    
    private void nhapSoLuongTra(int row) {
        String maThuoc = tableChiTiet.getValueAt(row, 0).toString();
        String tenThuoc = tableChiTiet.getValueAt(row, 1).toString();
        
        // Lấy mã hóa đơn và loại hóa đơn
        String maHD = txtMaHoaDon.getText().trim();
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();
        boolean isBanThuoc = loaiHoaDon.equals("Hóa đơn bán thuốc");
        
        // Lấy số lượng mua ban đầu
        int soLuongBan = chiTietTraThuocDAO.getSoLuongMuaBanDau(maHD, maThuoc, isBanThuoc);
        if (soLuongBan == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin mua ban đầu cho thuốc này!");
            return;
        }
        
        // Lấy tổng số lượng đã trả
        int tongSoLuongDaTra = chiTietTraThuocDAO.getTongSoLuongDaTra(maHD, maThuoc);
        
        // Kiểm tra số lượng còn lại
        int soLuongConLai = soLuongBan - tongSoLuongDaTra;
        if (soLuongConLai <= 0) {
            JOptionPane.showMessageDialog(this, "Thuốc này đã được trả đủ số lượng, không thể trả thêm!");
            return;
        }
        
        // Lấy đơn giá từ tableChiTiet và loại bỏ dấu chấm phân cách hàng nghìn
        String donGiaStr = tableChiTiet.getValueAt(row, 3).toString().replace("đ", "").replace(",", "");
        double donGia;
        try {
            donGia = Double.parseDouble(donGiaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ: " + donGiaStr);
            return;
        }

        Dialog_ChiTietTraThuoc dialog = new Dialog_ChiTietTraThuoc(null, maThuoc, tenThuoc, soLuongConLai);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            int soLuongTra = dialog.getSoLuongTra();
            double thanhTien = soLuongTra * donGia;

            // Thêm dòng vào tableChiTietTra
            modelChiTietTra.addRow(new Object[] {
                maThuoc,
                tenThuoc,
                soLuongTra,
                String.format("%,.0fđ", donGia),
                String.format("%,.0fđ", thanhTien)
            });

            updateLuuPhieuTraButtonState();
        }
    }

    private void updateLuuPhieuTraButtonState() {
        boolean hasValidReturn = tableChiTietTra.getRowCount() > 0;
        btnLuuPhieuTra.setEnabled(hasValidReturn);
    }

    private void luuPhieuTra() throws ParseException {
        if (tableChiTiet.getRowCount() == 0 || txtMaHoaDon.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn và thêm thuốc trả");
            return;
        }

        if (maNV == null || maNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không thể lưu phiếu trả: Mã nhân viên không hợp lệ");
            return;
        }

        String maPhieuTra = txtMaPhieuTra.getText().trim();
        String maHoaDon = txtMaHoaDon.getText().trim();
        String maKH = txtMaKH.getText().trim();
        String ngayTra = new SimpleDateFormat("dd/MM/yyyy").format(txtNgayTra.getDate());
        String lyDoTra = comboLyDoTra.getSelectedItem().toString();
        String loaiHoaDon = (String) comboBoxLoaiHoaDon.getSelectedItem();

        boolean success = traThuocDAO.luuPhieuTra(maPhieuTra, maHoaDon, maNV, maKH, ngayTra, lyDoTra, loaiHoaDon.equals("Hóa đơn bán thuốc"));
        if (!success) {
            JOptionPane.showMessageDialog(this, "Lưu phiếu trả thất bại");
            return;
        }

        success = luuChiTietPhieuTra(maPhieuTra);
        if (success) {
            for (int i = 0; i < tableChiTietTra.getRowCount(); i++) {
                int soLuongTra = Integer.parseInt(tableChiTietTra.getValueAt(i, 2).toString());
                String maThuoc = tableChiTietTra.getValueAt(i, 0).toString();
                traThuocDAO.capNhatSoLuong(maThuoc, soLuongTra);
            }
            // Lưu phiếu thu chi
            success = taiChinhDAO.luuPhieuThu(maNV, ngayTra, "Tiền mặt", "Trả thuốc", maPhieuTra);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Lưu phiếu thu chi thất bại!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Lưu phiếu trả thành công");
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu chi tiết phiếu trả thất bại");
        }
    }

    private boolean luuChiTietPhieuTra(String maPhieuTra) {
        try {
            for (int i = 0; i < tableChiTietTra.getRowCount(); i++) {
                int soLuongTra = Integer.parseInt(tableChiTietTra.getValueAt(i, 2).toString());
                String maThuoc = tableChiTietTra.getValueAt(i, 0).toString();
                double donGia = Double.parseDouble(tableChiTietTra.getValueAt(i, 3).toString().replace("đ", "").replace(",", ""));
                if (!chiTietTraThuocDAO.luuChiTietPhieuTra(maPhieuTra, maThuoc, soLuongTra, donGia)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void resetForm() {
        txtMaPhieuTra.setText(generateMaPhieuTra());
        txtMaHoaDon.setText("");
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtNgayTra.setDate(new Date());
        comboLyDoTra.setSelectedIndex(0);
        modelChiTiet.setRowCount(0);
        modelChiTietTra.setRowCount(0);
        comboBoxMaHD.setSelectedItem(null);
        txtMaKHSearch.setText("");
        txtTenKHSearch.setText("");
        txtSoDienThoaiSearch.setText("");
        loadDataToTable();
    }
}