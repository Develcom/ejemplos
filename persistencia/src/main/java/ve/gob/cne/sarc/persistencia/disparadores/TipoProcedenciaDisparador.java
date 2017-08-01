package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TipoProcedenciaEntidad;

/**
 * TipoProcedenciaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoParticipanteEntidad sean correctos en
 * cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.1 07/09/2016
 */
public class TipoProcedenciaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TipoProcedenciaEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoProcedencia Objeto del modelo ontologico de Tipo Procedencia
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoProcedencia(
            TipoProcedenciaEntidad tipoProcedencia) throws ExceptionDisparador {
        validarNombreNulo(tipoProcedencia);
        validarNombreVacio(tipoProcedencia);
        validarFechaInicioNulo(tipoProcedencia);
    }

    private void validarNombreNulo(TipoProcedenciaEntidad tipoProcedencia)
            throws ExceptionDisparador {
        if (tipoProcedencia.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el tipo de procedencia");
        }
    }

    private void validarNombreVacio(TipoProcedenciaEntidad tipoProcedencia)
            throws ExceptionDisparador {
        if ("".equals(tipoProcedencia.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique el tipo de procedencia");
        }
    }

    private void validarFechaInicioNulo(TipoProcedenciaEntidad tipoProcedencia) {
        if (tipoProcedencia.getFechaInicio() == null) {
            tipoProcedencia.setFechaInicio(new Date());
        }
    }
}
