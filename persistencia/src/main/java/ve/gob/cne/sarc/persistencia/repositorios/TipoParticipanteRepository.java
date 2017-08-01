package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;

/**
 * TipoParticipanteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad TipoParticipanteEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 *
 */
public interface TipoParticipanteRepository
        extends CrudRepository<TipoParticipanteEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar todos los datos de un TipoParticipanteEntidad
     * @param id Long id de TipoParticipante
     * @return TipoParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de TipoParticipante.
     */
    public TipoParticipanteEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findByNombre que permite consultar todos los datos de un tipo participante por
     * codigo
     * @param codigo String codigo de tipo participante
     * @return TipoParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de TipoParticipante.
     */
    TipoParticipanteEntidad findByCodigo(@Param("codigo") String codigo);

}
