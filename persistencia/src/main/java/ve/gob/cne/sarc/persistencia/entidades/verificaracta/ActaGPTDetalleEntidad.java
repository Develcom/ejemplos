package ve.gob.cne.sarc.persistencia.entidades.verificaracta;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the I008T_ACTA_GPT_DETALLE database table.
 * 
 */
/**
 * Descripcion de la clase: Entity de la tabla "I008T_ACTA_GPT_DETALLE". 
 * @author Julio.Posada
 *
 */
@Entity
@Table(name="I008T_ACTA_GPT_DETALLE")
@NamedQuery(name="ActaGPTDetalleEntidad.findAll", query="SELECT i FROM ActaGPTDetalleEntidad i")
public class ActaGPTDetalleEntidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CO_ACTA_GPT_DET")
	private long id;

	@Column(name="TX_VALOR")
	private String txValor;

	public ActaGPTDetalleEntidad() {
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

}