package ve.gob.cne.sarc.libro.core.libro.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.persistencia.entidades.LibroEstatusEntidad;

/**
 * LibroEstatusMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class LibroEstatusMapper {

    /**
     * Convierte una entidad en un String
     *
     * @param libroEstatusEntidad Objeto entidad de tipo LibroEstatusEntidad
     * @return String que contiene el estatus de un libro
     */
    public String entityToBusiness(LibroEstatusEntidad libroEstatusEntidad) {
        return libroEstatusEntidad.getNombre();

    }

    /**
     * Convierte un String en una Entidad
     *
     * @param nombre String con el nombre del estatus del libro
     * @return Objeto entidad del tipo LibroestatusEntidad
     */
    public LibroEstatusEntidad entityToBusiness(String nombre) {
        LibroEstatusEntidad libroEstatusEntidad = new LibroEstatusEntidad();
        libroEstatusEntidad.setNombre(nombre);
        return libroEstatusEntidad;

    }

}
