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
@Table(name = "Tienda.Vacuna")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacuna.findAll", query = "SELECT v FROM Vacuna v")
    , @NamedQuery(name = "Vacuna.findById", query = "SELECT v FROM Vacuna v WHERE v.id = :id")
    , @NamedQuery(name = "Vacuna.findByVacuna", query = "SELECT v FROM Vacuna v WHERE v.vacuna = :vacuna")})
public class Vacuna implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Long id;
    @Column(name = "Vacuna")
    private String vacuna;
    @OneToMany(mappedBy = "idVacuna")
    private List<Vacunacion> vacunacionList;

    public Vacuna() {
    }

    public Vacuna(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
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
        if (!(object instanceof Vacuna)) {
            return false;
        }
        Vacuna other = (Vacuna) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Vacuna[ id=" + id + " ]";
    }
    
}
