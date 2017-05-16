/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import com.skatettoo.backend.persistence.facade.NoticiaFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "noticiaManagedBean")
@RequestScoped
public class NoticiaManagedBean implements Serializable {

    private Part file;
    private Noticia noti;
    @EJB
    private NoticiaFacadeLocal notifc;
    
    @Inject
    LoginManagedBean log;

    public LoginManagedBean getLog() {
        return log;
    }
            
    public NoticiaManagedBean() {
    }

    public Noticia getNoti() {
        return noti;
    }

    public void setNoti(Noticia noti) {
        this.noti = noti;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    @PostConstruct
    public void init(){
        noti = new Noticia();
    }
    
    public String publicarNoticia(){
        ResourceBundle prop = FacesUtils.getBundle("editeliBundle");
        try {
            noti.setIdUsuario(getLog().getUsuario());
            noti.setImgn(UploadFIles.uploadFileN(file, String.valueOf(noti.getTitulo())));
            notifc.create(noti);
            FacesUtils.mensaje(prop.getString("succesNoti"));
            return "/pages/tatuador/gnoticia.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje("Perra");
        }
        return "";
    }
    
    public void eliminarNoticia(Noticia n){
        ResourceBundle prop = FacesUtils.getBundle("editeliBundle");
        try {
            notifc.remove(n);
            FacesUtils.mensaje(prop.getString("deleteNoti"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti"));
        }
    }
    
    public void editarNoticia(){
        ResourceBundle prop = FacesUtils.getBundle("editeliBundle");
        try {
            noti.setIdUsuario(getLog().getUsuario());
            notifc.edit(noti);
            FacesUtils.mensaje(prop.getString("actualNoti"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti") + e.getMessage());
        }
    }
    
    public void actualizarNoticia(Noticia n){
        setNoti(n);
    }
    
    public void verNoticia(Noticia nn){
        setNoti(nn);
    }
    
    public List<Noticia> listarNoticia(){
        return notifc.findAll();
    }
    
    public List<Noticia> notiUsuario(){
        List<Noticia> notUs = new ArrayList<>();
        for (Noticia n: listarNoticia()){
            if (n.getIdUsuario().equals(log.getUsuario())){
                notUs.add(n);
            }
        } return notUs;
    }
}
