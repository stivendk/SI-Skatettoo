/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.frontend.util.GeneradorPss;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "usuarioManagedBean")
@RequestScoped
public class UsuarioManagedBean implements Serializable, Managedbean<Usuario> {

    private Rol r;
    private Usuario usuario;
    @EJB
    private UsuarioFacadeLocal usuariofc;

    @Inject
    LoginManagedBean us;

    public LoginManagedBean getUs() {
        return us;
    }

    public Rol getR() {
        return r;
    }

    public void setR(Rol r) {
        this.r = r;
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

    /*public String registrarUsuario() {
        try {
            Rol r = new Rol();
            r.setIdRol(1);
            usuario.setIdRol(r);
            usuariofc.create(usuario);
            Email e = new Email("Nuevo Usuario", getUsuario().getNombre() + getUsuario().getApellido() + "\n Bienvenido a Skatettoo espero que lo disfrutes mucho", getUsuario().getEmail());
            e.enviarEmail();
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
    }*/

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

    public String iniciarSessions() {
        Object obj = usuariofc.autenticar(usuario);
        ResourceBundle prop = FacesUtils.getBundle("loginBundle");
        if (obj instanceof Integer) {
            switch ((Integer)obj){
                case 1: 
                    FacesUtils.mensaje(prop.getString("msjError"));
                break;
                case 2: 
                    FacesUtils.mensaje(prop.getString("msjErrorEm"));
                break;
                case 3: 
                    FacesUtils.mensaje(prop.getString("msjErrorCl"));
                break;
                case 4: 
                    FacesUtils.mensaje(prop.getString("msjErrorTa"));
                break;
            }
        }else{
            FacesUtils.setUserSession((Usuario) obj);
            return "/pages/usuario/inicio.xhtml?faces-redirect=true";
        }
        return "";
    }
    
    public String recuperarContraseña(){
        try {
            usuariofc.enviarCorreo(usuario);
            return "login.xhtml";
        } catch (Exception e) {
            FacesUtils.mensaje("Te la creiste we" + " " + e.getMessage());
        }
        return "";
    }
    
    public String iniciarSession() {
        Usuario u;
        try {
            if (usuariofc.iniciarSesion(usuario) != null) {
                usuario = usuariofc.iniciarSesion(usuario);
                FacesUtils.setObjectAcceso("usuario", usuario);
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
