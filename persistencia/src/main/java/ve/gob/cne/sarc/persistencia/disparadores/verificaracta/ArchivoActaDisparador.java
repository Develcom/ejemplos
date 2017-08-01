package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ArchivoActaEntidad;

/**
 * ArchivoActaDisparador.java
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ArchivoActaEntidad
 * @version 1.0
 * 21 de jun. de 2016
 * @author Domingo Rondon
 */
public class ArchivoActaDisparador {
    
    /**
     * validacionesArchivoActa
     * Este método recibe como parámetro la entidad Archivo Acta y llamara a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico en caso de que sea necesario.
     * @since 21 de jun. de 2016
     * @param archivoActaEntidad
     * @throws Exception
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SST-223
     */
    @PrePersist
    @PreUpdate
    public void validacionesArchivoActa(ArchivoActaEntidad archivoActaEntidad) throws Exception {
            validarActaTranscritaNulo(archivoActaEntidad);
    }
    
    /**
     * validarActaTranscritaNulo
     * <p>
     * Este método recibe como parámetro la entidad ArchivoActaEntidad validar que el
     * atributo actaTranscrita que no sea nulo.
     * </p>
     * 
     * @since 22/6/2016
     * @param archivoActaEntidad
     * @throws Exception
     * @return void
     * @author Maricruz Lista
     * @version 1.0
     * Jira SST-248
     */
    private void validarActaTranscritaNulo(ArchivoActaEntidad archivoActaEntidad) throws Exception {
        if (archivoActaEntidad.getActaTranscrita() == null) {
            throw new Exception("Debe introducir el Acta Transcrita correspondiente al Archivo");
        }
    }


}
