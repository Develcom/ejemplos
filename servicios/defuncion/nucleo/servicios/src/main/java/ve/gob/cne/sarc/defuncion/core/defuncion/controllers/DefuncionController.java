package ve.gob.cne.sarc.defuncion.core.defuncion.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.comunes.defuncion.Defuncion;
import ve.gob.cne.sarc.comunes.defuncion.PermisoInhCre;
import ve.gob.cne.sarc.defuncion.core.defuncion.business.DefuncionBF;


/**
 * DefuncionController.java
 * @descripcion [Controlador del servicio REST defuncion]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@RestController
@RequestMapping("/defuncion")
public class DefuncionController {

    private static final Logger LOG = LoggerFactory
            .getLogger(DefuncionController.class);
    private static final String METODO_EJECUCION_CONTROLLER
            = "Iniciando ejecucion del metodo ";
    private static final String FORMATO_FECHA = "dd/MM/yyyy";

    @Autowired
    private DefuncionBF defuncionBF;

    /**
     * Valida si el certificado medico de defuncion existe.
     *
     * @param numeroCertificado long que contiene el numero de certificado
     * medico
     * @return Verdadero si existe en caso contrario falso
     */
    @RequestMapping(value = "/validarCertificadoMedicoDefuncion/{numeroCertificado}",
            method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public boolean validarCertificadoMedicoDefuncion(@PathVariable("numeroCertificado") long numeroCertificado) {

        LOG.debug(METODO_EJECUCION_CONTROLLER + " validarCertificadoMedicoDefuncion");

        return defuncionBF.validarCertificadoMedicoDefuncion(numeroCertificado);
    }

    /**
     * 
     * Metodo guardarPermisoInhCre que permite guardar la informacion de permiso
     * inhumacion y cremacion.
     *
     * @param permisoInhCre objeto del modelo ontologico que contiene la
     * informacion de permiso inhumacion y cremacion
     * @return permiso objeto del modelo ontologico que contiene la informacion
     * de permiso inhumacion y cremacion
     */
    @RequestMapping(value = "/guardarPermisoInhCre", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public PermisoInhCre guardarPermisoInhCre(@RequestBody PermisoInhCre permisoInhCre) {
        LOG.debug(METODO_EJECUCION_CONTROLLER + " guardarPermisoInhCre");
        return defuncionBF.guardarPermisoInhCre(permisoInhCre);
    }

    /**
     * 
     * Metodo que permite consultar un participante dado un numero de solicitud
     * y un codigo tipo de participante
     *
     * @param numeroSolicitud String numero de solicitud
     * @return PermisoInhCre entidad que contiene la informacion de permiso
     * inhumacion y cremacion
     * @throws ParseException
     *
     */
    @RequestMapping(value = "/consultaPermisoInhCre/{numeroSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public PermisoInhCre consultaPermisoInhCre(@PathVariable("numeroSolicitud") String numeroSolicitud)
            throws ParseException {

        LOG.debug(METODO_EJECUCION_CONTROLLER + " consultaPermisoInhCre");

        return defuncionBF.consultaPermisoInhCre(numeroSolicitud);
    }

    /**
     * 
     * Metodo que permite buscar el proximo numero de permiso de inhumacion y
     * cremacion
     *
     * @param codOficina String codigo de la oficina
     * @return variable long contiene el proximo numero de permiso
     */
    @RequestMapping(value = "/buscarProxNumPermiso/{codOficina}",
            method = RequestMethod.GET)
    @ResponseBody
    public long buscarProxNumPermiso(@PathVariable("codOficina") String codOficina) {
        LOG.info("buscarProxNumPermiso -->");
        return defuncionBF.buscarProxNumPermiso(codOficina);
    }

    /**
     * Metodo que permite guardar Defuncion
     *
     * @param defuncion pojo con la informacion de Defuncion
     * @return verdadero si la transaccion fue exitosa, en caso contrario falso
     */
    @RequestMapping(value = "/guardarDefuncion", method
            = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public boolean guardarDefuncion(@RequestBody Defuncion defuncion) {

        LOG.info(METODO_EJECUCION_CONTROLLER + " guardarDefuncion -");

        return defuncionBF.guardarDefuncion(defuncion);
    }
    
    /**
     * Metodo que permite guardar Defuncion
     *
     * @param defuncion pojo con la informacion de Defuncion
     * @return Defuncion  entidad que contiene la informacion de defuncion
     * 
     */    
    @RequestMapping(value = "/consultarDefuncion/{numSolicitud}",
            method = RequestMethod.GET)
    @ResponseBody
    public Defuncion consultarDefuncion(@PathVariable("numSolicitud") String numSolicitud) {

        LOG.info(METODO_EJECUCION_CONTROLLER + " consultarDefuncion");

       return defuncionBF.consultarDefuncion(numSolicitud);
    }


    /**
     *
     * Metodo crearJSON que permite generar un objeto Permiso Inhumacion y
     * Cremacion.
     *
     * @return Permiso Inhumacion y Cremacion objeto del modelo ontologico que
     * contiene la informacion de un Permiso Inhumacion y Cremacion
     */
    @RequestMapping(value = "/crearJSONPermiso", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public PermisoInhCre crearJSONPermiso() {
        PermisoInhCre permisoInhCre = new PermisoInhCre();
        permisoInhCre.setPrimerNombreAutoriza("Maria");
        permisoInhCre.setPrimerApellidoAutoriza("Diaz");
        permisoInhCre.setNombreCementerio("Lagunita");
        permisoInhCre.setNumeroCertificadoDef(894);
        permisoInhCre.setNumSolicitud("201627000003201");
        permisoInhCre.setFechaDefuncion("18/03/2007 10:05:00 AM");
        permisoInhCre.setDireccionDefuncion("Lagunita");
        permisoInhCre.setEstado("MIRANDA");
        permisoInhCre.setMunicipio("ACEVEDO");
        permisoInhCre.setParroquia("PANAQUIRE");
        permisoInhCre.setTipoPermiso("I");
        permisoInhCre.setObservacion("correcto");
        return permisoInhCre;

    }

    /**
     *
     * Metodo crearDefuncion que permite generar un objeto Defuncion
     *
     * @return Defuncion objeto del modelo ontologico que contiene la
     * informacion de una Defuncion
     */
    @RequestMapping(value = "/crearDefuncion", method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseBody
    public Defuncion crearDefuncion() {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Date fechaCertificado = null;
        Date fechaDefuncion = null;
        Defuncion defuncion = new Defuncion();
        defuncion.setEstadoCivil("SOLTERO");
        try {
            fechaCertificado = sdf.parse(sdf.format(new Date()));
            fechaDefuncion = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            LOG.error("ERROR: Defuncion no pudo ser creado" + e.getMessage());
        }
        defuncion.setFechaCertificado(fechaCertificado);
        defuncion.setFechaDefuncion(fechaDefuncion);
        defuncion.setParroquiaDefuncion("EL CARITO");
        defuncion.setMunicipioDefuncion("LIBERTAD");
        defuncion.setNuMPPS(8);
        defuncion.setNumeroCertificado(99);
        defuncion.setPrimerNombreMedico("Sol");
        defuncion.setPrimerApellidoMedico("Freitez");
        defuncion.setSexo("FEMENINO");
        defuncion.setTextoCausa("prueba");
        defuncion.setNumeroActa("20160000120123537431");
        defuncion.setParroquiaDefuncion("EL CARITO");
        defuncion.setMunicipioDefuncion("LIBERTAD");
        defuncion.setEstadoDefuncion("prueba");
        defuncion.setDocIdenMedico("76999");
        defuncion.setTipoDoc("");
        defuncion.setDocumentoIdentConsular("");
        return defuncion;

    }
}
