package c1521mjavaangular.ecotienda.Config;

import c1521mjavaangular.ecotienda.Usuarios.Role;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedDataConfig implements CommandLineRunner {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuariosServiceImpl usuariosServiceImpl;

    @Override
    public void run(String... args) throws Exception {

        if (usuariosRepository.count() == 0) {

            Usuarios admin = Usuarios
                    .builder()
                    .nombre("admin")
                    .email("admin@admin.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ROLE_ADMIN)
                    .build();

            usuariosServiceImpl.save(admin);
            log.debug("created ADMIN user - {}", admin);
        }
    }

}
