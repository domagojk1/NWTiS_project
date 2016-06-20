/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author domagoj
 */
public class Regex {
    private String regexAdd = "^USER ([A-Za-z0-9_,-čćšžđČĆŠĐ]*) PASSWD ([A-Za-z0-9_,-čćšžđČĆŠĐ]*); ADD ([A-Za-z0-9_,-čćšžđČĆŠĐ]*); PASSWD ([A-Za-z0-9_,#,!,-čćšžđČĆŠĐ]*); ROLE (ADMIN|USER);$";
    private String input;
    private Pattern pattern;
    private Matcher matcher;

    public Regex(String input) {
        this.input = input;
    }
   
    public Matcher validate(String command) {
        pattern = Pattern.compile(regexAdd);
        matcher = pattern.matcher(command);
        
        if (matcher.matches())
        {
            return matcher;
        }
      
        return null;
    }
    
    public Matcher matchMail(String mail) {
        String lines[] = mail.split("\\r?\\n");
        String command = lines[0];
        
        return validate(command);
    }
}
