package com.fpt.galaxymovie.gui;

import com.fpt.galaxymovie.controller.DateHelper;
import java.sql.*;
import com.fpt.galaxymovie.database.dao.DAONhanVien;
import com.fpt.galaxymovie.controller.MovePage;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.database.helper.Save_helper;
import com.fpt.galaxymovie.models.NhanVien;
import com.fpt.galaxymovie.validate.CheckInvalid;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
/**
 *
 * @author admin
 */
public final class GLX_4a1_Account extends javax.swing.JDialog {
    String sql_nhanvien = """
                          SELECT HoTen, CMND, DiaChi, Email, SDT, NgaySinh, GioiTinh, VaiTro FROM NhanVien
                          INNER JOIN TaiKhoan ON NhanVien.ID = TaiKhoan.NhanVienID
                          WHERE TaiKhoan.NhanVienID=?""";
    
    String VN = "change_language_vn";
    String EN = "change_language_en";
    ResourceBundle resourceBundle = null;
    
    public GLX_4a1_Account(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.Fill_info();
        this.changeLG(Save_helper.Language);
    }
    
    public void hide_input(){
        txt_account_name.setEnabled(false);
        txt_account_cmnd.setEnabled(false);
        txt_account_gender.setEnabled(false);
        txt_account_address.setEnabled(false);
        txt_account_email.setEnabled(false);
        txt_account_phone.setEnabled(false);
        txt_account_birthday.setEnabled(false);
        txt_account_role.setEnabled(false);
        btn_account_save.setEnabled(false);
    }
    
    public void show_input(){
        txt_account_name.setEnabled(true);
        txt_account_cmnd.setEnabled(true);
        txt_account_gender.setEnabled(true);
        txt_account_address.setEnabled(true);
        txt_account_email.setEnabled(true);
        txt_account_phone.setEnabled(true);
        txt_account_birthday.setEnabled(true);
        txt_account_role.setEnabled(true);
        btn_account_save.setEnabled(true);
    }
    
    void changeLG (String ngonngu){
        Save_helper.Language = ngonngu;
        try {
            switch (ngonngu) {
                // file propertice phải đưa sang build -> classes thay vì để ở src như này
                case "vn" -> {
                    resourceBundle = ResourceBundle.getBundle(VN);
                    Save_helper.Language = "vn";
                }
                case "en" -> {
                    resourceBundle = ResourceBundle.getBundle(EN);                    
                    Save_helper.Language = "en";

                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Không hỗ trợ ngôn ngữ " );
                }
            }
            lb_account_name.setText(resourceBundle.getString("lb_account_name"));
            lb_account_cmnd.setText(resourceBundle.getString("lb_account_cmnd"));
            lb_account_gender.setText(resourceBundle.getString("lb_account_gender"));
            lb_account_address.setText(resourceBundle.getString("lb_account_address"));
            lb_account_email.setText(resourceBundle.getString("lb_account_email"));
            lb_account_phone.setText(resourceBundle.getString("lb_account_phone"));
            lb_account_birth.setText(resourceBundle.getString("lb_account_birth"));
            lb_account_role.setText(resourceBundle.getString("lb_account_role"));

            btn_account_update.setText(resourceBundle.getString("btn_account_update"));
            btn_account_save.setText(resourceBundle.getString("btn_account_save"));
            btn_account_changepass.setText(resourceBundle.getString("btn_account_changepass"));
        } catch (Exception e) {
        } 
    }
    
