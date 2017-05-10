/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Sucursal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author APRENDIZ
 */
@Stateless
public class SucursalFacade extends AbstractFacade<Sucursal> implements SucursalFacadeLocal {

    @PersistenceContext(unitName = "SiSkatettooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SucursalFacade() {
        super(Sucursal.class);
    }

    @Override
    public List<Sucursal> consuSuc(String pin) {
        return em.createNamedQuery("Sucursal.findByPinSucursal").setParameter("pin", pin).getResultList();
    }

    @Override
    public Sucursal consultarSuc(Sucursal su) {
        try {
            return getEntityManager().createNamedQuery("Sucursal.findByPin", Sucursal.class).setParameter("pín", su.getPin()).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
