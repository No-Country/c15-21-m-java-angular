package c1521mjavaangular.ecotienda.Producto;

import c1521mjavaangular.ecotienda.Exceptions.RecursoNoEncontradoException;
import c1521mjavaangular.ecotienda.Jwt.JwtService;
import c1521mjavaangular.ecotienda.ProductoRating.ProductRatingDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
        @Autowired
        private JwtService jwtService;


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

    @PostMapping("/agregarReseña/{productId}")
    @Operation(summary = "Agrega una reseña a un producto")
    public ResponseEntity<?> addRating(
            @PathVariable Long productId,
            @RequestParam int rating,
            @RequestParam(required = false) String comment,
            @RequestHeader("Authorization") String tokenHeader
    ) {
        try {
            Long userId = getUserIdFromToken(tokenHeader);
            productoService.addRating(productId, userId, rating, comment);
            return new ResponseEntity<>("Reseña agregada exitosamente", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error al agregar la reseña: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}/reseñas")
    @Operation(summary = "Trae las reseñas de un producto")
    public ResponseEntity<List<ProductRatingDTO>> getProductRatings(@PathVariable Long productId) {
        try {
            List<ProductRatingDTO> ratings = productoService.getProductRatings(productId);
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        } catch (RecursoNoEncontradoException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{productId}/promedioReseñas")
    @Operation(summary = "Obtiene el promedio de calificación de un producto")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        try {
            double averageRating = productoService.getAverageRating(productId);
            return ResponseEntity.ok(averageRating);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Long getUserIdFromToken(String tokenHeader) {
        try {
            if (StringUtils.isNotEmpty(tokenHeader) && tokenHeader.startsWith("Bearer ")) {
                String token = tokenHeader.substring(7);

                UserDetails userDetails = getUserDetailsFromToken(token);

                if (jwtService.isTokenValid(token, userDetails)) {
                    return jwtService.extractUserIdFromToken(token);
                }
            }
            throw new RuntimeException("JWT token does not exist or is invalid");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT token has expired");
        } catch (JwtException e) {
            throw new RuntimeException("Error extracting UserID from JWT: " + e.getMessage());
        }
    }

    private UserDetails getUserDetailsFromToken(String token) {
        Claims claims = jwtService.extractAllClaims(token);
        String username = jwtService.extractUserName(token);

        return new User(username, "", new ArrayList<>());
    }
}

