package ve.gob.cne.sarc.solicitud.core.solicitud.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ve.gob.cne.sarc.comunes.catalogo.EntePublico;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.solicitante.DocumentoEntePublico;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.persistencia.entidades.EntePublicoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocDecEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipDocEnteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteTipoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitanteEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.EntePublicoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitanteTipDocDecRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitanteTipDocEnteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitanteTipoRepository;
import ve.gob.cne.sarc.solicitud.core.util.Constantes;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * SolicitanteMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y las clase del modelo de
 * datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class SolicitanteMapper {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitanteMapper.class);

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private SolicitanteTipDocDecRepository solicitanteTipDocDecRepository;

    @Autowired
    private SolicitanteTipDocEnteRepository solicitanteTipDocEnteRepository;

    @Autowired
    private EntePublicoRepository entePublicoRepository;

    @Autowired
    private SolicitanteTipoRepository solicitanteTipoRepository;

    /**
     *
     * Metodo de mapeo de los objetos SolicitanteEntidad y Solicitante
     *
     * @param solicitanteEntidad objeto de tipo SolicitanteEntidad
     * @return objeto de tipo Solicitante
     */
    public Solicitante solicitanteESolicitanteTOSolicitante(SolicitanteEntidad solicitanteEntidad) {
        Solicitante solicitante = new Solicitante();

        if (solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo()
                .equals(Constantes.TIPO_SOLICITANTE_DECLARANTE_B)) {

            Ciudadano ciudadano = new Ciudadano();
            ciudadano.setPrimerNombre(solicitanteEntidad.getPrimerNombre());
            ciudadano.setPrimerApellido(solicitanteEntidad.getPrimerApellido());
            ciudadano.setSegundoNombre(solicitanteEntidad.getSegundoNombre());
            ciudadano.setSegundoApellido(solicitanteEntidad.getSegundoApellido());

            if (solicitanteEntidad.getSolicitanteTipDocDec() != null) {
                TipoDocumento tipoDocumento = new TipoDocumento();
                tipoDocumento.setNombre(solicitanteEntidad.getSolicitanteTipDocDec().getNombre());
//                tipoDocumento.setCodigo(solicitanteEntidad.getSolicitanteTipDocDec().getCodigo());
                DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
                documentoIdentidad.setTipoDocumento(tipoDocumento);
                documentoIdentidad.setNumero(solicitanteEntidad.getNumeroDocumentoOficio());
                List<DocumentoIdentidad> documentosIdentidad = new ArrayList<>();
                documentosIdentidad.add(documentoIdentidad);
                ciudadano.setDocumentoIdentidad(documentosIdentidad);
            }

            solicitante.setTipo(solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo());
            solicitante.setCiudadano(ciudadano);
        } else if (solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo()
                .equals(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO_B)) {

            if (solicitanteEntidad.getSolicitanteTipDocEnte() != null) {
                DocumentoEntePublico documentoEntePublico = new DocumentoEntePublico();
                documentoEntePublico.setTipoDocumentoEntePublico(
                        solicitanteEntidad.getSolicitanteTipDocEnte().getNombre());
                documentoEntePublico.setEnteOrigen(solicitanteEntidad.getEntePublico().getNombre());
                EntePublico entePublico = new EntePublico();
                entePublico.setNombre(solicitanteEntidad.getEntePublico().getNombre());
//                entePublico.setCodigo(solicitanteEntidad.getEntePublico().getCodigo());
                documentoEntePublico.setEntePublico(entePublico);
                documentoEntePublico.setNumero(solicitanteEntidad.getNumeroDocumentoOficio());
                solicitante.setDocumentoEntePublico(documentoEntePublico);

            }
            solicitante.setTipo(solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo());

        }
        return solicitante;

    }

    /**
     ** Metodo de mapeo de los objetos Solicitante y SolicitanteEntidad
     *
     * @param solicitante objeto de tipo Solicitante
     * @return objeto de tipo SolicitanteEntidad
     */
    public SolicitanteEntidad solicitanteBOTOsolicitanteEntidad(Solicitante solicitante) throws GenericException {
        SolicitanteEntidad solicitanteEntidad = new SolicitanteEntidad();
        String tipoSolicitante;
        String nombDocumento = "";

        if (solicitante.getTipo().equals(Constantes.TIPO_SOLICITANTE_DECLARANTE)) {

            LOG.info("doc ciu: " + solicitante.getCiudadano().getDocumentoIdentidad().get(0).getTipoDocumento().getCodigo());
            //lee properties
            nombDocumento = buscarValorProperties(
                    solicitante.getCiudadano().getDocumentoIdentidad().get(0).getTipoDocumento().getCodigo());
            LOG.info("nombre doc: " + nombDocumento);

            solicitanteEntidad.setPrimerNombre(solicitante.getCiudadano().getPrimerNombre());
            solicitanteEntidad.setSegundoNombre(solicitante.getCiudadano().getSegundoNombre());
            solicitanteEntidad.setPrimerApellido(solicitante.getCiudadano().getPrimerApellido());
            solicitanteEntidad.setSegundoApellido(solicitante.getCiudadano().getSegundoApellido());

            tipoSolicitante = properties.getProperty(solicitante.getTipo());
            LOG.info("tipoSolicitante -->" + tipoSolicitante);
            solicitanteEntidad.setSolicitanteTipo(obtenerTipoSolicitante(tipoSolicitante));

            solicitanteEntidad.setSolicitanteTipDocDec(this.obtenerTipoDocumentoEntidadDec(nombDocumento));

            solicitanteEntidad.setNumeroDocumentoOficio(solicitante.getCiudadano()
                    .getDocumentoIdentidad().get(0).getNumero());

        } else if (solicitante.getTipo().equals(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO)) {

            LOG.info("doc ente: " + solicitante.getDocumentoEntePublico().getTipoDocumentoEntePublico());
            //lee properties
            nombDocumento = buscarValorProperties(solicitante.getDocumentoEntePublico().getTipoDocumentoEntePublico());
            LOG.info("nombre doc: " + nombDocumento);

            tipoSolicitante = properties.getProperty(solicitante.getTipo());
            solicitanteEntidad.setSolicitanteTipo(obtenerTipoSolicitante(tipoSolicitante));
            EntePublicoEntidad entePublico;
            LOG.info("Ente Publico Codigo: "+solicitante.getDocumentoEntePublico().getEntePublico().getCodigo());
            entePublico = consultarEntePublicoCodigo(solicitante.getDocumentoEntePublico()
                    .getEntePublico().getCodigo());
            solicitanteEntidad.setEntePublico(entePublico);
            solicitanteEntidad.setSolicitanteTipDocEnte(this.obtenerTipoDocumentoEntidadEnte(
                    solicitante.getDocumentoEntePublico().getTipoDocumentoEntePublico()));
            solicitanteEntidad.setNumeroDocumentoOficio(solicitante.getDocumentoEntePublico().getNumero());

        }
        return solicitanteEntidad;
    }

    /**
     *
     * Metodo utilitario para obtener los datos del ente publico
     *
     * @param codigo String que describe el codigo de Ente Publico
     * @return objeto entidad de tipo EntePublicoEntidad
     *
     */
    private EntePublicoEntidad consultarEntePublicoCodigo(String codigo) {

        return entePublicoRepository.findOne(Long.valueOf(codigo));
    }

    /**
     *
     * Metodo utilitario para obtener el tipo de documento
     *
     * @param indicadorTipoDocumento String que describe el indicador de tipo de Documento
     * @return objeto de tipo SolicitanteTipDocDecEntidad
     *
     */
    private SolicitanteTipDocDecEntidad obtenerTipoDocumentoEntidadDec(String indicadorTipoDocumento) {

        return solicitanteTipDocDecRepository.findByNombre(indicadorTipoDocumento);

    }

    /**
     *
     * Metodo utilitario para obtener el tipo de documento
     *
     * @param indicadorTipoDocumento String que describe el indicador de tipo de Documento
     * @return objeto de tipo SolicitanteTipDocEnteEntidad
     *
     */
    private SolicitanteTipDocEnteEntidad obtenerTipoDocumentoEntidadEnte(String indicadorTipoDocumento) {
        return solicitanteTipDocEnteRepository.findOne(Long.valueOf(indicadorTipoDocumento));

    }

    /**
     *
     * Metodo utilitario para obtener el tipo de solicitante
     *
     *
     * @param codigoSolicitanteTipo String que describe el codigo de tipo Solicitante
     * @return objeto de tipo SolicitanteTipoEntidad
     *
     */
    private SolicitanteTipoEntidad obtenerTipoSolicitante(String codigoSolicitanteTipo) {

        return solicitanteTipoRepository.findByNombreSolicitanteTipo(codigoSolicitanteTipo);

    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo properties del servicio solicitud
     *
     * @param clave String Propiedad a buscar en el archivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        LOG.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOG.info("Error leyendo properties: " + ex.getMessage());
        }
        LOG.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }
}
