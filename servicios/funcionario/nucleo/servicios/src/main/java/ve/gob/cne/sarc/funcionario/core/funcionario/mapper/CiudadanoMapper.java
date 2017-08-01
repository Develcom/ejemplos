package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;

/**
 * CiudadanoMapper.java
 * @descripcion [ Clase mapper para realizar el mapeo entre Las clase del modelo
 *  ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface CiudadanoMapper {

    /**
     * Realiza el map de una entidad a un BO
     *
     * @param ecuEntidad ecuEntidad que contiene la informacion de Ecu
     * @return Ecu pojo que contiene la informacion de Ecu
     */
    Ecu map(EcuEntidad ecuEntidad);

    /**
     * Realiza el map de una entidad a un BO
     *
     * @param comunidadIndigenaEntidad entidad que contiene la informacion de ComunidadIndigena
     * @return String que describe a la entidad comunidad Indigena
     */
    String map(ComunidadIndigenaEntidad comunidadIndigenaEntidad);

    /**
     * Realiza el map de una entidad a un BO
     *
     * @param nacionalidadEntidad entidad que describe al entidad nacionalidadEntidad
     * @return String que describe a la entidad nacionalidad
     */
    String map(NacionalidadEntidad nacionalidadEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param ciudadanoEntidad entidad que describe la entidad Ciudadano
     * @return Ciudadano pojo con la informacion de Ciudadano
     */
    public Ciudadano entityToBO(CiudadanoEntidad ciudadanoEntidad);
}
