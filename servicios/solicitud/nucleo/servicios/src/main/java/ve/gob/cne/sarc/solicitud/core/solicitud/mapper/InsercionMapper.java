package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;

/**
 * InsercionMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ParroquiaMapper.class)
public interface InsercionMapper {

    /**
     * Convertir una entidad a un pojo
     * @param insercionEntidad objeto de tipo InsercionEntidad
     * @return objeto de tipo Insercion
     */
    Insercion entityToBo(InsercionEntidad insercionEntidad);

    /**
     * Convertir un pojo a una entidad
     * @param insercion objeto de tipo Insercion
     * @return objeto de tipo InsercionEntidad
     */
    InsercionEntidad boToEntity(Insercion insercion);

}
