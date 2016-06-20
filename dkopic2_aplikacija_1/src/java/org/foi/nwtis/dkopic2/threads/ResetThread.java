/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.threads;

import org.foi.nwtis.dkopic2.user.UserDB;

/**
 *
 * @author domagoj
 */
public class ResetThread extends Thread{
    private int interval;

    public ResetThread(int interval) {
        this.interval = interval;
    }
   
    @Override
    public void run() {
        try
        {
            while (!Thread.interrupted())
            {
                reset();   
                Thread.sleep(interval * 1000);
            }
        } 
        catch (InterruptedException ex) {}
    }
    
    private synchronized void reset() {
        UserDB.resetRequests();
    }
}
