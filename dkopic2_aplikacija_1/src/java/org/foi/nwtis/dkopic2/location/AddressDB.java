/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.util.DBConnection;
import org.foi.nwtis.dkopic2.location.Address;
import org.foi.nwtis.dkopic2.location.Geolocation;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;


/**
 *
 * @author domagoj
 */
public class AddressDB {
    public static ArrayList<Address> getAdresses() {
        ArrayList<Address> list = null;
        String query = "SELECT * FROM dkopic2_adrese";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = new ArrayList<>();
            
            while (resultSet.next())
            {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setAddress(resultSet.getString("adresa"));
                Geolocation location = new Geolocation();
                location.setLongitude(resultSet.getString("longitude"));
                location.setLatitude(resultSet.getString("latitude"));
                address.setLocation(location);
                User user = UserDB.getUser(resultSet.getInt("korisnik"));
                address.setUser(user);
                
                list.add(address);
            }
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        } 

        return list;
    }
    
    public static Address getAddress(String addr) {
        Address address = null;
        String sql = "SELECT * FROM dkopic2_adrese WHERE adresa=?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, addr);
            ResultSet result = statement.executeQuery();
            
            if (result.next())
            {
                address = new Address();
                address.setId(result.getInt("id"));
                address.setAddress(result.getString("adresa"));
                Geolocation location = new Geolocation();
                location.setLongitude(result.getString("longitude"));
                location.setLatitude(result.getString("latitude"));
                address.setLocation(location);
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(AddressDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return address;
    } 
    
    public static Address getAddress(int id) {
        Address address = null;
        String sql = "SELECT * FROM dkopic2_adrese WHERE id=?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            
            if (result.next())
            {
                address = new Address();
                address.setId(result.getInt("id"));
                address.setAddress(result.getString("adresa"));
                Geolocation location = new Geolocation();
                location.setLongitude(result.getString("longitude"));
                location.setLatitude(result.getString("latitude"));
                address.setLocation(location);
            }
            
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(AddressDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return address;
    } 
    
    public static List<Address> getUsers(int id) {
        ArrayList<Address> list = null;
        String query = "SELECT * FROM dkopic2_adrese where korisnik = ?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            list = new ArrayList<>();
            
            while (resultSet.next())
            {
                Address address = new Address();
                address.setId(resultSet.getInt("id"));
                address.setAddress(resultSet.getString("adresa"));
                Geolocation location = new Geolocation();
                location.setLongitude(resultSet.getString("longitude"));
                location.setLatitude(resultSet.getString("latitude"));
                address.setLocation(location);
                User user = UserDB.getUser(resultSet.getInt("korisnik"));
                address.setUser(user);
                
                list.add(address);
            }
            
            database.closeConnection(connection);
        } 
        catch (SQLException ex)
        {
            ex.printStackTrace();
        } 

        return list;
    }
    
    public static void insert(Address address, int user){
        String sql = "INSERT INTO dkopic2_adrese (longitude, latitude, korisnik, adresa) VALUES (?,?,?,?)";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, address.getLocation().getLongitude());
            statement.setString(2, address.getLocation().getLatitude());
            statement.setInt(3, user);
            statement.setString(4, address.getAddress());
            statement.executeUpdate();
            
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(AddressDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Rank> getRankList(int limit) {
        String sql = "SELECT adresa, COUNT( * ) AS  'broj' FROM dkopic2_meteo GROUP BY adresa LIMIT ?";
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        ArrayList<Rank> list = null;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, limit);
            ResultSet result = statement.executeQuery();
            list = new ArrayList<>();
            
            while (result.next())
            {
                Address addr = getAddress(result.getInt("adresa"));
                Rank rank = new Rank(addr, result.getInt("broj"));
                list.add(rank);
            }
            
            database.closeConnection(connection);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AddressDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
