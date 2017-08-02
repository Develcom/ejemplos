package ve.gob.cne.sarc.gestionLibros.web.gestionLibros.utilitario;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utilitario.java
 * 
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version Dec 21, 2016
 * @author Anderson Delgado
 */
public class Utilitario {

    /**
     * 
     */
    private static Properties config;
    private final static Logger LOG = Logger.getLogger(Utilitario.class.getName());

    private Utilitario() {
    }

    /**
     * @param campo
     * @return
     */
    public static boolean esCampoVacio(String campo) {

        return campo == null || campo.isEmpty() ? false : true;

    }

    /**
     * @param primerCampo
     * @param segundoCampo
     * @return
     */
    public static boolean esIgualCampos(String primerCampo, String segundoCampo) {
        boolean exito = false;
        if (primerCampo.equalsIgnoreCase(segundoCampo) && !(esCampoVacio(primerCampo) && esCampoVacio(segundoCampo))) {
            exito = true;
        }
        return exito;
    }

    /**
     * @param path
     * @return
     */
    public static Properties getConfig(String path) {
        if (config == null) {
            config = new Properties();
            try {
                config.load(new FileInputStream(path));
            } catch (IOException e) {
                LOG.error(e);
            }
        }
        return config;
    }

    /**
     * @param parametro
     * @return
     */
    public static String obtenerFechaOrHoraActual(String parametro) {

        String valor = null;
        Date fecha = new Date();
        SimpleDateFormat formateador;

        switch (parametro) {
        case "DIA":
            formateador = new SimpleDateFormat("dd");
            valor = formateador.format(fecha);
            break;
        case "NUM_MES":
            formateador = new SimpleDateFormat("MM");
            valor = formateador.format(fecha);
            break;
        case "ANIO":
            formateador = new SimpleDateFormat("yyyy");
            valor = formateador.format(fecha);
            break;
        case "FECHACOMPLETA":
            formateador = new SimpleDateFormat("dd'-'MM'-'yyyy");
            valor = formateador.format(fecha);
            break;
        case "STRING_MES":
            String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
                    "Octubre", "Noviembre", "Diciembre" };
            formateador = new SimpleDateFormat("MM");
            valor = meses[Integer.parseInt(formateador.format(fecha)) - 1];
            break;
        case "HORA_ACTUAL":
            formateador = new SimpleDateFormat("hh.mm.ss a");
            valor = formateador.format(fecha);
            break;
        default:
            // no encontrado
            break;
        }
        return valor;

    }

    /**
     * @return
     */
    public static String obtenerHoraActual() {

        int hora = 0;
        int minuto = 0;
        int segundo = 0;
        try {

            Calendar fecha = new GregorianCalendar();
            hora = fecha.get(Calendar.HOUR_OF_DAY);
            minuto = fecha.get(Calendar.MINUTE);
            segundo = fecha.get(Calendar.SECOND);

        } catch (Exception e) {
            LOG.error(e);
        }

        return hora + ":" + minuto + "-" + segundo;
    }
}
