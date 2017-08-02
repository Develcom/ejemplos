package EDPIC.EDPIC.web.permisoInhumacionCremacion.controllers;

import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonGenerator;

import javax.management.modelmbean.ModelMBeanOperationInfo;
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

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.exolab.castor.types.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.catalogo.Estado;
import ve.gob.cne.sarc.comunes.catalogo.Municipio;
import ve.gob.cne.sarc.comunes.catalogo.Pais;
import ve.gob.cne.sarc.comunes.catalogo.Parroquia;
import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.comunes.solicitante.Solicitante;
import ve.gob.cne.sarc.comunes.solicitud.Solicitud;
import ve.gob.cne.sarc.defuncion.servicio.cliente.DefuncionServicioCliente;
import ve.gob.cne.sarc.funcionario.servicio.cliente.FuncionarioServicioCliente;
import ve.gob.cne.sarc.generales.catalogo.Catalogo;
import ve.gob.cne.sarc.generales.defuncion.Defuncion;
import ve.gob.cne.sarc.generales.excepciones.GeneralException;
import ve.gob.cne.sarc.generales.funcionario.FuncionarioServicio;
import ve.gob.cne.sarc.generales.reporte.GenerarActas;
import ve.gob.cne.sarc.generales.solicitud.SolicitudServicio;
import ve.gob.cne.sarc.generales.util.Util;
import ve.gob.cne.sarc.generales.util.formatDate;
import ve.gob.cne.sarc.participante.servicio.cliente.ParticipanteServicioCliente;
import ve.gob.cne.sarc.seguridad.servicio.cliente.SeguridadServicioCliente;
import ve.gob.cne.sarc.solicitud.servicio.cliente.Constantes;


@RestController public class PermisoInhumacionCremacion{
    @Autowired
    ServletContext context;

    Logger log = Logger.getLogger(getClass().getName());
    private final String rutaHtml="/resources/pages/templates/";
    private final String RUTA_PLANTILLA = "resources/pages/plantillas/";
    private final String PLANTILLA_INHUMACION = "Permiso_obito_fetal_inhumacion.jrxml";
    private final String PLANTILLA_CREMACION = "Permiso_obito_fetal_cremacion.jrxml";
    private final String PLANTILLA_INHUMACION_CREMACION = "Permiso_obito_fetal_inhumacion_cremacion.jrxml";
    
    private final String PLANTILLA_INHUMACION_CREMACION_DECLARANTE = "Permiso_obito_fetal_declarantes_inhumacion_cremacion.jrxml";
    private final String PLANTILLA_INHUMACION_DECLARANTE = "Permiso_obito_fetal_declarantes_inhumacion.jrxml";
    private final String PLANTILLA_CREMACION_DECLARANTE = "Permiso_obito_fetal_declarantes_cremacion.jrxml";
    
    private final String RUTA_VISTA = "/resources/pages/templates/imprimir_documento.html";
    private final String RUTA_PV = "/resources/pages/templates/vista_permiso_inhumacion_cremacion.html";

    private final String RUTA_TMP_INHUMACION = "C:/tmp/"+PLANTILLA_INHUMACION;
    private final String RUTA_TMP_CREMACION = "C:/tmp/"+PLANTILLA_CREMACION;
    private final String RUTA_TMP_INHUMACION_CREMACION = "C:/tmp/"+PLANTILLA_INHUMACION_CREMACION;
    //private final String  rutaImp="C:/tmp/";
    private  final String rutaImp="/home/jboss/tmp/";
    private final String extfile=".pdf";
	private final String rutaLogo="resources/img/";

    SolicitudServicio solicitud = new SolicitudServicio();

    SeguridadServicioCliente seguridadCliente = new SeguridadServicioCliente();
    Solicitud sol = new Solicitud();
    ParticipanteServicioCliente participante = new ParticipanteServicioCliente();

    FuncionarioServicio funcionario = new FuncionarioServicio();
    CatalogoServicioCliente catalogoServicio = new CatalogoServicioCliente();
    FuncionarioServicioCliente funcionarioServicioCliente = new FuncionarioServicioCliente();



    /**
     *Funcion que devuelve una vista y modelo de cada inicio del proceso
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     */
    @RequestMapping(value = "/iniciarTramiteEPDIC", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String iniciar(@RequestBody String data, HttpServletRequest request,
                                        HttpSession session, HttpServletResponse response) throws Exception {

    	JSONObject modelo = new JSONObject(data);
        String token=request.getHeader("Authorization");
        log.info("**********************Token:  "+token);
        String htmlInicio=null;

        Solicitud sol = solicitud.consultarDetalleSolicitud(modelo.getString("id"));


        htmlInicio=buscarHtml(sol.getEstadoSolicitud());

        String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+htmlInicio+".html"));

