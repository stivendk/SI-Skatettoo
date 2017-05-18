/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Disenio;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "sucursalSessionController")
@ConversationScoped
public class SucursalSessionController implements Serializable{

    private Sucursal su;
    private Usuario ta;
    private Disenio d;
    @Inject
    LoginManagedBean us;
    @Inject
    Conversation con;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    public LoginManagedBean getUs() {
        return us;
    }

    public Conversation getCon() {
        return con;
    }

    public void setCon(Conversation con) {
        this.con = con;
    }

    public Disenio getD() {
        return d;
    }

    public void setD(Disenio d) {
        this.d = d;
    }

    public Sucursal getSu() {
        return su;
    }

    public void setSu(Sucursal su) {
        this.su = su;
    }

    public Usuario getTa() {
        return ta;
    }

    public void setTa(Usuario ta) {
        this.ta = ta;
    }

    public SucursalSessionController() {
    }

    @PostConstruct
    public void init() {
        su = (Sucursal) FacesUtils.getObjectMapSession("sucursal");
    }

    public String tatuadorS(Usuario ta) {
        setTa(ta);
        FacesUtils.setObjectAcceso("tatuador", ta);
        return "/pages/tatuador/tatuador.xhtml?faces-redirect=true";
    }
    
    public String autor(Usuario us){
        setTa(us);
        FacesUtils.setObjectAcceso("tatuador", us);
        return "/pages/disenios/perfil.xhtml?faces-redirect=true";
    }
    
    public String tatuadorA(Usuario ta) {
        setTa(ta);
        FacesUtils.setObjectAcceso("tatuador", ta);
        return "/pages/admin/diseniador.xhtml?faces-redirect=true";
    }
    
    public List<Usuario> tattuadoresSurcursal() {
        return su.getUsuarioList();
    }
    
    public String enviarSucursal(){
        if(getUs().getUsuario().getEstadoUsuario()!=2){
            setSu(su);
            FacesUtils.setObjectAcceso("sucursal", su);
            return "/pages/disenios/cita.xhtml?faces-redirect=true";
        } else{
            FacesUtils.mensaje(prop.getString("citNo"));
            return "";
        }
    }
    
}
