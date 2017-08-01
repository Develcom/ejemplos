package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.disparadores.OficinaFuncionarioDisparador;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OficinaFuncionarioEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase OficinaFuncionarioEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X009T_OFICINA_FUNCIONARIO")
@EntityListeners({OficinaFuncionarioDisparador.class})
@ClassEspecification(name = "Oficina Funcionario", identifier = "id")
public class OficinaFuncionarioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_FUNCIONARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "OFICINA_FUNCIONARIO_SEQ", sequenceName = "X009S_CO_OFICINA_FUNCIONARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFICINA_FUNCIONARIO_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_FUNCIONARIO", referencedColumnName = "CO_FUNCIONARIO", nullable = false)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS, type = "java.lang.Long",
            label = "Funcionario", order = 1, step = 0)
    private FuncionarioEntidad funcionario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_OFICINA", referencedColumnName = "CO_OFICINA", nullable = false)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS, type = "java.lang.Long",
            label = "Oficina", order = 2, step = 0)
    private OficinaEntidad oficina;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TIPO_FUNCIONARIO", referencedColumnName = "CO_TIPO_FUNCIONARIO", nullable = false)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS, type = "java.lang.Long",
            label = "Tipo Funcionario", order = 3, step = 0)
    private TipoFuncionarioEntidad tipoFuncionario;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_CORREO_ELECTRONICO", nullable = true, length = 50)
    @FieldEspecification(size = 50, label = "Correo electr&oacute;nico", order = 4, step = 0)
    private String correoElectronicoFuncionario;

    @Basic(optional = true)
    @Column(name = "NU_TELEFONO_FIJO", nullable = true, length = 15)
    @FieldEspecification(size = 15, label = "Tel&eacute;fono fijo", order = 5, step = 0)
    private String telefonoFijo;

    @Basic(optional = true)
    @Column(name = "NU_TELEFONO_MOVIL", nullable = true, length = 15)
    @FieldEspecification(size = 15, label = "Tel&eacute;fono movil", order = 6, step = 0)
    private String telefonoMovil;

    @Basic(optional = true)
    @Column(name = "NU_RESOLUCION", nullable = true, length = 50)
    @FieldEspecification(size = 50, label = "Numero resoluci&oacute;n", order = 7, step = 0)
    private String numeroResolucion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_RESOLUCION", nullable = true)
    @FieldEspecification(input = FieldEspecification.InputType.DATEINPUT,
            type = "java.util.Date", label = "Fecha resoluci&oacute;n")
    private Date fechaResolucion;

    @Basic(optional = true)
    @Column(name = "IN_TIPO_GACETA", nullable = true, length = 1)
    @FieldEspecification(size = 1, label = "Indicador tipo gaceta", order = 8, step = 0)
    private String indicadorTipoGaceta;

    @Basic(optional = true)
    @Column(name = "NU_GACETA", nullable = true, length = 22)
    @FieldEspecification(size = 22, type = "java.lang.Long", order = 9, step = 0)
    private long numeroGaceta;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_GACETA", nullable = true)
    @FieldEspecification(input = FieldEspecification.InputType.DATEINPUT,
            type = "java.util.Date", label = "Fecha gaceta")
    private Date fechaGaceta;

    @Basic(optional = true)
    @Column(name = "TX_OBSERVACION", nullable = true, length = 200)
    @FieldEspecification(size = 1, label = "Observaci&oacute;n", order = 8, step = 0)
    private String observacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaFuncionario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitudEntidad> solicitudes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaFuncionario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ActaEntidad> actas = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaFuncionario")
    private List<InscripcionMayor18Entidad> inscripcionesMayor18 = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaFuncionario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UsuarioEntidad> usuarios = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA_FUNC_ESTATUS",
            name = "CO_OFICINA_FUNC_ESTATUS", nullable = false)
    private OficinaFuncionarioEstatusEntidad oficFuncEstatus;

    public OficinaFuncionarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public FuncionarioEntidad getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioEntidad funcionario) {
        this.funcionario = funcionario;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public TipoFuncionarioEntidad getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(TipoFuncionarioEntidad tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
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

    public String getCorreoElectronicoFuncionario() {
        return correoElectronicoFuncionario;
    }

    public void setCorreoElectronicoFuncionario(String correoElectronicoFuncionario) {
        this.correoElectronicoFuncionario = correoElectronicoFuncionario;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public String getIndicadorTipoGaceta() {
        return indicadorTipoGaceta;
    }

    public void setIndicadorTipoGaceta(String indicadorTipoGaceta) {
        this.indicadorTipoGaceta = indicadorTipoGaceta;
    }

    public long getNumeroGaceta() {
        return numeroGaceta;
    }

    public void setNumeroGaceta(long numeroGaceta) {
        this.numeroGaceta = numeroGaceta;
    }

    public Date getFechaGaceta() {
        return fechaGaceta;
    }

    public void setFechaGaceta(Date fechaGaceta) {
        this.fechaGaceta = fechaGaceta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<SolicitudEntidad> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudEntidad> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<ActaEntidad> getActas() {
        return actas;
    }

    public void setActas(List<ActaEntidad> actas) {
        this.actas = actas;
    }

    public List<InscripcionMayor18Entidad> getInscripcionesMayor18() {
        return inscripcionesMayor18;
    }

    public void setInscripcionesMayor18(List<InscripcionMayor18Entidad> inscripcionesMayor18) {
        this.inscripcionesMayor18 = inscripcionesMayor18;
    }

    public List<UsuarioEntidad> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEntidad> usuarios) {
        this.usuarios = usuarios;
    }

    public OficinaFuncionarioEstatusEntidad getOficFuncEstatus() {
        return oficFuncEstatus;
    }

    public void setOficFuncEstatus(OficinaFuncionarioEstatusEntidad oficFuncEstatus) {
        this.oficFuncEstatus = oficFuncEstatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OficinaFuncionarioEntidad that = (OficinaFuncionarioEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
