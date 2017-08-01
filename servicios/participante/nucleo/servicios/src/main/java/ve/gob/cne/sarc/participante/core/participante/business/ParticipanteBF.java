package ve.gob.cne.sarc.participante.core.participante.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;

/**
 * ParticipanteBF.java
 *
 * @descripcion BusinessFacade con la logica de negocio de manejo de
 * participantes.
 * @version 1.0 24/11/2015
 * @author Anabell De Faria
 */
public interface ParticipanteBF {

    /**
     * Interfaz del metodo registrarParticipantes que permite registrar una
     * lista de participantes a una solicitud.
     *
     * @param solicitud objeto del modelo ontologico
     * @param token String Token
     * @return {@link solicitud} objeto del modelo ontologico
     * @throws java.lang.Exception
     */
    public Solicitud registrarParticipantes(Solicitud solicitud, String token) throws Exception;

    /**
     * Interfaz del metodo buscarTipoParticipantePorTramite que permite buscar
     * los tipos de participantes asociados a un tramite; y si se desean los
     * declarantes unicamente para dicho tramite.
     *
     * @param codigoTramite String que contiene el codigo del tramite
     * @param tipoDeclarante String que contiene el tipo de declarante
     * @return Lista de {@link TipoParticipante}
     * @throws java.lang.Exception
     */
    public List<TipoParticipante> buscarTipoParticipantePorTramite(
            String codigoTramite, String tipoDeclarante) throws Exception;

    /**
     * Interfaz del metodo consultarParticPorActa que permite buscar los
     * participantes dado un numero de acta
     *
     * @param numeroActa String que contiene el numero de Acta
     * @return Lista de {@link Participante}
     */
    public List<Participante> consultarParticPorActa(
            String numeroActa);

    /**
     * Interfaz del metodo consultarParticPorSolicitud que permite buscar los
     * participantes dado un numero de solicitud y un indicador (D:declarante,
     * T:Todos)
     *
     * @param numeroSolicitud String que contiene el numero de Solicitud
     * @param esDeclarante String que contiene el indicador del tipo de
     * participantes a buscar
     * @return Lista de {@link Participante}
     * @throws java.lang.Exception
     */
    public List<Participante> consultarParticPorSolicitud(
            String numeroSolicitud, String esDeclarante) throws Exception;

    /**
     * Interfaz del metodo consultarParticPorTipo que permite consultar un
     * participante dado un numero de solicitud y un codigo tipo de participante
     *
     * @param numeroSolicitud String numero de solicitud
     * @param codigoTipo String[] Un Array de string de codigo tipo de
     * participante
     * @return Participante
     * @throws java.lang.Exception
     */
    public List<Participante> consultarParticPorTipo(String numeroSolicitud,
            String[] codigoTipo) throws Exception;

    /**
     * Interfaz del metodo actualizarParticipante que permite registrar un
     * participante
     *
     * @param participante objeto del modelo ontologico
     * @param numeroSolic String Numero de solicitud
     * @return {@link participante} objeto del modelo ontologico
     * @throws java.lang.Exception
     */
    public Participante actualizarParticipante(Participante participante, String numeroSolic) throws Exception;

    /**
     * Interfaz del metodo registrarDeclaracionJurada que permite registrar una
     * declaracion jurada del participante
     *
     * @param declaracionJurada objeto de tipo DeclaracionJurada
     * @return Verdadero si registra la declaracion jurada
     * @throws java.lang.Exception
     */
    public boolean registrarDeclaracionJurada(DeclaracionJurada declaracionJurada) throws Exception;

    /**
     * Interfaz del metodo registrarConsejoComunal que permite registrar una
     * carta consejo comunal
     *
     * @param cartaConsejoComunal objeto de tipo CartaConsejoComunal
     * @return Verdadero si registra la carta consejo comunal
     * @throws java.lang.Exception
     */
    public boolean registrarConsejoComunal(CartaConsejoComunal cartaConsejoComunal) throws Exception;

    /**
     * Interfaz del metodo consultarDeclaracionJurada que permite consultar
     * declaraciones juradas
     *
     * @param numSolicitud numero de solicitud
     * @return Lista de {@link DeclaracionJurada}
     */
    public List<DeclaracionJurada> consultarDeclaracionJurada(String numSolicitud);

    /**
     * Interfaz del metodo registrarParticipantes que permite registrar una
     * lista de participantes a una solicitud.
     *
     * @param numSolicitud numero de la solicitud
     * @param participante objeto del modelo ontologico
     * @return {@link solicitud} objeto del modelo ontologico
     * @throws java.lang.Exception
     */
    public Participante registrarParticPorSolicitud(String numSolicitud, Participante participante) throws Exception;

    /**
     * Busca un listado de participante por el documento de identidad, para
     * obtener sus actas relacionada
     *
     * @param numeroDocumento Numero de documento de identidad del participante
     * @param flag Bandera que indica si son actas propias (verdadero) o relacionada (falsa)
     * @return Listado de Actas con su participante
     * @throws Exception
     */
    public List<Acta> consultarParticipantePorDocumento(String numeroDocumento, boolean flag) throws Exception;
}
