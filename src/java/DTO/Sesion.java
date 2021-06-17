/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berserker
 */
@Entity
@Table(name = "sesion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sesion.findAll", query = "SELECT s FROM Sesion s")
    , @NamedQuery(name = "Sesion.findByIdSesion", query = "SELECT s FROM Sesion s WHERE s.idSesion = :idSesion")
    , @NamedQuery(name = "Sesion.findByHoraInicio", query = "SELECT s FROM Sesion s WHERE s.horaInicio = :horaInicio")
    , @NamedQuery(name = "Sesion.findByHoraFin", query = "SELECT s FROM Sesion s WHERE s.horaFin = :horaFin")})
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_sesion")
    private Integer idSesion;
    @Basic(optional = false)
    @Column(name = "hora_inicio")
    @Temporal(TemporalType.TIME)
    private Date horaInicio;
    @Basic(optional = false)
    @Column(name = "hora_fin")
    @Temporal(TemporalType.TIME)
    private Date horaFin;
    @JoinColumn(name = "id_dia", referencedColumnName = "id_dia")
    @ManyToOne(optional = false)
    private Dia idDia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSesion")
    private List<ArchivoAdjunto> archivoAdjuntoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSesion")
    private List<Ponencia> ponenciaList;

    public Sesion() {
    }

    public Sesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public Sesion(Integer idSesion, Date horaInicio, Date horaFin) {
        this.idSesion = idSesion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Integer idSesion) {
        this.idSesion = idSesion;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Dia getIdDia() {
        return idDia;
    }

    public void setIdDia(Dia idDia) {
        this.idDia = idDia;
    }

    @XmlTransient
    public List<ArchivoAdjunto> getArchivoAdjuntoList() {
        return archivoAdjuntoList;
    }

    public void setArchivoAdjuntoList(List<ArchivoAdjunto> archivoAdjuntoList) {
        this.archivoAdjuntoList = archivoAdjuntoList;
    }

    @XmlTransient
    public List<Ponencia> getPonenciaList() {
        return ponenciaList;
    }

    public void setPonenciaList(List<Ponencia> ponenciaList) {
        this.ponenciaList = ponenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSesion != null ? idSesion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sesion)) {
            return false;
        }
        Sesion other = (Sesion) object;
        if ((this.idSesion == null && other.idSesion != null) || (this.idSesion != null && !this.idSesion.equals(other.idSesion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Sesion[ idSesion=" + idSesion + " ]";
    }
    
}
