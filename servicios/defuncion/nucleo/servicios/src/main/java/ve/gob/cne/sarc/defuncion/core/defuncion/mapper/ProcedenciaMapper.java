package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.acta.Procedencia;
import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;


/**
 * ProcedenciaMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring",
        uses = {MedidaProteccionMapper.class, DecisionJudicialMapper.class,
        ExtemporaneaMapper.class})
public interface ProcedenciaMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param procedenciaEntidads List<ProcedenciaEntidad> Lista de entidad con la informacion de Tabla Procedencia
     * @return Lista de pojo Procedencia
     */
    List<Procedencia> entitysToBos(List<ProcedenciaEntidad> procedenciaEntidads);
    
    /**
     * Convertir una entidad a un pojo
     * @param procedenciaEntidad ProcedenciaEntidad entidad con la informacion de Tbala Procedencia
     * @return Pojo Procedencia
     */
    Procedencia entityToBo(ProcedenciaEntidad procedenciaEntidad);
    
    /**
     * Convertir una lista de pojo a una lista entidad
     * @param procedencias List<Procedencia> lista de pojo procedencia
     * @return Lista de Objeto de tipo ProcedenciaEntidad
     */
    List<ProcedenciaEntidad> boToEntity(List<Procedencia> procedencias);
    
    /**
     * Convertir un pojo a una entidad
     * @param procedencia List<Procedencia> pojo con la informacion de procedencia
     * @return Objeto de tipo entidad
     */
    ProcedenciaEntidad boToEntity(Procedencia procedencia);
    
}
