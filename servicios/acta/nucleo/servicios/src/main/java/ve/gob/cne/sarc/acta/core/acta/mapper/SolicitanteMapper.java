package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
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

/**
 * SolicitanteMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 25/7/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class SolicitanteMapper {

    public static final String TIPO_SOLICITANTE_DECLARANTE = "Declarante";
    public static final String TIPO_SOLICITANTE_ENTE_PUBLICO = "Ente Publico";

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
                .equals(TIPO_SOLICITANTE_DECLARANTE)) {

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
                .equals(TIPO_SOLICITANTE_ENTE_PUBLICO)) {

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
    public SolicitanteEntidad solicitanteBOTOsolicitanteEntidad(Solicitante solicitante) {
        SolicitanteEntidad solicitanteEntidad = new SolicitanteEntidad();
        if (solicitante.getTipo().equals(TIPO_SOLICITANTE_DECLARANTE)) {

            solicitanteEntidad.setPrimerNombre(solicitante.getCiudadano().getPrimerNombre());
            solicitanteEntidad.setSegundoNombre(solicitante.getCiudadano().getSegundoNombre());
            solicitanteEntidad.setPrimerApellido(solicitante.getCiudadano().getPrimerApellido());
            solicitanteEntidad.setSegundoApellido(solicitante.getCiudadano().getSegundoApellido());

            solicitanteEntidad.setSolicitanteTipo(obtenerTipoSolicitante(TIPO_SOLICITANTE_DECLARANTE));

            solicitanteEntidad.setSolicitanteTipDocDec(this.obtenerTipoDocumentoEntidadDec(
                    solicitante.getCiudadano().getDocumentoIdentidad().get(0).getTipoDocumento().getNombre()));

            solicitanteEntidad.setNumeroDocumentoOficio(solicitante.getCiudadano()
                    .getDocumentoIdentidad().get(0).getNumero());

        } else if (solicitante.getTipo().equals(TIPO_SOLICITANTE_ENTE_PUBLICO)) {
            solicitanteEntidad.setSolicitanteTipo(obtenerTipoSolicitante(TIPO_SOLICITANTE_ENTE_PUBLICO));
            EntePublicoEntidad entePublico;
            entePublico = consultarEntePublicoCodigo(solicitante.getDocumentoEntePublico()
                    .getEntePublico().getNombre());
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
        return entePublicoRepository.findByNombre(codigo);
    }

    /**
     *
     * Metodo utilitario para obtener el tipo de documento
     *
     * @param indicadorTipoDocumento String que describe el indicador de tipo de
     * Documento
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
     * @param indicadorTipoDocumento String que describe el indicador de tipo de
     * Documento
     * @return objeto de tipo SolicitanteTipDocEnteEntidad
     *
     */
    private SolicitanteTipDocEnteEntidad obtenerTipoDocumentoEntidadEnte(String indicadorTipoDocumento) {

        return solicitanteTipDocEnteRepository.findByNombre(indicadorTipoDocumento);

    }

    /**
     *
     * Metodo utilitario para obtener el tipo de solicitante
     *
     *
     * @param codigoSolicitanteTipo String que describe el codigo de tipo
     * Solicitante
     * @return objeto de tipo SolicitanteTipoEntidad
     *
     */
    private SolicitanteTipoEntidad obtenerTipoSolicitante(String codigoSolicitanteTipo) {

        return solicitanteTipoRepository.findByNombreSolicitanteTipo(codigoSolicitanteTipo);

    }
}
