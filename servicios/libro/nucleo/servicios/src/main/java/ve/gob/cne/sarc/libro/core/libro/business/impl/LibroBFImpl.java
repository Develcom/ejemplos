package ve.gob.cne.sarc.libro.core.libro.business.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import ve.gob.cne.sarc.libro.core.libro.util.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ve.gob.cne.sarc.comunes.oficina.Libro;
import ve.gob.cne.sarc.comunes.oficina.Tomo;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.libro.core.libro.business.LibroBF;
import ve.gob.cne.sarc.libro.core.libro.mapper.LibroMapper;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoLibroTipoOficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoOficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ActaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.FuncionarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.LibroEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoLibroRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoLibroTipoOficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.LibroRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoOficinaRepository;
import ve.gob.cne.sarc.libro.core.libro.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DiarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.LibroDiarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TomoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TomoEstatusEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.DiarioEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DiarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.LibroDiarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TomoEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TomoRepository;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * LibroBFImpl.java
 *
 * @descripcion Implementacion por defacto de {@link LibroBF}
 * @version 1.0 11/10/2015
 * @author Oscar Eubieda
 */
@Component
public class LibroBFImpl implements LibroBF {

    private static final Logger LOGGER = LoggerFactory.getLogger(LibroBFImpl.class);
    private static final String ESTATUS_LIBRO_ABIERTO = "servicios.libro.estatus.abierto";
    private static final String ESTATUS_LIBRO_CERRADO = "servicios.libro.estatus.cerrado";
    private static final String TIPO_LIBRO_TRAMITE = "servicios.libro.tipoLibro.tramite.";
    private static final String TRAMITE_PERMISO_INHUMACION = "servicios.libro.tramite.permiso-inhumacion";

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private LibroMapper libroMapper;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private TipoOficinaRepository tipoOficinaRepository;

    @Autowired
    private TipoLibroRepository tipoLibroRepository;

    @Autowired
    private TipoLibroTipoOficinaRepository tipoLibroTipoOficinaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private LibroEstatusRepository libroEstatusRepository;

    @Autowired
    private LibroDiarioRepository libroDiarioRepository;

    @Autowired
    private ActaRepository actaRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private DiarioRepository diarioRepository;

    @Autowired
    private DiarioEstatusRepository diarioEstatusRepository;

    @Autowired
    private TomoEstatusRepository tomoEstatusRepository;

    @Autowired
    private TomoRepository tomoRepository;

    /**
     * Método consultarTodos que permite obtener la colección de libros dado un
     * codigo de oficina
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    @Override
    public List<Libro> consultarTodos(String codigoOfic) {

        LOGGER.info("Ejecutando LibroBFImpl.consultarTodos(" + codigoOfic + ")");

        List<Libro> librosPorOficina = null;
        List<LibroEntidad> libroEntidades = null;

        OficinaEntidad oficinaEntidad = buscarOficinaPorCodigo(codigoOfic, false);
        if (oficinaEntidad == null) {
            return librosPorOficina;
        }

        try {
            libroEntidades = libroRepository.findByOficinaId(oficinaEntidad.getId());
            LOGGER.info("Libros encontrados: " + libroEntidades.size());
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando libros para la oficina: " + codigoOfic, e);
        }

        try {
            librosPorOficina = libroMapper.entitiesToBusinesses(libroEntidades);
        } catch (Exception e) {
            LOGGER.error("Error mapeando el resultado de BDs.", e);
        }

        return librosPorOficina;
    }

    /**
     * Método buscarLibrosEstatusPorTipoOficina que permite recuperar la lista
     * de libros por oficina, estatus y anio
     *
     * @param codigoOfic String codigo de oficina a consultar.
     * @param estatus long id del estatus de libros a consultar.
     * @param anio int año de libros a consultar.
     * @return Colección de objetos <tt>Libro</tt> en formato <tt>JSON</tt>.
     */
    @Override
    public List<Libro> buscarLibrosEstatusPorTipoOficina(String codigoOfic, long estatus, int anio) {

        LOGGER.info("Ejecutando LibroBFImpl.buscarLibrosEstatusPorTipoOficina(" + codigoOfic + "," + estatus + ","
                + anio + ")");

        List<Libro> libros = null;

        OficinaEntidad oficinaEntidad = buscarOficinaPorCodigo(codigoOfic, false);
        if (oficinaEntidad == null) {
            return libros;
        }

        List<LibroEntidad> libroEntidades = consultarLibrosActivos(oficinaEntidad.getId(), estatus, anio);

        LOGGER.info("LibroEntidades: " + libroEntidades.size());
        libros = libroMapper.entitiesToBusinesses(libroEntidades);

        return libros;
    }

