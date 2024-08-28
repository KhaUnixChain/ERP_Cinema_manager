/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.ChiTietChieuPhim;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class DAOChiTietChieuPhim {        
    public void insertDB(ChiTietChieuPhim ctcp){
        String sql = "INSERT INTO ChiTietChieuPhim VALUES (?,?,?,?,?)";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, ctcp.getVeID());
            ps.setString(2, ctcp.getPhongID());
            ps.setString(3, ctcp.getPhimID());
            ps.setInt(4, ctcp.getSuatChieu());
            ps.setDate(5, (Date) ctcp.getNgayChieu());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteDB(int ID){
        String sql = "DELETE FROM ChiTietChieuPhim WHERE ID = ?";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, ID);
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateDB(ChiTietChieuPhim ctcp){
        String sql = "UPDATE ChiTietChieuPhim SET VeID=?, PhongChieuID=?, PhimID=?, SuatChieuID=?, NgayChieu=? WHERE ID=?";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, ctcp.getVeID());
            ps.setString(2, ctcp.getPhongID());
            ps.setString(3, ctcp.getPhimID());
            ps.setInt(4, ctcp.getSuatChieu());
            ps.setDate(5, (Date) ctcp.getNgayChieu());
            ps.setInt(6, ctcp.getId());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateVeNull(ChiTietChieuPhim ctcp){
        String sql = "UPDATE ChiTietChieuPhim SET VeID=? WHERE ID=?";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setInt(1, ctcp.getVeID());
            ps.setInt(2, ctcp.getId());
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
