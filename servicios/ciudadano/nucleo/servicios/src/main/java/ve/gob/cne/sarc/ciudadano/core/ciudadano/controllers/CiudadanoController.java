package ve.gob.cne.sarc.ciudadano.core.ciudadano.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.ciudadano.core.ciudadano.business.CiudadanoBF;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;

/**
 * CiudadanoController.java
 * @descripcion [ Controlador del servicio REST ciudadano]
 * @version 1.0 24/11/2015
 * @author Oscar Eubieda
 */
@RestController
@RequestMapping("/ciudadano")
public class CiudadanoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CiudadanoController.class);

    @Autowired
    private CiudadanoBF ciudadanoBF;

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando la ejecucion del metodo ";

    /**
     * 
     * Metodo consultarPorNumeroDocumento que consultar los datos de un Ciudadano con el numero de documento de
     * identidad.
     * 
     *
     * @param numeroDocIdentidad String que contiene el numero de documento de identidad del ciudadano
     * @return Ciudadano objeto del modelo ontologico que contiene la informacion del Ciudadano
     */
    @RequestMapping(value = "/consultarPorNumeroDocumento/{numeroDocIdentidad}", method = RequestMethod.GET)
    public Ciudadano consultarPorNumeroDocumento(@PathVariable("numeroDocIdentidad") String numeroDocIdentidad) {
        LOGGER.debug(METODO_EJECUCION_CONTROLLER + "consultarDetalle");
        return ciudadanoBF.consultarPorNumeroDocumento(numeroDocIdentidad);
    }
}
