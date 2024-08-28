/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.gui;

import com.fpt.galaxymovie.controller.*;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.database.helper.Save_helper;
import javax.swing.JOptionPane;
import com.fpt.galaxymovie.validate.CheckInvalid;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class GLX_4ab_changePassword extends javax.swing.JDialog {
    final String plh_pass_moi = "* Enter your new password ...";
    final String plh_pass_confirm = "* Enter your confirm password ...";
    final String sql = "SELECT * FROM TaiKhoan";
    final String sql_mk = "UPDATE TaiKhoan SET MatKhau=? WHERE NhanVienID=?";
    
    public GLX_4ab_changePassword(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.initProgram();
        this.checkAccount();
    }
    
    public void initProgram(){
        btnDoimatkhau.requestFocus();
        txtID_forget.setEnabled(false);
        txtPassword_forget.setEnabled(false);
        Placeholder.focusLostPassword(txtPasswordNew_forget, plh_pass_moi);
        Placeholder.focusLostPassword(txtPasswordConfirm_forget, plh_pass_confirm);
    }
    
    public boolean checkAccount(){
        boolean kq = false;
        try {
            String user = Save_helper.taikhoan.getMaNV();
            String pass = Save_helper.taikhoan.getMatKhau();
            txtID_forget.setText(user);
            txtPassword_forget.setText(pass);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", 0);
        }
        return kq;
    }
    
    public void checkForget(){
        String user = txtID_forget.getText();
        String pass2 = txtPasswordNew_forget.getText().trim();
        String pass3 = txtPasswordConfirm_forget.getText().trim();
        try {
            if (CheckInvalid.checkChangePass(pass2, pass3)) {
                JOptionPane.showMessageDialog(this, "Bạn không thể để trống các thông tin !", "Lỗi để trống", 0);
            }
            else if (pass2.equals(pass3) == false) {
                JOptionPane.showMessageDialog(this, "Password mới không trùng với Password xác nhận !", "Lỗi password", 2);
            }
            else if (pass2.length() < 5 || pass3.length() < 5) {
                JOptionPane.showMessageDialog(this, "Password phải lớn hơn 5 ký tự !!", "Lỗi password", 2);
            }
            else {
                PreparedStatement ps = ConnectSQL.getPreparedStatement(sql_mk);
                ps.setString(1, pass2);
                ps.setString(2, user);
                ps.execute();
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu đã thành công !!", "Thành công", 1);
                MovePage.moveJDialogToJDialog(new GLX_2_Login(new javax.swing.JFrame(), true), this);
            }
        } catch (HeadlessException | SQLException e) {
        }
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            this.checkForget();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtID_forget = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPassword_forget = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtPasswordNew_forget = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtPasswordConfirm_forget = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        btnDoimatkhau = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnThoatra = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(250, 250, 250));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ĐỔI MẬT KHẨU");

        jLabel3.setText("Username : ");

        txtID_forget.setMargin(new java.awt.Insets(2, 10, 2, 2));

        jLabel4.setText("Password : ");

        txtPassword_forget.setMargin(new java.awt.Insets(2, 10, 2, 2));

        jLabel5.setText("Password mới : ");

        txtPasswordNew_forget.setMargin(new java.awt.Insets(2, 10, 2, 2));
        txtPasswordNew_forget.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordNew_forgetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordNew_forgetFocusLost(evt);
            }
        });
        txtPasswordNew_forget.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordNew_forgetKeyPressed(evt);
            }
        });

        jLabel6.setText("Xác nhận password : ");

        txtPasswordConfirm_forget.setMargin(new java.awt.Insets(2, 10, 2, 2));
        txtPasswordConfirm_forget.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordConfirm_forgetFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordConfirm_forgetFocusLost(evt);
            }
        });
        txtPasswordConfirm_forget.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordConfirm_forgetKeyPressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(49, 163, 227));

        btnDoimatkhau.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDoimatkhau.setForeground(new java.awt.Color(255, 255, 255));
        btnDoimatkhau.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDoimatkhau.setText("Xác nhận");
        btnDoimatkhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoimatkhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDoimatkhauMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDoimatkhau, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDoimatkhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 153, 153));

        btnThoatra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThoatra.setForeground(new java.awt.Color(255, 255, 255));
        btnThoatra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnThoatra.setText("Trở lại");
        btnThoatra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThoatra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThoatraMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThoatra, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThoatra, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtPasswordConfirm_forget, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(txtPasswordNew_forget, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(txtID_forget)
                                .addComponent(txtPassword_forget, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtID_forget, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword_forget, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(9, 9, 9)
                .addComponent(txtPasswordNew_forget, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPasswordConfirm_forget, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThoatraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoatraMouseClicked
        btnThoatra.requestFocus();
        if (JOptionPane.showConfirmDialog(this, "Bạn có muốn hủy thao tác không ?", "", 0) == JOptionPane.YES_OPTION) {
            MovePage.moveJDialogToJDialog(new GLX_0_Shotcut(new javax.swing.JFrame(), true), this);
        }
    }//GEN-LAST:event_btnThoatraMouseClicked

    private void btnDoimatkhauMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoimatkhauMouseClicked
        this.checkForget();
    }//GEN-LAST:event_btnDoimatkhauMouseClicked

    private void txtPasswordConfirm_forgetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordConfirm_forgetFocusLost
        Placeholder.focusLostPassword(txtPasswordConfirm_forget, plh_pass_confirm);
    }//GEN-LAST:event_txtPasswordConfirm_forgetFocusLost

    private void txtPasswordConfirm_forgetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordConfirm_forgetFocusGained
        Placeholder.focusGaindPassword(txtPasswordConfirm_forget, plh_pass_confirm);
    }//GEN-LAST:event_txtPasswordConfirm_forgetFocusGained

    private void txtPasswordNew_forgetFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordNew_forgetFocusLost
        Placeholder.focusLostPassword(txtPasswordNew_forget, plh_pass_moi);
    }//GEN-LAST:event_txtPasswordNew_forgetFocusLost

    private void txtPasswordNew_forgetFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordNew_forgetFocusGained
        Placeholder.focusGaindPassword(txtPasswordNew_forget, plh_pass_moi);
    }//GEN-LAST:event_txtPasswordNew_forgetFocusGained

    private void txtPasswordNew_forgetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordNew_forgetKeyPressed
        this.keyPressed(evt);
    }//GEN-LAST:event_txtPasswordNew_forgetKeyPressed

    private void txtPasswordConfirm_forgetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordConfirm_forgetKeyPressed
        this.keyPressed(evt);
    }//GEN-LAST:event_txtPasswordConfirm_forgetKeyPressed

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
            java.util.logging.Logger.getLogger(GLX_4ab_changePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GLX_4ab_changePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GLX_4ab_changePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_4ab_changePassword.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            GLX_4ab_changePassword dialog = new GLX_4ab_changePassword(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnDoimatkhau;
    private javax.swing.JLabel btnThoatra;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtID_forget;
    private javax.swing.JPasswordField txtPasswordConfirm_forget;
    private javax.swing.JPasswordField txtPasswordNew_forget;
    private javax.swing.JPasswordField txtPassword_forget;
    // End of variables declaration//GEN-END:variables
}
