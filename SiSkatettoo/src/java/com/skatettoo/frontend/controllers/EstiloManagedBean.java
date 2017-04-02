/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.EstiloDisenio;
import com.skatettoo.backend.persistence.facade.EstiloDisenioFacadeLocal;
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
@Named(value = "estiloManagedBean")
@SessionScoped
public class EstiloManagedBean implements Serializable, Managedbean <EstiloDisenio> {

    private EstiloDisenio estil;
    @EJB
    private EstiloDisenioFacadeLocal estilfc;
    
    public EstiloManagedBean() {
    }

    public EstiloDisenio getEstil() {
        return estil;
    }

    public void setEstil(EstiloDisenio estil) {
        this.estil = estil;
    }
    
    @PostConstruct
    public void init(){
        estil = new EstiloDisenio();
    }
    
    public void  crearEstilo(){
        estilfc.create(estil);
    }
    
    public void eliminarEstilo(){
        estilfc.remove(estil);
    }
    
    public void editarEstilo(){
        estilfc.edit(estil);
    }
    
    public String actualizarEstilo(EstiloDisenio es){
        estil = es;
        return "";
    }
    
    public String verEstilo(EstiloDisenio es){
        estil = es;
        return "";
    }
    
    public String selecEstiloD(EstiloDisenio e){
        setEstil(e);
        FacesUtils.setObjectAcceso("estilo", e);
        return "/pages/disenios/disenioss?faces-redirect=true";
    }
    
    public List<EstiloDisenio> listarEstilo(){
        return estilfc.findAll();
    }
    
    @Override
    public EstiloDisenio getObject(Integer i) {
        return  estilfc.find(i);
    }

}
