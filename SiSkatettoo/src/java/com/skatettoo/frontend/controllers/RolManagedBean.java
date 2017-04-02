/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Rol;
import com.skatettoo.backend.persistence.facade.RolFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author StivenDavid
 */
@Named(value = "rolManagedBean")
@SessionScoped
public class RolManagedBean implements Serializable, Managedbean <Rol> {

    private Rol rol;
    @EJB
    private RolFacadeLocal rolfc;
            
    public RolManagedBean() {
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    @PostConstruct
    public void init(){
        rol = new Rol();
    }
    
    public List<Rol> listarRol(){
        return rolfc.findAll();
    }

    @Override
    public Rol getObject(Integer i) {
        return rolfc.find(i);
    }
}
