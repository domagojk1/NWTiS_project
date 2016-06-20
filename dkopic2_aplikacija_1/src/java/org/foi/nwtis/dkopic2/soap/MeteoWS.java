/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.soap;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.dkopic2.location.Address;
import org.foi.nwtis.dkopic2.location.AddressDB;
import org.foi.nwtis.dkopic2.location.Rank;
import org.foi.nwtis.dkopic2.log.LogDB;
import org.foi.nwtis.dkopic2.log.WebServiceLog;
import org.foi.nwtis.dkopic2.meteo.MeteoDB;
import org.foi.nwtis.dkopic2.meteo.MeteoData;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;

/**
 *
 * @author domagoj
 */
@WebService(serviceName = "MeteoWS")
public class MeteoWS {

    @Resource
    WebServiceContext context;
   
    private void insertLog(String action, int status, User user, long duration) {
        HttpServletRequest request = (HttpServletRequest)context.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        String ip = request.getRemoteAddr();
        String url = request.getRequestURL().toString() + "?method=" + request.getMethod() + "&action=" + action;
       
        WebServiceLog log = new WebServiceLog();
        log.setUser(user);
        log.setIp(ip);
        log.setUrl(url);
        log.setStatus(status);
        log.setTime(new Date());
        log.setDuration(duration);
        LogDB.insert(log);
    }
   
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getMeteo")
    public MeteoData getMeteo(@WebParam(name = "address") String address, @WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        MeteoData data = null;
        User user = UserDB.getUser(username, password);
        long start = System.currentTimeMillis();
        int status = 0;
       
        Address addr = AddressDB.getAddress(address);
       
        if (addr != null)
        {
            if (user != null)
            {
                if (user.isAdmin())
                {
                    data = MeteoDB.getMeteo(addr.getId());
                    status = 1;
                }
                else if (user.checkRequests())
                {
                    UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                    data = MeteoDB.getMeteo(addr.getId());
                    status = 1;
                }
            }
        }
 
        long end = System.currentTimeMillis();
        insertLog("getMeteo", status, user, end - start);
       
        return data;
    }
 
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAddressList")
    public List<Address> getAddressList(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        List<Address> list = null;
        User user = UserDB.getUser(username, password);
        long start = System.currentTimeMillis();
        int status = 0;
       
        if (user != null)
        {
            if (user.isAdmin())
            {
                list = AddressDB.getUsers(user.getId());
                status = 1;
            }  
            else if (user.checkRequests())
            {
                UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                list = AddressDB.getUsers(user.getId());
                status = 1;
            }
        }
       
        long end = System.currentTimeMillis();
        insertLog("getAddressList", status, user, end - start);
             
        return list;
    }
 
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getLast")
    public List<MeteoData> getLast(@WebParam(name = "n") int n, @WebParam(name = "address") String address, @WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        List<MeteoData> list = null;
        User user = UserDB.getUser(username, password);
        Address addr = AddressDB.getAddress(address);
        long start = System.currentTimeMillis();
        int status = 0;
       
        if (addr != null)
        {
            if (user != null)
            {
                if (user.isAdmin())
                {
                    list = MeteoDB.getLast(addr.getId(), n);
                    status = 1;
                }
                if (user.checkRequests())
                {
                    UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                    list = MeteoDB.getLast(addr.getId(), n);
                    status = 1;
                }
            }
        }
       
        long end = System.currentTimeMillis();
        insertLog("getLast", status, user, end - start);
       
        return list;
    }
 
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getInterval")
    public List<MeteoData> getInterval(@WebParam(name = "address") String address, @WebParam(name = "startDate") String startDate, @WebParam(name = "endDate") String endDate, @WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        List<MeteoData> list = null;
        User user = UserDB.getUser(username, password);
        Address addr = AddressDB.getAddress(address);
        long start = System.currentTimeMillis();
        int status = 0;
       
        if (addr != null)
        {
            if (user != null)
            {
                if (user.isAdmin())
                {
                    list = MeteoDB.getMeteo(addr.getId(), startDate, endDate);
                    status = 1;
                }
                else if (user.checkRequests())
                {
                    UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                    list = MeteoDB.getMeteo(addr.getId(), startDate, endDate);
                    status = 1;
                }
            }
        }
       
        long end = System.currentTimeMillis();
        insertLog("getInterval", status, user, end - start);
       
        return list;
    }
 
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getRank")
    public List<Rank> getRank(@WebParam(name = "n") int n, @WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        List<Rank> rankList = null;
        User user = UserDB.getUser(username, password);
        long start = System.currentTimeMillis();
        int status = 0;
       
        if (user != null)
        {
            if (user.isAdmin())
            {
                rankList = AddressDB.getRankList(n);
                status = 1;
            }
            else if (user.checkRequests())
            {
                UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                rankList = AddressDB.getRankList(n);
                status = 1;
            }
        }
       
        long end = System.currentTimeMillis();
        insertLog("getRank", status, user, end - start);
 
        return rankList;
    }
}
