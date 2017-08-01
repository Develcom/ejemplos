package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the I004T_TIPO_ARCHIVO database table.
 * 
 */
@Entity
@Table(name="I004T_TIPO_ARCHIVO")
@NamedQuery(name="TipoArchivoEntidad.findAll", query="SELECT i FROM TipoArchivoEntidad i")
public class TipoArchivoEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_TIPO_ARCHIVO")
	private long id;

	@Column(name="TX_DESCRIPCION")
	private String txDescripcion;

	@Column(name="TX_FORMATO")
	private String txFormato;

	//bi-directional many-to-one association to I011tArchivoActa
	@OneToMany(mappedBy="tipoArchivo")
	private List<ArchivoActaEntidad> archivoActas;

	public TipoArchivoEntidad() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTxDescripcion() {
		return this.txDescripcion;
	}

	public void setTxDescripcion(String txDescripcion) {
		this.txDescripcion = txDescripcion;
	}

	public String getTxFormato() {
		return this.txFormato;
	}

	public void setTxFormato(String txFormato) {
		this.txFormato = txFormato;
	}

	public List<ArchivoActaEntidad> getArchivoActas() {
		return this.archivoActas;
	}

	public void setArchivoActas(List<ArchivoActaEntidad> archivoActas) {
		this.archivoActas = archivoActas;
	}

	public ArchivoActaEntidad addArchivoActa(ArchivoActaEntidad archivoActa) {
		getArchivoActas().add(archivoActa);
		archivoActa.setTipoArchivo(this);

		return archivoActa;
	}

	public ArchivoActaEntidad removeArchivoActa(ArchivoActaEntidad archivoActa) {
		getArchivoActas().remove(archivoActa);
		archivoActa.setTipoArchivo(null);

		return archivoActa;
	}

}