package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.MotivoTrasladoEntidad;



/**
 * 
 * MotivoTrasladoDisparador.java
 * @descripcion Clase que desarrolla el disparador del motivo de traslado
 * @version 1.0
 * 22/08/2016
 * @author georman.calderon
 */
public class MotivoTrasladoDisparador {
    
    /**
     * 
     * Este método recibe como parámetro la entidad LugarCelebracionEntidad y llama a otros métodos privados para validar los
     * atributos que no puedan ser nulos, vacios o deban cumplir con un formato específico.
     * 
     * @param actaEstatus Objeto del modelo ontologico de Acta estatus 
     * @throws ExceptionDisparador - Excepción para indicar si un dato viene null o vacio.
     */
    @PrePersist
    @PreUpdate
    public void validacionesMotivoTrasladoEntidad(MotivoTrasladoEntidad motivoTraslado) throws ExceptionDisparador {
        validarCodigoNulo(motivoTraslado);
        validarFechaInicioNulo(motivoTraslado);
        validarDescripcion(motivoTraslado);
    }

     private void validarDescripcion(MotivoTrasladoEntidad lugarCelebracion) throws ExceptionDisparador {
        if (lugarCelebracion.getDescripcion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la descripcion del motivo de traslado");
        }
        
    }

    private void validarFechaInicioNulo(MotivoTrasladoEntidad lugarCelebracion) throws ExceptionDisparador {
        if (lugarCelebracion.getFechaInicio() == null) {
            throw new ExceptionDisparador("2#Debe introducir la fecha de inicio del motivo de traslado");
        }
        
    }

    private void validarCodigoNulo(MotivoTrasladoEntidad lugarCelebracion) throws ExceptionDisparador {
        if (lugarCelebracion.getCodigo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el código correspondiente del motivo de traslado");
        }
    }

}
