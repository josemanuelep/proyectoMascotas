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
@Table(name = "Tienda.Raza")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Raza.findAll", query = "SELECT r FROM Raza r")
    , @NamedQuery(name = "Raza.findByIdRaza", query = "SELECT r FROM Raza r WHERE r.idRaza = :idRaza")
    , @NamedQuery(name = "Raza.findByRaza", query = "SELECT r FROM Raza r WHERE r.raza = :raza")})
public class Raza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IdRaza")
    private Long idRaza;
    @Column(name = "Raza")
    private String raza;
    @JoinColumn(name = "IdEspecie", referencedColumnName = "IdEspecie")
    @ManyToOne
    private Especie idEspecie;
    @OneToMany(mappedBy = "idRaza")
    private List<Mascota> mascotaList;

    public Raza() {
    }

    public Raza(Long idRaza) {
        this.idRaza = idRaza;
    }

    public Long getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(Long idRaza) {
        this.idRaza = idRaza;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Especie getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(Especie idEspecie) {
        this.idEspecie = idEspecie;
    }

    @XmlTransient
    public List<Mascota> getMascotaList() {
        return mascotaList;
    }

    public void setMascotaList(List<Mascota> mascotaList) {
        this.mascotaList = mascotaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRaza != null ? idRaza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Raza)) {
            return false;
        }
        Raza other = (Raza) object;
        if ((this.idRaza == null && other.idRaza != null) || (this.idRaza != null && !this.idRaza.equals(other.idRaza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Raza[ idRaza=" + idRaza + " ]";
    }
    
}
