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
public class ChiTietChieuPhim {
    private int id;
    private int veID;
    private String phongID;
    private String phimID;
    private int suatChieu;
    private Date ngayChieu;

    public ChiTietChieuPhim() {
    }

    public ChiTietChieuPhim(int veID, String phongID, String phimID, int suatChieu, Date ngayChieu) {
        this.veID = veID;
        this.phongID = phongID;
        this.phimID = phimID;
        this.suatChieu = suatChieu;
        this.ngayChieu = ngayChieu;
    }

    public ChiTietChieuPhim(int id, int veID, String phongID, String phimID, int suatChieu, Date ngayChieu) {
        this.id = id;
        this.veID = veID;
        this.phongID = phongID;
        this.phimID = phimID;
        this.suatChieu = suatChieu;
        this.ngayChieu = ngayChieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVeID() {
        return veID;
    }

    public void setVeID(int veID) {
        this.veID = veID;
    }

    public String getPhongID() {
        return phongID;
    }

    public void setPhongID(String phongID) {
        this.phongID = phongID;
    }

    public String getPhimID() {
        return phimID;
    }

    public void setPhimID(String phimID) {
        this.phimID = phimID;
    }

    public int getSuatChieu() {
        return suatChieu;
    }

    public void setSuatChieu(int suatChieu) {
        this.suatChieu = suatChieu;
    }

    public Date getNgayChieu() {
        return ngayChieu;
    }

    public void setNgayChieu(Date ngayChieu) {
        this.ngayChieu = ngayChieu;
    }
}
