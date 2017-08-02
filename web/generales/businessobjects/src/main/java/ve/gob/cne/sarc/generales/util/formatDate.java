package ve.gob.cne.sarc.generales.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class formatDate {
	
	public String convertDDMMYYY(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String date = sdf.format(d); 
		return date;
	}
	
	    /**
	     * Permite convertir un String en fecha (Date).
	     * @param fecha Cadena de fecha dd/MM/yyyy
	     * @return Objeto Date
	     */
	    public static Date ParseFecha(String fecha) {
	    	Date date = null;
	    	try {
                Date myDate = new Date();

                SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ");
                SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");
                         
                String mdy = mdyFormat.format(myDate);
                String dmy = dmyFormat.format(myDate);
                System.out.println(mdy);
                System.out.println(dmy);            
                
                date = dmyFormat.parse(dmy);
        		System.out.println(dmyFormat.format(fecha));
            } catch (ParseException exp) {
                exp.printStackTrace();
            }
			return date;
 }
	    
	public String convertYYYY(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String date = sdf.format(d); 
		return date;
	}
	public Integer calcularEdad(String fecha, String tipo){
	    Date fechaNac=null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
            que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
            en este caso es día mes año*/
            fechaNac = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        /*Se crea un objeto con la fecha actual*/
        Calendar fechaActual = Calendar.getInstance();
        /*Se asigna la fecha recibida a la fecha de nacimiento.*/
        fechaNacimiento.setTime(fechaNac);
        /*Se restan la fecha actual y la fecha de nacimiento*/
        int ano = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
        /*Se ajusta el año dependiendo el mes y el día*/
        if(mes<0 || (mes==0 && dia<0)){
        	ano--;
        }
        /*Regresa la edad en base a la fecha de nacimiento*/
        if(tipo != null && "D".equalsIgnoreCase(tipo.trim())){
        	return dia;
        }else if(tipo != null && "M".equalsIgnoreCase(tipo.trim())){
        	return mes;
        }else if(tipo != null && "A".equalsIgnoreCase(tipo.trim())){
        	return ano;
        }else{
        	return ano;
        }
    }
	
	
   public static String convertirDateAString(Date d){
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      String date = sdf.format(d); 
      return date;
   }
	
	
	 public static void main(String[] args) {
		
		 formatDate date =  new  formatDate();
		 		 
		String test="HIJ";
		
		Pattern pat = Pattern.compile("HIJ1");
	    Matcher mat = pat.matcher(test);
	     if (mat.matches()) {
	         System.out.println("SI");
	     } else {
	         System.out.println("NO");
		
	     }
	}
	
}
