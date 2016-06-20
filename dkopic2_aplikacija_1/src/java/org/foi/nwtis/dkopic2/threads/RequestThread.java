/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.threads;

import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import org.foi.nwtis.dkopic2.SocketHandler;
import org.foi.nwtis.dkopic2.user.User;
import org.foi.nwtis.dkopic2.user.UserDB;
import org.foi.nwtis.dkopic2.util.Configuration;
import org.foi.nwtis.dkopic2.util.Regex;
import org.foi.nwtis.dkopic2.util.Responser;

/**
 *
 * @author domagoj
 */
public class RequestThread extends Thread{
    private Socket socket;
    private Matcher matcherBasic;
    private Matcher matcherAdmin;
    private Matcher matcherUser;
    private User user;

    public RequestThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try 
        {
            SocketHandler handler = new SocketHandler(socket, Configuration.getHost(), Configuration.getServerPort());
            String request = handler.getInput();
            
            byte[] bytes = request.getBytes(StandardCharsets.ISO_8859_1);
            request = new String(bytes, StandardCharsets.UTF_8);
            
            Regex regex = new Regex(request);
            matcherAdmin = regex.getAdmin();
            matcherUser = regex.getUser();
            matcherBasic = regex.getBasic();
            
            Responser responser = null;
            String response = "";
            long start = System.currentTimeMillis();
            System.out.println(request);
            
            if (matcherBasic != null)
            {
                user = UserDB.getUser(matcherBasic.group(1), matcherBasic.group(2));
                responser = new Responser(matcherAdmin, matcherUser, matcherBasic, socket, user);
                
                if (user != null)
                {
                    if (user.isAdmin())
                    {
                        responser.insertLog(user, System.currentTimeMillis()-start, 1, "LOGIN", "");
                        response = responser.getOk();
                    }
                        
                    else if (user.checkRequests())
                    {
                        UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                        responser.insertLog(user, System.currentTimeMillis()-start, 1, "LOGIN", "");
                        response = responser.getOk();
                    }
                    
                    else 
                    {
                        responser.insertLog(user, System.currentTimeMillis()-start, 0, "LOGIN", "");
                        response = responser.getErr40();
                    }
                }
                else 
                {
                    responser.insertLog(user, System.currentTimeMillis()-start, 0, "LOGIN", "");
                    response = responser.getErr20();
                }
            }
            
            else if (matcherAdmin != null)
            {
                System.out.println("admin");
                user = UserDB.getUser(matcherAdmin.group(1), matcherAdmin.group(2));
                responser = new Responser(matcherAdmin, matcherUser, matcherBasic, socket, user);
                
                if (user != null)
                {
                    if (user.isAdmin())
                    {
                        response = responser.getOk();

                        if (request.contains("PAUSE"))
                            response = responser.handlePause();

                        else if (request.contains("START"))
                            response = responser.handleStart();

                        else if (request.contains("STOP"))
                            response = responser.handleStop();

                        else if (request.contains("STATUS"))
                            response = responser.getStatus();

                        else if (request.contains("ADD") && request.contains("ROLE"))
                            response = responser.handleAddUser(request);

                        else if (request.contains("UP") || request.contains("DOWN"))
                            response = responser.handleUpdate();
                    }
                    
                    else response = responser.getErr21();
                }
                
                else response = responser.getErr20();
            }
            
            else if (matcherUser != null)
            {
                System.out.println("user");
                user = UserDB.getUser(matcherUser.group(1), matcherUser.group(2));
                responser = new Responser(matcherAdmin, matcherUser, matcherBasic, socket, user);
               
                if (user != null)
                {
                    if (user.isAdmin())
                    {
                        if (request.contains("ADD"))
                            response = responser.handleAdd();

                        else if (request.contains("TEST"))
                            response = responser.handleTest();
                    }
                    
                    else if (user.checkRequests())
                    {
                        if (request.contains("ADD"))
                        {
                            UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                            response = responser.handleAdd();
                        }
                        else if (request.contains("TEST"))
                        {
                            UserDB.updateRequests(user.getUsername(), user.getRequests() + 1);
                            response = responser.handleTest();
                        } 
                    }
                    
                    else response = responser.getErr40();
                }
                
                else response = responser.getErr20();
            }
            else response = "ERR: wrong syntax.";
         
            handler.sendOutput(response);
            handler.closeInput();
            handler.closeOutput();
        } 
        catch (Exception ex)
        {
            Logger.getLogger(RequestThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void start() {
        super.start(); 
    }
    
}
