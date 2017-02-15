/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Disenio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author StivenDavid
 */
@Stateless
public class DisenioFacade extends AbstractFacade<Disenio> implements DisenioFacadeLocal {

    @PersistenceContext(unitName = "SkatettooSI1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisenioFacade() {
        super(Disenio.class);
    }

    /*@Override
    public List<Disenio> estiloDisenio(int estilo) {
        return em.createNamedQuery("Disenio.estilo").setParameter("id_estilo_disenio", estilo).getResultList();
    }
    */
}
