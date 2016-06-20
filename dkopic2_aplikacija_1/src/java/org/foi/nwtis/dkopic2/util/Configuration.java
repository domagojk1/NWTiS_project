/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;

/**
 *
 * @author domagoj
 */
public class Configuration {
    private static Konfiguracija configuration;
    
    public Configuration(Konfiguracija configuration) {
        this.configuration = configuration;
    }

    public static int getThreadInterval() {
        return Integer.parseInt(configuration.dajPostavku("intervalDretve"));
    }

    public static int getThreadNumber() {
        return Integer.parseInt(configuration.dajPostavku("brojDretvi"));
    }

    public static String getOwmId() {
        return configuration.dajPostavku("APPID");
    }

    public static Konfiguracija getConfiguration() {
        return configuration;
    }

    public static int getServerPort() {
        return Integer.parseInt(configuration.dajPostavku("serverPort"));
    }

    public static String getHost() {
        return configuration.dajPostavku("adresa");
    }

    public static String getSender() {
        return configuration.dajPostavku("adresaPosiljatelj");
    }

    public static String getReceiver() {
        return configuration.dajPostavku("adresaPrimatelj");
    }

    public static String getSubject() {
        return configuration.dajPostavku("predmetPoruke");
    }

    public static int getUserInterval() {
        return Integer.parseInt(configuration.dajPostavku("intervalVremena"));
    }

    public static int getQuota() {
        return Integer.parseInt(configuration.dajPostavku("kvota"));
    }

    public static int getLines() {
        return Integer.parseInt(configuration.dajPostavku("brojLinija"));
    }
    
    public static String getDriver() {
        return configuration.dajPostavku("driver");
    }
    
    public static String getUsername() {
        return configuration.dajPostavku("username");
    }
    
    public static String getPassword() {
        return configuration.dajPostavku("password");
    }
    
    public static String getUrl() {
        return configuration.dajPostavku("url");
    }
}
