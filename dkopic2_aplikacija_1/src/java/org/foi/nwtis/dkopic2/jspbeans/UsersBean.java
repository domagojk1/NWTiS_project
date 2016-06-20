/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.jspbeans;


import java.io.Serializable;
import java.util.ArrayList;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;

/**
 *
 * @author domagoj
 */
public class UsersBean implements Serializable {
    private ArrayList<User> users;
    
    public UsersBean() {
    }

    public ArrayList<User> getUsers() {
        return UserDB.getUsers();
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
