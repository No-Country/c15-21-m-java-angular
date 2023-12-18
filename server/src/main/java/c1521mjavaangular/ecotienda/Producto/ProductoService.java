package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Orden.OrdenDto;
import c1521mjavaangular.ecotienda.ProductoRating.ProductRatingDTO;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    public List<Productos> listarProductos();

    public Productos buscarProducto(Long id);

    public Productos guardarProducto(Productos productos);

    Optional<ProductoDto> buscarProductoOptional(Long id);

    public void eliminarProducto(Long id);

    void addRating(Long productosdId, Long usuariosId, int rating, String comment);
    double getAverageRating(Long productosId);
    List<ProductRatingDTO> getProductRatings(Long productosId);

}
