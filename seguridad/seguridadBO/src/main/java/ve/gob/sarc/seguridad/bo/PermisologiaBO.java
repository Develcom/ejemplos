package ve.gob.sarc.seguridad.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * PermisologiaBO.java
 * Descripcion de la clase: Business Obj. para el mapeo y acceso a la tabla permisologia de la BD
 * 11 de ago. de 2016
 * @author Ismayer Hernandez
 */
public class PermisologiaBO implements Serializable {
    
    /**
     * Variable manejadas en la tabla C067T_PERMISOLGIA
     */
    private static final long serialVersionUID = -2971808371876162204L;
    private Long coPermisologia;
    private ModuloBO coModulo;
    private RecursoBO coRecurso;
    private TipoPermisoBO coPermiso;
    private RolBO coRol;
    private Long nivelRestriccion;
    private Date feInicio;
    private Date feFin;

    public PermisologiaBO() {
        super();
    }

    public PermisologiaBO(Long coPermisologia, ModuloBO coModulo, RecursoBO coRecurso, TipoPermisoBO coPermiso,
            RolBO coRol, Long nivelRestriccion, Date feInicio, Date feFin) {
        super();
        this.coPermisologia = coPermisologia;
        this.coModulo = coModulo;
        this.coRecurso = coRecurso;
        this.coPermiso = coPermiso;
        this.coRol = coRol;
        this.nivelRestriccion = nivelRestriccion;
        this.feInicio = feInicio;
        this.feFin = feFin;
    }

    public Long getCoPermisologia() {
        return coPermisologia;
    }

    public void setCoPermisologia(Long coPermisologia) {
        this.coPermisologia = coPermisologia;
    }

    public ModuloBO getCoModulo() {
        return coModulo;
    }

    public void setCoModulo(ModuloBO coModulo) {
        this.coModulo = coModulo;
    }

    public RecursoBO getCoRecurso() {
        return coRecurso;
    }

    public void setCoRecurso(RecursoBO coRecurso) {
        this.coRecurso = coRecurso;
    }

    public TipoPermisoBO getCoPermiso() {
        return coPermiso;
    }

    public void setCoPermiso(TipoPermisoBO coPermiso) {
        this.coPermiso = coPermiso;
    }

    public RolBO getCoRol() {
        return coRol;
    }

    public void setCoRol(RolBO coRol) {
        this.coRol = coRol;
    }

    public Long getNivelRestriccion() {
        return nivelRestriccion;
    }

    public void setNivelRestriccion(Long nivelRestriccion) {
        this.nivelRestriccion = nivelRestriccion;
    }

    public Date getFeInicio() {
        return feInicio;
    }

    public void setFeInicio(Date feInicio) {
        this.feInicio = feInicio;
    }

    public Date getFeFin() {
        return feFin;
    }

    public void setFeFin(Date feFin) {
        this.feFin = feFin;
    }

}