    /**
     * Método validarLibrosActivosPorOficina que permite validar los libros para
     * una oficina, anio, estatus y tipo de libro dado
     *
     * @param codigoOfic el codigo de oficina a consultar.
     * @param estatus el estatus de libros a consultar.
     * @param anio el año de libros a consultar.
     * @return <tt>boolean</tt> que indica la validez del libro, en formato
     * <tt>JSON</tt>.
     */
    @Override
    public boolean validarLibrosActivosPorOficina(String codigoOfic, long estatus, int anio) {

        boolean respuesta;
        int cantidadLibrosOficina = 0;
        int cantidadLibrosActivos;

        LOGGER.info("Ejecutando LibroBFImpl.validarLibrosActivosPorOficina("
                + codigoOfic + "," + estatus + "," + anio + ")");

        // 1) Buscar los datos de la oficina a partir del codigo
        OficinaEntidad oficinaEntidad = buscarOficinaPorCodigo(codigoOfic, false);
        if (oficinaEntidad == null) {
            return false;
        }

        // 2) Recupero los Libros activos asignados a esta Oficina
        List<LibroEntidad> libroEntidadesActivos = consultarLibrosActivos(oficinaEntidad.getId(), estatus, anio);

        cantidadLibrosActivos = libroEntidadesActivos.size();
        LOGGER.info("Cantidad-Abiertos Libros por Oficina: " + cantidadLibrosActivos);
        long idTipoOficina;
        TipoOficinaEntidad tipoOficina = new TipoOficinaEntidad();

        try {
            idTipoOficina = oficinaEntidad.getTipoOficina().getId();
            LOGGER.info("id tipo oficina " + idTipoOficina);
            tipoOficina = tipoOficinaRepository.findOne(idTipoOficina);

        } catch (NullPointerException e) {
            LOGGER.error("error buscando oficina ", e);
        }

        // 3) Recupero la cantidad de libros asociados a esta Oficina
        if (!libroEntidadesActivos.isEmpty()) {
            List<TipoLibroTipoOficinaEntidad> tipoLibroTipoOficinaEntidades
                    = tipoLibroTipoOficinaRepository.findByTipoOficina(tipoOficina);

            cantidadLibrosOficina = tipoLibroTipoOficinaEntidades.size();
        }
        LOGGER.info("Cantidad Total-Libros por Oficina: " + cantidadLibrosOficina);

        // 4) Valido que todos los libros de la oficina se encuentren activos
        respuesta = cantidadLibrosOficina > 0 && (cantidadLibrosOficina == cantidadLibrosActivos);
        return respuesta;

    }

    /**
     * Metodo gestionarLibro que permite crear o actualizar un libro
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con
     * éxito, en formato <tt>JSON</tt>.
     */
    @Override
    public boolean gestionarLibro(Libro libro) {
        LOGGER.info("=====INICIANDO Libro.gestionarLibro==========");

        String codigoOfic;
        LibroEntidad libroEntidad;
        boolean resp = false;

        imprimirObjeto(libro);
        //lee properties
        long idEstatus = Long.parseLong(buscarValorProperties(ESTATUS_LIBRO_ABIERTO));
        LOGGER.info("idEstatus Abierto: " + idEstatus);
        if (libro.getOficina() != null && libro.getTipoLibro() != null) {

            List<LibroEntidad> libroEntidades
                    = libroRepository.findByOficinaCodigoAndEstatusIdAndTipoLibroIdAndNumeroAnio(
                            libro.getOficina().getCodigo(), idEstatus, Long.valueOf(libro.getTipoLibro().getCodigo()),
                            libro.getNumeroAnio());
            LOGGER.info("Busqueda de libros");
            if (!libroEntidades.isEmpty()) {
                LOGGER.info("Libro existente");

                libroEntidad = libroEntidades.get(0);
            } else {
                LOGGER.info("Libro nuevo");

                libroEntidad = libroMapper.businessToEntity(libro);

                codigoOfic = libroEntidad.getOficina().getCodigo();

                LOGGER.info("codigo de la oficina a buscar " + codigoOfic);

                OficinaEntidad oficinaEntidad = buscarOficinaPorCodigo(codigoOfic, true);
                LOGGER.info("objeto oficina " + oficinaEntidad);
                libroEntidad.setOficina(oficinaEntidad);

                // 1. Asignando funcionario de apertura
                LOGGER.info("Asignando funcionario apertura");
                LOGGER.info("libroEntidad " + libroEntidad);
                if (libroEntidad.getFuncionarioApertura() != null) {
                    FuncionarioEntidad funcionarioApertura = funcionarioRepository
                            .buscarPorCedula(libroEntidad.getFuncionarioApertura().getCedula());
                    libroEntidad.setFuncionarioApertura(funcionarioApertura);
                }

                // 2. Asignando funcionario de cierre
                LOGGER.info("Asignando funcionario cierre");
                if (libroEntidad.getFuncionarioCierre() != null) {
                    FuncionarioEntidad funcionarioCierre;
                    funcionarioCierre = funcionarioRepository
                            .buscarPorCedula(libroEntidad.getFuncionarioCierre().getCedula());

                    libroEntidad.setFuncionarioCierre(funcionarioCierre);
                } else {
                    LOGGER.info("Sin funcionario cierre");
                    libroEntidad.setFuncionarioCierre(null);
                }

                TipoLibroEntidad tipoLibroEntidad = buscarTipoLibroEntidad(Long.valueOf(libro.getTipoLibro().getCodigo()), true);

                libroEntidad.setTipoLibro(tipoLibroEntidad);

                LibroEstatusEntidad libroEstatusEntidad = libroEstatusRepository.findById(idEstatus);
                libroEntidad.setEstatus(libroEstatusEntidad);
            }

            libroEntidad.setFechaActualizacion(new Date());

            try {
                libroRepository.save(libroEntidad);
                resp = true;
            } catch (Exception e) {
                LOGGER.error("Error actualizando libro.", e);
                return false;
            }
        }
        return resp;
    }

