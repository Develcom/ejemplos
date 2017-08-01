package ve.gob.cne.sarc.persistencia.entidades;

import java.util.Date;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import ve.gob.cne.sarc.persistencia.disparadores.RecaudoDisparador;

/**
 * RecaudoEntidad.java
 *
 * @descripcion Se crea la clase RecaudoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C017T_RECAUDO")
@EntityListeners({RecaudoDisparador.class})
@ClassEspecification(name = "Recaudo", identifier = "Nombre", canBeListed = true,
        generatesTask = SincronizationPolicy.BROADCAST)
public class RecaudoEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(
            name = "CO_RECAUDO",
            nullable = false,
            length = 22
    )
    @SequenceGenerator(name = "RECAUDO_SEQ", sequenceName = "C017S_CO_RECAUDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECAUDO_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(
            name = "IDEN_RECAUDO",
            unique = true,
            nullable = false,
            length = 5
    )
    @FieldEspecification(size = 5, required = true, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "Ejemplo: AAA00")
    private String codigo;

    @Basic(optional = false)
    @Column(
            name = "NB_NOMBRE",
            nullable = false,
            length = 50
    )
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = false)
    @Column(
            name = "IN_TIPO",
            nullable = false,
            length = 1
    )
    @FieldEspecification(size = 1, required = true, label = "Tipo", order = 3, step = 0, placeHolder = "Ejemplo: A")
    private String tipo;

    @Basic(optional = false)
    @Column(
            name = "IN_ESTATUS",
            nullable = false,
            length = 5
    )
    @FieldEspecification(size = 5, required = true, label = "Estatus", order = 4, step = 0)
    private String estatus;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(
            name = "FE_INICIO",
            nullable = false,
            length = 10
    )
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(
            name = "FE_FIN",
            length = 10
    )
    private Date fechaFin;

    @Basic(optional = true)
    @Column(
            name = "TX_DESCRIPCION",
            length = 500
    )
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Observaci&oacute;n")
    private String observacion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public RecaudoEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecaudoEntidad that = (RecaudoEntidad) o;
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