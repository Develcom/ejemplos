package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * OficinaMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring")
public interface OficinaMapper {

    /**
     * Convertir una entidad a un pojo
     *
     * @param tipoOficinaEntidad TipoOficinaEntidad entidad con la informacion de tipo oficina
     * @return Pojo TipoOficina
     */
    TipoOficina map(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Convertir una entidad a un pojo
     *
     * @param oficinaEntidad OficinaEntidad entidad con la informacion de la oficina
     * @return Pojo Oficina
     */
    @Mapping(target = "direccion", ignore = true)
    Oficina entityToBo(OficinaEntidad oficinaEntidad);

    /**
     * Convertir un pojo a una entidad
     *
     * @param tipoOficina TipoOficina pojo con la informacion de la oficina
     * @return Objeto de tipo entidad
     */
    TipoOficinaEntidad map(TipoOficina tipoOficina);

    /**
     * Convertir un pojo a una entidad
     *
     * @param oficina Oficina pojo con la informacion de la oficina
     * @return Objeto de tipo entidad
     */
    @Mapping(target = "direccion", ignore = true)
    OficinaEntidad boToEntity(Oficina oficina);

}
