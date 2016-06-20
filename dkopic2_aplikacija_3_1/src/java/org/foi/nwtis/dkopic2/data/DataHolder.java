/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.data;

import java.util.ArrayList;
import org.foi.nwtis.dkopic2.data.JMSAddress;
import org.foi.nwtis.dkopic2.data.JMSMail;

/**
 *
 * @author domagoj
 */
public class DataHolder {
    private static ArrayList<JMSAddress> addressMessages;
    private static ArrayList<JMSMail> mailMessages;
    
    public DataHolder() {
        addressMessages = new ArrayList<>();
        mailMessages = new ArrayList<>();
    }
    
    public static ArrayList<JMSAddress> getAddressMessages() {
        return addressMessages;
    }

    public static void setAddressMessages(ArrayList<JMSAddress> addressMessages) {
        DataHolder.addressMessages = addressMessages;
    }

    public static ArrayList<JMSMail> getMailMessages() {
        return mailMessages;
    }

    public static void setMailMessages(ArrayList<JMSMail> mailMessages) {
        DataHolder.mailMessages = mailMessages;
    }
    
    public static void addAddressMessage(JMSAddress addr) {
        if (addressMessages != null)
            addressMessages.add(addr);
    }
    
    public static void addMailMessage(JMSMail message) {
        if (mailMessages != null)
            mailMessages.add(message);
    }
    
    public static void deleteMail(JMSMail mail) {
        if (mailMessages.contains(mail))
        {
            mailMessages.remove(mail);
        }
    }
    
    public static void deleteAddr(JMSAddress addr) {
        if (addressMessages.contains(addr))
        {
            addressMessages.remove(addr);
        }
    }
    
    public static void deleteAllAddr() {
        if (addressMessages != null)
        {
            addressMessages.clear();
        }
    }
    
    public static void deleteAllMails() {
        if (mailMessages != null) 
        {
            mailMessages.clear();
        }
    }
}
