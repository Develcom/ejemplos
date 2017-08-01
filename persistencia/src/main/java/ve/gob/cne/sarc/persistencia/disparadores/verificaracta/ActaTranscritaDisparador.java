package ve.gob.cne.sarc.persistencia.disparadores.verificaracta;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaTranscritaDetalleEntidad;
import ve.gob.cne.sarc.persistencia.entidades.verificaracta.ActaTranscritaEntidad;

/**
 * ActaTranscritaDisparador.java
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaTranscritaEntidad
 * @version 1.0
 * 20 de jun. de 2016
 * @author Domingo Rondon
 */
public class ActaTranscritaDisparador {
	
    /**
	 * validacionesActaTranscrita
	 * Este método recibe como parámetro la entidad ActaTranscritaEntidad y llama a otros
     * métodos privados para validar los atributos que no puedan ser nulos,
     * vacios o deban cumplir con un formato específico.
	 * @since 20 de jun. de 2016
	 * @param actaTranscrita
	 * @throws Exception
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	@PrePersist
	@PreUpdate
	public void validacionesActaTranscrita(ActaTranscritaEntidad actaTranscrita) throws Exception {
		
		validarFechaActaNulo(actaTranscrita);
		validarFechaActaFormato(actaTranscrita);
		validarNumeroActaNulo(actaTranscrita);
		validarNumeroTomoNulo(actaTranscrita);
		validarNotaMarguinalNulo(actaTranscrita);
		validarActaTranscritaDetallesNulo(actaTranscrita);
		
	}
	
	/**
	 * validarFechaActaNulo
	 * Método que valida si el Campo Fecha Acta es Nulo
	 * @since 20 de jun. de 2016
	 * @param actaTranscrita
	 * @throws Exception
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	private void validarFechaActaNulo(ActaTranscritaEntidad actaTranscrita) throws Exception {
		if (actaTranscrita.getFeActa() == null) {
			actaTranscrita.setFeActa(new Date());
        }
	}
	
	/**
	 * validarFechaActaFormato
	 * Método que valida si el Campo Fecha Acta no tiene el formato correcto.
	 * @since 20 de jun. de 2016
	 * @param actaTranscrita
	 * @throws Exception
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	private void validarFechaActaFormato(ActaTranscritaEntidad actaTranscrita) throws Exception {
        try {
            if (actaTranscrita.getFeActa() != null) {
                SimpleDateFormat format = new SimpleDateFormat(
                        "dd/MM/yyyy hh:mm:ss");
                actaTranscrita.setFeActa(format.parse(actaTranscrita.getFeActa()
                        .toString()));
            }
        } catch (ParseException ex) {
            throw new Exception("Error en la fecha fechaActa");
        }
    }
	
	/**
	 * validarNumeroActaNulo
	 * Método que valida si el Número del Acta es Nulo
	 * @since 20 de jun. de 2016
	 * @param actaTranscrita
	 * @throws Exception
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	private void validarNumeroActaNulo(ActaTranscritaEntidad actaTranscrita) throws Exception {
        if (Long.valueOf(actaTranscrita.getNumeroActa()) == null) {
            throw new Exception(
                    "Debe introducir el numeroActa correspondiente al acta");
        }
    }

	/**
	 * validarNumeroTomoNulo
	 * Método que valida si el Número del Tomo es Nulo
	 * @since 20 de jun. de 2016
	 * @param actaTranscrita
	 * @throws Exception
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	private void validarNumeroTomoNulo(ActaTranscritaEntidad actaTranscrita) throws Exception {
        if (Long.valueOf(actaTranscrita.getNuTomo()) == null) {
            throw new Exception(
                    "Debe introducir el numeroTomo correspondiente al acta");
        }
    }
	
	/**
	 * validarNotaMarguinalNulo
	 * Método que valida si la Nota Marguinal es Nula
	 * @since 20 de jun. de 2016
	 * @param actaTranscrita
	 * @throws Exception
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-216
	 */
	private void validarNotaMarguinalNulo(ActaTranscritaEntidad actaTranscrita) throws Exception {
        if (Long.valueOf(actaTranscrita.getInNotaMarginal()) == null) {
            throw new Exception(
                    "Debe introducir la notaMarginal correspondiente al acta");
        }
    }
	
	/**
	 * validarActaTranscritaDetallesNulo
	 * Método que valida que la lista de Detalle de las Actas Transcritas no se encuentre Nula
	 * @since 21 de jun. de 2016
	 * @param actaTranscrita
	 * @return void
	 * @author Domingo Rondon
	 * @version 1.0
	 * Jira SST-247
	 */
	private void validarActaTranscritaDetallesNulo(ActaTranscritaEntidad actaTranscrita) {
        if (actaTranscrita.getActaTranscritaDetalles() == null) {
            actaTranscrita.setActaTranscritaDetalles(new ArrayList<ActaTranscritaDetalleEntidad>());
        }
    }
}
