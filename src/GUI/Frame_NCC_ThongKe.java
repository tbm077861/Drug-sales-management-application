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

import DAO.NhaCungCapDAO;

public class Frame_NCC_ThongKe extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTable tableNhaCungCap;
    private DefaultTableModel modelNhaCungCap;
    private JComboBox<String> cboNam;
    private JComboBox<String> cboThang;
    private JTextField txtTongNhaCungCap;
    private JTextField txtTongGiaoDich;
    private JTextField txtTrungBinh;
    private JSpinner spnTopK;
    private NhaCungCapDAO nhaCungCapDAO;

    // Define colors from Frame_KhachHang_ThongKe
    private final Color MAIN_COLOR = new Color(254, 222, 192);
    private final Color HEADER_COLOR = new Color(251, 203, 150);
    private final Color BUTTON_COLOR = new Color(249, 187, 118);
    private final Color BUTTON_TEXT_COLOR = new Color(121, 70, 13);
    private final Color TEXT_COLOR = new Color(100, 60, 20);
    private final Color PANEL_BORDER_COLOR = new Color(222, 184, 135);
    private final Color TABLE_HEADER_COLOR = new Color(251, 203, 150);
    private final Color SELECTED_COLOR = new Color(255, 239, 213);

    public Frame_NCC_ThongKe() {
        nhaCungCapDAO = new NhaCungCapDAO();
        setBackground(MAIN_COLOR);
        setLayout(null);
        setPreferredSize(new Dimension(1550, 878));

        // Title Panel
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBounds(0, 0, 1540, 60);
        pnlTitle.setBackground(HEADER_COLOR);
        add(pnlTitle);
        pnlTitle.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("THỐNG KÊ NHÀ CUNG CẤP");
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
        JLabel lblTopK = new JLabel("Top:");
        lblTopK.setForeground(TEXT_COLOR);
        lblTopK.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTopK.setBounds(10, 30, 150, 30);
        pnlFilter.add(lblTopK);

        spnTopK = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
        spnTopK.setFont(new Font("Arial", Font.PLAIN, 16));
        spnTopK.setBounds(119, 30, 100, 30);
        pnlFilter.add(spnTopK);

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
        pnlTable.setBounds(529, 70, 999, 508);
        pnlTable.setBackground(MAIN_COLOR);
        pnlTable.setBorder(new TitledBorder(new LineBorder(PANEL_BORDER_COLOR, 1),
                "Bảng thống kê nhà cung cấp", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), TEXT_COLOR));
        add(pnlTable);
        pnlTable.setLayout(null);

        modelNhaCungCap = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Mã nhà cung cấp", "Tên nhà cung cấp", "Số giao dịch", "Tổng số lượng nhập"}
        ) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableNhaCungCap = new JTable(modelNhaCungCap);
        tableNhaCungCap.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tableNhaCungCap.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        tableNhaCungCap.getTableHeader().setForeground(TEXT_COLOR);
        tableNhaCungCap.setBackground(Color.WHITE);
        tableNhaCungCap.setForeground(TEXT_COLOR);
        tableNhaCungCap.setSelectionBackground(SELECTED_COLOR);
        tableNhaCungCap.setRowHeight(30);
        tableNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 14));
        tableNhaCungCap.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableNhaCungCap.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableNhaCungCap.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableNhaCungCap.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(tableNhaCungCap);
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

        JLabel lblTongNhaCungCap = new JLabel("Tổng NCC:");
        lblTongNhaCungCap.setForeground(TEXT_COLOR);
        lblTongNhaCungCap.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongNhaCungCap.setBounds(10, 30, 300, 30);
        pnlThongTin.add(lblTongNhaCungCap);

        txtTongNhaCungCap = new JTextField("0");
        txtTongNhaCungCap.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTongNhaCungCap.setEditable(false);
        txtTongNhaCungCap.setFont(new Font("Arial", Font.BOLD, 18));
        txtTongNhaCungCap.setForeground(new Color(165, 42, 42));
        txtTongNhaCungCap.setBackground(new Color(253, 245, 230));
        txtTongNhaCungCap.setBounds(171, 30, 328, 30);
        pnlThongTin.add(txtTongNhaCungCap);

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

        txtTrungBinh = new JTextField("0");
        txtTrungBinh.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTrungBinh.setEditable(false);
        txtTrungBinh.setFont(new Font("Arial", Font.BOLD, 18));
        txtTrungBinh.setForeground(new Color(165, 42, 42));
        txtTrungBinh.setBackground(new Color(253, 245, 230));
        txtTrungBinh.setBounds(171, 110, 328, 30);
        pnlThongTin.add(txtTrungBinh);

        // Button events
        btnThongKe.addActionListener(e -> performStatistics());
        btnXuatBaoCao.addActionListener(e -> xuatExcel());
    }

    private void performStatistics() {
        String year = cboNam.getSelectedItem().toString();
        String month = cboThang.getSelectedItem().toString();
        int topK = (Integer) spnTopK.getValue();

        List<Object[]> data;
        modelNhaCungCap.setRowCount(0);

        data = nhaCungCapDAO.getTopNhaCungCapByImportQuantity(topK, year, month);
        for (Object[] row : data) {
            // Bỏ cột STT (row[0]) khi thêm vào model
            Object[] newRow = new Object[row.length - 1];
            System.arraycopy(row, 1, newRow, 0, newRow.length);
            modelNhaCungCap.addRow(newRow);
        }
        updateSummary(data);
    }

    private void updateSummary(List<Object[]> data) {
        int tongNhaCungCap = data.size();
        int tongGiaoDich = 0;
        int tongSoLuong = 0;

        for (Object[] row : data) {
            tongGiaoDich += (Integer) row[3];
            tongSoLuong += (Integer) row[4];
        }

        txtTongNhaCungCap.setText(String.valueOf(tongNhaCungCap));
        txtTongGiaoDich.setText(String.valueOf(tongGiaoDich));
        txtTrungBinh.setText(String.format("%d", tongNhaCungCap > 0 ? tongSoLuong / tongNhaCungCap : 0));
    }

    private void xuatExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ThongKeNhaCungCap");

        // tạo tiêu đề cho sheet
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Mã nhà cung cấp", "Tên nhà cung cấp", "Số giao dịch", "Tổng số lượng nhập"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Ghi dữ liệu từ bảng vào sheet
        DefaultTableModel tableModel = (DefaultTableModel) tableNhaCungCap.getModel();
        int rowCount = tableModel.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                Object value = tableModel.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if (value != null) {
                    if (j == 2 || j == 3) { // Cột Số giao dịch và Tổng số lượng nhập
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
        try (FileOutputStream fileOut = new FileOutputStream("ThongKeNhaCungCap.xlsx")) {
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
                JFrame frame = new JFrame("Thống Kê Nhà Cung Cấp");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1550, 878);
                frame.setContentPane(new Frame_NCC_ThongKe());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}