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
import org.foi.nwtis.dkopic2.entity.User;

/**
 *
 * @author domagoj
 */
@Named(value = "registration")
@SessionScoped
public class Registration implements Serializable {
    private String name;
    private String username;
    private String surname;
    private String password;
    private String passwordCheck;
    private String mail;
    private String role;

    private String message;

    /**
     * Creates a new instance of Registration
     */
    public Registration() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private boolean checkEmpty() {
        if (username == null || surname == null || password == null || passwordCheck == null || mail == null || role == null)
            return false;
        return true;
    }

    public String register() {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String register = "";
        message = "";

        if (!checkEmpty())
        {
            message = "Polja ne mogu biti prazna.";
            if (locale.equals(Locale.ENGLISH))
                message = "All fields must be filled.";
            
            register = "ERROR";
        }
        else
        {
            User user = EJBSource.getUserFacade().fetchUser(username);
            User userMail = EJBSource.getUserFacade().fetchUserMail(mail);
            
            if (user != null)
            {
                if (locale.equals(Locale.ENGLISH))
                    message += "\n User with username" + username + " already exists.";
                else
                    message += "\n korisnik " + username + " već postoji.";
                
                register = "ERROR";
            }
            else if (userMail != null)
            {
                if (locale.equals(Locale.ENGLISH))
                    message += "\n User with mail " + mail + " already exists.";
                else
                    message += "\n Korisnik sa mailom " + mail + " već postoji.";
                
                register = "ERROR";
            }
            else
            {
                if (! password.equals(passwordCheck))
                {
                    if (locale.equals(Locale.ENGLISH))
                        message += "\n Passwords must be same.";
                    else
                        message += "\n Lozinke moraju odgovarati.";
                    
                    register = "ERROR";
                }
                else
                {
                    User newUser = new User();
                    newUser.setIme(name);
                    newUser.setPrezime(surname);
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setStatus(0);
                    newUser.setUloga(role);
                    newUser.setMail(mail);
                    EJBSource.getUserFacade().create(newUser);
                    
                    register = "OK";
                }
            }
        }
        
        return register;
    }
}
