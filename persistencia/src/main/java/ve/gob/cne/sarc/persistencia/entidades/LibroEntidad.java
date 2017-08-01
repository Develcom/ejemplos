package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.disparadores.LibroDisparador;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * LibroEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.1 11/08/2016
 * @descripcion Se crea la clase LibroEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.1 06/09/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "X006T_LIBRO")
@EntityListeners({LibroDisparador.class})
@NamedQueries({
    @NamedQuery(name = LibroEntidad.BUSCAR_POR_CODIGO_OFICINA_ESTATUS_ANIO, query = "SELECT lib FROM LibroEntidad lib  "
            + "WHERE  lib.oficina.nombre = :nombreOficina "
            + "AND    lib.estatus.nombre = :nombreEstatus "
            + "AND    lib.numeroAnio     = :anio")
})

@ClassEspecification(name = "Libro", identifier = "Id",
generatesTask = ClassEspecification.SincronizationPolicy.NONE, canBeListed = false)
public class LibroEntidad implements java.io.Serializable {

    public static final String BUSCAR_POR_CODIGO_OFICINA_ESTATUS_ANIO
            = "LibroEntidad.buscarPorCodigoOficinaEstatusAnio";

    private static final long serialVersionUID = 1L;
    

    @Id
    @Basic(optional = false)
    @Column(name = "CO_LIBRO", nullable = false, length = 22)
    @SequenceGenerator(name = "LIBRO_SEQ", sequenceName = "X006S_CO_LIBRO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIBRO_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_OFICINA", name = "CO_OFICINA")
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Oficina", order = 2, step = 0)
    private OficinaEntidad oficina;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_FUNCIONARIO", name = "CO_FUNCIONARIO_APERTURA", nullable = false)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Funcionario Apertura", order = 3, step = 0)
    private FuncionarioEntidad funcionarioApertura;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_FUNCIONARIO", name = "CO_FUNCIONARIO_CIERRE", nullable = true)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Funcionario Cierre", order = 4, step = 0)
    private FuncionarioEntidad funcionarioCierre;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_TIPO_LIBRO", name = "CO_TIPO_LIBRO", nullable = false)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Tipo de Libro", order = 1, step = 0)
    private TipoLibroEntidad tipoLibro;

    @ManyToOne(optional = true)
    @JoinColumn(referencedColumnName = "CO_LIBRO_ESTATUS", name = "CO_LIBRO_ESTATUS", nullable = true)
    @FieldEspecification(required = true, input = FieldEspecification.InputType.SELECT,
            options = FieldEspecification.CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Estatus del Libro", order = 5, step = 0)
    private LibroEstatusEntidad estatus;

    @Basic(optional = false)
    @Column(name = "NU_ANIO", nullable = false, length = 4)
    @FieldEspecification(required = true, size = 8, type = "java.lang.Integer",
            label = "N&uacute;mero de A&ntilde;o", order = 6, step = 0, constraints = ">=0")
    private int numeroAnio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_CREACION", nullable = false)
    @FieldEspecification(hide = true, input = FieldEspecification.InputType.DATEINPUT, type = "java.util.Date",
            label = "Fecha de Creaci&oacute;n")
    private Date fechaCreacion;

    @Basic(optional = false)
    @Column(name = "NU_ACTA_ACTUAL", nullable = false, length = 23)
    @FieldEspecification(required = true, size = 23, label = "N&uacute;mero de Acta", order = 8, step = 0)
    private String numeroActa;

    @Basic(optional = true)
    @Column(name = "NU_FOLIO_ACTUAL", nullable = true, length = 6)
    @FieldEspecification(required = true, size = 8, type = "java.lang.Integer",
            label = "N&uacute;mero de Folio", order = 9, step = 0, constraints = ">=0")
    private int numeroFolio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_ACTUALIZACION", nullable = false)
    @FieldEspecification(hide = true, input = FieldEspecification.InputType.DATEINPUT, type = "java.util.Date",
            label = "Fecha de Actualizaci&oacute;n")
    private Date fechaActualizacion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_IMPRESION", nullable = true)
    @FieldEspecification(required = false, input = FieldEspecification.InputType.DATEINPUT, type = "java.util.Date",
            label = "Fecha de Impresi&oacute;n")
    private Date fechaImpresion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_CIERRE", nullable = true)
    @FieldEspecification(required = false, input = FieldEspecification.InputType.DATEINPUT, type = "java.util.Date",
            label = "Fecha de Cierre")
    private Date fechaCierre;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "libro", cascade = CascadeType.PERSIST)
    private List<SolicitudEntidad> solicitud = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "libro", cascade = CascadeType.PERSIST)
    private List<ActaEntidad> acta = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "libro", cascade = CascadeType.PERSIST)
    private List<TomoEntidad> tomo = new ArrayList<>();

    public LibroEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OficinaEntidad getOficina() {
        return oficina;
    }

    public void setOficina(OficinaEntidad oficina) {
        this.oficina = oficina;
    }

    public FuncionarioEntidad getFuncionarioApertura() {
        return funcionarioApertura;
    }

    public void setFuncionarioApertura(FuncionarioEntidad funcionarioApertura) {
        this.funcionarioApertura = funcionarioApertura;
    }

    public FuncionarioEntidad getFuncionarioCierre() {
        return funcionarioCierre;
    }

    public void setFuncionarioCierre(FuncionarioEntidad funcionarioCierre) {
        this.funcionarioCierre = funcionarioCierre;
    }

    public int getNumeroAnio() {
        return numeroAnio;
    }

    public void setNumeroAnio(int numeroAnio) {
        this.numeroAnio = numeroAnio;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNumeroActa() {
        return numeroActa;
    }

    public void setNumeroActa(String numeroActa) {
        this.numeroActa = numeroActa;
    }

    public int getNumeroFolio() {
        return numeroFolio;
    }

    public void setNumeroFolio(int numeroFolio) {
        this.numeroFolio = numeroFolio;
    }

    public LibroEstatusEntidad getEstatus() {
        return estatus;
    }

    public void setEstatus(LibroEstatusEntidad estatus) {
        this.estatus = estatus;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaImpresion() {
        return fechaImpresion;
    }

    public void setFechaImpresion(Date fechaImpresion) {
        this.fechaImpresion = fechaImpresion;
    }

    public List<SolicitudEntidad> getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(List<SolicitudEntidad> solicitud) {
        this.solicitud = solicitud;
    }

    public List<ActaEntidad> getActa() {
        return acta;
    }

    public void setActa(List<ActaEntidad> acta) {
        this.acta = acta;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public TipoLibroEntidad getTipoLibro() {
        return tipoLibro;
    }

    public void setTipoLibro(TipoLibroEntidad tipoLibro) {
        this.tipoLibro = tipoLibro;
    }

    public List<TomoEntidad> getTomo() {
        return tomo;
    }

    public void setTomo(List<TomoEntidad> tomo) {
        this.tomo = tomo;
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

        LibroEntidad that = (LibroEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
