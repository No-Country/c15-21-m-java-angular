package c1521mjavaangular.ecotienda.controllers;

import c1521mjavaangular.ecotienda.exceptions.RecursoNoEncontradoException;
import c1521mjavaangular.ecotienda.models.Productos;
import c1521mjavaangular.ecotienda.services.ProductoService;
import c1521mjavaangular.ecotienda.services.UploadFilesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("administrador")
@CrossOrigin(value = "http://localhost:4200") //Se Necesita la Url donde esta desplegado el fronted

public class ProductoController {
        @Autowired
        ProductoService productoService;
        @Autowired
        private UploadFilesService upload;


        //http://url/api/administrador/productos
        @Operation(summary = "Lista los productos en la base de datos")
        @GetMapping("/productos")
            public List<Productos> obtenerProductos() {
            return productoService.listarProductos();
    }

        @Operation(summary = "agrega productos a la base de datos")
        @PostMapping("/productos")
            public Productos agregarProducto(@RequestBody Productos productos, @RequestParam("img") MultipartFile file) throws IOException {
            //Imagen
            if (productos.getId() == null) {
                String nombreImagen = upload.guardarImagen(file);
                productos.setImagen(nombreImagen);
            } else {
            }
            return this.productoService.guardarProducto(productos);
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
    @Operation(summary = "Actualiza un producto por su id")
    @PutMapping("/productos/{id}")
    public ResponseEntity<Productos> actualizarProducto(
            @PathVariable int id,
            @RequestBody Productos productoRecibido,
            @RequestParam(value = "img", required = false) MultipartFile file) throws IOException {
            Productos productos = this.productoService.buscarProducto(id);
             if (productos == null)
                throw new RecursoNoEncontradoException("No se encontró el id: " + id);

            // Si se proporciona una imagen nueva
             if (file.isEmpty()) {
                Productos p = new Productos();
                p = productoService.buscarProducto(productos.getId());
            } else{
                 String nombreImagen = upload.guardarImagen(file);
                 productos.setImagen(nombreImagen);
             }

              // Actualizar otros campos
                productos.setCategorias(productoRecibido.getCategorias());
                productos.setPrecio(productoRecibido.getPrecio());
                productos.setCodigo(productoRecibido.getCodigo());
                productos.setNombre(productoRecibido.getNombre());
                productos.setStock(productoRecibido.getStock());

                this.productoService.guardarProducto(productos);
                return ResponseEntity.ok(productos);
    }

        @Operation(summary = "elimina un producto por su id")
        @DeleteMapping("productos/{id}")
        public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id){
            Productos productos = this.productoService.buscarProducto(id);
            if(productos == null)
                throw new RecursoNoEncontradoException("No se encontro el id: " + id);
            // Borrar la imagen antes de eliminar el producto
            if (productos.getImagen() != null && !productos.getImagen().isEmpty()) {
                upload.borrarImagen(productos.getImagen());
            }
            this.productoService.eliminarProducto(productos.getId());
            Map<String, Boolean> respuesta = new HashMap<>();
            respuesta.put("eliminado", Boolean.TRUE);
            return ResponseEntity.ok(respuesta);
        }


}

