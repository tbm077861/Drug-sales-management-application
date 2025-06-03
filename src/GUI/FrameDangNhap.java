package GUI;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import connectDB.*;

@SuppressWarnings("serial")
public class FrameDangNhap extends JFrame {
    private JLabel lblTitle;
    private JLabel lblMaNV;
    private JLabel lblMatKhau;
    private JTextField txtMaNV;
    private JPasswordField txtMatKhau; 
    private JButton btnDangNhap;
    private JButton btnThoat;
    private JComboBox<String> cboChucVu;
    private JLabel lblChucVu;
    private String backgroundPath = "image\\PharmacyStore.png";

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        private float opacity;

        public BackgroundPanel(String imagePath, float opacity) {
            this.opacity = opacity;
            try {
                File file = new File(imagePath);
                if (file.exists()) {
                    backgroundImage = new ImageIcon(imagePath).getImage();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Không tìm thấy file hình nền: " + imagePath,
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    setBackground(new Color(240, 240, 240));
                }
            } catch (Exception e) {
                e.printStackTrace();
                setBackground(new Color(240, 240, 240));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                g2d.dispose();
            }
        }
    }

    public FrameDangNhap() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("icon\\pharmacy.png"));
        setTitle("Đăng nhập hệ thống quản lý nhà thuốc");
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        BackgroundPanel mainPanel = new BackgroundPanel(backgroundPath, 0.1f);
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        lblTitle = new JLabel("TRANG ĐĂNG NHẬP", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        lblMaNV = new JLabel("Mã nhân viên: ");
        lblMatKhau = new JLabel("Mật khẩu: ");
        lblChucVu = new JLabel("Chức vụ: ");
        lblMaNV.setFont(labelFont);
        lblMatKhau.setFont(labelFont);
        lblChucVu.setFont(labelFont);
        Color labelColor = Color.BLACK;
        lblMaNV.setForeground(labelColor);
        lblMatKhau.setForeground(labelColor);
        lblChucVu.setForeground(labelColor);

        txtMaNV = new JTextField(15);
        txtMatKhau = new JPasswordField(15);
        txtMaNV.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyReleased(KeyEvent e) {
		        String text = txtMaNV.getText();
		        txtMaNV.setText(text.toUpperCase());
		    }
		});
        
        cboChucVu = new JComboBox<>(new String[]{"Quản Lý", "Nhân Viên"});
        Font inputFont = new Font("Arial", Font.PLAIN, 14);
        txtMaNV.setFont(inputFont);
        txtMatKhau.setFont(inputFont);
        cboChucVu.setFont(inputFont);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(lblMaNV, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(txtMaNV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(lblMatKhau, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(txtMatKhau, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(lblChucVu, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        centerPanel.add(cboChucVu, gbc);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        btnDangNhap = new JButton("Đăng nhập");
        btnThoat = new JButton("Thoát");
        btnDangNhap.setFont(buttonFont);
        btnThoat.setFont(buttonFont);
        Dimension buttonSize = new Dimension(120, 35);
        btnDangNhap.setPreferredSize(buttonSize);
        btnThoat.setPreferredSize(buttonSize);
        buttonPanel.add(btnDangNhap);
        buttonPanel.add(btnThoat);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnDangNhap.addActionListener(evt -> btnDangNhapActionPerformed());
        btnThoat.addActionListener(evt -> btnThoatActionPerformed());

        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private boolean kiemTraDangNhap(String maNV, String matKhau, String chucVu) {
    	String sql = "SELECT nv.maNV, nv.chucVu " +
                "FROM TaiKhoan tk " +
                "JOIN NhanVien nv ON tk.maNV = nv.maNV " +
                "WHERE tk.maNV = ? AND RTRIM(tk.matKhau) = ? AND nv.chucVu = ?";
        
        try (Connection conn = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, maNV);
            pstmt.setString(2, matKhau);
            pstmt.setString(3, chucVu);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            String errorMessage = "Lỗi kết nối database: " + e.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void btnDangNhapActionPerformed() {
        String maNV = txtMaNV.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String chucVu = ((String) cboChucVu.getSelectedItem()).trim();

        
        if (maNV.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập đầy đủ mã nhân viên và mật khẩu", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (kiemTraDangNhap(maNV, matKhau, chucVu)) {
            JOptionPane.showMessageDialog(this, 
                "Đăng nhập thành công với quyền " + chucVu, 
                "Thông báo", 
                JOptionPane.INFORMATION_MESSAGE);
            
            FrameTrangChu frameTrangChu = new FrameTrangChu(maNV, chucVu);
            frameTrangChu.setVisible(true); // Mở FrameTrangChu
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Thông tin đăng nhập không chính xác hoặc không có quyền truy cập!", 
                "Thông báo", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


    private void btnThoatActionPerformed() {
        int a = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát ứng dụng không?", "Lựa chọn", JOptionPane.YES_NO_OPTION);
        if (a == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> new FrameDangNhap().setVisible(true));
    }
}
