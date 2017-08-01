package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.DireccionParticipanteEntidad;

/**
 * DireccionParticipanteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad DireccionParticipanteEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface DireccionParticipanteRepository extends CrudRepository<DireccionParticipanteEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar los datos de un DireccionParticipante por el id
     * @param id El id de registro de la tabla de Direccion Participante.
     * @return DireccionParticipante - Objeto del modelo ontologico que contiene la informacion de Direccion
     * Participante.
     *
     */
    public DireccionParticipanteEntidad findById(@Param("id") long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByDireccionUbicacion que permite consultar los datos de un
     * DireccionParticipante por el direccion
     * @param direccion La direccion de Participante.
     * @return DireccionParticipante - Objeto del modelo ontologico que contiene la informacion de Direccion
     * Participante.
     *
     */
    public DireccionParticipanteEntidad findByDireccionUbicacion(@Param("direccion") String direccion);

    /**
     *
     * @metodo Metodo de acceso a datos findByParticipanteNumeroDocIdentidad que permite consultar los datos de un
     * DireccionParticipante por el numero de participante
     * @param numero Numero de participante
     * @return DireccionParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de Direccion
     * participante
     */
    public DireccionParticipanteEntidad findByParticipanteNumeroDocIdentidad(@Param("numero") String numero);

    /**
     * @metodo Metodo de consulta findById por el numero id de Direccion Participante
     * @param id Long id de Direccion Participante
     * @return DireccionParticipanteEntidad
     */
        DireccionParticipanteEntidad findByParticipanteId(@Param("id") Long id);
}
