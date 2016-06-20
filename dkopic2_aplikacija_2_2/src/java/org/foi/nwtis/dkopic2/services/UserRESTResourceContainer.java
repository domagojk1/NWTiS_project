/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.services;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.entity.User;

/**
 * REST Web Service
 *
 * @author domagoj
 */
@Path("/user")
@RequestScoped
public class UserRESTResourceContainer {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserRESTResourceContainer
     */
    public UserRESTResourceContainer() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String response = "";
        
        if (session != null)
        {
            if (session.getServletContext().getAttribute("activeUsers") != null)
            {
                ArrayList<String> users = (ArrayList) session.getServletContext().getAttribute("activeUsers");
                UsersList list = new UsersList();
                
                for (String us : users)
                {
                    User user = EJBSource.getUserFacade().fetchUser(us);
                    list.addUser(user);
                }
                
                try 
                {
                    JAXBContext jc = JAXBContext.newInstance(UsersList.class);
                    Marshaller marshaller = jc.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter writer = new StringWriter();
                    marshaller.marshal(list, writer);
                    response = writer.toString();
                } 
                catch (JAXBException ex)
                {
                    Logger.getLogger(UserRESTResource.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return response;
    }

    /**
     * POST method for creating an instance of UserRESTResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response postXml(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public UserRESTResource getUserRESTResource(@PathParam("id") String id) {
        return UserRESTResource.getInstance(id);
    }
}
