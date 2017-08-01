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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.MunicipioDisparador;

/**
 * MunicipioEntidad.java
 *
 * @descripcion Se crea la clase MunicipioEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 06/09/2016
 */
@Entity
@Table(name = "C003T_MUNICIPIO")
@EntityListeners({MunicipioDisparador.class})
@NamedQueries({
        @NamedQuery(name = MunicipioEntidad.BUSCAR_MUNICIPIO_Y_ESTADO_POR_ID,
                query = "SELECT mun.id,mun.nombre,mun.estado.id,mun.estado.nombre "
                + "FROM  MunicipioEntidad mun "
                + "WHERE  mun.id=:id")
})

public class MunicipioEntidad implements Serializable {

    public static final String BUSCAR_MUNICIPIO_Y_ESTADO_POR_ID = "MunicipioEntidad.buscarMunicipioEstadoPorId";

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_MUNICIPIO", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ESTADO", referencedColumnName = "CO_ESTADO", nullable = false)
    private EstadoEntidad estado;

    @Basic(optional = false)
    @Column(name = "NB_MUNICIPIO", nullable = false, length = 100)
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "municipio", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParroquiaEntidad> parroquias = new ArrayList<>();

    public MunicipioEntidad() {
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

    public List<ParroquiaEntidad> getParroquias() {
        return parroquias;
    }

    public void setParroquias(List<ParroquiaEntidad> parroquias) {
        this.parroquias = parroquias;
    }

    public EstadoEntidad getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntidad estado) {
        this.estado = estado;
    }
}
