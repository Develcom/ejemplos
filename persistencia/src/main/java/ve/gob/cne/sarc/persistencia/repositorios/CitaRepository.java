package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.CitaEntidad;

/**
 * CitaRepository.java
 * @descripcion Repositorio de las Citas de Matrimonio
 * @version 
 * 13 de oct. de 2016
 * @author Domingo Rondon
 */
public interface CitaRepository extends
CrudRepository<CitaEntidad, Long>{
    
    /**
     * buscarPorCodigoOficina
     * Busca las citas de una oficina
     * @since 17 de oct. de 2016
     * @param oficina
     * @return
     * @return List<CitaEntidad>
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-201
     */
    public List<CitaEntidad> buscarPorCodigoOficina(@Param("oficina") String oficina);
    
    /**
     * buscarOficinas
     * Metodo que busca las citas por Codigo de la Oficina y la Fecha
     * @since 17 de oct. de 2016
     * @param oficina
     * @param fecha
     * @return
     * @return List<CitaEntidad>
     * @author Domingo Rondon
     * @version 1.0
     * Jira SAM-201
     */
    public List<CitaEntidad> buscarCitas(@Param("oficina") String oficina, @Param("fechaInicio") Date fechaInicio,@Param("fechaFin") Date fechaFin);
    
    
    /**
     * 
     * contarCitarPorPeriodo
     * Metodo que calcula el número de citas asignadas e una oficina en un periodo
     * de tiempo para determinar si estas citas se pueden o no asignar.
     * @since 18/10/2016
     * @param oficina
     * @param fechaDesde
     * @param fechaHasta
     * @return
     * @return Integer
     * @author Windows
     * @version 1.0
     * Jira SAM-200
     */
    public Integer contarCitarPorPeriodo(@Param("oficina") String oficina,
                                         @Param("estatus") String estatus,
                                         @Param("fechaInicio") Date fechaInicio,
                                         @Param("fechaFin") Date fechaFin);

}
