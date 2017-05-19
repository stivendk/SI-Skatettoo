/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.frontend.util.GeneradorPss;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "registroSucController")
@RequestScoped
public class RegistroSucController implements Serializable {

    private Sucursal suc;
    private Part file;
    @EJB
    private SucursalFacadeLocal slfc;
    @Inject
    private RegistroController sfc;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    
    public RegistroController getSfc() {
        return sfc;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Sucursal getSuc() {
        return suc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
    }
    
    public RegistroSucController() {
    }

    @PostConstruct
    public void init() {
        suc = new Sucursal();
    }

    public String registrarAdminS() {
        try {
            suc.setPin(GeneradorPss.generadorPassword());
            suc.setFotoSuc(UploadFIles.uploadFileS(file, GeneradorPss.generadorPassword()));
            slfc.create(suc);
            Sucursal sl = slfc.consultarSuc(suc);
            getSfc().getUs().setIdSucursal(suc);
            getSfc().registrarAdmin();
            return "login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError"));
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

            FacesMessage msg = new FacesMessage(prop.getString("errorPass"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();

        }

    }
}
