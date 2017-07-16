/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Noticia;
import com.skatettoo.backend.persistence.facade.NoticiaFacadeLocal;
import com.skatettoo.frontend.util.GeneradorPss;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "noticiaController")
@RequestScoped
public class NoticiaController {

    private Noticia nt;
    private Part file;
    ResourceBundle prop = FacesUtils.getBundle("controllerMsjBundle");
    @EJB
    private NoticiaFacadeLocal nfc;
    @Inject LoginManagedBean us;

    public Noticia getNt() {
        return nt;
    }

    public void setNt(Noticia nt) {
        this.nt = nt;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public LoginManagedBean getUs() {
        return us;
    }
    
    public NoticiaController() {
    }
    
    @PostConstruct
    public void init(){
        nt = new Noticia();
    }
    
    public String publicarNoticia(){
        try {
            nt.setIdUsuario(getUs().getUsuario());
            nt.setImgn(UploadFIles.uploadFileN(file, GeneradorPss.generadorPassword()));
            nfc.create(nt);
            FacesUtils.mensaje(prop.getString("pubNot"));
            return "/pages/tatuador/gnoticia.xhtml?faces-redirect=true?";
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError ") + e.getMessage());
        }
        return "";
    }
    
    public void editarNoticia(){
        try {
            nfc.edit(nt);
            FacesUtils.mensaje(prop.getString("update"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError ") + e.getMessage());
        }
    }
    public void actualizarNoticia(Noticia n){
        setNt(n);
        FacesUtils.setObjectAcceso("noticia", n);
    }
    
    public void verNoticia(Noticia n){
        setNt(n);
    }
    
    public void cogerNoticia(Noticia n){
        FacesUtils.setObjectAcceso("noticia", n);
    }
    
    public void eliminarNoticia(Noticia n){
        try {
            nfc.remove(n);
            FacesUtils.mensaje(prop.getString("delete"));
        } catch (Exception e) {
            FacesUtils.mensaje(prop.getString("msjError ") + e.getMessage());
        }
    }
    public List<Noticia> listarNoticias(){
        return nfc.findAll();
    }
    
    public List<Noticia> notiUsuario(){
        List<Noticia> notUs = new ArrayList<>();
        for (Noticia n: listarNoticias()){
            if (n.getIdUsuario().equals(us.getUsuario())){
                notUs.add(n);
            }
        } return notUs;
    }
}
