/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.google;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.dkopic2.location.Geolocation;

/**
 * Klasa za za pozivanje Geocode API-a.
 * @author domagoj
 */
public class GMClient {

    private GMHelper helper;
    private Client client;
    private Geolocation location = null;

    /**
     * Konstruktor, inicijalizira novog klijenta.
     */
    public GMClient() {
        client = ClientBuilder.newClient();
    }

    /**
     * Poziva Geocode API za proslijeđenom adresom.
     * @param adresa naziv adrese
     * @return objekt klase Lokacija
     */
    public Geolocation getGeoLocation(String adresa) {
        try 
        {
            WebTarget webResource = client.target(GMHelper.getGM_BASE_URI())
                                    .path("maps/api/geocode/json");
            webResource = webResource.queryParam("address", URLEncoder.encode(adresa, "UTF-8"));
            webResource = webResource.queryParam("sensor", "false");

            String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);

            JsonReader reader = Json.createReader(new StringReader(odgovor));

            JsonObject jsonObject = reader.readObject();
                     
            JsonObject obj = jsonObject.getJsonArray("results")
                            .getJsonObject(0)
                            .getJsonObject("geometry")
                            .getJsonObject("location");

            location = new Geolocation(obj.getJsonNumber("lat").toString(), obj.getJsonNumber("lng").toString());
        } 
        catch (UnsupportedEncodingException ex)
        {
            Logger.getLogger(GMClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return location;
    }
}
