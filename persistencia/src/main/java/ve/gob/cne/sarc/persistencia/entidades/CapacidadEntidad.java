package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.util.Date;

/**
 * Se crea la clase CapacidadEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C036T_CAPACIDAD")
@NamedQueries({
        @NamedQuery(name = CapacidadEntidad.BUSCAR_TODOS,
                query = "SELECT cap FROM CapacidadEntidad cap"),
        @NamedQuery(name = CapacidadEntidad.BUSCAR_POR_ID,
                query = "SELECT cap FROM CapacidadEntidad cap WHERE cap.id = :id"),
        @NamedQuery(name = CapacidadEntidad.BUSCAR_POR_NOMBRE_PATRON,
                query = "SELECT cap FROM CapacidadEntidad cap WHERE UPPER(cap.nombre) LIKE :nombre")})
@ClassEspecification(name = "Capacidad", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class CapacidadEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "CapacidadEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "CapacidadEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "CapacidadEntidad.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_CAPACIDAD", nullable = false, length = 22)
    @SequenceGenerator(name = "CAPACIDAD_SEQ", sequenceName = "C036S_CO_CAPACIDAD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAPACIDAD_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_CAPACIDAD", nullable = false, length = 50)
    @FieldEspecification(required = true, size = 50, label = "Nombre")
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
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

    public CapacidadEntidad() {
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
        CapacidadEntidad that = (CapacidadEntidad) o;
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