package ve.gob.cne.sarc.persistencia.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import ve.gob.cne.sarc.persistencia.disparadores.ComunidadIndigenaDisparador;

/**
 * ComunidadIndigenaEntidad.java
 *
 * @descripcion Se crea la clase ComunidadIndigenaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "C009T_COMUNIDAD_INDIGENA")
@EntityListeners({ComunidadIndigenaDisparador.class})
@ClassEspecification(name = "Comunidad Indígena", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class ComunidadIndigenaEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "ComunidadIndigenaEntidad.BUSCAR_TODOS";
    public static final String BUSCAR_POR_ID = "ComunidadIndigenaEntidad.BUSCAR_POR_ID";
    public static final String BUSCAR_POR_CODIGO = "ComunidadIndigenaEntidad.BUSCAR_POR_CODIGO";
    public static final String BUSCAR_POR_NOMBRE = "ComunidadIndigenaEntidad.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "ComunidadIndigenaEntidad.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_COMUNIDAD_INDIGENA", nullable = false, length = 22)
    @SequenceGenerator(name = "COMUNIDAD_INDIGENA_SEQ", sequenceName = "C009S_CO_COMUNIDAD_INDIGENA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMUNIDAD_INDIGENA_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_COMUNIDAD_INDIGENA", nullable = false, length = 100)
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
    @Column(name = "TX_DESCRIPCION", length = 200)
    @FieldEspecification(size = 200, input = InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Basic(optional = true)
    @Column(name = "DI_UBICACION", length = 500)
    @FieldEspecification(size = 500, input = InputType.TEXTAREAINPUT, label = "Direcci&oacute;n")
    private String direccion;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "comunidadIndigena", cascade
            = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<CiudadanoEntidad> ciudadanos = new ArrayList<>();

    public ComunidadIndigenaEntidad() {
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

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<CiudadanoEntidad> getCiudadanos() {
        return this.ciudadanos;
    }

    public void setCiudadanos(List<CiudadanoEntidad> ciudadanos) {
        this.ciudadanos = ciudadanos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ComunidadIndigenaEntidad that = (ComunidadIndigenaEntidad) o;
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
