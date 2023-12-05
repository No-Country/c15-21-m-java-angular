package c1521mjavaangular.ecotienda.repositories;

import c1521mjavaangular.ecotienda.models.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository <Categorias, Long> {
    boolean existsByNombre(String nombre);
}
