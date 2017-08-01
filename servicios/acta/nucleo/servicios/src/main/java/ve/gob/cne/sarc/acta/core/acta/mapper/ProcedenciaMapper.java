package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Procedencia;
import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;

/**
 * ProcedenciaMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring",
        uses = {MedidaProteccionMapper.class, DecisionJudicialMapper.class,
            ExtemporaneaMapper.class})
public interface ProcedenciaMapper {

    /**
     * Metodo de mapeo de los objetos ProcedenciaEntidad y Procedencia
     *
     * @param procedenciaEntidad Objeto ProcedenciaEntidad
     * @return procedencia, instancia de objeto que contiene la informacion de
     * procedencia
     */
    Procedencia entityToBo(ProcedenciaEntidad procedenciaEntidad);

    /**
     * Metodo de mapeo de los objetos de tipo lista ProcedenciaEntidad y
     * Procedencia
     *
     * @param procedenciaEntidad lista de Objeto ProcedenciaEntidad
     * @return List<Procedencia>, lista de objeto que contiene la informacion de
     * procedencias
     */
    List<Procedencia> entitysToBos(List<ProcedenciaEntidad> procedenciaEntidad);

    /**
     * Metodo de mapeo de los objetos de tipo lista ProcedenciaEntidad y
     * Procedencia
     *
     * @param procedencias, lista de objeto que contiene la informacion de
     * procedencias
     * @return List<ProcedenciaEntidad> lista de Objeto ProcedenciaEntidad
     */
    List<ProcedenciaEntidad> boToEntitys(List<Procedencia> procedencias);

    /**
     * Metodo de mapeo de los objetos Procedencia y ProcedenciaEntidad
     *
     * @param procedencia instancia de objeto que contiene la informacion de
     * Procedencia
     * @return ProcedenciaEntidad Objeto ProcedenciaEntidad
     */
    ProcedenciaEntidad boToEntity(Procedencia procedencia);

}
