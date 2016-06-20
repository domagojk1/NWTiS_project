/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans.admin;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.entity.Log;
import org.foi.nwtis.dkopic2.util.Configuration;

/**
 *
 * @author domagoj
 */
@Named(value = "logs")
@SessionScoped
public class Logs implements Serializable {
    
    private ArrayList<Log> logs;
    private String startTime;
    private String endTime;
    private String ip;
    private String user;
    private int rows;
    /**
     * Creates a new instance of Log
     */
    public Logs() {
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<Log> logs) {
        this.logs = logs;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getRows() {
        return Configuration.getLines();
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @PostConstruct
    private void fetchData() {
        logs = new ArrayList<>(EJBSource.getLogFacade().findAll());
    }
    
    private void reset() {
        startTime = endTime = ip = user = "";
    }
    
    public void filter() throws ParseException {
        EJBSource.getLogFacade().init();
        
        if (startTime != null && endTime != null) 
        {
            EJBSource.getLogFacade().filterByTime(startTime, endTime);
        }
        else if (startTime != null)
        {
            EJBSource.getLogFacade().filterByStart(startTime);
        }
        else if (endTime != null) 
        {
            EJBSource.getLogFacade().filterByEnd(endTime);
        }
        if (user != null)
        {
            EJBSource.getLogFacade().filterByUser(user);
        }
        if (ip != null)
        {
            EJBSource.getLogFacade().filterByIp(ip);
        }
        
        logs = new ArrayList<>(EJBSource.getLogFacade().getResult());
        reset();
    }
}
