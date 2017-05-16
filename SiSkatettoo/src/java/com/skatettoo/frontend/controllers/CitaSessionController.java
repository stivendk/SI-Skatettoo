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
        FacesUtils.mensaje("Se ha Actualizado satisfactoriamente");
        Email e = new Email("Cita aceptada", "El tatuador " + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\nTe ha respondido la cita que enviaste para el dia " + getCit().getFechaHora() + "\nPor el valor de: $" + getCit().getValorTatuaje(), getCit().getIdUsuario().getEmail());
        e.enviarEmail();
    }

    public void aplazarCita() {
        try {
            cit.setEstadoCita((short) 2);
            cfl.edit(cit);
            FacesUtils.mensaje("Se ha Actualizado satisfactoriamente");
            Email e = new Email("Cita aplazada", "El tatuador " + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\nTe ha puesto una nueva fecha para continuar con la cita el dia " + getCit().getFechaHora(), getCit().getIdUsuario().getEmail());
            e.enviarEmail();
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un problema" + e.getMessage());
        }

    }

    public void terminarCita(Cita c) {
        cfl.terminarCita(c, usu.getUsuario());
        cfl.edit(c);
        Email e = new Email("Cita terminada", "El tatto studdio " + getUs().getUsuario().getIdSucursal().getNombre() + "\nY Skatettoo, te agradecemos por utilizar nuestro sistema, esperamos que regreses pronto. ", getCit().getIdUsuario().getEmail());
        e.enviarEmail();
        FacesUtils.mensaje("La cita ha finalizado satisfactoriamente");
    }

}
