/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "noticiaSessionController")
@RequestScoped
public class NoticiaSessionController {

    private Noticia noti;

    public Noticia getNoti() {
        return noti;
    }

    public void setNoti(Noticia noti) {
        this.noti = noti;
    }
    
    public NoticiaSessionController() {
    }
    
    public void init (){
        noti = (Noticia) FacesUtils.getObjectMapSession("noticia");
    }
}
