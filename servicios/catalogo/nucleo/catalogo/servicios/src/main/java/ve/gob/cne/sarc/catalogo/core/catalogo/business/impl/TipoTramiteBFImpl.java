package ve.gob.cne.sarc.catalogo.core.catalogo.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.business.TipoTramiteBF;
import ve.gob.cne.sarc.catalogo.core.catalogo.mapper.TipoTramiteMapper;
import ve.gob.cne.sarc.catalogo.core.catalogo.util.Constantes;
import ve.gob.cne.sarc.comunes.catalogo.Tramite;
import ve.gob.cne.sarc.persistencia.entidades.TramiteEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.TramiteRepository;

/**
 * TipoTramiteBFImpl.java
 *
 * @descripcion Implementacion del catalogo para los tramites
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Component
public class TipoTramiteBFImpl implements TipoTramiteBF {

    private static final Logger LOG = LoggerFactory.getLogger(TipoTramiteBFImpl.class);

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private TipoTramiteMapper tipoDocumentoMapper;

    /**
     * Este metodo es el responsable de buscar los codigos de tramites a ignorar
     *
     * @param codigoModulo String codigo de modulo,
     * @return codigo String codigo de tramite
     */
    private String buscarCodigoIgnorar(String codigoModulo) {
        String codigo = null;

        switch (codigoModulo) {
            case Constantes.CONST_NACIMIENTO:
                codigo = Constantes.CONST_REG_ADOPCION;
                break;
            case Constantes.CONST_DEFUNCION:
                codigo = Constantes.CONST_PER_INHUMACION_CREMACION;
                break;
            default:
                break;
        }
        return codigo;
    }

    /**
     * Este metodo private es el responsable de listar tramite
     *
     * @param codigoModulo String codigo de modulo, listaTramite Lista de Tramites
     * @return Lista de {@link Tramite}
     */
    private List<Tramite> listarTramite(String codigoModulo,
            List<Tramite> listaTramite) {

        List<Tramite> listaResult = new ArrayList<>();
        String codigo;

        codigo = buscarCodigoIgnorar(codigoModulo);

        if (codigo != null) {

            for (Tramite tramite : listaTramite) {
                if (!tramite.getCodigo().equals(codigo)) {
                    listaResult.add(tramite);
                }
            }

        }
        return listaResult;
    }

    /**
     * Este metodo es el responsable de listar los tramites de acuerdo al codigo del modulo y el codigo del tipo de
     * Solicitante
     *
     * @param codigoModulo String codigo de modulo,
     * @param codSolicitante String codigo de tipo Solicitante
     * @return Lista de {@link Tramite}
     */
    @Override
    public List<Tramite> consultarTramitePorModulo(String codigoModulo,
            String codTipoSolicitante) {
        LOG.debug("=====INICIANDO TipoTramiteBFImpl.ConsultarTramitePorModulo==========");

        List<TramiteEntidad> listaTramitesEntidad;
        List<Tramite> listaResultado;

        listaTramitesEntidad = tramiteRepository.buscarPorCodigoModulo(codigoModulo);
        List<Tramite> listaTramite = tipoDocumentoMapper.entitiesToBOs(listaTramitesEntidad);

        if (codTipoSolicitante.equals(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO)
                && (codigoModulo.equals(Constantes.CONST_NACIMIENTO)
                || codigoModulo.equals(Constantes.CONST_DEFUNCION))) {

            listaResultado = listarTramite(codigoModulo, listaTramite);
        } else {
            listaResultado = listaTramite;
        }

        return listaResultado;
    }

}
