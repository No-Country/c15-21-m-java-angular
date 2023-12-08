package c1521mjavaangular.ecotienda.Producto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Productos> listarProductos() {
        return this.productoRepository.findAll();

    }

    @Override
    public Productos buscarProducto(Integer id) {
        Productos productos = this.productoRepository.findById(id).orElse(null);
        return productos;
    }

    @Override
    public Productos guardarProducto(Productos productos) {
        return this.productoRepository.save(productos);
    }

    @Override
    public void eliminarProducto(Integer id) {
        this.productoRepository.deleteById(id);

    }
}
