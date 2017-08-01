package ve.gob.cne.sarc.funcionario.core.funcionario.business.impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ve.gob.cne.sarc.comunes.oficina.Funcionario;
import ve.gob.cne.sarc.comunes.oficina.Oficina;
import ve.gob.cne.sarc.comunes.oficina.Usuario;
import ve.gob.cne.sarc.funcionario.core.funcionario.business.FuncionarioBF;
import ve.gob.cne.sarc.funcionario.core.funcionario.exception.ResourceNotFoundException;
import ve.gob.cne.sarc.funcionario.core.funcionario.mapper.FuncionarioMapper;
import ve.gob.cne.sarc.funcionario.core.funcionario.mapper.FuncionarioOfMapper;
import ve.gob.cne.sarc.funcionario.core.funcionario.mapper.OficinaMapper;
import ve.gob.cne.sarc.funcionario.core.funcionario.mapper.UsuarioMapper;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaFuncionarioEstatusEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolUsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.TipoFuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.FuncionarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaFuncionarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.UsuarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaFuncionarioEstatusRepository;
import ve.gob.cne.sarc.persistencia.repositorios.OficinaRepository;
import ve.gob.cne.sarc.persistencia.repositorios.RolUsuarioRepository;
import ve.gob.cne.sarc.persistencia.repositorios.TipoFuncionarioRepository;
import ve.gob.cne.sarc.utilitarios.excepciones.GenericException;
import ve.gob.cne.sarc.utilitarios.propiedades.AdministradorPropiedades;

/**
 * FuncionarioBFImpl.java
 *
 * @descripcion [Implementacion por defacto de {@link FuncionarioBF}]
 * @version 1.0 21/7/2016
 * @author Anabell De Faria
 */
@Component
public class FuncionarioBFImpl implements FuncionarioBF {

    private static final Logger LOG = LoggerFactory.getLogger(FuncionarioBFImpl.class);

    @Autowired
    public AdministradorPropiedades properties;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolUsuarioRepository rolUsuarioRepository;
    @Autowired
    private OficinaRepository oficinaRepository;
    @Autowired
    private TipoFuncionarioRepository tipoFuncionarioRepository;
    @Autowired
    private OficinaFuncionarioRepository oficinaFuncionarioRepository;
    @Autowired
    private OficinaFuncionarioEstatusRepository oficinaFuncionarioEstatusRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private FuncionarioMapper funcionarioMapper;
    @Autowired
    private FuncionarioOfMapper funcionarioOfMapper;
    @Autowired
    private OficinaMapper oficinaMapper;
    @Autowired
    private UsuarioMapper usuarioMapper;

    /**
     *
     * Metodo buscarPorLogin que permite consultar los datos de un funcionario segun su login
     *
     * @param login String que describe el login
     * @return Funcionario ojo que contiene al informacion del Funcionario
     * @throws java.lang.Exception
     */
    @Override
    public Funcionario buscarPorLogin(String login) throws Exception {
        LOG.info("=====INICIANDO FuncionarioBFImpl.buscarPorLogin========== " + login);

        if (login == null) {
            LOG.error("ERROR: en buscarPorLogin - login null");
            throw new Exception("Login null");
        }

        UsuarioEntidad usuarioEntidad;
        usuarioEntidad = usuarioRepository.buscarPorNombre(login + ".com");
        FuncionarioEntidad funcionarioEntidad;
        OficinaEntidad oficinaEntidad;
        Funcionario funcionario;
        Oficina oficina = null;
        Usuario usuario;
        if (usuarioEntidad != null) {
            usuario = usuarioMapper.entityToBO(usuarioEntidad);

            RolUsuarioEntidad rolUsuarioEntidad;
            rolUsuarioEntidad = rolUsuarioRepository.buscarIdRolFunc(usuarioEntidad.getNombre());
            if (rolUsuarioEntidad != null) {
                usuario.setCodigoRol(rolUsuarioEntidad.getRol().getCodigo());
                usuario.setNombreRol(rolUsuarioEntidad.getRol().getNombre());
            }
            funcionarioEntidad = usuarioEntidad.getOficinaFuncionario().getFuncionario();
            funcionario = funcionarioMapper.entityToBO(funcionarioEntidad);
            oficinaEntidad = oficinaRepository.findByCodigo(usuarioEntidad.getOficinaFuncionario().getOficina().getCodigo());

            if (oficinaEntidad != null) {
                oficina = oficinaMapper.entityToBO(oficinaEntidad);
            }

            List<Usuario> usuarios = new ArrayList<>();
            usuarios.add(usuario);

            funcionario.setUsuarios(usuarios);
            funcionario.setOficina(oficina);

        } else {
            LOG.error("ERROR: consultando el  usuariio - el usuario no existe");

            throw new Exception("Usuario no existe");
        }

        return funcionario;
    }

