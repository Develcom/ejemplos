package ve.gob.cne.sarc.funcionario.core.funcionario.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;

/**
 * OficinaMapper.java
 * @descripcion [ Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {TipoOficinaMapper.class})
public interface OficinaMapper {
    
    @Mappings({
        @Mapping(target = "direccion", ignore = true),
        @Mapping(source = "oficinaEntidad.numero", target = "numeroGaceta"),
        @Mapping(target = "funcionario", ignore = true),
        @Mapping(target = "solicitud", ignore = true)
    })
    public Oficina entityToBO(OficinaEntidad oficinaEntidad);
    
    public List<Oficina> entitiesToBOs(List<OficinaEntidad> oficinasEntidad);
    
}
