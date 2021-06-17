/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author berserker
 */
@Entity
@Table(name = "ponencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ponencia.findAll", query = "SELECT p FROM Ponencia p")
    , @NamedQuery(name = "Ponencia.findByIdPonencia", query = "SELECT p FROM Ponencia p WHERE p.idPonencia = :idPonencia")
    , @NamedQuery(name = "Ponencia.findByTipo", query = "SELECT p FROM Ponencia p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Ponencia.findByTitulo", query = "SELECT p FROM Ponencia p WHERE p.titulo = :titulo")
    , @NamedQuery(name = "Ponencia.findBySpeaker", query = "SELECT p FROM Ponencia p WHERE p.speaker = :speaker")
    , @NamedQuery(name = "Ponencia.findByIdArticulo", query = "SELECT p FROM Ponencia p WHERE p.idArticulo = :idArticulo")
    , @NamedQuery(name = "Ponencia.findByTituloArticulo", query = "SELECT p FROM Ponencia p WHERE p.tituloArticulo = :tituloArticulo")
    , @NamedQuery(name = "Ponencia.findByAutorArticulo", query = "SELECT p FROM Ponencia p WHERE p.autorArticulo = :autorArticulo")})
public class Ponencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ponencia")
    private Integer idPonencia;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "speaker")
    private String speaker;
    @Basic(optional = false)
    @Column(name = "id_articulo")
    private int idArticulo;
    @Basic(optional = false)
    @Column(name = "titulo_articulo")
    private String tituloArticulo;
    @Basic(optional = false)
    @Column(name = "autor_articulo")
    private String autorArticulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPonencia")
    private List<Asistencia> asistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPonencia")
    private List<Sala> salaList;
    @JoinColumn(name = "id_sesion", referencedColumnName = "id_sesion")
    @ManyToOne(optional = false)
    private Sesion idSesion;

    public Ponencia() {
    }

    public Ponencia(Integer idPonencia) {
        this.idPonencia = idPonencia;
    }

    public Ponencia(Integer idPonencia, String tipo, String titulo, String descripcion, String speaker, int idArticulo, String tituloArticulo, String autorArticulo) {
        this.idPonencia = idPonencia;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.speaker = speaker;
        this.idArticulo = idArticulo;
        this.tituloArticulo = tituloArticulo;
        this.autorArticulo = autorArticulo;
    }

    public Integer getIdPonencia() {
        return idPonencia;
    }

    public void setIdPonencia(Integer idPonencia) {
        this.idPonencia = idPonencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getTituloArticulo() {
        return tituloArticulo;
    }

    public void setTituloArticulo(String tituloArticulo) {
        this.tituloArticulo = tituloArticulo;
    }

    public String getAutorArticulo() {
        return autorArticulo;
    }

    public void setAutorArticulo(String autorArticulo) {
        this.autorArticulo = autorArticulo;
    }

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
    }

    @XmlTransient
    public List<Sala> getSalaList() {
        return salaList;
    }

    public void setSalaList(List<Sala> salaList) {
        this.salaList = salaList;
    }

    public Sesion getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Sesion idSesion) {
        this.idSesion = idSesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPonencia != null ? idPonencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ponencia)) {
            return false;
        }
        Ponencia other = (Ponencia) object;
        if ((this.idPonencia == null && other.idPonencia != null) || (this.idPonencia != null && !this.idPonencia.equals(other.idPonencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Ponencia[ idPonencia=" + idPonencia + " ]";
    }
    
}
