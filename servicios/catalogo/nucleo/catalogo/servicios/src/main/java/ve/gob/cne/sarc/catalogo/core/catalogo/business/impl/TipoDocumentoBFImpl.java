package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.TipoDocumentoBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.SolicitanteTipDocDecMapper;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.SolicitanteTipDocEnteMapper;
import ve.gob.cne.sarc.catalogo.core.catalogo.util.Constantes;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocDecEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocEnteEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitanteTipDocDecRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitanteTipDocEnteRepository;

/**
 * TipoDocumentoBFImpl.java
 * @descripcion Implementacion del catalogo Tipo de Documento
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */ 
@Component
public class TipoDocumentoBFImpl implements TipoDocumentoBF {

    private static final Logger LOG = LoggerFactory.getLogger(TipoDocumentoBFImpl.class);

    @Autowired
    private SolicitanteTipDocDecRepository solicitanteTipDocDecRepository;

    @Autowired
    private SolicitanteTipDocEnteRepository solicitanteTipDocEnteRepository;

    @Autowired
    private SolicitanteTipDocDecMapper solicitanteTipDocDecMapper;

    @Autowired
    private SolicitanteTipDocEnteMapper solicitanteTipDocEnteMapper;

    /**
     * Este metodo es el responsable de listar los tipos de documentos de acuerdo al tipo de doc
     *
     * @param tipoSolicitante (E= ente publico) o (D = declarante)
     * @return Lista de {@link TipoDocumento}
     */
    @Override
    public List<TipoDocumento> consultarTipoDocumento(String tipoSolicitante) {
        
        LOG.debug("=====INICIANDO TipoDocumentoBFImpl.consultarTipoDocumento==========");
        List<TipoDocumento> tipodocumentoList;

        if (tipoSolicitante.equalsIgnoreCase(Constantes.TIPO_SOLICITANTE_DECLARANTE)) {
            List<SolicitanteTipDocDecEntidad> solicitanteTipDocDecEntidadList = 
                    (List<SolicitanteTipDocDecEntidad>) solicitanteTipDocDecRepository.findAll();
            tipodocumentoList = solicitanteTipDocDecMapper.entitiesToBOs(solicitanteTipDocDecEntidadList);
        } else {

            List<SolicitanteTipDocEnteEntidad> solicitanteTipDocEnteEntidadList = 
                    (List<SolicitanteTipDocEnteEntidad>) solicitanteTipDocEnteRepository.findAll();
            tipodocumentoList = solicitanteTipDocEnteMapper.entitiesToBOs(solicitanteTipDocEnteEntidadList);
        }

        return tipodocumentoList;
    }

}
