package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.SolicitudEstatusEntidad;

/**
 * SolicitudEstatusRepository.java
 *
 * @descripcion Clase Repositorio de la entidad SolicitudEstatusEntidad
 * @version: 1.0 12/08/2016
 * @author: Oscar Montilla
 *
 */
public interface SolicitudEstatusRepository extends
        CrudRepository<SolicitudEstatusEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findById que permite consultar todos los de un SolicitudEstatusEntidad
     * @param id Long Identificador de SolicitudEstatus.
     * @return SolicitudEstatusEntidad - Objeto del modelo ontologico que contiene la informacion de SolicitudEstatus.
     */
    public SolicitudEstatusEntidad findById(@Param("id") Long id);

    /**
     * @metodo Metodo de acceso a datos findById que permite consultar todos los de un SolicitudEstatusEntidad por el
     * codigo de Solicitud Estatus
     * @param codigo String Nombre de solicitud Estatus
     * @return SolicitudEstatusEntidad - Objeto del modelo ontologico que contiene la informacion de SolicitudEstatus.
     */
    SolicitudEstatusEntidad findByCodigo(@Param("codigo") String codigo);

}
