package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.EcuParticipanteActaEntidad;

/**
 * EcuParticipanteActaRepository.java
 *
 * @descripcion Clase Repositorio de la entidad EcuParticipanteActaEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface EcuParticipanteActaRepository extends CrudRepository<EcuParticipanteActaEntidad, Long> {

    /**
     * @metodo Metodo que busca una lista de EcuParticipanteActaEntidad basado en el identificador del participante
     * @param id Identificador del participante
     * @return Lista de EcuParticipanteActaEntidad
     */
    List<EcuParticipanteActaEntidad> findByParticipanteId(@Param("id") long id);

    /**
     * @metodo Metodo que busca una lista de EcuParticipanteActaEntidad basado en el identificador del ecu
     * @param id Identificador del ecu
     * @return Lista de EcuParticipanteActaEntidad
     */
    List<EcuParticipanteActaEntidad> findByEcuId(@Param("id") long id);

    /**
     * @metodo Metodo que busca una lista de EcuParticipanteActaEntidad basado en el identificador del acta
     * @param id Identificador del acta
     * @return Lista de EcuParticipanteActaEntidad
     */
    List<EcuParticipanteActaEntidad> findByActaId(@Param("id") long id);

}
