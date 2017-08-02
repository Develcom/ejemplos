package ve.gob.cne.sarc.autenticarCiudadano.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.comunes.participante.Participante;

public class ProcesadorListas {

	/**
	 * <p>
	 * Retorna true si es necesario habilitar la opci&oacute;n de repetir
	 * testigo verifica que el testigo 2 de la declaraci&oacute;n jurada de la
	 * madre o el padre est&eacute; autenticado y que el &iacute;ndice del
	 * participante actual es mayor
	 * </p>
	 * 
	 * @param participantes
	 *            : lista de participantes autenticados con anterioridad
	 * @param participante
	 *            : participante a ser procesado en el siguiente paso
	 * @return
	 */
	public static boolean habilitarOpcionRepetir(
			List<Participante> participantes, Participante participante) {
		int indiceParticipante = participantes.indexOf(participante);
		int indicePadre = -1;
		int indiceMadre = -1;
		for (Participante p : participantes) {
			if ("TDM2".equals(p.getRol()) && p.isAutenticado()) {
				indiceMadre = participantes.indexOf(p);
				break;
			}
			if ("TDP2".equals(p.getRol()) && p.isAutenticado()) {
				indicePadre = participantes.indexOf(p);
				break;
			}
		}
		return ((indiceMadre > -1) && (indiceParticipante >= indiceMadre))
				|| ((indicePadre > -1) && (indiceParticipante >= indicePadre));
	}

	/**
	 * 
	 * @param participantes
	 * @return
	 */
	public static List<Participante> encontrarTestigosAutenticados(
			List<Participante> participantes, String rol, String seleccionado,
			int limiteSuperior) {
		int mLimiteSuperior = (limiteSuperior != -1 ? limiteSuperior
				: participantes.size());
		List<Participante> nombres = new ArrayList<Participante>();
		// Busca todos los participantes con rol de testigo que poseen diferente
		// documento
		for (int i = 0; i < mLimiteSuperior; i++) {
			Participante participante = participantes.get(i);
			if (participante.getRol().startsWith("T")
					&& !estaNumeroDeDocumentoRepetido(nombres, participante
							.getDocumentoIdentidad().get(0).getNumero(), -1)) {
				nombres.add(participante);
			}
		}
		// Elimina loa repeticion en roles incompatibles, por ejemplo si ha sido
		// autenticado como testigo 1 de la madre no
		// puede ser utilizado como testigo 2 de la madre
		String rolAeliminar = null;
		if (rol.equals("TDM2"))
			rolAeliminar = "TDM1";
		if (rol.equals("TDP2"))
			rolAeliminar = "TDP1";
		if (rol.equals("TA2"))
			rolAeliminar = "TA1";
		// Busca los participantes
		if (rolAeliminar != null)
			for (Participante part : nombres) {
				if (rolAeliminar.equals(part.getRol())) {
					nombres.remove(part);
					break;
				}
			}
		if ("TDM2".equals(rol) || "TDP2".equals(rol) || "TA2".equals(rol)) {
			// TODO eliminar seleccionado como argumento
			if (seleccionado != null)
				for (Participante part : nombres) {
					if (part.getRol().equals(seleccionado)) {
						nombres.remove(part);
						break;
					}
				}
		}
		return nombres;
	}

	public static boolean estaDocumentoRepetido(
			Vector<Participante> participantes, Participante participante,
			boolean menosUno) {
		boolean loContiene = false;
		int indiceParticipante = (menosUno ? participantes
				.indexOf(participante) : participantes.size());
		for (int i = 0; i < indiceParticipante; i++) {
			Participante participante1 = participantes.elementAt(i);
			if (participante1
					.getDocumentoIdentidad()
					.get(0)
					.getNumero()
					.equals(participante.getDocumentoIdentidad().get(0)
							.getNumero()))
				return true;
		}
		return loContiene;
	}

	public static Participante encontrarParticipantePorCodigo(
			List<Participante> participantes, String codigo) {
		Participante participante2 = null;
		for (Participante participante : participantes) {
			if (participante.getRol().equals(codigo)) {
				participante2 = new Participante();
				participante2.setRol(participante.getRol());
				participante2.setParentesco(participante.getParentesco());
				participante2.setDocumentoIdentidad(participante
						.getDocumentoIdentidad());
				participante2.setPrimerApellido(participante
						.getPrimerApellido());
				participante2.setSegundoApellido(participante
						.getSegundoApellido());
				participante2.setPrimerNombre(participante.getPrimerNombre());
				participante2.setSegundoNombre(participante.getSegundoNombre());
				participante2.setDireccion(participante.getDireccion());
				participante2.setTipoDocumento("V");
				participante2.setFechaNacimiento(participante
						.getFechaNacimiento());
			}
		}
		return participante2;
	}

