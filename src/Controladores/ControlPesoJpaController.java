/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.ControlPeso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Mascota;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author palac
 */
public class ControlPesoJpaController implements Serializable {

    public ControlPesoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ControlPeso controlPeso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota idMascota = controlPeso.getIdMascota();
            if (idMascota != null) {
                idMascota = em.getReference(idMascota.getClass(), idMascota.getId());
                controlPeso.setIdMascota(idMascota);
            }
            em.persist(controlPeso);
            if (idMascota != null) {
                idMascota.getControlPesoList().add(controlPeso);
                idMascota = em.merge(idMascota);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findControlPeso(controlPeso.getNroControl()) != null) {
                throw new PreexistingEntityException("ControlPeso " + controlPeso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ControlPeso controlPeso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlPeso persistentControlPeso = em.find(ControlPeso.class, controlPeso.getNroControl());
            Mascota idMascotaOld = persistentControlPeso.getIdMascota();
            Mascota idMascotaNew = controlPeso.getIdMascota();
            if (idMascotaNew != null) {
                idMascotaNew = em.getReference(idMascotaNew.getClass(), idMascotaNew.getId());
                controlPeso.setIdMascota(idMascotaNew);
            }
            controlPeso = em.merge(controlPeso);
            if (idMascotaOld != null && !idMascotaOld.equals(idMascotaNew)) {
                idMascotaOld.getControlPesoList().remove(controlPeso);
                idMascotaOld = em.merge(idMascotaOld);
            }
            if (idMascotaNew != null && !idMascotaNew.equals(idMascotaOld)) {
                idMascotaNew.getControlPesoList().add(controlPeso);
                idMascotaNew = em.merge(idMascotaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = controlPeso.getNroControl();
                if (findControlPeso(id) == null) {
                    throw new NonexistentEntityException("The controlPeso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ControlPeso controlPeso;
            try {
                controlPeso = em.getReference(ControlPeso.class, id);
                controlPeso.getNroControl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The controlPeso with id " + id + " no longer exists.", enfe);
            }
            Mascota idMascota = controlPeso.getIdMascota();
            if (idMascota != null) {
                idMascota.getControlPesoList().remove(controlPeso);
                idMascota = em.merge(idMascota);
            }
            em.remove(controlPeso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ControlPeso> findControlPesoEntities() {
        return findControlPesoEntities(true, -1, -1);
    }

    public List<ControlPeso> findControlPesoEntities(int maxResults, int firstResult) {
        return findControlPesoEntities(false, maxResults, firstResult);
    }

    private List<ControlPeso> findControlPesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ControlPeso.class));
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

    public ControlPeso findControlPeso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ControlPeso.class, id);
        } finally {
            em.close();
        }
    }

    public int getControlPesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ControlPeso> rt = cq.from(ControlPeso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
