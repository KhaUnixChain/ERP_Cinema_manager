package com.fpt.galaxymovie.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class DateHelper {    
    static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat("dd-MM-yyyy");
    
    // Chuyển đổi từ String sang Date
    public static Date toDate(String date, String...pattern) {
        try {
            if(pattern.length > 0){
                DATE_FORMATER.applyPattern(pattern[0]);
            }
            if(date == null){
                return new Date();
            }
            return DATE_FORMATER.parse(date);
        }
        catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    // Chuyển đổi từ Date sang String
    public static String toString(Date date, String...pattern) {
        if(pattern.length > 0){
            DATE_FORMATER.applyPattern(pattern[0]);
        }
        if(date == null){
            date = new Date();
        }
        return DATE_FORMATER.format(date);
    }
    
    // lấy định dạng từ kiểu String yyyy-MM-dd ra để chuyển kiểu String đảo ngược  dd-MM-yyyy
    public static String PrintDate(String birth){
        java.util.Date d = java.sql.Date.valueOf(birth);
        String ngaysinh = toString(d, "dd-MM-yyyy");
        return ngaysinh.trim();
    }
    
    
    public static Date Convert_String_To_Date_SQL(String birth){
        java.util.Date date = DateHelper.toDate(birth, "dd-MM-yyyy");
        String dateFormated = DateHelper.toString(date, "yyyy-MM-dd").trim();
        java.util.Date ngaysinh = java.sql.Date.valueOf(dateFormated);
        return ngaysinh;
    }

    public static String changeFullDate(String date){
        String months = date.substring(0, date.indexOf(" ")).trim();
        String day = date.substring(date.indexOf(" "), date.indexOf(",")).trim();
        String year = date.substring(date.lastIndexOf(" "), date.length()).trim();
        switch (months){
            case "Jan" -> { months = "1"; }
            case "Feb" -> { months = "2"; }
            case "Mar" -> { months = "3"; }
            case "Apr" -> { months = "4"; }
            case "May" -> { months = "5"; }
            case "Jun" -> { months = "6"; }
            case "Jul" -> { months = "7"; }
            case "Aug" -> { months = "8"; }
            case "Sep" -> { months = "9"; }
            case "Oct" -> { months = "10"; }
            case "Nov" -> { months = "11"; }
            case "Dec" -> { months = "12"; }
        }
        return day+"-"+months+"-"+year;
    }
    
    public static String changeDays(String day){
        String d = "Th";
        switch (day){
            case "SUNDAY" -> { d = "CN"; }
            case "MONDAY" -> { d += "2"; }
            case "TUESDAY" -> { d += "3"; }
            case "WEDNESDAY" -> { d += "4"; }
            case "THURSDAY" -> { d += "5"; }
            case "FRIDAY" -> { d += "6"; }
            case "SATURDAY" -> { d += "7"; }
        }
        return d;
    }
    
    public static boolean compareNow(String start, String end){
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        try {
            Date ts = parser.parse(start);
            Date te = parser.parse(end);
            Date userDate = parser.parse(java.time.LocalTime.now().toString());
            return (userDate.after(ts) && userDate.before(te)) || ( userDate.before(ts) && userDate.before(te) );
        } catch (ParseException e) {
            return false;
        }
    }
    
    public static Date convertSchedule (String date) {
        String replaceAll = "";
        if (date.contains("/")) {
            replaceAll = date.replaceAll("/", "-") + "-" + LocalDate.now().getYear();
        }
        else {
            replaceAll = date + "-" + LocalDate.now().getYear();
        }
        Date d = Convert_String_To_Date_SQL(replaceAll);
        return d;
    }
}
