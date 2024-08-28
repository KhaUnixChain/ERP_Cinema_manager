/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

import com.fpt.galaxymovie.database.helper.Save_helper;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class ResetTable {
    public static void removeRowTable(DefaultTableModel model, JTable jTable){
        int number_rows = model.getRowCount();
        if (number_rows > 0) {
            for (int i = number_rows - 1; i >= 0; i--) {
                model.removeRow(i);
            }
        }        
    }
    
    public static void removeTicket(JLabel sum, JLabel...obj){
        sum.setText("0");
        if (obj.length > 0) {
            for (JLabel obj1 : obj) {
                obj1.setText("...");
            }
        }
        Save_helper.save_other = null;
        Save_helper.chairs = null;
    }
}
