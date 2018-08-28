/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Vacuna;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Vacunacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author palac
 */
public class VacunaJpaController implements Serializable {

    public VacunaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vacuna vacuna) throws PreexistingEntityException, Exception {
        if (vacuna.getVacunacionList() == null) {
            vacuna.setVacunacionList(new ArrayList<Vacunacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Vacunacion> attachedVacunacionList = new ArrayList<Vacunacion>();
            for (Vacunacion vacunacionListVacunacionToAttach : vacuna.getVacunacionList()) {
                vacunacionListVacunacionToAttach = em.getReference(vacunacionListVacunacionToAttach.getClass(), vacunacionListVacunacionToAttach.getNroVacunacion());
                attachedVacunacionList.add(vacunacionListVacunacionToAttach);
            }
            vacuna.setVacunacionList(attachedVacunacionList);
            em.persist(vacuna);
            for (Vacunacion vacunacionListVacunacion : vacuna.getVacunacionList()) {
                Vacuna oldIdVacunaOfVacunacionListVacunacion = vacunacionListVacunacion.getIdVacuna();
                vacunacionListVacunacion.setIdVacuna(vacuna);
                vacunacionListVacunacion = em.merge(vacunacionListVacunacion);
                if (oldIdVacunaOfVacunacionListVacunacion != null) {
                    oldIdVacunaOfVacunacionListVacunacion.getVacunacionList().remove(vacunacionListVacunacion);
                    oldIdVacunaOfVacunacionListVacunacion = em.merge(oldIdVacunaOfVacunacionListVacunacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVacuna(vacuna.getId()) != null) {
                throw new PreexistingEntityException("Vacuna " + vacuna + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vacuna vacuna) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vacuna persistentVacuna = em.find(Vacuna.class, vacuna.getId());
            List<Vacunacion> vacunacionListOld = persistentVacuna.getVacunacionList();
            List<Vacunacion> vacunacionListNew = vacuna.getVacunacionList();
            List<Vacunacion> attachedVacunacionListNew = new ArrayList<Vacunacion>();
            for (Vacunacion vacunacionListNewVacunacionToAttach : vacunacionListNew) {
                vacunacionListNewVacunacionToAttach = em.getReference(vacunacionListNewVacunacionToAttach.getClass(), vacunacionListNewVacunacionToAttach.getNroVacunacion());
                attachedVacunacionListNew.add(vacunacionListNewVacunacionToAttach);
            }
            vacunacionListNew = attachedVacunacionListNew;
            vacuna.setVacunacionList(vacunacionListNew);
            vacuna = em.merge(vacuna);
            for (Vacunacion vacunacionListOldVacunacion : vacunacionListOld) {
                if (!vacunacionListNew.contains(vacunacionListOldVacunacion)) {
                    vacunacionListOldVacunacion.setIdVacuna(null);
                    vacunacionListOldVacunacion = em.merge(vacunacionListOldVacunacion);
                }
            }
            for (Vacunacion vacunacionListNewVacunacion : vacunacionListNew) {
                if (!vacunacionListOld.contains(vacunacionListNewVacunacion)) {
                    Vacuna oldIdVacunaOfVacunacionListNewVacunacion = vacunacionListNewVacunacion.getIdVacuna();
                    vacunacionListNewVacunacion.setIdVacuna(vacuna);
                    vacunacionListNewVacunacion = em.merge(vacunacionListNewVacunacion);
                    if (oldIdVacunaOfVacunacionListNewVacunacion != null && !oldIdVacunaOfVacunacionListNewVacunacion.equals(vacuna)) {
                        oldIdVacunaOfVacunacionListNewVacunacion.getVacunacionList().remove(vacunacionListNewVacunacion);
                        oldIdVacunaOfVacunacionListNewVacunacion = em.merge(oldIdVacunaOfVacunacionListNewVacunacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vacuna.getId();
                if (findVacuna(id) == null) {
                    throw new NonexistentEntityException("The vacuna with id " + id + " no longer exists.");
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
            Vacuna vacuna;
            try {
                vacuna = em.getReference(Vacuna.class, id);
                vacuna.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vacuna with id " + id + " no longer exists.", enfe);
            }
            List<Vacunacion> vacunacionList = vacuna.getVacunacionList();
            for (Vacunacion vacunacionListVacunacion : vacunacionList) {
                vacunacionListVacunacion.setIdVacuna(null);
                vacunacionListVacunacion = em.merge(vacunacionListVacunacion);
            }
            em.remove(vacuna);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vacuna> findVacunaEntities() {
        return findVacunaEntities(true, -1, -1);
    }

    public List<Vacuna> findVacunaEntities(int maxResults, int firstResult) {
        return findVacunaEntities(false, maxResults, firstResult);
    }

    private List<Vacuna> findVacunaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vacuna.class));
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

    public Vacuna findVacuna(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vacuna.class, id);
        } finally {
            em.close();
        }
    }

    public int getVacunaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vacuna> rt = cq.from(Vacuna.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
