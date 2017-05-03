/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.frontend.email.Email;
import com.skatettoo.backend.persistence.facade.UsuarioFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author APRENDIZ
 */
@Named(value = "registroController")
@RequestScoped
public class RegistroController {

    private Usuario us;
    private Rol rl;
    @EJB private UsuarioFacadeLocal ufc;

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }
    
    public RegistroController() {
    }
    
    public void init(){
       us = new Usuario();
    }
    
    
    public String registrarCliente(){
        try {
            Rol r = new Rol();
            r.setIdRol(1);
            us.setIdRol(r);
            ufc.create(us);
            Email e = new Email("Nuevo Usuario", getUs().getNombre() + getUs().getApellido() + "\n Bienvenido a Skatettoo espero que lo disfrutes mucho", getUs().getEmail());
            e.enviarEmail();
            return "login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
    }
    
    public String registrarTatuador(){
        try {
            Rol r = new Rol();
            r.setIdRol(2);
            us.setIdRol(r);
            ufc.create(us);
            Email e = new Email("Nuevo Tatuador", getUs().getNombre() + getUs().getApellido() + "\n Bienvenido a Skatettoo espero que lo disfrutes mucho", getUs().getEmail());
            e.enviarEmail();
            return "login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
    }
    
    public String registrarAdmin(){
        try {
            Rol r = new Rol();
            r.setIdRol(3);
            us.setIdRol(r);
            ufc.create(us);
            Email e = new Email("Nueva Sucursal", getUs().getNombre() + getUs().getApellido() + "\n Bienvenido a Skatettoo " + getUs().getIdSucursal().getNombre() +  "\n Espero que lo disfrutes mucho", getUs().getEmail());
            e.enviarEmail();
            return "login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            FacesUtils.mensaje("Ocurrio un error");
        }
        return "";
    }
}