    /**
     * Actualiza el estatus de un libro en base a la información dada.
     *
     * @param libro objeto <tt>Libro</tt> en formato <tt>JSON</tt>.
     * @return <tt>boolean</tt> que indica si la operación de ha completado con
     * éxito, en formato <tt>JSON</tt>.
     */
    @Override
    @Transactional
    public boolean aperturaCierreLibro(Libro libro) {

        LOGGER.info("=====INICIANDO Libro.aperturaCierreLibro==========");

        boolean resp = false;
        List<LibroEntidad> libroEntidads;
        LibroEstatusEntidad libroEstatusEntidad = null;
        FuncionarioEntidad funcionarioEntidad;
        TomoEntidad tomoEntidad, teResp;
        List<TomoEntidad> tomosEntidad = new ArrayList<>();
        TomoEstatusEntidad tomoEstatusEntidad;
        int numTomoTmp;
        String idEstatusAbierto, idEstatusCerrado, estatusTomoAbierto, estatusTomoCerrado;

        imprimirObjeto(libro);

        try {

            LOGGER.info("estatus actual del libro " + libro.getEstatus() + " del tipo libro "
                    + libro.getTipoLibro().getCodigo() + " de la oficina " + libro.getOficina().getCodigo());

            //lee properties
            idEstatusAbierto = buscarValorProperties(ESTATUS_LIBRO_ABIERTO);
            idEstatusCerrado = buscarValorProperties(ESTATUS_LIBRO_CERRADO);
            estatusTomoAbierto = buscarValorProperties(Constantes.ESTATUS_TOMO_ABIERTO);
            estatusTomoCerrado = buscarValorProperties(Constantes.ESTATUS_TOMO_CERRADO);

            if (idEstatusAbierto == null || idEstatusCerrado == null || estatusTomoAbierto == null
                    || estatusTomoCerrado == null
                    || "".equals(idEstatusAbierto) || "".equals(idEstatusCerrado) || "".equals(estatusTomoAbierto)
                    || "".equals(estatusTomoCerrado)) {

                return false;
            }

            libroEntidads = buscarLibrosEntidad(libro.getOficina().getCodigo(), Long.valueOf(libro.getEstatus()),
                    libro.getNumeroAnio(), Long.valueOf(libro.getTipoLibro().getCodigo()));
            LOGGER.info("tamaño lista libros buscados " + libroEntidads.size());

            for (LibroEntidad libroEntidad : libroEntidads) {

                if (String.valueOf(libroEntidad.getTipoLibro().getId()).equalsIgnoreCase(libro.getTipoLibro().getCodigo())) {

                    libroEntidad.setFechaActualizacion(new Date());
                    libroEntidad.setNumeroActa(libro.getNumeroActa());

                    //se verifica si el libro esta cerrado para poder aperturarlo
                    if (libro.getEstatus().equals(idEstatusCerrado)) {

                        LOGGER.info("se procede a la apertura del libro");

                        libroEstatusEntidad = libroEstatusRepository.findById(Long.valueOf(idEstatusAbierto));

                        funcionarioEntidad = buscarFuncionarioEntidad(libro.getFuncionarioApertura()
                                .getDocumentoIdentidad().get(0).getNumero(), false);
                        if (funcionarioEntidad == null) {
                            return false;
                        }

                        libroEntidad.setFuncionarioApertura(funcionarioEntidad);

                        tomoEstatusEntidad = tomoEstatusRepository.findOne(Long.parseLong(estatusTomoAbierto));

                        if (libroEntidad.getTomo().isEmpty() || libroEntidad.getTomo() == null) {

                            tomosEntidad = tomoRepository.findByLibroId(libroEntidad.getId());

                            if (tomosEntidad.isEmpty()) {

                                tomoEntidad = new TomoEntidad();

                                tomoEntidad.setLibro(libroEntidad);
                                tomoEntidad.setNumeroTomo(1);
                                tomoEntidad.setTomoEstatus(tomoEstatusEntidad);
                                tomoEntidad.setEsFisico(false);

                                teResp = tomoRepository.save(tomoEntidad);
                                tomosEntidad.add(teResp);

                            } else {
                                for (TomoEntidad te : tomosEntidad) {
                                    if (te.isEsFisico()) {
                                        te.setLibro(libroEntidad);
                                        te.setTomoEstatus(tomoEstatusEntidad);

                                        teResp = tomoRepository.save(te);
                                        tomosEntidad.add(teResp);
                                        break;
                                    }
                                }
                            }

                            libroEntidad.setTomo(tomosEntidad);

                        } else {

                            numTomoTmp = buscarUltimoTomo(libroEntidad.getTomo());

                            if (numTomoTmp == 0) {

                                tomoEntidad = new TomoEntidad();

                                tomoEntidad.setLibro(libroEntidad);
                                tomoEntidad.setNumeroTomo(1);
                                tomoEntidad.setTomoEstatus(tomoEstatusEntidad);
                                tomoEntidad.setEsFisico(false);

                                teResp = tomoRepository.save(tomoEntidad);

                                libroEntidad.getTomo().add(teResp);

                                //se cambia el estatus de tomo a cerrado para cerrar los otros tomos del libro
                                tomoEstatusEntidad = tomoEstatusRepository.findOne(Long.parseLong(estatusTomoCerrado));
                                for (TomoEntidad te : libroEntidad.getTomo()) {

                                    te.setTomoEstatus(tomoEstatusEntidad);

                                }

                            } else {
                                for (TomoEntidad te : libroEntidad.getTomo()) {
                                    if (numTomoTmp == te.getNumeroTomo()) {
                                        te.setTomoEstatus(tomoEstatusEntidad);
                                    }
                                }
                            }
                        }

                    } else if (libro.getEstatus().equals(idEstatusAbierto)) {

                        LOGGER.info("se procede al cierre del libro");
                        libroEstatusEntidad = libroEstatusRepository.findById(Long.valueOf(idEstatusCerrado));

                        tomoEstatusEntidad = tomoEstatusRepository.findOne(Long.parseLong(estatusTomoCerrado));

                        funcionarioEntidad = buscarFuncionarioEntidad(libro.getFuncionarioCierre()
                                .getDocumentoIdentidad().get(0).getNumero(), true);

                        if (funcionarioEntidad == null) {
                            return false;
                        }

                        libroEntidad.setFuncionarioCierre(funcionarioEntidad);

                        for (TomoEntidad te : libroEntidad.getTomo()) {
                            te.setTomoEstatus(tomoEstatusEntidad);
                        }

                    }

                    libroEntidad.setEstatus(libroEstatusEntidad);

                    libroRepository.save(libroEntidad);
                    resp = true;

                    break;
                }
            }

        } catch (Exception e) {
            LOGGER.error("Error actualizando libro.", e);
            return false;
        }

        return resp;
    }

