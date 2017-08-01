package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.GeograficoEntidad;

/**
 * GeograficoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase GeograficoEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @version 1.0 18/08/2016
 * @author Jennifer Alvarez
 *
 */
public class GeograficoDisparador {

	  /**
     * validacionesGeografico
     * Este método recibe como parámetro la entidad Geografico y llama a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico.
     * @since 18 de agost. de 2016
     * @param geografico
     * @throws Exception
     * @return void
     * @author Jennifer Alvarez
     * @version 1.0
     * Jira S2RC-565
     */
    @PrePersist
    @PreUpdate
    public void validacionesGeografico(GeograficoEntidad geografico) throws Exception {
    	
    	validarValorNulo(geografico);   
    }

    /**
     * validarValorNulo
     * Método que Valida si el valor del Geografico llega Nulo
     * @since 18 de agost. de 2016
     * @param geografico
     * @throws Exception
     * @return void
     * @author Jennifer Alvarez
     * @version 1.0
     * Jira S2RC-565
     */
    private void validarValorNulo(GeograficoEntidad geografico) throws Exception {
        if (geografico.getNbGeografico() == null) {
            throw new Exception(
                    "Debe introducir el valor correspondiente al geografico");
        }
    }
}
