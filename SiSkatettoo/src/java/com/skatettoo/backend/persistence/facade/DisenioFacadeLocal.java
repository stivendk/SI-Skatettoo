/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Disenio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author APRENDIZ
 */
@Local
public interface DisenioFacadeLocal {

    void create(Disenio disenio);

    void edit(Disenio disenio);

    void remove(Disenio disenio);

    Disenio find(Object id);

    List<Disenio> findAll();

    List<Disenio> findRange(int[] range);

    int count();
    
}
