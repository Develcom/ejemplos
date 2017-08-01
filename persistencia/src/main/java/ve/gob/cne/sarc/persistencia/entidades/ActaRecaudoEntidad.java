package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the C085T_ACTA_RECAUDO database table.
 * 
 */
@Entity
@Table(name="C085T_ACTA_RECAUDO")
@NamedQueries(value = {
        @NamedQuery(name = "ActaRecaudoEntidad.findBySolicitud", query = "SELECT ar FROM ActaRecaudoEntidad ar WHERE ar.acta.solicitud.numero = :numeroSolicitud"),
        @NamedQuery(name = "ActaRecaudoEntidad.findBySolicitudAndEscenarioRecaudo", query = "SELECT ar FROM ActaRecaudoEntidad ar"
                + " WHERE ar.acta.solicitud.numero = :numeroSolicitud AND ar.escenarioRecaudo.id = :idEscenarioRecaudo") })
public class ActaRecaudoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ACTA_RECAUDO")
	private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name="CO_ACTA")
	private ActaEntidad acta;

	@ManyToOne(optional = false)
	@JoinColumn(name="CO_ESCENARIO_RECAUDO")
	private EscenarioRecaudoEntidad escenarioRecaudo;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_CONSIGNACION")
	private Date fechaConsignacion;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_SINCRONIZACION")
	private Date fechaSincronizacion;

	@Column(name="IN_ESTATUS_CARGA")
	private String estatusCarga;

	@Column(name="IN_TIPO_ARCHIVO")
	private String tipoArchivo;

	@Column(name="IN_VALIDO")
	private long valido;

	@Column(name="NB_ARCHIVO")
	private String nombreArchivo;

	@Column(name="TX_OBSERVACION")
	private String observacion;

	public ActaRecaudoEntidad() {
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ActaEntidad getActa() {
        return acta;
    }

    public void setActa(ActaEntidad acta) {
        this.acta = acta;
    }

    public EscenarioRecaudoEntidad getEscenarioRecaudo() {
        return escenarioRecaudo;
    }

    public void setEscenarioRecaudo(EscenarioRecaudoEntidad escenarioRecaudo) {
        this.escenarioRecaudo = escenarioRecaudo;
    }

    public Date getFechaConsignacion() {
        return fechaConsignacion;
    }

    public void setFechaConsignacion(Date fechaConsignacion) {
        this.fechaConsignacion = fechaConsignacion;
    }

    public Date getFechaSincronizacion() {
        return fechaSincronizacion;
    }

    public void setFechaSincronizacion(Date fechaSincronizacion) {
        this.fechaSincronizacion = fechaSincronizacion;
    }

    public String getEstatusCarga() {
        return estatusCarga;
    }

    public void setEstatusCarga(String estatusCarga) {
        this.estatusCarga = estatusCarga;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public long getValido() {
        return valido;
    }

    public void setValido(long valido) {
        this.valido = valido;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

	
}