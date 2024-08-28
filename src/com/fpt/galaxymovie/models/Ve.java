/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.models;

import java.sql.Date;

/**
 *
 * @author admin
 */
public class Ve {
    private int veID;
    private String khachHangID;
    private String loaiVe;
    private String nhanVienID;
    private Date ngayBanVe;

    public Ve() {
    }

    public Ve(int veID, String khachHangID, String loaiVe, String nhanVienID, Date ngayBanVe) {
        this.veID = veID;
        this.khachHangID = khachHangID;
        this.loaiVe = loaiVe;
        this.nhanVienID = nhanVienID;
        this.ngayBanVe = ngayBanVe;
    }

    public int getVeID() {
        return veID;
    }

    public void setVeID(int veID) {
        this.veID = veID;
    }

    public String getKhachHangID() {
        return khachHangID;
    }

    public void setKhachHangID(String khachHangID) {
        this.khachHangID = khachHangID;
    }

    public String getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(String loaiVe) {
        this.loaiVe = loaiVe;
    }

    public String getNhanVienID() {
        return nhanVienID;
    }

    public void setNhanVienID(String nhanVienID) {
        this.nhanVienID = nhanVienID;
    }

    public Date getNgayBanVe() {
        return ngayBanVe;
    }

    public void setNgayBanVe(Date ngayBanVe) {
        this.ngayBanVe = ngayBanVe;
    }
}
