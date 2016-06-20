/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import org.foi.nwtis.dkopic2.google.GMClient;
import org.foi.nwtis.dkopic2.listener.ApplicationListener;
import org.foi.nwtis.dkopic2.location.Address;
import org.foi.nwtis.dkopic2.location.AddressDB;
import org.foi.nwtis.dkopic2.location.Geolocation;
import org.foi.nwtis.dkopic2.log.LogDB;
import org.foi.nwtis.dkopic2.log.RequestLog;
import org.foi.nwtis.dkopic2.threads.MeteoThread;
import org.foi.nwtis.dkopic2.threads.ServerThread;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;

/**
 *
 * @author domagoj
 */
public class Responser {
    private Matcher matcherAdmin;
    private Matcher matcherUser;
    private Matcher matcherBasic;
    private Socket socket;
    private User user;

    public Responser(Matcher matcherAdmin, Matcher matcherUser, Matcher matcherBasic, Socket socket, User user) {
        this.matcherAdmin = matcherAdmin;
        this.matcherUser = matcherUser;
        this.matcherBasic = matcherBasic;
        this.socket = socket;
        this.user = user;
    }
    
    public String handlePause() {
        long start = System.currentTimeMillis();
        
        if (!MeteoThread.isPaused())
        {
            MeteoThread.setPaused(true);
            insertLog(user, System.currentTimeMillis() - start, 1, "PAUSE", "");
            return getOk();
        }
        else 
        {
            insertLog(user, System.currentTimeMillis() - start, 0, "PAUSE", "");
            return "ERR 30";
        }
    }
    
    public String handleStart() {
        long start = System.currentTimeMillis();
        
        if (MeteoThread.isPaused()) 
        {
            MeteoThread.setPaused(false);
            insertLog(user, System.currentTimeMillis() - start, 1, "START", "");
            return getOk();
        }
        else 
        {
            insertLog(user, System.currentTimeMillis() - start, 0, "START", "");
            return "ERR 31";
        }
    }
    
    public String handleStop() {
        long start = System.currentTimeMillis();
        
        if (!MeteoThread.isStopped()) 
        {
            MeteoThread.setStopped(true);
            ServerThread.setStopped(true);
            insertLog(user, System.currentTimeMillis() - start, 1, "STOP", "");
            return getOk();
        }
        else 
        {
            insertLog(user, System.currentTimeMillis() - start, 0, "STOP", "");
            return "ERR 32";
        }
    }
    
    public String getStatus() {
        long start = System.currentTimeMillis();
        String response = "";
        
        if (MeteoThread.isPaused() && !ServerThread.isStopped())
            response = "00";
        
        else if (!MeteoThread.isPaused() && !ServerThread.isStopped())
            response = "01";
        
        else if (MeteoThread.isPaused() && ServerThread.isStopped()) 
            response = "02"; 
        
        insertLog(user, System.currentTimeMillis() - start, 1, "STATUS", "");
        return response;
    }
    
    public String handleAddUser(String request) {
        long start = System.currentTimeMillis();
        String username = matcherAdmin.group(5);
        String password = matcherAdmin.group(6);
        String role = matcherAdmin.group(7);
        
        User userToAdd = UserDB.getUser(username);
        
        if (userToAdd != null)
        {
            insertLog(user, System.currentTimeMillis() - start, 0, "ADD_USER", "");
            return "ERR 33";
        }
        else 
        {
            UserDB.insertUser(username, password, role);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String time = formatter.format(new Date());
            
            String message = request + "\r\n" 
                    + "Vrijeme zahtjeva: " + time + "\r\n"
                    + "Ukupan broj korisnika: " + UserDB.getUserCount() + "\r\n"
                    + "Broj administratora: " + UserDB.getRoleCount("Administrator") + "\r\n"
                    + "Broj korisnika: " + UserDB.getRoleCount("Korisnik") + "\r\n";
            
            Mail.send(message);
            
            insertLog(user, System.currentTimeMillis() - start, 1, "ADD_USER", "");
            return getOk();
        }
    }
    
    public String handleUpdate() {
        long start = System.currentTimeMillis();
        String username = matcherAdmin.group(9);
        String category = matcherAdmin.group(8);
        
        User userComm = UserDB.getUser(username);
        
        if (userComm != null)
        {
            if (category.equals("UP")) 
            {
                if (userComm.getCategory() == 5)
                {
                    insertLog(user, System.currentTimeMillis() - start, 0, category, "");
                    return "ERR 34";
                }
                    
                else
                {
                    UserDB.updateCategory(username, userComm.getCategory() + 1);
                    insertLog(user, System.currentTimeMillis() - start, 1, category, "");
                    return getOk();
                }
            }
            else
            {
                if (userComm.getCategory() == 1)
                {
                    insertLog(user, System.currentTimeMillis() - start, 0, category, "");
                    return "ERR 34";
                }
                    
                else
                {
                    UserDB.updateCategory(username, userComm.getCategory() - 1);
                    insertLog(user, System.currentTimeMillis() - start, 1, category, "");
                    return getOk();
                }
            }
        }
        else
        {
            insertLog(user, System.currentTimeMillis() - start, 0, category, "");
            return "ERR 35";
        }
    }
    
    public String handleAdd() {
        long start = System.currentTimeMillis();
        String address = matcherUser.group(7);
        Address addr = AddressDB.getAddress(address);
        
        if (addr != null)
        {
            insertLog(user, System.currentTimeMillis() - start, 0, "ADD_adresa", address);
            return "ERR 41";
        }
        else 
        {
            GMClient client = new GMClient();
            Geolocation location = client.getGeoLocation(address);
            addr = new Address(address,location);
            AddressDB.insert(addr, user.getId());
            
            insertLog(user, System.currentTimeMillis() - start, 1, "ADD_adresa", address);
            return "OK 10";
        }
    }
    
    public String handleTest() {
        long start = System.currentTimeMillis();
        String address = matcherUser.group(4);
        Address addr = AddressDB.getAddress(address);
        
        if (addr != null)
        {
            insertLog(user, System.currentTimeMillis() - start, 1, "TEST_adresa", address);
            return "OK 10";
        }
        else 
        {
            insertLog(user, System.currentTimeMillis() - start, 0, "TEST_adresa", address);
            return "ERR 42";
        }
    }
    
    public String getErr40() {
        return "ERR 40";
    }
    
    public String getOk() {
        return "OK 10";
    }
    
    public String getErr21() {
        return "ERR 21";
    }
    
    public String getErr20(){
        return "ERR 20";
    }
    
    public void insertLog(User user, long duration, int status, String action, String addr) {
        String ip = socket.getRemoteSocketAddress().toString();
        RequestLog log = new RequestLog();
        log.setIp(ip);
        log.setDuration(duration);
        log.setAction(action);
        log.setUser(user);
        log.setAddress(addr);
        log.setTime(new Date());
        log.setStatus(status);
        
        LogDB.insert(log);
    }
}
