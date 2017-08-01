package ve.gob.cne.sarc.ecu.core.ecu.aop;

import javax.persistence.NoResultException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import ve.gob.cne.sarc.ecu.core.ecu.exception.ResourceNotFoundException;


/**
 * NoResultExceptionAspect.java
 * @descripcion [Aspecto para capturar excepciones de tipo {@link NoResultException} de los
 * controladores para encapsularlas como {@link ResourceNotFoundException}]
 * @version 14/7/2016
 * @author Erick Escalona
 */
@Aspect
@Component
public class NoResultExceptionAspect {

    /**
     * Metodo que envia la excepcion capturada
     * @param joinPoint Objeto de tipo JoinPoint
     * @param e Objeto de tipo NoResultException
     */
    @AfterThrowing(pointcut = "execution(* ve.gob.cne.sarc.ecu.core.ecu.controllers.*.**(..))", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, NoResultException e) {
        throw new ResourceNotFoundException("The resource does not exists", e);
    }
}
