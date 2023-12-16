package c1521mjavaangular.ecotienda.Usuarios;

import c1521mjavaangular.ecotienda.Jwt.JwtService;
import c1521mjavaangular.ecotienda.Producto.Productos;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosServiceImpl usuariosServiceImpl;
    private final JwtService jwtService;

    @Operation(summary = "Busca un usuario por su id")
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuariosServiceImpl.getByID(id));
    }
    @Operation(summary = "Busca a un usuario por su Username(Email)")
    @GetMapping("/username/{username}")
    public ResponseEntity<UsuariosResponse> getByUsername(@PathVariable String username)  {
        return ResponseEntity.ok().body(usuariosServiceImpl.getByUsername(username));
    }

    @Operation(summary = "Trae a todos los usuarios")
    @GetMapping()
    public ResponseEntity<List<UsuariosResponse>> traerTodos() {
        return ResponseEntity.ok().body(usuariosServiceImpl.findAll());
    }

    @Operation(summary = "Modifica un usuario")
    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@Valid @RequestBody UsuarioUpdateRequest request, @PathVariable Long id) {
        usuariosServiceImpl.update(request,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Borra a un usuario por su id")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        usuariosServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Agrega a favoritos un producto del usuario logeado")
    @PostMapping("/agregarFavorito/{productId}")
    public ResponseEntity<?> addToFavorites(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String tokenHeader) {
        Long userId = getUserIdFromToken(tokenHeader);
        usuariosServiceImpl.addToFavorites(userId, productId);
        return new ResponseEntity<>("Producto " + productId + " agregado a favoritos", HttpStatus.OK);
    }

    @Operation(summary = "Trae los favoritos de un Usuario")
    @GetMapping("/{userId}/productosFavoritos")
    public ResponseEntity<List<Productos>> getFavoriteProducts(@PathVariable Long userId) {
        List<Productos> favoriteProducts = usuariosServiceImpl.getFavoriteProducts(userId);
        return new ResponseEntity<>(favoriteProducts, HttpStatus.OK);
    }
    @Operation(summary = "Remueve de favoritos un producto del usuario logeado")
    @PostMapping("/removerFavorito/{productId}")
    public ResponseEntity<?> removeFavorite(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String tokenHeader) {
        Long userId = getUserIdFromToken(tokenHeader);
        usuariosServiceImpl.removeFavoriteProduct(userId, productId);
        return new ResponseEntity<>("Producto " + productId + " removido de favoritos", HttpStatus.OK);
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
