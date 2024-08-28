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
public class LoaiVe {
    private String ID;
    private String TenLV;
    private int DonGia;

    public LoaiVe() {
    }

    public LoaiVe(String ID, String TenLV, int DonGia) {
        this.ID = ID;
        this.TenLV = TenLV;
        this.DonGia = DonGia;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTenLV() {
        return TenLV;
    }

    public void setTenLV(String TenLV) {
        this.TenLV = TenLV;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int DonGia) {
        this.DonGia = DonGia;
    }
}