    /**
     *
     * Metodo buscarFuncionarioPorOficina que permite consultar los datos de un funcionario segun la oficina
     *
     * @param codOficina String que describe el codigo de la Oficina
     * @param codTipoFuncionario String que describe el codigo del tipo de Funcionario
     * @param codEstatusFuncionario String que describe el codigo del estatus del Funcionario
     *
     * @return Funcionario pojo que contiene la informacion del Funcionario
     * @throws java.lang.Exception
     */
    @Transactional
    @Override
    public Funcionario buscarFuncionarioPorOficina(String codOficina,
            String codTipoFuncionario, String codEstatusFuncionario) throws Exception {
        LOG.info("=====INICIANDO FuncionarioBFImpl.buscarFuncionarioPorOficina========== "
                + codOficina + codTipoFuncionario + codEstatusFuncionario);

        Funcionario funcionario;
        OficinaEntidad oficinaEntidad;
        TipoFuncionarioEntidad tipoFuncionarioEntidad;
        OficinaFuncionarioEstatusEntidad oficinaFuncionarioEstatusEntidad;
        OficinaFuncionarioEntidad oficinaFuncionarioEntidad;
        FuncionarioEntidad funcionarioEntidad;

        //lectura de properties 
        String idTipoFuncionario = buscarValorProperties(codTipoFuncionario);
        LOG.info("valor idTipoFunc --> " + idTipoFuncionario);
        String idEstatusOficFunc = buscarValorProperties(codEstatusFuncionario);
        LOG.info("valor idEstatusOficFunc --> " + idEstatusOficFunc);

        LOG.info("=====Buscando Oficina====");
        try {
            oficinaEntidad = buscarOficina(codOficina);
            LOG.info("Oficina+++++++++++++++++++++" + oficinaEntidad.getNombre());
        } catch (Exception e) {
            throw new Exception("Error buscando oficina: " + e.getMessage());
        }

        LOG.info("=====Buscando Rol de Tipo Funcionario====");
        try {
            tipoFuncionarioEntidad = buscarTipoFuncionario(Long.valueOf(idTipoFuncionario));
            LOG.info("Tipo Funcionario+++++++++++++++++++++" + tipoFuncionarioEntidad.getNombre());
        } catch (Exception e) {
            throw new Exception("Error buscando tipo funcionario: " + e.getMessage());
        }

        LOG.info("=====Buscando Estatus del Funcionario====");
        try {
            oficinaFuncionarioEstatusEntidad = buscarOficinaFuncionarioEstatus(Long.valueOf(idEstatusOficFunc));
            LOG.info("Estatus del Funcionario++++++" + oficinaFuncionarioEstatusEntidad.getNomOficFuncEstatus());
        } catch (Exception e) {
            throw new Exception("Error buscando estatus de la oficina funcionario: " + e.getMessage());
        }

        LOG.info("======Buscando Oficina Funcionario======");
        try{
            oficinaFuncionarioEntidad = buscarOficinaFuncionario(oficinaEntidad.getId(),
                    tipoFuncionarioEntidad.getId(), oficinaFuncionarioEstatusEntidad.getId());

            LOG.info("ID Tipo Funcionario: " + oficinaFuncionarioEntidad.getTipoFuncionario().getId());
        }catch (Exception e){
            throw new Exception("Error buscando oficina funcionario: "+e.getMessage());
        }

        try{
            funcionarioEntidad = buscarFuncionario(oficinaFuncionarioEntidad.getFuncionario().getId());
        }catch (Exception e){
            throw new Exception("Error buscando funcionario: "+e.getMessage());
        }

        if (funcionarioEntidad != null) {
            funcionario = funcionarioOfMapper.entityToBO(oficinaFuncionarioEntidad);

        } else {
            LOG.error("ERROR: consultando el  funcionario - el funcionario no existe");

            throw new Exception("Funcionario no existe");
        }

        return funcionario;
    }

    /**
     *
     * Metodo privado buscarOficina que permite buscar la Oficina
     *
     * @param codOficina String codigo de la Oficina
     * @return entidad que contiene la informacion de la Oficina
     */
    private OficinaEntidad buscarOficina(String codOficina) throws Exception {
        OficinaEntidad oficinaBuscar;

        oficinaBuscar = oficinaRepository.findByCodigo(codOficina);

        if (oficinaBuscar == null) {
            LOG.info("================no consiguio la oficina ================"
                    + codOficina);
            throw new Exception("Oficina tiene valor nulo");
        }
        return oficinaBuscar;
    }

