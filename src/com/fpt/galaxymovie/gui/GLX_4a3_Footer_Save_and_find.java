/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.gui;

import com.fpt.galaxymovie.controller.KeepFiles;
import com.fpt.galaxymovie.controller.ReadWriteFile;
import com.fpt.galaxymovie.database.helper.Save_helper;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.validate.CheckInvalid;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public final class GLX_4a3_Footer_Save_and_find extends javax.swing.JDialog {
    DefaultTableModel tbModel;
    String sql = "SELECT * FROM KhachHang";
    String sql_insert_kh = "INSERT INTO KhachHang VALUES (?,?,?)";
    String path = "E:\\SAVE-JAVA\\DuAnOne\\src\\com\\fpt\\galaxymovie\\programing file\\ma_khachhang.txt";

    public GLX_4a3_Footer_Save_and_find(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.initTable();
        this.findCustomers();
    }
    
    public void initTable(){
        String[] header = {"Mã Khách Hàng", "Tên Khách Hàng", "Số điện thoại"};
        tbModel = new DefaultTableModel(header, 0);
        try {
            ResultSet rs = ConnectSQL.getPreparedStatement(sql).executeQuery();
            while (rs.next()) {                
                Object[] rows = new Object[]{
                    rs.getString(1), rs.getString(2), rs.getString(3)
                };
                tbModel.addRow(rows);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", 0);
        }
        tb_KhachHang.setModel(tbModel);
        tb_KhachHang.getTableHeader().setReorderingAllowed(false);
    }
    
    public int getMaKhachHang(){
        try {
            String maVe = KeepFiles.readTXT(path);
            return Integer.parseInt(maVe);
        } catch (Exception e) {
            return -1;
        }
    }
    
    public void findCustomers(){
        int n = tb_KhachHang.getRowCount();
        if (txtNameCustomer.getText().isEmpty() && txtPhoneCustomer.getText().isEmpty()) {
        }
        else {
            new Thread(){
                @Override
                public void run(){
                    String name = txtNameCustomer.getText().trim();
                    String phone = txtPhoneCustomer.getText().trim();
                    try{ 
                        for (int i = 0; i < n; i++) {
                            tb_KhachHang.setRowSelectionInterval(i, i);
                            String col_1 = (String) tb_KhachHang.getValueAt(i, 1);
                            String col_2 = (String) tb_KhachHang.getValueAt(i, 2);
                            jScrollPane1.getVerticalScrollBar().setValue( i * 22 - 40);

                            if (name.equalsIgnoreCase(col_1.trim()) || phone.equalsIgnoreCase(col_2.trim())) {
                                Save_helper.tenKH = name;
                                break;
                            }
                            Thread.sleep(7);
                        }
                    }
                    catch (Exception e){
                        JOptionPane.showMessageDialog(null, e.getMessage(), "", 0);
                    }
                }
            }.start();
        }
    }
    
    public void insertKhachHang(){
        if (txtNameCustomer.getText().trim().isEmpty() || txtPhoneCustomer.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống thông tin", "Lỗi để trống", 2);
        }
        else if (CheckInvalid.checkPhone(txtPhoneCustomer.getText()) == false) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ !", "Lỗi số điện thoại", 2);
        }
        else{
            try {
                PreparedStatement ps = ConnectSQL.getPreparedStatement(sql_insert_kh);
                ps.setString(1, "KH" + getMaKhachHang());
                ps.setString(2, txtNameCustomer.getText());
                ps.setString(3, txtPhoneCustomer.getText());
                ps.execute();
                ps.close();
                JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thành công !");
                ReadWriteFile.writeTXT(String.valueOf(getMaKhachHang()+1), path);
                Save_helper.tenKH = txtNameCustomer.getText();
                this.initTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "", 0);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNameCustomer = new javax.swing.JTextField();
        txtPhoneCustomer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_KhachHang = new javax.swing.JTable();
        btnFind = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("TÊN KHÁCH HÀNG : ");

        jLabel2.setText("SỐ ĐIỆN THOẠI : ");

        tb_KhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tb_KhachHang.setRowHeight(22);
        jScrollPane1.setViewportView(tb_KhachHang);

        btnFind.setText("Tìm kiếm");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 204, 204));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setText("TRỞ LẠI GIAO DIỆN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(txtNameCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNameCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPhoneCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFind)
                            .addComponent(btnThem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        findCustomers();
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.insertKhachHang();
    }//GEN-LAST:event_btnThemActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(GLX_4a3_Footer_Save_and_find.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GLX_4a3_Footer_Save_and_find.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GLX_4a3_Footer_Save_and_find.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_4a3_Footer_Save_and_find.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            GLX_4a3_Footer_Save_and_find dialog = new GLX_4a3_Footer_Save_and_find(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tb_KhachHang;
    private javax.swing.JTextField txtNameCustomer;
    private javax.swing.JTextField txtPhoneCustomer;
    // End of variables declaration//GEN-END:variables
}
