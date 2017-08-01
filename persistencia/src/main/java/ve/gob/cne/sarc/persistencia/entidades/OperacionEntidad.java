package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Se crea la clase OperacionEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C035T_OPERACION")
@NamedQueries({
        @NamedQuery(name = OperacionEntidad.BUSCAR_TODOS,
                query = "SELECT ope FROM OperacionEntidad ope"),
        @NamedQuery(name = OperacionEntidad.BUSCAR_POR_ID,
                query = "SELECT ope FROM OperacionEntidad ope WHERE ope.id = :id"),
        @NamedQuery(name = OperacionEntidad.BUSCAR_POR_NOMBRE,
                query = "SELECT ope FROM OperacionEntidad ope WHERE ope.nombre = :nombre"),
        @NamedQuery(name = OperacionEntidad.BUSCAR_POR_NOMBRE_PATRON,
                query = "SELECT ope FROM OperacionEntidad ope WHERE ope.nombre LIKE '%:nombre%'")})
@ClassEspecification(name = "Operación", identifier = "Nombre", canBeListed = true,
        generatesTask = SincronizationPolicy.BROADCAST)
public class OperacionEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "OperacionEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "OperacionEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_NOMBRE = "OperacionEntidad.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "OperacionEntidad.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OPERACION", nullable = false, length = 22)
    @SequenceGenerator(name = "OPERACION_SEQ", sequenceName = "C035S_CO_OPERACION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OPERACION_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_Operacion", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n", size = 200)
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public OperacionEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OperacionEntidad that = (OperacionEntidad) o;
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