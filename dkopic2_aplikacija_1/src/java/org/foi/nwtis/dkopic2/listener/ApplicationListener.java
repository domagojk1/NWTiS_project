/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.listener;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.util.DBConnection;
import org.foi.nwtis.dkopic2.konfiguracije.Konfiguracija;
import org.foi.nwtis.dkopic2.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.dkopic2.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.dkopic2.threads.ServerThread;
import org.foi.nwtis.dkopic2.threads.MeteoThread;
import org.foi.nwtis.dkopic2.threads.ResetThread;
import org.foi.nwtis.dkopic2.util.ExecutorManager;

/**
 *
 * @author domagoj
 */
public class ApplicationListener implements ServletContextListener {
    public static Konfiguracija configuration;
    private static MeteoThread meteo;
    public static ServerThread server;
    private static ResetThread reset;
    
    private ExecutorManager executor;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String path = context.getRealPath("/WEB-INF") + File.separator;
        String configFile = context.getInitParameter("config_xml");
        
        try 
        {    
            configuration = KonfiguracijaApstraktna.preuzmiKonfiguraciju(path + configFile);
            Configuration config = new Configuration(configuration);
        
            meteo = new MeteoThread(Configuration.getThreadInterval());
            server = new ServerThread();
            reset = new ResetThread(Configuration.getUserInterval());
            
            executor = ExecutorManager.getInstance();
            executor.submit(meteo);
            executor.submit(server);
            executor.submit(reset);
        } 
        catch (NemaKonfiguracije ex) 
        {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServerThread.closeSocket();
        DBConnection.getInstance().closeDatasource();
        executor.shutDown();
    }   
}
