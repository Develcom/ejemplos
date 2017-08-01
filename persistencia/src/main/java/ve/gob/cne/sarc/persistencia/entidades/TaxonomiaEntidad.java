package ve.gob.cne.sarc.persistencia.entidades;

import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.MethodEspecification;
import ve.gob.cne.sarc.persistencia.anotaciones.ClassEspecification.SincronizationPolicy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.CompletationStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldEspecification.InputType;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Se crea la clase TaxonomiaEntidad donde se Realizan los Query de consulta de cada metodo
 *
 * @author Oscar Montilla
 */
@Entity
@Table(name = "C015T_TAXONOMIA")
@ClassEspecification(name = "Taxonomía", identifier = "Nombre", canBeListed = true,
        generatesTask=SincronizationPolicy.BROADCAST)
public class TaxonomiaEntidad implements Serializable {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @FieldEspecification(type = "java.lang.Long", hide = true, order = 0, step = 0)
    private long id;
    
    @FieldEspecification(required = true, size = 255, label = "Nombre", order = 1, step = 0)
    private String nombre;
    
    @FieldEspecification(input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Taxonomia Padre", order = 2, step = 0)
    private TaxonomiaEntidad padre;
    
    @FieldDeletionStrategy(options = DeletionStrategy.ALONE_OPTIONS)
    private List<TaxonomiaEntidad> hijos = new ArrayList<>();
    
    @FieldEspecification(required = true, input = InputType.SELECT, options = CompletationStrategy.DB_OPTIONS,
            type = "java.lang.Long", label = "Elemento", order = 3, step = 0)
    private ElementoEntidad elemento;
    
    private Date fechaInicio;

    private Date fechaFin;
    
    @FieldEspecification(hide = true, label = "C&oacute;digo de sincronizaci&oacute;n", size = 255) 
    private String codigoSincronizacion;

    public TaxonomiaEntidad() {
        //Metodo Constructor
    }

    /**
     * @param elemento
     */
    public TaxonomiaEntidad(ElementoEntidad elemento) {
        this.setElemento(elemento);
    }

    /**
     * @param id
     * @param elemento
     * @param padre
     */
    public TaxonomiaEntidad(Long id, ElementoEntidad elemento,
                            TaxonomiaEntidad padre) {
        this.setId(id);
        this.setElemento(elemento);
        this.setPadre(padre);
    }

    /**
     * @param elemento
     * @param padre
     * @param codigo
     */
    public TaxonomiaEntidad(ElementoEntidad elemento, TaxonomiaEntidad padre) {
        this.setElemento(elemento);
        this.setPadre(padre);
    }

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TAXONOMIA", nullable = false, length = 22)
    @SequenceGenerator(name = "TAXONOMIA_SEQ", sequenceName = "C015S_CO_TAXONOMIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAXONOMIA_SEQ")
    @MethodEspecification(targetedAttribute="id")
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "IDEN_TAXONOMIA", nullable = false, length = 255)
    @MethodEspecification(targetedAttribute="nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_TAXONOMIA_PADRE", referencedColumnName = "CO_TAXONOMIA", nullable = true)
    @MethodEspecification(targetedAttribute="padre")
    public TaxonomiaEntidad getPadre() {
        return padre;
    }

    public void setPadre(TaxonomiaEntidad padre) {
        this.padre = padre;
    }

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MethodEspecification(targetedAttribute="hijos")
    public List<TaxonomiaEntidad> getHijos() {
        return hijos;
    }

    public void setHijos(List<TaxonomiaEntidad> hijos) {
        this.hijos = hijos;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ELEMENTO", referencedColumnName = "CO_ELEMENTO", nullable = false)
    @MethodEspecification(targetedAttribute="elemento")
    public ElementoEntidad getElemento() {
        return elemento;
    }

    public void setElemento(ElementoEntidad elemento) {
        this.elemento = elemento;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false, length = 10)
    @MethodEspecification(targetedAttribute="fechaInicio")
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true, length = 10)
    @MethodEspecification(targetedAttribute="fechaFin")
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Basic(optional = false)
    @Column(name = "TX_CODIGO_SINCRONIZACION", nullable = false, length = 256)
    @MethodEspecification(targetedAttribute="codigoSincronizacion")
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
        TaxonomiaEntidad that = (TaxonomiaEntidad) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

}