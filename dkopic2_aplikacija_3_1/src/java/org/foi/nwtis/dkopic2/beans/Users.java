/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.foi.nwtis.dkopic2.rest.AddressList;
import org.foi.nwtis.dkopic2.rest.ResourceREST;
import org.foi.nwtis.dkopic2.rest.UserRESTClient;
import org.foi.nwtis.dkopic2.rest.UsersList;
import org.foi.nwtis.dkopic2.soap.MeteoData;
import org.foi.nwtis.dkopic2.soap.SOAPClient;
import org.foi.nwtis.dkopic2.soap.User;

/**
 *
 * @author domagoj
 */
@Named(value = "users")
@SessionScoped
public class Users implements Serializable {
    private AddressList addresses;
    private UsersList users;
    private String address;
    private MeteoData meteo;
    private boolean showMeteo;
    
    /**
     * Creates a new instance of Users
     */
    public Users() {
    }

    public AddressList getAddresses() {
        return addresses;
    }

    public void setAddresses(AddressList addresses) {
        this.addresses = addresses;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public MeteoData getMeteo() {
        return meteo;
    }

    public void setMeteo(MeteoData meteo) {
        this.meteo = meteo;
    }

    public boolean isShowMeteo() {
        return showMeteo;
    }

    public void setShowMeteo(boolean showMeteo) {
        this.showMeteo = showMeteo;
    }

    public UsersList getUsers() {
        try 
        {
            ResourceREST rest = new ResourceREST();
            String xmlString = rest.getXml();
            JAXBContext context = JAXBContext.newInstance(UsersList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(xmlString));
            JAXBElement<UsersList> el = unmarshaller.unmarshal(source,UsersList.class);
            users = (UsersList) el.getValue();
        } 
        catch (JAXBException ex){}
        
        return users;
    }

    public void setUsers(UsersList users) {
        this.users = users;
    }
    
    public void fetchAddrs(int id) {
        try
        {
            UserRESTClient rest = new UserRESTClient(String.valueOf(id));
            String xmlString = rest.getXml();
            JAXBContext context = JAXBContext.newInstance(AddressList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StreamSource source = new StreamSource(new StringReader(xmlString));
            JAXBElement<AddressList> el = unmarshaller.unmarshal(source,AddressList.class);
            addresses = (AddressList) el.getValue();
        } 
        catch (JAXBException ex){}
    }
    
    public void fetchMeteo() {
        if (address != null) 
        {
            String username = "";
            String password = "";
            
            for (User us : users.getUsers())
            {
                if (us.getId() == addresses.getId())
                {
                    username = us.getUsername();
                    password = us.getPassword();
                    break;
                }
            }
            
 
            meteo = SOAPClient.getMeteo(address, username, password);
            showMeteo = true;
        }
    }
}
