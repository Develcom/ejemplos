package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.persistencia.entidades.InsercionEntidad;

/**
 * InsercionMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ParroquiaMapper.class)
public interface InsercionMapper {

    /**
     * Metodo que mapea la entidad InsercionEntidad al pojo Insercion
     *
     * @param insercionEntidad Objeto entidad de tipo InsercionEntidad
     * @return Objeto de tipo Insercion
     */
    Insercion entityToBo(InsercionEntidad insercionEntidad);

    /**
     * Metodo que mapea el pojo Insercion a la entidad InsercionEntidad
     *
     * @param insercion Objeto de tipo Insercion
     * @return Objeto entidad de tipo InsercionEntidad
     */
    InsercionEntidad boToEntity(Insercion insercion);

}
