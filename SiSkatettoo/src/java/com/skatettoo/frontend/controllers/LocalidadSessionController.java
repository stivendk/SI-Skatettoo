/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Localidad;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.facade.LocalidadFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "localidadSessionController")
@RequestScoped
public class LocalidadSessionController implements Serializable{

    private Localidad loc;
    private Sucursal suc;

    @EJB LocalidadFacadeLocal lfl;
    
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
    
    public LocalidadSessionController() {
    }
    
    @PostConstruct
    public void init(){
        loc = (Localidad) FacesUtils.getObjectMapSession("localidad");
        suc = new Sucursal();
    }
    
    public void selectLocalidad(Localidad l){
        setLoc(l);
    }
    
    public List<Localidad> listarLoc(){
        return lfl.findAll();
    }
    
    public String verSucursal(Sucursal ss) {
        suc = ss;
        FacesUtils.setObjectAcceso("sucursal", ss);
        return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
    }
    
    public List<Sucursal> sucuList(){
        return loc.getSucursalList();
    }
    
}
