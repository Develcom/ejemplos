package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.persistencia.entidades.CartaConsejoComunalEntidad;

/**
 * CartaConsejoComunalMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParticipanteMapper.class})
public interface CartaConsejoComunalMapper {

    /**
     * Metodo de mapeo de la entidad CartaConsejoComunalEntidad al pojo CartaConsejoComunal
     *
     * @param cartaConsejoComunalEntidad Objeto con la informacion de la carta de consejo comunal de un participante
     * @return Informacion de la carta del consejo comunal de un participante
     */
    @Mappings({
        @Mapping(source = "cartaConsejoComunalEntidad.nombreConsejoComunal", target = "nombre"),
        @Mapping(source = "cartaConsejoComunalEntidad.rifConsejoComunal", target = "rif"),
        @Mapping(source = "cartaConsejoComunalEntidad.numeroTelefonico", target = "telefono"),
        @Mapping(source = "cartaConsejoComunalEntidad.fechaCarta", target = "fechaCarta"),
        @Mapping(target = "integrantes", ignore = true)
    })
    CartaConsejoComunal entityToBo(CartaConsejoComunalEntidad cartaConsejoComunalEntidad);

    /**
     * Metodo de mapeo de pojo CartaConsejoComunal a la entidad CartaConsejoComunalEntidad
     *
     * @param cartaConsejoComunal Objeto de tipo CartaConsejoComunal
     * @return Objeto entidad de tipo CartaConsejoComunalEntidad
     */
    @Mappings({
        @Mapping(source = "cartaConsejoComunal.nombre", target = "nombreConsejoComunal"),
        @Mapping(source = "cartaConsejoComunal.rif", target = "rifConsejoComunal")
    })
    CartaConsejoComunalEntidad boToEntity(CartaConsejoComunal cartaConsejoComunal);
}
