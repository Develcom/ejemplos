package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroTipoOficinaEntidad;

/**
 * TipoLibroTipoOficinaDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoLibroTipoOficinaEntidad sean
 * correctos en cuanto a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class TipoLibroTipoOficinaDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TipoLibroTipoOficinaEntidad y llama a otros métodos privados
     * para validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoLibroTipoOficina objeto del modelo ontologico de Tipo libro tipo Oficina
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoLibroTipoOficina(
            TipoLibroTipoOficinaEntidad tipoLibroTipoOficina) throws ExceptionDisparador {
        validarTipoLibroNulo(tipoLibroTipoOficina);
        validarFechaInicioNulo(tipoLibroTipoOficina);
    }

    private void validarTipoLibroNulo(
            TipoLibroTipoOficinaEntidad tipoLibroTipoOficina) throws ExceptionDisparador {
        if (tipoLibroTipoOficina.getTipoLibro() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el TipoLibro correspondiente al TipoLibroTipoOficina");
        }
    }

    private void validarFechaInicioNulo(
            TipoLibroTipoOficinaEntidad tipoLibroTipoOficina) {
        if (tipoLibroTipoOficina.getFechaInicio() == null) {
            tipoLibroTipoOficina.setFechaInicio(new Date());
        }
    }

}
