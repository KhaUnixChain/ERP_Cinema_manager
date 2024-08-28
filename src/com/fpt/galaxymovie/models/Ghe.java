/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.models;

/**
 *
 * @author admin
 */
public class Ghe {
    private int seatId;
    private String roomId;
    private int status;

    public Ghe() {
    }

    public Ghe(int seatId, String roomId, int status) {
        this.seatId = seatId;
        this.roomId = roomId;
        this.status = status;
    }


    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
}
