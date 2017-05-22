/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author StivenDavid
 */
@Named(value = "traductorController")
@SessionScoped
public class traductorController implements Serializable {

    private String locale;
    private static Map<String, Object> listIdiomas;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Map<String, Object> getListIdiomas() {
        return listIdiomas;
    }

    public static void setListIdiomas(Map<String, Object> listIdiomas) {
        traductorController.listIdiomas = listIdiomas;
    }

    static {
        listIdiomas = new LinkedHashMap<String, Object>();
        Locale español = new Locale("es");
        listIdiomas.put("Español", español);
        listIdiomas.put("English", Locale.ENGLISH);
    }

    public String changeLanguage(String locale) {
        this.locale = locale;
        FacesContext.getCurrentInstance().getViewRoot()
                .setLocale(new Locale(this.locale));
        return locale;
    }

    public void cambioIdioma() {
        for (Map.Entry<String, Object> entry : listIdiomas.entrySet()) {
            if (entry.getValue().toString().equals(locale)) {
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
            }
        }
    }

    public void cambioIdiomaIngles() {

        for (Map.Entry<String, Object> entry : listIdiomas.entrySet()) {
            if (entry.getValue().toString().equals(locale)) {

                FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
            }
        }
    }


}
