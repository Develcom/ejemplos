/**
 *@CitaRepositorioTest.java
 * @version 1.0
 * 25/10/2016
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.persistencia.repositorios.CitaRepository;
import ve.gob.cne.sarc.persistencia.entidades.CitaEntidad;



/**
 * CitaRepositorioTest.java
 * @descripcion clase que permite hacer pruebas de los metodo de consulta
 * del negocios de las citas
 * @version 
 * 25/10/2016
 * @author Georman Calderon
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CitaRepositorioTest {
    
    @Autowired
    private CitaRepository citaRepository;
    
    @Test
    public void TestConsultarCitasRepositorio() throws Exception{
        
        List<CitaEntidad> citas=(List<CitaEntidad>) citaRepository.findAll();
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    
    @Test
    public void TestConsultarCitasPorOficina() throws Exception{
        
        String code="OFN";
        List<CitaEntidad> citas=(List<CitaEntidad>) citaRepository.buscarPorCodigoOficina(code);
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    @Test
    public void TestConsultarCitasPorOficinaYfecha() throws Exception{
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String code="OFN";
        String fe_inicio="16-10-2016 00:00:00";
        Date inicio=sdf.parse(fe_inicio);
        String fe_fin="16-10-2016 23:59:59.000";
        Date fin=sdf.parse(fe_fin);
        List<CitaEntidad> citas=(List<CitaEntidad>) citaRepository.buscarCitas(code, inicio, fin);
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    
    @Test
    public void TestContarCitasPorOficinaYfecha() throws Exception{
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String code="OFN";
        String fe_inicio="16-10-2016 00:00:00";
        Date inicio=sdf.parse(fe_inicio);
        String fe_fin="16-10-2016 23:59:59.000";
        Date fin=sdf.parse(fe_fin);
        Integer citas= citaRepository.contarCitarPorPeriodo(code, "ACT", inicio,fin);
        Assert.assertNotNull(citas);
        Assert.assertTrue(citas>0);
        
    }


}
