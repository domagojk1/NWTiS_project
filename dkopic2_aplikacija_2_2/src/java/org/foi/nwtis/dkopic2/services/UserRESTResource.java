/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.services;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.entity.Address;
import org.foi.nwtis.dkopic2.session.UserFacade;
import org.foi.nwtis.dkopic2.entity.User;
import org.foi.nwtis.dkopic2.session.AddressFacade;

/**
 * REST Web Service
 *
 * @author domagoj
 */
@RequestScoped
public class UserRESTResource {
    
    private String id;

    /**
     * Creates a new instance of UserRESTResource
     */
    private UserRESTResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the UserRESTResource
     */
    public static UserRESTResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of UserRESTResource class.
        return new UserRESTResource(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        User user = EJBSource.getUserFacade().fetchUser(Integer.parseInt(id));
        String response = "";
        List<Address> list = EJBSource.getAddressFacade().getAddresses(user);
        List<String> addresses = new ArrayList<>();

        for (Address adr : list)
        {
            addresses.add(adr.getAdresa());
        }

        AddressList addr = new AddressList();
        addr.setAddresses(addresses);
        addr.setUser(user.getUsername());
        addr.setAddresses(addresses);
        addr.setId(user.getId());
        
        try 
        { 
            JAXBContext jc = JAXBContext.newInstance(AddressList.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(addr, writer);
            response = writer.toString();
        } 
        catch (JAXBException ex)
        {
            Logger.getLogger(UserRESTResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return response;
    }
    /**
     * PUT method for updating or creating an instance of UserRESTResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }

    /**
     * DELETE method for resource UserRESTResource
     */
    @DELETE
    public void delete() {
    }
}
