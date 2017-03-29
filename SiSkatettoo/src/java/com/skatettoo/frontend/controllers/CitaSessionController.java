/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "citaSessionController")
@RequestScoped
public class CitaSessionController implements Serializable{

    private Cita cit;
    @EJB private CitaFacadeLocal cfl;
    
    public CitaSessionController() {
    }

    public Cita getCit() {
        return cit;
    }

    public void setCit(Cita cit) {
        this.cit = cit;
    }
    
    @PostConstruct
    public void init(){
        cit = (Cita) FacesUtils.getObjectMapSession("cita");
    }
    
    public void responderCita(){
        cfl.edit(cit);
        FacesUtils.mensaje("Se ha Actualizado satisfactoriamente");
    }
}
