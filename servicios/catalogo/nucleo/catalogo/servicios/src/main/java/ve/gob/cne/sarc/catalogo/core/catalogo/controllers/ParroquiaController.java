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

import ve.gob.cne.sarc.catalogo.core.catalogo.business.ParroquiaBF;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;

/**
 * ParroquiaController.java
 * @descripcion  Controlador REST de Servicio Catalogo - Parroquia
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */

@RestController
@RequestMapping("/catalogo")
public class ParroquiaController {

    private static final Logger LOG = LoggerFactory.getLogger(ParroquiaController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private ParroquiaBF parroquiaBF;

    /**
     * Metodo para consultar todos las Parroquias
     *
     * @return Lista de {@link Parroquia}
     */
    @RequestMapping(value = "/Parroquia/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Parroquia> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarTodos - " + "Parroquia");
        return parroquiaBF.consultarTodos();
    }

    /**
     * Metodo para consultar las Parroquias dado un Municipio
     *
     * @param codigo String codigo de municipio
     * @return Lista de {@link Parroquia}
     */
    @RequestMapping(value = "/Parroquia/consultarPorMunicipio/{codigo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Parroquia> consultarPorMunicipio(@PathVariable("codigo") String codigo) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarPorMunicipio - " + "Parroquia");
        return parroquiaBF.consultarPorMunicipio(Long.valueOf(codigo));
    }
}
