/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "panelSucursalManagedBean")
@RequestScoped
public class PanelSucursalManagedBean {

    private Sucursal sucu;
    private Usuario tatuador;

    public Sucursal getSucu() {
        return sucu;
    }

    public void setSucu(Sucursal sucu) {
        this.sucu = sucu;
    }

    public Usuario getTatuador() {
        return tatuador;
    }

    public void setTatuador(Usuario tatuador) {
        this.tatuador = tatuador;
    }
    
    public PanelSucursalManagedBean() {
    }

    @PostConstruct
    public void init() {
        sucu = (Sucursal) FacesUtils.getObjectMapSession("sucursal");
    }

    public List<Usuario> tattuadoresSurcursal() {
        return sucu.getUsuarioList();
    }

    public List<Disenio> listaDisenio() {
        try {
            return getTatuador().getDisenioList();
        } catch (Exception e) {
        }
        return null;
    }
}
