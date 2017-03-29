/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.plandemjr.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.facade.DisenioFacadeLocal;
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
@Named(value = "disenioManagedBean")
@RequestScoped
public class DisenioManagedBean implements Serializable, Managedbean<Disenio> {

    private Disenio disenio;
    @EJB
    private DisenioFacadeLocal diseniofc;
    private List<Disenio> resultado;

    @Inject
    LoginManagedBean us;

    @Inject
    EstiloManagedBean est;

    public EstiloManagedBean getEst() {
        return est;
    }
    
    public LoginManagedBean getUs() {
        return us;
    }


    public DisenioManagedBean() {
    }

    public Disenio getDisenio() {
        return disenio;
    }

    public void setDisenio(Disenio disenio) {
        this.disenio = disenio;
    }

    public List<Disenio> getResultado() {
        return resultado;
    }

    public void setResultado(List<Disenio> resultado) {
        this.resultado = resultado;
    }

    

    @PostConstruct
    public void init() {
        disenio = new Disenio();
    }

    public void registrarDisenio() {
        try {
            disenio.setIdUsuario(getUs().getUsuario());
            diseniofc.create(disenio);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha publicado con exito", "Se ha agregado un nuevo diseño"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha producido un error", "¡Error!"));
        }
    }

    public void eliminarDisenio() {
        diseniofc.remove(disenio);
    }

    public void editarDisenio() {
        diseniofc.edit(disenio);
    }

    public String actualizarDisenio(Disenio d) {
        disenio = d;
        return "/pages/tatuador/cdisenio";
    }

    public String verDisenio(Disenio d) {
        disenio = d;
        FacesUtils.setObjectAcceso("disenio", d);
        return "/pages/disenios/disenio";
    }

    public String verDisenio2(Disenio d) {
        disenio = d;
        return "disenio";
    }

    public String solicitarDisenio(Disenio d) {
        disenio = d;
        return "/pages/disenios/citas";
    }

    public String selecDisenio(Disenio d){
        setDisenio(d);
        FacesUtils.setObjectAcceso("Disenio", d);
        return "/pages/disenios/diseniossfaces-redirect=true";
    }
    
    public List<Disenio> listarDisenio() {
        return diseniofc.findAll();
    }

    @Override
    public Disenio getObject(Integer i) {
        return diseniofc.find(i);
    }

    
    public List<Disenio> disenioTat() {
        List<Disenio> dis = new ArrayList<>();
        for (Disenio di : listarDisenio()) {
            if (di.getIdUsuario().equals( us.getUsuario())) {
                dis.add(di);
            }
        }
        return dis;
    }
    
    
    public List<Disenio> disenioEst() {
        List<Disenio> dis = new ArrayList<>();
        for (Disenio di : listarDisenio()) {
            if (di.getIdEstiloDisenio().equals( est.getEstil())) {
                dis.add(di);
            }
        }
        return dis;
    }

}
