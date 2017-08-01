package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ve.gob.cne.sarc.persistencia.entidades.PaqueteEntidad;

/**
 * PaqueteRepository.java 
 *
 * @descripcion Clase Repositorio de la entidad PaqueteEntidad
 * @version 1.0 20 Mayo 2016
 * @author Jorge Maguina
 *
 */
public interface PaqueteRepository extends CrudRepository<PaqueteEntidad, Long> {

    /**
     * @metodo Metodo de acceso a datos findBySolicitudEntidadId que permite consultar los datos de un Paquete por el Id
     * de Socicitud
     * @author Oscar Montilla
     * @param id long Id de Solicitud
     * @return PaqueteEntidad - objeto del modelo ontologico que posee la informacion de Procedencia.
     */
    PaqueteEntidad findBySolicitudEntidadId(@Param("id") Long id);
    
}
