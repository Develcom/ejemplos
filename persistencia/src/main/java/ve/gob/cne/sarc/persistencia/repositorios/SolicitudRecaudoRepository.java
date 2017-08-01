package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudRecaudoEntidad;

/**
 * SolicitudRecaudoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitudRecaudoEntidad
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 */
public interface SolicitudRecaudoRepository extends
        CrudRepository<SolicitudRecaudoEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findBySolicitudNumeroAndRecaudoId que permite consultar las Solicitud por
     * recaudo existentes, buscandolas por numero de solicitud y id de recaudo.
     * @author Oscar Montilla
     * @param numero String Numero de Solicitud
     * @param id Long Identificador de Recaudo
     * @return SolicitudRecaudoEntidad - Objeto del modelo ontologico de Solicitud Recaudo
     */
    SolicitudRecaudoEntidad findBySolicitudNumeroAndRecaudoId(@Param("numero") String numero,
            @Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findBySolicitudNumero que permite consultar las Solicitud por recaudo
     * existentes, buscandolas por numero de solicitud.
     * @param numero String numero de solicitud
     * @return SolicitudRecaudoEntidad - Objeto del modelo Ontologico que posee la informacion de Solicitud recaudo
     */
    List<SolicitudRecaudoEntidad> findBySolicitudNumero(@Param("numero") String numero);

    /**
     * @metodo Metodo de acceso a datos findBySolicitudNumeroAndRecaudoCodigo que permite consultar las Solicitud
     * recaudo por Numero de solicitud y codigo de Recaudo
     * @param numero String Numero de Solicitud
     * @param codigo String codigo de recaudo
     * @return SolicitudRecaudoEntidad - Objeto del modelo Ontologico que posee la informacion de Solicitud recaudo
     */
    SolicitudRecaudoEntidad findBySolicitudNumeroAndRecaudoCodigo(@Param("numero") String numero,
            @Param("codigo") String codigo);
}