        JSONObject respuesta = new JSONObject();
        if("PV".equals(modelo.getString("statu"))){
           List<Participante> part = new ArrayList<>();
           part =participante.consultarParticPorSolicitud(modelo.getString("id"), "D");
           JSONObject modeloReporte = new JSONObject();
           if(part.size() == 2){
              modeloReporte  = generarReporteDeclarante(modelo.getString("id"),modelo.getString("statu"), token, part);
           }else{
              modeloReporte = generarReporte(modelo.getString("id"),modelo.getString("statu"), token, part);
 
           }

            
            modelo.put("titulo", modeloReporte.get("titulo"));
            respuesta.put("modelo", modelo);
            respuesta.put("vista", modeloReporte.get("vista"));

        }else if("NC".equals(modelo.getString("statu"))){

            PermisoInhCre datosConsulta = this.consultarPermiso(modelo.getString("id"));
            JSONObject objeto = new JSONObject();
            objeto =(modelo.getJSONObject("PermisoInhCre"));
            objeto.put("pais", datosConsulta.getPais());
            objeto.put("estado", datosConsulta.getEstado());
            objeto.put("municipio", datosConsulta.getMunicipio());
            objeto.put("parroquia", datosConsulta.getParroquia());
            objeto.put("nombreCementerio", datosConsulta.getNombreCementerio());
            objeto.put("fechaDefuncion", datosConsulta.getFechaDefuncion());
            objeto.put("numeroCertificadoDef", datosConsulta.getNumeroCertificadoDef());
            objeto.put("direccionDefuncion", datosConsulta.getDireccionDefuncion());
            objeto.put("primerNombreAutoriza", datosConsulta.getPrimerNombreAutoriza());
            objeto.put("primerApellidoAutoriza", datosConsulta.getPrimerApellidoAutoriza());
            objeto.put("segundoNombreAutoriza", datosConsulta.getSegundoNombreAutoriza());
            objeto.put("segundoApellidoAutoriza", datosConsulta.getSegundoApellidoAutoriza());
            objeto.put("tipoPermiso", datosConsulta.getTipoPermiso());
            objeto.put("observaciones", datosConsulta.getObservacion());



            modelo.put("titulo", "Ver observaciones");
            modelo.put("observaciones", sol.getMotivoCambioEstado());
           // modelo.put("PermisoInhCre", objeto);

            respuesta.put("vista", htmlValido);
            respuesta.put("modelo", modelo);
        }else if("PPD".equals(modelo.getString("statu"))){
            modelo.put("titulo", "Cargar documento");
            respuesta.put("vista", htmlValido);
            respuesta.put("modelo", modelo);
        }else{
            modelo.put("titulo", "Validar recaudos");
            //modelo.put("PermisoInhCre", objeto);
            respuesta.put("vista", htmlValido);
            respuesta.put("modelo", modelo);
        }

