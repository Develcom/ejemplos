package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.persistencia.entidades.EntePublicoEntidad;

/**
 * EntePublicoMapper.java
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y
 * las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = {SolicitanteMapper.class})
public interface EntePublicoMapper {

    /**
     * Convierte una entidad a un pojo
     * @param entePublicoEntidad objeto de tipo EntePublicoEntidad
     * @return objeto de tipo EntePublico
     */
    public EntePublico entityToBO(EntePublicoEntidad entePublicoEntidad);

    /**
     * Convierte una lista entidad a una lista pojo
     * @param entePublicosList lista de objetos entidad de tipo EntePublicoEntidad
     * @return lista de objetos de tipo EntePublico
     */
    public List<EntePublico> entitiesToBOs(List<EntePublicoEntidad> entePublicosList);
}
