/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author domagoj
 */
public class SocketHandler {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String host;
    private int port;

    public SocketHandler(Socket socket, String host, int port) {
        this.socket = socket;
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host,port);
    }
    
    public void sendOutput(String output) throws IOException {
        out = socket.getOutputStream();
        out.write(output.getBytes());
        out.flush();
        socket.shutdownOutput();
    }
    
    public String getInput() throws IOException {
        String request = "";
        InputStream in = socket.getInputStream();
        StringBuilder builder = new StringBuilder();
        int data;

        while(true)
        {
            data = in.read();
            if(data == -1)
                break;
            builder.append((char) data);
        }

        request = builder.toString().trim();
        socket.shutdownInput();
       
        return request;
    }
    
    public void closeInput() throws IOException {
        if (in != null)
            in.close();
    }
    
    public void closeOutput() throws IOException {
        if (out != null)
            out.close();
    }
}
