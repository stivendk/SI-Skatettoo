/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author StivenDavid
 */
@Named(value = "uploadFile")
@SessionScoped
public class UploadFile implements Serializable {

    private Part file;
    private String nombre;
    private String pathReal;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPathReal() {
        return pathReal;
    }

    public void setPathReal(String pathReal) {
        this.pathReal = pathReal;
    }

    public UploadFile() {
    }

    public String upload(Part file) {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img");
        path = path.substring(0, path.indexOf("\\build"));
        path = path + "\\web\\img\\";
        try {
            this.nombre = file.getSubmittedFileName();
            pathReal = "/SISkatettoo/img/" + nombre;
            path = path + this.nombre;
            InputStream in = file.getInputStream();

            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
             
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
