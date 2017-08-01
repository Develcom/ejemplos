package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    
    /**
     * Convertir una entidad a un pojo
     * @param funcionarioEntidad FuncionarioEntidad entidad con la informacion de funcionario
     * @return Pojo Funcionario que contiene la informacion de Funcionario
     */
    @Mapping(target = "nacionalidad", ignore = true)
    Funcionario entityToBo(FuncionarioEntidad funcionarioEntidad);
    
    /**
     * Convertir un pojo a una entidad
     * @param funcionario Funcionario pojo con la informacion de funcionario
     * @return Objeto de tipo Entidad
     */
    @Mapping(target = "nacionalidad", ignore = true)
    FuncionarioEntidad boToEntity(Funcionario funcionario);
    
}
