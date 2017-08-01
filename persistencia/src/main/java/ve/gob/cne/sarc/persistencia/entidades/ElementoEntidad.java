package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Se crea la clase ElementoEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C043T_ELEMENTO")
@NamedQueries({@NamedQuery(name = "ElementoEntidad.findAll", query = "SELECT e FROM ElementoEntidad e")})
@ClassEspecification(name = "Elemento", identifier = "Nombre")
public class ElementoEntidad implements Serializable {

    private static final long serialVersionUID = -979778749841446610L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ELEMENTO", nullable = false, length = 22)
    @SequenceGenerator(name = "ELEMENTO_SEQ", sequenceName = "C043S_CO_ELEMENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ELEMENTO_SEQ")
    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_ELEMENTO", nullable = false, length = 50)
    @FieldEspecification(size = 50, required = true, label = "Nombre", order = 2, step = 0)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
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

    @FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "elemento", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TaxonomiaEntidad> taxonomia = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_CATEGORIA", referencedColumnName = "CO_CATEGORIA", nullable = false)
    @FieldEspecification(required = true, input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Categor&iacute;a", order = 3, step = 0)
    private CategoriaEntidad categoria;
    
    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    private String codigoSincronizacion;

    public ElementoEntidad() {
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

    public List<TaxonomiaEntidad> getTaxonomia() {
        return taxonomia;
    }

    public void setTaxonomia(List<TaxonomiaEntidad> taxonomia) {
        this.taxonomia = taxonomia;
    }

    public CategoriaEntidad getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntidad categoria) {
        this.categoria = categoria;
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
        ElementoEntidad that = (ElementoEntidad) o;
        return id == that.id;
    }


    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}