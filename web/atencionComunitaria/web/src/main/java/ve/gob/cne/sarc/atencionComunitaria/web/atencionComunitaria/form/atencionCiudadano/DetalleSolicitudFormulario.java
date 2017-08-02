package ve.gob.cne.sarc.atencionComunitaria.web.atencionComunitaria.form.atencionCiudadano;

import java.util.Date;

import ve.gob.cne.sarc.generales.util.formatDate;


public class DetalleSolicitudFormulario {
	
	private String numeroSolicitud;
	private String nombreCiudadano;
	private String apellidoCiudadano;
	private String tipoDocumento;
	private String nroDocumento;
	private String tipoTramite;
	private String moduloRegistro;
	private Date fchaInicio;
	private Date fchaFin;
	private String estatusSolicitud;
	private String nroOficio;
	private String nombreOrgano;
	private String fchOficio;
	private String oficina;
	
	public String getOficina() {
		return oficina;
	}
	public void setOficina(String oficina) {
		this.oficina = oficina;
	}
	public String getNumeroSolicitud() {
		return numeroSolicitud;
	}
	public void setNumeroSolicitud(String numeroSolicitud) {
		this.numeroSolicitud = numeroSolicitud;
	}
	public String getNombreCiudadano() {
		return nombreCiudadano;
	}
	public void setNombreCiudadano(String nombreCiudadano) {
		this.nombreCiudadano = nombreCiudadano;
	}
	public String getApellidoCiudadano() {
		return apellidoCiudadano;
	}
	public void setApellidoCiudadano(String apellidoCiudadano) {
		this.apellidoCiudadano = apellidoCiudadano;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getModuloRegistro() {
		return moduloRegistro;
	}
	public void setModuloRegistro(String moduloRegistro) {
		this.moduloRegistro = moduloRegistro;
	}
	public String getFchaInicio() {
		if(this.fchaInicio != null){
			formatDate fd = new formatDate();
			return fd.convertDDMMYYY(this.fchaInicio);
		}else{
			return null;
		}
	}
	public void setFchaInicio(Date fchaInicio) {
		this.fchaInicio = fchaInicio;
	}
	public String getFchaFin() {
		if(this.fchaFin != null){
			formatDate fd = new formatDate();
			return fd.convertDDMMYYY(this.fchaFin);
		}else{
			return null;
		}
	}
	public void setFchaFin(Date fchaFin) {
		this.fchaFin = fchaFin;
	}
	public String getEstatusSolicitud() {
		return estatusSolicitud;
	}
	public void setEstatusSolicitud(String estatusSolicitud) {
		this.estatusSolicitud = estatusSolicitud;
	}
	public String getNroOficio() {
		return nroOficio;
	}
	public void setNroOficio(String nroOficio) {
		this.nroOficio = nroOficio;
	}
	public String getNombreOrgano() {
		return nombreOrgano;
	}
	public void setNombreOrgano(String nombreOrgano) {
		this.nombreOrgano = nombreOrgano;
	}
	public String getFchOficio() {
		return fchOficio;
	}
	public void setFchOficio(String fchOficio) {
		this.fchOficio = fchOficio;
	}
}
