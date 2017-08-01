package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SolicitudRecaudoEntidad.java
 *
 * @descripcion Se crea la clase SolicitudEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X011T_SOLICITUD_RECAUDO")

public class SolicitudRecaudoEntidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_SOLICITUD_RECAUDO", nullable = false, length = 22)
    @SequenceGenerator(name = "SOLICITUD_RECAUDO_SEQ", sequenceName = "X011S_CO_SOLICITUD_RECAUDO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SOLICITUD_RECAUDO_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_SOLICITUD", referencedColumnName = "CO_SOLICITUD", nullable = false)
    private SolicitudEntidad solicitud;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_RECAUDO", referencedColumnName = "CO_RECAUDO", nullable = false)
    private RecaudoEntidad recaudo;

    @Basic(optional = false)
    @Column(name = "OBLIGATORIO", nullable = false)
    private boolean obligatorio;

    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Basic(optional = true)
    @Column(name = "FE_ACTUALIZADO", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date fechaActualizando;

    public SolicitudRecaudoEntidad() {

        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SolicitudEntidad getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudEntidad solicitud) {
        this.solicitud = solicitud;
    }

    public RecaudoEntidad getRecaudo() {
        return recaudo;
    }

    public void setRecaudo(RecaudoEntidad recaudo) {
        this.recaudo = recaudo;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaActualizando() {
        return fechaActualizando;
    }

    public void setFechaActualizando(Date fechaActualizando) {
        this.fechaActualizando = fechaActualizando;
    }

}
