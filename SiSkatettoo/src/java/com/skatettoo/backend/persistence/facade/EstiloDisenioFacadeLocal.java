/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.EstiloDisenio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StivenDavid
 */
@Local
public interface EstiloDisenioFacadeLocal {

    void create(EstiloDisenio estiloDisenio);

    void edit(EstiloDisenio estiloDisenio);

    void remove(EstiloDisenio estiloDisenio);

    EstiloDisenio find(Object id);

    List<EstiloDisenio> findAll();

    List<EstiloDisenio> findRange(int[] range);

    int count();
    
}
