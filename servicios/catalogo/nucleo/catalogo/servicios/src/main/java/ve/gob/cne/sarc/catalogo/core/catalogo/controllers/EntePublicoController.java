package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.EntePublicoBF;
import ve.gob.cne.sarc.comunes.catalogo.EntePublico;

/**
 * EntePublicoController.java
 * @descripcion Controlador REST de Servicio Catalogo - Ente Publico
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class EntePublicoController {

    private static final Logger LOG = LoggerFactory.getLogger(EntePublicoController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private EntePublicoBF entePublicoBF;

    /**
     * Metodo para consultar todos los Entes Publicos
     *
     * @return Lista de {@link EntePublico}
     */
    @RequestMapping(value = "/EntePublico/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<EntePublico> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "EntePublico");
        return entePublicoBF.consultarTodos();
    }
}
