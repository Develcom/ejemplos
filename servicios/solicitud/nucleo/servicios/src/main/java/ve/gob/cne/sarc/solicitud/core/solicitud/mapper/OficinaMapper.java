package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;

import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * OficinaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {FuncionarioMapper.class, DireccionMapper.class, TipoOficinaMapper.class})
public interface OficinaMapper {

    /**
     * Convertir una entidad a un pojo
     * @param oficinaEntidad objeto de tipo OficinaEntidad
     * @return objeto de tipo Oficina
     */
    public Oficina entityToBO(OficinaEntidad oficinaEntidad);

    /**
     * Convertir una lista entidad a un pojo
     * @param oficinasEntidad lista de objetos de tipo OficinaEntidad
     * @return lista de Objetos de tipo Oficina
     */
    public List<Oficina> entitiesToBOs(List<OficinaEntidad> oficinasEntidad);

    /**
     * Convertir un pojo a una entidad
     * @param tipoOficina objeto de tipo TipoOficina
     * @return objeto de TipoOficinaEntidad
     */
    TipoOficinaEntidad map(TipoOficina tipoOficina);

    /**
     * Convertir un pojo a una entidad
     * @param oficina objeto de tipo Oficina
     * @return objeto entidad de tipo OficinaEntidad
     */
    @Mapping(target = "direccion", ignore = true)
    OficinaEntidad boToEntity(Oficina oficina);
}
