package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.NacionalidadBF;
import ve.gob.cne.sarc.comunes.catalogo.Nacionalidad;

/**
 * NacionalidadController.java
 * @descripcion Controlador REST de Servicio Catalogo - Nacionalidad
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class NacionalidadController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(NacionalidadController.class);

    @Autowired
    private NacionalidadBF nacionalidadBF;

    /**
     * Metodo para consultar todos las Nacionalidades
     *
     * @return Lista de {@link Nacionalidad}
     */
    @RequestMapping(value = "/Nacionalidad/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Nacionalidad> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Nacionalidad");
        return nacionalidadBF.consultarTodos();
    }
}
