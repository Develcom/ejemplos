package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TipoGeograficoEntidad;

/**
 * TipoGeograficoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase
 *              TipoGeograficoEntidad sean correctos en cuanto a obligatoriedad
 *              y formato.
 * @version 1.0 18/08/2016
 * @author Jennifer Alvarez
 *
 */
public class TipoGeograficoDisparador {

	/**
	 * validacionesTipoGeografico Este método recibe como parámetro la entidad
	 * TipoGeografico y llama a otros métodos privados para validar los
	 * atributos que no puedan ser nulos, vacios o deban cumplir con un formato
	 * específico.
	 * 
	 * @since 18 de agost. de 2016
	 * @param tipoGeografico
	 * @throws Exception
	 * @return void
	 * @author Jennifer Alvarez
	 * @version 1.0 Jira S2RC-565
	 */
	@PrePersist
	@PreUpdate
	public void validacionesTipoGeografico(TipoGeograficoEntidad tipoGeografico) throws Exception {

		validarValorNulo(tipoGeografico);
	}

	/**
	 * validarValorNulo Método que Valida si el valor del TipoGeografico llega
	 * Nulo
	 * 
	 * @since 18 de agost. de 2016
	 * @param tipoGeografico
	 * @throws Exception
	 * @return void
	 * @author Jennifer Alvarez
	 * @version 1.0 Jira S2RC-565
	 */
	private void validarValorNulo(TipoGeograficoEntidad tipoGeografico) throws Exception {
		if (tipoGeografico.getTxDescripcion() == null) {
			throw new Exception("Debe introducir el valor correspondiente a la descripcion del tipo geografico");
		}
	}
}
