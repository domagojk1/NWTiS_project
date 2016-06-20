/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.threads.RequestThread;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.util.ExecutorManager;

/**
 *
 * @author domagoj
 */
public class ServerThread extends Thread{
    private volatile static boolean stopped;
    private volatile static boolean paused;
    private static ServerSocket server;

    @Override
    public void run() {
        listen();
    }
   
    public void listen() {
        ExecutorManager exec = ExecutorManager.getInstance();
      
        try 
        {
            server = new ServerSocket(Configuration.getServerPort());
        } 
        catch (IOException ex)
        {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server is listening on port: " + Configuration.getServerPort());
        
        try 
        {
            while(!Thread.interrupted())
            {
                if (isStopped()) break;
                Socket client = server.accept();
                exec.submit(new RequestThread(client));
            }
        } 
        catch (Exception ex){}
    }

    public static boolean isStopped() {
        return stopped;
    }

    public static void setStopped(boolean stopped) {
        ServerThread.stopped = stopped;
    }
    
    public static void closeSocket() {
        try 
        {
            server.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
