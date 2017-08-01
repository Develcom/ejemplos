package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.DefuncionDisparador;

/**
 * DefuncionEntidad.java
 *
 * @descripcion Se crea la clase DefuncionEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 25/08/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X019T_DEFUNCION")
@EntityListeners({DefuncionDisparador.class})
@NamedQueries({
    @NamedQuery(name = DefuncionEntidad.BUSCAR_CERTIFICADO_DEFUNCION,
            query = "SELECT def FROM DefuncionEntidad def WHERE "
            + "def.numeroCertificado = :numeroCertificado")
})
public class DefuncionEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_CERTIFICADO_DEFUNCION
            = "DefuncionEntidad.findNumeroCertificado";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_DEFUNCION", nullable = false, length = 22)
    @SequenceGenerator(name = "DEFUNCION_SEQ", sequenceName = "X019S_CO_DEFUNCION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEFUNCION_SEQ")
    private long id;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_PARROQUIA", name = "CO_PARROQUIA", nullable = true)
    private ParroquiaEntidad parroquia;

    @OneToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_ACTA", name = "CO_ACTA")
    private ActaEntidad acta;

    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = true)
    @Column(name = "FE_DEFUNCION", nullable = true)
    private Date fechaDefuncion;

    @Basic(optional = true)
    @Column(name = "TX_CAUSA_DEFUNCION", nullable = true, length = 100)
    private String textoCausa;

    @Basic(optional = true)
    @Column(name = "IN_SEXO", nullable = true, length = 9)
    private String sexo;

    @Basic(optional = true)
    @Column(name = "IN_ESTADO_CIVIL_FALLECIDO", nullable = true, length = 1)
    private String estadoCivil;

    @Basic(optional = true)
    @Column(name = "NB_ESTADO_DEFUNCION", nullable = true, length = 50)
    private String estadoDefuncion;

    @Basic(optional = true)
    @Column(name = "NB_MUNICIPIO_DEFUNCION", nullable = true, length = 50)
    private String municipioDefuncion;

    @Basic(optional = true)
    @Column(name = "NB_PARROQUIA_DEFUNCION", nullable = true, length = 50)
    private String parroquiaDefuncion;

    @Basic(optional = true)
    @Column(name = "NU_CERTIFICADO_DEFUNCION", nullable = true, length = 12)
    private long numeroCertificado;

    @Temporal(TemporalType.TIMESTAMP)
    @Basic(optional = true)
    @Column(name = "FE_CERTIFICADO_DEFUNCION", nullable = true)
    private Date fechaCertificado;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_NOMBRE_MEDICO", nullable = true, length = 50)
    private String primerNombreMedico;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_MEDICO", nullable = true, length = 50)
    private String segundoNombreMedico;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_APELLIDO_MEDICO", nullable = true, length = 50)
    private String primerApellidoMedico;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_MEDICO", nullable = true, length = 50)
    private String segundoApellidoMedico;

    @Basic(optional = true)
    @Column(name = "CO_NUI_MEDICO", nullable = true, length = 15)
    private String documentoIdentidadMedico;

    @Basic(optional = true)
    @Column(name = "NU_MPPS", nullable = true, length = 20)
    private long nuMPPS;

    @Basic(optional = true)
    @Column(name = "NB_CENTRO_SALUD", nullable = true, length = 50)
    private String centroSalud;

    @Basic(optional = true)
    @Column(name = "NU_EXTRACTO_CONSULAR", nullable = true, length = 20)
    private long numeroExtractoConsular;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_EXTRACTO_CONSULAR", nullable = true)
    private Date fechaExtractoConsular;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_NOMBRE_CONSULAR", nullable = true, length = 50)
    private String primerNombreConsular;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_NOMBRE_CONSULAR", nullable = true, length = 50)
    private String segundoNombreConsular;

    @Basic(optional = true)
    @Column(name = "NB_PRIMER_APELLIDO_CONSULAR", nullable = true, length = 50)
    private String primerApellidoConsular;

    @Basic(optional = true)
    @Column(name = "NB_SEGUNDO_APELLIDO_CONSULAR", nullable = true, length = 50)
    private String segundoApellidoConsular;

    @Basic(optional = true)
    @Column(name = "TX_NOTA_MARGINAL", nullable = true, length = 500)
    private String notaMarginal;

    @Basic(optional = true)
    @Column(name = "NU_DOCUMENTO_CONSULAR", nullable = true, length = 50)
    private String numeroDocumentoConsular;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_TIPO_DOC_IDENTIDAD", name = "CO_TIPO_DOC_IDENTIDAD", nullable = true)
    private TipoDocIdentidadEntidad tipoDocumentoIdentidad;

    public DefuncionEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ParroquiaEntidad getParroquia() {
        return parroquia;
    }

    public void setParroquia(ParroquiaEntidad parroquia) {
        this.parroquia = parroquia;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public Date getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(Date fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public String getTextoCausa() {
        return textoCausa;
    }

    public void setTextoCausa(String textoCausa) {
        this.textoCausa = textoCausa;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEstadoDefuncion() {
        return estadoDefuncion;
    }

    public void setEstadoDefuncion(String estadoDefuncion) {
        this.estadoDefuncion = estadoDefuncion;
    }

    public String getMunicipioDefuncion() {
        return municipioDefuncion;
    }

    public void setMunicipioDefuncion(String municipioDefuncion) {
        this.municipioDefuncion = municipioDefuncion;
    }

    public String getParroquiaDefuncion() {
        return parroquiaDefuncion;
    }

    public void setParroquiaDefuncion(String parroquiaDefuncion) {
        this.parroquiaDefuncion = parroquiaDefuncion;
    }

    public long getNumeroCertificado() {
        return numeroCertificado;
    }

    public void setNumeroCertificado(long numeroCertificado) {
        this.numeroCertificado = numeroCertificado;
    }

    public Date getFechaCertificado() {
        return fechaCertificado;
    }

    public void setFechaCertificado(Date fechaCertificado) {
        this.fechaCertificado = fechaCertificado;
    }

    public String getPrimerNombreMedico() {
        return primerNombreMedico;
    }

    public void setPrimerNombreMedico(String primerNombreMedico) {
        this.primerNombreMedico = primerNombreMedico;
    }

    public String getSegundoNombreMedico() {
        return segundoNombreMedico;
    }

    public void setSegundoNombreMedico(String segundoNombreMedico) {
        this.segundoNombreMedico = segundoNombreMedico;
    }

    public String getPrimerApellidoMedico() {
        return primerApellidoMedico;
    }

    public void setPrimerApellidoMedico(String primerApellidoMedico) {
        this.primerApellidoMedico = primerApellidoMedico;
    }

    public String getSegundoApellidoMedico() {
        return segundoApellidoMedico;
    }

    public void setSegundoApellidoMedico(String segundoApellidoMedico) {
        this.segundoApellidoMedico = segundoApellidoMedico;
    }

    public String getDocumentoIdentidadMedico() {
        return documentoIdentidadMedico;
    }

    public void setDocumentoIdentidadMedico(String documentoIdentidadMedico) {
        this.documentoIdentidadMedico = documentoIdentidadMedico;
    }

    public long getNuMPPS() {
        return nuMPPS;
    }

    public void setNuMPPS(long nuMPPS) {
        this.nuMPPS = nuMPPS;
    }

    public String getCentroSalud() {
        return centroSalud;
    }

    public void setCentroSalud(String centroSalud) {
        this.centroSalud = centroSalud;
    }

    public long getNumeroExtractoConsular() {
        return numeroExtractoConsular;
    }

    public void setNumeroExtractoConsular(long numeroExtractoConsular) {
        this.numeroExtractoConsular = numeroExtractoConsular;
    }

    public Date getFechaExtractoConsular() {
        return fechaExtractoConsular;
    }

    public void setFechaExtractoConsular(Date fechaExtractoConsular) {
        this.fechaExtractoConsular = fechaExtractoConsular;
    }

    public String getPrimerNombreConsular() {
        return primerNombreConsular;
    }

    public void setPrimerNombreConsular(String primerNombreConsular) {
        this.primerNombreConsular = primerNombreConsular;
    }

    public String getSegundoNombreConsular() {
        return segundoNombreConsular;
    }

    public void setSegundoNombreConsular(String segundoNombreConsular) {
        this.segundoNombreConsular = segundoNombreConsular;
    }

    public String getPrimerApellidoConsular() {
        return primerApellidoConsular;
    }

    public void setPrimerApellidoConsular(String primerApellidoConsular) {
        this.primerApellidoConsular = primerApellidoConsular;
    }

    public String getSegundoApellidoConsular() {
        return segundoApellidoConsular;
    }

    public void setSegundoApellidoConsular(String segundoApellidoConsular) {
        this.segundoApellidoConsular = segundoApellidoConsular;
    }

    public String getNotaMarginal() {
        return notaMarginal;
    }

    public void setNotaMarginal(String notaMarginal) {
        this.notaMarginal = notaMarginal;
    }

    public String getNumeroDocumentoConsular() {
        return numeroDocumentoConsular;
    }

    public void setNumeroDocumentoConsular(String numeroDocumentoConsular) {
        this.numeroDocumentoConsular = numeroDocumentoConsular;
    }

    public TipoDocIdentidadEntidad getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(TipoDocIdentidadEntidad tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }

}
