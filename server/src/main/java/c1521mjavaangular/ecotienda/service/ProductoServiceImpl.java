package c1521mjavaangular.ecotienda.service;


import c1521mjavaangular.ecotienda.models.Productos;
import c1521mjavaangular.ecotienda.repository.ProductoRepository;
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
    public Productos buscarProducto(Long id) {
        Productos productos = this.productoRepository.findById(id).orElse(null);
        return productos;
    }

    @Override
    public void guardarProducto(Productos productos) {
        this.productoRepository.save(productos);

    }

    @Override
    public void eliminarProducto(Long id) {
        this.productoRepository.deleteById(id);

    }
}
