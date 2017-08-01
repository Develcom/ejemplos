package ve.gob.cne.sarc.participante.core.participante.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.TipoDireccion;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.persistencia.entidades.ComunidadIndigenaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DireccionParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.MunicipioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.PaisEntidad;
import ve.gob.cne.sarc.persistencia.entidades.ParroquiaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoDocIdentidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.ComunidadIndigenaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.DireccionParticipanteRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EstadoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.MunicipioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.PaisRepository;
import ve.gob.cne.sarc.persistencia.repositorios.ParroquiaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoDocIdentidadRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoParticipanteRepository;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * ParticipanteMapper.java
 *
 * @descripcion Clase mapper que realiza el mapeo entre las clase del modelo ontologico y las clase del modelo de datos
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = ActaMapper.class)
public abstract class ParticipanteMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipanteMapper.class);

    private static final String CODIGO_NAC = "NAC";
    private static final String NOMBRE_NACIMIENTO = "DIRECCION DE NACIMIENTO";
    private static final String DESCRIPCION_NACIMIENTO = "NACIMIENTO";

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private TipoParticipanteRepository tipoParticipanteRepository;

    @Autowired
    private TipoDocIdentidadRepository tipoDocIdentidadRepository;

    @Autowired
    private TipoParticipanteMapper tipoParticipanteMapper;

    @Autowired
    private DireccionParticipanteRepository direccionParticipanteRepository;

    @Autowired
    private ParroquiaRepository parroquiaRepository;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private ParroquiaMapper parroquiaMapper;

    @Autowired
    private MunicipioMapper municipioMapper;

    @Autowired
    private EstadoMapper estadoMapper;

    @Autowired
    private PaisMapper paisMapper;

    @Autowired
    private ComunidadIndigenaRepository comunidadIndigenaRepository;

    /**
     * Metodo que permite mapear Participante y ParticipanteEntidad
     *
     * @param participante objeto de la capa ontologica
     * @return {@link Participante} objeto de la entidad Participante
     */
    public ParticipanteEntidad boToEntity(Participante participante) {

        ParticipanteEntidad participanteEntidad = new ParticipanteEntidad();
        String tipoDocIden = "";
        String valorProp = "";

        participanteEntidad.setPrimerApellido(participante.getPrimerApellido());
        participanteEntidad.setPrimerNombre(participante.getPrimerNombre());
        participanteEntidad.setSegundoNombre(participante.getSegundoNombre() == null ? ""
                : participante.getSegundoNombre());

        if (participante.getSegundoApellido() == null) {
            participanteEntidad.setSegundoApellido(" ");
        } else {
            participanteEntidad.setSegundoApellido(participante.getSegundoApellido());
        }

        participanteEntidad.setSexo(buscarIdentificadorSexo(participante.getSexo()));
        participanteEntidad.setEstadoCivil(buscarIdentificadorEstadoCivil(participante.getEstadoCivil()));
        participanteEntidad.setTipoDocumento(participante.getTipoDocumento());

        LOGGER.info("rol de participante " + participante.getRol());

        participanteEntidad.setTipoParticipante(consultarTipoParticipante(participante.getRol()));

        if (participante.getFechaNacimiento() != null) {
            participanteEntidad.setFechaNacimiento(participante.getFechaNacimiento());
        }

        if (participante.getLugarNacimiento() != null) {
            participanteEntidad.setLugarNacimiento(participante.getLugarNacimiento());
        }

        if (participante.getDocumentoIdentidad() == null) {
            participanteEntidad.setNumeroDocIdentidad(null);
        } else {
            participanteEntidad.setNumeroDocIdentidad(participante.getDocumentoIdentidad().get(0).getNumero());
        }

        if (participante.getTipoDocumento() == null) {
            participanteEntidad.setTipoDocumentoIdentidad(null);
        } else {
            if (!isNumeric(participante.getDocumentoIdentidad().get(0).getTipoDocumento().getCodigo())) {
                valorProp = buscarValorProperties(
                        participante.getDocumentoIdentidad().get(0).getTipoDocumento().getCodigo());
                LOGGER.info("valorProp --> " + valorProp);
                tipoDocIden = buscarValorProperties("servicios.participante.tipo.documento." + valorProp);
                LOGGER.info("tipoDocIden --> " + tipoDocIden);
            }else{
                
                tipoDocIden = participante.getDocumentoIdentidad().get(0).getTipoDocumento().getCodigo();
            } 

            participanteEntidad.setTipoDocumentoIdentidad(consultarTipoDocIdentidad(Long.valueOf(tipoDocIden)));
        }

        //comunidad indigena
        if (participante.getComunidadIndigena() == null) {
            participanteEntidad.setComunidadIndigena(null);
        } else {
            participanteEntidad.setComunidadIndigena(consultarComunidadIndigena(participante.getComunidadIndigena()));
        }
        return participanteEntidad;
    }

    /**
     * Metodo que permite mapear ParticipanteEntidad y Participante
     *
     * @param participanteEntidad objeto de la entidad Participante
     * @return {@link Participante} objeto de la capa ontológica
     */
    public Participante entityToBO(ParticipanteEntidad participanteEntidad) {

        ParroquiaEntidad parroquiaEntidad;
        MunicipioEntidad municipioEntidad;
        EstadoEntidad estadoEntidad;
        PaisEntidad paisEntidad = null;
        Participante participante = new Participante();

        Parroquia parroquia = null;
        Municipio municipio = null;
        Estado estado = null;
        Pais pais = null;

        participante.setPrimerApellido(participanteEntidad.getPrimerApellido());
        participante.setPrimerNombre(participanteEntidad.getPrimerNombre());
        participante.setSegundoApellido(participanteEntidad.getSegundoApellido());
        participante.setSegundoNombre(participanteEntidad.getSegundoNombre());

        if (participanteEntidad.getTipoDocumentoIdentidad() == null) {
            participante.setDocumentoIdentidad(null);
        } else {
            TipoDocumento tipoDocumento = new TipoDocumento();
            tipoDocumento.setCodigo(String.valueOf(participanteEntidad.getTipoDocumentoIdentidad().getId()));
            tipoDocumento.setNombre(participanteEntidad.getTipoDocumentoIdentidad().getNombreTipoDocIdentidad());

            DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
            documentoIdentidad.setTipoDocumento(tipoDocumento);
            documentoIdentidad.setNumero(participanteEntidad.getNumeroDocIdentidad());

            List<DocumentoIdentidad> documentosIdentidad = new ArrayList<>();
            documentosIdentidad.add(documentoIdentidad);
            participante.setDocumentoIdentidad(documentosIdentidad);
        }

        Map<String, String> hmESexo = new HashMap<>();
        hmESexo.put("M", "MASCULINO");
        hmESexo.put("F", "FEMENINO");
        participante.setSexo(hmESexo.get(participanteEntidad.getSexo()));

        Map<String, String> hmEstadoCivil = new HashMap<>();
        hmEstadoCivil.put("S", "SOLTERO");
        hmEstadoCivil.put("C", "CASADO");
        hmEstadoCivil.put("V", "VIUDO");
        hmEstadoCivil.put("D", "DIVORCIADO");
        participante.setEstadoCivil(hmEstadoCivil.get(participanteEntidad.getEstadoCivil()));

        if (participanteEntidad.getTipoDocumento() == null) {
            participante.setTipoDocumento("");
        } else {
            participante.setTipoDocumento(participanteEntidad.getTipoDocumento());
        }

        participante.setFechaNacimiento(participanteEntidad.getFechaNacimiento());
        participante.setLugarNacimiento(participanteEntidad.getLugarNacimiento());
        if (participanteEntidad.getNacionalidad() != null) {
            participante.setNacionalidad(participanteEntidad.getNacionalidad().getNombre());
        }
        if (participanteEntidad.getOcupacion() != null) {
            participante.setOcupacion(participanteEntidad.getOcupacion().getNombre());
        }
        participante.setRol(tipoParticipanteMapper.tipoParticipanteTOrol(participanteEntidad.getTipoParticipante()));
        participante.setNombreRol(
                tipoParticipanteMapper.tipoParticipanteToNombreRol(participanteEntidad.getTipoParticipante()));
        participante.setParentesco(participanteEntidad.getIndicadorParentesco());

        if (participanteEntidad.getIndicadorAutenticar() != null) {
            participante.setAutenticado((participanteEntidad.getIndicadorAutenticar() == 0) ? false : true);
        } else {
            participante.setAutenticado(false);
        }
        if (participanteEntidad.getIndicadorVive() != null) {
            participante.setVive("N".equalsIgnoreCase(participanteEntidad.getIndicadorVive()) ? false : true);
        } else {
            participante.setVive(true);
        }

        participante.setObservacion((participanteEntidad.getObservacion() == null) ? ""
                : participanteEntidad.getObservacion());

        /**
         * Agregar Dirección de Nacimiento
         */
        Direccion direccionNacimiento = new Direccion();
        List<Direccion> direccionParticipante = new ArrayList<>();
        direccionNacimiento.setUbicacion(participanteEntidad.getLugarNacimiento());
        TipoDireccion tipoDireccionNacimiento = new TipoDireccion();
        tipoDireccionNacimiento = setDireccionNacimiento();
        direccionNacimiento.setTipoDireccion(tipoDireccionNacimiento);
        // El lugar de Nacimiento del adoptado lo busca en la tabla de direccionParticipante
        // por ello se hace mas abajo
        if (!"ADO".equalsIgnoreCase(participante.getRol())) {
            direccionParticipante.add(direccionNacimiento);
        }

        /**
         * Agregar Direccion de Residencia
         */
        Direccion direccionResidencia = new Direccion();
        DireccionParticipanteEntidad direccionParticEntity;

        direccionParticEntity
                = direccionParticipanteRepository.findByParticipanteId(participanteEntidad.getId());

        if (direccionParticEntity != null) {
            direccionResidencia.setUbicacion(direccionParticEntity.getDireccionUbicacion());
            TipoDireccion tipoDireccionResidencia = new TipoDireccion();
            tipoDireccionResidencia.setCodigo("RES");
            tipoDireccionResidencia.setNombre("RESIDENCIA");
            tipoDireccionResidencia.setDescripcion("RESIDENCIA");
            if ("ADO".equalsIgnoreCase(participante.getRol())) {
                tipoDireccionResidencia = setDireccionNacimiento();
            }
            direccionResidencia.setTipoDireccion(tipoDireccionResidencia);

            if (direccionParticEntity.getParroquia() != null) {
                parroquiaEntidad = parroquiaRepository.findOne(direccionParticEntity.getParroquia().getId());
                parroquia = parroquiaMapper.entityToBO(parroquiaEntidad);

                municipioEntidad = consultarMunicipio(direccionParticEntity.getParroquia().getId());  //(Long.valueOf(parroquia.getCodigo()));

                municipio = municipioMapper.entityToBO(municipioEntidad);
                municipio.setParroquias(new ArrayList<Parroquia>());

                estadoEntidad = consultarEstado(municipioEntidad.getId());
                estado = estadoMapper.entityToBO(estadoEntidad);
                estado.setMunicipios(new ArrayList<Municipio>());
            }

            //Busca el pais guardado en la base de datos o lo busca si viene el dato al buscar la parroquia
            if (direccionParticEntity.getPais() != null) {
                paisEntidad = consultarPaisPorId(direccionParticEntity.getPais().getId());
            } else if (estado.getCodigo() != null) {
                paisEntidad = consultarPais(Long.valueOf(estado.getCodigo()));
            }

            pais = paisMapper.entityToBO(paisEntidad);
//            pais.setEstados(new ArrayList<Estado>() );
            //comunidad Indigena
            if (participanteEntidad.getComunidadIndigena() != null) {
                participante.setComunidadIndigena(participanteEntidad.getComunidadIndigena().getNombre());
            }

            direccionResidencia.setParroquia(parroquia);
            direccionResidencia.setMunicipio(municipio);
            direccionResidencia.setEstado(estado);
            direccionResidencia.setPais(pais);
            direccionParticipante.add(direccionResidencia);
        } else {
            if ("ADO".equalsIgnoreCase(participante.getRol())) {
                direccionParticipante.add(direccionNacimiento);
            }
        }

        participante.setDireccion(direccionParticipante);
        return participante;
    }

    /**
     * Metodo que permite mapear una lista de Participante y ParticipanteEntidad
     *
     * @param participantes Objeto de la capa ontologico Participante
     * @return Lista de {@link ParticipanteEntidad}
     */
    public List<ParticipanteEntidad> bosToEntities(List<Participante> participantes) {

        List<ParticipanteEntidad> participantesEntidad = new ArrayList<>();
        ParticipanteEntidad participanteEntidad;

        for (Participante ttp : participantes) {
            participanteEntidad = this.boToEntity(ttp);
            participantesEntidad.add(participanteEntidad);
        }

        return participantesEntidad;
    }

    /**
     * Metodo que permite mapear una lista de ParticipanteEntidad y Participante
     *
     * @param participanteEntidadList lista de objeto ParticipanteEntidad
     * @return Lista de {@link participante}
     */
    public List<Participante> entitiesToBOs(List<ParticipanteEntidad> participanteEntidadList) {

        List<Participante> participantes = new ArrayList<>();
        Participante participante;

        for (ParticipanteEntidad ttp : participanteEntidadList) {
            participante = this.entityToBO(ttp);
            participantes.add(participante);
        }

        return participantes;
    }

    /**
     * Metodo que setea los atributos del tipo de direccion de nacimiento
     *
     * @return Pojo TipoDireccion
     */
    private TipoDireccion setDireccionNacimiento() {

        TipoDireccion tipoDireccionNacimiento = new TipoDireccion();

        tipoDireccionNacimiento.setCodigo(CODIGO_NAC);
        tipoDireccionNacimiento.setNombre(NOMBRE_NACIMIENTO);
        tipoDireccionNacimiento.setDescripcion(DESCRIPCION_NACIMIENTO);

        return tipoDireccionNacimiento;
    }

    /*
     * Metodo que permite consultar el Tipo de Participante apartir del codigo
     *
     * @param codigoTipo string con el codigo del tipo participante.
     * @return TipoParticipanteEntidad objeto con la informacion de la entidad
     * TipoParticipante.
     */
    private TipoParticipanteEntidad consultarTipoParticipante(String codigoTipo) {
        return tipoParticipanteRepository.findByCodigo(codigoTipo);
    }

    /**
     * Metodo que permite consulta el Tipo de Documento de Identidad segun el codigo.
     *
     * @param codigo Long con el id del tipo de documento de identidad.
     * @return TipoDocIdentidadEntidad objeto con la informacion de la entidad TipoDocIdentidad.
     */
    private TipoDocIdentidadEntidad consultarTipoDocIdentidad(Long codigo) {

        LOGGER.info("consultarTipoDocIdentidad --> " + codigo);
        TipoDocIdentidadEntidad tipoDocIndeDocIdentidad = null;

        try {
            tipoDocIndeDocIdentidad = tipoDocIdentidadRepository.findById(codigo);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando el tipo documento de identidad ", e);
        }

        return tipoDocIndeDocIdentidad;
    }

    /**
     * Metodo que permite consultar el municipio dado el id de la parroquia
     *
     * @param idParroquia Long id de la parroquia
     * @return Objeto entidad de tipo MunicipioEntidad con los datos del municipio consultado
     */
    private MunicipioEntidad consultarMunicipio(Long idParroquia) {

        LOGGER.info("consultarMunicipio --> " + idParroquia);
        MunicipioEntidad municipioEntidad = null;

        try {
            municipioEntidad = municipioRepository.findByParroquiasIdIn(idParroquia);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando el municipio para la parroquia: " + idParroquia, e);
        }

        return municipioEntidad;
    }

    /**
     * Metodo que permite consultar el estado dado el id de un municipio
     *
     * @param idMunicipio String id del municipio
     * @return Objeto entidad del tipo EstadoEntidad con la informacion del estado consultado
     */
    private EstadoEntidad consultarEstado(Long idMunicipio) {

        LOGGER.info("consultarEstado --> " + idMunicipio);
        EstadoEntidad estadoEntidad = null;

        try {
            estadoEntidad = estadoRepository.findByMunicipiosIdIn(idMunicipio);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando el estado para el municipio: " + idMunicipio, e);
        }

        return estadoEntidad;
    }

    /**
     * Metodo que permite consultar el pais dado el id de un estado
     *
     * @param idEstado String id del estado
     * @return Objeto entidad del tipo PaisEntidad con la informacion del pais consultado
     */
    private PaisEntidad consultarPais(Long idEstado) {

        LOGGER.info("consultarPais --> " + idEstado);
        PaisEntidad paisEntidad = null;

        try {
            paisEntidad = paisRepository.findByEstadosIdIn(idEstado);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando el pais para el estado: " + idEstado, e);
        }

        return paisEntidad;
    }

    /**
     * Metodo que permite consultar el pais dado el id de un pais
     *
     * @param idPais Lond Id del pais
     * @return Objeto entidad del tipo PaisEntidad con la informacion del pais consultado
     */
    private PaisEntidad consultarPaisPorId(Long idPais) {

        LOGGER.info("consultarPaisPorId --> " + idPais);
        PaisEntidad paisEntidad = null;

        try {
            paisEntidad = paisRepository.findOne(idPais);
        } catch (NullPointerException e) {
            LOGGER.error("Error consultando el pais: " + idPais, e);
        }

        return paisEntidad;
    }

    /**
     * Metodo que permite consultar la comunidad indigena dado el nombre de la comunidad indigena
     *
     * @param comunidadIndigena String Nombre de la comunidad indigena
     * @return Objeto entidad del tipo ComunidadIndigenaEntidad
     */
    private ComunidadIndigenaEntidad consultarComunidadIndigena(String comunidadIndigena) {
        return comunidadIndigenaRepository.findByNombre(comunidadIndigena);
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
     * Metodo que permite buscar el valor de una propiedad en el archivo properties del servicio funcionario
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        System.out.println("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOGGER.error("Error leyendo properties: " + ex.getMessage());
        }
        System.out.println("buscarValorProperties --> valor --> " + valor);

        return valor;
    }
    
    /**
     * Metodo que devuelve si una cadena es numerico
     * 
     * @param cadena String que contiene el valor de una cadena
     * @return Devuelve verdadero si la cadena es numerica, y falso en caso contrario
     */
    private static boolean isNumeric(String cadena) {
        
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
