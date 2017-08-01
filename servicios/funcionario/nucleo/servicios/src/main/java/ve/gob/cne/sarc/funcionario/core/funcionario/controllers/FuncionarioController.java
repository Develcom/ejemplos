package ve.gob.cne.sarc.funcionario.core.funcionario.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.funcionario.core.funcionario.business.FuncionarioBF;


/**
 * FuncionarioController.java
 * @descripcion [Controlador REST de Funcionario]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    private static final Logger LOG = LoggerFactory.getLogger(FuncionarioController.class);
    private static final String METODO_EJECUCION_CONTROLLER = "Inciando ejecucion del metodo ";
    @Autowired
    private FuncionarioBF funcionarioBF;

    /**
     * 
     * Metodo buscarPorLogin que permite consultar los datos de un funcionario segun su login
     * 
     * @param login String que describe el login
     * @return Funcionario pojo que contiene la informacion del Funcionario
     */
    @RequestMapping(value = "/buscarPorLogin/{login}", method = RequestMethod.GET)
    public Funcionario buscarPorLogin(@PathVariable("login") String login) throws Exception {
        LOG.info(METODO_EJECUCION_CONTROLLER + "buscarPorLogin " + login);
        return funcionarioBF.buscarPorLogin(login);

    }
    /**
     * 
     * Metodo buscarFuncionarioPorOficina que permite consultar los datos de un funcionario segun la oficina
     * 
     * @param codOficina String que describe el codigo de la Oficina
     * @param codTipoFuncionario String que describe el codigo del tipo de Funcionario
     * @param codEstatusFuncionario String que describe el codigo del estatus del Funcionario
     * 
     * @return Funcionario pojo que contiene la informacion del Funcionario
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/buscarFuncionarioPorOficina/{codOficina}/"
            + "{codTipoFuncionario}/{codEstatusFuncionario}", 
            method = RequestMethod.GET)
    public Funcionario buscarFuncionarioPorOficina(@PathVariable("codOficina")
            String codOficina,@PathVariable("codTipoFuncionario") String codTipoFuncionario,
            @PathVariable("codEstatusFuncionario") String codEstatusFuncionario) throws Exception {
        LOG.info(METODO_EJECUCION_CONTROLLER + "buscarFuncionarioPorOficina " 
                + codOficina + codTipoFuncionario + codEstatusFuncionario);
        return funcionarioBF.buscarFuncionarioPorOficina(codOficina,
                codTipoFuncionario,codEstatusFuncionario);

    }
}
