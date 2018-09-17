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
@Table(name = "Tienda.Cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByIdentCliente", query = "SELECT c FROM Cliente c WHERE c.identCliente = :identCliente")
    , @NamedQuery(name = "Cliente.findByNombreCliente", query = "SELECT c FROM Cliente c WHERE c.nombreCliente = :nombreCliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "IdentCliente")
    private Float identCliente;
    @Basic(optional = false)
    @Column(name = "NombreCliente")
    private String nombreCliente;
    @OneToMany(mappedBy = "identCliente")
    private List<Mascota> mascotaList;

    public Cliente() {
    }

    public Cliente(Float identCliente) {
        this.identCliente = identCliente;
    }

    public Cliente(Float identCliente, String nombreCliente) {
        this.identCliente = identCliente;
        this.nombreCliente = nombreCliente;
    }

    public Float getIdentCliente() {
        return identCliente;
    }

    public void setIdentCliente(Float identCliente) {
        this.identCliente = identCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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
        hash += (identCliente != null ? identCliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.identCliente == null && other.identCliente != null) || (this.identCliente != null && !this.identCliente.equals(other.identCliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cliente[ identCliente=" + identCliente + " ]";
    }
    
}
