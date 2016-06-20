/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.listeners;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.foi.nwtis.dkopic2.beans.EJBSource;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkopic2.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dkopic2.mail.MailThread;
import org.foi.nwtis.dkopic2.session.AddressFacade;
import org.foi.nwtis.dkopic2.session.LogFacade;
import org.foi.nwtis.dkopic2.session.UserFacade;

/**
 *
 * @author domagoj
 */
public class ApplicationListener extends HttpServlet {

    @EJB
    private LogFacade logFacade;

    @EJB
    private UserFacade userFacade;
    
    @EJB
    private AddressFacade addressFacade;

    private Konfiguracija configuration;
    private MailThread mailThread;
    
    @Override
    public ServletContext getServletContext() {
        return super.getServletContext(); 
    }
    
    @Override
    public void init() throws ServletException {
        
        String path = getServletContext().getRealPath("/WEB-INF") + File.separator;
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
        
        EJBSource.initialize(userFacade, logFacade, addressFacade);  
        
        mailThread = new MailThread(Configuration.getThreadInterval());
        mailThread.start(); 
    }
   
    @Override
    public void destroy() {
        if (mailThread != null)
            mailThread.interrupt();
    }  
}
