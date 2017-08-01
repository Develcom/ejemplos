package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Procedencia;
import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;

/**
 * ProcedenciaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring",
        uses = {MedidaProteccionMapper.class, DecisionJudicialMapper.class,
            ExtemporaneaMapper.class})
public interface ProcedenciaMapper {

    /**
     * Metodo de mapeo de una lista de entidad ProcedenciaEntidad a una lista de pojo Procedencia
     *
     * @param procedenciaEntidads Lista de objetos de tipo ProcedenciaEntidad
     * @return Lista de objetos de tipo Procedencia
     */
    List<Procedencia> entitysToBos(List<ProcedenciaEntidad> procedenciaEntidads);

    /**
     * Metodo de mapeo de entidad ProcedenciaEntidad a pojo Procedencia
     *
     * @param procedenciaEntidad Objeto entidad de tipo ProcedenciaEntidad
     * @return Objeto de tipo Procedencia
     */
    Procedencia entityToBo(ProcedenciaEntidad procedenciaEntidad);

    /**
     * Metodo de mapeo de una lista de pojo Procedencia a una lista de entidad ProcedenciaEntidad
     *
     * @param procedencias Lista de objetos de tipo Procedencia
     * @return Lista de objetos de tipo ProcedenciaEntidad
     */
    List<ProcedenciaEntidad> boToEntity(List<Procedencia> procedencias);

    /**
     * Metodo de mapeo de pojo Procedencia a entidad ProcedenciaEntidad
     *
     * @param procedencia Objeto de tipo Procedencia
     * @return Objeto entidad de tipo ProcedenciaEntidad
     */
    ProcedenciaEntidad boToEntity(Procedencia procedencia);

}
