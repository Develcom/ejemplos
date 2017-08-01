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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.OficinaDisparador;
import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaTranscritaEntidad;

/**
 * OficinaEntidad.java
 *
 * @descripcion Se crea la clase OficinaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C067T_OFICINA")
@EntityListeners({OficinaDisparador.class})
@NamedQueries({
    @NamedQuery(name = OficinaEntidad.BUSCAR_TODOS, query = "SELECT ofi "
            + "FROM   OficinaEntidad ofi"),
    @NamedQuery(name = OficinaEntidad.BUSCAR_POR_ID, query = "SELECT ofi "
            + "FROM   OficinaEntidad ofi WHERE  ofi.id = :id"),
    @NamedQuery(name = OficinaEntidad.BUSCAR_POR_GEOGRAFICO, query = "SELECT ofi "
            + "FROM   OficinaEntidad ofi WHERE  ofi.ambito = :ambito AND ofi.tipoOficina = :tipoOficina"),
    @NamedQuery(name = OficinaEntidad.BUSCAR_OFICINA_ORE, query = "SELECT ofiDepende "
            + "FROM OficinaEntidad ofic, OficinaEntidad ofiDepende "
            + "WHERE ofic.id =:id "
            + "and ofiDepende.id = ofic.oficina "
            + "and ofiDepende.tipoOficina.id='10' ")
})
@ClassEspecification(name = "Oficina", identifier = "Nombre",
        generatesTask = ClassEspecification.SincronizationPolicy.NONE)
public class OficinaEntidad implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_TODOS = "OficinaEntidad.buscarTodos";
    public static final String BUSCAR_POR_ID = "OficinaEntidad.buscarPorId";
    public static final String BUSCAR_POR_CODIGO = "OficinaEntidad.buscarPorCodigo";
    public static final String BUSCAR_POR_GEOGRAFICO = "OficinaEntidad.buscarPorGeografico";
    public static final String BUSCAR_OFICINA_ORE = "OficinaEntidad.buscarOficinaOre";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA", nullable = false, length = 22)
    @SequenceGenerator(name = "OFICINA_SEQ", sequenceName = "C067S_CO_OFICINA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFICINA_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_OFICINA", nullable = false, length = 255)
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
    private String nombre;
    
    @FieldEspecification(required = true, input = InputType.SELECT,
    type = "java.lang.Long", label = "Tipo Geogr&aacute;fico", order = 3, step = 0)
    @Transient
    private TipoGeograficoEntidad tipoGeografico;

    @Basic(optional = false)
    @Column(name = "IDEN_OFICINA", nullable = false, length = 15)
    @FieldEspecification(required = true, size = 15, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "00000-0000")
    private String codigo;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    @FieldEspecification(hide = true, type = "java.util.Date", order = 13, step = 0)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "NU_GACETA", nullable = true, length = 20)
    @FieldEspecification(size = 8, type = "java.lang.Integer", label = "N&uacute;mero de la Gaceta",
            order = 14, step = 0, constraints = ">=0", placeHolder = "Ejemplo: 1234")
    private Integer numero;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_GACETA", nullable = true)
    @FieldEspecification(input = InputType.DATEINPUT, type = "java.util.Date", label = "Fecha Gaceta",
            order = 12, step = 0)
    private Date fechaGaceta;

    @Basic(optional = false)
    @Column(name = "DI_UBICACION", nullable = true, length = 500)
    @FieldEspecification(required = true, size = 500, input = InputType.TEXTAREAINPUT,
            label = "Direcci&oacute;n", order = 10, step = 0)
    private String direccion;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 500)
    @FieldEspecification(size = 500, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", order = 9, step = 0)
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "CO_GEOGRAFICO", unique = true, nullable = false)
    @FieldEspecification(required = true, input = InputType.SELECT,
    type = "java.lang.Long", label = "Geogr&aacute;fico", order = 4, step = 0)
    private long geografico;

    @Basic(optional = false)
    @Column(name = "CO_AMBITO", unique = true, nullable = false)
    @FieldEspecification(required = true, size = 8, type = "java.lang.Integer", label = "&Aacute;mbito",
            order = 15, step = 0, constraints = ">=0", placeHolder = "Ejemplo: 1234")
    private int ambito;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TIPO_OFICINA", referencedColumnName = "CO_TIPO_OFICINA", nullable = false)
    @FieldEspecification(required = true, input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Tipo Oficina", order = 6, step = 0)
    private TipoOficinaEntidad tipoOficina;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_OFICINADEPENDE",
            referencedColumnName = "CO_OFICINA", nullable = true)
    @FieldEspecification(input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS, type = "java.lang.Long",
            label = "Dependencia de la Oficina", order = 5, step = 0)
    private OficinaEntidad oficina;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaEntidad> oficinaDepende = new ArrayList<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_CIUDAD", referencedColumnName = "CO_CIUDAD", nullable = true)
    private CiudadEntidad ciudad;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_PARROQUIA", referencedColumnName = "CO_PARROQUIA", nullable = true)
    private ParroquiaEntidad parroquia;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA_CONEXION",
            referencedColumnName = "CO_OFICINA_CONEXION", nullable = false)
    @FieldEspecification( required = true,input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Conexi&oacute;n", order = 8, step = 0)
    private OficinaConexionEntidad oficinaConexion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA_ESTATUS",
            referencedColumnName = "CO_OFICINA_ESTATUS", nullable = false)
    @FieldEspecification(required = true, input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Estatus Oficina", order = 7, step = 0)
    private OficinaEstatusEntidad oficinaEstatus;

    @OneToMany(mappedBy = "oficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaFuncionarioEntidad> oficinasFuncionarios = new ArrayList<>();
    // bi-directional many-to-one association to ActaEntity
    @OneToMany(mappedBy = "codigoOficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActaTranscritaEntidad> actasOficina = new ArrayList<>();
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<LibroDiarioEntidad> libroDiario = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<NuiEntidad> nui = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaPeticionPaqueteNuiEntidad> OficinaPeticionPaqueteNui = new ArrayList<>();

    public OficinaEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoOficinaEntidad getTipoOficina() {
        return tipoOficina;
    }

    public void setTipoOficina(TipoOficinaEntidad tipoOficina) {
        this.tipoOficina = tipoOficina;
    }

    public long getGeografico() {
        return geografico;
    }

    public void setGeografico(long geografico) {
        this.geografico = geografico;
    }
    
    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getAmbito() {
        return ambito;
    }

    public void setAmbito(Integer ambito) {
        this.ambito = ambito;
    }

    public Date getFechaGaceta() {
        return fechaGaceta;
    }

    public void setFechaGaceta(Date fechaGaceta) {
        this.fechaGaceta = fechaGaceta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<OficinaEntidad> getOficinaDepende() {
        return oficinaDepende;
    }

    public void setOficinaDepende(List<OficinaEntidad> oficinaDepende) {
        this.oficinaDepende = oficinaDepende;
    }

    public CiudadEntidad getCiudad() {
        return ciudad;
    }

    public void setCiudad(CiudadEntidad ciudad) {
        this.ciudad = ciudad;
    }

    public ParroquiaEntidad getParroquia() {
        return parroquia;
    }

    public void setParroquia(ParroquiaEntidad parroquia) {
        this.parroquia = parroquia;
    }

    public OficinaConexionEntidad getOficinaConexion() {
        return oficinaConexion;
    }

    public void setOficinaConexion(OficinaConexionEntidad oficinaConexion) {
        this.oficinaConexion = oficinaConexion;
    }

    public OficinaEstatusEntidad getOficinaEstatus() {
        return oficinaEstatus;
    }

    public void setOficinaEstatus(OficinaEstatusEntidad oficinaEstatus) {
        this.oficinaEstatus = oficinaEstatus;
    }

    public List<OficinaFuncionarioEntidad> getOficinasFuncionarios() {
        return oficinasFuncionarios;
    }

    public void setOficinasFuncionarios(List<OficinaFuncionarioEntidad> oficinasFuncionarios) {
        this.oficinasFuncionarios = oficinasFuncionarios;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public List<LibroDiarioEntidad> getLibroDiario() {
        return libroDiario;
    }

    public void setLibroDiario(List<LibroDiarioEntidad> libroDiario) {
        this.libroDiario = libroDiario;
    }

    public List<NuiEntidad> getNui() {
        return nui;
    }

    public void setNui(List<NuiEntidad> nui) {
        this.nui = nui;
    }

    public List<OficinaPeticionPaqueteNuiEntidad> getOficinaPeticionPaqueteNui() {
        return OficinaPeticionPaqueteNui;
    }

    public void setOficinaPeticionPaqueteNui(List<OficinaPeticionPaqueteNuiEntidad> OficinaPeticionPaqueteNui) {
        this.OficinaPeticionPaqueteNui = OficinaPeticionPaqueteNui;
    }

    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OficinaEntidad that = (OficinaEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}