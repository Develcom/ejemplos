package ve.gob.cne.sarc.seguridad.core.autorizar.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.persistencia.entidades.TipoPermisoEntidad;
//import ve.gob.cne.sarc.persistencia.repositorios.TipoPermisoRepository;
import ve.gob.cne.sarc.seguridad.core.autorizar.business.TipoPermisoBF;


/**
 * Descripcion de la clase: implement para buscar tipo permiso
 * @author Ismayer Hernandez
 *
 */
@Component
public class TipoPermisoBFImpl implements TipoPermisoBF {

//    @Autowired
//    private TipoPermisoRepository tipoPermisoRepository;
    
    @Override
    public Boolean findByCoPermisoBF(String coPermiso ) {
        
        long l = Long.parseLong(coPermiso);
//        TipoPermisoEntidad entidades = tipoPermisoRepository.findByCoPermiso(l);
//        return validar(entidades);
          return true;
    }    
    
    private Boolean validar(TipoPermisoEntidad entidades) {

        if (entidades == null) {
            return false;
        } else {
            return true;
        }
    }
    

}
