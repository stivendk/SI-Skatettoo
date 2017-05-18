/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author StivenDavid
 */
@Named(value = "tatuadorSessionController")
@RequestScoped
public class TatuadorSessionController implements Serializable{

    private Usuario ta;
    private Sucursal su;
    private Disenio di;
    
    public Usuario getTa() {
        return ta;
    }

    public void setTa(Usuario ta) {
        this.ta = ta;
    }

    public Sucursal getSu() {
        return su;
    }

    public void setSu(Sucursal su) {
        this.su = su;
    }
    
    public TatuadorSessionController() {
    }
    
    @PostConstruct
    public void init(){
        ta = (Usuario) FacesUtils.getObjectMapSession("tatuador");
    }
    
    public List<Disenio> disenioTatuador(){
        return ta.getDisenioList();
    }
     
    public String sucursal(){
        setSu(ta.getIdSucursal());
        FacesUtils.setObjectAcceso("sucursal", su);
        return "/pages/tatuador/csucursal.xhtml?faces-redirect=true";
    }
    
    public String sucursalV(){
        setSu(ta.getIdSucursal());
        FacesUtils.setObjectAcceso("sucursal", su);
        return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
    }
    
    public List<Usuario> tattuadoresSurcursal() {
        return ta.getIdSucursal().getUsuarioList();
    }
    
    public String tatuadorS(Usuario ta){
        setTa(ta);
        FacesUtils.setObjectAcceso("tatuador", ta);
        return "/pages/disenios/tatuador.xhtml?faces-redirect=true";
    }
}
