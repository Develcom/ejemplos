package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.InhumacionYCremacionEntidad;

/**
 * InhumacionYCremacionMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring")
public interface InhumacionYCremacionMapper {
    
    /**
     * Realiza el map dado un string a una entidad
     *
     * @param estado String describe el nombre del estado
     * @return EstadoEntidad entidad con la informacion del Estado
     */
    EstadoEntidad mapEstadoEntidad(String estado);

    /**
     * Realiza el map dado un string a una entidad
     *
     * @param pais String describe el nombre del pais
     * @return PaisEntidad entidad con la informacion del Pais
     */
    PaisEntidad mapPaisEntidad(String pais);
    
    /**
     * Realiza el map dado un string a una entidad
     *
     * @param parroquia String describe el nombre de la parroquia
     * @return ParroquiaEntidad entidad con la informacion de la Parroquia
     */
    ParroquiaEntidad mapParroquiaEntidad(String parroquia);

     /**
     * Realiza el map dado un string a una entidad
     *
     * @param municipio String describe el nombre del municipio
     * @return MunicipioEntidad entidad con la informacion del Municipio
     */
    MunicipioEntidad mapMunicipio(String municipio);

    /**
     * Convierte una entidad a un pojo
     *
     * @param inhumacionYCremacionEntidad InhumacionYCremacionEntidad entidad 
     * con la informacion de permiso Inhumacion y Cremacion
     * @return Pojo PermisoInhCre
     */
    @Mappings({
        @Mapping(source = "inhumacionYCremacionEntidad.numeroPermiso", 
                target = "numero"),
        @Mapping(source = "inhumacionYCremacionEntidad.primerNombre", 
                target = "primerNombreAutoriza"),
        @Mapping(source = "inhumacionYCremacionEntidad.segundoNombre", 
                target = "segundoNombreAutoriza"),
        @Mapping(source = "inhumacionYCremacionEntidad.primerApellido",
                target = "primerApellidoAutoriza"),
        @Mapping(source = "inhumacionYCremacionEntidad.segundoApellido", 
                target = "segundoApellidoAutoriza"),
        @Mapping(source = "inhumacionYCremacionEntidad.observacion", 
                target = "observacion"),
        @Mapping(source = "inhumacionYCremacionEntidad.tipoPermiso", 
                target = "tipoPermiso"),
        @Mapping(source = "inhumacionYCremacionEntidad.numeroCertificado", 
                target = "numeroCertificadoDef"),
        @Mapping(source = "inhumacionYCremacionEntidad.estado.nombre",
                target = "estado"),
        @Mapping(source = "inhumacionYCremacionEntidad.municipio.nombre",
                target = "municipio"),
        @Mapping(source = "inhumacionYCremacionEntidad.parroquia.nombre", 
                target = "parroquia"),
        @Mapping(source = "inhumacionYCremacionEntidad.solicitud.numero", 
                target = "numSolicitud"),
        @Mapping(source = "inhumacionYCremacionEntidad.direccion", 
                target = "direccionDefuncion"),
        @Mapping(source = "inhumacionYCremacionEntidad.pais.nombre", 
                target = "pais")        
    })

    PermisoInhCre entityToBusiness(InhumacionYCremacionEntidad
            inhumacionYCremacionEntidad);

    /**
     * Convierte un pojo a una entidad
     *
     * @param permisoInhCre PermisoInhCre pojo con la informacion de permiso
     * Inhumacion y Cremacion
     * @return InhumacionYCremacionEntidad Objeto de tipo Entidad
     */
    InhumacionYCremacionEntidad businessToEntity(PermisoInhCre permisoInhCre);

}
