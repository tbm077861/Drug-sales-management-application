package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Dialog_ChiTietTraThuoc extends JDialog {
    private JTextField txtSoLuongTra;
    private JButton btnXacNhan;
    private JButton btnHuy;
    private boolean confirmed = false;
    private int soLuongTra = 0;

    public Dialog_ChiTietTraThuoc(Frame owner, String maThuoc, String tenThuoc, int soLuongConLai) {
        super(owner, "Nhập Chi Tiết Trả Thuốc", true);
        setIconImage(Toolkit.getDefaultToolkit().getImage("icon\\return.png"));
        setSize(400, 250);
        setLocationRelativeTo(owner);
        getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.setLayout(null);

        // Thông tin thuốc
        JLabel lblMaThuoc = new JLabel("Mã Thuốc: " + maThuoc);
        lblMaThuoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMaThuoc.setBounds(20, 20, 350, 30);
        formPanel.add(lblMaThuoc);

        JLabel lblTenThuoc = new JLabel("Tên Thuốc: " + tenThuoc);
        lblTenThuoc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTenThuoc.setBounds(20, 50, 350, 30);
        formPanel.add(lblTenThuoc);

        JLabel lblSoLuongCoTheTra = new JLabel("Số lượng có thể trả: " + soLuongConLai);
        lblSoLuongCoTheTra.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSoLuongCoTheTra.setBounds(20, 80, 350, 30);
        formPanel.add(lblSoLuongCoTheTra);

        // Trường nhập số lượng trả
        JLabel lblSoLuongTra = new JLabel("Số Lượng Trả:");
        lblSoLuongTra.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblSoLuongTra.setBounds(20, 110, 120, 30);
        formPanel.add(lblSoLuongTra);

        txtSoLuongTra = new JTextField();
        txtSoLuongTra.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSoLuongTra.setBounds(170, 110, 200, 30);
        formPanel.add(txtSoLuongTra);

        // Nút Xác Nhận và Hủy
        btnXacNhan = new JButton("Xác Nhận");
        btnXacNhan.setIcon(new ImageIcon("icon\\save.png"));
        btnXacNhan.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnXacNhan.setBounds(51, 160, 155, 40);
        formPanel.add(btnXacNhan);

        btnHuy = new JButton("Hủy");
        btnHuy.setIcon(new ImageIcon("icon\\cancel.png"));
        btnHuy.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnHuy.setBounds(216, 160, 120, 40);
        formPanel.add(btnHuy);

        getContentPane().add(formPanel, BorderLayout.CENTER);

        // Sự kiện nút Xác Nhận
        btnXacNhan.addActionListener(e -> {
            try {
                String soLuongTraText = txtSoLuongTra.getText().trim();

                if (soLuongTraText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng trả");
                    txtSoLuongTra.requestFocus();
                    return;
                }

                soLuongTra = Integer.parseInt(soLuongTraText);
                if (soLuongTra <= 0 || soLuongTra > soLuongConLai) {
                    JOptionPane.showMessageDialog(this, "Số lượng trả không hợp lệ (phải từ 1 đến " + soLuongConLai + ")");
                    txtSoLuongTra.requestFocus();
                    return;
                }

                confirmed = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ cho số lượng trả");
                txtSoLuongTra.requestFocus();
            }
        });

        // Sự kiện nút Hủy
        btnHuy.addActionListener(e -> dispose());
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getSoLuongTra() {
        return soLuongTra;
    }
}