package ve.gob.cne.sarc.defuncion.core.defuncion.business.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;
import ve.gob.cne.sarc.defuncion.core.defuncion.business.DefuncionBF;
import ve.gob.cne.sarc.defuncion.core.defuncion.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.defuncion.core.defuncion.mapper.DefuncionMapper;
import ve.gob.cne.sarc.defuncion.core.defuncion.mapper.InhumacionYCremacionMapper;
import ve.gob.cne.sarc.persistencia.entidades.ActaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DefuncionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.InhumacionYCremacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.SolicitudEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoDocIdentidadEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ActaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EstadoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.MunicipioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.PaisRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ParroquiaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DefuncionRepository;
import ve.gob.cne.sarc.persistencia.repositorios.InhumacionYCremacionRepository;
import ve.gob.cne.sarc.persistencia.repositorios.SolicitudRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoDocIdentidadRepository;

/**
 * DefuncionBFimpl.java
 *
 * @descripcion [Clase con las logicas de negocio, de implemantacion para la
 * interfaz]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Component
public class DefuncionBFimpl implements DefuncionBF {

    private static final Logger LOG = LoggerFactory.
            getLogger(DefuncionBFimpl.class);
    private static final String FORMATO_DATETIME = "dd/MM/yyyy hh:mm:ss a";

    @Autowired
    private DefuncionRepository defuncionRepository;
    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private OficinaRepository oficinaRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private InhumacionYCremacionRepository inhumacionYCremacionRepository;

    @Autowired
    private ActaRepository actaRepository;

    @Autowired
    private InhumacionYCremacionMapper inhumacionYCremacionMapper;

    @Autowired
    private DefuncionMapper defuncionMapper;

    @Autowired
    private TipoDocIdentidadRepository tipoDocIdentidadRepository;

    /**
     * Valida si el certificado medico de defuncion existe.
     *
     * @param numeroCertificado long que contiene el numero de certificado
     * medico
     * @return Verdadero si existe en caso contrario falso
     */
    @Override
    @Transactional
    public boolean validarCertificadoMedicoDefuncion(long numeroCertificado) {

        LOG.debug("buscando el certificado " + numeroCertificado);

        boolean resp = false;
        DefuncionEntidad defuncionEntidad;
        Defuncion defuncion;

        defuncionEntidad = defuncionRepository.
                findNumeroCertificado(numeroCertificado);

        defuncion = defuncionMapper.entityToBusiness(defuncionEntidad);

        if (defuncion != null) {
            resp = defuncion.getNumeroCertificado() == numeroCertificado;
        }

        return resp;
    }

    /**
     *
     * Metodo guardarPermisoInhCre que permite guardar la informacion de permiso
     * inhumacion y cremacion.
     *
     * @param permisoInhCre objeto del modelo ontologico que contiene la
     * informacion de permiso inhumacion y cremacion
     * @return permiso objeto del modelo ontologico que contiene la informacion
     * de permiso inhumacion y cremacion
     */
    @Override
    @Transactional
    public PermisoInhCre guardarPermisoInhCre(PermisoInhCre permisoInhCre) {

        InhumacionYCremacionEntidad permisoInhCreEntidad
                = new InhumacionYCremacionEntidad();
        InhumacionYCremacionEntidad iyc;
        PermisoInhCre perm = new PermisoInhCre();
        EstadoEntidad estadoEntidad = null;
        MunicipioEntidad municipioEntidad = null;
        SolicitudEntidad solicitudEntidad;
        ParroquiaEntidad parroquiaEntidad = null;
        PaisEntidad paisEntidad;

        LOG.info("=====================Buscando pais=======================");
        paisEntidad = buscarPais(permisoInhCre.getPais());

        if (paisEntidad.getNombre().equals("VENEZUELA")) {

            LOG.info("=====================Buscando estado=======================");

            estadoEntidad = buscarEstado(permisoInhCre.getEstado());

            LOG.info("=================Buscando Municipio=======================");
            municipioEntidad = buscarMunicipio(permisoInhCre.getMunicipio(),permisoInhCre.getEstado());

            LOG.info("=================Buscando Parroquia========================");
            parroquiaEntidad = buscarParroquia(permisoInhCre.getParroquia(),permisoInhCre.getMunicipio());

        }

        LOG.info("==================Buscando Solicitud=====================");
        solicitudEntidad = buscarSolicitud(permisoInhCre.getNumSolicitud());

        InhumacionYCremacionEntidad permiso;
        permiso = buscarPermiso(permisoInhCre.getNumSolicitud());

        if (permiso != null) {
            permisoInhCreEntidad = permiso;
        } else {
            long numero = buscarProxNumPermiso(solicitudEntidad.
                    getOficinaFuncionario().getOficina().getCodigo());
            permisoInhCreEntidad.setNumeroPermiso(numero);
        }

        permisoInhCreEntidad.setNumeroCertificado(permisoInhCre
                .getNumeroCertificadoDef());
        permisoInhCreEntidad.setPais(paisEntidad);
        permisoInhCreEntidad.setEstado(estadoEntidad);
        permisoInhCreEntidad.setParroquia(parroquiaEntidad);
        permisoInhCreEntidad.setMunicipio(municipioEntidad);
        permisoInhCreEntidad.setSolicitud(solicitudEntidad);
        permisoInhCreEntidad.setDireccion(permisoInhCre
                .getDireccionDefuncion());
        permisoInhCreEntidad.setNombreCementerio(permisoInhCre
                .getNombreCementerio());
        permisoInhCreEntidad.setPrimerNombre(permisoInhCre
                .getPrimerNombreAutoriza());
        permisoInhCreEntidad.setPrimerApellido(permisoInhCre
                .getPrimerApellidoAutoriza());
        permisoInhCreEntidad.setTipoPermiso(permisoInhCre.getTipoPermiso());

        permisoInhCreEntidad.setSegundoNombre(permisoInhCre.getSegundoNombreAutoriza() == null ? "" : permisoInhCre
                .getSegundoNombreAutoriza());
        permisoInhCreEntidad.setSegundoApellido(permisoInhCre.getSegundoApellidoAutoriza() == null ? "" : permisoInhCre
                .getSegundoApellidoAutoriza());
        permisoInhCreEntidad.setObservacion(permisoInhCre.getObservacion() == null ? "" : permisoInhCre
                .getObservacion());

        if (permisoInhCre.getFechaDefuncion() != null) {

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                LOG.info("fecha json " + permisoInhCre.getFechaDefuncion());
                DateTime fecha = new DateTime(sdf.parse(permisoInhCre.getFechaDefuncion()));
//                Date fecha = sdf.parse(permisoInhCre.getFechaDefuncion());
                LOG.info("Fecha con Formato Despues---->" + fecha);
                permisoInhCreEntidad.setFechaDefuncion(fecha);
            } catch (ResourceNotFoundException | ParseException e) {
                LOG.info("Exception", e);
            }
        } else {
            permisoInhCreEntidad.setFechaDefuncion(null);
        }

        LOG.info("==================Guardar PermisoInhCre====================");

        iyc = inhumacionYCremacionRepository.save(permisoInhCreEntidad);

        if (iyc != null) {
            perm = inhumacionYCremacionMapper
                    .entityToBusiness(permisoInhCreEntidad);
        } else {
            LOG.info("Error al guardar el permiso de inhumacion y cremacion");
        }

        return perm;
    }

    /**
     *
     * Metodo consultaPermisoInhCre que permite consultar la informacion de
     * permiso inhumacion y cremacion.
     *
     * @param numeroSolicitud String que contiene el numero de la solicitud
     * @return permiso objeto del modelo ontologico que contiene la informacion
     * de permiso inhumacion y cremacion
     */
    @Override
    @Transactional
    public PermisoInhCre consultaPermisoInhCre(String numeroSolicitud) {

        LOG.debug("=====INICIANDO DefuncionBF.consultaPermisoInhCre==========");
        LOG.debug("numeroSolicitud: " + numeroSolicitud);

        PermisoInhCre permisoInhCre;
        InhumacionYCremacionEntidad inhumacionYCremacionEntidad;
        SolicitudEntidad solicitudEntidad;
        DateTime dt = null;
//        Date dt = null;

        solicitudEntidad = solicitudRepository.findByNumero(numeroSolicitud);

        if (solicitudEntidad != null) {
            inhumacionYCremacionEntidad = inhumacionYCremacionRepository.
                    findBySolicitudId(solicitudEntidad.getId());

            if (inhumacionYCremacionEntidad != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

                LOG.debug("Fecha consultada --> "
                        + inhumacionYCremacionEntidad.getFechaDefuncion().toString("dd/MM/yyyy hh:mm:ss a"));

                permisoInhCre = inhumacionYCremacionMapper
                        .entityToBusiness(inhumacionYCremacionEntidad);
                permisoInhCre.setFechaDefuncion(
                        inhumacionYCremacionEntidad.getFechaDefuncion().toString("dd/MM/yyyy hh:mm:ss a"));
            } else {
                throw new ResourceNotFoundException("No se encontro permiso de "
                        + "inhumacion y cremacion");
            }

        } else {
            throw new ResourceNotFoundException("Numero de Solicitud no encontrada "
                    + numeroSolicitud);
        }
        LOG.info("Return consulta --> " + permisoInhCre.getFechaDefuncion());

        return permisoInhCre;

    }

    /**
     *
     * Metodo buscarProxNumPermiso que permite buscar el proximo numero de
     * permiso de inhumacion y cremacion.
     *
     * @param codOficina String codigo de la oficina
     * @return long retorna el proximo numero de permiso
     *
     */
    @Override
    public long buscarProxNumPermiso(String codOficina) {
        long numero;
        String valor;
        OficinaEntidad oficinaEntidad;
        //Valida que exista la oficina
        oficinaEntidad = oficinaRepository.findByCodigo(codOficina);
        if (oficinaEntidad == null) {
            LOG.error("ERROR: consultando la oficina para buscar Proximo Numero"
                    + " de Permiso - oficina no existe");
            throw new ResourceNotFoundException("Oficina no encontrada");
        }

        valor = inhumacionYCremacionRepository
                .buscarPorCodigoInhumacionYCremacion(codOficina);
        LOG.debug("INFO: Max Numero: " + valor);
        if (valor == null) {
            valor = String.valueOf(0);
        }

        numero = Long.valueOf(valor) + 1;

        return numero;
    }

    /**
     *
     * Metodo privado buscarProxNumPermisoSolicitud que permite buscar el
     * proximo numero de permiso de inhumacion y cremacion en base a la
     * solicitud.
     *
     * @param numSolicitud String numero de la solicitud
     * @return InhumacionYCremacionEntidad entidad que contiene la informacion
     * de permiso Inhumacion y Cremacion
     */
    private InhumacionYCremacionEntidad buscarPermiso(String numSolicitud) {

        return inhumacionYCremacionRepository
                .findBySolicitudNumero(numSolicitud);
    }

    /**
     *
     * Metodo privado buscarPais que permite buscar el Pais
     *
     * @param nombrePais String nombre del Pais
     * @return PaisEntidad entidad que contiene la informacion de Pais
     */
    private PaisEntidad buscarPais(String nombrePais) {
        PaisEntidad paisBuscar;
        paisBuscar = paisRepository.findByNombre(nombrePais);

        if (paisBuscar == null) {
            LOG.info("================no consiguio el Pais ================"
                    + nombrePais);
            throw new ResourceNotFoundException("Pais tiene valor nulo");
        }
        return paisBuscar;
    }

    /**
     *
     * Metodo privado buscarEstado que permite buscar el estado
     *
     * @param nombreEstado String nombre del Estado
     * @return EstadoEntidad entidad que contiene la informacion de Estado
     */
    private EstadoEntidad buscarEstado(String nombreEstado) {
        EstadoEntidad estadoBuscar;
        estadoBuscar = estadoRepository.findByNombre(nombreEstado);

        if (estadoBuscar == null) {
            LOG.info("================no consiguio el estado ================"
                    + nombreEstado);
            throw new ResourceNotFoundException("Estado tiene valor nulo");
        }
        return estadoBuscar;
    }

    /**
     *
     * Metodo privado buscarMunicipio que permite buscar el municipio
     *
     * @param nombreMunicipio String nombre del Municipio
     * @param nombreEstado String nombre del Estado
     * @return MunicipioEntidad entidad que contiene la informacion de Municipio
     */
    private MunicipioEntidad buscarMunicipio(String nombreMunicipio, String nombreEstado) {
        MunicipioEntidad municipioBuscar;
        municipioBuscar = municipioRepository.findByNombreAndEstadoNombre(nombreMunicipio, nombreEstado);

        if (municipioBuscar == null) {

            LOG.info("===============no consiguio el Municipio =============="
                    + municipioBuscar);
            throw new ResourceNotFoundException("Municipio tiene valor nulo");
        }
        return municipioBuscar;
    }

    /**
     *
     * Metodo privado buscarParroquia que permite buscar la parroquia
     *
     * @param nombreParroquia String nombre de la Parroquia
     * @param nombreMunicipio String nombre del Municipio
     * @return ParroquiaEntidad entidad que contiene la informacion de Parroquia
     */
    private ParroquiaEntidad buscarParroquia(String nombreParroquia, String nombreMunicipio) {
        ParroquiaEntidad parroquiaBuscar;
        parroquiaBuscar = parroquiaRepository.findByNombreAndMunicipioNombre(nombreParroquia, nombreMunicipio);

        if (parroquiaBuscar == null) {
            LOG.info("=================no consiguio la parroquia ============="
                    + nombreParroquia);
            throw new ResourceNotFoundException("Parroquia tiene valor nulo");
        }
        return parroquiaBuscar;

    }

    /**
     *
     * Metodo privado buscarSolicitud que permite buscar la solicitud
     *
     * @param nombreSolicitud String nombre de la Solicitud
     * @return SolicitudEntidad entidad que contiene la informacion de Solicitud
     */
    private SolicitudEntidad buscarSolicitud(String nombreSolicitud) {
        SolicitudEntidad solicitudBuscar;
        solicitudBuscar = solicitudRepository.findByNumero(nombreSolicitud);

        if (solicitudBuscar == null) {
            LOG.info("===========no consiguio la solicitud =============="
                    + nombreSolicitud);
            throw new ResourceNotFoundException("Solicitud tiene valor nulo");
        }
        return solicitudBuscar;
    }

    /**
     * Metodo que permite guardar Defuncion
     *
     * @return verdadero si se guardo con exito en caso contrario false
     */
    @Transactional
    @Override
    public boolean guardarDefuncion(Defuncion defuncion) {
        LOG.info("=====INICIANDO DefuncionBFImpl.guardarDefuncion==========");

        DefuncionEntidad defuncionEntidad;

        ActaEntidad actaEntidad;
        DefuncionEntidad resultado;
        ParroquiaEntidad parroquiaEntidad = null;
        boolean resp = false;

        try {
            LOG.info("===================== Buscando Acta =======================");
            actaEntidad = buscarActa(defuncion.getNumeroActa());

            LOG.info("===================== Buscando Parroquia de Nacimiento =======================");
            if (defuncion.getParroquiaDefuncion() != null && defuncion.getMunicipioDefuncion() != null) {
                parroquiaEntidad = buscarParroquiaGuardar(defuncion.getParroquiaDefuncion(),
                        defuncion.getMunicipioDefuncion());
            }

            LOG.info("==============Buscando Defuncion===============");
            defuncionEntidad = defuncionRepository.findByActaNumeroActa(actaEntidad.getNumeroActa());

            if (defuncionEntidad == null) {
                LOG.info("=====Guardando DefuncionEntidad=====");
                defuncionEntidad = new DefuncionEntidad();
                defuncionEntidad.setActa(actaEntidad);
                defuncionEntidad.setFechaDefuncion(defuncion.getFechaDefuncion());
                defuncionEntidad.setNumeroCertificado(defuncion.getNumeroCertificado());

            }
            defuncionEntidad.setEstadoDefuncion(defuncion.getEstadoDefuncion());
            defuncionEntidad.setFechaCertificado(defuncion.getFechaCertificado());
            defuncionEntidad.setNuMPPS(defuncion.getNuMPPS());
            defuncionEntidad.setParroquia(parroquiaEntidad);
            defuncionEntidad.setPrimerApellidoMedico(defuncion.getPrimerApellidoMedico());
            defuncionEntidad.setPrimerNombreMedico(defuncion.getPrimerNombreMedico());
            defuncionEntidad.setDocumentoIdentidadMedico(defuncion.getDocIdenMedico());
            defuncionEntidad.setTextoCausa(defuncion.getTextoCausa());
            defuncionEntidad.setCentroSalud(defuncion.getCentroSalud());
            defuncionEntidad.setFechaExtractoConsular(defuncion.getFechaExtractoConsular());
            defuncionEntidad.setNotaMarginal(defuncion.getNotaMarginal());
            defuncionEntidad.setNumeroExtractoConsular(defuncion.getNumeroExtractoConsular());
            defuncionEntidad.setParroquiaDefuncion(defuncion.getParroquiaDefuncion());
            defuncionEntidad.setMunicipioDefuncion(defuncion.getMunicipioDefuncion());
            defuncionEntidad.setPrimerApellidoConsular(defuncion.getPrimerApellidoConsular());
            defuncionEntidad.setPrimerNombreConsular(defuncion.getPrimerNombreConsular());
            defuncionEntidad.setSegundoNombreConsular(defuncion.getSegundoNombreConsular());
            defuncionEntidad.setSegundoApellidoConsular(defuncion.getSegundoApellidoConsular());
            defuncionEntidad.setSegundoNombreMedico(defuncion.getSegundoNombreMedico());
            defuncionEntidad.setSegundoApellidoMedico(defuncion.getSegundoApellidoMedico());
            defuncionEntidad.setSexo(buscarIdentificadorSexo(defuncion.getSexo()));
            defuncionEntidad.setEstadoCivil(buscarIdentificadorEstadoCivil(defuncion.getEstadoCivil()));

            if (defuncion.getDocumentoIdentConsular() != null && defuncion.getTipoDoc() != null) {
                defuncionEntidad.setNumeroDocumentoConsular(defuncion.getDocumentoIdentConsular());
                defuncionEntidad.setTipoDocumentoIdentidad(consultarTipoDocIdentidad(defuncion.getTipoDoc()));
            }

            resultado = defuncionRepository.save(defuncionEntidad);

            if (resultado != null) {
                resp = true;
            }

        } catch (Exception e) {
            LOG.error("ERROR: Defuncion no pudo ser guardado", e);
        }
        return resp;
    }

    /**
     * Metodo que busca el identificador del sexo dado la descripcion del sexo
     *
     * @param sexo String nombre del sexo
     * @return String Identificador del sexo
     */
    private String buscarIdentificadorSexo(String sexo) {

        String valor;

        Map<String, String> hmESexo = new HashMap<>();
        hmESexo.put("MASCULINO", "M");
        hmESexo.put("FEMENINO", "F");
        hmESexo.put("", " ");
        valor = hmESexo.get(sexo);

        return valor;
    }

    /**
     * Metodo que busca el identificador del sexo dado la descripcion del sexo
     *
     * @param sexo String nombre del sexo
     * @return String Identificador del sexo
     */
    private String buscarIdentificadorEstadoCivil(String estadoCivil) {

        String valor;

        Map<String, String> hmEstadoCivil = new HashMap<>();
        hmEstadoCivil.put("SOLTERO", "S");
        hmEstadoCivil.put("CASADO", "C");
        hmEstadoCivil.put("VIUDO", "V");
        hmEstadoCivil.put("DIVORCIADO", "D");
        hmEstadoCivil.put("", " ");
        valor = hmEstadoCivil.get(estadoCivil);

        return valor;
    }

    /**
     * Metodo que permite consulta el Tipo de Documento de Identidad segun el
     * nombre del tipo de documento
     *
     * @param tipoDocumento String nombre del tipo de documento
     * @return TipoDocIdentidadEntidad objeto con la informacion de la entidad
     * TipoDocIdentidad.
     */
    private TipoDocIdentidadEntidad consultarTipoDocIdentidad(String tipoDocumento) {
        return tipoDocIdentidadRepository.findByNombreTipoDocIdentidad(tipoDocumento);
    }

    /**
     *
     * Metodo privado buscarActa que permite buscar el estado
     *
     * @param numeroActa String numero del Acta
     * @return ActaEntidad entidad que contiene la informacion de Acta
     */
    private ActaEntidad buscarActa(String numeroActa) throws Exception {

        ActaEntidad actaBuscar;
        actaBuscar = actaRepository.findByNumeroActa(numeroActa);

        if (actaBuscar == null) {
            LOG.info("================no consiguio el acta ================"
                    + numeroActa);
            throw new Exception("Acta tiene valor nulo");
        }
        return actaBuscar;
    }

    /**
     *
     * Metodo privado buscarParroquiaGuardar que permite buscar la parroquia
     * dado el nombre de la parroquia y el nombre del municipio
     *
     * @param nombreParroquia String nombre de la Parroquia
     * @param nombreMunicipio String nombre de municipio
     * @return objeto entidad que contiene la informacion de Parroquia
     */
    private ParroquiaEntidad buscarParroquiaGuardar(String nombreParroquia, String nombreMunicipio) {
        ParroquiaEntidad parroquiaBuscar;
        parroquiaBuscar = parroquiaRepository.findByNombreAndMunicipioNombre(nombreParroquia, nombreMunicipio);

        if (parroquiaBuscar == null) {
            LOG.info("================no consiguio la parroquia ================"
                    + nombreParroquia);
            throw new ResourceNotFoundException("Parroquia tiene valor nulo");
        }
        return parroquiaBuscar;
    }

    /**
     *
     * Metodo que formatea un String a Date
     *
     * @param fechaInput String que contiene una fecha
     * @param formato String que contiene el formato usado para formatear
     * @return Objeto de tipo Date
     */
    public static Date doFormatearFecha(String fechaInput, String formato) {

        SimpleDateFormat dateParser = new SimpleDateFormat(formato);
        Date fecha = null;

        try {
            fecha = dateParser.parse(fechaInput);
        } catch (ParseException ex) {
            LOG.error("ERROR: formateando la fecha - formateada--> " + fechaInput, ex);
            throw new ResourceNotFoundException("Fecha inv\\u00e1lida");
        }
        return fecha;
    }

    /**
     *
     * Metodo consultarDefuncion que permite consultar la informacion de
     * defuncion
     *
     * @param numeroSolicitud String que contiene el numero de la solicitud
     * @return defuncion objeto del modelo ontologico que contiene la
     * informacion de defuncion
     */
    @Override
    @Transactional
    public Defuncion consultarDefuncion(String numeroSolicitud) {
        LOG.debug("=====INICIANDO DefuncionBF.consultarDefuncion==========");
        LOG.debug("numeroSolicitud: " + numeroSolicitud);

        Defuncion defuncion = new Defuncion();
        DefuncionEntidad defuncionEntidad = null;
        SolicitudEntidad solicitudEntidad;

        solicitudEntidad = solicitudRepository.findByNumero(numeroSolicitud);

        if (solicitudEntidad != null) {
            defuncionEntidad = defuncionRepository.findByActaSolicitudId(solicitudEntidad.getId());

            if (defuncionEntidad != null) {
                defuncion = defuncionMapper.entityToBusiness(defuncionEntidad);
                defuncion.setEstadoCivil(mostrarIdentificadorEdoCivil(defuncionEntidad.getEstadoCivil()));
                defuncion.setSexo(mostrarIdentificadorSexo(defuncionEntidad.getSexo()));
            }

        } else {
            throw new ResourceNotFoundException("Numero de Solicitud no encontrada "
                    + numeroSolicitud);
        }
        LOG.info("Return consulta --> " + defuncion.getFechaDefuncion());

        return defuncion;

    }

    private String mostrarIdentificadorSexo(String sexo) {
        String valor;
        Map<String, String> hmESexo = new HashMap<>();
        hmESexo.put("M", "MASCULINO");
        hmESexo.put("F", "FEMENINO");
        hmESexo.put(" ", "");
        valor = hmESexo.get(sexo);
        return valor;
    }

    private String mostrarIdentificadorEdoCivil(String edoCivil) {
        String valor;
        Map<String, String> hmEstadoCivil = new HashMap<>();
        hmEstadoCivil.put("S", "SOLTERO");
        hmEstadoCivil.put("C", "CASADO");
        hmEstadoCivil.put("D", "DIVORCIADO");
        hmEstadoCivil.put("V", "VIUDO");
        hmEstadoCivil.put(" ", "");
        valor = hmEstadoCivil.get(edoCivil);
        return valor;
    }
}
