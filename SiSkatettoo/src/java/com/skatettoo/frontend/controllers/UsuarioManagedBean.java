/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.email.Email;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
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

    private Usuario usuario;
    @EJB
    private UsuarioFacadeLocal usuariofc;

    @Inject RolManagedBean rol;
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
        Email e = new Email("Bienvenido a Skatettoo " + getUsuario().getNombre() + getUsuario().getApellido()," Te damos la bienvenida, ahora puedes pedir citas para la realizacion de tus tatuajes", getUsuario().getEmail());
        e.enviarEmail();
        usuariofc.create(usuario);
        return "login.xhtml?faces-redirect=true";
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
    
    public void validatePassword(ComponentSystemEvent event) {

	  FacesContext fc = FacesContext.getCurrentInstance();

	  UIComponent components = event.getComponent();

	  // get password
	  UIInput uiInputPassword = (UIInput) components.findComponent("password");
	  String password = uiInputPassword.getLocalValue() == null ? ""
		: uiInputPassword.getLocalValue().toString();
	  String passwordId = uiInputPassword.getClientId();

	  // get confirm password
	  UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
	  String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
		: uiInputConfirmPassword.getLocalValue().toString();

	  // Let required="true" do its job.
	  if (password.isEmpty() || confirmPassword.isEmpty()) {
		return;
	  }

	  if (!password.equals(confirmPassword)) {

		FacesMessage msg = new FacesMessage("Las contraseñas deben coincidir");
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		fc.addMessage(passwordId, msg);
		fc.renderResponse();

	  }

	}
    
    @Override
    public Usuario getObject(Integer i) {
        return usuariofc.find(i);
    }

    public List<Usuario> listarSucur() {
        return usuariofc.findAll();
    }
}
