package ve.gob.cne.sarc.persistencia.disparadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.DocumentoPublicoEntidad;

/**
 * DocumentoPublicoDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase DocumentoPublicoEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class DocumentoPublicoDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad DocumentoPublicoEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param documentoPublico objeto del modelo ontologico de Documento Publico
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesDocumentoPublico(
            DocumentoPublicoEntidad documentoPublico) throws ExceptionDisparador {
        validarNombreEntePublicoNulo(documentoPublico);
        validarParticipanteNulo(documentoPublico);
        validarNumeroOficioNulo(documentoPublico);
        validarApellidoNulo(documentoPublico);
        validarNombreNulo(documentoPublico);
        validarApellidoJuezNulo(documentoPublico);
        validarNombreJuezNulo(documentoPublico);
        validarFechaElaboracionFormato(documentoPublico);
    }

    private void validarParticipanteNulo(
            DocumentoPublicoEntidad documentoPublico) throws ExceptionDisparador {
        if (documentoPublico.getParticipante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el participante correspondiente al documento público");
        }
    }

    private void validarNombreEntePublicoNulo(
            DocumentoPublicoEntidad documentoPublico) throws ExceptionDisparador {
        if (documentoPublico.getNombreEntePublico() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombreEntePúblico en la entidad documentoPúblico");
        }
    }

    private void validarNumeroOficioNulo(
            DocumentoPublicoEntidad documentoPublico) throws ExceptionDisparador {
        if (documentoPublico.getNumeroOficio() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor númeroOficio en la entidad documentoPúblico");
        }
    }

    private void validarApellidoNulo(DocumentoPublicoEntidad documentoPublico)
            throws ExceptionDisparador {
        if (documentoPublico.getApellidoDeclarante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor apellido en la entidad documentoPúblico");
        }
    }

    private void validarNombreNulo(DocumentoPublicoEntidad documentoPublico)
            throws ExceptionDisparador {
        if (documentoPublico.getNombreDeclarante() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombre en la entidad documentoPúblico");
        }
    }

    private void validarApellidoJuezNulo(
            DocumentoPublicoEntidad documentoPublico) throws ExceptionDisparador {
        if (documentoPublico.getApelidoJuez() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor apellido en la entidad documentoPúblico");
        }
    }

    private void validarNombreJuezNulo(DocumentoPublicoEntidad documentoPublico)
            throws ExceptionDisparador {
        if (documentoPublico.getNombreJuez() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el valor nombre en la entidad documentoPúblico");
        }
    }

    private void validarFechaElaboracionFormato(
            DocumentoPublicoEntidad documentoPublico) throws ExceptionDisparador {
        try {
            if (documentoPublico.getFechaElaboracion() != null) {
                SimpleDateFormat format = new SimpleDateFormat(
                        "dd/MM/yyyy hh:mm:ss");
                documentoPublico.setFechaElaboracion(format
                        .parse(documentoPublico.getFechaElaboracion()
                                .toString()));
            }
        } catch (ParseException ex) {
            throw new ExceptionDisparador("1#Error en la fecha fechaElaboración");
        }
    }
}
