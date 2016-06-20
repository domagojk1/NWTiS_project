/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.threads;

import java.util.ArrayList;
import org.foi.nwtis.dkopic2.meteo.Forecast;
import org.foi.nwtis.dkopic2.meteo.ForecastDB;
import org.foi.nwtis.dkopic2.meteo.MeteoDB;
import org.foi.nwtis.dkopic2.meteo.MeteoData;
import org.foi.nwtis.dkopic2.location.AddressDB;
import org.foi.nwtis.dkopic2.location.Address;
import org.foi.nwtis.dkopic2.owm.OWMClient;

/**
 *
 * @author domagoj
 */
public class MeteoThread extends Thread{
    private int interval;
    private volatile static boolean paused;
    private volatile static boolean stopped;

    public MeteoThread(int interval) {
        this.interval = interval;
    }
    
    @Override
    public void run() {
        try 
        {
            while (!Thread.interrupted())
            {
                
                if(isStopped()) break;
                
                if (!isPaused())
                {
                    downloadData();
                }
                else
                {
                    System.out.println("MeteoThread paused.");
                }
                Thread.sleep(interval * 1000);
            }
        }
        catch (InterruptedException ex) {} 
    }

    @Override
    public synchronized void start() {
        super.start();
    }
    
    public synchronized void downloadData() {
        ArrayList<Address> list = AddressDB.getAdresses();
        
        for (Address address : list)
        {
            OWMClient client = new OWMClient();
            
            MeteoData meteoData = client.getRealTimeWeather(address);
            Forecast forecast = client.getForecast(address);
            
            if (meteoData != null)
                MeteoDB.insert(meteoData);
            
            if (forecast != null)
                ForecastDB.insert(forecast);
        }
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        MeteoThread.paused = paused;
    }

    public static boolean isStopped() {
        return stopped;
    }

    public static void setStopped(boolean stopped) {
        MeteoThread.stopped = stopped;
    }
}
