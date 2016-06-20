/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author domagoj
 */
public class JMSMail implements Serializable{
    private Date threadStart;
    private Date threadEnd;
    private int messages;
    private int messagesNWTiS;
    private int approvedUsers;
    private int unapprovedUsers;

    public JMSMail() {
    }

    public Date getThreadStart() {
        return threadStart;
    }

    public void setThreadStart(Date threadStart) {
        this.threadStart = threadStart;
    }

    public Date getThreadEnd() {
        return threadEnd;
    }

    public void setThreadEnd(Date threadEnd) {
        this.threadEnd = threadEnd;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public int getMessagesNWTiS() {
        return messagesNWTiS;
    }

    public void setMessagesNWTiS(int messagesNWTiS) {
        this.messagesNWTiS = messagesNWTiS;
    }

    public int getApprovedUsers() {
        return approvedUsers;
    }

    public void setApprovedUsers(int approvedUsers) {
        this.approvedUsers = approvedUsers;
    }

    public int getUnapprovedUsers() {
        return unapprovedUsers;
    }

    public void setUnapprovedUsers(int unapprovedUsers) {
        this.unapprovedUsers = unapprovedUsers;
    }
}
