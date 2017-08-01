package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.OficinaCitaDetalleEntidad;

/**
 * OficinaCitaDetalleRepository.java
 * @descripcion Repositorio que Contendrá el detalle de las Citas Registradas
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
public interface OficinaCitaDetalleRepository extends
CrudRepository<OficinaCitaDetalleEntidad, Long>{
    
    /**
     * buscarPorCodigoOficinaFecha
     * Busca el Detalle de la Cita ya configurada para una Fecha y una Oficina en particular
     * @since 18 de oct. de 2016
     * @param oficina
     * @param fecha
     * @return
     * @return OficinaCitaDetalleEntidad
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-201
     */
    public OficinaCitaDetalleEntidad buscarPorCodigoOficinaFecha(@Param("oficina") String oficina, @Param("fechaInicio") Date fechaInicio,@Param("fechaFin") Date fechaFin);
    
    /**
     * buscarPorCodigoOficina
     * Busca todas los detalles configurados para una Cita
     * @since 18 de oct. de 2016
     * @param oficina
     * @return
     * @return List<OficinaCitaDetalleEntidad>
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-201
     */
    public List<OficinaCitaDetalleEntidad> buscarPorCodigoOficina(@Param("oficina") String oficina);

}
