/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DTO.Conferencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Topico;
import java.util.ArrayList;
import java.util.List;
import DTO.Dia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class ConferenciaJpaController implements Serializable {

    public ConferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Conferencia conferencia) {
        if (conferencia.getTopicoList() == null) {
            conferencia.setTopicoList(new ArrayList<Topico>());
        }
        if (conferencia.getDiaList() == null) {
            conferencia.setDiaList(new ArrayList<Dia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Topico> attachedTopicoList = new ArrayList<Topico>();
            for (Topico topicoListTopicoToAttach : conferencia.getTopicoList()) {
                topicoListTopicoToAttach = em.getReference(topicoListTopicoToAttach.getClass(), topicoListTopicoToAttach.getIdTopico());
                attachedTopicoList.add(topicoListTopicoToAttach);
            }
            conferencia.setTopicoList(attachedTopicoList);
            List<Dia> attachedDiaList = new ArrayList<Dia>();
            for (Dia diaListDiaToAttach : conferencia.getDiaList()) {
                diaListDiaToAttach = em.getReference(diaListDiaToAttach.getClass(), diaListDiaToAttach.getIdDia());
                attachedDiaList.add(diaListDiaToAttach);
            }
            conferencia.setDiaList(attachedDiaList);
            em.persist(conferencia);
            for (Topico topicoListTopico : conferencia.getTopicoList()) {
                Conferencia oldIdConferenciaOfTopicoListTopico = topicoListTopico.getIdConferencia();
                topicoListTopico.setIdConferencia(conferencia);
                topicoListTopico = em.merge(topicoListTopico);
                if (oldIdConferenciaOfTopicoListTopico != null) {
                    oldIdConferenciaOfTopicoListTopico.getTopicoList().remove(topicoListTopico);
                    oldIdConferenciaOfTopicoListTopico = em.merge(oldIdConferenciaOfTopicoListTopico);
                }
            }
            for (Dia diaListDia : conferencia.getDiaList()) {
                Conferencia oldIdConferenciaOfDiaListDia = diaListDia.getIdConferencia();
                diaListDia.setIdConferencia(conferencia);
                diaListDia = em.merge(diaListDia);
                if (oldIdConferenciaOfDiaListDia != null) {
                    oldIdConferenciaOfDiaListDia.getDiaList().remove(diaListDia);
                    oldIdConferenciaOfDiaListDia = em.merge(oldIdConferenciaOfDiaListDia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Conferencia conferencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Conferencia persistentConferencia = em.find(Conferencia.class, conferencia.getIdConferencia());
            List<Topico> topicoListOld = persistentConferencia.getTopicoList();
            List<Topico> topicoListNew = conferencia.getTopicoList();
            List<Dia> diaListOld = persistentConferencia.getDiaList();
            List<Dia> diaListNew = conferencia.getDiaList();
            List<String> illegalOrphanMessages = null;
            for (Topico topicoListOldTopico : topicoListOld) {
                if (!topicoListNew.contains(topicoListOldTopico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Topico " + topicoListOldTopico + " since its idConferencia field is not nullable.");
                }
            }
            for (Dia diaListOldDia : diaListOld) {
                if (!diaListNew.contains(diaListOldDia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Dia " + diaListOldDia + " since its idConferencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Topico> attachedTopicoListNew = new ArrayList<Topico>();
            for (Topico topicoListNewTopicoToAttach : topicoListNew) {
                topicoListNewTopicoToAttach = em.getReference(topicoListNewTopicoToAttach.getClass(), topicoListNewTopicoToAttach.getIdTopico());
                attachedTopicoListNew.add(topicoListNewTopicoToAttach);
            }
            topicoListNew = attachedTopicoListNew;
            conferencia.setTopicoList(topicoListNew);
            List<Dia> attachedDiaListNew = new ArrayList<Dia>();
            for (Dia diaListNewDiaToAttach : diaListNew) {
                diaListNewDiaToAttach = em.getReference(diaListNewDiaToAttach.getClass(), diaListNewDiaToAttach.getIdDia());
                attachedDiaListNew.add(diaListNewDiaToAttach);
            }
            diaListNew = attachedDiaListNew;
            conferencia.setDiaList(diaListNew);
            conferencia = em.merge(conferencia);
            for (Topico topicoListNewTopico : topicoListNew) {
                if (!topicoListOld.contains(topicoListNewTopico)) {
                    Conferencia oldIdConferenciaOfTopicoListNewTopico = topicoListNewTopico.getIdConferencia();
                    topicoListNewTopico.setIdConferencia(conferencia);
                    topicoListNewTopico = em.merge(topicoListNewTopico);
                    if (oldIdConferenciaOfTopicoListNewTopico != null && !oldIdConferenciaOfTopicoListNewTopico.equals(conferencia)) {
                        oldIdConferenciaOfTopicoListNewTopico.getTopicoList().remove(topicoListNewTopico);
                        oldIdConferenciaOfTopicoListNewTopico = em.merge(oldIdConferenciaOfTopicoListNewTopico);
                    }
                }
            }
            for (Dia diaListNewDia : diaListNew) {
                if (!diaListOld.contains(diaListNewDia)) {
                    Conferencia oldIdConferenciaOfDiaListNewDia = diaListNewDia.getIdConferencia();
                    diaListNewDia.setIdConferencia(conferencia);
                    diaListNewDia = em.merge(diaListNewDia);
                    if (oldIdConferenciaOfDiaListNewDia != null && !oldIdConferenciaOfDiaListNewDia.equals(conferencia)) {
                        oldIdConferenciaOfDiaListNewDia.getDiaList().remove(diaListNewDia);
                        oldIdConferenciaOfDiaListNewDia = em.merge(oldIdConferenciaOfDiaListNewDia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = conferencia.getIdConferencia();
                if (findConferencia(id) == null) {
                    throw new NonexistentEntityException("The conferencia with id " + id + " no longer exists.");
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
            Conferencia conferencia;
            try {
                conferencia = em.getReference(Conferencia.class, id);
                conferencia.getIdConferencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The conferencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Topico> topicoListOrphanCheck = conferencia.getTopicoList();
            for (Topico topicoListOrphanCheckTopico : topicoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Conferencia (" + conferencia + ") cannot be destroyed since the Topico " + topicoListOrphanCheckTopico + " in its topicoList field has a non-nullable idConferencia field.");
            }
            List<Dia> diaListOrphanCheck = conferencia.getDiaList();
            for (Dia diaListOrphanCheckDia : diaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Conferencia (" + conferencia + ") cannot be destroyed since the Dia " + diaListOrphanCheckDia + " in its diaList field has a non-nullable idConferencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(conferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Conferencia> findConferenciaEntities() {
        return findConferenciaEntities(true, -1, -1);
    }

    public List<Conferencia> findConferenciaEntities(int maxResults, int firstResult) {
        return findConferenciaEntities(false, maxResults, firstResult);
    }

    private List<Conferencia> findConferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Conferencia.class));
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

    public Conferencia findConferencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Conferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getConferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Conferencia> rt = cq.from(Conferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
