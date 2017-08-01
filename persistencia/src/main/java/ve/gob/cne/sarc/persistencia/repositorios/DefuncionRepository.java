package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;

/**
 * DefuncionRepository.java
 *
 * @descripcion Clase Repositorio de la entidad DefuncionEntidad
 * @version 1.0 23/08/2016
 * @author Anabell De Faria
 *
 */
public interface DefuncionRepository extends CrudRepository<DefuncionEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos findNumeroCertificado que permite consultar los datos de un DefuncionEntidad
     * @author Anabell De Faria
     * @param numeroCertificado Long numeroCertificado Defuncion.
     * @return List<CiudadEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de Defuncion.
     */
    DefuncionEntidad findNumeroCertificado(@Param("numeroCertificado") long numeroCertificado);

    /**
     * @metodo Metodo de acceso a datos findbyActaNumeroActa que permite consultar datos por el Numero del acta.
     * @author Oscar Montilla
     * @param numeroActa String Numero de acta del acta
     * @return DefuncionEntidad - Objeto del modelo ontologico que contiene la informacion de Defuncion.
     */
    DefuncionEntidad findByActaNumeroActa(@Param("numeroActa") String numeroActa);

    /**
     * @metodo Metodo de acceso a datos findBySolicitudId que permite consultar datos por el id de Solicitud.
     * @param id Long id de Solicitud
     * @return DefuncionEntidad - Objeto del modelo ontologico que contiene la informacion de Defuncion.
     */
    DefuncionEntidad findByActaSolicitudId(@Param("id") Long id);

}
