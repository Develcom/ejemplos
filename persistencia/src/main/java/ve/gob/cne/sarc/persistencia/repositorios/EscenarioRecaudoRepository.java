package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.EscenarioRecaudoEntidad;


/**
 * Repositorio de la entidad EscenarioRecaudo
 * 
 * @author Juan.Carrasco
 *
 */
public interface EscenarioRecaudoRepository extends CrudRepository<EscenarioRecaudoEntidad, Long> {

    /**
     * Busca la lista de recaudos aplicables a determinado escenario.
     * 
     * @param idEscenario
     *            El código del escenario a consultar
     * @return una colección de <tt>EscenarioRecaudoEntidad</tt> conteniendo los
     *         registros encontrados.
     */
    List<EscenarioRecaudoEntidad> findByEscenarioId(@Param("codigoEscenario") long id);
    

    /**
     * Busca la lista de recaudos pendientes de una solicitud en determinado
     * escenario.
     * 
     * @param numeroSolicitud
     *            El número de identificación de la solicitud a consultar.
     * @param codigoEscenario
     *            El código del escenario a consultar.
     * 
     * 
     * @return una colección de <tt>EscenarioRecaudoEntidad</tt> conteniendo los
     *         registros encontrados.
     */
    List<EscenarioRecaudoEntidad> findPendientes(@Param("numeroSolicitud") String numeroSolicitud,
            @Param("idEscenario") long idEscenario);

    /**
     * Consulta la cantidad de Recaudos consignados dado un número de solicitud
     * y un código de escenario dados
     * 
     * @param numeroSolicitud
     *            el número de identificación de la solicitud a consultar.
     * @param codigoEscenario
     *            el código del escenario asociado
     * @return un valor de tipo <tt>long</tt> con la cantidad de recaudos
     *         asociados a una solicitud y código de escenario
     */
    public Long countByEscenarioIdAndObligatorio(@Param("codigoEscenario") long idEscenario, @Param("obligatorio") long obligatorio);
    
    public List<EscenarioRecaudoEntidad> findAll();
    
    public List<EscenarioRecaudoEntidad> findById(@Param("id") long id);

}
