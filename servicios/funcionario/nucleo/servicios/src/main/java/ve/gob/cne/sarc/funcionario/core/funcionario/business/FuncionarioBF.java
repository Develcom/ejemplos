package ve.gob.cne.sarc.funcionario.core.funcionario.business;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;

/**
 * FuncionarioBF.java
 * @descripcion [BusinessFacade con la logica de negocio de manejo de Funcionario]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
public interface FuncionarioBF {

    /**
     * Busca un funcionario por su login
     *
     * @param login String que contiene la informacion de login
     * @return Funcionario pojo que contiene la informacion de Funcionario
     * @throws java.lang.Exception
     */
    Funcionario buscarPorLogin(String login) throws Exception;

    /**
     * 
     * Busca un funcionario por oficina
     * 
     * @param codOficina String que describe el codigo de la Oficina
     * @param codTipoFuncionario String que describe el codigo del tipo de Funcionario
     * @param codEstatusFuncionario String que describe el codigo del estatus del Funcionario
     * 
     * @return Funcionario pojo que contiene la informacion del Funcionario
     * @throws java.lang.Exception
     */
    Funcionario buscarFuncionarioPorOficina(String codOficina, String codTipoFuncionario, String codEstatusFuncionario) 
            throws Exception;
    
}
