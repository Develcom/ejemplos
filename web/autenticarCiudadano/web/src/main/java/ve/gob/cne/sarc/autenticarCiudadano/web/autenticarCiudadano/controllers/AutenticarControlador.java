package ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ve.gob.cne.sarc.autenticarCiudadano.utils.ArchivoUtils;
import ve.gob.cne.sarc.autenticarCiudadano.utils.DJUtils;
import ve.gob.cne.sarc.autenticarCiudadano.utils.ProcesadorListas;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.ciudadano.servicio.cliente.CiudadanoServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.catalogo.TipoDireccion;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.IntegranteConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;

@Controller
public class AutenticarControlador {

    @Autowired
    private AdministradorPropiedades properties;

    private String DIRECCION_PLANTILLA = "";

    public static String MENSAJE_ERROR_AUT = "El n\u00famero de documento ingresado no se ha encontrado en la base de datos, ingrese un nuevo n\u00famero o presione \"Cancelar\" para finalizar la operaci\u00f3n";
    public static String MENSAJE_ERROR_REP = "El n\u00famero de documento ingresado ha sido autenticado con anterioridad, ingrese un nuevo n\u00famero o presione \"Cancelar\" para finalizar la operaci\u00f3n";
    public static final String[] POSIBLES_DECLARANTES = new String[] { "MAD", "PAD" };
    public static final String[] PREFIJOS = new String[] { "TDM", "TDP" };
    public static final String RUTA_FORMULARIO_AUTENTICACION = "/resources/js/json/comunes/formulario_MAD.json";
    public static String MENSAJE_ERROR_FECHA_FALLECIDO_MENOR_PADRES = "El fallecido no puede ser mayor que los padres, por favor corrija la fecha de nacimiento del mismo o presione \"Cancelar\" para finalizar la operaci\u00f3n";
    public static String MENSAJE_ERROR_DECLARANTE_MENOR_EDAD = "Delarante debe ser mayor de edad, por favor corrija la fecha de nacimiento del mismo o presione \"Cancelar\" para finalizar la operaci\u00f3n";
    // public static final String RUTA_PDF = "C:/tmp/Declaracion_jurada_";
    public static final String RUTA_PDF = "Declaracion_jurada_";
    // public static final String RUTA_PDF = "/home/jboss/tmp/Declaracion_jurada_";

    @Autowired
    private CiudadanoServicioCliente cliente;

    @Autowired
    private CatalogoServicioCliente catalogoServicioCliente;

    @Autowired
    private SolicitudServicioCliente solicitudServicioCliente;

    @Autowired
    private FuncionarioServicioCliente funcionarioServicioCliente;

    @Autowired
    private SeguridadServicioCliente seguridadServicioCliente;

    public static String tokenAutenticar;

    @Autowired
    ServletContext context;
    Logger log = Logger.getLogger(getClass().getName());

