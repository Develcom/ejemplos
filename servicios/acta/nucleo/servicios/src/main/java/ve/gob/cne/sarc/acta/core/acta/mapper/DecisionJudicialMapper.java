package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.persistencia.entidades.DecisionJudicialEntidad;

/**
 * DecisionJudicialMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface DecisionJudicialMapper {

    /**
     * Metodo de mapeo de los objetos DecisionJudicialEntidad y DecisionJudicial
     *
     * @param decisionJudicialEntidad Objeto DecisionJudicialEntidad
     * @return DecisionJudicial, instancia de objeto que contiene la informacion
     * de la Decision Judicial
     */
    @Mappings({
        @Mapping(source = "decisionJudicialEntidad.procedencia.textoExtracto", target = "extractoProcedencia"),
        @Mapping(source = "decisionJudicialEntidad.procedencia.acta.numeroActa", target = "numeroActa"),
        @Mapping(source = "decisionJudicialEntidad.nombreTribunal", target = "nombreTribunal"),
        @Mapping(source = "decisionJudicialEntidad.numeroSentencia", target = "numeroSentencia"),
        @Mapping(source = "decisionJudicialEntidad.cedulaJuez", target = "cedulaJuez"),
        @Mapping(source = "decisionJudicialEntidad.primerNombreJuez", target = "primerNombreJuez"),
        @Mapping(source = "decisionJudicialEntidad.segundoNombreJuez", target = "segundoNombreJuez"),
        @Mapping(source = "decisionJudicialEntidad.primerApellidoJuez", target = "primerApellidoJuez"),
        @Mapping(source = "decisionJudicialEntidad.segundoApellidoJuez", target = "segundoApellidoJuez"),
        @Mapping(source = "decisionJudicialEntidad.fechaSentencia", target = "fechaSentencia")

    })
    DecisionJudicial entityToBo(DecisionJudicialEntidad decisionJudicialEntidad);

    /**
     * Metodo de mapeo de los objetos DecisionJudicial y DecisionJudicialEntidad
     *
     * @param decisionJudicial instancia de objeto que contiene la informacion
     * de la Decision Judicial
     * @return decisionJudicialEntidad Objeto DecisionJudicialEntidad
     */
    DecisionJudicialEntidad boToEntity(DecisionJudicial decisionJudicial);

}
