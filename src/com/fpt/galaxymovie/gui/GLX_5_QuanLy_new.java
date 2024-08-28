/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.gui;

import com.fpt.galaxymovie.controller.DateHelper;
import com.fpt.galaxymovie.controller.MovePage;
import com.fpt.galaxymovie.controller.XDate;
import com.fpt.galaxymovie.database.dao.DAOPhim;
import com.fpt.galaxymovie.database.dao.DAOTaiKhoan;
import com.fpt.galaxymovie.database.helper.Save_helper;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.Phim;
import com.fpt.galaxymovie.models.TaiKhoan;
import com.fpt.galaxymovie.validate.CheckInvalid;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public final class GLX_5_QuanLy_new extends javax.swing.JFrame {
    int open_off = 1;
    String VN = "change_language_vn";
    String EN = "change_language_en";
    ResourceBundle resourceBundle = null;    
    DefaultTableModel tbModel_Staff;
    DefaultTableModel tbModel_Phim;
    DefaultTableModel tbModel_LichChieu;
    DefaultTableModel tbModel_DoanhThu;
    ArrayList<Phim> listFilm = new ArrayList<>();
    ArrayList<String> listLoaiPhim = new ArrayList<>();
    ArrayList<String> listTenPhim = new ArrayList<>();
    Calendar calendar = new GregorianCalendar();
    
    public GLX_5_QuanLy_new() {
        initComponents();
        pn_tacvu.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.fillData_NhanVien();
        this.fillData_Phim();
        this.fillData_CbxTheLoai_Phim();
        this.fillData_LichChieu();
        this.fillData_CbxTenPhim_LichChieu();
        this.fillData_DoanhThu();
        this.changeLG(Save_helper.Language);
        this.TimeClock();
    }
    
    public void TimeClock(){
        int d = calendar.get(Calendar.DATE);
        int m = calendar.get(Calendar.MONTH);
        int y = calendar.get(Calendar.YEAR);
        
        lb_month_year.setText((m+1) + "/" + y);
        lb_day.setText("" + d);
        new Thread(){
            @Override
            public void run(){
                while (true) {                    
                    Calendar calendar = new GregorianCalendar();
                    int hour = calendar.get(Calendar.HOUR);
                    int minutes = calendar.get(Calendar.MINUTE);
                    int seconds = calendar.get(Calendar.SECOND);
                    String AM_PM = (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM";
                    String time = hour + " : " + minutes + " : " + seconds + " " + AM_PM;
                    lb_time.setText(time);                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }.start();
    }
    
    public void openAndOff(){
        switch (open_off){
            case 0 -> { pn_tacvu.setVisible(false); open_off = 1; }
            case 1 -> { pn_tacvu.setVisible(true); open_off = 0; }
        }
    }
    
    public void Logout() {
        if (JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng xuất tài khoản không ?", "", 0) == JOptionPane.YES_OPTION) {
            try {
                Thread.sleep(700);
                MovePage.moveFrameToJDialog(new GLX_2_Login(new javax.swing.JFrame(), true), this);
            } catch (InterruptedException ex) {
            }
        }
    }
    
    void Hide_All_Frame(JPanel show){
        Panel_Details.setVisible(false);    
        Panel_Staff.setVisible(false);      
        show.setVisible(true);
    }
    
    void Hide_Panel_Details(JPanel show){
        Panel_Phim.setVisible(false);       
        Panel_LichChieu.setVisible(false);
        Panel_DoanhThu.setVisible(false);
        show.setVisible(true);
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
            menu_system.setText(resourceBundle.getString("menu_system"));
            menu_system_account.setText(resourceBundle.getString("menu_system_account"));
            menu_system_changepass.setText(resourceBundle.getString("menu_system_changepass"));
            menu_system_logout.setText(resourceBundle.getString("menu_system_logout"));
            menu_system_language.setText(resourceBundle.getString("menu_system_language"));
            menu_system_lan_VN.setText(resourceBundle.getString("menu_system_lan_VN"));
            menu_system_lan_EN.setText(resourceBundle.getString("menu_system_lan_EN"));
            menu_introduce.setText(resourceBundle.getString("menu_introduce"));
            menu_guide.setText(resourceBundle.getString("menu_guide"));
            menu_back.setText(resourceBundle.getString("menu_back"));

            
            
            
            btn_ChiTiet.setText(resourceBundle.getString("btn_ChiTiet"));
            btn_NhanVien.setText(resourceBundle.getString("btn_NhanVien"));
            btn_TaiKhoan.setText(resourceBundle.getString("btn_TaiKhoan"));
            btn_Phim.setText(resourceBundle.getString("btn_Phim"));
            btn_LichChieu.setText(resourceBundle.getString("btn_LichChieu"));
            btn_ChooseAction.setText(resourceBundle.getString("btn_ChooseAction"));
            btn_DoanhThu.setText(resourceBundle.getString("btn_DoanhThu"));
            
            
            btnChonHinh_Details_Phim.setText(resourceBundle.getString("btnChonHinh_Details_Phim"));
            lblMaPhim_Phim.setText(resourceBundle.getString("lblMaPhim_Phim"));
            lblTenPhim_Phim.setText(resourceBundle.getString("lblTenPhim_Phim"));
            lblTheLoai_Phim.setText(resourceBundle.getString("lblTheLoai_Phim"));
            lblThoiLuong_Phim.setText(resourceBundle.getString("lblThoiLuong_Phim")); 
            lblNgayKC_Phim.setText(resourceBundle.getString("lblNgayKC_Phim"));
            lblNgayKT_Phim.setText(resourceBundle.getString("lblNgayKT_Phim"));
            lblDaoDien_Phim.setText(resourceBundle.getString("lblDaoDien_Phim"));
            lblNSX_Phim.setText(resourceBundle.getString("lblNSX_Phim"));
            btnThem_Phim.setText(resourceBundle.getString("btnThem_Phim"));
            btnSua_Phim.setText(resourceBundle.getString("btnSua_Phim"));
            btnXoa_Phim.setText(resourceBundle.getString("btnXoa_Phim"));

            lblTenPhim_LichChieu.setText(resourceBundle.getString("lblTenPhim_LichChieu")); 
            
            lblMaNV_NhanVien.setText(resourceBundle.getString("lblMaNV_NhanVien")); 
            lblVaiTro_NhanVien.setText(resourceBundle.getString("lblVaiTro_NhanVien")); 
            lblMatKhau_NhanVien.setText(resourceBundle.getString("lblMatKhau_NhanVien")); 
            btnThem_Staff.setText(resourceBundle.getString("btnThem_Staff")); 
            btnSua_Staff.setText(resourceBundle.getString("btnSua_Staff")); 
            btnXoa_Staff.setText(resourceBundle.getString("btnXoa_Staff")); 
        } catch (HeadlessException e) {
        } 
    }
 //------------------------------------------------------------------------------------------------------------------------------------------------ DOANH THU
    
    

    private static JFreeChart createChart() {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            String sql = "select top 10 PhimID, SUM(Giatien) as N'Tổng Tiền', TenPhim from F_tongtien() group by PhimID,TenPhim order by SUM(Giatien) desc";
            ResultSet rs = ConnectSQL.getPreparedStatement(sql).executeQuery();
            while (rs.next()) {                
                dataset.addValue(rs.getInt("Tổng Tiền"), "Số tiền", rs.getString("PhimID"));
            }
        } catch (Exception e) {
        }
        // tạo mẫu dataset mặc định
            
        // tạo 1 biểu đồ char dựa trên thông tin dataset
        JFreeChart barChart = ChartFactory.createBarChart(
                "Top 10 Bộ Phim Có Doanh Thu Cao Nhất Của Rạp",
                "Phim", "Tổng Tiền",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        return barChart;
    }
    
    public void draw(){
        ChartPanel chartPanel = new ChartPanel(createChart());
        this.setTitle("Biểu đồ Doanh thu rap phim theo tháng");
        chartPanel.setSize(1065, 333);
        chartPanel.setVisible(true);
        bieudo.add(chartPanel);
        
    }
    
    void fillData_DoanhThu(){
        String[] header = {"PhimID", "Tên Phim", "Tổng Tiền"};
        tbModel_DoanhThu = new DefaultTableModel(header, 0);
        table_DoanhThu.setModel(tbModel_DoanhThu);
        String sql = "select top 10 PhimID, SUM(Giatien) as N'Tổng Tiền', TenPhim from F_tongtien() group by PhimID,TenPhim order by SUM(Giatien) desc";
        try {
            ResultSet rs = ConnectSQL.getResultSet(sql);
            while (rs.next()) {            
                Object[] data = new Object[]{
                    rs.getString("PhimID"),
                    rs.getString("TenPhim"),
                    rs.getInt("Tổng Tiền")
                };
                tbModel_DoanhThu.addRow(data);
            }
        } catch (Exception e) {
        }
    }

    
    
    
    
 //------------------------------------------------------------------------------------------------------------------------------------------------ LICH CHIEU
    void fillData_LichChieu(){
        String[] header = {"Tên Phim", "Phòng Chiếu", "Suất Chiếu", "Ngày Chiếu"};
        tbModel_LichChieu = new DefaultTableModel(header, 0);
        table_LichChieu.setModel(tbModel_LichChieu);
        String sql = "select TenPhim, PhongChieuID, SuatChieuID, NgayChieu from ChiTietChieuPhim inner join Phim on Phim.ID = ChiTietChieuPhim.PhimID where NgayChieu >= CAST(getdate() AS date) order by NgayChieu ";
        ResultSet rs = ConnectSQL.getResultSet(sql);
        try {
            while (rs.next()) {
                Object[] data = new Object[]{   
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                };
                tbModel_LichChieu.addRow(data);
            }
        } catch (Exception e) {
        }

    }

    
    void fillData_CbxTenPhim_LichChieu(){
        try {
            String sql = "Select id, TenPhim from Phim where NgayHetHan >= getDate()";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {            
                cbxTen_LichChieu.addItem(rs.getString("TenPhim"));                         //Đỗ tất cả mã phim dô list
                listTenPhim.add(rs.getString(1));                                                  //Đỗ tất cả mã phim dô list
            }
            ps.execute();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
    }
    
    void TenPhimTheoCBX(String s){
        String proc = "{Call sp_LichPhim(?)}";
        try {
            PreparedStatement ps = ConnectSQL.getPreparedStatement(proc);
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { 
                Object[] data = new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4)
                };
                tbModel_LichChieu.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    
    
    
 //------------------------------------------------------------------------------------------------------------------------------------------------ PHIM

    void fillData_Phim(){
        listFilm = new ArrayList<>();
        String[] header = {"Mã Phim", "Tên Phim", "Thể Loại", "Ngày Khởi Chiếu", "Ngày Kết Thúc", "Thời Lượng", "Hình Ảnh"};
        tbModel_Phim = new DefaultTableModel(header, 0);
        tablePhim_Details.setModel(tbModel_Phim);
        String sql = "select Phim.ID, TenPhim, LoaiPhim, NgayCongChieu, NgayHetHan, ThoiLuong, NSX, DaoDien, HinhAnh from Phim inner join LoaiPhim on LoaiPhim.ID = Phim.LoaiPHimID";
        ResultSet rs = ConnectSQL.getResultSet(sql);
        try {
            while (rs.next()) {   
                
                Object[] data = new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    DateHelper.PrintDate(rs.getString(4)),
                    DateHelper.PrintDate(rs.getString(5)),
                    rs.getString(6),
                    rs.getString(9)
                      
                };
                Phim phim = new Phim(rs.getString(1),  rs.getString("LoaiPhim"), rs.getString("TenPhim") 
                                    ,rs.getDate("NSX") ,rs.getString("DaoDien") ,rs.getString("HinhAnh")
                                    ,rs.getDate("NgayCongChieu"), rs.getDate("NgayHetHan"), rs.getInt("ThoiLuong"));
                listFilm.add(phim);
                tbModel_Phim.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }
    
    void ClickData_Phim(){
        int n = tablePhim_Details.getSelectedRow();
        String maPhim = (String) tablePhim_Details.getValueAt(n, 0);
        String hinhAnh = (String)tablePhim_Details.getValueAt(n, 6);
        for (int i = 0; i < listFilm.size(); i++) {
            if( maPhim.equalsIgnoreCase(listFilm.get(i).getID())){
                txtMaPhim_Details_Phim.setText(listFilm.get(i).getID());
                txtTenPhim_Details_Phim.setText(listFilm.get(i).getTenPhim());
                cbx_TheLoai_Details_Phim.setSelectedItem(listFilm.get(i).getLoaiPhimID());
                txtThoiLuong_Details_Phim.setText(listFilm.get(i).getThoiLuong()+ "");
                txtNgayKhoiChieu_Details_Phim.setText(DateHelper.PrintDate(listFilm.get(i).getNgayCongChieu() + ""));
                txtNgayKetThuc_Details_Phim.setText(DateHelper.PrintDate(listFilm.get(i).getNgayHetHan() + "" ));
                txtDaoDien_Details_Phim.setText(listFilm.get(i).getDaoDien());
                txtNSX_Details_Phim.setText(DateHelper.PrintDate(listFilm.get(i).getNSX() +""));
                ImageIcon s = new ImageIcon(this.getClass().getResource("/com/fpt/galaxymovie/icons/film/"+ hinhAnh));

                Image img = s.getImage();

                Image newImg = getScaledImage(img, lblHinh_Details_Phim.getWidth(), lblHinh_Details_Phim.getHeight());

                lblHinh_Details_Phim.setIcon(new ImageIcon(newImg));
                lblHinh_Details_Phim.setText("");
                break;
            }
        }
    }
    
    void fillData_CbxTheLoai_Phim(){
        try {
            String sql = "Select * from LoaiPhim";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {            
                cbx_TheLoai_Details_Phim.addItem(rs.getString("LoaiPhim"));                         //Đỗ tất cả mã phim dô list
                listLoaiPhim.add(rs.getString(1));                                                  //Đỗ tất cả mã phim dô list
            }
            ps.execute();
            ps.close();
            rs.close();
        } catch (Exception e) {
        }
    }
    
    
    void ChonAnh(){
        try {
            JFileChooser jfc = new JFileChooser("src\\com\\fpt\\galaxymovie\\icons\\film");
            jfc.showOpenDialog(null);
            File f = jfc.getSelectedFile();
            Image bi = ImageIO.read(f);
            Save_helper.path = f;
            lblHinh_Details_Phim.setText("");
            lblHinh_Details_Phim.setIcon(new ImageIcon(bi.getScaledInstance(lblHinh_Details_Phim.getWidth(), lblHinh_Details_Phim.getHeight(), BufferedImage.SCALE_SMOOTH)));
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    
    void reset_Phim(){
        lblHinh_Details_Phim.setIcon(null);
        txtMaPhim_Details_Phim.setText("");
        txtTenPhim_Details_Phim.setText("");
        txtThoiLuong_Details_Phim.setText("");
        txtNgayKhoiChieu_Details_Phim.setText("");
        txtNgayKetThuc_Details_Phim.setText("");
        txtDaoDien_Details_Phim.setText("");
        txtNSX_Details_Phim.setText("");
        cbx_TheLoai_Details_Phim.setSelectedIndex(0);
    }
    
 //------------------------------------------------------------------------------------------------------------------------------------------------ NHAN VIEN
    void fillData_NhanVien(){ //Đỗ lên table
        String[] header = {"Mã Nhân Viên", "Mật Khẩu", "Vai Trò", "Tên Nhân Viên"};
        tbModel_Staff = new DefaultTableModel(header, 0);
        table_Staff.setModel(tbModel_Staff);
        String sql = "select NhanVienID, MatKhau, VaiTro, HoTen from TaiKhoan INNER JOIN NhanVien ON NhanVien.ID = TaiKhoan.NhanVienID";
        ResultSet rs = ConnectSQL.getResultSet(sql);
        try {
            while (rs.next()) {            
                Object[] data = new Object[]{
                    rs.getString(1),
                    rs.getString(2),
                    (rs.getInt(3)==0) ? "Nhân Viên" : "Quản Lý",
                    rs.getString(4)
                };
                tbModel_Staff.addRow(data);
            }
        } catch (Exception e) {
        }
    }
    
    
    void ClickData_NhanVien(){ //Đỗ lên form
        int s = table_Staff.getSelectedRow();
        txtMaNV_Staff.setText(table_Staff.getValueAt(s,0).toString().trim());
        txtMatKhau_Staff.setText(table_Staff.getValueAt(s,1).toString().trim());
        cbx_VaiTro_Staff.setSelectedItem(String.valueOf(table_Staff.getValueAt(s,2)).trim() );
        txtTenNhanVien.setText(String.valueOf(table_Staff.getValueAt(s,3)));
    }
    
    void FindNhanVien(){
        try {
            String sql = "select * from TaiKhoan where NhanVienID = ?";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, txtTimKiem_Staff.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                txtMaNV_Staff.setText(rs.getString(1).trim());
                txtMatKhau_Staff.setText(rs.getString(2).trim());
                cbx_VaiTro_Staff.setSelectedIndex(rs.getInt(3));
                break;
            }
        } catch (Exception e) {
        }
    }
    
    
    void reset_NhanVien(){
        txtMaNV_Staff.setText("");
        txtMatKhau_Staff.setText("");
        txtTenNhanVien.setText("");
        cbx_VaiTro_Staff.setSelectedIndex(0);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_month_year = new javax.swing.JLabel();
        lb_day = new javax.swing.JLabel();
        lb_time = new javax.swing.JLabel();
        pn_tacvu = new javax.swing.JPanel();
        btn_ChiTiet = new javax.swing.JButton();
        btn_NhanVien = new javax.swing.JButton();
        btn_TaiKhoan = new javax.swing.JButton();
        btnTacVu = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Panel_Details = new javax.swing.JPanel();
        Menu_Details = new javax.swing.JPanel();
        btn_Phim = new javax.swing.JButton();
        btn_LichChieu = new javax.swing.JButton();
        btn_DoanhThu = new javax.swing.JButton();
        Main_Details = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Panel_Phim = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblHinh_Details_Phim = new javax.swing.JLabel();
        lblMaPhim_Phim = new javax.swing.JLabel();
        txtMaPhim_Details_Phim = new javax.swing.JTextField();
        lblTenPhim_Phim = new javax.swing.JLabel();
        txtTenPhim_Details_Phim = new javax.swing.JTextField();
        lblTheLoai_Phim = new javax.swing.JLabel();
        lblThoiLuong_Phim = new javax.swing.JLabel();
        txtThoiLuong_Details_Phim = new javax.swing.JTextField();
        lblNgayKC_Phim = new javax.swing.JLabel();
        txtNgayKhoiChieu_Details_Phim = new javax.swing.JTextField();
        lblNgayKT_Phim = new javax.swing.JLabel();
        txtNgayKetThuc_Details_Phim = new javax.swing.JTextField();
        lblDaoDien_Phim = new javax.swing.JLabel();
        txtDaoDien_Details_Phim = new javax.swing.JTextField();
        lblNSX_Phim = new javax.swing.JLabel();
        txtNSX_Details_Phim = new javax.swing.JTextField();
        btnThem_Phim = new javax.swing.JButton();
        btnSua_Phim = new javax.swing.JButton();
        btnXoa_Phim = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablePhim_Details = new javax.swing.JTable();
        cbx_TheLoai_Details_Phim = new javax.swing.JComboBox<>();
        btnChonHinh_Details_Phim = new javax.swing.JLabel();
        Panel_LichChieu = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_LichChieu = new javax.swing.JTable();
        cbxTen_LichChieu = new javax.swing.JComboBox<>();
        lblTenPhim_LichChieu = new javax.swing.JLabel();
        Panel_DoanhThu = new javax.swing.JPanel();
        bieudo = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        table_DoanhThu = new javax.swing.JTable();
        Panel_Staff = new javax.swing.JPanel();
        lblMaNV_NhanVien = new javax.swing.JLabel();
        txtMaNV_Staff = new javax.swing.JTextField();
        lblMatKhau_NhanVien = new javax.swing.JLabel();
        lblVaiTro_NhanVien = new javax.swing.JLabel();
        cbx_VaiTro_Staff = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        table_Staff = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTimKiem_Staff = new javax.swing.JTextField();
        lb_tennhanvien = new javax.swing.JLabel();
        txtTenNhanVien = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        checkPass_Staff = new javax.swing.JCheckBox();
        txtMatKhau_Staff = new javax.swing.JPasswordField();
        btnThem_Staff = new javax.swing.JButton();
        btnSua_Staff = new javax.swing.JButton();
        btnXoa_Staff = new javax.swing.JButton();
        btn_below_btnLogout = new javax.swing.JButton();
        btn_ChooseAction = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        MenuMain = new javax.swing.JMenuBar();
        menu_system = new javax.swing.JMenu();
        menu_system_account = new javax.swing.JMenuItem();
        menu_system_changepass = new javax.swing.JMenuItem();
        menu_system_logout = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menu_system_language = new javax.swing.JMenu();
        menu_system_lan_VN = new javax.swing.JMenuItem();
        menu_system_lan_EN = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menu_back = new javax.swing.JMenuItem();
        menu_introduce = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        menu_guide = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        lb_month_year.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_month_year.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_month_year.setText("12/2021");
        lb_month_year.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lb_day.setFont(new java.awt.Font("Tahoma", 1, 75)); // NOI18N
        lb_day.setForeground(new java.awt.Color(255, 102, 0));
        lb_day.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_day.setText("14");
        lb_day.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lb_time.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lb_time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_time.setText("11 : 40 : 00 AM");
        lb_time.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_month_year, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_time, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lb_day, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_month_year, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(lb_time, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(46, Short.MAX_VALUE)
                    .addComponent(lb_day, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(46, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, 210));

        pn_tacvu.setBackground(new java.awt.Color(102, 102, 102));

        btn_ChiTiet.setBackground(new java.awt.Color(204, 204, 204));
        btn_ChiTiet.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ChiTiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/details-Huong.png"))); // NOI18N
        btn_ChiTiet.setText("Chi Tiết");
        btn_ChiTiet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChiTietActionPerformed(evt);
            }
        });

        btn_NhanVien.setBackground(new java.awt.Color(204, 204, 204));
        btn_NhanVien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_NhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/Staff-Huong.png"))); // NOI18N
        btn_NhanVien.setText("Nhân Viên");
        btn_NhanVien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_NhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_NhanVienActionPerformed(evt);
            }
        });

        btn_TaiKhoan.setBackground(new java.awt.Color(204, 204, 204));
        btn_TaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_TaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/user-Huong.png"))); // NOI18N
        btn_TaiKhoan.setText("Tài Khoản");
        btn_TaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_TaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_tacvuLayout = new javax.swing.GroupLayout(pn_tacvu);
        pn_tacvu.setLayout(pn_tacvuLayout);
        pn_tacvuLayout.setHorizontalGroup(
            pn_tacvuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_tacvuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_tacvuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_ChiTiet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_NhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_TaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_tacvuLayout.setVerticalGroup(
            pn_tacvuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_tacvuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_ChiTiet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_NhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_TaiKhoan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(pn_tacvu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 70, 190, 220));

        btnTacVu.setBackground(new java.awt.Color(0, 0, 0));
        btnTacVu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnTacVu.setForeground(new java.awt.Color(0, 204, 255));
        btnTacVu.setText("TÁC VỤ");
        btnTacVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTacVuActionPerformed(evt);
            }
        });
        jPanel1.add(btnTacVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 20, 170, 40));

        jPanel3.setLayout(new java.awt.CardLayout());

        Panel_Details.setLayout(new java.awt.BorderLayout());

        Menu_Details.setBackground(new java.awt.Color(255, 255, 255));

        btn_Phim.setBackground(new java.awt.Color(204, 204, 204));
        btn_Phim.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_Phim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/film-Huong.png"))); // NOI18N
        btn_Phim.setText("Phim");
        btn_Phim.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_Phim.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_Phim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PhimActionPerformed(evt);
            }
        });

        btn_LichChieu.setBackground(new java.awt.Color(204, 204, 204));
        btn_LichChieu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_LichChieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/calender-Huong.png"))); // NOI18N
        btn_LichChieu.setText("Lịch Chiếu");
        btn_LichChieu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_LichChieu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_LichChieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LichChieuActionPerformed(evt);
            }
        });

        btn_DoanhThu.setBackground(new java.awt.Color(204, 204, 204));
        btn_DoanhThu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_DoanhThu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/revenue-Huong.png"))); // NOI18N
        btn_DoanhThu.setText("Doanh Thu");
        btn_DoanhThu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_DoanhThu.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_DoanhThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DoanhThuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Menu_DetailsLayout = new javax.swing.GroupLayout(Menu_Details);
        Menu_Details.setLayout(Menu_DetailsLayout);
        Menu_DetailsLayout.setHorizontalGroup(
            Menu_DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_DetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_LichChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_DoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(628, Short.MAX_VALUE))
        );
        Menu_DetailsLayout.setVerticalGroup(
            Menu_DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Menu_DetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Menu_DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Phim)
                    .addComponent(btn_LichChieu)
                    .addComponent(btn_DoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Details.add(Menu_Details, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new java.awt.CardLayout());

        Panel_Phim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_PhimMouseClicked(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        lblHinh_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblHinh_Details_Phim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHinh_Details_Phim.setText("Poster");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh_Details_Phim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHinh_Details_Phim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
        );

        lblMaPhim_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaPhim_Phim.setText("Mã Phim");

        txtMaPhim_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblTenPhim_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenPhim_Phim.setText("Tên Phim");

        txtTenPhim_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblTheLoai_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTheLoai_Phim.setText("Thể Loại");

        lblThoiLuong_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblThoiLuong_Phim.setText("Thời Lượng");

        txtThoiLuong_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblNgayKC_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNgayKC_Phim.setText("Ngày Khởi Chiếu");

        txtNgayKhoiChieu_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblNgayKT_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNgayKT_Phim.setText("Ngày Kết Thúc");

        txtNgayKetThuc_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblDaoDien_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDaoDien_Phim.setText("Đạo Diễn");

        txtDaoDien_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblNSX_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNSX_Phim.setText("Ngày Sản Xuất");

        txtNSX_Details_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnThem_Phim.setBackground(new java.awt.Color(102, 255, 102));
        btnThem_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem_Phim.setText("Thêm");
        btnThem_Phim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_PhimActionPerformed(evt);
            }
        });

        btnSua_Phim.setBackground(new java.awt.Color(255, 204, 0));
        btnSua_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSua_Phim.setText("Sửa");
        btnSua_Phim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_PhimActionPerformed(evt);
            }
        });

        btnXoa_Phim.setBackground(new java.awt.Color(255, 102, 51));
        btnXoa_Phim.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoa_Phim.setText("Xóa");
        btnXoa_Phim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_PhimActionPerformed(evt);
            }
        });

        tablePhim_Details.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tablePhim_Details.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phim", "Tên Phim", "Thể Loại", "Thời Lượng", "Ngày Kết Thúc", "Ngày Khởi Chiếu"
            }
        ));
        tablePhim_Details.setRowHeight(25);
        tablePhim_Details.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePhim_DetailsMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablePhim_Details);

        btnChonHinh_Details_Phim.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        btnChonHinh_Details_Phim.setForeground(new java.awt.Color(255, 0, 0));
        btnChonHinh_Details_Phim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnChonHinh_Details_Phim.setText("* Click to change image");
        btnChonHinh_Details_Phim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChonHinh_Details_PhimMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Panel_PhimLayout = new javax.swing.GroupLayout(Panel_Phim);
        Panel_Phim.setLayout(Panel_PhimLayout);
        Panel_PhimLayout.setHorizontalGroup(
            Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PhimLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_PhimLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 988, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 24, Short.MAX_VALUE))
                    .addGroup(Panel_PhimLayout.createSequentialGroup()
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnChonHinh_Details_Phim, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(57, 57, 57)
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_PhimLayout.createSequentialGroup()
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(Panel_PhimLayout.createSequentialGroup()
                                        .addComponent(lblMaPhim_Phim)
                                        .addGap(111, 111, 111)
                                        .addComponent(lblNgayKC_Phim))
                                    .addComponent(lblTenPhim_Phim)
                                    .addGroup(Panel_PhimLayout.createSequentialGroup()
                                        .addComponent(txtMaPhim_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(34, 34, 34)
                                        .addComponent(txtNgayKhoiChieu_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTenPhim_Details_Phim))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblNgayKT_Phim)
                                    .addComponent(txtNgayKetThuc_Details_Phim, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                                    .addComponent(lblThoiLuong_Phim)
                                    .addComponent(txtThoiLuong_Details_Phim))
                                .addGap(113, 113, 113))
                            .addGroup(Panel_PhimLayout.createSequentialGroup()
                                .addGap(72, 72, 72)
                                .addComponent(btnThem_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(32, 32, 32)
                                .addComponent(btnSua_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(btnXoa_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(Panel_PhimLayout.createSequentialGroup()
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDaoDien_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDaoDien_Phim))
                                .addGap(36, 36, 36)
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNSX_Phim)
                                    .addComponent(txtNSX_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(25, 25, 25)
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTheLoai_Phim)
                                    .addGroup(Panel_PhimLayout.createSequentialGroup()
                                        .addComponent(cbx_TheLoai_Details_Phim, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(113, 113, 113))))))))
        );
        Panel_PhimLayout.setVerticalGroup(
            Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_PhimLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(Panel_PhimLayout.createSequentialGroup()
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_PhimLayout.createSequentialGroup()
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblMaPhim_Phim)
                                    .addComponent(lblNgayKC_Phim))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMaPhim_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNgayKhoiChieu_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblTenPhim_Phim))
                            .addGroup(Panel_PhimLayout.createSequentialGroup()
                                .addComponent(lblNgayKT_Phim)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNgayKetThuc_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblThoiLuong_Phim)))
                        .addGap(3, 3, 3)
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtThoiLuong_Details_Phim, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                            .addComponent(txtTenPhim_Details_Phim))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDaoDien_Phim)
                            .addComponent(lblNSX_Phim)
                            .addComponent(lblTheLoai_Phim))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtDaoDien_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNSX_Details_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cbx_TheLoai_Details_Phim))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel_PhimLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa_Phim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChonHinh_Details_Phim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel4.add(Panel_Phim, "card3");

        table_LichChieu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        table_LichChieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Phim", "Phòng Chiếu", "Suất Chiếu"
            }
        ));
        table_LichChieu.setRowHeight(25);
        table_LichChieu.setSelectionBackground(new java.awt.Color(0, 153, 255));
        jScrollPane1.setViewportView(table_LichChieu);

        cbxTen_LichChieu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxTen_LichChieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Chọn Tên Phim--" }));
        cbxTen_LichChieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbxTen_LichChieuMouseClicked(evt);
            }
        });
        cbxTen_LichChieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTen_LichChieuActionPerformed(evt);
            }
        });

        lblTenPhim_LichChieu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenPhim_LichChieu.setText("Tên Phim");

        javax.swing.GroupLayout Panel_LichChieuLayout = new javax.swing.GroupLayout(Panel_LichChieu);
        Panel_LichChieu.setLayout(Panel_LichChieuLayout);
        Panel_LichChieuLayout.setHorizontalGroup(
            Panel_LichChieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_LichChieuLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(Panel_LichChieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_LichChieuLayout.createSequentialGroup()
                        .addComponent(lblTenPhim_LichChieu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxTen_LichChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        Panel_LichChieuLayout.setVerticalGroup(
            Panel_LichChieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_LichChieuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_LichChieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTen_LichChieu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenPhim_LichChieu))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 576, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jPanel4.add(Panel_LichChieu, "card4");

        bieudo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        javax.swing.GroupLayout bieudoLayout = new javax.swing.GroupLayout(bieudo);
        bieudo.setLayout(bieudoLayout);
        bieudoLayout.setHorizontalGroup(
            bieudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 996, Short.MAX_VALUE)
        );
        bieudoLayout.setVerticalGroup(
            bieudoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 331, Short.MAX_VALUE)
        );

        table_DoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        table_DoanhThu.setRowHeight(25);
        table_DoanhThu.setSelectionBackground(new java.awt.Color(0, 153, 255));
        jScrollPane2.setViewportView(table_DoanhThu);

        javax.swing.GroupLayout Panel_DoanhThuLayout = new javax.swing.GroupLayout(Panel_DoanhThu);
        Panel_DoanhThu.setLayout(Panel_DoanhThuLayout);
        Panel_DoanhThuLayout.setHorizontalGroup(
            Panel_DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_DoanhThuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Panel_DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bieudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(77, 77, 77))
        );
        Panel_DoanhThuLayout.setVerticalGroup(
            Panel_DoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_DoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bieudo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel4.add(Panel_DoanhThu, "card5");

        javax.swing.GroupLayout Main_DetailsLayout = new javax.swing.GroupLayout(Main_Details);
        Main_Details.setLayout(Main_DetailsLayout);
        Main_DetailsLayout.setHorizontalGroup(
            Main_DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Main_DetailsLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 1039, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        Main_DetailsLayout.setVerticalGroup(
            Main_DetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Panel_Details.add(Main_Details, java.awt.BorderLayout.CENTER);

        jPanel3.add(Panel_Details, "card2");

        Panel_Staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel_StaffMouseClicked(evt);
            }
        });

        lblMaNV_NhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaNV_NhanVien.setText("Mã Nhân Viên");

        txtMaNV_Staff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblMatKhau_NhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMatKhau_NhanVien.setText("Mật Khẩu");

        lblVaiTro_NhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblVaiTro_NhanVien.setText("Vai Trò");

        cbx_VaiTro_Staff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbx_VaiTro_Staff.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nhân Viên", "Quản Lý", " " }));

        table_Staff.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân Viên", "Tên Nhân Viên", "Mật Khẩu", "Giới Tính", "Vai Trò"
            }
        ));
        table_Staff.setRowHeight(27);
        table_Staff.setSelectionBackground(new java.awt.Color(0, 153, 255));
        table_Staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_StaffMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(table_Staff);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/search.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        jPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 50, 40));

        txtTimKiem_Staff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimKiem_Staff.setForeground(new java.awt.Color(204, 204, 204));
        txtTimKiem_Staff.setText("Search id of employee ...");
        txtTimKiem_Staff.setMargin(new java.awt.Insets(2, 15, 2, 2));
        txtTimKiem_Staff.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiem_StaffFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiem_StaffFocusLost(evt);
            }
        });
        txtTimKiem_Staff.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiem_StaffKeyPressed(evt);
            }
        });
        jPanel5.add(txtTimKiem_Staff, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 39));

        lb_tennhanvien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_tennhanvien.setText("Tên nhân viên");

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        checkPass_Staff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        checkPass_Staff.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        checkPass_Staff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/eye.png"))); // NOI18N
        checkPass_Staff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkPass_StaffMouseClicked(evt);
            }
        });
        checkPass_Staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkPass_StaffActionPerformed(evt);
            }
        });
        jPanel7.add(checkPass_Staff, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, -2, -1, 40));

        txtMatKhau_Staff.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel7.add(txtMatKhau_Staff, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 40));

        btnThem_Staff.setBackground(new java.awt.Color(51, 255, 51));
        btnThem_Staff.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThem_Staff.setText("Thêm");
        btnThem_Staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThem_StaffActionPerformed(evt);
            }
        });

        btnSua_Staff.setBackground(new java.awt.Color(255, 153, 0));
        btnSua_Staff.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSua_Staff.setText("Sửa");
        btnSua_Staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSua_StaffActionPerformed(evt);
            }
        });

        btnXoa_Staff.setBackground(new java.awt.Color(255, 51, 0));
        btnXoa_Staff.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnXoa_Staff.setText("Xóa");
        btnXoa_Staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoa_StaffActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_StaffLayout = new javax.swing.GroupLayout(Panel_Staff);
        Panel_Staff.setLayout(Panel_StaffLayout);
        Panel_StaffLayout.setHorizontalGroup(
            Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_StaffLayout.createSequentialGroup()
                .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_StaffLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_StaffLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Panel_StaffLayout.createSequentialGroup()
                                .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(Panel_StaffLayout.createSequentialGroup()
                                        .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMaNV_NhanVien)
                                            .addComponent(txtMaNV_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lb_tennhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblMatKhau_NhanVien))
                                .addGap(32, 32, 32)
                                .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblVaiTro_NhanVien)
                                    .addComponent(cbx_VaiTro_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(Panel_StaffLayout.createSequentialGroup()
                                        .addComponent(btnThem_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnSua_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnXoa_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Panel_StaffLayout.setVerticalGroup(
            Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_StaffLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_StaffLayout.createSequentialGroup()
                        .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaNV_NhanVien)
                            .addComponent(lb_tennhanvien))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(Panel_StaffLayout.createSequentialGroup()
                        .addComponent(lblVaiTro_NhanVien)
                        .addGap(9, 9, 9)))
                .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbx_VaiTro_Staff)
                    .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaNV_Staff, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTenNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(lblMatKhau_NhanVien)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_StaffLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThem_Staff, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSua_Staff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnXoa_Staff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(Panel_Staff, "card3");

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, 1040, 780));

        btn_below_btnLogout.setBackground(new java.awt.Color(0, 0, 0));
        btn_below_btnLogout.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_below_btnLogout.setForeground(new java.awt.Color(255, 0, 51));
        btn_below_btnLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/logout.png"))); // NOI18N
        btn_below_btnLogout.setText("OUT");
        btn_below_btnLogout.setToolTipText("Logout your account to login");
        btn_below_btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_btnLogoutActionPerformed(evt);
            }
        });
        jPanel1.add(btn_below_btnLogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 730, 180, 70));

        btn_ChooseAction.setBackground(new java.awt.Color(0, 0, 0));
        btn_ChooseAction.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btn_ChooseAction.setForeground(new java.awt.Color(255, 255, 255));
        btn_ChooseAction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/choose_action.png"))); // NOI18N
        btn_ChooseAction.setText("CHỌN CHỨC NĂNG");
        btn_ChooseAction.setToolTipText("Logout your account to login");
        btn_ChooseAction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_ChooseAction.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_ChooseAction.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_ChooseAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ChooseActionActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ChooseAction, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 639, 180, 70));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/logo/background_login.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1550, 840));

        MenuMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        MenuMain.setPreferredSize(new java.awt.Dimension(225, 35));

        menu_system.setText("Hệ thống");
        menu_system.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        menu_system.setMargin(new java.awt.Insets(0, 5, 0, 5));

        menu_system_account.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_system_account.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/User.png"))); // NOI18N
        menu_system_account.setText("Tài khoản");
        menu_system_account.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_system_accountActionPerformed(evt);
            }
        });
        menu_system_account.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                menu_system_accountKeyPressed(evt);
            }
        });
        menu_system.add(menu_system_account);

        menu_system_changepass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/Refresh.png"))); // NOI18N
        menu_system_changepass.setText("Đổi mật khẩu");
        menu_system_changepass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_system_changepassActionPerformed(evt);
            }
        });
        menu_system.add(menu_system_changepass);

        menu_system_logout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_system_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/logout_menu.png"))); // NOI18N
        menu_system_logout.setText("Đăng xuất");
        menu_system_logout.setToolTipText("Logout your account to login");
        menu_system_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_system_logoutActionPerformed(evt);
            }
        });
        menu_system.add(menu_system_logout);
        menu_system.add(jSeparator1);

        menu_system_language.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/Translate.png"))); // NOI18N
        menu_system_language.setText("Ngôn ngữ");

        menu_system_lan_VN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/vit.gif"))); // NOI18N
        menu_system_lan_VN.setText("Tiếng việt");
        menu_system_lan_VN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_system_lan_VNActionPerformed(evt);
            }
        });
        menu_system_language.add(menu_system_lan_VN);

        menu_system_lan_EN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/eng.gif"))); // NOI18N
        menu_system_lan_EN.setText("Tiếng Anh");
        menu_system_lan_EN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_system_lan_ENActionPerformed(evt);
            }
        });
        menu_system_language.add(menu_system_lan_EN);

        menu_system.add(menu_system_language);
        menu_system.add(jSeparator3);

        menu_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/choose_action - Copy.png"))); // NOI18N
        menu_back.setText("Quay lại");
        menu_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_backActionPerformed(evt);
            }
        });
        menu_system.add(menu_back);

        MenuMain.add(menu_system);

        menu_introduce.setText("Giới thiệu");
        menu_introduce.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        menu_introduce.setMargin(new java.awt.Insets(0, 5, 0, 5));

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/introduction-icon-6.png"))); // NOI18N
        jMenuItem3.setText("Thông tin phần mềm");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        menu_introduce.add(jMenuItem3);

        MenuMain.add(menu_introduce);

        menu_guide.setText("Hướng dẫn");
        menu_guide.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        menu_guide.setMargin(new java.awt.Insets(0, 5, 0, 5));

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/icon_document_yellow.png"))); // NOI18N
        jMenuItem1.setText("Tài liệu hướng dẫn");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menu_guide.add(jMenuItem1);

        MenuMain.add(menu_guide);

        setJMenuBar(MenuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChiTietActionPerformed
        this.Hide_All_Frame(Panel_Details);
    }//GEN-LAST:event_btn_ChiTietActionPerformed

    private void btn_NhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_NhanVienActionPerformed
        this.Hide_All_Frame(Panel_Staff);
    }//GEN-LAST:event_btn_NhanVienActionPerformed

    private void btn_TaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaiKhoanActionPerformed
        MovePage.moveFrameToJDialog(new GLX_4a1_Account(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_btn_TaiKhoanActionPerformed

    private void btnTacVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTacVuActionPerformed
        this.openAndOff();
    }//GEN-LAST:event_btnTacVuActionPerformed

    private void btn_below_btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_btnLogoutActionPerformed
        this.Logout();
    }//GEN-LAST:event_btn_below_btnLogoutActionPerformed

    private void btn_ChooseActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ChooseActionActionPerformed
        MovePage.moveFrameToJDialog(new GLX_3_Choose_action(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_btn_ChooseActionActionPerformed

    private void btn_PhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PhimActionPerformed
        Hide_Panel_Details(Panel_Phim);
    }//GEN-LAST:event_btn_PhimActionPerformed

    private void btn_LichChieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LichChieuActionPerformed
        Hide_Panel_Details(Panel_LichChieu);
    }//GEN-LAST:event_btn_LichChieuActionPerformed

    private void btn_DoanhThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DoanhThuActionPerformed
        Hide_Panel_Details(Panel_DoanhThu);
        this.draw();
    }//GEN-LAST:event_btn_DoanhThuActionPerformed

    private void btnThem_PhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_PhimActionPerformed
        // TODO add your handling code here:
        if(txtMaPhim_Details_Phim.getText().isEmpty() || txtTenPhim_Details_Phim.getText().isEmpty() || txtThoiLuong_Details_Phim.getText().isEmpty() || txtNgayKetThuc_Details_Phim.getText().isEmpty() || txtNgayKhoiChieu_Details_Phim.getText().isEmpty() || txtDaoDien_Details_Phim.getText().isEmpty() || txtNSX_Details_Phim.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống thông tin !");
        }
        else if (!CheckInvalid.checkNumber(txtThoiLuong_Details_Phim.getText())){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho thời lượng !");
        }
        else if (!CheckInvalid.checkDate(txtNgayKetThuc_Details_Phim.getText()) || (!CheckInvalid.checkDate(txtNgayKhoiChieu_Details_Phim.getText()) || (!CheckInvalid.checkDate(txtNSX_Details_Phim.getText())))){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày !");
        }
        else if (Save_helper.path == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hình ảnh phim !");
        }
        else {
            String MaPhim = txtMaPhim_Details_Phim.getText();
            String TenPhim = txtTenPhim_Details_Phim.getText();

            String dKC = txtNgayKhoiChieu_Details_Phim.getText();
            java.util.Date dKC1  = XDate.toDate(dKC, "dd-MM-yyyy");
            String dKC2 =  XDate.toString(dKC1, "yyyy-MM-dd");
            java.util.Date ngayKC = Date.valueOf(dKC2);

            String dKT = txtNgayKetThuc_Details_Phim.getText();
            java.util.Date dKT1  = XDate.toDate(dKT, "dd-MM-yyyy");
            String dKT2 =  XDate.toString(dKT1, "yyyy-MM-dd");
            java.util.Date ngayKT = Date.valueOf(dKT2);

            String dNSX = txtNSX_Details_Phim.getText();
            java.util.Date dNSX1  = XDate.toDate(dNSX, "dd-MM-yyyy");
            String dNSX2 =  XDate.toString(dNSX1, "yyyy-MM-dd");
            java.util.Date NSX = Date.valueOf(dNSX2);

            String DaoDien = txtDaoDien_Details_Phim.getText();
            int ThoiLuong = Integer.parseInt(txtThoiLuong_Details_Phim.getText());

            String TheLoai = listLoaiPhim.get(cbx_TheLoai_Details_Phim.getSelectedIndex());
            String HinhAnh = Save_helper.path.getName();
            Phim phim = new Phim( MaPhim, TheLoai, TenPhim, NSX, DaoDien, HinhAnh, ngayKC, ngayKT, ThoiLuong);
            DAOPhim.insert(phim);
            JOptionPane.showMessageDialog(this, "Thêm phim thành công !");
            fillData_Phim();
            reset_Phim();
        }

    }//GEN-LAST:event_btnThem_PhimActionPerformed

    private void btnSua_PhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_PhimActionPerformed
        // TODO add your handling code here:

        if(txtMaPhim_Details_Phim.getText().isEmpty() || txtTenPhim_Details_Phim.getText().isEmpty() || txtThoiLuong_Details_Phim.getText().isEmpty() || txtNgayKetThuc_Details_Phim.getText().isEmpty() || txtNgayKhoiChieu_Details_Phim.getText().isEmpty() || txtDaoDien_Details_Phim.getText().isEmpty() || txtNSX_Details_Phim.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống thông tin !");
        }
        else if (!CheckInvalid.checkNumber(txtThoiLuong_Details_Phim.getText())){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho thời lượng !");
        }
        else if (!CheckInvalid.checkDate(txtNgayKetThuc_Details_Phim.getText()) || (!CheckInvalid.checkDate(txtNgayKhoiChieu_Details_Phim.getText()) || (!CheckInvalid.checkDate(txtNSX_Details_Phim.getText())))){
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng ngày !");
        }
        else if (Save_helper.path == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hình ảnh phim !");
        }
        else {
            String MaPhim = txtMaPhim_Details_Phim.getText();
            String TenPhim = txtTenPhim_Details_Phim.getText();

            String dKC = txtNgayKhoiChieu_Details_Phim.getText();
            java.util.Date dKC1  = XDate.toDate(dKC, "dd-MM-yyyy");
            String dKC2 =  XDate.toString(dKC1, "yyyy-MM-dd");
            java.util.Date ngayKC = Date.valueOf(dKC2);

            String dKT = txtNgayKetThuc_Details_Phim.getText();
            java.util.Date dKT1  = XDate.toDate(dKT, "dd-MM-yyyy");
            String dKT2 =  XDate.toString(dKT1, "yyyy-MM-dd");
            java.util.Date ngayKT = Date.valueOf(dKT2);

            String dNSX = txtNSX_Details_Phim.getText();
            java.util.Date dNSX1  = XDate.toDate(dNSX, "dd-MM-yyyy");
            String dNSX2 =  XDate.toString(dNSX1, "yyyy-MM-dd");
            java.util.Date NSX = Date.valueOf(dNSX2);

            String DaoDien = txtDaoDien_Details_Phim.getText();
            int ThoiLuong = Integer.parseInt(txtThoiLuong_Details_Phim.getText());

            String TheLoai = listLoaiPhim.get(cbx_TheLoai_Details_Phim.getSelectedIndex());
            String HinhAnh = (Save_helper.path != null) ? Save_helper.path.getName() : (String)tablePhim_Details.getValueAt(tablePhim_Details.getSelectedRow(), 6);

            Phim phim = new Phim( MaPhim, TheLoai, TenPhim, NSX, DaoDien, HinhAnh, ngayKC, ngayKT, ThoiLuong);
            DAOPhim.update(phim);
            JOptionPane.showMessageDialog(this, "Chỉnh sửa phim thành công !");
            fillData_Phim();
            reset_Phim();

        }
    }//GEN-LAST:event_btnSua_PhimActionPerformed

    private void btnXoa_PhimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_PhimActionPerformed
        // TODO add your handling code here:
        try {
            String idPhim = (String) tablePhim_Details.getValueAt(tablePhim_Details.getSelectedRow(), 0);
            DAOPhim.delete(idPhim);
            JOptionPane.showMessageDialog(this, "Xóa phim thành công !");
            fillData_Phim();
            reset_Phim();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa phim !");
        }
    }//GEN-LAST:event_btnXoa_PhimActionPerformed

    private void tablePhim_DetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePhim_DetailsMouseClicked
        // TODO add your handling code here:
        ClickData_Phim();
    }//GEN-LAST:event_tablePhim_DetailsMouseClicked

    private void Panel_PhimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_PhimMouseClicked
        // TODO add your handling code here:
        reset_Phim();
    }//GEN-LAST:event_Panel_PhimMouseClicked

    private void cbxTen_LichChieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbxTen_LichChieuMouseClicked
        
    }//GEN-LAST:event_cbxTen_LichChieuMouseClicked

    private void cbxTen_LichChieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTen_LichChieuActionPerformed
        String[] header = {"Tên Phim", "Phòng Chiếu", "Suất Chiếu", "Ngày Chiếu"};
        tbModel_LichChieu = new DefaultTableModel(header, 0);
        table_LichChieu.setModel(tbModel_LichChieu);
        String s = (String)cbxTen_LichChieu.getSelectedItem();
        if (s.equalsIgnoreCase("--Chọn Tên Phim--")){
            fillData_LichChieu();
        }
        else{
            TenPhimTheoCBX(s);
        }
    }//GEN-LAST:event_cbxTen_LichChieuActionPerformed

    private void table_StaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_StaffMouseClicked
        // TODO add your handling code here:
        ClickData_NhanVien();
    }//GEN-LAST:event_table_StaffMouseClicked

    private void btnThem_StaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThem_StaffActionPerformed
        // TODO add your handling code here:
        if(txtMaNV_Staff.getText().isEmpty() || txtMatKhau_Staff.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống thông tin !");
        }
        else{
            String MaNV = txtMaNV_Staff.getText();
            String Pass = txtMatKhau_Staff.getText();
            int VaiTro = cbx_VaiTro_Staff.getSelectedIndex();
            TaiKhoan taikhoan = new TaiKhoan(MaNV,Pass,VaiTro);
            DAOTaiKhoan.insert(taikhoan);
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công !");
            fillData_NhanVien();
            reset_NhanVien();
        }
    }//GEN-LAST:event_btnThem_StaffActionPerformed

    private void btnSua_StaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSua_StaffActionPerformed
        // TODO add your handling code here:
        if(txtMaNV_Staff.getText().isEmpty() || txtMatKhau_Staff.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "Vui lòng không để trống thông tin !");
        }
        else{

            String MaNV = txtMaNV_Staff.getText();
            String Pass = txtMatKhau_Staff.getText();
            int VaiTro = cbx_VaiTro_Staff.getSelectedIndex();
            TaiKhoan taikhoan = new TaiKhoan(MaNV,Pass,VaiTro);
            DAOTaiKhoan.fix(taikhoan);
            JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công !");
            fillData_NhanVien();
            reset_NhanVien();
        }
    }//GEN-LAST:event_btnSua_StaffActionPerformed

    private void btnXoa_StaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoa_StaffActionPerformed
        // TODO add your handling code here:
        try {
            String MaNV = txtMaNV_Staff.getText();
            String Pass = txtMatKhau_Staff.getText();
            int VaiTro = cbx_VaiTro_Staff.getSelectedIndex();
            TaiKhoan taikhoan = new TaiKhoan(MaNV,Pass,VaiTro);
            DAOTaiKhoan.delete(taikhoan);
            JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công !");
            fillData_NhanVien();
            reset_NhanVien();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa tài khoản");
        }
    }//GEN-LAST:event_btnXoa_StaffActionPerformed

    private void checkPass_StaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkPass_StaffActionPerformed
        // TODO add your handling code here:
        if(checkPass_Staff.isSelected()){
            txtMatKhau_Staff.setEchoChar((char)0);
        }
        else{
            txtMatKhau_Staff.setEchoChar('*');
        }
    }//GEN-LAST:event_checkPass_StaffActionPerformed

    private void btnChonHinh_Details_PhimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChonHinh_Details_PhimMouseClicked
        ChonAnh();
        lblHinh_Details_Phim.setText("");
    }//GEN-LAST:event_btnChonHinh_Details_PhimMouseClicked

    private void menu_system_accountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_accountActionPerformed
        MovePage.moveFrameToJDialog(new GLX_4a1_Account(this, true), this);
    }//GEN-LAST:event_menu_system_accountActionPerformed

    private void menu_system_accountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menu_system_accountKeyPressed
        MovePage.moveFrameToJDialog(new GLX_4a1_Account(this, true), this);
    }//GEN-LAST:event_menu_system_accountKeyPressed

    private void menu_system_changepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_changepassActionPerformed
        MovePage.moveFrameToJDialog(new GLX_4ab_changePassword(this, true), this);
    }//GEN-LAST:event_menu_system_changepassActionPerformed

    private void menu_system_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_logoutActionPerformed
        this.Logout();
    }//GEN-LAST:event_menu_system_logoutActionPerformed

    private void menu_system_lan_VNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_lan_VNActionPerformed
        this.changeLG("vn");
    }//GEN-LAST:event_menu_system_lan_VNActionPerformed

    private void menu_system_lan_ENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_lan_ENActionPerformed
        this.changeLG("en");
    }//GEN-LAST:event_menu_system_lan_ENActionPerformed

    private void menu_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_backActionPerformed
        MovePage.moveFrameToJDialog(new GLX_3_Choose_action(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_menu_backActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        MovePage.showJFrameNotHide(new GLX_6_GioiThieu());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            URL path = this.getClass().getResource("/com/fpt/galaxymovie/HuongDan/HuongDan.html");
            Desktop.getDesktop().browse(new File(path.getPath()).toURI());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        FindNhanVien();
        txtTimKiem_Staff.setText("");
    }//GEN-LAST:event_jLabel2MouseClicked

    private void checkPass_StaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_checkPass_StaffMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_checkPass_StaffMouseClicked

    private void txtTimKiem_StaffFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiem_StaffFocusLost
        txtTimKiem_Staff.setText("Search id of employee ...");
        txtTimKiem_Staff.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_txtTimKiem_StaffFocusLost

    private void txtTimKiem_StaffFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiem_StaffFocusGained
        txtTimKiem_Staff.setText("");
        txtTimKiem_Staff.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtTimKiem_StaffFocusGained

    private void Panel_StaffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel_StaffMouseClicked
        reset_NhanVien();
    }//GEN-LAST:event_Panel_StaffMouseClicked

    private void txtTimKiem_StaffKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiem_StaffKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            FindNhanVien();
        }
    }//GEN-LAST:event_txtTimKiem_StaffKeyPressed

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
            java.util.logging.Logger.getLogger(GLX_5_QuanLy_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GLX_5_QuanLy_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GLX_5_QuanLy_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_5_QuanLy_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GLX_5_QuanLy_new().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Main_Details;
    private javax.swing.JMenuBar MenuMain;
    private javax.swing.JPanel Menu_Details;
    private javax.swing.JPanel Panel_Details;
    private javax.swing.JPanel Panel_DoanhThu;
    private javax.swing.JPanel Panel_LichChieu;
    private javax.swing.JPanel Panel_Phim;
    private javax.swing.JPanel Panel_Staff;
    private javax.swing.JPanel bieudo;
    private javax.swing.JLabel btnChonHinh_Details_Phim;
    private javax.swing.JButton btnSua_Phim;
    private javax.swing.JButton btnSua_Staff;
    private javax.swing.JButton btnTacVu;
    private javax.swing.JButton btnThem_Phim;
    private javax.swing.JButton btnThem_Staff;
    private javax.swing.JButton btnXoa_Phim;
    private javax.swing.JButton btnXoa_Staff;
    private javax.swing.JButton btn_ChiTiet;
    private javax.swing.JButton btn_ChooseAction;
    private javax.swing.JButton btn_DoanhThu;
    private javax.swing.JButton btn_LichChieu;
    private javax.swing.JButton btn_NhanVien;
    private javax.swing.JButton btn_Phim;
    private javax.swing.JButton btn_TaiKhoan;
    private javax.swing.JButton btn_below_btnLogout;
    private javax.swing.JComboBox<String> cbxTen_LichChieu;
    private javax.swing.JComboBox<String> cbx_TheLoai_Details_Phim;
    private javax.swing.JComboBox<String> cbx_VaiTro_Staff;
    private javax.swing.JCheckBox checkPass_Staff;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel lb_day;
    private javax.swing.JLabel lb_month_year;
    private javax.swing.JLabel lb_tennhanvien;
    private javax.swing.JLabel lb_time;
    private javax.swing.JLabel lblDaoDien_Phim;
    private javax.swing.JLabel lblHinh_Details_Phim;
    private javax.swing.JLabel lblMaNV_NhanVien;
    private javax.swing.JLabel lblMaPhim_Phim;
    private javax.swing.JLabel lblMatKhau_NhanVien;
    private javax.swing.JLabel lblNSX_Phim;
    private javax.swing.JLabel lblNgayKC_Phim;
    private javax.swing.JLabel lblNgayKT_Phim;
    private javax.swing.JLabel lblTenPhim_LichChieu;
    private javax.swing.JLabel lblTenPhim_Phim;
    private javax.swing.JLabel lblTheLoai_Phim;
    private javax.swing.JLabel lblThoiLuong_Phim;
    private javax.swing.JLabel lblVaiTro_NhanVien;
    private javax.swing.JMenuItem menu_back;
    private javax.swing.JMenu menu_guide;
    private javax.swing.JMenu menu_introduce;
    private javax.swing.JMenu menu_system;
    private javax.swing.JMenuItem menu_system_account;
    private javax.swing.JMenuItem menu_system_changepass;
    private javax.swing.JMenuItem menu_system_lan_EN;
    private javax.swing.JMenuItem menu_system_lan_VN;
    private javax.swing.JMenu menu_system_language;
    private javax.swing.JMenuItem menu_system_logout;
    private javax.swing.JPanel pn_tacvu;
    private javax.swing.JTable tablePhim_Details;
    private javax.swing.JTable table_DoanhThu;
    private javax.swing.JTable table_LichChieu;
    private javax.swing.JTable table_Staff;
    private javax.swing.JTextField txtDaoDien_Details_Phim;
    private javax.swing.JTextField txtMaNV_Staff;
    private javax.swing.JTextField txtMaPhim_Details_Phim;
    private javax.swing.JPasswordField txtMatKhau_Staff;
    private javax.swing.JTextField txtNSX_Details_Phim;
    private javax.swing.JTextField txtNgayKetThuc_Details_Phim;
    private javax.swing.JTextField txtNgayKhoiChieu_Details_Phim;
    private javax.swing.JTextField txtTenNhanVien;
    private javax.swing.JTextField txtTenPhim_Details_Phim;
    private javax.swing.JTextField txtThoiLuong_Details_Phim;
    private javax.swing.JTextField txtTimKiem_Staff;
    // End of variables declaration//GEN-END:variables
}
