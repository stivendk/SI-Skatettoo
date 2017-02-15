/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.facade;

import com.skatettoo.backend.persistence.entities.RolPermiso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author StivenDavid
 */
@Local
public interface RolPermisoFacadeLocal {

    void create(RolPermiso rolPermiso);

    void edit(RolPermiso rolPermiso);

    void remove(RolPermiso rolPermiso);

    RolPermiso find(Object id);

    List<RolPermiso> findAll();

    List<RolPermiso> findRange(int[] range);

    int count();
    
}
