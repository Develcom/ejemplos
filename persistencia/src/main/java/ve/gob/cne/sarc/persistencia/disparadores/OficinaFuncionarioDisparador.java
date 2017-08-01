package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.ArrayList;
import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.InscripcionMayor18Entidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * OficinaFuncionarioDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase OficinaFuncionarioEntidad sean correctos
 * en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 *
 */
public class OficinaFuncionarioDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad OficinaFuncionarioEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param oficinaFuncionario objeto del modelo ontologico de Oficina funcionario.
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesOficinaFuncionario(
            OficinaFuncionarioEntidad oficinaFuncionario) throws ExceptionDisparador {
        validarFuncionarioNulo(oficinaFuncionario);
        validarTipoFuncionarioNulo(oficinaFuncionario);
        validarOficinaNulo(oficinaFuncionario);
        validarEstatusNulo(oficinaFuncionario);
        validarFechaInicioNulo(oficinaFuncionario);
        validarSolicitudesNulo(oficinaFuncionario);
        validarActasNulo(oficinaFuncionario);
        validarInscripcionesMayor18Nulo(oficinaFuncionario);
        validarUsuarioNulo(oficinaFuncionario);
    }

    private void validarFuncionarioNulo(OficinaFuncionarioEntidad oficinaFuncionario)
            throws ExceptionDisparador {
        if (oficinaFuncionario.getFuncionario() == null) {
            throw new ExceptionDisparador("2#Debe introducir el "
                    + "funcionario correspondiente a la oficina funcionario");

        }
    }

    private void validarTipoFuncionarioNulo(OficinaFuncionarioEntidad oficinaFuncionario)
            throws ExceptionDisparador {
        if (oficinaFuncionario.getTipoFuncionario() == null) {
            throw new ExceptionDisparador("2#Debe indicar el tipo del funcionario asociado");
        }
    }

    private void validarOficinaNulo(OficinaFuncionarioEntidad oficinaFuncionario) throws ExceptionDisparador {
        if (oficinaFuncionario.getOficina() == null) {
            throw new ExceptionDisparador("2#Debe introducir la oficina"
                    + " correspondiente a la oficina funcionario");
        }
    }

    private void validarEstatusNulo(OficinaFuncionarioEntidad oficinaFuncionario) throws ExceptionDisparador {
        if (oficinaFuncionario.getOficFuncEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el dominio estatus"
                    + " correspondiente a la oficina funcionario");
        }
    }

    private void validarFechaInicioNulo(
            OficinaFuncionarioEntidad oficinaFuncionario) {
        if (oficinaFuncionario.getFechaInicio() == null) {
            oficinaFuncionario.setFechaInicio(new Date());
        }
    }

    private void validarSolicitudesNulo(
            OficinaFuncionarioEntidad oficinaFuncionario) {
        if (oficinaFuncionario.getSolicitudes() == null) {
            oficinaFuncionario
                    .setSolicitudes(new ArrayList<SolicitudEntidad>());
        }
    }

    private void validarActasNulo(OficinaFuncionarioEntidad oficinaFuncionario) {
        if (oficinaFuncionario.getActas() == null) {
            oficinaFuncionario.setActas(new ArrayList<ActaEntidad>());
        }
    }

    private void validarInscripcionesMayor18Nulo(
            OficinaFuncionarioEntidad oficinaFuncionario) {
        if (oficinaFuncionario.getInscripcionesMayor18() == null) {
            oficinaFuncionario
                    .setInscripcionesMayor18(new ArrayList<InscripcionMayor18Entidad>());
        }
    }

    private void validarUsuarioNulo(OficinaFuncionarioEntidad oficinaFuncionario) {
        if (oficinaFuncionario.getUsuarios() == null) {
            oficinaFuncionario.setUsuarios(new ArrayList<UsuarioEntidad>());
        }
    }

}
