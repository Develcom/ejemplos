package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;

/**
 * DefuncionMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {ParroquiaMapper.class, ActaMapper.class})
public interface DefuncionMapper {

    /**
     * Metodo que mapea de entidad a pojo
     *
     * @param defuncionEntidad Objeto entidad de tipo DefuncionEntidad
     * @return Objeto de tipo Defuncion
     */
    Defuncion entityToBo(DefuncionEntidad defuncionEntidad);

    /**
     * Metodo que mapea un atributo especifico
     * @param parroquiaEntidad Objeto entidad de tipo ParroquiaEntidad
     * @return La cadena especifica
     */
    String parroquiaEntidadToString(ParroquiaEntidad parroquiaEntidad);
    
    /**
     * Metodo que mapea un atributo especifico
     * @param parroquia La parroquia
     * @return La entidad con el valor del atributo especifico
     */
    ParroquiaEntidad stringToParroquiaEntidad(String parroquia);
    /**
     * Metodo que mapea un pojo a una entidad
     *
     * @param defuncion Objeto de tipo Defuncion
     * @return Objeto entidad de tipo DefuncionEntidad
     */
    DefuncionEntidad boToEntity(Defuncion defuncion);

}
