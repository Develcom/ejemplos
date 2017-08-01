package ve.gob.cne.sarc.catalogo.core.catalogo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.TipoParticipanteBF;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;

/**
 * TipoParticipanteController.java
 * @descripcion  Controlador REST de Servicio Catalogo - Tipo Participante
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
 
@RestController
@RequestMapping("/catalogo")
public class TipoParticipanteController {

    private static final String METODO_EJECUCION_CONTROLLER = "Iniciando ejecucion del metodo ";
    private static final Logger LOG = LoggerFactory.getLogger(TipoParticipanteController.class);

    @Autowired
    private TipoParticipanteBF tipoParticipanteBF;

    /**
     * Metodo para consultar el Tipo Participante dado un codigo
     *
     * @param codigo String que contiene el codigo de un Tipo Participante
     * @param isDeclarante boolean que contiene si el Participante 
     * @return {@link TipoParticipante}
     */
    @RequestMapping(value = "/tipoParticipante/buscarPorCodigo/{codigo}/{isDeclarante}", method = RequestMethod.GET)
    @ResponseBody
    public TipoParticipante buscarPorCodigo(@PathVariable("codigo") String codigo, 
            @PathVariable("isDeclarante") boolean isDeclarante) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + "tipoParticipante/buscarPorCodigo");
        return tipoParticipanteBF.buscarPorCodigo(codigo, isDeclarante);
    }
}
