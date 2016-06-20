/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;
import org.foi.nwtis.dkopic2.util.DBConnection;

/**
 *
 * @author domagoj
 */
public class LogDB {
    
    public static void insert(WebServiceLog log) {
        String sql = "INSERT INTO dkopic2_dnevnik (korisnik,url,ip,vrijeme,trajanje,status) VALUES (?,?,?,?,?,?)";
        Connection conn = null;
        DBConnection dbSource = DBConnection.getInstance();
        
        try 
        {
            conn = dbSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            if (log.getUser() != null)
                statement.setInt(1, log.getUser().getId());
            else
                statement.setNull(1, java.sql.Types.INTEGER);
            statement.setString(2, log.getUrl());
            statement.setString(3, log.getIp());
            statement.setTimestamp(4, new Timestamp(log.getTime().getTime()));
            statement.setLong(5, log.getDuration());
            statement.setInt(6, log.getStatus());
            statement.executeUpdate();
            
            dbSource.closeConnection(conn);
        }
        catch (SQLException ex) {
            Logger.getLogger(LogDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<WebServiceLog> getLogs() {
        String sql = "SELECT * FROM dkopic2_dnevnik";
        Connection conn = null;
        DBConnection dbSource = DBConnection.getInstance();
        ArrayList<WebServiceLog> logs = null;
        
        try 
        {
            conn = dbSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            logs = new ArrayList<>();
            
            while (result.next())
            {
                WebServiceLog log = new WebServiceLog();
                log.setId(result.getInt("id"));
                int userId = result.getInt("korisnik");
                log.setUser(UserDB.getUser(userId));
                log.setUrl(result.getString("url"));
                log.setIp(result.getString("ip"));
                log.setTime(result.getTimestamp("vrijeme"));
                log.setDuration(result.getLong("trajanje"));
                log.setStatus(result.getInt("status"));
                
                logs.add(log);
            }
            
            dbSource.closeConnection(conn);
        }
        catch (SQLException ex) {
            Logger.getLogger(LogDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return logs;
    }
    
    public static void insert(RequestLog log) {
        String sql = "INSERT INTO dkopic2_zahtjevi (ip, akcija, trajanje, vrijeme, status, adresa, korisnik)"
                + "VALUES (?,?,?,?,?,?,?)";
        DBConnection dbSource = DBConnection.getInstance();
        Connection conn = null;
        
        try 
        {
            conn = dbSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, log.getIp());
            statement.setString(2, log.getAction());
            statement.setLong(3, log.getDuration());
            statement.setTimestamp(4, new Timestamp(log.getTime().getTime()));
            statement.setInt(5, log.getStatus());
            statement.setString(6, log.getAddress());
            if (log.getUser() != null)
                statement.setInt(7, log.getUser().getId());
            else statement.setNull(7, java.sql.Types.INTEGER);
            statement.executeUpdate();
            
            dbSource.closeConnection(conn);
        }
        catch (SQLException ex) {
            Logger.getLogger(LogDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<RequestLog> getRequests() {
        System.out.println("bez");
        String sql = "SELECT * FROM dkopic2_zahtjevi";
        Connection conn = null;
        DBConnection dbSource = DBConnection.getInstance();
        ArrayList<RequestLog> logs = null;
        
        try 
        {
            conn = dbSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            logs = new ArrayList<>();
            
            while (result.next())
            {
                RequestLog log = new RequestLog();
                log.setId(result.getInt("id"));
                log.setIp(result.getString("ip"));
                log.setAction(result.getString("akcija"));
                log.setDuration(result.getLong("trajanje"));
                log.setTime(result.getTimestamp("vrijeme"));
                log.setStatus(result.getInt("status"));
                log.setAddress(result.getString("adresa"));
                log.setUser(UserDB.getUser(result.getInt("korisnik")));
                
                logs.add(log);
            }
            
            dbSource.closeConnection(conn);
        }
        catch (SQLException ex) {
            Logger.getLogger(LogDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return logs;
    }
    
    public static ArrayList<RequestLog> getRequests(String username) {
        System.out.println(username);
        String sql = "SELECT * FROM dkopic2_zahtjevi WHERE korisnik = ?";
        Connection conn = null;
        DBConnection dbSource = DBConnection.getInstance();
        ArrayList<RequestLog> logs = null;
        User user = UserDB.getUser(username);
        
        try 
        {
            conn = dbSource.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user.getId());
            ResultSet result = statement.executeQuery();
            logs = new ArrayList<>();
            
            while (result.next())
            {
                RequestLog log = new RequestLog();
                log.setId(result.getInt("id"));
                log.setIp(result.getString("ip"));
                log.setAction(result.getString("akcija"));
                log.setDuration(result.getLong("trajanje"));
                log.setTime(result.getTimestamp("vrijeme"));
                log.setStatus(result.getInt("status"));
                log.setAddress(result.getString("adresa"));
                log.setUser(UserDB.getUser(result.getInt("korisnik")));
                
                logs.add(log);
            }
            
            dbSource.closeConnection(conn);
        }
        catch (SQLException ex) {
            Logger.getLogger(LogDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return logs;
    }
}
