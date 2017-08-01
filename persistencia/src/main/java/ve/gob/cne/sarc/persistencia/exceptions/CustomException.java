package ve.gob.cne.sarc.persistencia.exceptions;

/**
 * @author HiSoft
 */
public class CustomException extends Exception {

    /**
     * @author HiSoft
     */
    public enum CustomExceptionMessages {
        BITACORA_NO_ENCONTRADA("La Bitacora Solicitada no ha sido encontrada"),
        BITACORA_INVALIDA("La Bitacora Solicitada es invalida"),
        SERVER_DIR_PATH_INVALIDO("La ruta especificada no es un directorio valido"),
        SERVER_HASH_INVALIDO("El hash especificado para el paquete es invalido"),
        TAREA_ESTRUCTURA_INVALIDA("La tarea especificada no tiene una estructura valida");

        private String msg;

        private CustomExceptionMessages(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return this.msg;
        }
    }

    /**
     * Conjunto de excepciones manejadas para bitacoras
     */
    private static final long serialVersionUID = 1L;

    public CustomException() {
        // nothing to do
    }

    /**
     * @param msg
     */
    public CustomException(String msg) {
        super(msg);
    }

    /**
     * @param th
     */
    public CustomException(Throwable th) {
        super(th);
    }

    /**
     * @param msg
     * @param th
     */
    public CustomException(String msg, Throwable th) {
        super(msg, th);
    }

    /**
     * @param msg
     * @param th
     * @param enSup
     * @param wStack
     */
    public CustomException(String msg, Throwable th, boolean enSup, boolean wStack) {
        super(msg, th, enSup, wStack);
    }

    /**
     * @param msgEnum
     */
    public CustomException(CustomExceptionMessages msgEnum) {
        super(msgEnum.getMsg());
    }
}

