package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.ArrayList;
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

import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy;
import ve.gob.cne.sarc.persistencia.anotaciones.FieldDeletionStrategy.DeletionStrategy;
import ve.gob.cne.sarc.persistencia.disparadores.EstatusTramiteDisparador;

/**
 * EstatusTramiteEntidad.java
 *
 * @descripcion Se crea la clase EstatusTramiteEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 */
@Entity
@Table(name = "C046T_ESTATUS_TRAMITE")
@EntityListeners({EstatusTramiteDisparador.class})
public class EstatusTramiteEntidad implements Serializable {

    private static final long serialVersionUID = -7603784554229083119L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ESTATUS_TRAMITE", nullable = false, length = 22)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_ESTATUS", name = "CO_ESTATUS")
    private EstatusEntidad estatus;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "CO_TRAMITE", name = "CO_TRAMITE")
    private TramiteEntidad tramite;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estatusTramite", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @FieldDeletionStrategy(options = DeletionStrategy.CASCADE_OPTIONS)
    private List<SolicitudEntidad> solicitud;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "estatusAntecesor", cascade
            = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EstatusTramiteEntidad> estatusTramite = new ArrayList<>();

    @ManyToOne(optional = true)
    @JoinColumn(name = "CO_ESTATUS_ANTECESOR", referencedColumnName = "CO_ESTATUS_TRAMITE")
    private EstatusTramiteEntidad estatusAntecesor;

    public EstatusTramiteEntidad() {
        // Metodo Constructor
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstatusEntidad getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusEntidad estatus) {
        this.estatus = estatus;
    }

    public TramiteEntidad getTramite() {
        return tramite;
    }

    public void setTramite(TramiteEntidad tramite) {
        this.tramite = tramite;
    }

    public List<SolicitudEntidad> getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(List<SolicitudEntidad> solicitud) {
        this.solicitud = solicitud;
    }

    public List<EstatusTramiteEntidad> getEstatusTramite() {
        return estatusTramite;
    }

    public void setEstatusTramite(List<EstatusTramiteEntidad> estatusTramite) {
        this.estatusTramite = estatusTramite;
    }

    public EstatusTramiteEntidad getEstatusAntecesor() {
        return estatusAntecesor;
    }

    public void setEstatusAntecesor(EstatusTramiteEntidad estatusAntecesor) {
        this.estatusAntecesor = estatusAntecesor;
    }
}
