/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ResultadoVerificacionEntidad;


/**
* <p>
* Esta clase se encarga de validar que los atributos de la clase ActaEntidad
* sean correctos en cuanto a obligatoriedad y formato.
* </p>
* 
* @author jennifer 
* @version 1.1
*/
public class ResultadoVerificacionDisparador {

  /**
  * <p>
  * Este método recibe como parámetro la entidad ActaEntidad y llama a otros
  * métodos privados para validar los atributos que no puedan ser nulos,
  * vacios o deban cumplir con un formato específico.
  * </p>
  * 
  * @param acta
  *            <p>
  *            </p>
  * @throws Exception
  */
 @PrePersist
 @PreUpdate
 public void validacionesResultadoVerificacion(ResultadoVerificacionEntidad resultado) throws Exception {
     validarCodigoActaTranscritaNulo(resultado);
     validarFechaVerificacionNulo(resultado);
     validarCodigoActaGPTNulo(resultado);
     validarCodigoResultadoVerifiNulo(resultado);
    
 }

 /**
 * validarCodigoResultadoVerifiNulo
 * Este metodo permite validar que el campo resultado no este vacio
 * @since 09/9/2016
 * @param resultado
 * @return void
 * @author Julio Posada
 * @version 1.0
 * Jira (Ejemplo: S2RC-611)
 * @throws Exception 
 */

private void validarCodigoResultadoVerifiNulo(ResultadoVerificacionEntidad resultado) throws Exception {
    // TODO Auto-generated method stub
    if (Long.valueOf(resultado.getId()) == null) {
        throw new Exception("Debe introducir el código del resultado de la verificación");
    }
    
}

/**
 * validarCodigoActaGPTNulo
 * Este metodo permite validar si el codigo del acta traida desde el GPT no este vacio 
 * @since 22/6/2016
 * @param resultado
 * @return void
 * @author Isaura Ruíz
 * @version 1.0
 * Jira (Ejemplo: S2RC-001)
 * @throws Exception 
 */

private void validarCodigoActaGPTNulo(ResultadoVerificacionEntidad resultado) throws Exception {
    // TODO Auto-generated method stub
    if (resultado.getActaGpt()== null) {
        throw new Exception("el código del acta del GPT no debe estar vacío");
    }
}

/**
 * validarFechaVerificacionNulo
 * Este metodo permite validar que la fecha de verificacion no este vacia
 * @since 22/6/2016
 * @param resultado
 * @return void
 * @author Isaura Ruíz
 * @version 1.0
 * Jira (Ejemplo: S2RC-001)
 * @throws Exception 
 */

private void validarFechaVerificacionNulo(ResultadoVerificacionEntidad resultado) throws Exception {
    // TODO Auto-generated method stub
    if (resultado.getFeVerificacion()== null) {
        throw new Exception("La fecha de verificación  no debe estar vacía");
    }
}

/**
 * validarCodigoActaTranscritaNulo
 * Este método permite validar que el codigo del acta transcrita no este vacía
 * @since 21/6/2016
 * @param resultado
 * @return void
 * @author Isaura Ruíz
 * @version 1.0
 * Jira (Ejemplo: S2RC-001)
 * @throws Exception 
 */

private void validarCodigoActaTranscritaNulo(ResultadoVerificacionEntidad resultado) throws Exception {
    // TODO Auto-generated method stub
    if (resultado.getActaGpt()== null) {
        throw new Exception("El código del acta transcrita no debe estar vacío");
    }
}


}
