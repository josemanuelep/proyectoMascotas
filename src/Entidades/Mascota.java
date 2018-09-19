/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author palac
 */
@Entity
@Table(name = "Tienda.Mascota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mascota.findAll", query = "SELECT m FROM Mascota m")
    , @NamedQuery(name = "Mascota.findById", query = "SELECT m FROM Mascota m WHERE m.id = :id")
    , @NamedQuery(name = "Mascota.findByNombre", query = "SELECT m FROM Mascota m WHERE m.nombre = :nombre")})
public class Mascota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Nombre")
    private String nombre;
    @JoinColumn(name = "IdentCliente", referencedColumnName = "IdentCliente")
    @ManyToOne
    private Cliente identCliente;
    @JoinColumn(name = "IdRaza", referencedColumnName = "IdRaza")
    @ManyToOne
    private Raza idRaza;
    @OneToMany(mappedBy = "idMascota")
    private List<ControlPeso> controlPesoList;
    @OneToMany(mappedBy = "idMascota")
    private List<Vacunacion> vacunacionList;

    public Mascota() {
    }

    public Mascota(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Cliente getIdentCliente() {
        return identCliente;
    }

    public void setIdentCliente(Cliente identCliente) {
        this.identCliente = identCliente;
    }

    public Raza getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Raza idRaza) {
        this.idRaza = idRaza;
    }

    @XmlTransient
    public List<ControlPeso> getControlPesoList() {
        return controlPesoList;
    }

    public void setControlPesoList(List<ControlPeso> controlPesoList) {
        this.controlPesoList = controlPesoList;
    }

    @XmlTransient
    public List<Vacunacion> getVacunacionList() {
        return vacunacionList;
    }

    public void setVacunacionList(List<Vacunacion> vacunacionList) {
        this.vacunacionList = vacunacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mascota)) {
            return false;
        }
        Mascota other = (Mascota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Mascota[ id=" + id + " ]";
    }
    
}