    @RequestMapping(value = "/iniciarAutenticar", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String iniciarAutenticar(HttpServletRequest request, HttpSession sesion) throws Exception {
        LinkedHashMap<String, String> listaCodigosNombresParticipantes = (LinkedHashMap<String, String>) sesion.getAttribute("listaCodigosNombresParticipantes");
        String codigoParticipante = listaCodigosNombresParticipantes.keySet().iterator().next();
        String nombreParticipante = listaCodigosNombresParticipantes.get(codigoParticipante);
        Participante participante = new Participante();
        participante.setRol(codigoParticipante);
        participante.setParentesco(nombreParticipante);
        participante.setAutenticado(false);
        List<Participante> listaParticipantes = new ArrayList<Participante>();
        listaParticipantes.add(participante);
        sesion.setAttribute("listaParticipantes", listaParticipantes);
        JSONArray fields = new JSONArray();

        String formularioString = ArchivoUtils.leerArchivo((String) sesion.getAttribute("rutaBase")+ "formulario_MAD.json", context);
        formularioString = formularioString.replace("ROL", participante.getRol());
        formularioString = formularioString.replace("PARENTESCO", participante.getParentesco());
        JSONObject jsonObject = new JSONObject(formularioString);
        log.info(formularioString);
        fields = (JSONArray) jsonObject.get("fields");

        JSONObject modelojson = new JSONObject();
        modelojson.put(participante.getRol(), participante2Json(participante, false, false, false, false, true, sesion));
        modelojson.put("validador", true);
        modelojson.put("falloAutenticacion", false);
        modelojson.put("autenticar", true);
        modelojson.put("rol", participante.getRol());
        modelojson.put("activo", "3");
        modelojson.put("titulo", "Autenticaci\u00f3n de ciudadanos");
        modelojson.put("inicial_etapa", true);
        modelojson.put("ancho", true);
        String sjson = "{\"fields\":" + fields.toString() + ",\"modelo\":" + modelojson.toString() + "}";
        return sjson;

    }

    @RequestMapping(value = "/validarParticipante", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String validarParticipante(@RequestBody Map<String, String> data, HttpSession sesion)
            throws Exception {
        String rol = data.get("rol");

        tokenAutenticar = DroolsControlador.tokenAutenticar;
        List<Participante> listaParticipantes = (List<Participante>) sesion.getAttribute("listaParticipantes");
        // indica un error si el numero introducido en el formulario ha sido autenticado con anterioridad
        boolean errorNroRepeido = false;
        // indica un fallo de autenticacion si el ciudadano no se encuentra en la base de datos con el
        // numero introducido en el formulario
        boolean falloAutenticacion = false;
        boolean errorValidacionParticipante = false;
        String errorValidacionDeclarante;
        boolean errorValidacionOtroDeclarante = false;
        // indica si ha terminado la etapa de autenticacion y se inicia la etapa de declaracion jurada
        boolean aDeclaracion = false;
        /*
         * indica que se ha terminado la etapa de autenticacion de participantes con identificacion y se debe insertar
         * los datos de los participantes en la solicitud
         */
        boolean guardar = false;
        // indica si el tramite incluye el proceso de declaracion jurada
        boolean hayDeclaracionJurada = Boolean.parseBoolean(data.get("repetir_testigo"));
        LinkedHashMap<String, String> listaCodigosNombresParticipantes = (LinkedHashMap<String, String>) sesion
                .getAttribute("listaCodigosNombresParticipantes");
        String parentescoParticipante = listaCodigosNombresParticipantes.get(rol);
        // rol del proximo participante a autenticar
        String proximoRol = null;
        int indiceDeparticipante = encontrarIndiceParticipanteEnLista(listaParticipantes, rol);
        Participante participante = listaParticipantes.remove(indiceDeparticipante);
        /*
         * variable que indica si se crea un nuevo participante a partir de un nuevo numero de documento o se utiliza
         * uno de la lista de autenticados anteriormente como testigos
         */
        boolean repetir = Boolean.parseBoolean(data.get(rol + "_repetir_testigo"));
        /*
         * String[] ss = sesion.getValueNames(); for (int x = 0; x < ss.length; x++) { log.info("--->sesiones: " + ss[x]
         * + "\n"); }
         */
        //String tipoDocumento = (String) sesion.getAttribute("TipoDocumento");
        String tipoDocumentoMadre = (String) sesion.getAttribute("TipoDocumentoPadre");
        String tipoDocumentoPadre = (String) sesion.getAttribute("TipoDocumentoMadre");
        log.info("**************Datos del  participante.getRol(): " + participante.getRol());
        log.info(" mad: " + tipoDocumentoMadre + " pad: " + tipoDocumentoPadre + "\n");
        if (repetir) {// Se carga uno de los participantes ya autenticados y se asigna un nuevo rol
            participante = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes,
                    data.get("indice_repetir"));
            participante.setAutenticado(true);
            participante.setRol(rol);
            participante.setParentesco(parentescoParticipante);
        } else {// se consulta al servicio ciudadano con los datos provistos por el usuario
            String[] datosDocumento = ProcesadorListas.getNumeroDocumento(data, rol);
            for (int i = 0; i < datosDocumento.length; i++) {
                log.info("----->datos: " + datosDocumento[i] + "\n---");
            }
            // log.info("----->datos: "+datosDocumento.toString().split(""));

            log.info("---->rol: " + participante.getRol());

            DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
            TipoDocumento tipoDocumento1 = new TipoDocumento();
            ArrayList<DocumentoIdentidad> listaDocumentosID = new ArrayList<DocumentoIdentidad>();
            log.info(" madq: " + tipoDocumentoMadre + " padq: " + tipoDocumentoPadre + "\n");
            log.info("---po0: " + datosDocumento[0] + " po1: " + datosDocumento[1]);
            // listaParticipantes
            if ("MAD".equalsIgnoreCase(participante.getRol()) && tipoDocumentoMadre != null) {
                tipoDocumento1.setCodigo(tipoDocumentoMadre);
                documentoIdentidad.setTipoDocumento(tipoDocumento1);
                documentoIdentidad.setNumero(datosDocumento[1]);
                listaDocumentosID.add(documentoIdentidad);
                participante.setDocumentoIdentidad(listaDocumentosID);
            }

            if ("PAD".equalsIgnoreCase(participante.getRol()) && tipoDocumentoPadre != null) {
                tipoDocumento1.setCodigo(tipoDocumentoPadre);
                documentoIdentidad.setTipoDocumento(tipoDocumento1);
                documentoIdentidad.setNumero(datosDocumento[1]);
                listaDocumentosID.add(documentoIdentidad);
                participante.setDocumentoIdentidad(listaDocumentosID);
            }

            participante.setDocumentoIdentidad(ProcesadorListas.procesaDatosDelDocumentoID(datosDocumento,
                    participante.getRol()));

            

            log.info("*****************Fecha de nacimiento***************" + data.get(rol + "_dia") + " "
                    + data.get(rol + "_mes") + " " + data.get(rol + "_ano"));
            Integer mes = Integer.parseInt(data.get(rol + "_mes"));
            mes = mes - 1;
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.get(rol + "_dia")));
            calendario.set(Calendar.MONTH, Integer.parseInt(data.get(rol + "_mes")));
            calendario.set(Calendar.YEAR, Integer.parseInt(data.get(rol + "_ano")));
            participante.setFechaNacimiento(calendario.getTime());
            log.info("FECHA DE NACIMIENTO COMPLETA: " + participante.getFechaNacimiento());
            errorValidacionOtroDeclarante = ProcesadorListas.calcularOtroDeclaranteMayorDeEdad(rol,
                    participante.getFechaNacimiento());
            errorValidacionParticipante = ProcesadorListas.fallecidoMenorA(data);
            // TODO verificar el tipo de documento
            participante.setTipoDocumento(getTipoDocumento(data, rol));
            Ciudadano ciudadano = null;
            errorNroRepeido = ProcesadorListas.estaNumeroDeDocumentoRepetido(listaParticipantes, datosDocumento[1],
                    indiceDeparticipante);

            if (!errorNroRepeido && !errorValidacionParticipante && !errorValidacionOtroDeclarante) {
                // consulta al servicio de validacion de participantes
                ciudadano = cliente.consultarPorNumeroDocumento(datosDocumento[1], tokenAutenticar);
            }
            if (ciudadano != null && ciudadano.getPrimerApellido() != null) {
                participante.setAutenticado(true);
                participante.setPrimerNombre(ciudadano.getPrimerNombre());
                participante.setSegundoNombre(ciudadano.getSegundoNombre());
                participante.setPrimerApellido(ciudadano.getPrimerApellido());
                participante.setSegundoApellido(ciudadano.getSegundoApellido());
            } else {
                participante.setAutenticado(false);
                falloAutenticacion = true;
            }
        }
        /**
         * Inicia el proceso de construccion de la vista a ser presentada al usuario
         */
        listaParticipantes.add(indiceDeparticipante, participante);
        if (falloAutenticacion || errorNroRepeido || errorValidacionParticipante || errorValidacionOtroDeclarante) {
            proximoRol = rol;
        } else if (indiceDeparticipante < listaCodigosNombresParticipantes.size() - 1) {
            indiceDeparticipante++;
            // Busca en la lista enviada de Drools el rol del proximo participante a autenticar
            Iterator<String> iterador = listaCodigosNombresParticipantes.keySet().iterator();
            while (iterador.hasNext()) {
                if (rol.equals(iterador.next())) {
                    proximoRol = iterador.next();
                    break;
                }
            }
            Participante participante12 = null;
            participante12 = new Participante();
            participante12.setRol(proximoRol);
            participante12.setParentesco(listaCodigosNombresParticipantes.get(proximoRol));
            participante12.setAutenticado(false);
            int indiceDeParticipanteEnLista = encontrarIndiceParticipanteEnLista(listaParticipantes, proximoRol);
            if (indiceDeParticipanteEnLista != -1) {
                listaParticipantes.set(indiceDeParticipanteEnLista, participante12);
            } else
                listaParticipantes.add(participante12);
        } else {
            if (hayDeclaracionJurada || hayCartaComunal(sesion)) aDeclaracion = true;
            else {
                guardar = true;
            }
        }
        /*
         * verificar si a partir del participante procesado en los pasos anteriores se puede utilizar la opcion de
         * repetir
         */
        hayDeclaracionJurada = ProcesadorListas.habilitarOpcionRepetir(listaParticipantes, participante);
        /*
         * Inicia la creacion del formulario que se presentara en pantalla JSON Array que contiene los campos del
         * formulario Formly
         */
        JSONArray fields = new JSONArray();
        // Inicializa el modelo asociado al formulario
        JSONObject modelojson = new JSONObject();
        // Carga el encabezado de la tabla de participantes autenticados
        if (indiceDeparticipante >= 0) fields.put(crearCampoFormly("el", "encabezadoLista", "", null, null, null, null,
                null, false));
        for (int i = 0; i <= indiceDeparticipante; i++) {
            // Presenta los participantes anteriormente autenticados
            if (hayDeclaracionJurada && !aDeclaracion && (i == indiceDeparticipante) && !"PAP".equals(proximoRol)) {
                List<Participante> participantesRepetibles = ProcesadorListas.encontrarTestigosAutenticados(
                        listaParticipantes, proximoRol, data.get("indice_repetir"), indiceDeparticipante);

                cargarPreguntaRepetir((String) sesion.getAttribute("rutaBase") + "repetir_testigo.json", fields,
                        proximoRol + ".repetir_testigo", listaCodigosNombresParticipantes.get(proximoRol),
                        participantesRepetibles);
            }
            Participante p = listaParticipantes.get(i);
            cargarCamposFormulario(RUTA_FORMULARIO_AUTENTICACION, p, fields);
            modelojson.put(
                    p.getRol(),
                    participante2Json(p, i == indiceDeparticipante ? falloAutenticacion : true,
                            i == indiceDeparticipante && !aDeclaracion && hayDeclaracionJurada && !falloAutenticacion,
                            i == indiceDeparticipante ? falloAutenticacion : false,
                            i == indiceDeparticipante ? errorValidacionOtroDeclarante : false,
                            i == indiceDeparticipante ? errorValidacionOtroDeclarante : true, sesion));
        }
        sesion.setAttribute("listaParticipantes", listaParticipantes);
        modelojson.put("rol", proximoRol);
        modelojson.put("falloAutenticacion", falloAutenticacion);
        modelojson.put("autenticar", !aDeclaracion);
        modelojson.put("repetir_testigo", hayDeclaracionJurada && !"PAP".equals(proximoRol));
        modelojson.put("a_declaracion", aDeclaracion);
        modelojson.put("ancho", true);
        if (aDeclaracion) modelojson.put("final_etapa", aDeclaracion);
        modelojson.put("guardar", guardar);
        modelojson.put("titulo", "Autenticaci\u00f3n de ciudadanos");
        modelojson.put("mensaje_error", errorNroRepeido ? MENSAJE_ERROR_REP : MENSAJE_ERROR_AUT);
        if(errorValidacionParticipante){
            modelojson.put("mensaje_error", MENSAJE_ERROR_FECHA_FALLECIDO_MENOR_PADRES);   
        }else if(errorValidacionOtroDeclarante){
            modelojson.put("mensaje_error",  MENSAJE_ERROR_DECLARANTE_MENOR_EDAD);
        }
        JSONObject formulario = new JSONObject();
        formulario.put("fields", fields);
        formulario.put("modelo", modelojson);
        return formulario.toString();
    }

    public boolean hayCartaComunal(HttpSession sesion) {

        /*
         * if (sesion.getAttribute("TipoDocumento") != null && "CM".equals(sesion.getAttribute("TipoDocumento"))) {
         * return true; }
         */
        if (sesion.getAttribute("TipoDocumentoMadre") != null && "CM".equals(sesion.getAttribute("TipoDocumentoMadre"))) {
            log.info("***************Tipo de documento Madre " + sesion.getAttribute("TipoDocumentoMadre"));
            return true;
        } else if (sesion.getAttribute("TipoDocumentoPadre") != null
                && "CM".equals(sesion.getAttribute("TipoDocumentoPadre"))) {
            log.info("***************Tipo de documento Padre " + sesion.getAttribute("TipoDocumentoPadre"));
            return true;
        } else if (sesion.getAttribute("TipoDocumentoPadre") != null
                && "CM".equals(sesion.getAttribute("TipoDocumentoPadre"))
                && sesion.getAttribute("TipoDocumentoMadre") != null
                && "CM".equals(sesion.getAttribute("TipoDocumentoMadre"))) {
            log.info("***************Tipo de documento Padre " + sesion.getAttribute("TipoDocumentoPadre") + "sasdvasd"
                    + sesion.getAttribute("TipoDocumentoMadre"));
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Presenta las opciones parea repetir testigos anteriormente autenticados
     * </p>
     * 
     * @param ruta
     *            : ruta a la plantilla formly
     * @param fields
     *            : arreglo de campos en el que se carga la plantilla
     * @param key
     *            : clave del campo formly
     * @param participantesRepetibles
     *            : lista de participantes que representa las opciones
     * @throws IOException
     * @throws JSONException
     */
    public void cargarPreguntaRepetir(String ruta, JSONArray fields, String key, String parentesco,
            List<Participante> participantesRepetibles) throws IOException, JSONException {
        String formularioString = ArchivoUtils.leerArchivo(ruta, context);
        formularioString = formularioString.replace("mKey", key);
        formularioString = formularioString.replace("PARENTESCO", parentesco);
        JSONArray opciones = new JSONArray();
        for (Participante p : participantesRepetibles) {
            JSONObject objCombo = new JSONObject();
            objCombo.put("name", p.getPrimerApellido() + " " + p.getPrimerNombre() + " "
                    + p.getDocumentoIdentidad().get(0).getNumero());
            objCombo.put("value", p.getRol());
            opciones.put(objCombo);
        }
        log.info("************Opciones repetibles: " + opciones.toString());
        formularioString = formularioString.replace("OPCIONES", opciones.toString());
        JSONObject jsonObject = new JSONObject(formularioString);
        JSONArray campos = (JSONArray) jsonObject.get("fields");
        for (int j = 0; j < campos.length(); j++) {
            fields.put(campos.get(j));
        }
    }

    /**
     * 
     * @param ruta
     *            : ruta del archivo json que contiene el formulario formly
     * @param p
     *            : participante
     * @param fields
     *            : array con los campos del formulario que se anaden
     * @throws IOException
     * @throws JSONException
     */
    public void cargarCamposFormulario(String ruta, Participante p, JSONArray fields) throws IOException, JSONException {
        String formularioString = ArchivoUtils.leerArchivo(ruta, context);
        formularioString = formularioString.replace("ROL", p.getRol());
        formularioString = formularioString.replace("PARENTESCO", p.getParentesco());
        JSONObject jsonObject = new JSONObject(formularioString);
        JSONArray campos = (JSONArray) jsonObject.get("fields");
        for (int j = 0; j < campos.length(); j++) {
            fields.put(campos.get(j));
        }
    }

    public String getTipoDocumento(Map<String, String> data, String rol) {
        return "CED".equalsIgnoreCase(data.get(rol + "_tipoDocumento")) ? data.get(rol + "_nacionalidad") : "P";
    }

    /**
     * <p>
     * Crea los campos del formulario formly
     * </p>
     * 
     * @param key
     * @param type
     * @param label
     * @param names
     *            Nombres de los elementos del radio
     * @param values
     *            Valores de los elementos del radio
     * @return
     * @throws JSONException
     */
    public JSONObject crearCampoFormly(String key, String type, String label, String templateUrl, String template,
            String[] names, Object[] values, String hideExpression, boolean required) throws JSONException {
        JSONObject fieldJson1 = new JSONObject();
        fieldJson1.put("key", key);
        fieldJson1.put("type", type);
        fieldJson1.put("templateUrl", templateUrl);
        fieldJson1.put("template", template);
        if (hideExpression != null) fieldJson1.put("hideExpression", hideExpression);
        JSONObject templateOptions = new JSONObject();
        templateOptions.put("label", label);
        templateOptions.put("required", required);
        if (names != null) {
            JSONArray opciones = new JSONArray();
            for (int i = 0; i < names.length; i++) {
                JSONObject opcion = new JSONObject();
                opcion.put("name", names[i]);
                opcion.put("value", values[i]);
                opciones.put(opcion);
            }
            templateOptions.put("options", opciones);
        }
        fieldJson1.put("templateOptions", templateOptions);
        return fieldJson1;
    }

    /**
     * 
     * @param participante
     *            : objeto participante a ser convertido a json
     * @param procesado
     *            : indica si el participante ha pasado por el proceso de autenticacion
     * @param habilitarRepetir
     *            : indica que el participante puedeser utilizado para otro ROL
     * @return
     * @throws JSONException
     */
    public JSONObject participante2Json(Participante participante, boolean procesado, boolean habilitarRepetir,
            boolean falloAutenticacion, boolean errorValidacionOtroDeclarante, boolean ValidacionOtroDeclarante,
            HttpSession sesion) throws JSONException {
        JSONObject participanteJson = new JSONObject();
        participanteJson.put("primerNombre", participante.getPrimerNombre());
        participanteJson.put("primerApellido", participante.getPrimerApellido());
        participanteJson.put("segundoNombre", participante.getSegundoNombre());
        participanteJson.put("segundoApellido", participante.getSegundoApellido());
        participanteJson.put("rol", participante.getRol());
        participanteJson.put("falloAutenticacion", falloAutenticacion);
        if (participante.getDocumentoIdentidad() != null) {
            DocumentoIdentidad documentoId = participante.getDocumentoIdentidad().get(0);
            String[] ndoc = participante.getDocumentoIdentidad().get(0).getNumero().split("-");
            String tipoDocumento = documentoId.getTipoDocumento().getCodigo();
            if ("ced".equalsIgnoreCase(tipoDocumento) || "DP".equalsIgnoreCase(tipoDocumento)) {
                participanteJson.put("nacionalidad", ndoc[0]);
                participanteJson.put("cedula", ndoc[1]);
                participanteJson.put("documentoIdentidad", ndoc[0] + ndoc[1]);
            } else {
                participanteJson.put("pasaporte", ndoc[0]);
                participanteJson.put("documentoIdentidad", ndoc[0]);
            }
            participanteJson.put("tipoDocumento", tipoDocumento);
        }
        List<Participante> listaParticipantes = (List<Participante>) sesion.getAttribute("listaParticipantes");
        Participante mP = ProcesadorListas.getParticipantePorDocumento(listaParticipantes, participante);
        // Datos de la direccion
        if (mP != null) {
            List<Direccion> direccion = mP.getDireccion();
            if (direccion != null) {
                Direccion pDireccion = direccion.get(0);
                participanteJson.put("Direccion", pDireccion.getUbicacion());
                participanteJson.put("Parroquia", pDireccion.getParroquia().getCodigo());
                participanteJson.put("Municipio", pDireccion.getMunicipio().getCodigo());
                participanteJson.put("Estado", pDireccion.getEstado().getCodigo());
                participanteJson.put("Pais", pDireccion.getPais().getCodigo());
            }
        }

        Date fechaNacimiento = participante.getFechaNacimiento();
        if (fechaNacimiento != null) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fechaNacimiento);
            participanteJson.put("dia", calendario.get(Calendar.DAY_OF_MONTH));
            participanteJson.put("mes", calendario.get(Calendar.MONTH) + 1);
            participanteJson.put("ano", calendario.get(Calendar.YEAR));
        }

        participanteJson.put("autenticado", participante.isAutenticado());
        if (!habilitarRepetir) participanteJson.put("repetir_testigo", habilitarRepetir);
        participanteJson.put("procesado", procesado);
        return participanteJson;
    }

    @RequestMapping(value = "/iniciarDeclaracion", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String iniciarDeclaracion(@RequestBody Map<String, String> data, HttpSession sesion,
            HttpServletResponse response) throws JSONException, IOException {
        if (sesion.getAttribute("listaFormularios") != null) {
            Map<String, String> listaFormularios = (HashMap<String, String>) sesion.getAttribute("listaFormularios");
            String rol = null, tag = null;
            // codigo del participante
            Iterator<String> key = listaFormularios.keySet().iterator();
            rol = key.next();
            // Nombre del participante segun su codigo
            tag = listaFormularios.get(rol);
            List<Participante> listaParticipantes = (List<Participante>) sesion.getAttribute("listaParticipantes");
            DeclaracionJurada declaracionJurada = new DeclaracionJurada();
            declaracionJurada.setFechaDeclaracion(new Date());
            declaracionJurada.setNumeroSolicitud(((Solicitud) sesion.getAttribute("solicitud")).getNumeroSolicitud());
            declaracionJurada.setTipoDeclaracion("AUCIU");
            declaracionJurada.setParticipantes(new ArrayList<Participante>());
            sesion.setAttribute("declaracionJurada", declaracionJurada);
            return generarFormularioDeDeclaracion(listaParticipantes, rol, tag, true, sesion);
        }

		if(getListaFormulariosCC(sesion) != null){
			String nMCC = data.get("nMCC");
			CartaConsejoComunal cartaConsejoComunal = new CartaConsejoComunal();
			cartaConsejoComunal.setIntegrantes(new ArrayList<IntegranteConsejoComunal>());
			sesion.setAttribute("cartaConsejoComunal", cartaConsejoComunal);
			cartaConsejoComunal.setNumeroSolicitud(((Solicitud)sesion.getAttribute("solicitud")).getNumeroSolicitud());
			return generarFormularioCC(sesion, null, nMCC);
		}
		return null;
	}
	
	
	@RequestMapping(value="/datosDeclaracion", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.CREATED)
	public String validarDeclaracion(@RequestBody Map<String,String> data,HttpSession sesion, HttpServletResponse response) throws JSONException, IOException{
		Map<String,String> listaFormularios=(HashMap<String,String>) sesion.getAttribute("listaFormularios");
		List<Participante> listaParticipantes=(ArrayList<Participante>) sesion.getAttribute("listaParticipantes");
		//datos del participante a presentar en el proximo formulario
		String rol=data.get("rol"), proximoRol=null, proximoTag=null;
		Iterator<String> key = listaFormularios.keySet().iterator();
		do{
			String mRol = key.next();
			if(mRol.equals(rol) && key.hasNext()){
				proximoRol = key.next();
				proximoTag = listaFormularios.get(proximoRol);
				break;
			}
		}while(key.hasNext());
		//lee los datos enviados desde el formulario
		if(!data.containsKey("presentarDeclaracion")){
			ArrayList<Direccion> direcciones=new ArrayList<Direccion>();
			Direccion direccion=new Direccion();
			direccion.setUbicacion(data.get(rol + "_Direccion"));
			Pais pais = new Pais();
			pais.setCodigo(data.get(rol+"_Pais"));
			direccion.setPais(pais);
			
			Estado estado= null;//new Estado();
			String cEstado = (data.get(rol+"_Estado"));
			List<Estado> mEstados = catalogoServicioCliente.consultarEstadoPorPais(pais.getCodigo(), tokenAutenticar);
			for(Estado e : mEstados){
				if(cEstado.equalsIgnoreCase(e.getCodigo())){
					estado = e;
					break;
				}
			}
			direccion.setEstado(estado);
			Municipio municipio= null;
			String cMunicipio = data.get(rol+"_Municipio");
			List<Municipio> mMunicipios = catalogoServicioCliente.consultarMunicipioPorEstado(estado.getCodigo(), tokenAutenticar);
			for(Municipio m : mMunicipios){
				if(cMunicipio.equalsIgnoreCase(m.getCodigo())){
					municipio = m;
					break;
				}
			}
			direccion.setMunicipio(municipio);
			Parroquia parroquia = null;
			String cParroquia = data.get(rol+"_Parroquia");
			List<Parroquia> mParroquias = catalogoServicioCliente.consultarParroquiaPorMunicipio(municipio.getCodigo(), tokenAutenticar);
			for(Parroquia p : mParroquias){
				if(cParroquia.equalsIgnoreCase(p.getCodigo())){
					parroquia = p;
					break;
				}
			}
			direccion.setParroquia(parroquia);
			TipoDireccion tipoDireccion = new TipoDireccion();
			tipoDireccion.setCodigo("RES");
			direccion.setTipoDireccion(tipoDireccion);
			direcciones.add(direccion);
			int indiceParticipanteAmodificar=encontrarIndiceParticipanteEnLista(listaParticipantes, rol);
			Participante part=null;
			//Aplica solo en caso de que sea el padre o la madre
			if(indiceParticipanteAmodificar==-1){
				System.out.println("Creando un  nuevo participante"); 
				part=new Participante();
				part.setRol(rol);
				String rolParticipante=catalogoServicioCliente.consultarTipoParticipantePorCodigo(rol,false, tokenAutenticar).getNombre().toUpperCase();				
				//cargar los datos
				part.setPrimerApellido(data.get(rol+"_primerApellido"));
				part.setPrimerNombre(data.get(rol+"_primerNombre"));
				part.setSegundoApellido(data.get(rol+"_segundoApellido"));
				part.setSegundoNombre(data.get(rol+"_segundoNombre"));
				log.info("*****************Fecha de nacimiento***************"+data.get(rol+"_dia")+" "+data.get(rol+"_mes")+" "+data.get(rol+"_ano"));
				Calendar calendario=Calendar.getInstance();
				calendario.set(Calendar.DAY_OF_MONTH,Integer.parseInt(data.get(rol+"_dia")));
				calendario.set(Calendar.MONTH,Integer.parseInt(data.get(rol+"_mes")));
				calendario.set(Calendar.YEAR,Integer.parseInt(data.get(rol+"_ano")));
				part.setFechaNacimiento(calendario.getTime());
				/*Si el documento no es obligatorio, se debe modificar la operacion de insercion
				  para que no pida tipo.*/
					part.setTipoDocumento("V");
					DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
					TipoDocumento tipoDocumento = new TipoDocumento();
					tipoDocumento.setCodigo("DJ");
					documentoIdentidad.setTipoDocumento(tipoDocumento);
					ArrayList<DocumentoIdentidad> listaDocumentosID = new ArrayList<DocumentoIdentidad>();
					listaDocumentosID.add(documentoIdentidad);
					part.setParentesco(rolParticipante);
					part.setDocumentoIdentidad(listaDocumentosID);
			}else{
				part=listaParticipantes.remove(indiceParticipanteAmodificar);
			}
			part.setDireccion(direcciones);
			listaParticipantes.add(part);
			sesion.setAttribute("listaParticipantes", listaParticipantes);
			((DeclaracionJurada)sesion.getAttribute("declaracionJurada")).getParticipantes().add(part);
			if(rol.equals("TDM1")||rol.equals("TDM2")||rol.equals("TDP1")||rol.equals("TDP2")){
				//retornar a la vista de declaracion jurada
				return "redirect:/plantillaDeclaracion/"+rol;
			}
	}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(generarFormularioDeDeclaracion(listaParticipantes, proximoRol, proximoTag, false, sesion));
		return null;
	}
	
	@RequestMapping(value="/retornarAcc", method = RequestMethod.GET)
	public @ResponseBody String  retornarAcc(HttpServletRequest request, HttpSession sesion) throws JRException, JSONException, IOException{
		log.info("******************hay carta del consejo comunal");
		CartaConsejoComunal cartaConsejoComunal = new CartaConsejoComunal();
		cartaConsejoComunal.setIntegrantes(new ArrayList<IntegranteConsejoComunal>());
		sesion.setAttribute("cartaConsejoComunal", cartaConsejoComunal);
		getListaFormulariosCC(sesion);
		return generarFormularioCC(sesion, null, "0");
	}
	
	
	@RequestMapping(value="/plantillaDeclaracion/{rol}", method = RequestMethod.GET)
	public @ResponseBody String  retornarPlantilla(@PathVariable String rol,HttpServletRequest request, HttpSession sesion) throws JRException, JSONException, IOException, Exception{
		int idFile=(int) ((1+Math.random())*10000/100);
		String dj1 = properties.getProperty("plantilla.jasper.report.ciudadano.declaracionJurada");
		String rutaLocal1=properties.getProperty("sarc.file.tmp");
		System.out.println("Rol: "+rol);
		System.out.println("Ramdom: "+idFile);
		//String rutaBase = "/resources/reportes/Declaracion_jurada.jrxml";
		//String realPath = context.getRealPath(rutaBase);
		  JSONObject login = seguridadServicioCliente.getUsernameCliente(DroolsControlador.tokenAutenticar);
	        
	    Funcionario funcionario = funcionarioServicioCliente.buscarPorLogin(login.getString("username"), DroolsControlador.tokenAutenticar);
	    String nombreOficina = funcionario.getOficina().getNombre();
       // String nResolucion = Integer.toString(funcionario.getNumeroResolucion());
        //String fResolucion = funcionario.getFechaResolucion().toString();
        //String nGaceta = Integer.toString(funcionario.getNumeroGaceta());
       // String fGaceta = funcionario.getFechaGaceta().toString();

		String realPathFdf = rutaLocal1+RUTA_PDF + rol + idFile + ".pdf";
        JasperReport jasperReport = JasperCompileManager.compileReport(dj1);
		List<Participante> listaParticipantes=(List<Participante>) sesion.getAttribute("listaParticipantes");
        Participante participante=ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, rol);
        Map<String,Object> parameters = new HashMap<String,Object>();
        String urlFile=properties.getProperty("plantilla.jasper.report.imagen.logo");
        parameters.put("dia", GenerarActas.obtenerFechaOrHoraActual("DIA"));
        parameters.put("mes", GenerarActas.obtenerFechaOrHoraActual("STRING_MES"));
        parameters.put("ano", GenerarActas.obtenerFechaOrHoraActual("ANIO"));
        String nombreDeclarante = participante.getPrimerNombre()
                + (participante.getSegundoNombre() == null ? " " : " " + participante.getSegundoNombre() + " ")
                + participante.getPrimerApellido()
                + (participante.getSegundoApellido() == null ? " " : " " + participante.getSegundoApellido());
        parameters.put("declarante", nombreDeclarante);
        parameters.put("ceduladec", participante.getDocumentoIdentidad().get(0).getNumero());
        Direccion mDireccion = ProcesadorListas.getDireccionPorTipo(participante, "RES");
        if (mDireccion == null) return null;
        parameters.put("direcciondec", mDireccion.getUbicacion());
        parameters.put("estadodec", mDireccion.getEstado().getNombre());
        parameters.put("municipiodec", mDireccion.getMunicipio().getNombre());
        parameters.put("parroquiadec", mDireccion.getParroquia().getNombre());
        parameters.put("urlFile", urlFile);
        Participante padre = null;
        if (rol.equals("TDM1") || rol.equals("TDM2")) {
            padre = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, "MAD");
        }
        if (rol.equals("TDP1") || rol.equals("TDP2")) {
            padre = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, "PAD");
        }
        String nombreDeclarado = padre.getPrimerNombre()
                + (padre.getSegundoNombre() == null ? " " : " " + padre.getSegundoNombre() + " ")
                + padre.getPrimerApellido()
                + (padre.getSegundoApellido() == null ? " " : " " + padre.getSegundoApellido());
        parameters.put("declarado", nombreDeclarado);
        Direccion pDireccion = ProcesadorListas.getDireccionPorTipo(padre, "RES");
        if (pDireccion == null) return null;
        parameters.put("direcciondeclarado", pDireccion.getUbicacion());
        parameters.put("estadodeclarado", pDireccion.getEstado().getNombre());
        parameters.put("municipiodeclarado", pDireccion.getMunicipio().getNombre());
        parameters.put("parroquiadeclarado", pDireccion.getParroquia().getNombre());
        String funcionarioFullName = StringUtils.capitalize(funcionario.getPrimerNombre().toLowerCase()) + ' '
                + StringUtils.capitalize(funcionario.getPrimerApellido().toLowerCase());
        parameters.put("registrador", funcionarioFullName);
        parameters.put("oficina", nombreOficina);

        if (padre.getDocumentoIdentidad() != null) parameters.put("numdoc", padre.getDocumentoIdentidad().get(0)
                .getNumero());

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint, realPathFdf);
        String server1 = properties.getProperty("sarc.web.servidor.atc");
        JSONArray fields = new JSONArray();
        // String template="<embed width='900' height='450' src='"+ "C:/tmp/" + "Declaracion_jurada_" + rol + idFile +
        // ".pdf' type='application/pdf'></embed>";
        String template = "<embed width='900' height='450' src='" + server1 + "/web-autenticarCiudadano/tmp/"
                + "Declaracion_jurada_" + rol + idFile + ".pdf' type='application/pdf'></embed>";
        fields.put(crearCampoFormly(null, null, null, null, template, null, null, null, false));
        JSONObject formulario = new JSONObject();
        JSONObject modelojson = new JSONObject();

        Map<String, String> listaFormularios = (HashMap<String, String>) sesion.getAttribute("listaFormularios");
        Iterator<String> keys = listaFormularios.keySet().iterator();
        boolean aCC = false;
        do {
            String mRol = keys.next();
            {
                if (mRol.equals(rol) && !keys.hasNext()) {
                    /*
                     * Al finalizarel procesamiento de los datos de la declaracion jurada inicia el procesamiento de
                     * datos de la carta del consejo comunal en caso de que exista
                     */
                    log.info("**********************************proximo formulario es nulo ");
                    if (hayCartaComunal(sesion)) {
                        aCC = true;
                    } else {
                        modelojson.put("guardar", true);
                    }
                }
            }
        } while (keys.hasNext());
        modelojson.put("presentarDeclaracion", true);
        modelojson.put("validador", true);
        modelojson.put("rol", rol);
        modelojson.put("ancho", true);
        modelojson.put("titulo", "Formato de la Declaraci\u00f3n Jurada");
        modelojson.put("a_declaracion", !aCC);
        modelojson.put("aCC", aCC);
        formulario.put("fields", fields);
        formulario.put("modelo", modelojson);
        return formulario.toString();
    }

    Map<String, String> getListaFormulariosCC(HttpSession sesion) {
        Map<String, String> mapa = null;
        String codigo = null;
        String codigo1 = null;
        // sesion.getAttribute("declaranteUnico");

        /*
         * if (sesion.getAttribute("TipoDocumento") != null && "CM".equals(sesion.getAttribute("TipoDocumento"))) { mapa
         * = new LinkedHashMap<String, String>(); codigo = (String) sesion.getAttribute("declaranteUnico");
         * mapa.put(codigo, catalogoServicioCliente.consultarTipoParticipantePorCodigo(codigo, false, tokenAutenticar)
         * .getNombre()); mapa.put("MCC", ""); mapa.put("DCC", ""); }
         */

        if (sesion.getAttribute("TipoDocumentoMadre") != null && "CM".equals(sesion.getAttribute("TipoDocumentoMadre"))) {
            log.info("***************Tipo de documento Madre " + sesion.getAttribute("TipoDocumentoMadre"));
            mapa = new LinkedHashMap<String, String>();
            codigo = "MAD";// (String) sesion.getAttribute("declaranteUnico");
            mapa.put(codigo, catalogoServicioCliente.consultarTipoParticipantePorCodigo(codigo, false, tokenAutenticar)
                    .getNombre());
            mapa.put("MCC", "");
            mapa.put("DCC", "");
        } else if (sesion.getAttribute("TipoDocumentoPadre") != null
                && "CM".equals(sesion.getAttribute("TipoDocumentoPadre"))) {
            log.info("***************Tipo de documento Padre " + sesion.getAttribute("TipoDocumentoPadre"));
            // if (mapa == null)
            mapa = new LinkedHashMap<String, String>();
            codigo = "PAD";
            mapa.put(codigo, catalogoServicioCliente.consultarTipoParticipantePorCodigo(codigo, false, tokenAutenticar)
                    .getNombre());
            mapa.put("MCCP", "");
            mapa.put("DCCP", "");
        } else if (sesion.getAttribute("TipoDocumentoMadre") != null
                && sesion.getAttribute("TipoDocumentoPadre") != null
                && "CM".equals(sesion.getAttribute("TipoDocumentoPadre"))
                && "CM".equals(sesion.getAttribute("TipoDocumentoMadre"))) {
            mapa = new LinkedHashMap<String, String>();
            codigo = "MAD";
            codigo1 = "PAD";
            mapa.put(codigo, catalogoServicioCliente.consultarTipoParticipantePorCodigo(codigo, false, tokenAutenticar)
                    .getNombre());
            mapa.put(codigo1,
                    catalogoServicioCliente.consultarTipoParticipantePorCodigo(codigo1, false, tokenAutenticar)
                            .getNombre());
            mapa.put("MCC", "");
            mapa.put("DCC", "");
        }
        sesion.setAttribute("listaFormulariosCC", mapa);
        return mapa;
    }

    @RequestMapping(value = "/datosCC", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String validarCC(@RequestBody Map<String, String> data, HttpSession sesion, HttpServletResponse response)
            throws JSONException, IOException {
        String rol = data.get("rol");
        // lee los datos enviados desde el formulario
        CartaConsejoComunal cartaConsejoComunal = (CartaConsejoComunal) sesion.getAttribute("cartaConsejoComunal");
        if ("MAD".equals(rol) || "PAD".equals(rol)) {
            Participante part = new Participante();
            ArrayList<Direccion> direcciones = new ArrayList<Direccion>();
            Direccion direccion = new Direccion();
            String direccionLeida = data.get(rol + "_Direccion");
            direccion.setUbicacion(direccionLeida);
            Estado estado = new Estado();
            estado.setNombre(data.get(rol + "_Estado"));
            direccion.setEstado(estado);
            Municipio municipio = new Municipio();
            municipio.setNombre(data.get(rol + "_Municipio"));
            direccion.setMunicipio(municipio);
            Parroquia parroquia = new Parroquia();
            parroquia.setNombre(data.get(rol + "_Parroquia"));
            direccion.setParroquia(parroquia);
            direcciones.add(direccion);
            boolean declarante = false;
            int declaranteContador = 0;
            part.setRol(rol);
            declaranteContador++;
            if (declaranteContador == 1) {
                declarante = true;
            } else {
                declarante = false;
            }
            String rolParticipante = catalogoServicioCliente
                    .consultarTipoParticipantePorCodigo(rol, declarante, tokenAutenticar).getNombre().toUpperCase();
            // cargar los datos de Nonbre, apellido y numero de documento
            part.setPrimerApellido(data.get(rol + "_primerApellido"));
            part.setPrimerNombre(data.get(rol + "_primerNombre"));
            part.setSegundoApellido(data.get(rol + "_segundoApellido"));
            part.setSegundoNombre(data.get(rol + "_segundoNombre"));
            log.info("*****************Fecha de nacimiento***************" + data.get(rol + "_dia") + " "
                    + data.get(rol + "_mes") + " " + data.get(rol + "_ano"));
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.get(rol + "_dia")));
            calendario.set(Calendar.MONTH, Integer.parseInt(data.get(rol + "_mes")));
            calendario.set(Calendar.YEAR, Integer.parseInt(data.get(rol + "_ano")));
            part.setFechaNacimiento(calendario.getTime());
            /*
             * Si el documento no es obligatorio entonces se debe modificar la operacion de insercion para que no pida
             * tipo.
             */
            part.setTipoDocumento("V");
            DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
            TipoDocumento tipoDocumento = new TipoDocumento();
            tipoDocumento.setCodigo("CM");
            documentoIdentidad.setTipoDocumento(tipoDocumento);
            ArrayList<DocumentoIdentidad> listaDocumentosID = new ArrayList<DocumentoIdentidad>();
            listaDocumentosID.add(documentoIdentidad);
            part.setParentesco(rolParticipante);
            part.setDocumentoIdentidad(listaDocumentosID);
            part.setDireccion(direcciones);
            List<Participante> listaParticipantes = (List<Participante>) sesion.getAttribute("listaParticipantes");
            listaParticipantes.add(part);
            cartaConsejoComunal.setParticipante(part);
            log.info("***************Datos del Declarante registrado con Carta del consejo comunal");
            log.info(part.getPrimerApellido() + " " + part.getPrimerNombre() + " "
                    + part.getDireccion().get(0).getUbicacion() + part.getRol() + part.getTipoDocumento());
            // Miembro del consejo comunal
        } else if ("MCC".equals(rol) || "MCCP".equals(rol)) {
            // Inicia el for para guardar los miembros del consejo comunal
            int nMCC = Integer.parseInt(data.get("nMCC"));
            for (int i = 0; i < nMCC; i++) {
                String mRol = i > 0 ? rol + i : rol;
                IntegranteConsejoComunal integrante = new IntegranteConsejoComunal();
                String pApellido = data.get(mRol + "_primerApellido");
                if (pApellido != null) {
                    integrante.setPrimerApellido(data.get(mRol + "_primerApellido"));
                    integrante.setPrimerNombre(data.get(mRol + "_primerNombre"));
                    integrante.setSegundoApellido(data.get(mRol + "_segundoApellido"));
                    integrante.setSegundoNombre(data.get(mRol + "_segundoNombre"));
                    integrante.setCargo(data.get(mRol + "_cargo"));
                    integrante.setTipoDocumento(data.get(mRol + "_nacionalidad"));
                    integrante.setNumeroDocumento("CED".equalsIgnoreCase(data.get(mRol + "_tipoDocumento")) ? data
                            .get(mRol + "_nacionalidad") + "-" + data.get(mRol + "_cedula") : data.get(mRol
                            + "_pasaporte"));
                    integrante.setTipoIntegrante("I");
                    log.info("*****************Datos del integrante del consejo comunal ");
                    log.info(integrante.getCargo() + " " + integrante.getPrimerApellido() + " "
                            + integrante.getPrimerNombre() + " " + integrante.getNumeroDocumento());
                    cartaConsejoComunal.getIntegrantes().add(integrante);
                }
            }
        } else {
            cartaConsejoComunal.setNombre(data.get(rol + "_nombre"));
            cartaConsejoComunal.setRif(data.get(rol + "_rif"));
            cartaConsejoComunal.setTelefono(data.get(rol + "_tlf"));
            log.info("*****************Fecha de la carta***************" + data.get(rol + "_dia") + " "
                    + data.get(rol + "_mes") + " " + data.get(rol + "_ano"));
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.get(rol + "_dia")));
            calendario.set(Calendar.MONTH, Integer.parseInt(data.get(rol + "_mes")));
            calendario.set(Calendar.YEAR, Integer.parseInt(data.get(rol + "_ano")));
            cartaConsejoComunal.setFechaCarta(calendario.getTime());
        }
        sesion.setAttribute("cartaConsejoComunal", cartaConsejoComunal);
        String proximoFormulario = generarFormularioCC(sesion, rol, data.get("nMCC"));
        if (proximoFormulario == null) {
            log.info("**********************************Proximo formulario es nulo ");
            log.info("**********************************Redireccionando a satisfactorio");
            return "redirect:/insertarParticipantes";
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(proximoFormulario);
        return null;
    }

    @RequestMapping(value = "/miembroCCadicional", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String miembroCCadicional(@RequestBody Map<String, String> data, HttpSession sesion,
            HttpServletResponse response) throws JSONException, IOException {
        String formularioString = ArchivoUtils.leerArchivo((String) sesion.getAttribute("rutaBase")
                + "formulario_miembros_cc.json", context);
        formularioString = formularioString.replace("ROL", "MCC" + data.get("nMCC"));
        formularioString = formularioString.replace("PARENTESCO", "");
        return formularioString;
    }

    public JSONObject generarPDFDeclaracion(DeclaracionJurada declaracion, String codigoDeclarante, int numTestigo)
            throws JRException, JSONException, IOException, Exception {
        String dj = properties.getProperty("plantilla.jasper.report.ciudadano.declaracionJurada");
        // String realPath = context.getRealPath(dj);
        String rutaLocal = properties.getProperty("sarc.file.tmp");
        List<Participante> participantes = declaracion.getParticipantes();
        String prefijo = PREFIJOS[Arrays.binarySearch(POSIBLES_DECLARANTES, codigoDeclarante)];
        Participante testigo = ProcesadorListas.encontrarParticipantePorCodigo(participantes, prefijo + numTestigo);
        Participante declarante = ProcesadorListas.encontrarParticipantePorCodigo(participantes, codigoDeclarante);
        int nSolicitud = (int) (10000 * Math.random());
        String realPathFdf = rutaLocal + RUTA_PDF + testigo.getRol() + nSolicitud + ".pdf";
        log.info("Path: " + realPathFdf);
        log.info("Ruta del contexto: " + context.getRealPath("/"));
        JasperReport jasperReport = JasperCompileManager.compileReport(dj);
        Map<String, Object> parameters = new HashMap<String, Object>();
        Date fecha = declaracion.getFechaDeclaracion();
        String urlFile = properties.getProperty("plantilla.jasper.report.imagen.logo");

        parameters.put("dia", GenerarActas.obtenerParametroDeFecha("DIA", fecha));
        parameters.put("mes", GenerarActas.obtenerParametroDeFecha("STRING_MES", fecha));
        parameters.put("ano", GenerarActas.obtenerParametroDeFecha("ANIO", fecha));
        parameters.put("urlFile", urlFile);

        cargarDatosDeclarante(parameters, declarante);
        cargarDatosParticipante(parameters, testigo);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint, realPathFdf);
        String server = properties.getProperty("sarc.web.servidor.atc");
        // String template="<embed width='900' height='450' src='"+ "C:/tmp/" + "Declaracion_jurada_" + testigo.getRol()
        // + nSolicitud + ".pdf' type='application/pdf'></embed>";
        String template = "<embed width='900' height='450' src='" + server + "/web-autenticarCiudadano/tmp/"
                + "Declaracion_jurada_" + testigo.getRol() + nSolicitud + ".pdf' type='application/pdf'></embed>";

        JSONObject formulario = new JSONObject();
        formulario.put("vista", template);
        formulario.put("rol", testigo.getRol());
        return formulario;
    }

    public void cargarDatosDeclarante(Map<String, Object> parameters, Participante padre) {
        String nombreDeclarado = padre.getPrimerNombre()
                + (padre.getSegundoNombre() == null ? " " : " " + padre.getSegundoNombre() + " ")
                + padre.getPrimerApellido()
                + (padre.getSegundoApellido() == null ? " " : " " + padre.getSegundoApellido());
        parameters.put("declarado", nombreDeclarado);
        Direccion mDireccion = ProcesadorListas.getDireccionPorTipo(padre, "RES");
        if (mDireccion == null) return;
        parameters.put("direcciondeclarado", mDireccion.getUbicacion());
        log.info("**************Direccion del declarante: " + mDireccion.getUbicacion());
        parameters.put("estadodeclarado", mDireccion.getEstado().getNombre());
        parameters.put("municipiodeclarado", mDireccion.getMunicipio().getNombre());
        parameters.put("parroquiadeclarado", mDireccion.getParroquia().getNombre());
        if (padre.getDocumentoIdentidad() != null) parameters.put("numdoc", padre.getDocumentoIdentidad().get(0)
                .getNumero());
    }

    public void cargarDatosParticipante(Map<String, Object> parameters, Participante participante) {
        String nombreDeclarante = participante.getPrimerNombre()
                + (participante.getSegundoNombre() == null ? " " : " " + participante.getSegundoNombre() + " ")
                + participante.getPrimerApellido()
                + (participante.getSegundoApellido() == null ? " " : " " + participante.getSegundoApellido());
        parameters.put("declarante", nombreDeclarante);
        parameters.put("ceduladec", participante.getDocumentoIdentidad().get(0).getNumero());
        Direccion mDireccion = ProcesadorListas.getDireccionPorTipo(participante, "RES");
        if (mDireccion == null) return;
        parameters.put("direcciondec", mDireccion.getUbicacion());
        log.info("**************Direccion del testigo: " + mDireccion.getUbicacion());
        parameters.put("estadodec", mDireccion.getEstado().getNombre());
        parameters.put("municipiodec", mDireccion.getMunicipio().getNombre());
        parameters.put("parroquiadec", mDireccion.getParroquia().getNombre());
    }

    public String generarFormularioCC(HttpSession sesion, String rolActual, String nMCC) throws IOException,
            JSONException {
        log.info("*************Iniciando el formulario de la carta comunal***");
        LinkedHashMap<String, String> listaFormularios = (LinkedHashMap<String, String>) sesion
                .getAttribute("listaFormulariosCC");
        String rol = null;
        String parentesco = null;
        boolean final_etapa = true;
        Iterator<Entry<String, String>> iterador = listaFormularios.entrySet().iterator();
        if (rolActual == null) {
            rol = iterador.next().getKey();
            parentesco = listaFormularios.get(rol);
            final_etapa = false;
        } else
            do {
                if (rolActual.equals(iterador.next().getKey()) && iterador.hasNext()) {
                    rol = iterador.next().getKey();
                    parentesco = listaFormularios.get(rol);
                    final_etapa = false;
                    break;
                }
            } while (iterador.hasNext());
        log.info("************************Rol del declarante de la carata comunal: " + rol + "\n");
        if (final_etapa) {
            log.info("***********final de etapa carta de consejo comunal ");
            return null;
        }
        String nombreForm = null;
        String titulo = null;
        if ("MAD".equals(rol) || "PAD".equals(rol)) {
            nombreForm = "formulario_padres_sin_id.json";
            titulo = "Carta del consejo comunal "
                    + ("MAD".equalsIgnoreCase(rol) ? "Madre declarante" : "Padre declarante");
            sesion.setAttribute("IDECLARANTE", "MAD".equalsIgnoreCase(rol) ? "Madre declarante" : "Padre declarante");
        }
        if ("MCC".equals(rol) || "MCCP".equals(rol)) {
            nombreForm = "formulario_miembros_cc.json";
            titulo = "Carta del consejo comunal " + sesion.getAttribute("IDECLARANTE"); // ("MCC".equalsIgnoreCase(rol)
                                                                                        // ? "Madre declarante" :
                                                                                        // "Padre declarante");
        }
        if ("DCC".equals(rol) || "DCCP".equals(rol)) {
            nombreForm = "formulario_datos_cc.json";
            titulo = "Carta del consejo comunal " + sesion.getAttribute("IDECLARANTE"); // ("DCC".equalsIgnoreCase(rol)
                                                                                        // ? "Madre declarante" :
                                                                                        // "Padre declarante");
        }

        String formularioString = ArchivoUtils.leerArchivo((String) sesion.getAttribute("rutaBase") + nombreForm,
                context);
        formularioString = formularioString.replace("ROL", rol);
        if ("MCC".equals(rol)) {
            formularioString = formularioString.replace("bkey", rol + nMCC);
        }
        formularioString = formularioString.replace("PARENTESCO", parentesco);
        JSONObject formlyJson = new JSONObject(formularioString);
        formlyJson.getJSONObject("modelo").put("final_etapa", final_etapa);
        formlyJson.getJSONObject("modelo").put("guardar", false);
        formlyJson.getJSONObject("modelo").put("aCC", true);
        formlyJson.getJSONObject("modelo").put("rol", rol);
        formlyJson.getJSONObject("modelo").put("enCC", true);
        formlyJson.getJSONObject("modelo").put("titulo", titulo);
        return formlyJson.toString();
    }

    public String generarFormularioDeDeclaracion(List<Participante> listaParticipantes, String rol, String tag,
            boolean inicial, HttpSession sesion) throws JSONException, IOException {
        String formularioString = ArchivoUtils.leerArchivo(
                (String) sesion.getAttribute("rutaBase")
                        + ("MAD".equals(rol) || "PAD".equals(rol) ? "formulario_padres_sin_id.json"
                                : "formulario_declaracion.json"), context);
        formularioString = formularioString.replace("ROL", rol);
        formularioString = formularioString.replace("PARENTESCO", tag);
        JSONObject formlyJson = new JSONObject(formularioString);
        // Inicializa el modelo asociado al formulario
        JSONObject modelojson = formlyJson.getJSONObject("modelo");
        Participante part = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, rol);
        if (part != null) {
            modelojson.put(rol, participante2Json(part, false, false, false, false, true, sesion));
        }
        modelojson.put("solicitarDatos", true);
        modelojson.put("a_declaracion", true);
        modelojson.put("inicial_etapa", inicial);
        modelojson.put("rol", rol);
        JSONObject formulario = new JSONObject();
        formulario.put("fields", formlyJson.get("fields"));
        formulario.put("modelo", modelojson);
        return formulario.toString();
    }

    public int encontrarIndiceParticipanteEnLista(List<Participante> participantes, String rol) {
        int indice = -1;
        for (int i = 0; i < participantes.size(); i++) {
            if (rol.equals(participantes.get(i).getRol())) indice = i;
        }
        return indice;
    }

    @RequestMapping(value = "/anularAdeclaracion", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String anularAdeclaracion(HttpSession sesion, HttpServletResponse response) throws JSONException,
            IOException {
        sesion.removeAttribute("declaracionJurada");
        return null;
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        ex.printStackTrace();
        log.info("Error: " + ex.getMessage() + " " + ex.getLocalizedMessage());
        return "redirect:/resources/js/json/error/error.json";
    }
}