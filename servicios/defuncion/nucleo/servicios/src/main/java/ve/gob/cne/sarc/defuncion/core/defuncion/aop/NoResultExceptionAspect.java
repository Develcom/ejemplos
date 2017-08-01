package ve.gob.cne.sarc.defuncion.core.defuncion.aop;

import javax.persistence.NoResultException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.defuncion.core.defuncion.exception.ResourceNotFoundException;

/**
 * NoResultExceptionAspect.java
 * @descripcion [Aspecto para capturar excepciones de tipo {@link NoResultException} de los
 * controladores para encapsularlas como {@link ResourceNotFoundException}]
 * @version 1.0 20/7/2016
 * @author Elvin.Gonzalez
 */
@Aspect
@Component
public class NoResultExceptionAspect {

    /**
     * Envia la excepcion caturada
     * @param joinPoint Objeto de tipo JoinPoint
     * @param e Objeto de tipo NoResultException
     */
    @AfterThrowing(pointcut = "execution(* ve.gob.cne.sarc.defuncion.core.defuncion.controllers.*.**(..))", 
            throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, NoResultException e) {
        throw new ResourceNotFoundException("The resource does not exists", e);
    }
}
