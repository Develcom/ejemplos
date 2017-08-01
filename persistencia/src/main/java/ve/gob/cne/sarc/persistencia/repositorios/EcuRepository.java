package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;

/**
 * EcuRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EcuEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
public interface EcuRepository extends CrudRepository<EcuEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByNumeroDocumento que permite consultar los datos de un EcuEntidad
     * @param numeroDocIdentidad String numeroDocIdentidad Numero de Cedula de ciudadano
     * @return EcuEntidad - Objeto del modelo ontologico que contiene la informacion de Ecu.
     */
    public EcuEntidad findByNumeroDocumento(@Param("numeroDocIdentidad") String numeroDocIdentidad);

    /**
     *
     * @metodo Metodo de acceso a datos existsByCiudadanoNumeroDocIdentidad que permite consultar si existe un
     * EcuEntidad por el numero de cedula Ciudadano
     * @param numeroDocIdentidad String numeroDocIdentidad Numero de Cedula de ciudadano
     * @return boolean - Boolean (True= existe EcuEntidad, false= no existe EcuEntidad)
     */
    boolean existsEcuByCiudadanoNumeroDocIdentidad(@Param("numeroDocIdentidad") String numeroDocIdentidad);

    /**
     * @metodo Metodo de acceso a datos findByCiudadanoCodigoNUI que permite consultar los datos de EcuEntidad por el
     * Codigo Nui de Ciudadano
     * @param codigoNUI String Codigo Nui de Ciudadano
     * @return EcuEntidad - Objeto del modelo Ontologico que contiene la informacion de Ecu
     */
    public EcuEntidad findByCiudadanoCodigoNUI(@Param("codigoNUI") String codigoNUI);

}
