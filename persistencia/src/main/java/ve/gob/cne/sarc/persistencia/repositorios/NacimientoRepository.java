package ve.gob.cne.sarc.persistencia.repositorios;

import ve.gob.cne.sarc.persistencia.entidades.NacimientoEntidad;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * NacimientoRepository.java
 *
 * @descripcion Clase Repositorio de la entidad NacimientoEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 */
public interface NacimientoRepository extends CrudRepository<NacimientoEntidad, Long> {

    /**
     *
     * @metodo Busca Nacimiento por su Id
     * @param id Long id identificador de Nacimiento
     * @return NacimientoEntidad
     */
    public NacimientoEntidad findById(@Param("id") int id);

    /**
     *
     * @metodo Busca un Nacimiento con el Numero de Certificado
     * @param certificado String Numero de certificado
     * @return NacimientoEntidad
     */
    public NacimientoEntidad findByCertificado(@Param("certificado") int certificado);

    /**
     * @metodo metodo de consulta finByActaNumeroActa que obtiene la informacion de un nacimiento por el Numero de Acta.
     * @param numeroActa String numeroActa que pertenece al Acta
     * @return NacimientoEntidad - objeto del modelo ontologico que posee la informacion de Nacimiento
     */
    NacimientoEntidad findByActaNumeroActa(@Param("numeroActa") String numeroActa);
}
