package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * OREMapper.java
 *
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring")
public interface OREMapper {

    /**
     * Convierte una entidad a un pojo
     *
     * @param solicitudEntidad objeto entidad de tipo SolicitudEntidad
     * @return objeto de tipo Ore
     */
    @Mappings({
        @Mapping(source = "solicitudEntidad.numero", target = "numeroSolicitud"),
        @Mapping(source = "solicitudEntidad.directorOre.funcionario.primerNombre", target = "primerNombre"),
        @Mapping(source = "solicitudEntidad.directorOre.funcionario.segundoNombre", target = "segundoNombre"),
        @Mapping(source = "solicitudEntidad.directorOre.funcionario.primerApellido", target = "primerApellido"),
        @Mapping(source = "solicitudEntidad.directorOre.funcionario.segundoApellido", target = "segundoApellido"),
        @Mapping(source = "solicitudEntidad.directorOre.oficina.nombre", target = "oficinaOre"),
        @Mapping(source = "solicitudEntidad.directorOre.oficina.parroquia.municipio.estado.nombre", target = "estado")
    })
    Ore entityToBusiness(SolicitudEntidad solicitudEntidad);

    /**
     * Convierte un pojo a una entidad
     *
     * @param ore pojo con la informacion de ORE
     * @return OREEntidad Objeto de tipo Entidad
     */
    OficinaFuncionarioEntidad businessToEntity(Ore ore);

}
