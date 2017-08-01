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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.anotaciones.MethodEspecification;
import ve.gob.cne.sarc.persistencia.disparadores.ModuloDisparador;

/**
 * ModuloEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase ModuloEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 06/09/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C013T_MODULO")
@EntityListeners({ModuloDisparador.class})
@ClassEspecification(name = "Módulo", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class ModuloEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @FieldEspecification(required = true, size = 5, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "Ejemplo: AAA00")
    private String codigo;

    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    private Date fechaInicio;

    private Date fechaFin;

    @FieldDeletionStrategy(options = FieldDeletionStrategy.DeletionStrategy.CASCADE_OPTIONS)
    private List<TramiteEntidad> tramites = new ArrayList<>();

    @FieldDeletionStrategy(options = FieldDeletionStrategy.DeletionStrategy.ALONE_OPTIONS)
    private List<TipoOficinaModuloEntidad> tiposOficinasModulos = new ArrayList<>();

    @FieldDeletionStrategy(options = FieldDeletionStrategy.DeletionStrategy.ALONE_OPTIONS)
    private List<PermisologiaEntidad> coPermisologias;

    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public ModuloEntidad() {
        // Metodo Constructor
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CO_MODULO", nullable = false, unique = true, length = 22)
    @SequenceGenerator(name = "MODULO_SEQ", sequenceName = "C013S_CO_MODULO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MODULO_SEQ")
    @MethodEspecification(targetedAttribute="id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "IDEN_MODULO", nullable = false, unique = true, length = 5)
    @MethodEspecification(targetedAttribute="codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Basic(optional = false)
    @Column(name = "NB_MODULO", nullable = false, length = 50)
    @MethodEspecification(targetedAttribute="nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @MethodEspecification(targetedAttribute="descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    @MethodEspecification(targetedAttribute="fechaInicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    @MethodEspecification(targetedAttribute="fechaFin")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "modulo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MethodEspecification(targetedAttribute="tramites")
    public List<TramiteEntidad> getTramites() {
        return tramites;
    }

    public void setTramites(List<TramiteEntidad> tramites) {
        this.tramites = tramites;
    }

    @Basic(optional = true)
    @OneToMany(mappedBy = "modulo", cascade = {CascadeType.PERSIST})
    @MethodEspecification(targetedAttribute="tiposOficinasModulos")
    public List<TipoOficinaModuloEntidad> getTiposOficinasModulos() {
        return tiposOficinasModulos;
    }

    public void setTiposOficinasModulos(List<TipoOficinaModuloEntidad> tiposOficinasModulos) {
        this.tiposOficinasModulos = tiposOficinasModulos;
    }

    @Basic(optional = true)
    @OneToMany(mappedBy = "coModulo")
    @MethodEspecification(targetedAttribute="coPermisologias")
    public List<PermisologiaEntidad> getCoPermisologias() {
        return this.coPermisologias;
    }

    public void setCoPermisologias(List<PermisologiaEntidad> coPermisologias) {
        this.coPermisologias = coPermisologias;
    }
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @MethodEspecification(targetedAttribute="codigoSincronizacion")
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

        ModuloEntidad that = (ModuloEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
