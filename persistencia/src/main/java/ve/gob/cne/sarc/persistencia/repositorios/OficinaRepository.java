package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * Clase Repositorio de la entidad OficinaEntidad
 *
 * @version 1.0 12/09/2016
 * @author Oscar Montilla
 *
 */
public interface OficinaRepository extends CrudRepository<OficinaEntidad, Long> {

    /**
     * @Metodo Metodo para buscar una oficina por el ID geofrafico
     * @param geografico el ID del geografico
     * @return OficinaEntidad
     * @author carlos.castillo
     */
    public List<OficinaEntidad> buscarPorGeografico(@Param("ambito") int ambito, @Param("tipoOficina") TipoOficinaEntidad tipoOficina);

    /**
     * @Metodo Metodo para buscar una oficina por su ID
     * @param id ID de la oficina
     * @return OficinaEntidad
     * @author carlos.castillo
     *
     */
    public OficinaEntidad buscarPorId(@Param("id") Long id);

    /**
     * @Metodo Metodo para buscar todas las oficinas
     * @param sort
     * @return List<OficinaEntidad>
     * @author carlos.castillo
     */
    List<OficinaEntidad> findAll(Sort sort);

    /**
     *
     * @metodo Metodo de acceso a datos buscarOficinaOre que permite consultar todos los datos de un OficinaEntidad
     * @author Oscar Montilla
     * @param id Long id de Oficina.
     * @return OficinaEntidad - Objeto del modelo ontologico que contiene la informacion de Oficina.
     */
    OficinaEntidad buscarOficinaOre(@Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByOficinasFuncionariosSolicitudesNumero que permite consultar todos los
     * datos de un OficinaEntidad
     * @param numero String numero de solicitud
     * @return OficinaEntidad - Objeto del modelo ontologico que contiene la informacion de Oficina.
     */
    OficinaEntidad findByOficinasFuncionariosSolicitudesNumero(@Param("numero") String numero);

    /**
     * @metodo Metodo de acceso de datos findByNombre que permite consultar todos los datos de una ofiina por nombre
     * @param nombre String nombre de Oficina
     * @return OficinaEntidad - Objeto del modelo ontologico que contiene la informacion de Oficina.
     */
    OficinaEntidad findByNombre(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso de datos findByCodigo que permite consultar todos los datos de una oficina por el codigo
     * identificador
     * @param codigo String codigo identificador de la entidad Oficina
     * @return OficinaEntidad - Objeto del modelo ontologico que contiene la informacion de Oficina.
     */
    OficinaEntidad findByCodigo(@Param("codigo") String codigo);
}
