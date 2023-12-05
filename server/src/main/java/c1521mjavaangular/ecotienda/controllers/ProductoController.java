package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.exceptions.RecursoNoEncontradoException;
import c1521mjavaangular.ecotienda.models.Productos;
import c1521mjavaangular.ecotienda.services.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("administrador")
@CrossOrigin(value = "http://localhost:4200") //Se Necesita la Url donde esta desplegado el fronted

public class ProductoController {
        @Autowired
        ProductoService productoService;

        //http://url/api/administrador/productos
        @Operation(summary = "Lista los productos en la base de datos")
        @GetMapping("/productos")
            public List<Productos> obtenerProductos() {
            return productoService.listarProductos();
    }

        @Operation(summary = "agrega productos a la base de datos")
        @PostMapping("/productos")
            public Productos agregarProducto(@RequestBody Productos productos){
            return  this.productoService.guardarProducto(productos);

        }

        @Operation(summary = "Busca un producto por id")
        @GetMapping("/productos/{id}")
            public ResponseEntity<Productos> obtenerProductoPorId(
                    @PathVariable int id){
            Productos productos = this.productoService.buscarProducto(id);
            if(productos != null)
                return ResponseEntity.ok(productos);
            else
                throw new RecursoNoEncontradoException("No se Encontro el id : " + id);
        }
        @Operation(summary = "actualiza un producto por su id")
        @PutMapping("/productos/{id}")
        public ResponseEntity<Productos> actualizarProducto(
                @PathVariable int id,
                @RequestBody Productos productoRecibido){
            Productos productos = this.productoService.buscarProducto(id);
            if(productos == null)
                throw new RecursoNoEncontradoException("No se encontro el id: " + id);
            productos.setCategorias(productoRecibido.getCategorias());
            productos.setPrecio(productoRecibido.getPrecio());
            productos.setCodigo(productoRecibido.getCodigo());
            productos.setNombre(productoRecibido.getNombre());
            productos.setStock(productoRecibido.getStock());
            productos.setImagen(productos.getImagen());
            this.productoService.guardarProducto(productos);
            return ResponseEntity.ok(productos);
        }

        @Operation(summary = "elimina un producto por su id")
        @DeleteMapping("productos/{id}")
        public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id){
            Productos productos = this.productoService.buscarProducto(id);
            if(productos == null)
                throw new RecursoNoEncontradoException("No se encontro el id: " + id);
            this.productoService.eliminarProducto(productos.getId());
            Map<String, Boolean> respuesta = new HashMap<>();
            respuesta.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(respuesta);
        }


}

