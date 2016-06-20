/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.owm;


import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.meteo.Forecast;
import org.foi.nwtis.dkopic2.meteo.MeteoData;
import org.foi.nwtis.dkopic2.location.Address;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * Klasa za pozivanje openweathermap.org API-a.
 * @author domagoj
 */
public class OWMClient {
    private String apiKey;
    private OWMHelper helper;
    private Client client;

    /**
     * Konstruktor, dohvaća API key i inicijalizira objekte klasi Client i OWMRESTHelper.
     */
    public OWMClient() {
        apiKey = Configuration.getOwmId();
        helper = new OWMHelper(apiKey);
        client = ClientBuilder.newClient();
    }

    /**
     * Dohvaća vremensku prognozu pozivajući API prema geografskoj širini i dužini.
     * @param address Address
     * @return objekt klase MeteoPodaci
     */
    public synchronized MeteoData getRealTimeWeather(Address address) {
        WebTarget webResource = client.target(OWMHelper.getOWM_BASE_URI())
                .path(OWMHelper.getOWM_Current_Path());
        webResource = webResource.queryParam("lat", address.getLocation().getLatitude());
        webResource = webResource.queryParam("lon", address.getLocation().getLongitude());
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", apiKey);
        
        String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
              
        try 
        {
            JsonReader reader = Json.createReader(new StringReader(odgovor));
            JsonObject object = reader.readObject();
            MeteoData meteo = new MeteoData();
            
            meteo.setAddress(address);
            meteo.setTemperatureValue(new Double(object.getJsonObject("main").getJsonNumber("temp").doubleValue()));
            meteo.setTemperatureMin(new Double(object.getJsonObject("main").getJsonNumber("temp_min").doubleValue()));
            meteo.setTemperatureMax(new Double(object.getJsonObject("main").getJsonNumber("temp_max").doubleValue()));
            meteo.setHumidityValue(new Double(object.getJsonObject("main").getJsonNumber("humidity").doubleValue()));
            meteo.setPressureValue(new Double(object.getJsonObject("main").getJsonNumber("pressure").doubleValue()));
            meteo.setWindSpeedValue(new Double(object.getJsonObject("wind").getJsonNumber("speed").doubleValue()));
            meteo.setWeatherValue(object.getJsonArray("weather").getJsonObject(0).getString("description"));
            meteo.setLastUpdate(new Date(object.getJsonNumber("dt").bigDecimalValue().longValue()*1000));
            meteo.setDownloadTime(new Date());
            
            return meteo;
            
        } 
        catch (Exception ex)
        {
            return null;
        }
    }
    
    /**
     * Dohvaća vremensku prognozu u idućih 5 dana prema proslijeđenoj adresi pozivajući API.
     * @param address naziv adrese
     * @return JSON odgovor
     */
    public synchronized Forecast getForecast(Address address) {
        Date date = null;
        Forecast forecast = null;
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        List<MeteoData> list = new ArrayList<>();
        
        WebTarget webResource = client.target(OWMHelper.getOWM_BASE_URI())
                .path(OWMHelper.getOWM_Forecast_Path());
        webResource = webResource.queryParam("lat", address.getLocation().getLatitude());
        webResource = webResource.queryParam("lon", address.getLocation().getLongitude());
        webResource = webResource.queryParam("lang", "hr");
        webResource = webResource.queryParam("units", "metric");
        webResource = webResource.queryParam("APIKEY", apiKey);
        
        String response = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        
        try 
        {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = (JSONArray)jsonObject.get("list");
            
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i).getJSONObject("main");
                
                MeteoData meteo = new MeteoData();
                meteo.setAddress(address);
                meteo.setTemperatureValue(object.getDouble("temp"));
                meteo.setTemperatureMin(object.getDouble("temp_min"));
                meteo.setTemperatureMax(object.getDouble("temp_max"));
                meteo.setPressureValue(object.getDouble("pressure"));
                meteo.setHumidityValue(object.getDouble("humidity"));
                meteo.setWeatherValue(array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description"));
                
                object = array.getJSONObject(i).getJSONObject("wind");
                meteo.setWindSpeedValue(object.getDouble("speed"));
                meteo.setDownloadTime(new Date());
                
                try 
                {
                    long d = array.getJSONObject(i).getLong("dt");
                    date = new Date(d * 1000);
                    meteo.setLastUpdate(date);
                } 
                catch (Exception ex) 
                {
                    return null;
                }
                
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                
                list.add(meteo);
            }
            
            forecast = new Forecast(list);
        } 
        catch (JSONException ex) {
            return null;
        }
        
        
        return forecast;
    }
}