    private int buscarUltimoTomo(List<TomoEntidad> tomos) {

        int tomo, tmp;
        
        if (tomos.size() == 2) {
            if (tomos.get(0).getNumeroTomo() < tomos.get(1).getNumeroTomo()) {
                tmp = tomos.get(0).getNumeroTomo();
                tomos.get(0).setNumeroTomo(tomos.get(1).getNumeroTomo());
                tomos.get(1).setNumeroTomo(tmp);
            }
        } else if (tomos.size() > 1) {

            for (int i = 0; i < tomos.size(); i++) {
                for (int j = 0; j < tomos.size(); j++) {
                    if (tomos.get(j).getNumeroTomo() < tomos.get(j + 1).getNumeroTomo()) {
                        tmp = tomos.get(j).getNumeroTomo();
                        tomos.get(j).setNumeroTomo(tomos.get(j + 1).getNumeroTomo());
                        tomos.get(j + 1).setNumeroTomo(tmp);
                    }
                }
            }
        }
        tomo = tomos.get(0).getNumeroTomo();

        return tomo;
    }

    /**
     * Metodo que obtiene el codigo del Tipo de Libro que corresponde segun el
     * tramite
     *
     * @param codTramite objeto String que contiene el codigo del tramite.
     * @return Objeto String con el codigo del Tipo Libro
     */
    public String obtenerTipoLibro(String codTramite) {
        LOGGER.info("=====INICIANDO Libro.obtenerTipoLibro==========");

        //lee properties
        String propertiesTipoLibroTramite = buscarValorProperties(TIPO_LIBRO_TRAMITE + codTramite);
        LOGGER.info("propertiesTipoLibroTramite --> " + propertiesTipoLibroTramite);

        String tipoLibroTramite = buscarValorProperties(propertiesTipoLibroTramite);
        LOGGER.info("tipoLibroTramite --> " + tipoLibroTramite);

        String codTipoLibro = tipoLibroTramite;

        LOGGER.info("valor properties - tipoLibroTramite --> " + tipoLibroTramite);
        if (tipoLibroTramite == null || tipoLibroTramite.isEmpty()) {
            //Setea codTipoLibro a 0 (cero)
            codTipoLibro = "0";
            LOGGER.info("No existe valor para properties: " + TIPO_LIBRO_TRAMITE + codTramite);
        }

        return codTipoLibro;
    }

    /**
     * Metodo que permite buscar un Libro a partir de los codigos de Tipo Libro,
     * Oficina, Estatus y Anio actual.
     *
     * @param codOficina String que contiene el codigo de la oficina
     * @param idEstatus String Id del estatus
     * @param anioActual int que contiene el año actual
     * @param idTipoLibro String que contiene el id del tipo de libro
     * @return LibroEntidad objeto con la informacion de la entidad Libro.
     */
    private LibroEntidad buscarLibro(String codOficina, long idEstatus, int anioActual, long idTipoLibro) {

        LOGGER.info("buscarLibro--> ofic " + codOficina + " est " + idEstatus + " anio "
                + anioActual + " tipo Libro " + idTipoLibro);
        LibroEntidad libro = null;

        List<LibroEntidad> librosEntidad = libroRepository.
                findByOficinaCodigoAndEstatusIdAndTipoLibroIdAndNumeroAnio(codOficina,
                        idEstatus, idTipoLibro, anioActual);

        if (!librosEntidad.isEmpty()) {
            libro = librosEntidad.get(0);
        }
        return libro;
    }