	public static boolean estaNumeroDeDocumentoRepetido(
			List<Participante> participantes, String nroDocumento,
			int limiteSuperior) {
		int mLimiteSuperior = (limiteSuperior != -1 ? limiteSuperior
				: participantes.size());
		for (int i = 0; i < mLimiteSuperior; i++) {
			Participante participante1 = participantes.get(i);
			if (participante1.getDocumentoIdentidad().get(0).getNumero()
					.equals(nroDocumento))
				return true;
		}
		return false;
	}

	/**
	 * <p>
	 * Retorna el c&oacute;digo del documento CED, PAS, y el n&uacute;mero
	 * </p>
	 * 
	 * @param data
	 * @param rol
	 * @return
	 */
	public static String[] getNumeroDocumento(Map<String, String> data,
			String rol) {
		String codigoDocumento = data.get(rol + "_tipoDocumento");
		String nroDoc = null;
		if (codigoDocumento.equalsIgnoreCase("CED")) {
			nroDoc = data.get(rol + "_nacionalidad") + "-"
					+ data.get(rol + "_cedula");
		} else if (codigoDocumento.equalsIgnoreCase("PAS")) {
			nroDoc = data.get(rol + "_pasaporte");
		}
		return new String[] { codigoDocumento, nroDoc };
	}

	/**
	 * 
	 * @param datosDocumento
	 * @param rol
	 * @return
	 */
	public static ArrayList<DocumentoIdentidad> procesaDatosDelDocumentoID(
			String[] datosDocumento, String rol) {
		DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
		TipoDocumento tipoDocumento = new TipoDocumento();
		tipoDocumento.setCodigo(datosDocumento[0]);
		documentoIdentidad.setTipoDocumento(tipoDocumento);
		documentoIdentidad.setNumero(datosDocumento[1]);
		ArrayList<DocumentoIdentidad> listaDocumentosID = new ArrayList<DocumentoIdentidad>();
		listaDocumentosID.add(documentoIdentidad);
		return listaDocumentosID;
	}

	public static Participante getParticipanteEnLista(
			List<Participante> participantes, String rol) {
		for (Participante p : participantes) {
			if (rol.equals(p.getRol()))
				return p;
		}
		return null;
	}

	public static Participante getParticipantePorDocumento(List<Participante> participantes, Participante mParticipante) {
		List<DocumentoIdentidad> mDocumentosIdentidad = mParticipante.getDocumentoIdentidad();
		String[] repetibles = new String[]{"TDM1", "TDM2","TDP1", "TDP2"};
		if(mDocumentosIdentidad == null)
			return null;
		DocumentoIdentidad mDocumentoIdentidad = mDocumentosIdentidad.get(0);
		String mRol = mParticipante.getRol();
		for (Participante p : participantes) {
			List<DocumentoIdentidad> lDocumentosIdentidad = p.getDocumentoIdentidad();
			if(lDocumentosIdentidad != null){
				DocumentoIdentidad lDocumentoIdentidad = lDocumentosIdentidad.get(0);
				String lRol = p.getRol();
				int indexRol = Arrays.binarySearch(repetibles, lRol);
				if(mDocumentoIdentidad.equals(lDocumentoIdentidad) && !mRol.equals(lRol) && indexRol >= 0)
					return p;
			}
		}
		return null;
	}
	
	/**
	 * Retorna el tipo de direccion especificado para un participante
	 * @param p
	 * @param tipoDireccion  
	 * @return
	 */
	public static Direccion getDireccionPorTipo(Participante p, String tipoDireccion){
		List<Direccion> direcciones = p.getDireccion();
		for(Direccion d : direcciones){
			if(tipoDireccion.equalsIgnoreCase(d.getTipoDireccion().getCodigo())){
				return d;
			}
		}
		return null;
	}
	
	public static boolean fallecidoMenorA(Map<String,String> data){
	    boolean retorno = false;
	    int anioMam;
	    int anioFal;
	    if(data.get("MAD_ano") !=null && data.get("FAL_ano") !=null){
	        anioMam = Integer.parseInt(data.get("MAD_ano"));
	        anioFal = Integer.parseInt(data.get("FAL_ano"));
	        retorno = validarFechaFallecido(anioMam,anioFal);
	    }else if(data.get("PAD_ano") !=null && data.get("FAL_ano") !=null){
	        anioMam = Integer.parseInt(data.get("PAD_ano"));
	        anioFal = Integer.parseInt(data.get("FAL_ano"));
	        retorno = validarFechaFallecido(anioMam,anioFal);
	    }else if(data.get("MAM_ano") !=null && data.get("FAL_ano") !=null){
	        anioMam = Integer.parseInt(data.get("MAM_ano"));
	        anioFal = Integer.parseInt(data.get("FAL_ano"));
	        retorno = validarFechaFallecido(anioMam,anioFal);
	    }else if(data.get("PAP_ano") !=null && data.get("FAL_ano") !=null){
            anioMam = Integer.parseInt(data.get("PAP_ano"));
            anioFal = Integer.parseInt(data.get("FAL_ano"));
            retorno = validarFechaFallecido(anioMam,anioFal);
        }else{
            retorno = false;
        }
	    
	    return retorno;
	}
	
