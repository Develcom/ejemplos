package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.catalogo.TipoOficina;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.OficinaFuncionario;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;

/**
 * ActaMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de ActaEntidad en
 * instancias de Acta y viceversa]
 * @version 1.0 20/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring",
        uses = {InsercionMapper.class,
            LibroMapper.class, ProcedenciaMapper.class,
            ParticipanteMapper.class
        })
public interface ActaMapper {

    /**
     * Convierte una entidad a un pojo
     * @param actaEntidad ActaEntidad con la informacion de Acta
     * @return Pojo Acta
     */
    @Mappings({
        @Mapping(source = "actaEntidad.estatus.nombre", target = "actaEstatus"),
        @Mapping(target = "defuncion", ignore = true)
    })
    Acta entityToBo(ActaEntidad actaEntidad);

    /**
     * Convierte una entidad a un pojo
     * @param oficinaFuncionarioEntidad objeto entidad con la informacion de OficinaFuncionario
     * @return objeto de tipo OficinaFuncionario
     */
    OficinaFuncionario entityToBoOficFunc(OficinaFuncionarioEntidad oficinaFuncionarioEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param oficinaFuncionario objeto de tipo OficinaFuncionario
     * @return objeto entidad de tipo OficinaFuncionarioEntidad
     */
    OficinaFuncionarioEntidad boToEntityOficFun(OficinaFuncionario oficinaFuncionario);
    
    /**
     * Convierte una entidad a un pojo
     * @param solicitudEntidad objeto entidad con la informacion de SolicitudEntidad
     * @return objeto de tipo Solicitud 
     */
    Solicitud entityToBoSolicitud(SolicitudEntidad solicitudEntidad);
    
    /**
     * Convierte una pojo a una entidad
     * @param solicitud objeto con la informacion de Solicitud
     * @return objeto entidad de tipo Solicitud
     */
    SolicitudEntidad boToEntitySolicitud (Solicitud solicitud);
    
    /**
     * Convierte un pojo a una entidad
     * @param funcionarioEntidad objeto entidad con la informacion de Funcionario
     * @return objeto de tipo Funcionario
     */
    Funcionario entityToBoFuncionario(FuncionarioEntidad funcionarioEntidad);
    
    /**
     * Convierte una entidad a un pojo
     * @param funcionario objeto con la informacion de Funcionario
     * @return objeto entidad de tipo Funcionario
     */
    FuncionarioEntidad boToEntityFuncionario(Funcionario funcionario);
    
    /**
     * Convierte una entidad a un pojo
     * @param oficinaEntidad objeto con la informacion de Oficina
     * @return objeto de tipo Oficina
     */
    Oficina entityToBoOficina(OficinaEntidad oficinaEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param oficina objeto con la informacion de Oficina
     * @return objeto entida de tipo Oficina
     */
    OficinaEntidad boToEntityOficina(Oficina oficina);
    
     /**
     * Convierte una entidad a un pojo
     * @param solicitanteEntidad objeto con la informacion de Solicitante
     * @return objeto de tipo Solicitante
     */
    Solicitante entityToBoSolicitante(SolicitanteEntidad solicitanteEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param solicitante objeto con la informacion de solicitante
     * @return objeto entidad de tipo Solicitante
     */
    SolicitanteEntidad boToEntity(Solicitante solicitante);
    
     /**
     * Convierte un pojo a una entidad
     * @param tramite objeto con la informacion de Tramite
     * @return objeto entidad de tipo Tramite
     */
    TramiteEntidad boToEntityTramite(Tramite tramite);
    
     /**
     * Convierte una entidad en un String
     * @param nacionalidadEntidad objeto con la informacion de nacionalidad
     * @return String de nacionalidad
     */
    String nacionalidadToString (NacionalidadEntidad nacionalidadEntidad);
    
     /**
     * Convierte un String a una entidad
     * @param nacionalidad objeto con la informacion de nacionalidad
     * @return objeto entidad de tipo Nacionalidad
     */
    NacionalidadEntidad stringToNacionalidad(String nacionalidad);
    
     /**
     * Convierte una entidad a un pojo
     * @param tramiteEntidad objeto con la informacion de Tramite
     * @return objeto de tipo Tramite
     */
    Tramite entityToBoTramite(TramiteEntidad tramiteEntidad);
    
    /**
     * Convierte un pojo a un String
     * @param direccion String que describe la direccion
     * @return objeto d tipo Direccion
     */
    Direccion direccionToString (String direccion);
    
     /**
     * Convierte un pojo a un String
     * @param direccion String que describe la direccion
     * @return objeto de tipo Direccion
     */
    String stringToDireccion(Direccion direccion);
    
    /**
     * Convierte un pojo a una entidad
     * @param tipoOficina objeto de Tipo Oficina
     * @return objeto entidad de tipo TipoOficina
     */
    TipoOficinaEntidad boToEntityTipoOficina(TipoOficina tipoOficina);
    
     /**
     * Convierte una entidad a un pojo
     * @param tipoOficinaEntidad objeto entidad con la informacion de TipoOficina
     * @return objeto de tipo Oficina
     */
    TipoOficina entityToBo(TipoOficinaEntidad tipoOficinaEntidad);
    /**
     * Convierte un pojo a una entidad
     * @param acta pojo con la informacion de un acta
     * @return Objeto de tipo Entidad con la informacion de Acta
     */
    @Mapping(target = "defuncion", ignore = true)
    ActaEntidad boToEntity(Acta acta);

}
