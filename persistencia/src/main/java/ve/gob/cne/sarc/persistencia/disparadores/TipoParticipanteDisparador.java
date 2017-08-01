package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;

/**
 * TipoParticipanteDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoParticipanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class TipoParticipanteDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TipoParticipanteEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoParticipante Objeto del modelo ontologico de Tipo Participante
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoParticipante(
            TipoParticipanteEntidad tipoParticipante) throws ExceptionDisparador {

        validarNombreNulo(tipoParticipante);
        validarNombreVacio(tipoParticipante);
        validarFechaInicioNulo(tipoParticipante);
    }

    private void validarNombreNulo(TipoParticipanteEntidad tipoParticipante)
            throws ExceptionDisparador {
        if (tipoParticipante.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al tipo de participante");
        }
    }

    private void validarNombreVacio(TipoParticipanteEntidad tipoParticipante)
            throws ExceptionDisparador {
        if ("".equals(tipoParticipante.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al tipo de participante");
        }
    }

    private void validarFechaInicioNulo(TipoParticipanteEntidad tipoParticipante) {
        if (tipoParticipante.getFechaInicio() == null) {
            tipoParticipante.setFechaInicio(new Date());
        }
    }
}
