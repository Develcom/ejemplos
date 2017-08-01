package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.joda.time.DateTime;

import ve.gob.cne.sarc.persistencia.entidades.InhumacionYCremacionEntidad;

/**
 * InhumacionYCremacionDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase InhumacionYCremacionEntidad sean
 * correctos en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class InhumacionYCremacionDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad InhumacionYCremacionEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param inhumacion objeto del modelo ontologico de Inhumacion y cremacion
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesInhumacionYCremacion(InhumacionYCremacionEntidad inhumacion)
            throws ExceptionDisparador {
        validarPrimerNombreNulo(inhumacion);
        validarPrimerApellido(inhumacion);
        validarNombreCementerio(inhumacion);
        validarFechaDefuncion(inhumacion);
        validarNumeroCertificado(inhumacion);
        validarDireccion(inhumacion);

    }

    private void validarPrimerNombreNulo(InhumacionYCremacionEntidad inhumacion)
            throws ExceptionDisparador {
        if (inhumacion.getPrimerNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el primero nombre del autorizado");
        }
    }

    private void validarPrimerApellido(InhumacionYCremacionEntidad inhumacion)
            throws ExceptionDisparador {
        if (inhumacion.getPrimerApellido() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el primer Apellido del autorizado");
        }
    }

    private void validarNombreCementerio(InhumacionYCremacionEntidad inhumacion)
            throws ExceptionDisparador {
        if (inhumacion.getNombreCementerio() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre del cementerio");
        }
    }

    private void validarFechaDefuncion(InhumacionYCremacionEntidad inhumacion) {
        if (inhumacion.getFechaDefuncion() == null) {
            inhumacion.setFechaDefuncion(new DateTime());
        }
    }

    private void validarNumeroCertificado(InhumacionYCremacionEntidad inhumacion)
            throws ExceptionDisparador {
        if (inhumacion.getNumeroCertificado() == 0) {
            throw new ExceptionDisparador(
                    "2#Debe indicar el número del certificado de defunción");
        }
    }

    private void validarDireccion(InhumacionYCremacionEntidad inhumacion)
            throws ExceptionDisparador {
        if (inhumacion.getDireccion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la direccion");
        }
    }

}
