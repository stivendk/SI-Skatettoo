/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author SENA
 */
public class UploadFIles {

    public static void uploadFile() {
    }

    public static String upload(Part file) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img");
            if (path.substring(0, path.indexOf("/build")) == null) {
                path:
                path.substring(0, path.indexOf("\\build"));
                path = path + "\\web\\img\\";
            } else {
                path = path.substring(0, path.indexOf("/build"));
                path = path + "/web/img/";
            }
            String nombre = file.getSubmittedFileName();
            String pathReal = "/SISkatettoo/img/" + nombre;
            path = path + nombre;
            InputStream in = file.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
            return pathReal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String uploadFile(Part file, String d) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img");
            path = path.substring(0, path.indexOf("\\build"));
            path = path + "\\web\\img\\";
            String[] h = file.getContentType().split("/");
            String pathReal = "img/" + d + "." + h[h.length - 1];
            System.out.println(pathReal);
            path = path + d + "." + h[h.length - 1];
            InputStream in = file.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
            return pathReal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String uploadFileU(Part file, String d) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("usuario");
            path = path.substring(0, path.indexOf("\\build"));
            path = path + "\\web\\img\\usuario\\";
            String[] h = file.getContentType().split("/");
            String pathReal = "img/usuario/" + d + "." + h[h.length - 1];
            System.out.println(pathReal);
            path = path + d + "." + h[h.length - 1];
            InputStream in = file.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
            return pathReal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String uploadFileN(Part file, String d) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img/noticias");
            path = path.substring(0, path.indexOf("\\build"));
            path = path + "\\web\\img\\noticias\\";
            String[] h = file.getContentType().split("/");
            String pathReal = "img/noticias/" + d + "." + h[h.length - 1];
            System.out.println(pathReal);
            path = path + d + "." + h[h.length - 1];
            InputStream in = file.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
            return pathReal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String uploadFileC(Part file, String d) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img/citas");
            path = path.substring(0, path.indexOf("\\build"));
            path = path + "\\web\\img\\citas\\";
            String[] h = file.getContentType().split("/");
            String pathReal = "img/citas/" + d + "." + h[h.length - 1];
            System.out.println(pathReal);
            path = path + d + "." + h[h.length - 1];
            InputStream in = file.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
            return pathReal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
    
    public static String uploadFileS(Part file, String d) {
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("img/sucursal");
            path = path.substring(0, path.indexOf("\\build"));
            path = path + "\\web\\img\\sucursal\\";
            String[] h = file.getContentType().split("/");
            String pathReal = "img/sucursal/" + d + "." + h[h.length - 1];
            System.out.println(pathReal);
            path = path + d + "." + h[h.length - 1];
            InputStream in = file.getInputStream();
            byte[] data = new byte[in.available()];
            in.read(data);
            FileOutputStream out = new FileOutputStream(new File(path));
            out.write(data);
            in.close();
            out.close();
            return pathReal;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
