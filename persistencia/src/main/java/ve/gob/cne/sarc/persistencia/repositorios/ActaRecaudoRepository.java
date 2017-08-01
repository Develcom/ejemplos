package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ActaRecaudoEntidad;

/**
 * Repositorio de la entidad ActaRecaudo
 * 
 * @author Juan.Carrasco
 *
 */
public interface ActaRecaudoRepository extends CrudRepository<ActaRecaudoEntidad, Long> {

    /**
     * Consulta la metadata de un recaudo asociado a una solicitud.
     * 
     * @param numeroSolicitud
     *            el número de identificación de la solicitud a consultar.
     * @param idEscenarioRecaudo
     *            el identificador del Escenario-Recaudo.
     * @return un objeto <tt>ActaRecaudoEntidad</tt> conteniendo el registro
     *         encontrado
     */
    public ActaRecaudoEntidad findByActaSolicitudNumeroAndEscenarioRecaudoId(@Param("numero") String numeroSolicitud,
            @Param("id") long id);

    /**
     * Consulta el Listado de Recaudos consignados dada una solicitud y el escenario requerido.
     * 
     * @param numeroSolicitud
     *            el número de identificación de la solicitud a consultar.
     * @param codigoEscenario
     *            el código del escenario a ubicar            
     * @return una colección de objetos <tt>
     */
    public List<ActaRecaudoEntidad> findByActaSolicitudNumeroAndEscenarioRecaudoEscenarioId(@Param("numero") String numeroSolicitud, @Param("nuEscenario") long idEscenario);

    /**
     * Consulta la cantidad de Recaudos consignados dado un número de solicitud
     * y un código de escenario dados
     * 
     * @param numeroSolicitud
     *            el número de identificación de la solicitud a consultar.
     * @param valido
     *            indica si el recaudo es válido
     * @return un valor de tipo <tt>long</tt> con la cantidad de recaudos
     *         asociados a una solicitud y código de escenario
     */
    public Long countByActaSolicitudNumeroAndEscenarioRecaudoEscenarioIdAndValidoAndEscenarioRecaudoObligatorio(
            @Param("numero") String numeroSolicitud,
            @Param("id") long idEscenario, 
            @Param("valido") long valido,
            @Param("obligatorio") long obligatorio);

}