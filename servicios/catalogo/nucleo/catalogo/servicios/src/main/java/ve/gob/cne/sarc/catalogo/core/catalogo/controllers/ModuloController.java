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

import ve.gob.cne.sarc.catalogo.core.catalogo.business.ModuloBF;
import ve.gob.cne.sarc.comunes.catalogo.Modulo;


/**
 * ModuloController.java
 * @descripcion Controlador REST de Servicio Catalogo - Modulo
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class ModuloController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(ModuloController.class);

    @Autowired
    private ModuloBF moduloBF;

    /**
     * Metodo para consultar todos los Modulos dado el codigo de la oficina
     *
     * @param codigo String que contiene el codigo de la oficina
     * @return Lista de {@link Modulo}
     */
    @RequestMapping(value = "/consultarModuloPorOficina/{codigo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Modulo> consultarModuloPorOficina(@PathVariable("codigo") String codigo) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarModuloPorOficina");
        return moduloBF.consultarModuloPorOficina(codigo);
    }
}
