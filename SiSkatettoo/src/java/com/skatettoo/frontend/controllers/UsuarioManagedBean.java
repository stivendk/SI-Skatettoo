/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.plandemjr.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.UsuarioFacade;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "usuarioManagedBean")
@RequestScoped
public class UsuarioManagedBean implements Serializable, Managedbean<Usuario> {

    private Usuario usuario;
    @EJB
    private UsuarioFacadeLocal usuariofc;

    @Inject LoginManagedBean us;

    public LoginManagedBean getUs() {
        return us;
    }
    
    public UsuarioManagedBean() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PostConstruct
    public void init() {
        usuario = new Usuario();
    }

    public String registrarUsuario() {
        usuariofc.create(usuario);
        return "/pages/usuario/inicio?faces-redirect=true";
    }

    public void eliminarUsuario(Usuario u) {
        usuariofc.remove(usuario);
    }

    public void modificarUsuario() {
        try {
            usuariofc.edit(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Se a guardado satisfactoriamente!", "Se modifico"));
        } catch (Exception e) {
            throw e;
        }
    }

    public String actualizarUsuario() {
        us.getUsuario();
        return "/pages/usuario/configuracion?faces-redirect=true";
    }

    public List<Usuario> listarUsuario() {
        return usuariofc.findAll();
    }

    public String iniciarSession(){
        Usuario u;
        try {
            if (usuariofc.iniciarSesion(usuario)!=null) {
                usuario = usuariofc.iniciarSesion(usuario);
                FacesUtils.setObjectAcceso("usuario",usuario);
                return "/pages/usuario/inicio.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "¡Credenciales Incorrectas!", "Error"));
        }
        return null;
    }
    
    @Override
    public Usuario getObject(Integer i) {
        return usuariofc.find(i);
    }

    public List<Usuario> listarSucur() {
        return usuariofc.findAll();
    }
}
