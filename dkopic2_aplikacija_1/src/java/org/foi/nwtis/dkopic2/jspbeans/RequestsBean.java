/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.jspbeans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.dkopic2.log.LogDB;
import org.foi.nwtis.dkopic2.log.RequestLog;
import org.foi.nwtis.dkopic2.util.Configuration;

/**
 *
 * @author domagoj
 */
public class RequestsBean {
    private ArrayList<RequestLog> logs;
    private HttpServletRequest request;
    private int lines;

    public RequestsBean() {
    }

    public ArrayList<RequestLog> getLogs() throws ParseException {
        String address = "";
        String dateStart = "";
        String dateEnd = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        
        logs = LogDB.getRequests();
        
        if (request.getParameter("submit") != null)
        {
            if (request.getParameter("address") != "")
            {
                address = request.getParameter("address");
                
                for (Iterator<RequestLog> iterator = logs.iterator(); iterator.hasNext();)
                {
                    String addr = iterator.next().getAddress();
                    
                    if (! address.equals(addr))
                        iterator.remove();
                }
            }
            
            if (request.getParameter("dateStart") != "")
            {
                dateStart = request.getParameter("dateStart");
                Date start = formatter.parse(dateStart);
                
                for (Iterator<RequestLog> iterator = logs.iterator(); iterator.hasNext();)
                {
                    long date = iterator.next().getTime().getTime();
                    
                    if (date <= start.getTime())
                        iterator.remove();
                }
            }
            
            if (request.getParameter("dateEnd") != "")
            {
                dateEnd = request.getParameter("dateEnd");
                Date end = formatter.parse(dateEnd);
                
                for (Iterator<RequestLog> iterator = logs.iterator(); iterator.hasNext();)
                {
                    long date = iterator.next().getTime().getTime();
                    
                    if (date >= end.getTime())
                        iterator.remove();
                }
            }
            
        }
        
        return logs;
    }

    public void setLogs(ArrayList<RequestLog> logs) {
        this.logs = logs;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public int getLines() {
        return Configuration.getLines();
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}
