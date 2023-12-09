package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@CrossOrigin(value = "http://localhost:4200") //Se Necesita la Url donde esta desplegado el fronted

public class HomeController {
    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar los productos en el Home")
    @GetMapping
    public List<Productos> obtenerProductos() {
        return productoService.listarProductos();
    }
}
