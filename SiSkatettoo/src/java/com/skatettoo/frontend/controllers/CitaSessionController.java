/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import com.skatettoo.frontend.email.Email;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "citaSessionController")
@RequestScoped
public class CitaSessionController implements Serializable {

    private Cita cit;
    @EJB
    private CitaFacadeLocal cfl;
    @Inject
    LoginManagedBean us;
    @Inject
    PanelSucursalManagedBean su;
    @Inject
    UsuarioManagedBean usu;
    ResourceBundle prop = FacesUtils.getBundle("editeliBundle");

    public CitaSessionController() {
    }

    public Cita getCit() {
        return cit;
    }

    public void setCit(Cita cit) {
        this.cit = cit;
    }

    public LoginManagedBean getUs() {
        return us;
    }

    public PanelSucursalManagedBean getSu() {
        return su;
    }

    public UsuarioManagedBean getUsu() {
        return usu;
    }

    @PostConstruct
    public void init() {
        cit = (Cita) FacesUtils.getObjectMapSession("cita");
    }

    public void responderCita() {
        cfl.edit(cit);
        FacesUtils.mensaje(prop.getString("resCita"));
        Email e = new Email(prop.getString("citaa"), prop.getString("citaAt ") + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\n" + prop.getString("citaFech ") + getCit().getFechaHora() + "\n" + prop.getString("citaVala") + getCit().getValorTatuaje(), getCit().getIdUsuario().getEmail());
        e.enviarEmail();
    }

    public void aplazarCita() {
        try {
            cit.setEstadoCita((short) 2);
            cfl.edit(cit);
            FacesUtils.mensaje(prop.getString("citaAc"));
            Email e = new Email(prop.getString("citaAp"), prop.getString("citaAt ") + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\n" + prop.getString("citaFech2 ") + getCit().getFechaHora(), getCit().getIdUsuario().getEmail());
            e.enviarEmail();
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError") + e.getMessage());
        }

    }

}
