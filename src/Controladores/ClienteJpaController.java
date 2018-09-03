/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Mascota;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author palac
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getMascotaList() == null) {
            cliente.setMascotaList(new ArrayList<Mascota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : cliente.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getId());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            cliente.setMascotaList(attachedMascotaList);
            em.persist(cliente);
            for (Mascota mascotaListMascota : cliente.getMascotaList()) {
                Cliente oldIdentClienteOfMascotaListMascota = mascotaListMascota.getIdentCliente();
                mascotaListMascota.setIdentCliente(cliente);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldIdentClienteOfMascotaListMascota != null) {
                    oldIdentClienteOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldIdentClienteOfMascotaListMascota = em.merge(oldIdentClienteOfMascotaListMascota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getIdentCliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdentCliente());
            List<Mascota> mascotaListOld = persistentCliente.getMascotaList();
            List<Mascota> mascotaListNew = cliente.getMascotaList();
            List<Mascota> attachedMascotaListNew = new ArrayList<Mascota>();
            for (Mascota mascotaListNewMascotaToAttach : mascotaListNew) {
                mascotaListNewMascotaToAttach = em.getReference(mascotaListNewMascotaToAttach.getClass(), mascotaListNewMascotaToAttach.getId());
                attachedMascotaListNew.add(mascotaListNewMascotaToAttach);
            }
            mascotaListNew = attachedMascotaListNew;
            cliente.setMascotaList(mascotaListNew);
            cliente = em.merge(cliente);
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    mascotaListOldMascota.setIdentCliente(null);
                    mascotaListOldMascota = em.merge(mascotaListOldMascota);
                }
            }
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Cliente oldIdentClienteOfMascotaListNewMascota = mascotaListNewMascota.getIdentCliente();
                    mascotaListNewMascota.setIdentCliente(cliente);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldIdentClienteOfMascotaListNewMascota != null && !oldIdentClienteOfMascotaListNewMascota.equals(cliente)) {
                        oldIdentClienteOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldIdentClienteOfMascotaListNewMascota = em.merge(oldIdentClienteOfMascotaListNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Double id = cliente.getIdentCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Double id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdentCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<Mascota> mascotaList = cliente.getMascotaList();
            for (Mascota mascotaListMascota : mascotaList) {
                mascotaListMascota.setIdentCliente(null);
                mascotaListMascota = em.merge(mascotaListMascota);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Double id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public List findCliente(String nombreCliente) {
        EntityManager em = getEntityManager();
        try {

            Query q = em.createNativeQuery("SELECT Cliente.NombreCliente , Cliente.IdentCliente FROM Tienda.Cliente WHERE Cliente.NombreCliente LIKE ?1", Cliente.class);
            q.setParameter(1, "%" + nombreCliente + "%");
            List<Cliente> cl = (List<Cliente>) q.getResultList();

            return cl;
        } finally {
            em.close();
        }
    }

    public List OrderBy_clientes(String parametro) {
        EntityManager em = getEntityManager();

        if (parametro.equals("nombre")) {

            try {

                Query q = em.createNativeQuery("SELECT Cliente.NombreCliente , Cliente.IdentCliente FROM Tienda.Cliente order by Cliente.NombreCliente", Cliente.class);
                List<Cliente> cl = (List<Cliente>) q.getResultList();
                return cl;

            } finally {
                em.close();
            }

        } else {

            try {

                Query q = em.createNativeQuery("SELECT Cliente.NombreCliente , Cliente.IdentCliente FROM Tienda.Cliente order by Cliente.IdentCliente", Cliente.class);
                List<Cliente> cl = (List<Cliente>) q.getResultList();
                return cl;

            } finally {
                em.close();
            }

        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
