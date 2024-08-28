/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpt.galaxymovie.database.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DAO <T>{
    private ArrayList<T> list;

    public DAO() {
        list = new ArrayList<>();
    }
    
    public DAO(ArrayList<T> list){
        this.list = list;
    }

    public ArrayList<T> getList() {
        return list;
    }

    public void setList(ArrayList<T> list) {
        this.list = list;
    }
    
    public void insert(T obj){
        list.add(obj);
    }
    
    public void deleteObj(T obj){
        list.remove(obj);
    }
    
    public void deleteInt(int vitri){
        list.remove(vitri);
    }
    
    public void update(int vitri, T obj){
        list.set(vitri, obj);
    }
    
    public void clear_list(){
        list.clear();
    }
        
    public void write(String path){
        try {
            ObjectOutputStream oos;
            try (FileOutputStream fos = new FileOutputStream(path)) {
                oos = new ObjectOutputStream(fos);
                oos.writeObject(list);
            }
            oos.close();
        } catch (IOException e) {
        }
    }
    
    public ArrayList<T> read(String path){
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<T>) ois.readObject();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
