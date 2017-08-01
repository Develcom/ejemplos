package ve.gob.cne.sarc.persistencia.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import ve.gob.cne.sarc.persistencia.disparadores.TipoParticipanteDisparador;

/**
 * TipoParticipanteEntidad.java
 *
 * @descripcion Se crea la clase TipoParticipanteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C005T_TIPO_PARTICIPANTE")
@EntityListeners({TipoParticipanteDisparador.class})
@ClassEspecification(name = "Tipo de Participante", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class TipoParticipanteEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_PARTICIPANTE", nullable = false, length = 22)
    @SequenceGenerator(name = "TIPO_PARTICIPANTE_SEQ", sequenceName = "C005S_CO_TIPO_PARTICIPANTE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_PARTICIPANTE_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "IDEN_TIPO_PARTICIPANTE", unique = true, nullable = false, length = 5)
    @FieldEspecification(size = 5, required = true, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "Ejemplo: AAA00")
    private String codigo;

    @Basic(optional = false)
    @Column(name = "NB_TIPO_PARTICIPANTE", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "IN_IDENTIFICACION", nullable = true, length = 2)
    @FieldEspecification(size = 1, required = false, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Identificaci&oacute;n", order = 3, step = 0)
    private int indicadorIdentificacion;

    @Basic(optional = true)
    @Column(name = "IN_DOCUMENTO_PUBLICO", nullable = true, length = 2)
    @FieldEspecification(size = 1, required = false, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Documento P&uacute;blico", order = 4, step = 0)
    private int indicadorDocumentoPublico;

    @Basic(optional = true)
    @Column(name = "IN_CONSEJO_COMUNAL", nullable = true, length = 2)
    @FieldEspecification(size = 1, required = false, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Consejo Comunal", order = 5, step = 0)
    private int indicadorConsejoComunal;

    @Basic(optional = true)
    @Column(name = "IN_DECLARACION_JURADA", nullable = true, length = 2)
    @FieldEspecification(size = 1, required = false, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Declaraci&oacute;n Jurada", order = 6, step = 0)
    private int indicadorDeclaracionJurada;

    @Basic(optional = true)
    @Column(name = "NU_EDAD_MINIMA", nullable = false, length = 2)
    @FieldEspecification(size = 2, required = false, type = "java.lang.Integer", constraints = ">=0", label = "Edad M&iacute;nima", order = 7, step = 0)
    private int edadMinima;

    @Basic(optional = true)
    @Column(name = "IN_FIRMA", nullable = true, length = 1)
    @FieldEspecification(size = 1, required = false, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Firma", order = 8, step = 0)
    private int indicadorFirma;

    @Basic(optional = true)
    @Column(name = "IN_HUELLA", nullable = true, length = 1)
    @FieldEspecification(size = 1, required = false, type = "java.lang.Integer", constraints = "/[0-1]/", label = "Huella", order = 9, step = 0)
    private int indicadorHuella;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoParticipante",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParticipanteEntidad> participantes = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoParticipante",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TramiteTipoParticipanteEntidad> tramitesTiposParticipantes = new ArrayList<>();

    public TipoParticipanteEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getIndicadorIdentificacion() {
        return indicadorIdentificacion;
    }

    public void setIndicadorIdentificacion(int indicadorIdentificacion) {
        this.indicadorIdentificacion = indicadorIdentificacion;
    }

    public int getIndicadorDocumentoPublico() {
        return indicadorDocumentoPublico;
    }

    public void setIndicadorDocumentoPublico(int indicadorDocumentoPublico) {
        this.indicadorDocumentoPublico = indicadorDocumentoPublico;
    }

    public int getIndicadorConsejoComunal() {
        return indicadorConsejoComunal;
    }

    public void setIndicadorConsejoComunal(int indicadorConsejoComunal) {
        this.indicadorConsejoComunal = indicadorConsejoComunal;
    }

    public int getIndicadorDeclaracionJurada() {
        return indicadorDeclaracionJurada;
    }

    public void setIndicadorDeclaracionJurada(int indicadorDeclaracionJurada) {
        this.indicadorDeclaracionJurada = indicadorDeclaracionJurada;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public int getIndicadorFirma() {
        return indicadorFirma;
    }

    public void setIndicadorFirma(int indicadorFirma) {
        this.indicadorFirma = indicadorFirma;
    }

    public int getIndicadorHuella() {
        return indicadorHuella;
    }

    public void setIndicadorHuella(int indicadorHuella) {
        this.indicadorHuella = indicadorHuella;
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
    
    public String getCodigoSincronizacion() {
        return codigoSincronizacion;
    }

    public void setCodigoSincronizacion(String codigoSincronizacion) {
        this.codigoSincronizacion = codigoSincronizacion;
    }

    public List<ParticipanteEntidad> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteEntidad> participantes) {
        this.participantes = participantes;
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
        TipoParticipanteEntidad that = (TipoParticipanteEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}
