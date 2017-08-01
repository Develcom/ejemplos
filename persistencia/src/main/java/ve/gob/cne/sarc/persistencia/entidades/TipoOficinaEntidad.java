package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.disparadores.TipoOficinaDisparador;
import javax.persistence.*;

/**
 * TipoOficinaEntidad.java
 *
 * @descripcion Se crea la clase TipoOficinaEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C006T_TIPO_OFICINA")
@EntityListeners({TipoOficinaDisparador.class})
@NamedQueries({
    @NamedQuery(name = TipoOficinaEntidad.BUSCAR_TODOS, query = "SELECT tof "
            + "FROM   TipoOficinaEntidad tof"),
    @NamedQuery(name = TipoOficinaEntidad.BUSCAR_POR_ID, query = "SELECT tof "
            + "FROM   TipoOficinaEntidad tof " + "WHERE  tof.id = :id"),
    @NamedQuery(name = TipoOficinaEntidad.BUSCAR_POR_NOMBRE, query = "SELECT tof "
            + "FROM   TipoOficinaEntidad tof "
            + "WHERE  UPPER(tof.nombre) = :nombre"),

    @NamedQuery(name = TipoOficinaEntidad.BUSCAR_POR_NOMBRE_PATRON, query = "SELECT tof "
            + "FROM   TipoOficinaEntidad tof "
            + "WHERE  UPPER(tof.nombre) LIKE :nombre")
})
@ClassEspecification(name = "Tipo de Oficina", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class TipoOficinaEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String BUSCAR_TODOS = "TipoOficinaEntidad.buscarTodos";
    public static final String BUSCAR_POR_ID = "TipoOficinaEntidad.buscarPorId";
    public static final String BUSCAR_POR_NOMBRE = "TipoOficinaEntidad.BUSCAR_POR_NOMBRE";
    public static final String BUSCAR_POR_NOMBRE_PATRON = "TipoOficinaEntidad.BUSCAR_POR_NOMBRE_PATRON";

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_OFICINA", nullable = false, length = 22)
    @SequenceGenerator(name = "TIPO_OFICINA_SEQ", sequenceName = "C006S_CO_TIPO_OFICINA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TIPO_OFICINA_SEQ")
    @FieldEspecification(hide = true, size = 22, type = "java.lang.Long", order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_OFICINA", nullable = false, length = 255)
    @FieldEspecification(required = true, size = 50, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = false)
    @Column(name = "IDEN_TIPO_OFICINA", nullable = false, length = 50)
    @FieldEspecification(required = true, size = 5, label = "C&oacute;digo", order = 1, step = 0, placeHolder = "Ejemplo: AAA00")
    private String codigo;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
    @FieldEspecification(size = 200, input = FieldEspecification.InputType.TEXTAREAINPUT, label = "Descripci&oacute;n")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoOficina", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<OficinaEntidad> oficinas = new ArrayList<>();
    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoOficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TipoOficinaModuloEntidad> tiposOficinasModulos = new ArrayList<>();

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoOficina", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TipoLibroTipoOficinaEntidad> tiposLibrosTiposOficinas = new ArrayList<>();

    public TipoOficinaEntidad() {
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

    public List<OficinaEntidad> getOficinas() {
        return oficinas;
    }

    public void setOficinas(List<OficinaEntidad> oficinas) {
        this.oficinas = oficinas;
    }

    public List<TipoOficinaModuloEntidad> getTiposOficinasModulos() {
        return tiposOficinasModulos;
    }

    public void setTiposOficinasModulos(
            List<TipoOficinaModuloEntidad> tiposOficinasModulos) {
        this.tiposOficinasModulos = tiposOficinasModulos;
    }

    public List<TipoLibroTipoOficinaEntidad> getTiposLibrosTiposOficinas() {
        return tiposLibrosTiposOficinas;
    }

    public void setTiposLibrosTiposOficinas(
            List<TipoLibroTipoOficinaEntidad> tiposLibrosTiposOficinas) {
        this.tiposLibrosTiposOficinas = tiposLibrosTiposOficinas;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

        TipoOficinaEntidad that = (TipoOficinaEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}