/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.Ghe;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class DAOChiTietChoNgoi {
    DAO<Ghe> daoCTCN;
    ArrayList<Ghe> listCTCN;

    public DAOChiTietChoNgoi(DAO<Ghe> daoCTCN, ArrayList<Ghe> listCTCN) {
        this.daoCTCN = daoCTCN;
        this.listCTCN = listCTCN;
    }
    
    public DAOChiTietChoNgoi() {
        daoCTCN = new DAO<>();
        listCTCN = daoCTCN.getList();
    }

    public ArrayList<Ghe> getListCTCN() {
        return listCTCN;
    }

    public DAO<Ghe> getDaoCTCN() {
        return daoCTCN;
    }

    public void setDaoCTCN(DAO<Ghe> daoCTCN) {
        this.daoCTCN = daoCTCN;
    }

    public void setListCTCN(ArrayList<Ghe> listCTCN) {
        this.listCTCN = listCTCN;
    }
        
    public void insertDB(Ghe model){
        String sql = "INSERT INTO ChiTietChoNgoi VALUES (?,?,?)";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, model.getSeatId());
            ps.setString(2, model.getRoomId());
            ps.setInt(3, model.getStatus());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteDB(Ghe ghe){
        String sql = "DELETE FROM ChiTietChoNgoi WHERE MaChoNgoi=? and PhongChieuID=?";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, ghe.getSeatId());
            ps.setString(2, ghe.getRoomId());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateDB(Ghe ghe){
        String sql = "UPDATE ChiTietChoNgoi SET MaChoNgoi=?, PhongChieuID=?, Status=? WHERE MaChoNgoi=? and PhongChieuID=?";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, ghe.getSeatId());
            ps.setString(2, ghe.getRoomId());
            ps.setInt(3, ghe.getStatus());
            ps.setInt(4, ghe.getSeatId());
            ps.setString(5, ghe.getRoomId());
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "Đã hoàn thành cập nhật ghế !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
