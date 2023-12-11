package c1521mjavaangular.ecotienda.Producto;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, Long> {

    @EntityGraph(attributePaths = "categorias")
    Optional<Productos> findById(Long id);

    List<Productos> findByNombreContainingIgnoreCase(String palabraClave);
}


