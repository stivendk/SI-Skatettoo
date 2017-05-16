/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import com.skatettoo.backend.persistence.facade.NoticiaFacadeLocal;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "noticiaSessionController")
@RequestScoped
public class NoticiaSessionController {

    private Noticia noti;
    private Part file;
    @EJB
    private NoticiaFacadeLocal notifc;
    @Inject
    LoginManagedBean log;

    public LoginManagedBean getLog() {
        return log;
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

    public NoticiaSessionController() {
    }

    @PostConstruct
    public void init() {
        noti = (Noticia) FacesUtils.getObjectMapSession("noticia");
        noti = new Noticia();
    }

    public String publicarNoticia() {
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

    public void editarNoticia() {
        ResourceBundle prop = FacesUtils.getBundle("editeliBundle");
        try {
            notifc.edit(noti);
            FacesUtils.mensaje(prop.getString("actualNoti"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("errorNoti") + e.getMessage());
        }
    }
}
