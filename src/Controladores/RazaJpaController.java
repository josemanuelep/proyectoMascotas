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
import Entidades.Especie;
import Entidades.Mascota;
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
public class RazaJpaController implements Serializable {

    public RazaJpaController() {
        
        this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Raza raza) throws PreexistingEntityException, Exception {
        if (raza.getMascotaList() == null) {
            raza.setMascotaList(new ArrayList<Mascota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Especie idEspecie = raza.getIdEspecie();
            if (idEspecie != null) {
                idEspecie = em.getReference(idEspecie.getClass(), idEspecie.getIdEspecie());
                raza.setIdEspecie(idEspecie);
            }
            List<Mascota> attachedMascotaList = new ArrayList<Mascota>();
            for (Mascota mascotaListMascotaToAttach : raza.getMascotaList()) {
                mascotaListMascotaToAttach = em.getReference(mascotaListMascotaToAttach.getClass(), mascotaListMascotaToAttach.getId());
                attachedMascotaList.add(mascotaListMascotaToAttach);
            }
            raza.setMascotaList(attachedMascotaList);
            em.persist(raza);
            if (idEspecie != null) {
                idEspecie.getRazaList().add(raza);
                idEspecie = em.merge(idEspecie);
            }
            for (Mascota mascotaListMascota : raza.getMascotaList()) {
                Raza oldIdRazaOfMascotaListMascota = mascotaListMascota.getIdRaza();
                mascotaListMascota.setIdRaza(raza);
                mascotaListMascota = em.merge(mascotaListMascota);
                if (oldIdRazaOfMascotaListMascota != null) {
                    oldIdRazaOfMascotaListMascota.getMascotaList().remove(mascotaListMascota);
                    oldIdRazaOfMascotaListMascota = em.merge(oldIdRazaOfMascotaListMascota);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRaza(raza.getIdRaza()) != null) {
                throw new PreexistingEntityException("Raza " + raza + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Raza raza) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Raza persistentRaza = em.find(Raza.class, raza.getIdRaza());
            Especie idEspecieOld = persistentRaza.getIdEspecie();
            Especie idEspecieNew = raza.getIdEspecie();
            List<Mascota> mascotaListOld = persistentRaza.getMascotaList();
            List<Mascota> mascotaListNew = raza.getMascotaList();
            if (idEspecieNew != null) {
                idEspecieNew = em.getReference(idEspecieNew.getClass(), idEspecieNew.getIdEspecie());
                raza.setIdEspecie(idEspecieNew);
            }
            List<Mascota> attachedMascotaListNew = new ArrayList<Mascota>();
            for (Mascota mascotaListNewMascotaToAttach : mascotaListNew) {
                mascotaListNewMascotaToAttach = em.getReference(mascotaListNewMascotaToAttach.getClass(), mascotaListNewMascotaToAttach.getId());
                attachedMascotaListNew.add(mascotaListNewMascotaToAttach);
            }
            mascotaListNew = attachedMascotaListNew;
            raza.setMascotaList(mascotaListNew);
            raza = em.merge(raza);
            if (idEspecieOld != null && !idEspecieOld.equals(idEspecieNew)) {
                idEspecieOld.getRazaList().remove(raza);
                idEspecieOld = em.merge(idEspecieOld);
            }
            if (idEspecieNew != null && !idEspecieNew.equals(idEspecieOld)) {
                idEspecieNew.getRazaList().add(raza);
                idEspecieNew = em.merge(idEspecieNew);
            }
            for (Mascota mascotaListOldMascota : mascotaListOld) {
                if (!mascotaListNew.contains(mascotaListOldMascota)) {
                    mascotaListOldMascota.setIdRaza(null);
                    mascotaListOldMascota = em.merge(mascotaListOldMascota);
                }
            }
            for (Mascota mascotaListNewMascota : mascotaListNew) {
                if (!mascotaListOld.contains(mascotaListNewMascota)) {
                    Raza oldIdRazaOfMascotaListNewMascota = mascotaListNewMascota.getIdRaza();
                    mascotaListNewMascota.setIdRaza(raza);
                    mascotaListNewMascota = em.merge(mascotaListNewMascota);
                    if (oldIdRazaOfMascotaListNewMascota != null && !oldIdRazaOfMascotaListNewMascota.equals(raza)) {
                        oldIdRazaOfMascotaListNewMascota.getMascotaList().remove(mascotaListNewMascota);
                        oldIdRazaOfMascotaListNewMascota = em.merge(oldIdRazaOfMascotaListNewMascota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = raza.getIdRaza();
                if (findRaza(id) == null) {
                    throw new NonexistentEntityException("The raza with id " + id + " no longer exists.");
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
            Raza raza;
            try {
                raza = em.getReference(Raza.class, id);
                raza.getIdRaza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The raza with id " + id + " no longer exists.", enfe);
            }
            Especie idEspecie = raza.getIdEspecie();
            if (idEspecie != null) {
                idEspecie.getRazaList().remove(raza);
                idEspecie = em.merge(idEspecie);
            }
            List<Mascota> mascotaList = raza.getMascotaList();
            for (Mascota mascotaListMascota : mascotaList) {
                mascotaListMascota.setIdRaza(null);
                mascotaListMascota = em.merge(mascotaListMascota);
            }
            em.remove(raza);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Raza> findRazaEntities() {
        return findRazaEntities(true, -1, -1);
    }

    public List<Raza> findRazaEntities(int maxResults, int firstResult) {
        return findRazaEntities(false, maxResults, firstResult);
    }

    private List<Raza> findRazaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Raza.class));
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

    public Raza findRaza(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Raza.class, id);
        } finally {
            em.close();
        }
    }

    public int getRazaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Raza> rt = cq.from(Raza.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
