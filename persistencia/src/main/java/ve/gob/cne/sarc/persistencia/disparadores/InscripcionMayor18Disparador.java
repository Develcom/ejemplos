package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.InscripcionMayor18Entidad;

/**
 * InscripcionMayor18Disparador.java
 *
 * @descripcin Esta clase se encarga de validar que los atributos de la clase InscripcionMayor18Entidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class InscripcionMayor18Disparador {

    /**
     *
     * Este método recibe como parámetro la entidad InscripcionMayor18Entidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     *
     *
     * @param inscripcionMayor18 Objeto del modelo ontologico que contiene la informacion de Inscripcion Mayor 18
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesInscripcionMayor18(
            InscripcionMayor18Entidad inscripcionMayor18) throws ExceptionDisparador {
        validarOficinaFuncionarioNulo(inscripcionMayor18);
        validarSolicitudNulo(inscripcionMayor18);
    }

    private void validarOficinaFuncionarioNulo(
            InscripcionMayor18Entidad inscripcionMayor18) throws ExceptionDisparador {
        if (inscripcionMayor18.getOficinaFuncionario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la oficina funcionario correspondiente a la inscripción mayor 18");
        }
    }

    private void validarSolicitudNulo(
            InscripcionMayor18Entidad inscripcionMayor18) throws ExceptionDisparador {
        if (inscripcionMayor18.getSolicitud() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la solicitud correspondiente a la inscripción mayor 18");
        }
    }
}
