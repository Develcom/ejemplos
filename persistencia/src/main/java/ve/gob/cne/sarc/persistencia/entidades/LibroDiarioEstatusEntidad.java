package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * LibroDiarioEstatusEntidad.java
 *
 * @descripcion Se crea la clase LibroDiarioEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 12/12/2016
 */
@Entity
@Table(name = "C061T_LIBRO_DIARIO_ESTATUS")
public class LibroDiarioEstatusEntidad implements Serializable {

    private static final long serialVersionUID = -6455771008790728720L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_LIBRO_DIARIO_ESTATUS", nullable = false, length = 22)
    @SequenceGenerator(name = "LIBRO_DIARIO_ESTATUS_SEQ", sequenceName = "C061S_CO_LIBRO_DIARIO_ESTATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIBRO_DIARIO_ESTATUS_SEQ")
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_LIBRO_DIARIO_ESTATUS", nullable = false, length = 50)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "libroDiarioEstatus",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<LibroDiarioEntidad> libroDiario;

    public LibroDiarioEstatusEntidad() {
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

    public List<LibroDiarioEntidad> getLibroDiario() {
        return libroDiario;
    }

    public void setLibroDiario(List<LibroDiarioEntidad> libroDiario) {
        this.libroDiario = libroDiario;
    }

}
