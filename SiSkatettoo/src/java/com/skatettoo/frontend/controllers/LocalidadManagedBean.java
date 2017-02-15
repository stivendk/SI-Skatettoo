/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.plandemjr.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Localidad;
import com.skatettoo.backend.persistence.facade.LocalidadFacadeLocal;
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
@Named(value = "localidadManagedBean")
@SessionScoped
public class LocalidadManagedBean implements Serializable, Managedbean <Localidad> {

    private Localidad loc;
    @EJB
    private LocalidadFacadeLocal lofc;
            
    public LocalidadManagedBean() {
    }

    public Localidad getLoc() {
        return loc;
    }

    public void setLoc(Localidad loc) {
        this.loc = loc;
    }
    
    @PostConstruct
    public void init(){
        loc = new Localidad();
    }
    
    public List<Localidad> listarLocalidad(){
        return lofc.findAll();
    }
            
    @Override
    public Localidad getObject(Integer i) {
        return lofc.find(i);
    }

    
}
