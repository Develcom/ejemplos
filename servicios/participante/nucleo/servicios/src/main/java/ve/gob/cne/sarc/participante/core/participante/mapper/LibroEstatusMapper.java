package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.persistencia.entidades.LibroEstatusEntidad;

/**
 * LibroEstatusMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class LibroEstatusMapper {

    /**
     * Metodo de mapeo de entidad LibroEstatusEntidad a String
     *
     * @param libroEstatusEntidad Objeto de tipo LibroEstatusEntidad
     * @return String con el estatus del libro
     */
    public String libroEstatusTOEstatus(LibroEstatusEntidad libroEstatusEntidad) {
        return libroEstatusEntidad.getNombre();
    }

}
