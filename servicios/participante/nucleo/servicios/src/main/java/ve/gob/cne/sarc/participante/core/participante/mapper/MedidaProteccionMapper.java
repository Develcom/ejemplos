package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;

/**
 * MedidaProteccionMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface MedidaProteccionMapper {

    /**
     * Metodo de mapeo de la entidad MedidaProteccionEntidad al pojo MedidaProteccion
     *
     * @param medidaProteccionEntidad Objeto entidad de tipo MedidaProteccionEntidad
     * @return Objeto de tipo MedidaProteccion
     */
    MedidaProteccion entityToBo(MedidaProteccionEntidad medidaProteccionEntidad);

    /**
     * Metodo de mapeo del pojo MedidaProteccion a la entidad MedidaProteccionEntidad
     *
     * @param medidaProteccion Objeto de tipo MedidaProteccion
     * @return Objeto entidad de tipo MedidaProteccionEntidad
     */
    MedidaProteccionEntidad boToEntity(MedidaProteccion medidaProteccion);

}
