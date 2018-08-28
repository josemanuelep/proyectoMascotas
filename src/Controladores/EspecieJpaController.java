/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Especie;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Raza;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author palac
 */
public class EspecieJpaController implements Serializable {

    public EspecieJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Especie especie) throws PreexistingEntityException, Exception {
        if (especie.getRazaList() == null) {
            especie.setRazaList(new ArrayList<Raza>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Raza> attachedRazaList = new ArrayList<Raza>();
            for (Raza razaListRazaToAttach : especie.getRazaList()) {
                razaListRazaToAttach = em.getReference(razaListRazaToAttach.getClass(), razaListRazaToAttach.getIdRaza());
                attachedRazaList.add(razaListRazaToAttach);
            }
            especie.setRazaList(attachedRazaList);
            em.persist(especie);
            for (Raza razaListRaza : especie.getRazaList()) {
                Especie oldIdEspecieOfRazaListRaza = razaListRaza.getIdEspecie();
                razaListRaza.setIdEspecie(especie);
                razaListRaza = em.merge(razaListRaza);
                if (oldIdEspecieOfRazaListRaza != null) {
                    oldIdEspecieOfRazaListRaza.getRazaList().remove(razaListRaza);
                    oldIdEspecieOfRazaListRaza = em.merge(oldIdEspecieOfRazaListRaza);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEspecie(especie.getIdEspecie()) != null) {
                throw new PreexistingEntityException("Especie " + especie + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Especie especie) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especie persistentEspecie = em.find(Especie.class, especie.getIdEspecie());
            List<Raza> razaListOld = persistentEspecie.getRazaList();
            List<Raza> razaListNew = especie.getRazaList();
            List<Raza> attachedRazaListNew = new ArrayList<Raza>();
            for (Raza razaListNewRazaToAttach : razaListNew) {
                razaListNewRazaToAttach = em.getReference(razaListNewRazaToAttach.getClass(), razaListNewRazaToAttach.getIdRaza());
                attachedRazaListNew.add(razaListNewRazaToAttach);
            }
            razaListNew = attachedRazaListNew;
            especie.setRazaList(razaListNew);
            especie = em.merge(especie);
            for (Raza razaListOldRaza : razaListOld) {
                if (!razaListNew.contains(razaListOldRaza)) {
                    razaListOldRaza.setIdEspecie(null);
                    razaListOldRaza = em.merge(razaListOldRaza);
                }
            }
            for (Raza razaListNewRaza : razaListNew) {
                if (!razaListOld.contains(razaListNewRaza)) {
                    Especie oldIdEspecieOfRazaListNewRaza = razaListNewRaza.getIdEspecie();
                    razaListNewRaza.setIdEspecie(especie);
                    razaListNewRaza = em.merge(razaListNewRaza);
                    if (oldIdEspecieOfRazaListNewRaza != null && !oldIdEspecieOfRazaListNewRaza.equals(especie)) {
                        oldIdEspecieOfRazaListNewRaza.getRazaList().remove(razaListNewRaza);
                        oldIdEspecieOfRazaListNewRaza = em.merge(oldIdEspecieOfRazaListNewRaza);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = especie.getIdEspecie();
                if (findEspecie(id) == null) {
                    throw new NonexistentEntityException("The especie with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especie especie;
            try {
                especie = em.getReference(Especie.class, id);
                especie.getIdEspecie();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The especie with id " + id + " no longer exists.", enfe);
            }
            List<Raza> razaList = especie.getRazaList();
            for (Raza razaListRaza : razaList) {
                razaListRaza.setIdEspecie(null);
                razaListRaza = em.merge(razaListRaza);
            }
            em.remove(especie);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Especie> findEspecieEntities() {
        return findEspecieEntities(true, -1, -1);
    }

    public List<Especie> findEspecieEntities(int maxResults, int firstResult) {
        return findEspecieEntities(false, maxResults, firstResult);
    }

    private List<Especie> findEspecieEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Especie.class));
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

    public Especie findEspecie(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Especie.class, id);
        } finally {
            em.close();
        }
    }

    public int getEspecieCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Especie> rt = cq.from(Especie.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
