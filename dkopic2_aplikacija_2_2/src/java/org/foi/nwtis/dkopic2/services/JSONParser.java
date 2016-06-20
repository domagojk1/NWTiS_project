/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.foi.nwtis.dkopic2.soap.Address;
import org.foi.nwtis.dkopic2.soap.Geolocation;
import org.foi.nwtis.dkopic2.soap.MeteoData;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author domagoj
 */
public class JSONParser {
    public static ArrayList<MeteoData> getForecast(String json) {
        ArrayList<MeteoData> list = new ArrayList<>();
        
        try 
        {
            JSONObject object = new JSONObject(json.trim());
            JSONArray array = object.getJSONArray("forecast");
            
            Address address = new Address();
            Geolocation location = new Geolocation();
            address.setAddress(array.getJSONObject(0).getString("address"));
            location.setLatitude(array.getJSONObject(0).getString("latitude"));
            location.setLongitude(array.getJSONObject(0).getString("longitude"));
            address.setLocation(location);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            GregorianCalendar gregory = new GregorianCalendar();
            Date date = null;

            for (int i = 1; i < array.length(); i++)
            {
                MeteoData data = new MeteoData();
                data.setAddress(address);
                date = formatter.parse(array.getJSONObject(i).getString("download_time"));
                gregory.setTime(date);
                data.setDownloadTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory));
                
                data.setTemperatureValue(array.getJSONObject(i).getDouble("temperature"));
                data.setTemperatureMin(array.getJSONObject(i).getDouble("temperature_min"));
                data.setTemperatureMax(array.getJSONObject(i).getDouble("temperature_max"));
                data.setWeatherValue(array.getJSONObject(i).getString("weather"));
                data.setHumidityValue(array.getJSONObject(i).getDouble("humidity"));
                data.setWindSpeedValue(array.getJSONObject(i).getDouble("wind_speed"));
                data.setPressureValue(array.getJSONObject(i).getDouble("pressure"));
                
                date = formatter.parse(array.getJSONObject(i).getString("time"));
                gregory.setTime(date);
                data.setLastUpdate(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory));
                
                list.add(data);
            }
        }
        catch (JSONException ex)
        {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
    
    public static List<Address> getAddresses(String json) {
        ArrayList<Address> list = null;
        try
        {
            System.out.println(json);
            JSONObject object = new JSONObject(json.trim());
            JSONArray array = object.getJSONArray("adresses");
            list = new ArrayList<>();
            
            for (int i = 0; i < array.length(); i++)
            {
                Address addr = new Address();
                addr.setAddress(array.getJSONObject(i).getString("address"));
                Geolocation loc = new Geolocation();
                loc.setLatitude(array.getJSONObject(i).getString("latitude"));
                loc.setLongitude(array.getJSONObject(i).getString("longitude"));
                addr.setLocation(loc);
                addr.setId(array.getJSONObject(i).getInt("id"));
                list.add(addr);
            }
        }
        catch (JSONException ex)
        {
            Logger.getLogger(JSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
    }
}
