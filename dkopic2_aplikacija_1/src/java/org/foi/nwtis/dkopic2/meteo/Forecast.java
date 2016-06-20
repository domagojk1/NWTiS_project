/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.meteo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author domagoj
 */
public class Forecast implements Serializable {
    private List<MeteoData> forecast;

    public Forecast(List<MeteoData> forecast) {
        this.forecast = forecast;
    }
    
    public void setForecast(List<MeteoData> forecast) {
        this.forecast = forecast;
    }

    public List<MeteoData> getForecast() {
        return forecast;
    }
    
}
