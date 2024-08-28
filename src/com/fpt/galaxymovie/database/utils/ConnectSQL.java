package com.fpt.galaxymovie.database.utils;

/**
 *
 * @author admin
 */
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectSQL {
    public static String user = "sa";
    public static String password = "0907718993";
    public static String dbName = "QuanLyRapPhim;";
    public static String url = "jdbc:sqlserver://localhost:1433;databaseName=" + dbName;
    
    public static ResultSet getResultSet(String sql){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(sql);
            return rs;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ConnectSQL --> khong the ket noi getResultSet");
        }
        return null;
    }
    
    public static PreparedStatement getPreparedStatement(String sql){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = con.prepareStatement(sql);
            return ps;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("ConnectSQL --> khong the ket noi getPreparedStatement");
        }
        return null;
    }
    
    public static void main(String[] args) {
        try {
            PreparedStatement ps = ConnectSQL.getPreparedStatement("SELECT * FROM Phim");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
        }
    }
}
