/**
 *@OficinaCitaRepositoryTest.java
 * @version 1.0
 * 26/10/2016
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.entidades;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.persistencia.repositorios.OficinaCitaRepository;

/**
 * OficinaCitaRepositoryTest.java
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version 
 * 26/10/2016
 * @author geormancalderon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OficinaCitaRepositoryTest {
    
    
    
    @Autowired
    private OficinaCitaRepository repository;
    
    @Test
    public void TestConsultarOficinaCitasRepositorio() throws Exception{

        List<OficinaCitaEntidad> citas=(List<OficinaCitaEntidad>) repository.findAll();
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    
    @Test
    public void TestConsultarOficinaCitasRepositorioCodigo() throws Exception{
   
        String codigo="OFR";
        OficinaCitaEntidad citas=repository.buscarPorCodigoOficina(codigo);
        Assert.assertNotNull(citas);
        Assert.assertTrue(citas.getOficina().contains(codigo));
        
    }

}
