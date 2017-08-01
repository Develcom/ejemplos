package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.disparadores.TipoLibroDisparador;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TipoLibroEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 11/08/2016
 * @descripcion Se crea la clase TipoLibroEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C012T_TIPO_LIBRO")
@EntityListeners({TipoLibroDisparador.class})
@ClassEspecification(name = "Tipo de Libro", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class TipoLibroEntidad implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_LIBRO", nullable = false, length = 22)
    @SequenceGenerator(name = "TIPO_LIBRO_SEQ", sequenceName = "C012S_CO_TIPO_LIBRO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_LIBRO_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_TIPO_LIBRO", nullable = false, length = 50)
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
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
    @FieldEspecification(size = 200, input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
    private String descripcion;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoLibro", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TipoLibroTipoOficinaEntidad> tiposLibrosTiposOficinas = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoLibro", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<LibroEntidad> libros = new ArrayList<>();
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public TipoLibroEntidad() {
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

    public List<TipoLibroTipoOficinaEntidad> getTiposLibrosTiposOficinas() {
        return tiposLibrosTiposOficinas;
    }

    public void setTiposLibrosTiposOficinas(
            List<TipoLibroTipoOficinaEntidad> tiposLibrosTiposOficinas) {
        this.tiposLibrosTiposOficinas = tiposLibrosTiposOficinas;
    }

    public List<LibroEntidad> getLibros() {
        return libros;
    }

    public void setLibros(List<LibroEntidad> libros) {
        this.libros = libros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoLibroEntidad that = (TipoLibroEntidad) o;

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
