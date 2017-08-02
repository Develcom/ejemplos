package ve.gob.cne.sarc.autenticarCiudadano.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Utilitario {
	
	
	private static Properties config;
	
	private  Logger LOG = Logger.getLogger(getClass().getName());
	

	 public  static boolean esCampoVacio(String campo){
		 boolean exito= false;
		
		 try {
		
			
			 return exito= campo==null || campo.isEmpty() ? true : false;
						 
		} catch (Exception e) {
		
		}
	    
		
		  return exito;
	 }
	 
	 
	 public  static boolean esIgualCampos(String primerCampo, String segundoCampo){
		 boolean exito= false;
		
		 try {
			
			   if( ! (esCampoVacio(primerCampo) && esCampoVacio(segundoCampo)) ){
				    
				   if(primerCampo.equalsIgnoreCase(segundoCampo)){					   
					   exito=true;					  
				   }
			   }
						 
		} catch (Exception e) {
			
		}
		 
		  return exito;
	 }


	 public static Properties getConfig(String path) {
			
//		 ServletContext context;
			if (config == null) {
				config = new Properties();
				try {
//			   		config.load(new FileInputStream("configuracion/sistema.properties"));
					config.load(new FileInputStream(path));
			   					   		
			   	} catch (IOException e) {
			   		System.out.println("  error--> "+e.getMessage());
			    }
				
			}
		 
		return config;
		}
	 
	 
  public static String obtenerFechaOrHoraActual(String parametro){
		 
		 String valor="";
		
		 try {
			 Date fecha = new Date();
			 SimpleDateFormat formateador=null;
			 
			 switch(parametro) {
				 case "DIA": 
					 formateador = new SimpleDateFormat("dd");
					 valor =formateador.format(fecha);
				 break;
				 case "NUM_MES": 
					 formateador = new SimpleDateFormat("MM");
					 valor =formateador.format(fecha);
				 break;
				 case "ANIO": 
					 formateador = new SimpleDateFormat("yyyy");
					 valor =formateador.format(fecha);
			     break;
				 case "FECHACOMPLETA": 
					     formateador = new  SimpleDateFormat("dd'-'MM'-'yyyy");
					     valor= formateador.format(fecha);
				        
				  break;	
				  case "STRING_MES": 
					   String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};	
					   formateador = new SimpleDateFormat("MM");
					   valor=  meses[Integer.parseInt(formateador.format(fecha))-1];
					  
				  break;
				  
				  case "HORA_ACTUAL":
					   formateador = new SimpleDateFormat("hh.mm.ss a");
					   valor=formateador.format(fecha);
					  
				  break;
			     
			 }			 
			
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
	 
		 return valor;
		 
	 }	 
	 
   public static String obtenerHoraActual(){
		 		 
		 int hora=0, minuto=0, segundo=0;
		 try {
			
			 Calendar fecha = new GregorianCalendar();
			 hora = fecha.get(Calendar.HOUR_OF_DAY);
		     minuto = fecha.get(Calendar.MINUTE);
		     segundo = fecha.get(Calendar.SECOND);
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		 
		 return hora+":"+ minuto+"-"+segundo;
		 
	 }	 

   
   public static void main(String argv[]) throws Exception
	 {
		// Utilitario utilitario = new Utilitario();
		 
		 
		 String[] mesesString = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
		 
//		 
//		 
//		 
//		 
//		 System.out.println(" mes--->     "+ mesesString[10-1]);
		 
		//System.out.println("   valor--> "+Utilitario.getConfig().getProperty("ruta_archivo_input"));
//		 Calendar fecha = new GregorianCalendar();
//		 System.out.println("mes -> "+fecha.get(Calendar.MONTH));
		 
		 
		    		
		 String test="webC:\\CNE\\jb0oss-as-7.1.0.Final\\standalone\\tmp\\vfs\\temp122b356fa4819883\\web-gestionLibros.war-11e2e65e259dab04\\resources\\plantillaActa//plantilla_apertura.html";		 
		 String[] splitArray = test.split("//");
		 
	      
	 
		 System.out.println(" respuesta--->  "+splitArray[0]);
		 
		 
//		 Date ahora = new Date();
//		    SimpleDateFormat formateador = new SimpleDateFormat("hh.mm.ss a");
//		   System.out.println(" hora-> "+ formateador.format(ahora));
		
	 }

}
