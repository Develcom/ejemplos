package ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.comunes.PropertyHelper;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;

/**
 * Controller para aplicar un proxy inverso para los servicios de SARCI, con esto se evita la necesidad de manejar
 * filtros CORS
 * 
 * 
 * @Descripcion
 * @author douglas.palacios
 * @fecha 26/5/2016
 * @version
 */
@RestController
@RequestMapping("/sarc/api/v1")
@Order(30)
public class ProxyController {

    @Autowired
    private PropertyHelper pmh;

    /**
     * 
     * getPropiedad expone como servicio web las propiedades que cumple con el prefijo de habilitadas para el cliente
     * web
     * 
     * @since 6/7/2016
     * @param propiedad
     *            este parametro es optional, sino esta presente se devuelven todas las propiedades del cliente web, si
     *            existe se devuelve solo la propiedad solicitada
     * @throws GeneralException
     * @return Map<String,Object> lista de propiedades consultadas
     * @author douglas.palacios
     * @version 1.0 Jira (Ejemplo: S2RC-001)
     */
    @RequestMapping(value = "propiedades", method = RequestMethod.GET, produces = "application/json")
    public Map<String, Object> getPropiedad(@RequestParam(required = false, value = "id") String propiedad)
            throws GenericException {

        if (propiedad != null) {
            return pmh.consultarPropiedad(propiedad);
        } else {
            return pmh.obtenerPropiedades();
        }

    }

}
