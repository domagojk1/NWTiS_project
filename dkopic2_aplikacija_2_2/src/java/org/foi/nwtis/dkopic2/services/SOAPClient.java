/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.services;

import org.foi.nwtis.dkopic2.soap.MeteoData;

/**
 *
 * @author domagoj
 */
public class SOAPClient {

    public static java.util.List<org.foi.nwtis.dkopic2.soap.Address> getAddressList(java.lang.String username, java.lang.String password) {
        org.foi.nwtis.dkopic2.soap.MeteoWS_Service service = new org.foi.nwtis.dkopic2.soap.MeteoWS_Service();
        org.foi.nwtis.dkopic2.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getAddressList(username, password);
    }

    public static java.util.List<org.foi.nwtis.dkopic2.soap.MeteoData> getLast(int n, java.lang.String address, java.lang.String username, java.lang.String password) {
        org.foi.nwtis.dkopic2.soap.MeteoWS_Service service = new org.foi.nwtis.dkopic2.soap.MeteoWS_Service();
        org.foi.nwtis.dkopic2.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getLast(n, address, username, password);
    }

    public static MeteoData getMeteo(java.lang.String address, java.lang.String username, java.lang.String password) {
        org.foi.nwtis.dkopic2.soap.MeteoWS_Service service = new org.foi.nwtis.dkopic2.soap.MeteoWS_Service();
        org.foi.nwtis.dkopic2.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getMeteo(address, username, password);
    }

    public static java.util.List<org.foi.nwtis.dkopic2.soap.MeteoData> getInterval(java.lang.String address, java.lang.String startDate, java.lang.String endDate, java.lang.String username, java.lang.String password) {
        org.foi.nwtis.dkopic2.soap.MeteoWS_Service service = new org.foi.nwtis.dkopic2.soap.MeteoWS_Service();
        org.foi.nwtis.dkopic2.soap.MeteoWS port = service.getMeteoWSPort();
        return port.getInterval(address, startDate, endDate, username, password);
    }
}
