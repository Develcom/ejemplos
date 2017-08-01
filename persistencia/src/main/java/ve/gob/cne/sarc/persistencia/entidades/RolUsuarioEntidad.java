package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ve.gob.cne.sarc.persistencia.disparadores.RolUsuarioDisparador;

/**
 * RolUsuarioEntidad.java
 *
 * @author Oscar Montilla
 * @version 1.0 08 de jun. de 2016
 * @descripccion Se crea la clase RolUsuarioEntidad donde se Realizan los Query de consulta de cada metodo
 * @author Oscar Montilla
 * @version 1.0 07/09/2016
 */
@Entity
@Table(name = "X020T_ROL_USUARIO")
@EntityListeners({RolUsuarioDisparador.class})

@NamedQueries({
        @NamedQuery(name = RolUsuarioEntidad.BUSCAR_ROL_POR_NOMBRE_FUNCIO, query =
                "SELECT rol FROM  RolUsuarioEntidad rol WHERE rol.usuario.correoElectronico = :nombre")

})

public class RolUsuarioEntidad implements Serializable {

    public static final String BUSCAR_ROL_POR_NOMBRE_FUNCIO = "RolUsuarioEntidad.buscarIdRolFunc";

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "CO_ROL_USUARIO", nullable = false, length = 22)
    @SequenceGenerator(name = "ROL_USUARIO_SEQ", sequenceName = "X020S_CO_ROL_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_USUARIO_SEQ")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_USUARIO", referencedColumnName = "CO_USUARIO", nullable = false)
    private UsuarioEntidad usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CO_ROL", referencedColumnName = "CO_ROL", nullable = false)
    private RolEntidad rol;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = false)
    @Column(name = "FE_INICIO", nullable = false)
    private Date fechaInicio;
    
    @Temporal(TemporalType.DATE)
    @Basic(optional = true)
    @Column(name = "FE_FIN", nullable = true)
    private Date fechaFin;

    public RolUsuarioEntidad() {
        // Metodo Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsuarioEntidad getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntidad usuario) {
        this.usuario = usuario;
    }

    public RolEntidad getRol() {
        return rol;
    }

    public void setRol(RolEntidad rol) {
        this.rol = rol;
    }

    public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RolUsuarioEntidad that = (RolUsuarioEntidad) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
