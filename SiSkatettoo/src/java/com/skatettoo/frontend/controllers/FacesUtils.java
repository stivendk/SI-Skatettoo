/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Usuario;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author StivenDavid
 */
public class FacesUtils {

    public static void setUsuarioLogueado(Usuario u) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("usuario", u);
    }

    public static void mensaje(String mensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(mensaje));
    }

    public static Usuario getUsuarioLogueado() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    public static void removerUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().remove("usuario");
        context.getExternalContext().invalidateSession();
    }

    public static void redireccionar() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        context.getExternalContext().redirect(request.getContextPath());
    }

    
}
