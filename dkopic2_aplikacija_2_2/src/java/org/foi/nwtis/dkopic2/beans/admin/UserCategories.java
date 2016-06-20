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
@Named(value = "userCategories")
@SessionScoped
public class UserCategories implements Serializable {

    private List<User> users;
    private String response;
    private HttpSession session;
    private FacesContext context;
    /**
     * Creates a new instance of UserCategories
     */
    public UserCategories() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
    }

    public List<User> getUsers() {
        users = EJBSource.getUserFacade().fetchUsers();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public void increment(User user) throws IOException {
        String username = (String) session.getAttribute("admin");
        User admin = EJBSource.getUserFacade().fetchUser(username);

        String command = "USER " + admin.getUsername() + "; " + "PASSWD "  + admin.getPassword() + "; "
                + "UP " + user.getUsername() + ";";

        SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
        handler.connect();
        handler.sendOutput(command);
        response = handler.getInput();
        handler.closeInput();
        handler.closeOutput();
    }
    
    public void decrement(User user) throws IOException {
        String username = (String) session.getAttribute("admin");
        User admin = EJBSource.getUserFacade().fetchUser(username);

        String command = "USER " + admin.getUsername() + "; " + "PASSWD "  + admin.getPassword() + "; "
                    + "DOWN " + user.getUsername() + ";";

        SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
        handler.connect();
        handler.sendOutput(command);
        response = handler.getInput();
        handler.closeInput();
        handler.closeOutput();
    }
}
