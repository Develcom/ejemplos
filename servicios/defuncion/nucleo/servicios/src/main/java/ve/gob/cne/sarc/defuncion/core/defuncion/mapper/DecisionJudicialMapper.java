package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;

/**
 * DecisionJudicialMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de DecisionJudicialEntidad en
 * instancias de DecisionJudicial y viceversa]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface DecisionJudicialMapper {

    /**
     * Convierte una entidad a un pojo
     * @param decisionJudicialEntidad DecisionJudicialEntidad entidad con la informacion de la decision judicial
     * @return Pojo DecisionJudicial
     */
    DecisionJudicial entityToBo (DecisionJudicialEntidad decisionJudicialEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param decisionJudicial DecisionJudicial pojo con la informacion de la decision judicial
     * @return Objeto de tipo Entidad
     */
    DecisionJudicialEntidad boToEntity(DecisionJudicial decisionJudicial);
    
}
