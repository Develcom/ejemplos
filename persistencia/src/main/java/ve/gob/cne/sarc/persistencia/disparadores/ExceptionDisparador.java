package ve.gob.cne.sarc.persistencia.disparadores;

/**
 * <p>
 * Excepcion que representa una recurso no encontrado
 * </P>
 *
 * @author carlos.castillo
 */
public class ExceptionDisparador extends Exception {

    /**
     *
     * @param msg
     */
    public ExceptionDisparador(String msg) {
        super(msg);
    }

    /**
     *
     * @param mensaje
     * @param ex
     */
    public ExceptionDisparador(String mensaje, Exception ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
