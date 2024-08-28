/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

import com.fpt.galaxymovie.database.dao.DAOChiTietChieuPhim;
import com.fpt.galaxymovie.database.dao.DAOChiTietChoNgoi;
import com.fpt.galaxymovie.database.dao.DAOKhachHang;
import com.fpt.galaxymovie.database.dao.DAOVe;
import com.fpt.galaxymovie.models.ChiTietChieuPhim;
import com.fpt.galaxymovie.models.Ghe;
import com.fpt.galaxymovie.models.KhachHang;
import com.fpt.galaxymovie.models.Ve;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author admin
 */
public class AutoFillData {
    String[] ho = {"Nguyễn", "Huỳnh", "Phạm", "Phan", "Trần", "Lê", "Mai", "Trương", "Trịnh", "Phùng", "Lại", "Hà", "Lương", "Lý", "Hà", "Thôi", "Triệu", "Tào", "Đoàn", "Nhâm", "Khương", "Viên", "Tăng", "Đồng", "Diệp", "Tô", "Thái", "Từ", "Đàm", "Quách", "Đào", "Đinh", "Tạ", "Ông", "Cao"};
    String[] lot = {"Thị", "", "Văn", "Minh", "Tấn", "Lộc", "Kim", "Đình", "Ngọc", "Bá", "Bích", "Thu", "Thanh", "Quang", "Tú", "Lan", "Khởi", "Trùng", "Vân", "Tiên", "Bích", "Thanh", "Thiên", "Trọng", "Mỹ"};
    String[] ten =      {"Nhi", "Khang", "Tâm", "Sương", "Thảo", "Linh", "Tài", "Khải", "Hương", "Lan", "Nam", "Bình", "Lam", "Cường", "Phú", "Tân", "Lĩnh", "Trinh", "Ngân", "Thành", "Thanh", "Trọng", "Tuấn", "Sơn", "Đức", "An", "Mỹ", "Vân", "My"};
    int[] nam_nhanvien = {1988, 1989, 1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003};
    int[] nam_banve = {2015, 2016, 2017, 2018, 2019, 2020, 2021};
    int[] thang = {1,2,3,4,5,6,7,8,9,10,11,12};
    int[] ngay = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    int[] gioitinh = {0,1};
    int[] vaitro = {0,1};
    int[] digit = {0,1,2,3,4,5,6,7,8,9};
    String[] headerphone = {"031", "032", "033", "035", "037", "039", "071", "072", "073", "075", "077", "090", "096", "095", "091", "099", "062", "063", "064", "067"};
    String[] detailemail = {"@yahoo.com", "@gmail.com", "@fpt.edu.vn", "@io.com", "@outlook.com", "@zoho.com", "@edu.com.vn"};
    String[] nhanVien = {"NV01", "NV02", "NV03", "NV04", "NV05", "NV06", "NV07", "NV08", "NV09", "NV10", "NV11", "NV12","NV13","NV14","NV15","NV16","NV17"};
    String[] loaive = {"LV01", "LV02", "LV03", "LV04"};
    String[] room = {"A", "B", "C"};
    String[] loaiphim = {"LP01","LP02","LP03","LP04","LP05","LP06","LP07","LP08","LP09"};
    ArrayList<String> khachhang = new ArrayList<>();
    
    
    // -----------------------------------------------------------------------------
    int number = 500;
    
    public String getRandom_HoTen(){
        // ngau nhien ho ten
        int randomHo = (int) Math.floor(Math.random()*ho.length + 0);
        int randomLot = (int) Math.floor(Math.random()*lot.length + 0);
        int randomTen = (int) Math.floor(Math.random()*ten.length + 0);
        String hoten = ho[randomHo] + " " + lot[randomLot] + " " + ten[randomTen];
        return hoten;
    }
    
    public String getRandom_SDT(){
        // phone đầu số
        int randomheaderphone = (int) Math.floor(Math.random() * headerphone.length + 0);
        String phone = headerphone[randomheaderphone];
        for (int j = 0; j < 7; j++) {
            phone += "" + digit[  (int) Math.floor(Math.random()*digit.length + 0)  ];
        }
        return phone;
    }
    
    public String getRandom_3number(){
        String rdNumber = "";
        for (int j = 0; j < 3; j++) {
            rdNumber += (int) Math.floor(Math.random()*digit.length + 0);
        }
        return rdNumber;
    }
    
    public String getRandom_date(){
        int random_ngay = (int) Math.floor(Math.random() * ngay.length + 0);
        int _ngay = this.ngay[random_ngay];
        int random_thang = (int) Math.floor(Math.random() * thang.length + 0);
        int _thang = this.thang[random_thang];
        int random_nambanve = (int) Math.floor(Math.random() * nam_banve.length + 0);
        int _nam = this.nam_banve[random_nambanve];
        if (random_thang == 2 && random_ngay > 28) {
            return java.sql.Date.valueOf(LocalDate.now()).toString();
        }
        return _nam + "-" + _thang + "-" + _ngay;
    }

