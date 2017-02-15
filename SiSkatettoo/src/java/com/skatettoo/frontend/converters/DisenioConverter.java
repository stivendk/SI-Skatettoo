/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.frontend.converters;

import com.plandemjr.frontend.util.AbstractConverter;
import com.skatettoo.backend.persistence.entities.Disenio;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author StivenDavid
 */
@FacesConverter(forClass = Disenio.class)
public class DisenioConverter extends AbstractConverter{

    public DisenioConverter() {
        this.nombreMB = "disenioManagedBean";
    }
    
}
