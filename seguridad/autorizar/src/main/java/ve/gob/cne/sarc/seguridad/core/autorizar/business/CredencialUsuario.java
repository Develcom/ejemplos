package ve.gob.cne.sarc.seguridad.core.autorizar.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ve.gob.cne.sarc.persistencia.entidades.FuncionarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.OficinaEntidad;
import ve.gob.cne.sarc.persistencia.entidades.RolUsuarioEntidad;
import ve.gob.cne.sarc.persistencia.entidades.UsuarioEntidad;
import ve.gob.cne.sarc.persistencia.repositorios.UsuarioRepository;
import ve.gob.cne.sarc.seguridad.core.autorizar.oauth.Usuario;

/**
 * CredencialUsuario.java
 *
 * @descripcion Clase que implementa {@link UserDetailsService}
 * @version 1.0
 * @author Erick Escalona
 *
 */
@Component
public class CredencialUsuario implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(CredencialUsuario.class);

    @Autowired
    private UsuarioRepository repository;

    /**
     * Comprueba las credenciales del usuario
     *
     * @param username El usuario
     * @return Un registro del usuario
     * @throws UsernameNotFoundException Si el usuario no se ha encontrado o el
     * usuario no tiene {@link GrantedAuthority}
     */
    @Override
    @Transactional
    public Usuario loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioEntidad usuarioEntidad;
        FuncionarioEntidad funcionarioEntidad;
        OficinaEntidad oficinaEntidad;
        List<RolUsuarioEntidad> rue;
        Set<GrantedAuthority> setAuths = new HashSet<>();
        List<GrantedAuthority> auth;
        String rol = "";
        Usuario usuario;

        LOG.info("validando credenciales del usuario " + username);

        usuarioEntidad = repository.buscarPorNombre(username);

        funcionarioEntidad = usuarioEntidad.getOficinaFuncionario().getFuncionario();
        oficinaEntidad = usuarioEntidad.getOficinaFuncionario().getOficina();

        if (usuarioEntidad == null) {
            LOG.error("error al buscar el usuario " + username);
            throw new UsernameNotFoundException("Usuario " + username + " no encontrado.");
        } else {

            rue = usuarioEntidad.getRolUsuario();

            for (RolUsuarioEntidad ru : rue) {
                setAuths.add(new SimpleGrantedAuthority(ru.getRol().getCodigo()));
                rol = ru.getRol().getNombre();
            }
            auth = new ArrayList<>(setAuths);

            usuario = new Usuario(usuarioEntidad.getNombre(), usuarioEntidad.getClaveUsuario(),
                    true, true, true, true, auth);

            usuario.setOficina(oficinaEntidad.getNombre());
            usuario.setPrimerNombre(funcionarioEntidad.getPrimerNombre());
            if (funcionarioEntidad.getSegundoNombre() != null) {
                usuario.setSegundoNombre(funcionarioEntidad.getSegundoNombre());
            } else {
                usuario.setSegundoNombre("");
            }
            usuario.setPrimerApellido(funcionarioEntidad.getPrimerApellido());
            if (funcionarioEntidad.getSegundoApellido() != null) {
                usuario.setSegundoApellido(funcionarioEntidad.getSegundoApellido());
            } else {
                usuario.setSegundoApellido("");
            }
            usuario.setRol(rol);
        }

        return usuario;
    }

}
