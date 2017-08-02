package ve.gob.cne.sarc.autenticarCiudadano.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import ve.gob.cne.sarc.catalogo.servicio.cliente.CatalogoServicioCliente;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.solicitud.servicio.cliente.SolicitudServicioCliente;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.autenticarCiudadano.web.autenticarCiudadano.controllers.DroolsControlador;

/**
 * <p>Clase que controla parte de la l&oacute;gica necesaria para la autenticaci&oacute;n de ciudadanos con declaraci&oacute;n jurada</p>
 * @author dgonzalez
 *
 */
public class DJUtils {
	
	@Autowired
	private static AdministradorPropiedades properties;

	public static final String[] POSIBLES_DECLARANTES = new String[]{"MAD","PAD"}; //Posibles declarantes de la declaracion jurada
	public static final String[] PREFIJOS = new String[]{"TDM","TDP"}; //Posibles testigos de la declaracion jurada
	public static final String[] CODIGOS_PADRE = new String[]{"PAD","TDP1","TDP2"};
	public static final String[] CODIGOS_MADRE = new String[]{"MAD","TDM1","TDM2"};
	public static final String DIRECCION_PLANTILLA = "/resources/reportes/Declaracion_jurada.jrxml";
	public static final String PLANTILLA_DECLARANTES = "/resources/js/json/RNACI/CIUDADANO/formulario_padres_sin_id.json";
	public static final String PLANTILLA_TESTIGOS = "/resources/js/json/RNACI/CIUDADANO/formulario_declaracion.json";
	public static final String[] CODIGOS_REPETIBLES = new String[]{"TDM1", "TDM2", "TDP1", "TDP2", "TA1", "TA2"}; //Posibles testigos repetibles en el tramite de registro de nacimiento
	public static final String[] CODIGOS_REFERENCIA = new String[]{"TDM2", "TDP2"}; //Referencia a partir de la que se puede repetir testigos
	public static final String RUTA_PDF = "Declaracion_jurada_";
	//public static final String RUTA_PDF = "C:/tmp/"; "Declaracion_jurada_"
	//public static final String RUTA_PDF = "/home/jboss/tmp/Declaracion_jurada_";
	
	
	public static JSONArray generarFormularioDeclaracion(JSONObject participante, ServletContext context) throws JRException, JSONException, IOException{
		String rol =  participante.getString("rol");
		String formulario = ArchivoUtils.leerArchivo( esTestigo(rol) ? PLANTILLA_TESTIGOS : PLANTILLA_DECLARANTES, context);
        formulario = formulario.replace("ROL",rol);
        formulario = formulario.replace("PARENTESCO", participante.getString("parentesco"));
        formulario = formulario.replace("GENERO", "MAD".equals(rol) ? "de la" : "del");
		JSONObject objetoFormly = new JSONObject(formulario);		        
		return objetoFormly.getJSONArray("fields");
	}
	
	public static JSONArray generarPDFDeclaracion(JSONObject declarante, JSONObject participante, ServletContext context) throws JRException, JSONException, IOException, Exception{
		String dj = properties.getProperty("plantilla.jasper.report.ciudadano.declaracionJurada");
		//String realPath = context.getRealPath(DIRECCION_PLANTILLA);
		int nSolicitud = (int) (10000*Math.random());
		String rutaLocal=properties.getProperty("sarc.file.tmp");
		String nombreArchivo = participante.getString("rol") + nSolicitud + ".pdf";
		String realPathFdf = rutaLocal+RUTA_PDF + nombreArchivo;
        JasperReport jasperReport = JasperCompileManager.compileReport(dj);
        Map<String,Object> parameters = new HashMap<String,Object>();
        String urlFile=properties.getProperty("plantilla.jasper.report.imagen.logo");
//        Date fecha = declaracion.getFechaDeclaracion();
//        parameters.put("dia", GenerarActas.obtenerParametroDeFecha("DIA", fecha));
//        parameters.put("mes", GenerarActas.obtenerParametroDeFecha("STRING_MES", fecha));
//        parameters.put("ano", GenerarActas.obtenerParametroDeFecha("ANIO", fecha));
        parameters.put("urlFile", urlFile);
        
        cargarDatosDeclarante(parameters, declarante);
//        cargarDatosParticipante(parameters, testigo);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,  new JREmptyDataSource());      
        JasperExportManager.exportReportToPdfFile(jasperPrint, realPathFdf);
        String server=properties.getProperty("sarc.web.servidor.atc");
        String template="<embed width='900' height='450' src='"+server+"/web-autenticarCiudadano/tmp/" + "Declaracion_jurada_" + participante.getString("rol") + nSolicitud + ".pdf' type='application/pdf'></embed>";
        //String template="<embed width='900' height='450' src='"+ "C:/tmp/" + nombreArchivo + "' type='application/pdf'></embed>";
        //String template="<embed width='900' height='450' src='"+ "/web-generales/tmp/" + nombreArchivo + "' type='application/pdf'></embed>";
		JSONObject campo = new JSONObject();
		campo.put("template", template);
		JSONArray fields = new JSONArray();
		fields.put(campo);
		return fields;
	}
	

