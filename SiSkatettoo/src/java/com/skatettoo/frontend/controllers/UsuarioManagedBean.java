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
        usuariofc.edit(usuario);
    }

    public String actualizarUsuario(Usuario u) {
        usuario = u;
        return "/pages/editar";
    }

    public List<Usuario> listarUsuario() {
        return usuariofc.findAll();
    }

    public String iniciarSession(){
        Usuario u;
        try {
            if (usuariofc.iniciarSesion(usuario)!=null) {
                usuario = usuariofc.iniciarSesion(usuario);
                FacesUtils.setUsuarioLogueado(usuario);
                return "/pages/usuario/inicio.xhtml?faces-redirect=true";
            }
        } catch (Exception e) {
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
