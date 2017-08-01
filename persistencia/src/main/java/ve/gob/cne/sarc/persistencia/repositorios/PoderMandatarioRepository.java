package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.PoderMandatorioEntidad;

/**
 * PoderMandatarioRepository.java
 *
 * @descripcion Repositorio de la entidad DecisionJudicial
 * @author Oscar Montilla
 * @version 1.0 05/11/2016
 *
 */
public interface PoderMandatarioRepository
        extends CrudRepository<PoderMandatorioEntidad, Long> {

    /**
     * @metodo Metodo de acceso a los datos findById el cual busca un Poder Mandatario por id
     * @param id Long Id consecutivo de poder mandatario
     * @return PoderMandatorioEntidad - Objeto del modelo ontologico que contiene la informacion de Poder Mandatario.
     */
    public PoderMandatorioEntidad findById(@Param("id") long id);
}
