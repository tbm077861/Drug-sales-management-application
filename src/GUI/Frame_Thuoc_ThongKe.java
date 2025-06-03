package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

import DAO.ThuocDAO;

public class Frame_Thuoc_ThongKe extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tableThuoc;
    private DefaultTableModel modelThuoc;
    private JComboBox<String> cboLoaiThongKe;
    private JComboBox<String> cboNam;
    private JComboBox<String> cboThang;
    private JDateChooser dateChooserTuNgay;
    private JDateChooser dateChooserDenNgay;
    private JTextField txtTongLoaiThuoc;
    private JTextField txtTongSoLuongBan;
    private JTextField txtTongDoanhThu;
    private ThuocDAO thuocDAO = new ThuocDAO();

    // Define colors from Frame_GiaoDich_NhapThuoc
    private final Color MAIN_COLOR = new Color(254, 222, 192);
    private final Color HEADER_COLOR = new Color(251, 203, 150);
    private final Color BUTTON_COLOR = new Color(249, 187, 118);
    private final Color BUTTON_TEXT_COLOR = new Color(121, 70, 13);
    private final Color TEXT_COLOR = new Color(100, 60, 20);
    private final Color PANEL_BORDER_COLOR = new Color(222, 184, 135);
    private final Color TABLE_HEADER_COLOR = new Color(251, 203, 150);
    private final Color SELECTED_COLOR = new Color(255, 239, 213);

    public Frame_Thuoc_ThongKe() {
        setBackground(MAIN_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(1550, 878));

        // Title Panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBounds(0, 0, 1540, 60);
        pnlTitle.setBackground(HEADER_COLOR);
        add(pnlTitle);
        pnlTitle.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("THỐNG KÊ THUỐC");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_COLOR);
        pnlTitle.add(lblTitle, BorderLayout.CENTER);

        // Filter Panel
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBounds(10, 70, 509, 274);
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

        cboLoaiThongKe = new JComboBox<>(new String[] {"Tháng", "Năm", "Khoảng thời gian"});
        cboLoaiThongKe.setBackground(Color.WHITE);
        cboLoaiThongKe.setForeground(TEXT_COLOR);
        cboLoaiThongKe.setFont(new Font("Arial", Font.PLAIN, 16));
        cboLoaiThongKe.setBounds(119, 30, 380, 30);
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
        cboNam.setFont(new Font("Arial", Font.PLAIN, 16));
        cboNam.setBounds(119, 70, 380, 30);
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
        cboThang.setBounds(119, 110, 380, 30);
        pnlFilter.add(cboThang);

        // Filter Row 2
        JLabel lblTuNgay = new JLabel("Từ ngày:");
        lblTuNgay.setForeground(TEXT_COLOR);
        lblTuNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTuNgay.setBounds(10, 150, 140, 30);
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
        lblDenNgay.setBounds(279, 150, 80, 30);
        pnlFilter.add(lblDenNgay);

        dateChooserDenNgay = new JDateChooser();
        dateChooserDenNgay.setDateFormatString("dd/MM/yyyy");
        dateChooserDenNgay.setFont(new Font("Arial", Font.PLAIN, 16));
        dateChooserDenNgay.setBounds(359, 150, 140, 30);
        dateChooserDenNgay.setDate(new Date());
        pnlFilter.add(dateChooserDenNgay);

        // Buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.setBackground(MAIN_COLOR);
        pnlButtons.setBounds(89, 203, 331, 40);
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
        pnlTable.setBounds(529, 70, 1011, 508);
        pnlTable.setBackground(MAIN_COLOR);
        pnlTable.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Bảng thống kê thuốc", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlTable);
        pnlTable.setLayout(null);

        modelThuoc = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Mã thuốc", "Tên thuốc", "Số lượng bán", "Doanh thu", "Tồn kho"}
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableThuoc = new JTable(modelThuoc);
        tableThuoc.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableThuoc.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        tableThuoc.getTableHeader().setForeground(TEXT_COLOR);
        tableThuoc.setBackground(Color.WHITE);
        tableThuoc.setForeground(TEXT_COLOR);
        tableThuoc.setSelectionBackground(SELECTED_COLOR);
        tableThuoc.setRowHeight(30);
        tableThuoc.setFont(new Font("Arial", Font.PLAIN, 14));
        tableThuoc.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableThuoc.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableThuoc.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableThuoc.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableThuoc.getColumnModel().getColumn(4).setPreferredWidth(100);

        // Thêm TableRowSorter để hỗ trợ sắp xếp
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelThuoc);
        tableThuoc.setRowSorter(sorter);

        // Cấu hình comparator cho các cột
        sorter.setComparator(0, (o1, o2) -> { // Mã thuốc
            String maThuoc1 = (String) o1;
            String maThuoc2 = (String) o2;
            return maThuoc1.compareTo(maThuoc2);
        });

        sorter.setComparator(2, (o1, o2) -> { // Số lượng bán
            Integer soLuong1 = (Integer) o1;
            Integer soLuong2 = (Integer) o2;
            return soLuong1.compareTo(soLuong2);
        });

        sorter.setComparator(3, (o1, o2) -> { // Doanh thu
            String doanhThuStr1 = (String) o1;
            String doanhThuStr2 = (String) o2;
            double doanhThu1 = Double.parseDouble(doanhThuStr1.replaceAll("[^\\d.]", ""));
            double doanhThu2 = Double.parseDouble(doanhThuStr2.replaceAll("[^\\d.]", ""));
            return Double.compare(doanhThu1, doanhThu2);
        });

        sorter.setComparator(4, (o1, o2) -> { // Tồn kho
            Integer tonKho1 = (Integer) o1;
            Integer tonKho2 = (Integer) o2;
            return tonKho1.compareTo(tonKho2);
        });

        // Cho phép sắp xếp trên các cột được chỉ định
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sorter.setSortable(1, false); // Không sort cột Tên thuốc
        tableThuoc.getTableHeader().setToolTipText("Nhấn để sắp xếp");

        JScrollPane scrollPane = new JScrollPane(tableThuoc);
        scrollPane.setBounds(5, 21, 996, 477);
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

        JLabel lblTongLoaiThuoc = new JLabel("Tổng loại thuốc:");
        lblTongLoaiThuoc.setForeground(TEXT_COLOR);
        lblTongLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongLoaiThuoc.setBounds(10, 30, 300, 30);
        pnlThongTin.add(lblTongLoaiThuoc);

        txtTongLoaiThuoc = new JTextField("0");
        txtTongLoaiThuoc.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongLoaiThuoc.setEditable(false);
        txtTongLoaiThuoc.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongLoaiThuoc.setForeground(new Color(165, 42, 42));
        txtTongLoaiThuoc.setBackground(new Color(253, 245, 230));
        txtTongLoaiThuoc.setBounds(171, 30, 328, 30);
        pnlThongTin.add(txtTongLoaiThuoc);

        JLabel lblTongSoLuongBan = new JLabel("Tổng số lượng bán:");
        lblTongSoLuongBan.setForeground(TEXT_COLOR);
        lblTongSoLuongBan.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongSoLuongBan.setBounds(10, 70, 300, 30);
        pnlThongTin.add(lblTongSoLuongBan);

        txtTongSoLuongBan = new JTextField("0");
        txtTongSoLuongBan.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongSoLuongBan.setEditable(false);
        txtTongSoLuongBan.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongSoLuongBan.setForeground(TEXT_COLOR);
        txtTongSoLuongBan.setBackground(new Color(253, 245, 230));
        txtTongSoLuongBan.setBounds(171, 70, 328, 30);
        pnlThongTin.add(txtTongSoLuongBan);

        JLabel lblTongDoanhThu = new JLabel("Tổng doanh thu:");
        lblTongDoanhThu.setForeground(TEXT_COLOR);
        lblTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongDoanhThu.setBounds(10, 110, 300, 30);
        pnlThongTin.add(lblTongDoanhThu);

        txtTongDoanhThu = new JTextField("0 VNĐ");
        txtTongDoanhThu.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongDoanhThu.setEditable(false);
        txtTongDoanhThu.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongDoanhThu.setForeground(new Color(165, 42, 42));
        txtTongDoanhThu.setBackground(new Color(253, 245, 230));
        txtTongDoanhThu.setBounds(171, 110, 328, 30);
        pnlThongTin.add(txtTongDoanhThu);

        // Event for cboLoaiThongKe
        cboLoaiThongKe.addActionListener(e -> {
            String selectedOption = cboLoaiThongKe.getSelectedItem().toString();
            switch (selectedOption) {
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
        cboLoaiThongKe.setSelectedIndex(0); // Set default to "Tháng"

        // Button events
        btnThongKe.addActionListener(e -> thongKeThuoc());
        btnXuatBaoCao.addActionListener(e -> xuatExcel());
    }

    private void thongKeThuoc() {
        modelThuoc.setRowCount(0);
        String loaiThongKe = cboLoaiThongKe.getSelectedItem().toString();
        List<Object[]> data = null;
        int tongLoaiThuoc = 0;
        int tongSoLuongBan = 0;
        double tongDoanhThu = 0;

        SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int nam = Integer.parseInt(cboNam.getSelectedItem().toString());
            int thang = Integer.parseInt(cboThang.getSelectedItem().toString());
            String tuNgay = null, denNgay = null;

            switch (loaiThongKe) {
                case "Tháng":
                    data = thuocDAO.thongKeTheoThang(nam, thang);
                    break;
                case "Năm":
                    data = thuocDAO.thongKeTheoNam(nam);
                    break;
                case "Khoảng thời gian":
                    if (dateChooserTuNgay.getDate() == null || dateChooserDenNgay.getDate() == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khoảng thời gian hợp lệ!");
                        return;
                    }
                    tuNgay = sdfSQL.format(dateChooserTuNgay.getDate());
                    denNgay = sdfSQL.format(dateChooserDenNgay.getDate());
                    data = thuocDAO.thongKeTheoKhoangThoiGian(tuNgay, denNgay);
                    break;
            }

            // Update table and calculate totals
            if (data != null) {
                for (Object[] row : data) {
                    double doanhThu = (Double) row[3];
                    row[3] = String.format("%,.0f VNĐ", doanhThu);
                    modelThuoc.addRow(row);

                    int soLuongBan = Integer.parseInt(row[2].toString());
                    tongSoLuongBan += soLuongBan;
                    tongDoanhThu += doanhThu;
                    if (soLuongBan > 0) {
                        tongLoaiThuoc++;
                    }
                }
            }

            // Update summary
            txtTongLoaiThuoc.setText(String.valueOf(tongLoaiThuoc));
            txtTongSoLuongBan.setText(String.valueOf(tongSoLuongBan));
            txtTongDoanhThu.setText(String.format("%,.0f VNĐ", tongDoanhThu));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi thống kê: " + e.getMessage());
        }
    }
    
    private void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ThongKeThuoc");

        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Mã thuốc", "Tên thuốc", "Số lượng bán", "Doanh thu", "Tồn kho"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Ghi dữ liệu từ bảng vào file Excel
        DefaultTableModel tableModel = (DefaultTableModel) tableThuoc.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    if (j == 3) { // Cột Doanh thu
                        String valueStr = value.toString().replaceAll("[^0-9.]", "");
                        try {
                            double number = Double.parseDouble(valueStr);
                            cell.setCellValue(number);
                        } catch (NumberFormatException e) {
                            cell.setCellValue(value.toString());
                        }
                    } else if (j == 2 || j == 4) { // Cột Số lượng bán và Tồn kho
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

        // Xuất file Excel
        try (FileOutputStream fileOut = new FileOutputStream("ThongKeThuoc.xlsx")) {
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
                JFrame frame = new JFrame("Thống Kê Thuốc");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1550, 878);
                frame.setContentPane(new Frame_Thuoc_ThongKe());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}