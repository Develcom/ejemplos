package ve.gob.cne.sarc.ciudadano.core.ciudadano.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import ve.gob.cne.sarc.comunes.catalogo.TipoDireccion;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.Ciudadano;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.ciudadano.Ecu;
import ve.gob.cne.sarc.comunes.ciudadano.Nui;
import ve.gob.cne.sarc.comunes.direccion.Direccion;
import ve.gob.cne.sarc.persistencia.entidades.CiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.DireccionCiudadanoEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EcuEntidad;
import ve.gob.cne.sarc.persistencia.entidades.EstadoEcuEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.DireccionCiudadanoRepository;
import ve.gob.cne.sarc.persistencia.repositorios.EcuRepository;


/**
 * CiudadanoMapper.java
 *
 * @descripcion [Clase mapper para realizar el mapeo entre Las clase del modelo ontologico y las clase del modelo de
 * datos.]
 * @version 1.0 11/11/2015
 * @author Oscar Eubieda
 */
@Mapper(componentModel = "spring")
public abstract class CiudadanoMapper {

    @Autowired
    private DireccionCiudadanoRepository direccionCiudadanoRepository;

    @Autowired
    private EcuRepository ecuRepository;


    /**
     *
     * Metodo de mapeo de los objetos CiudadanoEntidad y Ciudadano.
     *
     * @param ciudadanoEntidad objeto con la informacion de la entidad Ciudadano.
     * @return Ciudadano objeto del modelo ontologico que contiene la informacion del Ciudadano
     */
    public Ciudadano entityToBO(CiudadanoEntidad ciudadanoEntidad) {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo(ciudadanoEntidad.getTipoDocIdentificacion());
        tipoDocumento.setNombre(this.obtenerTipoDocumento(ciudadanoEntidad.getTipoDocIdentificacion()));
        Ciudadano ciudadano = new Ciudadano();
        ciudadano.setPrimerNombre(ciudadanoEntidad.getPrimerNombre());
        ciudadano.setSegundoNombre(ciudadanoEntidad.getSegundoNombre());
        ciudadano.setPrimerApellido(ciudadanoEntidad.getPrimerApellido());
        ciudadano.setSegundoApellido(ciudadanoEntidad.getSegundoApellido());

        DocumentoIdentidad documentoIdentidad = new DocumentoIdentidad();
        documentoIdentidad.setTipoDocumento(tipoDocumento);
        documentoIdentidad.setNumero(ciudadanoEntidad.getNumeroDocIdentidad());

        List<DocumentoIdentidad> documentosIdentidad = new ArrayList<>();
        documentosIdentidad.add(documentoIdentidad);

        /* Buscar el Ecu del Ciudadano */
        EcuEntidad ecuEntidad;
        ecuEntidad = consultarEcuCiudadano(ciudadanoEntidad.getNumeroDocIdentidad());
        if (ecuEntidad != null) {
            List<Nui> listaDeNui = null;
      
            Ecu ecuCiudadano = new Ecu();
            ecuCiudadano.setFechaCreacion(ecuEntidad.getFechaCreacion());
            ecuCiudadano.setListaNui(listaDeNui);
            
            
            EstadoEcuEntidad estadoEcuEntidad =ecuEntidad.getEstadoEcu();
            ecuCiudadano.setEstatus(estadoEcuEntidad.getNombreEstadoEcu());
            ciudadano.setEcu(ecuCiudadano);

         }

        ciudadano.setDocumentoIdentidad(documentosIdentidad);
        ciudadano.setNacionalidad(ciudadanoEntidad.getNacionalidad().getNombre());
        ciudadano.setFechaNacimiento(ciudadanoEntidad.getFechaNacimiento());

        Map<String, String> hmESexo = new HashMap<>();
        hmESexo.put("M", "MASCULINO");
        hmESexo.put("F", "FEMENINO");
        ciudadano.setSexo(hmESexo.get(ciudadanoEntidad.getSexo()));

        Map<String, String> hmEstadoCivil = new HashMap<>();
        hmEstadoCivil.put("S", "SOLTERO");
        hmEstadoCivil.put("C", "CASADO");
        hmEstadoCivil.put("V", "VIUDO");
        hmEstadoCivil.put("D", "DIVORCIADO");
        ciudadano.setEstadoCivil(hmEstadoCivil.get(ciudadanoEntidad.getEstatusCivil()));

        Map<String, String> hmCondicionNacionalidad = new HashMap<>();
        hmCondicionNacionalidad.put("NAC", "NACIMIENTO");
        hmCondicionNacionalidad.put("NAT", "NATURALIZACION");
        ciudadano.setCondicionNacionalidad(hmCondicionNacionalidad.get(ciudadanoEntidad.getCondicionNacionalidad()));

        Map<String, String> hmUEH = new HashMap<>();
        hmUEH.put("1", "SI");
        hmUEH.put("0", "NO");
        ciudadano.setUEHRegistrada(hmUEH.get(ciudadanoEntidad.getIndicadorUEH()));

        ciudadano.setComunidadIndigena(ciudadanoEntidad.getComunidadIndigena().getNombre());
        ciudadano.setEstadoCiudadano(ciudadanoEntidad.getEstatusCiudadano().getNombre());
        ciudadano.setFechaResidencia(ciudadanoEntidad.getFechaResidencia());
        ciudadano.setNumeroHijosRegistrados(ciudadanoEntidad.getNumeroHijos());

        // Agregando Direccion de Nacimiento
        Direccion direccionNacimiento = new Direccion();
        direccionNacimiento.setUbicacion(ciudadanoEntidad.getDireccionNacimiento());
        TipoDireccion tipoDireccionNacimiento = new TipoDireccion();
        tipoDireccionNacimiento.setCodigo("NAC");
        tipoDireccionNacimiento.setNombre("DIRECCION DE NACIMIENTO");
        tipoDireccionNacimiento.setDescripcion("NACIMIENTO");
        direccionNacimiento.setTipoDireccion(tipoDireccionNacimiento);
        List<Direccion> direccionOntologia = new ArrayList<>();
        direccionOntologia.add(direccionNacimiento);

        // Agregando Direccion de Residencia
        DireccionCiudadanoEntidad direccionCiudadanoEntidad;
        direccionCiudadanoEntidad = direccionCiudadanoRepository.findByCiudadano_Id(ciudadanoEntidad.getId());

        if (direccionCiudadanoEntidad != null) {
            Direccion direccionResidencia = new Direccion();
            direccionResidencia.setUbicacion(direccionCiudadanoEntidad.getDetalle());
            TipoDireccion tipoDireccionResidencia = new TipoDireccion();
            tipoDireccionResidencia.setCodigo("RES");
            tipoDireccionResidencia.setNombre("RESIDENCIA");
            tipoDireccionResidencia.setDescripcion("RESIDENCIA");
            direccionResidencia.setTipoDireccion(tipoDireccionResidencia);
            direccionOntologia.add(direccionResidencia);
        }

        ciudadano.setDireccion(direccionOntologia);

        return ciudadano;
    }

