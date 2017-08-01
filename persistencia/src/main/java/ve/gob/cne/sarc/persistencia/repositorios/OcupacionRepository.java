package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;

/**
 * OcupacionRepository.java
 *
 * @descripcion Clase Repositorio de la entidad OcupacionEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface OcupacionRepository extends CrudRepository<OcupacionEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un CiudadEntidad
     * @param sort La variable para ordenar una Lista de Nacionalidad.
     * @return List<OcupacionEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Ocupacion.
     */
    List<OcupacionEntidad> findAll(Sort sort);

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorNombre que permite consultar los datos de un CiudadEntidad
     * @param nombre String Nombre del modelo Ontologico de Ocupacion.
     * @return List<CiudadEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Ocupacion.
     */
    public OcupacionEntidad findByNombreIgnoreCase(@Param("nombre") String nombre);

}
