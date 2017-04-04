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
public class CitaSessionController implements Serializable{

    private Cita cit;
    @EJB private CitaFacadeLocal cfl;
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
    public void init(){
        cit = (Cita) FacesUtils.getObjectMapSession("cita");
    }
    
    public String responderCita(){
        if(getUsu().getUsuario().getEstadoUsuario() !=1){
            Email e = new Email("Cita aceptada", "El tatuador " + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\nTe ha respondido la cita que enviaste para el dia " + getCit().getFechaHora() + "\nPor el valor de: $" + getCit().getValorTatuaje(), getCit().getIdUsuario().getEmail());
            e.enviarEmail();
            cfl.edit(cit);
            FacesUtils.mensaje("Se ha Actualizado satisfactoriamente");
            return "/pages/tatuador/ccitas.xhtml?faces-redirect=true";
        }else{
            FacesUtils.mensaje("Ya respondiste esta cita");
            return "";
        }
    }
    
    public String aplazarCita(){
        if(getCit().getEstadoCita() != 1){
            cit.setEstadoCita((short)2);
            Email e = new Email("Cita aplazada", "El tatuador " + getUs().getUsuario().getNombre() + " " + getUs().getUsuario().getApellido() + "\nTe ha puesto una nueva fecha para continuar con la cita el dia " + getCit().getFechaHora(), getCit().getIdUsuario().getEmail());
            e.enviarEmail();
            cfl.edit(cit);
            FacesUtils.mensaje("Se ha Actualizado satisfactoriamente");
            return "/pages/tatuador/ccitas.xhtml?faces-redirect=true";
        }else{
            FacesUtils.mensaje("Ya respondiste esta cita");
            return "";
        }
    }
    
    public String terminarCita(){
        if(getCit().getEstadoCita()!= 3 | cit.getEstadoCita()!= 2){
            cfl.terminarCita(cit, usu.getUsuario());
            cfl.edit(cit);
            Email e = new Email("Cita terminada", "El tatto studdio " + getUs().getUsuario().getIdSucursal().getNombre() + "\nY Skatettoo, te agradecemos por utilizar nuestro sistema, esperamos que regreses pronto. ", getCit().getIdUsuario().getEmail());
            e.enviarEmail();
            FacesUtils.mensaje("La cita ha finalizado satisfactoriamente");
            return "/pages/tatuador/ccitas.xhtml?faces-redirect=true";
        }else{
            FacesUtils.mensaje("Aun no has respondido la cita");
            return "";
        }
    }
    
}
