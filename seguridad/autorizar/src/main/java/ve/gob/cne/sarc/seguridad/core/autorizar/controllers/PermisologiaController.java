package ve.gob.cne.sarc.seguridad.core.autorizar.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ve.gob.cne.sarc.seguridad.core.autorizar.business.PermisologiaBF;
import ve.gob.sarc.seguridad.bo.EndPointsBO;
import ve.gob.sarc.seguridad.bo.GrupoRecurosBO;

/**
 * Descripcion de la clase:Rest Controller que expone el servico que retorna el
 * recurso a proteger
 *
 * @author Ismayer Hernandez 16 de ago. de 2016
 */
@RestController
@RequestMapping("/seguridad/buscaRecurso")
public class PermisologiaController {

    @Autowired
    private PermisologiaBF permisologiaBF;

    /**
     * Metodo que recibe el codigo del modulo y retorna list con el endPoints a
     * proteger
     *
     * @param codigoModulo
     * @return
     */
    @RequestMapping(value = "/{codigoModulo}", method = RequestMethod.GET)
//    public List<EndPointsBO> permisologia(@PathVariable("codigoModulo") String codigoModulo) {
    public List<GrupoRecurosBO> permisologia(@PathVariable("codigoModulo") String codigoModulo) {

//        List<EndPointsBO> permisologia = new ArrayList<>();
//        permisologia = permisologiaBF.buscaRecurso(codigoModulo);
//        return permisologia;
        List<GrupoRecurosBO> permisologia;
        permisologia = permisologiaBF.buscarRecurso(codigoModulo);
        return permisologia;
    }

}
