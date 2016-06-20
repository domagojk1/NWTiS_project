/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans.user;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.entity.User;
import org.foi.nwtis.dkopic2.location.GMClient;
import org.foi.nwtis.dkopic2.data.JMSAddress;
import org.foi.nwtis.dkopic2.mail.JMSService;
import org.foi.nwtis.dkopic2.services.JSONParser;
import org.foi.nwtis.dkopic2.services.RESTClient;
import org.foi.nwtis.dkopic2.services.RESTResClient;
import org.foi.nwtis.dkopic2.services.SOAPClient;
import org.foi.nwtis.dkopic2.soap.Address;
import org.foi.nwtis.dkopic2.soap.Geolocation;
import org.foi.nwtis.dkopic2.soap.MeteoData;

/**
 *
 * @author domagoj
 */
@Named(value = "addresses")
@SessionScoped
public class Addresses implements Serializable {

    private HttpSession session;
    private FacesContext context;
    private String username;
    private String password;
    
    private List<Address> list;
    private boolean usersAddresses;
    private String newAddress;
    private User user;
    private String response;
    private String chosenAddress;
    
    private String meteoNumber;
    private boolean showMeteo;
    private boolean showForecast;
    private boolean showDeviation;
    private List<MeteoData> meteoList;
    private List<MeteoData> forecastList;
    private List<MeteoData> deviation;
    private String startDate,endDate;
        
