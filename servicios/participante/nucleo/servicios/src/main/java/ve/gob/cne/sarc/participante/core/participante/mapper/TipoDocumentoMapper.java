package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;

/**
 * TipoDocumentoMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoMapper {

    /**
     * Metodo de mepao de un String a un pojo TipoDocumento
     *
     * @param documento String con la descripcion del tipo de documento
     * @return Objeto de tipo TipoDocumento
     */
    TipoDocumento map(String documento);

    /**
     * Metodo de mapeo de un pojo TipoDocumento a un String
     *
     * @param documento Objeto de tipo TipoDocumento
     * @return String con la descripcion del tipo de documento
     */
    String map1(TipoDocumento documento);

}
