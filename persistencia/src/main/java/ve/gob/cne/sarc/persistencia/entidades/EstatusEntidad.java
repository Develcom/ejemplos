package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.EstatusDisparador;
/**
 * EstatusEntidad.java
 *
 * @descripcion Se crea la clase ActaEntidad donde se Realizan los Query de consulta de cada metodo
 * @version 1.0 06/09/2016
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C045T_ESTATUS")
@EntityListeners({EstatusDisparador.class})
@ClassEspecification(name = "Estatus", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class EstatusEntidad implements Serializable {

    private static final long serialVersionUID = -2333049591247399654L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ESTATUS", nullable = false, length = 22)
    @SequenceGenerator(name = "ESTATUS_SEQ", sequenceName = "C045S_CO_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ESTATUS_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_TIPO_ESTATUS", name = "CO_TIPO_ESTATUS", nullable = false)
    @FieldEspecification(required = true, input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Tipo de Estatus", order = 3, step = 0)
    private TipoEstatusEntidad tipoEstatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estatus", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
    private List<EstatusTramiteEntidad> listaEstatusTramite;

    @Basic(optional = false)
    @Column(name = "NB_ESTATUS", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public EstatusEntidad() {
        // Metodo Constructor  
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TipoEstatusEntidad getTipoEstatus() {
        return tipoEstatus;
    }

    public void setTipoEstatus(TipoEstatusEntidad tipoEstatus) {
        this.tipoEstatus = tipoEstatus;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<EstatusTramiteEntidad> getListaEstatusTramite() {
        return listaEstatusTramite;
    }

    public void setListaEstatusTramite(List<EstatusTramiteEntidad> listaEstatusTramite) {
        this.listaEstatusTramite = listaEstatusTramite;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EstatusEntidad that = (EstatusEntidad) o;

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
