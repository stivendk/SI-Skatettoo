/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.frontend.util.Generador;
import com.skatettoo.frontend.util.GeneradorPss;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
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
    private Sucursal sc;
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
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
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

    public Sucursal getSc() {
        return sc;
    }

    public void setSc(Sucursal sc) {
        this.sc = sc;
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
            cita.setDisenioAdjunto(UploadFIles.uploadFileC(file, GeneradorPss.generadorPassword()));
            citafc.create(cita);
            Email e = new Email(prop.getString("emailSol"), prop.getString("emailCSol") + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\n" + prop.getString("emailSdes ") + getCita().getFechaHora(), getCita().getTatuador().getEmail());
            e.enviarEmail();
            FacesUtils.mensaje(prop.getString("envioSol"));
            return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError") + e.getMessage());
        }
        return "";
    }

    public void eliminarCita() {
        citafc.remove(cita);
    }

    public void responderCita() {
        FacesUtils.setObjectAcceso("cita", cita);
        citafc.edit(cita);
        FacesUtils.mensaje(prop.getString("citaActu"));

    }

    public void terminarCita(Cita c) {
        try {
            Date n = new Date();
            setCita(c);
            if (c.getFechaHora().after(c.getFechaHora())) {
                citafc.terminarCita(getCita(), c.getIdUsuario());
                FacesUtils.mensaje(prop.getString("citat"));
            }else{
                FacesUtils.mensaje("La fecha aun no ha pasado");
            }
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError") + " " + e.getMessage());
        }
    }

    public void actualizarCita(Cita c) {
        if (getCita().getEstadoCita() != 1) {
            setCita(c);
            FacesUtils.setObjectAcceso("cita", c);
        } else {
            FacesUtils.mensaje("Ya respondiste esta cita");
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
                if (c.getEstadoCita() == 4){
                    l.remove(c);
                }
            }
        } catch (Exception e) {

        }
        return l;
    }
    
    public List<Cita> listarAminR() {
        List<Cita> l = new ArrayList<>();
        try {
            for (Cita c : listarCita()) {
                if (c.getTatuador().getIdUsuario().equals(us.getUsuario().getIdUsuario())) {
                    l.add(c);
                } 
                if (c.getEstadoCita() != 4 ){
                    l.remove(c);
                }
            }
        } catch (Exception e) {

        }
        return l;
    }
    
    public List<Cita> listarAdminq(){
        List<Cita> t = new ArrayList<>();
        for(Cita l : us.getUsuario().getCitaList1()){
            if (l.getEstadoCita() == 4) {
                t.add(l);
            }
        }
        return t;
    }
    
    public List<Cita> listarAdminR(){
        List<Cita> t = new ArrayList<>();
        for(Cita l : us.getUsuario().getCitaList1()){
            if (l.getEstadoCita() != 4) {
                t.add(l);
            }
        }
        return t;
    }
    
    public void mostrarInfo(Cita c) {
        setCita(c);
        FacesUtils.setObjectAcceso("cita", cita);
    }
    
    
}
