package ve.gob.cne.sarc.defuncion.core.defuncion.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoDocIdentidadEntidad;

/**
 * ParticipanteMapper.java
 * @descripcion [{@link Mapper} usado para convertir instancias de entidad en
 * instancias de pojo]
 * @version 1.0 20/7/2016
 * @author Erick Escalona
 */
@Mapper(componentModel = "spring")
public abstract class ParticipanteMapper {

    /**
     * Metodo que convierte una lista de entidad Participante a una lista de pojo
     * 
     * @param participanteEntidad List<ParticipanteEntidad> lista de entidad
     * @return Lista de pojo Participante
     */
    abstract List<Participante> entitysToBos(List<ParticipanteEntidad> participanteEntidad);

    /**
     * Convertir una entidad a un pojo
     * @param participanteEntidad ParticipanteEntidad entidad con la informacion del participante
     * @return Pojo Participante
     */
    public Participante entityToBo(ParticipanteEntidad participanteEntidad) {

        if (participanteEntidad == null) {
            return null;
        }

        Participante participante = new Participante();

        participante.setOcupacion(participanteEntidadOcupacionDescripcion(participanteEntidad));
        participante.setNacionalidad(participanteEntidad.getNacionalidad().getNombre());
//        participante.setNacionalidad(participanteEntidadNacionalidadCodigo(participanteEntidad));
        participante.setEstadoCivil(participanteEntidad.getEstadoCivil());
        if (participanteEntidad.getCondicionNacimiento() != null) {
            participante.setCondicionNacimiento(String.valueOf(participanteEntidad.getCondicionNacimiento()));
        }
        participante.setFechaNacimiento(participanteEntidad.getFechaNacimiento());
        participante.setPrimerApellido(participanteEntidad.getPrimerApellido());
        participante.setPrimerNombre(participanteEntidad.getPrimerNombre());
        participante.setSegundoApellido(participanteEntidad.getSegundoApellido());
        participante.setSegundoNombre(participanteEntidad.getSegundoNombre());
        participante.setSexo(participanteEntidad.getSexo());
        if (participanteEntidad.getTiempoRelacion() != null) {
            participante.setTiempoRelacion(Integer.parseInt(participanteEntidad.getTiempoRelacion()));
        }
        participante.setObservacion(participanteEntidad.getObservacion());
        participante.setTipoDocumento(participanteEntidad.getTipoDocumento());
        participante.setLugarNacimiento(participanteEntidad.getLugarNacimiento());

        participante.setDocumentoIdentidad(docIdentidadToTipoDocIdentidadEntidad(participanteEntidad));

        return participante;
    }

    /**
     * Metodo para convertir una entidad en una lista de pojo DocumentoIdentidad
     * 
     * @param entidad ParticipanteEntidad entidad con la informacion del participante
     * @return Lista de Objetos de tipo DocumentoIdentidad
     */
    private List<DocumentoIdentidad> docIdentidadToTipoDocIdentidadEntidad(ParticipanteEntidad entidad) {

        Participante participante = new Participante();

        List<DocumentoIdentidad> listDI = new ArrayList<>();
        DocumentoIdentidad di = new DocumentoIdentidad();
        TipoDocumento td = null;
        TipoDocIdentidadEntidad tdi;

        if (entidad == null) {
            return new ArrayList<>();
        }

        tdi = entidad.getTipoDocumentoIdentidad();
        if (entidad.getNumeroDocIdentidad() == null && tdi == null) {
            return new ArrayList<>();
        }
        di.setNumero(entidad.getNumeroDocIdentidad());

        if (tdi != null) {
            td = new TipoDocumento();
//            td.setCodigo(entidad.getTipoDocumentoIdentidad().getCodigoTipoDocIdentidad());
            td.setDescripcion(entidad.getTipoDocumentoIdentidad().getDescripcion());
            td.setNombre(entidad.getTipoDocumentoIdentidad().getNombreTipoDocIdentidad());

        }
        if (td != null) {
            di.setTipoDocumento(td);
        }
        listDI.add(di);

        participante.setDocumentoIdentidad(listDI);

        return listDI;
    }