    /**
     *
     * Metodo que busca la descripcion del tipo de documento de identificacion
     * del ciudadano.
     *
     * @param indicadorTipoDocumento string con el codigo del tipo de documento
     * de identificacion.
     * @return String que contiene el nombre del tipo de documento de
     * identificacion.
     */
    private String obtenerTipoDocumento(String indicadorTipoDocumento) {
        String nombreTipoDocumento;

        switch (indicadorTipoDocumento) {
            case "CED":
                nombreTipoDocumento = "CÉDULA";
                break;
            case "NUI":
                nombreTipoDocumento = "NUI (NÚMERO ÚNICO DE IDENTIDAD)";
                break;
            case "PAS":
                nombreTipoDocumento = "PASAPORTE";
                break;
            default:
                nombreTipoDocumento = "Codigo documento invalido";
                break;
        }

        return nombreTipoDocumento;
    }

    /**
     *
     * Metodo que permite consultar el ECU de una Ciudadano segun el numero de
     * documento de identidad.
     *
     * @param numeroDocumento String que contiene el numero de documento de
     * identidad
     * @return EcuEntidad objeto con la informacion de la entidad ECU.
     */
    private EcuEntidad consultarEcuCiudadano(String numeroDocumento) {
        return ecuRepository.findByNumeroDocumento(numeroDocumento);
    }

    
   

   

}
