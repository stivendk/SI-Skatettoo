/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author APRENDIZ
 */
@Entity
@Table(name = "estado_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoUsuario.findAll", query = "SELECT e FROM EstadoUsuario e"),
    @NamedQuery(name = "EstadoUsuario.findByIdEstadoUsuario", query = "SELECT e FROM EstadoUsuario e WHERE e.idEstadoUsuario = :idEstadoUsuario"),
    @NamedQuery(name = "EstadoUsuario.findByNombreEstadoUsuario", query = "SELECT e FROM EstadoUsuario e WHERE e.nombreEstadoUsuario = :nombreEstadoUsuario")})
public class EstadoUsuario implements Serializable, IEntitie {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_usuario")
    private Integer idEstadoUsuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 33)
    @Column(name = "nombre_estado_usuario")
    private String nombreEstadoUsuario;

    public EstadoUsuario() {
    }

    public EstadoUsuario(Integer idEstadoUsuario) {
        this.idEstadoUsuario = idEstadoUsuario;
    }

    public EstadoUsuario(Integer idEstadoUsuario, String nombreEstadoUsuario) {
        this.idEstadoUsuario = idEstadoUsuario;
        this.nombreEstadoUsuario = nombreEstadoUsuario;
    }

    public Integer getIdEstadoUsuario() {
        return idEstadoUsuario;
    }

    public void setIdEstadoUsuario(Integer idEstadoUsuario) {
        this.idEstadoUsuario = idEstadoUsuario;
    }

    public String getNombreEstadoUsuario() {
        return nombreEstadoUsuario;
    }

    public void setNombreEstadoUsuario(String nombreEstadoUsuario) {
        this.nombreEstadoUsuario = nombreEstadoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoUsuario != null ? idEstadoUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoUsuario)) {
            return false;
        }
        EstadoUsuario other = (EstadoUsuario) object;
        if ((this.idEstadoUsuario == null && other.idEstadoUsuario != null) || (this.idEstadoUsuario != null && !this.idEstadoUsuario.equals(other.idEstadoUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.skatettoo.backend.persistence.entities.EstadoUsuario[ idEstadoUsuario=" + idEstadoUsuario + " ]";
    }

    @Override
    public String getId() {
        return idEstadoUsuario.toString();
    }
    
}
