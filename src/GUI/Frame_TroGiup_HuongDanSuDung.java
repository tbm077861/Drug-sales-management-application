package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class Frame_TroGiup_HuongDanSuDung extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel pnlBackGround;
    private JPanel pdfPanel;
    private JScrollPane scrollPane;
    private JComboBox<String> guideComboBox;
    private static final String DEFAULT_PDF_PATH = "file/pdf/main.pdf";
    private Map<String, String> guidePdfMap;

    @SuppressWarnings("serial")
    public Frame_TroGiup_HuongDanSuDung() {
        setLayout(null);
        setPreferredSize(new Dimension(1550, 778));

        // Khởi tạo map để ánh xạ tên hướng dẫn với đường dẫn PDF
        guidePdfMap = new HashMap<>();
        guidePdfMap.put("Hướng dẫn sử dụng", "file/pdf/main.pdf");
        guidePdfMap.put("Hướng dẫn về thuốc", "file/pdf/thuoc.pdf");
        guidePdfMap.put("Huớng dẫn về nhân viên", "file/pdf/nhanVien.pdf");
        guidePdfMap.put("Hướng dẫn về khách hàng", "file/pdf/khachHang.pdf");
        guidePdfMap.put("Hướng dẫn về nhà cung cấp", "file/pdf/nhaCungCap.pdf");
        guidePdfMap.put("Hướng dẫn về hóa đơn", "file/pdf/hoaDon.pdf");
        guidePdfMap.put("Hướng dẫn về tài chính", "file/pdf/taiChinh.pdf");

        // Tạo pnlBackGround
        pnlBackGround = new JPanel();
        pnlBackGround.setBounds(0, 0, 1545, 778);
        pnlBackGround.setBackground(new Color(254, 222, 192));
        pnlBackGround.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnlBackGround.setLayout(null);
        add(pnlBackGround);

        // Tạo JLabel "Hướng Dẫn Sử Dụng"
        JLabel lblHDSD = new JLabel("Hướng Dẫn Sử Dụng");
        lblHDSD.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblHDSD.setBounds(32, 5, 212, 40);
        pnlBackGround.add(lblHDSD);

        // Tạo JComboBox để chọn hướng dẫn
        String[] guideOptions = guidePdfMap.keySet().toArray(new String[0]);
        guideComboBox = new JComboBox<>(guideOptions);
        guideComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        guideComboBox.setBounds(254, 5, 232, 40);
        guideComboBox.addActionListener(btnShowPDFAction());
        pnlBackGround.add(guideComboBox);

        // Tạo nút Reset
        JButton btnReset = new JButton("");
        btnReset.setIcon(new ImageIcon("icon\\refresh.png"));
        btnReset.setBounds(521, 5, 58, 40);
        btnReset.addActionListener(e -> {
            guideComboBox.setSelectedIndex(0); // Set combobox to default
            loadPDF(DEFAULT_PDF_PATH); // Load default PDF
        });
        pnlBackGround.add(btnReset);

        // Tạo panel để hiển thị PDF
        pdfPanel = new JPanel();
        pdfPanel.setLayout(new javax.swing.BoxLayout(pdfPanel, javax.swing.BoxLayout.Y_AXIS));

        // Tạo JScrollPane
        scrollPane = new JScrollPane(pdfPanel);
        scrollPane.setBounds(10, 50, 1525, 718);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Tăng tốc độ cuộn chuột
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Tăng bước cuộn
        pnlBackGround.add(scrollPane);

        // Load default PDF initially
        loadPDF(DEFAULT_PDF_PATH);
    }

    private ActionListener btnShowPDFAction() {
        return e -> {
            String selectedGuide = (String) guideComboBox.getSelectedItem();
            String pdfPath = guidePdfMap.get(selectedGuide);
            if (pdfPath != null) {
                loadPDF(pdfPath);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Không tìm thấy file PDF cho hướng dẫn này!");
            }
        };
    }

    private void loadPDF(String pdfPath) {
        // Xóa nội dung pdfPanel trước khi load PDF mới
        pdfPanel.removeAll();
        
        try {
            File pdfFile = new File(pdfPath);
            PDDocument document = PDDocument.load(pdfFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Lấy chiều rộng của scrollPane để scale ảnh
            int targetWidth = scrollPane.getWidth() - 20;
            int totalHeight = 0; // Tính tổng chiều cao của các trang

            // Chuyển từng trang PDF thành hình ảnh và thêm vào pdfPanel
            for (int page = 0; page < document.getNumberOfPages(); page++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(page, 300);

                // Tính toán tỷ lệ để scale ảnh
                double scale = (double) targetWidth / image.getWidth();
                int scaledHeight = (int) (image.getHeight() * scale);
                totalHeight += scaledHeight; // Cộng dồn chiều cao
                
                // Scale ảnh với chất lượng cao
                BufferedImage scaledImage = new BufferedImage(targetWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawImage(image, 0, 0, targetWidth, scaledHeight, null);
                g2d.dispose();

                // Tạo panel cho từng trang
                final BufferedImage finalImage = scaledImage;
                JPanel pagePanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        g2d.drawImage(finalImage, 0, 0, null);
                    }
                };
                pagePanel.setPreferredSize(new Dimension(targetWidth, scaledHeight));
                pdfPanel.add(pagePanel);
            }

            // Thêm spacerPanel với chiều cao vừa đủ
            int spacerHeight = Math.max(0, scrollPane.getHeight() - totalHeight + 50); // Đảm bảo đủ không gian cuộn
            JPanel spacerPanel = new JPanel();
            spacerPanel.setPreferredSize(new Dimension(targetWidth, spacerHeight));
            pdfPanel.add(spacerPanel);

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Không thể tải file PDF: " + e.getMessage());
        }

        // Cập nhật giao diện
        pdfPanel.revalidate();
        pdfPanel.repaint();
        scrollPane.getVerticalScrollBar().setValue(0); // Cuộn về đầu
    }
}