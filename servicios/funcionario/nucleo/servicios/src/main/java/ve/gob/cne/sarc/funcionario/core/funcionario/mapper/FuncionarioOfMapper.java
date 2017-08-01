package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.catalogo.TipoFuncionario;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;


/**
 * FuncionarioOfMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, CiudadMapper.class})
public interface FuncionarioOfMapper {


    /**
     * Convierte una entidad en un pojo
     *
     * @param oficinaFuncionarioEntidad objeto entidad que contiene OicinaFuncionarioEntidad
     * @return  pojo que contiene al funcionario
     */
    @Mappings({
        @Mapping(source = "oficinaFuncionarioEntidad.funcionario.primerNombre", target = "primerNombre"),
        @Mapping(source = "oficinaFuncionarioEntidad.funcionario.segundoNombre", target = "segundoNombre"),
        @Mapping(source = "oficinaFuncionarioEntidad.funcionario.primerApellido", target = "primerApellido"),
        @Mapping(source = "oficinaFuncionarioEntidad.funcionario.segundoApellido", target = "segundoApellido"),
        @Mapping(source = "oficinaFuncionarioEntidad.fechaInicio", target = "fechaInicio"),
        @Mapping(source = "oficinaFuncionarioEntidad.fechaFin", target = "fechaFin"),
        @Mapping(source = "oficinaFuncionarioEntidad.fechaResolucion", target = "fechaResolucion"),
        @Mapping(source = "oficinaFuncionarioEntidad.fechaGaceta", target = "fechaGaceta"),
        @Mapping(source = "oficinaFuncionarioEntidad.numeroGaceta", target = "numeroGaceta")
    })
    public Funcionario entityToBO(OficinaFuncionarioEntidad oficinaFuncionarioEntidad);

     /**
     * Convierte una entidad en un pojo
     *
     * @param oficinaEntidad objeto entidad que contiene la Oficina
     * @return  pojo que contiene informacion de Oficina
     */
    public Oficina oficinaEntidadToBo(OficinaEntidad oficinaEntidad);

    /**
     * Convierte una entidad en un pojo
     *
     * @param tipoFuncionarioEntidad objeto entidad que contiene el tipoFuncionario
     * @return  pojo que contiene informacion de TipoFuncionario
     */
    public TipoFuncionario tipoFuncionarioEntidadToBo(TipoFuncionarioEntidad tipoFuncionarioEntidad);
    
     /**
     * Convierte un String a un pojo
     *
     * @param direccion String que describe la Direccion
     * @return  pojo que contiene informacion de Direccion
     */
    public Direccion direccionEntidadToBo(String direccion);
    
    /**
     * Convierte una entidad a un pojo
     *
     * @param tipoOficinaEntidad objeto entidad que describe la Direccion
     * @return  pojo que contiene informacion de TipoOficina
     */
    public TipoOficina tipoOficinaEntidadToBO(TipoOficinaEntidad tipoOficinaEntidad);
}