	private static boolean validarFechaFallecido(Integer fecha, Integer fecha2){
	    boolean retorno = false;
	        if(fecha <= fecha2){
	            retorno = false;
	        }else if(fecha2 <= fecha){
	            retorno = true; 
	        }
	    return retorno;
	    
	}
	
	public static boolean calcularOtroDeclaranteMayorDeEdad(String rol, Date fechaNacimiento){
	    String fechaCalculada;
	    boolean declaranteMayorMenor = false;

	    
    if(rol.equals("MAD") || rol.equals("PAD") || rol.equals("FAL")){
        declaranteMayorMenor = false;
	}else{
        fechaCalculada = CalculoDiasMesyAno(fechaNacimiento);
        declaranteMayorMenor = DeclaranteMayoMenorEdad(fechaCalculada);  
	}
    return declaranteMayorMenor;
	}

	private static String CalculoDiasMesyAno(Date fechaNacimiento){
	    String fechaRetorno;
	    Date fechaHoy = new Date();
	    DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");   
	    String fechaNacDeclarante = outputFormat.format(fechaNacimiento);
	    String fechaSistema = outputFormat.format(fechaHoy);
	    String fechaInicio = fechaNacDeclarante; 
	    String fechaActual = fechaSistema; 
	    String[] aFechaIng = fechaInicio.split("/"); 
	    Integer diaInicio = Integer.parseInt(aFechaIng[0]); 
	    Integer mesInicio = Integer.parseInt(aFechaIng[1]); 
	    Integer anioInicio = Integer.parseInt(aFechaIng[2]); 

	    String[] aFecha = fechaActual.split("/"); 
	    Integer diaActual = Integer.parseInt(aFecha[0]); 
	    Integer mesActual = Integer.parseInt(aFecha[1]); 
	    Integer anioActual = Integer.parseInt(aFecha[2]); 
	    int b = 0; 
	    int dias = 0; 
	    int mes = 0; 
	    int anios = 0; 
	    int meses = 0; 
	    mes = mesInicio - 1; 
	    if(mes==2){ 
	        if ((anioActual % 4 == 0) && ((anioActual % 100 != 0) || (anioActual % 400 == 0))){ 
	            b = 29; 
	        }else{ 
	            b = 28; 
	        } 
	    }else if(mes <= 7){ 
	        if(mes == 0){ 
	            b = 31; 
	        }else if(mes % 2==0){ 
	            b = 30; 
	        }else{ 
	            b = 31; 
	        } 
	    }else if(mes > 7){ 
	        if(mes % 2 == 0){ 
	            b = 31; 
	        }else{ 
	            b = 30; 
	        } 
	    } 
	    if((anioInicio > anioActual) || (anioInicio == anioActual && mesInicio > mesActual) || 
	            (anioInicio == anioActual && mesInicio == mesActual && diaInicio > diaActual)){ 
	    }else{ 
	        if(mesInicio <= mesActual){ 
	            anios = anioActual - anioInicio; 
	            if (diaInicio <= diaActual){ 
	                meses = mesActual - mesInicio; 
	                dias = b - (diaInicio - diaActual); 
	            }else{ 
	                if(mesActual == mesInicio){ 
	                    anios = anios - 1; 
	                } 
	                meses = (mesActual - mesInicio - 1 + 12) % 12; 
	                dias = b - (diaInicio - diaActual); 
	            } 
	        }else{ 
	            anios = anioActual - anioInicio - 1; 
	            System.out.println(anios); 
	            if(diaInicio > diaActual){ 
	                meses = mesActual - mesInicio - 1 + 12; 
	                dias = b - (diaInicio - diaActual); 
	            }else{ 
	                meses = mesActual - mesInicio + 12; 
	                dias = diaActual -diaInicio; 
	            } 
	        } 
	    } 
	    fechaRetorno = dias+"/"+meses+"/"+anios;
	    return fechaRetorno;
	}
	
	private static boolean DeclaranteMayoMenorEdad(String fecha){
	    boolean retorno = false;
	    int var = 18;
	    String[] arregloFecha = fecha.split("/");
	    if( Integer.parseInt(arregloFecha[2]) < var){
	        retorno = true;
	    }    
	    return retorno;
	}


}
