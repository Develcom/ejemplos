package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EstatusCitaEntidad;

/**
 * EstatusCitaRepository.java
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version 
 * 11 de oct. de 2016
 * @author Domingo Rondon
 */
public interface EstatusCitaRepository extends
CrudRepository<EstatusCitaEntidad, Long>{
    
    EstatusCitaEntidad findByCode(@Param("codigo") String codigo);

}
