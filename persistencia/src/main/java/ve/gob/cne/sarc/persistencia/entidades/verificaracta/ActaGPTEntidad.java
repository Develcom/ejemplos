package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;




/**
 * The persistent class for the I010T_ACTA_GPT database table.
 * 
 */
@Entity
@Table(name="I010T_ACTA_GPT")
@NamedQuery(name="ActaGPTEntidad.findAll", query="SELECT i FROM ActaGPTEntidad i")
public class ActaGPTEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ACTA_GPT")
	private long id;

	@Column(name="CO_OFICINA")
	private long coOficina;

	@Temporal(TemporalType.DATE)
	@Column(name="FE_ACTA")
	private Date feActa;

	@Column(name="IN_NOTA_MARGINAL")
	private long inNotaMarginal;

	@Column(name="NU_ACTA")
	private long numeroActa;

	@Column(name="NU_FOLIO")
	private long nuFolio;

	@Column(name="NU_TOMO")
	private long nuTomo;

	@Column(name="TX_OBSERVACIONES")
	private String txObservaciones;

	//bi-directional many-to-one association to ResultadoVerificacionEntidad
	@OneToMany(mappedBy="ActaGpt")
	private List<ResultadoVerificacionEntidad> ResultadoVerificacions;

	public ActaGPTEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCoOficina() {
		return this.coOficina;
	}

	public void setCoOficina(long coOficina) {
		this.coOficina = coOficina;
	}

	public Date getFeActa() {
		return this.feActa;
	}

	public void setFeActa(Date feActa) {
		this.feActa = feActa;
	}

	public long getInNotaMarginal() {
		return this.inNotaMarginal;
	}

	public void setInNotaMarginal(long inNotaMarginal) {
		this.inNotaMarginal = inNotaMarginal;
	}

	public long getNumeroActa() {
		return this.numeroActa;
	}

	public void setNumeroActa(long numeroActa) {
		this.numeroActa = numeroActa;
	}

	public long getNuFolio() {
		return this.nuFolio;
	}

	public void setNuFolio(long nuFolio) {
		this.nuFolio = nuFolio;
	}

	public long getNuTomo() {
		return this.nuTomo;
	}

	public void setNuTomo(long nuTomo) {
		this.nuTomo = nuTomo;
	}

	public String getTxObservaciones() {
		return this.txObservaciones;
	}

	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public List<ResultadoVerificacionEntidad> getResultadoVerificacions() {
		return this.ResultadoVerificacions;
	}

	public void setResultadoVerificacions(List<ResultadoVerificacionEntidad> ResultadoVerificacions) {
		this.ResultadoVerificacions = ResultadoVerificacions;
	}

	public ResultadoVerificacionEntidad addResultadoVerificacion(ResultadoVerificacionEntidad ResultadoVerificacion) {
		getResultadoVerificacions().add(ResultadoVerificacion);
		ResultadoVerificacion.setActaGpt(this);

		return ResultadoVerificacion;
	}

	public ResultadoVerificacionEntidad removeResultadoVerificacion(ResultadoVerificacionEntidad ResultadoVerificacion) {
		getResultadoVerificacions().remove(ResultadoVerificacion);
		ResultadoVerificacion.setActaGpt(null);

		return ResultadoVerificacion;
	}

}