/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.dkopic2.location.Address;
import org.foi.nwtis.dkopic2.location.AddressDB;
import org.foi.nwtis.dkopic2.log.WebServiceLog;
import org.foi.nwtis.dkopic2.log.LogDB;
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
@Path("/ForecastREST")
public class ForecastRESTResourceContainer {

    @Context
    private UriInfo ctx;
    
    @Context 
    HttpServletRequest request;

    /**
     * Creates a new instance of ForecastRESTResourceContainer
     */
    public ForecastRESTResourceContainer() {
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.dkopic2.rest.ForecastRESTResourceContainer
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@QueryParam("username") String username, @QueryParam("password") String password) {
        String response = "";
        ArrayList<Address> list = AddressDB.getAdresses();
        User user = UserDB.getUser(username, password);
        long start = System.currentTimeMillis();
        int status = 0;
        
        if (user != null)
        {
            if (user.isAdmin())
            {
                response = produceJsonAddr(list);
                status = 1;
            }
            else if (user.checkRequests())
            {
                UserDB.updateRequests(username, user.getRequests() + 1);
                response = produceJsonAddr(list);
                status = 1;
            }
            else
            {
                JSONObject obj = new JSONObject();
                JSONArray arr = new JSONArray();
                try 
                {
                    obj.put("addresses", arr);
                    response = obj.toString();
                }
                catch (JSONException ex) 
                {
                    Logger.getLogger(ForecastRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray();
            try 
            {
                obj.put("addresses", arr);
                response = obj.toString();
            }
            catch (JSONException ex) 
            {
                Logger.getLogger(ForecastRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        long end = System.currentTimeMillis();
        insertLog("getAddreses", status, user, end - start);
        
        return response;
    }
  
    /**
     * POST method for creating an instance of ForecastRESTResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        //TODO
        return Response.created(ctx.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public ForecastRESTResource getForecastRESTResource(@PathParam("id") String id) {
        return ForecastRESTResource.getInstance(id);
    }
    
    private String produceJsonAddr(ArrayList<Address> list) {
        JSONObject main = new JSONObject();
        JSONArray array = new JSONArray();
        String json = "";
        
        try
        {
            for (Address addr : list)
            {
                JSONObject object = new JSONObject();
                object.put("id", addr.getId());
                object.put("address", addr.getAddress());
                object.put("longitude", addr.getLocation().getLongitude());
                object.put("latitude", addr.getLocation().getLatitude());

                array.put(object);
            }
            
            main.put("adresses", array);
            json = main.toString().trim();
        }
        catch(JSONException ex)
        {
            ex.printStackTrace();
        }
        
        return json;
    }
    
    private void insertLog(String action, int status, User user, long duration) {
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
