package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.disparadores.CiudadDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CiudadEntidad.java
 *
 * @descripcion Se crea la clase CiudadEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
@Entity
@Table(name = "C029T_CIUDAD")
@EntityListeners({CiudadDisparador.class})

public class CiudadEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_CIUDAD", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_PAIS", referencedColumnName = "CO_PAIS", nullable = false)
    private PaisEntidad pais;

    @Basic(optional = false)
    @Column(name = "NB_CIUDAD", nullable = false, length = 50)
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

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ciudad", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaEntidad> oficinas = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ciudad", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<DireccionCiudadanoEntidad> direccionesCiudadanos = new ArrayList<>();

    public CiudadEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaisEntidad getPais() {
        return pais;
    }

    public void setPais(PaisEntidad pais) {
        this.pais = pais;
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

    public List<OficinaEntidad> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<OficinaEntidad> oficinas) {
        this.oficinas = oficinas;
    }

    public List<DireccionCiudadanoEntidad> getDireccionesCiudadanos() {
        return direccionesCiudadanos;
    }

    public void setDireccionesCiudadanos(
            List<DireccionCiudadanoEntidad> direccionesCiudadanos) {
        this.direccionesCiudadanos = direccionesCiudadanos;
    }

}
