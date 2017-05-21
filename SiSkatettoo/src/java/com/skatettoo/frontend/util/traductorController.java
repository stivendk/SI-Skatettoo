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
@ApplicationScoped
public class traductorController implements Serializable{
        
    public void changeLocale(String trad){
        FacesContext context=FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale(trad));
    }
}
