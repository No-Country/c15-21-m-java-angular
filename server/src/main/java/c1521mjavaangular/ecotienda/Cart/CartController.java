package c1521mjavaangular.ecotienda.Cart;

import c1521mjavaangular.ecotienda.Orden.OrdenDto;
import c1521mjavaangular.ecotienda.Producto.ProductoRepository;
import c1521mjavaangular.ecotienda.Producto.Productos;
import c1521mjavaangular.ecotienda.Usuarios.Usuarios;
import c1521mjavaangular.ecotienda.Usuarios.UsuariosRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("/api/cart")
    public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Operation(summary = "Crear un carrito asociado a un usuario")
    @PostMapping("/public/carts/user/{email}")
    public ResponseEntity<CartDTO> createCartForUser(@PathVariable String email) {
        CartDTO cartDTO = cartService.createCart(email);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }


    @Operation(summary = "Obtener detalles del carrito")
    @GetMapping("/public/carts/{cartId}")
    public ResponseEntity<CartDTO> getCartDetails(@PathVariable Long cartId) {
        CartDTO cartDTO = cartService.getCartDetails(cartId);
        return new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }

    @Operation(summary = "Obtener carrito por email")
    @GetMapping("/public/carts/email/{emailId}")
    public  ResponseEntity<CartDTO> getCartDetailsbyEmail(@PathVariable String emailId){
        CartDTO cartDTO = cartService.getCartbyEmail(emailId);
        return  new ResponseEntity<>(cartDTO, HttpStatus.OK);
    }


    @Operation(summary = "agregar al carrito")
    @PostMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Integer quantity) {
        CartDTO cartDTO = cartService.addProductToCart(cartId, productId, quantity);

        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

        @Operation(summary = "ver todos los carritos")

        @GetMapping("/admin/carts")
        public ResponseEntity<List<CartDTO>> getCarts() {

            List<CartDTO> cartDTOs = cartService.getAllCarts();

            return new ResponseEntity<List<CartDTO>>(cartDTOs, HttpStatus.FOUND);
        }

        @Operation(summary = "obtener datos del carrito")
        @GetMapping("/public/users/{emailId}/carts/{cartId}")
        public ResponseEntity<CartDTO> getCartById(@PathVariable String emailId, @PathVariable Long cartId) {
            CartDTO cartDTO = cartService.getCart(emailId, cartId);

            return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.FOUND);
        }

        @Operation(summary = "editar elementos del carrito")
        @PutMapping("/public/carts/{cartId}/products/{productId}/quantity/{quantity}")
        public ResponseEntity<CartDTO> updateCartProduct(@PathVariable Long cartId, @PathVariable Long productId, @PathVariable Integer quantity) {
            CartDTO cartDTO = cartService.updateProductQuantityInCart(cartId, productId, quantity);

            return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.OK);
        }

        @Operation(summary = "borrar elementos del carrito")
        @DeleteMapping("/public/carts/{cartId}/product/{productId}")
        public ResponseEntity<String> deleteProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
            String status = cartService.deleteProductFromCart(cartId, productId);

            return new ResponseEntity<String>(status, HttpStatus.OK);
        }


}
