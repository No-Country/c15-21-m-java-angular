package c1521mjavaangular.ecotienda.service;

import c1521mjavaangular.ecotienda.models.Productos;

import java.util.List;

public interface ProductoService {
    public List<Productos> listarProductos();

    public Productos buscarProducto(Integer id);

    public Productos guardarProducto(Productos productos);

    public void eliminarProducto(Integer id);

}
