package c1521mjavaangular.ecotienda.Producto;

import java.util.List;

public interface ProductoService {
    public List<Productos> listarProductos();

    public Productos buscarProducto(Long id);

    public Productos guardarProducto(Productos productos);

    public void eliminarProducto(Long id);

}
