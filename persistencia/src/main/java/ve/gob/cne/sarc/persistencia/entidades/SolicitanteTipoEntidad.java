package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.SolicitanteTipoDisparador;

/**
 * SolicitanteTipoEntidad.java
 *
 * @descripcion Se crea la clase SolicitanteTipoEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C046T_SOLICITANTE_TIPO")
@EntityListeners({SolicitanteTipoDisparador.class})
public class SolicitanteTipoEntidad implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "CO_SOLICITANTE_TIPO", nullable = false, length = 22)
    private long id;

    @Basic(optional = true)
    @Column(name = "TX_DESCRIPCION", nullable = true, length = 200)
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
    @Column(name = "NB_SOLICITANTE_TIPO", nullable = true, length = 50)
    private String nombreSolicitanteTipo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitanteTipo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<SolicitanteEntidad> solicitantes;

    public SolicitanteTipoEntidad() {
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

    public String getNombreSolicitanteTipo() {
        return nombreSolicitanteTipo;
    }

    public void setNombreSolicitanteTipo(String nombreSolicitanteTipo) {
        this.nombreSolicitanteTipo = nombreSolicitanteTipo;
    }

    public List<SolicitanteEntidad> getSolicitantes() {
        return solicitantes;
    }

    public void setSolicitantes(List<SolicitanteEntidad> solicitantes) {
        this.solicitantes = solicitantes;
    }

}
