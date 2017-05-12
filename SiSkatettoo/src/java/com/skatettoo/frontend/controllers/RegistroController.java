/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "registroController")
@RequestScoped
public class RegistroController {

    private Usuario us;
    private Rol rl;
    @EJB private UsuarioFacadeLocal ufc;
    @Inject SucursalManagedBean smb;

    public Usuario getUs() {
        return us;
    }

    public SucursalManagedBean getSmb() {
        return smb;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }
    
    public RegistroController() {
    }

    public Rol getRl() {
        return rl;
    }

    public void setRl(Rol rl) {
        this.rl = rl;
    }
    
    
    @PostConstruct
    public void init(){
       us = new Usuario();
    }
    
    
    public String registrarCliente(){
        try {
            Rol r = new Rol();
            r.setIdRol(1);
            us.setIdRol(r);
            ufc.create(us);
            Email e = new Email("Nuevo Usuario", getUs().getNombre() + " " + getUs().getApellido() + "\nBienvenido a Skatettoo espero que lo disfrutes mucho", getUs().getEmail());
            e.enviarEmail();
            return "login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
    }
    
    public String registrarAdmin(){
        try {
            Rol r = new Rol();
            r.setIdRol(3);
            us.setIdRol(r);
            us.setEstadoUsuario(1);
            ufc.create(us);
            Email e = new Email("Nueva Sucursal", getUs().getNombre() + " " + getUs().getApellido() + "\nBienvenido a Skatettoo " + getUs().getIdSucursal().getNombre() + "\nEl codigo de tu tattoo studio es " + getUs().getIdSucursal().getPin() + "\nEspero que lo disfrutes mucho", getUs().getEmail());
            e.enviarEmail();
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
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
}
