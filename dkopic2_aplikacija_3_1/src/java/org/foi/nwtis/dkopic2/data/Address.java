/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.data;

import org.foi.nwtis.dkopic2.soap.Geolocation;
import org.foi.nwtis.dkopic2.soap.User;



/**
 *
 * @author domagoj
 */
public class Address {
    private int id;
    private String address;
    private Geolocation location;
    private User user;

    public Address(String address, Geolocation location) {
        this.address = address;
        this.location = location;
    }

    public Address(String address) {
        this.address = address;
    }
    
    public Address() {
    }
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Geolocation getLocation() {
        return location;
    }

    public void setLocation(Geolocation location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
