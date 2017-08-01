package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * OficinaMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring")
public interface OficinaMapper {
    
    /**
     * Metodo de mapeo de los objetos TipoOficinaEntidad y TipoOficina
     *
     * @param tipoOficinaEntidad Objeto TipoOficinaEntidad
     * @return TipoOficina, instancia de objeto que contiene la informacion de
     * TipoOficina
     */
    TipoOficina map(TipoOficinaEntidad tipoOficinaEntidad);
    
    /**
     * Metodo de mapeo de los objetos OficinaEntidad y Oficina
     *
     * @param oficinaEntidad Objeto OficinaEntidad
     * @return oficina, instancia de objeto que contiene la informacion de
     * Oficina
     */
    @Mapping(target = "direccion", ignore = true)
    Oficina entityToBo(OficinaEntidad oficinaEntidad);
    
    /**
     * Metodo de mapeo de los objetos TipoOficina y TipoOficinaEntidad
     *
     * @param tipoOficina instancia de objeto que contiene la informacion de
     * TipoOficina
     * @return tipoOficinaEntidad Objeto TipoOficinaEntidad
     */
    TipoOficinaEntidad map(TipoOficina tipoOficina);
    
    /**
     * Metodo de mapeo de los objetos Oficina y OficinaEntidad
     *
     * @param oficina instancia de objeto que contiene la informacion de Oficina
     * @return OficinaEntidad Objeto OficinaEntidad
     */
    @Mapping(target = "direccion", ignore = true)
    OficinaEntidad boToEntity(Oficina oficina);
    
}
