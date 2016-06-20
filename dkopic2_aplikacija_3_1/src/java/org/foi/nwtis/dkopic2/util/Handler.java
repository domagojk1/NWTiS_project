/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.dkopic2.data.JMSAddress;

/**
 *
 * @author domagoj
 */
public class Handler {
    private static boolean checkAddress(JMSAddress message) {
        boolean ok = false;
        
        String command = "USER " + message.getUsername() 
                + "; PASSWD " + message.getPassword()
                + "; TEST " + message.getAddress() + ";";
        
        SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
        try 
        {
            handler.connect();
            handler.sendOutput(command);
            String response = handler.getInput();
            System.out.println(response);
            
            if (! response.equals("OK 10"))
            {
                ok = true;
            }
            
            handler.closeInput();
            handler.closeOutput();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ok;
    }
    
    public static String procAddress(JMSAddress message) {
        String response = "";
        
        if (checkAddress(message))
        {
            String command = "USER " + message.getUsername() 
                + "; PASSWD " + message.getPassword() 
                + "; ADD " + message.getAddress() + ";";
            
            SocketHandler handler = new SocketHandler(new Socket(), Configuration.getHost(), Configuration.getServerPort());
            try 
            {
                handler.connect();
                handler.sendOutput(command);
                System.out.println(response);
                handler.closeInput();
                handler.closeOutput();
            }
            catch (IOException ex)
            {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return response;
    }
}
