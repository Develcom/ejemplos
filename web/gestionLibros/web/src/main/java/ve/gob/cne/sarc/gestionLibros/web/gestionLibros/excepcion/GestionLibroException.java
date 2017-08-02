package ve.gob.cne.sarc.gestionLibros.web.gestionLibros.excepcion;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * GestionLibroException.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 21, 2016
 * @author Anderson Delgado
 */
/**
 * GestionLibroException.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 21, 2016
 * @author Anderson Delgado
 */
public class GestionLibroException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String errCode;
    private String errMsg;

    /**
     * @param errCode
     */
    public GestionLibroException(String errCode) {
        this.errCode = errCode;
        this.errMsg = "";
    }

    /**
     * @return
     */
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg
     */
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     * @param archivo
     * @param code
     * @throws IOException
     */
    public void getMessage(String archivo, String code) throws IOException {
        this.setErrMsg(getMessageS(archivo, code));

    }

    /**
     * @param archivo
     * @param code
     * @return
     * @throws IOException
     */
    public String getMessageS(String archivo, String code) throws IOException {
        try {
            Properties propiedades = new Properties();
            InputStream entrada;
            entrada = new FileInputStream(archivo);
            propiedades.load(entrada);
            return propiedades.getProperty(code);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

}