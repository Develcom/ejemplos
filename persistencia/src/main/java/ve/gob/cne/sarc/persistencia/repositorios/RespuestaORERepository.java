package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.RespuestaOREEntidad;

/**
 * RespuestaORERepository.java
 * @descripcion Entididad que contiene los metodos de Repositorio de la Respuesta ORE
 * @version 
 * 23 de nov. de 2016
 * @author Domingo Rondon
 */
public interface RespuestaORERepository extends CrudRepository<RespuestaOREEntidad, Long>{
    
    /**
     * buscarPorSolicitudORE
     * Metodo que obtiene todas las respuesta realizadas por los funcionarios ORE en base a una Solicitud
     * @since 1 de dic. de 2016
     * @param idSolicitud
     * @return
     * @return List<RespuestaOREEntidad>
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-97
     */
    public List<RespuestaOREEntidad> buscarPorSolicitudORE(
            @Param("idSolicitud") Long idSolicitud);

}