    public void insertKH(){
        for (int i = 1; i <= number; i++) {
            try {
                /* ------------------- Khach Hang -----------------------*/
                if (i < 10) {
                    KhachHang kh = new KhachHang("KH00"+i,  getRandom_HoTen(), getRandom_SDT());
                    new DAOKhachHang().insertDB(kh);
                    khachhang.add(i-1, ("KH00" + i));
                }
                else if (i < 100 && i > 9) {
                    KhachHang kh = new KhachHang("KH0"+i,  getRandom_HoTen(), getRandom_SDT());
                    new DAOKhachHang().insertDB(kh);
                    khachhang.add(i-1, ("KH0" + i));
                }
                else {
                    KhachHang kh = new KhachHang("KH"+i,  getRandom_HoTen(), getRandom_SDT());
                    new DAOKhachHang().insertDB(kh);
                    khachhang.add(i-1, ("KH" + i));
                }
                System.out.println("> Khach hang %d Thanh cong !!".formatted(i));
            } catch (Exception e){
                e.printStackTrace();
            }
        }     
    }
    
    public void insert_CTCN(){
        /* --------------------- CTCN ----------------------------*/
        int[] status = {1,2};
        for (String room1 : room) {
            for (int j = 1; j < 101; j++) {
                int s  = (int) Math.floor(Math.random()*status.length + 1);
                System.out.println(s);
                Ghe ghe = new Ghe(j, room1, s);
                new DAOChiTietChoNgoi().insertDB(ghe);
                System.out.println(">> Room %s : Chair ( %d ) : Status is %d".formatted(room1, j, s));
            }
        }
    }
    
    public void insertVe(){
        /* --------------------- VE -----------------------------*/
        for (int i = 1; i <= number; i++) {
            int random_KH = (int) Math.floor(Math.random()*khachhang.size() + 0);
            String khachHangID = khachhang.get(random_KH);
            int random_LV = (int) Math.floor(Math.random()*loaive.length + 0);
            String loaiVeID = loaive[random_LV];
            int random_NV = (int) Math.floor(Math.random()*nhanVien.length + 0);
            String nhanVienID = nhanVien[random_NV];
            
            java.sql.Date ngayBan = java.sql.Date.valueOf(getRandom_date());
            Ve ve = new Ve(i, khachHangID, loaiVeID, nhanVienID, ngayBan);
            new DAOVe().insertDB(ve);
            System.out.println(">>> Ve %d Thanh cong !!".formatted(i));
        }
    }
    
    public void insertCTCP(){
        try {
            java.sql.Date now = java.sql.Date.valueOf(LocalDate.now());
            for (int i = 1; i < 600; i++) {
                int ve = (int) Math.floor(Math.random() * 500 + 1);
                int random_room = (int) Math.floor(Math.random() * room.length + 0);
                String _room = this.room[random_room];
                int random_suatchieu = (int) (Math.random() * (digit.length - 5) + 1);
                int suatchieu = this.digit[random_suatchieu];
                
                int random_phim = (int) Math.floor(Math.random() * (ngay.length - 3) + 0);
                int ma_phim = this.ngay[random_phim];
                String phim = (ma_phim < 10) ? "P0"+ma_phim : "P" + ma_phim;

                int random_ngay = (int) Math.floor(Math.random() * ngay.length + 0);
                int _ngay = this.ngay[random_ngay];
                int random_thang = (int) Math.floor(Math.random() * thang.length + 0);
                int _thang = this.thang[random_thang];
                int random_nambanve = (int) (Math.random() * (nam_banve.length - 4) + 4);
                int _nam = this.nam_banve[random_nambanve];
                Date date = java.sql.Date.valueOf("%d-%d-%d".formatted(_nam, _thang, _ngay));
                
                if (i <= 195) {
                    ChiTietChieuPhim ctcp = new ChiTietChieuPhim(i, ve, _room, phim, suatchieu, date);
                    new DAOChiTietChieuPhim().updateVeNull(ctcp);
                    System.out.println("%d null: %d - %s - %s - %d - %s".formatted(i, ve, _room, phim, suatchieu, date.toString()));
                }
                else {
                    if (date.compareTo(now) < 0) {
                        ChiTietChieuPhim ctcp = new ChiTietChieuPhim(i, ve, _room, phim, suatchieu, date);
                        new DAOChiTietChieuPhim().insertDB(ctcp);
                        System.out.println("%d : %d - %s - %s - %d - %s".formatted(i, ve, _room, phim, suatchieu, date.toString()));
                    }
                    else{
                        ChiTietChieuPhim ctcp = new ChiTietChieuPhim(i, ve, _room, phim, suatchieu, now);
                        new DAOChiTietChieuPhim().insertDB(ctcp);
                        System.out.println("%d : %d - %s - %s - %d - %s".formatted(i, ve, _room, phim, suatchieu, now));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        AutoFillData auto = new AutoFillData();
        auto.insertKH();
        System.out.println("---------------------------------");
        auto.insertVe();
        System.out.println("---------------------------------");
        auto.insert_CTCN();
        auto.insertCTCP();
    }
}
