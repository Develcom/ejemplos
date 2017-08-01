package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * EnteEstatusEntidad.java
 *
 * @descripcion Se crea la clase EnteEstatusEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 19/12/2016
 */
@Entity
@Table(name = "C025T_ENTE_ESTATUS")

public class EnteEstatusEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ENTE_ESTATUS", nullable = false, length = 22)
    private long id;

    @Basic(optional = false)
    @Column(name = "NB_ENTE_ESTATUS", length = 50, nullable = false)
    private String nombre;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", length = 200, nullable = true)
    private String descripcion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    @Basic(optional = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enteEstatus", cascade = {
        CascadeType.PERSIST, CascadeType.MERGE})
    private List<EnteEntidad> ente = new ArrayList<>();

    public EnteEstatusEntidad() {
        //Metodo Constructor
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

    public List<EnteEntidad> getEnte() {
        return ente;
    }

    public void setEnte(List<EnteEntidad> ente) {
        this.ente = ente;
    }

}
