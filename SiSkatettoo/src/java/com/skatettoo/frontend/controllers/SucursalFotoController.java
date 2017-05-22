/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
import com.skatettoo.frontend.util.GeneradorPss;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "sucursalFotoController")
@RequestScoped
public class SucursalFotoController {

    public static final long serialVersionUID = 12l;
    
    private Sucursal su;
    private Part file;
    @EJB
    private SucursalFacadeLocal sfc;
    
    public SucursalFotoController() {
    }

    public Sucursal getSu() {
        return su;
    }

    public void setSu(Sucursal su) {
        this.su = su;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    @PostConstruct
    public void init(){
        su = (Sucursal) FacesUtils.getObjectMapSession("sucursal");
    }
    
    public String cambiarFoto() throws NoSuchAlgorithmException{
        try{
        su.setFotoSuc(UploadFIles.uploadFile(file, GeneradorPss.generadorPassword()));
        sfc.edit(su);
        return "/pages/admin/gsucursal.xhtml?faces-redirect=true";
        }catch(Exception e){
            FacesUtils.mensaje("asdasdasd" + e.getMessage());
        }
        return"";
    }
}
