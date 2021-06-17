/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import DTO.Asistencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Ponencia;
import DTO.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class AsistenciaJpaController implements Serializable {

    public AsistenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ponencia idPonencia = asistencia.getIdPonencia();
            if (idPonencia != null) {
                idPonencia = em.getReference(idPonencia.getClass(), idPonencia.getIdPonencia());
                asistencia.setIdPonencia(idPonencia);
            }
            Persona idPersona = asistencia.getIdPersona();
            if (idPersona != null) {
                idPersona = em.getReference(idPersona.getClass(), idPersona.getIdPersona());
                asistencia.setIdPersona(idPersona);
            }
            em.persist(asistencia);
            if (idPonencia != null) {
                idPonencia.getAsistenciaList().add(asistencia);
                idPonencia = em.merge(idPonencia);
            }
            if (idPersona != null) {
                idPersona.getAsistenciaList().add(asistencia);
                idPersona = em.merge(idPersona);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAsistencia(asistencia.getIdAsistencia()) != null) {
                throw new PreexistingEntityException("Asistencia " + asistencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getIdAsistencia());
            Ponencia idPonenciaOld = persistentAsistencia.getIdPonencia();
            Ponencia idPonenciaNew = asistencia.getIdPonencia();
            Persona idPersonaOld = persistentAsistencia.getIdPersona();
            Persona idPersonaNew = asistencia.getIdPersona();
            if (idPonenciaNew != null) {
                idPonenciaNew = em.getReference(idPonenciaNew.getClass(), idPonenciaNew.getIdPonencia());
                asistencia.setIdPonencia(idPonenciaNew);
            }
            if (idPersonaNew != null) {
                idPersonaNew = em.getReference(idPersonaNew.getClass(), idPersonaNew.getIdPersona());
                asistencia.setIdPersona(idPersonaNew);
            }
            asistencia = em.merge(asistencia);
            if (idPonenciaOld != null && !idPonenciaOld.equals(idPonenciaNew)) {
                idPonenciaOld.getAsistenciaList().remove(asistencia);
                idPonenciaOld = em.merge(idPonenciaOld);
            }
            if (idPonenciaNew != null && !idPonenciaNew.equals(idPonenciaOld)) {
                idPonenciaNew.getAsistenciaList().add(asistencia);
                idPonenciaNew = em.merge(idPonenciaNew);
            }
            if (idPersonaOld != null && !idPersonaOld.equals(idPersonaNew)) {
                idPersonaOld.getAsistenciaList().remove(asistencia);
                idPersonaOld = em.merge(idPersonaOld);
            }
            if (idPersonaNew != null && !idPersonaNew.equals(idPersonaOld)) {
                idPersonaNew.getAsistenciaList().add(asistencia);
                idPersonaNew = em.merge(idPersonaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = asistencia.getIdAsistencia();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
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
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getIdAsistencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Ponencia idPonencia = asistencia.getIdPonencia();
            if (idPonencia != null) {
                idPonencia.getAsistenciaList().remove(asistencia);
                idPonencia = em.merge(idPonencia);
            }
            Persona idPersona = asistencia.getIdPersona();
            if (idPersona != null) {
                idPersona.getAsistenciaList().remove(asistencia);
                idPersona = em.merge(idPersona);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
