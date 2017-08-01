package ve.gob.cne.sarc.participante.core.participante.mapper;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.persistencia.entidades.ActaEstatusEntidad;

/**
 * EstatusActaMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class EstatusActaMapper {

    /**
     * Metodo que mapea la entidad ActaEstatusEntidad a un String
     *
     * @param estatusEntidad Objeto entidad de tipo ActaEstatusEntidad
     * @return String que contiene el estatus del acta
     */
    public String actaEstatusTOestatus(ActaEstatusEntidad estatusEntidad) {
        return estatusEntidad.getNombre();
    }
}
