/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import java.sql.PreparedStatement;
import com.fpt.galaxymovie.models.NhanVien;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class DAONhanVien {
    public static void updateDB(NhanVien nhanvien){
        if (nhanvien != null) {
            String sql = "UPDATE NhanVien SET HoTen=?, CMND=?, DiaChi=?, Email=?, SDT=?, NgaySinh=?, GioiTinh=? WHERE ID=?";
            try {
                PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
                ps.setString(1, nhanvien.getHoTen().trim());
                ps.setString(2, nhanvien.getCMND().trim());
                ps.setString(3, nhanvien.getDiaChi().trim());
                ps.setString(4, nhanvien.getEmail().trim());
                ps.setString(5, nhanvien.getSdt().trim());
                ps.setDate(6, nhanvien.getNgaySinh());
                ps.setInt(7, nhanvien.getGioiTinh());
                ps.setString(8, nhanvien.getID().trim());
                ps.execute();
                ps.close();
            } catch (SQLException e) {
                System.out.println("Loi cap nhat _ daonhanvien");
            }
        }
    }
}
