package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaModuloEntidad;

/**
 * TipoOficinaModuloDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoOficinaModuloEntidadOld sean
 * correctos en cuanto a obligatoriedad y formato.
 * @version 1.0 12/08/2016
 * @author Oscar Montilla
 *
 */
public class TipoOficinaModuloDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TipoOficinaModuloEntidadOld y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoOficinaModulo Objeto del modelo ontologico e Tipo Oficina Modulo
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoOficinaModulo(
            TipoOficinaModuloEntidad tipoOficinaModulo) throws ExceptionDisparador {
        validarModuloNulo(tipoOficinaModulo);
        validarTipoOficinaNulo(tipoOficinaModulo);
        validarFechaInicioNulo(tipoOficinaModulo);
    }

    private void validarModuloNulo(TipoOficinaModuloEntidad tipoOficinaModulo)
            throws ExceptionDisparador {
        if (tipoOficinaModulo.getModulo() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el módulo correspondiente al tipo de oficina del módulo");
        }
    }

    private void validarTipoOficinaNulo(
            TipoOficinaModuloEntidad tipoOficinaModulo) throws ExceptionDisparador {
        if (tipoOficinaModulo.getTipoOficina() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el tipo de oficina correspondiente al tipo de oficina del módulo");
        }
    }

    private void validarFechaInicioNulo(
            TipoOficinaModuloEntidad tipoOficinaModulo) {
        if (tipoOficinaModulo.getFechaInicio() == null) {
            tipoOficinaModulo.setFechaInicio(new Date());
        }
    }

}
