package ve.gob.cne.sarc.generales.comunes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.excepciones.IncorrectParameter;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;


/**  
 * Descripcion de la clase:
 * Clase de apoyo que permite el acceso a propiededes declaradas en el filesystem,
 * ademas implementa la logica de preparacion de las propiedades que van a ser 
 * expuestas al cliente web.
 * 
 * {@link Class} PropertyHelper
 * @author douglas.palacios *
 */
@Component
@Lazy
public class PropertyHelper implements EscuchaAdministradorPropiedades {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyHelper.class);
    private AdministradorPropiedades propertyManagerWeb;
    private static final String[] IDS_PARCIALES_VALIDOS_WEB = { "sarc.web" };
    private static List<String> idsPropiedades;
    private boolean sincronizado = false;
    private static Map<String, Object> valorPropiedades;
    

    
   
    /**
     * contructor que registra al helper para escuchar actualizacion de propiedades en 
     * filesystem y realiza la primera carga del cache de las propiedades expuestas
     *  al cliente web.
     * 
     * @PropertyHelper.java
     * 
     * @param props Administrador de porpiedades registrado en el IOC spring
     */
    @Autowired
    public PropertyHelper(AdministradorPropiedades props) {
        this.propertyManagerWeb = props;
        try {
            propertyManagerWeb.registrarEscucha(this);
        } catch (IncorrectParameter e) {
            LOGGER.error("PropertyHelper-IncorrectParameter ", e);
        } catch (GenericException e) {
            LOGGER.error("PropertyHelper-GeneralException", e);
        }
        if (!sincronizado) {
            refrescarPropiedades();
        }
    }

    /**
     * 
     * refrescarPropiedades
     * metodo privado que actua como cache para las propiedades que se exponen al cliente web
     * 
     * @since 6/7/2016
     * @return void
     * @author douglas.palacios
     * @version 1.0
     * Jira (Ejemplo: S2RC-001)
     */
    private synchronized  void refrescarPropiedades() {

        LOGGER.info("refrescarPropiedades");
        if (valorPropiedades == null) {
            valorPropiedades = new HashMap<>();
        } else {
            valorPropiedades.clear();
        }

        idsPropiedades = propertyManagerWeb.obtenerListaPropiedades();
        for (String patron : IDS_PARCIALES_VALIDOS_WEB) {
            for (String idPropiedad : idsPropiedades) {
                chequearSetearPropiedad(idPropiedad, patron);
            }
        }
        sincronizado = true;
    }
    
    
    private void chequearSetearPropiedad(String propiedad, String patron ){
        if (propiedad.startsWith(patron)) {
            try {
                valorPropiedades.put(propiedad, propertyManagerWeb.getProperty(propiedad));
            } catch (IncorrectParameter e) {
                LOGGER.error("refrescarPropiedades-IncorrectParameter", e);
            } catch (GenericException e) {
                LOGGER.error("refrescarPropiedades-GeneralException", e);
            }
        }
    }

    /**
     * 
     * getPropertyManagerWeb
     * 
     * getter para el componente que maneja las propiedades 
     * 
     * @since 6/7/2016
     * @return
     * @return AdministradorPropiedades
     * @author douglas.palacios
     * @version 1.0
     * Jira (Ejemplo: S2RC-001)
     */
    public AdministradorPropiedades getPropertyManagerWeb() {
        return propertyManagerWeb;
    }

    
    /**
     * 
     * obtenerPropiedades
     * devuelve las propiedades que segun el prefijo de la propiedad debe ser expuesto al cliente web
     * 
     * @since 6/7/2016
     * @return
     * @return Map<String,Object>
     * @author douglas.palacios
     * @version 1.0
     * Jira (Ejemplo: S2RC-001)
     */
    public Map<String, Object> obtenerPropiedades() {

        LOGGER.info("obtenerPropiedades");
        try {
            propertyManagerWeb.getProperty("");
        } catch (IncorrectParameter e) {
            LOGGER.error("obtenerPropiedades-IncorrectParameter", e);
        } catch (GenericException e) {
            LOGGER.error("obtenerPropiedades-GeneralException", e);
        }

        if (!sincronizado) {
            refrescarPropiedades();
        }

        LOGGER.info("obtenerPropiedades out");
        return valorPropiedades;
    }

    /**
     * 
     * consultarPropiedad
     * dado un nombre de propiedad expuesta al cliente web como parametro de busqueda devuelve su valor
     * obtenido del cache del helper
     * 
     * @since 6/7/2016
     * @param propiedad
     * @return
     * @return Map<String,Object>
     * @author douglas.palacios
     * @version 1.0
     * Jira (Ejemplo: S2RC-001)
     */
    public Map<String, Object> consultarPropiedad(String propiedad) {
        Map<String, Object> respuesta = new HashMap<>();
        if (validarAcceso(propiedad)) {
            try {
                respuesta.put(propiedad, propertyManagerWeb.getProperty(propiedad));
            } catch (IncorrectParameter e) {
                LOGGER.error("consultarPropiedad-IncorrectParameter", e);
            } catch (GenericException e) {
                LOGGER.error("consultarPropiedad-GeneralException", e);
            }
        }
        return respuesta;
    }

    /**
     * 
     * validarAcceso
     * dado un string representando una propiedad expuesta al cliente web
     * determina si el prefijo es igual a la propiedad IDS_PARCIALES_VALIDOS_WEB
     * 
     * @since 6/7/2016
     * @param propiedad
     * @return
     * @return boolean
     * @author douglas.palacios
     * @version 1.0
     * Jira (Ejemplo: S2RC-001)
     */
    private boolean validarAcceso(String propiedad) {
        if (propiedad != null && !propiedad.isEmpty()) {
            for (String pathParcial : IDS_PARCIALES_VALIDOS_WEB) {
                if (propiedad.startsWith(pathParcial)) {
                    return true;
                }
            }
        }
        return false;
    }

    
    @Override
    public String changeProperties(String archivo) {

        sincronizado = false;
        LOGGER.info("entre al cambio de archivo " + sincronizado);

        return null;
    }

}