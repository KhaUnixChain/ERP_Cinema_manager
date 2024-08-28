package com.fpt.galaxymovie.controller;

import javax.swing.*;


public class MovePage {
    public static void moveFrameToFrame(JFrame show, JFrame hide){
        new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(100);
                    show.setVisible(true);
                    stop();
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Move go ahead so failed.", "Error move", 3);
                }
            }
        }.start();
        hide.setVisible(false);
    }
    
    public static void moveJDialogToJDialog(JDialog show, JDialog hide){
        new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(100);
                    show.setVisible(true);
                    stop();
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Move go ahead so failed.", "Error move", 3);
                }
            }
        }.start();
        hide.setVisible(false);
    }
    
    public static void moveJDialogToFrame(JFrame show, JDialog hide){
        new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(100);
                    show.setVisible(true);
                    stop();
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Move go ahead so failed.", "Error move", 3);
                }
            }
        }.start();
        hide.setVisible(false);
    }
    
    public static void moveFrameToJDialog(JDialog show, JFrame hide){
        new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(100);
                    show.setVisible(true);
                    stop();
                } catch (InterruptedException e) {
                    JOptionPane.showMessageDialog(null, "Move go ahead so failed.", "Error move", 3);
                }
            }
        }.start();
        hide.setVisible(false);
    }
    
    public static void showDialogNotHide(JDialog jDialog){
        try {
            Thread.sleep(500);
            jDialog.setVisible(true);
        } catch (Exception e) {
        }
    }
    
    public static void showJFrameNotHide(JFrame jFrame){
        try {
            Thread.sleep(500);
            jFrame.setVisible(true);
        } catch (Exception e) {
        }
    }
}
