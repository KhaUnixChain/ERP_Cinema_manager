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
public class TaiKhoan {
    private String MaNV;
    private String MatKhau;
    private int vaitro;

    public TaiKhoan(String MaNV, String MatKhau, int vaitro) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.vaitro = vaitro;
    }

    public TaiKhoan() {
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public int getVaitro() {
        return vaitro;
    }

    public void setVaitro(int vaitro) {
        this.vaitro = vaitro;
    }
}
