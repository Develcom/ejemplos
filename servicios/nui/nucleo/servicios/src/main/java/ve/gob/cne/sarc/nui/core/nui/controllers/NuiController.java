package ve.gob.cne.sarc.nui.core.nui.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.nui.core.nui.business.NuiBF;


/**
 * NuiController.java
 * @descripcion [Controlador del servicio REST nui]
 * @version 1.0 14/7/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/nui")
public class NuiController {

    private static final Logger LOG = LoggerFactory.getLogger(NuiController.class);
    @Autowired
    private NuiBF nuiBF;

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    /**
     * Busca el proximo numero de Nui disponible
     *
     * @param codigoOficina String codigo de la Oficina
     * @return long con el proximo numero de Nui
     */
    @RequestMapping(value = "/buscarProximoNui/{codigoOficina}", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public String buscarProximoNui(@PathVariable("codigoOficina") String codigoOficina) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " buscarProximoNui");
        return nuiBF.buscarProximoNui(codigoOficina);
    }

    /**
     * Busca un Ecu segun el numero de documento de identidad
     *
     * @param numeroDocIdentidad String que contiene el numero de documento de
     * identidad
     * @return true si existe el ecu y en caso contrario false
     */
    @RequestMapping(value = "/buscarExistenciaNUI/{numeroDocIdentidad}",
            method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public boolean buscarExistenciaNUI(@PathVariable("numeroDocIdentidad") String numeroDocIdentidad) {
        boolean resp;
        LOG.info("buscando el nui (controlador) ");
        resp = nuiBF.buscarExistenciaNUI(numeroDocIdentidad);
        return resp;
    }
}