    /**
     * Metodo que busca el codigo de la nacionalidad dado la entidad del participante
     * 
     * @param participanteEntidad ParticipanteEntidad entidad con la informacion del participante
     * @return Codigo de la nacionalidad del participante
     */
//    private String participanteEntidadNacionalidadCodigo(ParticipanteEntidad participanteEntidad) {
//
//        if (participanteEntidad == null) {
//            return null;
//        }
//        NacionalidadEntidad nacionalidad = participanteEntidad.getNacionalidad();
//        if (nacionalidad == null) {
//            return null;
//        }
//        String codigo = nacionalidad.getCodigo();
//        if (codigo == null) {
//            return null;
//        }
//        return codigo;
//    }

    /**
     * Metodo que busca la descripcion de la ocupacion dado la entidad del participante
     * 
     * @param participanteEntidad ParticipanteEntidad entidad que contiene la informacion del participante
     * @return Descripcion de la ocupacion del participante
     */
    private String participanteEntidadOcupacionDescripcion(ParticipanteEntidad participanteEntidad) {

        if (participanteEntidad == null) {
            return null;
        }
        OcupacionEntidad ocupacion = participanteEntidad.getOcupacion();
        
        if (ocupacion == null) {
            return null;
        }
        String decripcion = ocupacion.getDescripcion();
        if (decripcion == null) {
            return null;
        }
        return decripcion;
    }

    /**
     * Metodo que convierte una lista de pojo Participante a una lista de entidad
     * 
     * @param participante List<Participante> Lista de pojo participante
     * @return Lista de Objeto de tipo ParticipanteEntidad
     */
    abstract List<ParticipanteEntidad> bosToEntitys(List<Participante> participante);

    /**
     * Convertir un pojo a una entidad
     * @param participante Participante pojo con la informacion del participante
     * @return Objeto de tipo entidad
     */
    public ParticipanteEntidad boToEntity(Participante participante) {
        if (participante == null) {
            return null;
        }

        ParticipanteEntidad participanteEntidad = new ParticipanteEntidad();
        
        OcupacionEntidad ocupacion = new OcupacionEntidad();
        
        if(participante.getOcupacion() != null){
            ocupacion.setDescripcion(participante.getOcupacion());
            participanteEntidad.setOcupacion(ocupacion);
        }

        participanteEntidad = tipoDocIdentidadEntidadToDocIdentidad(participante.getDocumentoIdentidad());

        participanteEntidad.setPrimerApellido(participante.getPrimerApellido());
        participanteEntidad.setSegundoApellido(participante.getSegundoApellido());
        participanteEntidad.setPrimerNombre(participante.getPrimerNombre());
        participanteEntidad.setSegundoNombre(participante.getSegundoNombre());
        participanteEntidad.setTipoDocumento(participante.getTipoDocumento());
        participanteEntidad.setSexo(participante.getSexo());
        participanteEntidad.setTiempoRelacion(String.valueOf(participante.getTiempoRelacion()));
        participanteEntidad.setFechaNacimiento(participante.getFechaNacimiento());
        participanteEntidad.setEstadoCivil(participante.getEstadoCivil());
        if (participante.getCondicionNacimiento() != null) {
            participanteEntidad.setCondicionNacimiento(Integer.parseInt(participante.getCondicionNacimiento()));
        }
        participanteEntidad.setObservacion(participante.getObservacion());
        participanteEntidad.setLugarNacimiento(participante.getLugarNacimiento());

        return participanteEntidad;
    }

    /**
     * Metodo que convierte una lista pojo DocumentoIdentidad a entidad
     * 
     * @param list List<DocumentoIdentidad> lista de pojo DocumentoIdentidad
     * @return Objeto de tipo ParticipanteEntidad
     */
    private ParticipanteEntidad tipoDocIdentidadEntidadToDocIdentidad(List<DocumentoIdentidad> list) {

        ParticipanteEntidad participanteEntidad = new ParticipanteEntidad();
        TipoDocIdentidadEntidad tdie = new TipoDocIdentidadEntidad();

        for (DocumentoIdentidad di : list) {
            if (di != null) {
                participanteEntidad.setNumeroDocIdentidad(di.getNumero());
//                tdie.setCodigoTipoDocIdentidad(di.getTipoDocumento().getCodigo());
                tdie.setDescripcion(di.getTipoDocumento().getDescripcion());
                tdie.setNombreTipoDocIdentidad(di.getTipoDocumento().getNombre());
                participanteEntidad.setTipoDocumentoIdentidad(tdie);
            }
        }

        return participanteEntidad;

    }
}
