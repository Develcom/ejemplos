package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;

import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * OficinaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {FuncionarioMapper.class,
    DireccionMapper.class, TipoOficinaMapper.class
})
public interface OficinaMapper {

    /**
     * Metodo de mapeo de entidad OficinaEntidad a pojo Oficina
     *
     * @param oficinaEntidad Objeto entidad de tipo OficinaEntidad
     * @return Objeto de tipo Oficina
     */
    public Oficina entityToBO(OficinaEntidad oficinaEntidad);

    /**
     * Metodo de mapeo de lista de entidad OficinaEntidad a una lista de pojo Oficina
     *
     * @param oficinasEntidad Lista de objetos de tipo OficinaEntidad
     * @return Lista de objetos de tipo Oficina
     */
    public List<Oficina> entitiesToBOs(List<OficinaEntidad> oficinasEntidad);

    /**
     * Metodo de mapeo de pojo TipoOficina a una entidad TipoOficinaEntidad
     *
     * @param tipoOficina Objeto de tipo TipoOficina
     * @return Objeto entidad de tipo TipoOficinaEntidad
     */
    TipoOficinaEntidad map(TipoOficina tipoOficina);

    /**
     * Metodo de mapeo de pojo Oficina a entidad OficinaEntidad
     *
     * @param oficina Objeto de tipo Oficina
     * @return Objeto entidad de tipo OficinaEntidad
     */
    @Mapping(target = "direccion", ignore = true)
    OficinaEntidad boToEntity(Oficina oficina);
}
