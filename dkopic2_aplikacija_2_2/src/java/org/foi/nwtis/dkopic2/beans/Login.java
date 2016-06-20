/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.dkopic2.entity.User;

/**
 *
 * @author domagoj
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    private String username;
    private String password;
    private String message;
    
    private HttpSession session;
    private FacesContext context;
    /**
     * Creates a new instance of Login
     */
    public Login() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession) context.getExternalContext().getSession(true);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private boolean checkEmpty() {
        if (username.isEmpty() || password.isEmpty())
            return false;
        return true;
    }
    
    private boolean existsUser() {
        if (session.getAttribute("user") != null)
            return true;
        return false;
    }
    
    private boolean existsAdmin() {
        if (session.getAttribute("admin") != null)
            return true;
        return false;
    }
   
    public String login() {
        String login = "ERROR";
        message = "";
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

        if (existsUser()) 
        {
            login = "USER";
        }
        else if (existsAdmin())
        {
            login = "ADMIN";
        }
        else 
        {
            if (checkEmpty())
            {
                User user = EJBSource.getUserFacade().fetchUser(username, password);
                if (user != null)
                {
                    if (user.getStatus() == 0)
                    {
                        login = "ERROR";

                        message = "Korisnički račun nije odobren od administratora.";
                        if (locale.equals(Locale.ENGLISH))
                            message = "User account is not approved by administrator.";
                    }
                    else if (user.getStatus() == 3)
                    {
                        login = "ERROR";

                        message = "Korisnički račun je odbijen.";
                        if (locale.equals(Locale.ENGLISH))
                            message = "User account rejected";
                    }
                    else 
                    {
                        if (user.getUloga().equals("Administrator"))
                        {
                            session.setAttribute("admin", username);
                            login = "ADMIN";
                        }
                        else 
                        {
                            session.setAttribute("user", username); 
                            login = "USER";
                        }
                    }   
                }
                else
                {
                    message = "Krivo korisničko ime/lozinka.";
                    if (locale.equals(Locale.ENGLISH))
                        message = "Wrong username/password.";
                }

            }
            else
            {
                message = "Polja ne mogu biti prazna.";
                if (locale.equals(Locale.ENGLISH))
                    message = "Fields cannot be empty";
            }   
        }
           
        System.out.println(login);
        return login;
    }
    
    public String logout() {
        if (session.getAttribute("user") != null)
            session.removeAttribute("user");
        if (session.getAttribute("admin") != null)
            session.removeAttribute("admin");
        return "LOGOUT";
    }
}
