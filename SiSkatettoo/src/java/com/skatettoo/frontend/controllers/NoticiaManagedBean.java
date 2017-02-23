/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import com.skatettoo.backend.persistence.facade.NoticiaFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "noticiaManagedBean")
@SessionScoped
public class NoticiaManagedBean implements Serializable {

    private Noticia noti;
    @EJB
    private NoticiaFacadeLocal notifc;
    
    @Inject
    LoginManagedBean log;

    public LoginManagedBean getLog() {
        return log;
    }
            
    public NoticiaManagedBean() {
    }

    public Noticia getNoti() {
        return noti;
    }

    public void setNoti(Noticia noti) {
        this.noti = noti;
    }
    
    @PostConstruct
    public void init(){
        noti = new Noticia();
    }
    
    public void publicarNoticia(){
        notifc.create(noti);
    }
    
    public void eliminarNoticia(){
        notifc.remove(noti);
    }
    
    public void editarNoticia(){
        notifc.edit(noti);
    }
    
    public String actualizarNoticia(Noticia n){
        noti = n;
        return "/pages/tatuador/gnoticia1?faces-redirect=true";
    }
    
    public String verNoticia(Noticia nn){
        noti = nn;
        return "";
        
    }
    
    public List<Noticia> listarNoticia(){
        return notifc.findAll();
    }
    
    public List<Noticia> notiUsuario(){
        List<Noticia> notUs = new ArrayList<>();
        for (Noticia n: listarNoticia()){
            if (n.getIdUsuario().equals(log.getUsuario())){
                notUs.add(n);
            }
        } return notUs;
    }
}
