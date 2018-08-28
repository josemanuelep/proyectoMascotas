/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Mascota;
import Entidades.Vacuna;
import Entidades.Vacunacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author palac
 */
public class VacunacionJpaController implements Serializable {

    public VacunacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vacunacion vacunacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota idMascota = vacunacion.getIdMascota();
            if (idMascota != null) {
                idMascota = em.getReference(idMascota.getClass(), idMascota.getId());
                vacunacion.setIdMascota(idMascota);
            }
            Vacuna idVacuna = vacunacion.getIdVacuna();
            if (idVacuna != null) {
                idVacuna = em.getReference(idVacuna.getClass(), idVacuna.getId());
                vacunacion.setIdVacuna(idVacuna);
            }
            em.persist(vacunacion);
            if (idMascota != null) {
                idMascota.getVacunacionList().add(vacunacion);
                idMascota = em.merge(idMascota);
            }
            if (idVacuna != null) {
                idVacuna.getVacunacionList().add(vacunacion);
                idVacuna = em.merge(idVacuna);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVacunacion(vacunacion.getNroVacunacion()) != null) {
                throw new PreexistingEntityException("Vacunacion " + vacunacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vacunacion vacunacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacunacion persistentVacunacion = em.find(Vacunacion.class, vacunacion.getNroVacunacion());
            Mascota idMascotaOld = persistentVacunacion.getIdMascota();
            Mascota idMascotaNew = vacunacion.getIdMascota();
            Vacuna idVacunaOld = persistentVacunacion.getIdVacuna();
            Vacuna idVacunaNew = vacunacion.getIdVacuna();
            if (idMascotaNew != null) {
                idMascotaNew = em.getReference(idMascotaNew.getClass(), idMascotaNew.getId());
                vacunacion.setIdMascota(idMascotaNew);
            }
            if (idVacunaNew != null) {
                idVacunaNew = em.getReference(idVacunaNew.getClass(), idVacunaNew.getId());
                vacunacion.setIdVacuna(idVacunaNew);
            }
            vacunacion = em.merge(vacunacion);
            if (idMascotaOld != null && !idMascotaOld.equals(idMascotaNew)) {
                idMascotaOld.getVacunacionList().remove(vacunacion);
                idMascotaOld = em.merge(idMascotaOld);
            }
            if (idMascotaNew != null && !idMascotaNew.equals(idMascotaOld)) {
                idMascotaNew.getVacunacionList().add(vacunacion);
                idMascotaNew = em.merge(idMascotaNew);
            }
            if (idVacunaOld != null && !idVacunaOld.equals(idVacunaNew)) {
                idVacunaOld.getVacunacionList().remove(vacunacion);
                idVacunaOld = em.merge(idVacunaOld);
            }
            if (idVacunaNew != null && !idVacunaNew.equals(idVacunaOld)) {
                idVacunaNew.getVacunacionList().add(vacunacion);
                idVacunaNew = em.merge(idVacunaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vacunacion.getNroVacunacion();
                if (findVacunacion(id) == null) {
                    throw new NonexistentEntityException("The vacunacion with id " + id + " no longer exists.");
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
            Vacunacion vacunacion;
            try {
                vacunacion = em.getReference(Vacunacion.class, id);
                vacunacion.getNroVacunacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vacunacion with id " + id + " no longer exists.", enfe);
            }
            Mascota idMascota = vacunacion.getIdMascota();
            if (idMascota != null) {
                idMascota.getVacunacionList().remove(vacunacion);
                idMascota = em.merge(idMascota);
            }
            Vacuna idVacuna = vacunacion.getIdVacuna();
            if (idVacuna != null) {
                idVacuna.getVacunacionList().remove(vacunacion);
                idVacuna = em.merge(idVacuna);
            }
            em.remove(vacunacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vacunacion> findVacunacionEntities() {
        return findVacunacionEntities(true, -1, -1);
    }

    public List<Vacunacion> findVacunacionEntities(int maxResults, int firstResult) {
        return findVacunacionEntities(false, maxResults, firstResult);
    }

    private List<Vacunacion> findVacunacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacunacion.class));
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

    public Vacunacion findVacunacion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacunacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getVacunacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vacunacion> rt = cq.from(Vacunacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
