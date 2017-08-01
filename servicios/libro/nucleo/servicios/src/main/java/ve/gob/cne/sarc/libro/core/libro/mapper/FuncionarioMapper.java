package ve.gob.cne.sarc.libro.core.libro.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import ve.gob.cne.sarc.comunes.catalogo.TipoDocumento;
import ve.gob.cne.sarc.comunes.ciudadano.DocumentoIdentidad;
import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;

/**
 * FuncionarioMapper.java
 * @descripcion {@link Mapper} usado para convertir instancias de entidad en instancias de pojo.
 * @version 1.0 11/5/2016
 * @author Anabell De Faria
 */
@Mapper(componentModel = "spring", uses = { NacionalidadMapper.class })
public abstract class FuncionarioMapper {

    /**
     * Convierte una entidad en un pojo
     * @param funcionarioEntidad Objeto de entidad de tipo FuncionarioEntidad
     * @return Objeto de tipo Funcionario 
     */
    @Mappings({ @Mapping(ignore = true, target = "acta"),
            @Mapping(ignore = true, target = "direccion"),
            @Mapping(ignore = true, target = "direccionesElectronica"),
            @Mapping(ignore = true, target = "documentoIdentidad"),
            @Mapping(ignore = true, target = "telefonos"),
            @Mapping(ignore = true, target = "tipoFuncionario") })
    public Funcionario entityToBusiness(FuncionarioEntidad funcionarioEntidad) {

        Funcionario funcionario = new Funcionario();
        TipoDocumento tipoDocumento = new TipoDocumento();
        DocumentoIdentidad documento = new DocumentoIdentidad();
        List<DocumentoIdentidad> documentos = new ArrayList<>();

        if (funcionarioEntidad != null) {
            tipoDocumento.setCodigo(funcionarioEntidad
                    .getIndicadorTipoDocumento());
            documento.setTipoDocumento(tipoDocumento);
            documento.setNumero(funcionarioEntidad.getCedula());
            documentos.add(documento);

            funcionario.setPrimerNombre(funcionarioEntidad.getPrimerNombre());
            funcionario.setSegundoNombre(funcionarioEntidad.getSegundoNombre());
            funcionario.setPrimerApellido(funcionarioEntidad
                    .getPrimerApellido());
            funcionario.setSegundoApellido(funcionarioEntidad
                    .getSegundoApellido());
            funcionario.setDocumentoIdentidad(documentos);
        }
        return funcionario;
    }

    /**
     * Convierte un pojo en una entidad
     * @param funcionario Objeto de tipo Funcionario
     * @return Objeto entidad de tipo FuncionarioEntidad
     */
    public FuncionarioEntidad businessToEntity(Funcionario funcionario) {
        FuncionarioEntidad funcionarioEntidad = new FuncionarioEntidad();

        if (funcionario != null && funcionario.getDocumentoIdentidad() != null) {
            funcionarioEntidad.setCedula(funcionario.getDocumentoIdentidad()
                    .get(0).getNumero());
            funcionarioEntidad.setIndicadorTipoDocumento(funcionario
                    .getDocumentoIdentidad().get(0).getTipoDocumento()
                    .getCodigo());
            funcionarioEntidad.setPrimerNombre(funcionario.getPrimerNombre());
            funcionarioEntidad.setSegundoNombre(funcionario.getSegundoNombre());
            funcionarioEntidad.setPrimerApellido(funcionario
                    .getPrimerApellido());
            funcionarioEntidad.setSegundoApellido(funcionario
                    .getSegundoApellido());
        }
        return funcionarioEntidad;
    }

}