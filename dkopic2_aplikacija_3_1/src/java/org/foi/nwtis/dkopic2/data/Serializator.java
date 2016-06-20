/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 *
 * @author domagoj
 */
public class Serializator {
    private static String path = "/home/domagoj/NetBeansProjects/dkopic2_projekt/dkopic2_aplikacija_3_1/web/WEB-INF";
    
    public static void serializeAddress(String file, ArrayList<JMSAddress> record) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path + File.separator + file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(record);
        objectOutputStream.close();
        fileOutputStream.close();
    }
    
    public static ArrayList<JMSAddress> deserializeAddress(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ArrayList<JMSAddress> record = null;
        FileInputStream inputStream = new FileInputStream(path + File.separator + file);
        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
        record = (ArrayList<JMSAddress>) objInputStream.readObject();
        inputStream.close();
        objInputStream.close();
        return record;
    }
    
    public static void serializeMail(String file, ArrayList<JMSMail> record) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(path + File.separator + file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(record);
        objectOutputStream.close();
        fileOutputStream.close();
    }
    
    public static ArrayList<JMSMail> deserializeMail(String file) throws FileNotFoundException, IOException, ClassNotFoundException {
        ArrayList<JMSMail> record = null;
        FileInputStream inputStream = new FileInputStream(path + File.separator + file);
        ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
        record = (ArrayList<JMSMail>) objInputStream.readObject();
        inputStream.close();
        objInputStream.close();
        return record;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Serializator.path = path;
    }
    
    
}
