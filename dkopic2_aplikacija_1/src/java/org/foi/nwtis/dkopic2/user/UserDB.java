/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.util.DBConnection;

/**
 *
 * @author domagoj
 */
public class UserDB {
    
    public static User getUser(String username, String password) {
        String sql = "SELECT * FROM dkopic2_korisnici WHERE username=? AND password=?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        User user = null;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            
            if (result.next())
            {
                user = new User(result.getInt("id"), result.getString("username"), result.getString("password"),
                        result.getString("naziv_uloge"), result.getInt("kategorija"), result.getInt("broj_zahtjeva"));
            }
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return user;
    }
    
    public static User getUser(String username, int role) {
        String sql = "SELECT * FROM dkopic2_korisnici WHERE username=? AND uloga=?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        User user = null;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setInt(2, role);
            ResultSet result = statement.executeQuery();
            
            if (result.next())
            {
                user = new User(result.getInt("id"), result.getString("username"), result.getString("password"),
                        result.getString("naziv_uloge"), result.getInt("kategorija"), result.getInt("broj_zahtjeva"));
            }
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return user;
    }
    
    public static User getUser(String username) {
        String sql = "SELECT * FROM dkopic2_korisnici WHERE username=?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        User user = null;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            
            if (result.next())
            {
                user = new User(result.getInt("id"), result.getString("username"), result.getString("password"),
                        result.getString("naziv_uloge"), result.getInt("kategorija"), result.getInt("broj_zahtjeva"));
            }
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return user;
    }
    
    public static User getUser(int id) {
        String sql = "SELECT * FROM dkopic2_korisnici WHERE id=?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        User user = null;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            
            if (result.next())
            {
                user = new User(result.getInt("id"), result.getString("username"), result.getString("password"),
                        result.getString("naziv_uloge"), result.getInt("kategorija"), result.getInt("broj_zahtjeva"));
            }
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
        return user;
    }
    
    public static ArrayList<User> getUsers() {
        String sql = "SELECT * FROM dkopic2_korisnici";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        ArrayList<User> users = null;
        
        try 
        {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (result.next())
            {
                User user = new User(result.getInt("id"), result.getString("username"), result.getString("password"),
                    result.getString("naziv_uloge"), result.getInt("kategorija"), result.getInt("broj_zahtjeva"));
                users.add(user);
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return users;
    }
    
    public static void insertUser(String username, String password, String role) {
        String sql = "INSERT INTO dkopic2_korisnici (username, password, naziv_uloge, kategorija, broj_zahtjeva) VALUES (?,?,?,?,?)";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            
            if (role.equals("ADMIN"))
                statement.setString(3, "Administrator");
            
            else
                statement.setString(3, "Korisnik");
            
            statement.setInt(4, 1);
            statement.setInt(5, 0);
            
            statement.executeUpdate();
            database.closeConnection(connection);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int getUserCount() {
        String sql = "SELECT COUNT(*) FROM dkopic2_korisnici";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        int count = 0;
        
        try 
        {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            while (result.next())
            {
                count = result.getInt(1);
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;
    }
    
    public static int getRoleCount(String role) {
        String sql = "SELECT COUNT(*) FROM dkopic2_korisnici WHERE naziv_uloge = ?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        int count = 0;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role);
            ResultSet result = statement.executeQuery();
            
            while (result.next())
            {
                count = result.getInt(1);
            }
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;
    }
    
    public static void updateCategory(String user, int category) {
        String sql = "UPDATE dkopic2_korisnici SET kategorija = ? WHERE username = ?";
        DBConnection database = DBConnection.getInstance();
        Connection connection = null;
        
        try
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, category);
            statement.setString(2, user);
            statement.executeUpdate();
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateRequests(String user, int number) {
        String sql = "UPDATE dkopic2_korisnici SET broj_zahtjeva = ? WHERE username = ?";
        DBConnection database = DBConnection.getInstance();
        Connection connection = null;
        
        try
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, number);
            statement.setString(2, user);
            statement.executeUpdate();
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex)
        {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void resetRequests() {
        String sql = "UPDATE dkopic2_korisnici SET broj_zahtjeva = 0";
        DBConnection database = DBConnection.getInstance();
        Connection connection = null;
        
        try 
        {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
