package ve.gob.cne.sarc.acta.core.acta.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.participante.Participante;
import ve.gob.cne.sarc.persistencia.entidades.ParticipanteEntidad;
import ve.gob.cne.sarc.persistencia.entidades.NacionalidadEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OcupacionEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoDocIdentidadEntidad;

/**
 * ParticipanteMapper.java
 *
 * @descripcion [Clase mapper que realiza el mapeo entre las clase del modelo
 * ontologico y las clase del modelo de datos]
 * @version 1.0 13/7/2016
 * @author Elvin.Gonzalez
 */
@Mapper(componentModel = "spring")
public abstract class ParticipanteMapper {

    public static final ParticipanteMapper INSTANCE = Mappers.getMapper(ParticipanteMapper.class);

    /**
     * Metodo abstracto de mapeo de los objetos de tipo lista
     * ParticipanteEntidad y Participante
     *
     * @param participanteEntidad lista de Objeto ParticipanteEntidad
     * @return participante, lista de objeto que contiene la informacion de
     * participantes
     */
    abstract List<Participante> entitysToBos(List<ParticipanteEntidad> participanteEntidad);

    /**
     * Metodo de mapeo de los objetos ParticipanteEntidad y Participante
     *
     * @param participanteEntidad Objeto ParticipanteEntidad
     * @return participante, instancia de objeto que contiene la informacion de
     * participante
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

        participante.setDocumentoIdentidad(docIdentToTipoDocIdentEntidad(participanteEntidad));

        return participante;
    }

    /**
     * Metodo que consulta una lista de documentos de identidad
     *
     * @param entidad Objeto ParticipanteEntidad
     * @return List<DocumentoIdentidad>, lista de objeto que contiene la
     * informacion de documento de identidad
     */
    private List<DocumentoIdentidad> docIdentToTipoDocIdentEntidad(ParticipanteEntidad entidad) {

        Participante participante = new Participante();

        List<DocumentoIdentidad> listDI = new ArrayList<>();
        DocumentoIdentidad di = new DocumentoIdentidad();
        TipoDocumento td = null;
        TipoDocIdentidadEntidad tdi;

        if (entidad == null) {
            return Collections.emptyList();
        }

        tdi = entidad.getTipoDocumentoIdentidad();
        if (entidad.getNumeroDocIdentidad() == null && tdi == null) {
            return Collections.emptyList();
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
     * Metodo que consulta el codigo de la nacionalidad del participante
     *
     * @param participanteEntidad Objeto ParticipanteEntidad
     * @return codigo String que contiene codigo de la nacionalidad
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
     * Metodo que consulta el codigo de la ocupacion del participante
     *
     * @param participanteEntidad Objeto ParticipanteEntidad
     * @return descripcion String que contiene la descripcion de la ocupacion
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
     * Metodo abstracto de mapeo de los objetos de tipo lista
     * ParticipanteEntidad y Participante
     *
     * @param participante, lista de objeto que contiene la informacion de
     * participantes
     * @return participanteEntidad lista de Objeto ParticipanteEntidad
     */
    abstract List<ParticipanteEntidad> bosToEntitys(List<Participante> participante);

    /**
     * Metodo de mapeo de los objetos Participante y ParticipanteEntidad
     *
     * @param participante instancia de objeto que contiene la informacion de
     * Participante
     * @return ParticipanteEntidad Objeto ParticipanteEntidad
     */
    public ParticipanteEntidad boToEntity(Participante participante) {
        if (participante == null) {
            return null;
        }

        ParticipanteEntidad participanteEntidad = new ParticipanteEntidad();

        OcupacionEntidad ocupacion = new OcupacionEntidad();

        if (participante.getOcupacion() != null) {
            ocupacion.setDescripcion(participante.getOcupacion());
            participanteEntidad.setOcupacion(ocupacion);
        }

        participanteEntidad = tipoDocIdentEntidadToDocIdent(participante.getDocumentoIdentidad());

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
     * Metodo que consulta el participante dado una lista de documentos
     *
     * @param list lista de documentos de identidad
     * @return participanteEntidad Objeto ParticipanteEntidad
     *
     */
    private ParticipanteEntidad tipoDocIdentEntidadToDocIdent(List<DocumentoIdentidad> list) {

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
