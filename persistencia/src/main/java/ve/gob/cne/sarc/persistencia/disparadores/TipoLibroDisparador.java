package ve.gob.cne.sarc.persistencia.disparadores;

import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;

/**
 * TipoLibroDisparador.java
 *
 * @descripcion Esta clase se encarga de validar que los atributos de la clase TipoLibroEntidad sean correctos en cuanto
 * a obligatoriedad y formato.
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 *
 */
public class TipoLibroDisparador {

    /**
     *
     * @metodo Este método recibe como parámetro la entidad TipoLibroEntidad y llama a otros métodos privados para
     * validar los atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * @param tipoLibro objeto del modelo ontologico de Tipo Libro
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesTipoLibro(TipoLibroEntidad tipoLibro)
            throws ExceptionDisparador {
        validarNombreNulo(tipoLibro);
        validarNombreVacio(tipoLibro);
        validarFechaInicioNulo(tipoLibro);
    }

    private void validarNombreNulo(TipoLibroEntidad tipoLibro) throws ExceptionDisparador {
        if (tipoLibro.getNombre() == null) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al tipo de libro");
        }
    }

    private void validarNombreVacio(TipoLibroEntidad tipoLibro)
            throws ExceptionDisparador {
        if ("".equals(tipoLibro.getNombre().trim())) {
            throw new ExceptionDisparador(
                    "2#Debe introducir el nombre que identifique al tipo de libro");
        }
    }

    private void validarFechaInicioNulo(TipoLibroEntidad tipoLibro) {
        if (tipoLibro.getFechaInicio() == null) {
            tipoLibro.setFechaInicio(new Date());
        }
    }
}
