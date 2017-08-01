/**
 *@TipoArchivoDisparador.java
 * @version 1.0
 * 21/6/2016
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.TipoArchivoEntidad;

/**
 * TipoArchivoDisparador.java
 * @descripcion
 * <p>
 * Esta clase se encarga de validar que los atributos de la clase TipoArchivoEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * </p>
 * @version 1.0
 * 21/6/2016
 * @author Maricruz Lista
 */
public class TipoArchivoDisparador {
    /**
     * validacionesTipoArchivo
     * <p>
     * Este método recibe como parámetro la entidad TipoArchivoEntity y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * </p>
     * 
     * @since 21/6/2016
     * @param tipoarchivo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0 
     * Jira SST-224
     */

    @PrePersist
    @PreUpdate
    public void validacionesTipoArchivo(TipoArchivoEntidad tipoarchivo) throws Exception {
        validarDescripcionNulo(tipoarchivo);
        validarFormatoNulo(tipoarchivo);
    }

    /**
     * validarDescripcionNulo
     * <p>
     * Este método recibe como parámetro la entidad TipoArchivoEntidad validar que el
     * atributo descripcion que no sea nulo.
     * </p>
     * 
     * @since 21/6/2016
     * @param tipoarchivo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0
     * Jira SST-224
     */
    private void validarDescripcionNulo(TipoArchivoEntidad tipoarchivo) throws Exception {
        if (tipoarchivo.getTxDescripcion() == null) {
            throw new Exception("Debe introducir la Descripcion correspondiente al Tipo de Archivo");
        }
    }


    /**
     * validarFormatoNulo
     * <p>
     * Este método recibe como parámetro la entidad TipoArchivoEntidad validar que el
     * atributo formato que no sea nulo.
     * </p>
     * 
     * @since 21/6/2016
     * @param tipoarchivo
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0
     * Jira SST-224
     */
    private void validarFormatoNulo(TipoArchivoEntidad tipoarchivo) throws Exception {
        if (tipoarchivo.getTxFormato() == null) {
            throw new Exception("Debe introducir el Formato correspondiente al Tipo de Archivo");
        }
    }
    
}
