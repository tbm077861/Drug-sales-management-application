package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.toedter.calendar.JDateChooser;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

import DAO.KhachHangDAO;

public class Frame_KhachHang_ThongKe extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tableKhachHang;
    private DefaultTableModel modelKhachHang;
    private JComboBox<String> cboLoaiThongKe;
    private JComboBox<String> cboNam;
    private JComboBox<String> cboThang;
    private JDateChooser dateChooserTuNgay;
    private JDateChooser dateChooserDenNgay;
    private JTextField txtTongKhachHang;
    private JTextField txtTongGiaoDich;
    private JTextField txtTrungBinh;
    private KhachHangDAO khachHangDAO = new KhachHangDAO();
    private ChartPanel chartPanelDoTuoi;
    private ChartPanel chartPanelPhuongThuc; // Biểu đồ mới

    // Define colors
    private final Color MAIN_COLOR = new Color(254, 222, 192);
    private final Color HEADER_COLOR = new Color(251, 203, 150);
    private final Color BUTTON_COLOR = new Color(249, 187, 118);
    private final Color BUTTON_TEXT_COLOR = new Color(121, 70, 13);
    private final Color TEXT_COLOR = new Color(100, 60, 20);
    private final Color PANEL_BORDER_COLOR = new Color(222, 184, 135);
    private final Color TABLE_HEADER_COLOR = new Color(251, 203, 150);
    private final Color SELECTED_COLOR = new Color(255, 239, 213);

    public Frame_KhachHang_ThongKe() {
        setBackground(MAIN_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(1550, 878)); // Tăng kích thước giống Frame_HoaDon_ThongKe

        // Title Panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBounds(0, 0, 1540, 60);
        pnlTitle.setBackground(HEADER_COLOR);
        add(pnlTitle);
        pnlTitle.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("THỐNG KÊ KHÁCH HÀNG");
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

        cboLoaiThongKe = new JComboBox<>(new String[] {"Ngày", "Tháng", "Năm", "Khoảng thời gian"});
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
        for (int i = currentYear - 5; i <= currentYear + 1; i++) {
            cboNam.addItem(String.valueOf(i));
        }
        cboNam.setSelectedItem(String.valueOf(currentYear));
        cboNam.setBackground(Color.WHITE);
        cboNam.setForeground(TEXT_COLOR);
        cboNam.setFont(new Font("Arial", Font.PLAIN, 16)); // Fixed typo: setFontew -> setFont
        cboNam.setBounds(119, 70, 370, 30);
        pnlFilter.add(cboNam);

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

        // Filter Row 2
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setForeground(TEXT_COLOR);
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTuNgay.setBounds(10, 150, 150, 30);
        pnlFilter.add(lblTuNgay);

        dateChooserTuNgay = new JDateChooser();
        dateChooserTuNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserTuNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        dateChooserTuNgay.setBounds(119, 150, 140, 30);
        dateChooserTuNgay.setDate(new Date());
        pnlFilter.add(dateChooserTuNgay);

        JLabel lblDenNgay = new JLabel("Đến ngày:");
        lblDenNgay.setForeground(TEXT_COLOR);
        lblDenNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDenNgay.setBounds(266, 150, 80, 30);
        pnlFilter.add(lblDenNgay);

        dateChooserDenNgay = new JDateChooser();
        dateChooserDenNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserDenNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        dateChooserDenNgay.setBounds(349, 150, 140, 30);
        dateChooserDenNgay.setDate(new Date());
        pnlFilter.add(dateChooserDenNgay);

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
                "Bảng thống kê khách hàng", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlTable);
        pnlTable.setLayout(null);

        modelKhachHang = new DefaultTableModel(
                new Object[][] {},
                new String[] {"STT", "Mã khách hàng", "Tên khách hàng", "Số giao dịch", "Doanh thu", "Trung bình"}
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableKhachHang = new JTable(modelKhachHang);
        tableKhachHang.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableKhachHang.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        tableKhachHang.getTableHeader().setForeground(TEXT_COLOR);
        tableKhachHang.setBackground(Color.WHITE);
        tableKhachHang.setForeground(TEXT_COLOR);
        tableKhachHang.setSelectionBackground(SELECTED_COLOR);
        tableKhachHang.setRowHeight(30);
        tableKhachHang.setFont(new Font("Arial", Font.PLAIN, 14));
        tableKhachHang.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableKhachHang.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableKhachHang.getColumnModel().getColumn(2).setPreferredWidth(150);
        tableKhachHang.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableKhachHang.getColumnModel().getColumn(4).setPreferredWidth(150);
        tableKhachHang.getColumnModel().getColumn(5).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableKhachHang);
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

        JLabel lblTongKhachHang = new JLabel("Tổng khách hàng:");
        lblTongKhachHang.setForeground(TEXT_COLOR);
        lblTongKhachHang.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongKhachHang.setBounds(10, 30, 300, 30);
        pnlThongTin.add(lblTongKhachHang);

        txtTongKhachHang = new JTextField("0");
        txtTongKhachHang.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongKhachHang.setEditable(false);
        txtTongKhachHang.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongKhachHang.setForeground(new Color(165, 42, 42));
        txtTongKhachHang.setBackground(new Color(253, 245, 230));
        txtTongKhachHang.setBounds(171, 30, 328, 30);
        pnlThongTin.add(txtTongKhachHang);

        JLabel lblTongGiaoDich = new JLabel("Tổng giao dịch:");
        lblTongGiaoDich.setForeground(TEXT_COLOR);
        lblTongGiaoDich.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongGiaoDich.setBounds(10, 70, 300, 30);
        pnlThongTin.add(lblTongGiaoDich);

        txtTongGiaoDich = new JTextField("0");
        txtTongGiaoDich.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongGiaoDich.setEditable(false);
        txtTongGiaoDich.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongGiaoDich.setForeground(TEXT_COLOR);
        txtTongGiaoDich.setBackground(new Color(253, 245, 230));
        txtTongGiaoDich.setBounds(171, 70, 328, 30);
        pnlThongTin.add(txtTongGiaoDich);

        JLabel lblTrungBinh = new JLabel("Trung bình:");
        lblTrungBinh.setForeground(TEXT_COLOR);
        lblTrungBinh.setFont(new Font("Arial", Font.BOLD, 18));
        lblTrungBinh.setBounds(10, 110, 300, 30);
        pnlThongTin.add(lblTrungBinh);

        txtTrungBinh = new JTextField("0 VNĐ");
        txtTrungBinh.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTrungBinh.setEditable(false);
        txtTrungBinh.setFont(new Font("Arial", Font.BOLD, 18));
        txtTrungBinh.setForeground(new Color(165, 42, 42));
        txtTrungBinh.setBackground(new Color(253, 245, 230));
        txtTrungBinh.setBounds(171, 110, 328, 30);
        pnlThongTin.add(txtTrungBinh);

        // Chart Panel for phân bố khách hàng theo độ tuổi
        chartPanelDoTuoi = new ChartPanel(null);
        chartPanelDoTuoi.setBounds(539, 422, 450, 300);
        chartPanelDoTuoi.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Phân bố khách hàng theo độ tuổi", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12), TEXT_COLOR));
        add(chartPanelDoTuoi);

        // Chart Panel for phân bố giao dịch theo phương thức thanh toán (biểu đồ mới)
        chartPanelPhuongThuc = new ChartPanel(null);
        chartPanelPhuongThuc.setBounds(1030, 422, 450, 300);
        chartPanelPhuongThuc.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Phân bố giao dịch theo phương thức thanh toán", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12), TEXT_COLOR));
        add(chartPanelPhuongThuc);

        // Event for cboLoaiThongKe
        cboLoaiThongKe.addActionListener(e -> {
            String selectedOption = cboLoaiThongKe.getSelectedItem().toString();
            switch (selectedOption) {
                case "Ngày":
                case "Tháng":
                    lblNam.setVisible(true);
                    cboNam.setVisible(true);
                    lblThang.setVisible(true);
                    cboThang.setVisible(true);
                    lblTuNgay.setVisible(false);
                    dateChooserTuNgay.setVisible(false);
                    lblDenNgay.setVisible(false);
                    dateChooserDenNgay.setVisible(false);
                    break;
                case "Năm":
                    lblNam.setVisible(true);
                    cboNam.setVisible(true);
                    lblThang.setVisible(false);
                    cboThang.setVisible(false);
                    lblTuNgay.setVisible(false);
                    dateChooserTuNgay.setVisible(false);
                    lblDenNgay.setVisible(false);
                    dateChooserDenNgay.setVisible(false);
                    break;
                case "Khoảng thời gian":
                    lblNam.setVisible(false);
                    cboNam.setVisible(false);
                    lblThang.setVisible(false);
                    cboThang.setVisible(false);
                    lblTuNgay.setVisible(true);
                    dateChooserTuNgay.setVisible(true);
                    lblDenNgay.setVisible(true);
                    dateChooserDenNgay.setVisible(true);
                    break;
            }
            pnlFilter.revalidate();
            pnlFilter.repaint();
        });

        // Initialize default state
        cboLoaiThongKe.setSelectedIndex(0); // Set default to "Ngày"

        // Button events
        btnThongKe.addActionListener(e -> thongKeKhachHang());
        btnXuatBaoCao.addActionListener(e -> xuatExcel());

        // Load initial charts
        updateCharts();
    }

    private void thongKeKhachHang() {
        modelKhachHang.setRowCount(0);
        String loaiThongKe = cboLoaiThongKe.getSelectedItem().toString();
        List<Object[]> data = null;
        int tongKhachHang = 0;
        int tongGiaoDich = 0;
        double tongDoanhThu = 0;

        SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
        try {
            switch (loaiThongKe) {
                case "Ngày":
                    if (dateChooserTuNgay.getDate() == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!");
                        return;
                    }
                    String ngaySQL = sdfSQL.format(dateChooserTuNgay.getDate());
                    data = khachHangDAO.thongKeTheoNgay(ngaySQL);
                    break;
                case "Tháng":
                    int namThang = Integer.parseInt(cboNam.getSelectedItem().toString());
                    int thang = Integer.parseInt(cboThang.getSelectedItem().toString());
                    data = khachHangDAO.thongKeTheoThang(namThang, thang);
                    break;
                case "Năm":
                    int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
                    data = khachHangDAO.thongKeTheoNam(nam);
                    break;
                case "Khoảng thời gian":
                    if (dateChooserTuNgay.getDate() == null || dateChooserDenNgay.getDate() == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khoảng thời gian hợp lệ!");
                        return;
                    }
                    String tuNgay = sdfSQL.format(dateChooserTuNgay.getDate());
                    String denNgay = sdfSQL.format(dateChooserDenNgay.getDate());
                    data = khachHangDAO.thongKeTheoKhoangThoiGian(tuNgay, denNgay);
                    break;
            }

            // Update table and calculate totals
            if (data != null) {
                for (Object[] row : data) {
                    // Định dạng doanhThu và trungBinh để hiển thị
                    double doanhThu = (Double) row[4];
                    double trungBinh = (Double) row[5];
                    row[4] = String.format("%,.0f VNĐ", doanhThu);
                    row[5] = String.format("%,.0f VNĐ", trungBinh);
                    modelKhachHang.addRow(row);

                    int soGiaoDich = Integer.parseInt(row[3].toString());
                    tongGiaoDich += soGiaoDich;
                    tongDoanhThu += doanhThu;
                    if (soGiaoDich > 0) { // Chỉ tính khách hàng có giao dịch
                        tongKhachHang++;
                    }
                }
            }

            // Update summary
            txtTongKhachHang.setText(String.valueOf(tongKhachHang));
            txtTongGiaoDich.setText(String.valueOf(tongGiaoDich));
            txtTrungBinh.setText(tongGiaoDich > 0 ? String.format("%,.0f VNĐ", tongDoanhThu / tongGiaoDich) : "0 VNĐ");

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
            if (loaiThongKe.equals("Ngày")) {
                if (dateChooserTuNgay.getDate() != null) {
                    tuNgay = sdfSQL.format(dateChooserTuNgay.getDate());
                    denNgay = tuNgay;
                    System.out.println("Khoảng thời gian (theo ngày): " + tuNgay + " đến " + denNgay);
                }
            } else if (loaiThongKe.equals("Tháng")) {
                int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
                int thang = Integer.parseInt(cboThang.getSelectedItem().toString());
                tuNgay = String.format("%d-%02d-01", nam, thang);
                Calendar cal = Calendar.getInstance();
                cal.set(nam, thang - 1, 1);
                int maxDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                denNgay = String.format("%d-%02d-%02d", nam, thang, maxDayOfMonth);
                System.out.println("Khoảng thời gian (theo tháng): " + tuNgay + " đến " + denNgay);
            } else if (loaiThongKe.equals("Năm")) {
                int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
                tuNgay = nam + "-01-01";
                denNgay = nam + "-12-31";
                System.out.println("Khoảng thời gian (theo năm): " + tuNgay + " đến " + denNgay);
            } else if (loaiThongKe.equals("Khoảng thời gian")) {
                if (dateChooserTuNgay.getDate() != null && dateChooserDenNgay.getDate() != null) {
                    tuNgay = sdfSQL.format(dateChooserTuNgay.getDate());
                    denNgay = sdfSQL.format(dateChooserDenNgay.getDate());
                    System.out.println("Khoảng thời gian (khoảng thời gian): " + tuNgay + " đến " + denNgay);
                }
            }

            // Chart phân bố khách hàng theo độ tuổi
            Map<String, Double> doTuoiData = khachHangDAO.thongKeTheoDoTuoi();
            DefaultPieDataset doTuoiDataset = new DefaultPieDataset();
            if (doTuoiData.isEmpty()) {
                doTuoiDataset.setValue("Không có dữ liệu", 100.0);
            } else {
                for (Map.Entry<String, Double> entry : doTuoiData.entrySet()) {
                    doTuoiDataset.setValue(entry.getKey() + " (" + String.format("%.1f%%", entry.getValue()) + ")", entry.getValue());
                }
            }
            JFreeChart doTuoiChart = ChartFactory.createPieChart("", doTuoiDataset, true, true, false);
            chartPanelDoTuoi.setChart(doTuoiChart);

            // Chart phân bố giao dịch theo phương thức thanh toán
            Map<String, Double> phuongThucData = khachHangDAO.thongKePhuongThucThanhToanKhachHang(tuNgay, denNgay);
            DefaultPieDataset phuongThucDataset = new DefaultPieDataset();
            if (phuongThucData.isEmpty()) {
                phuongThucDataset.setValue("Không có dữ liệu", 100.0);
            } else {
                for (Map.Entry<String, Double> entry : phuongThucData.entrySet()) {
                    phuongThucDataset.setValue(entry.getKey() + " (" + String.format("%.1f%%", entry.getValue()) + ")", entry.getValue());
                }
            }
            JFreeChart phuongThucChart = ChartFactory.createPieChart("", phuongThucDataset, true, true, false);
            chartPanelPhuongThuc.setChart(phuongThucChart);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật biểu đồ: " + e.getMessage());
        }
    }
    
    
    private void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ThongKeKhachHang");

        // Tạo tiêu đề cho sheet
        Row headerRow = sheet.createRow(0);
        String[] headers = {"STT", "Mã khách hàng", "Tên khách hàng", "Số giao dịch", "Doanh thu", "Trung bình"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Điền dữ liệu từ bảng vào sheet
        DefaultTableModel tableModel = (DefaultTableModel) tableKhachHang.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    if (j == 4 || j == 5) { // Cột Doanh thu và Trung bình
                        String valueStr = value.toString().replaceAll("[^0-9.]", "");
                        try {
                            double number = Double.parseDouble(valueStr);
                            cell.setCellValue(number);
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

        // Ghi workbook vào file Excel
        try (FileOutputStream fileOut = new FileOutputStream("ThongKeKhachHang.xlsx")) {
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
                JFrame frame = new JFrame("Thống Kê Khách Hàng");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1550, 878);
                frame.setContentPane(new Frame_KhachHang_ThongKe());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}