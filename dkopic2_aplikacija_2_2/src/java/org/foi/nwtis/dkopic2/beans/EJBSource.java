/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans;

import org.foi.nwtis.dkopic2.session.AddressFacade;
import org.foi.nwtis.dkopic2.session.LogFacade;
import org.foi.nwtis.dkopic2.session.UserFacade;

/**
 *
 * @author domagoj
 */
public class EJBSource {
    private static EJBSource instance;
    private UserFacade userFacade;
    private LogFacade logFacade;
    private AddressFacade addressFacade;
    
    private EJBSource() {
    }
    
    public static EJBSource getInstance() {
        if (instance == null)
            instance = new EJBSource();
        return instance;
    }
    
    public static void initialize(UserFacade userFacade, LogFacade logFacade, AddressFacade addressFacade) {
        getInstance().userFacade = userFacade;
        getInstance().logFacade = logFacade;
        getInstance().addressFacade = addressFacade;
    }
    
    public synchronized static UserFacade getUserFacade () {
        return getInstance().userFacade;
    }
    
    public synchronized static LogFacade getLogFacade() {
        return getInstance().logFacade;
    }
    
    public synchronized static AddressFacade getAddressFacade() {
        return getInstance().addressFacade;
    }
}
