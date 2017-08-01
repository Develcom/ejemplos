package ve.gob.cne.sarc.persistencia.entidades;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.ParticipanteDisparador;

/**
 * ParticipanteEntidad.java
 *
 * @descripcion Se crea la clase ParticipanteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 02/11/2016
 */
@Entity
@Table(name = "Y001T_PARTICIPANTE")
@EntityListeners({ParticipanteDisparador.class})
@NamedQueries({
    @NamedQuery(name = ParticipanteEntidad.BUSCAR_POR_NUMEROIDENTIDAD, query = " SELECT par "
            + "FROM   ParticipanteEntidad par " + "WHERE par.numeroDocIdentidad = :numeroDocIdentidad "),

    @NamedQuery(name = ParticipanteEntidad.BUSCAR_POR_SOLICITUDYTIPOPARTICIPANTE, query = "SELECT par "
            + "FROM ParticipanteEntidad par "
            + "WHERE par.acta.solicitud.numero=:numero "
            + "and par.tipoParticipante.id=:id "),

    @NamedQuery(name = ParticipanteEntidad.BUSCAR_POR_SOLICITUD, query = " SELECT par "
            + "FROM   ParticipanteEntidad par "
            + "WHERE par.acta.solicitud.numero =:numero"),

    @NamedQuery(name = ParticipanteEntidad.BUSCAR_POR_ACTA, query = " SELECT par "
            + "FROM   ParticipanteEntidad par " + "WHERE par.acta.numeroActa = :numeroActa"),
    @NamedQuery(name = ParticipanteEntidad.BUSCAR_POR_SOLICITUD_LIST, query = "SELECT par "
            + "FROM   ParticipanteEntidad par, IN(par.acta.solicitud) sol "
            + "WHERE sol.numero = :numero "
            + "order by par.id")
})

