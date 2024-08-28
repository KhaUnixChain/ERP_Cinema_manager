/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.TaiKhoan;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class DAOTaiKhoan {
    
    public static void insert (TaiKhoan taikhoan){
        try {
            String sql = "insert into TaiKhoan (NhanVienID, MatKhau, VaiTro) values (?,?,?)";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, taikhoan.getMaNV());
            ps.setString(2, taikhoan.getMatKhau());
            ps.setInt(3, taikhoan.getVaitro());
            ps.execute();
            ps.close();
            System.out.println("Thêm Tài Khoản Thành Công !");
        } catch (Exception e) {
            System.out.println("Lỗi thêm tài khoản");
        }
    }
    
    
    public static void fix(TaiKhoan taikhoan){
        try {
            String sql = "update TaiKhoan set MatKhau = ?, VaiTro = ? where NhanVienID = ?";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, taikhoan.getMatKhau());
            ps.setInt(2, taikhoan.getVaitro());
            ps.setString(3, taikhoan.getMaNV());
            ps.execute();
            ps.close();
            System.out.println("Sửa Tài Khoản Thành Công !");
        } catch (Exception e) {
            System.out.println("Lỗi sửa tài khoản");
        }
    }
    
    public static void delete(TaiKhoan taikhoan){
        try {
            String sql = "delete from TaiKhoan where NhanVienID = ?";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, taikhoan.getMaNV());
            System.out.println("Xóa tài khoản thành công !");
        } catch (Exception e) {
            System.out.println("Lỗi xóa tài khoản");
        }
    }
    
    
}
