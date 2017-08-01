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

import ve.gob.cne.sarc.catalogo.core.catalogo.business.TipoDocumentoBF;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;

/**
 * TipoDocumentoController.java
 * @descripcion  Controlador REST de Servicio Catalogo - Tipo Documento
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */

@RestController
@RequestMapping("/catalogo")
public class TipoDocumentoController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(TipoDocumentoController.class);

    @Autowired
    private TipoDocumentoBF tipoDocumentoBF;

    /**
     * Metodo para consultar el Tipo Documento dado un Tipo
     *
     * @param tipo String que contiene el Tipo de Documento
     * @return Lista de {@link TipoDocumento}
     */
    @RequestMapping(value = "/TipoDocumento/{tipo}", method = RequestMethod.GET)
    @ResponseBody
    public List<TipoDocumento> consultarTipoDocumento(@PathVariable("tipo") String tipo) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "consultarTipoDocumento");
        return tipoDocumentoBF.consultarTipoDocumento(tipo);
    }
}
