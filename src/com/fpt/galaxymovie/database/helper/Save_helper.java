/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.helper;

import com.fpt.galaxymovie.models.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 *
 * @author admin
 */
public class Save_helper {
    public static TaiKhoan taikhoan = null;
    public static String randomCode = null;
    public static int act = 0; // action
    public static File path = null;
    public static JButton btnDelete = null;
    public static String Language = "vn";
    public static ArrayList<String> save_other = null; // to save suất chiều và khách hàng
    public static HashMap<String, ArrayList<Ghe>> chairs = null;
    public static JPanel ve = null;
    public static String tenKH = "...";
}
