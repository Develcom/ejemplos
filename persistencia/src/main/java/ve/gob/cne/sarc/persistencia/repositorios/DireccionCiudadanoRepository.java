package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.DireccionCiudadanoEntidad;

/**
 * DireccionCiudadanoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad DireccionCiudadanoEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
public interface DireccionCiudadanoRepository extends CrudRepository<DireccionCiudadanoEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findByCiudadano_Id que permite consultar todos los datos de un
     * DireccionCiudadanoEntidad
     * @param id Long id de Direccion
     * @return DireccionCiudadanoEntidad - Objeto del modelo ontologico que contiene la informacion de Direccion.
     */
    public DireccionCiudadanoEntidad findByCiudadano_Id(Long id);

}
