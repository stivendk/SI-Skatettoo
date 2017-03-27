/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Persa
 */
@Named(value = "panelNoticiaManagedBean")
@RequestScoped
public class PanelNoticiaManagedBean {

    private Noticia not;
    
    public PanelNoticiaManagedBean() {
    }

    public Noticia getNot() {
        return not;
    }

    public void setNot(Noticia not) {
        this.not = not;
    }
    
    @PostConstruct
    public void init(){
        not = (Noticia) FacesUtils.getObjectMapSession("noticia");
    }
}
