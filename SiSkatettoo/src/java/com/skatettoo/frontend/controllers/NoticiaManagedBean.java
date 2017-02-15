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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

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
        return "";
    }
    
    public String verNoticia(Noticia nn){
        noti = nn;
        return "";
    }
    
    public List<Noticia> listarNoticia(){
        return notifc.findAll();
    }
    
}
