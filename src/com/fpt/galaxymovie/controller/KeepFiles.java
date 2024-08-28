package com.fpt.galaxymovie.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KeepFiles {
    public static void writeTXT(String path, String content){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            bw.write(content);
            bw.newLine();
            bw.flush();
            bw.close();
        }
        catch (IOException e) {
            System.out.println("Khong the viet file txt");
        }
    }
    
    public static String readTXT(String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String content = br.readLine().trim();
            return content;
        } catch (IOException e) {
            System.out.println("khong the doc file txt");
            return null;
        }
    }
    
    public static void deleteTXT(String[] Paths){
        for (String path : Paths) {
            File file = new File(path);
            file.delete();
        }        
    }
}
