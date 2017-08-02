package ve.gob.cne.sarc.ecu.web.ecu.Exceptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * ECUComunException.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 22, 2016
 * @author Anderson Delgado
 */
public class ECUComunException extends Exception {
    private static final long serialVersionUID = 1L;

    private String errCode;
    private String errMsg;
    private String docIdent;

    /**
     * @param errCode
     * @param errMsg
     * @param docIdent
     */
    public ECUComunException(String errCode, String errMsg, String docIdent) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.docIdent = docIdent;
    }

    public String getDocIdent() {
        return docIdent;
    }

    public void setDocIdent(String docIdent) {
        this.docIdent = docIdent;
    }

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

    /**
     * @param archivo
     * @param code
     */
    public void getMessage(String archivo, String code) {
        try {
            this.setErrMsg(MessageFormat.format(getMessageS(archivo, code), this.getDocIdent()));
        } catch (IOException e) {
            this.setErrMsg(e.getMessage());
        }
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
            InputStream entrada = new FileInputStream(archivo);
            propiedades.load(entrada);
            return propiedades.getProperty(code);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

}
