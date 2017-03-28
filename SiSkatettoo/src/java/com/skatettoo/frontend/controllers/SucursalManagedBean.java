/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.plandemjr.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Localidad;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "sucursalManagedBean")
@ConversationScoped
public class SucursalManagedBean implements Serializable, Managedbean<Sucursal> {

    private static final long serialVersionUID = 1L;
    
    private Sucursal sucu;
    private Localidad loc;

    @EJB
    private SucursalFacadeLocal sucufc;
    @Inject LocalidadManagedBean loca;
    
    public SucursalManagedBean() {
    }

    public LocalidadManagedBean getLoca() {
        return loca;
    }

    public Localidad getLoc() {
        return loc;
    }

    public void setLoc(Localidad loc) {
        this.loc = loc;
    }
    public Sucursal getSucu() {
        return sucu;
    }

    public void setSucu(Sucursal sucu) {
        this.sucu = sucu;
    }

    @PostConstruct
    public void init() {
        sucu = new Sucursal();
        loc = new Localidad();
        
    }

    public void crearSucursal() {
        sucufc.create(sucu);
    }

    public void eliminarSucursal() {
        sucufc.remove(sucu);
    }

    public void editarSucursal() {
        sucufc.edit(sucu);
    }

    public String actualizarSucursal(Sucursal s) {
        sucu = s;
        return "";
    }
    
    public List<Localidad> listarloc(){
        return loca.listarLocalidad();
    }
    
    public void selectLocalidad(Localidad l){
        setLoc(l);
    }
    
    public List<Sucursal> sucuList(){
        return getLoc().getSucursalList();
    }
    
    

    public List<Sucursal> listarSucursal() {
        return sucufc.findAll();
    }

    @Override
    public Sucursal getObject(Integer i) {
        return sucufc.find(i);
    }

    public String verSucursal(Sucursal ss) {
        sucu = ss;
        FacesUtils.setObjectAcceso("sucursal", ss);
        return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
    }
}
