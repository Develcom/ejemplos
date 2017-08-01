package ve.gob.cne.sarc.persistencia.repositorios;

import org.springframework.data.repository.CrudRepository;

import ve.gob.cne.sarc.persistencia.entidades.SolicitudOREEntidad;

/**
 * SolicitudORERepository.java
 * @descripcion Clase que contendra los repositorios de las Solicitudes
 * @version 
 * 23 de nov. de 2016
 * @author Domingo Rondon
 */
public interface SolicitudORERepository extends CrudRepository<SolicitudOREEntidad, Long>{

}
