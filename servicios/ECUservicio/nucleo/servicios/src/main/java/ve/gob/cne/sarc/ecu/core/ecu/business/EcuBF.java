package ve.gob.cne.sarc.ecu.core.ecu.business;

import java.util.List;

import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.ecu.core.ecu.exception.EcuException;

/**
 * EcuBF.java
 *
 * @descripcion [Interfaz para la logica de negocio del controlador]
 * @version 1.0 14/7/2016
 * @author Erick Escalona
 */
public interface EcuBF {

    /**
     * Valida un ecu y devuelve una lista de participante
     *
     * @param ciudadano Ciudadano pojo con la informacion del ciudadano
     * @return Lista de Objetos de tipo Participante
     */
    List<Participante> validarEcu(Ciudadano ciudadano);

    /**
     * Vincula el acta con el ecu y los participantes
     *
     * @param solicitud solicitud a vincular
     * @return Verdadero si se vinculo falso en caso contrario
     * @throws EcuException
     */
    boolean vincularActaEcuParticipante(String solicitud) throws EcuException;

    /**
     * Actualiza un Ecu
     *
     * @param codigoEstadoEcu String que contiene el estado del Ecu
     * @param participante Participante pojo con la informacion del participante
     * @return ecu pojo con la informacion del ecu
     */
    Ecu actualizarEcu(String codigoEstadoEcu, Participante participante);

    /**
     * Busca un Ecu segun su numero de documento de identidad
     *
     * @param numeroDocIdentidad String numero de documento de identidad
     * @return true si existe el ecu y en caso contrario false
     */
    boolean buscarExistenciaECU(String numeroDocIdentidad);

    /**
     * Consulta el Ecu de un Ciudadano dado el numero de documento y el tipo de documento de identificacon
     *
     * @param numeroDocumento String Numero de documento de identificacion del ciudadano
     * @param tipoDocumento String Tipo de documento de identificacion (CED:Cedula, PAS:Pasaporte, NUI: Nui)
     * @return Pojo Ecu con los datos del Ecu del ciudadano
     */
    Ecu consultarECU(String numeroDocumento, String tipoDocumento);

}
