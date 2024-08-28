package com.fpt.galaxymovie.gui;

import java.awt.Color;
import javax.swing.JButton;
import com.fpt.galaxymovie.controller.MovePage;
import com.fpt.galaxymovie.database.dao.DAO;
import com.fpt.galaxymovie.database.helper.Save_helper;
import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.Ghe;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public final class GLX_4a2_Choose_seats extends javax.swing.JDialog {
    String[] room = {"A", "B", "C"};
    DAO<Integer> daoKeepIndex = new DAO<>();
    int row = 10, col = 10, hGap = 10, vGap = 10, roomID = 0;    
    String sql_ctcn = """ 
                      SELECT PhongChieuID, MaChoNgoi, Status
                      from ChiTietChoNgoi  
                      WHERE PhongChieuID=? and MaChoNgoi=? 
                      ORDER BY PhongChieuID, MaChoNgoi""";
    String sql_update_chair = "UPDATE ChiTietChoNgoi SET Status=? WHERE MaChoNgoi=? and PhongChieuID=?";
    String VN = "change_language_vn";
    String EN = "change_language_en";
    ResourceBundle resourceBundle = null;

    public GLX_4a2_Choose_seats(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lb_title.requestFocus();
        this.setLocationRelativeTo(null);
        this.autoFillUpChair();
        this.changeLG(Save_helper.Language);
    }
    
    void changeLG (String ngonngu){
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
            
            lb_title.setText(resourceBundle.getString("lb_title")); 
            lb_go_door.setText(resourceBundle.getString("lb_go_door")); 
            lblTrong.setText(resourceBundle.getString("lblTrong")); 
            lblDaDatGhe.setText(resourceBundle.getString("lblDaDatGhe")); 
            lblDangChonGhe.setText(resourceBundle.getString("lblDangChonGhe")); 
            lblCanhBao.setText(resourceBundle.getString("lblCanhBao")); 
            btn_HoanThanh.setText(resourceBundle.getString("btn_HoanThanh")); 
            btn_Thoat.setText(resourceBundle.getString("btn_Thoat")); 
            
        } catch (Exception e) {
        } 
    }
    
    // ---------- Show room and number of seats as that room --------------
    public void showPanelChooseSeat(int index){
        this.resetListAndDao(); 
        card_seats_1.setVisible(false);
        card_seats_2.setVisible(false);
        card_seats_3.setVisible(false);
        combox_room.setSelectedIndex( index - 1 );
        switch (index){
            case 1 -> {card_seats_1.setVisible(true);}
            case 2 -> {card_seats_2.setVisible(true);}
            case 3 -> {card_seats_3.setVisible(true);}
        }
    }

    // ------------------  Event ActionListener for button  --------------------
    class ButtonActionPerformed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton jButton = (JButton) e.getSource(); // get sourse of this button
            if (jButton.getBackground() == Color.YELLOW) {
                jButton.setBackground(Color.WHITE);
                daoKeepIndex.deleteObj( Integer.parseInt(jButton.getText()) );
            }
            else if (jButton.getBackground() == Color.RED) {
                Save_helper.btnDelete = jButton;
            }
            else {
                jButton.setBackground(Color.YELLOW);
                daoKeepIndex.insert( Integer.parseInt(jButton.getText()) );
            }
            repaint();
            validate();
        }
    }
    // ---------------- create object event of button   ------------------------
    ButtonActionPerformed buttonActionPerformed = new ButtonActionPerformed();
    

    // --------------- add seats bought after clicked --------------
    public void updateChairBought(){        
        // ----  create a temp arraylist to storedata temporarily  ----
        ArrayList<Ghe> list_chair = new ArrayList<>();
        String roomId = String.valueOf(combox_room.getSelectedItem());
        int status = 2;
        if (daoKeepIndex.getList().size() > 0) {
            daoKeepIndex.getList().stream().map(id -> new Ghe(id, roomId, status)).forEachOrdered(ghe -> {
                list_chair.add(ghe);
            });
            
            // chỉ lưu lại trong hashmap chờ để khi thanh toán bên BUY
            HashMap<String, ArrayList<Ghe>> hashMapChair = new HashMap<>();
            hashMapChair.put(roomId, list_chair);
            
            Save_helper.chairs = hashMapChair;
            
            JOptionPane.showMessageDialog(this, "Hoàn tất !");
            MovePage.moveJDialogToFrame(new GLX_4a0_Buy(), this);
        }
        else {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn ghế ?", "Lỗi chọn ghế", 3);
        }
    }
    
    // ---------------- increase auto number 100 chairs ------------------------
    public void ChooseFillCardSeatID(JPanel jPanel, String roomID){
        int index_start = 1;
        
        // create a GidLayout with row and col and distance each button created
        GridLayout gidLayout = new GridLayout(row, col);
        gidLayout.setHgap(hGap);
        gidLayout.setVgap(vGap);
        jPanel.setLayout(gidLayout);
        jPanel.setPreferredSize(new Dimension(950, 600));
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                // create button with content of index
                JButton btnButton = new JButton("" + index_start);
                btnButton.setActionCommand("" + index_start);

                // check id of seat with id room equals -1 or another number
                int status = this.checkStatusChair(roomID, index_start);
                if (status == 1) {
                    btnButton.setBackground(Color.WHITE);
                }
                else if (status == 2) {
                    btnButton.setBackground(Color.RED);
                }
                // add event for all of button created not distinct color
                btnButton.addActionListener(buttonActionPerformed);
                jPanel.add(btnButton);
                index_start++;
            }
        }
    }
    
    // ------------- auto insert fill up chair follow serial ------------------
    public void autoFillUpChair(){
        for (String room1 : room) {
            switch (room1) {
                case "A" -> {
                    this.ChooseFillCardSeatID(card_seats_1, room1);
                }
                case "B" -> {
                    this.ChooseFillCardSeatID(card_seats_2, room1);
                }
                case "C" -> {
                    this.ChooseFillCardSeatID(card_seats_3, room1);
                }
            }
        }
    }
    
    // ---------- this is a function to process status chair of a room ---------
    public int checkStatusChair(String roomID, int seatID){
        int status = 1;
        try {
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql_ctcn);
            ps.setString(1, roomID);
            ps.setInt(2, seatID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                status = rs.getInt(3);
                break;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "", 0);
        }
        return status;
    }
    
    public int findRoomID_below(){
        roomID++;
        if (roomID > room.length-1) {
            roomID = 0;
        }
        return roomID + 1;
    }
    
    public int findRoomID_above(){
        roomID--;
        if (roomID < 0) {
            roomID = room.length-1;
        }
        return roomID + 1;
    }
    
    public void resetListAndDao(){
        daoKeepIndex = new DAO<>();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pn_go = new javax.swing.JPanel();
        btn_above = new javax.swing.JButton();
        btn_below = new javax.swing.JButton();
        pn_body_container = new javax.swing.JPanel();
        pn_container = new javax.swing.JPanel();
        pn_title = new javax.swing.JPanel();
        lb_title = new javax.swing.JLabel();
        pn_container_content = new javax.swing.JPanel();
        lb_go_door = new javax.swing.JLabel();
        pn_card_seats = new javax.swing.JPanel();
        card_seats_1 = new javax.swing.JPanel();
        card_seats_2 = new javax.swing.JPanel();
        card_seats_3 = new javax.swing.JPanel();
        combox_room = new javax.swing.JComboBox<>();
        color_empty = new javax.swing.JPanel();
        color_empty1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblDaDatGhe = new javax.swing.JLabel();
        lblTrong = new javax.swing.JLabel();
        lblDangChonGhe = new javax.swing.JLabel();
        btn_HoanThanh = new javax.swing.JButton();
        btn_Thoat = new javax.swing.JButton();
        lblCanhBao = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        pn_go.setPreferredSize(new java.awt.Dimension(50, 583));
        pn_go.setLayout(new java.awt.GridLayout(2, 1));

        btn_above.setBackground(new java.awt.Color(0, 51, 51));
        btn_above.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/above_small.png"))); // NOI18N
        btn_above.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_above.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aboveActionPerformed(evt);
            }
        });
        pn_go.add(btn_above);

        btn_below.setBackground(new java.awt.Color(0, 51, 51));
        btn_below.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/below_small.png"))); // NOI18N
        btn_below.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_below.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_belowActionPerformed(evt);
            }
        });
        pn_go.add(btn_below);

        getContentPane().add(pn_go, java.awt.BorderLayout.LINE_END);

        pn_body_container.setBackground(new java.awt.Color(0, 0, 0));

        pn_container.setLayout(new java.awt.BorderLayout());

        pn_title.setBackground(new java.awt.Color(153, 153, 153));
        pn_title.setPreferredSize(new java.awt.Dimension(1032, 50));
        pn_title.setLayout(new java.awt.CardLayout());

        lb_title.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lb_title.setForeground(new java.awt.Color(255, 255, 255));
        lb_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_title.setText("MÀN HÌNH CHIẾU");
        pn_title.add(lb_title, "card2");

        pn_container.add(pn_title, java.awt.BorderLayout.PAGE_START);

        pn_container_content.setBackground(new java.awt.Color(255, 255, 255));

        lb_go_door.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lb_go_door.setForeground(new java.awt.Color(255, 0, 0));
        lb_go_door.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_go_door.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/go_door.png"))); // NOI18N
        lb_go_door.setText("VÀO CỬA");
        lb_go_door.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lb_go_door.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        pn_card_seats.setLayout(new java.awt.CardLayout());

        card_seats_1.setBackground(new java.awt.Color(252, 252, 252));

        javax.swing.GroupLayout card_seats_1Layout = new javax.swing.GroupLayout(card_seats_1);
        card_seats_1.setLayout(card_seats_1Layout);
        card_seats_1Layout.setHorizontalGroup(
            card_seats_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        card_seats_1Layout.setVerticalGroup(
            card_seats_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        pn_card_seats.add(card_seats_1, "card2");

        card_seats_2.setBackground(new java.awt.Color(252, 252, 252));

        javax.swing.GroupLayout card_seats_2Layout = new javax.swing.GroupLayout(card_seats_2);
        card_seats_2.setLayout(card_seats_2Layout);
        card_seats_2Layout.setHorizontalGroup(
            card_seats_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        card_seats_2Layout.setVerticalGroup(
            card_seats_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        pn_card_seats.add(card_seats_2, "card3");

        card_seats_3.setBackground(new java.awt.Color(252, 252, 252));

        javax.swing.GroupLayout card_seats_3Layout = new javax.swing.GroupLayout(card_seats_3);
        card_seats_3.setLayout(card_seats_3Layout);
        card_seats_3Layout.setHorizontalGroup(
            card_seats_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 928, Short.MAX_VALUE)
        );
        card_seats_3Layout.setVerticalGroup(
            card_seats_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        pn_card_seats.add(card_seats_3, "card3");

        combox_room.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        combox_room.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C" }));
        combox_room.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combox_roomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pn_container_contentLayout = new javax.swing.GroupLayout(pn_container_content);
        pn_container_content.setLayout(pn_container_contentLayout);
        pn_container_contentLayout.setHorizontalGroup(
            pn_container_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_container_contentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_container_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(combox_room, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_go_door, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pn_card_seats, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        pn_container_contentLayout.setVerticalGroup(
            pn_container_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_container_contentLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pn_container_contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_card_seats, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pn_container_contentLayout.createSequentialGroup()
                        .addComponent(lb_go_door)
                        .addGap(138, 138, 138)
                        .addComponent(combox_room, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pn_container.add(pn_container_content, java.awt.BorderLayout.CENTER);

        color_empty.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout color_emptyLayout = new javax.swing.GroupLayout(color_empty);
        color_empty.setLayout(color_emptyLayout);
        color_emptyLayout.setHorizontalGroup(
            color_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        color_emptyLayout.setVerticalGroup(
            color_emptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        color_empty1.setBackground(new java.awt.Color(255, 0, 51));

        javax.swing.GroupLayout color_empty1Layout = new javax.swing.GroupLayout(color_empty1);
        color_empty1.setLayout(color_empty1Layout);
        color_empty1Layout.setHorizontalGroup(
            color_empty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        color_empty1Layout.setVerticalGroup(
            color_empty1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        lblDaDatGhe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDaDatGhe.setForeground(new java.awt.Color(255, 255, 255));
        lblDaDatGhe.setText("Đã đặt ghế");

        lblTrong.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTrong.setForeground(new java.awt.Color(255, 255, 255));
        lblTrong.setText("Trống");

        lblDangChonGhe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDangChonGhe.setForeground(new java.awt.Color(255, 255, 255));
        lblDangChonGhe.setText("Đang chọn ghế");

        btn_HoanThanh.setBackground(new java.awt.Color(51, 51, 51));
        btn_HoanThanh.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btn_HoanThanh.setForeground(new java.awt.Color(255, 255, 255));
        btn_HoanThanh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/done.png"))); // NOI18N
        btn_HoanThanh.setText("HOÀN TẤT");
        btn_HoanThanh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_HoanThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_HoanThanhActionPerformed(evt);
            }
        });

        btn_Thoat.setBackground(new java.awt.Color(0, 0, 0));
        btn_Thoat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_Thoat.setForeground(new java.awt.Color(255, 255, 255));
        btn_Thoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/fpt/galaxymovie/icons/button/logout.png"))); // NOI18N
        btn_Thoat.setText("THOÁT");
        btn_Thoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_Thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThoatActionPerformed(evt);
            }
        });

        lblCanhBao.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblCanhBao.setForeground(new java.awt.Color(255, 0, 0));
        lblCanhBao.setText("* Việc chọn ghế chỉ được thực hiện khi nó đang ở 1 phòng từ combox");

        javax.swing.GroupLayout pn_body_containerLayout = new javax.swing.GroupLayout(pn_body_container);
        pn_body_container.setLayout(pn_body_containerLayout);
        pn_body_containerLayout.setHorizontalGroup(
            pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_body_containerLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_body_containerLayout.createSequentialGroup()
                        .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pn_body_containerLayout.createSequentialGroup()
                                .addComponent(color_empty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTrong)
                                .addGap(31, 31, 31)
                                .addComponent(color_empty1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDaDatGhe)
                                .addGap(26, 26, 26)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblDangChonGhe))
                            .addComponent(lblCanhBao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(262, 262, 262)
                        .addComponent(btn_HoanThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btn_Thoat))
                    .addComponent(pn_container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pn_body_containerLayout.setVerticalGroup(
            pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_body_containerLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(pn_container, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_body_containerLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_Thoat, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_HoanThanh, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
                    .addGroup(pn_body_containerLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(color_empty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTrong))
                            .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblDangChonGhe)
                                .addGroup(pn_body_containerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(color_empty1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDaDatGhe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCanhBao)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        getContentPane().add(pn_body_container, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_belowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_belowActionPerformed
        int id = this.findRoomID_below();
        this.showPanelChooseSeat(id);
    }//GEN-LAST:event_btn_belowActionPerformed

    private void btn_aboveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aboveActionPerformed
        int id = this.findRoomID_above();
        this.showPanelChooseSeat(id);
    }//GEN-LAST:event_btn_aboveActionPerformed

    private void btn_ThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThoatActionPerformed
        MovePage.moveJDialogToJDialog(new GLX_0_Shotcut(new javax.swing.JFrame(), true), this);
    }//GEN-LAST:event_btn_ThoatActionPerformed

    private void combox_roomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combox_roomActionPerformed
        int index = combox_room.getSelectedIndex() + 1;
        this.showPanelChooseSeat(index);
    }//GEN-LAST:event_combox_roomActionPerformed

    private void btn_HoanThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_HoanThanhActionPerformed
        this.updateChairBought();
    }//GEN-LAST:event_btn_HoanThanhActionPerformed

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
            java.util.logging.Logger.getLogger(GLX_4a2_Choose_seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GLX_4a2_Choose_seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GLX_4a2_Choose_seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GLX_4a2_Choose_seats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            GLX_4a2_Choose_seats dialog = new GLX_4a2_Choose_seats(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_HoanThanh;
    private javax.swing.JButton btn_Thoat;
    private javax.swing.JButton btn_above;
    private javax.swing.JButton btn_below;
    private javax.swing.JPanel card_seats_1;
    private javax.swing.JPanel card_seats_2;
    private javax.swing.JPanel card_seats_3;
    private javax.swing.JPanel color_empty;
    private javax.swing.JPanel color_empty1;
    private javax.swing.JComboBox<String> combox_room;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lb_go_door;
    private javax.swing.JLabel lb_title;
    private javax.swing.JLabel lblCanhBao;
    private javax.swing.JLabel lblDaDatGhe;
    private javax.swing.JLabel lblDangChonGhe;
    private javax.swing.JLabel lblTrong;
    private javax.swing.JPanel pn_body_container;
    private javax.swing.JPanel pn_card_seats;
    private javax.swing.JPanel pn_container;
    private javax.swing.JPanel pn_container_content;
    private javax.swing.JPanel pn_go;
    private javax.swing.JPanel pn_title;
    // End of variables declaration//GEN-END:variables
}
