/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.soap;

/**
 *
 * @author domagoj
 */
public class SOAPClient {

    public static MeteoData getMeteo(java.lang.String address, java.lang.String username, java.lang.String password) {
        org.foi.nwtis.dkopic2.soap.MeteoWS_Service service = new org.foi.nwtis.dkopic2.soap.MeteoWS_Service();
        org.foi.nwtis.dkopic2.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getMeteo(address, username, password);
    }
    
}
