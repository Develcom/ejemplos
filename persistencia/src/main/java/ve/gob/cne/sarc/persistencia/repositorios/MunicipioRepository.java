package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;

/**
 * MunicipioRepository.java
 *
 * @descripcion Clase Repositorio de la entidad MunicipioEntidad
 * @author Oscar Montilla
 * @version: 1.0 08/09/2016
 */
public interface MunicipioRepository extends CrudRepository<MunicipioEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un MunicipioEntidad
     * @param sort La variable para ordenar una Lista de Comunidad Municipio.
     * @return<MunicipioEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Municipio.
     */
    List<MunicipioEntidad> findAll(Sort sort);

    /**
     *
     * @metodo Metodo de acceso a datos findByEstadoId que permite consultar los datos de un MunicipioEntidad
     * @param id Long de Municipio
     * @return List<MunicipioEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Municipio.
     */
    public List<MunicipioEntidad> findByEstadoId(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos buscarMunicipioEstadoPorId que permite consultar todos los datos de un
     * MunicipioEntidad
     * @param id Long id de la tabla municipio.
     * @return MunicipioEntidad - Objeto del modelo ontologico que contiene la informacion de Municipio.
     *
     */
    public String buscarMunicipioEstadoPorId(@Param("id") long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los datos de un EstadoEntidad
     * @param nombre String nombre de el Municipio
     * @return EstadoEntidad - Objeto del modelo ontologico que contiene la informacion de Municipio.
     */
    MunicipioEntidad findByNombre(@Param("nombre") String nombre);

    /**
     *
     * @metodo Metodo de acceso a datos findByParroquiasCodigoIn que permite consultar un Municipio que tenga registro
     * de una parroquia.
     * @param id Long codigo de Parroquia
     * @return MunicipioEntidad - Objeto del modelo ontologico que contiene la informacion de Municipio
     */
    MunicipioEntidad findByParroquiasIdIn(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar un Municipio por id de Municipio.
     * @param id Long codigo de Municipio
     * @return MunicipioEntidad - Objeto del modelo ontologico que contiene la informacion de Municipio.
     */
    MunicipioEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByEstadoIdOrderByNombreAsc que permite consultar un Municipio por id de
     * Estado.
     * @param id Long codigo de Municipio
     * @return List<MunicipioEntidad> - Objeto del modelo ontologico que contiene la informacion de Municipio
     */
    List<MunicipioEntidad> findByEstadoIdOrderByNombreAsc(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByParroquiasNombreIn que permite consultar un Municipio por el nombre de
     * parroquia
     * @param nombre String Nombre de Parroquia
     * @return MunicipioEntidad - Objeto del modelo ontologico que contiene la informacion de Municipio
     */
    MunicipioEntidad findByParroquiasNombreIn(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso a datos findByNombreAndEstadoNombre que permite consultar un Municipio por nombre de
     * municipio y nombre de estado
     *
     * @param nombreMunicipio String Nombre de Municipio
     * @param nombreEstado String Nombre de Estado
     * @return MunicipioEntidad - Objeto del modelo ontologico que contiene la informacion de Municipio.
     */
    MunicipioEntidad findByNombreAndEstadoNombre(
            @Param("nombreMunicipio") String nombreMunicipio, @Param("nombreEstado") String nombreEstado);

}
