/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.RolPermiso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author APRENDIZ
 */
@Stateless
public class RolPermisoFacade extends AbstractFacade<RolPermiso> implements RolPermisoFacadeLocal {

    @PersistenceContext(unitName = "SiSkatettooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolPermisoFacade() {
        super(RolPermiso.class);
    }
    
}
