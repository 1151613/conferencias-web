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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "conferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conferencia.findAll", query = "SELECT c FROM Conferencia c")
    , @NamedQuery(name = "Conferencia.findByIdConferencia", query = "SELECT c FROM Conferencia c WHERE c.idConferencia = :idConferencia")
    , @NamedQuery(name = "Conferencia.findByNombre", query = "SELECT c FROM Conferencia c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Conferencia.findBySigla", query = "SELECT c FROM Conferencia c WHERE c.sigla = :sigla")
    , @NamedQuery(name = "Conferencia.findByUrl", query = "SELECT c FROM Conferencia c WHERE c.url = :url")
    , @NamedQuery(name = "Conferencia.findByFechaInicio", query = "SELECT c FROM Conferencia c WHERE c.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Conferencia.findByFechaFin", query = "SELECT c FROM Conferencia c WHERE c.fechaFin = :fechaFin")})
public class Conferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_conferencia")
    private Integer idConferencia;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "sigla")
    private String sigla;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @Basic(optional = false)
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @Column(name = "fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConferencia")
    private List<Topico> topicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConferencia")
    private List<Dia> diaList;

    public Conferencia() {
    }

    public Conferencia(Integer idConferencia) {
        this.idConferencia = idConferencia;
    }

    public Conferencia(Integer idConferencia, String nombre, String sigla, String descripcion, String url, Date fechaInicio, Date fechaFin) {
        this.idConferencia = idConferencia;
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.url = url;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getIdConferencia() {
        return idConferencia;
    }

    public void setIdConferencia(Integer idConferencia) {
        this.idConferencia = idConferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @XmlTransient
    public List<Topico> getTopicoList() {
        return topicoList;
    }

    public void setTopicoList(List<Topico> topicoList) {
        this.topicoList = topicoList;
    }

    @XmlTransient
    public List<Dia> getDiaList() {
        return diaList;
    }

    public void setDiaList(List<Dia> diaList) {
        this.diaList = diaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConferencia != null ? idConferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conferencia)) {
            return false;
        }
        Conferencia other = (Conferencia) object;
        if ((this.idConferencia == null && other.idConferencia != null) || (this.idConferencia != null && !this.idConferencia.equals(other.idConferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Conferencia[ idConferencia=" + idConferencia + " ]";
    }
    
}
