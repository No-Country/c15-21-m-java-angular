package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.models.Productos;
import c1521mjavaangular.ecotienda.service.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("administrador")
@CrossOrigin(value = "http://localhost:4200") //Se Necesita la Url donde esta desplegado el fronted

public class ProductoController {
        @Autowired
        ProductoServiceImpl productoService;

        //http://url/api/administrador/productos
        @GetMapping("/productos")
            public List<Productos> obtenerProductos() {
            return productoService.listarProductos();
    }
}

