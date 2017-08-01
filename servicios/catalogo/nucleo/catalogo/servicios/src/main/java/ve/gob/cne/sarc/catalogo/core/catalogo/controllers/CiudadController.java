package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.CiudadBF;
import ve.gob.cne.sarc.comunes.catalogo.Ciudad;

/**
 * Controlador REST de Servicio Catalogo - Ciudad.java
 * @descripcion Controlador REST de Servicio Catalogo - Ciudad
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class CiudadController {

    private static final Logger LOG = LoggerFactory.getLogger(CiudadController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private CiudadBF ciudadBF;

    /**
     * Metodo para consultar todos las Ciudades
     *
     * @return Lista de {@link Ciudad}
     */
    @RequestMapping(value = "/Ciudad/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Ciudad> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Ciudad");
        return ciudadBF.consultarTodos();
    }
}
