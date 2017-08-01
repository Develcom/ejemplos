package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;

/**
 * ActaOfMapper.java
 *
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 28/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring")
public interface ActaOfMapper {

    /**
     * Convierte una entidad aun pojo
     *
     * @param actaEntidad La entidad del Acta
     * @return Pojo Acta
     */
    @Mappings({
        @Mapping(source = "actaEntidad.estatus.nombre", target = "actaEstatus"),
        @Mapping(target = "defuncion", ignore = true),
        @Mapping(target = "solicitud", ignore = true),
        @Mapping(target = "oficinaFuncionario", ignore = true),
        @Mapping(target = "libro", ignore = true),
        @Mapping(target = "participantes", ignore = true),
        @Mapping(target = "procedencias", ignore = true),
        @Mapping(target = "insercion", ignore = true),
        @Mapping(target = "acceso", ignore = true)
    })
    Acta entityToBo(ActaEntidad actaEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     *
     * @param actaEntidads Listado de entidades del acta
     * @return Listado pojo Acta
     */
    List<Acta> entitysToBos(List<ActaEntidad> actaEntidads);

}
