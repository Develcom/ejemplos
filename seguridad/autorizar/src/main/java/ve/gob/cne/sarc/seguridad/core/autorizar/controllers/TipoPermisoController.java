package ve.gob.cne.sarc.seguridad.core.autorizar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.seguridad.core.autorizar.business.PermisologiaBF;
import ve.gob.cne.sarc.seguridad.core.autorizar.business.TipoPermisoBF;

/**
 * Descripcion de la clase: Controller para obtener si tipo de permiso funciona.
 * @author Ismayer Hernandez
 * 11 de ago. de 2016
 */
@RestController
@RequestMapping("/seguridad/tpermiso")
public class TipoPermisoController {

    @Autowired
    private TipoPermisoBF tipoPermisoBF;

    @RequestMapping(value = "/{coPermiso}", method = RequestMethod.GET)
    public Boolean tipoPermiso(@PathVariable("coPermiso") String coPermiso) {
        
        Boolean coPermisoVar = false;
        //coPermisoVar = tipoPermisoBF.findByCoPermiso(coPermiso);

        return coPermisoVar;
    }

}
