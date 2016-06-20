/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.listeners;

import java.util.ArrayList;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 *
 * @author domagoj
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().equals("user") || event.getName().equals("admin")) 
        {
            String user = (String) event.getValue();
            ArrayList<String> activeUsers = (ArrayList<String>) 
                    event.getSession().getServletContext().getAttribute("activeUsers");
            
            if (activeUsers == null)
            {
                activeUsers = new ArrayList<String>();
            }
            
            activeUsers.add(user);
            event.getSession().getServletContext().setAttribute("activeUsers", activeUsers);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
       if (event.getName().equals("user") || event.getName().equals("admin"))
       {
           String user = (String) event.getValue();
           ArrayList<String> activeUsers = (ArrayList<String>) 
                    event.getSession().getServletContext().getAttribute("activeUsers");
           activeUsers.remove(user);
           event.getSession().getServletContext().setAttribute("activeUsers", activeUsers);
       }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }
    
}
