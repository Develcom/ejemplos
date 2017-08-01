package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;

/**
 * DefuncionMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring", uses = {ParroquiaMapper.class, ActaMapper.class})
public interface DefuncionMapper {
    
    /**
     * Metodo de mapeo de los objetos DefuncionEntidad y Defuncion
     *
     * @param defuncionEntidad Objeto DefuncionEntidad
     * @return Defuncion, instancia de objeto que contiene la informacion de la
     * defuncion
     */
    Defuncion entityToBo(DefuncionEntidad defuncionEntidad);
    /**
     * Metodo de mapeo de los objetos ParroquiaEntidad y Parroquia
     *
     * @param parroquiaEntidad Objeto ParroquiaEntidad
     * @return entidad convertida a String
     */
    String parroquiaEntidadToString(ParroquiaEntidad parroquiaEntidad);
    
    /**
     * Metodo de mapeo de los objetos ParroquiaEntidad y Parroquia
     *
     * @param parroquia String que describe el nombre de la Parroquia
     * @return objeto entidad de tipo Parroquia
     */   
    ParroquiaEntidad stringToParroquiaEntidad(String parroquia);
    /**
     * Metodo de mapeo de los objetos Defuncion y DefuncionEntidad
     *
     * @param defuncion instancia de objeto que contiene la informacion de la
     * Defuncion
     * @return defuncionEntidad Objeto DefuncionEntidad
     */
    DefuncionEntidad boToEntity(Defuncion defuncion);
    
}
