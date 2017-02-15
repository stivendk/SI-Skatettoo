/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author StivenDavid
 */
@Entity
@Table(name = "estado_cita")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoCita.findAll", query = "SELECT e FROM EstadoCita e"),
    @NamedQuery(name = "EstadoCita.findByIdEstadoCita", query = "SELECT e FROM EstadoCita e WHERE e.idEstadoCita = :idEstadoCita"),
    @NamedQuery(name = "EstadoCita.findByNombreEstadoCita", query = "SELECT e FROM EstadoCita e WHERE e.nombreEstadoCita = :nombreEstadoCita")})
public class EstadoCita implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_cita")
    private Integer idEstadoCita;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombre_estado_cita")
    private String nombreEstadoCita;
    @OneToMany(mappedBy = "idEstadoCita", fetch = FetchType.LAZY)
    private List<Cita> citaList;

    public EstadoCita() {
    }

    public EstadoCita(Integer idEstadoCita) {
        this.idEstadoCita = idEstadoCita;
    }

    public EstadoCita(Integer idEstadoCita, String nombreEstadoCita) {
        this.idEstadoCita = idEstadoCita;
        this.nombreEstadoCita = nombreEstadoCita;
    }

    public Integer getIdEstadoCita() {
        return idEstadoCita;
    }

    public void setIdEstadoCita(Integer idEstadoCita) {
        this.idEstadoCita = idEstadoCita;
    }

    public String getNombreEstadoCita() {
        return nombreEstadoCita;
    }

    public void setNombreEstadoCita(String nombreEstadoCita) {
        this.nombreEstadoCita = nombreEstadoCita;
    }

    @XmlTransient
    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadoCita != null ? idEstadoCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoCita)) {
            return false;
        }
        EstadoCita other = (EstadoCita) object;
        if ((this.idEstadoCita == null && other.idEstadoCita != null) || (this.idEstadoCita != null && !this.idEstadoCita.equals(other.idEstadoCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.skatettoo.backend.persistence.entities.EstadoCita[ idEstadoCita=" + idEstadoCita + " ]";
    }
    
}
