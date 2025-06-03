package GUI;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;

import DAO.LichLamViecDAO;
import DAO.ThuocDAO;
import connectDB.ConnectDB;



@SuppressWarnings("serial")
public class Dialog_ChiTietLich extends JDialog {
	private JTextField txtMaLich;
	private JTextField txtGhi;
	private JDateChooser dateChooserNgay;
	private JLabel lblNgay;
	private JLabel lblGhiChu;
	private JButton btnSave;
	private JButton btnCancel;
	private boolean isUpdate = false;
	private LichLamViecDAO lich = new LichLamViecDAO();
	private JComboBox comboBoxMaNV;
	private JComboBox comboBoxMaCa;

	public Dialog_ChiTietLich(Frame owner, boolean isUpdate) {
		super(owner, !isUpdate ? "Thêm lịch làm việc mới" : "Chỉnh Sửa Lịch", true);
		setFont(new Font("Segoe UI", Font.BOLD, 13));
		setIconImage(Toolkit.getDefaultToolkit().getImage("icon\\medicine.png"));
		setSize(633, 682);
		setLocationRelativeTo(owner);
		getContentPane().setLayout(new BorderLayout());

		JPanel formPanel = new JPanel();
		formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		formPanel.setLayout(null);

		// Add form fields
		JLabel lblMaLLV = new JLabel("Mã lịch:");
		lblMaLLV.setBounds(21, 25, 220, 40);
		lblMaLLV.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblMaLLV);
		txtMaLich = new JTextField();
		txtMaLich.setEditable(false);
		txtMaLich.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtMaLich.setBounds(264, 29, 326, 40);
		formPanel.add(txtMaLich);

		JLabel lblMaNV = new JLabel("Mã nhân viên:");
		lblMaNV.setBounds(21, 88, 220, 40);
		lblMaNV.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblMaNV);

		JLabel lblMaCa = new JLabel("Mã ca:");
		lblMaCa.setBounds(21, 148, 220, 40);
		lblMaCa.setFont(new Font("Segoe UI", Font.BOLD, 17));
		formPanel.add(lblMaCa);

		lblNgay = new JLabel("Ngày:");
		lblNgay.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblNgay.setBounds(21, 209, 220, 40);
		formPanel.add(lblNgay);
		dateChooserNgay = new JDateChooser();
		dateChooserNgay.setDateFormatString("dd/MM/yyyy");
		dateChooserNgay.setBounds(264, 209, 326, 40);
		dateChooserNgay.getDateEditor().getUiComponent().setFont(new Font("Segoe UI", Font.PLAIN, 18));
		formPanel.add(dateChooserNgay);

		lblGhiChu = new JLabel("Ghi chú:");
		lblGhiChu.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblGhiChu.setBounds(21, 271, 220, 40);
		formPanel.add(lblGhiChu);
		txtGhi = new JTextField();
		txtGhi.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		txtGhi.setBounds(264, 271, 326, 40);
		formPanel.add(txtGhi);

		btnSave = new JButton("Lưu");
		btnSave.setIcon(new ImageIcon("icon\\save.png"));
		btnSave.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnSave.setBounds(291, 584, 142, 51);
		formPanel.add(btnSave);

		btnCancel = new JButton("Hủy");
		btnCancel.setIcon(new ImageIcon("icon\\cancel.png"));
		btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnCancel.setBounds(467, 584, 142, 51);
		formPanel.add(btnCancel);

		getContentPane().add(formPanel, BorderLayout.CENTER);
		
		comboBoxMaCa = new JComboBox();
		comboBoxMaCa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxMaCa.setBounds(264, 151, 250, 40);
		formPanel.add(comboBoxMaCa);
		// Load data to comboBoxMaCa
		loadMaCaToComboBox();
		
		comboBoxMaNV = new JComboBox();
		comboBoxMaNV.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxMaNV.setBounds(264, 88, 250, 40);
		formPanel.add(comboBoxMaNV);
		
		// Load data to comboBoxMaNV
		loadMaNVToComboBox();

		btnSave.addActionListener(e -> {
			try {
				btnSaveActionPerformed();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		btnCancel.addActionListener(e -> dispose());
	}
	
	public void loadMaNVToComboBox() {
	    String sql = "SELECT maNV FROM NhanVien";
	    try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
	         PreparedStatement stmt = con.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        comboBoxMaNV.removeAllItems(); // Clear existing items
	        while (rs.next()) {
	            comboBoxMaNV.addItem(rs.getString("maNV"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Error loading maNV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	public void loadMaCaToComboBox() {
		String sql = "SELECT maCa FROM CaLamViec";
		try (Connection con = ConnectDB.getConnection("DB_QuanLyNhaThuoc");
				PreparedStatement stmt = con.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			comboBoxMaCa.removeAllItems(); // Clear existing items
			while (rs.next()) {
				comboBoxMaCa.addItem(rs.getString("maCa"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading maCa: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void hienThiLich(String maLich, String maNV, String maCa, String ngay, String ghiChu) {
		isUpdate = true;
		txtMaLich.setText(maLich);
		comboBoxMaNV.setSelectedItem(maNV);
		comboBoxMaCa.setSelectedItem(maCa);
		// Set date in dateChooser
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date date = sdf.parse(ngay);
			dateChooserNgay.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txtGhi.setText(ghiChu);
	}
	public void hienThiMaLich(String maLich) {
		isUpdate = false;
		txtMaLich.setText(maLich);
		txtMaLich.setEditable(false);
	}
	 
	// Class kiemTraNhap
	public class kiemTraNhap {
		public static String validateNgay(String ngay) {
		    if (ngay == null || ngay.trim().isEmpty()) {
		        return "Ngày không được để trống.";
		    }

		    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    sdf.setLenient(false); // Ensure strict date parsing
		    try {
		        Date inputDate = sdf.parse(ngay);
		        Date currentDate = new Date();

		        if (!inputDate.after(currentDate)) {
		            return "Ngày phải lớn hơn ngày hiện tại.";
		        }
		    } catch (ParseException e) {
		        return "Ngày không hợp lệ. Định dạng phải là dd/MM/yyyy.";
		    }
		    return null; // No error
	}
}
	
	// Xự kiện nút Lưu
	private void btnSaveActionPerformed() throws ParseException {
	    String maLich = txtMaLich.getText().trim();
	    String maNV = (String) comboBoxMaNV.getSelectedItem();
	    String maCa = (String) comboBoxMaCa.getSelectedItem();
	    Date ngay = dateChooserNgay.getDate();
	    String ghiChu = txtGhi.getText().trim();

	    if (ngay == null) {
	        JOptionPane.showMessageDialog(this, "Ngày không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    String ngayStr = sdf.format(ngay);
	    String errorMessage = kiemTraNhap.validateNgay(ngayStr);

	    if (errorMessage != null) {
	        JOptionPane.showMessageDialog(this, errorMessage, "Lỗi", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (isUpdate) {
	        lich.updateLichLamViec(maLich, maNV, ngayStr, maCa, ghiChu);
	    } else {
	        lich.addLichLamViec(maLich, maNV, ngayStr, maCa, ghiChu);
	    }

	    JOptionPane.showMessageDialog(Dialog_ChiTietLich.this,
	        isUpdate ? "Cập nhật thông tin lịch thành công!" : "Thêm lịch mới thành công!");
	    dispose();
	}

}

	
