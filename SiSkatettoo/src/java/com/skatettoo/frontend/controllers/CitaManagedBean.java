/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
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

    private Cita cita;
    
    @Inject
    EmailManagedBean email;
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

    public EmailManagedBean getEmail() {
        return email;
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
            cita.setEstadoCita("1");
            Email e = new Email("Nueva solicitud", "El cliente " + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\nTe ha enviado una cita para el dia " + getCita().getFechaHora(), getCita().getTatuador().getEmail());
            e.enviarEmail();
            citafc.create(cita);
            FacesUtils.mensaje("Se ha enviado");
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
            throw e;
        }
    }    

    public void eliminarCita() {
        citafc.remove(cita);
    }

    public void responderCita() {
        FacesUtils.setObjectAcceso("cita", cita);
        citafc.edit(cita);
        FacesUtils.mensaje("Se ha actualizado la cita");
        
    }

    public String actualizarCita(Cita c) {
        cita = c;
        FacesUtils.setObjectAcceso("cita", cita);
        return "/pages/tatuador/rcita.xhtml?faces-redirect=true";
    }
    
    public String actualizarCitaA(Cita c) {
        cita = c;
        FacesUtils.setObjectAcceso("cita", cita);
        return "/pages/admin/rcita.xhtml?faces-redirect=true";
    }

    public String aplazarCita(Cita c) {
        cita = c;
        FacesUtils.setObjectAcceso("cita", cita);
        return "/pages/tatuador/acita.xhtml?faces-redirect=true";
    }
    
    public String aplazarCitaA(Cita c) {
        cita = c;
        FacesUtils.setObjectAcceso("cita", cita);
        return "/pages/admin/acita.xhtml?faces-redirect=true";
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
                if (ci.getIdUsuario().equals(us.getUsuario().getIdUsuario())) {
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

    public List<Cita> listarAdmin() {
        List<Cita> l = new ArrayList<>();
        try {
            for (Cita c : listarCita()) {
                if (c.getTatuador().getIdUsuario().equals(us.getUsuario().getIdUsuario())) {
                    l.add(c);
                }
            }
        } catch (Exception e) {

        }
        return l;
    }
}
