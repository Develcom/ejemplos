package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, 
    CiudadMapper.class})
public interface FuncionarioMapper {

    /**
     * Convierte una entidad en un pojo
     *
     * @param funcionarioEntidad entidad que contiene al Funcionario
     * @return Funcionario pojo que contiene al funcionario
     */
    @Mappings({
        @Mapping(source = "funcionarioEntidad.nacionalidad.nombre", target = "nacionalidad")})
    public Funcionario entityToBO(FuncionarioEntidad funcionarioEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param funcionariosEntidad lista de entidades de Funcionario 
     * @return List<Funcionario> lista de pojo de funcionarios
     */
    public List<Funcionario> entitiesToBOs(List<FuncionarioEntidad> funcionariosEntidad);
}
