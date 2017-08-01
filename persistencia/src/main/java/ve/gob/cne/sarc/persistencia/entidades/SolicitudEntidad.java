package ve.gob.cne.sarc.persistencia.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import java.io.Serializable;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import ve.gob.cne.sarc.persistencia.disparadores.SolicitudDisparador;

/**
 * SolicitudEntidad.java
 *
 * @descripcion Se crea la clase SolicitudEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X010T_SOLICITUD")
@EntityListeners({SolicitudDisparador.class})
@NamedQueries({
    @NamedQuery(name = SolicitudEntidad.BUSCAR_POR_DOC_SOLICITANTE_DEC, query = "SELECT sol "
            + "FROM   SolicitudEntidad sol "
            + "WHERE  sol.solicitante.solicitanteTipDocDec.nombre = :nombre "
            + "AND    sol.solicitante.numeroDocumentoOficio  = :numeroDocumento "
            + "ORDER BY sol.numero"),
    @NamedQuery(name = SolicitudEntidad.BUSCAR_POR_DOC_SOLICITANTE_ENTE, query = "SELECT sol "
            + "FROM   SolicitudEntidad sol "
            + "WHERE  sol.solicitante.solicitanteTipDocEnte.id = :id "
            + "AND    sol.solicitante.numeroDocumentoOficio  = :numeroDocumento "
            + "ORDER BY sol.numero"),
    @NamedQuery(name = SolicitudEntidad.BUSCAR_SOLICITUD_POR_USUARIO, query = "SELECT sol "
            + "FROM  SolicitudEntidad sol, IN(sol.oficinaFuncionario.usuarios) usu "
            + "WHERE usu.nombre= :nombre "
            + "ORDER BY sol.fechaInicio DESC"),

    @NamedQuery(name = SolicitudEntidad.BUSCAR_SOLICITUD_POR_USUARIO_Y_CODIGO, query = "SELECT sol "
            + "FROM  SolicitudEntidad sol, IN(sol.oficinaFuncionario.usuarios) usu "
            + "WHERE usu.nombre= :nombre "
            + "and sol.estatus.id IN ('1','2','3','4','5') "
            + "ORDER BY sol.fechaInicio DESC"),})

public class SolicitudEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_DOC_SOLICITANTE_DEC = "SolicitudEntidad.buscarPorDocSolicitanteDec";
    public static final String BUSCAR_POR_DOC_SOLICITANTE_ENTE = "SolicitudEntidad.buscarPorDocSolicitanteEnte";

    public static final String BUSCAR_SOLICITUD_POR_USUARIO = "SolicitudEntidad.buscarSolicitudPorUsuario";
    public static final String BUSCAR_SOLICITUD_POR_USUARIO_Y_CODIGO
            = "SolicitudEntidad.buscarSolicitudPorUsuarioYcodigo";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_SOLICITUD", nullable = false, length = 22)
    @SequenceGenerator(name = "SOLICITUD_SEQ", sequenceName = "X010S_CO_SOLICITUD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOLICITUD_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TRAMITE", referencedColumnName = "CO_TRAMITE", nullable = false)
    private TramiteEntidad tramite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_SOLICITUD_ESTATUS", referencedColumnName = "CO_SOLICITUD_ESTATUS", nullable = false)
    private SolicitudEstatusEntidad estatus;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA_FUNCIONARIO", referencedColumnName = "CO_OFICINA_FUNCIONARIO",
            nullable = false)
    private OficinaFuncionarioEntidad oficinaFuncionario;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_SOLICITANTE", referencedColumnName = "CO_SOLICITANTE", nullable = true)
    private SolicitanteEntidad solicitante;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_LIBRO", referencedColumnName = "CO_LIBRO", nullable = true)
    private LibroEntidad libro;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ESTATUS_TRAMITE", referencedColumnName = "", nullable = false)
    private EstatusTramiteEntidad estatusTramite;

    @Basic(optional = false)
    @Column(unique = true, name = "NU_SOLICITUD", nullable = false, length = 15)
    private String numero;

    @Basic(optional = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "FE_INICIO", nullable = false)
    private DateTime fechaInicio;

    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fechaFin;

    @Basic(optional = true)
    @Column(name = "FE_NOTIFICACION", nullable = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fechaNotificacion;

    @Basic(optional = true)
    @Column(name = "TX_OBSERVACION", nullable = true, length = 200)
    private String observacion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_DIRECTOR_ORE", referencedColumnName = "CO_OFICINA_FUNCIONARIO", nullable = true)
    private OficinaFuncionarioEntidad directorOre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitud")
    private List<DeclaracionJuradaEntidad> declaracionesJuradas = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitud", cascade = CascadeType.PERSIST)
    private List<DiarioEntidad> diarios = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitud", cascade = CascadeType.PERSIST)
    private List<InscripcionMayor18Entidad> inscripcionMayor18 = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitud", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActaEntidad> acta = new ArrayList<>();

    public SolicitudEntidad() {
        this.solicitante = new SolicitanteEntidad();
        this.solicitante.setId(0L);
    }

    /**
     * Constructor con  parámetros
     * @param tramite
     */
    public SolicitudEntidad(TramiteEntidad tramite) {
        this.tramite = tramite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TramiteEntidad getTramite() {
        return tramite;
    }

    public void setTramite(TramiteEntidad tramite) {
        this.tramite = tramite;
    }

    public OficinaFuncionarioEntidad getOficinaFuncionario() {
        return oficinaFuncionario;
    }

    public void setOficinaFuncionario(
            OficinaFuncionarioEntidad oficinaFuncionario) {
        this.oficinaFuncionario = oficinaFuncionario;
    }

    public SolicitanteEntidad getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(SolicitanteEntidad solicitante) {
        this.solicitante = solicitante;
    }

    public LibroEntidad getLibro() {
        return libro;
    }

    public void setLibro(LibroEntidad libro) {
        this.libro = libro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public SolicitudEstatusEntidad getEstatus() {
        return estatus;
    }

    public void setEstatus(SolicitudEstatusEntidad estatus) {
        this.estatus = estatus;
    }

    public DateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(DateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public DateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(DateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public DateTime getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(DateTime fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<DeclaracionJuradaEntidad> getDeclaracionesJuradas() {
        return declaracionesJuradas;
    }

    public void setDeclaracionesJuradas(
            List<DeclaracionJuradaEntidad> declaracionesJuradas) {
        this.declaracionesJuradas = declaracionesJuradas;
    }

    public List<InscripcionMayor18Entidad> getInscripcionMayor18() {
        return inscripcionMayor18;
    }

    public void setInscripcionMayor18(
            List<InscripcionMayor18Entidad> inscripcionMayor18) {
        this.inscripcionMayor18 = inscripcionMayor18;
    }

    public List<DiarioEntidad> getDiarios() {
        return diarios;
    }

    public void setDiarios(List<DiarioEntidad> diarios) {
        this.diarios = diarios;
    }

    public EstatusTramiteEntidad getEstatusTramite() {
        return estatusTramite;
    }

    public void setEstatusTramite(EstatusTramiteEntidad estatusTramite) {
        this.estatusTramite = estatusTramite;
    }

    public List<ActaEntidad> getActa() {
        return acta;
    }

    public void setActa(List<ActaEntidad> acta) {
        this.acta = acta;
    }

    /**
     * @return directorOre
     */
    public OficinaFuncionarioEntidad getDirectorOre() {
        return directorOre;
    }

    /**
     * @param directorOre
     */
    public void setDirectorOre(OficinaFuncionarioEntidad directorOre) {
        this.directorOre = directorOre;
    }

}
