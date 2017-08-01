package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;

/**
 * InsercionMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 03/8/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring", uses = ParroquiaMapper.class)
public interface InsercionMapper {

    /**
     * Metodo de mapeo de los objetos InsercionEntidad y Insercion
     *
     * @param insercionEntidad Objeto InsercionEntidad
     * @return Insercion, instancia de objeto que contiene la informacion de
     * Insercion
     */
    @Mappings({
        @Mapping(source = "insercionEntidad.acta.numeroActa", target = "numeroActa"),})
    Insercion entityToBo(InsercionEntidad insercionEntidad);

    /**
     * Metodo de mapeo de los objetos Insercion y InsercionEntidad
     *
     * @param insercion instancia de objeto que contiene la informacion de
     * Insercion
     * @return insercionEntidad Objeto InsercionEntidad
     */
    InsercionEntidad boToEntity(Insercion insercion);

}
