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
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
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
@Named(value = "registroTaController")
@ConversationScoped
public class RegistroTaController implements Serializable {

    private Usuario ta;
    private Sucursal suc;
    private String pin;
    private List<Sucursal> consulta;
    @EJB
    private UsuarioFacadeLocal tfc;
    @EJB
    private SucursalFacadeLocal sfc;
    @Inject
    private Conversation conversation;
    @Inject
    private LoginManagedBean us;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");

    public RegistroTaController() {
    }

    public LoginManagedBean getUs() {
        return us;
    }
    
    public Usuario getTa() {
        return ta;
    }

    public void setTa(Usuario ta) {
        this.ta = ta;
    }

    public Sucursal getSuc() {
        return suc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public List<Sucursal> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Sucursal> consulta) {
        this.consulta = consulta;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    @PostConstruct
    public void init() {
        ta = new Usuario();
    }

    public void pinSucursal() {
        consulta = sfc.consuSuc(pin);
        conversation.begin();
    }

    public String enviarSucur(Sucursal sc) {
        suc = sc;
        return "tatuador.xhtml?faces-redirect=true";
    }

    public void registrarTatuador() {
        try {
            Rol r = new Rol();
            r.setIdRol(2);
            ta.setIdRol(r);
            ta.setEstadoUsuario(4);
            ta.setIdSucursal(getUs().getSuc());
            ta.setPassword(GeneradorPss.generadorPassword());
            tfc.create(ta);
            Email e = new Email(prop.getString("emailTas"), getTa().getNombre() + getTa().getApellido() + "\n" + prop.getString("emailDesS1") + "\n" + prop.getString("emailTdes"), getTa().getEmail());
            e.enviarEmail();
            conversation.end();
            FacesUtils.mensaje(prop.getString("newtat"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError" + e.getMessage()));
        }
    }

    public void validatePassword(ComponentSystemEvent event) {
        try {

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
        } catch (NullPointerException e) {
            FacesUtils.mensaje("No te olvides de Confirmar tu contraseña");
        }

    }
}
