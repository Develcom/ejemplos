package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ConsecutivoOficinaEntidad;

/**
 * ConsecutivoOficinaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ConsecutivoOficinaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
public interface ConsecutivoOficinaRepository extends CrudRepository<ConsecutivoOficinaEntidad, Long> {

    /**
     * @metodo Metodo de acceso a datos obtenerConsecutivoPorOficina que permite consultar los datos de un
     * ConsecutivoOficinaEntidad
     * @param oficina String oficina de Cosecutivo Oficina
     * @return Long - Obtiene una variable tipo Long
     */
//    @Procedure
//    Long obtenerConsecutivoPorOficina(@Param("oficina") Long oficina);
}
