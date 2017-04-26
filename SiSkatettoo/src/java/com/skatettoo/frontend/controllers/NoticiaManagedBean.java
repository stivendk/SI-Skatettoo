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
        try {
            noti.setIdUsuario(getLog().getUsuario());
            noti.setImgn(UploadFIle.uploadFile(file, String.valueOf(noti.getImgn())));
            notifc.create(noti);
            FacesUtils.mensaje("Has publicado una nueva noticia.");
            return "/pages/tatuador/gnoticia.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje("Perra");
        }
        return "";
    }
    
    public void eliminarNoticia(Noticia n){
        try {
            notifc.remove(n);
            FacesUtils.mensaje("Se ha eliminado con exito");
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
    }
    
    public void editarNoticia(){
        try {
            notifc.edit(noti);
            FacesUtils.mensaje("Se ha actualizado con Exito");
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
    }
    
    public String actualizarNoticia(Noticia n){
        noti = n;
        FacesUtils.setObjectAcceso("noticia", n);
        return "/pages/tatuador/gnoticia1.xhtml?faces-redirect=true";
    }
    
    public String verNoticia(Noticia nn){
        noti = nn;
        FacesUtils.setObjectAcceso("noticia", nn);
        return "";
        
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
