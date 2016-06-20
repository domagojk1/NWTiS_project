/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.location;

/**
 * Klasa koja služi za dohvaćanje BASE_URI-a Google Mapsa.
 * @author domagoj
 */
public class GMHelper {
    private static final String GM_BASE_URI = "http://maps.google.com/";    

    public GMHelper() {
    }

    public static String getGM_BASE_URI() {
        return GM_BASE_URI;
    }
}
