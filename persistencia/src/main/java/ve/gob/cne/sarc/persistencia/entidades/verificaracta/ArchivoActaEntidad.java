package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the I011T_ARCHIVO_ACTA database table.
 * 
 */
@Entity
@Table(name="I011T_ARCHIVO_ACTA")
@NamedQuery(name="ArchivoActaEntidad.findAll", query="SELECT i FROM ArchivoActaEntidad i")
public class ArchivoActaEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ARC_ACTA")
	private long id;

	@Column(name="TX_HASH")
	private String txHash;

	@Column(name="TX_RUTA")
	private String txRuta;

	//bi-directional many-to-one association to TipoArchivoEntidad
	@ManyToOne
	@JoinColumn(name="CO_TIPO_ARCHIVO")
	private TipoArchivoEntidad tipoArchivo;

	//bi-directional many-to-one association to ActaTranscritaEntidad
	@ManyToOne
	@JoinColumn(name="CO_ACTA_TRANS")
	private ActaTranscritaEntidad actaTranscrita;

	public ArchivoActaEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTxHash() {
		return this.txHash;
	}

	public void setTxHash(String txHash) {
		this.txHash = txHash;
	}

	public String getTxRuta() {
		return this.txRuta;
	}

	public void setTxRuta(String txRuta) {
		this.txRuta = txRuta;
	}

	public TipoArchivoEntidad getTipoArchivo() {
		return this.tipoArchivo;
	}

	public void setTipoArchivo(TipoArchivoEntidad tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

	public ActaTranscritaEntidad getActaTranscrita() {
		return this.actaTranscrita;
	}

	public void setActaTranscrita(ActaTranscritaEntidad actaTranscrita) {
		this.actaTranscrita = actaTranscrita;
	}

}