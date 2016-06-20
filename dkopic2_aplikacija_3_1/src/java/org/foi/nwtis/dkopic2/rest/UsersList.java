/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import org.foi.nwtis.dkopic2.soap.User;

/**
 *
 * @author domagoj
 */
@XmlRootElement(name = "active-users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UsersList implements Serializable {
    
    @XmlElementWrapper(name="users")
    private List<User> users;

    public UsersList() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public void addUser(User user) {
        this.users.add(user);
    }
}
