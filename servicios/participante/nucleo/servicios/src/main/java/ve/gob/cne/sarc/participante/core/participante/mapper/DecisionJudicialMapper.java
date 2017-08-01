package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;

/**
 * DecisionJudicialMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface DecisionJudicialMapper {

    /**
     * Metodo que mapea la entidad DecisionJudicialEntidad al pojo DecisionJudicial
     *
     * @param decisionJudicialEntidad Objeto entidad de tipo DecisionJudicialEntidad
     * @return Objeto de tipo DecisionJudicial
     */
    DecisionJudicial entityToBo(DecisionJudicialEntidad decisionJudicialEntidad);

    /**
     * Metodo que mapea el pojo DecisionJudicial a la entidad DecisionJudicialEntidad
     *
     * @param decisionJudicial Objeto de tipo DecisionJudicial
     * @return Objeto de tipo DecisionJudicialEntidad
     */
    DecisionJudicialEntidad boToEntity(DecisionJudicial decisionJudicial);

}
