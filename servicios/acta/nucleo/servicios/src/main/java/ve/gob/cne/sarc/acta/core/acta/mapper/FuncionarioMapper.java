package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    
    /**
     * Metodo de mapeo de los objetos FuncionarioEntidad y Funcionario
     *
     * @param funcionarioEntidad Objeto FuncionarioEntidad
     * @return Funcionario, instancia de objeto que contiene la informacion de
     * Funcionario
     */
    @Mapping(target = "nacionalidad", ignore = true)
    Funcionario entityToBo(FuncionarioEntidad funcionarioEntidad);
    
    /**
     * Metodo de mapeo de los objetos Funcionario y FuncionarioEntidad
     *
     * @param funcionario instancia de objeto que contiene la informacion de
     * Funcionario
     * @return funcionarioEntidad Objeto FuncionarioEntidad
     */
    @Mapping(target = "nacionalidad", ignore = true)
    FuncionarioEntidad boToEntity(Funcionario funcionario);
    
}
