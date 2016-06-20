/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.listener.ApplicationListener;

/**
 *
 * @author domagoj
 */
public class ExecutorManager {
    private static ExecutorService executor;
    private static ExecutorManager instance;
    
    private ExecutorManager() {
        executor = Executors.newFixedThreadPool(Configuration.getThreadNumber());
    }
    
    public static ExecutorManager getInstance() {
        if (instance == null) 
        {
            instance = new ExecutorManager();
        }
        return instance;
    }
    
    public void submit(Thread thread) {
        executor.submit(thread);
    }
    
    public void shutDown() {
        try 
        {
            executor.shutdown();
            
            if (executor.awaitTermination(10, TimeUnit.SECONDS))
            {
                System.out.println("Tasks completed");
            }
            else
            {
                System.out.println("Forcing shutdown...");
                executor.shutdownNow();
            }
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
