package c1521mjavaangular.ecotienda.ProductoRating;

import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {
    List<ProductRating> findByProduct_Id(Long productosId);
    boolean existsByProductAndUsuarios(Productos productos, Usuarios usuarios);
}