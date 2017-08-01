package ve.gob.cne.sarc.catalogo.core.catalogo.aop;

import javax.persistence.NoResultException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.catalogo.core.catalogo.exception.ResourceNotFoundException;

 
/**
 * NoResultExceptionAspect.java
 * @descripcion Aspecto para capturar excepciones de tipo
 * {@link NoResultException} de los controladores para encapsularlas como
 * {@link ResourceNotFoundException}
 * @version 1.0 11/11/2015
 * @author Elvin.Gonzalez
 */ 
@Aspect
@Component
public class NoResultExceptionAspect {

    /**
     * Metodo que envia la excepcion capturada
     *
     * @param joinPoint Objeto de tipo JoinPoint
     * @param e Objeto de tipo NoResultException
     */
    @AfterThrowing(pointcut = "execution"
            + "(* ve.gob.cne.sarc.catalogo.core.catalogo.controllers.*.**(..))",
            throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, NoResultException e) {
        throw new ResourceNotFoundException("The resource does not exists", e);
    }
}
