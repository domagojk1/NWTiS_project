/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.meteo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.location.AddressDB;
import org.foi.nwtis.dkopic2.util.DBConnection;

/**
 *
 * @author domagoj
 */
public class MeteoDB {
    public static void insert(MeteoData data) {
        String sql = "INSERT INTO dkopic2_meteo (adresa, temperatura, temperatura_min, temperatura_max, tlak, vlaga, vjetar_brzina, " + 
                     "vidljivost, opis, vrijeme_izmjene, vrijeme_preuzimanja) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            Connection connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, data.getAddress().getId());
            statement.setDouble(2, data.getTemperatureValue());
            statement.setDouble(3, data.getTemperatureMin());
            statement.setDouble(4, data.getTemperatureMax());
            statement.setDouble(5, data.getPressureValue());
            statement.setDouble(6, data.getHumidityValue());
            statement.setDouble(7, data.getWindSpeedValue());
            statement.setString(8, data.getVisibility());
            statement.setString(9, data.getWeatherValue());
            statement.setTimestamp(10, new Timestamp(data.getLastUpdate().getTime()));
            statement.setTimestamp(11, new Timestamp(data.getDownloadTime().getTime()));
            
            statement.executeUpdate();
            database.closeConnection(connection);
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static MeteoData getMeteo(int id) {
        MeteoData meteo = null;
        String sql = "SELECT * FROM dkopic2_meteo WHERE adresa = ? order by vrijeme_preuzimanja DESC";
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
                meteo = new MeteoData();
           
                meteo.setAddress(AddressDB.getAddress(id));
                meteo.setTemperatureValue(result.getDouble("temperatura"));
                meteo.setTemperatureMin(result.getDouble("temperatura_min"));
                meteo.setTemperatureMax(result.getDouble("temperatura_max"));
                meteo.setHumidityValue(result.getDouble("vlaga"));
                meteo.setPressureValue(result.getDouble("tlak"));
                meteo.setWindSpeedValue(result.getDouble("vjetar_brzina"));
                meteo.setWeatherValue(result.getString("opis"));
                meteo.setLastUpdate(result.getTimestamp("vrijeme_izmjene"));
                meteo.setDownloadTime(result.getTimestamp("vrijeme_preuzimanja"));
            }
            
            
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(MeteoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return meteo;
    }
    
    public static List<MeteoData> getLast(int id, int limit) {
        String sql = "SELECT * FROM  dkopic2_meteo WHERE adresa = ? ORDER BY vrijeme_preuzimanja DESC LIMIT ?";
        List<MeteoData> list = new ArrayList<>();
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, limit);
            ResultSet result = statement.executeQuery();
            
            while (result.next())
            {
                MeteoData meteo = new MeteoData();
       
                meteo.setAddress(AddressDB.getAddress(id));
                meteo.setTemperatureValue(result.getDouble("temperatura"));
                meteo.setTemperatureMin(result.getDouble("temperatura_min"));
                meteo.setTemperatureMax(result.getDouble("temperatura_max"));
                meteo.setHumidityValue(result.getDouble("vlaga"));
                meteo.setPressureValue(result.getDouble("tlak"));
                meteo.setWindSpeedValue(result.getDouble("vjetar_brzina"));
                meteo.setWeatherValue(result.getString("opis"));
                meteo.setLastUpdate(result.getTimestamp("vrijeme_izmjene"));
                meteo.setDownloadTime(result.getTimestamp("vrijeme_preuzimanja"));
                
                list.add(meteo);
            }
            
            database.closeConnection(connection);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(MeteoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<MeteoData> getMeteo(int id, String dateStart, String dateEnd) {
        String sql = "SELECT * FROM  `dkopic2_meteo` WHERE adresa = ? AND vrijeme_preuzimanja BETWEEN ? AND ?";
        List<MeteoData> list = new ArrayList<>();
        Connection connection = null;
        DBConnection database = DBConnection.getInstance();
        
        SimpleDateFormat source = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try 
        {
            Date start = source.parse(dateStart);
            Date end = source.parse(dateEnd);
            
            dateStart = formatter.format(start);
            dateEnd = formatter.format(end);
        } 
        catch (ParseException ex)
        {
            Logger.getLogger(MeteoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, dateStart);
            statement.setString(3, dateEnd);
            
            ResultSet result = statement.executeQuery();
            
            while (result.next())
            {
                MeteoData meteo = new MeteoData();
       
                meteo.setAddress(AddressDB.getAddress(id));
                meteo.setTemperatureValue(result.getDouble("temperatura"));
                meteo.setTemperatureMin(result.getDouble("temperatura_min"));
                meteo.setTemperatureMax(result.getDouble("temperatura_max"));
                meteo.setHumidityValue(result.getDouble("vlaga"));
                meteo.setPressureValue(result.getDouble("tlak"));
                meteo.setWindSpeedValue(result.getDouble("vjetar_brzina"));
                meteo.setWeatherValue(result.getString("opis"));
                meteo.setLastUpdate(result.getTimestamp("vrijeme_izmjene"));
                meteo.setDownloadTime(result.getTimestamp("vrijeme_preuzimanja"));
                
                list.add(meteo);
            }
            
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(MeteoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
