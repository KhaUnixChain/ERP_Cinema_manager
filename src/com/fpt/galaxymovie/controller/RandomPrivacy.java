/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

import java.util.Random;

/**
 *
 * @author admin
 */
public class RandomPrivacy {
    public static String fillCode(){
        String codeString = "";
        String[] word = {"a","B","c","D","e","F","g","H","i","J","k","L","m","N","o","P","q","R","s","T","u","V","w","X","y","Z", 
                        "0", "1", "2", "3", "4", "5", "6",
                        "A","b","C","d","E","f","G","h","I","j","K","l","M","n","O","p","Q","r","S","t","U","v","W","x","Y","z",
                        "7", "8", "9"};
        Random r = new Random();
        for (int i = 1; i <= 16; i++) {
            codeString += word[(int)r.nextInt(word.length)];
            if (i % 4 == 0 && i != 16) {
                codeString += "-";
            }
        }
        return codeString;
    }
    
    public static String codeVoucher(){
        String codeString = "";
        String[] word = {"B","D","F","H","J","L","N","P","R","T","V","X", 
                        "0", "1", "2", "3", "4", "5", "6",
                        "A","C","E","G","I","K","M","O","Q","S","U","W","Y",
                        "7", "8", "9"};
        Random r = new Random();
        for (int i = 1; i <= 9; i++) {
            codeString += word[(int)r.nextInt(word.length)];
            if (i % 3 == 0 && i != 9) {
                codeString += " - ";
            }
        }
        return codeString;
    }
}
