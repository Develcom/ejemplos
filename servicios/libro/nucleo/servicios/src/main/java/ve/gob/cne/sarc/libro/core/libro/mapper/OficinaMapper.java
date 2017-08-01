package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;

/**
 * OficinaMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {FuncionarioMapper.class,
    DireccionMapper.class, TipoOficinaMapper.class})
public interface OficinaMapper {

    /**
     * Convierte una entidad en un pojo
     * @param oficinaEntidad Objeto entidad de tipo OficinaEntidad
     * @return Objeto de tipo Oficina
     */
    Oficina entityToBusiness(OficinaEntidad oficinaEntidad);

    /**
     * Convierte una lista entidad en una lista pojo
     * @param oficinasEntidad Lista de objetos de tipo OficinaEntidad
     * @return Lista de objetos de tipo Oficina
     */
    List<Oficina> entitiesToBusinesses(List<OficinaEntidad> oficinasEntidad);

    /**
     * Convierte un pojo en una entidad
     * @param oficina Objeto de tipo oficina
     * @return Objeto entidad de tipo OficinaEntidad
     */
    @InheritInverseConfiguration
    OficinaEntidad businessToEntity(Oficina oficina);

    /**
     * Convierte una lista pojo en una lista entidad
     * @param oficinas Lista de objetos de tipo oficina
     * @return Lista de objetos de tipo OficinaEntidad
     */
    @InheritInverseConfiguration
    List<OficinaEntidad> businessesToEntities(List<Oficina> oficinas);

}