    /**
     * Metodo que permite actualizar un libro Civil
     *
     * @param acta Objeto de tipo Acta que contiene la informacion de un Acta
     * @return Verdadero si la operacion se ha completado exitosamente, en caso
     * contrario devuelve Falso
     */
    @Transactional
    @Override
    public boolean actualizarLibroCivil(Acta acta) {
        LOGGER.info("=====INICIANDO Libro.actualizarLibroCivil==========");
        boolean resp = false;
        ActaEntidad actaEntidad;

        imprimirObjeto(acta);
        LOGGER.info("=====================Acta==============================");
        actaEntidad = actaRepository.findByNumeroActa(acta.getNumeroActa());

        if (actaEntidad == null) {
            LOGGER.error("ERROR: consultando el acta- acta no existe");
            throw new ResourceNotFoundException("Acta no encontrada");
        }

        LOGGER.info("====Acta encontrada==========");

        Calendar calendario = Calendar.getInstance();
        LibroEntidad libroEntidad;

        //lee properties
        long idEstatus = Long.parseLong(buscarValorProperties(ESTATUS_LIBRO_ABIERTO));
        String codTramitePermiso = buscarValorProperties(TRAMITE_PERMISO_INHUMACION);
        LOGGER.info("codTramitePermiso --> " + codTramitePermiso);
        String tipoLibroTram = this.obtenerTipoLibro(actaEntidad.getSolicitud().getTramite().getCodigo());

        libroEntidad = buscarLibro(actaEntidad.getOficinaFuncionario().getOficina().getCodigo(),
                idEstatus, calendario.get(Calendar.YEAR), Long.parseLong(tipoLibroTram));

        LOGGER.info("====Libro: ==== " + libroEntidad + " para Tramite: "
                + actaEntidad.getSolicitud().getTramite().getId());

        if (libroEntidad == null
                && !actaEntidad.getSolicitud().getTramite().getCodigo().equals(codTramitePermiso)) {
            LOGGER.error("ERROR: consultando el libro abierto para tramite correspondiente - libro no existe");
            throw new ResourceNotFoundException("No encontro Libro Abierto en Anio Actual para el Tramite: "
                    + actaEntidad.getSolicitud().getTramite().getNombre() + " y Tipo Libro: "
                    + actaEntidad.getSolicitud().getTramite().getId());
        }

        if (libroEntidad == null) {
            LOGGER.error("ERROR: consultando el libro para tramite correspondiente - libro no existe");
            throw new ResourceNotFoundException("No encontro Libro En Anio Actual");
        }

        actaEntidad.setLibro(libroEntidad);
        try {
            actaRepository.save(actaEntidad);
            resp = true;
            LOGGER.info("====Libro en Acta GUARDADO=== " + actaEntidad.getLibro().getTipoLibro());
        } catch (Exception e) {
            LOGGER.error("error buscando oficina ", e);
        }

        return resp;

    }

    /**
     * Método validarTopeTomo que permite validar el tope del numero del Tomo
     * dado un codigo de oficina, un codigo de tipo libro y un año
     *
     * @param codigoOfic String codigo de oficina.
     * @param tipoLibro long Id de tipo de libro.
     * @param anio integer año del libro.
     * @return Verdadero si el Numero del Tomo llego al tope establecido.
     */
    @Override
    public boolean validarTopeTomo(String codigoOfic, long tipoLibro, int anio) {
        LOGGER.info("=====INICIANDO Libro.validarTopeTomo==========");

        OficinaEntidad oficinaEntidad;
        TipoLibroEntidad tipoLibroEntidad;
        LibroEntidad libroEntidad;

        int tope;
        int valorTope = 99;

        //lee properties
        long idEstatus = Long.parseLong(buscarValorProperties(ESTATUS_LIBRO_ABIERTO));
        long idEstatusTomo = Long.parseLong(buscarValorProperties(Constantes.ESTATUS_TOMO_ABIERTO));

        oficinaEntidad = this.buscarOficinaPorCodigo(codigoOfic, true);
        if (oficinaEntidad == null) {
            return false;
        }

        tipoLibroEntidad = this.buscarTipoLibroEntidad(tipoLibro, true);
        if (tipoLibroEntidad == null) {
            return false;
        }

        libroEntidad = this.buscarLibroEntidad(codigoOfic, idEstatus, anio, tipoLibro);

        for (TomoEntidad tomoEntidad : libroEntidad.getTomo()) {
            if (tomoEntidad.getTomoEstatus().getId() == idEstatusTomo) {

                LOGGER.info("Libro encontrado --> " + libroEntidad.getId() + " tomo " + tomoEntidad.getNumeroTomo());

                tope = tomoEntidad.getNumeroTomo();

                if (tope >= valorTope) {
                    return false;
                }
                break;
            }
        }

        return true;
    }

