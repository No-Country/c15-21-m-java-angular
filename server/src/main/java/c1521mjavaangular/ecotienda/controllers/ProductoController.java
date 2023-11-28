package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.models.Productos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductoController {

    @GetMapping("/api/producto")
    public List<Productos> findAll(){
        List<Productos> productosList = new ArrayList<>();
        Productos productos = new Productos();
        productos.setCategoria("Cosmetica");
        productos.setNombre("Shampoo solido");
        productos.setCodigo("1234");
        productos.setImagen("url de la imagen");
        productos.setPrecio(500);
        productos.setStock(100);
        productos.setId(1L);
        productosList.add(productos);
        return productosList;
    }
}
