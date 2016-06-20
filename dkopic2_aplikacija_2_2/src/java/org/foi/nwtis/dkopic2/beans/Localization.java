/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.dkopic2.beans;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author domagoj
 */
@Named(value = "localization")
@SessionScoped
public class Localization implements Serializable {

    private Map<String, Object> languages;
    private String chosenLanguage;
    private Locale chosenLocale;
    /**
     * Creates a new instance of Localization
     */
    public Localization() {
        languages = new HashMap<String, Object>();
        languages.put("Hrvatski", new Locale("hr"));
        languages.put("English", Locale.ENGLISH);
    }

    public Map<String, Object> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, Object> languages) {
        this.languages = languages;
    }

    public String getChosenLanguage() {
        return chosenLanguage;
    }

    public void setChosenLanguage(String chosenLanguage) {
        this.chosenLanguage = chosenLanguage;
    }

    public Locale getChosenLocale() {
        return chosenLocale;
    }

    public void setChosenLocale(Locale chosenLocale) {
        this.chosenLocale = chosenLocale;
    }
    
    public String chooseLanguage() throws IOException {
        for (Map.Entry<String, Object> entry : languages.entrySet()) 
        {
            if (entry.getValue().toString().equals(chosenLanguage)) 
            {
                setChosenLocale((Locale) entry.getValue());
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
                return "OK";
            }
        }
        return "ERROR";
    }
}
