/**
 *@EstatusCitaRepositoryTest.java
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


import ve.gob.cne.sarc.persistencia.repositorios.EstatusCitaRepository;

/**
 * EstatusCitaRepositoryTest.java
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version 
 * 26/10/2016
 * @author Windows
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class EstatusCitaRepositoryTest {

    @Autowired
    private EstatusCitaRepository citaRepository;
    
    @Test
    public void TestConsultarTodosEstadusCitasRepositorio() throws Exception{
        
     
        List<EstatusCitaEntidad> citas=(List<EstatusCitaEntidad>) citaRepository.findAll();
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    @Test
    public void TestConsultarTodosEstadusCitascodifoPorRepositorio() throws Exception{
        
        String codigo="ACT";
        EstatusCitaEntidad citas=citaRepository.findByCode(codigo);
        Assert.assertNotNull(citas);
        Assert.assertTrue(citas.getCodigo().equals(codigo));
        
    }
    
    
}
