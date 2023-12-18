package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Exceptions.RecursoNoEncontradoException;
import c1521mjavaangular.ecotienda.Orden.Orden;
import c1521mjavaangular.ecotienda.OrdenDetalles.OrdenDetalles;
import c1521mjavaangular.ecotienda.OrdenDetalles.OrdenDetallesRepository;
import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Producto.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("home")
@CrossOrigin(value = "https://c15-21-m-java-angular-ovaspx2qe-my-team-a0db5045.vercel.app/") //Se Necesita la Url donde esta desplegado el fronted

public class HomeController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private OrdenDetallesRepository ordenDetallesRepository;
    //Almacenar detalles de la orden
    List<OrdenDetalles> detalles = new ArrayList<OrdenDetalles>();
    //Datos de la Orden
    Orden orden = new Orden();


    @Operation(summary = "Listar los productos en el Home")
    @GetMapping
    public List<Productos> obtenerProductos() {
        return productoService.listarProductos();
    }

    @Operation(summary = "Detalle de Producto por id")
    @GetMapping("/{id}")
    public ResponseEntity<Productos> obtenerProductoPorId(
            @PathVariable Long id) {
        Productos productos = this.productoService.buscarProducto(id);
        if (productos != null)
            return ResponseEntity.ok(productos);
        else
            throw new RecursoNoEncontradoException("No se Encontro el id : " + id);
    }

}

