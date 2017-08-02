package  ve.gob.cne.sarc.generales.reporte;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

//import org.apache.log4j.Logger;

import java.util.logging.Logger;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;

/**
 * <p>Clase que contiene todos los metodos necesarios para la generacion de las actas </p>
 * @author: Juan Carlos Subero
 * @version: 1
 */
public class GenerarActas {
	
	 private String templatePath;
	 Logger LOG = Logger.getLogger(getClass().getName());
	
	
	 public GenerarActas(){
			
		
			
	}
		
	public GenerarActas(String templatePath){
		
		this.templatePath=templatePath;
		
	}
	
	

    
    /**Compila el reporte y genera una versión binaria de la misma
     * 
     * @param rutaArchivo: ruta del archivo plantilla de jasperReport
     * @return
     * @throws JRException
     */
    public JasperReport compilarReporte(String rutaArchivo) throws JRException {
    	LOG.info(" Esta en el metodo compilar reporte");
    	LOG.info("****************rutaArchivo: "+rutaArchivo);
        JasperReport jasperReport=null;
       
        	LOG.info(" Se compila el diseno");
        	jasperReport=JasperCompileManager.compileReport(rutaArchivo);
            
        	LOG.info("*********************jaspeReport*******: "+jasperReport);
            LOG.info(" Se compilo el diseno");
     
     
        return(jasperReport);
    }

    /**Compila el reporte y genera una versión binaria de la misma
     * 
     * @param rutaArchivo: ruta del archivo plantilla de jasperReport
     * @return
     * @throws JRException
     */
    public JasperReport compilarReporte(InputStream rutaArchivo) throws JRException {
    	LOG.info(" Esta en el metodo compilar reporte");
    	LOG.info("****************rutaArchivo: "+rutaArchivo);
        JasperReport jasperReport=null;
       
        	LOG.info(" Se compila el diseno");
        	jasperReport=JasperCompileManager.compileReport(rutaArchivo);
            
        	LOG.info("*********************jaspeReport*******: "+jasperReport);
            LOG.info(" Se compilo el diseno");
     
     
        return(jasperReport);
    }
    /**
     * Llena el informe y genera una versión binaria de la misma
     * @param jasperReport El diseño de reporte compilado
     * @param data data a reemplazar
     * @return JasperPrint
     */
        JasperPrint jasperPrint=null;
        public JasperPrint sustituirTag(JasperReport jasperReport, HashMap<String, Object> data)throws JRException{
        LOG.info("  metodo  sustituirTag");
       
                    
        LOG.info("  antes de sutituir  ");
        jasperPrint=JasperFillManager.fillReport(jasperReport, data,  new JREmptyDataSource());
        LOG.info("  luego de sutituir  ");
       
        LOG.info("  fin  metodo  sustituirTag");
        return(jasperPrint);
    }
	
	

	
    /** Metodo que exporta el reporte a un formato especifico
     * 
     * @param jasperPrint
     * @param nombreArchivo
     * @param rutaSalida
     */
    
    public void exportarDocumento(JasperPrint jasperPrint, String formato,  String rutaSalida) throws JRException{
    	
    	 
    		    if(formato.equalsIgnoreCase("PDF")){
    		      		    	
    		    	JasperExportManager.exportReportToPdfFile(jasperPrint, rutaSalida+".pdf");
    		    	
    		    }else
    		    	if(formato.equalsIgnoreCase("HTML")){
    		    		
    		    		JasperExportManager.exportReportToHtmlFile(jasperPrint, rutaSalida+".html");
    		    	}
    		  
		    
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
	
  public static String obtenerParametroDeFecha(String parametro, Date fecha){
      
      String valor="";
     
      try {
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
    

}
