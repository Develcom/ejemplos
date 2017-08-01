package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.CampoEntidad;

/**
 * CampoDisparador.java
 * 
 * @descripcion 
 * <p>
 * Esta clase se encarga de validar que los atributos de la clase CampoEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * </p>
 * @version 1.0 
 * 17/6/2016
 * @author Maricruz Lista
 */

public class CampoDisparador {

    /**
     * validacionesPersona
     * <p>
     * Este método recibe como parámetro la entidad CampoEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * </p>
     * 
     * @since 17/6/2016
     * @param campo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0 
     * Jira SST-222
     */

    @PrePersist
    @PreUpdate
    public void validacionesPersona(CampoEntidad campo) throws Exception {
        validarNombreNulo(campo);
        validarTipoDatoNulo(campo);
        validarFormatoNulo(campo);
    }

    /**
     * validarNombreNulo
     * <p>
     * Este método recibe como parámetro la entidad CampoEntidad validar que el
     * atributo Nombre que no sea nulo.
     * </p>
     * 
     * @since 17/6/2016
     * @param campo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0
     * Jira SST-222
     */
    private void validarNombreNulo(CampoEntidad campo) throws Exception {
        if (campo.getTxNombre() == null) {
            throw new Exception("Debe introducir el Nombre correspondiente al Campo");
        }
    }

    /**
     * validarTipoDatoNulo
     * <p>
     * Este método recibe como parámetro la entidad CampoEntidad validar que el
     * atributo TipoDato que no sea nulo.
     * </p>
     * 
     * @since 17/6/2016
     * @param campo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0
     * Jira SST-222
     */
    private void validarTipoDatoNulo(CampoEntidad campo) throws Exception {
        if (campo.getTxTipoDato() == null) {
            throw new Exception("Debe introducir el Tipo de Dato correspondiente al Campo");
        }
    }

    /**
     * validarFormatoNulo
     * <p>
     * Este método recibe como parámetro la entidad CampoEntidad validar que el
     * atributo Formato que no sea nulo.
     * </p>
     * 
     * @since 17/6/2016
     * @param campo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0
     * Jira SST-222
     */
    private void validarFormatoNulo(CampoEntidad campo) throws Exception {
        if (campo.getTxFormato() == null) {
            throw new Exception("Debe introducir el Formato correspondiente al Campo");
        }
    }

}
