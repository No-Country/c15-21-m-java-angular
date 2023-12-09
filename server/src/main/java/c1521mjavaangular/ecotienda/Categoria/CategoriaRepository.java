package c1521mjavaangular.ecotienda.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository <Categorias, Long> {
    boolean existsByNombre(String nombre);
}
