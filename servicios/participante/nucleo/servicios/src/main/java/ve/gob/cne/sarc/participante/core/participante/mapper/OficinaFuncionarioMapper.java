package ve.gob.cne.sarc.participante.core.participante.mapper;

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
     * Metodo de mapeo de entidad OficinaFuncionarioEntidad a pojo OficinaFuncionario
     * @param oficinaFuncionarioEntidad Objeto entidad de tipo OficinaFuncionarioEntidad
     * @return Objeto de tipo OficinaFuncionario
     */
    OficinaFuncionario boToEntity(OficinaFuncionarioEntidad oficinaFuncionarioEntidad);
    
    /**
     * Metodo de mapeo de pojo OficinaFuncionario a entidad OficinaFuncionarioEntidad
     * @param oficinaFuncionario Objecto pojo de tipo OficinaFuncionario
     * @return Objecto de entidad de tipo OficinaFuncionarioEntidad
     */
    OficinaFuncionarioEntidad entityToBo(OficinaFuncionario oficinaFuncionario);
    
}
