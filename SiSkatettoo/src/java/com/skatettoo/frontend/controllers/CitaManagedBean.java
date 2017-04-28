/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.frontend.util.Generador;
import java.io.File;
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
import javax.servlet.http.Part;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;

/**
 *
 * @author StivenDavid
 */
@Named(value = "citaManagedBean")
@RequestScoped
public class CitaManagedBean implements Serializable {

    private Cita cita;
    private Part file;
    private Generador gen;
    @Inject
    EmailManagedBean email;
    @Inject
    UsuarioManagedBean usu;
    @EJB
    UsuarioFacadeLocal ufl;
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

    public LoginManagedBean getUs() {
        return us;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Generador getGen() {
        return gen;
    }

    public void setGen(Generador gen) {
        this.gen = gen;
    }

    @PostConstruct
    public void init() {
        cita = new Cita();
    }

    public String solicitarCita() {
        try {
            citafc.crearCita(us.getUsuario(), usu.getUsuario(), cita, su.getSucu());
            cita.setDisenioAdjunto(UploadFIles.uploadFileC(file, String.valueOf(cita.getDisenioAdjunto())));
            citafc.create(cita);
            Email e = new Email("Nueva solicitud", "El cliente " + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\nTe ha enviado una cita para el dia " + getCita().getFechaHora(), getCita().getTatuador().getEmail());
            e.enviarEmail();
            FacesUtils.mensaje("Se ha enviado");
            return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
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

    public String terminarCita(Cita c) {
        if (getCita().getEstadoCita() != 3 | cita.getEstadoCita() != 2) {
            cita = c;
            FacesUtils.setObjectAcceso("cita", cita);
            return "/pages/tatuador/tcita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje("Aun no has respondido la cita");
            return "";
        }
    }
    
    public String terminarCitaA(Cita c) {
        if (getCita().getEstadoCita() != 3 | cita.getEstadoCita() != 2) {
            cita = c;
            FacesUtils.setObjectAcceso("cita", cita);
            return "/pages/admin/tcita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje("Aun no has respondido la cita");
            return "";
        }
    }

    public String actualizarCita(Cita c) {
        if (getCita().getEstadoCita() != 1) {
            cita = c;
            FacesUtils.setObjectAcceso("cita", cita);
            return "/pages/tatuador/rcita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje("Ya respondiste esta cita");
            return "";
        }
    }

    public String actualizarCitaA(Cita c) {
        if (getCita().getEstadoCita() != 1) {
            cita = c;
            FacesUtils.setObjectAcceso("cita", cita);
            return "/pages/admin/rcita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje("Ya respondiste esta cita");
            return "";
        }
    }

    public String aplazarCita(Cita c) {
        if (getCita().getEstadoCita() != 1) {
            cita = c;
            FacesUtils.setObjectAcceso("cita", cita);
            return "/pages/tatuador/acita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje("Ya respondiste esta cita");
            return "";
        }
    }

    public String aplazarCitaA(Cita c) {
        if (getCita().getEstadoCita() != 1) {
        cita = c;
        FacesUtils.setObjectAcceso("cita", cita);
        return "/pages/admin/acita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje("Ya respondiste esta cita");
            return "";
        }
    }

    public List<Cita> listarCita() {
        return citafc.findAll();
    }

    public List<Cita> citaSucuf() {
        List<Cita> cit = new ArrayList<>();
        try {
            for (Cita ci : listarCita()) {
                if (ci.getIdUsuario().getIdUsuario().equals(us.getUsuario().getIdUsuario())) {
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
    
    public void mostrarInfo(Cita c){
        setCita(c);
        FacesUtils.setObjectAcceso("cita", cita);
    }
}
