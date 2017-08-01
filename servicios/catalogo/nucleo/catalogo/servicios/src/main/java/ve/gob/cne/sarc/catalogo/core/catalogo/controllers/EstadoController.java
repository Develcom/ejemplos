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

import ve.gob.cne.sarc.catalogo.core.catalogo.business.EstadoBF;
import ve.gob.cne.sarc.comunes.catalogo.Estado;

/**
 * EstadoController.java
 * @descripcion Controlador REST de Servicio Catalogo - Estado
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class EstadoController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(EstadoController.class);

    @Autowired
    private EstadoBF estadoBF;

    /**
     * Metodo para consultar todos los Estados
     *
     * @return Lista de {@link Estado}
     */
    @RequestMapping(value = "/Estado/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Estado> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Estado");
        return estadoBF.consultarTodos();
    }

    /**
     * Metodo para consultar los Estados dado un Pais
     *
     * @param codigo String codigo de pais
     * @return Lista de {@link Estado}
     */
    @RequestMapping(value = "/Estado/consultarPorPais/{codigo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Estado> consultarPorPais(@PathVariable("codigo") String codigo) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarPorPais - " + "Estado");
        return estadoBF.consultarPorPais(Long.valueOf(codigo));
    }
}
