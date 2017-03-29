/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.EstiloDisenio;
import javax.annotation.PostConstruct;
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
    
    public PanelEstiloManagedBean() {
    }

    public EstiloDisenio getEstild() {
        return estild;
    }

    public void setEstild(EstiloDisenio estild) {
        this.estild = estild;
    }
    
    @PostConstruct
    public void init (){
        estild = (EstiloDisenio) FacesUtils.getObjectMapSession("estiloDisenio");
    }
    
    public void selectEstiloD(EstiloDisenio e){
        setEstild(estild);
    }
}
