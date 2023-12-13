package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Exceptions.RecursoNoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("administrador")
@CrossOrigin(value = "http://localhost:4200") //Se Necesita la Url donde esta desplegado el fronted

public class ProductoController {
        @Autowired
        private ProductoService productoService;
        @Autowired
        private UploadFilesService upload;


        //http://url/administrador/productos
        @Operation(summary = "Lista los productos en la base de datos")
        @GetMapping("/productos")
            public List<Productos> obtenerProductos() {
            return productoService.listarProductos();
    }

         @Operation(summary = "agrega productos a la base de datos")
         @PostMapping("/productos")
        public Productos agregarProducto(@RequestBody Productos productos){
        return productoService.guardarProducto(productos);
        }
        @Operation(summary = "Busca un producto por id")
        @GetMapping("/productos/{id}")
            public ResponseEntity<Productos> obtenerProductoPorId(
                    @PathVariable Long id){
            Productos productos = this.productoService.buscarProducto(id);
            if(productos != null)
                return ResponseEntity.ok(productos);
            else
                throw new RecursoNoEncontradoException("No se Encontro el id : " + id);
        }
        @Operation(summary = "Actualiza un producto por su id")
        @PutMapping("/productos/{id}")
        public ResponseEntity<Productos> actualizarProducto(
            @PathVariable Long id,
            @RequestBody Productos productoRecibido) {
            Productos productos = this.productoService.buscarProducto(id);

            if (productos == null)
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);

            productos.setCategorias(productoRecibido.getCategorias());
            productos.setPrecio(productoRecibido.getPrecio());
            productos.setCodigo(productoRecibido.getCodigo());
            productos.setNombre(productoRecibido.getNombre());
            productos.setStock(productoRecibido.getStock());
            productos.setDescripcion(productoRecibido.getDescripcion());
            productos.setImagen(productoRecibido.getImagen());

            this.productoService.guardarProducto(productos);
            return ResponseEntity.ok(productos);
         }

        @Operation(summary = "elimina un producto por su id")
        @DeleteMapping("productos/{id}")
        public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable Long id) {
        Productos productos = this.productoService.buscarProducto(id);
        if (productos == null) {
            throw new RecursoNoEncontradoException("No se encontró el id: " + id);
        }

        // Actualizar el producto para reflejar el cambio en la base de datos
        this.productoService.guardarProducto(productos);

        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }


}

