package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;
import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;

/**
 * OcupacionMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface OcupacionMapper {

    /**
     * Metodo de mapeo de entidad OcupacionEntidad a pojo Ocupacion
     *
     * @param ocupacionEntidad Objeto entidad de tipo OcupacionEntidad
     * @return Objeto de tipo Ocupacion
     */
    Ocupacion entityToBO(OcupacionEntidad ocupacionEntidad);

    /**
     * Metodo de mapeo de una lista de entidad OcupacionEntidad a una lista de pojo Ocupacion
     *
     * @param ocupaciones Lista de objetos de tipo OcupacionEntidad
     * @return Lista de objetos de tipo Ocupacion
     */
    List<Ocupacion> entitiesToBOs(List<OcupacionEntidad> ocupaciones);
}
