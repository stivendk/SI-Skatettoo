/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Usuario;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author StivenDavid
 */
public class FacesUtils {

    public static void setObjectAcceso(String nombre, Object objeto) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put(nombre, objeto);
    }

    public static void mensaje(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(mensaje));
    }

    public static Object getObjectMapSession(String s) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getExternalContext().getSessionMap().get(s);
    }

    public static void removerObjectAcceso(String nombre) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove(nombre);
        context.getExternalContext().invalidateSession();
    }

    public static void redireccionar() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        context.getExternalContext().redirect(request.getContextPath());
    }

    public static ResourceBundle getBundle(String nombAr){
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().getResourceBundle(context, nombAr);
    }
    
    public static void setUserSession(Usuario u){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession uss = (HttpSession) context.getExternalContext().getSession(false);
        uss.setAttribute("usuario", u);
    }
    
    public static Usuario getUserSession(String u){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession uss = (HttpSession) context.getExternalContext().getSession(false);
        return (Usuario) uss.getAttribute(u);
    }
}
