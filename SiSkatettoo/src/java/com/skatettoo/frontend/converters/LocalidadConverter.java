/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.converters;

import com.plandemjr.frontend.util.AbstractConverter;
import com.skatettoo.backend.persistence.entities.Localidad;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author StivenDavid
 */
@FacesConverter(forClass = Localidad.class)
public class LocalidadConverter extends AbstractConverter{

    public LocalidadConverter() {
        this.nombreMB = "localidadManagedBean";
    }
    
}
