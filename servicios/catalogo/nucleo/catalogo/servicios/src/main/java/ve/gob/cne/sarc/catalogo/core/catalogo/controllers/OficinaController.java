package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.OficinaBF;
import ve.gob.cne.sarc.comunes.oficina.Oficina;

/**
 * OficinaController.java
 *
 * @descripcion Controlador REST de Servicio Catalogo - Oficina
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class OficinaController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(OficinaController.class);

    @Autowired
    private OficinaBF oficinaBF;

    /**
     * Metodo para consultar todas las Oficinas
     *
     * @return Lista de {@link Oficina}
     */
    @RequestMapping(value = "/Oficina/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Oficina> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Oficina");
        return oficinaBF.consultarTodos();
    }

    /**
     * Metodo para consultar proximo numero consecutivo
     *
     * @param codigoOficina String codigo de la oficina
     * @return long proximo nro consecutivo
     */
    @RequestMapping(value = "/Oficina/proximoNroConsecutivo/{codigoOficina}", method = RequestMethod.GET)
    @ResponseBody
    public long proximoNroConsecutivo(@PathVariable("codigoOficina") String codigoOficina) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "proximoNroConsecutivo - " + "Oficina");
        return oficinaBF.proximoNroConsecutivo(codigoOficina);
    }
}
