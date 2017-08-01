package ve.gob.cne.sarc.funcionario.servicio.cliente;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedadesImplementacionApache;
import ve.gob.cne.sarc.utilitarios.propiedades.EscuchaAdministradorPropiedades;
import ve.gob.cne.sarc.utilitarios.web.rest.RestTemplateHelper;

/**
 * FuncionarioServicioCliente.java
 *
 * @descripcion [Clase cliente de los servicios de Funcionario]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Component
public class FuncionarioServicioCliente implements EscuchaAdministradorPropiedades {

    private static final Logger LOG = LoggerFactory.getLogger(FuncionarioServicioCliente.class);
    private String endPointbuscarPorLogin;
    private String endPointbuscarFuncPorOficina;
    private AdministradorPropiedades properties;

    /**
     * Constructor
     */
    public FuncionarioServicioCliente() throws GenericException {
        properties = new AdministradorPropiedadesImplementacionApache("SARC_HOME", 
                "conf/general/config-Manage.properties");
        properties.desRegistrarEscucha(this);
        init();
    }

    /**
     *
     * Método buscarPorLogin que permite buscar el login de funcionario
     *
     *
     * @param login que contiene la informacion del Login
     * @param token permiso de acceso
     * @return Funcionario pojo que contiene la informacion del Funcionario
     */
    public Funcionario buscarPorLogin(String login, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();

        return rt.procesarPeticionSeguridad(
                endPointbuscarPorLogin + login, token,
                null, Funcionario.class, HttpMethod.GET);

      
    }

    /**
     *
     * Método buscarFuncionarioPorOficina que permite buscar el funcionario por
     * Oficina
     *
     *
     * @param codOficina String que contiene el codigo de oficina
     * @param codTipoFuncionario String que contiene el codigo del tipo funcionario
     * @param token permiso de acceso
     * @param codEstatusFuncionario String que contiene el codigo del estatus del funcionario
     * @return Funcionario pojo que contiene la informacion del Funcionario
     */
    public Funcionario buscarFuncionarioPorOficina(String codOficina, 
            String codTipoFuncionario, String codEstatusFuncionario, String token) {

        RestTemplateHelper rt = new RestTemplateHelper();

        return rt.procesarPeticionSeguridad(
                endPointbuscarFuncPorOficina + codOficina + "/" + codTipoFuncionario
                + "/" + codEstatusFuncionario, token,
                null, Funcionario.class, HttpMethod.GET);
    }

    @Override
    public String changeProperties(String archivo) {
        try {
            if ("FuncionarioServicioCliente.properties".equals(archivo)) {
                init();
            }
        } catch (GenericException ex) {
            LOG.error("error al cargar el archivo", ex);
        }
        return null;
    }

    private void init() throws GenericException {
        endPointbuscarPorLogin = properties.getProperty("endPointbuscarPorLogin");
        endPointbuscarFuncPorOficina = properties.getProperty("endPointbuscarFuncPorOficina");
    }
}
