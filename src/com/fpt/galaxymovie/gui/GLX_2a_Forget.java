package com.fpt.galaxymovie.gui;

import com.fpt.galaxymovie.controller.*;
import com.fpt.galaxymovie.database.helper.Save_helper;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.validate.CheckInvalid;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public final class GLX_2a_Forget extends javax.swing.JDialog {
    final String sql_mk = "UPDATE TaiKhoan SET MatKhau=? WHERE NhanVienID = (SELECT ID FROM NhanVien WHERE Email=?)";
    int time = 0;
    public GLX_2a_Forget(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public void checkEmailValid (){
        if (CheckInvalid.checkForget(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Thông tin không được để trống !", "Lỗi để trống", 3);
        }
        else if (CheckInvalid.checkEmail(txtEmail.getText()) == false) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ !", "Lỗi Email", 0);
        }
        else {
            boolean kq = SendMail.send(txtEmail.getText());
            if (kq) {
                String[] code_split = Save_helper.randomCode.split("-");
                String cell_1 = code_split[0];
                String cell_2 = code_split[1];
                String cell_3 = code_split[2];
                String cell_4 = code_split[3];
                code1.setText(cell_1);
                code2.setText(cell_2);
                code3.setText(cell_3);
                code4.setText(cell_4);
            }
        }
    }
    
    public void checkConfirm(){
        if (time == 0) {
            String[] code_split = Save_helper.randomCode.split("-");
            String cell_1 = code_split[0];
            String cell_2 = code_split[1];
            String cell_3 = code_split[2];
            String cell_4 = code_split[3];
            if (code1.getText().equals(cell_1) && code2.getText().equals(cell_2) && 
                code3.getText().equals(cell_3) && code4.getText().equals(cell_4)) {
                
                try {
                    Thread.sleep(600);
                    this.showPanelSetting(pn_change_pass);
                    time++;
                } catch (InterruptedException e){ time = 0; }
            }
            else {
                JOptionPane.showMessageDialog(this, "Mã code không hợp lệ\nVui lòng kiểm tra lại email", "Lỗi gửi mã", 2);
            }
        }
        else {
            try {
                this.showPanelSetting(pn_change_pass);
                String email = txtEmail.getText().trim();
                String pass1 = txtPass1.getText().trim();
                String pass2 = txtPass2.getText().trim();
                if (CheckInvalid.checkChangePass(pass1, pass2)) {
                    JOptionPane.showMessageDialog(this, "Bạn không thể để trống các thông tin !!", "Lỗi để trống", 0);
                }
                else if (pass1.equals(pass2) == false) {
                    JOptionPane.showMessageDialog(this, "Password mới không trùng với Password xác nhận !!", "Lỗi password", 2);
                }
                else if (pass1.length() < 5 || pass2.length() < 5) {
                    JOptionPane.showMessageDialog(this, "Password phải lớn hơn 5 ký tự !!", "Lỗi password", 2);
                }
                else {
                    try {
                        Thread.sleep(700);
                        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql_mk);
                        ps.setString(1, pass1);
                        ps.setString(2, email);
                        ps.execute();
                        JOptionPane.showMessageDialog(this, "Đổi mật khẩu đã thành công !!", "Thành công", 1);
                        MovePage.moveJDialogToJDialog(new GLX_2_Login(new javax.swing.JFrame(), true), this);
                    } catch (HeadlessException | SQLException e) {
                    }
                }
            } catch (HeadlessException | InterruptedException e) {JOptionPane.showMessageDialog(this, e.getMessage(), "", 0);}
        }
    }

    public void showPanelSetting(JPanel tab){
        pn_change_pass.setVisible(false);
        pn_xacnhan.setVisible(false);
        tab.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnDangNhap = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnCancel = new javax.swing.JLabel();
        pn_main = new javax.swing.JPanel();
        pn_xacnhan = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        code1 = new javax.swing.JTextField();
        code2 = new javax.swing.JTextField();
        code3 = new javax.swing.JTextField();
        code4 = new javax.swing.JTextField();
        txtSendCode = new javax.swing.JLabel();
        pn_change_pass = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPass1 = new javax.swing.JPasswordField();
        txtPass2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(242, 242, 242));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("QUÊN MẬT KHẨU");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 11, 470, 43));

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBackground(new java.awt.Color(49, 163, 227));

        btnDangNhap.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnDangNhap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDangNhap.setText("XÁC NHẬN");
        btnDangNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDangNhapMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 153, 153));

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCancel.setText("HỦY BỎ");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        pn_main.setBackground(new java.awt.Color(242, 242, 242));
        pn_main.setToolTipText("");
        pn_main.setLayout(new java.awt.CardLayout());

        pn_xacnhan.setBackground(new java.awt.Color(242, 242, 242));
        pn_xacnhan.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Nhập email của bạn :");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmail.setMargin(new java.awt.Insets(2, 10, 2, 2));
        txtEmail.setName(""); // NOI18N

        code1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        code1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        code1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        code2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        code2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        code2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        code3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        code3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        code3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        code4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        code4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        code4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtSendCode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSendCode.setForeground(new java.awt.Color(0, 153, 255));
        txtSendCode.setText("Gửi lại mã code ?");
        txtSendCode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtSendCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSendCodeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pn_xacnhanLayout = new javax.swing.GroupLayout(pn_xacnhan);
        pn_xacnhan.setLayout(pn_xacnhanLayout);
        pn_xacnhanLayout.setHorizontalGroup(
            pn_xacnhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_xacnhanLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pn_xacnhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(pn_xacnhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pn_xacnhanLayout.createSequentialGroup()
                            .addGroup(pn_xacnhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(code1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSendCode))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(code2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(code3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(code4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        pn_xacnhanLayout.setVerticalGroup(
            pn_xacnhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_xacnhanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(txtSendCode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_xacnhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(code1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(code2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(code3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(code4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pn_main.add(pn_xacnhan, "card2");

        pn_change_pass.setBackground(new java.awt.Color(242, 242, 242));
        pn_change_pass.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Password mới : ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Password xác nhận lại : ");

        txtPass1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        txtPass2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout pn_change_passLayout = new javax.swing.GroupLayout(pn_change_pass);
        pn_change_pass.setLayout(pn_change_passLayout);
        pn_change_passLayout.setHorizontalGroup(
            pn_change_passLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_change_passLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pn_change_passLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(txtPass1, javax.swing.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                    .addComponent(txtPass2))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pn_change_passLayout.setVerticalGroup(
            pn_change_passLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_change_passLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pn_main.add(pn_change_pass, "card3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80))
            .addComponent(pn_main, javax.swing.GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(pn_main, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 470, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangNhapMouseClicked
        this.checkConfirm();
    }//GEN-LAST:event_btnDangNhapMouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        MovePage.moveJDialogToJDialog(new GLX_2_Login(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_btnCancelMouseClicked

    private void txtSendCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSendCodeMouseClicked
        txtSendCode.setForeground(new Color(153,51,255));
        this.checkEmailValid();
    }//GEN-LAST:event_txtSendCodeMouseClicked

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(GLX_2a_Forget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GLX_2a_Forget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GLX_2a_Forget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_2a_Forget.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            GLX_2a_Forget dialog = new GLX_2a_Forget(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel btnCancel;
    private javax.swing.JLabel btnDangNhap;
    private javax.swing.JTextField code1;
    private javax.swing.JTextField code2;
    private javax.swing.JTextField code3;
    private javax.swing.JTextField code4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel pn_change_pass;
    private javax.swing.JPanel pn_main;
    private javax.swing.JPanel pn_xacnhan;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtPass1;
    private javax.swing.JPasswordField txtPass2;
    private javax.swing.JLabel txtSendCode;
    // End of variables declaration//GEN-END:variables
}
