/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.controller;

/**
 *
 * @author admin
 */
public class Money {
    public static String getMoney(String loaive){
        String money = "";
        switch (loaive){
            case "NGƯỜI LỚN" -> { money = "75000"; }
            case "TRẺ EM" -> { money = "50000"; }
            case "SINH VIÊN" -> { money = "50000"; }
            case "GHẾ  VIP" -> { money = "85000"; }
        }
        return money;
    }
    
    public static String getMoneyVoucher(String loaive){
        String money = "";
        switch (loaive){
            case "NGƯỜI LỚN" -> { money = "37500"; }
            case "TRẺ EM" -> { money = "25000"; }
            case "SINH VIÊN" -> { money = "25000"; }
            case "GHẾ  VIP" -> { money = "42500"; }
        }
        return money;
    }
}
