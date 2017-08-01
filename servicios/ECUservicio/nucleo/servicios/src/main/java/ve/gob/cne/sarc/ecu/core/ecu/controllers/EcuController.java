package ve.gob.cne.sarc.ecu.core.ecu.controllers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.comunes.ciudadano.Nui;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.ecu.core.ecu.business.EcuBF;
import ve.gob.cne.sarc.ecu.core.ecu.exception.EcuException;

/**
 * EcuController.java
 *
 * @descripcion [Controlador del servicio REST ecu ]
 * @version 1.0 14/7/2016
 * @author Erick Escalona
 */
@RestController
@RequestMapping("/ecu")
public class EcuController {

    private static final Logger LOG = LoggerFactory.
            getLogger(EcuController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando la ejecucion del metodo ";

    @Autowired
    private EcuBF ecuBF;

    /**
     * Valida que exista un ECU para el ciudadano
     *
     * @param ciudadano Ciudadano pojo con la informacion del ciudadano
     * @return Lista de participante del ciudadano
     */
    @RequestMapping(value = "/validarEcu", method = RequestMethod.POST,
            produces = {"application/json"})
    public List<Participante> validarEcu(@RequestBody Ciudadano ciudadano) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "validarEcu");
        
        imprimirObjeto(ciudadano);

        List<Participante> listParticipante;

        LOG.info("validando el ecu del ciudadano (controller) "
                + ciudadano.getPrimerNombre()
                + " " + ciudadano.getPrimerApellido());

        LOG.info("ECU (controller) " + ciudadano.getEcu());

        listParticipante = ecuBF.validarEcu(ciudadano);

        if (listParticipante.isEmpty()) {
            listParticipante = null;
        }

        return listParticipante;

    }

    /**
     * Vincula el acta con el ecu y los participantes
     *
     * @param solicitud solictido a vincular
     * @return Verdadero si se vinculo falso en caso contrario
     * @throws EcuException
     */
    @RequestMapping(value = "/vincular/acta/ecu/participante/{solicitud}",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public boolean vincularActaEcuParticipante(@PathVariable("solicitud") String solicitud) throws EcuException {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "vincularActaEcuParticipante");

        boolean resp;

        resp = ecuBF.vincularActaEcuParticipante(solicitud);

        LOG.info("vincula acta ecu participante " + resp);

        return resp;
    }

    /**
     * Actualiza un Ecu
     *
     * @param codigoEstadoEcu String que contiene el estado del Ecu
     * @param participante Participante pojo con la informacion del participante
     * @return ecu pojo con la informacion del ecu
     */
    @RequestMapping(value = "/actualizarEcu/{codigoEstadoEcu}",
            method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public Ecu actualizarEcu(@PathVariable("codigoEstadoEcu") String codigoEstadoEcu,
            @RequestBody Participante participante) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "actualizarEcu");
        
        imprimirObjeto(participante);

        return ecuBF.actualizarEcu(codigoEstadoEcu, participante);
    }

    /**
     * Busca un Ecu segun el numero de documento de identidad
     *
     * @param numeroDocIdentidad String que contiene el numero de documento de
     * identidad
     * @return true si existe el ecu y en caso contrario false
     */
    @RequestMapping(value = "/buscarExistenciaECU/{numeroDocIdentidad}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public boolean buscarExistenciaECU(@PathVariable("numeroDocIdentidad") String numeroDocIdentidad) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "buscarExistenciaECU");

        boolean respEcu;
        LOG.info("buscando el ecu (controlador) ");
        respEcu = ecuBF.buscarExistenciaECU(numeroDocIdentidad);
        return respEcu;
    }

    /**
     * Consulta el ECU de un ciudadano dado el numero de documento y el tipo de
     * documento de identificacion
     *
     * @param numeroDocumento String Numero de documento de identificacion
     * @param tipoDocumento String Tipo de documento de identificacion
     * (CED:Cedula, PAS:Pasaporte, NUI: Nui)
     * @return Pojo Ecu con los datos del Ecu del ciudadano
     */
    @RequestMapping(value = "/consultarECU/{numeroDocumento}/{tipoDocumento}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public Ecu consultarECU(@PathVariable("numeroDocumento") String numeroDocumento,
            @PathVariable("tipoDocumento") String tipoDocumento) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarECU");

        return ecuBF.consultarECU(numeroDocumento, tipoDocumento);
    }

    /**
     * para pruebas
     *
     * @return Objeto de tipo Ciudadano
     */
    @RequestMapping(value = "/crearJson", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Ciudadano crearJson() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date fecha;
        Ciudadano cd = new Ciudadano();
        Ecu ecu = new Ecu();
        Nui nui = new Nui();
        List<Nui> nuis = new ArrayList<>();
        List<DocumentoIdentidad> listDI = new ArrayList<>();
        DocumentoIdentidad di = new DocumentoIdentidad();

        try {
            fecha = sdf.parse(sdf.format(new Date()));

            di.setNumero("V-12345678");
            listDI.add(di);

            cd.setDocumentoIdentidad(listDI);
            cd.setEstadoCivil("S");
            cd.setPrimerNombre("Maarlena");
            cd.setSegundoNombre("Nitsuma");
            cd.setPrimerApellido("Castillo");
            cd.setSegundoApellido("Salazar");

            nui.setCodigo("12345678");
            nui.setEstatusNui("HAB");
            nui.setIdNacionalidad("VEN");
            nui.setFechaVencimiento(sdf.parse("15/02/20"));
            nuis.add(nui);

            ecu.setListaNui(nuis);
            ecu.setFechaCreacion(fecha);
            cd.setEcu(ecu);

        } catch (ParseException ex) {
            LOG.error("error fecha " + ex);
        }

        return cd;
    }

    /**
     * Metodo que imprime el objeto de entrada de cualquier método que lo
     * implemente
     *
     * @param obj Objeto que es entrada de cualquier metodo
     */
    private void imprimirObjeto(Object obj) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("problemas al mostra el json ", ex);
        }
        LOG.info("Imprimir Json de Entrada: " + obj.getClass().getName() + " - " + json);

    }

}
