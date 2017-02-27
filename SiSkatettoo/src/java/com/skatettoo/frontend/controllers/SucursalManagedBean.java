/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.plandemjr.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
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
@Named(value = "sucursalManagedBean")
@RequestScoped
public class SucursalManagedBean implements Serializable, Managedbean <Sucursal> {

    private Sucursal sucu;
    @EJB
    private SucursalFacadeLocal sucufc;
            
    public SucursalManagedBean() {
    }

    public Sucursal getSucu() {
        return sucu;
    }

    public void setSucu(Sucursal sucu) {
        this.sucu = sucu;
    }
    
    @PostConstruct
    public void init(){
        sucu = new Sucursal();
    }
    
    public void crearSucursal(){
        sucufc.create(sucu);
    }
    
    public void eliminarSucursal(){
        sucufc.remove(sucu);
    }
    
    public void editarSucursal(){
        sucufc.edit(sucu);
    }
    
    public String actualizarSucursal(Sucursal s){
        sucu = s;
        return "";
    }
    
    public String verSucursal(Sucursal ss){
        sucu = ss;
        FacesUtils.setObjectAcceso("sucursal", ss);
        return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
    }
    
    public List<Sucursal> listarSucursal(){
        return sucufc.findAll();
    }

    @Override
    public Sucursal getObject(Integer i) {
        return sucufc.find(i);
    }
    
}
