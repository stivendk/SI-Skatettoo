/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "citaManagedBean")
@SessionScoped
public class CitaManagedBean implements Serializable {

    private Cita cita;
    @EJB
    private CitaFacadeLocal citafc;
    
    @Inject LoginManagedBean us;
    
    public CitaManagedBean() {
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }
    
    @PostConstruct
    public void init(){
        cita = new Cita();
    }
    
    public void solicitarCita(){
        cita.setIdUsuario(getUs().getUsuario()); 
        citafc.create(cita);
    }
    
    public void eliminarCita(){
        citafc.remove(cita);
    }
    
    public void responderCita(){
        citafc.edit(cita);
    }
    
    public String actualizarCita(Cita c){
        cita = c;
        return "/pages/usuario/tatuador/rcita";
    }
    
    public String aplazarCita(Cita c){
        cita = c;
        return "/pages/usuario/tatuador/acita";
    }
    
    public List<Cita> listarCita(){
        return citafc.findAll();
    }

    public LoginManagedBean getUs() {
        return us;
    }

    
    
}
