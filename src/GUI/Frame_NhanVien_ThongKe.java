package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

import DAO.NhanVienDAO;

public class Frame_NhanVien_ThongKe extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tableNhanVien;
    private DefaultTableModel modelNhanVien;
    private JComboBox<String> cboLoaiThongKe;
    private JComboBox<String> cboNam;
    private JComboBox<String> cboThang;
    private JTextField txtTongNhanVien;
    private JTextField txtTongGiaoDich;
    private JTextField txtTrungBinh;
    private JSpinner spnTopK;
    private NhanVienDAO nhanVienDAO;

    // Define colors from Frame_KhachHang_ThongKe
    private final Color MAIN_COLOR = new Color(254, 222, 192);
    private final Color HEADER_COLOR = new Color(251, 203, 150);
    private final Color BUTTON_COLOR = new Color(249, 187, 118);
    private final Color BUTTON_TEXT_COLOR = new Color(121, 70, 13);
    private final Color TEXT_COLOR = new Color(100, 60, 20);
    private final Color PANEL_BORDER_COLOR = new Color(222, 184, 135);
    private final Color TABLE_HEADER_COLOR = new Color(251, 203, 150);
    private final Color SELECTED_COLOR = new Color(255, 239, 213);

    public Frame_NhanVien_ThongKe() {
        nhanVienDAO = new NhanVienDAO();
        setBackground(MAIN_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(1550, 878));

        // Title Panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBounds(0, 0, 1540, 60);
        pnlTitle.setBackground(HEADER_COLOR);
        add(pnlTitle);
        pnlTitle.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("THỐNG KÊ NHÂN VIÊN");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(TEXT_COLOR);
        pnlTitle.add(lblTitle, BorderLayout.CENTER);

        // Filter Panel
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBounds(10, 70, 509, 270);
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

        cboLoaiThongKe = new JComboBox<>(new String[] {"Top doanh số", "Ngày đi làm"});
        cboLoaiThongKe.setBackground(Color.WHITE);
        cboLoaiThongKe.setForeground(TEXT_COLOR);
        cboLoaiThongKe.setFont(new Font("Arial", Font.PLAIN, 16));
        cboLoaiThongKe.setBounds(119, 30, 370, 30);
        pnlFilter.add(cboLoaiThongKe);

        JLabel lblTopK = new JLabel("Top:");
        lblTopK.setForeground(TEXT_COLOR);
        lblTopK.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTopK.setBounds(10, 70, 150, 30);
        pnlFilter.add(lblTopK);

        spnTopK = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
        spnTopK.setFont(new Font("Arial", Font.PLAIN, 16));
        spnTopK.setBounds(119, 70, 100, 30);
        pnlFilter.add(spnTopK);

        JLabel lblNam = new JLabel("Năm:");
        lblNam.setForeground(TEXT_COLOR);
        lblNam.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNam.setBounds(10, 110, 150, 30);
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
        cboNam.setBounds(119, 110, 370, 30);
        pnlFilter.add(cboNam);

        JLabel lblThang = new JLabel("Tháng:");
        lblThang.setForeground(TEXT_COLOR);
        lblThang.setFont(new Font("Arial", Font.PLAIN, 16));
        lblThang.setBounds(10, 150, 150, 30);
        pnlFilter.add(lblThang);

        cboThang = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            cboThang.addItem(String.valueOf(i));
        }
        cboThang.setSelectedItem(String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1));
        cboThang.setBackground(Color.WHITE);
        cboThang.setForeground(TEXT_COLOR);
        cboThang.setFont(new Font("Arial", Font.PLAIN, 16));
        cboThang.setBounds(119, 150, 370, 30);
        pnlFilter.add(cboThang);

        // Buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.setBackground(MAIN_COLOR);
        pnlButtons.setBounds(90, 210, 331, 40);
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
        pnlTable.setBounds(529, 70, 999, 508);
        pnlTable.setBackground(MAIN_COLOR);
        pnlTable.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Bảng thống kê nhân viên", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlTable);
        pnlTable.setLayout(null);

        modelNhanVien = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Mã nhân viên", "Tên nhân viên", "Số giao dịch", "Doanh thu", "Trung bình"}
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableNhanVien = new JTable(modelNhanVien);
        tableNhanVien.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableNhanVien.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        tableNhanVien.getTableHeader().setForeground(TEXT_COLOR);
        tableNhanVien.setBackground(Color.WHITE);
        tableNhanVien.setForeground(TEXT_COLOR);
        tableNhanVien.setSelectionBackground(SELECTED_COLOR);
        tableNhanVien.setRowHeight(30);
        tableNhanVien.setFont(new Font("Arial", Font.PLAIN, 14));
        tableNhanVien.getColumnModel().getColumn(0).setPreferredWidth(100);
        tableNhanVien.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableNhanVien.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableNhanVien.getColumnModel().getColumn(3).setPreferredWidth(150);
        tableNhanVien.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableNhanVien);
        scrollPane.setBounds(5, 21, 985, 477);
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

        JLabel lblTongNhanVien = new JLabel("Tổng nhân viên:");
        lblTongNhanVien.setForeground(TEXT_COLOR);
        lblTongNhanVien.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongNhanVien.setBounds(10, 30, 300, 30);
        pnlThongTin.add(lblTongNhanVien);

        txtTongNhanVien = new JTextField("0");
        txtTongNhanVien.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongNhanVien.setEditable(false);
        txtTongNhanVien.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongNhanVien.setForeground(new Color(165, 42, 42));
        txtTongNhanVien.setBackground(new Color(253, 245, 230));
        txtTongNhanVien.setBounds(171, 30, 328, 30);
        pnlThongTin.add(txtTongNhanVien);

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

        // Event for cboLoaiThongKe
        cboLoaiThongKe.addActionListener(e -> {
            String selectedOption = cboLoaiThongKe.getSelectedItem().toString();
            updateTableModel(selectedOption);
            lblTopK.setVisible(selectedOption.equals("Top doanh số"));
            spnTopK.setVisible(selectedOption.equals("Top doanh số"));
            pnlFilter.revalidate();
            pnlFilter.repaint();
        });

        // Button events
        btnThongKe.addActionListener(e -> performStatistics());
        btnXuatBaoCao.addActionListener(e -> xuatExcel());

        // Initialize default state
        cboLoaiThongKe.setSelectedIndex(0);
        updateTableModel("Top doanh số");
    }

    private void updateTableModel(String type) {
        String[] columns;
        if (type.equals("Ngày đi làm")) {
            columns = new String[] {"Mã nhân viên", "Tên nhân viên", "Số ngày làm"};
        } else {
            columns = new String[] {"Mã nhân viên", "Tên nhân viên", "Số giao dịch", "Doanh thu", "Trung bình"};
        }
        modelNhanVien.setColumnIdentifiers(columns);
        modelNhanVien.setRowCount(0);
        tableNhanVien.revalidate();
        tableNhanVien.repaint();
    }

    private void performStatistics() {
        String type = cboLoaiThongKe.getSelectedItem().toString();
        String year = cboNam.getSelectedItem().toString();
        String month = cboThang.getSelectedItem().toString();
        int topK = (Integer) spnTopK.getValue();

        List<Object[]> data;
        modelNhanVien.setRowCount(0);

        if (type.equals("Top doanh số")) {
            data = nhanVienDAO.getTopNhanVienByRevenue(topK, null, null, year, month);
            for (Object[] row : data) {
                // Bỏ cột STT (row[0]) khi thêm vào model
                Object[] newRow = new Object[row.length - 1];
                System.arraycopy(row, 1, newRow, 0, newRow.length);
                // Định dạng doanhThu và trungBinh trước khi thêm vào bảng
                newRow[3] = String.format("%,.0f VNĐ", (Double) newRow[3]); // Doanh thu
                newRow[4] = String.format("%,.0f VNĐ", (Double) newRow[4]); // Trung bình
                modelNhanVien.addRow(newRow);
            }
            updateSummary(data);
        } else if (type.equals("Ngày đi làm")) {
            data = nhanVienDAO.getWorkingDaysStats(null, null, year, month);
            for (Object[] row : data) {
                // Bỏ cột STT (row[0]) khi thêm vào model
                Object[] newRow = new Object[row.length - 1];
                System.arraycopy(row, 1, newRow, 0, newRow.length);
                modelNhanVien.addRow(newRow);
            }
            updateSummaryWorkingDays(data);
        }
    }

    private void updateSummary(List<Object[]> data) {
        int tongNhanVien = data.size();
        int tongGiaoDich = 0;
        double tongDoanhThu = 0;

        for (Object[] row : data) {
            tongGiaoDich += (Integer) row[3]; // Số giao dịch
            double doanhThu = (Double) row[4]; // Doanh thu gốc
            tongDoanhThu += doanhThu;
        }

        txtTongNhanVien.setText(String.valueOf(tongNhanVien));
        txtTongGiaoDich.setText(String.valueOf(tongGiaoDich));
        txtTrungBinh.setText(String.format("%,.0f VNĐ", tongGiaoDich > 0 ? tongDoanhThu / tongGiaoDich : 0));
    }

    private void updateSummaryWorkingDays(List<Object[]> data) {
        int tongNhanVien = data.size();
        int tongNgayLam = 0;

        for (Object[] row : data) {
            tongNgayLam += (Integer) row[3];
        }

        txtTongNhanVien.setText(String.valueOf(tongNhanVien));
        txtTongGiaoDich.setText(String.valueOf(tongNgayLam));
        txtTrungBinh.setText(String.format("%d ngày", tongNhanVien > 0 ? tongNgayLam / tongNhanVien : 0));
    }

    private void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ThongKeNhanVien");

        // Tạo hàng tiêu đề
        Row headerRow = sheet.createRow(0);
        String[] headers;
        String selectedType = cboLoaiThongKe.getSelectedItem().toString();
        if (selectedType.equals("Ngày đi làm")) {
            headers = new String[]{"Mã nhân viên", "Tên nhân viên", "Số ngày làm"};
        } else {
            headers = new String[]{"Mã nhân viên", "Tên nhân viên", "Số giao dịch", "Doanh thu", "Trung bình"};
        }
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Ghi dữ liệu từ bảng vào sheet
        DefaultTableModel tableModel = (DefaultTableModel) tableNhanVien.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    if (selectedType.equals("Top doanh số") && (j == 3 || j == 4)) { // Cột Doanh thu và Trung bình
                        String valueStr = value.toString().replaceAll("[^0-9.]", "");
                        try {
                            double number = Double.parseDouble(valueStr);
                            cell.setCellValue(number);
                        } catch (NumberFormatException e) {
                            cell.setCellValue(value.toString());
                        }
                    } else if (selectedType.equals("Ngày đi làm") && j == 2) { // Cột Số ngày làm
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

        // Tự đông điều chỉnh kích thước cột
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Ghi workbook vào file Excel
        try (FileOutputStream fileOut = new FileOutputStream("ThongKeNhanVien.xlsx")) {
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
                JFrame frame = new JFrame("Thống Kê Nhân Viên");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1550, 878);
                frame.setContentPane(new Frame_NhanVien_ThongKe());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}