package ve.gob.cne.sarc.autenticarCiudadano.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

public class ArchivoUtils {

	public static String leerArchivo(String ruta, ServletContext context ) throws IOException{
		String stringObject=null;
		File file=new File(context.getRealPath(ruta));
		try {
			FileInputStream fis=new FileInputStream(file);
			byte[] buffer=new byte[(int) file.length()];
			int nroBytesLidos=fis.read(buffer);
			stringObject= new String(buffer,0,nroBytesLidos);
			fis.close();
		}catch(IOException e){
			throw e;
		}
		return stringObject;
	}
}
