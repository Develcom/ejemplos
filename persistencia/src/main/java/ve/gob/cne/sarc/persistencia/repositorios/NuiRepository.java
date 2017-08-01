package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.NuiEntidad;

/**
 * NuiRepository.java
 *
 * @descripcion Clase Repositorio de la entidad NuiEntidad
 * @version 1.0 03/01/2017
 * @author Oscar Montilla
 *
 */
public interface NuiRepository extends CrudRepository<NuiEntidad, Long> {

    /**
     * @metodo Metodo que obtiene la lista de NUi de un paquete NUI
     * @param idPaquete Long id de Paquete de NUI
     * @param idEstatus Long id de estatus de NUI
     * @return List<NuiEntidad> - Objeto del modelo ontologico que contiene la informacion de Nui.
     */
    List<NuiEntidad> findByPaqueteNuiIdAndNuiEstatusIdOrderByFechaInicioAscIdAsc(
            @Param("idPaquete") Long idPaquete,
            @Param("idEstatus") Long idEstatus);
}
