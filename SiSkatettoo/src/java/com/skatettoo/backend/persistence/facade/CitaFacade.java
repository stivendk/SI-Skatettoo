/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Cita;
import com.skatettoo.backend.persistence.entities.Usuario;
import com.skatettoo.backend.persistence.entities.Sucursal;
import com.skatettoo.frontend.controllers.FacesUtils;
import com.skatettoo.frontend.controllers.LoginManagedBean;
import com.skatettoo.frontend.controllers.PanelSucursalManagedBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author APRENDIZ
 */
@Stateless
public class CitaFacade extends AbstractFacade<Cita> implements CitaFacadeLocal {

    @PersistenceContext(unitName = "SiSkatettooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitaFacade() {
        super(Cita.class);
    }
    
    @Override
    public String crearCita(Usuario cliente, Usuario tatuador, Cita c, Sucursal s){
        try {
            if(cliente.getEstadoUsuario() != 2){
                c.setIdUsuario(cliente);
                c.setIdSucursal(s);
                c.setEstadoCita((short)1);
                cliente.setEstadoUsuario((short)2);
                getEntityManager().merge(cliente);
            } else {
                FacesUtils.mensaje("Ya tienes una cita en proceso");
            }
        } catch (Exception e) {
            throw e;
        }
        return "";
    }

}
