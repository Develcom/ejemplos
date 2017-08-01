package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ParticipanteSinDocEntidad;

/**
 * ParticipanteSinDocRepository.java
 *
 * @descripcion Repositorio de la entidad ParticipanteSinDocEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface ParticipanteSinDocRepository extends CrudRepository<ParticipanteSinDocEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar los datos de un ParticipanteSinDocEntidad
     * @param id Integer id de ParticipanteSinDoc
     * @return ParticipanteSinDocEntidad - Objeto del modelo ontologico que contiene la informacion de
     * ParticipanteSinDoc.
     */
    public ParticipanteSinDocEntidad findById(@Param("id") long id);

}
