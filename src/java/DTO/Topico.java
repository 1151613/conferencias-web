/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author berserker
 */
@Entity
@Table(name = "topico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Topico.findAll", query = "SELECT t FROM Topico t")
    , @NamedQuery(name = "Topico.findByIdTopico", query = "SELECT t FROM Topico t WHERE t.idTopico = :idTopico")
    , @NamedQuery(name = "Topico.findByTopico", query = "SELECT t FROM Topico t WHERE t.topico = :topico")})
public class Topico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_topico")
    private Integer idTopico;
    @Basic(optional = false)
    @Column(name = "topico")
    private String topico;
    @JoinColumn(name = "id_conferencia", referencedColumnName = "id_conferencia")
    @ManyToOne(optional = false)
    private Conferencia idConferencia;

    public Topico() {
    }

    public Topico(Integer idTopico) {
        this.idTopico = idTopico;
    }

    public Topico(Integer idTopico, String topico) {
        this.idTopico = idTopico;
        this.topico = topico;
    }

    public Integer getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(Integer idTopico) {
        this.idTopico = idTopico;
    }

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public Conferencia getIdConferencia() {
        return idConferencia;
    }

    public void setIdConferencia(Conferencia idConferencia) {
        this.idConferencia = idConferencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTopico != null ? idTopico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Topico)) {
            return false;
        }
        Topico other = (Topico) object;
        if ((this.idTopico == null && other.idTopico != null) || (this.idTopico != null && !this.idTopico.equals(other.idTopico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Topico[ idTopico=" + idTopico + " ]";
    }
    
}
