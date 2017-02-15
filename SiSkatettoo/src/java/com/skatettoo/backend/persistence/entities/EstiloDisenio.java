/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "estilo_disenio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstiloDisenio.findAll", query = "SELECT e FROM EstiloDisenio e"),
    @NamedQuery(name = "EstiloDisenio.findByIdEstiloDisenio", query = "SELECT e FROM EstiloDisenio e WHERE e.idEstiloDisenio = :idEstiloDisenio"),
    @NamedQuery(name = "EstiloDisenio.findByNombreEstilo", query = "SELECT e FROM EstiloDisenio e WHERE e.nombreEstilo = :nombreEstilo")})
public class EstiloDisenio implements Serializable, IEntitie {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estilo_disenio")
    private Integer idEstiloDisenio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_estilo")
    private String nombreEstilo;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion_estilo")
    private String descripcionEstilo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstiloDisenio", fetch = FetchType.LAZY)
    private List<Disenio> disenioList;

    public EstiloDisenio() {
    }

    public EstiloDisenio(Integer idEstiloDisenio) {
        this.idEstiloDisenio = idEstiloDisenio;
    }

    public EstiloDisenio(Integer idEstiloDisenio, String nombreEstilo, String descripcionEstilo) {
        this.idEstiloDisenio = idEstiloDisenio;
        this.nombreEstilo = nombreEstilo;
        this.descripcionEstilo = descripcionEstilo;
    }

    public Integer getIdEstiloDisenio() {
        return idEstiloDisenio;
    }

    public void setIdEstiloDisenio(Integer idEstiloDisenio) {
        this.idEstiloDisenio = idEstiloDisenio;
    }

    public String getNombreEstilo() {
        return nombreEstilo;
    }

    public void setNombreEstilo(String nombreEstilo) {
        this.nombreEstilo = nombreEstilo;
    }

    public String getDescripcionEstilo() {
        return descripcionEstilo;
    }

    public void setDescripcionEstilo(String descripcionEstilo) {
        this.descripcionEstilo = descripcionEstilo;
    }

    @XmlTransient
    public List<Disenio> getDisenioList() {
        return disenioList;
    }

    public void setDisenioList(List<Disenio> disenioList) {
        this.disenioList = disenioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstiloDisenio != null ? idEstiloDisenio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstiloDisenio)) {
            return false;
        }
        EstiloDisenio other = (EstiloDisenio) object;
        if ((this.idEstiloDisenio == null && other.idEstiloDisenio != null) || (this.idEstiloDisenio != null && !this.idEstiloDisenio.equals(other.idEstiloDisenio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.skatettoo.backend.persistence.entities.EstiloDisenio[ idEstiloDisenio=" + idEstiloDisenio + " ]";
    }

    @Override
    public String getId() {
        return idEstiloDisenio.toString();
    }
    
}