    /**
     * Busca el tomo actual dado el codigo de la oficina, el año, el tipo libro
     * y el estatus.
     *
     * @param codigoOfic String codigo de la oficina a consultar
     * @param estatus long Id de estatus a consultar
     * @param anio int año del libro a consultar
     * @param idTipoLibro long Id del tipo libro a consultar
     * @return Objeto tipo Tomo que contiene la informacion del tomo consultado
     */
    @Override
    public Tomo buscarTomoActual(String codigoOfic, long estatus, int anio, long idTipoLibro) {
        LOGGER.info("=====INICIANDO Libro.buscarTomoActual==========");

        Tomo tomo = new Tomo();
        OficinaEntidad oficinaEntidad;
        TipoLibroEntidad tipoLibroEntidad;
        LibroEntidad libroEntidad;
        String valor = "Libro-";

        oficinaEntidad = buscarOficinaPorCodigo(codigoOfic, false);
        if (oficinaEntidad == null) {
            return null;
        }

        tipoLibroEntidad = this.buscarTipoLibroEntidad(idTipoLibro, false);
        if (tipoLibroEntidad == null) {
            return null;
        }

        libroEntidad = this.buscarLibroEntidad(codigoOfic, estatus, anio, idTipoLibro);
        if (libroEntidad != null) {
            LOGGER.info("buscarTomoActual --> Libro entontrado -> " + libroEntidad.getId());
            valor = valor + libroEntidad.getId();
            tomo.setNumeroTomo(valor);
        }

        //Buscar TomoEntidad por desarrollar
        return tomo;
    }

    /**
     * Metodo que busca la entidad de Oficina.
     *
     * @param codigoOfic String codigo de la oficina
     * @param emiteError boolean Verdadero si se desea emitir error si no
     * encuentra la oficina
     * @return Objeto entidad de tipo OficinaEntidad
     */
    private OficinaEntidad buscarOficinaEntidad(String codigoOfic, boolean emiteError) {
        OficinaEntidad oficinaEntidad;

        oficinaEntidad = oficinaRepository.findByNombre(codigoOfic);
        if (emiteError && oficinaEntidad == null) {
            LOGGER.error("ERROR: consultando oficina- codigo '" + codigoOfic + "'  no existe");
            throw new ResourceNotFoundException("Oficina no encontrada");
        }

        return oficinaEntidad;
    }

    /**
     * Metodo que busca la entidad de Tipo Libro.
     *
     * @param idTipoLibro long Id del tipo libro
     * @param emiteError boolean Verdadero si se desea emitir error si no
     * encuentra la oficina
     * @return Objeto entidad de tipo TipoLibroEntidad
     */
    private TipoLibroEntidad buscarTipoLibroEntidad(long idTipoLibro, boolean emiteError) {
        TipoLibroEntidad tipoLibroEntidad = null;

        try {
            tipoLibroEntidad = tipoLibroRepository.findById(idTipoLibro);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando el tipoLibro ", e);
            if (emiteError) {
                throw new ResourceNotFoundException("TipoLibro no encontrado");
            }
        }

        return tipoLibroEntidad;
    }

    /**
     * Metodo que busca la entidad Libro dado un codigo de la oficina, un
     * estatus, un anio y un codigo de tipo libro.
     *
     * @param codigoOfic String codigo de la oficina
     * @param estatus long Id del estatus
     * @param anio int año que se desea consultar
     * @param tipoLibro long Id del tipo libro
     * @return Objeto entidad de tipo LibroEntidad
     */
    private LibroEntidad buscarLibroEntidad(String codigoOfic, long estatus, int anio, long tipoLibro) {

        List<LibroEntidad> librosEntidad = null;
        LibroEntidad libroEntidad = null;

        //Cuando se agregue la implementacion del tomo debe modificarse este método para que busque el 
        //libro dado el codigo de la oficina, el id del estatus, el anio, el id de tipo libro y ademas 
        //por el tomo.
        //Para la fecha 25 Nov 2016 se parte de que solo hay un libro abierto por tipo libro, por lo cual se tiene
        //un tomo para cada tipo libro.
        try {
            librosEntidad = libroRepository.findByOficinaCodigoAndEstatusIdAndTipoLibroIdAndNumeroAnio(
                    codigoOfic, estatus, tipoLibro, anio);

            if (!librosEntidad.isEmpty()) {
                LOGGER.info("Encontro libro: " + librosEntidad.get(0).getId());
                libroEntidad = librosEntidad.get(0);
            }

        } catch (NullPointerException e) {
            LOGGER.error("Error consultando libros activos para el tipoLibro: " + tipoLibro, e);
            throw new ResourceNotFoundException("Libro no encontrado para la Ofic " + codigoOfic + "anio " + anio
                    + " tipoLibro " + tipoLibro + "est " + estatus);
        }

        return libroEntidad;
    }

