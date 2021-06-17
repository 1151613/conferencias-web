/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Dia;
import DTO.ArchivoAdjunto;
import java.util.ArrayList;
import java.util.List;
import DTO.Ponencia;
import DTO.Sesion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author berserker
 */
public class SesionJpaController implements Serializable {

    public SesionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sesion sesion) throws PreexistingEntityException, Exception {
        if (sesion.getArchivoAdjuntoList() == null) {
            sesion.setArchivoAdjuntoList(new ArrayList<ArchivoAdjunto>());
        }
        if (sesion.getPonenciaList() == null) {
            sesion.setPonenciaList(new ArrayList<Ponencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dia idDia = sesion.getIdDia();
            if (idDia != null) {
                idDia = em.getReference(idDia.getClass(), idDia.getIdDia());
                sesion.setIdDia(idDia);
            }
            List<ArchivoAdjunto> attachedArchivoAdjuntoList = new ArrayList<ArchivoAdjunto>();
            for (ArchivoAdjunto archivoAdjuntoListArchivoAdjuntoToAttach : sesion.getArchivoAdjuntoList()) {
                archivoAdjuntoListArchivoAdjuntoToAttach = em.getReference(archivoAdjuntoListArchivoAdjuntoToAttach.getClass(), archivoAdjuntoListArchivoAdjuntoToAttach.getIdArchivo());
                attachedArchivoAdjuntoList.add(archivoAdjuntoListArchivoAdjuntoToAttach);
            }
            sesion.setArchivoAdjuntoList(attachedArchivoAdjuntoList);
            List<Ponencia> attachedPonenciaList = new ArrayList<Ponencia>();
            for (Ponencia ponenciaListPonenciaToAttach : sesion.getPonenciaList()) {
                ponenciaListPonenciaToAttach = em.getReference(ponenciaListPonenciaToAttach.getClass(), ponenciaListPonenciaToAttach.getIdPonencia());
                attachedPonenciaList.add(ponenciaListPonenciaToAttach);
            }
            sesion.setPonenciaList(attachedPonenciaList);
            em.persist(sesion);
            if (idDia != null) {
                idDia.getSesionList().add(sesion);
                idDia = em.merge(idDia);
            }
            for (ArchivoAdjunto archivoAdjuntoListArchivoAdjunto : sesion.getArchivoAdjuntoList()) {
                Sesion oldIdSesionOfArchivoAdjuntoListArchivoAdjunto = archivoAdjuntoListArchivoAdjunto.getIdSesion();
                archivoAdjuntoListArchivoAdjunto.setIdSesion(sesion);
                archivoAdjuntoListArchivoAdjunto = em.merge(archivoAdjuntoListArchivoAdjunto);
                if (oldIdSesionOfArchivoAdjuntoListArchivoAdjunto != null) {
                    oldIdSesionOfArchivoAdjuntoListArchivoAdjunto.getArchivoAdjuntoList().remove(archivoAdjuntoListArchivoAdjunto);
                    oldIdSesionOfArchivoAdjuntoListArchivoAdjunto = em.merge(oldIdSesionOfArchivoAdjuntoListArchivoAdjunto);
                }
            }
            for (Ponencia ponenciaListPonencia : sesion.getPonenciaList()) {
                Sesion oldIdSesionOfPonenciaListPonencia = ponenciaListPonencia.getIdSesion();
                ponenciaListPonencia.setIdSesion(sesion);
                ponenciaListPonencia = em.merge(ponenciaListPonencia);
                if (oldIdSesionOfPonenciaListPonencia != null) {
                    oldIdSesionOfPonenciaListPonencia.getPonenciaList().remove(ponenciaListPonencia);
                    oldIdSesionOfPonenciaListPonencia = em.merge(oldIdSesionOfPonenciaListPonencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSesion(sesion.getIdSesion()) != null) {
                throw new PreexistingEntityException("Sesion " + sesion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sesion sesion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sesion persistentSesion = em.find(Sesion.class, sesion.getIdSesion());
            Dia idDiaOld = persistentSesion.getIdDia();
            Dia idDiaNew = sesion.getIdDia();
            List<ArchivoAdjunto> archivoAdjuntoListOld = persistentSesion.getArchivoAdjuntoList();
            List<ArchivoAdjunto> archivoAdjuntoListNew = sesion.getArchivoAdjuntoList();
            List<Ponencia> ponenciaListOld = persistentSesion.getPonenciaList();
            List<Ponencia> ponenciaListNew = sesion.getPonenciaList();
            List<String> illegalOrphanMessages = null;
            for (ArchivoAdjunto archivoAdjuntoListOldArchivoAdjunto : archivoAdjuntoListOld) {
                if (!archivoAdjuntoListNew.contains(archivoAdjuntoListOldArchivoAdjunto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ArchivoAdjunto " + archivoAdjuntoListOldArchivoAdjunto + " since its idSesion field is not nullable.");
                }
            }
            for (Ponencia ponenciaListOldPonencia : ponenciaListOld) {
                if (!ponenciaListNew.contains(ponenciaListOldPonencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ponencia " + ponenciaListOldPonencia + " since its idSesion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDiaNew != null) {
                idDiaNew = em.getReference(idDiaNew.getClass(), idDiaNew.getIdDia());
                sesion.setIdDia(idDiaNew);
            }
            List<ArchivoAdjunto> attachedArchivoAdjuntoListNew = new ArrayList<ArchivoAdjunto>();
            for (ArchivoAdjunto archivoAdjuntoListNewArchivoAdjuntoToAttach : archivoAdjuntoListNew) {
                archivoAdjuntoListNewArchivoAdjuntoToAttach = em.getReference(archivoAdjuntoListNewArchivoAdjuntoToAttach.getClass(), archivoAdjuntoListNewArchivoAdjuntoToAttach.getIdArchivo());
                attachedArchivoAdjuntoListNew.add(archivoAdjuntoListNewArchivoAdjuntoToAttach);
            }
            archivoAdjuntoListNew = attachedArchivoAdjuntoListNew;
            sesion.setArchivoAdjuntoList(archivoAdjuntoListNew);
            List<Ponencia> attachedPonenciaListNew = new ArrayList<Ponencia>();
            for (Ponencia ponenciaListNewPonenciaToAttach : ponenciaListNew) {
                ponenciaListNewPonenciaToAttach = em.getReference(ponenciaListNewPonenciaToAttach.getClass(), ponenciaListNewPonenciaToAttach.getIdPonencia());
                attachedPonenciaListNew.add(ponenciaListNewPonenciaToAttach);
            }
            ponenciaListNew = attachedPonenciaListNew;
            sesion.setPonenciaList(ponenciaListNew);
            sesion = em.merge(sesion);
            if (idDiaOld != null && !idDiaOld.equals(idDiaNew)) {
                idDiaOld.getSesionList().remove(sesion);
                idDiaOld = em.merge(idDiaOld);
            }
            if (idDiaNew != null && !idDiaNew.equals(idDiaOld)) {
                idDiaNew.getSesionList().add(sesion);
                idDiaNew = em.merge(idDiaNew);
            }
            for (ArchivoAdjunto archivoAdjuntoListNewArchivoAdjunto : archivoAdjuntoListNew) {
                if (!archivoAdjuntoListOld.contains(archivoAdjuntoListNewArchivoAdjunto)) {
                    Sesion oldIdSesionOfArchivoAdjuntoListNewArchivoAdjunto = archivoAdjuntoListNewArchivoAdjunto.getIdSesion();
                    archivoAdjuntoListNewArchivoAdjunto.setIdSesion(sesion);
                    archivoAdjuntoListNewArchivoAdjunto = em.merge(archivoAdjuntoListNewArchivoAdjunto);
                    if (oldIdSesionOfArchivoAdjuntoListNewArchivoAdjunto != null && !oldIdSesionOfArchivoAdjuntoListNewArchivoAdjunto.equals(sesion)) {
                        oldIdSesionOfArchivoAdjuntoListNewArchivoAdjunto.getArchivoAdjuntoList().remove(archivoAdjuntoListNewArchivoAdjunto);
                        oldIdSesionOfArchivoAdjuntoListNewArchivoAdjunto = em.merge(oldIdSesionOfArchivoAdjuntoListNewArchivoAdjunto);
                    }
                }
            }
            for (Ponencia ponenciaListNewPonencia : ponenciaListNew) {
                if (!ponenciaListOld.contains(ponenciaListNewPonencia)) {
                    Sesion oldIdSesionOfPonenciaListNewPonencia = ponenciaListNewPonencia.getIdSesion();
                    ponenciaListNewPonencia.setIdSesion(sesion);
                    ponenciaListNewPonencia = em.merge(ponenciaListNewPonencia);
                    if (oldIdSesionOfPonenciaListNewPonencia != null && !oldIdSesionOfPonenciaListNewPonencia.equals(sesion)) {
                        oldIdSesionOfPonenciaListNewPonencia.getPonenciaList().remove(ponenciaListNewPonencia);
                        oldIdSesionOfPonenciaListNewPonencia = em.merge(oldIdSesionOfPonenciaListNewPonencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sesion.getIdSesion();
                if (findSesion(id) == null) {
                    throw new NonexistentEntityException("The sesion with id " + id + " no longer exists.");
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
            Sesion sesion;
            try {
                sesion = em.getReference(Sesion.class, id);
                sesion.getIdSesion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sesion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ArchivoAdjunto> archivoAdjuntoListOrphanCheck = sesion.getArchivoAdjuntoList();
            for (ArchivoAdjunto archivoAdjuntoListOrphanCheckArchivoAdjunto : archivoAdjuntoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sesion (" + sesion + ") cannot be destroyed since the ArchivoAdjunto " + archivoAdjuntoListOrphanCheckArchivoAdjunto + " in its archivoAdjuntoList field has a non-nullable idSesion field.");
            }
            List<Ponencia> ponenciaListOrphanCheck = sesion.getPonenciaList();
            for (Ponencia ponenciaListOrphanCheckPonencia : ponenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sesion (" + sesion + ") cannot be destroyed since the Ponencia " + ponenciaListOrphanCheckPonencia + " in its ponenciaList field has a non-nullable idSesion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Dia idDia = sesion.getIdDia();
            if (idDia != null) {
                idDia.getSesionList().remove(sesion);
                idDia = em.merge(idDia);
            }
            em.remove(sesion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sesion> findSesionEntities() {
        return findSesionEntities(true, -1, -1);
    }

    public List<Sesion> findSesionEntities(int maxResults, int firstResult) {
        return findSesionEntities(false, maxResults, firstResult);
    }

    private List<Sesion> findSesionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sesion.class));
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

    public Sesion findSesion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sesion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSesionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sesion> rt = cq.from(Sesion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
