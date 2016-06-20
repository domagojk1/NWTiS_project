/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.mail;

import org.foi.nwtis.dkopic2.data.JMSMail;
import org.foi.nwtis.dkopic2.util.UserService;
import org.foi.nwtis.dkopic2.util.Regex;
import com.sun.mail.imap.IMAPInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import org.foi.nwtis.dkopic2.util.Configuration;

/**
 *
 * @author domagoj
 */
public class MailService {
    private String password;
    private String receiver;
    private int port;
    private String host;
    private String subject;
    private String successFolder;
    private String failFolder;
    private String wrongFolder;
    
    private Session session;
    private Store store;
    
    private static MailService instance;

    private MailService() {
        host = Configuration.getHost();
        port = Configuration.getImapPort();
        receiver = Configuration.getReceiver();
        password = Configuration.getPassword();
        subject = Configuration.getSubject();
        successFolder = Configuration.getSuccessFolder();
        failFolder = Configuration.getFailFolder();
        wrongFolder = Configuration.getWrongFolder();
    }
    
    public static MailService getInstance() {
        if (instance == null)
            instance = new MailService();
        return instance;
    }

    public Store connect() throws MessagingException {
        session = Session.getDefaultInstance(System.getProperties(), null);
        store = session.getStore("imap");
        store.connect(host, port, receiver, password);
        return store;
    }
    
    public Folder getFolder(String folderName) throws MessagingException { 
        Folder folder = store.getDefaultFolder();
        folder = folder.getFolder(folderName);
        folder.open(Folder.READ_WRITE);
        
        return folder;
    }
    
    public Message[] getMessages(FlagTerm term, Folder folder) throws MessagingException {
        Message messages[] = folder.search(term);
        return messages;
    }
    
    public Folder[] getFolders(Store store) throws MessagingException {
        return store.getDefaultFolder().list();
    }
    
    public void close(Folder folder) throws MessagingException {
        if (folder.isOpen())
            folder.close(false);
    }
    
    public void proccess() {
        JMSMail jms = new JMSMail();
        jms.setThreadStart(new Date());
        int messageCount = 0;
        int subjectMessages = 0;
        int approvedCount = 0;
        int unapprovedCount = 0;
        
        try 
        {
            connect();
            Folder folder = getFolder("inbox");
            Message messages[] = getMessages(new FlagTerm(new Flags(Flags.Flag.SEEN), false), folder);
            messageCount = messages.length;
            String mail = "";
            
            for (Message message : messages) 
            {
                MimeMessage msg = (MimeMessage) message;
                
                if (msg.getSubject().equals(subject))
                {
                    subjectMessages ++;
                    Object object = (Object) msg.getContent();
                    
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
                msg.setFlag(Flags.Flag.SEEN, true);
                
                Regex regex = new Regex(mail);
                Matcher matcher = regex.matchMail(mail);
                
                if (matcher != null)
                {
                    String username = matcher.group(3);
                    String password = matcher.group(4);
                    UserService user = new UserService(username, password);
                    
                    if (user.getUser() != null)
                    {
                        if (user.manageStatus())
                        {
                            store(msg, successFolder);
                            approvedCount ++;
                        }
                        else
                        {
                            store(msg, failFolder);
                            unapprovedCount ++;
                        }
                    }
                    else
                    {
                        store(msg, wrongFolder);
                    }
                }
            }
        }
        catch (MessagingException | IOException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        jms.setThreadEnd(new Date());
        jms.setMessages(messageCount);
        jms.setMessagesNWTiS(subjectMessages);
        jms.setApprovedUsers(approvedCount);
        jms.setUnapprovedUsers(unapprovedCount);
        sendJMS(jms);
    }
    
    private void store(Message mail, String folderName) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        
        if (!folder.exists())
        {
            folder.create(Folder.HOLDS_MESSAGES);
        }
        
        folder.appendMessages(new Message[]{mail});
        
        if (folder.isOpen())
        {
            folder.close(true);
        }
        
        System.out.println("Message stored to folder: " + folderName);
    }
    
    private void sendJMS(JMSMail message) {
        JMSService.sendToQueue_1(message);
    }

    private void deleteMails(Folder folder) throws MessagingException {
        Message[] messages = folder.getMessages();
        
        for (Message msg : messages)
        {
            msg.setFlag(Flags.Flag.DELETED, true);
        }
        
        folder.close(true);
    }
}
