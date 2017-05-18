/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.facade.DisenioFacadeLocal;
import com.skatettoo.frontend.util.GeneradorPss;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "disenioManagedBean")
@RequestScoped
public class DisenioManagedBean implements Serializable, Managedbean<Disenio> {

    private Part file;
    private Disenio disenio;
    @EJB
    private DisenioFacadeLocal diseniofc;
    private List<Disenio> resultado;
    ResourceBundle prop = FacesUtils.getBundle("editeliBundle");

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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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
            disenio.setNombreDisenio(UploadFIles.uploadFile(file, GeneradorPss.generadorPassword()));
            diseniofc.create(disenio);
            FacesUtils.mensaje(prop.getString("newDis"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti") + e.getMessage());
        }
    }

    public void eliminarDisenioA(Disenio d) {
        try {
            diseniofc.remove(d);
            FacesUtils.mensaje(prop.getString("deleteNoti"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti"));
        }
    }

    public void eliminarDisenio(Disenio d) {
        try {
            diseniofc.remove(d);
            FacesUtils.mensaje(prop.getString("deleteNoti"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti"));
        }
    }

    public void editarDisenio() {
        try {
            diseniofc.edit(disenio);
            FacesUtils.mensaje(prop.getString("actualNoti"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti"));
        }
    }

    public void actualizarDisenio(Disenio d) {
        setDisenio(d);
        FacesUtils.setObjectAcceso("disenio", d);
    }

    public String actualizarDisenioA(Disenio d) {
        disenio = d;
        FacesUtils.setObjectAcceso("disenio", d);
        return "/pages/admin/cdisenio.xhtml?faces-redirect=true";
    }

    public String verDisenio(Disenio d) {
        disenio = d;
        FacesUtils.setObjectAcceso("disenio", d);
        return "/pages/disenios/disenio.xhtml?faces-redirect=true";
    }

    public void verDisenio2(Disenio d) {
        setDisenio(d);
        FacesUtils.setObjectAcceso("disenio", d);
    }

    public String solicitarDisenio(Disenio d) {
        disenio = d;
        return "/pages/disenios/citas";
    }

    public String selecDisenio(Disenio d) {
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
            if (di.getIdUsuario().equals(us.getUsuario())) {
                dis.add(di);
            }
        }
        return dis;
    }

    public List<Disenio> disenioEst() {
        List<Disenio> dis = new ArrayList<>();
        for (Disenio di : listarDisenio()) {
            if (di.getIdEstiloDisenio().equals(est.getEstil())) {
                dis.add(di);
            }
        }
        return dis;
    }

}
