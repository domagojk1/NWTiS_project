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
    private String regexAdmin = "^USER ([A-Za-z0-9_,-čćšžđČĆŠĐŽ]*); PASSWD ([A-Za-z0-9_,#,!,-čćšžđČĆŠĐŽ]*); (PAUSE|START|STOP|STATUS|(ADD ([A-Za-z0-9_,-čćšžđČĆŠĐ]*); PASSWD ([A-Za-z0-9_,#,!,-čćšžđČĆŠĐŽ]*); ROLE (ADMIN|USER))|(UP|DOWN) ([A-Za-z0-9_,-čćšžđČĆŠĐŽ]*));$";
    private String regexUser = "^USER ([A-Za-z0-9_,-čćšžđČĆŠĐŽ]*); PASSWD ([A-Za-z0-9_,#,!,-čćšžđČĆŠĐŽ]*); (TEST ([A-Za-z0-9_,-čćšžđČĆŠĐŽ ]*)|GET ([A-Za-z0-9_,-čćšžđČĆŠĐŽ ]*)|(ADD ([A-Za-z0-9_,-čćšžđČĆŠĐŽ ]*)));$";
    private String regexBasic = "^USER ([A-Za-z0-9_,-čćšžđČĆŠĐŽ]*); PASSWD ([A-Za-z0-9_,#,!,-čćšžđČĆŠĐŽ]*);$";
    
    private Pattern pattern;
    private Matcher matcher;
    private String input;

    public Regex(String input) {
        this.input = input;
    }
    
    private boolean validateAdmin() {
        pattern = Pattern.compile(regexAdmin);
        matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    private boolean validateUser() {
        pattern = Pattern.compile(regexUser);
        matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    private boolean validateBasic() {
        pattern = Pattern.compile(regexBasic);
        matcher = pattern.matcher(input);
        
        return matcher.matches();
    }
    
    public Matcher getBasic() {
        if (validateBasic())
        {
            pattern = Pattern.compile(regexBasic);
            matcher = pattern.matcher(input);
            matcher.matches();
            return matcher;
        }
        return null;
    }
    
    public Matcher getAdmin() {
        if (validateAdmin())
        {
            pattern = Pattern.compile(regexAdmin);
            matcher = pattern.matcher(input);
            matcher.matches();
            return matcher;
        }
        return null;
    }
    
    public Matcher getUser() {
        if (validateUser())
        {
            pattern = Pattern.compile(regexUser);
            matcher = pattern.matcher(input);
            matcher.matches();
            return matcher;
        }
        return null;
    }
}
