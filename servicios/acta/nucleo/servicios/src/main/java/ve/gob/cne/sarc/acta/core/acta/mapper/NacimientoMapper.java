package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.persistencia.entidades.NacimientoEntidad;

/**
 * NacimientoMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring", uses = {ActaMapper.class})
public interface NacimientoMapper {
    
    /**
     * Metodo de mapeo de los objetos NacimientoEntidad y Nacimiento
     *
     * @param nacimientoEntidad Objeto NacimientoEntidad
     * @return Nacimiento, instancia de objeto que contiene la informacion de
     * Nacimiento
     */
    @Mappings({
        @Mapping(source = "nacimientoEntidad.extrahospitalario", target = "diExtrahospitalario"),
        @Mapping(source = "nacimientoEntidad.certificado", target = "numeroCertificado"),
        @Mapping(source = "nacimientoEntidad.numDocMedico", target = "documentoIdentidadMedico"),
        @Mapping(source = "nacimientoEntidad.mpps", target = "nuMPPS"),
        @Mapping(source = "nacimientoEntidad.caracterDeclarante", target = "caracterActuaDeclarante")
    })
    Nacimiento entityToBo(NacimientoEntidad nacimientoEntidad);
    
    /**
     * Metodo de mapeo de los objetos Nacimiento y NacimientoEntidad
     *
     * @param nacimiento instancia de objeto que contiene la informacion de
     * Nacimiento
     * @return nacimientoEntidad Objeto NacimientoEntidad
     */
    NacimientoEntidad boToEntity(Nacimiento nacimiento);
    
}
