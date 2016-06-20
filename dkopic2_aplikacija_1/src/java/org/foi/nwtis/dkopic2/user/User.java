/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.user;

import java.io.Serializable;
import org.foi.nwtis.dkopic2.util.Configuration;

/**
 *
 * @author domagoj
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String role;
    private int category;
    private int requests;

    public User(int id, String username, String password, String role, int category, int requests) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.category = category;
        this.requests = requests;
    }

    public User() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }
    
    public boolean isAdmin() {
        if (role.equals("Administrator"))
            return true;
        return false;
    }
    
    public boolean checkRequests() {
        if (getRequests() >= getCategory() * Configuration.getQuota())
            return false;
        return true;
    }
}
