/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.dkopic2.threads.RequestThread;

/**
 *
 * @author domagoj
 */
public class Mail {
    public static void send(String message) {
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", Configuration.getHost());
        
        Session session = Session.getDefaultInstance(props);
        
        try 
        {
            MimeMessage mime = new MimeMessage(session);
            mime.setFrom(new InternetAddress(Configuration.getSender()));
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(Configuration.getReceiver()));
            mime.setSubject(Configuration.getSubject());
            mime.setText(message);
            
            Transport.send(mime);
            System.out.println("Mail :" + message);
        }
        catch (MessagingException ex) 
        {
            Logger.getLogger(RequestThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