	public static void cargarDatosDeclarante(Map<String,Object> parameters,JSONObject declarante) throws JSONException{
        parameters.put("declarado",declarante.getString("primerNombre")+" "+
        		(declarante.has("segundoNombre") ? declarante.getString("segundoNombre")+" " : "") +
        		declarante.getString("primerApellido")+" "+
        		(declarante.has("segundoApellido") ? declarante.getString("segundoApellido")+" " : ""));
      //  parameters.put("Direccion", declarante.getString("Direccion"));
//        parameters.put("Estado", padre.getDireccion().get(1).getEstado().getCodigo());
//        parameters.put("Municipio", padre.getDireccion().get(1).getMunicipio().getCodigo());
//        parameters.put("Parroquia", padre.getDireccion().get(1).getParroquia().getCodigo());
//        parameters.put("Pais", padre.getDireccion().get(1).getPais().getCodigo());
        //if(declarante.getString("documentoIdentidad")!=null)
        	//parameters.put("documentoIdentidad",padre.getDocumentoIdentidad().get(0).getNumero());
     //   return parameters;
	}
	
	/**
	 * <p>Busca los posibles declarantes de la declaraci&oacute;n jurada</p>
	 * @param listaParticipantes Lista de participantes de la declaraci&oacute;n jurada
	 * @return lista de posibles declarantes, ejemplo: {""}
	 */
	public static List<String> getPosiblesDeclarantes(List<Participante> listaParticipantes){
		List<String> declarantes = new ArrayList<>();
		for (int i = 0; i < POSIBLES_DECLARANTES.length; i++) {
			Participante p = ProcesadorListas.encontrarParticipantePorCodigo(listaParticipantes, POSIBLES_DECLARANTES[i]);
			if (p != null) {
				declarantes.add(POSIBLES_DECLARANTES[i]);
				declarantes.add(PREFIJOS[i] + 1);
				declarantes.add(PREFIJOS[i] + 2);
			}
		}
		return declarantes;
	}
	
	/**
	 * 
	 * @param rol
	 * @return
	 */
	public static boolean esTestigo(String rol){
		for(int i = 0; i < CODIGOS_REPETIBLES.length; i++){
			if(rol.equalsIgnoreCase(CODIGOS_REPETIBLES[i]))
				return true;
		}
		return false;
	}

