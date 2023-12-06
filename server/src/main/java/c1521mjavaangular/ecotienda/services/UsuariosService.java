package c1521mjavaangular.ecotienda.services;

import c1521mjavaangular.ecotienda.exceptions.IdNotFoundException;
import c1521mjavaangular.ecotienda.models.Usuarios;
import c1521mjavaangular.ecotienda.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuariosService implements IUsuariosService {

    private static final Logger logger = LogManager.getLogger(UsuariosService.class);

    private final UsuarioRepository usuarioRepository;
    private final ModelMapper mapper;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return usuarioRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }

    public Usuarios save(Usuarios newUser){
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }
        newUser.setUpdatedAt(LocalDateTime.now());
        return usuarioRepository.save(newUser);
    }

    @Override
    public void update(UsuarioUpdateRequest usuarioUpdateRequest,Long id)  {
        Usuarios usuarios = usuarioRepository.findById(id).orElseThrow(() -> new IdNotFoundException("El usuario con el id " + id + " no existe"));
        logger.info(usuarios.getAuthorities().toString());

        if (usuarioUpdateRequest.getNombre() != null) {
            usuarios.setNombre(usuarioUpdateRequest.getNombre());
        }
        if (usuarioUpdateRequest.getTelefono() != null) {
            usuarios.setTelefono(usuarioUpdateRequest.getTelefono());
        }
        if (usuarioUpdateRequest.getDireccion() != null) {
            usuarios.setDireccion(usuarioUpdateRequest.getDireccion());
        }

        usuarioRepository.save(usuarios);
    }

    @Override
    public UsuariosResponse getByUsername(String username) {
        return  mapper.map(usuarioRepository.findByEmail(username).orElseThrow(()->new c1521mjavaangular.ecotienda.exceptions.UsernameNotFoundException("El usuario " +username+ " no existe")),UsuariosResponse.class);
    }

    @Override
    public UsuariosResponse getByID(Long id)  {
        return mapper.map(usuarioRepository.findById(id).orElseThrow(()->new IdNotFoundException("El usuario con el id " +id+ " no existe")),UsuariosResponse.class);
    }

    @Override
    public void delete(Long id) {
        Usuarios usuarios = usuarioRepository.findById(id).orElseThrow(
                ()-> new IdNotFoundException("El usuario con el id " +id+ " no existe")
        );

        usuarios.setEnabled(false);
        usuarioRepository.save(usuarios);
    }
}
