package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
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

import ve.gob.cne.sarc.persistencia.disparadores.EstadoEcuDisparador;

/**
 * EstadoEcuEntidad.java
 *
 * @descripcion Se crea la clase EstadoEcuEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "C063T_ESTADO_ECU")
@EntityListeners({EstadoEcuDisparador.class})

public class EstadoEcuEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ESTADO_ECU", nullable = false, length = 22)
    private long id;

    @Column(name = "TX_DESCRIPCION")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = false)
    @Column(name = "NB_ESTADO_ECU", nullable = false)
    private String nombreEstadoEcu;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoEcu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EcuEntidad> listaEcu = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estadoEcu", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<AuditoriaEcuEntidad> auditoriaEcuEntidad;

    public EstadoEcuEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getNombreEstadoEcu() {
        return nombreEstadoEcu;
    }

    public void setNombreEstadoEcu(String nombreEstadoEcu) {
        this.nombreEstadoEcu = nombreEstadoEcu;
    }

    public List<EcuEntidad> getListaEcu() {
        return listaEcu;
    }

    public void setListaEcu(List<EcuEntidad> listaEcu) {
        this.listaEcu = listaEcu;
    }

    public List<AuditoriaEcuEntidad> getAuditoriaEcuEntidad() {
        return auditoriaEcuEntidad;
    }

    public void setAuditoriaEcuEntidad(List<AuditoriaEcuEntidad> auditoriaEcuEntidad) {
        this.auditoriaEcuEntidad = auditoriaEcuEntidad;
    }

}
