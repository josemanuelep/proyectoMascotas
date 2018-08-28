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
import Entidades.Cliente;
import Entidades.Raza;
import Entidades.ControlPeso;
import Entidades.Mascota;
import java.util.ArrayList;
import java.util.List;
import Entidades.Vacunacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author palac
 */
public class MascotaJpaController implements Serializable {

    public MascotaJpaController() {
     this.emf = Persistence.createEntityManagerFactory("Administracion_MascotasPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mascota mascota) throws PreexistingEntityException, Exception {
        if (mascota.getControlPesoList() == null) {
            mascota.setControlPesoList(new ArrayList<ControlPeso>());
        }
        if (mascota.getVacunacionList() == null) {
            mascota.setVacunacionList(new ArrayList<Vacunacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente identCliente = mascota.getIdentCliente();
            if (identCliente != null) {
                identCliente = em.getReference(identCliente.getClass(), identCliente.getIdentCliente());
                mascota.setIdentCliente(identCliente);
            }
            Raza idRaza = mascota.getIdRaza();
            if (idRaza != null) {
                idRaza = em.getReference(idRaza.getClass(), idRaza.getIdRaza());
                mascota.setIdRaza(idRaza);
            }
            List<ControlPeso> attachedControlPesoList = new ArrayList<ControlPeso>();
            for (ControlPeso controlPesoListControlPesoToAttach : mascota.getControlPesoList()) {
                controlPesoListControlPesoToAttach = em.getReference(controlPesoListControlPesoToAttach.getClass(), controlPesoListControlPesoToAttach.getNroControl());
                attachedControlPesoList.add(controlPesoListControlPesoToAttach);
            }
            mascota.setControlPesoList(attachedControlPesoList);
            List<Vacunacion> attachedVacunacionList = new ArrayList<Vacunacion>();
            for (Vacunacion vacunacionListVacunacionToAttach : mascota.getVacunacionList()) {
                vacunacionListVacunacionToAttach = em.getReference(vacunacionListVacunacionToAttach.getClass(), vacunacionListVacunacionToAttach.getNroVacunacion());
                attachedVacunacionList.add(vacunacionListVacunacionToAttach);
            }
            mascota.setVacunacionList(attachedVacunacionList);
            em.persist(mascota);
            if (identCliente != null) {
                identCliente.getMascotaList().add(mascota);
                identCliente = em.merge(identCliente);
            }
            if (idRaza != null) {
                idRaza.getMascotaList().add(mascota);
                idRaza = em.merge(idRaza);
            }
            for (ControlPeso controlPesoListControlPeso : mascota.getControlPesoList()) {
                Mascota oldIdMascotaOfControlPesoListControlPeso = controlPesoListControlPeso.getIdMascota();
                controlPesoListControlPeso.setIdMascota(mascota);
                controlPesoListControlPeso = em.merge(controlPesoListControlPeso);
                if (oldIdMascotaOfControlPesoListControlPeso != null) {
                    oldIdMascotaOfControlPesoListControlPeso.getControlPesoList().remove(controlPesoListControlPeso);
                    oldIdMascotaOfControlPesoListControlPeso = em.merge(oldIdMascotaOfControlPesoListControlPeso);
                }
            }
            for (Vacunacion vacunacionListVacunacion : mascota.getVacunacionList()) {
                Mascota oldIdMascotaOfVacunacionListVacunacion = vacunacionListVacunacion.getIdMascota();
                vacunacionListVacunacion.setIdMascota(mascota);
                vacunacionListVacunacion = em.merge(vacunacionListVacunacion);
                if (oldIdMascotaOfVacunacionListVacunacion != null) {
                    oldIdMascotaOfVacunacionListVacunacion.getVacunacionList().remove(vacunacionListVacunacion);
                    oldIdMascotaOfVacunacionListVacunacion = em.merge(oldIdMascotaOfVacunacionListVacunacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMascota(mascota.getId()) != null) {
                throw new PreexistingEntityException("Mascota " + mascota + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mascota mascota) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mascota persistentMascota = em.find(Mascota.class, mascota.getId());
            Cliente identClienteOld = persistentMascota.getIdentCliente();
            Cliente identClienteNew = mascota.getIdentCliente();
            Raza idRazaOld = persistentMascota.getIdRaza();
            Raza idRazaNew = mascota.getIdRaza();
            List<ControlPeso> controlPesoListOld = persistentMascota.getControlPesoList();
            List<ControlPeso> controlPesoListNew = mascota.getControlPesoList();
            List<Vacunacion> vacunacionListOld = persistentMascota.getVacunacionList();
            List<Vacunacion> vacunacionListNew = mascota.getVacunacionList();
            if (identClienteNew != null) {
                identClienteNew = em.getReference(identClienteNew.getClass(), identClienteNew.getIdentCliente());
                mascota.setIdentCliente(identClienteNew);
            }
            if (idRazaNew != null) {
                idRazaNew = em.getReference(idRazaNew.getClass(), idRazaNew.getIdRaza());
                mascota.setIdRaza(idRazaNew);
            }
            List<ControlPeso> attachedControlPesoListNew = new ArrayList<ControlPeso>();
            for (ControlPeso controlPesoListNewControlPesoToAttach : controlPesoListNew) {
                controlPesoListNewControlPesoToAttach = em.getReference(controlPesoListNewControlPesoToAttach.getClass(), controlPesoListNewControlPesoToAttach.getNroControl());
                attachedControlPesoListNew.add(controlPesoListNewControlPesoToAttach);
            }
            controlPesoListNew = attachedControlPesoListNew;
            mascota.setControlPesoList(controlPesoListNew);
            List<Vacunacion> attachedVacunacionListNew = new ArrayList<Vacunacion>();
            for (Vacunacion vacunacionListNewVacunacionToAttach : vacunacionListNew) {
                vacunacionListNewVacunacionToAttach = em.getReference(vacunacionListNewVacunacionToAttach.getClass(), vacunacionListNewVacunacionToAttach.getNroVacunacion());
                attachedVacunacionListNew.add(vacunacionListNewVacunacionToAttach);
            }
            vacunacionListNew = attachedVacunacionListNew;
            mascota.setVacunacionList(vacunacionListNew);
            mascota = em.merge(mascota);
            if (identClienteOld != null && !identClienteOld.equals(identClienteNew)) {
                identClienteOld.getMascotaList().remove(mascota);
                identClienteOld = em.merge(identClienteOld);
            }
            if (identClienteNew != null && !identClienteNew.equals(identClienteOld)) {
                identClienteNew.getMascotaList().add(mascota);
                identClienteNew = em.merge(identClienteNew);
            }
            if (idRazaOld != null && !idRazaOld.equals(idRazaNew)) {
                idRazaOld.getMascotaList().remove(mascota);
                idRazaOld = em.merge(idRazaOld);
            }
            if (idRazaNew != null && !idRazaNew.equals(idRazaOld)) {
                idRazaNew.getMascotaList().add(mascota);
                idRazaNew = em.merge(idRazaNew);
            }
            for (ControlPeso controlPesoListOldControlPeso : controlPesoListOld) {
                if (!controlPesoListNew.contains(controlPesoListOldControlPeso)) {
                    controlPesoListOldControlPeso.setIdMascota(null);
                    controlPesoListOldControlPeso = em.merge(controlPesoListOldControlPeso);
                }
            }
            for (ControlPeso controlPesoListNewControlPeso : controlPesoListNew) {
                if (!controlPesoListOld.contains(controlPesoListNewControlPeso)) {
                    Mascota oldIdMascotaOfControlPesoListNewControlPeso = controlPesoListNewControlPeso.getIdMascota();
                    controlPesoListNewControlPeso.setIdMascota(mascota);
                    controlPesoListNewControlPeso = em.merge(controlPesoListNewControlPeso);
                    if (oldIdMascotaOfControlPesoListNewControlPeso != null && !oldIdMascotaOfControlPesoListNewControlPeso.equals(mascota)) {
                        oldIdMascotaOfControlPesoListNewControlPeso.getControlPesoList().remove(controlPesoListNewControlPeso);
                        oldIdMascotaOfControlPesoListNewControlPeso = em.merge(oldIdMascotaOfControlPesoListNewControlPeso);
                    }
                }
            }
            for (Vacunacion vacunacionListOldVacunacion : vacunacionListOld) {
                if (!vacunacionListNew.contains(vacunacionListOldVacunacion)) {
                    vacunacionListOldVacunacion.setIdMascota(null);
                    vacunacionListOldVacunacion = em.merge(vacunacionListOldVacunacion);
                }
            }
            for (Vacunacion vacunacionListNewVacunacion : vacunacionListNew) {
                if (!vacunacionListOld.contains(vacunacionListNewVacunacion)) {
                    Mascota oldIdMascotaOfVacunacionListNewVacunacion = vacunacionListNewVacunacion.getIdMascota();
                    vacunacionListNewVacunacion.setIdMascota(mascota);
                    vacunacionListNewVacunacion = em.merge(vacunacionListNewVacunacion);
                    if (oldIdMascotaOfVacunacionListNewVacunacion != null && !oldIdMascotaOfVacunacionListNewVacunacion.equals(mascota)) {
                        oldIdMascotaOfVacunacionListNewVacunacion.getVacunacionList().remove(vacunacionListNewVacunacion);
                        oldIdMascotaOfVacunacionListNewVacunacion = em.merge(oldIdMascotaOfVacunacionListNewVacunacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mascota.getId();
                if (findMascota(id) == null) {
                    throw new NonexistentEntityException("The mascota with id " + id + " no longer exists.");
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
            Mascota mascota;
            try {
                mascota = em.getReference(Mascota.class, id);
                mascota.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mascota with id " + id + " no longer exists.", enfe);
            }
            Cliente identCliente = mascota.getIdentCliente();
            if (identCliente != null) {
                identCliente.getMascotaList().remove(mascota);
                identCliente = em.merge(identCliente);
            }
            Raza idRaza = mascota.getIdRaza();
            if (idRaza != null) {
                idRaza.getMascotaList().remove(mascota);
                idRaza = em.merge(idRaza);
            }
            List<ControlPeso> controlPesoList = mascota.getControlPesoList();
            for (ControlPeso controlPesoListControlPeso : controlPesoList) {
                controlPesoListControlPeso.setIdMascota(null);
                controlPesoListControlPeso = em.merge(controlPesoListControlPeso);
            }
            List<Vacunacion> vacunacionList = mascota.getVacunacionList();
            for (Vacunacion vacunacionListVacunacion : vacunacionList) {
                vacunacionListVacunacion.setIdMascota(null);
                vacunacionListVacunacion = em.merge(vacunacionListVacunacion);
            }
            em.remove(mascota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mascota> findMascotaEntities() {
        return findMascotaEntities(true, -1, -1);
    }

    public List<Mascota> findMascotaEntities(int maxResults, int firstResult) {
        return findMascotaEntities(false, maxResults, firstResult);
    }

    private List<Mascota> findMascotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mascota.class));
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

    public Mascota findMascota(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mascota.class, id);
        } finally {
            em.close();
        }
    }

    public int getMascotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mascota> rt = cq.from(Mascota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