    public void Fill_info(){
        this.hide_input();
        String maNV = Save_helper.taikhoan.getMaNV().trim();
        try {
            lb_account_id.setText("-- MÃ NHÂN VIÊN : " + maNV);
            PreparedStatement ps  = ConnectSQL.getPreparedStatement(sql_nhanvien);
            ps.setString(1, maNV);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                txt_account_name.setText(rs.getString(1).trim());
                txt_account_cmnd.setText(rs.getString(2).trim());
                txt_account_address.setText(rs.getString(3).trim());
                txt_account_email.setText(rs.getString(4).trim());
                txt_account_phone.setText(rs.getString(5).trim());
                txt_account_birthday.setText((DateHelper.toString(rs.getDate(6))));
                txt_account_gender.setSelectedIndex(rs.getInt(7));
                txt_account_role.setSelectedIndex(rs.getInt(8));
                break;
            }
        } catch (Exception e) {
        }
    }
    
    public void Save_info(){
        String maNV = Save_helper.taikhoan.getMaNV().trim();
        String ten = txt_account_name.getText().trim();
        String cmnd = txt_account_cmnd.getText().trim();
        String diachi = txt_account_address.getText().trim();
        String email = txt_account_email.getText().trim();
        String sdt = txt_account_phone.getText().trim();
        String birth = txt_account_birthday.getText().trim();
        if (CheckInvalid.checkNhanVien(ten, cmnd, diachi, email, sdt, birth)) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin !", "Lỗi nhân viên", 2);
        }
        else if (CheckInvalid.checkCMND(cmnd) == false) {
            JOptionPane.showMessageDialog(this, "CMND của bạn không hợp lệ !", "Lỗi CMND", 1);
        }
        else if (CheckInvalid.checkEmail(email) == false) {
            JOptionPane.showMessageDialog(this, "Email của bạn phải đúng định dạng : abc@example.abc", "Lỗi email", 2);
        }
        else if (CheckInvalid.checkPhone(sdt) == false) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không đúng định dạng Việt Nam", "Lỗi số điện thoại", 2);
        }
        else if (CheckInvalid.checkDate(birth) == false) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải đúng định dạng ngày-tháng-năm (dd-MM-yyyy)", "Lỗi ngày sinh", 2);
        }
        else {
            NhanVien nv = new NhanVien();
            nv.setID(maNV);
            nv.setHoTen(ten);
            nv.setCMND(cmnd);
            nv.setGioiTinh(txt_account_gender.getSelectedIndex());
            nv.setDiaChi(diachi);
            nv.setEmail(email);
            nv.setSdt(sdt);
            nv.setNgaySinh((Date) DateHelper.Convert_String_To_Date_SQL(birth));
            DAONhanVien.updateDB(nv);
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin cá nhân thành công !!");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_account_container = new javax.swing.JPanel();
        pn_account = new javax.swing.JPanel();
        lb_account_id = new javax.swing.JLabel();
        lb_account_name = new javax.swing.JLabel();
        txt_account_name = new javax.swing.JTextField();
        lb_account_cmnd = new javax.swing.JLabel();
        txt_account_cmnd = new javax.swing.JTextField();
        lb_account_address = new javax.swing.JLabel();
        txt_account_address = new javax.swing.JTextField();
        lb_account_email = new javax.swing.JLabel();
        txt_account_email = new javax.swing.JTextField();
        lb_account_phone = new javax.swing.JLabel();
        txt_account_phone = new javax.swing.JTextField();
        lb_account_birth = new javax.swing.JLabel();
        txt_account_birthday = new javax.swing.JTextField();
        lb_account_role = new javax.swing.JLabel();
        txt_account_role = new javax.swing.JComboBox<>();
        btn_account_update = new javax.swing.JButton();
        btn_account_save = new javax.swing.JButton();
        btn_account_changepass = new javax.swing.JButton();
        lb_account_gender = new javax.swing.JLabel();
        txt_account_gender = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pn_account_container.setBackground(new java.awt.Color(255, 255, 255));

        pn_account.setBackground(new java.awt.Color(247, 247, 247));
        pn_account.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4), "Thông tin cá nhân", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        lb_account_id.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_account_id.setText("-- MÃ NHÂN VIÊN : NV17");

        lb_account_name.setText("TÊN NHÂN VIÊN : ");

        lb_account_cmnd.setText("CHỨNG MINH NHÂN DÂN : ");

        lb_account_address.setText("ĐỊA CHỈ : ");

        lb_account_email.setText("EMAIL : ");

        lb_account_phone.setText("SỐ ĐIỆN THOẠI : ");

        lb_account_birth.setText("NGÀY SINH : ");

        lb_account_role.setText("VAI TRÒ : ");

        txt_account_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý" }));

        btn_account_update.setBackground(new java.awt.Color(102, 204, 255));
        btn_account_update.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_account_update.setForeground(new java.awt.Color(255, 255, 255));
        btn_account_update.setText("SỬA THÔNG TIN");
        btn_account_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_account_updateActionPerformed(evt);
            }
        });

        btn_account_save.setBackground(new java.awt.Color(153, 255, 153));
        btn_account_save.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_account_save.setText("LƯU THAY ĐỔI");
        btn_account_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_account_saveActionPerformed(evt);
            }
        });

        btn_account_changepass.setBackground(new java.awt.Color(255, 153, 153));
        btn_account_changepass.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btn_account_changepass.setForeground(new java.awt.Color(255, 255, 255));
        btn_account_changepass.setText("ĐỔI MẬT KHẨU");
        btn_account_changepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_account_changepassActionPerformed(evt);
            }
        });

        lb_account_gender.setText("GIỚI TÍNH : ");

        txt_account_gender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        javax.swing.GroupLayout pn_accountLayout = new javax.swing.GroupLayout(pn_account);
        pn_account.setLayout(pn_accountLayout);
        pn_accountLayout.setHorizontalGroup(
            pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_accountLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lb_account_id, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_accountLayout.createSequentialGroup()
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_accountLayout.createSequentialGroup()
                        .addContainerGap(137, Short.MAX_VALUE)
                        .addComponent(btn_account_update, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_account_save, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_account_changepass, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_accountLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_account_address)
                            .addComponent(lb_account_cmnd)
                            .addComponent(lb_account_name)
                            .addComponent(txt_account_name, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(txt_account_cmnd)
                            .addComponent(txt_account_address)
                            .addComponent(lb_account_gender)
                            .addComponent(txt_account_gender, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_account_email)
                            .addComponent(txt_account_email, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(lb_account_phone)
                            .addComponent(txt_account_phone, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(lb_account_birth)
                            .addComponent(txt_account_birthday, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                            .addComponent(lb_account_role)
                            .addComponent(txt_account_role, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(21, 21, 21))
        );
        pn_accountLayout.setVerticalGroup(
            pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_accountLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_accountLayout.createSequentialGroup()
                        .addComponent(lb_account_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_account_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_accountLayout.createSequentialGroup()
                        .addComponent(lb_account_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_account_name, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_accountLayout.createSequentialGroup()
                        .addComponent(lb_account_phone)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_account_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pn_accountLayout.createSequentialGroup()
                        .addComponent(lb_account_cmnd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_account_cmnd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_account_birth)
                    .addComponent(lb_account_gender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_account_birthday, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_account_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_account_address, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_account_role, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_account_address, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txt_account_role))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(pn_accountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_account_save, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_account_update, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_account_changepass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lb_account_id)
                .addContainerGap())
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/cancel.png"))); // NOI18N
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pn_account_containerLayout = new javax.swing.GroupLayout(pn_account_container);
        pn_account_container.setLayout(pn_account_containerLayout);
        pn_account_containerLayout.setHorizontalGroup(
            pn_account_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_account_containerLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pn_account_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(pn_account, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        pn_account_containerLayout.setVerticalGroup(
            pn_account_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_account_containerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_account, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_account_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_account_container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        MovePage.moveJDialogToJDialog(new GLX_0_Shotcut(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void btn_account_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_account_updateActionPerformed
        this.show_input();
    }//GEN-LAST:event_btn_account_updateActionPerformed

    private void btn_account_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_account_saveActionPerformed
        this.Save_info();
    }//GEN-LAST:event_btn_account_saveActionPerformed

    private void btn_account_changepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_account_changepassActionPerformed
        MovePage.moveJDialogToJDialog(new GLX_4ab_changePassword(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_btn_account_changepassActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GLX_4a1_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GLX_4a1_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GLX_4a1_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_4a1_Account.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            GLX_4a1_Account dialog = new GLX_4a1_Account(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_account_changepass;
    private javax.swing.JButton btn_account_save;
    private javax.swing.JButton btn_account_update;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lb_account_address;
    private javax.swing.JLabel lb_account_birth;
    private javax.swing.JLabel lb_account_cmnd;
    private javax.swing.JLabel lb_account_email;
    private javax.swing.JLabel lb_account_gender;
    private javax.swing.JLabel lb_account_id;
    private javax.swing.JLabel lb_account_name;
    private javax.swing.JLabel lb_account_phone;
    private javax.swing.JLabel lb_account_role;
    private javax.swing.JPanel pn_account;
    private javax.swing.JPanel pn_account_container;
    private javax.swing.JTextField txt_account_address;
    private javax.swing.JTextField txt_account_birthday;
    private javax.swing.JTextField txt_account_cmnd;
    private javax.swing.JTextField txt_account_email;
    private javax.swing.JComboBox<String> txt_account_gender;
    private javax.swing.JTextField txt_account_name;
    private javax.swing.JTextField txt_account_phone;
    private javax.swing.JComboBox<String> txt_account_role;
    // End of variables declaration//GEN-END:variables
}
