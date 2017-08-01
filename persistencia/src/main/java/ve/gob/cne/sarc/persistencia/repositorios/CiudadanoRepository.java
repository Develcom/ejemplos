package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;

/**
 * CiudadanoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad CiudadanoEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface CiudadanoRepository extends
        CrudRepository<CiudadanoEntidad, Long> {

    /**
     *
     * @metodo Busca un Ciudadano a partir del valor del documento de identificacion
     * @param numeroDocIdentidad String numeroDocIdentidad de documento de Ciudadano
     * @return CiudadanoEntidad - Objeto del modelo ontologico que contiene la informacion de Ciudadano.
     */
    public CiudadanoEntidad findByNumeroDocIdentidad(@Param("numeroDocIdentidad") String numeroDocIdentidad);

}
