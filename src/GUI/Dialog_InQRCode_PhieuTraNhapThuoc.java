package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.io.IOException;

public class Dialog_InQRCode_PhieuTraNhapThuoc extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static boolean result = false;

	public Dialog_InQRCode_PhieuTraNhapThuoc(Frame parent, String maTNT, double tongTien) throws IOException {
		super(parent, "Mã phiếu trả nhập thuốc - " + maTNT, true);
		setSize(481, 611);
		setLocationRelativeTo(parent);

		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Tiêu đề
		JLabel title = new JLabel("Quét mã để thanh toán", SwingConstants.CENTER);
		title.setBounds(37, 0, 386, 45);
		title.setFont(new Font("Segoe UI", Font.BOLD, 18));
		title.setForeground(new Color(0, 128, 0));
		title.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
		contentPane.add(title);

		// Tổng tiền
		JLabel lblTongTien = new JLabel("Tổng Tiền:");
		lblTongTien.setBounds(33, 50, 100, 30);
		lblTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		contentPane.add(lblTongTien);

		JLabel txtTongTien = new JLabel(String.format("%,.0fđ", tongTien));
		txtTongTien.setBounds(143, 50, 200, 30);
		txtTongTien.setFont(new Font("Segoe UI", Font.BOLD, 16));
		contentPane.add(txtTongTien);

		// Ảnh QR
		ImageIcon originalIcon = new ImageIcon("image\\QR_code.jpg");
		// Lấy hình ảnh từ ImageIcon
		Image originalImage = originalIcon.getImage();
		// Tính toán kích thước mới (50% kích thước gốc)
		int newWidth = originalIcon.getIconWidth() / 2;
		int newHeight = originalIcon.getIconHeight() / 2;
		// Scale ảnh về kích thước mới
		Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		// Tạo ImageIcon mới từ ảnh đã scale
		ImageIcon scaledIcon = new ImageIcon(scaledImage);

		JLabel lblQRCode = new JLabel(scaledIcon);
		lblQRCode.setBounds(10, 90, 453, 422);
		contentPane.add(lblQRCode);

		JButton btnCancel = new JButton("Hủy");
		btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnCancel.setPreferredSize(new Dimension(120, 40));
		btnCancel.setForeground(new Color(255, 255, 255));
		btnCancel.setFocusPainted(false);
		btnCancel.setBackground(new Color(220, 53, 69));
		btnCancel.setBounds(174, 522, 120, 40);
		btnCancel.setOpaque(true);
		btnCancel.setContentAreaFilled(true);
		btnCancel.setBorderPainted(false);
		contentPane.add(btnCancel);

		JButton btnDone = new JButton("Hoàn tất");
		btnDone.setFont(new Font("Segoe UI", Font.BOLD, 14));
		btnDone.setPreferredSize(new Dimension(120, 40));
		btnDone.setForeground(new Color(255, 255, 255));
		btnDone.setFocusPainted(false);
		btnDone.setBackground(new Color(40, 167, 69));
		btnDone.setBounds(323, 522, 120, 40);
		btnDone.setOpaque(true);
		btnDone.setContentAreaFilled(true);
		btnDone.setBorderPainted(false);
		contentPane.add(btnDone);

		btnDone.addActionListener(e -> btnDoneActionPerformed());
		btnCancel.addActionListener(e -> btnCancelActionPerformed());
	}
	
	private void btnDoneActionPerformed() {
		JOptionPane.showMessageDialog(this, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		result = true;
		dispose();
	}
	
	private void btnCancelActionPerformed() {
		int choice = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn hủy không?", "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            result = false;
            dispose();
        }
	}
	
	public static boolean getResult() {
		return result;
	}

	
}