    /**
     * Metodo que busca los libros entidad dado un codigo de la oficina, un
     * estatus, un anio y un codigo de tipo libro.
     *
     * @param codigoOfic String codigo de la oficina
     * @param estatus long Id del estatus
     * @param anio int año que se desea consultar
     * @param tipoLibro long Id del tipo libro
     * @return Lista de objetos de tipo libroEntidad
     */
    private List<LibroEntidad> buscarLibrosEntidad(String codigoOfic, long estatus, int anio, long tipoLibro) {

        List<LibroEntidad> librosEntidad = null;

        LOGGER.info("buscarLibrosEntidad --> ofic: " + codigoOfic + " est: " + estatus + " anio: "
                + anio + " tipoLibro: " + tipoLibro);
        try {
            librosEntidad = libroRepository.findByOficinaCodigoAndEstatusIdAndTipoLibroIdAndNumeroAnio(
                    codigoOfic, estatus, tipoLibro, anio);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando libros activos para el tipoLibro: " + tipoLibro, e);
            throw new ResourceNotFoundException("Libro no encontrado para la Ofic " + codigoOfic + "anio " + anio
                    + " tipoLibro " + tipoLibro + "est " + estatus);
        }

        return librosEntidad;
    }

    /**
     * Valida el tope de numeros de acta por tomo dado un codigo de oficina, un
     * codigo de tipo libro, un año y un tomo.
     *
     * @param codOficina String codigo de la oficina
     * @param idTipoLibro long Id del tipo libro
     * @param anio int año del libro
     * @param numeroTomo String numero de tomo a validar
     * @return Verdadero si el numero de actas por tomo dado una oficina, tipo
     * libro, año y tomo
     */
    @Override
    public boolean validarActasPorTomo(String codOficina, long idTipoLibro, int anio, String numeroTomo) {
        LOGGER.info("=====INICIANDO Libro.validarActasPorTomo==========");

        OficinaEntidad oficinaEntidad;
        TipoLibroEntidad tipoLibroEntidad;
        LibroEntidad libroEntidad;

        oficinaEntidad = buscarOficinaPorCodigo(codOficina, true);
        if (oficinaEntidad == null) {
            return false;
        }

        tipoLibroEntidad = buscarTipoLibroEntidad(idTipoLibro, true);
        if (tipoLibroEntidad == null) {
            return false;
        }

        //lee properties
        long estatusAbierto = Long.parseLong(buscarValorProperties(ESTATUS_LIBRO_ABIERTO));

        libroEntidad = this.buscarLibroEntidad(codOficina, estatusAbierto, anio, idTipoLibro);

        LOGGER.info("Libro entontrado -> " + libroEntidad.getId());

        //Falta implementacion para buscar el tomo y buscar el nro maximo de actas por tomo
        return false;
    }

    /**
     * Crea libro diario dado el n&uacute;mero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @return Verdadero si cre&oacute; satisfactoriamente el libro diario, en
     * caso contrario devuelve falso
     */
    @Transactional
    @Override
    public boolean crearLibroDiario(String numeroSolicitud) {
        LOGGER.info("=====INICIANDO Libro.crearLibroDiario========== " + numeroSolicitud);

        boolean resp;
        SolicitudEntidad solicitudEntidad;
        DiarioEntidad diarioEntidad;

        solicitudEntidad = solicitudRepository.findByNumero(numeroSolicitud);

        if (solicitudEntidad == null) {
            LOGGER.error("ERROR: Datos invalidos para la actualizacion - Datos incompletos");
            throw new ResourceNotFoundException("Datos invalidos para la actualizacion");
        }

        //Busca el libro diario
        diarioEntidad = diarioRepository.findBySolicitudNumero(numeroSolicitud);
        if (diarioEntidad != null) {
            LOGGER.error("ERROR: Libro Diario ya existe.");
            throw new ResourceNotFoundException("Libro Diario ya existe");
        }

        try {
            diarioEntidad = new DiarioEntidad();
            //Buscar el estatus del libro diario
            diarioEntidad.setEstatus(this.consultarEstadoLibroDiario(solicitudEntidad.getEstatus().getCodigo()));
            LOGGER.info("Estatus del Libro diario--> " + diarioEntidad.getEstatus().getCodigoDiarioEstatus());
            diarioEntidad.setFechaRegistro(new Date());
            diarioEntidad.setSolicitud(solicitudEntidad);

        } catch (Exception e) {
            LOGGER.error("ERROR: Consultando estado de libro diario.");
            throw new ResourceNotFoundException(e);
        }

        try {
            diarioRepository.save(diarioEntidad);
            resp = true;
        } catch (Exception e) {
            LOGGER.error("ERROR: Guardando libro diario");
            throw new ResourceNotFoundException(e);
        }

        return resp;
    }

    /**
     * Buscar el estatus de un libro diario dado el codigo del estatus de la
     * solicitud
     *
     * @param codigo String codigo del estatus de la solicitud
     * @return Objeto entidad de tipo DiarioEstatusEntidad
     */
    private DiarioEstatusEntidad consultarEstadoLibroDiario(String codigo) {
        DiarioEstatusEntidad diarioEstatusEntidad;
        LOGGER.info("consultarEstadoLibroDiario========== " + codigo);
        diarioEstatusEntidad = diarioEstatusRepository.findByCodigoDiarioEstatus(codigo.toUpperCase());

        return diarioEstatusEntidad;
    }

