/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ASUS
 */
public class XDate {
    static SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
    
    // chuyen String thanh Date  ---> 22-06-1999   ---> Thu 22nd 00:00:00 PR
    public static Date toDate(String date, String...pattern){
        try {
            formater.applyPattern(pattern[0]);
            return formater.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
//    Chuyen Date thanh String    Thu 22nd 00:00:00 PR  --> 22-06-1999
    public static String toString (Date date, String...pattern){
        formater.applyPattern(pattern[0]);
        return formater.format(date);
    }
    
    public static Date now(){
        return new Date();
    }
    
    public static Date addDays(Date date, long days){
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        return date;
    }
}
