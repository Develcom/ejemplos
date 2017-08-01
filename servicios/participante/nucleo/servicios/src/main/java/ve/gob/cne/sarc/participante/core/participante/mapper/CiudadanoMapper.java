package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;

/**
 * CiudadanoMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface CiudadanoMapper {

    /**
     * Metodo que mapea la entidad EcuEntidad al pojo Ecu
     *
     * @param ecuEntidad Objeto entidad de tipo EcuEntidad
     * @return Objeto de tipo Ecu
     */
    Ecu map(EcuEntidad ecuEntidad);

    /**
     * Metodo que mapea la entidad ComunidadIndigenaEntidad a String
     *
     * @param comunidadIndigenaEntidad Objeto entidad de tipo ComunidadIndigenaEntidad
     * @return String con el nombre de la comunidad indigena
     */
    String map(ComunidadIndigenaEntidad comunidadIndigenaEntidad);

    /**
     * Metodo que mapea la entidad NacionalidadEntidad a String
     *
     * @param nacionalidadEntidad Objeto entidad de tipo NacionalidadEntidad
     * @return String con el nombre de la nacionalidad
     */
    String map(NacionalidadEntidad nacionalidadEntidad);

    /**
     * Metodo que mapea la entidad CiudadanoEntidad al pojo Ciudadano
     *
     * @param ciudadanoEntidad Objeto entidad de tipo CiudadanoEntidad
     * @return Objeto de tipo Ciudadano
     */
    public Ciudadano entityToBO(CiudadanoEntidad ciudadanoEntidad);
}
