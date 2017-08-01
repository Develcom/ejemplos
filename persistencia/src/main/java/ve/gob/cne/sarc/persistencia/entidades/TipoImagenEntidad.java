package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.util.Date;

/**
 * Se crea la clase TipoImagenEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C022T_TIPO_IMAGEN")
@NamedQueries({
        @NamedQuery(name = TipoImagenEntidad.BUSCAR_TODOS,
                query = "SELECT tim FROM TipoImagenEntidad tim"),
        @NamedQuery(name = TipoImagenEntidad.BUSCAR_POR_ID,
                query = "SELECT tim FROM TipoImagenEntidad tim WHERE tim.id = :id"),
        @NamedQuery(name = TipoImagenEntidad.BUSCAR_POR_NOMBRE,
                query = "SELECT tim FROM TipoImagenEntidad tim WHERE UPPER(tim.nombre) = :nombre"),
        @NamedQuery(name = TipoImagenEntidad.BUSCAR_POR_NOMBRE_PATRON,
                query = "SELECT tim FROM TipoImagenEntidad tim WHERE UPPER(tim.nombre) LIKE :nombre")
})
@ClassEspecification(name = "Tipo de Imagen", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class TipoImagenEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "TipoImagenEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "TipoImagenEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_NOMBRE = "TipoImagenEntidad.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "TipoImagenEntidad.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_IMAGEN", nullable = false, length = 22)
    @SequenceGenerator(name = "TIPO_IMAGEN_SEQ", sequenceName = "C022S_CO_TIPO_IMAGEN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_IMAGEN_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_TIPO_IMAGEN", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public TipoImagenEntidad() {
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
        return this.descripcion;
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
        TipoImagenEntidad that = (TipoImagenEntidad) o;
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