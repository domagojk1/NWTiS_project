/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans.admin;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.net.Socket;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.entity.User;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.util.SocketHandler;

/**
 *
 * @author domagoj
 */
@Named(value = "socket")
@SessionScoped
public class SocketStatus implements Serializable {
    
    private String send;
    private String response;
    private HttpSession session;
    private FacesContext context;
    /**
     * Creates a new instance of Socket
     */
    public SocketStatus() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public FacesContext getContext() {
        return context;
    }

    public void setContext(FacesContext context) {
        this.context = context;
    }
    
    public void execute() throws IOException {
        String username = (String) session.getAttribute("admin");
        User user = EJBSource.getUserFacade().fetchUser(username);
        String commandString = "USER " + user.getUsername() + "; PASSWD " + user.getPassword() + "; " + send + ";";

        SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
        handler.connect();
        handler.sendOutput(commandString);
        response = handler.getInput();
        handler.closeInput();
        handler.closeOutput();
    }
}
