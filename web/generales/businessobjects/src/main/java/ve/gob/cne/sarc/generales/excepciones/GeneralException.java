package ve.gob.cne.sarc.generales.excepciones;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ve.gob.cne.sarc.generales.util.Util;


public class GeneralException extends Exception{
	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public GeneralException(String errCode){
		this.errCode =  errCode;
		this.errMsg = "";
	}
	
	public void getMessage(String archivo, String code){
		try{
			this.setErrMsg(getMessageS(archivo, code));
		}catch(IOException e){
			this.setErrMsg(e.getMessage());
		}
	}

	public String getMessageS(String archivo, String code) throws IOException{
		try{
			Properties propiedades = new Properties();
			InputStream entrada = null;
			entrada = new FileInputStream(archivo);
			propiedades.load(entrada);
			return propiedades.getProperty(code);
		}catch(IOException e){
			throw new IOException(e);
		}
	}
	
}
