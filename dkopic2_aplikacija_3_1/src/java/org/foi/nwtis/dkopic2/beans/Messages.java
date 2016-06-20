/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import org.foi.nwtis.dkopic2.data.DataHolder;
import org.foi.nwtis.dkopic2.data.JMSAddress;
import org.foi.nwtis.dkopic2.data.JMSMail;

/**
 *
 * @author domagoj
 */
@Named(value = "messages")
@SessionScoped
public class Messages implements Serializable {

    private ArrayList<JMSAddress> addresses;
    private ArrayList<JMSMail> mails;
    /**
     * Creates a new instance of Messages
     */
    public Messages() {
    }
    
    public ArrayList<JMSAddress> getAddresses() {
        addresses = DataHolder.getAddressMessages();
        return addresses;
    }

    public void setAddresses(ArrayList<JMSAddress> addresses) {
        this.addresses = addresses;
    }

    public ArrayList<JMSMail> getMails() {
        mails = DataHolder.getMailMessages();
        return mails;
    }

    public void setMails(ArrayList<JMSMail> mails) {
        this.mails = mails;
    }
    
    public void deleteAddress(JMSAddress adr) {
        DataHolder.deleteAddr(adr);
    }
    
    public void deleteMail(JMSMail mail) {
        DataHolder.deleteMail(mail);
    }
    
    public void deleteAllAddr() {
        DataHolder.deleteAllAddr();
    }
    
    public void deleteAllMails() {
        DataHolder.deleteAllMails();
    }
    
    public void refresh() {
        addresses = DataHolder.getAddressMessages();
        mails = DataHolder.getMailMessages();
    }
}
