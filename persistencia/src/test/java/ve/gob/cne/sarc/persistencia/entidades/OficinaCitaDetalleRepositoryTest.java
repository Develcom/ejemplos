/**
 *@OficinaCitaDetalleEntidad.java
 * @version 1.0
 * 26/10/2016
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

import ve.gob.cne.sarc.persistencia.repositorios.OficinaCitaDetalleRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaCitaRepository;

/**
 * OficinaCitaDetalleEntidad.java
 * @descripcion [Detalle de la funcionalidad de la Clase]
 * @version 
 * 26/10/2016
 * @author Windows
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class OficinaCitaDetalleRepositoryTest {
    
    
    @Autowired
    private OficinaCitaDetalleRepository repository;
    
    @Test
    public void TestConsultarOficinaCitasDetalleRepositorio() throws Exception{

        List<OficinaCitaDetalleEntidad> citas=(List<OficinaCitaDetalleEntidad>) repository.findAll();
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    
    @Test
    public void TestConsultarOficinaCitasDetallePorOficina() throws Exception{
   
        String codigoOficina="OFN";
        List<OficinaCitaDetalleEntidad> citas=repository.buscarPorCodigoOficina(codigoOficina);
        Assert.assertNotNull(citas);
        Assert.assertTrue(!citas.isEmpty());
        
    }
    
    
    @Test
    public void TestConsultarOficinaCitasDetallePorOficinaYFecha() throws Exception{
   
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy HH:mm:ss");
        String codigoOficina="OFN";
        String fe_inicio="16-10-2016 00:00:00";
        Date inicio=sdf.parse(fe_inicio);
        String fe_fin="16-10-2016 23:59:59.000";
        Date fin=sdf.parse(fe_fin);
        OficinaCitaDetalleEntidad citas=repository.buscarPorCodigoOficinaFecha(codigoOficina, inicio, fin);
        Assert.assertNotNull(citas);
        Assert.assertTrue(citas.getOficinaCita().getOficina().equals(codigoOficina));
        
    }
    

}
