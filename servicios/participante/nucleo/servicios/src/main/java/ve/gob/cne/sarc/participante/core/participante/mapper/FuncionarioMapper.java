package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface FuncionarioMapper {

    /**
     * Metodo que mapea la entidad FuncionarioEntidad al pojo Funcionario
     *
     * @param funcionarioEntidad Objeto entidad de tipo FuncionarioEntidad
     * @return Objeto de tipo Funcionario
     */
    @Mappings({
        @Mapping(source = "funcionarioEntidad.nacionalidad.nombre", target = "nacionalidad")})
    public Funcionario entityToBO(FuncionarioEntidad funcionarioEntidad);

    /**
     * Metodo que mapea una lista funcionario Entidad a una lista pojo funcionario
     * @param funcionariosEntidad La entidad
     * @return una lista de funcionario
     */
    public List<Funcionario> entitiesToBOs(List<FuncionarioEntidad> funcionariosEntidad);

    /**
     * Metodo que mapea el pojo Funcionario a la entidad FuncionarioEntidad
     *
     * @param funcionario Objeto de tipo Funcionario
     * @return Objeto entidad de tipo FuncionarioEntidad
     */
    @Mapping(target = "nacionalidad", ignore = true)
    FuncionarioEntidad boToEntity(Funcionario funcionario);
}
