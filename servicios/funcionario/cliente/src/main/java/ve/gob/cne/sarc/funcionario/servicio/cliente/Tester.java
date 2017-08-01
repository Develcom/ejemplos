package ve.gob.cne.sarc.funcionario.servicio.cliente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;

/**
 * Tester.java
 * @descripcion [Clase de constante con las claves de las url]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
public class Tester {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tester.class);

    /**
     * Constructor
     */
    private Tester() {
        super();
    }

    /**
     * Pruebas varias
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        FuncionarioServicioCliente cliente = new FuncionarioServicioCliente();
        String login = "func1@cne.com";
        Funcionario funcionario = cliente.buscarPorLogin(login, "bdfed8fb-2335-46ca-b2b5-bf9584d63b5f");
        LOGGER.info("respuesta " + funcionario.getPrimerNombre());
        
        //-------------Test BuscarOficinaFuncionario-------------------//
        LOGGER.info("======Buscando Oficina Funcionario=========");
        Funcionario funcionarioPorOficina=cliente.buscarFuncionarioPorOficina("OBM", "RP", "ACT", "");
        LOGGER.info("respuesta "+ funcionarioPorOficina.getPrimerNombre());
    }

}
