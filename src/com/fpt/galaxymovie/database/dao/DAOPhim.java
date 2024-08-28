/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import com.fpt.galaxymovie.database.utils.ConnectSQL;
import com.fpt.galaxymovie.models.Phim;
import java.sql.Date;
import java.sql.PreparedStatement;

/**
 *
 * @author ASUS
 */
public class DAOPhim {
    public static void insert(Phim phim){
        try {
            String sql = "insert into Phim (ID, LoaiPhimID, TenPhim, NSX, DaoDien, HinhAnh, NgayCongChieu, NgayHetHan, ThoiLuong) values	(?,?,?,?,?,?,?,?,?) ";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, phim.getID());
            ps.setString(2, phim.getLoaiPhimID());
            ps.setString(3, phim.getTenPhim());
            ps.setDate  (4, (Date) phim.getNSX());
            ps.setString(5, phim.getDaoDien());
            ps.setString(6, phim.getHinhAnh());
            ps.setDate  (7, (Date) phim.getNgayCongChieu());
            ps.setDate  (8, (Date) phim.getNgayHetHan());
            ps.setInt   (9,  phim.getThoiLuong());
            ps.execute();
            ps.close();
            System.out.println("Thêm Phim thành công !");
        } catch (Exception e) {
            System.out.println("Không thể thêm mới phim !");
        }
    }
    
    public static void update(Phim phim){
        try {
            String sql = "update Phim set LoaiPhimID = ?, TenPhim = ?, NSX = ?, DaoDien = ?, HinhAnh = ?, NgayCongChieu = ?, NgayHetHan =?, ThoiLuong =? where ID = ?";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, phim.getLoaiPhimID());
            ps.setString(2, phim.getTenPhim());
            ps.setDate  (3, (Date) phim.getNSX());
            ps.setString(4, phim.getDaoDien());
            ps.setString(5, phim.getHinhAnh());
            ps.setDate  (6, (Date) phim.getNgayCongChieu());
            ps.setDate  (7, (Date) phim.getNgayHetHan());
            ps.setInt   (8,  phim.getThoiLuong());
            ps.setString(9, phim.getID());
            ps.execute();
            ps.close();
            System.out.println("Cập nhật Phim thành công !");
        } catch (Exception e) {
            System.out.println("Lỗi sửa Phim");
        }
    }
    
    
    public static void delete(String ID){
        try {
            String sql = "delete from Phim where ID = ?";
            PreparedStatement ps = ConnectSQL.getPreparedStatement(sql);
            ps.setString(1, ID.trim());
            ps.execute();
            ps.close();
            System.out.println("Xóa Phim thành công !");
        } catch (Exception e) {
            System.out.println("Lỗi xóa Phim");
        }
    }
    
    
}
