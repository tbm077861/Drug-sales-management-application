package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import DAO.*;
import connectDB.ConnectDB;

public class Frame_Thuoc_GiaoDich_NhapThuoc extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField txtMaPhieuNhap;
    private JTextField txtNgayNhap;
    private JComboBox<String> cboNhaCungCap;
    private JComboBox<String> cboHinhThucThanhToan;
    private JTextField txtTongTien;
    private JTextField txtTimKiemThuoc;
    private JComboBox<String> cboThuoc;
    private JTextField txtDonGiaNhap;
    private JSpinner spinnerSoLuong;
    private JTable tableThuocNhap;
    private DefaultTableModel modelThuocNhap;
    private NhapThuocDAO NhapThuocDAO = new NhapThuocDAO();
    private ChiTietNhapThuocDAO chiTietNhapThuocDAO = new ChiTietNhapThuocDAO();
    private ThuocDAO thuocDAO = new ThuocDAO();
    private NhaCungCapDAO nhaCungCapDAO = new NhaCungCapDAO();
    private TaiChinhDAO taiChinhDAO = new TaiChinhDAO();
    private String maNV;

    // Các thành phần hiển thị thông tin chi tiết thuốc trong panel_ChonThuoc
    private JLabel lblChiTietMaThuoc;
    private JTextField txtChiTietMaThuoc;
    private JLabel lblChiTietTenThuoc;
    private JTextField txtChiTietTenThuoc;
    private JLabel lblChiTietDonViTinh;
    private JTextField txtChiTietDonViTinh;
    private JLabel lblChiTietDonGiaNhap;
    private JTextField txtChiTietDonGiaNhap;
    private JLabel lblChiTietDonGiaBan;
    private JTextField txtChiTietDonGiaBan;
    private JLabel lblChiTietHanSuDung;
    private JTextField txtChiTietHanSuDung;
    private JLabel lblChiTietHamLuong;
    private JTextField txtChiTietHamLuong;
    private JLabel lblChiTietSoLuongTon;
    private JTextField txtChiTietSoLuongTon;

    // Panel Chọn Thuốc Nhập
    private JPanel panel_ChonThuoc;

    public Frame_Thuoc_GiaoDich_NhapThuoc(String maNV) {
        this.maNV = maNV;
        setLayout(null);
        setPreferredSize(new Dimension(1550, 755));
        initialize();
        loadData();
        setInitialValues();
    }

    private void initialize() {
        JPanel pnlBackground = new JPanel();
        pnlBackground.setBounds(0, 0, 1559, 771);
        pnlBackground.setBackground(new Color(254, 222, 192));
        add(pnlBackground);
        pnlBackground.setLayout(null);

        // Panel Thông Tin Phiếu Nhập
        JPanel panel_PhieuNhap = new JPanel();
        panel_PhieuNhap.setBackground(new Color(220, 128, 78));
        panel_PhieuNhap.setBounds(957, 10, 568, 400);
        panel_PhieuNhap.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.WHITE, 2), "Thông Tin Phiếu Nhập",
                0, 0, new Font("Segoe UI", Font.PLAIN, 12)));
        panel_PhieuNhap.setLayout(null);
        pnlBackground.add(panel_PhieuNhap);

        JLabel lblMaPhieuNhap = new JLabel("Mã Phiếu Nhập:");
        lblMaPhieuNhap.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblMaPhieuNhap.setBounds(10, 38, 166, 45);
        panel_PhieuNhap.add(lblMaPhieuNhap);

        txtMaPhieuNhap = new JTextField();
        txtMaPhieuNhap.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtMaPhieuNhap.setBounds(186, 38, 372, 43);
        txtMaPhieuNhap.setEditable(false);
        panel_PhieuNhap.add(txtMaPhieuNhap);

        JLabel lblNgayNhap = new JLabel("Ngày Nhập:");
        lblNgayNhap.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNgayNhap.setBounds(10, 93, 166, 45);
        panel_PhieuNhap.add(lblNgayNhap);

        txtNgayNhap = new JTextField();
        txtNgayNhap.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtNgayNhap.setBounds(186, 93, 372, 43);
        txtNgayNhap.setEditable(false);
        panel_PhieuNhap.add(txtNgayNhap);

        JLabel lblNhaCungCap = new JLabel("Nhà Cung Cấp:");
        lblNhaCungCap.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNhaCungCap.setBounds(10, 148, 166, 45);
        panel_PhieuNhap.add(lblNhaCungCap);

        cboNhaCungCap = new JComboBox<>();
        cboNhaCungCap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cboNhaCungCap.setBounds(186, 148, 372, 43);
        panel_PhieuNhap.add(cboNhaCungCap);

        JLabel lblHinhThucThanhToan = new JLabel("Hình Thức TT:");
        lblHinhThucThanhToan.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHinhThucThanhToan.setBounds(10, 203, 166, 45);
        panel_PhieuNhap.add(lblHinhThucThanhToan);

        cboHinhThucThanhToan = new JComboBox<>(new String[]{"Tiền mặt", "Chuyển khoản"});
        cboHinhThucThanhToan.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        cboHinhThucThanhToan.setBounds(186, 203, 372, 43);
        panel_PhieuNhap.add(cboHinhThucThanhToan);

        JLabel lblTongTien = new JLabel("Tổng Tiền:");
        lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTongTien.setBounds(10, 258, 166, 45);
        panel_PhieuNhap.add(lblTongTien);

        txtTongTien = new JTextField("0đ");
        txtTongTien.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        txtTongTien.setBounds(186, 258, 372, 43);
        txtTongTien.setEditable(false);
        panel_PhieuNhap.add(txtTongTien);

        JButton btnLuu = new JButton("LƯU PHIẾU NHẬP");
        btnLuu.setBounds(82, 338, 200, 51);
        panel_PhieuNhap.add(btnLuu);
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnLuu.setBackground(new Color(0, 128, 255));
        btnLuu.setOpaque(true);
        btnLuu.setBorderPainted(false);

        JButton btnHuy = new JButton("HỦY");
        btnHuy.setBounds(296, 338, 200, 51);
        panel_PhieuNhap.add(btnHuy);
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnHuy.setBackground(new Color(255, 0, 0));
        btnHuy.setOpaque(true);
        btnHuy.setBorderPainted(false);
        btnHuy.addActionListener(e -> resetForm());
        btnLuu.addActionListener(e -> luuPhieuNhap());

        // Panel Chọn Thuốc Nhập
        panel_ChonThuoc = new JPanel();
        panel_ChonThuoc.setBackground(new Color(220, 128, 78));
        panel_ChonThuoc.setBounds(10, 420, 1515, 321);
        panel_ChonThuoc.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.WHITE, 2), "Chọn Thuốc Nhập",
                0, 0, new Font("Segoe UI", Font.PLAIN, 12)));
        panel_ChonThuoc.setLayout(null);
        pnlBackground.add(panel_ChonThuoc);

        // Khu vực tìm kiếm và nhập liệu thuốc (bên trái)
        JPanel panelNhapThuoc = new JPanel();
        panelNhapThuoc.setBackground(new Color(220, 128, 78));
        panelNhapThuoc.setBounds(20, 20, 581, 280);
        panelNhapThuoc.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.WHITE, 1), "Nhập Thuốc",
                0, 0, new Font("Segoe UI", Font.PLAIN, 12)));
        panelNhapThuoc.setLayout(null);
        panel_ChonThuoc.add(panelNhapThuoc);

        JLabel lblTenThuoc = new JLabel("Tên Thuốc:");
        lblTenThuoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTenThuoc.setBounds(20, 30, 100, 30);
        panelNhapThuoc.add(lblTenThuoc);

        txtTimKiemThuoc = new JTextField();
        txtTimKiemThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTimKiemThuoc.setBounds(151, 30, 289, 30);
        panelNhapThuoc.add(txtTimKiemThuoc);

        JButton btnTimThuoc = new JButton("Tìm");
        btnTimThuoc.setIcon(new ImageIcon("icon\\find.png"));
        btnTimThuoc.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnTimThuoc.setBounds(450, 30, 121, 30);
        btnTimThuoc.addActionListener(e -> timThuoc());
        panelNhapThuoc.add(btnTimThuoc);

        JButton btnReset = new JButton("Reset");
        btnReset.setIcon(new ImageIcon("icon\\refresh.png"));
        btnReset.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnReset.setBounds(450, 80, 121, 30);
        btnReset.addActionListener(e -> resetThuoc());
        panelNhapThuoc.add(btnReset);

        JLabel lblThuoc = new JLabel("Thuốc:");
        lblThuoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblThuoc.setBounds(20, 80, 100, 30);
        panelNhapThuoc.add(lblThuoc);

        cboThuoc = new JComboBox<>();
        cboThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cboThuoc.setBounds(151, 80, 289, 30);
        cboThuoc.addActionListener(e -> updateDonGia());
        panelNhapThuoc.add(cboThuoc);

        JLabel lblDonGia = new JLabel("Đơn Giá Nhập:");
        lblDonGia.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblDonGia.setBounds(20, 129, 125, 30);
        panelNhapThuoc.add(lblDonGia);

        txtDonGiaNhap = new JTextField("0đ");
        txtDonGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtDonGiaNhap.setBounds(151, 129, 420, 30);
        txtDonGiaNhap.setEditable(false);
        panelNhapThuoc.add(txtDonGiaNhap);

        JLabel lblSoLuong = new JLabel("Số Lượng:");
        lblSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSoLuong.setBounds(20, 179, 100, 30);
        panelNhapThuoc.add(lblSoLuong);

        spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        spinnerSoLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        spinnerSoLuong.setBounds(151, 180, 289, 30);
        panelNhapThuoc.add(spinnerSoLuong);

        JButton btnThemThuoc = new JButton("Thêm");
        btnThemThuoc.setIcon(new ImageIcon("icon\\add.png"));
        btnThemThuoc.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnThemThuoc.setBounds(446, 181, 125, 30);
        btnThemThuoc.addActionListener(e -> themThuoc());
        panelNhapThuoc.add(btnThemThuoc);

        JButton btnThemThuocMoi = new JButton("Thêm Thuốc Mới");
        btnThemThuocMoi.setIcon(new ImageIcon("icon\\medicine.png"));
        btnThemThuocMoi.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnThemThuocMoi.setBounds(151, 237, 203, 30);
        btnThemThuocMoi.addActionListener(e -> themThuocMoi());
        panelNhapThuoc.add(btnThemThuocMoi);

        JButton btnNhapTuExcel = new JButton("Nhập từ Excel");
        btnNhapTuExcel.setIcon(new ImageIcon("icon\\excel.png"));
        btnNhapTuExcel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnNhapTuExcel.setBounds(364, 237, 203, 30);
        btnNhapTuExcel.addActionListener(e -> nhapTuExcel());
        panelNhapThuoc.add(btnNhapTuExcel);

        // Khu vực thông tin chi tiết thuốc (phần còn lại của panel)
        JPanel panelChiTietThuoc = new JPanel();
        panelChiTietThuoc.setBackground(new Color(220, 128, 78));
        panelChiTietThuoc.setBounds(609, 20, 879, 280);
        panelChiTietThuoc.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.WHITE, 1), "Thông Tin Chi Tiết Thuốc",
                0, 0, new Font("Segoe UI", Font.PLAIN, 12)));
        panelChiTietThuoc.setLayout(null);
        panel_ChonThuoc.add(panelChiTietThuoc);

        lblChiTietMaThuoc = new JLabel("Mã thuốc:");
        lblChiTietMaThuoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietMaThuoc.setBounds(20, 30, 131, 25);
        panelChiTietThuoc.add(lblChiTietMaThuoc);

        txtChiTietMaThuoc = new JTextField();
        txtChiTietMaThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietMaThuoc.setBounds(161, 30, 308, 25);
        txtChiTietMaThuoc.setEditable(false);
        panelChiTietThuoc.add(txtChiTietMaThuoc);

        lblChiTietTenThuoc = new JLabel("Tên thuốc:");
        lblChiTietTenThuoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietTenThuoc.setBounds(20, 79, 131, 25);
        panelChiTietThuoc.add(lblChiTietTenThuoc);

        txtChiTietTenThuoc = new JTextField();
        txtChiTietTenThuoc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietTenThuoc.setBounds(161, 79, 308, 25);
        txtChiTietTenThuoc.setEditable(false);
        panelChiTietThuoc.add(txtChiTietTenThuoc);

        lblChiTietDonViTinh = new JLabel("Đơn vị tính:");
        lblChiTietDonViTinh.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietDonViTinh.setBounds(20, 127, 131, 25);
        panelChiTietThuoc.add(lblChiTietDonViTinh);

        txtChiTietDonViTinh = new JTextField();
        txtChiTietDonViTinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietDonViTinh.setBounds(161, 127, 308, 25);
        txtChiTietDonViTinh.setEditable(false);
        panelChiTietThuoc.add(txtChiTietDonViTinh);

        lblChiTietDonGiaNhap = new JLabel("Đơn giá nhập:");
        lblChiTietDonGiaNhap.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietDonGiaNhap.setBounds(20, 178, 131, 25);
        panelChiTietThuoc.add(lblChiTietDonGiaNhap);

        txtChiTietDonGiaNhap = new JTextField();
        txtChiTietDonGiaNhap.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietDonGiaNhap.setBounds(161, 178, 308, 25);
        txtChiTietDonGiaNhap.setEditable(false);
        panelChiTietThuoc.add(txtChiTietDonGiaNhap);

        lblChiTietDonGiaBan = new JLabel("Đơn giá bán:");
        lblChiTietDonGiaBan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietDonGiaBan.setBounds(20, 228, 131, 25);
        panelChiTietThuoc.add(lblChiTietDonGiaBan);

        txtChiTietDonGiaBan = new JTextField();
        txtChiTietDonGiaBan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietDonGiaBan.setBounds(161, 228, 308, 25);
        txtChiTietDonGiaBan.setEditable(false);
        panelChiTietThuoc.add(txtChiTietDonGiaBan);

        lblChiTietHanSuDung = new JLabel("Hạn sử dụng:");
        lblChiTietHanSuDung.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietHanSuDung.setBounds(479, 30, 148, 25);
        panelChiTietThuoc.add(lblChiTietHanSuDung);

        txtChiTietHanSuDung = new JTextField();
        txtChiTietHanSuDung.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietHanSuDung.setBounds(637, 30, 200, 25);
        txtChiTietHanSuDung.setEditable(false);
        panelChiTietThuoc.add(txtChiTietHanSuDung);

        lblChiTietHamLuong = new JLabel("Hàm lượng:");
        lblChiTietHamLuong.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietHamLuong.setBounds(479, 79, 148, 25);
        panelChiTietThuoc.add(lblChiTietHamLuong);

        txtChiTietHamLuong = new JTextField();
        txtChiTietHamLuong.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietHamLuong.setBounds(637, 79, 200, 25);
        txtChiTietHamLuong.setEditable(false);
        panelChiTietThuoc.add(txtChiTietHamLuong);

        lblChiTietSoLuongTon = new JLabel("Số lượng tồn:");
        lblChiTietSoLuongTon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblChiTietSoLuongTon.setBounds(479, 127, 148, 25);
        panelChiTietThuoc.add(lblChiTietSoLuongTon);

        txtChiTietSoLuongTon = new JTextField();
        txtChiTietSoLuongTon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtChiTietSoLuongTon.setBounds(637, 127, 200, 25);
        txtChiTietSoLuongTon.setEditable(false);
        panelChiTietThuoc.add(txtChiTietSoLuongTon);

        // Panel Danh Sách Thuốc Nhập
        JPanel panel_DanhSachThuoc = new JPanel();
        panel_DanhSachThuoc.setBackground(new Color(220, 128, 78));
        panel_DanhSachThuoc.setBounds(10, 10, 933, 400);
        panel_DanhSachThuoc.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(Color.WHITE, 2), "Danh Sách Thuốc Nhập",
                0, 0, new Font("Segoe UI", Font.PLAIN, 12)));
        pnlBackground.add(panel_DanhSachThuoc);
        panel_DanhSachThuoc.setLayout(null);

        JScrollPane scrollPaneThuocNhap = new JScrollPane();
        scrollPaneThuocNhap.setBounds(20, 25, 900, 366);
        panel_DanhSachThuoc.add(scrollPaneThuocNhap);

        tableThuocNhap = new JTable();
        tableThuocNhap.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JTableHeader header = tableThuocNhap.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        modelThuocNhap = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Đơn Giá Nhập", "Thành Tiền"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableThuocNhap.setModel(modelThuocNhap);
        tableThuocNhap.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableThuocNhap.getColumnCount(); i++) {
            tableThuocNhap.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        scrollPaneThuocNhap.setViewportView(tableThuocNhap);
    }

    private void setInitialValues() {
        txtMaPhieuNhap.setText(NhapThuocDAO.generateMaPhieuNhap());
        txtNgayNhap.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtTongTien.setText("0đ");
        clearChiTietThuoc();
    }

    private void loadData() {
        List<Object[]> thuocList = thuocDAO.getAllThuoc();
        Vector<String> thuocItems = new Vector<>();
        for (Object[] thuoc : thuocList) {
            thuocItems.add(thuoc[0] + " - " + thuoc[1]);
        }
        cboThuoc.setModel(new DefaultComboBoxModel<>(thuocItems));

        List<Object[]> nccList = nhaCungCapDAO.getAllNhaCungCap();
        Vector<String> nccItems = new Vector<>();
        for (Object[] ncc : nccList) {
            nccItems.add(ncc[0] + " - " + ncc[1]);
        }
        cboNhaCungCap.setModel(new DefaultComboBoxModel<>(nccItems));
    }

    private void timThuoc() {
        String keyword = txtTimKiemThuoc.getText().trim();
        // Chỉ tìm kiếm theo tenThuoc, không tìm theo maThuoc hay soLuongTon
        List<Object[]> results = thuocDAO.timKiemThuoc("", keyword, "");
        Vector<String> thuocItems = new Vector<>();

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thuốc với tên: " + keyword);
            thuocItems.add("Không tìm thấy thuốc");
            cboThuoc.setModel(new DefaultComboBoxModel<>(thuocItems));
            txtDonGiaNhap.setText("0đ");
            clearChiTietThuoc();
            return;
        }

        thuocItems.clear();
        for (Object[] thuoc : results) {
            String maThuoc = (String) thuoc[0];
            String tenThuoc = (String) thuoc[1];
            thuocItems.add(maThuoc + " - " + tenThuoc);
            if (thuocItems.size() == 1) {
                txtChiTietMaThuoc.setText((String) thuoc[0]);
                txtChiTietTenThuoc.setText((String) thuoc[1]);
                txtChiTietDonViTinh.setText((String) thuoc[2]);
                txtChiTietDonGiaNhap.setText((String) thuoc[3]);
                txtChiTietDonGiaBan.setText((String) thuoc[4]);
                txtChiTietHanSuDung.setText((String) thuoc[5]);
                txtChiTietHamLuong.setText((String) thuoc[6]);
                txtChiTietSoLuongTon.setText(String.valueOf(thuoc[7] != null ? thuoc[7] : "0"));
            }
        }
        cboThuoc.setModel(new DefaultComboBoxModel<>(thuocItems));
        updateDonGia();
    }

    private void updateDonGia() {
        String selectedThuoc = (String) cboThuoc.getSelectedItem();
        if (selectedThuoc != null && !selectedThuoc.equals("Không tìm thấy thuốc")) {
            String maThuoc = selectedThuoc.split(" - ")[0];
            double donGia = thuocDAO.getDonGiaNhap(maThuoc);
            txtDonGiaNhap.setText(String.format("%,.0fđ", donGia));

            // Cập nhật thông tin chi tiết thuốc khi chọn từ combobox
            List<Object[]> results = thuocDAO.timKiemThuoc(maThuoc, "", "");
            if (!results.isEmpty()) {
                Object[] thuoc = results.get(0);
                txtChiTietMaThuoc.setText((String) thuoc[0]);
                txtChiTietTenThuoc.setText((String) thuoc[1]);
                txtChiTietDonViTinh.setText((String) thuoc[2]);
                txtChiTietDonGiaNhap.setText((String) thuoc[3]);
                txtChiTietDonGiaBan.setText((String) thuoc[4]);
                txtChiTietHanSuDung.setText((String) thuoc[5]);
                txtChiTietHamLuong.setText((String) thuoc[6]);
                txtChiTietSoLuongTon.setText(String.valueOf(thuoc[7] != null ? thuoc[7] : "0"));
            }
        } else {
            txtDonGiaNhap.setText("0đ");
            clearChiTietThuoc();
        }
    }

    private void themThuoc() {
        String selectedThuoc = (String) cboThuoc.getSelectedItem();
        if (selectedThuoc == null || selectedThuoc.equals("Không tìm thấy thuốc")) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc hoặc thêm thuốc mới!");
            return;
        }
        String maThuoc = selectedThuoc.split(" - ")[0];
        String tenThuoc = selectedThuoc.split(" - ")[1];
        int soLuong = (int) spinnerSoLuong.getValue();
        double donGia = thuocDAO.getDonGiaNhap(maThuoc);
        double thanhTien = soLuong * donGia;

        for (int i = 0; i < modelThuocNhap.getRowCount(); i++) {
            if (modelThuocNhap.getValueAt(i, 0).equals(maThuoc)) {
                int soLuongCu = Integer.parseInt(modelThuocNhap.getValueAt(i, 2).toString());
                int soLuongMoi = soLuongCu + soLuong;
                modelThuocNhap.setValueAt(soLuongMoi, i, 2);
                modelThuocNhap.setValueAt(String.format("%,.0fđ", soLuongMoi * donGia), i, 4);
                updateTongTien();
                return;
            }
        }

        modelThuocNhap.addRow(new Object[]{
                maThuoc, tenThuoc, soLuong,
                String.format("%,.0fđ", donGia),
                String.format("%,.0fđ", thanhTien)
        });
        updateTongTien();
    }

    private void themThuocMoi() {
        String lastMaThuoc = thuocDAO.getLastMaThuoc();
        String newMaThuoc = generateNextMaThuoc(lastMaThuoc);
        Dialog_ChiTietThuoc dialog = new Dialog_ChiTietThuoc(null, false);
        dialog.hienThiMaThuoc(newMaThuoc);
        dialog.setVisible(true);
        // Sau khi dialog đóng, làm mới danh sách thuốc
        loadData();
    }

    private String generateNextMaThuoc(String lastMaThuoc) {
        if (lastMaThuoc == null || lastMaThuoc.isEmpty() || !lastMaThuoc.startsWith("T")) {
            return "T001";
        }
        String numPart = lastMaThuoc.substring(1);
        int num = Integer.parseInt(numPart) + 1;
        return String.format("T%03d", num);
    }

    private void clearChiTietThuoc() {
        txtChiTietMaThuoc.setText("");
        txtChiTietTenThuoc.setText("");
        txtChiTietDonViTinh.setText("");
        txtChiTietDonGiaNhap.setText("");
        txtChiTietDonGiaBan.setText("");
        txtChiTietHanSuDung.setText("");
        txtChiTietHamLuong.setText("");
        txtChiTietSoLuongTon.setText("");
    }

    private void resetThuoc() {
        loadData();
        clearChiTietThuoc();
        txtTimKiemThuoc.setText("");
        txtDonGiaNhap.setText("0đ");
    }

    private void updateTongTien() {
        double tongTien = 0;
        for (int i = 0; i < modelThuocNhap.getRowCount(); i++) {
            String thanhTienStr = modelThuocNhap.getValueAt(i, 4).toString().replace("đ", "").replace(",", "");
            tongTien += Double.parseDouble(thanhTienStr);
        }
        txtTongTien.setText(String.format("%,.0fđ", tongTien));
    }

  
    private void luuPhieuNhap() {
        if (modelThuocNhap.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Danh sách thuốc nhập trống!");
            return;
        }
        String maPhieuNhap = txtMaPhieuNhap.getText().trim();
        String ngayNhap = txtNgayNhap.getText().trim();
        String maNCC = cboNhaCungCap.getSelectedItem() != null ? cboNhaCungCap.getSelectedItem().toString().split(" - ")[0] : "";
        String hinhThucThanhToan = cboHinhThucThanhToan.getSelectedItem().toString();

        if (maPhieuNhap.isEmpty() || ngayNhap.isEmpty() || maNCC.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        boolean success = NhapThuocDAO.luuPhieuNhap(maPhieuNhap, maNV, maNCC, ngayNhap, hinhThucThanhToan, 0);
        if (!success) {
            JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thất bại!");
            return;
        }

        success = chiTietNhapThuocDAO.luuChiTietPhieuNhap(maPhieuNhap, modelThuocNhap);
        if (success) {
            for (int i = 0; i < modelThuocNhap.getRowCount(); i++) {
                String maThuoc = modelThuocNhap.getValueAt(i, 0).toString();
                int soLuong = Integer.parseInt(modelThuocNhap.getValueAt(i, 2).toString());
                NhapThuocDAO.capNhatSoLuongTon(maThuoc, soLuong);
            }
            // Lưu phiếu thu chi
            success = taiChinhDAO.luuPhieuThu(maNV, ngayNhap, hinhThucThanhToan, "Nhập thuốc", maPhieuNhap);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Lưu phiếu thu chi thất bại!");
                return;
            }
            JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thành công!");
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lưu chi tiết phiếu nhập thất bại!");
        }
    }

    private void resetForm() {
        setInitialValues();
        cboNhaCungCap.setSelectedIndex(0);
        cboHinhThucThanhToan.setSelectedIndex(0);
        modelThuocNhap.setRowCount(0);
        txtTimKiemThuoc.setText("");
        loadData();
    }

    private void nhapTuExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file Excel");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files", "xlsx", "xls"));
        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = fileChooser.getSelectedFile();
        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                JOptionPane.showMessageDialog(this, "File Excel không có sheet dữ liệu!");
                return;
            }

            // Đọc mã nhà cung cấp từ dòng đầu tiên
            Row nccRow = sheet.getRow(0);
            if (nccRow == null || nccRow.getCell(0) == null) {
                JOptionPane.showMessageDialog(this, "Dòng đầu tiên phải chứa mã nhà cung cấp!");
                return;
            }
            String maNCC = getCellValueAsString(nccRow.getCell(1)).trim();
            if (maNCC.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã nhà cung cấp không được để trống!");
                return;
            }

            // Tìm và chọn nhà cung cấp trong cboNhaCungCap
            boolean nccFound = false;
            for (int i = 0; i < cboNhaCungCap.getItemCount(); i++) {
                String item = cboNhaCungCap.getItemAt(i);
                String maNCCItem = item.split(" - ")[0];
                if (maNCCItem.equals(maNCC)) {
                    cboNhaCungCap.setSelectedIndex(i);
                    nccFound = true;
                    break;
                }
            }
            if (!nccFound) {
                JOptionPane.showMessageDialog(this, "Nhà cung cấp với mã '" + maNCC + "' không tồn tại trong hệ thống!");
                return;
            }

            // Kiểm tra header (dòng thứ 2, index 1)
            Row headerRow = sheet.getRow(1);
            if (headerRow == null || !isValidHeader(headerRow)) {
                JOptionPane.showMessageDialog(this, "File Excel không đúng định dạng! Cần các cột: Mã Thuốc, Tên Thuốc, Số Lượng, Đơn Giá Nhập");
                return;
            }

            // Đọc dữ liệu từ dòng thứ 3 trở đi (index 2)
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String maThuoc = getCellValueAsString(row.getCell(0));
                    String tenThuoc = getCellValueAsString(row.getCell(1));
                    Cell soLuongCell = row.getCell(2);
                    Cell donGiaCell = row.getCell(3);

                    // Kiểm tra dữ liệu hợp lệ
                    if (maThuoc == null || maThuoc.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Mã Thuốc không được để trống!");
                        continue;
                    }
                    if (tenThuoc == null || tenThuoc.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Tên Thuốc không được để trống!");
                        continue;
                    }
                    if (soLuongCell == null || soLuongCell.getCellType() != CellType.NUMERIC) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Số Lượng phải là số!");
                        continue;
                    }
                    if (donGiaCell == null || donGiaCell.getCellType() != CellType.NUMERIC) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Đơn Giá Nhập phải là số!");
                        continue;
                    }

                    int soLuong = (int) soLuongCell.getNumericCellValue();
                    double donGia = donGiaCell.getNumericCellValue();

                    if (soLuong <= 0) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Số Lượng phải là số dương!");
                        continue;
                    }
                    if (donGia <= 0) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Đơn Giá Nhập phải là số dương!");
                        continue;
                    }

                    // Kiểm tra thuốc có tồn tại trong cơ sở dữ liệu
                    List<Object[]> thuocList = thuocDAO.timKiemThuoc(maThuoc, tenThuoc, "");
                    if (thuocList.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Dòng " + (i + 1) + ": Thuốc " + maThuoc + " - " + tenThuoc + " không tồn tại trong hệ thống!");
                        continue;
                    }

                    // Thêm hoặc cập nhật vào bảng
                    boolean found = false;
                    for (int j = 0; j < modelThuocNhap.getRowCount(); j++) {
                        if (modelThuocNhap.getValueAt(j, 0).equals(maThuoc)) {
                            int soLuongCu = Integer.parseInt(modelThuocNhap.getValueAt(j, 2).toString());
                            int soLuongMoi = soLuongCu + soLuong;
                            modelThuocNhap.setValueAt(soLuongMoi, j, 2);
                            modelThuocNhap.setValueAt(String.format("%,.0fđ", soLuongMoi * donGia), j, 4);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        double thanhTien = soLuong * donGia;
                        modelThuocNhap.addRow(new Object[]{
                                maThuoc, tenThuoc, soLuong,
                                String.format("%,.0fđ", donGia),
                                String.format("%,.0fđ", thanhTien)
                        });
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Lỗi khi đọc dòng " + (i + 1) + ": " + e.getMessage());
                    continue;
                }
            }

            updateTongTien();
            JOptionPane.showMessageDialog(this, "Đã nhập dữ liệu từ file Excel thành công!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi đọc file Excel: " + e.getMessage());
        }
    }

    private boolean isValidHeader(Row headerRow) {
        String[] expectedHeaders = {"Mã Thuốc", "Tên Thuốc", "Số Lượng", "Đơn Giá Nhập"};
        if (headerRow.getPhysicalNumberOfCells() < expectedHeaders.length) {
            return false;
        }
        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !getCellValueAsString(cell).trim().equalsIgnoreCase(expectedHeaders[i])) {
                return false;
            }
        }
        return true;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}