package ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import orgsarcreglas.adopcion.Adopcion;
import orgsarcreglas.defuncion.Defuncion;
import orgsarcreglas.inhumacioncremacion.InhumacionCremacion;
import orgsarcreglas.nacimiento.Nacimiento;
import orgsarcreglas.recomposicion.Recomposicion;
import orgsarcreglas.reconocimiento.Reconocimiento;
import ve.gob.cne.sarc.autenticarCiudadano.utils.ArchivoUtils;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.clienteWeb.servicio.cliente.ClienteWebServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.TipoParticipante;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.participante.CartaConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.DeclaracionJurada;
import ve.gob.cne.sarc.comunes.participante.IntegranteConsejoComunal;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.plantilla.Acta;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.generales.util.Util;
//import ve.gob.cne.sarc.generales.formly.UtilFormly;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

@Controller
public class DroolsControlador {

    private String tipoDeclarante;

    @Autowired
    private ParticipanteServicioCliente clienteParticipante;

    @Autowired
    private CatalogoServicioCliente catalogoServicioCliente;

    @Autowired
    private SolicitudServicioCliente solicitudServicioCliente;

    @Autowired
    private AdministradorPropiedades properties;

    private static final String DOCUMENTO_CEDULA = "servicios.participante.tipo.documento.cedula";
    private static final String DOCUMENTO_PASAPORTE = "servicios.participante.tipo.documento.pasaporte";
    private static final String DOCUMENTO_NUI = "servicios.participante.tipo.documento.nui";
    private static final String DOCUMENTO_CCC = "servicios.participante.tipo.documento.carta_consejo_comunal";
    private static final String DOCUMENTO_DJ = "servicios.participante.tipo.documento.declaracion_jurada";
    private static final String DOCUMENTO_PUBLICO = "servicios.participante.tipo.documento.documento_publico";
    private static final String DECLARANTE = "servicios.solicitud.tipo.solicitante.declarante";
    private static final String ENTE = "servicios.solicitud.tipo.solicitante.ente";
    public static String tokenAutenticar;

    @Autowired
    ServletContext context;
    Logger log = Logger.getLogger(getClass().getName());

