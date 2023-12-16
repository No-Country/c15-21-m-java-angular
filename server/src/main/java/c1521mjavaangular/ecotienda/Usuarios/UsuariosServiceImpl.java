package c1521mjavaangular.ecotienda.Usuarios;

import c1521mjavaangular.ecotienda.Email.EmailService;
import c1521mjavaangular.ecotienda.Exceptions.IdNotFoundException;
import c1521mjavaangular.ecotienda.Producto.ProductoRepository;
import c1521mjavaangular.ecotienda.Producto.Productos;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private static final Logger logger = LogManager.getLogger(UsuariosServiceImpl.class);

    private final UsuariosRepository usuariosRepository;
    private final ModelMapper mapper;
    private final ProductoRepository productoRepository;

    @Autowired
    private EmailService emailService;

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return usuariosRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            }
        };
    }

    public Usuarios save(Usuarios newUser) throws MessagingException, IOException {
        if (newUser.getId() == null) {
            newUser.setCreatedAt(LocalDateTime.now());
        }
        newUser.setUpdatedAt(LocalDateTime.now());
        return usuariosRepository.save(newUser);
    }

    @Override
    public void update(UsuarioUpdateRequest usuarioUpdateRequest,Long id)  {
        Usuarios usuarios = usuariosRepository.findById(id).orElseThrow(() -> new IdNotFoundException("El usuario con el id " + id + " no existe"));
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

        usuariosRepository.save(usuarios);
    }

    @Override
    public UsuariosResponse getByUsername(String username) {
        return  mapper.map(usuariosRepository.findByEmail(username).orElseThrow(()->new c1521mjavaangular.ecotienda.Exceptions.UsernameNotFoundException("El usuario " +username+ " no existe")),UsuariosResponse.class);
    }

    @Override
    public UsuariosResponse getByID(Long id)  {
        return mapper.map(usuariosRepository.findById(id).orElseThrow(()->new IdNotFoundException("El usuario con el id " +id+ " no existe")),UsuariosResponse.class);
    }

    @Override
    public void delete(Long id) {
        Usuarios usuarios = usuariosRepository.findById(id).orElseThrow(
                ()-> new IdNotFoundException("El usuario con el id " +id+ " no existe")
        );

        usuarios.setEnabled(false);
        usuariosRepository.save(usuarios);
    }

    @Override
    public List<UsuariosResponse> findAll() {
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        return usuariosList.stream()
                .map(usuario -> mapper.map(usuario, UsuariosResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addToFavorites(Long userId, Long productId) {
        try {
            if (userId == null || productId == null) {
                throw new IllegalArgumentException("User ID and Product ID must not be null");
            }

            Optional<Usuarios> userOptional = usuariosRepository.findById(userId);
            Optional<Productos> productOptional = productoRepository.findById(productId);

            if (userOptional.isPresent() && productOptional.isPresent()) {
                Usuarios user = userOptional.get();
                Productos product = productOptional.get();

                user.getFavoriteProducts().add(product);
                product.getFavoritedByUsers().add(user);

                usuariosRepository.save(user);
                productoRepository.save(product);
            } else {
                throw new IdNotFoundException("Usuario o Producto no encontrado");
            }

            logger.info("Product {} added to favorites for user {}", productId, userId);
        } catch (Exception e) {
            logger.error("Error adding product {} to favorites for user {}: {}", productId, userId, e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Productos> getFavoriteProducts(Long userId) {
        Optional<Usuarios> userOptional = usuariosRepository.findById(userId);
        return userOptional.map(Usuarios::getFavoriteProducts).orElse(new ArrayList<>());
    }

    @Override
    public void removeFavoriteProduct(Long userId, Long productId) {
        Usuarios user = usuariosRepository.findById(userId).orElseThrow(() -> new IdNotFoundException("Usario no encontrado"));
        Productos product = productoRepository.findById(productId).orElseThrow(() -> new IdNotFoundException("Producto no encontrado"));

        user.getFavoriteProducts().remove(product);
        usuariosRepository.save(user);
    }

}
