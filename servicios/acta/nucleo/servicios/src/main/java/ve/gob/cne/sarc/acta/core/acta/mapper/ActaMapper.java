package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;

/**
 * ActaMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 *
 */
@Mapper(componentModel = "spring",
        uses = {DefuncionMapper.class, InsercionMapper.class,
            LibroMapper.class, ProcedenciaMapper.class,
            ParticipanteMapper.class, SolicitudMapper.class,
            OficinaFuncionarioMapper.class, ParticipanteMapper.class
        })
public interface ActaMapper {

    /**
     * Metodo de mapeo de los objetos ActaEntidad y Acta
     *
     * @param actaEntidad Objeto ActaEntidad
     * @return Acta, instancia de objeto que contiene la informacion del Acta
     */
    @Mappings({
        @Mapping(source = "actaEntidad.estatus.nombre", target = "actaEstatus"),
        @Mapping(target = "defuncion", ignore = true),
        @Mapping(target = "procedencias", ignore = true),
        @Mapping(target = "participantes", ignore = true)
    })
    Acta entityToBo(ActaEntidad actaEntidad);

    /**
     * Metodo de mapeo de los objetos Funcionario y FuncionarioEntidad
     *
     * @param funcionario objeto que contiene la informacion del Funcionario
     * @return Objeto entidad de tipo FuncionarioEntidad
     */
    FuncionarioEntidad boToEntityFuncionario(Funcionario funcionario);

    /**
     * Metodo de mapeo de los objetos Oficina y OficinaEntidad
     *
     * @param oficina objeto que contiene la informacion de la Oficina
     * @return Objeto de tipo OficinaEntidad
     */
    OficinaEntidad boToEntityOficina(Oficina oficina);

    /**
     * Metodo de mapeo del objeto de NacionalidadEntidad
     *
     * @param nacionalidadEntidad objeto de tipo entidad que contiene la
     * informacion de la Nacionalidad
     * @return entidad convertida a String
     */
    String nacionalidadToString(NacionalidadEntidad nacionalidadEntidad);

    /**
     * Metodo de mapeo de NacionalidadEntidad
     *
     * @param nacionalidad String que contiene la Nacionalidad
     * @return objeto de tipo entidad convertida a String
     */
    NacionalidadEntidad stringToNacionalidad(String nacionalidad);

    /**
     * Metodo de mapeo de los objetos de Direccion
     *
     * @param direccion String que contiene la Nacionalidad
     * @return objeto de tipo Direccion
     */
    Direccion direccionToString(String direccion);

    /**
     * Metodo de mapeo de los objetos Funcionario y FuncionarioEntidad
     *
     * @param direccion objeto que contiene la informacion de la Direccion
     * @return entidad convertida a String
     */
    String stringToDireccion(Direccion direccion);

    /**
     * Metodo de mapeo de los objetos TipoOficinaEntidad y TipoOficina
     *
     * @param tipoOficina objeto que contiene la informacion del tipo Oficina
     * @return objeto entidad de tipo TipoOficinaEntidad
     */
    TipoOficinaEntidad boToEntityTipoOficina(TipoOficina tipoOficina);

    /**
     * Metodo de mapeo de los objetos TipoOficinaEntidad y TipoOficina
     *
     * @param tipoOficinaEntidad objeto entidad que contiene la informacion del tipo
     * Oficina
     * @return objeto de tipo TipoOficina
     */
    TipoOficina entityToBo(TipoOficinaEntidad tipoOficinaEntidad);

    /**
     * Metodo de mapeo de los objetos Acta y ActaEntidad
     *
     * @param acta instancia de objeto que contiene la informacion del Acta
     * @return actaEntidad Objeto ActaEntidad
     */
    @Mapping(target = "defuncion", ignore = true)
    ActaEntidad boToEntity(Acta acta);

    /**
     * Convierte una lista entidad a una lista pojo
     *
     * @param actaEntidads Listado de entidades del acta
     * @return Lista de pojo acta
     */
    List<Acta> entitysToBos(List<ActaEntidad> actaEntidads);

}
