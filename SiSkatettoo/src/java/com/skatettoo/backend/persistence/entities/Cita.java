/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skatettoo.backend.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author StivenDavid
 */
@Entity
@Table(name = "citas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findByIdCita", query = "SELECT c FROM Cita c WHERE c.idCita = :idCita"),
    @NamedQuery(name = "Cita.findByValorTatuaje", query = "SELECT c FROM Cita c WHERE c.valorTatuaje = :valorTatuaje"),
    @NamedQuery(name = "Cita.findByFechaHora", query = "SELECT c FROM Cita c WHERE c.fechaHora = :fechaHora")})
public class Cita implements Serializable {

    @Lob
    @Column(name = "disenioAdjunto")
    private byte[] disenioAdjunto;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cita")
    private Integer idCita;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "valor_tatuaje")
    private Integer valorTatuaje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario idUsuario;
    @JoinColumn(name = "id_disenio", referencedColumnName = "id_disenio")
    @ManyToOne(fetch = FetchType.LAZY)
    private Disenio idDisenio;
    @JoinColumn(name = "id_estado_cita", referencedColumnName = "id_estado_cita")
    @ManyToOne(fetch = FetchType.LAZY)
    private EstadoCita idEstadoCita;
    @JoinColumn(name = "id_sucursal", referencedColumnName = "id_sucursal")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Sucursal idSucursal;

    public Cita() {
    }

    public Cita(Integer idCita) {
        this.idCita = idCita;
    }

    public Cita(Integer idCita, String descripcion, Date fechaHora) {
        this.idCita = idCita;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValorTatuaje() {
        return valorTatuaje;
    }

    public void setValorTatuaje(Integer valorTatuaje) {
        this.valorTatuaje = valorTatuaje;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }


    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Disenio getIdDisenio() {
        return idDisenio;
    }

    public void setIdDisenio(Disenio idDisenio) {
        this.idDisenio = idDisenio;
    }

    public EstadoCita getIdEstadoCita() {
        return idEstadoCita;
    }

    public void setIdEstadoCita(EstadoCita idEstadoCita) {
        this.idEstadoCita = idEstadoCita;
    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.skatettoo.backend.persistence.entities.Cita[ idCita=" + idCita + " ]";
    }

    public byte[] getDisenioAdjunto() {
        return disenioAdjunto;
    }

    public void setDisenioAdjunto(byte[] disenioAdjunto) {
        this.disenioAdjunto = disenioAdjunto;
    }
    
}
