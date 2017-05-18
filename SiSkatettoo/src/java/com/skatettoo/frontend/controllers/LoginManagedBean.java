 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Permiso;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import com.skatettoo.frontend.util.GeneradorPss;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "loginManagedBean")
@SessionScoped
public class LoginManagedBean implements Serializable {

    private Usuario usuario;
    private Part file;
    private List<Permiso> permis;
    private Sucursal suc;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    @Inject SucursalManagedBean su;
    @EJB UsuarioFacadeLocal usfc;

    public Sucursal getSuc() {
        return suc;
    }

    public void setSuc(Sucursal suc) {
        this.suc = suc;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    public LoginManagedBean() {
    }

    public SucursalManagedBean getSu() {
        return su;
    }

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Permiso> getPermis() {       
        return permis;
    }    
    
    @PostConstruct
    public void init() {
        usuario = (Usuario) FacesUtils.getObjectMapSession("usuario");
        permis = getUsuario().getIdRol().getPermisoList();
        
    }

    public void modificarUs(){
        try {
            usuario.setFotoPerfil(UploadFIles.uploadFileU(file, GeneradorPss.generadorPassword()));
            usfc.edit(usuario);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Se a guardado satisfactoriamente!", "Se modifico"));
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error" + " " + e.getMessage());
        }
    }
    
    public List<Usuario> listE(){
        return usuario.getIdSucursal().getUsuarioList();
    }
    
    public String actualizarSucursal() {
        setSuc(usuario.getIdSucursal());
        return "/pages/admin/gsucursall.xhtml?faces-redirect=true";
    }
    
    public void editarSucursal(){
        try {
            su.editarSucursal();
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("succes"));
        }
    }
    
    
    public String gestSurcursal() {
        usuario.setIdSucursal(suc);
        FacesUtils.setObjectAcceso("sucursal", suc);
        return "/pages/admin/gsucursall?faces-redirect=true";
    }
    
    public void validatePassword(ComponentSystemEvent event) {

        FacesContext fc = FacesContext.getCurrentInstance();

        UIComponent components = event.getComponent();

        // get password
        UIInput uiInputPassword = (UIInput) components.findComponent("password");
        String password = uiInputPassword.getLocalValue() == null ? ""
                : uiInputPassword.getLocalValue().toString();
        String passwordId = uiInputPassword.getClientId();

        // get confirm password
        UIInput uiInputConfirmPassword = (UIInput) components.findComponent("confirmPassword");
        String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
                : uiInputConfirmPassword.getLocalValue().toString();

        // Let required="true" do its job.
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            return;
        }

        if (!password.equals(confirmPassword)) {

            FacesMessage msg = new FacesMessage(prop.getString("errorPass"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            fc.addMessage(passwordId, msg);
            fc.renderResponse();

        }

    }
    
    public String cerrarSesion() {
        FacesUtils.removerObjectAcceso("usuario");
        FacesUtils.removerObjectAcceso("tatuador");
        FacesUtils.removerObjectAcceso("sucursal");
        FacesUtils.removerObjectAcceso("estilo");
        FacesUtils.removerObjectAcceso("disenio");
        FacesUtils.removerObjectAcceso("localidad");
        FacesUtils.removerObjectAcceso("noticia");
        FacesUtils.removerObjectAcceso("cita");
        usuario = null;
        return "/index.xhtml?faces-redirect=true";
    }
    
}
