package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.TipoFuncionario;
import ve.gob.cne.sarc.persistencia.entidades.TipoFuncionarioEntidad;

/**
 * TipoFuncionarioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoFuncionarioMapper {

    /**
     * Convierte una entidad a un pojo
     * @param tipoFuncionarioEntidad objeto de tipo TipoFuncionarioEntidad
     * @return objeto de tipo TipoFuncionario
     */
    public TipoFuncionario entityToBO(TipoFuncionarioEntidad tipoFuncionarioEntidad);

    /**
     * Convierte una entidad a un pojo
     * @param tipoFuncionariosList lista de tipo TipoFuncionarioEntidad
     * @return lista de objetos de tipo TipoFuncionario
     */
    public List<TipoFuncionario> entitiesToBOs(List<TipoFuncionarioEntidad> tipoFuncionariosList);
}
