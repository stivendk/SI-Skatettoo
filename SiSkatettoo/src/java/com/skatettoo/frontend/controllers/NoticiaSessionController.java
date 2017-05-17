/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import com.skatettoo.backend.persistence.facade.NoticiaFacadeLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "noticiaSessionController")
@RequestScoped
public class NoticiaSessionController {

    private Noticia nt;
    @EJB 
    private NoticiaFacadeLocal nc;

    public Noticia getNt() {
        return nt;
    }

    public void setNt(Noticia nt) {
        this.nt = nt;
    }
    
    public NoticiaSessionController() {
    }
    
    @PostConstruct
    public void init(){
        nt = (Noticia) FacesUtils.getObjectMapSession("noticia");
    }
    
    public void editarNoticia(){
        try {
            nc.edit(nt);
            FacesUtils.mensaje("Se ha actualizado con Exito");
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error" + " " + e.getMessage());
        }
    }
}
