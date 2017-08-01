package ve.gob.cne.sarc.acta.core.acta.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.persistencia.entidades.MedidaProteccionEntidad;

/**
 * MedidaProteccionMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Gabriela Palmar
 */
@Mapper(componentModel = "spring", uses = ProcedenciaMapper.class)
public interface MedidaProteccionMapper {

    /**
     * Metodo de mapeo de los objetos MedidaProteccionEntidad y MedidaProteccion
     *
     * @param medidaProteccionEntidad Objeto MedidaProteccionEntidad
     * @return MedidaProteccion, instancia de objeto que contiene la informacion
     * de MedidaProteccion
     */
    @Mappings({
        @Mapping(source = "medidaProteccionEntidad.procedencia.textoExtracto", target = "extractoProcedencia"),
        @Mapping(source = "medidaProteccionEntidad.procedencia.acta.numeroActa", target = "numeroActa")
    })
    MedidaProteccion entityToBo(MedidaProteccionEntidad medidaProteccionEntidad);

    /**
     * Metodo de mapeo de los objetos MedidaProteccion y MedidaProteccionEntidad
     *
     * @param medidaProteccion instancia de objeto que contiene la informacion
     * de MedidaProteccion
     * @return medidaProteccionEntidad Objeto MedidaProteccionEntidad
     */
    MedidaProteccionEntidad boToEntity(MedidaProteccion medidaProteccion);

}
