/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Conferencia;
import DTO.Dia;
import DTO.Sesion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class DiaJpaController implements Serializable {

    public DiaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dia dia) {
        if (dia.getSesionList() == null) {
            dia.setSesionList(new ArrayList<Sesion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conferencia idConferencia = dia.getIdConferencia();
            if (idConferencia != null) {
                idConferencia = em.getReference(idConferencia.getClass(), idConferencia.getIdConferencia());
                dia.setIdConferencia(idConferencia);
            }
            List<Sesion> attachedSesionList = new ArrayList<Sesion>();
            for (Sesion sesionListSesionToAttach : dia.getSesionList()) {
                sesionListSesionToAttach = em.getReference(sesionListSesionToAttach.getClass(), sesionListSesionToAttach.getIdSesion());
                attachedSesionList.add(sesionListSesionToAttach);
            }
            dia.setSesionList(attachedSesionList);
            em.persist(dia);
            if (idConferencia != null) {
                idConferencia.getDiaList().add(dia);
                idConferencia = em.merge(idConferencia);
            }
            for (Sesion sesionListSesion : dia.getSesionList()) {
                Dia oldIdDiaOfSesionListSesion = sesionListSesion.getIdDia();
                sesionListSesion.setIdDia(dia);
                sesionListSesion = em.merge(sesionListSesion);
                if (oldIdDiaOfSesionListSesion != null) {
                    oldIdDiaOfSesionListSesion.getSesionList().remove(sesionListSesion);
                    oldIdDiaOfSesionListSesion = em.merge(oldIdDiaOfSesionListSesion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dia dia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dia persistentDia = em.find(Dia.class, dia.getIdDia());
            Conferencia idConferenciaOld = persistentDia.getIdConferencia();
            Conferencia idConferenciaNew = dia.getIdConferencia();
            List<Sesion> sesionListOld = persistentDia.getSesionList();
            List<Sesion> sesionListNew = dia.getSesionList();
            List<String> illegalOrphanMessages = null;
            for (Sesion sesionListOldSesion : sesionListOld) {
                if (!sesionListNew.contains(sesionListOldSesion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sesion " + sesionListOldSesion + " since its idDia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idConferenciaNew != null) {
                idConferenciaNew = em.getReference(idConferenciaNew.getClass(), idConferenciaNew.getIdConferencia());
                dia.setIdConferencia(idConferenciaNew);
            }
            List<Sesion> attachedSesionListNew = new ArrayList<Sesion>();
            for (Sesion sesionListNewSesionToAttach : sesionListNew) {
                sesionListNewSesionToAttach = em.getReference(sesionListNewSesionToAttach.getClass(), sesionListNewSesionToAttach.getIdSesion());
                attachedSesionListNew.add(sesionListNewSesionToAttach);
            }
            sesionListNew = attachedSesionListNew;
            dia.setSesionList(sesionListNew);
            dia = em.merge(dia);
            if (idConferenciaOld != null && !idConferenciaOld.equals(idConferenciaNew)) {
                idConferenciaOld.getDiaList().remove(dia);
                idConferenciaOld = em.merge(idConferenciaOld);
            }
            if (idConferenciaNew != null && !idConferenciaNew.equals(idConferenciaOld)) {
                idConferenciaNew.getDiaList().add(dia);
                idConferenciaNew = em.merge(idConferenciaNew);
            }
            for (Sesion sesionListNewSesion : sesionListNew) {
                if (!sesionListOld.contains(sesionListNewSesion)) {
                    Dia oldIdDiaOfSesionListNewSesion = sesionListNewSesion.getIdDia();
                    sesionListNewSesion.setIdDia(dia);
                    sesionListNewSesion = em.merge(sesionListNewSesion);
                    if (oldIdDiaOfSesionListNewSesion != null && !oldIdDiaOfSesionListNewSesion.equals(dia)) {
                        oldIdDiaOfSesionListNewSesion.getSesionList().remove(sesionListNewSesion);
                        oldIdDiaOfSesionListNewSesion = em.merge(oldIdDiaOfSesionListNewSesion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dia.getIdDia();
                if (findDia(id) == null) {
                    throw new NonexistentEntityException("The dia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dia dia;
            try {
                dia = em.getReference(Dia.class, id);
                dia.getIdDia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Sesion> sesionListOrphanCheck = dia.getSesionList();
            for (Sesion sesionListOrphanCheckSesion : sesionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dia (" + dia + ") cannot be destroyed since the Sesion " + sesionListOrphanCheckSesion + " in its sesionList field has a non-nullable idDia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Conferencia idConferencia = dia.getIdConferencia();
            if (idConferencia != null) {
                idConferencia.getDiaList().remove(dia);
                idConferencia = em.merge(idConferencia);
            }
            em.remove(dia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dia> findDiaEntities() {
        return findDiaEntities(true, -1, -1);
    }

    public List<Dia> findDiaEntities(int maxResults, int firstResult) {
        return findDiaEntities(false, maxResults, firstResult);
    }

    private List<Dia> findDiaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dia.class));
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

    public Dia findDia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dia.class, id);
        } finally {
            em.close();
        }
    }

    public int getDiaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dia> rt = cq.from(Dia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
