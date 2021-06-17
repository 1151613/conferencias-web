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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
@Table(name = "archivo_adjunto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArchivoAdjunto.findAll", query = "SELECT a FROM ArchivoAdjunto a")
    , @NamedQuery(name = "ArchivoAdjunto.findByIdArchivo", query = "SELECT a FROM ArchivoAdjunto a WHERE a.idArchivo = :idArchivo")
    , @NamedQuery(name = "ArchivoAdjunto.findByNombre", query = "SELECT a FROM ArchivoAdjunto a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "ArchivoAdjunto.findByUrl", query = "SELECT a FROM ArchivoAdjunto a WHERE a.url = :url")})
public class ArchivoAdjunto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_archivo")
    private Integer idArchivo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "id_sesion", referencedColumnName = "id_sesion")
    @ManyToOne(optional = false)
    private Sesion idSesion;

    public ArchivoAdjunto() {
    }

    public ArchivoAdjunto(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public ArchivoAdjunto(Integer idArchivo, String nombre, String descripcion, String url) {
        this.idArchivo = idArchivo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
    }

    public Integer getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Integer idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Sesion getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Sesion idSesion) {
        this.idSesion = idSesion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivo != null ? idArchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArchivoAdjunto)) {
            return false;
        }
        ArchivoAdjunto other = (ArchivoAdjunto) object;
        if ((this.idArchivo == null && other.idArchivo != null) || (this.idArchivo != null && !this.idArchivo.equals(other.idArchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.ArchivoAdjunto[ idArchivo=" + idArchivo + " ]";
    }
    
}
