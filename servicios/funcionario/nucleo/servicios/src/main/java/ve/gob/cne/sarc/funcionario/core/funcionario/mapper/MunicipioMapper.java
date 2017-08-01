package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;


/**
 * MunicipioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {EstadoMapper.class, ParroquiaMapper.class})
public interface MunicipioMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param municipioEntidad entidad que contiene al municipio
     * @return Municipio pojo que contiene al Municipio
     */
    public Municipio entityToBO(MunicipioEntidad municipioEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param municipioList lista de entidades de municipios
     * @return List<Municipio> lista de pojo de municipios
     */
    public List<Municipio> entitiesToBOs(List<MunicipioEntidad> municipioList);
}
