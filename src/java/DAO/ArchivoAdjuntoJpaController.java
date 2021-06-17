/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DTO.ArchivoAdjunto;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Sesion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class ArchivoAdjuntoJpaController implements Serializable {

    public ArchivoAdjuntoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ArchivoAdjunto archivoAdjunto) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sesion idSesion = archivoAdjunto.getIdSesion();
            if (idSesion != null) {
                idSesion = em.getReference(idSesion.getClass(), idSesion.getIdSesion());
                archivoAdjunto.setIdSesion(idSesion);
            }
            em.persist(archivoAdjunto);
            if (idSesion != null) {
                idSesion.getArchivoAdjuntoList().add(archivoAdjunto);
                idSesion = em.merge(idSesion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ArchivoAdjunto archivoAdjunto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ArchivoAdjunto persistentArchivoAdjunto = em.find(ArchivoAdjunto.class, archivoAdjunto.getIdArchivo());
            Sesion idSesionOld = persistentArchivoAdjunto.getIdSesion();
            Sesion idSesionNew = archivoAdjunto.getIdSesion();
            if (idSesionNew != null) {
                idSesionNew = em.getReference(idSesionNew.getClass(), idSesionNew.getIdSesion());
                archivoAdjunto.setIdSesion(idSesionNew);
            }
            archivoAdjunto = em.merge(archivoAdjunto);
            if (idSesionOld != null && !idSesionOld.equals(idSesionNew)) {
                idSesionOld.getArchivoAdjuntoList().remove(archivoAdjunto);
                idSesionOld = em.merge(idSesionOld);
            }
            if (idSesionNew != null && !idSesionNew.equals(idSesionOld)) {
                idSesionNew.getArchivoAdjuntoList().add(archivoAdjunto);
                idSesionNew = em.merge(idSesionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = archivoAdjunto.getIdArchivo();
                if (findArchivoAdjunto(id) == null) {
                    throw new NonexistentEntityException("The archivoAdjunto with id " + id + " no longer exists.");
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
            ArchivoAdjunto archivoAdjunto;
            try {
                archivoAdjunto = em.getReference(ArchivoAdjunto.class, id);
                archivoAdjunto.getIdArchivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The archivoAdjunto with id " + id + " no longer exists.", enfe);
            }
            Sesion idSesion = archivoAdjunto.getIdSesion();
            if (idSesion != null) {
                idSesion.getArchivoAdjuntoList().remove(archivoAdjunto);
                idSesion = em.merge(idSesion);
            }
            em.remove(archivoAdjunto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ArchivoAdjunto> findArchivoAdjuntoEntities() {
        return findArchivoAdjuntoEntities(true, -1, -1);
    }

    public List<ArchivoAdjunto> findArchivoAdjuntoEntities(int maxResults, int firstResult) {
        return findArchivoAdjuntoEntities(false, maxResults, firstResult);
    }

    private List<ArchivoAdjunto> findArchivoAdjuntoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ArchivoAdjunto.class));
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

    public ArchivoAdjunto findArchivoAdjunto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ArchivoAdjunto.class, id);
        } finally {
            em.close();
        }
    }

    public int getArchivoAdjuntoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ArchivoAdjunto> rt = cq.from(ArchivoAdjunto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
