package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.PaqueteNuiEntidad;

/**
 * PaqueteNuiRepository.java
 *
 * @descripcion Clase Repositorio de la entidad PaqueteNuiEntidad
 * @version 1.0 12/12/2016
 * @author Oscar Montilla
 *
 */
public interface PaqueteNuiRepository extends CrudRepository<PaqueteNuiEntidad, Long> {

    /**
     * @metodo Metodo de consulta que obtiene una lista de PaqueteNuiEntidad
     * @param codigo String codigo Identificador de oficina
     * @param paqueteEstatus Long Id Identificador de paquete estatus
     * @param estatusNui Long Id identificador de Nui Estatus
     * @return LibroDiarioEntidad - Objeto del modelo ontologico que contiene la informacion de PaqueteNui.
     */
    List<PaqueteNuiEntidad> buscarListaPaqueteYListaNui(
            @Param("codigo") String codigo,
            @Param("paqueteEstatus") Long paqueteEstatus,
            @Param("estatusNui") Long estatusNui);
}
