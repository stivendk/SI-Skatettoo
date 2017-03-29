/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Permiso;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author StivenDavid
 */
@Named(value = "loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    private Usuario usuario;
    private List<Permiso> permis;
    private Sucursal suc;
    
    @EJB UsuarioFacadeLocal usfc;

    public Sucursal getSuc() {
        return suc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
    }
    
    public LoginManagedBean() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Permiso> getPermis() {
        return permis;
    }    
    
    @PostConstruct
    public void init() {
        usuario = (Usuario) FacesUtils.getObjectMapSession("usuario");
        permis = getUsuario().getIdRol().getPermisoList();
        
    }

    public void modificarUs(){
        try {
            usfc.edit(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Se a guardado satisfactoriamente!", "Se modifico"));
        } catch (Exception e) {
            throw e;
        }
    }
    
    public String gestSurcursal() {
        usuario.setIdSucursal(suc);
        FacesUtils.setObjectAcceso("sucursal", suc);
        return "/pages/admin/gsucursall?faces-redirect=true";
    }
    
    public String cerrarSesion() {
        FacesUtils.removerObjectAcceso("usuario");
        FacesUtils.removerObjectAcceso("sucursal");
        FacesUtils.removerObjectAcceso("disenio");
        FacesUtils.removerObjectAcceso("localidad");
        FacesUtils.removerObjectAcceso("noticia");
        FacesUtils.removerObjectAcceso("cita");
        usuario = null;
        return "/index.xhtml?faces-redirect=true";
    }
}
