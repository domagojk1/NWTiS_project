/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.dkopic2.data.DataHolder;
import org.foi.nwtis.dkopic2.data.JMSMail;
import org.foi.nwtis.dkopic2.websocket.WebSocketEnd;

/**
 *
 * @author domagoj
 */
@MessageDriven(mappedName = "jms/NWTiS_dkopic2_1",activationConfig =  {
            @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
            @ActivationConfigProperty(propertyName = "destinationType",propertyValue = "javax.jms.Queue") })
public class MDBMail implements MessageListener {
    
    public MDBMail() {
    }
    
    @Override
    public void onMessage(Message message) {
        try 
        {
            ObjectMessage msg = (ObjectMessage) message;
            JMSMail mail = (JMSMail) msg.getObject();
            DataHolder.addMailMessage(mail);
            WebSocketEnd.onChange("change");
        }   
        catch (JMSException ex)
        {
            Logger.getLogger(MDBMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
