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

import ve.gob.cne.sarc.persistencia.disparadores.PaisDisparador;

/**
 * PaisEntidad.java
 *
 * @descripcion Se crea la clase PaisEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C001T_PAIS")
@EntityListeners({PaisDisparador.class})

public class PaisEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PAIS", nullable = false, length = 22)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_PAIS", nullable = false, length = 50)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pais", cascade = {CascadeType.PERSIST})
    private List<EstadoEntidad> estados = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pais", cascade = {CascadeType.PERSIST})
    private List<CiudadEntidad> ciudad = new ArrayList<>();

    public PaisEntidad() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<EstadoEntidad> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoEntidad> estados) {
        this.estados = estados;
    }

    public List<CiudadEntidad> getCiudad() {
        return ciudad;
    }

    public void setCiudad(List<CiudadEntidad> ciudad) {
        this.ciudad = ciudad;
    }
}
