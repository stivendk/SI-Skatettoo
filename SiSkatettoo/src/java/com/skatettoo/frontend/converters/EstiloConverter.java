/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.converters;

import com.skatettoo.frontend.util.AbstractConverter;
import com.skatettoo.backend.persistence.entities.EstiloDisenio;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author StivenDavid
 */
@FacesConverter(forClass = EstiloDisenio.class)
public class EstiloConverter extends AbstractConverter{

    public EstiloConverter() {
        this.nombreMB = "estiloManagedBean";
    }
    
}
