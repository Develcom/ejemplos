package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.TramiteTipoParticipanteEntidad;

/**
 * TramiteTipoParticipanteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TramiteTipoParticipanteEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 */
public interface TramiteTipoParticipanteRepository
        extends CrudRepository<TramiteTipoParticipanteEntidad, Long> {

}
