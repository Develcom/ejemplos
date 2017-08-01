package ve.gob.cne.sarc.recaudo.core.recaudo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.persistencia.entidades.RecaudoEntidad;

/**
 * RecaudoMapper.java
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public interface RecaudoMapper {

    
    /**
     * Metodo de mapeo de los objetos RecaudoEntidad y Recaudo
     *
     * @param recaudoEntidad Objeto RecaudoEntidad
     * @return Recaudo, instancia de objeto que contiene la informacion del Recaudo
     */
    Recaudo entityToBO(RecaudoEntidad recaudoEntidad);

    /**
     * Metodo de mapeo listas de objetos RecaudoEntidad y Recaudo
     *
     * @param recaudoEntidad Lista de objetos RecaudoEntidad
     * @return Lista de {@link Recaudo}
     */
    List<Recaudo> entitiesToBOs(List<RecaudoEntidad> recaudoEntidad);
}
