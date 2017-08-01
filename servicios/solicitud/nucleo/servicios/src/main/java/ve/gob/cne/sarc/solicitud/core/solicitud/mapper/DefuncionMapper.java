package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;

/**
 * DefuncionMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParroquiaMapper.class, ActaMapper.class})
public interface DefuncionMapper {

    /**
     * Convierte una entidad a un pojo
     * @param defuncionEntidad objeto de tipo defuncionEntidad
     * @return objeto de tipo Defuncion
     */
    Defuncion entityToBo(DefuncionEntidad defuncionEntidad);
    /**
     * Convierte una entidad a String
     * @param parroquiaEntidad objeto entidad de tipo parroquia
     * @return entidad convertida a String
     */
    String parroquiaEntidadToString(ParroquiaEntidad parroquiaEntidad);
    /**
     * Convierte un string a una entidad
     * @param parroquia string que describe la parroquia
     * @return objeto entidad de tipo Parroquia
     */
    ParroquiaEntidad stringToParroquiaEntidad(String parroquia);
    /**
     * Convierte un pojo a una entidad
     * @param defuncion objeto de tipo Defuncion
     * @return objeto de tipo defuncionEntidad
     */
    DefuncionEntidad boToEntity(Defuncion defuncion);

}
