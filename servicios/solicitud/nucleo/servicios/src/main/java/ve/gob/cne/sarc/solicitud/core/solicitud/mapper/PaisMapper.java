package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;

/**
 * PaisMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = EstadoMapper.class)
public interface PaisMapper {

    /**
     * Convertir una entidad a un pojo
     * @param paisEntidad objeto de tipo PaisEntidad
     * @return objeto de tipo Pais
     */
    Pais entityToBO(PaisEntidad paisEntidad);

    /**
     * Convertir una lista entidad a una lista pojo
     * @param paiseLists lista de objetos de tipo PaisEntidad
     * @return lista de objetos de tipo Pais
     */
    List<Pais> entitiesToBOs(List<PaisEntidad> paiseLists);
}
