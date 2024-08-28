/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.models;

import java.util.Date;

/**
 *
 * @author admin
 */
public class Phim {
    private String ID;
    private String LoaiPhimID;
    private String TenPhim;
    private Date NSX;
    private String DaoDien;
    private String HinhAnh;
    private Date NgayCongChieu;
    private Date NgayHetHan;
    private int ThoiLuong;

    public Phim() {
    }

    public Phim(String ID, String LoaiPhimID, String TenPhim, Date NSX, String DaoDien, String HinhAnh, Date NgayCongChieu, Date NgayHetHan, int ThoiLuong) {
        this.ID = ID;
        this.LoaiPhimID = LoaiPhimID;
        this.TenPhim = TenPhim;
        this.NSX = NSX;
        this.DaoDien = DaoDien;
        this.HinhAnh = HinhAnh;
        this.NgayCongChieu = NgayCongChieu;
        this.NgayHetHan = NgayHetHan;
        this.ThoiLuong = ThoiLuong;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLoaiPhimID() {
        return LoaiPhimID;
    }

    public void setLoaiPhimID(String LoaiPhimID) {
        this.LoaiPhimID = LoaiPhimID;
    }

    public String getTenPhim() {
        return TenPhim;
    }

    public void setTenPhim(String TenPhim) {
        this.TenPhim = TenPhim;
    }

    public Date getNSX() {
        return NSX;
    }

    public void setNSX(Date NSX) {
        this.NSX = NSX;
    }

    public String getDaoDien() {
        return DaoDien;
    }

    public void setDaoDien(String DaoDien) {
        this.DaoDien = DaoDien;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String HinhAnh) {
        this.HinhAnh = HinhAnh;
    }

    public Date getNgayCongChieu() {
        return NgayCongChieu;
    }

    public void setNgayCongChieu(Date NgayCongChieu) {
        this.NgayCongChieu = NgayCongChieu;
    }

    public Date getNgayHetHan() {
        return NgayHetHan;
    }

    public void setNgayHetHan(Date NgayHetHan) {
        this.NgayHetHan = NgayHetHan;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    
}