    /**
     * Actualiza el libro diario dado un n&uacute;mero de solicitud
     *
     * @param numeroSolicitud String Numero de solicitud
     * @param codigoEstatus String Codigo del Estatus
     * @return Verdadero si hizo la actualizaci&oacute;n satisfactoriamente, en
     * caso contrario devuelve falso
     */
    @Override
    public boolean actualizarLibroDiario(String numeroSolicitud, String codigoEstatus) {
        LOGGER.info("=====INICIANDO Libro.actualizarLibroDiario========== " + numeroSolicitud);

        boolean resp;
        SolicitudEntidad solicitudEntidad;
        DiarioEntidad diarioEntidad;

        solicitudEntidad = solicitudRepository.findByNumero(numeroSolicitud);

        if (solicitudEntidad == null) {
            LOGGER.error("ERROR: consultando la solicitusd para actualizar Libro Diario - solicitud no existe");
            throw new ResourceNotFoundException("Solicitud no encontrada");
        }
        LOGGER.debug("====Solicitud encontrada==========");

        diarioEntidad = diarioRepository.findBySolicitudNumero(numeroSolicitud);

        if (diarioEntidad == null) {
            LOGGER.error("ERROR: consultando el Libro Diario - Libro Diario no existe");
            throw new ResourceNotFoundException("Libro Diario no encontrado");
        }

        try {
            LOGGER.info("Estatus Solicitud --> " + codigoEstatus);
            diarioEntidad.setEstatus(consultarEstadoLibroDiario(codigoEstatus));
            diarioRepository.save(diarioEntidad);
            resp = true;
        } catch (Exception e) {
            throw new ResourceNotFoundException(e);
        }

        return resp;
    }

    /**
     * Metodo validarLibroDiarioActivoPorOficina que permite validar el libro
     * diario para una oficina, dia y estatus
     *
     * @param codOficina el codigo de oficina a consultar.
     * @param fecha fecha del libro diario a consultar.
     * @param estatus estatus del libro diario a consultar.
     * @return <tt>boolean</tt> que indica la validez del libro, en formato
     * <tt>JSON</tt>.
     */
    @Override
    public boolean validarLibroDiarioActivoPorOficina(String codOficina, String fecha, long estatus) {

        LibroDiarioEntidad libroDiarioEntidad;

        libroDiarioEntidad = libroDiarioRepository.validarLibroDiarioOficina(fecha, codOficina, estatus);

        if (libroDiarioEntidad == null) {

            LOGGER.info("No se encuentra un libro diario activo para la oficina " + codOficina);

            return false;

        }

        return true;

    }

    /**
     * Metodo que permite buscar la entidad oficinaEntidad dado el codigo de la
     * oficina
     *
     * @param codigo String codigo de la oficina
     * @param emiteError boolean Verdadero si se desea emitir error si no
     * encuentra la oficina
     * @return Objeto entidad de tipo OficinaEntidad
     */
    private OficinaEntidad buscarOficinaPorCodigo(String codigo, boolean emiteError) {

        OficinaEntidad oficinaEntidad = null;

        try {
            oficinaEntidad = oficinaRepository.findByCodigo(codigo);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando la oficina ", e);
            if (emiteError) {
                throw new ResourceNotFoundException("Oficina no encontrada");
            }
        }

        return oficinaEntidad;
    }

    /**
     * Metodo que permite consultar libros activos dado el id de la oficina, el
     * id del estatus y el anio.
     *
     * @param idOficina long Id de la oficina
     * @param idEstatus long Id del estatus
     * @param anio int anio
     * @return Lista de objetos del tipo LibroEntidad
     */
    private List<LibroEntidad> consultarLibrosActivos(long idOficina, long idEstatus, int anio) {

        List<LibroEntidad> libroEntidades = null;

        try {
            libroEntidades = libroRepository.findByOficinaIdAndEstatusIdAndNumeroAnio(
                    idOficina, idEstatus, anio);
        } catch (Exception e) {
            LOGGER.error("Error consultando los libros activos ", e);
        }

        return libroEntidades;
    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo
     * properties del servicio libro
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        LOGGER.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOGGER.info("Error leyendo properties: " + ex);
        }
        LOGGER.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }

    /**
     * Metodo que permite buscar un funcionario con el numero de la cedula
     *
     * @param cedula String Cedula del funcionario
     * @param funcCierre boolean Indica el tipo de funcionario (true:Funcionario
     * de cierre)
     * @return Objeto de tipo FuncionarioEntidad
     */
    private FuncionarioEntidad buscarFuncionarioEntidad(String cedula, boolean funcCierre) {

        String mensajeCierre;
        FuncionarioEntidad funcionarioEntidad;

        if (funcCierre) {
            mensajeCierre = "de Cierre";
        } else {
            mensajeCierre = "de Apertura";
        }

        try {
            funcionarioEntidad = funcionarioRepository.buscarPorCedula(cedula);
        } catch (Exception e) {
            LOGGER.error("Error consultando el funcionario " + mensajeCierre, e);
            throw new ResourceNotFoundException("No encontro el funcionario " + mensajeCierre);
        }

        return funcionarioEntidad;
    }

    /**
     * Metodo que imprime el objeto de entrada de cualquier metodo que lo
     * implemente
     *
     * @param obj Objeto que es entrada de cualquier metodo
     */
    private void imprimirObjeto(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getJsonFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
        String json = null;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            java.util.logging.Logger.getLogger(LibroBFImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOGGER.info("Imprimir Json de Entrada: " + obj.getClass().getName() + " - " + json);
    }

}
