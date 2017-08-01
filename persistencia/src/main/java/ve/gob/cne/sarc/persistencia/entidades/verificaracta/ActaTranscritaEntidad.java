package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;


/**
 * The persistent class for the I009T_ACTA_TRANSCRITA database table.
 * 
 */
@Entity
@Table(name="I009T_ACTA_TRANSCRITA")
@NamedQueries({ 
	@NamedQuery(name="ActaTranscritaEntidad.findAll", query="SELECT i FROM ActaTranscritaEntidad i")
	})

public class ActaTranscritaEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ACTA_TRANS")
	@SequenceGenerator(name = "ACTA_TRANS_SEQ", sequenceName = "I009S_CO_ACTA_TRANS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTA_TRANS_SEQ")
	private long id;

	@Column(name="CO_TRAMITE_PAQUETE")
	private long coTramitePaquete;

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
	
	@Column(name="TIPO_ACTA")
    private long tipoActa;
	
	@Column(name = "ACTA_VERIFICADA", length = 2)
	private String actaVerificada;
	
	@ManyToOne
	@JoinColumn(name = "CO_OFICINA", nullable = false)
	private OficinaEntidad codigoOficina;

	//bi-directional many-to-one association to ResultadoVerificacionEntidad
	@OneToMany(mappedBy="actaTranscrita")
	private List<ResultadoVerificacionEntidad> resultadoVerificacions;

	//bi-directional many-to-one association to ActaTranscritaDetalleEntidad
	@OneToMany(mappedBy="actaTranscrita")
	private List<ActaTranscritaDetalleEntidad> actaTranscritaDetalles;

	//bi-directional many-to-one association to ArchivoActaEntidad
	@OneToMany(mappedBy="actaTranscrita")
	private List<ArchivoActaEntidad> archivoActas;

	public ActaTranscritaEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public OficinaEntidad getCodigoOficina() {
		return this.codigoOficina;
	}

	public void setCodigoOficina(OficinaEntidad codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	public long getCoTramitePaquete() {
		return this.coTramitePaquete;
	}

	public void setCoTramitePaquete(long coTramitePaquete) {
		this.coTramitePaquete = coTramitePaquete;
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

	public String getActaVerificada() {
		return actaVerificada;
	}

	public void setActaVerificada(String actaVerificada) {
		this.actaVerificada = actaVerificada;
	}
	
	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}

	public List<ResultadoVerificacionEntidad> getResultadoVerificacions() {
		return this.resultadoVerificacions;
	}

	public void setResultadoVerificacions(List<ResultadoVerificacionEntidad> resultadoVerificacions) {
		this.resultadoVerificacions = resultadoVerificacions;
	}

	public ResultadoVerificacionEntidad addResultadoVerificacion(ResultadoVerificacionEntidad resultadoVerificacion) {
		getResultadoVerificacions().add(resultadoVerificacion);
		resultadoVerificacion.setActaTranscrita(this);

		return resultadoVerificacion;
	}

	public ResultadoVerificacionEntidad removeResultadoVerificacion(ResultadoVerificacionEntidad resultadoVerificacion) {
		getResultadoVerificacions().remove(resultadoVerificacion);
		resultadoVerificacion.setActaTranscrita(null);

		return resultadoVerificacion;
	}

	public List<ActaTranscritaDetalleEntidad> getActaTranscritaDetalles() {
		return this.actaTranscritaDetalles;
	}

	public void setActaTranscritaDetalles(List<ActaTranscritaDetalleEntidad> actaTranscritaDetalles) {
		this.actaTranscritaDetalles = actaTranscritaDetalles;
	}

	public ActaTranscritaDetalleEntidad addActaTranscritaDetalle(ActaTranscritaDetalleEntidad actaTranscritaDetalle) {
		getActaTranscritaDetalles().add(actaTranscritaDetalle);
		actaTranscritaDetalle.setActaTranscrita(this);

		return actaTranscritaDetalle;
	}

	public ActaTranscritaDetalleEntidad removeActaTranscritaDetalle(ActaTranscritaDetalleEntidad actaTranscritaDetalle) {
		getActaTranscritaDetalles().remove(actaTranscritaDetalle);
		actaTranscritaDetalle.setActaTranscrita(null);

		return actaTranscritaDetalle;
	}

	public List<ArchivoActaEntidad> getArchivoActas() {
		return this.archivoActas;
	}

	public void setArchivoActas(List<ArchivoActaEntidad> archivoActas) {
		this.archivoActas = archivoActas;
	}

	public ArchivoActaEntidad addArchivoActa(ArchivoActaEntidad archivoActa) {
		getArchivoActas().add(archivoActa);
		archivoActa.setActaTranscrita(this);

		return archivoActa;
	}

	public ArchivoActaEntidad removeArchivoActa(ArchivoActaEntidad archivoActa) {
		getArchivoActas().remove(archivoActa);
		archivoActa.setActaTranscrita(null);

		return archivoActa;
	}

    public long getTipoActa() {
        return tipoActa;
    }

    public void setTipoActa(long tipoActa) {
        this.tipoActa = tipoActa;
    }

}