package ve.gob.cne.sarc.persistencia.entidades;


import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Se crea la clase MensajeUsuarioEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C019T_MENSAJE_USUARIO")
@NamedQueries({
        @NamedQuery(name = MensajeUsuarioEntidad.BUSCAR_TODOS,
                query = "SELECT mus FROM MensajeUsuarioEntidad mus"),
        @NamedQuery(name = MensajeUsuarioEntidad.BUSCAR_POR_ID,
                query = "SELECT mus FROM MensajeUsuarioEntidad mus WHERE mus.id = :id"),
        @NamedQuery(name = MensajeUsuarioEntidad.BUSCAR_POR_NOMBRE,
                query = "SELECT mus FROM MensajeUsuarioEntidad mus WHERE mus.nombre = :nombre"),
        @NamedQuery(name = MensajeUsuarioEntidad.BUSCAR_POR_NOMBRE_PATRON,
                query = "SELECT mus FROM MensajeUsuarioEntidad mus WHERE mus.nombre LIKE '%:nombrePatron%'")})
@ClassEspecification(name = "Mensaje de Usuario", identifier = "Nombre", canBeListed = true,
        generatesTask = SincronizationPolicy.BROADCAST)
public class MensajeUsuarioEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "MensajeUsuario.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "MensajeUsuario.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_NOMBRE = "MensajeUsuario.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "MensajeUsuario.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_MENSAJE_USUARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "MENSAJE_USUARIO_SEQ", sequenceName = "C019S_CO_MENSAJE_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MENSAJE_USUARIO_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "IN_RESPUESTA", nullable = false, length = 5)
    @FieldEspecification(size = 5, required = true, label = "Respuesta", order = 3, step = 0)
    private String indicadorRespuesta;

    @Basic(optional = false)
    @Column(name = "NB_MENSAJE_USUARIO", nullable = false, length = 50)
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
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public MensajeUsuarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIndicadorRespuesta() {
        return indicadorRespuesta;
    }

    public void setIndicadorRespuesta(String indicadorRespuesta) {
        this.indicadorRespuesta = indicadorRespuesta;
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
        MensajeUsuarioEntidad that = (MensajeUsuarioEntidad) o;
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