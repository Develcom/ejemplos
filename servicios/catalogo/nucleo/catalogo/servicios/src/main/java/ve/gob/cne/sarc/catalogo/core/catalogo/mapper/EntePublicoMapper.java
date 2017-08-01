package ve.gob.cne.sarc.catalogo.core.catalogo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.persistencia.entidades.EntePublicoEntidad;

/**
 * EntePublicoMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface EntePublicoMapper {

    /**
     * Metodo de mapeo de los objetos EntePublicoEntidad y EntePublico
     *
     * @param entePublicoEntidad Objeto con la informacion de la entidad
     * EntePublico
     * @return {@link EntePublico}
     */
    @Mappings({
        @Mapping(source = "entePublicoEntidad.id", target = "codigo")})
    EntePublico entitiesToBOs(EntePublicoEntidad entePublicoEntidad);

    /**
     * Metodo de mapeo de las listas de EntePublicoEntidad y EntePublico
     *
     * @param entePublicoEntidad Lista de {@link EntePublicoEntidad}
     * @return Lista de {@link EntePublico}
     */
    List<EntePublico> entitiesToBOs(List<EntePublicoEntidad> entePublicoEntidad);
}
