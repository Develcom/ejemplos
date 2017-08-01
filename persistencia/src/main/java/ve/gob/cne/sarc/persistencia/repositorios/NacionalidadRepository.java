package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;

/**
 * NacionalidadRepository.java
 *
 * @descripcion Clase Repositorio de la entidad NacionalidadEntidad
 * @version 1.0 12/08/2016
 * @author Anabell De Faria
 *
 */
public interface NacionalidadRepository extends CrudRepository<NacionalidadEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findAll que permite consultar todos los datos de un NacionalidadEntidad
     * @author Oscar Montilla
     * @param sort La variable para ordenar una Lista de Nacionalidad.
     * @return List<NacionalidadEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * Nacionalidad.
     */
    List<NacionalidadEntidad> findAll(Sort sort);

    /**
     *
     * @metodo Metodo de acceso a datos BUSCAR_POR_NOMBRE que permite consultar los datos de un DireccionParticipante
     * por el nombre
     * @author Oscar Montilla
     * @date 15 Mar 2016
     * @param nombre String nombre de la nacionaslidad.
     * @return NacionalidadEntidad - Lista de Objetos del modelo ontologico que contiene la informacion de Nacionalidad.
     *
     */
    public NacionalidadEntidad buscarPorNombre(@Param("nombre") String nombre);

}
