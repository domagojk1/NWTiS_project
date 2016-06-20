/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dkopic2.log.WebServiceLog;
import org.foi.nwtis.dkopic2.log.LogDB;
import org.foi.nwtis.dkopic2.meteo.Forecast;
import org.foi.nwtis.dkopic2.meteo.ForecastDB;
import org.foi.nwtis.dkopic2.meteo.MeteoData;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author domagoj
 */
public class ForecastRESTResource {
    private String id; 
    
    /**
     * Creates a new instance of ForecastRESTResource
     */
    private ForecastRESTResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the ForecastRESTResource
     */
    public static ForecastRESTResource getInstance(String id) {
        return new ForecastRESTResource(id);
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.dkopic2.rest.ForecastRESTResource
     * @return an instance of java.lang.String
     */
    @Path("/current")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@Context HttpServletRequest request, @QueryParam("username") String username, @QueryParam("password") String password) {
        String response = "";
        Forecast forecast = ForecastDB.getForecast(Integer.parseInt(id));
        User user = UserDB.getUser(username, password);
        long start = System.currentTimeMillis();
        int status = 0;
        
        if (user != null)
        {
            if (user.isAdmin())
            {
                response = produceJsonForecast(forecast);
                status = 1;
            }
            else if (user.checkRequests())
            {
                UserDB.updateRequests(username, user.getRequests() + 1);
                response = produceJsonForecast(forecast);
                status = 1;
            }
            else response = "Too much requests";
        }
        else response = "User not valid.";
        
        long end = System.currentTimeMillis();
        insertLog(request, "getForecast", status, user, end - start);
        
        return response;
    }
    
    @Path("/timeinterval")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@Context HttpServletRequest request, @QueryParam("start") String start, @QueryParam("end") String end, @QueryParam("username") String username, @QueryParam("password") String password) {
        String response = "";
        User user = UserDB.getUser(username, password);
        Forecast forecast = ForecastDB.getForecast(Integer.parseInt(id), start, end);
        long st = System.currentTimeMillis();
        int status = 0;

        if (user != null)
        {
            if (user.isAdmin())
            {
                response = produceJsonForecast(forecast);
                status = 1;
            }
            else if (user.checkRequests())
            {
                UserDB.updateRequests(username, user.getRequests() + 1);
                response = produceJsonForecast(forecast);
                status = 1;
            }
            else response = "Too much requests";
        }
        else response = "User not valid.";
        
        long en = System.currentTimeMillis();
        insertLog(request, "getForecastInterval", status, user, en - st);
        
        return response;
    }

    /**
     * PUT method for updating or creating an instance of ForecastRESTResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource ForecastRESTResource
     */
    @DELETE
    public void delete() {
    }
    
    private String produceJsonForecast(Forecast forecast) {
        JSONObject main = new JSONObject();
        JSONArray array = new JSONArray();
        String json = ""; 
        
        try
        {
            JSONObject object = new JSONObject();
            object.put("id", forecast.getForecast().get(0).getAddress().getId());
            object.put("address", forecast.getForecast().get(0).getAddress().getAddress());
            object.put("longitude", forecast.getForecast().get(0).getAddress().getLocation().getLongitude());
            object.put("latitude", forecast.getForecast().get(0).getAddress().getLocation().getLatitude());
            array.put(object);
            
            for (MeteoData meteo : forecast.getForecast())
            {
                object = new JSONObject();
                object.put("weather", meteo.getWeatherValue());
                object.put("temperature", meteo.getTemperatureValue());
                object.put("temperature_min", meteo.getTemperatureMin());
                object.put("temperature_max", meteo.getTemperatureMax());
                object.put("humidity", meteo.getHumidityValue());
                object.put("pressure", meteo.getPressureValue());
                object.put("wind_speed", meteo.getWindSpeedValue());
                object.put("time", meteo.getLastUpdate());
                object.put("download_time", meteo.getDownloadTime());
                
                array.put(object);
            }
            
            main.put("forecast", array);
            json = main.toString();
        }
        catch (JSONException ex)
        {
            ex.printStackTrace();
        }
        
        return json;
    }
    
    private void insertLog(HttpServletRequest request, String action, int status, User user, long duration) {
        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString() + "?method=" + request.getMethod() + "&action=" + action;
        
        WebServiceLog log = new WebServiceLog();
        log.setUser(user);
        log.setIp(ip);
        log.setUrl(url);
        log.setStatus(status);
        log.setTime(new Date());
        log.setDuration(duration);
        LogDB.insert(log);
    }
}
