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
public class LoaiPhim {
    private String ID;
    private String loaiPhim;

    public LoaiPhim() {
    }

    public LoaiPhim(String ID, String loaiPhim) {
        this.ID = ID;
        this.loaiPhim = loaiPhim;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLoaiPhim() {
        return loaiPhim;
    }

    public void setLoaiPhim(String loaiPhim) {
        this.loaiPhim = loaiPhim;
    }
}
