package ve.gob.cne.sarc.persistencia.entidades;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the C080T_HUELLAS_REGISTRADAS database table.
 * 
 */
@Entity
@Table(name="C080T_HUELLAS_REGISTRADAS")
@NamedQuery(name="HuellasRegistradaEntidad.findAll", query="SELECT h FROM HuellasRegistradaEntidad h")
public class HuellasRegistradaEntidad implements Serializable  {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="HUELLAS_REGISTRADAS_SEQ", sequenceName="C080S_CO_HUELLAS_REGISTRADAS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="HUELLAS_REGISTRADAS_SEQ")
	@Column(name="CO_HUELLAS")
	private long coHuellas;

	@Lob
	@Column(name="HUELLA_DOS")
	private byte[] huellaDos;

	@Lob
	@Column(name="HUELLA_UNO")
	private byte[] huellaUno;

	@Column(name="ID_CIUDADANO")
	private String idCiudadano;

	@Column(name="ID_HUELLA_DOS")
	private String idHuellaDos;

	@Column(name="ID_HUELLA_UNO")
	private String idHuellaUno;

	@Column(name="ID_SOLICITUD")
	private String idSolicitud;

	public HuellasRegistradaEntidad() {
	}

	public long getCoHuellas() {
		return this.coHuellas;
	}

	public void setCoHuellas(long coHuellas) {
		this.coHuellas = coHuellas;
	}

	public byte[] getHuellaDos() {
		return this.huellaDos;
	}

	public void setHuellaDos(byte[] huellaDos) {
		this.huellaDos = huellaDos;
	}

	public byte[] getHuellaUno() {
		return this.huellaUno;
	}

	public void setHuellaUno(byte[] huellaUno) {
		this.huellaUno = huellaUno;
	}

	public String getIdCiudadano() {
		return this.idCiudadano;
	}

	public void setIdCiudadano(String idCiudadano) {
		this.idCiudadano = idCiudadano;
	}

	public String getIdHuellaDos() {
		return this.idHuellaDos;
	}

	public void setIdHuellaDos(String idHuellaDos) {
		this.idHuellaDos = idHuellaDos;
	}

	public String getIdHuellaUno() {
		return this.idHuellaUno;
	}

	public void setIdHuellaUno(String idHuellaUno) {
		this.idHuellaUno = idHuellaUno;
	}

	public String getIdSolicitud() {
		return this.idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

}
