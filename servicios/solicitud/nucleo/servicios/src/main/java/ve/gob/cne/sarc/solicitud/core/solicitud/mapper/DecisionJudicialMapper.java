package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;

/**
 * DecisionJudicialMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface DecisionJudicialMapper {

    /**
     * Convierte una entidad a un pojo
     * @param decisionJudicialEntidad Objeto entidad de tipo decisionJudicialEntidad
     * @return objeto de tipo DesicionJudicial
     */
    DecisionJudicial entityToBo(DecisionJudicialEntidad decisionJudicialEntidad);

    /**
     * Convierte un  pojo a una entidad
     * @param decisionJudicial objeto de tipo DecisionJudicial
     * @return  objeto Entidad de tipo DecisionJudicialEntidad
     */
    DecisionJudicialEntidad boToEntity(DecisionJudicial decisionJudicial);

}
