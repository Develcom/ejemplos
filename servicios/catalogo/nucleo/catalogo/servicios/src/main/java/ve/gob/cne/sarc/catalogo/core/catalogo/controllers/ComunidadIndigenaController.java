package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.ComunidadIndigenaBF;
import ve.gob.cne.sarc.comunes.catalogo.ComunidadIndigena;

/**
 * ComunidadIndigenaController.java
 * @descripcion Controlador REST de Servicio Catalogo - ComunidadIndigena
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class ComunidadIndigenaController {

    private static final Logger LOG = LoggerFactory.getLogger(ComunidadIndigenaController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private ComunidadIndigenaBF comunidadIndigenaBF;

    /**
     * Metodo para consultar todos las Comunidades Indigenas
     *
     * @return Lista de {@link ComunidadIndigena}
     */
    @RequestMapping(value = "/ComunidadIndigena/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<ComunidadIndigena> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "ComunidadIndigena");
        return comunidadIndigenaBF.consultarTodos();
    }
}
