package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import ve.gob.cne.sarc.persistencia.disparadores.EstadoDisparador;

/**
 * EstadoEntidad.java
 *
 * @descripcion Se crea la clase EstadoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 06/09/2016
 */
@Entity
@Table(name = "C002T_ESTADO")
@EntityListeners({EstadoDisparador.class})

public class EstadoEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ESTADO", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_PAIS", name = "CO_PAIS", nullable = false)
    private PaisEntidad pais;

    @Basic(optional = false)
    @Column(name = "NB_ESTADO", nullable = false, length = 50)
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
    private String descripcion;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estado", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MunicipioEntidad> municipios = new ArrayList<>();

    public EstadoEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PaisEntidad getPais() {
        return pais;
    }

    public void setPais(PaisEntidad pais) {
        this.pais = pais;
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

    public List<MunicipioEntidad> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<MunicipioEntidad> municipios) {
        this.municipios = municipios;
    }

}
