package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;


/**
 * CiudadanoMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface CiudadanoMapper {

    /**
     * Convierte una entidad a un pojo
     * @param ecuEntidad Objeto entidad de tipo EcuEntidad
     * @return Objeto de tipo Ecu
     */
    Ecu map(EcuEntidad ecuEntidad);

    /**
     * Convierte una entidad a un String
     * @param comunidadIndigenaEntidad Objeto entidad de tipo ComunidadIndigenaEntidad
     * @return String de ComunidadIndigena
     */
    String map(ComunidadIndigenaEntidad comunidadIndigenaEntidad);

    /**
     * Convierte una entidad a un String
     * @param nacionalidadEntidad Objeto entidad de tipo NacionalidadEntidad
     * @return String de Nacionalidad
     */
    String map(NacionalidadEntidad nacionalidadEntidad);

    /**
     * Convierte una entidad a un pojo
     * @param ciudadanoEntidad Objeto entidad de tipo CiudadanoEntidad
     * @return objeto de tipo Ciudadano
     */
    public Ciudadano entityToBO(CiudadanoEntidad ciudadanoEntidad);
}
