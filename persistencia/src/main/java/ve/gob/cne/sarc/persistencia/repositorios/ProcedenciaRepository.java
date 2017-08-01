package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ProcedenciaEntidad;

/**
 * ProcedenciaRepository.java
 *
 * @drescripcion Repositorio de la entidad Procedencia
 * @author Oscar Montilla
 * @version 1.0 09/09/2016
 */
public interface ProcedenciaRepository extends CrudRepository<ProcedenciaEntidad, Long> {

    /**
     * @metodo Metodo de acceso a datos findByActaNumeroActa que permite consultar los datos de un Procedencia
     * @param numeroActa string Numero de acta
     * @return ProcedenciaEntidad - objeto del modelo ontologico que posee la informacion de Procedencia.
     */
    List<ProcedenciaEntidad> findByActaNumeroActa(@Param("numeroActa") String numeroActa);

    /**
     * @metodo Metodo de acceso a datos findByActaNumeroActa que permite consultar los datos de un Procedencia
     * @param textoExtracto String Extracto de procedencia
     * @return ProcedenciaEntidad - objeto del modelo ontologico que posee la informacion de Procedencia.
     */
    ProcedenciaEntidad findByTextoExtracto(@Param("textoExtracto") String textoExtracto);

    /**
     * @metodo Metodo de acceso a datos findByTipoProcedenciaIdAndActaNumeroActa que permite consultar los datos de un
     * Procedencia
     * @param id Long identificador de Procedencia
     * @param numeroActa String Numero de Acta
     * @return ProcedenciaEntidad - objeto del modelo ontologico que posee la informacion de Procedencia.
     */
    ProcedenciaEntidad findByTipoProcedenciaIdAndActaNumeroActa(@Param("id") Long id,
            @Param("numeroActa") String numeroActa);

    /**
     * @metodo Metodo de acceso a datos findByNombre, que obtiene la informacion de una procedencia por nombre
     * @param nombre String nombre de Procedencia
     * @return ProcedenciaEntidad - objeto del modelo ontologico que posee la informacion de Procedencia.
     */
    ProcedenciaEntidad findByTipoProcedenciaNombre(@Param("nombre") String nombre);

    /**
     * @metodo Metodo de acceso findByTipoProcedenciaNombreAndActaNumeroActa, que obtiene la informacion de Procedencia
     * por nombre de tipo procedencia y numero de acta
     * @param nombre String Nombre de Tipo Procedencia
     * @param numeroActa String Numero de Acta
     * @return ProcedenciaEntidad - objeto del modelo ontologico que posee la informacion de Procedencia.
     */
    ProcedenciaEntidad findByTipoProcedenciaNombreAndActaNumeroActa(
            @Param("nombre") String nombre, @Param("numeroActa") String numeroActa);
}
