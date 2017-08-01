package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;


/**
 * The persistent class for the I006T_CAMPO database table.
 * 
 */
@Entity
@Table(name="I006T_CAMPO")
@NamedQuery(name="CampoEntidad.findAll", query="SELECT i FROM CampoEntidad i")
public class CampoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_CAMPO")
	private long id;

	@Column(name="TX_FORMATO")
	private String txFormato;

	@Column(name="TX_NOMBRE")
	private String txNombre;

	@Column(name="TX_TIPO_DATO")
	private String txTipoDato;

	//bi-directional many-to-one association to ActaTranscritaDetalleEntidad
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(fetch = FetchType.EAGER, mappedBy="campo")
	private List<ActaTranscritaDetalleEntidad> actaTranscritaDetalles;

	public CampoEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTxFormato() {
		return this.txFormato;
	}

	public void setTxFormato(String txFormato) {
		this.txFormato = txFormato;
	}

	public String getTxNombre() {
		return this.txNombre;
	}

	public void setTxNombre(String txNombre) {
		this.txNombre = txNombre;
	}

	public String getTxTipoDato() {
		return this.txTipoDato;
	}

	public void setTxTipoDato(String txTipoDato) {
		this.txTipoDato = txTipoDato;
	}

	public List<ActaTranscritaDetalleEntidad> getActaTranscritaDetalles() {
		return this.actaTranscritaDetalles;
	}

	public void setActaTranscritaDetalles(List<ActaTranscritaDetalleEntidad> actaTranscritaDetalles) {
		this.actaTranscritaDetalles = actaTranscritaDetalles;
	}

	public ActaTranscritaDetalleEntidad addActaTranscritaDetalle(ActaTranscritaDetalleEntidad ActaTranscritaDetalle) {
		getActaTranscritaDetalles().add(ActaTranscritaDetalle);
		ActaTranscritaDetalle.setCampo(this);

		return ActaTranscritaDetalle;
	}

	public ActaTranscritaDetalleEntidad removeActaTranscritaDetalle(ActaTranscritaDetalleEntidad ActaTranscritaDetalle) {
		getActaTranscritaDetalles().remove(ActaTranscritaDetalle);
		ActaTranscritaDetalle.setCampo(null);

		return ActaTranscritaDetalle;
	}

}