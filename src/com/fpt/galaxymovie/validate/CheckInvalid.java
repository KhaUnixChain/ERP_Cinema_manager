package com.fpt.galaxymovie.validate;

import java.util.regex.Pattern;

public class CheckInvalid {    
    public static boolean checkLogin(String user, String password){
        return (user.length() == 0 || password.length() == 0 || user.equals("Enter your ID ...") || password.equals("Enter your password ..."));
    }
    
    public static boolean checkForget(String email){
        return (email.length() == 0);
    }
    
    public static boolean checkNhanVien(String ten, String cmnd, String diachi, String email, String sdt, String ngaysinh){
        return ten.length()==0 || cmnd.length()==0 ||diachi.length()==0 ||email.length()==0 ||sdt.length()==0 ||ngaysinh.length()==0;
    }
    
    public static boolean checkChangePass(String password2, String password3){
        return ( 
                password2.length() == 0 || 
                password3.length() == 0 ||
                password2.equals("* Enter your new password ...") || 
                password3.equals("* Enter your confirm password ...")
        );
    }
    
    public static boolean checkGender(String gender){
        return (gender.equals("Nam") == false && gender.equals("Nữ") == false);
    }
    
    public static boolean checkAge(String age){
        String rexAge = "^[0-9]{1,3}$";
        Pattern check = Pattern.compile(rexAge);
        try{
            if ( Byte.parseByte(age) < 15 ||  Byte.parseByte(age) > 65) {
                return false;
            }
            else{
                return check.matcher(age.trim()).matches();
            }
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    
    // kiểm tra cái đưa vào có phải là số hay ko
    public static boolean checkNumber(String number){
        String rexNumberInt = "^[0-9]{1,10}$";
        String rexNumberDouble = "[0-9]{1,}[,|.][0-9]{1,}";
        try {
            if (number.matches(rexNumberInt)) {
                return number.trim().matches(rexNumberInt);
            }
            else if (number.matches(rexNumberDouble)) {
                return number.trim().matches(rexNumberDouble);
            }
            else{
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean checkEmail(String email){
        String rexEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return (email == null) ? false : email.trim().matches(rexEmail);
    }
    
    public static boolean checkDate(String date){
        String rexDate = "^\\d{1,2}-\\d{1,2}-\\d{4}$";
        return (date == null) ? false : date.trim().matches(rexDate);
    }
    
    public static boolean checkPhone(String phone){
        String rexPhone = "^[0|+84]+(?:[0-9]{9})$";
        return (phone == null) ? false : phone.trim().matches(rexPhone);
    }
    
    public static boolean checkCMND(String cmnd){
        String rexCMND = "^[0-9]{9}$";
        return cmnd == null ? false : cmnd.matches(rexCMND);
    }
    
    public static boolean checkTime(String time){
        String rexTime = "(?m)^(\\d{1,2}:\\d{1,2}:\\d{1,2})";
        return (time == null) ? false : time.matches(rexTime);
    }
}
