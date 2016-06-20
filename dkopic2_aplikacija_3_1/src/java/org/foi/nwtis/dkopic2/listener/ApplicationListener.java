/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.listener;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.foi.nwtis.dkopic2.data.DataHolder;
import org.foi.nwtis.dkopic2.data.Serializator;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkopic2.konfiguracije.NemaKonfiguracije;

/**
 *
 * @author domagoj
 */
public class ApplicationListener extends HttpServlet {
    private Konfiguracija configuration;
    private String path;
    
    @Override
    public ServletContext getServletContext() {
        return super.getServletContext(); 
    }
    
    @Override
    public void init() throws ServletException {
        path = getServletContext().getRealPath("/WEB-INF") + File.separator;
        String configFile = getServletContext().getInitParameter("konfig_xml");
        
        try 
        {
            configuration = KonfiguracijaApstraktna.preuzmiKonfiguraciju(path + configFile);
            Configuration config = new Configuration(configuration);
        } 
        catch (NemaKonfiguracije ex) 
        {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataHolder holder = new DataHolder();
        
        try
        {    
            DataHolder.setMailMessages(Serializator.deserializeMail(Configuration.getFile1())); 
            System.out.println("Deserialized mails.");
        } 
        catch (IOException ex)
        {
            System.out.println("File " + Configuration.getFile1() + " doesn't exists.");
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        }
        
        try 
        {  
            DataHolder.setAddressMessages(Serializator.deserializeAddress(Configuration.getFile2()));
            System.out.println("Deserialized addresses.");
            
        }
        catch (IOException ex)
        {
            System.out.println("File " + Configuration.getFile2() + " doesn't exists.");
        }
        catch (ClassNotFoundException ex)
        {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    @Override
    public void destroy() {
        try 
        {
            Serializator.serializeMail(Configuration.getFile1(), DataHolder.getMailMessages());
            Serializator.serializeAddress(Configuration.getFile2(), DataHolder.getAddressMessages());
        }
        catch (IOException ex)
        {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}
