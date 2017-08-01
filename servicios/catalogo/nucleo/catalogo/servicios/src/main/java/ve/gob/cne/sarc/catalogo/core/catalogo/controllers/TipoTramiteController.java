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

import ve.gob.cne.sarc.catalogo.core.catalogo.business.TipoTramiteBF;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;

/**
 * TipoTramiteController.java
 * @descripcion  Controlador REST de Servicio Catalogo - Tipo Tramite
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class TipoTramiteController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(TipoTramiteController.class);

    @Autowired
    private TipoTramiteBF tipoTramiteBF;

    /**
     * Metodo para consultar los Tramites por Modulo
     *
     * @param codigoModulo String que contiene el codigo del Modulo,
     * @param codTipoSolicitante String de codigo del tipo de solicitud
     * @return Lista de {@link Tramite}
     */
    @RequestMapping(value = "/TipoTramite/consultarTramitePorModulo/{codigoModulo}/{codTipoSolicitante}",
            method = RequestMethod.GET)
    @ResponseBody
    public List<Tramite> consultarTramitePorModulo(@PathVariable("codigoModulo") String codigoModulo,
            @PathVariable("codTipoSolicitante") String codTipoSolicitante) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTramitePorModulo");
        return tipoTramiteBF.consultarTramitePorModulo(codigoModulo, codTipoSolicitante);
    }
}
