package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.oficina.OficinaFuncionario;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;

/**
 *
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring",
        uses = {OficinaMapper.class, FuncionarioMapper.class})
public interface OficinaFuncionarioMapper {
    
     /**
     * Convierte una entidad a un objeto
     *
     * @param oficinaFuncionarioEntidad objeto de tipo OficinaFuncionarioEntidad
     * @return objeto de tipo OficinaFuncionario
     */
    OficinaFuncionario boToEntity(OficinaFuncionarioEntidad oficinaFuncionarioEntidad);
    
     /**
     * Convierte un objeto a una entidad
     *
     * @param oficinaFuncionario objeto de tipo OficinaFuncionario
     * @return objeto entidad de tipo OficinaFuncionario
     */
    OficinaFuncionarioEntidad entityToBo(OficinaFuncionario oficinaFuncionario);
    
}
