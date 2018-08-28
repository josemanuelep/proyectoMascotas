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
import javax.persistence.Id;
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
@Table(name = "Tienda.Especie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especie.findAll", query = "SELECT e FROM Especie e")
    , @NamedQuery(name = "Especie.findByIdEspecie", query = "SELECT e FROM Especie e WHERE e.idEspecie = :idEspecie")
    , @NamedQuery(name = "Especie.findByEspecie", query = "SELECT e FROM Especie e WHERE e.especie = :especie")})
public class Especie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdEspecie")
    private Integer idEspecie;
    @Column(name = "Especie")
    private String especie;
    @OneToMany(mappedBy = "idEspecie")
    private List<Raza> razaList;

    public Especie() {
    }

    public Especie(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public Integer getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Integer idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    @XmlTransient
    public List<Raza> getRazaList() {
        return razaList;
    }

    public void setRazaList(List<Raza> razaList) {
        this.razaList = razaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEspecie != null ? idEspecie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Especie)) {
            return false;
        }
        Especie other = (Especie) object;
        if ((this.idEspecie == null && other.idEspecie != null) || (this.idEspecie != null && !this.idEspecie.equals(other.idEspecie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Especie[ idEspecie=" + idEspecie + " ]";
    }
    
}
