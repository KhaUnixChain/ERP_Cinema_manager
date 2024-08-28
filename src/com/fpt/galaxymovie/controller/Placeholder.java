/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author admin
 */
public class Placeholder {
    public static void addPlaceholder(JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(new Color(200, 200, 200));
    }
    
    public static void removePlaceholder (JTextField textField){
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN);
        textField.setFont(font);
        textField.setForeground(Color.BLACK);
    }
    
    // create placeholder for JTextField : event focusGained
    public static void focusGaindText(JTextField textField, String content){
        if (textField.getText().equals(content)) {
            try {
                Thread.sleep(100);
                textField.setText(null);
                Placeholder.removePlaceholder(textField);
            } catch (InterruptedException ex) {}
        }
    }
    
    // create placeholder for JTextField : event focusLost
    public static void focusLostText(JTextField textField, String content){
        if (textField.getText().length() == 0) {
            textField.setText(content);
            Placeholder.addPlaceholder(textField);
        }
    }
    
    // create placeholder for JPasswordField : event focusGained
    public static void focusGaindPassword(JPasswordField passwordField, String content){
        if (passwordField.getText().equals(content)) {
            try {
                Thread.sleep(100);
                passwordField.setText(null);
                passwordField.setEchoChar('\u25cf');
                Placeholder.removePlaceholder(passwordField);
            } catch (InterruptedException ex) {}
        }
    }
    
    // create placeholder for JPasswordField : event focusLost
    public static void focusLostPassword(JPasswordField passwordField, String content){
        if (passwordField.getText().length() == 0) {
            passwordField.setText(content);
            passwordField.setEchoChar('\u0000');
            Placeholder.addPlaceholder(passwordField);
        }
    }
}
