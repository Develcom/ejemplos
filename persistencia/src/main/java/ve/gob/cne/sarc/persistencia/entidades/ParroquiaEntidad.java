package ve.gob.cne.sarc.persistencia.entidades;

import org.hibernate.annotations.GenericGenerator;

import ve.gob.cne.sarc.persistencia.disparadores.ParroquiaDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ParroquiaEntidad.java
 *
 * @author Oscar Montilla
 * @version 07/09/2016
 */
@Entity
@Table(name = "C004T_PARROQUIA", schema = "SARC")
@EntityListeners({ParroquiaDisparador.class})
@NamedQueries({
    @NamedQuery(name = ParroquiaEntidad.BUSCAR_POR_NOMBRE, query = "SELECT par " + "FROM   ParroquiaEntidad par "
            + "WHERE  UPPER(par.nombre) = :nombre"),
    @NamedQuery(name = ParroquiaEntidad.BUSCAR_POR_CODIGO_MUNICIPIO, query = "SELECT par "
            + "FROM   ParroquiaEntidad par " + "WHERE  par.municipio.id = :id")})

public class ParroquiaEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_CODIGO_MUNICIPIO = "ParroquiaEntidad.findByMunicipioOrderByNombreAsc";
    public static final String BUSCAR_POR_NOMBRE = "ParroquiaEntidad.buscarPorNombre";

    private long id;
    private MunicipioEntidad municipio;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private String descripcion;
    private List<OficinaEntidad> oficinas = new ArrayList<>();
    private List<DefuncionEntidad> defunciones = new ArrayList<>();
    private List<DireccionParticipanteEntidad> direccionesParticipantes = new ArrayList<>();
    private List<InsercionEntidad> inserciones = new ArrayList<>();
    private List<DireccionCiudadanoEntidad> direccionesCiudadanos = new ArrayList<>();

    public ParroquiaEntidad() {
        // Metodo Constructor
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PARROQUIA", nullable = false, length = 22)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_MUNICIPIO", referencedColumnName = "CO_MUNICIPIO")
    public MunicipioEntidad getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioEntidad municipio) {
        this.municipio = municipio;
    }

    @Basic(optional = false)
    @Column(name = "NB_PARROQUIA", nullable = false, length = 100)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parroquia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<OficinaEntidad> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<OficinaEntidad> oficinas) {
        this.oficinas = oficinas;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parroquia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<DefuncionEntidad> getDefunciones() {
        return defunciones;
    }

    public void setDefunciones(List<DefuncionEntidad> defunciones) {
        this.defunciones = defunciones;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parroquia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<DireccionParticipanteEntidad> getDireccionesParticipantes() {
        return direccionesParticipantes;
    }

    public void setDireccionesParticipantes(List<DireccionParticipanteEntidad> direccionesParticipantes) {
        this.direccionesParticipantes = direccionesParticipantes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parroquia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<InsercionEntidad> getInserciones() {
        return inserciones;
    }

    public void setInserciones(List<InsercionEntidad> inserciones) {
        this.inserciones = inserciones;
    }

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parroquia", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public List<DireccionCiudadanoEntidad> getDireccionesCiudadanos() {
        return direccionesCiudadanos;
    }

    public void setDireccionesCiudadanos(List<DireccionCiudadanoEntidad> direccionesCiudadanos) {
        this.direccionesCiudadanos = direccionesCiudadanos;
    }
}
