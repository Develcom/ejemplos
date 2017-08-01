package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaModuloEntidad;

/**
 * TipoOficinaModuloRepository.java
 *
 * @descripcion Clase Repositorio de la entidad CategoriaEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoOficinaModuloRepository
        extends CrudRepository<TipoOficinaModuloEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorIdTipoOficinaModulo que permite consultar todos los de un
     * TipoOficinaModuloEntidad
     * @author Anabell De Faria
     * @param id Long id de TipoOficina.
     * @return List<TipoOficinaModuloEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * SolicitudEstatus.
     */
    List<TipoOficinaModuloEntidad>
            buscarPorIdTipoOficinaModulo(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByTipoOficina_IdOrderByModulo_IdAsc que permite consultar todos los de un
     * TipoOficinaModuloEntidad
     * @author Anabell De Faria
     * @param id Long id de TipoOficina.
     * @return <TipoOficinaModuloEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * SolicitudEstatus.
     */
    public List<TipoOficinaModuloEntidad>
            findByTipoOficina_IdOrderByModulo_IdAsc(Long id);

}
