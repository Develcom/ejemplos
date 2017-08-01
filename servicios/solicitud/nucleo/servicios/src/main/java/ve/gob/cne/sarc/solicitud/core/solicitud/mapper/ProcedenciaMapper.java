package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Procedencia;
import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;

/**
 * ProcedenciaMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring",
    uses = {MedidaProteccionMapper.class, DecisionJudicialMapper.class,
        ExtemporaneaMapper.class})
public interface ProcedenciaMapper {

    /**
     * Convertir una lista entidad a una lista pojo
     * @param procedenciaEntidads lista de objetos de Tipo ProcedenciaEntidad
     * @return lista de objetos tipo Procedencia
     */
    List<Procedencia> entitysToBos(List<ProcedenciaEntidad> procedenciaEntidads);

    /**
     * Convertir una entidad a un pojo
     * @param procedenciaEntidad objeto entidad de tipo ProcedenciaEntidad
     * @return objeto de tipo Procedencia
     */
    Procedencia entityToBo(ProcedenciaEntidad procedenciaEntidad);

    /**
     * Convertir una lista pojo a una lista entidad
     * @param procedencias lista de objetos de tipo Procedencia
     * @return Lista de objetos de tipo ProcedenciaEntidad
     */
    List<ProcedenciaEntidad> boToEntity(List<Procedencia> procedencias);

    /**
     * Convertir un pojo a una entidad
     * @param procedencia objeto de tipo Procedencia
     * @return objeto entidad de tipo ProcedenciaEntidad
     */
    ProcedenciaEntidad boToEntity(Procedencia procedencia);

}
