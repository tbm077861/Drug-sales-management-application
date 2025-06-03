package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

import DAO.HoaDonBanThuocDAO;

public class Frame_TaiChinh_DoanhThu extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tableHoaDon;
    private DefaultTableModel modelHoaDon;
    private JComboBox<String> cboLoaiThongKe;
    private JComboBox<String> cboNam;
    private JComboBox<String> cboTuNam;
    private JComboBox<String> cboDenNam;
    private JComboBox<String> cboThang;
    private JComboBox<String> cboTuan;
    private JTextField txtTongHoaDon;
    private JTextField txtTongDoanhThu;
    private HoaDonBanThuocDAO hoaDonDAO = new HoaDonBanThuocDAO();
    private ChartPanel chartPanelSoLuong;
    private ChartPanel chartPanelDoanhThu;
    private DecimalFormat df;

    // Define colors from Frame_GiaoDich_NhapThuoc
    private final Color MAIN_COLOR = new Color(254, 222, 192);
    private final Color HEADER_COLOR = new Color(251, 203, 150);
    private final Color BUTTON_COLOR = new Color(249, 187, 118);
    private final Color BUTTON_TEXT_COLOR = new Color(121, 70, 13);
    private final Color TEXT_COLOR = new Color(100, 60, 20);
    private final Color PANEL_BORDER_COLOR = new Color(222, 184, 135);
    private final Color TABLE_HEADER_COLOR = new Color(251, 203, 150);
    private final Color SELECTED_COLOR = new Color(255, 239, 213);

    public Frame_TaiChinh_DoanhThu() {
        // Set up DecimalFormat for Vietnamese locale
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("vi", "VN"));
        symbols.setGroupingSeparator(',');
        symbols.setDecimalSeparator('.');
        df = new DecimalFormat("#,### VNĐ", symbols);

        setBackground(MAIN_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(1550, 878));

        // Title Panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBounds(0, 0, 1540, 60);
        pnlTitle.setBackground(HEADER_COLOR);
        add(pnlTitle);
        pnlTitle.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("THỐNG KÊ HÓA ĐƠN");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_COLOR);
        pnlTitle.add(lblTitle, BorderLayout.CENTER);

        // Filter Panel
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBounds(10, 70, 509, 342);
        pnlFilter.setBackground(MAIN_COLOR);
        pnlFilter.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Điều kiện thống kê", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlFilter);
        pnlFilter.setLayout(null);

        // Filter Row 1
        JLabel lblLoaiThongKe = new JLabel("Loại thống kê:");
        lblLoaiThongKe.setForeground(TEXT_COLOR);
        lblLoaiThongKe.setFont(new Font("Arial", Font.PLAIN, 16));
        lblLoaiThongKe.setBounds(10, 30, 150, 30);
        pnlFilter.add(lblLoaiThongKe);

        cboLoaiThongKe = new JComboBox<>(new String[] {"Tuần", "Tháng", "Quý", "Năm"});
        cboLoaiThongKe.setBackground(Color.WHITE);
        cboLoaiThongKe.setForeground(TEXT_COLOR);
        cboLoaiThongKe.setFont(new Font("Arial", Font.PLAIN, 16));
        cboLoaiThongKe.setBounds(119, 30, 370, 30);
        pnlFilter.add(cboLoaiThongKe);

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setForeground(TEXT_COLOR);
        lblNam.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNam.setBounds(10, 70, 150, 30);
        pnlFilter.add(lblNam);

        cboNam = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            cboNam.addItem(String.valueOf(i));
        }
        cboNam.setSelectedItem(String.valueOf(currentYear));
        cboNam.setBackground(Color.WHITE);
        cboNam.setForeground(TEXT_COLOR);
        cboNam.setFont(new Font("Arial", Font.PLAIN, 16));
        cboNam.setBounds(119, 70, 370, 30);
        pnlFilter.add(cboNam);

        JLabel lblTuNam = new JLabel("Từ năm:");
        lblTuNam.setForeground(TEXT_COLOR);
        lblTuNam.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTuNam.setBounds(10, 70, 150, 30);
        pnlFilter.add(lblTuNam);

        cboTuNam = new JComboBox<>();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            cboTuNam.addItem(String.valueOf(i));
        }
        cboTuNam.setSelectedItem(String.valueOf(currentYear - 5));
        cboTuNam.setBackground(Color.WHITE);
        cboTuNam.setForeground(TEXT_COLOR);
        cboTuNam.setFont(new Font("Arial", Font.PLAIN, 16));
        cboTuNam.setBounds(119, 70, 185, 30);
        pnlFilter.add(cboTuNam);

        JLabel lblDenNam = new JLabel("Đến năm:");
        lblDenNam.setForeground(TEXT_COLOR);
        lblDenNam.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDenNam.setBounds(304, 70, 80, 30);
        pnlFilter.add(lblDenNam);

        cboDenNam = new JComboBox<>();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            cboDenNam.addItem(String.valueOf(i));
        }
        cboDenNam.setSelectedItem(String.valueOf(currentYear));
        cboDenNam.setBackground(Color.WHITE);
        cboDenNam.setForeground(TEXT_COLOR);
        cboDenNam.setFont(new Font("Arial", Font.PLAIN, 16));
        cboDenNam.setBounds(384, 70, 105, 30);
        pnlFilter.add(cboDenNam);

        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setForeground(TEXT_COLOR);
        lblThang.setFont(new Font("Arial", Font.PLAIN, 16));
        lblThang.setBounds(10, 110, 150, 30);
        pnlFilter.add(lblThang);

        cboThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            cboThang.addItem(String.valueOf(i));
        }
        cboThang.setSelectedItem(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
        cboThang.setBackground(Color.WHITE);
        cboThang.setForeground(TEXT_COLOR);
        cboThang.setFont(new Font("Arial", Font.PLAIN, 16));
        cboThang.setBounds(119, 110, 370, 30);
        pnlFilter.add(cboThang);

        JLabel lblTuan = new JLabel("Tuần:");
        lblTuan.setForeground(TEXT_COLOR);
        lblTuan.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTuan.setBounds(10, 150, 150, 30);
        pnlFilter.add(lblTuan);

        cboTuan = new JComboBox<>(new String[] {"1", "2", "3", "4"}); // Bỏ tuần 5
        cboTuan.setBackground(Color.WHITE);
        cboTuan.setForeground(TEXT_COLOR);
        cboTuan.setFont(new Font("Arial", Font.PLAIN, 16));
        cboTuan.setBounds(119, 150, 370, 30);
        pnlFilter.add(cboTuan);

        // Buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.setBackground(MAIN_COLOR);
        pnlButtons.setBounds(90, 270, 331, 40);
        pnlFilter.add(pnlButtons);
        pnlButtons.setLayout(null);

        JButton btnThongKe = new JButton("Thống kê");
        btnThongKe.setBackground(new Color(254, 152, 65));
        btnThongKe.setForeground(new Color(50, 20, 0));
        btnThongKe.setFont(new Font("Arial", Font.BOLD, 16));
        btnThongKe.setBounds(0, 0, 153, 40);
        pnlButtons.add(btnThongKe);

        JButton btnXuatBaoCao = new JButton("Xuất báo cáo");
        btnXuatBaoCao.setBackground(BUTTON_COLOR);
        btnXuatBaoCao.setForeground(BUTTON_TEXT_COLOR);
        btnXuatBaoCao.setFont(new Font("Arial", Font.BOLD, 16));
        btnXuatBaoCao.setBounds(163, 0, 153, 40);
        pnlButtons.add(btnXuatBaoCao);

        // Table Panel
        JPanel pnlTable = new JPanel();
        pnlTable.setBounds(529, 70, 1011, 342);
        pnlTable.setBackground(MAIN_COLOR);
        pnlTable.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Bảng thống kê hóa đơn", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlTable);
        pnlTable.setLayout(null);

        modelHoaDon = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Mã hóa đơn", "Ngày lập", "Mã khách hàng", "Tổng tiền"}
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableHoaDon = new JTable(modelHoaDon);
        tableHoaDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableHoaDon.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        tableHoaDon.getTableHeader().setForeground(TEXT_COLOR);
        tableHoaDon.setBackground(Color.WHITE);
        tableHoaDon.setForeground(TEXT_COLOR);
        tableHoaDon.setSelectionBackground(SELECTED_COLOR);
        tableHoaDon.setRowHeight(30);
        tableHoaDon.setFont(new Font("Arial", Font.PLAIN, 14));
        tableHoaDon.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableHoaDon.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableHoaDon.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableHoaDon.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableHoaDon);
        scrollPane.setBounds(5, 21, 996, 311);
        pnlTable.add(scrollPane);

        // Summary Panel
        JPanel pnlThongTin = new JPanel();
        pnlThongTin.setBounds(10, 422, 509, 156);
        pnlThongTin.setBackground(MAIN_COLOR);
        pnlThongTin.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Thông tin tổng hợp", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlThongTin);
        pnlThongTin.setLayout(null);

        JLabel lblTongHoaDon = new JLabel("Tổng số hóa đơn:");
        lblTongHoaDon.setForeground(TEXT_COLOR);
        lblTongHoaDon.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongHoaDon.setBounds(10, 30, 300, 30);
        pnlThongTin.add(lblTongHoaDon);

        txtTongHoaDon = new JTextField("0");
        txtTongHoaDon.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongHoaDon.setEditable(false);
        txtTongHoaDon.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongHoaDon.setForeground(new Color(165, 42, 42));
        txtTongHoaDon.setBackground(new Color(253, 245, 230));
        txtTongHoaDon.setBounds(171, 30, 328, 30);
        pnlThongTin.add(txtTongHoaDon);

        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu:");
        lblTongDoanhThu.setForeground(TEXT_COLOR);
        lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongDoanhThu.setBounds(10, 70, 300, 30);
        pnlThongTin.add(lblTongDoanhThu);

        txtTongDoanhThu = new JTextField("0 VNĐ");
        txtTongDoanhThu.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongDoanhThu.setEditable(false);
        txtTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongDoanhThu.setForeground(new Color(165, 42, 42));
        txtTongDoanhThu.setBackground(new Color(253, 245, 230));
        txtTongDoanhThu.setBounds(171, 70, 328, 30);
        pnlThongTin.add(txtTongDoanhThu);

        // Chart Panels for số lượng hóa đơn và doanh thu
        chartPanelSoLuong = new ChartPanel(null);
        chartPanelSoLuong.setBounds(539, 422, 500, 300);
        chartPanelSoLuong.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Biểu đồ số lượng hóa đơn", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12), TEXT_COLOR));
        add(chartPanelSoLuong);

        chartPanelDoanhThu = new ChartPanel(null);
        chartPanelDoanhThu.setBounds(1055, 422, 500, 300);
        chartPanelDoanhThu.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Biểu đồ doanh thu", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12), TEXT_COLOR));
        add(chartPanelDoanhThu);

        // Event for cboLoaiThongKe
        cboLoaiThongKe.addActionListener(e -> {
            String selectedOption = cboLoaiThongKe.getSelectedItem().toString();
            switch (selectedOption) {
                case "Tuần":
                    lblNam.setVisible(true);
                    cboNam.setVisible(true);
                    lblTuNam.setVisible(false);
                    cboTuNam.setVisible(false);
                    lblDenNam.setVisible(false);
                    cboDenNam.setVisible(false);
                    lblThang.setVisible(true);
                    cboThang.setVisible(true);
                    lblTuan.setVisible(true);
                    cboTuan.setVisible(true);
                    modelHoaDon.setColumnIdentifiers(new String[] {"Mã hóa đơn", "Ngày lập", "Mã khách hàng", "Tổng tiền"});
                    break;
                case "Tháng":
                case "Quý":
                    lblNam.setVisible(true);
                    cboNam.setVisible(true);
                    lblTuNam.setVisible(false);
                    cboTuNam.setVisible(false);
                    lblDenNam.setVisible(false);
                    cboDenNam.setVisible(false);
                    lblThang.setVisible(false);
                    cboThang.setVisible(false);
                    lblTuan.setVisible(false);
                    cboTuan.setVisible(false);
                    modelHoaDon.setColumnIdentifiers(new String[] {"Tháng/Quý", "Số hóa đơn", "Tổng doanh thu"});
                    break;
                case "Năm":
                    lblNam.setVisible(false);
                    cboNam.setVisible(false);
                    lblTuNam.setVisible(true);
                    cboTuNam.setVisible(true);
                    lblDenNam.setVisible(true);
                    cboDenNam.setVisible(true);
                    lblThang.setVisible(false);
                    cboThang.setVisible(false);
                    lblTuan.setVisible(false);
                    cboTuan.setVisible(false);
                    modelHoaDon.setColumnIdentifiers(new String[] {"Năm", "Số hóa đơn", "Tổng doanh thu"});
                    break;
            }
            pnlFilter.revalidate();
            pnlFilter.repaint();
        });

        // Initialize default state
        cboLoaiThongKe.setSelectedIndex(0); // Set default to "Tuần"

        // Button events
        btnThongKe.addActionListener(e -> thongKeHoaDon());
        btnXuatBaoCao.addActionListener(e -> xuatExcel());

        // Load initial charts
        updateCharts();
    }

    private void thongKeHoaDon() {
        modelHoaDon.setRowCount(0);
        String loaiThongKe = cboLoaiThongKe.getSelectedItem().toString();
        List<Object[]> data = null;
        int tongHoaDon = 0;
        double tongDoanhThu = 0;

        try {
            switch (loaiThongKe) {
                case "Tuần":
                    int namTuan = Integer.parseInt(cboNam.getSelectedItem().toString());
                    int thangTuan = Integer.parseInt(cboThang.getSelectedItem().toString());
                    int tuan = Integer.parseInt(cboTuan.getSelectedItem().toString());
                    modelHoaDon.setColumnIdentifiers(new String[] {"Mã hóa đơn", "Ngày lập", "Mã khách hàng", "Tổng tiền"});
                    data = hoaDonDAO.thongKeTheoTuan(namTuan, thangTuan, tuan);
                    break;
                case "Tháng":
                    int namThang = Integer.parseInt(cboNam.getSelectedItem().toString());
                    modelHoaDon.setColumnIdentifiers(new String[] {"Tháng", "Số hóa đơn", "Tổng doanh thu"});
                    data = hoaDonDAO.thongKeTheoThang(namThang);
                    break;
                case "Quý":
                    int namQuy = Integer.parseInt(cboNam.getSelectedItem().toString());
                    modelHoaDon.setColumnIdentifiers(new String[] {"Quý", "Số hóa đơn", "Tổng doanh thu"});
                    data = hoaDonDAO.thongKeTheoQuy(namQuy);
                    break;
                case "Năm":
                    int tuNam = Integer.parseInt(cboTuNam.getSelectedItem().toString());
                    int denNam = Integer.parseInt(cboDenNam.getSelectedItem().toString());
                    if (tuNam > denNam) {
                        JOptionPane.showMessageDialog(this, "Năm bắt đầu phải nhỏ hơn hoặc bằng năm kết thúc!");
                        return;
                    }
                    modelHoaDon.setColumnIdentifiers(new String[] {"Năm", "Số hóa đơn", "Tổng doanh thu"});
                    data = hoaDonDAO.thongKeTheoNam(tuNam, denNam);
                    break;
            }

            // Update table and calculate totals
            if (data != null && !data.isEmpty()) {
                for (Object[] row : data) {
                    modelHoaDon.addRow(row);
                    try {
                        if (loaiThongKe.equals("Tuần")) {
                            tongHoaDon++;
                            String tongTienStr = row[3].toString().replaceAll("[^0-9]", ""); // Remove all non-digits
                            double tongTien = Double.parseDouble(tongTienStr);
                            tongDoanhThu += tongTien;
                        } else {
                            int soHoaDon = Integer.parseInt(row[1].toString());
                            String tongDoanhThuStr = row[2].toString().replaceAll("[^0-9]", ""); // Remove all non-digits
                            double doanhThu = Double.parseDouble(tongDoanhThuStr);
                            tongHoaDon += soHoaDon;
                            tongDoanhThu += doanhThu;
                        }
                    } catch (NumberFormatException ex) {
                        System.err.println("Lỗi định dạng số: " + ex.getMessage() + " - Dữ liệu: " + (loaiThongKe.equals("Tuần") ? row[3] : row[2]));
                        continue;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không có dữ liệu để thống kê!");
            }

            // Update summary with proper formatting
            txtTongHoaDon.setText(String.valueOf(tongHoaDon));
            txtTongDoanhThu.setText(df.format(tongDoanhThu));

            // Update charts
            updateCharts();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thống kê: " + e.getMessage());
        }
    }

    private void updateCharts() {
        String tuNgay = "2000-01-01";
        String denNgay = "2100-12-31";
        SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");

        try {
            String loaiThongKe = cboLoaiThongKe.getSelectedItem().toString();
            if (loaiThongKe.equals("Tuần")) {
                int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
                int thang = Integer.parseInt(cboThang.getSelectedItem().toString());
                int tuan = Integer.parseInt(cboTuan.getSelectedItem().toString());
                int ngayBatDau, ngayKetThuc;
                switch (tuan) {
                    case 1:
                        ngayBatDau = 1;
                        ngayKetThuc = 7;
                        break;
                    case 2:
                        ngayBatDau = 8;
                        ngayKetThuc = 14;
                        break;
                    case 3:
                        ngayBatDau = 15;
                        ngayKetThuc = 21;
                        break;
                    case 4:
                        ngayBatDau = 22;
                        ngayKetThuc = 28;
                        break;
                    default:
                        ngayBatDau = 1;
                        ngayKetThuc = 28;
                        break;
                }
                tuNgay = String.format("%d-%02d-%02d", nam, thang, ngayBatDau);
                denNgay = String.format("%d-%02d-%02d", nam, thang, ngayKetThuc);
            } else if (loaiThongKe.equals("Tháng") || loaiThongKe.equals("Quý")) {
                int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
                tuNgay = nam + "-01-01";
                denNgay = nam + "-12-31";
            } else if (loaiThongKe.equals("Năm")) {
                int tuNam = Integer.parseInt(cboTuNam.getSelectedItem().toString());
                int denNam = Integer.parseInt(cboDenNam.getSelectedItem().toString());
                tuNgay = tuNam + "-01-01";
                denNgay = denNam + "-12-31";
            }

            // Tạo biểu đồ cột
            if (loaiThongKe.equals("Tháng")) {
                int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
                List<Object[]> data = hoaDonDAO.thongKeTheoThang(nam);

                DefaultCategoryDataset soLuongDataset = new DefaultCategoryDataset();
                DefaultCategoryDataset doanhThuDataset = new DefaultCategoryDataset();

                for (Object[] row : data) {
                    String thang = row[0].toString();
                    int soHoaDon = Integer.parseInt(row[1].toString());
                    double doanhThu = Double.parseDouble(row[2].toString().replaceAll("[^0-9]", ""));
                    soLuongDataset.addValue(soHoaDon, "Số hóa đơn", thang);
                    doanhThuDataset.addValue(doanhThu, "Doanh thu (VNĐ)", thang);
                }

                JFreeChart soLuongChart = ChartFactory.createBarChart(
                        "",
                        "Tháng",
                        "Số lượng hóa đơn",
                        soLuongDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                chartPanelSoLuong.setChart(soLuongChart);

                JFreeChart doanhThuChart = ChartFactory.createBarChart(
                        "",
                        "Tháng",
                        "Doanh thu (VNĐ)",
                        doanhThuDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                chartPanelDoanhThu.setChart(doanhThuChart);
            } else if (loaiThongKe.equals("Năm")) {
                int tuNam = Integer.parseInt(cboTuNam.getSelectedItem().toString());
                int denNam = Integer.parseInt(cboDenNam.getSelectedItem().toString());
                List<Object[]> data = hoaDonDAO.thongKeTheoNam(tuNam, denNam);

                DefaultCategoryDataset soLuongDataset = new DefaultCategoryDataset();
                for (Object[] row : data) {
                    String nam = row[0].toString();
                    int soHoaDon = Integer.parseInt(row[1].toString());
                    soLuongDataset.addValue(soHoaDon, "Số hóa đơn", nam);
                }

                JFreeChart soLuongChart = ChartFactory.createBarChart(
                        "",
                        "Năm",
                        "Số lượng hóa đơn",
                        soLuongDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                chartPanelSoLuong.setChart(soLuongChart);

                chartPanelDoanhThu.setChart(null);
            } else {
                // Biểu đồ trạng thái và phương thức thanh toán (cho Tuần, Quý)
                Map<String, Double> trangThaiData = hoaDonDAO.thongKeTrangThai(tuNgay, denNgay);
                Map<String, Double> phuongThucData = hoaDonDAO.thongKePhuongThucThanhToan(tuNgay, denNgay);

                DefaultCategoryDataset trangThaiDataset = new DefaultCategoryDataset();
                DefaultCategoryDataset phuongThucDataset = new DefaultCategoryDataset();

                // Add data to datasets with duplicate check
                for (Map.Entry<String, Double> entry : trangThaiData.entrySet()) {
                    trangThaiDataset.addValue(entry.getValue(), "Tỷ lệ (%)", entry.getKey());
                }

                for (Map.Entry<String, Double> entry : phuongThucData.entrySet()) {
                    phuongThucDataset.addValue(entry.getValue(), "Tỷ lệ (%)", entry.getKey());
                }

                JFreeChart trangThaiChart = ChartFactory.createBarChart(
                        "",
                        "Trạng thái",
                        "Tỷ lệ (%)",
                        trangThaiDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                chartPanelSoLuong.setChart(trangThaiChart);

                JFreeChart phuongThucChart = ChartFactory.createBarChart(
                        "",
                        "Phương thức",
                        "Tỷ lệ (%)",
                        phuongThucDataset,
                        PlotOrientation.VERTICAL,
                        true, true, false);
                chartPanelDoanhThu.setChart(phuongThucChart);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật biểu đồ: " + e.getMessage());
        }
    }
    
    private void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ThongKeDoanhThu");

        // Tạo tiêu đề cho sheet
        Row headerRow = sheet.createRow(0);
        String[] headers;
        String loaiThongKe = cboLoaiThongKe.getSelectedItem().toString();
        if (loaiThongKe.equals("Tuần")) {
            headers = new String[]{"Mã hóa đơn", "Ngày lập", "Mã khách hàng", "Tổng tiền"};
        } else if (loaiThongKe.equals("Tháng")) {
            headers = new String[]{"Tháng", "Số hóa đơn", "Tổng doanh thu"};
        } else if (loaiThongKe.equals("Quý")) {
            headers = new String[]{"Quý", "Số hóa đơn", "Tổng doanh thu"};
        } else { // Năm
            headers = new String[]{"Năm", "Số hóa đơn", "Tổng doanh thu"};
        }
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Ghi dữ liệu từ bảng vào sheet
        DefaultTableModel tableModel = (DefaultTableModel) tableHoaDon.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    if (loaiThongKe.equals("Tuần") && j == 3) { // Cột Tổng tiền
                        String valueStr = value.toString().replaceAll("[^0-9.]", "");
                        try {
                            double number = Double.parseDouble(valueStr);
                            cell.setCellValue(number);
                        } catch (NumberFormatException e) {
                            cell.setCellValue(value.toString());
                        }
                    } else if (!loaiThongKe.equals("Tuần") && j == 2) { // Cột Tổng doanh thu
                        String valueStr = value.toString().replaceAll("[^0-9.]", "");
                        try {
                            double number = Double.parseDouble(valueStr);
                            cell.setCellValue(number);
                        } catch (NumberFormatException e) {
                            cell.setCellValue(value.toString());
                        }
                    } else if (!loaiThongKe.equals("Tuần") && j == 1) { // Cột Số hóa đơn
                        try {
                            cell.setCellValue(Integer.parseInt(value.toString()));
                        } catch (NumberFormatException e) {
                            cell.setCellValue(value.toString());
                        }
                    } else {
                        cell.setCellValue(value.toString());
                    }
                } else {
                    cell.setCellValue("");
                }
            }
        }

        // Tự động điều chỉnh kích thước cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi workbook vào file
        try (FileOutputStream fileOut = new FileOutputStream("ThongKeDoanhThu.xlsx")) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(this, "Xuất file Excel thành công!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Xuất file Excel thất bại!");
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                JFrame frame = new JFrame("Thống Kê Hóa Đơn");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1550, 878);
                frame.setContentPane(new Frame_TaiChinh_DoanhThu());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}