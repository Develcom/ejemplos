package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ve.gob.cne.sarc.catalogo.core.catalogo.business.OcupacionBF;
import ve.gob.cne.sarc.comunes.catalogo.Ocupacion;

/**
 * OcupacionController.java
 * @descripcion Controlador REST de Servicio Catalogo - Ocupacion
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class OcupacionController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(OcupacionController.class);

    @Autowired
    private OcupacionBF ocupacionBF;

    /**
     * Metodo para consultar todos las Ocupaciones
     *
     * @return Lista de {@link Ocupacion}
     */
    @RequestMapping(value = "/Ocupacion/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Ocupacion> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Ocupacion");
        return ocupacionBF.consultarTodos();
    }
}
