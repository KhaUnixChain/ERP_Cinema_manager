/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.Ve;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class DAOVe {
    public void insertDB(Ve model){
        String sql = "INSERT INTO Ve VALUES (?,?,?,?)";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setString(1, model.getKhachHangID());
            ps.setString(2, model.getLoaiVe());
            ps.setString(3, model.getNhanVienID());
            ps.setDate(4, model.getNgayBanVe());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