        return respuesta.toString();
    }

    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     */
    @RequestMapping(value="/tipoPermiso", method = RequestMethod.POST)
    public @ResponseBody String consultarTipoPermiso(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{


        JSONObject modelo = new JSONObject(data); 
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"tipo_permiso.html"));

        modelo.put("titulo", "Tipo de permiso");

        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);

        return respuesta.toString();
    }

    /**
     *Funcion que devuelve una vista y modelo a presentar, devuelve una consulta de un permiso guardado segun sea el caso
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
    * @throws ParseException 
     */
    @RequestMapping(value="/permisoInhumacionCremacion", method = RequestMethod.POST)
    public @ResponseBody String permisoInhumacionCremacion(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, ParseException{

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();
        JSONObject objeto = new JSONObject();
        objeto =(modelo.getJSONObject("PermisoInhCre"));
        String vista =  null;
        String tipoPermisoData = null;


        if("NC".equals(modelo.getString("statu"))){
            PermisoInhCre datosConsulta = this.consultarPermiso(modelo.getString("id"));
           
           String formatoFecha = datosConsulta.getFechaDefuncion();
           
           String[] Arrayformato = formatoFecha.split(" ");

            vista = Util.leerArchivo(context.getRealPath(rutaHtml+"permiso_inhumacion_cremacion.html"));

            objeto.put("tipoPermiso", datosConsulta.getTipoPermiso());
            
            

            modelo.put("PermisoInhCre", objeto);
            modelo.put("formato2", Arrayformato[2].toLowerCase());
            modelo.put("titulo", datosConsulta.getTipoPermiso());
            respuesta.put("vista", vista);
            respuesta.put("modelo", modelo);

        }else{
            vista = Util.leerArchivo(context.getRealPath(rutaHtml+"permiso_inhumacion_cremacion.html"));

            tipoPermisoData = transformarDataConsulta(objeto.get("tipoPermiso").toString());


            objeto.put("tipoPermiso", objeto.get("tipoPermiso").toString());
            modelo.put("PermisoInhCre", objeto);
            modelo.put("titulo", objeto.get("tipoPermiso").toString());
            respuesta.put("vista", vista);
            respuesta.put("modelo", modelo);
        }
        return respuesta.toString();
    }

    /**
     *Funcion que devuelve el modelo y la vista a mostrar en el controlador js
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
    * @throws ParseException 
     */
    @RequestMapping(value="/presentarImprimir", method = RequestMethod.POST)
    public @ResponseBody String presentarImprimir(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException, ParseException{

        String token=request.getHeader("Authorization");
        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();
        JSONObject modeloReporte = new JSONObject();
        
        List<Participante> part = new ArrayList<>();
        part =participante.consultarParticPorSolicitud(modelo.getString("id"), "D");

        if(part.size() == 2){
           modeloReporte  = generarReporteDeclarante(modelo.getString("id"),modelo.getString("statu"), token, part);
        }else{
           modeloReporte = generarReporte(modelo.getString("id"),modelo.getString("statu"), token, part);

        }
        
        sol = solicitud.consultarDetalleSolicitud((String) modelo.get("id"));
        
        String fechaRegistro = formatDate.convertirDateAString(sol.getFechaInicio());
        
        modelo.put("titulo", modeloReporte.get("tituloDoc"));
        modelo.put("fechaRegistro", fechaRegistro);
        respuesta.put("modelo", modelo);
        respuesta.put("vista", modeloReporte.get("vista"));
        return respuesta.toString();
    }

    /**
     *Funcion que genera el reporte de un permiso de inhumacion y/o cremacion
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
    * @throws ParseException 
     * @throws JsonParseException
     * @throws GeneralException
     */

    public JSONObject generarReporte(String modelo, String statu, String token, List<Participante> part)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException {
        // TODO Auto-generated method stub
       String RutaIMAGE=context.getRealPath(rutaLogo);
        //Funcionario obtenerDatosFuncionario = Util.buscarFuncionarioPorLogin(token);
       JSONObject formulario=new JSONObject();
        String login = seguridadCliente.getUsernameCliente(token);
        FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
        Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);

        String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
        String tipoPermisoData = null;

        
        String codTipoFuncionario = "RP";
        String parentesco ="";

        String codEstatusFuncionario = "ACT";
        Funcionario dataServicio = new Funcionario();

        dataServicio = funcionarioServicioCliente.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
        Oficina datosOficina = new Oficina();
        datosOficina = obtenerDatosFuncionario.getOficina();

        
        sol = solicitud.consultarDetalleSolicitud(modelo);


        String PrimerNombresAutoriza= null;
        PrimerNombresAutoriza = StringUtils.capitalize(part.get(0).getPrimerNombre().toLowerCase());
        String SegundoNombresAutoriza = null;
        String SegundoNombresAutorizar = part.get(0).getSegundoNombre()==null?" ": part.get(0).getSegundoNombre();
        SegundoNombresAutoriza=StringUtils.capitalize(SegundoNombresAutorizar.toLowerCase());
        String ApellidosAutoriza = null;
        ApellidosAutoriza = StringUtils.capitalize(part.get(0).getPrimerApellido().toLowerCase());
        String SegundoApellidosAutoriza = null;
        String SegundoApellidosAutorizar = part.get(0).getSegundoApellido()==null?" ": part.get(0).getSegundoApellido();
        SegundoApellidosAutoriza=StringUtils.capitalize(SegundoApellidosAutorizar.toLowerCase());
        String parentescos=this.obtenerParetescoDeclarante(part.get(0).getRol());
        parentesco=StringUtils.capitalize(parentescos.toLowerCase());

        String rutaFin = null;
        String rutaPlantilla = null;

        String vista = null;
        
        HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
        PermisoInhCre dataConsulta =this.consultarPermiso(modelo);
        
        String nombreCiudadano = null;
        nombreCiudadano = StringUtils.capitalize(dataConsulta.getPrimerNombreAutoriza().toLowerCase());
        String segundoNombreCiudaddano = null;
        String segundoNombreData = dataConsulta.getSegundoNombreAutoriza()==null?" ":  dataConsulta.getSegundoNombreAutoriza();
        segundoNombreCiudaddano = StringUtils.capitalize(segundoNombreData.toLowerCase());
        
        String apellidoCiudadano = null;
        apellidoCiudadano = StringUtils.capitalize(dataConsulta.getPrimerApellidoAutoriza().toLowerCase());
        String segundoApellidoCiudadadno =  null;
        String segundoApellidoCiudadadnoData = dataConsulta.getSegundoApellidoAutoriza()==null?" ":  dataConsulta.getSegundoApellidoAutoriza();
        segundoApellidoCiudadadno = StringUtils.capitalize(segundoApellidoCiudadadnoData.toLowerCase());
              
        String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
        String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
        String FObito = dataConsulta.getFechaDefuncion();
        
        log.info("-----FECHA CONSULTA---- " + FObito);
        
        String[] arrayFecha = FObito.split(" "); 
        
        String fechaMostrar = arrayFecha[0];
        String horaMostrar = arrayFecha[1]+' '+arrayFecha[2];
        
        String documentoI = String.valueOf(sol.getSolicitante().getCiudadano().getDocumentoIdentidad());
        //capitalizeStr = StringUtils.capitalize(str);
        datosAPintar.put("nombreRegistrador", StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));

        //datosAPintar.put("oficinaRegistradora", StringUtils.capitalize(datosOficina.getNombre().toLowerCase()));
        datosAPintar.put("oficinaRegistradora", (datosOficina.getNombre().toUpperCase()));

        datosAPintar.put("parroquia", StringUtils.capitalize(datosOficina.getDireccion().getParroquia().getNombre().toLowerCase()));

        datosAPintar.put("municipio", StringUtils.capitalize(datosOficina.getDireccion().getMunicipio().getNombre().toLowerCase()));

        datosAPintar.put("estado", StringUtils.capitalize(datosOficina.getDireccion().getEstado().getNombre().toLowerCase()));

        datosAPintar.put("nombreCiudadano", nombreCiudadano+' '+segundoNombreCiudaddano+' '+apellidoCiudadano+' '+segundoApellidoCiudadadno);

        datosAPintar.put("cementerio", StringUtils.capitalize(dataConsulta.getNombreCementerio().toLowerCase()));

        datosAPintar.put("fechaObito", fechaMostrar);
        datosAPintar.put("horaObito", horaMostrar);

        datosAPintar.put("direccionObito", StringUtils.capitalize(dataConsulta.getDireccionDefuncion().toLowerCase()));

        datosAPintar.put("numeroEV14", String.valueOf(dataConsulta.getNumeroCertificadoDef()));
        datosAPintar.put("dia", GenerarActas.obtenerFechaOrHoraActual("DIA"));
        datosAPintar.put("mes", GenerarActas.obtenerFechaOrHoraActual("STRING_MES"));
        datosAPintar.put("anno", GenerarActas.obtenerFechaOrHoraActual("ANIO"));

        datosAPintar.put("nResolucion", String.valueOf(dataServicio.getNumeroResolucion()));

        datosAPintar.put("fResolucion", fechaResolucion);

        datosAPintar.put("nGaceta", String.valueOf(dataServicio.getNumeroGaceta()));

        datosAPintar.put("fGaceta", fechaGaceta);

        datosAPintar.put("parroquiaFallecimiento", StringUtils.capitalize(dataConsulta.getParroquia().toLowerCase()));

        datosAPintar.put("municipioFallecimiento", StringUtils.capitalize(dataConsulta.getMunicipio().toLowerCase()));

        datosAPintar.put("estadoFallecimiento", StringUtils.capitalize(dataConsulta.getEstado().toLowerCase()));

        datosAPintar.put("nombreSolicitante", PrimerNombresAutoriza+' '+SegundoNombresAutoriza+' '+ApellidosAutoriza+' '+SegundoApellidosAutoriza);

        datosAPintar.put("identificacionSolicitante", part.get(0).getDocumentoIdentidad().get(0).getNumero());
        datosAPintar.put("parentescoSolicitante", parentesco);

        datosAPintar.put("nConsecutivo", String.valueOf(dataConsulta.getNumero()));
        datosAPintar.put("rutaImg",RutaIMAGE);

        if("Permiso de inhumacion".equals(dataConsulta.getTipoPermiso())){

            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_INHUMACION);
            rutaFin = rutaImp+""+modelo+""+extfile;

        }else if("Permiso de cremacion".equals(dataConsulta.getTipoPermiso())){

            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_CREMACION);
            rutaFin = rutaImp+""+modelo+""+extfile;
        }else if("Permiso de inhumacion y cremacion".equals(dataConsulta.getTipoPermiso())){

            rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_INHUMACION_CREMACION);
            rutaFin = rutaImp+""+modelo+""+extfile;
        }


        JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource());

        JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);

         String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-permisoInhumacionCremacion/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
         if("Pendiente por imprimir".equals(statu)){
            vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA));
         }else if("PV".equals(statu)){
            vista = Util.leerArchivo(context.getRealPath(RUTA_PV));
            formulario.put("titulo", dataConsulta.getTipoPermiso());
        }

        tipoPermisoData = transformarDataConsulta(dataConsulta.getTipoPermiso());

        vista = vista.replace("ARCHIVOPDF", template);
        
        formulario.put("vista", vista);
        formulario.put("tituloDoc", tipoPermisoData);
        return formulario;
    }
    
    
    /**
     *Funcion que genera el reporte de un permiso de inhumacion y/o cremacion
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
    * @throws ParseException 
     * @throws JsonParseException
     * @throws GeneralException
     */

    public JSONObject generarReporteDeclarante(String modelo, String statu, String token, List<Participante> part)throws JRException, JsonGenerationException, JsonMappingException, IOException, JSONException, ParseException {

       String RutaIMAGE=context.getRealPath(rutaLogo);
       JSONObject formulario=new JSONObject();
       String login = seguridadCliente.getUsernameCliente(token);
       FuncionarioServicioCliente servFuncionario = new FuncionarioServicioCliente();
       Funcionario obtenerDatosFuncionario = servFuncionario.buscarPorLogin(login);

       String codOficina = obtenerDatosFuncionario.getOficina().getCodigo();
       String tipoPermisoData = null;

       
       String codTipoFuncionario = "RP";
       String parentesco ="";

       String codEstatusFuncionario = "ACT";
       Funcionario dataServicio = new Funcionario();

       dataServicio = funcionarioServicioCliente.buscarFuncionarioPorOficina(codOficina, codTipoFuncionario, codEstatusFuncionario);
       Oficina datosOficina = new Oficina();
       datosOficina = obtenerDatosFuncionario.getOficina();

       
       sol = solicitud.consultarDetalleSolicitud(modelo);


       String PrimerNombresAutoriza= null;
       PrimerNombresAutoriza = StringUtils.capitalize(part.get(0).getPrimerNombre().toLowerCase());
       
       String SegundoNombresAutoriza = null;
       String SegundoNombresAutorizar = part.get(0).getSegundoNombre()==null?" ": part.get(0).getSegundoNombre();
       SegundoNombresAutoriza=StringUtils.capitalize(SegundoNombresAutorizar.toLowerCase());
       
       String ApellidosAutoriza = null;
       ApellidosAutoriza = StringUtils.capitalize(part.get(0).getPrimerApellido().toLowerCase());
       String SegundoApellidosAutoriza = null;
       String SegundoApellidosAutorizar = part.get(0).getSegundoApellido()==null?" ": part.get(0).getSegundoApellido();
       SegundoApellidosAutoriza=StringUtils.capitalize(SegundoApellidosAutorizar.toLowerCase());
       String parentescos=this.obtenerParetescoDeclarante(part.get(0).getRol());
       parentesco=StringUtils.capitalize(parentescos.toLowerCase());
       
       String PrimerNombreSegundoData = StringUtils.capitalize(part.get(1).getPrimerNombre().toLowerCase());
       
       String SegundoNombreSegundoD = null;
       String SegundoNombreSegundoData = part.get(1).getSegundoNombre()==null?" ": part.get(1).getSegundoNombre();
       SegundoNombreSegundoD = StringUtils.capitalize(SegundoNombreSegundoData.toLowerCase());
       
       String PrimerApellidoSegundoD = null;
       PrimerApellidoSegundoD = StringUtils.capitalize(part.get(1).getPrimerApellido().toLowerCase());
       
       String SegundoApellidoSegundoD = null;
       String SegundoApellidoSegundoData = part.get(1).getSegundoApellido()==null?" ": part.get(1).getSegundoApellido();
       SegundoApellidoSegundoD = StringUtils.capitalize(SegundoApellidoSegundoData.toLowerCase());
       
       String IdentificacionSegundoD = part.get(1).getDocumentoIdentidad().get(0).getNumero();
       
       String parentescoSegundoD = this.obtenerParetescoDeclarante(part.get(1).getRol());
       String parentescoSegundoData=StringUtils.capitalize(parentescoSegundoD.toLowerCase());
       
       String rutaFin = null;
       String rutaPlantilla = null;

       String vista = null;

       HashMap<String, Object> datosAPintar = new HashMap<String, Object>();
       PermisoInhCre dataConsulta =this.consultarPermiso(modelo);
       
       String nombreCiudadano = null;
       nombreCiudadano = StringUtils.capitalize(dataConsulta.getPrimerNombreAutoriza().toLowerCase());
       String segundoNombreCiudaddano = null;
       String segundoNombreData = dataConsulta.getSegundoNombreAutoriza()==null?" ":  dataConsulta.getSegundoNombreAutoriza();
       segundoNombreCiudaddano = StringUtils.capitalize(segundoNombreData.toLowerCase());
       
       String apellidoCiudadano = null;
       apellidoCiudadano = StringUtils.capitalize(dataConsulta.getPrimerApellidoAutoriza().toLowerCase());
       String segundoApellidoCiudadadno =  null;
       String segundoApellidoCiudadadnoData = dataConsulta.getSegundoApellidoAutoriza()==null?" ":  dataConsulta.getSegundoApellidoAutoriza();
       segundoApellidoCiudadadno = StringUtils.capitalize(segundoApellidoCiudadadnoData.toLowerCase());
       
       String fechaResolucion = formatDate.convertirDateAString(dataServicio.getFechaResolucion());
       String fechaGaceta = formatDate.convertirDateAString(dataServicio.getFechaGaceta());
       String FObito = dataConsulta.getFechaDefuncion();
       
       log.info("-----FECHA CONSULTA---- " + FObito);
      
       String[] arrayFecha = FObito.split(" ");
       
       String fechaMostrar = arrayFecha[0];
       String horaMostrar = arrayFecha[1]+' '+arrayFecha[2];
       
      
      
       String documentoI = String.valueOf(sol.getSolicitante().getCiudadano().getDocumentoIdentidad());
       //capitalizeStr = StringUtils.capitalize(str);
       datosAPintar.put("nombreRegistrador", StringUtils.capitalize(obtenerDatosFuncionario.getPrimerNombre().toLowerCase())+' '+StringUtils.capitalize(obtenerDatosFuncionario.getPrimerApellido().toLowerCase()));

       //datosAPintar.put("oficinaRegistradora", StringUtils.capitalize(datosOficina.getNombre().toLowerCase()));
       datosAPintar.put("oficinaRegistradora", (datosOficina.getNombre().toUpperCase()));

       datosAPintar.put("parroquia", StringUtils.capitalize(datosOficina.getDireccion().getParroquia().getNombre().toLowerCase()));

       datosAPintar.put("municipio", StringUtils.capitalize(datosOficina.getDireccion().getMunicipio().getNombre().toLowerCase()));

       datosAPintar.put("estado", StringUtils.capitalize(datosOficina.getDireccion().getEstado().getNombre().toLowerCase()));

       datosAPintar.put("nombreCiudadano", nombreCiudadano +' '+segundoNombreCiudaddano+' '+apellidoCiudadano+' '+segundoApellidoCiudadadno);

       datosAPintar.put("cementerio", StringUtils.capitalize(dataConsulta.getNombreCementerio().toLowerCase()));

       datosAPintar.put("fechaObito", fechaMostrar);       
       
       datosAPintar.put("horaObito", horaMostrar);

       datosAPintar.put("direccionObito", StringUtils.capitalize(dataConsulta.getDireccionDefuncion().toLowerCase()));

       datosAPintar.put("numeroEV14", String.valueOf(dataConsulta.getNumeroCertificadoDef()));
       datosAPintar.put("dia", GenerarActas.obtenerFechaOrHoraActual("DIA"));
       datosAPintar.put("mes", GenerarActas.obtenerFechaOrHoraActual("STRING_MES"));
       datosAPintar.put("anno", GenerarActas.obtenerFechaOrHoraActual("ANIO"));

       datosAPintar.put("nResolucion", String.valueOf(dataServicio.getNumeroResolucion()));

       datosAPintar.put("fResolucion", fechaResolucion);

       datosAPintar.put("nGaceta", String.valueOf(dataServicio.getNumeroGaceta()));

       datosAPintar.put("fGaceta", fechaGaceta);

       datosAPintar.put("parroquiaFallecimiento", StringUtils.capitalize(dataConsulta.getParroquia().toLowerCase()));

       datosAPintar.put("municipioFallecimiento", StringUtils.capitalize(dataConsulta.getMunicipio().toLowerCase()));

       datosAPintar.put("estadoFallecimiento", StringUtils.capitalize(dataConsulta.getEstado().toLowerCase()));

       datosAPintar.put("nombreSolicitante", PrimerNombresAutoriza+' '+SegundoNombresAutoriza+' '+ApellidosAutoriza+' '+SegundoApellidosAutoriza);

       datosAPintar.put("identificacionSolicitante", part.get(0).getDocumentoIdentidad().get(0).getNumero());
       datosAPintar.put("parentescoSolicitante", parentesco);

       datosAPintar.put("nConsecutivo", String.valueOf(dataConsulta.getNumero()));
       
       datosAPintar.put("nombreSegundoSolicitante", PrimerNombreSegundoData+' '+SegundoNombreSegundoD+' '+PrimerApellidoSegundoD+' '+SegundoApellidoSegundoD);
       datosAPintar.put("identificacionSegundoSolicitante", IdentificacionSegundoD);
       datosAPintar.put("parentescoSegundoSolicitante", parentescoSegundoData);
       datosAPintar.put("rutaImg",RutaIMAGE); 

       if("Permiso de inhumacion".equals(dataConsulta.getTipoPermiso())){

           rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_INHUMACION_DECLARANTE);
           rutaFin = rutaImp+""+modelo+""+extfile;

       }else if("Permiso de cremacion".equals(dataConsulta.getTipoPermiso())){

           rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_CREMACION_DECLARANTE);
           rutaFin = rutaImp+""+modelo+""+extfile;
       }else if("Permiso de inhumacion y cremacion".equals(dataConsulta.getTipoPermiso())){

           rutaPlantilla = context.getRealPath(RUTA_PLANTILLA+PLANTILLA_INHUMACION_CREMACION_DECLARANTE);
           rutaFin = rutaImp+""+modelo+""+extfile;
       }


       JasperReport jasperReport = JasperCompileManager.compileReport(rutaPlantilla);

       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, datosAPintar,  new JREmptyDataSource());

       JasperExportManager.exportReportToPdfFile(jasperPrint, rutaFin);

        String template="<iframe   id='plugin' width='800'  height='800' src='"+ "/web-permisoInhumacionCremacion/tmp/" + modelo + ".pdf#toolbar=0' type='application/pdf'></iframe>";
        if("Pendiente por imprimir".equals(statu)){
           vista = Util.leerArchivo(context.getRealPath(RUTA_VISTA));

        }else if("PV".equals(statu)){
           vista = Util.leerArchivo(context.getRealPath(RUTA_PV));
           formulario.put("titulo", dataConsulta.getTipoPermiso());
       }

       tipoPermisoData = transformarDataConsulta(dataConsulta.getTipoPermiso());

       vista = vista.replace("ARCHIVOPDF", template);
       
       formulario.put("vista", vista);
       formulario.put("tituloDoc", tipoPermisoData);
       return formulario;
       
    }
    

    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/presentarSatisfactorio", method = RequestMethod.POST)
    public @ResponseBody String presentarSatisfactorio(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"satisfactorio.html"));

        modelo.put("mensaje", "Impresi&oacute;n exitosa");

        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }


    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/validarReporteConforme", method = RequestMethod.POST)
    public @ResponseBody String validarReporteConforme(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException, JRException{

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"verificacion_registrador.html"));


        modelo.put("titulo", "Certificacion del registrador");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }

    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/corregirObservaciones", method = RequestMethod.POST)
    public @ResponseBody String corregirObservaciones(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{

        ObjectMapper mapper= new ObjectMapper();
        JSONObject modelo = new JSONObject(data);
        //Map<String, String> data = mapper.readValue(request.getHeader("datos"), Map.class);
        JSONObject objetoJson = new JSONObject();
        String token=request.getHeader("Authorization");

        String htmlValido = Util.leerArchivo(context.getRealPath(rutaHtml+"ver_observaciones.html"));


        objetoJson.put("html", htmlValido);
        objetoJson.put("datosAPintar", "");
        return objetoJson.toString();
    }


    /**
     *Funcion que devuelve una vista y modelo a presentar
     * @author Maria Marsicano
     * @param request
     * @return JsonObject
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @throws GeneralException
     */
    @RequestMapping(value="/cargaExitosa", method = RequestMethod.POST)
    public @ResponseBody String cargaExitosa(@RequestBody String data, HttpSession sesion, HttpServletRequest request) throws GeneralException, JsonParseException, JsonMappingException, IOException, JSONException{


        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();

        String vista = Util.leerArchivo(context.getRealPath(rutaHtml+"cargar_exitosa.html"));

        //modelo.put("mensaje", "Carga de documento exitosa.");
        modelo.put("titulo","Carga de documento exitosa");
        respuesta.put("vista", vista);
        respuesta.put("modelo", modelo);
        return respuesta.toString();
    }

    /**
     *Funcion que devuelve el nombre de un html para mostrar segun el estado del proceso
     * @author Maria Marsicano
     * @param String: estaus
     * @return String

     */
    public String buscarHtml(String estatus){


        String html=null;

        if(("ABIERTA").equals(estatus)){
            html="tipo_escenario";
            log.info("tipo_escenario ifno: "+html);
        }else if(("PENDIENTE POR VERIFICAR R.C").equals(estatus)){
            html="vista_permiso_inhumacion_cremacion";
        }else if(("NO CONFORME POR REGISTRADOR CIVIL").equals(estatus)){
            html="ver_observaciones";
        }else if(("PENDIENTE POR CARGAR DOCUMENTO").equals(estatus)){
            html = "cargar_documento";
        }
        return html;
    }

    /**
     *Funcion que cunsulta pais
     * @author Maria Marsicano
     * @return List<Pais> lista de objetos Pais
     */
    @RequestMapping(value="/consultarPais", method = RequestMethod.GET)
    public List<Pais> consultarPaisTodos(){
        Catalogo catalogo = new Catalogo();
        return catalogo.consultarPaisTodos();
    }

    /**
     *Funcion que cunsulta municipio
     * @author Maria Marsicano
     * @param codigoEstados String codigo del estado
     * @return List<Municipio> lista de objetos municipio
     */
    @RequestMapping(value="/consultarMunicipios/{codigoEstado}", method = RequestMethod.GET)
    public List<Municipio> consultarMunicipiosEstado(@PathVariable("codigoEstado") String codigoEstado){

        Catalogo catalogo = new Catalogo();
        return catalogo.consultarMunicipiosEstado(codigoEstado);
    }

    /**
     *Funcion que cunsulta parroquias por estado
     * @author Maria Marsicano
     * @param codigoMunicipio String codigo del municipio
     * @return List<Parroquia> lista de objetos Parroquia
     */
    @RequestMapping(value="/consultarParroquias/{codigoMunicipio}", method = RequestMethod.GET)
    public List<Parroquia> consultarParroquiasMunicipio(@PathVariable("codigoMunicipio") String codigoMunicipio){
        //String codEstado ="APU";
        Catalogo catalogo = new Catalogo();
        return catalogo.consultarParroquiasMunicipio(codigoMunicipio);
    }


    /**
     *Funcion que actualiza el estatu de una solicitud
     * @author Maria Marsicano
     * @param numCertificado String numero de certificado
     * @return Solicitud soli
     */
    @SuppressWarnings("static-access")
    @RequestMapping(value="/actualizarEstado", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Solicitud actualizarEstatuSolicitud(@RequestBody String data, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception{


        JSONObject dataModelo = new JSONObject(data); //Util.getDataFromRequest(request);
        String parametro=dataModelo.getString("statu");
        

        Solicitud sol = solicitud.consultarDetalleSolicitud(dataModelo.getString("id"));
        Solicitud  solicitudSesion= new Solicitud();


        if(("PV").equals(parametro) && ("conforme").equals(dataModelo.get("permiso"))){
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_IMPRIMIR);
            solicitudSesion.setMotivoCambioEstado("CAMBIO DE ESTADO");
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));

        }else if(("PV").equals(parametro) && ("noConforme").equals(dataModelo.get("permiso"))){
            solicitudSesion.setEstadoSolicitud(Constantes.NO_CONFORME_POR_REGISTRADOR_CIVIL);
            solicitudSesion.setMotivoCambioEstado(dataModelo.getString("observaciones"));
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));


        }else if(("Pendiente por imprimir").equals(parametro)){
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOCUMENTO");
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));

        }else if(("AB").equals(parametro) && ("true").equals(dataModelo.get("numeroCertificado"))){

            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.CANCELADA);
            solicitudSesion.setMotivoCambioEstado("CANCELADA");

        }else if(("AB").equals(parametro) && ("false").equals(dataModelo.get("numeroCertificado"))){

            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");

        }else if(("NC").equals(parametro) && ("true").equals(dataModelo.get("numeroCertificado"))){

            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.CANCELADA);
            solicitudSesion.setMotivoCambioEstado("CANCELADA");

        }else if(("NC").equals(parametro) && ("false").equals(dataModelo.get("numeroCertificado"))){

            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_VERIFICAR_RC);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_VERIFICAR_RC");

        }else if(("PI").equals(parametro)){

            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.PENDIENTE_POR_CARGAR_DOCUMENTO);
            solicitudSesion.setMotivoCambioEstado("PENDIENTE_POR_CARGAR_DOCUMENTO");


        }else if(("PPD").equals(parametro)){
            solicitudSesion.setNumeroSolicitud(dataModelo.getString("id"));
            solicitudSesion.setEstadoSolicitud(Constantes.CERRADA);
            solicitudSesion.setMotivoCambioEstado("CERRADA");
        }

        SolicitudServicio solicitudActualizar = new SolicitudServicio();
        Solicitud soli = solicitudActualizar.actualizaEstadoSolicitud(solicitudSesion);
        return soli;
    }


    /**
     *Funcion que valida la existencia del certificado medico dentro de la base de datos
     * @author Maria Marsicano
     * @param numCertificado String numero de certificado
     * @return boolean
     */
    @RequestMapping(value="/validarCertificadoEV/{numCertificado}", method = RequestMethod.GET)
    public @ResponseBody Boolean validarCertificadoEV(@PathVariable("numCertificado") Long numCertificado){

        DefuncionServicioCliente servicioCliente = new DefuncionServicioCliente();
        boolean resp = servicioCliente.validarCertificadoMedicoDefuncion(numCertificado);


        return resp;


    }

    /**
     *Metodo envia los datos obtenidos del formulario al cliente para ser guardados
     * @author Maria Marsicano
     * @param String data
     * @return String
     * @throws JSONException
     * @throws ParseException
     * @throws GeneralException
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/guardarDatosForm", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String guardarDatosForm(@RequestBody String data, HttpServletRequest request, HttpSession session) throws JsonParseException, JsonMappingException, IOException, JSONException, ParseException, GeneralException, com.fasterxml.jackson.core.JsonParseException, com.fasterxml.jackson.databind.JsonMappingException {

        JSONObject modelo = new JSONObject(data);
        JSONObject respuesta = new JSONObject();
        respuesta =(modelo.getJSONObject("PermisoInhCre"));
       
         if("AB".equals(modelo.getString("statu"))){
            

            String formato = modelo.getString("formato2");
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");

            Date date = inputFormat.parse( respuesta.getString("fechaDefuncion"));
            String outputText = outputFormat.format(date);
            
            log.info("-------outputText+' '+formato.toUpperCase()------" + outputText+' '+formato.toUpperCase());

            respuesta.put("fechaDefuncion", outputText+' '+formato.toUpperCase());

         }else{
         
            DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX");
            String outputText = null;
           

            try{
            Date date = inputFormat.parse(respuesta.getString("fechaDefuncion"));
            outputText = outputFormat.format(date);
           
            respuesta.put("fechaDefuncion", outputText+' '+modelo.getString("formato2").toUpperCase());
            
            }catch(Exception e){
               
               String fechaAguardar = respuesta.getString("fechaDefuncion");
               String[] ArrayFecha = fechaAguardar.split(" ");
               String fechaFin = ArrayFecha[0]+' '+ArrayFecha[1]+' '+modelo.getString("formato2").toUpperCase();
               
               
               respuesta.put("fechaDefuncion", fechaFin);
              
            
         }
         }


        Defuncion.guardarPermisoInhCre(respuesta.toString());

        return "1";

    }


    /**
     *Metodo consulta y retorna los datos de una solicitud
     * @author Maria Marsicano
     * @param String numSolicitud
     * @return PermisoInhCre
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws JsonMappingException
     */
    public @ResponseBody PermisoInhCre consultarPermiso (String numSolicitud) throws JsonGenerationException, JsonMappingException, IOException{


        PermisoInhCre consulta= null;
        consulta =Defuncion.consultaPermisoInhCre(numSolicitud);


        return consulta;
    }


    /**
     *Metodo consulta y retorna los datos de una solicitud
     * @author Maria Marsicano
     * @param String codigo
     * @return parentesco
     */
    public String obtenerParetescoDeclarante(String codigo){

        String parentesco=null;

        switch (codigo) {
            case "MAD":
                parentesco="Madre";
                break;
            case "PAD":
                parentesco="Padre";
                break;
            case "ABU":
                parentesco="Abuelo(a)";
                break;
            case "HRM":
                parentesco="Hermano(a)";
                break;
            case "TTBO":
                parentesco="Tatarabuelo(a)";
                break;
            case "BISA":
                parentesco="Bisabuelo(a)";
                break;
            case "SOB":
                parentesco="Sobrino(a)";
                break;
            case "TIO":
                parentesco="Tio(a)";
                break;
        }
        return parentesco;

    }

    /**
     *Metodo consulta y retorna los datos de una solicitud
     * @author Maria Marsicano
     * @param String codigoOficina
     * @return retorno
     */
    public String buscarNumeroProximo(String codigoOficina){


        Long numero = null;
        numero = catalogoServicio.proximoNroConsecutivo(codigoOficina);



        String retorno= "";
        retorno = String.valueOf(numero);



        return retorno;

    }

    /**
     *Metodo consulta y retorna los datos de una solicitud
     * @author Maria Marsicano
     * @param String dato
     * @return permiso
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public String transformarDataConsulta(String dato) throws JsonGenerationException, JsonMappingException, IOException{
        //String permiso = null;

        switch (dato) {
            case "Permiso de inhumacion":
                //permiso="Permiso de inhumaci&oacute;n";
                return "Permiso de inhumaci&oacute;n";
            case "Permiso de cremacion":
                return "Permiso de cremaci&oacute;n";
            case "Permiso de inhumacion y cremacion":
                return "Permiso de inhumaci&oacute;n y cremaci&oacute;n";
        }



        return null;

    }



}