    /**
     *
     * Metodo privado buscarTipoFuncionario que permite buscar el tipo de Funcionario
     *
     * @param idTipoFunc Long id del tipo de funcionario
     * @return entidad que contiene la informacion de TipoFuncionario
     */
    private TipoFuncionarioEntidad buscarTipoFuncionario(Long idTipoFunc) throws Exception {
        TipoFuncionarioEntidad tipoFuncionarioBuscar;
        tipoFuncionarioBuscar = tipoFuncionarioRepository.findById(idTipoFunc);

        if (tipoFuncionarioBuscar == null) {
            LOG.info("================no consiguio el tipo de Funcionario ================"
                    + idTipoFunc);
            throw new Exception("Tipo Funcionario tiene valor nulo");
        }
        return tipoFuncionarioBuscar;
    }

    /**
     *
     * Metodo privado buscarOficinaFuncionarioEstatus que permite buscar el estatus de OficinaFuncionario
     *
     * @param codEstatusFuncionario Long que describe el codigo del estatus del Funcionario
     * @return entidad que contiene la informacion de OficinaFuncionarioEstatusEntidad
     */
    private OficinaFuncionarioEstatusEntidad buscarOficinaFuncionarioEstatus(Long codEstatusFuncionario) throws Exception {
        OficinaFuncionarioEstatusEntidad oficinaFuncionarioEstatusBuscar;
        oficinaFuncionarioEstatusBuscar = oficinaFuncionarioEstatusRepository.findById(codEstatusFuncionario);

        if (oficinaFuncionarioEstatusBuscar == null) {
            LOG.info("================no consiguio la oficina Funcionario Estatus================"
                    + codEstatusFuncionario);
            throw new Exception("Oficina Funcionario Estatus tiene valor nulo");
        }
        return oficinaFuncionarioEstatusBuscar;
    }

    /**
     *
     * Metodo privado buscarOficinaFuncionario que permite buscar el OficinaFuncionario
     *
     * @param codOficina Long que describe el codigo del tipoFuncionario
     * @param codTipoFuncionario Long que describe el codigo del tipo de funcionario
     * @param codEstatusFuncionario Long que describe el codigo del estatus de funcionario
     * @return entidad que contiene la informacion de OficinaFuncionarioEntidad
     */
    private OficinaFuncionarioEntidad buscarOficinaFuncionario(Long codOficina,
            Long codTipoFuncionario, Long codEstatusFuncionario) throws Exception {

        OficinaFuncionarioEntidad oficinaFuncionarioBuscar;
        oficinaFuncionarioBuscar = oficinaFuncionarioRepository.
                findByOficinaIdAndTipoFuncionarioIdAndOficFuncEstatusId(codOficina, codTipoFuncionario,
                        codEstatusFuncionario);

        if (oficinaFuncionarioBuscar == null) {
            LOG.info("================no consiguio la Oficina Funcionario ================"
                    + codOficina + codTipoFuncionario + codEstatusFuncionario);
            throw new Exception("Oficina Funcionario tiene valor nulo");
        }

        return oficinaFuncionarioBuscar;
    }

    /**
     *
     * Metodo privado buscarFuncionario que permite buscar el tipo de Funcionario
     *
     * @param idFuncionario String codigo del tipoFuncionario
     * @return entidad que contiene la informacion de Funcionario
     */
    private FuncionarioEntidad buscarFuncionario(Long idFuncionario) throws Exception {
        FuncionarioEntidad funcionarioBuscar;
        funcionarioBuscar = funcionarioRepository.findById(idFuncionario);

        if (funcionarioBuscar == null) {
            LOG.info("================no consiguio el Funcionario ================"
                    + idFuncionario);
            throw new Exception("Oficina Funcionario tiene valor nulo");
        }

        return funcionarioBuscar;
    }

    /**
     * Metodo que permite buscar el valor de una propiedad en el archivo properties del servicio funcionario
     *
     * @param clave String Propiedad a buscar en ela rchivo properties
     * @return String con el valor de la propiedad
     */
    private String buscarValorProperties(String clave) {

        String valor = "";
        //lee properties
        LOG.info("buscarValorProperties --> clave --> " + clave);
        try {
            valor = properties.getProperty(clave);
        } catch (GenericException ex) {
            LOG.info("Error leyendo properties: " + ex);
        }
        LOG.info("buscarValorProperties --> valor --> " + valor);

        return valor;
    }
}
