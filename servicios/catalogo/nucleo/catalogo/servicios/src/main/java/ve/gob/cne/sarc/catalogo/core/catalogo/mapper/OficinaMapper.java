package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
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
@Mapper(componentModel = "spring")
public interface OficinaMapper {

    /**
     * Metodo de mapeo de String a pojo
     *
     * @param direccion String que describe la direccion
     * @return objeto de tipo Direccion
     */
    Direccion map(String direccion);

    /**
     * Metodo de mapeo de los objetos tipoOficinaEntidad y TipoOficina
     *
     * @param tipoOficinaEntidad objeto de tipoOficina
     * @return objeto de tipo TipoOficina
     */
    TipoOficina map(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Metodo de mapeo de los objetos OficinaEntidacd y Oficina
     *
     * @param oficinaEntidad objeto entidad de tipo Oficina
     * @return objeto de tipo Oficina
     */
    Oficina entityToBo(OficinaEntidad oficinaEntidad);

    /**
     * Metodo de mapeo de las listas de OficinaEntidad y Oficina
     *
     * @param oficinaEntidad Lista de {@link oficinaEntidad}
     * @return Lista de {@link Oficinas}
     */
    List<Oficina> entitiesToBOs(List<OficinaEntidad> oficinaEntidad);

}