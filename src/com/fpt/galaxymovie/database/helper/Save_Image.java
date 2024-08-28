/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.helper;

import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author ASUS
 */
public class Save_Image {
    public static boolean save(File src){
        File dst = new File("src\\com\\fpt\\galaxymovie\\controller\\Film",src.getName());
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs(); //tạo thư mục
            
        }
        try {
            Path from = Paths.get(src.getAbsolutePath());
            Path to = Paths.get(dst.getAbsolutePath());
            Files.copy(from,to,StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static ImageIcon resizeImage(String path, JLabel label){
        try {
            if (path.startsWith("/")) {
                path = path.substring(1);
            }
            System.out.println(path);
            ImageIcon icon = new ImageIcon(path);
            Image img = icon.getImage();
            Image imgScale = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon iconScale = new ImageIcon(imgScale);
            label.setIcon(iconScale);
            return iconScale;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
