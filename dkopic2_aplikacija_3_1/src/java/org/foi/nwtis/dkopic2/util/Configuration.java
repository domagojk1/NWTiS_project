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

    public static Konfiguracija getConfiguration() {
        return configuration;
    }

    public static int getServerPort() {
        return Integer.parseInt(configuration.dajPostavku("serverPort"));
    }

    public static String getHost() {
        return configuration.dajPostavku("adresa");
    }

    public static String getFile1() {
        return configuration.dajPostavku("datoteka1");
    }
    
    public static String getFile2() {
        return configuration.dajPostavku("datoteka2");
    }
}
