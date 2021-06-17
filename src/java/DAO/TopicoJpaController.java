/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Conferencia;
import DTO.Topico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class TopicoJpaController implements Serializable {

    public TopicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Topico topico) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conferencia idConferencia = topico.getIdConferencia();
            if (idConferencia != null) {
                idConferencia = em.getReference(idConferencia.getClass(), idConferencia.getIdConferencia());
                topico.setIdConferencia(idConferencia);
            }
            em.persist(topico);
            if (idConferencia != null) {
                idConferencia.getTopicoList().add(topico);
                idConferencia = em.merge(idConferencia);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTopico(topico.getIdTopico()) != null) {
                throw new PreexistingEntityException("Topico " + topico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Topico topico) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Topico persistentTopico = em.find(Topico.class, topico.getIdTopico());
            Conferencia idConferenciaOld = persistentTopico.getIdConferencia();
            Conferencia idConferenciaNew = topico.getIdConferencia();
            if (idConferenciaNew != null) {
                idConferenciaNew = em.getReference(idConferenciaNew.getClass(), idConferenciaNew.getIdConferencia());
                topico.setIdConferencia(idConferenciaNew);
            }
            topico = em.merge(topico);
            if (idConferenciaOld != null && !idConferenciaOld.equals(idConferenciaNew)) {
                idConferenciaOld.getTopicoList().remove(topico);
                idConferenciaOld = em.merge(idConferenciaOld);
            }
            if (idConferenciaNew != null && !idConferenciaNew.equals(idConferenciaOld)) {
                idConferenciaNew.getTopicoList().add(topico);
                idConferenciaNew = em.merge(idConferenciaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = topico.getIdTopico();
                if (findTopico(id) == null) {
                    throw new NonexistentEntityException("The topico with id " + id + " no longer exists.");
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
            Topico topico;
            try {
                topico = em.getReference(Topico.class, id);
                topico.getIdTopico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The topico with id " + id + " no longer exists.", enfe);
            }
            Conferencia idConferencia = topico.getIdConferencia();
            if (idConferencia != null) {
                idConferencia.getTopicoList().remove(topico);
                idConferencia = em.merge(idConferencia);
            }
            em.remove(topico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Topico> findTopicoEntities() {
        return findTopicoEntities(true, -1, -1);
    }

    public List<Topico> findTopicoEntities(int maxResults, int firstResult) {
        return findTopicoEntities(false, maxResults, firstResult);
    }

    private List<Topico> findTopicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Topico.class));
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

    public Topico findTopico(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Topico.class, id);
        } finally {
            em.close();
        }
    }

    public int getTopicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Topico> rt = cq.from(Topico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
