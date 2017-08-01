package ve.gob.cne.sarc.recaudo.core.recaudo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.comunes.catalogo.Recaudo;
import ve.gob.cne.sarc.recaudo.core.recaudo.business.RecaudoBF;

/**
 * RecaudoController.java
 *
 * @descripcion Controlador REST de Servicio Recaudo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/recaudo")
public class RecaudoController {

    private static final Logger LOG = LoggerFactory.getLogger(RecaudoController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private RecaudoBF recaudoBF;

    /**
     * Metodo para consultar un Recaudo dado el codigo
     *
     * @param codigo String que contiene el codigo del Recaudo
     * @return Recaudo, instancia de objeto que contiene la informacion del
     * Recaudo
     */
    @RequestMapping(value = "/consultarPorCodigo/{codigo}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Recaudo consultarPorCodigo(@PathVariable("codigo") String codigo) {
        LOG.info(METODO_EJECUCION_CONTROLLER + "consultarPorCodigo");
        return recaudoBF.consultarPorCodigo(codigo);
    }

    /**
     * Metodo que permite registrar los recaudos solicitados en una solicitud
     *
     * @param numeroSolicitud String que contiene el numero de la solicitud
     * @param recaudos String[] que contiene los recaudos solicitados y la
     * obligatoriedad, su formato es {codRecaudo1:true, codRecaudo2:false,
     * codRecaudo3:true}
     * @return Verdadero si actualiza satisfactoriamente, en caso contrario
     * falso
     */
    @RequestMapping(value = "/registrarRecaudos/{numeroSolicitud}/{recaudos}", method = RequestMethod.POST)
    @ResponseBody
    public boolean registrarRecaudos(@PathVariable("numeroSolicitud") String numeroSolicitud,
            @PathVariable("recaudos") String[] recaudos) {

        LOG.info(METODO_EJECUCION_CONTROLLER + " registrarRecaudos numeroSol - " + numeroSolicitud
                + " recaudos - " + recaudos);

        return recaudoBF.registrarRecaudos(numeroSolicitud, recaudos);
    }

    /**
     *
     * Metodo que permite consultar los recaudos en una solicitud
     *
     * @param numSolicitud String numero de solicitud
     * @return lista de objetos de tipo Recaudo
     *
     */
    @RequestMapping(value = "/consultarRecaudos/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Recaudo> consultarRecaudos(@PathVariable("numSolicitud") String numSolicitud) {

        LOG.debug(METODO_EJECUCION_CONTROLLER + " consultarRecaudos");

        return recaudoBF.consultarRecaudos(numSolicitud);
    }
}
