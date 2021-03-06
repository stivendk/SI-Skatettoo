/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
import com.skatettoo.frontend.util.GeneradorPss;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "panelSucursalManagedBean")
@ConversationScoped
public class PanelSucursalManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Sucursal sucu;
    private Part file;
    private Usuario tatuador;
    private Disenio dis;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    @EJB
    private SucursalFacadeLocal sfl;
    @Inject
    LoginManagedBean us;

    public LoginManagedBean getUs() {
        return us;
    }

    public Sucursal getSucu() {
        return sucu;
    }

    public void setSucu(Sucursal sucu) {
        this.sucu = sucu;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public Usuario getTatuador() {
        return tatuador;
    }

    public void setTatuador(Usuario tatuador) {
        this.tatuador = tatuador;
    }

    public Disenio getDis() {
        return dis;
    }

    public void setDis(Disenio dis) {
        this.dis = dis;
    }

    public PanelSucursalManagedBean() {
    }

    @PostConstruct
    public void init() {
        sucu = (Sucursal) FacesUtils.getObjectMapSession("sucursal");
        dis = new Disenio();
    }

    public List<Usuario> tattuadoresSurcursal() {
        return sucu.getUsuarioList();
    }

    public List<Disenio> listaDisenio() {
        try {
            return getTatuador().getDisenioList();
        } catch (Exception e) {
        }
        return null;
    }

    public String enviarSucursal() {
        if (getUs().getUsuario().getEstadoUsuario() != 2) {
            setSucu(sucu);
            FacesUtils.setObjectAcceso("sucursal", sucu);
            return "/pages/disenios/cita.xhtml?faces-redirect=true";
        } else {
            FacesUtils.mensaje(prop.getString("citNo"));
            return "";
        }
    }

    public String enviarDisenio(Disenio d) {
        dis = d;
        FacesUtils.setObjectAcceso("disenio", dis);
        return "/pages/disenios/disenio.xhtml?faces-redirect=true";
    }

    public void seleccionarTatt(Usuario t) {
        setTatuador(t);
    }

    public void selecTatt(Usuario t) {
        tatuador = t;
    }

    public List<Disenio> listUsSucu() {
        return getTatuador().getDisenioList();
    }

    public String tatuadorS(Usuario ta) {
        setTatuador(ta);
        FacesUtils.setObjectAcceso("tatuador", ta);
        return "/pages/tatuador/perfil.xhtml?faces-redirect=true";
    }
    
    public void cambiarFoto() throws NoSuchAlgorithmException{
        try {
            sucu.setFotoSuc(UploadFIles.uploadFile(file, GeneradorPss.generadorPassword()));
            sfl.edit(sucu);
            FacesUtils.mensaje("buena");
        } catch (Exception e) {
            FacesUtils.mensaje("error");
        }
        
    }
}
