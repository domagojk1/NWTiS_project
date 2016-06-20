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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.location.AddressDB;
import org.foi.nwtis.dkopic2.util.DBConnection;

/**
 *
 * @author domagoj
 */
public class ForecastDB {
    
    public static void insert(Forecast forecast) {
        String sql = "INSERT INTO dkopic2_prognoze (adresa, temperatura, temperatura_min, temperatura_max, tlak, vlaga, vjetar_brzina," + 
                     "opis, vrijeme_prognoze, vrijeme_preuzimanja) VALUES (?,?,?,?,?,?,?,?,?,?)";
        DBConnection database = DBConnection.getInstance();
        try 
        {
            for (MeteoData meteo : forecast.getForecast())
            {
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                
                statement.setInt(1, meteo.getAddress().getId());
                statement.setDouble(2, meteo.getTemperatureValue());
                statement.setDouble(3, meteo.getTemperatureMin());
                statement.setDouble(4, meteo.getTemperatureMax());
                statement.setDouble(5, meteo.getPressureValue());
                statement.setDouble(6, meteo.getHumidityValue());
                statement.setDouble(7, meteo.getWindSpeedValue());
                statement.setString(8, meteo.getWeatherValue());
                statement.setTimestamp(9, new Timestamp(meteo.getLastUpdate().getTime()));
                statement.setTimestamp(10, new Timestamp(meteo.getDownloadTime().getTime()));
                
                statement.executeUpdate();
                database.closeConnection(connection);
                
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
    }
    
    public static Forecast getForecast(int id) {
        String sql = "SELECT * FROM dkopic2_prognoze WHERE adresa = ? ORDER BY vrijeme_preuzimanja DESC LIMIT 40";
        DBConnection database = DBConnection.getInstance();
        Connection connection = null;
        Forecast forecast = null;
        ArrayList<MeteoData> list = null;
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            list = new ArrayList<>();
            
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
                meteo.setLastUpdate(result.getTimestamp("vrijeme_prognoze"));
                meteo.setDownloadTime(result.getTimestamp("vrijeme_preuzimanja"));
                
                list.add(meteo);
            }
            
            forecast = new Forecast(list);
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ForecastDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return forecast;
    }
    
    public static Forecast getForecast(int id, String start, String end) {
        String sql = "SELECT * FROM dkopic2_prognoze WHERE adresa = ? and vrijeme_prognoze BETWEEN ? and ?";
        DBConnection database = DBConnection.getInstance();
        Connection connection = null;
        Forecast forecast = null;
        ArrayList<MeteoData> list = null;
        
        SimpleDateFormat source = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try 
        {
            Date startDate = source.parse(start);
            Date endDate = source.parse(end);
            
            start = formatter.format(startDate);
            end = formatter.format(endDate);
        }
        catch (ParseException ex) {
            Logger.getLogger(ForecastDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try 
        {
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, start);
            statement.setString(3, end);
            ResultSet result = statement.executeQuery();
            list = new ArrayList<>();
            
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
                meteo.setLastUpdate(result.getTimestamp("vrijeme_prognoze"));
                meteo.setDownloadTime(result.getTimestamp("vrijeme_preuzimanja"));
                
                list.add(meteo);
            }
            
            forecast = new Forecast(list);
            database.closeConnection(connection);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(ForecastDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return forecast;
    }
}
