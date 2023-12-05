package c1521mjavaangular.ecotienda.repositories;

import c1521mjavaangular.ecotienda.models.Productos;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Productos, Integer>{

    @EntityGraph(attributePaths = "categorias")
    Optional<Productos> findById(Integer id);
}
