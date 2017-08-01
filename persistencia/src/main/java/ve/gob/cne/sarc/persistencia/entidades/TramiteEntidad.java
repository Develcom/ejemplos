package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ve.gob.cne.sarc.persistencia.disparadores.TramiteDisparador;

/**
 * TramiteEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase TramiteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C014T_TRAMITE")
@EntityListeners({TramiteDisparador.class})
@NamedQueries({
        @NamedQuery(name = TramiteEntidad.BUSCAR_TODOS, query = "SELECT tra "
                + "FROM   TramiteEntidad tra"),
        @NamedQuery(name = TramiteEntidad.BUSCAR_POR_ID, query = "SELECT tra "
                + "FROM   TramiteEntidad tra " + "WHERE  tra.id = :id"),
        @NamedQuery(name = TramiteEntidad.BUSCAR_POR_CODIGO, query = "SELECT tra "
                + "FROM   TramiteEntidad tra "
                + "WHERE  tra.codigo = :codigo "
                + "ORDER BY tra.id"),
        @NamedQuery(name = TramiteEntidad.BUSCAR_POR_NOMBRE, query = "SELECT tra "
                + "FROM   TramiteEntidad tra "
                + "WHERE  UPPER(tra.nombre) = :nombre"),
        @NamedQuery(name = TramiteEntidad.BUSCAR_POR_NOMBRE_PATRON, query = "SELECT tra "
                + "FROM   TramiteEntidad tra "
                + "WHERE  UPPER(tra.nombre) LIKE :nombre"),
        @NamedQuery(name = TramiteEntidad.BUSCAR_POR_CODIGO_MODULO, query = "SELECT tra "
                + "FROM   TramiteEntidad tra "
                + "WHERE  tra.modulo.codigo = :codigoModulo "
                + "ORDER BY tra.id")
})
@ClassEspecification(name = "Trámite", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class TramiteEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "TramiteEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "TramiteEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_CODIGO = "TramiteEntidad.buscarPorCodigo";
    public static final String BUSCAR_POR_NOMBRE = "TramiteEntidad.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "TramiteEntidad.BUSCAR_POR_NOMBRE_PATRON";
    public static final String BUSCAR_POR_CODIGO_MODULO = "TramiteEntidad.buscarPorCodigoModulo";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TRAMITE", nullable = false, length = 22)
    @SequenceGenerator(name = "TRAMITE_SEQ", sequenceName = "C014S_CO_TRAMITE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAMITE_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "IDEN_TRAMITE", nullable = false, unique = true, length = 5)
    @FieldEspecification(required = true, size = 5, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "Ejemplo: AAA00")
    private String codigo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_MODULO", referencedColumnName = "CO_MODULO", nullable = false)
    @FieldEspecification(
            required = true,
            input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Modulo", order = 3, step = 0)
    private ModuloEntidad modulo;

    @Basic(optional = false)
    @Column(name = "NB_TRAMITE", nullable = false, length = 150)
    @FieldEspecification(required = true, size = 150, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(size = 200, input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tramite", cascade = {CascadeType.PERSIST})
    private List<SolicitudEntidad> solicitudes = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tramite", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TramiteRolEntidad> tramitesRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tramite", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EstatusTramiteEntidad> estatusTramite = new ArrayList<>();
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tramite", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TramiteTipoParticipanteEntidad> tramitesTiposParticipantes = new ArrayList<>();

    public TramiteEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ModuloEntidad getModulo() {
        return modulo;
    }

    public void setModulo(ModuloEntidad modulo) {
        this.modulo = modulo;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public List<SolicitudEntidad> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudEntidad> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<TramiteRolEntidad> getTramitesRoles() {
        return tramitesRoles;
    }

    public void setTramitesRoles(List<TramiteRolEntidad> tramitesRoles) {
        this.tramitesRoles = tramitesRoles;
    }

    public List<EstatusTramiteEntidad> getEstatusTramite() {
        return this.estatusTramite;
    }

    public void setEstatusTramite(List<EstatusTramiteEntidad> estatusTramite) {
        this.estatusTramite = estatusTramite;
    }

    public List<TramiteTipoParticipanteEntidad> getTramitesTiposParticipantes() {
        return tramitesTiposParticipantes;
    }

    public void setTramitesTiposParticipantes(List<TramiteTipoParticipanteEntidad> tramitesTiposParticipantes) {
        this.tramitesTiposParticipantes = tramitesTiposParticipantes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TramiteEntidad that = (TramiteEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }
}
