package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.TramiteTipoParticipanteDisparador;

/**
 *
 * @descripcion Se crea la clase TramiteTipoParticipanteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "C038T_TRAMITE_T_PARTICIPANTE")
@EntityListeners({TramiteTipoParticipanteDisparador.class})

public class TramiteTipoParticipanteEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_TRAMITE_T_PARTICIPANTE", nullable = false, length = 22)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TRAMITE", referencedColumnName = "CO_TRAMITE", nullable = false)
    private TramiteEntidad tramite;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_TIPO_PARTICIPANTE", referencedColumnName = "CO_TIPO_PARTICIPANTE", nullable = false)
    private TipoParticipanteEntidad tipoParticipante;

    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    public TramiteTipoParticipanteEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TramiteEntidad getTramite() {
        return tramite;
    }

    public void setTramite(TramiteEntidad tramite) {
        this.tramite = tramite;
    }

    public TipoParticipanteEntidad getTipoParticipante() {
        return tipoParticipante;
    }

    public void setTipoParticipante(TipoParticipanteEntidad tipoParticipante) {
        this.tipoParticipante = tipoParticipante;
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
}
