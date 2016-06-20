/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.util.ArrayList;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;

/**
 *
 * @author domagoj
 */
public class Configuration {
    private static Konfiguracija configuration;
    private static int threadInterval;
    private static int threadNumber;
    private static String owmId;
    private static int serverPort;
    private static String host;
    private static String sender;
    private static String receiver;
    private static String subject;
    private static int userInterval;
    private static int quota;
    private static int lines;
    private static int imapPort;
    private static String password;
    private static String successFolder;
    private static String failFolder;
    private static String wrongFolder;

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

    public static int getImapPort() {
        return Integer.parseInt(configuration.dajPostavku("imapPort"));
    }

    public static String getPassword() {
        return configuration.dajPostavku("sifraPrimatelj");
    }

    public static String getSuccessFolder() {
        return configuration.dajPostavku("uspjesnePoruke");
    }

    public static String getFailFolder() {
        return configuration.dajPostavku("neuspjesnePoruke");
    }

    public static String getWrongFolder() {
        return configuration.dajPostavku("neispravnePoruke");
    }
    
    public static ArrayList<String> getFolders() {
        ArrayList<String> folders = new ArrayList<>();
        folders.add(configuration.dajPostavku("uspjesnePoruke"));
        folders.add(configuration.dajPostavku("neuspjesnePoruke"));
        folders.add(configuration.dajPostavku("neispravnePoruke"));
        return folders;
    }
}
