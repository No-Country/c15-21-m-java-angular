package c1521mjavaangular.ecotienda.repositories;

import c1521mjavaangular.ecotienda.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByEmail(String email);
}
