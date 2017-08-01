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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import ve.gob.cne.sarc.persistencia.disparadores.TipoDocIdentidaDisparador;

/**
 * TipoDocIdentidadEntidad.java
 *
 * @descripcion Se crea la clase TipoDocIdentidadEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C065T_TIPO_DOC_IDENTIDAD")
@EntityListeners({TipoDocIdentidaDisparador.class})
public class TipoDocIdentidadEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TIPO_DOC_IDENTIDAD", nullable = false, length = 22)
    private long id;

    @Column(name = "TX_DESCRIPCION")
    private String descripcion;

    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaFin;

    @Basic(optional = false)
    @Size(min = 1, max = 50)
    @Column(name = "NB_TIPO_DOC_IDENTIDAD", nullable = false)
    private String nombreTipoDocIdentidad;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocumentoIdentidad",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<ParticipanteEntidad> participantes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocumentoIdentidad",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DefuncionEntidad> defuncion = new ArrayList<>();

    public TipoDocIdentidadEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        // Metodo Constructor
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

    public String getNombreTipoDocIdentidad() {
        return nombreTipoDocIdentidad;
    }

    public void setNombreTipoDocIdentidad(String nombreTipoDocIdentidad) {
        this.nombreTipoDocIdentidad = nombreTipoDocIdentidad;
    }

    public List<ParticipanteEntidad> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<ParticipanteEntidad> participantes) {
        this.participantes = participantes;
    }

    public List<DefuncionEntidad> getDefuncion() {
        return defuncion;
    }

    public void setDefuncion(List<DefuncionEntidad> defuncion) {
        this.defuncion = defuncion;
    }

}
