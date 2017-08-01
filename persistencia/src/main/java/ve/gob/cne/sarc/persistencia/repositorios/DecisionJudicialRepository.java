package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;

/**
 * DecisionJudicialRepository.java
 *
 * @descripcion Repositorio de la entidad DecisionJudicial
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface DecisionJudicialRepository extends CrudRepository<DecisionJudicialEntidad, Long> {

    /**
     *
     * @metodo Busca Decision Judicial por su Id
     * @param id Long el id de registro de la tabla de Decision Judicial.
     * @return DecisionJudicialEntidad - Objeto del modelo ontologico que contiene la informacion de Decision Judicial.
     */
    public DecisionJudicialEntidad findById(@Param("id") long id);

    /**
     *
     * @metodo Metodo de acceso a datos findByProcedenciaId que permite consultar los datos de un Decision Judicial.
     * @param id Long identificador de la tabla procedencia
     * @return DecisionJudicialEntidad - Objeto del modelo ontologico que contiene la informacion de Decision Judicial.
     */
    DecisionJudicialEntidad findByProcedenciaId(@Param("id") long id);

    /**
     * @metodo Metodo de acceso a datos findByProcedenciaActaNumeroActaAndProcedenciaTipoProcedenciaNombre que permite
     * consultar los datos de una Decision Judicial por el Numero de Acta y el tipo de procedencia.
     * @param numeroActa String Numero de Acta
     * @param id Long id consecutivo de tipo procedencia
     * @return - DecisionJudicialEntidad - Objeto del modelo ontologico que contiene la informacion de Decision
     * Judicial.
     */
    DecisionJudicialEntidad findByProcedenciaActaNumeroActaAndProcedenciaTipoProcedenciaId(
            @Param("numeroActa") String numeroActa, @Param("id") Long id);

}
