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

import ve.gob.cne.sarc.catalogo.core.catalogo.business.MunicipioBF;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;

/**
 * MunicipioController.java
 * @descripcion Controlador REST de Servicio Catalogo - Municipio
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class MunicipioController {

    private static final Logger LOG = LoggerFactory.getLogger(MunicipioController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private MunicipioBF municipioBF;

    /**
     * Metodo para consultar todos los Municipios
     *
     * @return Lista de {@link Municipio}
     */
    @RequestMapping(value = "/Municipio/consultarTodos", method = RequestMethod.GET)
    @ResponseBody
    public List<Municipio> consultarTodos() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "Municipio");
        return municipioBF.consultarTodos();
    }

    /**
     * Metodo para consultar los Municipios dado un Estado
     *
     * @param codigo String codigo de estado
     * @return Lista de {@link Municipio}
     */
    @RequestMapping(value = "/Municipio/consultarPorEstado/{codigo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Municipio> consultarPorEstado(@PathVariable("codigo") String codigo) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarPorEstado - " + "Municipio");
        return municipioBF.consultarPorEstado(Long.valueOf(codigo));
    }
}
