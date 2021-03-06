/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Localidad;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
import com.skatettoo.frontend.util.GeneradorPss;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "sucursalManagedBean")
@ConversationScoped
public class SucursalManagedBean implements Serializable, Managedbean<Sucursal> {

    private static final long serialVersionUID = 1L;

    private Usuario us;
    private Sucursal sucu;
    private Part file;
    private Localidad loc;
    private String pin;
    private List<Sucursal> consulta;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    @EJB
    private SucursalFacadeLocal sucufc;
    @Inject
    LoginManagedBean usu;
    @Inject
    CitaManagedBean cit;

    public SucursalManagedBean() {
    }

    public Localidad getLoc() {
        return loc;
    }

    public void setLoc(Localidad loc) {
        this.loc = loc;
    }

    public Sucursal getSucu() {
        return sucu;
    }

    public void setSucu(Sucursal sucu) {
        this.sucu = sucu;
    }

    public void setCit(CitaManagedBean cit) {
        this.cit = cit;
    }

    public List<Sucursal> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Sucursal> consulta) {
        this.consulta = consulta;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public LoginManagedBean getUsu() {
        return usu;
    }

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @PostConstruct
    public void init() {
        sucu = new Sucursal();
        loc = new Localidad();

    }

    public void crearSucursal() {
        sucufc.create(sucu);
    }

    public void eliminarSucursal() {
        FacesUtils.setObjectAcceso("sucursal", sucu);
        sucufc.remove(sucu);
    }

    public void editarSucursal() throws NoSuchAlgorithmException {
        sucu.setFotoSuc(UploadFIles.uploadFileS(file, GeneradorPss.generadorPassword()));
        sucufc.edit(sucu);
        FacesUtils.mensaje(prop.getString("update"));
    }

    public void erSucursal() {
        FacesUtils.setObjectAcceso("sucursal", sucu);
        sucufc.edit(sucu);
        FacesUtils.mensaje(prop.getString("update"));
    }

    public void selectLocalidad(Localidad l) {
        setLoc(l);
    }

    public List<Sucursal> sucuList() {
        return getLoc().getSucursalList();
    }

    public List<Sucursal> listarSucursal() {
        return sucufc.findAll();
    }

    @Override
    public Sucursal getObject(Integer i) {
        return sucufc.find(i);
    }

    public String verSucursal(Sucursal ss) {
        sucu = ss;
        FacesUtils.setObjectAcceso("sucursal", ss);
        return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
    }

    public String selecSucursal(Sucursal ss) {
        sucu = ss;
        return "/pages/admin/gsucursall.xhtml?faces-redirect=true";
    }

    public List<Sucursal> miSucu() {
        List<Sucursal> sul = new ArrayList<>();
        try {
            for (Sucursal ci : listarSucursal()) {
                if (ci.getIdSucursal().equals(usu.getUsuario().getIdUsuario())) {
                    sul.add(ci);
                }
            }
        } catch (Exception e) {

        }
        return sul;
    }

    public String pinSucursal() {
        consulta = sucufc.consuSuc(pin);
        return "newxhtml1.xhtml?faces-redirect=true";
    }

    public String enviarSucur(Sucursal suc) {
        setSucu(suc);
        return "newxhtml.xhtml?faces-redirect=true";
    }
    
    
}
