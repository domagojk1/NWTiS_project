/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans.admin;

import com.sun.mail.imap.IMAPInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.dkopic2.mail.MailService;
import org.foi.nwtis.dkopic2.util.Configuration;

/**
 *
 * @author domagoj
 */
@Named(value = "mailViewer")
@SessionScoped
public class MailViewer implements Serializable {

    private ArrayList<String> availableFolders;
    private String selectedFolder;
    private ArrayList<MimeMessage> messages;
    private boolean showMessages;
    private boolean showMessage;
    private String mail;
    private int rows;
    
    /**
     * Creates a new instance of MailViewer
     */
    public MailViewer() {
    }

    public ArrayList<String> getAvailableFolders() throws MessagingException {
        MailService service = MailService.getInstance();
        Store store = service.connect();
        ArrayList<String> folders = Configuration.getFolders();
        ArrayList<Folder> allFolders = new ArrayList<>(Arrays.asList(service.getFolders(store)));
        store.close();
        
        availableFolders = new ArrayList<>();
        
        for (Folder folder : allFolders)
        {
            if (folders.contains(folder.getName()))
            {
                availableFolders.add(folder.getName());
            }
        }
        
        return availableFolders;
    }

    public void setAvailableFolders(ArrayList<String> availableFolders) {
        this.availableFolders = availableFolders;
    }

    public String getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(String selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public ArrayList<MimeMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<MimeMessage> messages) {
        this.messages = messages;
    }

    public boolean isShowMessages() {
        return showMessages;
    }

    public void setShowMessages(boolean showMessages) {
        this.showMessages = showMessages;
    }

    public boolean isShowMessage() {
        return showMessage;
    }

    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;
    }

    public int getRows() {
        return Configuration.getLines();
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public void fetchMails() throws MessagingException {
        showMessages = true;
        showMessage = false;
        MailService service = MailService.getInstance();
        Store store = service.connect();
        Folder folder = service.getFolder(selectedFolder);
        Message[] mails = folder.getMessages();
        messages = new ArrayList<>();
        
        for (Message message : mails)
        {
            messages.add((MimeMessage) message);
        }
    }
    
    public void showMessage(Message message) throws IOException, MessagingException {
        
        showMessage = true;
        Object object = (Object) message.getContent();
       
        if(object instanceof String)
        {
            mail = (String) object;
        }
        else if(object instanceof IMAPInputStream)
        {
            IMAPInputStream input = (IMAPInputStream) object;
            StringBuilder builder = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            while ((line = reader.readLine()) != null)
            {
                    builder.append(line);
            }
            if(reader != null)
            {
                reader.close();
            }
            mail = builder.toString();
        }
    }
}
