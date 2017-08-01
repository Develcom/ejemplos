package ve.gob.cne.sarc.acta.core.acta.business;

import java.util.List;
import java.util.Map;
import ve.gob.cne.sarc.comunes.acta.ActaPrimigenia;

import ve.gob.cne.sarc.comunes.acta.DecisionJudicial;
import ve.gob.cne.sarc.comunes.acta.Extemporanea;
import ve.gob.cne.sarc.comunes.acta.Insercion;
import ve.gob.cne.sarc.comunes.acta.MedidaProteccion;
import ve.gob.cne.sarc.comunes.nacimiento.Nacimiento;
import ve.gob.cne.sarc.comunes.oficina.Ore;
import ve.gob.cne.sarc.comunes.plantilla.Acta;

/**
 * BusinessFacade con la logica de negocio de manejo de Acta
 *
 * @descripcion BusinessFacade con la logica de negocio de manejo de Acta
 * @version 1.0 13/07/2016
 * @author Elvin.Gonzalez
 *
 */
public interface ActaBF {

    /**
     * Busca un Acta segun la informacion suministrada
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @return Un acta si existe en caso contrario null
     */
    Acta consultarActa(Acta acta);

    /**
     * Busca un listado de actas segun la informacion suministrada
     *
     * @param acta Objecto con la informacion requeridad para la busqueda
     * @return Un listado de Actas
     */
    List<Acta> consultarActaLista(Acta acta);

    /**
     * Interfaz del metodo responsable de actualizar un acta segun la
     * informacion suministrada
     *
     * @param acta pojo que contiene la informacion del Acta
     * @return verdadero si se actualizo falso en caso contrario
     */
    boolean actualizarActa(Acta acta);

    /**
     * Interfaz del metodo responsable de validar si el certificado medico
     * existe.
     *
     * @param codigoTramite String que describe el codigo del tramite,
     * @param numeroCertificado long numero del certificado medico
     * @return Verdadero si existe el certificado medico, en caso contrario
     * falso
     */
    public boolean validarCertificadoMedico(String codigoTramite,
            long numeroCertificado);

    /**
     * Interfaz del metodo responsable de buscar si existe o no el numero de
     * acta
     *
     * @param numeroActa String que describe el numero de acta,
     * @return Map<String,String> que permite almacenar valores
     */
    public Map<String, String> existeActa(String numeroActa);

    /**
     * Interfaz del metodo responsable de buscar el numero de acta dado un
     * numero de solicitud.
     *
     * @param nroSolicitud String que contiene el numero de solicitud
     * @return String que contiene el Numero de Acta
     */
    String buscarNumeroActaPorSolic(String nroSolicitud);

    /**
     * Actualiza el estatus de un acta
     *
     * @param idEstatusActa String que contiene el id del estatus del Acta
     * @param acta Acta pojo con la informacion del acta
     * @return acta pojo con la informacion del acta
     * @throws java.lang.Exception
     */
    Acta actualizarEstatusActa(Long idEstatusActa, Acta acta) throws Exception;

    /**
     * Metodo que permite guardar el ORE
     *
     * @param numSolicitud String que describe el numero de la Solicitud
     * @return objeto de tipo Ore
     * @throws java.lang.Exception
     */
    Ore guardarORE(String numSolicitud) throws Exception;

    /**
     * Metodo responsable de buscar Ore dado un numero de Solicitud
     *
     * @param numSolicitud String que contiene el numero de solicitud
     * @return Ore, instancia de objeto que contiene la informacion del ORE
     * @throws java.lang.Exception
     */
    Ore consultarOre(String numSolicitud) throws Exception;

    /**
     * Metodo que permite guardar la insercion
     *
     * @param insercion pojo con la informacion de Insercion
     * @return objeto de tipo Insercion
     * @throws java.lang.Exception
     */
    boolean guardarInsercion(Insercion insercion) throws Exception;

    /**
     * Metodo responsable de consultar la insercion
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de la Insercion
     * @throws java.lang.Exception
     */
    Insercion consultarInsercion(String numSolicitud) throws Exception;

    /**
     * Metodo que permite guardar la Decision Judicial
     *
     * @param decisionJudicial pojo con la informacion de Decision Judicial
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     */
    boolean guardarDecisionJudicial(DecisionJudicial decisionJudicial);

    /**
     * Metodo responsable de consultar la decision Judicial
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de la Decision Judicial
     * @throws java.lang.Exception
     */
    DecisionJudicial consultarDecisionJudicial(String numSolicitud) throws Exception;

    /**
     * Metodo que permite guardar la Medida de Proteccion
     *
     * @param medidaProteccion pojo con la informacion de Medida Proteccion
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     */
    boolean guardarMedidaProteccion(MedidaProteccion medidaProteccion);

    /**
     * Metodo responsable de consultar la medida de proteccion
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de la Medida de Proteccion
     * @throws java.lang.Exception
     */
    MedidaProteccion consultarMedidaProteccion(String numSolicitud) throws Exception;

    /**
     * Metodo que permite guardar Extemporanea
     *
     * @param extemporanea pojo con la informacion de Extemporanea
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     * @throws java.lang.Exception
     */
    boolean guardarExtemporanea(Extemporanea extemporanea) throws Exception;

    /**
     * Metodo responsable de consultar Extemporanea
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de Extemporanea
     * @throws java.lang.Exception
     */
    Extemporanea consultarExtemporanea(String numSolicitud) throws Exception;

    /**
     * Metodo que permite guardar Nacimiento
     *
     * @param nacimiento pojo con la informacion de Nacimiento
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     * @throws java.lang.Exception
     */
    boolean guardarNacimiento(Nacimiento nacimiento) throws Exception;

    /**
     * Metodo que permite con consultar nacimiento
     *
     * @param numSolicitud String que contiene el numero de la Solicitud
     * @return objeto que contiene la informacion de Nacimiento
     */
    Nacimiento consultarNacimiento(String numSolicitud);

    /**
     * Metodo que permite guardar acta primigenia
     *
     * @param actaPrimigenia ActaPrimigenia pojo con la informacion del acta
     * primigenia
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     * @throws java.lang.Exception
     */
    ActaPrimigenia guardarActaPrimigenia(ActaPrimigenia actaPrimigenia) throws Exception;

    /**
     * Metodo que permite consulta la informacion del acta primigenia dado el
     * numero de solciitud
     *
     * @param numSolicitud String Numero de la solicitud
     * @return Pojo ActaPrimigenia con la informacion del acta primigenia
     * @throws java.lang.Exception
     */
    ActaPrimigenia consultaActaPrimigenia(String numSolicitud) throws Exception;
}