public class ParticipanteEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_POR_ACTA
            = "ParticipanteEntidad.buscarPorNumeroActa";
    public static final String BUSCAR_POR_NUMEROIDENTIDAD
            = "ParticipanteEntidad.buscarPorNumeroDocIdentidad";
    public static final String BUSCAR_POR_SOLICITUDYTIPOPARTICIPANTE
            = "ParticipanteEntidad.buscarPorSolicitudYTipoParticipante";
    public static final String BUSCAR_POR_SOLICITUD
            = "ParticipanteEntidad.buscarPorSolicitud";
    public static final String BUSCAR_POR_SOLICITUD_LIST
            = "ParticipanteEntidad.buscarPorNumeroSolicitud";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_PARTICIPANTE", nullable = false, length = 22)
    @SequenceGenerator(name = "PARTICIPANTE_SEQ", sequenceName = "Y001S_CO_PARTICIPANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PARTICIPANTE_SEQ")
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_NACIONALIDAD", referencedColumnName = "CO_NACIONALIDAD", nullable = true)
    private NacionalidadEntidad nacionalidad;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_OCUPACION", referencedColumnName = "CO_OCUPACION", nullable = true)
    private OcupacionEntidad ocupacion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_TIPO_PARTICIPANTE", referencedColumnName = "CO_TIPO_PARTICIPANTE", nullable = true)
    private TipoParticipanteEntidad tipoParticipante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ACTA", referencedColumnName = "CO_ACTA", nullable = false)
    private ActaEntidad acta;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_DECLARACION_JURADA", referencedColumnName = "CO_DECLARACION_JURADA",
            nullable = true)
    private DeclaracionJuradaEntidad declaracionJurada;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_PARTICIPANTE_SIN_DOC", referencedColumnName = "CO_PARTICIPANTE_SIN_DOC",
            nullable = true)
    private ParticipanteSinDocEntidad participanteSinDoc;

    @Basic(optional = true)
    @OneToOne(optional = true, mappedBy = "participante")
    private DocumentoPublicoEntidad documentoPublico;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_TIPO_DOC_IDENTIDAD", referencedColumnName = "CO_TIPO_DOC_IDENTIDAD",
            nullable = true)
    private TipoDocIdentidadEntidad tipoDocumentoIdentidad;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_APELLIDO", nullable = false, length = 50)
    private String primerApellido;

    @Basic(optional = false)
    @Column(name = "NB_SEGUNDO_APELLIDO", length = 50)
    private String segundoApellido;

    @Basic(optional = false)
    @Column(name = "NB_PRIMER_NOMBRE", nullable = false, length = 50)
    private String primerNombre;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE", length = 50)
    private String segundoNombre;

    @Basic(optional = true)
    @Column(name = "TX_CORREO_ELECTRONICO", length = 50)
    private String correoElectronico;

    @Basic(optional = true)
    @Column(name = "NU_TELEFONO_FIJO", length = 20)
    private String telefonoFijo;

    @Basic(optional = true)
    @Column(name = "NU_TELEFONO_MOVIL", length = 20)
    private String telefonoMovil;

    @Basic(optional = true)
    @Column(name = "IN_AUTENTICAR", length = 2)
    private Integer indicadorAutenticar;

    @Basic(optional = true)
    @Column(name = "IN_TIPO_DOCUMENTO", nullable = true, length = 1)
    private String tipoDocumento;

    @Basic(optional = true)
    @Column(name = "NU_DOCUMENTO_IDENTIDAD", nullable = true, length = 15)
    private String numeroDocIdentidad;

    @Basic(optional = true)
    @Column(name = "IN_SEXO", nullable = false, length = 1)
    private String sexo;

    @Basic(optional = true)
    @Column(name = "IN_PARENTESCO", length = 1)
    private String indicadorParentesco;

    @Basic(optional = true)
    @Column(name = "NU_TIEMPO_RELACION", length = 100)
    private String tiempoRelacion;

    @Basic(optional = true)
    @Column(name = "IN_VIVE", length = 1)
    private String indicadorVive;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_NACIMIENTO", nullable = false)
    private Date fechaNacimiento;

    @Basic(optional = true)
    @Column(name = "IN_ESTADO_CIVIL", length = 5)
    private String estadoCivil;

    @Basic(optional = true)
    @Column(name = "IN_CONDICION_NACIMIENTO", nullable = true, length = 5)
    private Integer condicionNacimiento;

    @Basic(optional = true)
    @Column(name = "TX_OBSERVACION", length = 200)
    private String observacion;

    @Basic(optional = true)
    @Column(name = "DI_LUGAR_NACIMIENTO", length = 200)
    private String lugarNacimiento;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "participante",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DireccionParticipanteEntidad> direccionesParticipantes = new ArrayList<>();

    @OneToOne(optional = true, fetch = FetchType.LAZY, mappedBy = "participante",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private CartaConsejoComunalEntidad cartaConsejoComunal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acta",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidad;

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_COMUNIDAD_INDIGENA", referencedColumnName = "CO_COMUNIDAD_INDIGENA",
            nullable = true)
    private ComunidadIndigenaEntidad comunidadIndigena;

    public ParticipanteEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoParticipanteEntidad getTipoParticipante() {
        return this.tipoParticipante;
    }

    public void setTipoParticipante(TipoParticipanteEntidad tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
    }

    public NacionalidadEntidad getNacionalidad() {
        return this.nacionalidad;
    }

    public void setNacionalidad(NacionalidadEntidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public OcupacionEntidad getOcupacion() {
        return this.ocupacion;
    }

    public void setOcupacion(OcupacionEntidad ocupacion) {
        this.ocupacion = ocupacion;
    }

    public ActaEntidad getActa() {
        return this.acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public DeclaracionJuradaEntidad getDeclaracionJurada() {
        return this.declaracionJurada;
    }

    public void setDeclaracionJurada(DeclaracionJuradaEntidad declaracionJurada) {
        this.declaracionJurada = declaracionJurada;
    }

    public DocumentoPublicoEntidad getDocumentoPublico() {
        return documentoPublico;
    }

    public void setDocumentoPublico(DocumentoPublicoEntidad documentoPublico) {
        this.documentoPublico = documentoPublico;
    }

    public String getPrimerApellido() {
        return this.primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return this.segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return this.primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return this.segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getCorreoElectronico() {
        return this.correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoFijo() {
        return this.telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return this.telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public Integer getIndicadorAutenticar() {
        return this.indicadorAutenticar;
    }

    public void setIndicadorAutenticar(Integer indicadorAutenticar) {
        this.indicadorAutenticar = indicadorAutenticar;
    }

    public String getTipoDocumento() {
        return this.tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocIdentidad() {
        return numeroDocIdentidad;
    }

    public void setNumeroDocIdentidad(String numeroDocIdentidad) {
        this.numeroDocIdentidad = numeroDocIdentidad;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIndicadorParentesco() {
        return this.indicadorParentesco;
    }

    public void setIndicadorParentesco(String indicadorParentesco) {
        this.indicadorParentesco = indicadorParentesco;
    }

    public String getTiempoRelacion() {
        return this.tiempoRelacion;
    }

    public void setTiempoRelacion(String tiempoRelacion) {
        this.tiempoRelacion = tiempoRelacion;
    }

    public String getIndicadorVive() {
        return this.indicadorVive;
    }

    public void setIndicadorVive(String indicadorVive) {
        this.indicadorVive = indicadorVive;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstadoCivil() {
        return this.estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Integer getCondicionNacimiento() {
        return condicionNacimiento;
    }

    public void setCondicionNacimiento(Integer condicionNacimiento) {
        this.condicionNacimiento = condicionNacimiento;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public CartaConsejoComunalEntidad getCartaConsejoComunal() {
        return cartaConsejoComunal;
    }

    public void setCartaConsejoComunal(
            CartaConsejoComunalEntidad cartaConsejoComunal) {
        this.cartaConsejoComunal = cartaConsejoComunal;
    }

    public ParticipanteSinDocEntidad getParticipanteSinDoc() {
        return participanteSinDoc;
    }

    public void setParticipanteSinDoc(ParticipanteSinDocEntidad participanteSinDoc) {
        this.participanteSinDoc = participanteSinDoc;
    }

    public List<DireccionParticipanteEntidad> getDireccionesParticipantes() {
        return direccionesParticipantes;
    }

    public void setDireccionesParticipantes(
            List<DireccionParticipanteEntidad> direccionesParticipantes) {
        this.direccionesParticipantes = direccionesParticipantes;
    }

    public List<EcuParticipanteActaEntidad> getEcuParticipanteActaEntidadList() {
        return ecuParticipanteActaEntidad;
    }

    public void setEcuParticipanteActaEntidadList(List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidadList) {
        this.ecuParticipanteActaEntidad = ecuParticipanteActaEntidadList;
    }

    public TipoDocIdentidadEntidad getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(
            TipoDocIdentidadEntidad tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }

    public List<EcuParticipanteActaEntidad> getEcuParticipanteActaEntidad() {
        return ecuParticipanteActaEntidad;
    }

    public void setEcuParticipanteActaEntidad(
            List<EcuParticipanteActaEntidad> ecuParticipanteActaEntidad) {
        this.ecuParticipanteActaEntidad = ecuParticipanteActaEntidad;
    }

    public ComunidadIndigenaEntidad getComunidadIndigena() {
        return comunidadIndigena;
    }

    public void setComunidadIndigena(ComunidadIndigenaEntidad comunidadIndigena) {
        this.comunidadIndigena = comunidadIndigena;
    }

}
