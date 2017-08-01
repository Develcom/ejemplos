package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the I005T_RESULTADO_VERIFICACION database table.
 * 
 */
@Entity
@Table(name="I005T_RESULTADO_VERIFICACION")
@NamedQuery(name="ResultadoVerificacionEntidad.findAll", query="SELECT i FROM ResultadoVerificacionEntidad i")
public class ResultadoVerificacionEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_RESULTADO_VERIFICACION")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_VERIFICACION")
	private Date feVerificacion;

	@Column(name="TX_OBSERVACIONES")
	private String txObservaciones;

	@Column(name="TX_RESULTADO")
	private String txResultado;

	//bi-directional many-to-one association to I009tActaTranscrita
	@ManyToOne
	@JoinColumn(name="CO_ACTA_TRANS")
	private ActaTranscritaEntidad actaTranscrita;

	//bi-directional many-to-one association to I010tActaGpt
	@ManyToOne
	@JoinColumn(name="CO_ACTA_GPT")
	private ActaGPTEntidad ActaGpt;

	public ResultadoVerificacionEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFeVerificacion() {
		return this.feVerificacion;
	}

	public void setFeVerificacion(Date feVerificacion) {
		this.feVerificacion = feVerificacion;
	}

	public String getTxObservaciones() {
		return this.txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public String getTxResultado() {
		return this.txResultado;
	}

	public void setTxResultado(String txResultado) {
		this.txResultado = txResultado;
	}

	public ActaTranscritaEntidad getActaTranscrita() {
		return this.actaTranscrita;
	}

	public void setActaTranscrita(ActaTranscritaEntidad actaTranscrita) {
		this.actaTranscrita = actaTranscrita;
	}

	public ActaGPTEntidad getActaGpt() {
		return this.ActaGpt;
	}

	public void setActaGpt(ActaGPTEntidad ActaGpt) {
		this.ActaGpt = ActaGpt;
	}

}