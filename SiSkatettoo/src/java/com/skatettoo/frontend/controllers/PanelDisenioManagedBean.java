/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.DisenioFacadeLocal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "panelDisenioManagedBean")
@RequestScoped
public class PanelDisenioManagedBean {
    
    private Disenio dis;
    private Usuario ta;
    @EJB
    private DisenioFacadeLocal dfl;
    
    public PanelDisenioManagedBean() {
    }

    public Disenio getDis() {
        return dis;
    }

    public void setDis(Disenio dis) {
        this.dis = dis;
    }

    public Usuario getTa() {
        return ta;
    }

    public void setTa(Usuario ta) {
        this.ta = ta;
    }
    
    @PostConstruct
    public void init(){
        dis = (Disenio) FacesUtils.getObjectMapSession("disenio");
    }
    
    public void selectDisenio(Disenio d){
        setDis(d);
    }
    
    public String editarDisenio() {
        dfl.edit(dis);
        FacesUtils.mensaje("Tu diseño se ha actualizado");
        return "/pages/tatuador/cgdisenios.xhtml?faces-redirect=true";
    }
    
    public String editarDisenioA() {
        dfl.edit(dis);
        FacesUtils.mensaje("Tu diseño se ha actualizado");
        return "/pages/admin/cgdisenios.xhtml?faces-redirect=true";
    }
    
    public String autor(Usuario us){
        setTa(dis.getIdUsuario());
        FacesUtils.setObjectAcceso("tatuador", ta);
        return "/pages/tatuador/perfil.xhtml?faces-redirect=true";
    }
}
