/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.models;

/**
 *
 * @author admin
 */
public class KhachHang {
    private String MaKH;
    private String HoTen_KH;
    private String SDT;

    public KhachHang() {
    }

    public KhachHang(String MaKH, String HoTen_KH, String SDT) {
        this.MaKH = MaKH;
        this.HoTen_KH = HoTen_KH;
        this.SDT = SDT;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getHoTen_KH() {
        return HoTen_KH;
    }

    public void setHoTen_KH(String HoTen_KH) {
        this.HoTen_KH = HoTen_KH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
