package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.DeclaracionJuradaEntidad;

/**
 * DeclaracionJuradaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad DeclaracionJuradaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface DeclaracionJuradaRepository extends CrudRepository<DeclaracionJuradaEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudNumero que permite consultar la DeclaracionJurada por el numero
     * de solicitud
     * @author Oscar Montilla
     * @param numero String Numero de Solicitud.
     * @return List<DeclaracionJuradaEntidad> - Una lista de objetos del modelo ontologico que contiene la informacion
     * de DeclaracionJurada.
     */
    public List<DeclaracionJuradaEntidad> findBySolicitudNumero(@Param("numero") String numero);

}
