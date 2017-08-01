package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;

/**
 * EstadoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EstadoEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface EstadoRepository extends CrudRepository<EstadoEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar todos los datos de un EstadoEntidad
     * @param id long Id del estado.
     * @return EstadoEntidad - Objeto del modelo ontologico que contiene la informacion de Estado.
     */
    public EstadoEntidad findById(@Param("id") long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByPaisOrderByNombreAsc que permite consultar todos los datos de un
     * EstadoEntidad
     * @param sort La variable para ordenar una Lista del Estado.
     * @return List<EstadoEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Estado.
     */
    List<EstadoEntidad> findAll(Sort sort);

    /**
     *
     * @metodo Metodo de acceso a datos findByPaisOrderByNombreAsc que permite consultar todos los datos de un
     * EstadoEntidad
     * @param id Long id del estado.
     * @return List<EstadoEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Estado.
     */
    public List<EstadoEntidad> findByPaisIdOrderByNombreAsc(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los datos de un EstadoEntidad
     * @param nombre String nombre de el Estado
     * @return EstadoEntidad - Objeto del modelo ontologico que contiene la informacion de Estado.
     */
    EstadoEntidad findByNombre(@Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findByMunicipiosIdIn que permite consultar todos los datos de un EstadoEntidad
     * @param id Long de Municipio
     * @return EstadoEntidad - Objeto del modelo ontologico que contiene la informacion de Estado.
     */
    EstadoEntidad findByMunicipiosIdIn(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByMunicipiosIdIn que permite consultar todos los datos de un EstadoEntidad
     * por el nombre de municipio
     * @param nombre String Nombre de Municipio
     * @return EstadoEntidad - Objeto del modelo ontologico que contiene la informacion de Estado.
     */
    EstadoEntidad findByMunicipiosNombreIn(@Param("nombre") String nombre);

}
