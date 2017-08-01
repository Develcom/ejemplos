package ve.gob.cne.sarc.persistencia.entidades;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import ve.gob.cne.sarc.persistencia.disparadores.InhumacionYCremacionDisparador;

/**
 *
 * InhumacionYCremacionEntidad.java
 *
 * @Descripcion Se crea la clase DefuncionEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 06/09/2016
 */
@Entity
@Table(name = "X054T_PERMISO_INHCRE")
@EntityListeners({InhumacionYCremacionDisparador.class})
@NamedQueries({
    @NamedQuery(name = InhumacionYCremacionEntidad.BUSCAR_POR_CODIGO_INHUMACION_Y_CREMACION, query
            = "SELECT max(INHU.numeroPermiso) "
            + "FROM   InhumacionYCremacionEntidad INHU "
            + "WHERE INHU.solicitud.oficinaFuncionario.oficina.codigo=:codigo"),})

public class InhumacionYCremacionEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_CODIGO_INHUMACION_Y_CREMACION
            = "InhumacionYCremacionEntidad.buscarPorCodigoInhumacionYCremacion";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PERMISO_INHCRE", nullable = false, length = 22)
    @SequenceGenerator(name = "PERMISO_INHCRE_SEQ", sequenceName = "X054S_CO_PERMISO_INHCRE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISO_INHCRE_SEQ")
    private long id;

    @Basic(optional = true)
    @Column(name = "NU_PERMISO", length = 12)
    private long numeroPermiso;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE_AUTORIZA", nullable = false, length = 50)
    private String primerNombre;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_AUTORIZA", nullable = true, length = 50)
    private String segundoNombre;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO_AUTORIZA", nullable = false, length = 50)
    private String primerApellido;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_AUTORIZA", nullable = true, length = 50)
    private String segundoApellido;

    @Basic(optional = false)
    @Column(name = "TIPO_PERMISO", nullable = false, length = 200)
    private String tipoPermiso;

    @Basic(optional = true)
    @Column(name = "TX_OBSERVACION", nullable = true, length = 200)
    private String observacion;

    @Basic(optional = false)
    @Column(name = "NB_CEMENTERIO", nullable = false, length = 200)
    private String nombreCementerio;

    @Basic(optional = true)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "FE_DEFUNCION", nullable = true)
    private DateTime fechaDefuncion;

    @Basic(optional = false)
    @Column(name = "NU_CERTIFICADO_DEFUNCION", nullable = false, length = 12)
    private long numeroCertificado;

    @Basic(optional = true)
    @Column(name = "DI_DEFUNCION", nullable = true, length = 500)
    private String direccion;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_ESTADO", name = "CO_ESTADO", nullable = true)
    private EstadoEntidad estado;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_MUNICIPIO", name = "CO_MUNICIPIO", nullable = true)
    private MunicipioEntidad municipio;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_PARROQUIA", name = "CO_PARROQUIA", nullable = true)
    private ParroquiaEntidad parroquia;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_SOLICITUD", name = "CO_SOLICITUD", nullable = false)
    private SolicitudEntidad solicitud;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PAIS", name = "CO_PAIS", nullable = false)
    private PaisEntidad pais;

    public InhumacionYCremacionEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumeroPermiso() {
        return numeroPermiso;
    }

    public void setNumeroPermiso(long numeroPermiso) {
        this.numeroPermiso = numeroPermiso;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombreCementerio() {
        return nombreCementerio;
    }

    public void setNombreCementerio(String nombreCementerio) {
        this.nombreCementerio = nombreCementerio;
    }

    public DateTime getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(DateTime fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public long getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(long numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public EstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntidad estado) {
        this.estado = estado;
    }

    public MunicipioEntidad getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioEntidad municipio) {
        this.municipio = municipio;
    }

    public ParroquiaEntidad getParroquia() {
        return parroquia;
    }

    public void setParroquia(ParroquiaEntidad parroquia) {
        this.parroquia = parroquia;
    }

    public SolicitudEntidad getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public PaisEntidad getPais() {
        return pais;
    }

    public void setPais(PaisEntidad pais) {
        this.pais = pais;
    }

}
