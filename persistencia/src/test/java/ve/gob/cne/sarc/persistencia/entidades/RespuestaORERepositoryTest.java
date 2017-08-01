package ve.gob.cne.sarc.persistencia.entidades;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.persistencia.repositorios.RespuestaORERepository;

/**
 * RespuestaORERepositoryTest.java
 * @descripcion Test Cases de la Entidad de Respuesta ORE
 * @version 
 * 28 de nov. de 2016
 * @author Domingo Rondon
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RespuestaORERepositoryTest {
    
    @Autowired
    private RespuestaORERepository repository;
    
    @Test
    public void findAllElementsTest(){
        
        List<RespuestaOREEntidad> respuestaOre = (List<RespuestaOREEntidad>) repository.findAll();

        assertNotNull(respuestaOre);
        
    }

}
