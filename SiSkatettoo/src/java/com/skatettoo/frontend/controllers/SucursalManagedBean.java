/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.controllers;

import com.skatettoo.frontend.util.Managedbean;
import com.skatettoo.backend.persistence.entities.Localidad;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.backend.persistence.facade.SucursalFacadeLocal;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author StivenDavid
 */
@Named(value = "sucursalManagedBean")
@ConversationScoped
public class SucursalManagedBean implements Serializable, Managedbean<Sucursal> {

    private static final long serialVersionUID = 1L;
    
    private Sucursal sucu;
    private Localidad loc;
    private List<Sucursal> consulta;

    @EJB
    private SucursalFacadeLocal sucufc;
    @Inject LoginManagedBean usu;
    
    public SucursalManagedBean() {
    }

    
    public Localidad getLoc() {
        return loc;
    }

    public void setLoc(Localidad loc) {
        this.loc = loc;
    }
    public Sucursal getSucu() {
        return sucu;
    }

    public void setSucu(Sucursal sucu) {
        this.sucu = sucu;
    }

    public List<Sucursal> getConsulta() {
        return consulta;
    }

    public void setConsulta(List<Sucursal> consulta) {
        this.consulta = consulta;
    }

    public LoginManagedBean getUsu() {
        return usu;
    }

    @PostConstruct
    public void init() {
        sucu = new Sucursal();
        loc = new Localidad();
        
    }

    public void crearSucursal() {
        sucufc.create(sucu);
    }

    public void eliminarSucursal() {
        FacesUtils.setObjectAcceso("sucursal", sucu);
        sucufc.remove(sucu);
    }

    public void editarSucursal() {
        FacesUtils.setObjectAcceso("sucursal", sucu);
        sucufc.edit(sucu);
        FacesUtils.mensaje("Se ha actualizado con exito!");
    }

    public String actualizarSucursal(Sucursal s) {
        sucu = s;
        return "";
    }
    
    public void selectLocalidad(Localidad l){
        setLoc(l);
    }
    
    public List<Sucursal> sucuList(){
        return getLoc().getSucursalList();
    }
    
    

    public List<Sucursal> listarSucursal() {
        return sucufc.findAll();
    }

    @Override
    public Sucursal getObject(Integer i) {
        return sucufc.find(i);
    }

    public String verSucursal(Sucursal ss) {
        sucu = ss;
        FacesUtils.setObjectAcceso("sucursal", ss);
        return "/pages/disenios/sucurv.xhtml?faces-redirect=true";
    }
    
    public String selecSucursal(Sucursal ss) {
        sucu = ss;
        return "/pages/admin/gsucursall.xhtml?faces-redirect=true";
    }

    public List<Sucursal> miSucu() {
        List<Sucursal> sul = new ArrayList<>();
        try {
            for (Sucursal ci : listarSucursal()) {
                if (ci.getUsuarioList().equals(getUsu().getUsuario().getIdSucursal())) {
                    sul.add(ci);
                }
            }
        } catch (Exception e) {

        }
        return sul;
    }
}
