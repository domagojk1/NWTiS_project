/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.mail;


import org.foi.nwtis.dkopic2.data.JMSAddress;
import org.foi.nwtis.dkopic2.data.JMSMail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author domagoj
 */
public class JMSService {
    private static final String CONNECTION_FACTORY = "java:comp/env/jms/NWTiS_QF_dkopic2";
    private static final String QUEUE_1 = "java:comp/env/jms/NWTiS_dkopic2_1";
    private static final String QUEUE_2 = "java:comp/env/jms/NWTiS_dkopic2_2";
    
    public static void sendToQueue_1(JMSMail message) {
        Context context = null;
        Connection conn = null;
        Session session = null;
        ConnectionFactory factory = null;
        
        try 
        {
            context = new InitialContext();
            factory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
            conn = factory.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
         
            Destination destination = (Destination) context.lookup(QUEUE_1);
            MessageProducer producer = session.createProducer(destination);

            ObjectMessage objectMessage = session.createObjectMessage(message);
            producer.send(objectMessage);
        }
        catch (NamingException | JMSException ex)
        {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (session != null)
            {
                try 
                {
                    session.close();
                }
                catch (JMSException ex)
                {
                    Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null)
            {
                try 
                {
                    conn.close();
                }
                catch (JMSException ex) 
                {
                    Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static void sendToQueue_2(JMSAddress message) {
        Context context = null;
        Connection conn = null;
        Session session = null;
        ConnectionFactory factory = null;
        
        try 
        {
            context = new InitialContext();
            factory = (ConnectionFactory) context.lookup(CONNECTION_FACTORY);
            conn = factory.createConnection();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
         
            Destination destination = (Destination) context.lookup(QUEUE_2);
            MessageProducer producer = session.createProducer(destination);

            ObjectMessage objectMessage = session.createObjectMessage(message);
            producer.send(objectMessage);
        }
        catch (NamingException | JMSException ex)
        {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (session != null)
            {
                try 
                {
                    session.close();
                }
                catch (JMSException ex)
                {
                    Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null)
            {
                try 
                {
                    conn.close();
                }
                catch (JMSException ex) 
                {
                    Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
