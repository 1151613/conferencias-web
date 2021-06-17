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
import DTO.Sesion;
import DTO.Asistencia;
import DTO.Ponencia;
import java.util.ArrayList;
import java.util.List;
import DTO.Sala;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class PonenciaJpaController implements Serializable {

    public PonenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ponencia ponencia) {
        if (ponencia.getAsistenciaList() == null) {
            ponencia.setAsistenciaList(new ArrayList<Asistencia>());
        }
        if (ponencia.getSalaList() == null) {
            ponencia.setSalaList(new ArrayList<Sala>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sesion idSesion = ponencia.getIdSesion();
            if (idSesion != null) {
                idSesion = em.getReference(idSesion.getClass(), idSesion.getIdSesion());
                ponencia.setIdSesion(idSesion);
            }
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : ponencia.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getIdAsistencia());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            ponencia.setAsistenciaList(attachedAsistenciaList);
            List<Sala> attachedSalaList = new ArrayList<Sala>();
            for (Sala salaListSalaToAttach : ponencia.getSalaList()) {
                salaListSalaToAttach = em.getReference(salaListSalaToAttach.getClass(), salaListSalaToAttach.getIdSala());
                attachedSalaList.add(salaListSalaToAttach);
            }
            ponencia.setSalaList(attachedSalaList);
            em.persist(ponencia);
            if (idSesion != null) {
                idSesion.getPonenciaList().add(ponencia);
                idSesion = em.merge(idSesion);
            }
            for (Asistencia asistenciaListAsistencia : ponencia.getAsistenciaList()) {
                Ponencia oldIdPonenciaOfAsistenciaListAsistencia = asistenciaListAsistencia.getIdPonencia();
                asistenciaListAsistencia.setIdPonencia(ponencia);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldIdPonenciaOfAsistenciaListAsistencia != null) {
                    oldIdPonenciaOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldIdPonenciaOfAsistenciaListAsistencia = em.merge(oldIdPonenciaOfAsistenciaListAsistencia);
                }
            }
            for (Sala salaListSala : ponencia.getSalaList()) {
                Ponencia oldIdPonenciaOfSalaListSala = salaListSala.getIdPonencia();
                salaListSala.setIdPonencia(ponencia);
                salaListSala = em.merge(salaListSala);
                if (oldIdPonenciaOfSalaListSala != null) {
                    oldIdPonenciaOfSalaListSala.getSalaList().remove(salaListSala);
                    oldIdPonenciaOfSalaListSala = em.merge(oldIdPonenciaOfSalaListSala);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ponencia ponencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ponencia persistentPonencia = em.find(Ponencia.class, ponencia.getIdPonencia());
            Sesion idSesionOld = persistentPonencia.getIdSesion();
            Sesion idSesionNew = ponencia.getIdSesion();
            List<Asistencia> asistenciaListOld = persistentPonencia.getAsistenciaList();
            List<Asistencia> asistenciaListNew = ponencia.getAsistenciaList();
            List<Sala> salaListOld = persistentPonencia.getSalaList();
            List<Sala> salaListNew = ponencia.getSalaList();
            List<String> illegalOrphanMessages = null;
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its idPonencia field is not nullable.");
                }
            }
            for (Sala salaListOldSala : salaListOld) {
                if (!salaListNew.contains(salaListOldSala)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Sala " + salaListOldSala + " since its idPonencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idSesionNew != null) {
                idSesionNew = em.getReference(idSesionNew.getClass(), idSesionNew.getIdSesion());
                ponencia.setIdSesion(idSesionNew);
            }
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getIdAsistencia());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            ponencia.setAsistenciaList(asistenciaListNew);
            List<Sala> attachedSalaListNew = new ArrayList<Sala>();
            for (Sala salaListNewSalaToAttach : salaListNew) {
                salaListNewSalaToAttach = em.getReference(salaListNewSalaToAttach.getClass(), salaListNewSalaToAttach.getIdSala());
                attachedSalaListNew.add(salaListNewSalaToAttach);
            }
            salaListNew = attachedSalaListNew;
            ponencia.setSalaList(salaListNew);
            ponencia = em.merge(ponencia);
            if (idSesionOld != null && !idSesionOld.equals(idSesionNew)) {
                idSesionOld.getPonenciaList().remove(ponencia);
                idSesionOld = em.merge(idSesionOld);
            }
            if (idSesionNew != null && !idSesionNew.equals(idSesionOld)) {
                idSesionNew.getPonenciaList().add(ponencia);
                idSesionNew = em.merge(idSesionNew);
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Ponencia oldIdPonenciaOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getIdPonencia();
                    asistenciaListNewAsistencia.setIdPonencia(ponencia);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldIdPonenciaOfAsistenciaListNewAsistencia != null && !oldIdPonenciaOfAsistenciaListNewAsistencia.equals(ponencia)) {
                        oldIdPonenciaOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldIdPonenciaOfAsistenciaListNewAsistencia = em.merge(oldIdPonenciaOfAsistenciaListNewAsistencia);
                    }
                }
            }
            for (Sala salaListNewSala : salaListNew) {
                if (!salaListOld.contains(salaListNewSala)) {
                    Ponencia oldIdPonenciaOfSalaListNewSala = salaListNewSala.getIdPonencia();
                    salaListNewSala.setIdPonencia(ponencia);
                    salaListNewSala = em.merge(salaListNewSala);
                    if (oldIdPonenciaOfSalaListNewSala != null && !oldIdPonenciaOfSalaListNewSala.equals(ponencia)) {
                        oldIdPonenciaOfSalaListNewSala.getSalaList().remove(salaListNewSala);
                        oldIdPonenciaOfSalaListNewSala = em.merge(oldIdPonenciaOfSalaListNewSala);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ponencia.getIdPonencia();
                if (findPonencia(id) == null) {
                    throw new NonexistentEntityException("The ponencia with id " + id + " no longer exists.");
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
            Ponencia ponencia;
            try {
                ponencia = em.getReference(Ponencia.class, id);
                ponencia.getIdPonencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ponencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Asistencia> asistenciaListOrphanCheck = ponencia.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ponencia (" + ponencia + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable idPonencia field.");
            }
            List<Sala> salaListOrphanCheck = ponencia.getSalaList();
            for (Sala salaListOrphanCheckSala : salaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ponencia (" + ponencia + ") cannot be destroyed since the Sala " + salaListOrphanCheckSala + " in its salaList field has a non-nullable idPonencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sesion idSesion = ponencia.getIdSesion();
            if (idSesion != null) {
                idSesion.getPonenciaList().remove(ponencia);
                idSesion = em.merge(idSesion);
            }
            em.remove(ponencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ponencia> findPonenciaEntities() {
        return findPonenciaEntities(true, -1, -1);
    }

    public List<Ponencia> findPonenciaEntities(int maxResults, int firstResult) {
        return findPonenciaEntities(false, maxResults, firstResult);
    }

    private List<Ponencia> findPonenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ponencia.class));
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

    public Ponencia findPonencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ponencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getPonenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ponencia> rt = cq.from(Ponencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
