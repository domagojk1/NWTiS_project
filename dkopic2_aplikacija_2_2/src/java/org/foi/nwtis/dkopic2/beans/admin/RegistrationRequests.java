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
import java.util.List;
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
@Named(value = "registrationRequests")
@SessionScoped
public class RegistrationRequests implements Serializable {
    private List<User> pending;
    private String response;
    private boolean showResponse;
    private HttpSession session;
    private FacesContext context;
    
    /**
     * Creates a new instance of RegistrationRequest
     */
    public RegistrationRequests() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
    }

    public List<User> getPending() {
        pending = EJBSource.getUserFacade().fetchPending();
        return pending;
    }

    public void setPending(List<User> pending) {
        this.pending = pending;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isShowResponse() {
        return showResponse;
    }

    public void setShowResponse(boolean showResponse) {
        this.showResponse = showResponse;
    }

    public void approveRequest(User user) throws IOException {
        String username = (String) session.getAttribute("admin");
        User admin = EJBSource.getUserFacade().fetchUser(username);

        String role = "";
        if (user.getUloga().equals("Administrator"))
        {
            role = "ADMIN";
        }
        else
        {
            role = "USER";
        }

        String command = "USER " + admin.getUsername() + "; " + "PASSWD "  + admin.getPassword() + "; "
                + "ADD " + user.getUsername() + "; PASSWD " + user.getPassword() + "; ROLE " + role + ";";

        SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
        handler.connect();
        handler.sendOutput(command);
        response = handler.getInput();
        handler.closeInput();
        handler.closeOutput();
        
        pending = EJBSource.getUserFacade().fetchPending();
    }
    
    public void rejectRequest(User user) {
        user.setStatus(3);
        EJBSource.getUserFacade().edit(user);
        pending = EJBSource.getUserFacade().fetchPending();
    }
}
