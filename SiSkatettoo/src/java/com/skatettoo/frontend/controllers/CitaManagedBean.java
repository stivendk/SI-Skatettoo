/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.facade.CitaFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "citaManagedBean")
@SessionScoped
public class CitaManagedBean implements Serializable {
    
    private int hora = 5;
    private Cita cita;
    private Disenio dis;
    @EJB
    private CitaFacadeLocal citafc;
    @Inject LoginManagedBean us;
    @Inject DisenioManagedBean du;

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }
 

    public Disenio getDis() {
        return dis;
    }

    public void setDis(Disenio dis) {
        this.dis = dis;
    }

    public DisenioManagedBean getDu() {
        return du;
    }

    public void setDu(DisenioManagedBean du) {
        this.du = du;
    }
    @Inject UsuarioManagedBean usu;

    public UsuarioManagedBean getUsu() {
        return usu;
    }
    @Inject SucursalManagedBean suc;


    public SucursalManagedBean getSuc() {
        return suc;
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
    public void init(){
        cita = new Cita();
    }
    
    public void solicitarCita(){
        try {
            cita.setIdUsuario(getUs().getUsuario());
            citafc.create(cita);
            FacesUtils.mensaje("Se ha enviado");
        } catch (Exception e) {
            throw e;
        }
    }
    
    public String seleccionarDis(Disenio d){
        try {
            dis = d;
            FacesUtils.setObjectAcceso("disenio", d);
            return "/pages/disenios/citadis?faces-redirect=true";
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void solicitarDisenio(){
        try {
            cita.setIdSucursal(getSuc().getSucu());
            cita.setIdDisenio(getDu().getDisenio());
            citafc.create(cita);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Tu solicitud se ha realizado!", "Se modifico"));
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void eliminarCita(){
        citafc.remove(cita);
    }
    
    public void responderCita(){
        citafc.edit(cita);
    }
    
    public String actualizarCita(Cita c){
        cita = c;
        return "/pages/tatuador/rcita";
    }
    
    public String aplazarCita(Cita c){
        cita = c;
        return "/pages/tatuador/acita";
    }
    
    public List<Cita> listarCita(){
        return citafc.findAll();
    }

    public LoginManagedBean getUs() {
        return us;
    }

    public List<Cita> citaSucu(){
        List<Cita> cit = new ArrayList<>();
        try {
            for (Cita ci : listarCita()) {
                if (ci.getIdDisenio().getIdUsuario().equals(us.getUsuario())) {
                    cit.add(ci);
                }

            }
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "No hay citas por el momento", "Se modifico"));
        }
        return cit;
    }
    
}
