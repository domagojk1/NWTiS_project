/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.jspbeans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.foi.nwtis.dkopic2.log.WebServiceLog;
import org.foi.nwtis.dkopic2.log.LogDB;
import org.foi.nwtis.dkopic2.util.Configuration;

/**
 *
 * @author domagoj
 */
public class LogBean implements Serializable {
    private ArrayList<WebServiceLog> logs;
    private int lines;
    private HttpServletRequest request;

    public LogBean() {
    }

    public ArrayList<WebServiceLog> getLogs() throws ParseException {
        String serviceType = "";
        String serviceName = "";
        String dateStart = "";
        String dateEnd = "";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        
        logs = LogDB.getLogs();
        
        if (request.getParameter("submit") != null)
        {
            if (request.getParameter("serviceType") != "")
            {
                serviceType = request.getParameter("serviceType");
                
                for (Iterator<WebServiceLog> iterator = logs.iterator(); iterator.hasNext();)
                {
                    String url = iterator.next().getUrl();
                    
                    if (! url.contains(serviceType))
                        iterator.remove();
                }
            }
            
            if (request.getParameter("serviceName") != "")
            {
                serviceName = request.getParameter("serviceName");
                
                for (Iterator<WebServiceLog> iterator = logs.iterator(); iterator.hasNext();)
                {
                    String url = iterator.next().getUrl();
                    
                    if (! url.contains(serviceName))
                        iterator.remove();
                }
            }
            
            if (request.getParameter("dateStart") != "")
            {
                dateStart = request.getParameter("dateStart");
                Date start = formatter.parse(dateStart);
                
                for (Iterator<WebServiceLog> iterator = logs.iterator(); iterator.hasNext();)
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
                
                for (Iterator<WebServiceLog> iterator = logs.iterator(); iterator.hasNext();)
                {
                    long date = iterator.next().getTime().getTime();
                    
                    if (date >= end.getTime())
                        iterator.remove();
                }
            }
        }
        
        return logs;
    }

    public void setLogs(ArrayList<WebServiceLog> logs) {
        this.logs = logs;
    }

    public int getLines() {
        return Configuration.getLines();
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
    
    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }   
}
