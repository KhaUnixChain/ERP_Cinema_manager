/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class NewClass {
    public static void main(String[] args) {
        while (true) {            
            new Thread() {
            @Override
            public void run(){
                int second = 0;
                for (int i = 0; i < 6; i++) {
                    
                    if (second == 5) {
                        second = 0;
                        System.out.println("--------");
                    }
                    try {
                        Thread.sleep(1000);
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                System.out.println(" %d".formatted(second) );
            }
        }.start();
        }
        
    }
}
