package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.TipoEstatusDisparador;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;

/**
 * TipoEstatusEntidad.java
 *
 * @descripcion Se crea la clase TipoEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C062T_TIPO_ESTATUS")
@EntityListeners({TipoEstatusDisparador.class})
@ClassEspecification(name = "Tipo de Estatus", identifier = "Nombre", generatesTask=SincronizationPolicy.BROADCAST)
public class TipoEstatusEntidad implements Serializable {

    private static final long serialVersionUID = -6455771008790728720L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_ESTATUS", nullable = false, length = 22)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_TIPO_ESTATUS", nullable = false, length = 50)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoEstatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EstatusEntidad> estatus;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    public TipoEstatusEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<EstatusEntidad> getEstatus() {
        return estatus;
    }

    public void setEstatus(List<EstatusEntidad> estatus) {
        this.estatus = estatus;
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
    
    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

}
