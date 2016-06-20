/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.entity.User;


/**
 *
 * @author domagoj
 */

public class UserService {
    private User user;
    
    public UserService(String username, String password) {
        this.user = EJBSource.getUserFacade().fetchUser(username, password);
        System.out.println(username + " " + password);
    }
    
    public boolean manageStatus() {
        if(! isApproved())
        {
            user.setStatus(1);
            EJBSource.getUserFacade().edit(user);
            return true;
        }
        return false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isApproved() {
        if (user.getStatus() == 0)
        {
            return false;
        }
        return true;
    }
}
