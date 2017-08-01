package ve.gob.cne.sarc.persistencia.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;

/**
 * ParticipanteRepository.java
 *
 * @descripcion Clase Repositorio de la entidad ParticipanteEntidad
 * @author Oscar Montilla
 * @version 1.0 08/09/2016
 *
 */
public interface ParticipanteRepository extends CrudRepository<ParticipanteEntidad, Long> {

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorNumeroActa que permite consultar los datos de un Participante por el
     * Numero de Acta
     * @param numeroActa Numero del acta.
     * @return List<ParticipanteEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * Participante.
     *
     *
     */
    List<ParticipanteEntidad> buscarPorNumeroActa(@Param("numeroActa") String numeroActa);

    /**
     *
     * @metodo Metodo de acceso a datos BuscarPorNumeroDocIdentidad que permite consultar los datos de un Participante
     * por el id
     * @param numeroDocIdentidad Numero de documento de identidad.
     * @return Participante - Objeto del modelo ontologico que contiene la informacion de Participante.
     *
     *
     */
    public ParticipanteEntidad buscarPorNumeroDocIdentidad(@Param("numeroDocIdentidad") String numeroDocIdentidad);

    /**
     *
     *
     * @metodo Metodo de acceso a datos BuscarPorSolicitudYTipoParticipante que permite consultar los datos de un
     * Participante por el NU_SOLICITUD y id de tipo participante
     * @param numero Numero de Solicitud.
     * @param id Long id tipo participante.
     * @return ParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de Participante.
     *
     *
     */
    public ParticipanteEntidad buscarPorSolicitudYTipoParticipante(@Param("numero") String numero,
            @Param("id") Long id);

    /**
     *
     * @metodo Metodo de acceso a datos buscarPorSolicitud que permite consultar los datos de un Participante por el
     * NU_SOLICITUD
     * @param numero String Numero de Solicitud.
     * @return ParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de Participante.
     */
    public ParticipanteEntidad buscarPorSolicitud(@Param("numero") String numero);

    /**
     *
     *
     * @metodo Metodo de acceso a datos buscarPorSolicitud que permite consultar los datos de un Participante por el
     * NU_SOLICITUD
     * @param numero String Numero de Solicitud.
     * @return List<ParticipanteEntidad> - Lista de Objetos del modelo ontologico que contiene la informacion de
     * Participante.
     */
    public List<ParticipanteEntidad> buscarPorNumeroSolicitud(@Param("numero") String numero);

    /**
     *
     * @metodo Metodo de acceso a datos findByTipoParticipanteCodigoEndingWithAndActaSolicitudNumero que permite
     * consultar los datos de una lista de Participantes por el NU_SOLICITUD y por la ultima letra en el
     * ParticipanteCodigo.
     * @param letrafinal String letra final para buscar por ParticipanteCodigo
     * @param numero String numero de NU_SOLICITUD
     * @return ParticipanteEntidad - Objeto del modelo ontologico que contiene la informacion de Participante.
     */
    List<ParticipanteEntidad> findByTipoParticipanteCodigoEndingWithAndActaSolicitudNumero(
            @Param("letrafinal") String letrafinal, @Param("numero") String numero);

    /**
     *
     * @metodo Metodo de acceso de datos a findByActaSolicitudNumeroAndTipoParticipanteIn que permite consultar los
     * datos de un participante por numero de solicitud y una lista de tipo participante.
     * @param numero String de Numero de solicitud.
     * @param tipoParticipanteEntidad Lista de objetos de Tipo participante
     * @return List<ParticipanteEntidad> - Lista de objetos del modelo ontologico de Participante.
     */
    List<ParticipanteEntidad> findByActaSolicitudNumeroAndTipoParticipanteIn(@Param("numero") String numero,
            @Param("tipoParticipanteEntidad") List<TipoParticipanteEntidad> tipoParticipanteEntidad);

    /**
     *
     * @metodo Metodo de acceso de datos findByNumeroDocIdentidadAndTipoParticipanteCodigoAndActaSolicitudNumero que
     * permite consultar los datos de un participante por numero de numeroDocIdentidad, codigo de tipo participante y
     * Numero de solicitud para obtener un participante.
     * @param numeroDocIdentidad String Numero de documento de Participante.
     * @param codigo String codigo de tipo participante.
     * @param numero String Numero de solicitud
     * @return ParticipanteEntidad - objetos del modelo ontologico de Participante.
     */
    public ParticipanteEntidad findByNumeroDocIdentidadAndTipoParticipanteCodigoAndActaSolicitudNumero(
            @Param("numeroDocIdentidad") String numeroDocIdentidad,
            @Param("codigo") String codigo, @Param("numero") String numero);
    /**
     * 
     * @metodo Metodo de consulta que obtiene una lista de participantes por el numero de documento de identidad del participante
     * @param numeroDocIdentidad String Numero de documento de Participante.
     * @return  List<ParticipanteEntidad> - Lista de objetos del modelo ontologico de Participante.
     */
    List<ParticipanteEntidad> findByNumeroDocIdentidad(@Param("numeroDocIdentidad") String numeroDocIdentidad );
}
