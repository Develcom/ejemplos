package ve.gob.cne.sarc.ciudadano.core.ciudadano.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;

/**
 * ComunidadIndigena.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y las clase del modelo de
 * datos.]
 * @version 1.0 11/11/2015
 * @author Oscar Eubieda
 */
@Mapper(componentModel = "spring")
public abstract class ComunidadIndigena {

    String entityToBO(ComunidadIndigenaEntidad comunidadIndigenaEntidad) {
        return comunidadIndigenaEntidad.getNombre();
    }
}
