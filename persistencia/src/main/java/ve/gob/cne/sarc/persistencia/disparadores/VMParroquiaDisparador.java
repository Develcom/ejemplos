package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.VMParroquiaEntidad;

/**
 * VMParroquiaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase
 *              VMParroquiaDisparador sean correctos en cuanto a obligatoriedad
 *              y formato.
 * @version 1.0 18/08/2016
 * @author Jennifer Alvarez
 *
 */
public class VMParroquiaDisparador {

	/**
	 * validacionesVMParroquia Este método recibe como parámetro la entidad
	 * VMParroquiaEntidad y llama a otros métodos privados para validar los
	 * atributos que no puedan ser nulos, vacios o deban cumplir con un formato
	 * específico.
	 * 
	 * @since 18 de agost. de 2016
	 * @param municipio
	 * @throws Exception
	 * @return void
	 * @author Jennifer Alvarez
	 * @version 1.0 Jira S2RC-565
	 */
	@PrePersist
	@PreUpdate
	public void validacionesVMParroquia(VMParroquiaEntidad parroquia) throws Exception {

		validarPadreNulo(parroquia);
		validarNombreNulo(parroquia);
	}

	/**
	 * validarPadreNulo Método que Valida si el valor del padre llega
	 * Nulo
	 * 
	 * @since 18 de agost. de 2016
	 * @param parroquia
	 * @throws Exception
	 * @return void
	 * @author Jennifer Alvarez
	 * @version 1.0 Jira S2RC-565
	 */
	private void validarPadreNulo(VMParroquiaEntidad parroquia) throws Exception {
		if (parroquia.getNuPadreGeografico() == null) {
			throw new Exception("Debe introducir el valor correspondiente a la padre del geografico");
		}
	}
	
	/**
	 * validarNombreNulo Método que Valida si el valor del nombre llega
	 * Nulo
	 * 
	 * @since 18 de agost. de 2016
	 * @param parroquia
	 * @throws Exception
	 * @return void
	 * @author Jennifer Alvarez
	 * @version 1.0 Jira S2RC-565
	 */
	private void validarNombreNulo(VMParroquiaEntidad parroquia) throws Exception {
		if (parroquia.getNbGeografico() == null) {
			throw new Exception("Debe introducir el valor correspondiente a la nombre del geografico");
		}
	}
}
