package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEcuEntidad;

/**
 * EstadoEcuRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EstadoEcuEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface EstadoEcuRepository extends CrudRepository<EstadoEcuEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar los datos de un EstadoEcu por el id
     * @param id Long findById de EstaduEcu
     * @return EstadoEcuEntidad - Objeto del modelo ontologico que contiene la informacion de EstadoEcu.
     */
    public EstadoEcuEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso findByNombreEstadoEcu que permite consultar los datos por
     * @param nombreEstadoEcu String nombre de nombreEstadoEcu
     * @return EstadoEcuEntidad - Objeto del modelo ontologico que contiene la informacion de EstadoEcu.
     */
    EstadoEcuEntidad findByNombreEstadoEcu(@Param("nombreEstadoEcu") String nombreEstadoEcu);
    
    
    
    

}
