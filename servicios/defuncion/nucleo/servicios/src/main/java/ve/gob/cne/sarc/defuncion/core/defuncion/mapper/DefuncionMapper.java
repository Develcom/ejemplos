package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;

/**
 * DefuncionMapper.java
 *
 * @descripcion [Clase para el mapeo de la Entidad defuncion con la clase
 * Defuncion y viceversa]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring", uses = {ActaMapper.class, ParroquiaMapper.class,EstadoMapper.class})
public interface DefuncionMapper {

    /**
     * Convierte una entidad a un String
     *
     * @param parroquiaEntidad ParroquiaEntidad entidad con la informacion de
     * Parroquia
     * @return String con la informacion de Parroquia
     */
    String parroquiaToString(ParroquiaEntidad parroquiaEntidad);

    /**
     * Convierte un String a una Entidad
     *
     * @param parroquia String de tipo Parroquia
     * @return objeto entidad de tipo Parroquia
     */
    ParroquiaEntidad stringToParroquia(String parroquia);

    /**
     * Convierte una entidad a un pojo
     *
     * @param defuncionEntidad DefuncionEntidad entidad con la informacion de
     * Defuncion
     * @return Pojo Defuncion
     */
    @Mappings({
        @Mapping(source = "defuncionEntidad.acta.numeroActa", target = "numeroActa"),
        @Mapping(source = "defuncionEntidad.fechaDefuncion", target = "fechaDefuncion"),
        @Mapping(source = "defuncionEntidad.textoCausa", target = "textoCausa"),
        @Mapping(source = "defuncionEntidad.sexo", target = "sexo"),
        @Mapping(source = "defuncionEntidad.estadoCivil", target = "estadoCivil"),
        @Mapping(source = "defuncionEntidad.estadoDefuncion", target = "estadoDefuncion"),
        @Mapping(source = "defuncionEntidad.municipioDefuncion", target = "municipioDefuncion"),
        @Mapping(source = "defuncionEntidad.parroquiaDefuncion", target = "parroquiaDefuncion"),
        @Mapping(source = "defuncionEntidad.parroquia.municipio.estado.pais.nombre", target = "paisDefuncion"),
        @Mapping(source = "defuncionEntidad.numeroCertificado", target = "numeroCertificado"),
        @Mapping(source = "defuncionEntidad.fechaCertificado", target = "fechaCertificado"),
        @Mapping(source = "defuncionEntidad.primerNombreMedico", target = "primerNombreMedico"),
        @Mapping(source = "defuncionEntidad.segundoNombreMedico", target = "segundoNombreMedico"),
        @Mapping(source = "defuncionEntidad.primerApellidoMedico", target = "primerApellidoMedico"),
        @Mapping(source = "defuncionEntidad.segundoApellidoMedico", target = "segundoApellidoMedico"),
        @Mapping(source = "defuncionEntidad.nuMPPS", target = "nuMPPS"),
        @Mapping(source = "defuncionEntidad.centroSalud", target = "centroSalud"),
        @Mapping(source = "defuncionEntidad.numeroExtractoConsular", target = "numeroExtractoConsular"),
        @Mapping(source = "defuncionEntidad.fechaExtractoConsular", target = "fechaExtractoConsular"),
        @Mapping(source = "defuncionEntidad.primerNombreConsular", target = "primerNombreConsular"),
        @Mapping(source = "defuncionEntidad.segundoNombreConsular", target = "segundoNombreConsular"),
        @Mapping(source = "defuncionEntidad.primerApellidoConsular", target = "primerApellidoConsular"),
        @Mapping(source = "defuncionEntidad.segundoApellidoConsular", target = "segundoApellidoConsular"),
        @Mapping(source = "defuncionEntidad.notaMarginal", target = "notaMarginal"),
        @Mapping(source = "defuncionEntidad.documentoIdentidadMedico", target = "docIdenMedico"),
        @Mapping(source = "defuncionEntidad.numeroDocumentoConsular", target = "documentoIdentConsular"),
        @Mapping(source = "defuncionEntidad.tipoDocumentoIdentidad.nombreTipoDocIdentidad", target = "tipoDoc"),

     })
    Defuncion entityToBusiness(DefuncionEntidad defuncionEntidad);
    
    /**
     * Convierte un pojo a una entidad
     * @param defuncion Defuncion pojo con la informacion de una defuncion
     * @return Objeto de tipo Entidad
     */
    DefuncionEntidad businessToEntity(Defuncion defuncion);
}
