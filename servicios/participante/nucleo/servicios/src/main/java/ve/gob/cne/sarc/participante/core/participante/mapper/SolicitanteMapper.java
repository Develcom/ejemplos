package ve.gob.cne.sarc.participante.core.participante.mapper;

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
import ve.gob.cne.sarc.participante.core.util.Constantes;
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
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring")
public abstract class SolicitanteMapper {

    @Autowired
    private SolicitanteTipDocDecRepository solicitanteTipDocDecRepository;

    @Autowired
    private SolicitanteTipDocEnteRepository solicitanteTipDocEnteRepository;

    @Autowired
    private EntePublicoRepository entePublicoRepository;

    @Autowired
    private SolicitanteTipoRepository solicitanteTipoRepository;

    /**
     * Metodo de mapeo de los abjetos SolicitanteEntidad y Solicitante
     *
     * @param SolicitanteEntidad
     * @return Solicitante
     * @throws Exception
     */
    public Solicitante solicitanteESolicitanteTOSolicitante(SolicitanteEntidad solicitanteEntidad) {
        Solicitante solicitante = new Solicitante();
        if (solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo().
		equals(Constantes.TIPO_SOLICITANTE_DECLARANTE)) {
            Ciudadano ciudadano = new Ciudadano();
            ciudadano.setPrimerNombre(solicitanteEntidad.getPrimerNombre());
            ciudadano.setPrimerApellido(solicitanteEntidad.getPrimerApellido());
            ciudadano.setSegundoNombre(solicitanteEntidad.getPrimerNombre());
            ciudadano.setSegundoApellido(solicitanteEntidad.getPrimerApellido());
            if (solicitanteEntidad.getSolicitanteTipDocDec() != null) {
                TipoDocumento tipoDocumento = new TipoDocumento();
//                tipoDocumento.setNombre(solicitanteEntidad.getSolicitanteTipDocDec().getCodigo());
                tipoDocumento.setCodigo(solicitanteEntidad.getSolicitanteTipDocDec().getNombre());
                DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();

                documentoIdentidad.setTipoDocumento(tipoDocumento);
                documentoIdentidad.setNumero(solicitanteEntidad.getNumeroDocumentoOficio());
                List<DocumentoIdentidad> documentosIdentidad = new ArrayList<>();
                documentosIdentidad.add(documentoIdentidad);
                ciudadano.setDocumentoIdentidad(documentosIdentidad);
            }
            solicitante.setTipo(solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo());
            solicitante.setCiudadano(ciudadano);
        } else if (solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo().
		equals(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO)) {
            DocumentoEntePublico documentoEntePublico = new DocumentoEntePublico();
            documentoEntePublico.setTipoDocumentoEntePublico(solicitanteEntidad.getSolicitanteTipDocEnte().getNombre());
            documentoEntePublico.setEnteOrigen(solicitanteEntidad.getEntePublico().getNombre());
            EntePublico entePublico = new EntePublico();
            entePublico.setNombre(solicitanteEntidad.getEntePublico().getNombre());
//            entePublico.setCodigo(solicitanteEntidad.getEntePublico().getCodigo());
            documentoEntePublico.setEntePublico(entePublico);
            documentoEntePublico.setNumero(solicitanteEntidad.getNumeroDocumentoOficio());
            solicitante.setTipo(solicitanteEntidad.getSolicitanteTipo().getNombreSolicitanteTipo());
            solicitante.setDocumentoEntePublico(documentoEntePublico);
        }
        return solicitante;
    }

    /**
     * Metodo de mapeo de los abjetos Solicitante y SolicitanteEntidad
     *
     * @param solicitante
     * @return SolicitanteEntidad
     */
    public SolicitanteEntidad solicitanteBOTOsolicitanteEntidad(Solicitante solicitante) {
        SolicitanteEntidad solicitanteEntidad = new SolicitanteEntidad();
        if (solicitante.getTipo().equals(Constantes.TIPO_SOLICITANTE_DECLARANTE)) {
            solicitanteEntidad.setPrimerNombre(solicitante.getCiudadano().getPrimerNombre());
            solicitanteEntidad.setSegundoNombre(solicitante.getCiudadano().getSegundoNombre());
            solicitanteEntidad.setPrimerApellido(solicitante.getCiudadano().getSegundoApellido());
            solicitanteEntidad.setSegundoApellido(solicitante.getCiudadano().getSegundoApellido());

            solicitanteEntidad.setSolicitanteTipo(obtenerTipoSolicitante(Constantes.TIPO_SOLICITANTE_DECLARANTE));

            solicitanteEntidad.setSolicitanteTipDocDec(
                    this.obtenerTipoDocumentoEntidadDec(solicitante.getCiudadano().getDocumentoIdentidad().get(0).
                            getTipoDocumento().getCodigo()));

            solicitanteEntidad.setNumeroDocumentoOficio(solicitante.getCiudadano().getDocumentoIdentidad().get(0).
                    getNumero());
        } else if (solicitante.getTipo().equals(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO)) {
            solicitanteEntidad.setSolicitanteTipo(obtenerTipoSolicitante(Constantes.TIPO_SOLICITANTE_ENTE_PUBLICO));
            EntePublicoEntidad entePublico;
            entePublico = consultarEntePublicoCodigo(
                    solicitante.getDocumentoEntePublico().getEntePublico().getCodigo());
            solicitanteEntidad.setEntePublico(entePublico);
            solicitanteEntidad.setSolicitanteTipDocEnte(
                    obtenerTipoDocumentoEntidadEnte(solicitante.getDocumentoEntePublico().
                            getTipoDocumentoEntePublico()));
            solicitanteEntidad.setNumeroDocumentoOficio(solicitante.getDocumentoEntePublico().getNumero());
        }
        return solicitanteEntidad;
    }

    /**
     * Metodo utilitario par abtener los datos del ente publico
     *
     * @param codigo
     * @return EntePublicoEntidad
     * @throws Exception
     */
    private EntePublicoEntidad consultarEntePublicoCodigo(String codigo) {
        return entePublicoRepository.findByNombre(codigo);
    }

    /**
     * Metodo utilitario par abtener el tipo de documento
     *
     * @param solicitudEntidad
     * @return Solicitud
     * @throws Exception
     */
    private SolicitanteTipDocDecEntidad obtenerTipoDocumentoEntidadDec(String indicadorTipoDocumento) {
        return solicitanteTipDocDecRepository.findByNombre(indicadorTipoDocumento);
    }

    /**
     * Metodo utilitario par abtener el tipo de documento
     *
     * @param solicitudEntidad
     * @return Solicitud
     * @throws Exception
     */
    private SolicitanteTipDocEnteEntidad obtenerTipoDocumentoEntidadEnte(String indicadorTipoDocumento) {
        return solicitanteTipDocEnteRepository.findByNombre(indicadorTipoDocumento);
    }

    /**
     * Metodo utilitario par abtener el tipo de solicitante
     *
     * @param codigoSolicitanteTipo
     * @return SolicitanteTipoEntidad
     * @throws Exception
     */
    private SolicitanteTipoEntidad obtenerTipoSolicitante(String codigoSolicitanteTipo) {
        return solicitanteTipoRepository.findByNombreSolicitanteTipo(codigoSolicitanteTipo);
    }

}
