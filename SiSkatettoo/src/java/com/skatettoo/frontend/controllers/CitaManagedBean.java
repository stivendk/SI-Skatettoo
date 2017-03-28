/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.entities.EstadoCita;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import com.skatettoo.frontend.email.Email;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "citaManagedBean")
@RequestScoped
public class CitaManagedBean implements Serializable {

    private int hora = 5;
    private Cita cita;
    private EstadoCita estad;
    @Inject
    UsuarioManagedBean usu;
    @EJB
    private CitaFacadeLocal citafc;
    @Inject
    LoginManagedBean us;
    @Inject
    PanelSucursalManagedBean su;

    public PanelSucursalManagedBean getSu() {
        return su;
    }

    public EstadoCita getEstad() {
        return estad;
    }

    public void setEstad(EstadoCita estad) {
        this.estad = estad;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public UsuarioManagedBean getUsu() {
        return usu;
    }

    public CitaManagedBean() {
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    @PostConstruct
    public void init() {
        cita = new Cita();
    }

    public void solicitarCita() {
        try {
            cita.setIdUsuario(getUs().getUsuario());
            cita.setIdSucursal(getSu().getSucu());
            citafc.create(cita);
            enviarEmail();
            FacesUtils.mensaje("Se ha enviado");
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
            throw e;
        }
    }
        public void enviarEmail(){
            Email e = new Email("Nueva solicitud","Te han enviado una nueva solcitud de cita" + getUs().getUsuario().getNombre() + getUs().getUsuario().getApellido(), getUsu().getUsuario().getEmail());
            e.enviarEmail2();
        }
    public void eliminarCita() {
        citafc.remove(cita);
    }

    public void responderCita() {
        citafc.edit(cita);
    }

    public String actualizarCita(Cita c) {
        cita = c;
        return "/pages/tatuador/rcita";
    }

    public String aplazarCita(Cita c) {
        cita = c;
        return "/pages/tatuador/acita";
    }

    public List<Cita> listarCita() {
        return citafc.findAll();
    }

    public LoginManagedBean getUs() {
        return us;
    }

    public List<Cita> citaSucuf() {
        List<Cita> cit = new ArrayList<>();
        try {
            for (Cita ci : listarCita()) {
                if (ci.getIdUsuario().equals(us.getUsuario())) {
                    cit.add(ci);
                }

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay citas por el momento", "Se modifico"));
        }
        return cit;
    }

    public List<Cita> citaSucu() {
        List<Cita> cit = new ArrayList<>();
        try {
            for (Cita ci : listarCita()) {
                if (ci.getIdSucursal().equals(us.getUsuario().getIdSucursal())) {
                    cit.add(ci);
                }
            }
        } catch (Exception e) {

        }
        return cit;
    }

}
