/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.email.Email;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "emailManagedBean")
@RequestScoped
public class EmailManagedBean {

    private String asunto;
    private String destinatario;
    private String mensaje;

    @Inject
    UsuarioManagedBean usu;
    @Inject
    LoginManagedBean us;

    public UsuarioManagedBean getUsu() {
        return usu;
    }

    public LoginManagedBean getUs() {
        return us;
    }
    
    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public EmailManagedBean() {
    }

    public String enviarCorreo1(){
        Email e = new Email(asunto, mensaje, destinatario);
        e.enviarEmail2();
        FacesContext fc = FacesContext.getCurrentInstance();
        FacesMessage m = new FacesMessage("Envio de correo exitoso!");
        fc.addMessage(null, m);
        return "";
    }
    
    public void enviarCorreo(){
        Email e = new Email("Nueva solicitud", "Te han enviado una nueva solcitud de cita" + getUs().getUsuario().getNombre() + getUs().getUsuario().getApellido(), getUsu().getUsuario().getEmail());
        e.enviarEmail();
    }
}
