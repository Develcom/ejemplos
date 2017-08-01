package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface FuncionarioMapper {

    /**
     * Convierte una entidad a un pojo
     * @param funcionarioEntidad objeto de tipo FuncionarioEntidad
     * @return objeto de tipoFuncionario
     */
    @Mappings({
        @Mapping(source = "funcionarioEntidad.nacionalidad.nombre", target = "nacionalidad")})
    public Funcionario entityToBO(FuncionarioEntidad funcionarioEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param funcionariosEntidad lista de objeto de tipo FuncionarioEntidad
     * @return lista de objetos de tipo Funcionario
     */
    public List<Funcionario> entitiesToBOs(List<FuncionarioEntidad> funcionariosEntidad);

    /**
     * Convierte un pojo a una entidad
     * @param funcionario objeto de tipo Funcionario
     * @return objeto de tipo FuncionarioEntidad
     */
    @Mapping(target = "nacionalidad", ignore = true)
    FuncionarioEntidad boToEntity(Funcionario funcionario);
}
