package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.PaisBF;
import ve.gob.cne.sarc.comunes.catalogo.Pais;

/**
 * PaisController.java
 * @descripcion Controlador REST de Servicio Catalogo - Pais
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class PaisController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(PaisController.class);

    @Autowired
    private PaisBF paisBF;

    /**
     * Metodo para consultar todos los Paises
     *
     * @return Lista de {@link Pais}
     */
    @RequestMapping(value = "/Pais/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Pais> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Pais");
        return paisBF.consultarTodos();
    }
}
