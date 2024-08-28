/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;
import com.fpt.galaxymovie.database.helper.Save_helper;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class SendMail {
    
    public static boolean send(String email){
        // ----  lấy thông tin thứ tự ----
        String accountName = "khannpd05178@fpt.edu.vn";
        String accountPass = "0907718993";
        String from = "khannpd05178@fpt.edu.vn";
        String to = email;
        String subject = "Confirm account with a code random OTP" ;
        String body = RandomPrivacy.fillCode();
        Save_helper.randomCode = body;
        
        // ----  đưa thông tin Properties ----
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.port", "587");

        // ---- tạo session để đưa thông tin user password ----
        Session s;
        s = Session.getInstance(p,
                new javax.mail.Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication(){
                        return new PasswordAuthentication(accountName, accountPass);
                    }
                }
        );
        
        // ---- Tạo Message để đẩy Session vào     ---------------
        Message msg = new MimeMessage(s);
        
        // ---- xác đinh trường hợp gửi tin nhắn hay kèm file ----
        try {
            msg.setContent(body, "text/html; charset=utf-8");
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
            JOptionPane.showMessageDialog(null, "Gửi mã code thành công", "Send code", 1, null);
            return true;
        }catch (MessagingException ex) {
            JOptionPane.showMessageDialog(null, "Gửi mã code thất bại", "Send code", 1, null);
        }
        return false;
    }
    
    
    public static void main(String[] args) {
        boolean k = SendMail.send("nhomchung1999@gmail.com");
        System.out.println(k);
    }
}