	public static boolean esTestigoDeMadre(String rol){
		for(int i = 0; i < CODIGOS_MADRE.length; i++){
			if(rol.equalsIgnoreCase(CODIGOS_MADRE[i]))
				return true;
		}
		return false;
	}

	
	/**
	 * <p>Recibe la lista de participantes autenticados y verifica la existencia de participantes repetibles para ese rol</p>
	 * @param listaParticipantesAutenticados
	 * @return true si hay participantes repetibles, false en caso contrario
	 * @throws JSONException 
	 */
	public static boolean testigosRepetibles(JSONArray listaParticipantesAutenticados) throws JSONException{
		if(listaParticipantesAutenticados == null)
			return false;
		String proximoRol = listaParticipantesAutenticados.getJSONObject(listaParticipantesAutenticados.length()-1).getString("rol");
		boolean hayReferencia = false;
		for(int i = 0; i < listaParticipantesAutenticados.length()-1; i++){
			String lRol = listaParticipantesAutenticados.getJSONObject(i).getString("rol");
			for(int j=0; j < DJUtils.CODIGOS_REFERENCIA.length; j++){
				if(DJUtils.CODIGOS_REFERENCIA[j].equalsIgnoreCase(lRol)){
					hayReferencia = true;
					break;
				}
			}
		}
		for(int i = 0; i < listaParticipantesAutenticados.length()-1 ; i++){
			if(hayReferencia && esTestigo(proximoRol))
				return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param rol
	 * @param rol2
	 * @return
	 * @throws JSONException 
	 */
	public static boolean esCompatible(JSONObject p1, JSONObject p2) throws JSONException{
		String rol = p1.getString("rol");
		String rol2 = p2.getString("rol");
		if("TA2".equals(rol) && "TA1".equals(rol2))
			return false;
		if("TDM2".equals(rol) && "TDM1".equals(rol2))
			return false;
		if("TDP2".equals(rol) && "TDP1".equals(rol2))
			return false;
		return true;
	}

	
	/**
	 * <p>Busca los testigos autenticados en la lista que se pasa como argumento en el que el &uacute;ltimo elemento
	 * representa el testigo en proceso de autenticaci&oacute;n</p>
	 * @param participantes : lista de participantes autenticados
	 * @return
	 * @throws JSONException 
	 */
	public static  JSONArray encontrarTestigosAutenticados(JSONArray listaParticipantes) throws JSONException {
		String documentoIrrepetible = getDocumentoIrrepetible(listaParticipantes);
		JSONArray testigosRepetibles = new JSONArray();
		for (int i = 0; i < listaParticipantes.length()-1; i++) {
			JSONObject jsonParticipante = listaParticipantes.getJSONObject(i);
			if (!estaDocumentoRepetido(testigosRepetibles, jsonParticipante) && esTestigo(jsonParticipante.getString("rol")) && (documentoIrrepetible == null || !documentoIrrepetible.equals(getCedula(jsonParticipante)))) {
				testigosRepetibles.put(jsonParticipante);
			}
		}
		return testigosRepetibles;
	}
	
	/**
	 * 
	 * @param listaAutenticados
	 * @return
	 * @throws JSONException
	 */
	public static String getDocumentoIrrepetible(JSONArray listaAutenticados) throws JSONException{
		//Participante a serautenticado
		JSONObject mParticipante = listaAutenticados.getJSONObject(listaAutenticados.length()-1);
		for(int i = 0; i < listaAutenticados.length() - 1; i++){
			JSONObject participante = listaAutenticados.getJSONObject(i);
			if(!esCompatible(mParticipante, participante))
				return getCedula(participante);
		}
		return null;
	}

	public static JSONArray getListaParticipantesDeclaracion(JSONArray listaParticipantes, CatalogoServicioCliente catalogoCliente) throws JSONException{
		boolean djMadre = false, djPadre = false;
		JSONArray listaCodigosDJ = new JSONArray();
		for (int i = 0; i < listaParticipantes.length(); i++) {
			JSONObject jsonCodigo = listaParticipantes.getJSONObject(i);
			Iterator<String> keys = jsonCodigo.keys();
			String codigoRol = keys.next();
			if(Arrays.binarySearch(DJUtils.CODIGOS_MADRE, codigoRol)>=0)
				djMadre = true;
			if(Arrays.binarySearch(DJUtils.CODIGOS_PADRE, codigoRol)>=0)
				djPadre = true;
		}
		if(djMadre){
			for (int i = 0; i < DJUtils.CODIGOS_MADRE.length; i++) {
				JSONObject jsonCodigo = new JSONObject();
				//jsonCodigo.put(DJUtils.CODIGOS_MADRE[i], catalogoCliente.consultarTipoParticipantePorCodigo(DJUtils.CODIGOS_MADRE[i], false, catalogoCliente.buscarToken()).getNombre());
				jsonCodigo.put(DJUtils.CODIGOS_MADRE[i], catalogoCliente.consultarTipoParticipantePorCodigo(DJUtils.CODIGOS_MADRE[i], false, DroolsControlador.tokenAutenticar).getNombre());
				listaCodigosDJ.put(jsonCodigo);
			}
		}
		if(djPadre){
			for (int i = 0; i < DJUtils.CODIGOS_PADRE.length; i++) {
				JSONObject jsonCodigo = new JSONObject();
				//jsonCodigo.put(DJUtils.CODIGOS_PADRE[i], catalogoCliente.consultarTipoParticipantePorCodigo(DJUtils.CODIGOS_PADRE[i], false, catalogoCliente.buscarToken()).getNombre());
				jsonCodigo.put(DJUtils.CODIGOS_MADRE[i], catalogoCliente.consultarTipoParticipantePorCodigo(DJUtils.CODIGOS_MADRE[i], false, DroolsControlador.tokenAutenticar).getNombre());
				listaCodigosDJ.put(jsonCodigo);
			}
		}
		return listaCodigosDJ;
	}
	
	/**
	 * <p>Retorna el n&uacute;mero de c&eacute;dula de identidad de un participante</p>
	 * @param participante
	 * @return numero de ceduta de identidad
	 * @throws JSONException
	 */
	public static String getCedula(JSONObject participante) throws JSONException{
		JSONArray listaDocumentos = participante.getJSONArray("documentoIdentidad");
		for(int i = 0; i < listaDocumentos.length(); i++){
			JSONObject documento = listaDocumentos.getJSONObject(i);
			JSONObject tipoDocumento = documento.getJSONObject("tipoDocumento");
			if("CED".equalsIgnoreCase(tipoDocumento.getString("codigo"))){
				return documento.getString("numero");
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param listaParticipantes
	 * @param jParticipante
	 * @return
	 * @throws JSONException
	 */
	public static boolean estaDocumentoRepetido(JSONArray listaParticipantes, JSONObject jParticipante) throws JSONException{
		String cedula = getCedula(jParticipante);
		for(int i = 0; i < listaParticipantes.length(); i++){
			JSONObject lJparticipante = listaParticipantes.getJSONObject(i);
			if(cedula.equals(getCedula(lJparticipante)))
				return true;
		}
		return false;
	}


	/**
	 * 
	 * @param listaParticipantes
	 * @param rol
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getParticipantePorRol(JSONArray listaParticipantes, String rol) throws JSONException{
		for(int i =0; i < listaParticipantes.length(); i++){
			JSONObject jsonParticipante = listaParticipantes.getJSONObject(i);
			if(rol.equalsIgnoreCase(jsonParticipante.getString("rol"))){
				return jsonParticipante;
			}
		}
		return null;
	}
	
	
	
	public static List<String> getDeclarantesYtestigosDJ(){
		return null;
	}
}
