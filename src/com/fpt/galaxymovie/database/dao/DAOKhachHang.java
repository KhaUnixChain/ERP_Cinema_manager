package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.KhachHang;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Administrator
 */
public class DAOKhachHang {    
    public void insertDB(KhachHang model){
        String sql = "INSERT INTO KhachHang VALUES (?,?,?)";
        PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
        try {
            ps.setString(1, model.getMaKH());
            ps.setString(2, model.getHoTen_KH());
            ps.setString(3, model.getSDT());
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
        }
    }
}
