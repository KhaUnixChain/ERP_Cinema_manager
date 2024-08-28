package com.fpt.galaxymovie.gui;

import com.fpt.galaxymovie.controller.MovePage;
import com.fpt.galaxymovie.database.helper.Save_helper;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.controller.DateHelper;
import com.fpt.galaxymovie.controller.KeepFiles;
import com.fpt.galaxymovie.controller.Money;
import com.fpt.galaxymovie.controller.PrintPanel_PDF;
import com.fpt.galaxymovie.controller.RandomPrivacy;
import com.fpt.galaxymovie.controller.ReadWriteFile;
import com.fpt.galaxymovie.controller.ResetTable;
import com.fpt.galaxymovie.database.dao.DAO;
import com.fpt.galaxymovie.models.Ghe;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public final class GLX_4a0_Buy extends javax.swing.JFrame {
    int open = 0, index_film = 1;
    DefaultTableModel tbModel;
    UtilDateModel model = new UtilDateModel();
    Calendar calendar = new GregorianCalendar();
    JDatePanelImpl datePanel = new JDatePanelImpl(model);
    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
    DAO<String> thaotac = new DAO<>();
    String sql_film = "EXEC SP_fillMovie @date=?";
    String sql_get_name_film = "SELECT TenPhim, HinhAnh FROM Phim";
    String sql_insert_ve = "INSERT INTO Ve VALUES (?,?,?,?)";
    String sql_insert_ctcp = "INSERT INTO ChiTietChieuPhim VALUES (?,?,?,?,?)";
    String VN = "change_language_vn";
    String EN = "change_language_en";
    ResourceBundle resourceBundle = ResourceBundle.getBundle(VN);
    String path = "E:\\SAVE-JAVA\\DuAnOne\\src\\com\\fpt\\galaxymovie\\programing file\\ma_ve.txt";

    public GLX_4a0_Buy() {
        initComponents();
        lb_above_logoName.requestFocus();
        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.initTable();
//        this.Add_calendar();
        this.Account();
        this.setMaVe(Save_helper.Language);
        this.insertHashmapChairTicket();
        this.checkStatusDialogOff();
        this.TimeClock();
        this.cinemaInterest();
        this.changeLG(Save_helper.Language);
    }
    
    
    
    public void changeLG (String ngonngu){
        Save_helper.Language = ngonngu;
        setMaVe(ngonngu);
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

            lblRapPhim.setText(resourceBundle.getString("lblRapPhim"));
            lblHoaDon.setText(resourceBundle.getString("lblHoaDon"));
            lblTenKH.setText(resourceBundle.getString("lblTenKH"));
            lblNgayMuaVe.setText(resourceBundle.getString("lblNgayMuaVe"));
            lblMaNV.setText(resourceBundle.getString("lblMaNV"));
            lblSuatChieu.setText(resourceBundle.getString("lblSuatChieu"));
            lblLoaiVe.setText(resourceBundle.getString("lblLoaiVe"));
            lblTongHoaDon.setText(resourceBundle.getString("lblTongHoaDon"));
            btn_HoanThanh.setText(resourceBundle.getString("btn_HoanThanh"));
            btnRemoveAll.setText(resourceBundle.getString("btnRemoveAll"));

            btn_big_man.setText(resourceBundle.getString("btn_big_man"));
            btn_boy.setText(resourceBundle.getString("btn_boy"));
            btn_student.setText(resourceBundle.getString("btn_student"));
            btn_vip.setText(resourceBundle.getString("btn_vip"));

            btn_below_service_voucher.setText(resourceBundle.getString("btn_below_service_voucher"));
            btn_below_service_food.setText(resourceBundle.getString("btn_below_service_food"));
            btn_below_service_save.setText(resourceBundle.getString("btn_below_service_save"));
            btn_below_service_scanner.setText(resourceBundle.getString("btn_below_service_scanner"));
            btn_below_pay_wallet.setText(resourceBundle.getString("btn_below_pay_wallet"));
            btn_below_pay_money.setText(resourceBundle.getString("btn_below_pay_money"));
            btn_below_pay_ATM.setText(resourceBundle.getString("btn_below_pay_ATM"));
        } catch (Exception e) {
        } 
    }


    btnChooseTimeWatch btnTimeWatch = new btnChooseTimeWatch();


    // -------------- Nút in suất chiếu khi clocked vào nút -------------------------------
    class btnChooseTimeWatch implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            JButton jButton = (JButton) e.getSource();
            lbSuatchieu.setText(jButton.getText());
            insertNameFilm_Table(jButton.getToolTipText());
        }
    }
    
    public void initTable() {
        tbModel = (DefaultTableModel) tb_hoadon.getModel();
        tb_hoadon.setModel(tbModel);
        tb_hoadon.getColumnModel().getColumn(0).setPreferredWidth(40);
        tb_hoadon.getColumnModel().getColumn(1).setPreferredWidth(40);
        tb_hoadon.getColumnModel().getColumn(2).setPreferredWidth(180);
    }
    
    public void insertHashmapChairTicket() {
        if (Save_helper.chairs != null) {
            Save_helper.chairs.entrySet().forEach(obj -> {
                String roomID = obj.getKey();
                obj.getValue().stream().map(ghe -> new Object[]{
                    roomID, "" + ghe.getSeatId(), ""
                }).forEachOrdered(row -> {
                    tbModel.addRow(row);
                });
            });
        }
    }

    public void Account() {
        if (Save_helper.taikhoan == null) {
            lb_above_employee.setText("Null");
        } else {
            try {
                String sql = "SELECT ID, HoTen FROM NhanVien INNER JOIN TaiKhoan ON NhanVien.ID = TaiKhoan.NhanVienID WHERE TaiKhoan.NhanVienID=?";
                PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
                ps.setString(1, Save_helper.taikhoan.getMaNV());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lb_above_employee.setText(rs.getString(2));
                    lbNhanVien.setText(rs.getString(1));
                    break;
                }
            } catch (Exception e) {
            }
        }
    }
    
    public void checkStatusDialogOff(){
        new Thread(){
            @Override
            public void run(){
                while (true) {                    
                    invalidate();
                    validate();
                    repaint();
                    try {
                        Thread.sleep(1000);
                        if (!Save_helper.tenKH.equals("...")) {
                            lbKhachhang.setText(Save_helper.tenKH);
                            stop();
                            break;
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
    }

    // ---------------------- Sự kiện điền loại vé khi click vào 4 nut loại vé  -------------------------
    public void btnKindTicketActionPerformed(JButton jButton) {
        String loaive = jButton.getText();
        lbLoaive.setText(loaive);
        if (lbChietKhau.getText().equals("...")) {
            int tong = (tb_hoadon.getRowCount() != 0) ? Integer.parseInt(Money.getMoney(loaive)) * tb_hoadon.getRowCount() : Integer.parseInt(Money.getMoney(loaive));
            lbTongtien.setText("" + tong);
        } else {
            int tong = (tb_hoadon.getRowCount() != 0) ? Integer.parseInt(Money.getMoneyVoucher(loaive)) * tb_hoadon.getRowCount() : Integer.parseInt(Money.getMoneyVoucher(loaive));
            lbTongtien.setText("" + tong);
        }
    }

//    public void Add_calendar() {
//        Calendar_show.add(datePicker);
//        datePicker.setSize(Calendar_show.getWidth() - 5, 30); // set size for datePicker
//        datePicker.getJFormattedTextField().setFont(new Font("Tamanho", Font.BOLD, 17));
//        datePicker.getJFormattedTextField().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//        datePicker.getJFormattedTextField().setHorizontalAlignment(JTextField.CENTER);
//        datePicker.addActionListener(btnDateActionPerformed);
//    }
// ---------------------  Sự kiện điền ngày mua vé khi click vào datePicker  -------------------------
//    class btnDateActionPerformed implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String date_US = datePicker.getJFormattedTextField().getText();
//            String showDate = DateHelper.changeFullDate(date_US);
//            datePicker.getJFormattedTextField().setText(showDate);
//            lbNgaymuave.setText(showDate);
//        }
//    }
//    btnDateActionPerformed btnDateActionPerformed = new btnDateActionPerformed();

    public void TimeClock() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Calendar calendar = new GregorianCalendar();
                    int hour = calendar.get(Calendar.HOUR);
                    int minutes = calendar.get(Calendar.MINUTE);
                    int seconds = calendar.get(Calendar.SECOND);
                    String AM_PM = (calendar.get(Calendar.AM_PM) == 0) ? "AM" : "PM";
                    String time = hour + " : " + minutes + " : " + seconds + " " + AM_PM;
                    lb_above_clock.setText(time);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();
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

    public void open_bar() {
        switch (open) {
            case 0 -> {
                pn_below_service.setVisible(false);
                pn_below_pay.setVisible(false);
                open = 1;
            }
            case 1 -> {
                pn_below_service.setVisible(true);
                pn_below_pay.setVisible(true);
                open = 0;
            }
        }
    }

    public void reset_ticket() {
        ResetTable.removeRowTable(tbModel, tb_hoadon);
        ResetTable.removeTicket(lbTongtien, lbChietKhau, lbKhachhang, lbLoaive, lbSuatchieu, lbNgaymuave);
    }
    

    
    public void setMaVe(String language){
        try {
            String maVe = KeepFiles.readTXT(path);
            if (language.equals("vn")) {
                lbMaVe.setText("-------------------------   MÃ VÉ: %s   -------------------------".formatted(maVe));
            }
            else {
                lbMaVe.setText("-----------------------   TicketID: %s   ------------------------".formatted(maVe));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getMaVe(){
        try {
            String maVe = KeepFiles.readTXT(path);
            return Integer.parseInt(maVe);
        } catch (Exception e) {
            return -1;
        }
    }
    
    
    // -----------------------------------------------------------------------------
    public void focusTimeButton(int i) {
        try {
            btn_time0.setBackground(Color.BLACK);
            btn_time1.setBackground(Color.BLACK);
            btn_time2.setBackground(Color.BLACK);
            btn_time3.setBackground(Color.BLACK);
            btn_time4.setBackground(Color.BLACK);
            btn_time5.setBackground(Color.BLACK);
            switch (i) {
                case 0 -> {
                    btn_time0.setBackground(Color.GRAY); this.fillPanelMovie(btn_time0);
                    lbNgaymuave.setText( DateHelper.convertSchedule(btn_time0.getText()).toString() );
                }
                case 1 -> {
                    btn_time1.setBackground(Color.GRAY); this.fillPanelMovie(btn_time1);
                    lbNgaymuave.setText( DateHelper.convertSchedule(btn_time1.getText()).toString() );
                }
                case 2 -> {
                    btn_time2.setBackground(Color.GRAY); this.fillPanelMovie(btn_time2);
                    lbNgaymuave.setText( DateHelper.convertSchedule(btn_time2.getText()).toString() );
                }
                case 3 -> {
                    btn_time3.setBackground(Color.GRAY); this.fillPanelMovie(btn_time3);
                    lbNgaymuave.setText( DateHelper.convertSchedule(btn_time3.getText()).toString() );
                }
                case 4 -> {
                    btn_time4.setBackground(Color.GRAY); this.fillPanelMovie(btn_time4);
                    lbNgaymuave.setText( DateHelper.convertSchedule(btn_time4.getText()).toString() );
                }
                case 5 -> {
                    btn_time5.setBackground(Color.GRAY); this.fillPanelMovie(btn_time5);
                    lbNgaymuave.setText( DateHelper.convertSchedule(btn_time5.getText()).toString() );
                }
            }
        }
        catch (Exception e){
        }
    }
    
    public void insertNameFilm_Table(String name){
        if (tbModel.getRowCount() != 0) {
            for (int i = 0; i < tbModel.getRowCount(); i++) {
                tb_hoadon.setValueAt(name, i, 2);
            }
        }
    }

    public void insertFilm (JPanel jPanel, String phim, int time){
        try{
            Component[] components = jPanel.getComponents();
            JLabel lbPhim  = (JLabel)  components[5];
            JLabel lbTime  = (JLabel)  components[6];
            for (int i = 0; i < 5; i++) {
                // lấy button từ component
                JButton jButton = (JButton) components[i];
                
                // lấy content String từ nút vừa lấy đc
                String[] time_array = jButton.getText().split(" - ");
                
                // set giá trị enabled cho nút đó để xem ẩn hay hiện
                jButton.setEnabled( DateHelper.compareNow(time_array[0], time_array[1]) );
                
                // thêm thuộc tính để lấy suất chiếu in cho lbSuatChieu
                jButton.addActionListener(btnTimeWatch);
                
                // thêm tên phim trong titptool của button
                jButton.setToolTipText(phim);
            }
            lbPhim.setText(phim);
            lbTime.setText("- Thời lượng : " + time + " phút -");
        }
        catch (Exception e){
        }
    }
    
    public void resetFilm(JPanel jPanel){
        try {
            Component[] components = jPanel.getComponents();
            for (Component component : components) {
                JPanel sub_com = (JPanel) component;
                Component[] pn_sub = sub_com.getComponents();
                
                JLabel lbPhim  = (JLabel)  pn_sub[5];
                JLabel lbTime  = (JLabel)  pn_sub[6];
                lbPhim.setText("");
                lbTime.setText("");
                for (int i = 0; i < 5; i++) {
                    JButton jButton = (JButton) pn_sub[i];                
                    jButton.setEnabled( false );
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void fillPanelMovie(JButton jButton){
        int index = 1;
        Component[] pn1 = pn_list_movie_1.getComponents();
        JPanel pb_list_1 = (JPanel) pn1[0];
        JPanel pb_list_2 = (JPanel) pn1[1];
        JPanel pb_list_3 = (JPanel) pn1[2];
        JPanel pb_list_4 = (JPanel) pn1[3];
        Component[] pn2 = pn_list_movie_2.getComponents();
        JPanel pb_list_5 = (JPanel) pn2[0];
        JPanel pb_list_6 = (JPanel) pn2[1];
        JPanel pb_list_7 = (JPanel) pn2[2];
        JPanel pb_list_8 = (JPanel) pn2[3];
        this.resetFilm(pn_list_movie_1);
        this.resetFilm(pn_list_movie_2);
        
        
        try {
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql_film);
            ps.setDate(1, (Date) DateHelper.convertSchedule( jButton.getText()) );
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                if (rs.wasNull()) {
                    break;
                }
                else {
                    if (index <= 4) {
                        switch (index){
                            case 1 -> { insertFilm(pb_list_1, rs.getString(1), rs.getInt(2)); }
                            case 2 -> { insertFilm(pb_list_2, rs.getString(1), rs.getInt(2)); }
                            case 3 -> { insertFilm(pb_list_3, rs.getString(1), rs.getInt(2)); }
                            case 4 -> { insertFilm(pb_list_4, rs.getString(1), rs.getInt(2)); }
                        }
                    }
                    else {
                        switch (index){
                            case 5 -> { insertFilm(pb_list_5, rs.getString(1), rs.getInt(2)); }
                            case 6 -> { insertFilm(pb_list_6, rs.getString(1), rs.getInt(2)); }
                            case 7 -> { insertFilm(pb_list_7, rs.getString(1), rs.getInt(2)); }
                            case 8 -> { insertFilm(pb_list_8, rs.getString(1), rs.getInt(2)); }
                        }
                    }
                    index++;
                }
            }
        } catch (Exception e) {
        }
    }
    // ---------------------------------------------------------------------

    
    
    // auto fill day next of now in schedule
    public void cinemaInterest() {
        // ------------------------------
        LocalDate date = LocalDate.now();
        int countCom = pn_left_filter.getComponentCount();
        Component[] components = pn_left_filter.getComponents();
        for (int i = 0; i < countCom; i++) {
            date = date.plusDays(i);
            JButton jButton = (JButton) components[i];
            jButton.setText(
                    date.getDayOfMonth() + "/" + date.getMonthValue()
            );
            date = LocalDate.now();
        }
    }
    
    
    public void pay (){
        Save_helper.ve = pn_right_ticket;
        if (tb_hoadon.getRowCount()==0 || lbNgaymuave.getText().isEmpty() || lbSuatchieu.getText().isEmpty() || lbLoaive.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa đủ thông tin để in vé !");
        }
        else {
            try {
                

                if (Save_helper.chairs != null) {
                    for (Entry<String, ArrayList<Ghe>> entry: Save_helper.chairs.entrySet()) {
                        for (Ghe ghe : entry.getValue()) {
                            String sql = "UPDATE ChiTietChoNgoi SET Status = ? WHERE MaChoNgoi=? and PhongChieuID=?";
                            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
                            ps.setInt(1, ghe.getStatus());
                            ps.setInt(2, ghe.getSeatId());
                            ps.setString(3, entry.getKey());
                            ps.execute();
                            ps.close();
                        }
                    }
                    ReadWriteFile.writeTXT(String.valueOf(getMaVe()+1), path);
                    this.setMaVe(Save_helper.Language);
                    PrintPanel_PDF.printComponenet(Save_helper.ve);
                    this.reset_ticket();
                }
                else {
                    System.out.println("NUll mia no roi");
                }
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_above = new javax.swing.JPanel();
        lb_above_employee = new javax.swing.JLabel();
        lb_above_logo = new javax.swing.JLabel();
        lb_above_clock = new javax.swing.JLabel();
        lb_above_slogan = new javax.swing.JLabel();
        lb_above_logoName = new javax.swing.JLabel();
        pn_left = new javax.swing.JPanel();
        pn_left_filter = new javax.swing.JPanel();
        btn_time0 = new javax.swing.JButton();
        btn_time1 = new javax.swing.JButton();
        btn_time2 = new javax.swing.JButton();
        btn_time3 = new javax.swing.JButton();
        btn_time4 = new javax.swing.JButton();
        btn_time5 = new javax.swing.JButton();
        pn_left_list_movie_container = new javax.swing.JPanel();
        pn_list_movie = new javax.swing.JPanel();
        pn_list_movie_1 = new javax.swing.JPanel();
        pn_list_1 = new javax.swing.JPanel();
        lb_list_tenphim_2 = new javax.swing.JLabel();
        lb_list_thoiluong_2 = new javax.swing.JLabel();
        btn_list_2_1 = new javax.swing.JButton();
        btn_list_2_2 = new javax.swing.JButton();
        btn_list_2_3 = new javax.swing.JButton();
        btn_list_2_4 = new javax.swing.JButton();
        btn_list_2_5 = new javax.swing.JButton();
        pn_list_2 = new javax.swing.JPanel();
        lb_list_tenphim_3 = new javax.swing.JLabel();
        lb_list_thoiluong_3 = new javax.swing.JLabel();
        btn_list_2_6 = new javax.swing.JButton();
        btn_list_2_7 = new javax.swing.JButton();
        btn_list_2_8 = new javax.swing.JButton();
        btn_list_2_9 = new javax.swing.JButton();
        btn_list_2_10 = new javax.swing.JButton();
        pn_list_3 = new javax.swing.JPanel();
        lb_list_tenphim_4 = new javax.swing.JLabel();
        lb_list_thoiluong_4 = new javax.swing.JLabel();
        btn_list_2_11 = new javax.swing.JButton();
        btn_list_2_12 = new javax.swing.JButton();
        btn_list_2_13 = new javax.swing.JButton();
        btn_list_2_14 = new javax.swing.JButton();
        btn_list_2_15 = new javax.swing.JButton();
        pn_list_4 = new javax.swing.JPanel();
        lb_list_tenphim_6 = new javax.swing.JLabel();
        lb_list_thoiluong_6 = new javax.swing.JLabel();
        btn_list_2_21 = new javax.swing.JButton();
        btn_list_2_22 = new javax.swing.JButton();
        btn_list_2_23 = new javax.swing.JButton();
        btn_list_2_24 = new javax.swing.JButton();
        btn_list_2_25 = new javax.swing.JButton();
        pn_list_movie_2 = new javax.swing.JPanel();
        pn_list_5 = new javax.swing.JPanel();
        lb_list_tenphim_7 = new javax.swing.JLabel();
        lb_list_thoiluong_7 = new javax.swing.JLabel();
        btn_list_2_26 = new javax.swing.JButton();
        btn_list_2_27 = new javax.swing.JButton();
        btn_list_2_28 = new javax.swing.JButton();
        btn_list_2_29 = new javax.swing.JButton();
        btn_list_2_30 = new javax.swing.JButton();
        pn_list_6 = new javax.swing.JPanel();
        lb_list_tenphim_8 = new javax.swing.JLabel();
        lb_list_thoiluong_8 = new javax.swing.JLabel();
        btn_list_2_31 = new javax.swing.JButton();
        btn_list_2_32 = new javax.swing.JButton();
        btn_list_2_33 = new javax.swing.JButton();
        btn_list_2_34 = new javax.swing.JButton();
        btn_list_2_35 = new javax.swing.JButton();
        pn_list_7 = new javax.swing.JPanel();
        lb_list_tenphim_9 = new javax.swing.JLabel();
        lb_list_thoiluong_9 = new javax.swing.JLabel();
        btn_list_2_36 = new javax.swing.JButton();
        btn_list_2_37 = new javax.swing.JButton();
        btn_list_2_38 = new javax.swing.JButton();
        btn_list_2_39 = new javax.swing.JButton();
        btn_list_2_40 = new javax.swing.JButton();
        pn_list_8 = new javax.swing.JPanel();
        lb_list_tenphim_10 = new javax.swing.JLabel();
        lb_list_thoiluong_10 = new javax.swing.JLabel();
        btn_list_2_41 = new javax.swing.JButton();
        btn_list_2_42 = new javax.swing.JButton();
        btn_list_2_43 = new javax.swing.JButton();
        btn_list_2_44 = new javax.swing.JButton();
        btn_list_2_45 = new javax.swing.JButton();
        pn_list_button = new javax.swing.JPanel();
        btn_list_1 = new javax.swing.JButton();
        btn_list_2 = new javax.swing.JButton();
        pn_right = new javax.swing.JPanel();
        pn_right_ticket = new javax.swing.JPanel();
        pn_right_head_ticket = new javax.swing.JPanel();
        lblRapPhim = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblHoaDon = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        pn_right_body_ticket = new javax.swing.JPanel();
        lblNgayMuaVe = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        lbNgaymuave = new javax.swing.JLabel();
        lbKhachhang = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lbNhanVien = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tb_hoadon = new javax.swing.JTable();
        lblSuatChieu = new javax.swing.JLabel();
        lbSuatchieu = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbChietKhau = new javax.swing.JLabel();
        lblTongHoaDon = new javax.swing.JLabel();
        lbTongtien = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        lblLoaiVe = new javax.swing.JLabel();
        lbLoaive = new javax.swing.JLabel();
        lbMaVe = new javax.swing.JLabel();
        btnRemoveAll = new javax.swing.JButton();
        btn_HoanThanh = new javax.swing.JButton();
        pn_below = new javax.swing.JPanel();
        btn_below_menubar = new javax.swing.JButton();
        pn_below_service = new javax.swing.JPanel();
        btn_below_service_voucher = new javax.swing.JButton();
        btn_below_service_food = new javax.swing.JButton();
        btn_below_service_save = new javax.swing.JButton();
        btn_below_service_scanner = new javax.swing.JButton();
        pn_below_pay = new javax.swing.JPanel();
        btn_below_pay_wallet = new javax.swing.JButton();
        btn_below_pay_QR = new javax.swing.JButton();
        btn_below_pay_money = new javax.swing.JButton();
        btn_below_pay_ATM = new javax.swing.JButton();
        btn_below_btnLogout = new javax.swing.JButton();
        pn_center = new javax.swing.JPanel();
        btn_big_man = new javax.swing.JButton();
        btn_boy = new javax.swing.JButton();
        btn_student = new javax.swing.JButton();
        btn_vip = new javax.swing.JButton();
        btn_center_choose_seats = new javax.swing.JButton();
        lb_center_price_big_man = new javax.swing.JLabel();
        lb_center_price_boy = new javax.swing.JLabel();
        lb_center_price_student = new javax.swing.JLabel();
        lb_center_price_vip = new javax.swing.JLabel();
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
        setTitle("Galaxy ME IDE 1.7.0");
        setUndecorated(true);

        pn_above.setBackground(new java.awt.Color(153, 153, 153));
        pn_above.setPreferredSize(new java.awt.Dimension(1075, 60));

        lb_above_employee.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lb_above_employee.setForeground(new java.awt.Color(255, 255, 255));
        lb_above_employee.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_above_employee.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/account.png"))); // NOI18N
        lb_above_employee.setText("...");

        lb_above_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_above_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/title/icon_70.png"))); // NOI18N

        lb_above_clock.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lb_above_clock.setForeground(new java.awt.Color(255, 255, 255));
        lb_above_clock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_above_clock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/clock.png"))); // NOI18N
        lb_above_clock.setText("12:00:00 AM");

        lb_above_slogan.setForeground(new java.awt.Color(255, 255, 255));
        lb_above_slogan.setText("Chất lượng không thể nào thiếu phong cách");

        lb_above_logoName.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lb_above_logoName.setForeground(new java.awt.Color(255, 255, 255));
        lb_above_logoName.setText("GALAXY ME");

        javax.swing.GroupLayout pn_aboveLayout = new javax.swing.GroupLayout(pn_above);
        pn_above.setLayout(pn_aboveLayout);
        pn_aboveLayout.setHorizontalGroup(
            pn_aboveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_aboveLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(lb_above_logo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_aboveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_above_slogan, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_above_logoName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 926, Short.MAX_VALUE)
                .addComponent(lb_above_clock, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_above_employee)
                .addGap(63, 63, 63))
        );
        pn_aboveLayout.setVerticalGroup(
            pn_aboveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_aboveLayout.createSequentialGroup()
                .addGroup(pn_aboveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lb_above_logo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pn_aboveLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pn_aboveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lb_above_employee, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lb_above_clock, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1))
            .addGroup(pn_aboveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_above_logoName)
                .addGap(3, 3, 3)
                .addComponent(lb_above_slogan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(pn_above, java.awt.BorderLayout.PAGE_START);

        pn_left.setBackground(new java.awt.Color(255, 255, 255));
        pn_left.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        pn_left.setPreferredSize(new java.awt.Dimension(500, 430));
        pn_left.setLayout(new java.awt.BorderLayout());

        pn_left_filter.setPreferredSize(new java.awt.Dimension(490, 50));
        pn_left_filter.setLayout(new java.awt.GridLayout(1, 6));

        btn_time0.setBackground(new java.awt.Color(0, 0, 0));
        btn_time0.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btn_time0.setForeground(new java.awt.Color(255, 255, 255));
        btn_time0.setText("...");
        btn_time0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_time0ActionPerformed(evt);
            }
        });
        pn_left_filter.add(btn_time0);

        btn_time1.setBackground(new java.awt.Color(0, 0, 0));
        btn_time1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btn_time1.setForeground(new java.awt.Color(255, 255, 255));
        btn_time1.setText("...");
        btn_time1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_time1ActionPerformed(evt);
            }
        });
        pn_left_filter.add(btn_time1);

        btn_time2.setBackground(new java.awt.Color(0, 0, 0));
        btn_time2.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btn_time2.setForeground(new java.awt.Color(255, 255, 255));
        btn_time2.setText("...");
        btn_time2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_time2ActionPerformed(evt);
            }
        });
        pn_left_filter.add(btn_time2);

        btn_time3.setBackground(new java.awt.Color(0, 0, 0));
        btn_time3.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btn_time3.setForeground(new java.awt.Color(255, 255, 255));
        btn_time3.setText("...");
        btn_time3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_time3ActionPerformed(evt);
            }
        });
        pn_left_filter.add(btn_time3);

        btn_time4.setBackground(new java.awt.Color(0, 0, 0));
        btn_time4.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btn_time4.setForeground(new java.awt.Color(255, 255, 255));
        btn_time4.setText("...");
        btn_time4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_time4ActionPerformed(evt);
            }
        });
        pn_left_filter.add(btn_time4);

        btn_time5.setBackground(new java.awt.Color(0, 0, 0));
        btn_time5.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        btn_time5.setForeground(new java.awt.Color(255, 255, 255));
        btn_time5.setText("...");
        btn_time5.setName(""); // NOI18N
        btn_time5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_time5ActionPerformed(evt);
            }
        });
        pn_left_filter.add(btn_time5);

        pn_left.add(pn_left_filter, java.awt.BorderLayout.PAGE_START);

        pn_left_list_movie_container.setBackground(new java.awt.Color(255, 255, 255));
        pn_left_list_movie_container.setLayout(new java.awt.BorderLayout());

        pn_list_movie.setBackground(new java.awt.Color(255, 255, 255));
        pn_list_movie.setName(""); // NOI18N
        pn_list_movie.setLayout(new java.awt.CardLayout());

        pn_list_1.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_2.setForeground(new java.awt.Color(255, 0, 0));
        lb_list_tenphim_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_1.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_1.setText("7:00 - 9:00");
        btn_list_2_1.setEnabled(false);

        btn_list_2_2.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_2.setText("13:00 - 15:00");
        btn_list_2_2.setEnabled(false);

        btn_list_2_3.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_3.setText("16:00 - 18:00");
        btn_list_2_3.setEnabled(false);

        btn_list_2_4.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_4.setText("19:00 - 21:00");
        btn_list_2_4.setEnabled(false);

        btn_list_2_5.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_5.setText("10:00 - 12:00");
        btn_list_2_5.setEnabled(false);

        javax.swing.GroupLayout pn_list_1Layout = new javax.swing.GroupLayout(pn_list_1);
        pn_list_1.setLayout(pn_list_1Layout);
        pn_list_1Layout.setHorizontalGroup(
            pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_5, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(btn_list_2_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_2, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(btn_list_2_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_4)
                .addContainerGap())
            .addComponent(lb_list_tenphim_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_1Layout.setVerticalGroup(
            pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_list_1Layout.createSequentialGroup()
                        .addGroup(pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_1)
                            .addComponent(btn_list_2_2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_list_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_5)
                            .addComponent(btn_list_2_3)))
                    .addComponent(btn_list_2_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_list_2.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_3.setForeground(new java.awt.Color(255, 0, 0));
        lb_list_tenphim_3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_6.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_6.setText("7:00 - 9:00");
        btn_list_2_6.setEnabled(false);

        btn_list_2_7.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_7.setText("13:00 - 15:00");
        btn_list_2_7.setEnabled(false);

        btn_list_2_8.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_8.setText("16:00 - 18:00");
        btn_list_2_8.setEnabled(false);

        btn_list_2_9.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_9.setText("19:00 - 21:00");
        btn_list_2_9.setEnabled(false);

        btn_list_2_10.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_10.setText("10:00 - 12:00");
        btn_list_2_10.setEnabled(false);

        javax.swing.GroupLayout pn_list_2Layout = new javax.swing.GroupLayout(pn_list_2);
        pn_list_2.setLayout(pn_list_2Layout);
        pn_list_2Layout.setHorizontalGroup(
            pn_list_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_10, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_9)
                .addContainerGap())
            .addComponent(lb_list_tenphim_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_2Layout.setVerticalGroup(
            pn_list_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_list_2Layout.createSequentialGroup()
                        .addComponent(btn_list_2_6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_10))
                    .addComponent(btn_list_2_9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_list_2Layout.createSequentialGroup()
                        .addComponent(btn_list_2_7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_8)))
                .addContainerGap())
        );

        pn_list_3.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_4.setForeground(new java.awt.Color(255, 0, 0));
        lb_list_tenphim_4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_11.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_11.setText("7:00 - 9:00");
        btn_list_2_11.setEnabled(false);

        btn_list_2_12.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_12.setText("13:00 - 15:00");
        btn_list_2_12.setEnabled(false);

        btn_list_2_13.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_13.setText("16:00 - 18:00");
        btn_list_2_13.setEnabled(false);

        btn_list_2_14.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_14.setText("19:00 - 21:00");
        btn_list_2_14.setEnabled(false);

        btn_list_2_15.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_15.setText("10:00 - 12:00");
        btn_list_2_15.setEnabled(false);

        javax.swing.GroupLayout pn_list_3Layout = new javax.swing.GroupLayout(pn_list_3);
        pn_list_3.setLayout(pn_list_3Layout);
        pn_list_3Layout.setHorizontalGroup(
            pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_15, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_14)
                .addContainerGap())
            .addComponent(lb_list_tenphim_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_3Layout.setVerticalGroup(
            pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_list_3Layout.createSequentialGroup()
                        .addGroup(pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_11)
                            .addComponent(btn_list_2_12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_list_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_15)
                            .addComponent(btn_list_2_13))))
                .addContainerGap())
        );

        pn_list_4.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_6.setForeground(new java.awt.Color(255, 0, 0));
        lb_list_tenphim_6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_21.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_21.setText("7:00 - 9:00");
        btn_list_2_21.setEnabled(false);

        btn_list_2_22.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_22.setText("13:00 - 15:00");
        btn_list_2_22.setEnabled(false);

        btn_list_2_23.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_23.setText("16:00 - 18:00");
        btn_list_2_23.setEnabled(false);

        btn_list_2_24.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_24.setText("19:00 - 21:00");
        btn_list_2_24.setEnabled(false);

        btn_list_2_25.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_25.setText("10:00 - 12:00");
        btn_list_2_25.setEnabled(false);

        javax.swing.GroupLayout pn_list_4Layout = new javax.swing.GroupLayout(pn_list_4);
        pn_list_4.setLayout(pn_list_4Layout);
        pn_list_4Layout.setHorizontalGroup(
            pn_list_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_25, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_24)
                .addContainerGap())
            .addComponent(lb_list_tenphim_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_4Layout.setVerticalGroup(
            pn_list_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_list_4Layout.createSequentialGroup()
                        .addComponent(btn_list_2_21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_25))
                    .addComponent(btn_list_2_24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_list_4Layout.createSequentialGroup()
                        .addComponent(btn_list_2_22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_23)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_list_movie_1Layout = new javax.swing.GroupLayout(pn_list_movie_1);
        pn_list_movie_1.setLayout(pn_list_movie_1Layout);
        pn_list_movie_1Layout.setHorizontalGroup(
            pn_list_movie_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_movie_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_movie_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_list_1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_list_2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_list_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_list_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_list_movie_1Layout.setVerticalGroup(
            pn_list_movie_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_movie_1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_list_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_list_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_list_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_list_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        pn_list_movie.add(pn_list_movie_1, "card5");

        pn_list_movie_2.setEnabled(false);

        pn_list_5.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_7.setForeground(new java.awt.Color(255, 0, 51));
        lb_list_tenphim_7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_26.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_26.setText("7:00 - 9:00");
        btn_list_2_26.setEnabled(false);

        btn_list_2_27.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_27.setText("13:00 - 15:00");
        btn_list_2_27.setEnabled(false);

        btn_list_2_28.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_28.setText("16:00 - 18:00");
        btn_list_2_28.setEnabled(false);

        btn_list_2_29.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_29.setText("19:00 - 21:00");
        btn_list_2_29.setEnabled(false);

        btn_list_2_30.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_30.setText("10:00 - 12:00");
        btn_list_2_30.setEnabled(false);

        javax.swing.GroupLayout pn_list_5Layout = new javax.swing.GroupLayout(pn_list_5);
        pn_list_5.setLayout(pn_list_5Layout);
        pn_list_5Layout.setHorizontalGroup(
            pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_30, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                    .addComponent(btn_list_2_26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_27, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(btn_list_2_28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_29)
                .addContainerGap())
            .addComponent(lb_list_tenphim_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_5Layout.setVerticalGroup(
            pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_list_5Layout.createSequentialGroup()
                        .addGroup(pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_26)
                            .addComponent(btn_list_2_27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_list_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_30)
                            .addComponent(btn_list_2_28)))
                    .addComponent(btn_list_2_29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_list_6.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_8.setForeground(new java.awt.Color(255, 0, 51));
        lb_list_tenphim_8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_31.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_31.setText("7:00 - 9:00");
        btn_list_2_31.setEnabled(false);

        btn_list_2_32.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_32.setText("13:00 - 15:00");
        btn_list_2_32.setEnabled(false);

        btn_list_2_33.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_33.setText("16:00 - 18:00");
        btn_list_2_33.setEnabled(false);

        btn_list_2_34.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_34.setText("19:00 - 21:00");
        btn_list_2_34.setEnabled(false);

        btn_list_2_35.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_35.setText("10:00 - 12:00");
        btn_list_2_35.setEnabled(false);

        javax.swing.GroupLayout pn_list_6Layout = new javax.swing.GroupLayout(pn_list_6);
        pn_list_6.setLayout(pn_list_6Layout);
        pn_list_6Layout.setHorizontalGroup(
            pn_list_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_35, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_34)
                .addContainerGap())
            .addComponent(lb_list_tenphim_8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_6Layout.setVerticalGroup(
            pn_list_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_list_6Layout.createSequentialGroup()
                        .addComponent(btn_list_2_31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_35))
                    .addComponent(btn_list_2_34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_list_6Layout.createSequentialGroup()
                        .addComponent(btn_list_2_32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_33)))
                .addContainerGap())
        );

        pn_list_7.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_9.setForeground(new java.awt.Color(255, 0, 51));
        lb_list_tenphim_9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_36.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_36.setText("7:00 - 9:00");
        btn_list_2_36.setEnabled(false);

        btn_list_2_37.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_37.setText("13:00 - 15:00");
        btn_list_2_37.setEnabled(false);

        btn_list_2_38.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_38.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_38.setText("16:00 - 18:00");
        btn_list_2_38.setEnabled(false);

        btn_list_2_39.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_39.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_39.setText("19:00 - 21:00");
        btn_list_2_39.setEnabled(false);

        btn_list_2_40.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_40.setText("10:00 - 12:00");
        btn_list_2_40.setEnabled(false);

        javax.swing.GroupLayout pn_list_7Layout = new javax.swing.GroupLayout(pn_list_7);
        pn_list_7.setLayout(pn_list_7Layout);
        pn_list_7Layout.setHorizontalGroup(
            pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_40, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_39)
                .addContainerGap())
            .addComponent(lb_list_tenphim_9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_7Layout.setVerticalGroup(
            pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_list_7Layout.createSequentialGroup()
                        .addGroup(pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_36)
                            .addComponent(btn_list_2_37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pn_list_7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_list_2_40)
                            .addComponent(btn_list_2_38))))
                .addContainerGap())
        );

        pn_list_8.setBackground(new java.awt.Color(255, 255, 255));

        lb_list_tenphim_10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_list_tenphim_10.setForeground(new java.awt.Color(255, 0, 51));
        lb_list_tenphim_10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        lb_list_thoiluong_10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lb_list_thoiluong_10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btn_list_2_41.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_41.setText("7:00 - 9:00");
        btn_list_2_41.setEnabled(false);

        btn_list_2_42.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_42.setText("13:00 - 15:00");
        btn_list_2_42.setEnabled(false);

        btn_list_2_43.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_43.setText("16:00 - 18:00");
        btn_list_2_43.setEnabled(false);

        btn_list_2_44.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_44.setText("19:00 - 21:00");
        btn_list_2_44.setEnabled(false);

        btn_list_2_45.setBackground(new java.awt.Color(255, 255, 255));
        btn_list_2_45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_list_2_45.setText("10:00 - 12:00");
        btn_list_2_45.setEnabled(false);

        javax.swing.GroupLayout pn_list_8Layout = new javax.swing.GroupLayout(pn_list_8);
        pn_list_8.setLayout(pn_list_8Layout);
        pn_list_8Layout.setHorizontalGroup(
            pn_list_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_list_2_41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_45, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_list_2_42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_list_2_43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_list_2_44)
                .addContainerGap())
            .addComponent(lb_list_tenphim_10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_list_thoiluong_10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_list_8Layout.setVerticalGroup(
            pn_list_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lb_list_tenphim_10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_list_thoiluong_10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_list_8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_list_8Layout.createSequentialGroup()
                        .addComponent(btn_list_2_41)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_45))
                    .addComponent(btn_list_2_44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_list_8Layout.createSequentialGroup()
                        .addComponent(btn_list_2_42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_list_2_43)))
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_list_movie_2Layout = new javax.swing.GroupLayout(pn_list_movie_2);
        pn_list_movie_2.setLayout(pn_list_movie_2Layout);
        pn_list_movie_2Layout.setHorizontalGroup(
            pn_list_movie_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_movie_2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_list_movie_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_list_5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_list_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_list_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_list_8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pn_list_movie_2Layout.setVerticalGroup(
            pn_list_movie_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_list_movie_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_list_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_list_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_list_7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_list_8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        pn_list_movie.add(pn_list_movie_2, "card5");

        pn_left_list_movie_container.add(pn_list_movie, java.awt.BorderLayout.CENTER);

        pn_list_button.setBackground(new java.awt.Color(255, 255, 255));
        pn_list_button.setPreferredSize(new java.awt.Dimension(40, 545));
        pn_list_button.setLayout(new java.awt.GridLayout(2, 1));

        btn_list_1.setBackground(new java.awt.Color(153, 153, 153));
        btn_list_1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/above_small.png"))); // NOI18N
        btn_list_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_list_1ActionPerformed(evt);
            }
        });
        pn_list_button.add(btn_list_1);

        btn_list_2.setBackground(new java.awt.Color(153, 153, 153));
        btn_list_2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/below_small.png"))); // NOI18N
        btn_list_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_list_2ActionPerformed(evt);
            }
        });
        pn_list_button.add(btn_list_2);

        pn_left_list_movie_container.add(pn_list_button, java.awt.BorderLayout.LINE_END);

        pn_left.add(pn_left_list_movie_container, java.awt.BorderLayout.CENTER);

        getContentPane().add(pn_left, java.awt.BorderLayout.LINE_START);

        pn_right.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        pn_right.setPreferredSize(new java.awt.Dimension(500, 595));

        pn_right_ticket.setBackground(new java.awt.Color(255, 255, 255));
        pn_right_ticket.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pn_right_head_ticket.setBackground(new java.awt.Color(255, 255, 255));

        lblRapPhim.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblRapPhim.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRapPhim.setText("RẠP CHIẾU PHIM GALAXY MOVIE");

        jLabel7.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("54 NGUYỄN THIỆN THẬP - Q.LIÊN CHIỂU - KP.HÒA NAM - TP.ĐÀ NẴNG");

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ĐT : 091 825 7468 - (028) 758 8965");

        lblHoaDon.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(0, 102, 255));
        lblHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHoaDon.setText("HÓA ĐƠN VÉ PHIM");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout pn_right_head_ticketLayout = new javax.swing.GroupLayout(pn_right_head_ticket);
        pn_right_head_ticket.setLayout(pn_right_head_ticketLayout);
        pn_right_head_ticketLayout.setHorizontalGroup(
            pn_right_head_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblRapPhim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_right_head_ticketLayout.setVerticalGroup(
            pn_right_head_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_right_head_ticketLayout.createSequentialGroup()
                .addComponent(lblRapPhim, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHoaDon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pn_right_body_ticket.setBackground(new java.awt.Color(255, 255, 255));

        lblNgayMuaVe.setText("∘ Ngày mua vé  : ");

        lblTenKH.setText("∘ Tên KH   : ");

        lbNgaymuave.setText("...");

        lbKhachhang.setText("...");

        lblMaNV.setText("∘ Mã nhân viên  :");

        lbNhanVien.setText("...");

        tb_hoadon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Phòng chiếu", "Mã ghế ngồi", "Tên bộ phim"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tb_hoadon.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tb_hoadon.setRowHeight(20);
        tb_hoadon.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tb_hoadon);

        lblSuatChieu.setText("∘ Suất chiếu  : ");

        lbSuatchieu.setText("...");

        jLabel27.setText("∘ Voucher : ");

        lbChietKhau.setText("...");

        lblTongHoaDon.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblTongHoaDon.setText("TỔNG HÓA ĐƠN :");

        lbTongtien.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        lbTongtien.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbTongtien.setText("0");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("VND");

        lblLoaiVe.setText("∘ Loại vé            : ");

        lbLoaive.setText("...");

        lbMaVe.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbMaVe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMaVe.setText("-------------------------   MÃ VÉ: 500   -------------------------");

        javax.swing.GroupLayout pn_right_body_ticketLayout = new javax.swing.GroupLayout(pn_right_body_ticket);
        pn_right_body_ticket.setLayout(pn_right_body_ticketLayout);
        pn_right_body_ticketLayout.setHorizontalGroup(
            pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                        .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                                .addComponent(lblSuatChieu)
                                .addGap(7, 7, 7)
                                .addComponent(lbSuatchieu, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbChietKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                                .addComponent(lblTenKH)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbKhachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNgayMuaVe)
                            .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                                        .addComponent(lblLoaiVe)
                                        .addGap(2, 2, 2)
                                        .addComponent(lbLoaive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                                        .addComponent(lblMaNV)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbNgaymuave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                            .addComponent(lblTongHoaDon)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbTongtien, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel25))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addComponent(lbMaVe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_right_body_ticketLayout.setVerticalGroup(
            pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_right_body_ticketLayout.createSequentialGroup()
                .addComponent(lbMaVe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTenKH)
                    .addComponent(lbKhachhang)
                    .addComponent(lblNgayMuaVe)
                    .addComponent(lbNgaymuave))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(lbChietKhau)
                    .addComponent(lblMaNV)
                    .addComponent(lbNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSuatChieu)
                    .addComponent(lbSuatchieu)
                    .addComponent(lblLoaiVe)
                    .addComponent(lbLoaive))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_right_body_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongHoaDon)
                    .addComponent(lbTongtien)
                    .addComponent(jLabel25))
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout pn_right_ticketLayout = new javax.swing.GroupLayout(pn_right_ticket);
        pn_right_ticket.setLayout(pn_right_ticketLayout);
        pn_right_ticketLayout.setHorizontalGroup(
            pn_right_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pn_right_head_ticket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pn_right_body_ticket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pn_right_ticketLayout.setVerticalGroup(
            pn_right_ticketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_right_ticketLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pn_right_head_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_right_body_ticket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnRemoveAll.setBackground(new java.awt.Color(255, 255, 255));
        btnRemoveAll.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRemoveAll.setForeground(new java.awt.Color(255, 0, 51));
        btnRemoveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/delete_all.png"))); // NOI18N
        btnRemoveAll.setText("XÓA TẤT CẢ");
        btnRemoveAll.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoveAll.setIconTextGap(8);
        btnRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveAllActionPerformed(evt);
            }
        });

        btn_HoanThanh.setBackground(new java.awt.Color(255, 255, 255));
        btn_HoanThanh.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btn_HoanThanh.setForeground(new java.awt.Color(0, 204, 0));
        btn_HoanThanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/done.png"))); // NOI18N
        btn_HoanThanh.setText("HOÀN THÀNH");
        btn_HoanThanh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_HoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HoanThanhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_rightLayout = new javax.swing.GroupLayout(pn_right);
        pn_right.setLayout(pn_rightLayout);
        pn_rightLayout.setHorizontalGroup(
            pn_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_rightLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_HoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(btnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pn_rightLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(pn_right_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pn_rightLayout.setVerticalGroup(
            pn_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_rightLayout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(pn_right_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pn_rightLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_HoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(pn_right, java.awt.BorderLayout.LINE_END);

        pn_below.setBackground(new java.awt.Color(102, 102, 102));
        pn_below.setPreferredSize(new java.awt.Dimension(1322, 110));

        btn_below_menubar.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_menubar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/menu_bar.png"))); // NOI18N
        btn_below_menubar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_menubar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_menubarActionPerformed(evt);
            }
        });

        pn_below_service.setBackground(new java.awt.Color(0, 0, 0));
        pn_below_service.setLayout(new java.awt.GridLayout(2, 2));

        btn_below_service_voucher.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_service_voucher.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_service_voucher.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/voucher.png"))); // NOI18N
        btn_below_service_voucher.setText("Mã Voucher");
        btn_below_service_voucher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_service_voucher.setIconTextGap(8);
        btn_below_service_voucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_service_voucherActionPerformed(evt);
            }
        });
        pn_below_service.add(btn_below_service_voucher);

        btn_below_service_food.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_service_food.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btn_below_service_food.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/food.png"))); // NOI18N
        btn_below_service_food.setText("Mua thức ăn");
        btn_below_service_food.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_service_food.setIconTextGap(16);
        btn_below_service_food.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_service_foodActionPerformed(evt);
            }
        });
        pn_below_service.add(btn_below_service_food);

        btn_below_service_save.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_service_save.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_service_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/save.png"))); // NOI18N
        btn_below_service_save.setText("Nhập lưu và tìm kiếm");
        btn_below_service_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_service_save.setIconTextGap(8);
        btn_below_service_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_service_saveActionPerformed(evt);
            }
        });
        pn_below_service.add(btn_below_service_save);

        btn_below_service_scanner.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_service_scanner.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_service_scanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/ma_vach.png"))); // NOI18N
        btn_below_service_scanner.setText("Máy quét mã vạch");
        btn_below_service_scanner.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_service_scanner.setIconTextGap(8);
        btn_below_service_scanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_service_scannerActionPerformed(evt);
            }
        });
        pn_below_service.add(btn_below_service_scanner);

        pn_below_pay.setBackground(new java.awt.Color(0, 0, 0));
        pn_below_pay.setLayout(new java.awt.GridLayout(2, 3));

        btn_below_pay_wallet.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_pay_wallet.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_pay_wallet.setForeground(new java.awt.Color(102, 102, 102));
        btn_below_pay_wallet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/e_wallet.png"))); // NOI18N
        btn_below_pay_wallet.setText("Ví điện tử");
        btn_below_pay_wallet.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_pay_wallet.setIconTextGap(8);
        btn_below_pay_wallet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_pay_walletActionPerformed(evt);
            }
        });
        pn_below_pay.add(btn_below_pay_wallet);

        btn_below_pay_QR.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_pay_QR.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_pay_QR.setForeground(new java.awt.Color(102, 102, 102));
        btn_below_pay_QR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/qr_code.png"))); // NOI18N
        btn_below_pay_QR.setText("QR-Code");
        btn_below_pay_QR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_pay_QR.setIconTextGap(8);
        btn_below_pay_QR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_pay_QRActionPerformed(evt);
            }
        });
        pn_below_pay.add(btn_below_pay_QR);

        btn_below_pay_money.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_pay_money.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_pay_money.setForeground(new java.awt.Color(102, 102, 102));
        btn_below_pay_money.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/money.png"))); // NOI18N
        btn_below_pay_money.setText("Trả Tiền mặt");
        btn_below_pay_money.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_pay_money.setIconTextGap(8);
        btn_below_pay_money.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_pay_moneyActionPerformed(evt);
            }
        });
        pn_below_pay.add(btn_below_pay_money);

        btn_below_pay_ATM.setBackground(new java.awt.Color(255, 255, 255));
        btn_below_pay_ATM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_below_pay_ATM.setForeground(new java.awt.Color(102, 102, 102));
        btn_below_pay_ATM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/credit-card.png"))); // NOI18N
        btn_below_pay_ATM.setText("Thẻ ATM   ");
        btn_below_pay_ATM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below_pay_ATM.setIconTextGap(8);
        btn_below_pay_ATM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_below_pay_ATMActionPerformed(evt);
            }
        });
        pn_below_pay.add(btn_below_pay_ATM);

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

        javax.swing.GroupLayout pn_belowLayout = new javax.swing.GroupLayout(pn_below);
        pn_below.setLayout(pn_belowLayout);
        pn_belowLayout.setHorizontalGroup(
            pn_belowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_belowLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(btn_below_menubar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_below_service, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pn_below_pay, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_below_btnLogout)
                .addContainerGap())
        );
        pn_belowLayout.setVerticalGroup(
            pn_belowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_belowLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_belowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pn_below_service, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_below_menubar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pn_below_pay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_below_btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        getContentPane().add(pn_below, java.awt.BorderLayout.PAGE_END);

        pn_center.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        btn_big_man.setBackground(new java.awt.Color(255, 255, 255));
        btn_big_man.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btn_big_man.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/big_man.png"))); // NOI18N
        btn_big_man.setText("NGƯỜI LỚN");
        btn_big_man.setBorder(null);
        btn_big_man.setBorderPainted(false);
        btn_big_man.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_big_man.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_big_man.setIconTextGap(20);
        btn_big_man.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_big_man.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_big_manActionPerformed(evt);
            }
        });

        btn_boy.setBackground(new java.awt.Color(255, 255, 255));
        btn_boy.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btn_boy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/child.png"))); // NOI18N
        btn_boy.setText("TRẺ EM");
        btn_boy.setToolTipText("");
        btn_boy.setBorder(null);
        btn_boy.setBorderPainted(false);
        btn_boy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_boy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_boy.setIconTextGap(20);
        btn_boy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_boy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_boyActionPerformed(evt);
            }
        });

        btn_student.setBackground(new java.awt.Color(255, 255, 255));
        btn_student.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btn_student.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/student.png"))); // NOI18N
        btn_student.setText("SINH VIÊN");
        btn_student.setBorder(null);
        btn_student.setBorderPainted(false);
        btn_student.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_student.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_student.setIconTextGap(16);
        btn_student.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_student.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_studentActionPerformed(evt);
            }
        });

        btn_vip.setBackground(new java.awt.Color(255, 255, 255));
        btn_vip.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btn_vip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/vip.png"))); // NOI18N
        btn_vip.setText("GHẾ VIP");
        btn_vip.setBorder(null);
        btn_vip.setBorderPainted(false);
        btn_vip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_vip.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_vip.setIconTextGap(20);
        btn_vip.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_vip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_vipActionPerformed(evt);
            }
        });

        btn_center_choose_seats.setBackground(new java.awt.Color(255, 255, 255));
        btn_center_choose_seats.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/choose_seat.png"))); // NOI18N
        btn_center_choose_seats.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_center_choose_seats.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_center_choose_seatsActionPerformed(evt);
            }
        });

        lb_center_price_big_man.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_center_price_big_man.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_center_price_big_man.setText("75.000 đồng / 4 USA");

        lb_center_price_boy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_center_price_boy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_center_price_boy.setText("50.000 đồng / 2 USA");

        lb_center_price_student.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_center_price_student.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_center_price_student.setText("50.000 đồng / 2 USA");

        lb_center_price_vip.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lb_center_price_vip.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_center_price_vip.setText("85.000 đồng / 5 USA");

        javax.swing.GroupLayout pn_centerLayout = new javax.swing.GroupLayout(pn_center);
        pn_center.setLayout(pn_centerLayout);
        pn_centerLayout.setHorizontalGroup(
            pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_centerLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_big_man, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_center_price_big_man, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_student, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pn_centerLayout.createSequentialGroup()
                        .addComponent(btn_center_choose_seats, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lb_center_price_student, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(77, 77, 77)
                .addGroup(pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_vip, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_center_price_boy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lb_center_price_vip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_boy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(34, 34, 34))
        );
        pn_centerLayout.setVerticalGroup(
            pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_centerLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_big_man, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(btn_boy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_center_price_big_man)
                    .addComponent(lb_center_price_boy))
                .addGap(30, 30, 30)
                .addGroup(pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_vip, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addComponent(btn_student, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pn_centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_center_price_student)
                    .addComponent(lb_center_price_vip))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_center_choose_seats, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pn_center, java.awt.BorderLayout.CENTER);

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

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_center_choose_seatsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_center_choose_seatsActionPerformed
        MovePage.moveFrameToJDialog(new GLX_4a2_Choose_seats(this, true), this);
    }//GEN-LAST:event_btn_center_choose_seatsActionPerformed

    private void menu_system_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_logoutActionPerformed
        this.Logout();
    }//GEN-LAST:event_menu_system_logoutActionPerformed

    private void menu_system_changepassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_changepassActionPerformed
        MovePage.moveFrameToJDialog(new GLX_4ab_changePassword(this, true), this);
    }//GEN-LAST:event_menu_system_changepassActionPerformed

    private void menu_system_accountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menu_system_accountKeyPressed
        MovePage.moveFrameToJDialog(new GLX_4a1_Account(this, true), this);
    }//GEN-LAST:event_menu_system_accountKeyPressed

    private void menu_system_accountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_accountActionPerformed
        MovePage.moveFrameToJDialog(new GLX_4a1_Account(this, true), this);
    }//GEN-LAST:event_menu_system_accountActionPerformed

    private void btn_below_menubarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_menubarActionPerformed
        this.open_bar();
    }//GEN-LAST:event_btn_below_menubarActionPerformed

    private void btn_below_pay_ATMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_pay_ATMActionPerformed
        MovePage.showDialogNotHide(new GLX_4a3_Footer_ATM(this, true));
        this.pay();
        this.reset_ticket();
    }//GEN-LAST:event_btn_below_pay_ATMActionPerformed

    private void btn_below_pay_QRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_pay_QRActionPerformed
        MovePage.showDialogNotHide(new GLX_4a3_Footer_QR(this, true));
        this.pay();
        this.reset_ticket();
    }//GEN-LAST:event_btn_below_pay_QRActionPerformed

    private void btn_below_service_scannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_service_scannerActionPerformed
        MovePage.showDialogNotHide(new GLX_4a3_Footer_MaVach(this, true));
    }//GEN-LAST:event_btn_below_service_scannerActionPerformed

    private void btn_below_pay_moneyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_pay_moneyActionPerformed
        Save_helper.ve = pn_right_ticket;
        MovePage.showDialogNotHide(new GLX_4a3_Footer_Money(this, true));
        this.setMaVe(Save_helper.Language);
    }//GEN-LAST:event_btn_below_pay_moneyActionPerformed

    private void btn_below_pay_walletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_pay_walletActionPerformed
        MovePage.showDialogNotHide(new GLX_4a3_Footer_Wall(this, true));
    }//GEN-LAST:event_btn_below_pay_walletActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            URL path = this.getClass().getResource("/com/fpt/galaxymovie/HuongDan/HuongDan.html");
            Desktop.getDesktop().browse(new File(path.getPath()).toURI());
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        MovePage.showJFrameNotHide(new GLX_6_GioiThieu());
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void btn_big_manActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_big_manActionPerformed
        this.btnKindTicketActionPerformed(btn_big_man);
    }//GEN-LAST:event_btn_big_manActionPerformed

    private void btn_boyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_boyActionPerformed
        this.btnKindTicketActionPerformed(btn_boy);
    }//GEN-LAST:event_btn_boyActionPerformed

    private void btn_studentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_studentActionPerformed
        this.btnKindTicketActionPerformed(btn_student);
    }//GEN-LAST:event_btn_studentActionPerformed

    private void btn_vipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_vipActionPerformed
        this.btnKindTicketActionPerformed(btn_vip);
    }//GEN-LAST:event_btn_vipActionPerformed

    private void btn_below_btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_btnLogoutActionPerformed
        this.Logout();
    }//GEN-LAST:event_btn_below_btnLogoutActionPerformed

    private void btn_below_service_voucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_service_voucherActionPerformed
        String[] options = new String[16];
        options[0] = "...";
        for (int i = 1; i < 15; i++) {
            options[i] = RandomPrivacy.codeVoucher();
        }
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/logo/voucher_input1.png"));
        String n = (String) JOptionPane.showInputDialog(this, "What would you like a voucher ??\n✂ .... 01/10/2021 đến 10/02/2021 .... ✂", "Choose a voucher", JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
        lbChietKhau.setText(n);
        String loaive = lbLoaive.getText();
        if (n.equals("...")) {
            int tong = (tb_hoadon.getRowCount() != 0) ? Integer.parseInt(Money.getMoney(loaive)) * tb_hoadon.getRowCount() : Integer.parseInt(Money.getMoney(loaive));
            lbTongtien.setText("" + tong);
        } else {
            int tong = (tb_hoadon.getRowCount() != 0) ? Integer.parseInt(Money.getMoneyVoucher(loaive)) * tb_hoadon.getRowCount() : Integer.parseInt(Money.getMoneyVoucher(loaive));
            lbTongtien.setText("" + tong);
        }
    }//GEN-LAST:event_btn_below_service_voucherActionPerformed

    private void btn_below_service_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_service_saveActionPerformed
        MovePage.showDialogNotHide(new GLX_4a3_Footer_Save_and_find(this, true));
    }//GEN-LAST:event_btn_below_service_saveActionPerformed

    private void btnRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveAllActionPerformed
        this.reset_ticket();
    }//GEN-LAST:event_btnRemoveAllActionPerformed

    private void btn_HoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HoanThanhActionPerformed
        this.pay();               
    }//GEN-LAST:event_btn_HoanThanhActionPerformed

    private void btn_time0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_time0ActionPerformed
        focusTimeButton(0);
    }//GEN-LAST:event_btn_time0ActionPerformed

    private void btn_time1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_time1ActionPerformed
        focusTimeButton(1);
    }//GEN-LAST:event_btn_time1ActionPerformed

    private void btn_time2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_time2ActionPerformed
        focusTimeButton(2);
    }//GEN-LAST:event_btn_time2ActionPerformed

    private void btn_time3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_time3ActionPerformed
        focusTimeButton(3);
    }//GEN-LAST:event_btn_time3ActionPerformed

    private void btn_time4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_time4ActionPerformed
        focusTimeButton(4);
    }//GEN-LAST:event_btn_time4ActionPerformed

    private void btn_time5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_time5ActionPerformed
        focusTimeButton(5);
    }//GEN-LAST:event_btn_time5ActionPerformed

    private void btn_list_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_list_1ActionPerformed
        pn_list_movie_2.setVisible(true); pn_list_movie_1.setVisible(false);
    }//GEN-LAST:event_btn_list_1ActionPerformed

    private void btn_list_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_list_2ActionPerformed
        pn_list_movie_1.setVisible(true); pn_list_movie_2.setVisible(false);
    }//GEN-LAST:event_btn_list_2ActionPerformed

    private void menu_system_lan_VNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_lan_VNActionPerformed
        this.changeLG("vn");
    }//GEN-LAST:event_menu_system_lan_VNActionPerformed

    private void menu_system_lan_ENActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_system_lan_ENActionPerformed
        this.changeLG("en");
    }//GEN-LAST:event_menu_system_lan_ENActionPerformed

    private void menu_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_backActionPerformed
        MovePage.moveFrameToJDialog(new GLX_3_Choose_action(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_menu_backActionPerformed

    private void btn_below_service_foodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_below_service_foodActionPerformed
        JOptionPane.showMessageDialog(this, "Phần mềm hiện đang trong quá trình bảo trì.", "Maintance", 2);
    }//GEN-LAST:event_btn_below_service_foodActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_4a0_Buy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GLX_4a0_Buy().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuMain;
    private javax.swing.JButton btnRemoveAll;
    private javax.swing.JButton btn_HoanThanh;
    private javax.swing.JButton btn_below_btnLogout;
    private javax.swing.JButton btn_below_menubar;
    private javax.swing.JButton btn_below_pay_ATM;
    private javax.swing.JButton btn_below_pay_QR;
    private javax.swing.JButton btn_below_pay_money;
    private javax.swing.JButton btn_below_pay_wallet;
    private javax.swing.JButton btn_below_service_food;
    private javax.swing.JButton btn_below_service_save;
    private javax.swing.JButton btn_below_service_scanner;
    private javax.swing.JButton btn_below_service_voucher;
    private javax.swing.JButton btn_big_man;
    private javax.swing.JButton btn_boy;
    private javax.swing.JButton btn_center_choose_seats;
    private javax.swing.JButton btn_list_1;
    private javax.swing.JButton btn_list_2;
    private javax.swing.JButton btn_list_2_1;
    private javax.swing.JButton btn_list_2_10;
    private javax.swing.JButton btn_list_2_11;
    private javax.swing.JButton btn_list_2_12;
    private javax.swing.JButton btn_list_2_13;
    private javax.swing.JButton btn_list_2_14;
    private javax.swing.JButton btn_list_2_15;
    private javax.swing.JButton btn_list_2_2;
    private javax.swing.JButton btn_list_2_21;
    private javax.swing.JButton btn_list_2_22;
    private javax.swing.JButton btn_list_2_23;
    private javax.swing.JButton btn_list_2_24;
    private javax.swing.JButton btn_list_2_25;
    private javax.swing.JButton btn_list_2_26;
    private javax.swing.JButton btn_list_2_27;
    private javax.swing.JButton btn_list_2_28;
    private javax.swing.JButton btn_list_2_29;
    private javax.swing.JButton btn_list_2_3;
    private javax.swing.JButton btn_list_2_30;
    private javax.swing.JButton btn_list_2_31;
    private javax.swing.JButton btn_list_2_32;
    private javax.swing.JButton btn_list_2_33;
    private javax.swing.JButton btn_list_2_34;
    private javax.swing.JButton btn_list_2_35;
    private javax.swing.JButton btn_list_2_36;
    private javax.swing.JButton btn_list_2_37;
    private javax.swing.JButton btn_list_2_38;
    private javax.swing.JButton btn_list_2_39;
    private javax.swing.JButton btn_list_2_4;
    private javax.swing.JButton btn_list_2_40;
    private javax.swing.JButton btn_list_2_41;
    private javax.swing.JButton btn_list_2_42;
    private javax.swing.JButton btn_list_2_43;
    private javax.swing.JButton btn_list_2_44;
    private javax.swing.JButton btn_list_2_45;
    private javax.swing.JButton btn_list_2_5;
    private javax.swing.JButton btn_list_2_6;
    private javax.swing.JButton btn_list_2_7;
    private javax.swing.JButton btn_list_2_8;
    private javax.swing.JButton btn_list_2_9;
    private javax.swing.JButton btn_student;
    private javax.swing.JButton btn_time0;
    private javax.swing.JButton btn_time1;
    private javax.swing.JButton btn_time2;
    private javax.swing.JButton btn_time3;
    private javax.swing.JButton btn_time4;
    private javax.swing.JButton btn_time5;
    private javax.swing.JButton btn_vip;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel lbChietKhau;
    private javax.swing.JLabel lbKhachhang;
    private javax.swing.JLabel lbLoaive;
    private javax.swing.JLabel lbMaVe;
    private javax.swing.JLabel lbNgaymuave;
    private javax.swing.JLabel lbNhanVien;
    private javax.swing.JLabel lbSuatchieu;
    private javax.swing.JLabel lbTongtien;
    private javax.swing.JLabel lb_above_clock;
    private javax.swing.JLabel lb_above_employee;
    private javax.swing.JLabel lb_above_logo;
    private javax.swing.JLabel lb_above_logoName;
    private javax.swing.JLabel lb_above_slogan;
    private javax.swing.JLabel lb_center_price_big_man;
    private javax.swing.JLabel lb_center_price_boy;
    private javax.swing.JLabel lb_center_price_student;
    private javax.swing.JLabel lb_center_price_vip;
    private javax.swing.JLabel lb_list_tenphim_10;
    private javax.swing.JLabel lb_list_tenphim_2;
    private javax.swing.JLabel lb_list_tenphim_3;
    private javax.swing.JLabel lb_list_tenphim_4;
    private javax.swing.JLabel lb_list_tenphim_6;
    private javax.swing.JLabel lb_list_tenphim_7;
    private javax.swing.JLabel lb_list_tenphim_8;
    private javax.swing.JLabel lb_list_tenphim_9;
    private javax.swing.JLabel lb_list_thoiluong_10;
    private javax.swing.JLabel lb_list_thoiluong_2;
    private javax.swing.JLabel lb_list_thoiluong_3;
    private javax.swing.JLabel lb_list_thoiluong_4;
    private javax.swing.JLabel lb_list_thoiluong_6;
    private javax.swing.JLabel lb_list_thoiluong_7;
    private javax.swing.JLabel lb_list_thoiluong_8;
    private javax.swing.JLabel lb_list_thoiluong_9;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblLoaiVe;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblNgayMuaVe;
    private javax.swing.JLabel lblRapPhim;
    private javax.swing.JLabel lblSuatChieu;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTongHoaDon;
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
    private javax.swing.JPanel pn_above;
    private javax.swing.JPanel pn_below;
    private javax.swing.JPanel pn_below_pay;
    private javax.swing.JPanel pn_below_service;
    private javax.swing.JPanel pn_center;
    private javax.swing.JPanel pn_left;
    private javax.swing.JPanel pn_left_filter;
    private javax.swing.JPanel pn_left_list_movie_container;
    private javax.swing.JPanel pn_list_1;
    private javax.swing.JPanel pn_list_2;
    private javax.swing.JPanel pn_list_3;
    private javax.swing.JPanel pn_list_4;
    private javax.swing.JPanel pn_list_5;
    private javax.swing.JPanel pn_list_6;
    private javax.swing.JPanel pn_list_7;
    private javax.swing.JPanel pn_list_8;
    private javax.swing.JPanel pn_list_button;
    private javax.swing.JPanel pn_list_movie;
    private javax.swing.JPanel pn_list_movie_1;
    private javax.swing.JPanel pn_list_movie_2;
    private javax.swing.JPanel pn_right;
    private javax.swing.JPanel pn_right_body_ticket;
    private javax.swing.JPanel pn_right_head_ticket;
    private javax.swing.JPanel pn_right_ticket;
    private javax.swing.JTable tb_hoadon;
    // End of variables declaration//GEN-END:variables
}
