package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the I007T_ACTA_TRANSCRITA_DETALLE database table.
 * 
 */
@Entity
@Table(name="I007T_ACTA_TRANSCRITA_DETALLE")
@NamedQuery(name="ActaTranscritaDetalleEntidad.findAll", query="SELECT i FROM ActaTranscritaDetalleEntidad i")
public class ActaTranscritaDetalleEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ACTA_TRANS_DET")
	@SequenceGenerator(name = "ACTA_TRANS_DET_SEQ", sequenceName = "I007S_CO_ACTA_TRANS_DET", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTA_TRANS_DET_SEQ")
	private long id;

	@Column(name="TX_VALOR")
	private String txValor;

	//bi-directional many-to-one association to I006tCampo

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="CO_CAMPO")
	private CampoEntidad campo;

	//bi-directional many-to-one association to I009tActaTranscrita
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name="CO_ACTA_TRANS")
	private ActaTranscritaEntidad actaTranscrita;

	public ActaTranscritaDetalleEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTxValor() {
		return this.txValor;
	}

	public void setTxValor(String txValor) {
		this.txValor = txValor;
	}

	public CampoEntidad getCampo() {
		return this.campo;
	}

	public void setCampo(CampoEntidad campo) {
		this.campo = campo;
	}

	public ActaTranscritaEntidad getActaTranscrita() {
		return this.actaTranscrita;
	}

	public void setActaTranscrita(ActaTranscritaEntidad actaTranscrita) {
		this.actaTranscrita = actaTranscrita;
	}

}