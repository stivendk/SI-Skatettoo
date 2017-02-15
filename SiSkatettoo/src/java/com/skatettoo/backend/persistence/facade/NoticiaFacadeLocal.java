/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.Noticia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StivenDavid
 */
@Local
public interface NoticiaFacadeLocal {

    void create(Noticia noticia);

    void edit(Noticia noticia);

    void remove(Noticia noticia);

    Noticia find(Object id);

    List<Noticia> findAll();

    List<Noticia> findRange(int[] range);

    int count();
    
}
