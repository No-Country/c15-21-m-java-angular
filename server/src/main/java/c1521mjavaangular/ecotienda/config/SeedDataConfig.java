package c1521mjavaangular.ecotienda.config;

import c1521mjavaangular.ecotienda.models.Role;
import c1521mjavaangular.ecotienda.models.Usuarios;
import c1521mjavaangular.ecotienda.repositories.UsuarioRepository;
import c1521mjavaangular.ecotienda.services.UsuariosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuariosService usuariosService;

    @Override
    public void run(String... args) throws Exception {

        if (usuarioRepository.count() == 0) {

            Usuarios admin = Usuarios
                    .builder()
                    .nombre("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_ADMIN)
                    .build();

            usuariosService.save(admin);
            log.debug("created ADMIN user - {}", admin);
        }
    }

}