    @RequestMapping(value = "/entradaCiudadano", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String entrada(Model modelo, HttpServletRequest request, HttpSession sesion) throws Exception {
        System.out.println("*****************entradaCiudadano");
        String token;
        token = request.getHeader("Authorization");
        System.out.println("*****************entradaCiudadano token " + token);
        if (token.contains("Bearer") || token.contains("bearer")) {
            token = token.substring(7);
        }
        sesion.setAttribute("access_token", token);
        tokenAutenticar = (String) sesion.getAttribute("access_token");

        String html;
        html = properties.getProperty("endPointFormAutenticarEntradaCiudadano");
        System.out.println("*****************entradaCiudadano html " + html);
        return html;
    }

    @RequestMapping(value = "/iniciarTramite", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String iniciarAutenticar(@RequestBody Map<String, String> data,
            HttpSession session, HttpServletResponse response) throws Exception {
        log.info("********************Iniciando tr\u00e1amite de autenticaci\u00f3n desde generales************");

        try {
            session.removeAttribute("listaParticipantes");
            session.removeAttribute("declaracionJurada");
        } catch (Exception ex) {

        }
        Solicitud result = solicitudServicioCliente.consultarDetalleSolicitud(data.get("id"), tokenAutenticar);
        session.setAttribute("solicitud", result);
        log.info("Nombre del tramite: **********" + result.getTramite().getCodigo());
        Object objeto = null;
        List<Object> pojosTramite = new ArrayList<Object>();
        String codigoTramite = result.getTramite().getCodigo();
        session.setAttribute("codigoTramite", codigoTramite);
        /**
         * Determino que tramite es, instancio objeto correspondiente e
         * inicializo pantalla
         *
         */
        if (codigoTramite != null) {
            if ("RADOP".equalsIgnoreCase(codigoTramite)) {
                objeto = new Adopcion();
                ((Adopcion) objeto).setEscenario("GATE 0");
            } else if ("RNACI".equalsIgnoreCase(codigoTramite)) {
                objeto = new Nacimiento();
                ((Nacimiento) objeto).setEscenario("GATE 0");
                log.info("***********Tramite nacimiento********");
            } else if ("RDEFU".equalsIgnoreCase(codigoTramite)) {
                objeto = new Defuncion();
                ((Defuncion) objeto).setEscenario("GATE 0");
            } else if ("EPDIC".equalsIgnoreCase(codigoTramite)) {
                objeto = new InhumacionCremacion();
                ((InhumacionCremacion) objeto).setEscenario("GATE 0");
            } else if ("RRFIL".equalsIgnoreCase(codigoTramite)) {
                objeto = new Recomposicion();
                ((Recomposicion) objeto).setEscenario("GATE 0");
            } else if ("RRECO".equalsIgnoreCase(codigoTramite)) {
                objeto = new Reconocimiento();
                ((Reconocimiento) objeto).setEscenario("GATE 0");
            } else {
                JSONArray fields = new JSONArray();
                fields.put(crearCampoFormly(null, null, "Error: La solicitud seleccionada no puede ser procesada ", "/web-autenticarCiudadano/pages/error/pagina_error.html", null, null, null, false));
                JSONObject formulario = new JSONObject();
                JSONObject modelojson = new JSONObject();
                formulario.put("fields", fields);
                formulario.put("modelo", modelojson);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(formulario.toString());
                return null;
            }
        }
        pojosTramite.add(objeto);
        session.setAttribute("pojosTramite", pojosTramite);
        System.out.println("***************" + result.getSolicitante().getTipo());
        String rutaBase = "/resources/js/json/" + codigoTramite + "/" + ("DECLARANTE".equalsIgnoreCase(result.getSolicitante().getTipo()) ? "CIUDADANO/" : "ENTEPUB/");
        System.out.println("rutaaaaaaaaaaaa " + rutaBase);
        tipoDeclarante = result.getSolicitante().getTipo();
        System.out.println("tipoDeclarante " + tipoDeclarante);
        session.setAttribute("rutaBase", rutaBase);
        return "redirect:" + rutaBase + "GATE0.json";
    }

    @RequestMapping(value = "/consultarDrools", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String consultarDrools(@RequestBody Map<String, String> data, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String adopcion2 = null;
        adopcion2 = evaluarAdopcion(data, session);
//		******Evaluar si es otro declarante***
        if ("redirect:/resources/js/json/RDEFU/CIUDADANO/vista_selec_nombre_declarante.json".equals(adopcion2) || "redirect:/resources/js/json/EPDIC/CIUDADANO/vista_selec_nombre_declarante.json".equals(adopcion2) || "redirect:/resources/js/json/RDEFU/ENTEPUB/vista_selec_nombre_declarante_ente_publico.json".equals(adopcion2)) {
//		*******consumir el servicio para cargar el combo de otro declarante *************
            String tramite = (String) session.getAttribute("codigoTramite");
            Solicitud result = solicitudServicioCliente.consultarDetalleSolicitud(data.get("id"), tokenAutenticar);
            System.out.println("consultarDrools tipoDeclarante " + tipoDeclarante);

            if (tipoDeclarante.equalsIgnoreCase("DECLARANTE")) {
                tipoDeclarante = buscarValorProperties(DECLARANTE);
            } else {
                tipoDeclarante = buscarValorProperties(ENTE);
            }

            List<TipoParticipante> listaParticipantes = clienteParticipante.buscarTipoParticipantePorTramite(tramite, tipoDeclarante, tokenAutenticar);

            JSONArray opciones = new JSONArray();
            for (TipoParticipante lista : listaParticipantes) {
                JSONObject objCombo = new JSONObject();
                objCombo.put("name", lista.getNombre());
                objCombo.put("value", lista.getCodigo());
                opciones.put(objCombo);
            }
            String formularioString = null;
            if (tipoDeclarante.equalsIgnoreCase("1")) {
                formularioString = ArchivoUtils.leerArchivo((String) session.getAttribute("rutaBase") + "vista_selec_nombre_declarante.json", context);
            } else if (tipoDeclarante.equalsIgnoreCase("2")) {
                formularioString = ArchivoUtils.leerArchivo((String) session.getAttribute("rutaBase") + "vista_selec_nombre_declarante_ente_publico.json", context);
            }
            formularioString = formularioString.replace("OPCIONES", opciones.toString());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(formularioString);
            return null;
        } else {
            return adopcion2;
        }
    }

    @RequestMapping(value = "/consultarDrools1", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String consultarDrools1(@RequestBody Map<String, String> data, HttpServletRequest request, HttpSession session) throws Exception {
        return evaluarAdopcion(data, session);
    }

    @ExceptionHandler(Exception.class)
    public String handleAllException(Exception ex) {
        ex.printStackTrace();
        log.info("Error: " + ex.getMessage() + " " + ex.getLocalizedMessage());
        return "redirect:/resources/js/json/error/error.json";
    }

    public String evaluarAdopcion(Map<String, String> data, HttpSession sesion) throws Exception {
        String ruta = null;
        String rutaBase = (String) sesion.getAttribute("rutaBase");
        List<Object> pojosTramite = null;

        try {
            pojosTramite = (List<Object>) sesion.getAttribute("pojosTramite");
        } catch (ClassCastException e) {
            throw e;
        }
        // Identifica el tipo de documento de identificion
        ObjectMapper m = new ObjectMapper();
        // log.info("---->"+data.containsKey("")+""+data.containsKey("")+""+data.containsKey(""));
        log.info("---->dta: " + m.writeValueAsString(data) + "\n");
        // if (data.containsKey("TipoDocumento")) {
        // sesion.setAttribute("TipoDocumento", data.get("TipoDocumento"));
        if (data.containsKey("MadreDeclaranteID") && data.containsKey("PadreDeclaranteID")) {
            sesion.setAttribute("declaranteUnico", Boolean.parseBoolean(data.get("PadreDeclaranteID")) ? "MAD" : "PAD");
        } else {
            sesion.setAttribute("declaranteUnico", data.containsKey("MadreDeclaranteID") ? "MAD" : "PAD");
        }
        // }
        // String tipo = data.get("TipoDocumento");
        String mad = String.valueOf(data.get("MadreDeclaranteID"));
        String pad = String.valueOf(data.get("PadreDeclaranteID"));
        // String mad1 = String.valueOf(data.get("MadreDeclarante"));
        // String pad1 = String.valueOf(data.get("PadreDeclarante"));

        if ("true".equalsIgnoreCase(mad)) {
            // String dm = (data.get("TipoDocumentoMadre") == null) ? data.get("TipoDocumento") : data
            // .get("TipoDocumentoMadre");
            String dm = data.get("TipoDocumentoMadre");
            sesion.setAttribute("TipoDocumentoMadre", dm);
            // sesion.setAttribute("TipoDocumentoPadre", null);
        } else if ("true".equalsIgnoreCase(pad)) {
            // String dp = (data.get("TipoDocumentoPadre") == null) ? data.get("TipoDocumento") : data
            // .get("TipoDocumentoPadre");
            String dp = data.get("TipoDocumentoPadre");
            // sesion.setAttribute("TipoDocumentoMadre", null);
            sesion.setAttribute("TipoDocumentoPadre", dp);
        } else if ((("false".equalsIgnoreCase(mad) && "false".equalsIgnoreCase(pad)))) {

            sesion.setAttribute("TipoDocumentoMadre", data.get("TipoDocumentoMadre"));
            sesion.setAttribute("TipoDocumentoPadre", data.get("TipoDocumentoPadre"));
        }

        //recupera de sesion la instancia del objeto tramite a evaluar
        Object objeto = null;
        int nConsultas = Integer.parseInt(data.get("nConsultas"));

        for (int i = 0; i < nConsultas; i++) {
            int indiceConsulta = pojosTramite.size() - (nConsultas - i);
            objeto = pojosTramite.get(indiceConsulta);
            //carga en el objeto tramite los datos enviados desde la vista
            setPropiedad(objeto, data); //util.setPropiedad(objeto, data);
            //crea una instancia del cliente de reglas de negocio para el tramite especifico
            //Object kie=Class.forName("ve.gob.cne.sarc.reglas.servicio.cliente.ReglasServicioCliente"+nombreClase1).newInstance();
            Object kie = Class.forName("ve.gob.cne.sarc.reglas.servicio.cliente.ReglasServicioClienteDrools").newInstance();
            //realiza la consulta al motor de reglas de negocio
            Object adopcion2 = validarKie(kie, objeto);
            //guarda el objeto tramite evaluado
            pojosTramite.set(indiceConsulta, objeto);
            if ((indiceConsulta + 1) < pojosTramite.size()) {
                pojosTramite.set(indiceConsulta + 1, adopcion2);
            } else {
                pojosTramite.add(adopcion2);
            }
            if (adopcion2 != null) {
                if (getContinuarEvaluando(adopcion2)) {
                    log.info("***************Continuar evaluando: " + getContinuarEvaluando(adopcion2));
                    String vista = (String) getPropiedad(adopcion2, "getVista");
                    ruta = "redirect:" + rutaBase + vista + ".json";
                } else {
                    //retorna la lista de codigos de participantes a ser autenticados
                    ruta = "redirect:/json/" + (String) getPropiedad(adopcion2, "getLista");
                }
            } else //TODO MANEJAR EL ERROR GENERICO
            {
                log.info("El resultado de la consulta de validacion es nulo: ");
            }
        }
        sesion.setAttribute("pojosTramite", pojosTramite);
        log.info("ruta drools: " + ruta);
        return ruta;
    }

    public void setPropiedad(Object objeto, Map<String, String> data) throws Exception {

        Method[] metodos = objeto.getClass().getMethods();
        //Obtiene la lista de campos enviados desde la vista 
        Set<String> keys = data.keySet();
        Iterator<String> iterador = keys.iterator();
        log.info("La Data: " + data);

        do {
            String campo = iterador.next();
            for (int i = 0; i < metodos.length; i++) {

                if ("lista".equalsIgnoreCase(campo)) {
                    campo = campo.replaceAll("l", "L");
                }

                log.info("Campo: " + campo);
                log.info("Metodo: " + metodos[i].getName());
                if (("set" + campo).equals(metodos[i].getName())) {
                	

                    Pattern p = Pattern.compile("^(0|[1-9][0-9]{0,1})$");
                    if ("Lista".equalsIgnoreCase(campo)) {
                        campo = campo.replaceAll("L", "l");
                    }

                    String Str = "";
                    if (data.get(campo) != null) {
                        Str = data.get(campo);
                    }

                    log.info("----------------------------------------------------------------------");
                    log.info("valor a agregar  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + Str + " campo " + campo);

                    if (Str.equalsIgnoreCase("true") || Str.equalsIgnoreCase("false")) {
                        metodos[i].invoke(objeto, Boolean.parseBoolean(data.get(campo)));
                    } else if (p.matcher(Str).find()) {
                        metodos[i].invoke(objeto, Integer.valueOf(data.get(campo)));
                    } else {
                        metodos[i].invoke(objeto, data.get(campo));
                    }
                }
            }
        } while (iterador.hasNext());
    }

    /**
     * <p>
     * Ubica el metodo "validar..." del objeto tramite, por ejemplo: Adopcion
     * adopcion=kie.validarAdopcion(adopci&oacute;n)</p>;
     *
     * @param kie: Objeto de informaci&oacute;n del tramite en Drools
     * @param nombreClase: Nombre de la clase que hace referencia al tramite
     * @param objeto: Objeto del tramite a procesar
     * @return: Objeto tramite cuyo nombre de clase se especifica en
     * nombreClase.
     * @throws Exception
     */
    public Object getPropiedad2(Object kie, String nombreClase, Object objeto) throws Exception {
        Object objeto2 = null;
        Method[] metodos2 = kie.getClass().getMethods();
        for (Method metodo : metodos2) {
            if (("validar" + nombreClase).equals(metodo.getName())) {
                try {
                    objeto2 = metodo.invoke(kie, objeto);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return objeto2;
    }

    public Object validarKie(Object kie, Object objeto) throws Exception {
        Object objeto2 = null;
        //obtiene el nombre de la clase del objeto tramite, por ejemeplo Nacimiento o Adopcion
        String nombreClase = objeto.getClass().getName().replace(objeto.getClass().getPackage().getName() + ".", "");
        log.info("**************Clase del Objeto a validar: " + nombreClase);
        log.info("**************Clase del Kie: " + kie.getClass().getName());
        Method[] metodosObjeto = objeto.getClass().getMethods();
        for (Method metodo : metodosObjeto) {
            if (metodo.getName().equals("getEscenario")) {
                String escenario = (String) metodo.invoke(objeto, null);
                log.info("**************Escenario: " + escenario);
            }
        }
        Method[] metodos2 = kie.getClass().getMethods();
        for (Method metodo : metodos2) {
            log.info("Nombre del metodo: " + metodo.getName());
            if (("validar" + nombreClase).equals(metodo.getName())) {
                log.info("************evaluando defuncion ******************");
                try {
                    objeto2 = metodo.invoke(kie, objeto);
                } catch (Exception e) {
                    log.info("************ERROR: " + e.getMessage() + "*************");
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return objeto2;
    }

    public boolean getContinuarEvaluando(Object adopcion2) throws Exception {
        String continuars = null;
        Method[] metodos3 = adopcion2.getClass().getMethods();
        for (Method metodo : metodos3) {
            if (("getContinuarEvaluando").equals(metodo.getName())) {
                Object[] args = null;
                continuars = (String) metodo.invoke(adopcion2, args);
            }
        }
        return Boolean.parseBoolean(continuars);
    }

    public Object getPropiedad(Object adopcion2, String nombreMetodoGeter) throws Exception {
        Object atributo = null;
        Method[] metodos3 = adopcion2.getClass().getMethods();
        for (Method metodo : metodos3) {
            if ((nombreMetodoGeter).equals(metodo.getName())) {
                Object[] args = null;
                atributo = metodo.invoke(adopcion2, args);
            }
        }
        return atributo;
    }

    @RequestMapping(value = "/consultarDroolsAtras", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String consultarDroolsAtras(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("regresando drools");
        List<Object> pojosTramite = pojosTramite = (List<Object>) session.getAttribute("pojosTramite");
        pojosTramite.remove(pojosTramite.size() - 1);
        return null;
    }

    /**
     *
     * @param lista: lista de participantes separados por comas
     * @param request: objeto reques de la solicitud Http
     * @return Formulario Formly en formato json con la lista de participantes
     * seleccionados
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     * @throws JSONException
     */
    @RequestMapping(value = "json/{lista}", method = RequestMethod.GET)
    public @ResponseBody
    String retornarLista(@PathVariable String lista, HttpServletRequest request, HttpSession sesion) throws JSONException {
        //Elimina las comas y gurda la lista de participantes en un array
        String[] listaCodigos = lista.split(",");
        String ruta = (String) sesion.getAttribute("rutaBase");

        LinkedHashMap<String, String> listaCodigosNombresParticipantes = new LinkedHashMap<String, String>();
        LinkedHashMap<String, String> listaCodigosHijos = new LinkedHashMap<String, String>();
        Map<String, String> listaFormularios = getListaFormularios(listaCodigos, sesion);
        sesion.setAttribute("listaFormularios", listaFormularios);
        sesion.setAttribute("indiceFormulario", 0);
        // contiene los datos del modelo enviados a la vista
        JSONObject modelojson = new JSONObject();
        // contiene los campos del formulario
        JSONArray fields = null;
        String key;
        int h = 1;
        boolean declarante = false;
        int declaranteContador = 0;
        if (!"0".equals(lista)) {
            fields = new JSONArray();
            for (String codigo : listaCodigos) {
                declaranteContador++;
                if (declaranteContador == 1 && (!ruta.equals("/resources/js/json/RRFIL/ENTEPUB/"))) {
                    if (!"TA1".equals(listaCodigos[0]) && !"TA2".equals(listaCodigos[0])
                            && !"TDM1".equals(listaCodigos[0]) && !"TDM2".equals(listaCodigos[0])
                            && !"TDP1".equals(listaCodigos[0]) && !"TDP2".equals(listaCodigos[0])) declarante = true;
                } else {
                    declarante = false;
                }
                listaCodigosNombresParticipantes.put(codigo, catalogoServicioCliente
                        .consultarTipoParticipantePorCodigo(codigo, declarante, tokenAutenticar).getNombre());
            }
            Iterator<String> iterador = listaCodigosNombresParticipantes.keySet().iterator();
            List<Object> pojosTramite = (List<Object>) sesion.getAttribute("pojosTramite");
            Object pojo = pojosTramite.get(pojosTramite.size() - 1);

            Integer numHijos = null;
            if (((String) sesion.getAttribute("codigoTramite")).equals("RDEFU")) numHijos = ((Defuncion) pojo)
                    .getCantidadHijoConID();
            if (((String) sesion.getAttribute("codigoTramite")).equals("RRECO")) numHijos = ((Reconocimiento) pojo)
                    .getCantidadHijoConId();
            while (iterador.hasNext()) {
                key = iterador.next();
                if (pojo.getClass().getName().contains("Defuncion")
                        || pojo.getClass().getName().contains("Reconocimiento")) {
                    if ("HIJD".equals(key) && numHijos > 0) {
                        numHijos = numHijos - 1;
                    }
                    if ("HIJ".equals(key)) {
                        for (int i = 1; i <= numHijos; i++) {
                            if (((String) sesion.getAttribute("codigoTramite")).equals("RDEFU")) {
                                listaCodigosHijos.put("HIJ" + i, "HIJO(A) " + i + " DEL FALLECIDO(A)");
                            }
                            if (((String) sesion.getAttribute("codigoTramite")).equals("RRECO")) {
                                listaCodigosHijos.put("HIJ" + i, "HIJO(A) " + i + " DEL RECONOCIDO(A)");
                            }
                        }
                    }
                }
                if (pojo.getClass().getName().contains("Recomposicion")) {
                    if ("HIJ".equals(key)) {
                        fields.put(crearCampoFormly(key, "participanteSeleccionado",
                                listaCodigosNombresParticipantes.get(key) + " PARTICIPANTE", null, null));
                    }
                }
                if (!("HIJ".equals(key)) && !("TA1".equals(key)) && !("TA2".equals(key))) fields.put(crearCampoFormly(
                        key, "participanteSeleccionado", listaCodigosNombresParticipantes.get(key), null, null));
            }

        } else {
            // TODO MOVER EL MENSAJE A UN PROPERTI
            log.info("****************No hay participantes***********");
            modelojson
                    .put("errorDrools",
                            "No se autenticara ningun participante. Se procedera a finalizar el proceso para realizar el Registro de la Defuncion. Desea Continuar?");
        }

        for (Iterator<String> iterator = listaCodigosHijos.keySet().iterator(); iterator.hasNext();) {
            String type = iterator.next();
            log.info("iteracion hijos " + type);
            listaCodigosNombresParticipantes.put("HIJ" + h, listaCodigosHijos.get("HIJ" + h));
            fields.put(crearCampoFormly("HIJ" + h, "participanteSeleccionado", listaCodigosHijos.get("HIJ" + h), null,
                    null));
            h++;
        }

        if (!ruta.equalsIgnoreCase("/resources/js/json/RDEFU/ENTEPUB/")
                && !ruta.equalsIgnoreCase("/resources/js/json/EPDIC/CIUDADANO/")) {
            String test1 = listaCodigosNombresParticipantes.get("TA1");
            String test2 = listaCodigosNombresParticipantes.get("TA2");
            listaCodigosNombresParticipantes.remove("TA1");
            listaCodigosNombresParticipantes.remove("TA2");
            listaCodigosNombresParticipantes.put("TA1", test1);
            fields.put(crearCampoFormly("TA1", "participanteSeleccionado", listaCodigosNombresParticipantes.get("TA1"),
                    null, null));
            listaCodigosNombresParticipantes.put("TA2", test2);
            fields.put(crearCampoFormly("TA2", "participanteSeleccionado", listaCodigosNombresParticipantes.get("TA2"),
                    null, null));
        }
        if (!(ruta.equalsIgnoreCase("/resources/js/json/RRFIL/CIUDADANO/") || ruta
                .equalsIgnoreCase("/resources/js/json/RRFIL/ENTEPUB/"))) {
            listaCodigosNombresParticipantes.remove("HIJ");
        }
        /*
         * Indica que inicia la etapa de autenticacion y deja de consultar a Drools con cada llamada Guarda los datos en
         * una variable de sesion
         */
        sesion.setAttribute("listaCodigosNombresParticipantes", listaCodigosNombresParticipantes);
        modelojson.put("autenticar", true);
        modelojson.put("final_etapa", true);
        modelojson.put("final_etapa_drools", true);
        modelojson.put("validador", true);
        modelojson.put("activo", "2");
        modelojson.put("titulo", "Listado de participantes");
        String sjson = "{\"fields\":" + (fields == null ? "[{\"error\":\"error\"}]" : fields.toString())
                + ",\"modelo\":" + modelojson.toString() + "}";
        return sjson;
    }

    /**
     * DETERMINA EL ORDEN DE PROCESAMIENO DE LA DECLARACION JURADA
     *
     * @param lista
     * @return
     */
    public Map<String, String> getListaFormularios(String[] lista, HttpSession sesion) {
        String[] listaDeclaraciones = null;

		boolean djPadre=false,djMadre=false, declarante =false;
		for (int i = 0; i < lista.length; i++) {
			if(lista[i].equals("TDM2"))
				djMadre=true;
			if(lista[i].equals("TDP2"))
				djPadre=true;
		}
		if(djMadre)
			listaDeclaraciones=new String[]{"MAD","TDM1","TDM2"};
		if(djPadre)
			listaDeclaraciones=new String[]{"PAD","TDP1","TDP2"};
		if(djMadre && djPadre)
			listaDeclaraciones=new String[]{"MAD","TDM1","TDM2","PAD","TDP1","TDP2"};
		Map<String,String> listaFormularios=null;
		if(listaDeclaraciones!=null){
			listaFormularios = new LinkedHashMap<String, String>();
			for (int i = 0; i < listaDeclaraciones.length; i++){
				 int j=1;
				 if(j==1){
					declarante=true;
					 
				 }else
					declarante=false; 				 
				 	listaFormularios.put(listaDeclaraciones[i], catalogoServicioCliente.consultarTipoParticipantePorCodigo(listaDeclaraciones[i], declarante, tokenAutenticar).getNombre().toUpperCase());
			}
		}
		return listaFormularios;
	}
	public String retornarListaSeleccionados(HttpSession sesion) throws JsonGenerationException, JsonMappingException, IOException, JSONException{
		String[] listaCodigos=(String[]) sesion.getAttribute("listaCodigoParticipante");
		String[] listaNombres=(String[]) sesion.getAttribute("listaNombresParticipantes");
		JSONArray fields=new JSONArray();
		for (int i = 0; i < listaNombres.length; i++) {
			fields.put(crearCampoFormly(listaCodigos[i], "participanteSeleccionado", listaNombres[i], null, null));					
		}
		JSONObject modelojson=new JSONObject();
		modelojson.put("autenticar", true);
		modelojson.put("validador", true);
		modelojson.put("activo", "2");
		modelojson.put("titulo", "Listado de participantes seleccionados");
		JSONObject formulario=new JSONObject();
		formulario.put("fields",fields);
		formulario.put("modelo",modelojson);
		return formulario.toString();
	}

    /**
     * <
     * p>
     * Crea los campos del formulario formly</p>
     *
     * @param key
     * @param type
     * @param label
     * @param names Nombres de los elementos del radio
     * @param values Valores de los elementos del radio
     * @return
     * @throws JSONException
     */
    public JSONObject crearCampoFormly(String key, String type, String label, String[] names, Object[] values) throws JSONException {
        JSONObject fieldJson1 = new JSONObject();
        fieldJson1.put("key", key);
        fieldJson1.put("type", type);
        JSONObject templateOptions = new JSONObject();
        templateOptions.put("label", label);
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
        JSONObject data = new JSONObject();
        int color = (int) Math.floor(0xFFFFFF * Math.random());
        String colors = String.format("#%06X", color & 0xFFFFF);
        data.put("color", colors);
        fieldJson1.put("data", data);
        fieldJson1.put("templateOptions", templateOptions);
        return fieldJson1;
    }

    /**
     * <p>
     * Convierte el objeto participanteen un json</p>
     *
     * @param participante
     * @return
     * @throws JSONException
     */
    public JSONObject participante2Json(Participante participante) throws JSONException {
        JSONObject participanteJson = new JSONObject();
        participanteJson.put("primerNombre", participante.getPrimerNombre());
        participanteJson.put("primerApellido", participante.getPrimerApellido());
        participanteJson.put("rol", participante.getRol());
        participanteJson.put("documentoIdentidad", participante.getDocumentoIdentidad().get(0).getNumero());
        participanteJson.put("autenticado", participante.isAutenticado());
        return participanteJson;
    }

    /**
     *
     * @param data
     * @param rol
     * @return
     */
    String[] getNumeroDocumento(Map<String, String> data, String rol) {
        String codigoDocumento = data.get(rol + "_tipoDocumento");
        String nroDoc = null;
        if (codigoDocumento.equalsIgnoreCase(buscarValorProperties(DOCUMENTO_CEDULA))) {
            log.info("docccccccccccc" + buscarValorProperties(DOCUMENTO_CEDULA));
            nroDoc = data.get(rol + "_nacionalidad") + "-" + data.get(rol + "_cedula");
        } else if (codigoDocumento.equalsIgnoreCase(buscarValorProperties(DOCUMENTO_PASAPORTE))) {
            nroDoc = data.get(rol + "_pasaporte");
        }
        return new String[]{codigoDocumento, nroDoc};
    }

    public boolean identificaParticipanteEnLista(String rol, HttpSession sesion) {
        boolean estaPresente = false;
        String[] listaRoles = (String[]) sesion.getAttribute("listaCodigos");
        if (Arrays.binarySearch(listaRoles, rol) > 0) {
            estaPresente = true;
        }
        return estaPresente;
    }

    public boolean esParticipanteRepetido(String nroDocumento) {
        boolean esRepetido = false;
        return esRepetido;
    }

    @RequestMapping(value = "/insertarParticipantes", method = RequestMethod.GET) //cambio maria 10/1/2017
    @ResponseStatus(value = HttpStatus.CREATED)
    public String insertarParticipante(HttpSession sesion, HttpServletResponse response) throws Exception {
        try {
            log.info("-------------Iniciando registro de participantes--------------");
            Solicitud solicitud = (Solicitud) sesion.getAttribute("solicitud");
            log.info("*************************************Solicitud: " + solicitud.getNumeroSolicitud());
            log.info("*************************************Funcionario: " + solicitud.getFuncionario().getPrimerNombre() + " " + solicitud.getFuncionario().getPrimerApellido());

            List<Acta> actas = new ArrayList<Acta>();//solicitud.getActas();
            Acta acta = new Acta();
            String ccc = "cartaConsejoComunal";
            String ddjj = "declaracionJurada";
            List<Participante> participantes = (List<Participante>) sesion.getAttribute("listaParticipantes");
            validarRolHijo(participantes);
            acta.setParticipantes(participantes);
            log.info("Listado de participantes a Insertar: ");
            for (Participante participante : participantes) {
                String rol = participante.getRol();
                log.info("Rol del participante a insertar: " + rol);
                String parentesco = participante.getParentesco();
                String primerNombre = participante.getPrimerNombre();
                String segundoNombre = participante.getSegundoNombre();
                String PrimerApellido = participante.getPrimerApellido();
                String segundoApellido = participante.getSegundoApellido();
                String tipoDocumento = participante.getTipoDocumento();
                log.info("Tipo de documento de identidad: " + tipoDocumento);
                String numeroDocumetno = participante.getDocumentoIdentidad().get(0).getNumero();
                log.info("Numero de documento de identidad: " + numeroDocumetno);
                String fechaNacimiento = participante.getFechaNacimiento().toString();
                log.info(rol + " " + parentesco + " " + primerNombre + " " + segundoNombre + " " + PrimerApellido + " " + segundoApellido + " " + tipoDocumento + " " + numeroDocumetno + " " + fechaNacimiento);
            }
            actas.add(acta);
            solicitud.setActas(actas);
            log.info("**************Datos de la solicitud: " + solicitud);

            for (int i = 0; i < solicitud.getActas().get(0).getParticipantes().size(); i++) {
                // solicitud.getActas().get(0).getParticipantes().get(i).getDocumentoIdentidad().get(0).getTipoDocumento().setCodigo(buscarValorProperties(DOCUMENTO_CEDULA));
                log.info("identidad: "
                        + solicitud.getActas().get(0).getParticipantes().get(i).getDocumentoIdentidad().get(0)
                                .getTipoDocumento().getCodigo());
                String tipo = solicitud.getActas().get(0).getParticipantes().get(i).getDocumentoIdentidad().get(0)
                        .getTipoDocumento().getCodigo();
                log.info("---->tipo: " + tipo + " posi" + i + "\n");
                String cod = null;
                switch (tipo) {
                    case "CED":
                        cod = buscarValorProperties(DOCUMENTO_CEDULA);
                        break;
                    case "PAS":
                        cod = buscarValorProperties(DOCUMENTO_PASAPORTE);
                        break;
                    case "NUI":
                        cod = buscarValorProperties(DOCUMENTO_NUI);
                        break;
                    case "DP":
                        cod = buscarValorProperties(DOCUMENTO_PUBLICO);
                        break;
                    case "CM":
                        cod = buscarValorProperties(DOCUMENTO_CCC);
                        break;
                    case "CCC":
                        cod = buscarValorProperties(DOCUMENTO_CCC);
                        break;
                    case "DJ":
                        cod = buscarValorProperties(DOCUMENTO_DJ);
                        break;
                    default:
                        cod = tipo;
                        break;
                    // throw new Exception("error: "+cod);

                }
                solicitud.getActas().get(0).getParticipantes().get(i).getDocumentoIdentidad().get(0).getTipoDocumento()
                        .setCodigo(cod);
            }
            clienteParticipante.insertarParticipantes(solicitud, tokenAutenticar);
            try {
                CartaConsejoComunal cartaConsejoComunal = (CartaConsejoComunal) sesion
                        .getAttribute("cartaConsejoComunal");
                // log.info("La famosa Carta del Cosejo Comunal: "+cartaConsejoComunal.toString());
                if (cartaConsejoComunal != null) {
                    cartaConsejoComunal.setNumeroSolicitud(((Solicitud) sesion.getAttribute("solicitud"))
                            .getNumeroSolicitud());
                    // log.info("******************Numero de la solicitud: " +
                    // cartaConsejoComunal.getNumeroSolicitud());
                    // log.info("******************Fecha de carta: " + cartaConsejoComunal.getFechaCarta());
                    // log.info("***************Rif: " + cartaConsejoComunal.getRif());
                    // log.info("**************Datos del  Participante: " +
                    // cartaConsejoComunal.getParticipante().getRol()
                    // + " " + cartaConsejoComunal.getParticipante().getPrimerApellido());
                    // log.info("***************token: " + tokenAutenticar);
                    /*
                     * List<IntegranteConsejoComunal> integrantes = cartaConsejoComunal.getIntegrantes();
                     * log.info("Integrantes: " + integrantes); for (IntegranteConsejoComunal integrante : integrantes)
                     * { log.info(integrante.getPrimerNombre() + " " + integrante.getPrimerApellido() + " " +
                     * integrante.getNumeroDocumento() + " " + integrante.getCargo()); }
                     */
                    clienteParticipante.registrarConsejoComunal(cartaConsejoComunal, tokenAutenticar);
                    log.info("***************Se ha insertado la carta del consejo comunal");

                }
            } catch (Exception er) {
            }

            try {
                DeclaracionJurada declaracionJurada = (DeclaracionJurada) sesion.getAttribute("declaracionJurada");
                if (declaracionJurada != null) {
                    log.info("*****************Datos de declaracion   ");
                    List<Participante> listaP = declaracionJurada.getParticipantes();
                    for (Participante participante : listaP) {
                        String rol = participante.getRol();
                        String parentesco = participante.getParentesco();
                        String primerNombre = participante.getPrimerNombre();
                        String segundoNombre = participante.getSegundoNombre();
                        String PrimerApellido = participante.getPrimerApellido();
                        String segundoApellido = participante.getSegundoApellido();
                        String tipoDocumento = participante.getTipoDocumento();
                        String numeroDocumetno = participante.getDocumentoIdentidad().get(0).getNumero();
                        String fechaNacimiento = participante.getFechaNacimiento().toString();
                        log.info(rol + " " + parentesco + " " + primerNombre + " " + segundoNombre + " "
                                + PrimerApellido + " " + segundoApellido + " " + tipoDocumento + " " + numeroDocumetno
                                + " " + fechaNacimiento);
                        List<Direccion> direcciones = participante.getDireccion();
                        log.info("******************datos de la direccion: ");
                        for (Direccion direccion : direcciones) {
                            log.info("************" + direccion.getUbicacion());
                            log.info("************" + direccion.getPais().getCodigo());
                            log.info("************" + direccion.getEstado().getCodigo());
                            log.info("************" + direccion.getMunicipio().getCodigo());
                            log.info("************" + direccion.getParroquia().getCodigo());
                        }
                    }

                    clienteParticipante.registrarDeclaracionJurada(declaracionJurada, tokenAutenticar);
                    log.info("***************Se ha registrado la declaracion jurada");
                }
            } catch (Exception es) {
            }

            return "redirect:/resources/js/json/solicitud/guardado.json";

        } catch (Exception e) {
            JSONArray fields = new JSONArray();
            fields.put(crearCampoFormly(null, null, "Error al guardar los datos autenticados, por favor intentelo nuevamente", "/web-autenticarCiudadano/pages/error/pagina_error.html", null, null, null, false));
            JSONObject formulario = new JSONObject();
            JSONObject modelojson = new JSONObject();
            formulario.put("fields", fields);
            formulario.put("modelo", modelojson);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(formulario.toString());
            log.error("xxxxxxxxxxxxxxxError: " + e.getMessage());
            e.printStackTrace();
            return null;

        }
    }

    /**
     * <p>
     * Crea los campos del formulario formly</p>
     *
     * @param key
     * @param type
     * @param label
     * @param names Nombres de los elementos del radio
     * @param values Valores de los elementos del radio
     * @return
     * @throws JSONException
     */
    public JSONObject crearCampoFormly(String key, String type, String label, String template, String[] names, Object[] values, String hideExpression, boolean required) throws JSONException {
        JSONObject fieldJson1 = new JSONObject();
        fieldJson1.put("key", key);
        fieldJson1.put("type", type);
        fieldJson1.put("templateUrl", template);
        if (hideExpression != null) {
            fieldJson1.put("hideExpression", hideExpression);
        }
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

    private List<Participante> validarRolHijo(List<Participante> participantes) {

        for (Participante p : participantes) {

            if (p.getRol().length() >= 4 && p.getRol().substring(0, 3).equals("HIJ") && Character.isDigit(p.getRol().charAt(3))) {
                p.setRol("HIJ");
            }
        }

        return participantes;
    }

    @RequestMapping(value = "/cambiarEstado", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public String actualizarEstatuSolicitud(@RequestBody Map<String, String> data, HttpSession session, HttpServletResponse response) throws Exception {

        session.getAttribute("solicitud");

        Solicitud solicitudSesion = (Solicitud) session.getAttribute("solicitud");
        log.info("session: " + solicitudSesion.getNumeroSolicitud());
        if (("PENDIENTE").equals(data.get("mEstado"))) {
            solicitudSesion.setEstadoSolicitud("AB");
            solicitudSesion.setMotivoCambioEstado("ABIERTA");
        }
        solicitudServicioCliente.actualizarEstadoSolicitud(solicitudSesion, tokenAutenticar);

        return "redirect:/resources/js/json/solicitud/guardado.json";
    }

    @RequestMapping(value = "/cambiarStatus/{parametro}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void actualizarEstatuSolicitud(@PathVariable("parametro") String parametro, HttpSession session, HttpServletResponse response) throws Exception {

        session.getAttribute("solicitud");

        Solicitud solicitudSesion = (Solicitud) session.getAttribute("solicitud");
        log.info("session: " + solicitudSesion.getNumeroSolicitud());
        if (("PENDIENTE").equals(parametro.toUpperCase())) {
            solicitudSesion.setEstadoSolicitud(Constantes.ABIERTA);
            solicitudSesion.setMotivoCambioEstado("ABIERTA");
        }
        solicitudServicioCliente.actualizarEstadoSolicitud(solicitudSesion, tokenAutenticar);
    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo
     * properties del servicio funcionario
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        log.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            log.info("Error leyendo properties: " + ex.getMessage());
        }
        log.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }

}
