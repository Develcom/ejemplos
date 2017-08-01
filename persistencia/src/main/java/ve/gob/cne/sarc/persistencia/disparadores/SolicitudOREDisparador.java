package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.SolicitudOREEntidad;

/**
 * SolicitudOREDisparador.java
 * @descripcion Clase que contiene las validaciones de campos que no pueden estar nulas
 * @version 
 * 23 de nov. de 2016
 * @author Domingo Rondon
 */
public class SolicitudOREDisparador {
    
    /**
     * validacionesSolicitudORE
     * Metodo que contiene las validaciones de los campos que no pueden estar nulos
     * @since 23 de nov. de 2016
     * @param solicitudORE
     * @throws ExceptionDisparador
     * @return void
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-211
     */
    @PrePersist
    @PreUpdate
    public void validacionesSolicitudORE(SolicitudOREEntidad solicitudORE) throws ExceptionDisparador {
        validarActaNulo(solicitudORE);
        validarFechaSolicitudNulo(solicitudORE);
        validarLugarCelebracionNulo(solicitudORE);
        validarMotivoTrasladoNulo(solicitudORE);
        validarUrlDocumentoNulo(solicitudORE);
    }
    
    private void validarActaNulo(SolicitudOREEntidad solicitudORE) throws ExceptionDisparador {
        if (solicitudORE.getActa() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Acta correspondiente");
        }
    }
    
    private void validarFechaSolicitudNulo(SolicitudOREEntidad solicitudORE) throws ExceptionDisparador {
        if (solicitudORE.getFechaSolicitudORE() == null) {
            throw new ExceptionDisparador("2#Debe introducir la Fecha de la Solicitud correspondiente");
        }
    }
    
    private void validarLugarCelebracionNulo(SolicitudOREEntidad solicitudORE) throws ExceptionDisparador {
        if (solicitudORE.getLugarCelebracion() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Lugar de Celebracion correspondiente");
        }
    }
    
    private void validarMotivoTrasladoNulo(SolicitudOREEntidad solicitudORE) throws ExceptionDisparador {
        if (solicitudORE.getMotivoTraslado() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Motivo Traslado correspondiente");
        }
    }
    
    private void validarUrlDocumentoNulo(SolicitudOREEntidad solicitudORE) throws ExceptionDisparador {
        if (solicitudORE.getUrlDocumento() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Url del documento correspondiente");
        }
    }
    

}
