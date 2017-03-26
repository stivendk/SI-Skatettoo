/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.plandemjr.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Localidad;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.facade.LocalidadFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author StivenDavid
 */
@Named(value = "localidadManagedBean")
@RequestScoped
public class LocalidadManagedBean implements Serializable, Managedbean <Localidad> {

    private Localidad loc;
    private Sucursal suc;

    @EJB
    private LocalidadFacadeLocal lofc;
    
            
    public LocalidadManagedBean() {
    }

    public Sucursal getSuc() {
        return suc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
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
        suc = new Sucursal();
        
    }
    
    public List<Localidad> listarLocalidad(){
        return lofc.findAll();
    }
            
    @Override
    public Localidad getObject(Integer i) {
        return lofc.find(i);
    }
    
    public void selectLocalidad(Localidad l){
        setLoc(l);
    }
    
    public List<Sucursal> sucuList(){
        return loc.getSucursalList();
    }
    
}
