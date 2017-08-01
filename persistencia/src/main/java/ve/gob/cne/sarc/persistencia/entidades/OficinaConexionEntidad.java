package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;
import ve.gob.cne.sarc.persistencia.disparadores.OficinaConexionDisparador;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * OficinaConexionEntidad.java
 *
 * @descripcion Se crea la clase OficinaConexionEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C048T_OFICINA_CONEXION")
@EntityListeners({OficinaConexionDisparador.class})
@ClassEspecification(name = "Conexión de Oficina", identifier = "NombreOficConexion",
        canBeListed = true, generatesTask = SincronizationPolicy.BROADCAST)
public class OficinaConexionEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_OFICINA_CONEXION", nullable = false, length = 22)
    @SequenceGenerator(name = "OFICINA_CONEXION_SEQ", sequenceName = "C048S_CO_OFICINA_CONEXION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OFICINA_CONEXION_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

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

    @Basic(optional = true)
    @Column(name = "NB_OFICINA_CONEXION", nullable = true, length = 50)
    @FieldEspecification(size = 50, label = "Nombre", order = 2, step = 0)
    private String nombreOficConexion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "oficinaConexion",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaEntidad> oficinas;

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public OficinaConexionEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getNombreOficConexion() {
        return nombreOficConexion;
    }

    public void setNombreOficConexion(String nombreOficConexion) {
        this.nombreOficConexion = nombreOficConexion;
    }

    public List<OficinaEntidad> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<OficinaEntidad> oficinas) {
        this.oficinas = oficinas;
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

        OficinaConexionEntidad that = (OficinaConexionEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
