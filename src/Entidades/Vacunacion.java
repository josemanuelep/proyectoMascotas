/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author palac
 */
@Entity
@Table(name = "Tienda.Vacunacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacunacion.findAll", query = "SELECT v FROM Vacunacion v")
    , @NamedQuery(name = "Vacunacion.findByNroVacunacion", query = "SELECT v FROM Vacunacion v WHERE v.nroVacunacion = :nroVacunacion")
    , @NamedQuery(name = "Vacunacion.findByFecha", query = "SELECT v FROM Vacunacion v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Vacunacion.findByOrden", query = "SELECT v FROM Vacunacion v WHERE v.orden = :orden")})
public class Vacunacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NroVacunacion")
    private Long nroVacunacion;
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "Orden")
    private Integer orden;
    @JoinColumn(name = "IdMascota", referencedColumnName = "Id")
    @ManyToOne
    private Mascota idMascota;
    @JoinColumn(name = "IdVacuna", referencedColumnName = "Id")
    @ManyToOne
    private Vacuna idVacuna;

    public Vacunacion() {
    }

    public Vacunacion(Long nroVacunacion) {
        this.nroVacunacion = nroVacunacion;
    }

    public Long getNroVacunacion() {
        return nroVacunacion;
    }

    public void setNroVacunacion(Long nroVacunacion) {
        this.nroVacunacion = nroVacunacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Mascota getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Mascota idMascota) {
        this.idMascota = idMascota;
    }

    public Vacuna getIdVacuna() {
        return idVacuna;
    }

    public void setIdVacuna(Vacuna idVacuna) {
        this.idVacuna = idVacuna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroVacunacion != null ? nroVacunacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacunacion)) {
            return false;
        }
        Vacunacion other = (Vacunacion) object;
        if ((this.nroVacunacion == null && other.nroVacunacion != null) || (this.nroVacunacion != null && !this.nroVacunacion.equals(other.nroVacunacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vacunacion[ nroVacunacion=" + nroVacunacion + " ]";
    }
    
}
