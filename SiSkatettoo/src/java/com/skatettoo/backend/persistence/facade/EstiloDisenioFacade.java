/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.EstiloDisenio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author APRENDIZ
 */
@Stateless
public class EstiloDisenioFacade extends AbstractFacade<EstiloDisenio> implements EstiloDisenioFacadeLocal {

    @PersistenceContext(unitName = "SiSkatettooPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstiloDisenioFacade() {
        super(EstiloDisenio.class);
    }
    
}
