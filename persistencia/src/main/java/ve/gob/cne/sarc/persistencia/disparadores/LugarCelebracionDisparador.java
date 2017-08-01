/**
 *@LugarCelebracionDisparador.java
 * @version 1.0
 * 22/08/2016
 * Copyright
 */
package ve.gob.cne.sarc.persistencia.disparadores;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import ve.gob.cne.sarc.persistencia.entidades.LugarCelebracionEntidad;



/**
 * LugarCelebracionDisparador.java
 * @descripcion Define el disparador de la entidad del lugar de la celebracion
 * @version 1.0
 * 22/08/2016
 * @author georman.calderon
 */
public class LugarCelebracionDisparador {
    
    
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
    public void validacionesLugarCelebracionEntidad(LugarCelebracionEntidad lugarCelebracion) throws ExceptionDisparador {
        validarCodigoNulo(lugarCelebracion);
        validarFechaInicioNulo(lugarCelebracion);
        validarDescripcion(lugarCelebracion);
    }

    private void validarDescripcion(LugarCelebracionEntidad lugarCelebracion) throws ExceptionDisparador {
        if (lugarCelebracion.getDescripcion() == null) {
            throw new ExceptionDisparador("2#Debe introducir la descripcion del lugar de celebracion");
        }
        
    }

    private void validarFechaInicioNulo(LugarCelebracionEntidad lugarCelebracion) throws ExceptionDisparador {
        if (lugarCelebracion.getFechaInicio() == null) {
            throw new ExceptionDisparador("2#Debe introducir la fecha de inicio del registro");
        }
        
    }

    private void validarCodigoNulo(LugarCelebracionEntidad lugarCelebracion) throws ExceptionDisparador {
        if (lugarCelebracion.getCodigo() == null) {
            throw new ExceptionDisparador("2#Debe introducir el código correspondiente al lugar de celebracion");
        }
        
        
    }

}
