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
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author StivenDavid
 */
@Named(value = "disenioManagedBean")
@SessionScoped
public class DisenioManagedBean implements Serializable, Managedbean<Disenio> {

    private Disenio disenio;
    @EJB
    private DisenioFacadeLocal diseniofc;
    private List<Disenio> resultado;
    private int estilo;

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

    public int getEstilo() {
        return estilo;
    }

    public void setEstilo(int estilo) {
        this.estilo = estilo;
    }

    @PostConstruct
    public void init() {
        disenio = new Disenio();
    }

    public void registrarDisenio() {
        diseniofc.create(disenio);
    }

    public void eliminarDisenio() {
        diseniofc.remove(disenio);
    }

    public void editarDisenio() {
        diseniofc.edit(disenio);
    }

    public String actualizarDisenio(Disenio d) {
        disenio = d;
        return "/pages/usuario/tatuador/cdisenio";
    }

    public String verDisenio(Disenio d) {
        disenio = d;
        return "/pages/disenios/disenio";
    }

    public String solicitarDisenio(Disenio d) {
        disenio = d;
        return "/pages/disenios/citas";
    }

    public List<Disenio> listarDisenio() {
        return diseniofc.findAll();
    }

    @Override
    public Disenio getObject(Integer i) {
        return diseniofc.find(i);
    }

    /*public String estiloDisenio() {
        int esti = 0;
        try {
        resultado = diseniofc.estiloDisenio(estilo);
            if (esti == 1) {
                return "/pages/disenios/estilos/caligrafia";
            } if (esti == 2){
                return "/pages/disenios/estilos/acuarela";
            } if (esti == 3){
                return "/pages/disenios/estilos/sakyant";
            } if (esti == 4){
                return "/pages/disenios/estilos/animados";
            }if (esti == 5){
                return "/pages/disenios/estilos/realismo";
            }if (esti == 6){
                return "/pages/disenios/estilos/biomecanicos";
            }if (esti == 7){
                return "/pages/disenios/estilos/henna";
            }if (esti == 8){
                return "/pages/disenios/estilos/coverup";
            }if (esti == 9){
                return "/pages/disenios/estilos/ilustracion";
            }if (esti == 10){
                return "/pages/disenios/estilos/minimalista";
            }if (esti == 11){
                return "/pages/disenios/estilos/tradicional";
            }if (esti == 12){
                return "/pages/disenios/estilos/geometricos";
            }
        } catch (Exception e) {
        }
        return "";
    }
    */
    
}
