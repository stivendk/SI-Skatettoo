/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.entities.EstiloDisenio;
import com.skatettoo.backend.persistence.facade.EstiloDisenioFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Persa
 */
@Named(value = "panelEstiloManagedBean")
@RequestScoped
public class PanelEstiloManagedBean {

    private EstiloDisenio estild;
    private Disenio dis;
    @EJB 
    private EstiloDisenioFacadeLocal esfl;
    
    public PanelEstiloManagedBean() {
    }

    public Disenio getDis() {
        return dis;
    }

    public void setDis(Disenio dis) {
        this.dis = dis;
    }
    
    public EstiloDisenio getEstild() {
        return estild;
    }

    public void setEstild(EstiloDisenio estild) {
        this.estild = estild;
    }
    
    @PostConstruct
    public void init (){
        estild = (EstiloDisenio) FacesUtils.getObjectMapSession("estilo");
        dis = new Disenio();
    }
     
    public List<Disenio> disenioEstilo(){
       return estild.getDisenioList();
    }
}
