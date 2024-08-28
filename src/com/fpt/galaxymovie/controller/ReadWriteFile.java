/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author admin
 */
public class ReadWriteFile {
    static String path_df = "C:\\Users\\admin\\.movie\\number.txt";
    public static void writeTXT(String content, String...path){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(!path[0].isEmpty() ? path[0] : path_df));
            bw.write(content);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e) {
            System.out.println("Khong the viet file txt");
        }
    }
    
    public static String readTXT(String...path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(!path[0].isEmpty() ? path[0] : path_df));
            String content = br.readLine().trim();
            return content;
        } catch (IOException e) {
            System.out.println("khong the doc file txt");
            return null;
        }
    }
    
    public static void deleteTXT(String path){
        File file = new File(path);
        file.delete();
    }        
}
