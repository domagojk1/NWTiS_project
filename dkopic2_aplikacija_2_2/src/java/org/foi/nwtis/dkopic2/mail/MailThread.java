/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.mail;

import org.foi.nwtis.dkopic2.mail.MailService;

/**
 *
 * @author domagoj
 */
public class MailThread extends Thread{
    private int interval;
    private MailService mail;

    public MailThread(int interval) {
        this.interval = interval;
        mail = MailService.getInstance();
    }
    
    @Override
    public void run() {
        try
        {
            while (!Thread.interrupted())
            {
                mail.proccess();
                Thread.sleep(interval * 1000);
            }
        }
        catch (InterruptedException ex){}
    }
}