    /**
     * Creates a new instance of Addresses
     */
    public Addresses() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(false);
    }
    
    public List<Address> getList() {
        if (session != null)
        {
            if (session.getAttribute("user") != null)
            {
                username = (String) session.getAttribute("user");
            }
            else if (session.getAttribute("admin") != null)
            {
                username = (String) session.getAttribute("admin");
            }
            
            user = EJBSource.getUserFacade().fetchUser(username);
            password = user.getPassword();
            RESTResClient cl = new RESTResClient();
            
            list = JSONParser.getAddresses(cl.getJson(password, username));
        }
        
        if (usersAddresses) 
        {
            list = SOAPClient.getAddressList(username, password);
        }
       
        return list;
    }

    public String getChosenAddress() {
        return chosenAddress;
    }

    public void setChosenAddress(String chosenAddress) {
        this.chosenAddress = chosenAddress;
    }

    public void setList(List<Address> list) {
        this.list = list;
    }

    public String getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
    
    public void showUsers() {
        usersAddresses = true;
    }
    
    public void showAll() {
        usersAddresses = false;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMeteoNumber() {
        return meteoNumber;
    }

    public void setMeteoNumber(String meteoNumber) {
        this.meteoNumber = meteoNumber;
    }

    public boolean isShowMeteo() {
        return showMeteo;
    }

    public void setShowMeteo(boolean showMeteo) {
        this.showMeteo = showMeteo;
    }

    public List<MeteoData> getMeteoList() {
        return meteoList;
    }

    public void setMeteoList(List<MeteoData> meteoList) {
        this.meteoList = meteoList;
    }

    public List<MeteoData> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<MeteoData> forecastList) {
        this.forecastList = forecastList;
    }

    public boolean isShowForecast() {
        return showForecast;
    }

    public void setShowForecast(boolean showForecast) {
        this.showForecast = showForecast;
    }

    public List<MeteoData> getDeviation() {
        return deviation;
    }

    public void setDeviation(List<MeteoData> deviation) {
        this.deviation = deviation;
    }
    
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isShowDeviation() {
        return showDeviation;
    }

    public void setShowDeviation(boolean showDeviation) {
        this.showDeviation = showDeviation;
    }
    
    public void addNew() throws IOException {
        if (newAddress != null)
        {
            /*
            response = "";
            String command = "USER " + user.getUsername() + "; " + "PASSWD "  + user.getPassword() + "; "
                    + "ADD " + newAddress + ";";
            SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
            handler.connect();
            handler.sendOutput(command);
            response = handler.getInput();
            handler.closeInput();
            handler.closeOutput();
            */
            
            JMSAddress jms = new JMSAddress();
            jms.setAddress(newAddress);
            jms.setUsername(username);
            jms.setPassword(password);
            JMSService.sendToQueue_2(jms);
           
            GMClient client = new GMClient();
            Geolocation loc = client.getGeoLocation(newAddress);
            User user = EJBSource.getUserFacade().fetchUser(username);
           
            org.foi.nwtis.dkopic2.entity.Address address = new org.foi.nwtis.dkopic2.entity.Address();
            address.setAdresa(newAddress);
            address.setKorisnik(user);
            address.setLatitude(loc.getLatitude());
            address.setLongitude(loc.getLongitude());
            EJBSource.getAddressFacade().create(address);
            
            newAddress = "";
            getList();
        }
    }
    
    public void fetchMeteo() {
        if (meteoNumber != null && chosenAddress != null) 
        {
            meteoList = SOAPClient.getLast(Integer.parseInt(meteoNumber), chosenAddress, username, password);
            makeMeteoVisible();
        }
    }
    
    private int getId(String addr) {
        int id = 0;
        
        for (Address address : list)
        {
            if (address.getAddress().equals(addr))
            {
                id = address.getId();
                break;
            }
        }
        
        return id;
    }
    
    public void fetchForecast() {
        int id = 0;
        
        if (chosenAddress != null)
        {
            RESTClient client = new RESTClient(String.valueOf(getId(chosenAddress)));
            String json = client.getJson(username, password);
            forecastList = JSONParser.getForecast(json);
        }
        makeForecastVisible();
    }
    
    private MeteoData getAverage(List<MeteoData> list) {
        MeteoData meteo = null;
        double tempSum = 0;
        double tempMinSum = 0;
        double tempMaxSum = 0;
        double humiditySum = 0;
        double pressureSum = 0;
        double windSpeedSum = 0;
        
        for (MeteoData data : list)
        {
            tempSum += data.getTemperatureValue();
            tempMinSum += data.getTemperatureMin();
            tempMaxSum += data.getTemperatureMax();
            humiditySum += data.getHumidityValue();
            pressureSum += data.getPressureValue();
            windSpeedSum += data.getWindSpeedValue();
        }
        
        meteo = new MeteoData();
        meteo.setTemperatureValue(tempSum / list.size());
        meteo.setTemperatureMin(tempMinSum / list.size());
        meteo.setTemperatureMax(tempMaxSum / list.size());
        meteo.setHumidityValue(humiditySum / list.size());
        meteo.setPressureValue(pressureSum / list.size());
        meteo.setWindSpeedValue(windSpeedSum / list.size());
        
        return meteo;
    }
    
    public void fetchDeviation() {
        if (chosenAddress != null && startDate != null && endDate != null)
        {
            RESTClient client = new RESTClient(String.valueOf(getId(chosenAddress)));
            System.out.println(String.valueOf(getId(chosenAddress)));
            String meteoJson = client.getJson(username, password, startDate, endDate);
            List<MeteoData> mList = JSONParser.getForecast(meteoJson);
            List<MeteoData> fList = SOAPClient.getInterval(chosenAddress, startDate, endDate, username, password);
            
            MeteoData meteo = getAverage(mList);
            MeteoData forecast = getAverage(fList);
            
            deviation = new ArrayList<>();
            MeteoData data = new MeteoData();
            Address addr = new Address();
            addr.setAddress(chosenAddress);
            data.setAddress(addr);
            data.setTemperatureValue(Math.abs(forecast.getTemperatureValue() - meteo.getTemperatureValue()));
            data.setTemperatureMin(Math.abs(forecast.getTemperatureMin() - meteo.getTemperatureMin()));
            data.setTemperatureMax(Math.abs(forecast.getTemperatureMax() - meteo.getTemperatureMax()));
            data.setHumidityValue(Math.abs(forecast.getHumidityValue() - meteo.getHumidityValue()));
            data.setWindSpeedValue(Math.abs(forecast.getWindSpeedValue() - meteo.getWindSpeedValue()));
            data.setPressureValue(Math.abs(forecast.getPressureValue()- meteo.getPressureValue()));
            deviation.add(data);
            
            meteoList = new ArrayList<>(mList);
            forecastList = new ArrayList<>(fList);
            makeDevVisible();
        }
    }
    
    private void makeDevVisible() {
        showMeteo = true;
        showForecast = true;
        showDeviation = true;
    }
    
    private void makeForecastVisible() {
        showDeviation = false;
        showMeteo = false;
        showForecast = true;
    }
    
    private void makeMeteoVisible() {
        showDeviation = false;
        showForecast = false;
        showMeteo = true;
    }
}
