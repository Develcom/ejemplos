package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.DocumentosAutenticacionBF;

/**
 * DocumentosAutenticacionController.java
 * @descripcion Controlador REST de Servicio Catalogo - Documentos de Autenticacion
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/catalogo")
public class DocumentosAutenticacionController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentosAutenticacionController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";

    @Autowired
    private DocumentosAutenticacionBF documentosAutenticacionBF;

    /**
     * Metodo para consultar todos los Documentos de Autenticacion
     *
     * @return Lista de {@link String}
     */
    @RequestMapping(value = "/consultarDocumentosAutenticados", method = RequestMethod.GET)
    @ResponseBody
    public LinkedHashMap<String, String> consultarDocumentosAutenticados() {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "ConsultarTodos - " + "DocumentosAutenticados");
        return documentosAutenticacionBF.consultarDocumentosAutenticados();
    }

}
