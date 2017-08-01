package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PoderMandatorioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;

/**
 * ActaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase ActaEntidad sean correctos en cuanto a
 * obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 06/09/2016
 */
public class ActaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad ActaEntidad y llama a otros métodos privados para validar
     * los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param acta objeto del modelo ontologico de acta
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesActa(ActaEntidad acta) throws ExceptionDisparador {
        validarEstatusNulo(acta);
        validarOficinaFuncionarioNulo(acta);
        validarSolicitudNulo(acta);
        validarNumeroActaNulo(acta);
        validarNumeroActaVacio(acta);
        validarFechaCreacionNulo(acta);
        validarParticipantesNulo(acta);
        validarProcedenciasNulo(acta);
        validarPoderesMandatariosNulo(acta);
    }

    private void validarEstatusNulo(ActaEntidad acta) throws ExceptionDisparador {
        if (acta.getEstatus() == null) {
            throw new ExceptionDisparador("2#Debe introducir el Estatus correspondiente al acta");
        }
    }

    private void validarOficinaFuncionarioNulo(ActaEntidad acta)
            throws ExceptionDisparador {
        if (acta.getOficinaFuncionario() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la oficinaFuncionario correspondiente al acta");
        }
    }

    private void validarSolicitudNulo(ActaEntidad acta) throws ExceptionDisparador {
        if (acta.getSolicitud() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir la solicitud correspondiente al acta");
        }
    }

    private void validarNumeroActaNulo(ActaEntidad acta) throws ExceptionDisparador {
        if (acta.getNumeroActa() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el numeroActa correspondiente al acta");
        }
    }

    private void validarNumeroActaVacio(ActaEntidad acta) throws ExceptionDisparador {
        if ("".equals(acta.getNumeroActa().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el numeroActa correspondiente al acta");
        }
    }

    private void validarFechaCreacionNulo(ActaEntidad acta) {
        if (acta.getFechaCreacion() == null) {
            acta.setFechaCreacion(new Date());
        }
    }

    private void validarParticipantesNulo(ActaEntidad acta) {
        if (acta.getParticipantes() == null) {
            acta.setParticipantes(new ArrayList<ParticipanteEntidad>());
        }
    }

    private void validarProcedenciasNulo(ActaEntidad acta) {
        if (acta.getProcedencias() == null) {
            acta.setProcedencias(new ArrayList<ProcedenciaEntidad>());
        }
    }

    private void validarPoderesMandatariosNulo(ActaEntidad acta) {
        if (acta.getPoderesMandatarios() == null) {
            acta.setPoderesMandatarios(new ArrayList<PoderMandatorioEntidad>());
        }
    }

}
