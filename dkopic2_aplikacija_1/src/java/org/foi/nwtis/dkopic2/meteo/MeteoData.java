/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.meteo;

import java.io.Serializable;
import java.util.Date;
import org.foi.nwtis.dkopic2.location.Address;

/**
 * Klasa za spremanje podataka o vremenskoj prognozi.
 * @author domagoj
 */
public class MeteoData implements Serializable {
    private Address address;
    private Double temperatureValue;
    private Double temperatureMin;
    private Double temperatureMax;
    private Double humidityValue;
    private Double pressureValue;
    private Double windSpeedValue;
    private Double windDirectionValue;
    private String visibility;
    private String weatherValue;
    private Date lastUpdate;
    private Date downloadTime;

    public MeteoData(Address address, Double temperatureMin, Double temperatureMax, Double humidityValue, Double pressureValue, Double windSpeedValue, Double windDirectionValue, String visibility, String weatherValue, Date lastUpdate, Date downloadTime) {
        this.address = address;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.humidityValue = humidityValue;
        this.pressureValue = pressureValue;
        this.windSpeedValue = windSpeedValue;
        this.windDirectionValue = windDirectionValue;
        this.visibility = visibility;
        this.weatherValue = weatherValue;
        this.lastUpdate = lastUpdate;
        this.downloadTime = downloadTime;
    }

    public MeteoData() {
    }

    public Double getTemperatureValue() {
        return temperatureValue;
    }

    public void setTemperatureValue(Double temperatureValue) {
        this.temperatureValue = temperatureValue;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public Double getHumidityValue() {
        return humidityValue;
    }

    public void setHumidityValue(Double humidityValue) {
        this.humidityValue = humidityValue;
    }

    public Double getPressureValue() {
        return pressureValue;
    }

    public void setPressureValue(Double pressureValue) {
        this.pressureValue = pressureValue;
    }

    public Double getWindSpeedValue() {
        return windSpeedValue;
    }

    public void setWindSpeedValue(Double windSpeedValue) {
        this.windSpeedValue = windSpeedValue;
    }

    public Double getWindDirectionValue() {
        return windDirectionValue;
    }

    public void setWindDirectionValue(Double windDirectionValue) {
        this.windDirectionValue = windDirectionValue;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWeatherValue() {
        return weatherValue;
    }

    public void setWeatherValue(String weatherValue) {
        this.weatherValue = weatherValue;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }
    
}
