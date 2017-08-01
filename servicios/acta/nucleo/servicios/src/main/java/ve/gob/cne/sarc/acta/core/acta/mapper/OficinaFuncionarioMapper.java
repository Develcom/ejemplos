package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.oficina.OficinaFuncionario;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;

/**
 * OficinaFuncionarioMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 3/8/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring",
        uses = {OficinaMapper.class, FuncionarioMapper.class})
public interface OficinaFuncionarioMapper {
    /**
     * Metodo de mapeo de los objetos OficinaFuncionarioEntidad y
     * OficinaFuncionario
     *
     * @param oficinaFuncionarioEntidad Objeto de tipo OficinaFuncionario
     * @return objeto de tipo OficinaFuncionario
     */
    OficinaFuncionario boToEntity(OficinaFuncionarioEntidad oficinaFuncionarioEntidad);
    /**
     * Metodo de mapeo de los objetos OficinaFuncionarioEntidad y
     * OficinaFuncionario
     *
     * @param oficinaFuncionario Objeto de tipo OficinaFuncionario
     * @return objeto de tipo OficinaFuncionario
     */
    OficinaFuncionarioEntidad entityToBo(OficinaFuncionario oficinaFuncionario);
    
}
